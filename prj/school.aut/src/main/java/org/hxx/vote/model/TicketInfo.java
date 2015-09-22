package org.hxx.vote.model;

public class TicketInfo {
	private String voterId;
	private String picOwnerId;
	private String voterPhone;
	private String picOwnerPhone;
	private String eventId;
	public String getVoterId() {
		return voterId;
	}
	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}
	public String getPicOwnerId() {
		return picOwnerId;
	}
	public void setPicOwnerId(String picOwnerId) {
		this.picOwnerId = picOwnerId;
	}
	public String getVoterPhone() {
		return voterPhone;
	}
	public void setVoterPhone(String voterPhone) {
		this.voterPhone = voterPhone;
	}
	public String getPicOwnerPhone() {
		return picOwnerPhone;
	}
	public void setPicOwnerPhone(String picOwnerPhone) {
		this.picOwnerPhone = picOwnerPhone;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public TicketInfo(String voterId,  String voterPhone, String picOwnerId,
			String picOwnerPhone, String eventId) {
		super();
		this.voterId = voterId;
		this.picOwnerId = picOwnerId;
		this.voterPhone = voterPhone;
		this.picOwnerPhone = picOwnerPhone;
		this.eventId = eventId;
	}
	
}
