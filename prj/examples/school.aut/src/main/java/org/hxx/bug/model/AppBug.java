package org.hxx.bug.model;

import java.util.Date;


public class AppBug {
	private String id;
	private String username;
	private String phone;
	private String bugTitle;
	private String bugInfo;
	private Date time;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBugInfo() {
		return bugInfo;
	}
	public void setBugInfo(String bugInfo) {
		this.bugInfo = bugInfo;
	}
	public String getBugTitle() {
		return bugTitle;
	}
	public void setBugTitle(String bugTitle) {
		this.bugTitle = bugTitle;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
}
