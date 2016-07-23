package com.wenwo.message.api;

import java.util.List;
import java.util.Map;

import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.Variable;
import com.wenwo.message.model.Variable.VariableType;


public interface IVariableService {

	public int save(Variable variable);
	
	
	public List<Variable> getVariables(VariableType variableType,MessageProjectType messageProjectType);
	
	public void delete(String id);
	
	public Variable getVariableById(String id);
	
	public void update(Variable variable);
	
	public List<Variable> getVariableByType(VariableType variableType);
	
	public List<Variable> getexpress(String variableName);
	
	/**
	 * 
	 * @return 变量分类与属于该分类的所有变量的映射
	 */
	public Map<VariableType, List<Variable>> groupVariableByType(MessageProjectType projectType);


	/**
	 * @return
	 */
	public List<Variable> getAllVariable();
	
}
