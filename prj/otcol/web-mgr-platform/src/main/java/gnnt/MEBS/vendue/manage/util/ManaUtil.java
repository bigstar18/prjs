package gnnt.MEBS.vendue.manage.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class ManaUtil {
	public static String[] tokenString(String paramString1, String paramString2) {
		StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
		int i = localStringTokenizer.countTokens();
		String[] arrayOfString = new String[i];
		for (int j = 0; localStringTokenizer.hasMoreTokens(); j++) {
			arrayOfString[j] = localStringTokenizer.nextToken();
		}
		return arrayOfString;
	}

	public static String produceCode(int paramInt, String paramString1, String paramString2) {
		String str = "";
		for (int i = 0; i < paramInt - paramString1.length(); i++) {
			str = str + "0";
		}
		str = paramString2 + str + paramString1;
		return str;
	}

	public static String produceOutRecord(int paramInt, String paramString1, String paramString2) {
		String str = "";
		for (int i = 0; i < paramInt - paramString1.length(); i++) {
			str = str + "0";
		}
		str = paramString2 + str + paramString1;
		return str;
	}

	public static String randomString(int paramInt) {
		char[] arrayOfChar = new char[paramInt];
		for (int i = 0; i < paramInt; i++) {
			int j = (int) (48.0D + 75.0D * Math.random());
			if (((j > 57 ? 1 : 0) & (j < 97 ? 1 : 0)) != 0) {
				i--;
			} else {
				arrayOfChar[i] = ((char) j);
			}
		}
		String str = String.valueOf(arrayOfChar);
		return str;
	}

	public static String randomNum(int paramInt) {
		String str = "";
		int i = 0;
		for (int j = 0; j < paramInt; j++) {
			i = (int) (Math.random() * 10.0D);
			str = str + String.valueOf(i);
		}
		return str;
	}

	public static String retConStatus(int paramInt) {
		if (paramInt == 0) {
			return "付款提货中";
		}
		if (paramInt == 1) {
			return "提货完毕";
		}
		if (paramInt == 2) {
			return "归档";
		}
		return null;
	}

	public static boolean checkStr(String paramString) {
		return (paramString != null) && (!"".equals(paramString)) && (!"null".equals(paramString));
	}

	public static BigDecimal accuracyNum(BigDecimal paramBigDecimal, String paramString) {
		try {
			DecimalFormat localDecimalFormat = new DecimalFormat(paramString);
			return new BigDecimal(localDecimalFormat.format(paramBigDecimal));
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return new BigDecimal(0);
	}

	public static String convertDateFormat(String paramString1, String paramString2) {
		try {
			String str = "";
			if (paramString1 == null) {
				return "";
			}
			String[] arrayOfString = paramString1.split(paramString2);
			str = str + String.valueOf(Integer.parseInt(arrayOfString[1])) + "月";
			str = str + String.valueOf(Integer.parseInt(arrayOfString[2])) + "日";
			return str;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return "";
	}

	public static String convertDateFormatExt(String paramString1, String paramString2) {
		try {
			String str = "";
			if (paramString1 == null) {
				return "";
			}
			String[] arrayOfString = paramString1.split(paramString2);
			str = str + arrayOfString[0] + "年";
			str = str + String.valueOf(Integer.parseInt(arrayOfString[1])) + "月";
			str = str + String.valueOf(Integer.parseInt(arrayOfString[2])) + "日";
			return str;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return "";
	}

	public static BigDecimal disBD(BigDecimal paramBigDecimal) {
		try {
			if (paramBigDecimal == null) {
				return new BigDecimal(0);
			}
			return paramBigDecimal;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return new BigDecimal(0);
	}

	public static HashMap addPlaceAmount(String paramString) {
		try {
			String[] arrayOfString1 = paramString.split(";");
			HashMap localHashMap = new HashMap();
			for (int i = 0; i < arrayOfString1.length; i++) {
				String[] arrayOfString2 = arrayOfString1[i].split(",");
				if ((arrayOfString2 != null) && (arrayOfString2[0] != null) && (!"".equals(arrayOfString2[0]))) {
					BigDecimal localBigDecimal = null;
					Object localObject = null;
					if (localHashMap.containsKey(arrayOfString2[0])) {
						localBigDecimal = (BigDecimal) localHashMap.get(arrayOfString2[0]);
						localHashMap.put(arrayOfString2[0], localBigDecimal.add(new BigDecimal(arrayOfString2[(arrayOfString2.length - 1)])));
					} else {
						localHashMap.put(arrayOfString2[0], new BigDecimal(arrayOfString2[(arrayOfString2.length - 1)]));
					}
				}
			}
			return localHashMap;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return null;
	}

	public static HashMap addPlaceAmount(String paramString, BigDecimal paramBigDecimal) {
		try {
			String[] arrayOfString1 = paramString.split(";");
			HashMap localHashMap = new HashMap();
			for (int i = 0; i < arrayOfString1.length; i++) {
				String[] arrayOfString2 = arrayOfString1[i].split(",");
				if ((arrayOfString2 != null) && (arrayOfString2[0] != null) && (!"".equals(arrayOfString2[0]))) {
					BigDecimal localBigDecimal = null;
					Object localObject = null;
					if (localHashMap.containsKey(arrayOfString2[0])) {
						localBigDecimal = (BigDecimal) localHashMap.get(arrayOfString2[0]);
						localHashMap.put(arrayOfString2[0],
								localBigDecimal.add(new BigDecimal(arrayOfString2[(arrayOfString2.length - 1)]).multiply(paramBigDecimal)));
					} else {
						localHashMap.put(arrayOfString2[0], new BigDecimal(arrayOfString2[(arrayOfString2.length - 1)]).multiply(paramBigDecimal));
					}
				}
			}
			return localHashMap;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return null;
	}

	public static HashMap addPlaceGrade(String paramString) {
		try {
			String[] arrayOfString1 = paramString.split(";");
			HashMap localHashMap = new HashMap();
			for (int i = 0; i < arrayOfString1.length; i++) {
				String[] arrayOfString2 = arrayOfString1[i].split(",");
				if ((arrayOfString2 != null) && (arrayOfString2[0] != null) && (!"".equals(arrayOfString2[0]))) {
					String str = null;
					if (localHashMap.containsKey(arrayOfString2[0])) {
						str = (String) localHashMap.get(arrayOfString2[0]);
						localHashMap.put(arrayOfString2[0], str + "/" + arrayOfString2[2]);
					} else {
						localHashMap.put(arrayOfString2[0], arrayOfString2[2]);
					}
				}
			}
			return localHashMap;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return null;
	}

	public static String getGrade(String paramString) {
		try {
			String[] arrayOfString = paramString.split("/");
			HashMap localHashMap = new HashMap();
			String str = "";
			for (int i = 0; i < arrayOfString.length; i++) {
				localHashMap.put(arrayOfString[i], "");
			}
			Set localSet = localHashMap.entrySet();
			Iterator localIterator = localSet.iterator();
			while (localIterator.hasNext()) {
				Map.Entry localEntry = (Map.Entry) localIterator.next();
				if (!"".equals(str)) {
					str = str + "/" + (String) localEntry.getKey();
				} else {
					str = (String) localEntry.getKey();
				}
			}
			return str;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return null;
	}

	public static KeycodeBean splitStr(String paramString) {
		int i = 0;
		KeycodeBean localKeycodeBean = new KeycodeBean();
		try {
			StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
			if (localStringTokenizer.countTokens() < 5) {
				return null;
			}
			while (localStringTokenizer.hasMoreTokens()) {
				if (i == 3) {
					localKeycodeBean.setName(localStringTokenizer.nextToken());
				} else if (i == 4) {
					localKeycodeBean.setKeycode(localStringTokenizer.nextToken());
				} else {
					localStringTokenizer.nextToken();
				}
				i++;
			}
			return localKeycodeBean;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return null;
	}

	public static double round(double paramDouble, int paramInt) {
		if (paramInt < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble));
		BigDecimal localBigDecimal2 = new BigDecimal("1");
		return localBigDecimal1.divide(localBigDecimal2, paramInt, 4).doubleValue();
	}

	public static String delNull(String paramString) {
		try {
			if (paramString == null) {
				return "";
			}
			return paramString;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return "";
	}

	public static int betweenDays(Date paramDate1, Date paramDate2) {
		try {
			long l1 = paramDate1.getTime();
			long l2 = paramDate2.getTime();
			int i = (int) ((l2 - l1) / 86400000L);
			return i;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return 0;
	}

	public static String disDate(Timestamp paramTimestamp) {
		try {
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
			return localSimpleDateFormat.format(paramTimestamp);
		} catch (Exception localException) {
		}
		return "";
	}

	public static String getRepTimeTitle(String paramString1, String paramString2) {
		String str = "";
		try {
			if ((checkStr(paramString1)) && (checkStr(paramString2))) {
				if (!paramString1.equals(paramString2)) {
					str = str + convertDateFormatExt(paramString1.toString(), "-");
					str = str + "至";
					str = str + convertDateFormatExt(paramString2.toString(), "-");
				} else {
					str = str + convertDateFormatExt(paramString2.toString(), "-");
				}
			} else if ((checkStr(paramString1)) && (!checkStr(paramString2))) {
				str = str + convertDateFormatExt(paramString1.toString(), "-");
				str = str + "至";
			} else if ((!checkStr(paramString1)) && (checkStr(paramString2))) {
				str = "至";
				str = str + convertDateFormatExt(paramString2.toString(), "-");
			}
			return str;
		} catch (Exception localException) {
		}
		return "";
	}

	public static String splitSameGrade(String paramString) {
		try {
			String str = "";
			String[] arrayOfString = paramString.split("/");
			HashMap localHashMap = new HashMap();
			for (int i = 0; i < arrayOfString.length; i++) {
				localHashMap.put(arrayOfString[i], "");
			}
			Set localSet = localHashMap.entrySet();
			Iterator localIterator = localSet.iterator();
			while (localIterator.hasNext()) {
				Map.Entry localEntry = (Map.Entry) localIterator.next();
				if (!"".equals(str)) {
					str = str + "/" + (String) localEntry.getKey();
				} else {
					str = (String) localEntry.getKey();
				}
			}
			return str;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return null;
	}
}
