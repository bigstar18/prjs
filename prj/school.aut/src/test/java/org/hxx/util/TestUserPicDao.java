package org.hxx.util;

import static org.junit.Assert.*;

import org.junit.Test;

import org.hxx.aut.dao.UserPicDao;

public class TestUserPicDao {

	@Test
	public void testCheck() {
		UserPicDao dao = new UserPicDao();
		int result = dao.check("123");
		System.out.println(result);
	}
	@Test
	public void testSetStatus() {
		UserPicDao dao = new UserPicDao();
		dao.setEventStatus("123", "1");
	}
}
