package org.hxx.util;

import junit.framework.TestCase;

import org.junit.Test;

public class TestNjnuAuthentication extends TestCase {
	@Test
	public void testNjnuAuth() throws Exception {
		NjnuAuthentication njnu = new NjnuAuthentication();
		String usercode = "02130123";
		String password = "196712";
		String username = "钱嘉晖";
		boolean result = njnu.authenticate(usercode, username, password);
		if (result)
			System.out.println("SUCCESS");
		else
			System.out.println("FAILURE");
	}
}
