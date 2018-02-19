package com.wethink.data.model;

import com.wethink.data.model.util.Constants.TYPERESULT;

public class Question {
	String question;
	int answer;
	TYPERESULT result;
	
	public Question(String question, int answer) {
		this.question = question;
		this.answer = answer;
		this.result = TYPERESULT.NOTANSWERED;
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	public TYPERESULT getResult() {
		return result;
	}
	public void setResult(TYPERESULT result) {
		this.result = result;
	}
	

}
