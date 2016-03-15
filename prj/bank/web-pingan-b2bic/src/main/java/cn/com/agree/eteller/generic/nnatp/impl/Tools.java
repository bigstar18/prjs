package cn.com.agree.eteller.generic.nnatp.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cn.com.agree.eteller.generic.utils.CommonType;

public class Tools {
	public static final String NOT_SELECT_LABEL = "全部";
	public static final String NOT_SELECT_ID = "";
	public static final String BASEPATH = "config/";
	public static final String POSTFIX = ".properties";

	public static CommonType[] loadConfigFromFile(String filename, int flag) {
		List list = new ArrayList();
		if (flag == 1) {
			list.add(new CommonType("", "全部"));
		}
		FileReader fr = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(Tools.class.getClassLoader().getResourceAsStream("config/" + filename + ".properties")));
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				int e = line.indexOf("=");
				if (e != -1) {
					String id = line.substring(0, e);
					String value = line.substring(e + 1);
					list.add(new CommonType(id, value));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			if (br != null) {
				try {
					br.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			if (br != null) {
				try {
					br.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		CommonType[] ct = (CommonType[]) list.toArray(new CommonType[0]);
		return ct;
	}

	public static String getInit(String filename, String name) {
		String pwd_method = null;
		Properties p = new Properties();
		File f = new File("config/" + filename + ".properties");

		InputStream in = null;
		try {
			in = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			p.load(in);
			pwd_method = p.getProperty(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pwd_method;
	}
}
