package org.hxx.util;

import java.util.HashMap;
import java.util.Map;

public class OutputGenUtil {
	private static final Map<String, String> msgs = new HashMap<String, String>() {
		{
			put("405", "您提交的数据不完整");
			put("406", "您的验证未通过");
			put("407", "您的验证已通过");
			put("408", "您的数据需要人工验证");
			put("409", "业务繁忙，请重新尝试");
			put("410", "学校验证通道超时，请稍后尝试");
		}
	};

	public static String getOutput(String code) {
		String strOutput = "{\"Code\":\"" + code + "\",\"Msg\":\""
				+ msgs.get(code) + "\","
				+ "\"Total\":0,\"PIndex\":0,\"RecordCount\":0,\"Datalist\":[]}";
		return strOutput;
	}
}
