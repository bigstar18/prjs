package gnnt.MEBS.common.front.statictools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.proxy.HibernateProxy;

import gnnt.MEBS.common.front.model.StandardModel;

public class Tools {
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

	public static List<Field> getFieldList(Object paramObject, Class<?> paramClass) {
		ArrayList localArrayList = new ArrayList();
		Class localClass = paramObject.getClass();
		do {
			Field[] arrayOfField = localClass.getDeclaredFields();
			for (int i = 0; i < arrayOfField.length; i++) {
				localArrayList.add(arrayOfField[i]);
			}
			if (paramClass == localClass) {
				break;
			}
			localClass = localClass.getSuperclass();
		} while ((localClass != Object.class) && (localClass != paramClass));
		return localArrayList;
	}

	public static void CopyValue(Object paramObject1, Object paramObject2, Class<?> paramClass) {
		if (paramObject1 == null) {
			throw new IllegalArgumentException("源对象为空无法拷贝！");
		}
		if (paramObject2 == null) {
			throw new IllegalArgumentException("目标对象为空无法拷贝！");
		}
		if ((paramObject2 instanceof HibernateProxy)) {
			HibernateProxy localObject1 = (HibernateProxy) paramObject2;
			paramObject2 = ((HibernateProxy) localObject1).getHibernateLazyInitializer().getImplementation();
		}
		Object localObject1 = getFieldList(paramObject1, paramClass);
		List localList = getFieldList(paramObject2, paramClass);
		Iterator localIterator1 = ((List) localObject1).iterator();
		while (localIterator1.hasNext()) {
			Field localField1 = (Field) localIterator1.next();
			Iterator localIterator2 = localList.iterator();
			while (localIterator2.hasNext()) {
				Field localField2 = (Field) localIterator2.next();
				if ((localField1.getName().equals(localField2.getName())) && (localField1.getType().equals(localField2.getType()))
						&& (!Modifier.isFinal(localField2.getModifiers()))) {
					if (!localField1.isAccessible()) {
						localField1.setAccessible(true);
					}
					if (!localField2.isAccessible()) {
						localField2.setAccessible(true);
					}
					try {
						Object localObject2 = localField1.get(paramObject1);
						if ((localObject2 instanceof StandardModel)) {
							CombinationValue(localObject2, localField2.get(paramObject2));
						} else {
							localField2.set(paramObject2, localObject2);
						}
						localField2.set(paramObject2, localObject2);
					} catch (IllegalArgumentException localIllegalArgumentException) {
						localIllegalArgumentException.printStackTrace();
					} catch (IllegalAccessException localIllegalAccessException) {
						localIllegalAccessException.printStackTrace();
					}
				}
			}
		}
	}

	public static void CopyValue(Object paramObject1, Object paramObject2) {
		CopyValue(paramObject1, paramObject2, paramObject1 == null ? null : paramObject1.getClass());
	}

	public static void CombinationValue(Object paramObject1, Object paramObject2, Class<?> paramClass) {
		if (paramObject1 == null) {
			throw new IllegalArgumentException("源对象为空无法拷贝！");
		}
		if (paramObject2 == null) {
			throw new IllegalArgumentException("目标对象为空无法拷贝！");
		}
		if ((paramObject2 instanceof HibernateProxy)) {
			HibernateProxy localObject1 = (HibernateProxy) paramObject2;
			paramObject2 = ((HibernateProxy) localObject1).getHibernateLazyInitializer().getImplementation();
		}
		Object localObject1 = getFieldList(paramObject1, paramClass);
		List localList = getFieldList(paramObject2, paramClass);
		Iterator localIterator1 = ((List) localObject1).iterator();
		while (localIterator1.hasNext()) {
			Field localField1 = (Field) localIterator1.next();
			Iterator localIterator2 = localList.iterator();
			while (localIterator2.hasNext()) {
				Field localField2 = (Field) localIterator2.next();
				if ((localField1.getName().equals(localField2.getName())) && (localField1.getType().equals(localField2.getType()))
						&& (!Modifier.isFinal(localField2.getModifiers()))) {
					if (!localField1.isAccessible()) {
						localField1.setAccessible(true);
					}
					if (!localField2.isAccessible()) {
						localField2.setAccessible(true);
					}
					try {
						Object localObject2 = localField1.get(paramObject1);
						if (localObject2 == null) {
							break;
						}
						if ((localObject2 instanceof StandardModel)) {
							CombinationValue(localObject2, localField2.get(paramObject2));
						} else {
							localField2.set(paramObject2, localObject2);
						}
					} catch (IllegalArgumentException localIllegalArgumentException) {
						localIllegalArgumentException.printStackTrace();
					} catch (IllegalAccessException localIllegalAccessException) {
						localIllegalAccessException.printStackTrace();
					}
				}
			}
		}
	}

	public static void CombinationValue(Object paramObject1, Object paramObject2) {
		CombinationValue(paramObject1, paramObject2, paramObject1 == null ? null : paramObject1.getClass());
	}

	public static String convertTS(Timestamp paramTimestamp) {
		return paramTimestamp.toString().substring(0, paramTimestamp.toString().indexOf(" "));
	}

	public static String fmtDouble2(double paramDouble) {
		String str = "0.00";
		try {
			DecimalFormat localDecimalFormat = (DecimalFormat) NumberFormat.getNumberInstance();
			localDecimalFormat.applyPattern("###0.00");
			str = localDecimalFormat.format(paramDouble);
		} catch (Exception localException) {
		}
		return str;
	}

	public static String fmtDouble(double paramDouble) {
		String str = "0";
		try {
			str = String.valueOf(BigDecimal.valueOf(paramDouble));
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return str;
	}

	public static String fmtTime(Timestamp paramTimestamp) {
		String str = "";
		try {
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			str = localSimpleDateFormat.format(paramTimestamp);
		} catch (Exception localException) {
		}
		return str;
	}

	public static String fmtDate(Timestamp paramTimestamp) {
		String str = "";
		try {
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			str = localSimpleDateFormat.format(paramTimestamp);
		} catch (Exception localException) {
		}
		return str;
	}

	public static String fmtOnlyTime(Timestamp paramTimestamp) {
		String str = "";
		try {
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
			str = localSimpleDateFormat.format(paramTimestamp);
		} catch (Exception localException) {
		}
		return str;
	}

	public static String fmtTime(java.sql.Date paramDate) {
		String str = "";
		try {
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			str = localSimpleDateFormat.format(paramDate);
		} catch (Exception localException) {
		}
		return str;
	}

	public static String fmtDate(java.sql.Date paramDate) {
		String str = "";
		try {
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			str = localSimpleDateFormat.format(paramDate);
		} catch (Exception localException) {
		}
		return str;
	}

	public static String fmtTime(java.util.Date paramDate) {
		String str = "";
		try {
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			str = localSimpleDateFormat.format(paramDate);
		} catch (Exception localException) {
		}
		return str;
	}

	public static String fmtDate(java.util.Date paramDate) {
		String str = "";
		try {
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			str = localSimpleDateFormat.format(paramDate);
		} catch (Exception localException) {
		}
		return str;
	}

	public static java.util.Date dateAddSeconds(java.util.Date paramDate, Integer paramInteger) {
		if (paramDate == null) {
			throw new IllegalArgumentException("指定时间 不能为空！");
		}
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		localCalendar.add(13, paramInteger.intValue());
		return localCalendar.getTime();
	}

	public static java.sql.Date utilDateTosqlDate(java.util.Date paramDate) {
		if (paramDate == null) {
			return null;
		}
		return new java.sql.Date(paramDate.getTime());
	}

	public static int strToInt(String paramString) {
		return strToInt(paramString, 64536);
	}

	public static int strToInt(String paramString, int paramInt) {
		int i = paramInt;
		try {
			if ((paramString != null) && (paramString.length() != 0)) {
				i = Integer.parseInt(paramString);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return i;
	}

	public static long strToLong(String paramString) {
		return strToLong(paramString, -1000L);
	}

	public static long strToLong(String paramString, long paramLong) {
		long l = paramLong;
		try {
			if ((paramString != null) && (paramString.length() != 0)) {
				if ((paramString.endsWith("L")) || (paramString.endsWith("l"))) {
					paramString = paramString.substring(0, paramString.length() - 1);
				}
				l = Long.parseLong(paramString);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return l;
	}

	public static double strToDouble(String paramString, double paramDouble) {
		double d = paramDouble;
		try {
			if ((paramString != null) && (paramString.length() != 0)) {
				d = Double.parseDouble(paramString);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return d;
	}

	public static float strToFloat(String paramString) {
		return strToFloat(paramString, 0.0F);
	}

	public static float strToFloat(String paramString, float paramFloat) {
		float f = paramFloat;
		try {
			if ((paramString != null) && (paramString.length() != 0)) {
				f = Float.parseFloat(paramString);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return f;
	}

	public static boolean strToBoolean(String paramString) {
		return (paramString != null) && ((paramString.equalsIgnoreCase("true")) || (paramString.equals("1")));
	}

	public static java.util.Date strToDate(String paramString) {
		java.util.Date localDate = null;
		try {
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			localDate = localSimpleDateFormat.parse(paramString);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return localDate;
	}

	public static Time convertTime(String paramString) {
		Time localTime = null;
		try {
			localTime = Time.valueOf(paramString);
		} catch (Exception localException) {
		}
		return localTime;
	}

	public static String delNull(String paramString) {
		if (paramString == null) {
			return "";
		}
		return paramString;
	}

	public static String combineDateTime(String paramString) {
		return combineDateTime(paramString, 0);
	}

	public static String combineDateTime(String paramString, int paramInt) {
		String str1 = null;
		String str2 = null;
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
			str1 = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		}
		if (str2 == "") {
			if (paramInt == 1) {
				str2 = "23:59:59";
			} else {
				str2 = "00:00:00";
			}
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

	public static Timestamp strToTimestamp(String paramString) {
		Timestamp localTimestamp = null;
		Calendar localCalendar = Calendar.getInstance();
		try {
			Integer[] arrayOfInteger = { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0),
					Integer.valueOf(0) };
			paramString = paramString.replaceAll(":|-|/|\\\\| ", "");
			int i = paramString.length();
			if (i >= 14) {
				arrayOfInteger[5] = Integer.valueOf(Integer.parseInt(paramString.substring(12, 14)));
			}
			if (i >= 12) {
				arrayOfInteger[4] = Integer.valueOf(Integer.parseInt(paramString.substring(10, 12)));
			}
			if (i >= 10) {
				arrayOfInteger[3] = Integer.valueOf(Integer.parseInt(paramString.substring(8, 10)));
			}
			if (i >= 8) {
				arrayOfInteger[2] = Integer.valueOf(Integer.parseInt(paramString.substring(6, 8)));
			}
			if (i >= 6) {
				arrayOfInteger[1] = Integer.valueOf(Integer.parseInt(paramString.substring(4, 6)));
			}
			if (i >= 4) {
				arrayOfInteger[0] = Integer.valueOf(Integer.parseInt(paramString.substring(0, 4)));
			}
			localCalendar.set(arrayOfInteger[0].intValue(), arrayOfInteger[1].intValue() - 1, arrayOfInteger[2].intValue(),
					arrayOfInteger[3].intValue(), arrayOfInteger[4].intValue(), arrayOfInteger[5].intValue());
			localTimestamp = new Timestamp(localCalendar.getTime().getTime() / 1000L * 1000L);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return localTimestamp;
	}

	public static java.util.Date strToDate(DateFormat paramDateFormat, String paramString) {
		try {
			return paramDateFormat.parse(paramString);
		} catch (ParseException localParseException) {
		}
		return null;
	}

	public static String getEncoding(String paramString1, String paramString2) {
		if ((paramString1 == null) || (paramString1.trim().length() <= 0)) {
			return paramString1;
		}
		if ((!"GBK".equalsIgnoreCase(paramString2)) && (!"UTF-8".equalsIgnoreCase(paramString2))) {
			paramString2 = "GBK";
		}
		try {
			return new String(paramString1.getBytes("ISO-8859-1"), paramString2);
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
		}
		return "";
	}

	public static Object convert(Object paramObject, String paramString) throws Exception {
		if (paramObject == null) {
			return paramObject;
		}
		if ("string".equals(paramString)) {
			return paramObject.toString().trim();
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
		localObject = ((String) localObject).trim();
		if (((String) localObject).length() == 0) {
			return null;
		}
		if (paramString.equals("int")) {
			return Integer.valueOf(strToInt((String) localObject));
		}
		if (paramString.equals("number")) {
			return Float.valueOf(strToFloat((String) localObject));
		}
		if (paramString.equals("Long")) {
			return Long.valueOf(strToLong((String) localObject));
		}
		if (paramString.equals("date")) {
			localObject = combineDateTime((String) localObject);
			return strToDate(df, (String) localObject);
		}
		if (paramString.equals("datetime")) {
			localObject = combineDateTime((String) localObject, 1);
			return strToDate(df, (String) localObject);
		}
		if ("timestamp".equalsIgnoreCase(paramString)) {
			return strToTimestamp((String) localObject);
		}
		if (paramString.equals("boolean")) {
			return new Boolean(strToBoolean((String) localObject));
		}
		return ((String) localObject).trim();
	}

	public static String convertCurrencyToChinese(Object paramObject) {
		if (paramObject == null) {
			return "";
		}
		BigDecimal localBigDecimal = new BigDecimal(paramObject.toString());
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

	public static String encodeUrl(String paramString) {
		String str1 = "";
		if ((paramString != null) && (paramString.length() > 0)) {
			String[] arrayOfString1 = paramString.split("\\?");
			str1 = arrayOfString1[0];
			String str2 = arrayOfString1.length == 1 ? "" : arrayOfString1[1];
			if (str2.length() > 0) {
				HashMap localHashMap = new HashMap();
				int j = 0;
				int i;
				do {
					i = str2.indexOf('&', j) + 1;
					String str3;
					if (i > 0) {
						str3 = str2.substring(j, i - 1);
						j = i;
					} else {
						str3 = str2.substring(j);
					}
					String[] arrayOfString2 = str3.split("=");
					String str4 = arrayOfString2[0];
					String str5 = arrayOfString2.length == 1 ? "" : arrayOfString2[1];
					try {
						str5 = URLEncoder.encode(str5, "utf-8");
					} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
					}
					localHashMap.put(str4, str5);
				} while (i > 0);
				str1 = str1 + "?";
				Iterator localIterator = localHashMap.keySet().iterator();
				while (localIterator.hasNext()) {
					String str6 = (String) localIterator.next();
					str1 = str1 + str6 + "=" + (String) localHashMap.get(str6);
				}
			}
		}
		return str1;
	}

	public static String getExceptionTrace(Throwable paramThrowable) {
		if (paramThrowable != null) {
			StringWriter localStringWriter = new StringWriter();
			PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
			paramThrowable.printStackTrace(localPrintWriter);
			return localStringWriter.toString();
		}
		return "No Exception";
	}

	public static Integer secondToHour(Integer paramInteger) {
		if (paramInteger == null) {
			return null;
		}
		if (paramInteger.intValue() < 0) {
			return Integer.valueOf(-1);
		}
		int i = paramInteger.intValue() / 60 / 60;
		return Integer.valueOf(i);
	}

	public static Integer HoureToSecond(Integer paramInteger) {
		if (paramInteger == null) {
			return null;
		}
		if (paramInteger.intValue() < 0) {
			return Integer.valueOf(-1);
		}
		return Integer.valueOf(paramInteger.intValue() * 60 * 60);
	}

	public static String getToXml(Map<String, String> paramMap) {
		Set localSet = paramMap.entrySet();
		Iterator localIterator = localSet.iterator();
		Document localDocument = DocumentHelper.createDocument();
		localDocument.setXMLEncoding("GBK");
		Element localElement1 = localDocument.addElement("root");
		while (localIterator.hasNext()) {
			Map.Entry localObject = (Map.Entry) localIterator.next();
			Element localElement2 = localElement1.addElement("keyValue");
			Element localElement3 = localElement2.addElement("key");
			localElement3.addCDATA(((String) ((Map.Entry) localObject).getKey()).toString());
			Element localElement4 = localElement2.addElement("value");
			localElement4.addCDATA(((String) ((Map.Entry) localObject).getValue()).toString());
		}
		Object localObject = localDocument.asXML();
		return (String) localObject;
	}

	public static Map<String, String> addToXml(String paramString) {
		HashMap localHashMap = new HashMap();
		if ((paramString != null) && (!"".equals(paramString))) {
			Document localDocument = null;
			try {
				localDocument = DocumentHelper.parseText(paramString);
			} catch (DocumentException localDocumentException) {
				localDocumentException.printStackTrace();
			}
			Element localElement1 = localDocument.getRootElement();
			Iterator localIterator = localElement1.elementIterator();
			while (localIterator.hasNext()) {
				Element localElement2 = (Element) localIterator.next();
				String str1 = localElement2.element("key").getText();
				String str2 = localElement2.element("value").getText();
				localHashMap.put(str1, str2);
			}
		}
		return localHashMap;
	}
}
