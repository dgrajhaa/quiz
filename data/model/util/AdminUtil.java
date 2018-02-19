package com.wethink.data.model.util;

import java.io.File;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wethink.data.model.PrimaryCode;
import com.wethink.servlet.util.FileReadHelper;

public class AdminUtil {
	
	static HashMap<Integer, AdminUtil> adminUtil = new HashMap<Integer, AdminUtil>();
	
	HashMap<String, PrimaryCode> primayCodeMap = new HashMap<String, PrimaryCode>();
	JSONArray dependentTable;
	HashMap<String, PrimaryCode> level = new HashMap<String, PrimaryCode>();
	
	private AdminUtil(String folderPath) {
		generateLevel(folderPath);
		generatePrimaryCodes(folderPath);
		generateRelation(folderPath);
	}
	
	public static AdminUtil getInstance(int id) {
//		if(!adminUtil.containsKey(id)) {
			// get the input folder path dynamically from Assessment Table.
			AdminUtil util = new AdminUtil("E:\\work\\workplace\\WeThinkEdu\\input");
			adminUtil.put(id, util);
//		}
		return adminUtil.get(id);
	}
	
	public void generatePrimaryCodes(String folderPath) {
		File file = FileReadHelper.getFile(folderPath +"\\questionbank");
		try {
			JSONArray jsonArr = FileReadHelper.getJSONArray(file);
			for(int i = 0; i < jsonArr.length(); i++) {
				JSONObject job = jsonArr.getJSONObject(i);
				String pCode = job.getString("primaryCode").trim();
//				System.out.println("pcode "+pCode);
				String type = job.getString("questionType").trim();
//				System.out.println("type "+type);
				String question = job.getString("question").trim();
//				System.out.println("question "+question);
				int answer =  job.getInt("answer");
//				System.out.println("answer "+answer);
				PrimaryCode p = this.primayCodeMap.get(pCode);
				
				if(p == null) {
					p = new PrimaryCode(pCode);
				}
				p.addType(type, question, answer);
				this.primayCodeMap.put(pCode, p);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void generateRelation(String folderPath) {
		File file = FileReadHelper.getFile(folderPath +"\\relationtable");
		try {
			this.dependentTable = FileReadHelper.getJSONArray(file);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void generateLevel(String folderPath) {
		File file = FileReadHelper.getFile(folderPath +"\\level");
		try {
			JSONArray jsonArr = FileReadHelper.getJSONArray(file);
			for(int i = 0; i < jsonArr.length(); i++) {
				JSONObject job = jsonArr.getJSONObject(i);
				String test = job.getString("test").trim();
				String start = job.getString("start").trim();
				this.level.put(test, this.primayCodeMap.get(start));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, PrimaryCode> getPrimayCodeMap() {
		return this.primayCodeMap;
	}

	public JSONArray getDependentTable() {
		return this.dependentTable;
	}

	public HashMap<String, PrimaryCode> getLevel() {
		return this.level;
	}
	

}
