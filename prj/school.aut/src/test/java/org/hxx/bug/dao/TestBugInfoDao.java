package org.hxx.bug.dao;

import java.util.List;

import org.junit.Test;

import org.hxx.bug.dao.impl.AppBugDao;
import org.hxx.bug.model.AppBug;
import org.hxx.util.UUIDGenerator;

public class TestBugInfoDao {

	@Test
	public void testBug() throws Exception {
		AppBug bug = new AppBug();
		AppBugDao dao = new AppBugDao();
		String id = UUIDGenerator.getUUID();
		bug.setId(id);
		bug.setUsername("徐安平");
		bug.setPhone("15950495330");
		bug.setBugTitle("注册问题");
		bug.setBugInfo("没法注册");

		dao.add(bug);
	}

	@Test
	public void testLoad() {
		AppBugDao dao = new AppBugDao();
		List<AppBug> bugs = dao.load("123456");
		for (AppBug bug : bugs) {
			System.out.println(bug.getTime());
		}
	}
}
