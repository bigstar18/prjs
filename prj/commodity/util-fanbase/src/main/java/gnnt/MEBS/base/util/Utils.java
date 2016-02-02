package gnnt.MEBS.base.util;

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

import gnnt.MEBS.base.exception.BaseException;

public class Utils {
	public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat dfSimple = new SimpleDateFormat("yyyy-MM-dd");

	public static String capitalize(String source) {
		if ((source == null) || (source.length() == 0)) {
			return source;
		}
		if (source.length() == 1) {
			return source.toUpperCase();
		}
		return source.toUpperCase().charAt(0) + source.substring(1);
	}

	public static String concat(Object[] strings, String delimiter) {
		if (strings.length == 0) {
			return "";
		}
		if (delimiter == null) {
			delimiter = "";
		}
		StringBuffer sb = new StringBuffer();
		if (strings[0] != null) {
			sb.append((String) strings[0]);
		}
		for (int i = 1; i < strings.length; i++) {
			sb.append(delimiter);
			if (strings[i] != null) {
				sb.append((String) strings[i]);
			}
		}
		return sb.toString();
	}

	public static String quote(String source, String quote) {
		return quote + source.replaceAll(quote, new StringBuffer("\\").append(quote).toString()) + quote;
	}

	public static String quoteDouble(String source, String quote, boolean enclose) {
		if (enclose) {
			return quote + source.replaceAll(quote, new StringBuffer(String.valueOf(quote)).append(quote).toString()) + quote;
		}
		return source.replaceAll(quote, quote + quote);
	}

	public static Map stringToMap(String source, String delimiter) {
		return stringToMap(source, delimiter, "=");
	}

	public static Map stringToMap(String source, String delimiter, String delimiter2) {
		Map map = new HashMap();
		String[] pairs = source.split(delimiter);
		for (int i = 0; i < pairs.length; i++) {
			String[] pair = pairs[i].split(delimiter2, 2);
			if (pair.length == 2) {
				map.put(pair[0], pair[1]);
			}
		}
		return map;
	}

	public static Date parseDate(DateFormat df, String date) {
		try {
			return df.parse(date);
		} catch (ParseException e) {
		}
		return null;
	}

	public static byte parseByte(String source, byte defaultValue) {
		try {
			return Byte.parseByte(source);
		} catch (NumberFormatException e) {
		}
		return defaultValue;
	}

	public static byte parseByte(String source) {
		return parseByte(source, (byte) 0);
	}

	public static int parseInt(String source, int defaultValue) {
		try {
			return Integer.parseInt(source);
		} catch (NumberFormatException e) {
		}
		return defaultValue;
	}

	public static int parseInt(String source) {
		return parseInt(source, 0);
	}

	public static Integer parseInteger(String source) {
		try {
			return Integer.valueOf(source);
		} catch (NumberFormatException e) {
		}
		return null;
	}

	public static Long parseLong(String source) {
		try {
			return Long.valueOf(source);
		} catch (NumberFormatException e) {
		}
		return null;
	}

	public static float parseFloat(String source, float defaultValue) {
		try {
			return Float.parseFloat(source);
		} catch (NumberFormatException e) {
			return defaultValue;
		} catch (NullPointerException e) {
		}
		return defaultValue;
	}

	public static Float parseFloat(String source) {
		try {
			return Float.valueOf(source);
		} catch (NumberFormatException e) {
			return null;
		} catch (NullPointerException e) {
		}
		return null;
	}

	public static double parseDouble(String source, double defaultValue) {
		try {
			return Double.parseDouble(source);
		} catch (NumberFormatException e) {
			return defaultValue;
		} catch (NullPointerException e) {
		}
		return defaultValue;
	}

	public static Double parseDouble(String source) {
		try {
			return Double.valueOf(source);
		} catch (NumberFormatException e) {
			return null;
		} catch (NullPointerException e) {
		}
		return null;
	}

	public static boolean parseBoolean(String source) {
		return (source != null) && ((source.equalsIgnoreCase("true")) || (source.equals("1")));
	}

	public static char charAt(String source, int index, char def) {
		if ((source == null) || (source.length() <= index)) {
			return def;
		}
		return source.charAt(index);
	}

	public static Object convertSilent(Object source, String type) {
		try {
			return convert(source, type);
		} catch (BaseException ex) {
		}
		return null;
	}

	public static Object convertSilent(Object source, String type, String defaultValue) {
		Object obj = convertSilent(source, type);
		if (obj != null) {
			return obj;
		}
		return convertSilent(defaultValue, type);
	}

	public static Object convert(Object src, String type) throws BaseException {
		if ("string".equals(type)) {
			return src;
		}
		if (src == null) {
			return src;
		}
		if ((src instanceof List)) {
			List list = (List) src;
			if (list.size() == 0) {
				return null;
			}
			src = list.get(0);
		}
		if ((src == null) || (!(src instanceof String))) {
			return src;
		}
		String source = (String) src;
		if (source.length() == 0) {
			return null;
		}
		if (type.equals("int")) {
			return parseLong(source);
		}
		if (type.equals("number")) {
			return parseFloat(source);
		}
		if (type.equals("date")) {
			source = combineDateTime(source);
			return parseDate(df, source);
		}
		if (type.equals("boolean")) {
			return new Boolean(parseBoolean(source));
		}
		return source;
	}

	public static String combineDateTime(String source) {
		String date = null;
		String time = null;
		if (source.indexOf(" ") < 0) {
			if (source.indexOf("-") < 0) {
				date = "";
				time = source;
			} else {
				date = source;
				time = "";
			}
		} else {
			String[] dt = source.split(" ");
			date = dt[0];
			time = dt[1];
		}
		String[] dateFields = date.split("-");
		if (dateFields.length == 2) {
			date = Calendar.getInstance().get(1) + "-" + date;
		} else if (dateFields.length < 2) {
			date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		if (time == "") {
			time = "00:00:00";
		} else {
			String[] timeFields = time.split(":");
			if (timeFields.length == 0) {
				time = "00:00:00";
			} else if (timeFields.length == 1) {
				time = time + ":00:00";
			} else if (timeFields.length == 2) {
				time = time + ":00";
			}
		}
		return date + " " + time;
	}

	public static String formatDate(String fmt, Date date) {
		DateFormat df = new SimpleDateFormat(fmt);
		return df.format(date);
	}

	public static String join(Collection col, String sep, String quote) {
		if (sep == null) {
			sep = ",";
		}
		StringBuffer sb = new StringBuffer();
		Iterator it = col.iterator();
		while (it.hasNext()) {
			if (sb.length() > 0) {
				sb.append(sep);
			}
			Object value = it.next();
			if (value != null) {
				if (quote != null) {
					sb.append(quoteDouble(value.toString(), quote, true));
				} else {
					sb.append(value);
				}
			}
		}
		return sb.toString();
	}

	public static String join(Object[] objArray, String sep, String quote) {
		if (sep == null) {
			sep = ",";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objArray.length; i++) {
			if (sb.length() > 0) {
				sb.append(sep);
			}
			if (objArray[i] != null) {
				if (quote != null) {
					sb.append(quoteDouble(objArray[i].toString(), quote, true));
				} else {
					sb.append(objArray[i]);
				}
			}
		}
		return sb.toString();
	}

	public static List split(String src, String sep) {
		String[] arr = src.split(sep);
		List list = new ArrayList();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		return list;
	}

	public static String convertCurrencyToChinese(Object num) {
		if (num == null) {
			return "";
		}
		BigDecimal value = new BigDecimal(num.toString());
		System.out.println(value);
		String[] strs = value.toString().split("\\.");
		String preDotNum = strs[0];

		String format = "";
		int len = preDotNum.length();
		for (int j = preDotNum.length(); len > 0; len--) {
			if (preDotNum.charAt(j - len) == '-') {
				format = format + "负";
			} else {
				format = format + numChar2chinese(preDotNum.charAt(j - len), len);
			}
		}
		format = format.replaceAll("零仟零佰零拾零", "零");
		format = format.replaceAll("零仟零佰零拾", "零");
		format = format.replaceAll("零仟零佰", "零");
		format = format.replaceAll("零仟", "零");
		format = format.replaceAll("零佰零拾零", "零");
		format = format.replaceAll("零佰零拾", "零");
		format = format.replaceAll("零佰", "零");
		format = format.replaceAll("零拾零", "零");
		format = format.replaceAll("零拾", "零");
		format = format.replaceAll("零亿", "亿");
		format = format.replaceAll("零万", "万");
		format = format.replaceAll("亿万", "亿零");
		format = format.replaceAll("零零", "零");
		if (format.endsWith("零")) {
			format = format.substring(0, format.length() - 1);
		}
		format = format + "元";
		if ((strs.length == 2) && (!"00".equals(strs[1])) && (!"0".equals(strs[1]))) {
			String afterDotNum = strs[1];
			for (int i = 0; (i < afterDotNum.length()) && (i < 2); i++) {
				format = format + numChar2chinese(afterDotNum.charAt(i), 0 - i);
			}
		}
		return format;
	}

	private static String numChar2chinese(char num, int pos) {
		String str = "";
		switch (num) {
		case '0':
			str = "零";
			break;
		case '1':
			str = "壹";
			break;
		case '2':
			str = "贰";
			break;
		case '3':
			str = "叁";
			break;
		case '4':
			str = "肆";
			break;
		case '5':
			str = "伍";
			break;
		case '6':
			str = "陆";
			break;
		case '7':
			str = "柒";
			break;
		case '8':
			str = "扒";
			break;
		case '9':
			str = "玖";
		}
		String posName = "";
		switch (pos) {
		case -1:
			posName = "分";
			break;
		case 0:
			posName = "角";
			break;
		case 1:
			break;
		case 2:
			posName = "拾";
			break;
		case 3:
			posName = "佰";
			break;
		case 4:
			posName = "仟";
			break;
		case 5:
			posName = "万";
			break;
		case 6:
			posName = "拾";
			break;
		case 7:
			posName = "佰";
			break;
		case 8:
			posName = "仟";
			break;
		case 9:
			posName = "亿";
			break;
		case 10:
			posName = "拾";
			break;
		case 11:
			posName = "佰";
			break;
		case 12:
			posName = "仟";
		}
		return str + posName;
	}

	public static void main(String[] args) {
		System.out.print(parseDouble("1451.47776").floatValue());
	}
}
