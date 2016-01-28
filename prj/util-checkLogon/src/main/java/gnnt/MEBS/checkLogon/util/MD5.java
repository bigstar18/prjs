package gnnt.MEBS.checkLogon.util;

import java.security.MessageDigest;

/**
 * MD5加密
 * 
 * @author xuejt
 * 
 */
public class MD5 {
	/**
	 * 根据算法对字符串加密
	 * 
	 * @param 加密的字符串
	 * @param 算法
	 * @return 加密后的字符串
	 */
	private static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}

	/**
	 * 对用户名和密码进行加密；加密规则：将用户名和密码连接起来使用MD5加密
	 * 
	 * @param userId
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return 加密后字符串
	 */
	public final static String getMD5(String userId, String pwd) {
		return encodePassword(userId + pwd, "MD5");
	}
}
