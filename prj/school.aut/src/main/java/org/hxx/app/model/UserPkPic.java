package org.hxx.app.model;

import java.util.Date;
import java.util.List;

public class UserPkPic {
	private String uid;
	private List<String> picurl;
	private List<Date> picdate;
	private String nick;
	private String headPic;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public List<String> getPicurl() {
		return picurl;
	}
	public void setPicurl(List<String> picurl) {
		this.picurl = picurl;
	}
	public List<Date> getPicdate() {
		return picdate;
	}
	public void setPicdate(List<Date> picdate) {
		this.picdate = picdate;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getHeadPic() {
		return headPic;
	}
	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}
	
}
