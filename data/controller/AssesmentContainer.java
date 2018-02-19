package com.wethink.data.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
//import java.util.logging.Level;

import com.wethink.data.model.Assesment;
import com.wethink.data.model.Level;
import com.wethink.data.model.PrimaryCode;
import com.wethink.data.model.PrimaryCodeGraph;
import com.wethink.data.model.util.AdminUtil;

public class AssesmentContainer {
	
	static AssesmentContainer container = null;
	AdminUtil admin = null;
	
	Map<Integer, Assesment> ht = Collections.synchronizedMap(new HashMap<Integer, Assesment>());

	private AssesmentContainer() {
		
	}
	
	public static AssesmentContainer getInstance() {
		if(container == null) {
			container = new AssesmentContainer();
		}
		return container;
	}
	public void set(int id, Assesment assessment) {
		ht.put(id, assessment);
	}
	
	public Assesment get(int id) {
		if(!this.ht.containsKey(id)) {
			System.out.println("assessment id "+ id);
			this.admin = AdminUtil.getInstance(id); 
			HashMap<String, PrimaryCode> l = this.admin.getLevel();
			HashMap<String, PrimaryCode> pMap = this.admin.getPrimayCodeMap();
			Level level = new Level(l);
			PrimaryCodeGraph pcg = new PrimaryCodeGraph(pMap, level);
			pcg.updateDependencyGrpah(this.admin.getDependentTable());
			Assesment assessment = new Assesment(pcg);
			this.ht.put(id, assessment);
		}
		return this.ht.get(id);
	}
	
}
