package org.hxx.aut.dao;

import org.hxx.aut.model.UserPic;

public interface IUserPicDao {
	void add(UserPic userPic);
	int check(String phoneNum);
	void setEventStatus(String phoneNum,String eventId);
}
