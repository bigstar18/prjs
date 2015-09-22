package org.hxx.util;

import junit.framework.TestCase;

import org.junit.Test;

public class TestNjuptAuthentication extends TestCase {
	@Test
	public void testNjuptAuth() throws Exception {
		NjuptAuthentication njupt = new NjuptAuthentication();
		String usercode = "B12050301";
		String password = "wangsiyi.nanyou";
		String username = "王思懿";
		boolean result = njupt.authenticate(usercode, username, password);
		if (result)
			System.out.println("SUCCESS");
		else
			System.out.println("FAILURE");
	}
}
