package org.hxx.util;

public interface IAuthentication {
	public boolean authenticate(String usercode, String username,
			String password) throws Exception;
}
