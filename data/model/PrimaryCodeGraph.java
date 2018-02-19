package com.wethink.data.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

public class PrimaryCodeGraph {
	HashMap<String, PrimaryCode> dependency;
	PrimaryCode currentPrimaryCode;
	LinkedList<Integer> adjacencyMatrix[];
	int size = 0;
	private Level l;
	String primaryCodeString = "ABCDEFGH";
	
	@SuppressWarnings("unchecked")
	public PrimaryCodeGraph(HashMap<String, PrimaryCode> dependency, Level l) {
		this.l	= l;
		this.dependency = dependency;
		this.size = dependency.size();
		System.out.println("dependency size"+this.size);
		this.adjacencyMatrix = new LinkedList[this.size];
		for(int i = 0; i < this.size ; i++){
			this.adjacencyMatrix[i] = new LinkedList<Integer>();
        }
		PrimaryCode p = this.dependency.get("B");
		this.currentPrimaryCode = p;
	}
	
	public HashMap<String, PrimaryCode> getDependency() {
		return this.dependency;
	}

	public void setDependency(HashMap<String, PrimaryCode> dependency) {
		this.dependency = dependency;
	}

	public Level getLevel() {
		return l;
	}

	public void setLevel(Level l) {
		this.l = l;
	}

	public PrimaryCode getCurrentPrimaryCode() {
		return this.currentPrimaryCode;
	}

	public void setCurrentPrimaryCode(PrimaryCode currentPrimaryCode) {
		this.currentPrimaryCode = currentPrimaryCode;
	}

	public String getPrimaryCodeString() {
		return this.primaryCodeString;
	}

	public void setPrimaryCodeString(String primaryCodeString) {
		this.primaryCodeString = primaryCodeString;
	}

	public void updateDependencyGrpah(JSONArray dependentTable) {
		for(int i = 0; i < dependentTable.length(); i++) {
			JSONObject job = dependentTable.getJSONObject(i);
			int parentIndex = job.has("parent") ? this.primaryCodeString.indexOf(job.getString("parent").trim()) : -1;
			int childIndex = job.has("child") ? this.primaryCodeString.indexOf(job.getString("child").trim()) : -1;
			this.adjacencyMatrix[parentIndex].addFirst(childIndex);
		}
	}
	
	public PrimaryCode getPrimaryCode(String p) {
		return this.dependency.get(p);
	}
   
	public PrimaryCode getNextParent() {
		String p = this.currentPrimaryCode.getName();
		int index = this.primaryCodeString.indexOf(p);
		int result[] = new int[this.size];
		int count = 0;
		Arrays.fill(result, -1);
		for(int i = 0; i < this.size; i++) {
			Iterator<Integer> iter = this.adjacencyMatrix[i].iterator();
			while(iter.hasNext()) {
				int childIndex = iter.next();
				if(childIndex == index) {
					result[count] = i;
					count++;
				}	
			}
		}
		boolean parentFound = false;
		for(int i = 0; i < result.length; i++) {
			int pointer = result[i];
			if(pointer != -1) {
//				String p = String.valueOf(this.primaryCodeString.charAt(pointer));
				PrimaryCode pp = this.dependency.get(String.valueOf(this.primaryCodeString.charAt(pointer)));
				if(!pp.isVisited()) {
					parentFound = true;
					this.setCurrentPrimaryCode(pp);
					return pp;
				}
			}
		}
		if(parentFound)
			this.setCurrentPrimaryCode(null);
		return null;
	}
	public PrimaryCode getNextChild() {
		String p = this.currentPrimaryCode.getName();
		int index = this.primaryCodeString.indexOf(p);
		System.out.println("current Index "+ index + " curr P code "+p);
		Iterator<Integer> iter = this.adjacencyMatrix[index].iterator();
		boolean childFound = false;
		while(iter.hasNext()) {
			int childIndex = iter.next();
			System.out.println("child index "+childIndex);
			System.out.println("primarycode index "+this.primaryCodeString.charAt(childIndex));
			System.out.println(this.dependency.containsKey(String.valueOf(this.primaryCodeString.charAt(childIndex))));
			PrimaryCode prim = this.dependency.get(String.valueOf(this.primaryCodeString.charAt(childIndex)));
			if(!prim.isVisited())  {
//				prim.setVisited(true);
//				prim.initCurrentType();
				this.setCurrentPrimaryCode(prim);
				childFound = true;
				return prim;
			}
		}	
		if(childFound)
			this.setCurrentPrimaryCode(null);
		
		return null;
	}
	public JSONObject startTest() {
		JSONObject job = this.currentPrimaryCode.startPrimaryCode();
		return job;
	}
}
