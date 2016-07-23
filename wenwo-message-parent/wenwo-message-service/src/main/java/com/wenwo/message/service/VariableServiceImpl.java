package com.wenwo.message.service;

import java.util.List;
import java.util.Map;

import com.wenwo.message.api.IVariableService;
import com.wenwo.message.dao.IVariableDao;
import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.Variable;
import com.wenwo.message.model.Variable.VariableType;

public class VariableServiceImpl implements IVariableService{
	
	private IVariableDao variableDao;

	public void setVariableDao(IVariableDao variableDao) {
		this.variableDao = variableDao;
	}

	@Override
	public int save(Variable variable) {
		return variableDao.save(variable);
	}

	@Override
	public List<Variable> getVariables(VariableType variableType,MessageProjectType messageProjectType) {
		return variableDao.getVariables(variableType, messageProjectType);
	}

	@Override
	public void delete(String id) {
		variableDao.delete(id);
	}

	@Override
	public Variable getVariableById(String id) {
		return variableDao.getVariableById(id);
	}

	@Override
	public void update(Variable variable) {
		variableDao.update(variable);
		
	}

	@Override
	public List<Variable> getVariableByType(VariableType variableType) {
		return variableDao.getVariableByType(variableType);
	}

	@Override
	public List<Variable> getexpress(String variableName) {
		return variableDao.getexpress(variableName);
	}
	
	@Override
	public Map<VariableType, List<Variable>> groupVariableByType(MessageProjectType projectType) {
		return variableDao.groupVariableByType(projectType);
	}

	@Override
	public List<Variable> getAllVariable() {
		return variableDao.getAllVariable();
	}

}
