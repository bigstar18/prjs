package org.hxx.util;

import static org.junit.Assert.*;

import org.junit.Test;

import org.hxx.aut.dao.SqlServerSchoolDao;
import org.hxx.aut.model.SchoolHandler;

public class TestDbUtil {

	@Test
	public void testDB() {
		SqlServerSchoolDao sd = new SqlServerSchoolDao();
		SchoolHandler sh = sd.load("10293");
		System.out.println(sh.getHandler());
	}

}
