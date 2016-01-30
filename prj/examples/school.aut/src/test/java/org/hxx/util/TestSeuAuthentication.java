package org.hxx.util;

import org.junit.Test;

import junit.framework.TestCase;

public class TestSeuAuthentication extends TestCase {
	@Test
	public void testSeuAuth(){
		SeuAuthentication seu = new SeuAuthentication();
		String usercode = "213111282";
		String password = "170712";
		String username = "孙安龙";
		boolean result = seu.authenticate(usercode,username, password);
		if(result) System.out.println("SUCCESS");
		else 
			System.out.println("FAILURE");
	}
}
