package org.hxx.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties jdbcProp;
	public static Properties getProp(){
		try {
			if(jdbcProp==null){
				jdbcProp = new Properties();
				jdbcProp.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jdbcProp;
	}
}
