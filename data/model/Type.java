package com.wethink.data.model;

import com.wethink.data.model.util.Constants.TYPERESULT;

public class Type {
	String name;
	Question ques;
	boolean isAsked;
	
	public Type(String name, Question ques) {
		this.name = name;
		this.ques = ques;
	}
	
	public Question getQues() {
		return ques;
	}
	
	public void setQues(Question ques) {
		this.ques = ques;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isAsked() {
		return isAsked;
	}
	
	public void setAsked(boolean isAsked) {
		this.isAsked = isAsked;
	}
	
	public void updateAnswer(String name, int answer) {
		if(this.getName().equals(name) && this.isAsked && this.ques.getResult() == TYPERESULT.NOTANSWERED) {
			if(this.ques.getAnswer() == answer) 
				this.ques.setResult(TYPERESULT.RIGHT);
			else 
				this.ques.setResult(TYPERESULT.WRONG);
		}
	}
}
