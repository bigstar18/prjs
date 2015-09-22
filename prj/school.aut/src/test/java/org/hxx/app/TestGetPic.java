package org.hxx.app;

import java.util.List;

import org.junit.Test;

import org.hxx.app.dao.impl.AppDao;
import org.hxx.app.model.AppUser;
import org.hxx.app.model.UserPkPic;
import org.hxx.util.JsonUtil;

public class TestGetPic {

	@Test
	public void testGetPic() throws Exception {
		AppDao dao = new AppDao();
		List<AppUser> users = dao.getPic("123");
		for (AppUser user : users) {
			System.out.println(user.getNick());
		}
	}

	@Test
	public void testSearch() throws Exception {
		AppDao dao = new AppDao();
		List<AppUser> users = dao.search("怡", "123");
		for (AppUser user : users) {
			System.out.println(user.getNick());
		}
	}

	@Test
	public void testUserPkPicToJson() throws Exception {
		AppDao dao = new AppDao();
		UserPkPic pk = dao.getPicByUserId("01f1a5b0-bd73-489c-92a8-80454cf69034", "123");
		String result = JsonUtil.UserPkPicToJson(pk);
		System.out.println(result);
	}
}
