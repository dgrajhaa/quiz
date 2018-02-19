package com.wethink.servlet.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.wethink.data.controller.AssesmentContainer;
import com.wethink.data.controller.UserProfile;
import com.wethink.data.model.Assesment;
import com.wethink.data.model.ResourceInfo;
import com.wethink.data.model.util.ActionConstant;

public class URLParser {
	
	public void parse(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
//		String message = "Hello World";
//        request.setAttribute("message", message); // This will be available as ${message}
		String url = request.getParameter("actionURL");
        int actionURL = (url != null) ? Integer.parseInt(url) : -1;

        if(actionURL == -1) 
        	request.getRequestDispatcher("/login.jsp").forward(request, response);
        else {
        	if(isPermittedAction(actionURL)) {
        		ResourceInfo rInfo = UserProfile.getInfo();
        		AssesmentContainer container = AssesmentContainer.getInstance();
        		Assesment assess = container.get(rInfo.getAssesmentID());
        		JSONObject reqObj = this.httpReqToJSON(request);
        		JSONObject resp = this.processRequest(assess, reqObj);
        		request.setAttribute("response", resp);
        	}
        	request.getRequestDispatcher("/test.jsp").forward(request, response);
        }
	}
	
	private JSONObject httpReqToJSON(HttpServletRequest request) {
		JSONObject job = new JSONObject();
		String url = request.getParameter("actionURL");
        int actionConstant = (url != null) ? Integer.parseInt(url) : -1;
        String primaryCode = request.getParameter("primaryCode");
        String level = request.getParameter("level");
        String type = request.getParameter("type");
        String answer = request.getParameter("answer");
        if(actionConstant != -1)
		job.put("action", actionConstant);
        if(primaryCode != null)
        	job.put("primaryCode", primaryCode.trim());
        if(level != null)
        	job.put("level", level.trim());
        if(type != null)
        	job.put("type", type.trim());
        if(answer != null)
        	job.put("answer", answer.trim());
		
		return job;
	}
	
	public boolean isPermittedAction(int actionConstant) {
		return true;
	}
	
	public JSONObject processRequest(Assesment assessment, JSONObject request) {
		int assessmentID = 1000;
		int actionConstant = request.getInt("action");
		String primaryCode = request.has("primaryCode") ? request.getString("primaryCode") : null;
        String level = request.has("level") ? request.getString("level") : null;
        String type = request.has("type") ? request.getString("type") : null;
        int answer = request.has("answer") ? request.getInt("answer") : -1;
        JSONObject result = null;
        
		if(actionConstant == ActionConstant.ANSWER) {
			
			result = assessment.checkAnswer(assessmentID, primaryCode, level, type, answer);
		}
		else if(actionConstant == ActionConstant.LEVELS) {
			result = assessment.startTest("B");
		}
		return result;
	}

}
