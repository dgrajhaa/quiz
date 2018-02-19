package com.wethink.data.model;

public class ResourceInfo {
	public int assesmentID;
	public int userID;
	public int permissionType;
	public ResourceInfo(int assesmentID, int userID, int permissionType) {
		this.assesmentID = assesmentID;
		this.userID = userID;
		this.permissionType = permissionType;
	}
	public int getAssesmentID() {
		return assesmentID;
	}
	public void setAssesmentID(int assesmentID) {
		this.assesmentID = assesmentID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getPermissionType() {
		return permissionType;
	}
	public void setPermissionType(int permissionType) {
		this.permissionType = permissionType;
	}
	
}