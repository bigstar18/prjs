package org.hxx.app.dao;

import java.util.List;

import org.hxx.app.model.AppUser;
import org.hxx.app.model.UserPkPic;

public interface IAppDao {
	// public String getUIDByUserAccount(String userAccount) throws Exception;

	public boolean isLegal(String phone, String signId) throws Exception;

	public String getUIDByUserAccount2(String userAccount) throws Exception;

	public List<AppUser> getPic(String eventId) throws Exception;

	public List<AppUser> getUser(int sexType, String eventId) throws Exception;

	public List<AppUser> search(String nickname, String eventId) throws Exception;

	public UserPkPic getPicByUserId(String uid, String eventId) throws Exception;
}
