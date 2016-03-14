package gnnt.MEBS.base.query;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import gnnt.MEBS.base.query.exception.BaseException;

public class Utils {
	public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat dfSimple = new SimpleDateFormat("yyyy-MM-dd");

	public static String capitalize(String paramString) {
		if ((paramString == null) || (paramString.length() == 0)) {
			return paramString;
		}
		if (paramString.length() == 1) {
			return paramString.toUpperCase();
		}
		return paramString.toUpperCase().charAt(0) + paramString.substring(1);
	}

	public static String concat(Object[] paramArrayOfObject, String paramString) {
		if (paramArrayOfObject.length == 0) {
			return "";
		}
		if (paramString == null) {
			paramString = "";
		}
		StringBuffer localStringBuffer = new StringBuffer();
		if (paramArrayOfObject[0] != null) {
			localStringBuffer.append((String) paramArrayOfObject[0]);
		}
		for (int i = 1; i < paramArrayOfObject.length; i++) {
			localStringBuffer.append(paramString);
			if (paramArrayOfObject[i] != null) {
				localStringBuffer.append((String) paramArrayOfObject[i]);
			}
		}
		return localStringBuffer.toString();
	}

	public static String quote(String paramString1, String paramString2) {
		return paramString2 + paramString1.replaceAll(paramString2, new StringBuilder().append("\\").append(paramString2).toString()) + paramString2;
	}

	public static String quoteDouble(String paramString1, String paramString2, boolean paramBoolean) {
		if (paramBoolean) {
			return paramString2 + paramString1.replaceAll(paramString2, new StringBuilder().append(paramString2).append(paramString2).toString())
					+ paramString2;
		}
		return paramString1.replaceAll(paramString2, paramString2 + paramString2);
	}

	public static Map stringToMap(String paramString1, String paramString2) {
		return stringToMap(paramString1, paramString2, "=");
	}

	public static Map stringToMap(String paramString1, String paramString2, String paramString3) {
		HashMap localHashMap = new HashMap();
		String[] arrayOfString1 = paramString1.split(paramString2);
		for (int i = 0; i < arrayOfString1.length; i++) {
			String[] arrayOfString2 = arrayOfString1[i].split(paramString3, 2);
			if (arrayOfString2.length == 2) {
				localHashMap.put(arrayOfString2[0], arrayOfString2[1]);
			}
		}
		return localHashMap;
	}

	public static Date parseDate(DateFormat paramDateFormat, String paramString) {
		try {
			return paramDateFormat.parse(paramString);
		} catch (ParseException localParseException) {
		}
		return null;
	}

	public static byte parseByte(String paramString, byte paramByte) {
		try {
			return Byte.parseByte(paramString);
		} catch (NumberFormatException localNumberFormatException) {
		}
		return paramByte;
	}

	public static byte parseByte(String paramString) {
		return parseByte(paramString, (byte) 0);
	}

	public static int parseInt(String paramString, int paramInt) {
		try {
			return Integer.parseInt(paramString);
		} catch (NumberFormatException localNumberFormatException) {
		}
		return paramInt;
	}

	public static int parseInt(String paramString) {
		return parseInt(paramString, 0);
	}

	public static Integer parseInteger(String paramString) {
		try {
			return Integer.valueOf(paramString);
		} catch (NumberFormatException localNumberFormatException) {
		}
		return null;
	}

	public static Long parseLong(String paramString) {
		try {
			return Long.valueOf(paramString);
		} catch (NumberFormatException localNumberFormatException) {
		}
		return null;
	}

	public static float parseFloat(String paramString, float paramFloat) {
		try {
			return Float.parseFloat(paramString);
		} catch (NumberFormatException localNumberFormatException) {
			return paramFloat;
		} catch (NullPointerException localNullPointerException) {
		}
		return paramFloat;
	}

	public static Float parseFloat(String paramString) {
		try {
			return Float.valueOf(paramString);
		} catch (NumberFormatException localNumberFormatException) {
			return null;
		} catch (NullPointerException localNullPointerException) {
		}
		return null;
	}

	public static double parseDouble(String paramString, double paramDouble) {
		try {
			return Double.parseDouble(paramString);
		} catch (NumberFormatException localNumberFormatException) {
			return paramDouble;
		} catch (NullPointerException localNullPointerException) {
		}
		return paramDouble;
	}

	public static Double parseDouble(String paramString) {
		try {
			return Double.valueOf(paramString);
		} catch (NumberFormatException localNumberFormatException) {
			return null;
		} catch (NullPointerException localNullPointerException) {
		}
		return null;
	}

	public static boolean parseBoolean(String paramString) {
		return (paramString != null) && ((paramString.equalsIgnoreCase("true")) || (paramString.equals("1")));
	}

	public static char charAt(String paramString, int paramInt, char paramChar) {
		if ((paramString == null) || (paramString.length() <= paramInt)) {
			return paramChar;
		}
		return paramString.charAt(paramInt);
	}

	public static Object convertSilent(Object paramObject, String paramString) {
		try {
			return convert(paramObject, paramString);
		} catch (BaseException localBaseException) {
		}
		return null;
	}

	public static Object convertSilent(Object paramObject, String paramString1, String paramString2) {
		Object localObject = convertSilent(paramObject, paramString1);
		if (localObject != null) {
			return localObject;
		}
		return convertSilent(paramString2, paramString1);
	}

	public static Object convert(Object paramObject, String paramString) throws BaseException {
		if ("string".equals(paramString)) {
			return paramObject;
		}
		if (paramObject == null) {
			return paramObject;
		}
		if ((paramObject instanceof List)) {
			List localObject = (List) paramObject;
			if (((List) localObject).size() == 0) {
				return null;
			}
			paramObject = ((List) localObject).get(0);
		}
		if ((paramObject == null) || (!(paramObject instanceof String))) {
			return paramObject;
		}
		Object localObject = (String) paramObject;
		if (((String) localObject).length() == 0) {
			return null;
		}
		if (paramString.equals("int")) {
			return parseLong((String) localObject);
		}
		if (paramString.equals("number")) {
			return parseFloat((String) localObject);
		}
		if (paramString.equals("date")) {
			localObject = combineDateTime((String) localObject);
			return parseDate(df, (String) localObject);
		}
		if (paramString.equals("boolean")) {
			return new Boolean(parseBoolean((String) localObject));
		}
		return localObject;
	}

	public static String combineDateTime(String paramString) {
		String str1 = null;
		String str2 = null;
		paramString = paramString.trim();
		if (paramString.indexOf(" ") < 0) {
			if (paramString.indexOf("-") < 0) {
				str1 = "";
				str2 = paramString;
			} else {
				str1 = paramString;
				str2 = "";
			}
		} else {
			String[] arrayOfString1 = paramString.split(" ");
			str1 = arrayOfString1[0];
			str2 = arrayOfString1[1];
		}
		String[] arrayOfString1 = str1.split("-");
		if (arrayOfString1.length == 2) {
			str1 = Calendar.getInstance().get(1) + "-" + str1;
		} else if (arrayOfString1.length < 2) {
			str1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		if (str2 == "") {
			str2 = "00:00:00";
		} else {
			String[] arrayOfString2 = str2.split(":");
			if (arrayOfString2.length == 0) {
				str2 = "00:00:00";
			} else if (arrayOfString2.length == 1) {
				str2 = str2 + ":00:00";
			} else if (arrayOfString2.length == 2) {
				str2 = str2 + ":00";
			}
		}
		return str1 + " " + str2;
	}

	public static String formatDate(String paramString, Date paramDate) {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
		return localSimpleDateFormat.format(paramDate);
	}

	public static String join(Collection paramCollection, String paramString1, String paramString2) {
		if (paramString1 == null) {
			paramString1 = ",";
		}
		StringBuffer localStringBuffer = new StringBuffer();
		Iterator localIterator = paramCollection.iterator();
		while (localIterator.hasNext()) {
			if (localStringBuffer.length() > 0) {
				localStringBuffer.append(paramString1);
			}
			Object localObject = localIterator.next();
			if (localObject != null) {
				if (paramString2 != null) {
					localStringBuffer.append(quoteDouble(localObject.toString(), paramString2, true));
				} else {
					localStringBuffer.append(localObject);
				}
			}
		}
		return localStringBuffer.toString();
	}

	public static String join(Object[] paramArrayOfObject, String paramString1, String paramString2) {
		if (paramString1 == null) {
			paramString1 = ",";
		}
		StringBuffer localStringBuffer = new StringBuffer();
		for (int i = 0; i < paramArrayOfObject.length; i++) {
			if (localStringBuffer.length() > 0) {
				localStringBuffer.append(paramString1);
			}
			if (paramArrayOfObject[i] != null) {
				if (paramString2 != null) {
					localStringBuffer.append(quoteDouble(paramArrayOfObject[i].toString(), paramString2, true));
				} else {
					localStringBuffer.append(paramArrayOfObject[i]);
				}
			}
		}
		return localStringBuffer.toString();
	}

	public static List split(String paramString1, String paramString2) {
		String[] arrayOfString = paramString1.split(paramString2);
		ArrayList localArrayList = new ArrayList();
		for (int i = 0; i < arrayOfString.length; i++) {
			localArrayList.add(arrayOfString[i]);
		}
		return localArrayList;
	}

	public static String convertCurrencyToChinese(Object paramObject) {
		if (paramObject == null) {
			return "";
		}
		BigDecimal localBigDecimal = new BigDecimal(paramObject.toString());
		System.out.println(localBigDecimal);
		String[] arrayOfString = localBigDecimal.toString().split("\\.");
		String str1 = arrayOfString[0];
		String str2 = "";
		int i = str1.length();
		int j = str1.length();
		while (i > 0) {
			if (str1.charAt(j - i) == '-') {
				str2 = str2 + "负";
			} else {
				str2 = str2 + numChar2chinese(str1.charAt(j - i), i);
			}
			i--;
		}
		str2 = str2.replaceAll("零仟零佰零拾零", "零");
		str2 = str2.replaceAll("零仟零佰零拾", "零");
		str2 = str2.replaceAll("零仟零佰", "零");
		str2 = str2.replaceAll("零仟", "零");
		str2 = str2.replaceAll("零佰零拾零", "零");
		str2 = str2.replaceAll("零佰零拾", "零");
		str2 = str2.replaceAll("零佰", "零");
		str2 = str2.replaceAll("零拾零", "零");
		str2 = str2.replaceAll("零拾", "零");
		str2 = str2.replaceAll("零亿", "亿");
		str2 = str2.replaceAll("零万", "万");
		str2 = str2.replaceAll("亿万", "亿零");
		str2 = str2.replaceAll("零零", "零");
		if (str2.endsWith("零")) {
			str2 = str2.substring(0, str2.length() - 1);
		}
		str2 = str2 + "元";
		if ((arrayOfString.length == 2) && (!"00".equals(arrayOfString[1])) && (!"0".equals(arrayOfString[1]))) {
			String str3 = arrayOfString[1];
			for (j = 0; (j < str3.length()) && (j < 2); j++) {
				str2 = str2 + numChar2chinese(str3.charAt(j), 0 - j);
			}
		}
		return str2;
	}

	private static String numChar2chinese(char paramChar, int paramInt) {
		String str1 = "";
		switch (paramChar) {
		case '0':
			str1 = "零";
			break;
		case '1':
			str1 = "壹";
			break;
		case '2':
			str1 = "贰";
			break;
		case '3':
			str1 = "叁";
			break;
		case '4':
			str1 = "肆";
			break;
		case '5':
			str1 = "伍";
			break;
		case '6':
			str1 = "陆";
			break;
		case '7':
			str1 = "柒";
			break;
		case '8':
			str1 = "扒";
			break;
		case '9':
			str1 = "玖";
		}
		String str2 = "";
		switch (paramInt) {
		case -1:
			str2 = "分";
			break;
		case 0:
			str2 = "角";
			break;
		case 1:
			break;
		case 2:
			str2 = "拾";
			break;
		case 3:
			str2 = "佰";
			break;
		case 4:
			str2 = "仟";
			break;
		case 5:
			str2 = "万";
			break;
		case 6:
			str2 = "拾";
			break;
		case 7:
			str2 = "佰";
			break;
		case 8:
			str2 = "仟";
			break;
		case 9:
			str2 = "亿";
			break;
		case 10:
			str2 = "拾";
			break;
		case 11:
			str2 = "佰";
			break;
		case 12:
			str2 = "仟";
		}
		return str1 + str2;
	}

	public static void main(String[] paramArrayOfString) {
		System.out.print(parseDouble("1451.47776").floatValue());
	}
}
