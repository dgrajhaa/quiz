package com.wethink.data.model;

import org.json.JSONObject;

import com.wethink.data.model.util.Constants.FlowRules;

public class Assesment {
	PrimaryCodeGraph pcg;
	
	public Assesment(PrimaryCodeGraph pcg) {
		this.pcg = pcg;
	}
	
	
	public JSONObject startTest(String levelString) {
		PrimaryCode p = this.pcg.getPrimaryCode(levelString);
		System.out.println(p.getName());
		p.initCurrentType();
		this.pcg.setCurrentPrimaryCode(p);
		return this.pcg.startTest();
	}

	public JSONObject checkAnswer(int assessmentID, String primaryCode, String level, String type, int answer) {
		JSONObject job = new JSONObject();
		PrimaryCode currentPrimaryCode = this.pcg.getCurrentPrimaryCode();
		if(currentPrimaryCode != null)
			System.out.println("Current Primary Code " + currentPrimaryCode.getName() + " input primaryCode "+ primaryCode);
		if(currentPrimaryCode != null && currentPrimaryCode.getName().equals(primaryCode)) {
			FlowRules flow = currentPrimaryCode.checkAnswer(type, answer);
			System.out.println("next flow "+flow.name());
			job = this.getResponse(flow);
		}
		return job;
	}
	
	public JSONObject getResponse(FlowRules flowRules) {
		JSONObject job = new JSONObject();
		
		if(flowRules == FlowRules.CHILD) {
			job = this.getNextChild();
		}
		else if(flowRules == FlowRules.NEXTTYPE){
			job = this.getNextQuestion();
		}
		else if(flowRules == FlowRules.PARENT) {
			job = this.getNextParent();
		}
		return job;
	}
	
	private JSONObject getNextChild() {
		JSONObject job = new JSONObject();
		PrimaryCode child = this.pcg.getNextChild();
		if(child == null) 
			job.put("terminate", "Thanks for participation");
		else {
			child.setVisited(true);
			child.initCurrentType();
			this.pcg.setCurrentPrimaryCode(child);
			return this.getNextQuestion();
		}
		return job;
	}
	
	private JSONObject getNextParent() {
		PrimaryCode parent = this.pcg.getNextParent();
		JSONObject job = new JSONObject();
		if(parent == null) 
			job.put("terminate", "Thanks for participation");
		else {
			parent.setVisited(true);
			parent.initCurrentType();
			this.pcg.setCurrentPrimaryCode(parent);
			return this.getNextQuestion();
		}
		return job;
	}
	
	private JSONObject getNextQuestion() {
		PrimaryCode curr = this.pcg.getCurrentPrimaryCode();
		Type currType = null;
		if(curr != null)
			currType = curr.getCurrentType();
		else
			System.out.println("current PrimeCode null in getNextQuestion");
		Question currQues;
		JSONObject job = new JSONObject();
		if(currType != null) {
			currType.setAsked(true);
			currQues = currType.getQues();
			job.put("question", currQues.getQuestion());
		}
		else
			System.out.println("current type null in getNextQuestion");
		return job;
	}

}
