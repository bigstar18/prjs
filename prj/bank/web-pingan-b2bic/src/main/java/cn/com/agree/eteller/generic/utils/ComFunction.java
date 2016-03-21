package cn.com.agree.eteller.generic.utils;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.com.agree.eteller.generic.vo.LoginUser;
import jxl.Workbook;

public class ComFunction {
	private static final Logger logger = Logger.getLogger(ComFunction.class);

	public static String getCurrentTime2() {
		SimpleDateFormat sf = new SimpleDateFormat("HHmmss");
		return sf.format(new Date());
	}

	public static String getCurrentDate() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		return sf.format(new Date());
	}

	public static String getCurrentDate2() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public static String getCurrentTimeNoSeparator() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-ddHHmmss");
		return sd.format(new Date());
	}

	public static String getCurrentDateTime() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd-");
		String date = sf.format(new Date());
		String time = new Date().getHours() + "." + new Date().getMinutes() + "." + new Date().getSeconds() + ".000000";
		return date + time;
	}

	public static String getCurrentDateTime2() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sd.format(new Date());
	}

	public static boolean isSatOrSun(String date) {
		if (date == null) {
			return false;
		}
		date = date.replaceAll(" ", "");
		if (date.length() < 8) {
			return false;
		}
		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.set(1, Integer.parseInt(date.substring(0, 4)));
			gc.set(2, Integer.parseInt(date.substring(4, 6)) - 1);
			gc.set(5, Integer.parseInt(date.substring(6)));
			int week = gc.get(7);
			if ((week == 1) || (week == 7)) {
				return true;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String formatNumber(String number) {
		String str = "0";
		if (number == null) {
			return str;
		}
		number = number.replaceAll(" ", "");
		if (number.equals("")) {
			return str;
		}
		DecimalFormat decimalFormat = null;
		try {
			decimalFormat = new DecimalFormat("#,##0.00");
			str = decimalFormat.format(Double.parseDouble(number));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String formatNumberNosep(String number) {
		String str = "0";
		if (number == null) {
			return str;
		}
		number = number.replaceAll(" ", "");
		if (number.equals("")) {
			return str;
		}
		DecimalFormat decimalFormat = null;
		try {
			decimalFormat = new DecimalFormat("###0.00");
			str = decimalFormat.format(Double.parseDouble(number));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String formatNumberDivide(String number) {
		String str = "0";
		if (number == null) {
			return str;
		}
		number = number.replaceAll(" ", "");
		if (number.equals("")) {
			return str;
		}
		DecimalFormat decimalFormat = null;

		number = new BigDecimal(number).divide(new BigDecimal("100"), 2, 0).toString();
		try {
			decimalFormat = new DecimalFormat("#,##0.00");
			str = decimalFormat.format(Double.parseDouble(number));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static boolean isMincurrdate(String date) {
		if (date == null) {
			return false;
		}
		date = date.replaceAll(" ", "");
		if (date.length() < 8) {
			return false;
		}
		Date currdate = new Date();
		long curlong = currdate.getTime();
		long srclong = 0L;
		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.set(1, Integer.parseInt(date.substring(0, 4)));
			gc.set(2, Integer.parseInt(date.substring(4, 6)) - 1);
			gc.set(5, Integer.parseInt(date.substring(6)));

			srclong = gc.getTime().getTime();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (curlong - srclong < 0L) {
			return false;
		}
		if (curlong - srclong < 86400000L) {
			return true;
		}
		if (curlong > srclong) {
			return true;
		}
		return false;
	}

	public static String[] getDateByinterval(String startdate, String enddate) {
		if ((startdate == null) || (enddate == null)) {
			return null;
		}
		startdate = startdate.replaceAll(" ", "");
		enddate = enddate.replaceAll(" ", "");
		if ((startdate.length() < 8) || (enddate.length() < 8)) {
			return null;
		}
		String[] date = (String[]) null;
		try {
			GregorianCalendar startgc = new GregorianCalendar();
			startgc.set(1, Integer.parseInt(startdate.substring(0, 4)));
			startgc.set(2, Integer.parseInt(startdate.substring(4, 6)) - 1);
			startgc.set(5, Integer.parseInt(startdate.substring(6)));
			GregorianCalendar endgc = new GregorianCalendar();
			endgc.set(1, Integer.parseInt(enddate.substring(0, 4)));
			endgc.set(2, Integer.parseInt(enddate.substring(4, 6)) - 1);
			endgc.set(5, Integer.parseInt(enddate.substring(6)));

			long startlong = startgc.getTime().getTime();
			long endlong = endgc.getTime().getTime();
			long sec = endlong - startlong;
			if (sec < 0L) {
				return null;
			}
			long k = sec / 86400000L;
			long p = sec % 86400000L;
			if (p != 0L) {
				k += 1L;
			}
			date = new String[(int) k + 1];
			date[0] = startdate;
			long dateL = startlong;
			for (int i = 1; i < date.length; i++) {
				dateL += 86400000L;
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
				date[i] = sf.format(new Date(dateL));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getWeek(String date) {
		int week = 0;
		String[] weekName = { "礼拜天", "礼拜一", "礼拜二", "礼拜三", "礼拜四", "礼拜五", "礼拜六" };
		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.set(1, Integer.parseInt(date.substring(0, 4)));
			gc.set(2, Integer.parseInt(date.substring(4, 6)) - 1);
			gc.set(5, Integer.parseInt(date.substring(6)));

			week = gc.get(7);
			return weekName[(week - 1)];
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-1";
	}

	public static String getWeek2(String date) {
		int week = 0;
		String[] weekName = { "星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.set(1, Integer.parseInt(date.substring(0, 4)));
			gc.set(2, Integer.parseInt(date.substring(4, 6)) - 1);
			gc.set(5, Integer.parseInt(date.substring(6)));

			System.out.println(gc.get(7));
			week = gc.get(7);
			return weekName[(week - 1)];
		} catch (Exception e) {
			logger.warn("格式转换出错：" + date);
		}
		return "";
	}

	public boolean checkNum(String input) {
		boolean b = false;
		try {
			Long.parseLong(input);
		} catch (Exception e) {
			e.printStackTrace();
			b = true;
		}
		if (input.indexOf("-") != -1) {
			b = true;
		}
		return b;
	}

	public static boolean outOfDateRange(String d1, String d2, int days) {
		boolean b = false;
		try {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			Date d = sf.parse(d1);
			c.setTime(d);
			c.add(5, days);
			c.getTime();
			String ddd = sf.format(c.getTime());
			int aaa = d2.compareTo(ddd);
			if (aaa > 0) {
				b = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			b = true;
		}
		return b;
	}

	public static String convertToDispNum(String decimal) {
		String transdecimal = insertDot(decimal, 2);
		String num = transdecimal.substring(0, transdecimal.indexOf("."));
		char[] cnum = num.toCharArray();
		String temp = "";
		int count = 0;
		for (int i = cnum.length - 1; i >= 0; i--) {
			count++;
			temp = String.valueOf(cnum[i]) + temp;
			if ((count % 3 == 0) && (i != 0)) {
				temp = "," + temp;
			}
		}
		temp = temp + transdecimal.substring(transdecimal.indexOf("."));
		return temp;
	}

	public static String insertDot(String str, int bit) throws NumberFormatException {
		StringBuffer sb = new StringBuffer(str);
		if (str.length() - bit < 1) {
			for (int i = 0; i < -(str.length() - bit) + 1; i++) {
				sb.insert(0, "0");
			}
		}
		sb.insert(sb.length() - bit, ".");
		return sb.toString();
	}

	public static int checkDate(String inDate) {
		int iFlag = 0;
		if (inDate.length() != 8) {
			return -1;
		}
		for (int i = 0; i < inDate.length(); i++) {
			if (!Character.isDigit(inDate.charAt(i))) {
				return -1;
			}
		}
		Calendar c = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		String curDate = fmt.format(c.getTime());

		String inYear = inDate.substring(0, 4);
		String inMon = inDate.substring(4, 6);
		String inDay = inDate.substring(6, 8);
		if ((Integer.parseInt(inMon) < 1) || (Integer.parseInt(inMon) > 12) || (Integer.parseInt(inDay) < 1)) {
			return -1;
		}
		if (((Integer.parseInt(inYear) % 4 == 0) && (Integer.parseInt(inYear) % 100 != 0)) || (Integer.parseInt(inYear) % 400 == 0)) {
			iFlag = 1;
		}
		if ((Integer.parseInt(inMon) == 1) || (Integer.parseInt(inMon) == 3) || (Integer.parseInt(inMon) == 5) || (Integer.parseInt(inMon) == 7)
				|| (Integer.parseInt(inMon) == 8) || (Integer.parseInt(inMon) == 10) || (Integer.parseInt(inMon) == 12)) {
			if (Integer.parseInt(inDay) > 31) {
				return -1;
			}
		} else if (Integer.parseInt(inMon) == 2) {
			if ((iFlag == 1) && (Integer.parseInt(inDay) > 29)) {
				return -1;
			}
			if ((iFlag == 0) && (Integer.parseInt(inDay) > 28)) {
				return -1;
			}
		} else if (Integer.parseInt(inDay) > 30) {
			return -1;
		}
		return 0;
	}

	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if ((c >= 0) && (c <= 'ÿ')) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0) {
						k += 256;
					}
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	public static void putmapByDeptype(Map map) {
		String zoneno = null;
		String brno = null;
		LoginUser user = (LoginUser) ServletActionContext.getRequest().getSession().getAttribute("user");
		String deptype = user.getDept().getDepnotype();
		if (deptype.equals("1")) {
			zoneno = user.getDept().getId();
			map.put("zoneno", zoneno);
		} else if ((deptype.equals("2")) || (deptype.equals("3"))) {
			zoneno = user.getDept().getSuperiorDept().getId();
			brno = user.getDept().getId();
			map.put("zoneno", zoneno);
			map.put("brno", brno);
		}
	}

	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if ("".equals(obj)) {
			return true;
		}
		if ((obj.getClass().isArray()) && (((Object[]) obj).length == 0)) {
			return true;
		}
		if (((obj instanceof Collection)) && (((Collection) obj).isEmpty())) {
			return true;
		}
		if (((obj instanceof Number)) && ((obj.equals(Integer.valueOf(0))) || (obj.equals(Double.valueOf(0.0D))))) {
			return true;
		}
		if (((obj instanceof Map)) && (((Map) obj).isEmpty())) {
			return true;
		}
		return false;
	}

	public static String numToUpper(int num) throws Exception {
		if (num > 10) {
			throw new Exception("所转换的数字不能超过10");
		}
		String[] u = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾" };

		char[] str = String.valueOf(num).toCharArray();
		String rstr = "";
		for (int i = 0; i < str.length; i++) {
			rstr = rstr + u[Integer.parseInt(new StringBuilder(String.valueOf(str[i])).toString())];
		}
		return rstr;
	}

	public static String convertMoney2Ch(double money) {
		return Amount2RMB.convert(formatNumber(new Double(money).toString()));
	}

	public static String convertMoney2Ch(String money) {
		return Amount2RMB.convert(formatNumber(money));
	}

	public static Map<String, String> convertYuan2Fen(String[] fields, Map<String, String> map) {
		String[] arrayOfString = fields;
		int j = fields.length;
		for (int i = 0; i < j; i++) {
			String f = arrayOfString[i];
			String money = (String) map.get(f);
			if ((money != null) && (!"".equals(money))) {
				map.put(f, money.replaceAll("[\\.,]", ""));
			}
		}
		return map;
	}

	public static Map<String, String> convertFen2Yuan(String[] fields, Map<String, String> map) {
		String[] arrayOfString = fields;
		int j = fields.length;
		for (int i = 0; i < j; i++) {
			String f = arrayOfString[i];
			String money = (String) map.get(f);
			if ((money != null) && (!"".equals(money))) {
				map.put(f, formatNumberDivide(money));
			}
		}
		return map;
	}

	public static void convertFen2Yuan(String[] fields, Object bean) throws Exception {
		String[] arrayOfString = fields;
		int j = fields.length;
		for (int i = 0; i < j; i++) {
			String f = arrayOfString[i];
			try {
				String val = BeanUtils.getProperty(bean, f);
				if (!isEmpty(val)) {
					BeanUtils.setProperty(bean, f, formatNumberDivide(val));
				}
			} catch (IllegalAccessException e) {
			} catch (Exception e) {
				throw e;
			}
		}
	}

	public static void convertYuan2Fen(String[] fields, Object bean) throws Exception {
		String[] arrayOfString = fields;
		int j = fields.length;
		for (int i = 0; i < j; i++) {
			String f = arrayOfString[i];
			try {
				String val = BeanUtils.getProperty(bean, f);
				if (!isEmpty(val)) {
					BeanUtils.setProperty(bean, f, val.replaceAll("[\\.,]", ""));
				}
			} catch (IllegalAccessException e) {
			} catch (Exception e) {
				throw e;
			}
		}
	}

	public static String convertNoSaperatorDateStr(String dateStr) throws Exception {
		try {
			if (dateStr.matches("^\\d{8}$")) {
				return dateStr;
			}
			String[] dateStrs = dateStr.split("[/-]");

			StringBuilder sb = new StringBuilder();
			if (dateStrs[0].length() == 2) {
				sb.append("20");
			}
			sb.append(dateStrs[0]);
			if ((dateStrs[1].length() > 0) && (dateStrs[1].length() < 2)) {
				sb.append("0");
			}
			sb.append(dateStrs[1]);
			if ((dateStrs[2].length() > 0) && (dateStrs[2].length() < 2)) {
				sb.append("0");
			}
			sb.append(dateStrs[2]);

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("转换无分隔符日期字符串格式出错！");
		}
	}

	public static void main(String[] args) throws Exception {
		Workbook w = Workbook.getWorkbook(new File("C:/Users/Administrator/Desktop/人员日志导入模板.xls"));
		System.out.println(convertNoSaperatorDateStr(w.getSheet(0).getCell(5, 1).getContents()));
	}
}
