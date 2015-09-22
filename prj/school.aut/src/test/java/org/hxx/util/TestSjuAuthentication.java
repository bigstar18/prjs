package org.hxx.util;

import org.junit.Test;

import junit.framework.TestCase;

public class TestSjuAuthentication extends TestCase {
	@Test
	public void testSjuAuth(){
		SjuAuthentication sju = new SjuAuthentication();
		String usercode = "12013023016";
		String password = "houge1995";
		String username = "侯超";
		boolean result = sju.authenticate(usercode,username, password);
		if(result) System.out.println("SUCCESS");
		else 
			System.out.println("FAILURE");
	}
}
