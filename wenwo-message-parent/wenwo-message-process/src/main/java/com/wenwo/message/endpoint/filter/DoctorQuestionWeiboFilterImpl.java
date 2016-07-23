package com.wenwo.message.endpoint.filter;

import java.util.Date;
import java.util.Map;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import com.wenwo.message.constants.MessageConstants;
import com.wenwo.message.constants.TemplateConstants;
import com.wenwo.message.request.MessageRequest;
import com.wenwo.platform.entity.doctor.DrAnswer;
import com.wenwo.platform.entity.doctor.DrQuestion;
import com.wenwo.platform.entity.doctor.DrQuestion.QuestionType;
 
/**
 * 微问医生 集合消息的filter(目前为止，集合消息发送私信)
 * @author laisq
 *
 */
public class DoctorQuestionWeiboFilterImpl extends AbstractEndpointFilter {

	private static final Logger logger = LoggerFactory.getLogger(DoctorQuestionWeiboFilterImpl.class);
	
	
	@Autowired
	private ShardedJedisPool shardedJedisPool;
	
	/**
	 * 这里实现doctorNewQuestion,doctorQuestionMore这2种消息类型的集合消息
	 * 返回true，证明进入私信发送
	 */
	public boolean preHandler(MessageRequest request,Map<String,Object> preMap) throws Exception {
		VelocityContext context = request.getContext();
		String messageType = request.getMessageType();
		String targetId = request.getTargetUser().getId();
		
		//获取目标ID（questionid或者answerid）
//		}
		//boolean firstComingFlag = isFirstComing(uid);
		//在8-22点之间
		if(request.getContext().containsKey("isSmsSend")){
			return false;
		}
		if(isInTime()) {
			return true;
		}else{
			//当消息类型为新问题时，且是点对点和收费问题时（包括优惠券），则保存起来，等隔日再发
			if(messageType != null && messageType.equals("doctorNewQuestion")) { //新回答
				DrQuestion drQuestion = (DrQuestion)context.get(TemplateConstants.CONTEXT_QUESTION);
				if (drQuestion != null
						&& (drQuestion.getRealReward() != 0 || drQuestion
								.getPayCouponId() != null)
						&& drQuestion.getKindType().equals(QuestionType.P2P)) {
					targetId = drQuestion.getId();
					
	                saveIdToSortedSet(targetId);
				}
			} else {  //追问
				//追问也是隔日再发
				DrAnswer drAnswer = (DrAnswer)context.get(TemplateConstants.CONTEXT_ANSWER);
				if(drAnswer != null) {
					targetId = drAnswer.getId();
					saveIdToSortedSet(targetId);
	
				}
				
			}
			
			saveMessageToHash(targetId, messageType);
			
			return false;
		}
		
	}
	

	public void postHandler(MessageRequest request,Map<String,Object> reponseMap) throws Exception {
		
		
	}
	
	
	/**
	 * 是否在8-22点之间
	 * @return
	 */
	//testceshi
	@SuppressWarnings("deprecation")
	private boolean isInTime() {
		//开始时间
		Date startTime = new Date();
		startTime.setHours(8);
		startTime.setMinutes(0);
		startTime.setSeconds(0);
		Long start = startTime.getTime();
		
		//结束时间
		Date endTime = new Date();
		endTime.setHours(22);
		endTime.setMinutes(0);
		endTime.setSeconds(0);
		Long end = endTime.getTime();
		
		Long now = new Date().getTime();
		if(now >start && now < end){
			return true;
		}
		return false;
	}

	/**
	 * 保存到Hash
	 * @param request
	 * @param uid
	 */
	public void saveToHash(MessageRequest request,String uid,String targetId) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			/**
			 * 存储在Hash集合
			 * key:doctor_batch_message_user_set_uid
			 * 
			 * Hash key:answerid或者questionid
			 * Hash value：消息类型
			 */
			jedis.hset(MessageConstants.DOCTOR_BATCH_MESSAGE_USER_STATIS_PREFIX+uid, targetId, request.getMessageType());
			logger.info("保存到Hash,key为{},目标ID为{},消息类型为{}",MessageConstants.DOCTOR_BATCH_MESSAGE_USER_STATIS_PREFIX+uid,targetId,request.getMessageType());
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
	}
	
	/**
	 * 保存在sortedSet
	 * @param uid
	 */
	@SuppressWarnings("unused")
	private void saveToSortedSet(String uid) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			/**
			 * sortedSet集合
			 * 如果该用户在集合消息列表中不存在，则把该用户加入此列表，同时，使用当前时间戳作为其score，方便后续2个小时后取出该用户
			 * 如果该用户已经存在，则不能再add一次，否则会覆盖掉score
			 * 
			 * key：doctor_batch_message_user_set
			 * 
			 * value：uid
			 * score：时间戳
			 */
			//第一次就添加
			if(jedis.zrank(MessageConstants.DOCTOR_BATCH_MESSAGE_USER_SET_KEY, uid) == null) {
				//jedis.zrank(MessageConstants.DOCTOR_BATCH_MESSAGE_USER_SET_KEY, uid)<0
				jedis.zadd(MessageConstants.DOCTOR_BATCH_MESSAGE_USER_SET_KEY, System.currentTimeMillis(), uid);
				logger.info("sortedset中添加用户uid为{},时间戳为{}",uid,System.currentTimeMillis());
			}
			
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
		
	}
	
	/**
	 * 保存在sortedSet
	 * @param phone
	 */
	private void saveIdToSortedSet(String targetId){
		ShardedJedis jedis = shardedJedisPool.getResource();
		try{
			if(jedis.zrank(MessageConstants.MESSAGE_USER_SET_KEY,targetId)==null){
				jedis.zadd(MessageConstants.MESSAGE_USER_SET_KEY,System.currentTimeMillis(),targetId);
			}
			
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
	}
	
	/**
	 * 保存在sortedSet
	 * @param phone
	 */
	private void saveMessageToHash(String targetId,String MessageType){
		ShardedJedis jedis = shardedJedisPool.getResource();
		try{
			Long timeTemp=System.currentTimeMillis();
			jedis.hset(MessageConstants.MESSAGE_USER_CONTENT_KEY
					+targetId ,timeTemp+"_"+targetId,MessageType);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
	}
	
	public static void main(String args[]) {
		//new DoctorQuestionWeiboFilterImpl().isInTime();
		//new DoctorQuestionWeiboFilterImpl().isFirstComing("527515d4e4b0ac17cf3b3244");
		
		//new DoctorQuestionWeiboFilterImpl().saveIdToSortedSet("54607d51e4b04ae61b706864");
		//new DoctorQuestionWeiboFilterImpl().saveMessageToHash("54607d51e4b04ae61b706864","doctorNewQuestion");
	}
	
}
