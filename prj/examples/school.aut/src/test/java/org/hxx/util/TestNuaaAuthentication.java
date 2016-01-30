package org.hxx.util;

import junit.framework.TestCase;

import org.junit.Test;

public class TestNuaaAuthentication extends TestCase {
	@Test
	public void testNuaaAuth() throws Exception {
		NuaaAuthentication nuaa = new NuaaAuthentication();
		String usercode = "091210213";
		String password = "St130139";
		String username = "金丹磊";
		boolean result = nuaa.authenticate(usercode, username, password);
		if (result)
			System.out.println("SUCCESS");
		else
			System.out.println("FAILURE");
	}
}
