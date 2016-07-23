package com.wenwo.message.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.WenwoMongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.wenwo.message.dao.IVariableDao;
import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.fieldconstants.MessageConfigBaseFields;
import com.wenwo.message.fieldconstants.MessageTypeFields;
import com.wenwo.message.fieldconstants.VariableFields;
import com.wenwo.message.model.Variable;
import com.wenwo.message.model.Variable.VariableType;
import com.wenwo.message.model.base.MessageConfigBase.Status;

public class VariableDao extends BaseDao implements IVariableDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VariableDao.class);
	
	public static final String AGGREGATION_FORMMAT = "$%s";

	private WenwoMongoTemplate mongoTemplate;
	
	@Override
	public int save(Variable variable) {
		mongoUtil.save(variable);
		return 0;
	}

	@Override
	public List<Variable> getVariables(VariableType variableType,MessageProjectType messageProjectType) {
        Query query = new Query();
		
		if(variableType != null){
			query.addCriteria(Criteria.where(MessageTypeFields.VARIABLE_TYPE).is(variableType.name()));
		}
		
		if(messageProjectType != null){
			query.addCriteria(Criteria.where(MessageTypeFields.VARIABLE_PROJECT_TYPE).is(messageProjectType.name()));
		}
		Sort sort = new Sort(Direction.ASC, MessageTypeFields.END_TIME);
		query.with(sort);
		return mongoUtil.find(query, Variable.class);
	}

	@Override
	public void delete(String id) {
		Variable variable = new Variable();
		variable.setId(id);
		mongoUtil.remove(variable);
	}

	@Override
	public Variable getVariableById(String id) {
		Criteria criteria = Criteria.where(id).is(id);
		return mongoUtil.getEntity(Variable.class, new Criteria[]{criteria});
	}

	@Override
	public void update(Variable variable) {
		variable.setStatus(Status.NEED_LOAD);
		mongoUtil.update(variable);
		
	}

	@Override
	public List<Variable> getVariableByType(VariableType variableType) {
		   Query query = new Query();
			if(variableType != null){
				query.addCriteria(Criteria.where(MessageTypeFields.VARIABLE_TYPE).is(variableType.name()));
//				query.addCriteria(Criteria.where(MessageTypeFields.VARIABLE_PROJECT_TYPE).is(MessageProjectType.DEFAULT));
			}
			query.addCriteria(Criteria.where(MessageConfigBaseFields.STATUS).ne(Status.DELETE.name()));
			Sort sort = new Sort(new Order(Direction.ASC, VariableFields.VARIABLE_TYPE),
					new Order(Direction.ASC, VariableFields.NAME));
			query.with(sort);
		 return mongoUtil.find(query, Variable.class);
	}

	@Override
	public List<Variable> getexpress(String variableName) {
		 Query query = new Query();
			if(variableName != null){
				query.addCriteria(Criteria.where("name").is(variableName));
			}
		return mongoUtil.find(query, Variable.class);
	}

	@Override
	public Map<VariableType, List<Variable>> groupVariableByType(MessageProjectType projectType) {
		DBCollection coll = this.mongoTemplate.getCollection(VariableFields.COLLECTION_NAME);
		
		DBObject matchFields = new BasicDBObject(VariableFields.VARIABLE_TYPE, new BasicDBObject("$exists", true));
		
		if(MessageProjectType.DEFAULT == projectType){
			matchFields.put(VariableFields.PROJECT_TYPE, MessageProjectType.DEFAULT.name());
		}else{
			List<String> typeList = new ArrayList<String>();
			typeList.add(projectType.name());
			typeList.add(MessageProjectType.DEFAULT.name());
			matchFields.put(VariableFields.PROJECT_TYPE, new BasicDBObject("$in", typeList));
		}
		
		matchFields.put(MessageConfigBaseFields.STATUS, new BasicDBObject("$ne", Status.DELETE.name()));
		
		DBObject matchCommand = new BasicDBObject("$match", matchFields);
		
		DBObject variable = getVariableObject();
		DBObject groupFields = new BasicDBObject("_id", String.format(AGGREGATION_FORMMAT, VariableFields.VARIABLE_TYPE));
		groupFields.put("variable_list", new BasicDBObject("$push", variable));
		DBObject groupCommand = new BasicDBObject("$group", groupFields);
		
		AggregationOutput output = coll.aggregate(matchCommand, groupCommand);
		LOGGER.info("result output:{}", output);
		
		Map<VariableType, List<Variable>> typeAndList = new HashMap<VariableType, List<Variable>>();
		
		Iterator<DBObject> resultIterator = output.results().iterator();
		
		while(resultIterator.hasNext()){
			BasicDBObject result = (BasicDBObject)resultIterator.next();
			VariableType variableType = VariableType.valueOf(result.getString("_id"));
			
			@SuppressWarnings("unchecked")
			List<BasicDBObject> variableObjectList = (List<BasicDBObject>) result.get("variable_list");
			if(variableObjectList != null){
				List<Variable> variableList = null;
				if(typeAndList.containsKey(variableType)){
					variableList = typeAndList.get(variableType);
				}else{
					variableList = new ArrayList<Variable>();
					typeAndList.put(variableType, variableList);
				}
				
				MongoConverter converter = this.mongoTemplate.getConverter();
				for(BasicDBObject variableObject : variableObjectList){
					Variable vari = converter.read(Variable.class, variableObject);
					variableList.add(vari);
				}
			}
			
		}
		
		return typeAndList;
	}

	/**
	 * @return
	 */
	private DBObject getVariableObject() {
		DBObject variable = new BasicDBObject();
		variable.put(MessageConfigBaseFields.ID, String.format(AGGREGATION_FORMMAT, MessageConfigBaseFields.ID));
		variable.put(MessageConfigBaseFields.CREATE_TIME,  String.format(AGGREGATION_FORMMAT, MessageConfigBaseFields.CREATE_TIME));
		variable.put(MessageConfigBaseFields.EDITOR,  String.format(AGGREGATION_FORMMAT, MessageConfigBaseFields.EDITOR));
		variable.put(MessageConfigBaseFields.END_TIME,  String.format(AGGREGATION_FORMMAT, MessageConfigBaseFields.END_TIME));
		variable.put(MessageConfigBaseFields.INPUTOR,  String.format(AGGREGATION_FORMMAT, MessageConfigBaseFields.INPUTOR));
		
		
		variable.put(VariableFields.NAME,  String.format(AGGREGATION_FORMMAT, VariableFields.NAME));
		variable.put(VariableFields.PROJECT_TYPE,  String.format(AGGREGATION_FORMMAT, VariableFields.PROJECT_TYPE));
		variable.put(VariableFields.VARIABLE_TYPE,  String.format(AGGREGATION_FORMMAT, VariableFields.VARIABLE_TYPE));
		variable.put(VariableFields.VELOCITY_EXPRESS,  String.format(AGGREGATION_FORMMAT, VariableFields.VELOCITY_EXPRESS));
		return variable;
	}

	@Override
	public List<Variable> getAllVariable() {
		Query query = new Query();
		Criteria criteria = Criteria.where(MessageConfigBaseFields.STATUS).ne(Status.DELETE.name());
		Sort sort = new Sort(new Order(Direction.ASC, VariableFields.VARIABLE_TYPE),
				new Order(Direction.ASC, VariableFields.NAME));
		query.with(sort);
		return mongoUtil.getList(Variable.class, sort, criteria);
	}

	@Override
	public void changeNeedLoadStatus() {
		Query query = new Query();
		Criteria criteria = Criteria.where(MessageConfigBaseFields.STATUS).is(Status.NEED_LOAD.name());
		query.addCriteria(criteria);
		
		Update update = Update.update(MessageConfigBaseFields.STATUS, Status.IN_USE.name());
		mongoUtil.updateMulti(query, update, Variable.class);
	}

	public WenwoMongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(WenwoMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
}
