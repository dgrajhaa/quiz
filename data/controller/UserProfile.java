package com.wethink.data.controller;

import java.util.HashMap;

import com.wethink.data.model.ResourceInfo;

// here all the threadlocal variables are stored
public class UserProfile {
	
	private static UserProfile profile = null;
	
	private UserProfile() {
		
	}
	public static UserProfile getInstance() {
		if(profile == null) {
			profile = new UserProfile();
		}
		return profile;
	}
	public static ResourceFetcher rf = new ResourceFetcher();

	public static ResourceInfo getInfo() {
			return rf.get();
	}
	
	public void setInfo(ResourceInfo resInf) {
		rf.set(resInf);
	}
	
	@SuppressWarnings("rawtypes")
	private static class ResourceFetcher extends ThreadLocal {
	private static final String RESOURCE = "RESOURCE_INFO";
	@Override
	public ResourceInfo get() {
		@SuppressWarnings("unchecked")
		HashMap<String, ResourceInfo> map = (HashMap<String, ResourceInfo>)super.get();
		return (ResourceInfo) map.get(RESOURCE);
	}

	@Override
	protected HashMap<String, ResourceInfo> initialValue() {
		return new HashMap<String, ResourceInfo>();
	}

	@Override
	public void remove() {
		 @SuppressWarnings("unchecked")
		HashMap<String, ResourceInfo> map = (HashMap<String, ResourceInfo>)super.get();
		 map.remove(RESOURCE);
	}

	public void set(ResourceInfo value) {
		@SuppressWarnings("unchecked")
		HashMap<String, ResourceInfo> map = (HashMap<String, ResourceInfo>) super.get();
		map.put(ResourceFetcher.RESOURCE, value);
	}
	}
}
