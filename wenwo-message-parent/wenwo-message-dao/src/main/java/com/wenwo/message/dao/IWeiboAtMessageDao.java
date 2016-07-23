/**
 * 
 */
package com.wenwo.message.dao;

/**
 * @author shuangtai
 *
 */
public interface IWeiboAtMessageDao {

	/**
	 * @param weiboId
	 * @return
	 */
	String getQuestionIdByWeiboId(String weiboId);

	/**
	 * @param weiboId
	 * @return
	 */
	String getAnswerIdByWeiboId(String weiboId);

}
