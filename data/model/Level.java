package com.wethink.data.model;

import java.util.HashMap;

public class Level {
	private HashMap<String, PrimaryCode> level = new HashMap<String, PrimaryCode>();
	
	public Level(HashMap<String, PrimaryCode> level) {
		this.level = level;	
	}

	public HashMap<String, PrimaryCode> getLevel() {
		return level;
	}

	public void setLevel(HashMap<String, PrimaryCode> level) {
		this.level = level;
	}
	
	public String getFirstLevelPrimaryCode() {
		return this.level.get("Level 1").getName();
	}
	
}
