package org.hxx.util;

import junit.framework.TestCase;

import org.junit.Test;

public class TestNuistAuthentication extends TestCase {
	@Test
	public void testNuistAuth() throws Exception {
		NuistAuthentication nuist = new NuistAuthentication();
		String usercode = "20141311026";
		String password = "19950417";
		String username = "罗倩";
		boolean result = nuist.authenticate(usercode, username, password);
		if (result)
			System.out.println("SUCCESS");
		else
			System.out.println("FAILURE");
	}
}
