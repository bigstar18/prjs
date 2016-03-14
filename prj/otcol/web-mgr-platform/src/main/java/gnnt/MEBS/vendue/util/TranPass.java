package gnnt.MEBS.vendue.util;

import java.security.MessageDigest;

public class TranPass {
	public static final String MD5(String paramString1, String paramString2) {
		try {
			char[] arrayOfChar1 = toChar(paramString1);
			byte[] arrayOfByte1 = paramString2.getBytes();
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(arrayOfByte1);
			byte[] arrayOfByte2 = localMessageDigest.digest();
			int i = arrayOfByte2.length;
			char[] arrayOfChar2 = new char[i * 2];
			int j = 0;
			for (int k = 0; k < i; k++) {
				int m = arrayOfByte2[k];
				char[] arrayOfChar3 = complement(arrayOfChar1);
				arrayOfChar2[(j++)] = arrayOfChar3[(m >>> 3 & 0xF)];
				arrayOfChar2[(j++)] = arrayOfChar3[(m & 0xF)];
			}
			return new String(arrayOfChar2);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return null;
	}

	public static char[] complement(char[] paramArrayOfChar) throws Exception {
		char[] arrayOfChar = new char[16];
		if (paramArrayOfChar == null) {
			throw new Exception("密码转换时,摘要无效!");
		}
		if (paramArrayOfChar.length < 16) {
			for (int i = 0; i < paramArrayOfChar.length; i++) {
				arrayOfChar[i] = paramArrayOfChar[i];
			}
			for (int i = paramArrayOfChar.length; i < 16; i++) {
				arrayOfChar[i] = 'z';
			}
			return arrayOfChar;
		}
		return paramArrayOfChar;
	}

	public static char[] toChar(String paramString) {
		char[] arrayOfChar = new char[paramString.length()];
		for (int i = 0; i < arrayOfChar.length; i++) {
			arrayOfChar[i] = paramString.charAt(i);
		}
		return arrayOfChar;
	}

	public static String encodeMD5(String paramString) {
		String str1 = "";
		String str2 = "";
		try {
			byte[] arrayOfByte1 = paramString.getBytes();
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(arrayOfByte1);
			byte[] arrayOfByte2 = localMessageDigest.digest();
			for (int i = 0; i < arrayOfByte2.length; i++) {
				str2 = Integer.toHexString(arrayOfByte2[i] & 0xFF);
				if (str2.length() == 1) {
					str1 = str1 + "0" + str2;
				} else {
					str1 = str1 + str2;
				}
			}
			str1 = str1.toUpperCase();
		} catch (Exception localException) {
			System.out.println("ERROR:Wrong when encryption");
			localException.printStackTrace();
		}
		return str1;
	}
}
