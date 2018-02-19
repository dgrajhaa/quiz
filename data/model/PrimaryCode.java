package com.wethink.data.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import com.wethink.data.model.util.Constants.FlowRules;
import com.wethink.data.model.util.Constants.TESTRESULT;
import com.wethink.data.model.util.Constants.TYPERESULT;

public class PrimaryCode {
	String name;
	List<Type> typeList = new ArrayList<Type>();
	boolean isVisited;
	Type prevType, currentType;
	TESTRESULT status;
	int state = 0;
	
	public PrimaryCode(String name) {
		this.name = name;
		this.state = 0;
	}
	
	public PrimaryCode(String name, List<Type> typeList) {
		this.name = name;
		this.typeList = typeList;
	}
	
	public void addType(String type, String question, int answer) {
		Question ques = new Question(question, answer);
		Type t = new Type(type, ques);
		this.typeList.add(t);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Type> getTypeList() {
		return typeList;
	}
	
	public Type getType(String name) {
		Iterator<Type> iter = this.typeList.iterator();
		while(iter.hasNext()) {
			Type t = iter.next();
			if(t.getName().equals(name))
				return t;
		}
		return null;
	}

	public void setTypeList(List<Type> l) {
		this.typeList = l;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	public TESTRESULT getStatus() {
		return status;
	}

	public void setStatus(TESTRESULT status) {
		this.status = status;
	}
	public void setPrevType(Type t) { 
		this.prevType = t;
	}
	
	public Type getPrevType() {
		return this.prevType;
	}
	public void setCurrentType(Type t) { 
		this.currentType = t;
	}
	
	public Type getCurrentType() {
		return this.currentType;
	}
	public void initCurrentType() {
		this.currentType = this.typeList.get(0);
	}
	public FlowRules checkAnswer(String type, int answer) {
		Type t = this.getCurrentType();
		t.updateAnswer(type, answer);
		this.setPrevType(t);
		this.state++;
		if(this.state < this.typeList.size()) {
			this.setCurrentType(this.typeList.get(this.state));
		}
		else {
			this.setCurrentType(null);
		}
		return checkPrimaryCode();
	}

	public FlowRules checkPrimaryCode() {
			Type previousType = this.getPrevType();
			Question prevQues = null;			
			if(previousType != null) 
				prevQues = previousType.getQues();
			
			if(previousType != null && previousType.getName().equals("one")) {
				if(prevQues.getResult() == TYPERESULT.RIGHT) 
					return FlowRules.NEXTTYPE;
				else 
					return FlowRules.PARENT;
			}
			else if(previousType != null && previousType.getName().equals("two")) {
					return FlowRules.NEXTTYPE;
			}
			else {
				if(prevQues != null && prevQues.getResult() == TYPERESULT.RIGHT) {
					return FlowRules.CHILD;
				}
				else {
					if(this.getType("two").getQues().getResult() == TYPERESULT.RIGHT)
						return FlowRules.CHILD;
					else
						return FlowRules.PARENT;
				}
			}
	}
	
	public JSONObject startPrimaryCode() {
		if(!this.isVisited()){
			this.isVisited = true;
//			this.currentType = this.typeList.get(0);
			Type curr = this.getCurrentType();
			curr.setAsked(true);
			Question ques = curr.getQues();
			JSONObject job = new JSONObject();
			job.put("question", ques.getQuestion());
			return job;
		}
		else
			return null;
	}
}
