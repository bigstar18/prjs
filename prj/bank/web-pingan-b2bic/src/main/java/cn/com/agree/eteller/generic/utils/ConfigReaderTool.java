package cn.com.agree.eteller.generic.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.struts2.ServletActionContext;

public class ConfigReaderTool {
	public static final String NOT_SELECT_LABEL = "全部";
	public static final String NOT_SELECT_ID = "";
	public static final String BASEPATH = ServletActionContext.getServletContext().getRealPath("/") + "WEB-INF/config/";
	public static final String POSTFIX = ".properties";
	public static final String POSTFIX2 = ".txt";
	public static Map map = new HashMap();

	public static CommonType[] loadConfigFromFile(String selecttype, String filetype, String filename, String preName, String key) {
		List list = new ArrayList();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(new File(BASEPATH + filename + filetype));
			br = new BufferedReader(fr);
			if ((selecttype != null) && (selecttype.equals("1"))) {
				CommonType ct = new CommonType("", "全部");
				list.add(ct);
			}
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();

				int e = line.indexOf("=");
				if (e != -1) {
					String title = line.substring(0, line.indexOf("="));
					if (title.indexOf(".") != -1) {
						String preTitle = title.substring(0, title.indexOf("."));
						String id = title.substring(title.indexOf(".") + 1);
						if (preTitle.equals(preName)) {
							if ((key != null) && (!key.equals(""))) {
								if (key.equals(id)) {
									String value = line.substring(e + 1);
									list.add(new CommonType(id, value));
									break;
								}
							} else {
								String value = line.substring(e + 1);
								list.add(new CommonType(id, value));
							}
						}
					}
				}
			}
		} catch (Exception e) {
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

	public static List loadConfigFromFile(String filetype, String filename, String preName, String key) {
		List list = new ArrayList();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(new File(BASEPATH + filename + filetype));
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();

				int e = line.indexOf("=");
				if (e != -1) {
					String title = line.substring(0, line.indexOf("="));
					if (title.indexOf(".") != -1) {
						String preTitle = title.substring(0, title.indexOf("."));
						String id = title.substring(title.indexOf(".") + 1);
						if (preTitle.equals(preName)) {
							if ((key != null) && (!key.equals(""))) {
								if (key.equals(id)) {
									String value = line.substring(e + 1);
									list.add(new CommonType(id, value));
									break;
								}
							} else {
								String value = line.substring(e + 1);
								list.add(new CommonType(id, value));
							}
						}
					}
				}
			}
		} catch (Exception e) {
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
		return list;
	}

	public static String loadConfigByKey(String fileName, String key) {
		String result = null;
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(new File(BASEPATH + fileName + ".properties")));
			result = prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	public static String loadConfigByValue(String fileName, String filetype, String value, int l_r, String sp, int index, String retFlag) {
		String result = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(new File(BASEPATH + fileName + filetype));
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if ((line.indexOf("#") != 0) && (!line.equals(""))) {
					System.out.println(line);
					String value_b = "";
					try {
						String value_a = line.split("=")[l_r];
						if (sp.equals(".")) {
							if (index > 0) {
								value_b = value_a.substring(value_a.indexOf(".") + 1);
							} else {
								value_b = value_a.substring(0, value_a.indexOf("."));
							}
						} else {
							value_b = value_a.split(sp)[index];
						}
					} catch (RuntimeException e) {
						System.out.println("continue");
						continue;
					}
					if (value_b.trim().equals(value)) {
						String str1;
						if (retFlag.equals("")) {
							str1 = line;
							return str1;
						}
						if (retFlag.equals("0")) {
							str1 = line.split("=")[0];
							return str1;
						}
						if (retFlag.equals("1")) {
							str1 = line.split("=")[1];
							return str1;
						}
					}
				}
			}
		} catch (Exception e) {
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
		}

		finally {
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
		return result;
	}

	public static List loadAllConfigByValue(String fileName, String filetype, String value, int l_r, String sp, int index, String retFlag) {
		FileReader fr = null;
		BufferedReader br = null;
		List ret = new ArrayList();
		try {
			fr = new FileReader(new File(BASEPATH + fileName + filetype));
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if ((line.indexOf("#") != 0) && (!line.equals(""))) {
					System.out.println(line);
					String value_b = "";
					try {
						String value_a = line.split("=")[l_r];
						if (sp.equals(".")) {
							if (index > 0) {
								value_b = value_a.substring(value_a.indexOf(".") + 1);
							} else {
								value_b = value_a.substring(0, value_a.indexOf("."));
							}
						} else {
							value_b = value_a.split(sp)[index];
						}
					} catch (RuntimeException e) {
						System.out.println("continue");
						continue;
					}
					if (value_b.trim().equals(value)) {
						if (retFlag.equals("")) {
							ret.add(line);
						} else if (retFlag.equals("0")) {
							ret.add(line.split("=")[0]);
						} else if (retFlag.equals("1")) {
							ret.add(line.split("=")[1]);
						}
					}
				}
			}
			return ret;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		return ret;
	}

	public static String getSingleConfigMsg(String codeType, String key) {
		if (map.get(key) == null) {
			CommonType[] cttemp = loadConfigFromFile("0", ".txt", "gljyConf", codeType, key);
			map.put(key, cttemp[0].getValue());
			System.out.println("从来没读取过时，读取，并保存好[" + key + ":" + cttemp[0].getValue() + "]");
		}
		String value = (String) map.get(key);
		if (map.get("nowCount") == null) {
			map.put("nowCount", "0");
			System.out.println("读取次数初始化");
			CommonType[] cttemp = loadConfigFromFile("0", ".txt", "gljyConf", "loginInit", "cache");
			map.put("cache", cttemp[0].getValue());
			System.out.println("缓存标识控制[" + cttemp[0].getValue() + "]");
			cttemp = loadConfigFromFile("0", ".txt", "gljyConf", "loginInit", "maxReadCount");
			map.put("maxReadCount", cttemp[0].getValue());
			System.out.println("最大读取次数[" + cttemp[0].getValue() + "]");
		} else {
			long nowCount = Long.parseLong((String) map.get("nowCount"));
			nowCount += 1L;
			map.put("nowCount", String.valueOf(nowCount));
			System.out.println("当前读取数[" + String.valueOf(nowCount) + "]");
			if (Long.parseLong((String) map.get("nowCount")) > Long.parseLong((String) map.get("maxReadCount"))) {
				System.out.println("超过一定读数次数后，响应最新配置文件的变更,减少对性能的影响。");
				map.clear();
			}
		}
		System.out.println("返回值信息[" + key + ":" + value + "]");
		return value;
	}

	public static String getSingleConfigMsg_Online(String codeType, String key) {
		CommonType[] cttemp = loadConfigFromFile("0", ".txt", "gljyConf", codeType, key);
		return cttemp[0].getValue();
	}

	public static void main(String[] args) {
		String ret = loadConfigByValue("gatewayConf", ".txt", "A", 0, ".", 1, "1");
		System.out.println(ret);
	}
}
