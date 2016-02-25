package gnnt.bank.platform.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Tool {
	public static void log(String content) {
		Logger plog = Logger.getLogger("Processorlog");
		plog.debug(content);
	}

	public static String convertTS(Timestamp ts) {
		return ts.toString().substring(0, ts.toString().indexOf(" "));
	}

	public static String fmtDouble2(double num) {
		String result = "0.00";
		try {
			DecimalFormat nf = (DecimalFormat) NumberFormat.getNumberInstance();
			nf.applyPattern("###0.00");
			result = nf.format(num);
		} catch (Exception localException) {
		}
		return result;
	}

	public static String formatToMoney(double money) {
		NumberFormat numberFormate = NumberFormat.getCurrencyInstance();
		return numberFormate.format(money);
	}

	public static String fmtMoney(double money) {
		String str = formatToMoney(money);
		String result = str.replaceAll("￥", "");
		return result;
	}

	public static String fmtDouble(double num) {
		String result = "0";
		try {
			result = String.valueOf(BigDecimal.valueOf(num));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String fmtDouble0(double num) {
		String result = "0";
		try {
			DecimalFormat nf = (DecimalFormat) NumberFormat.getNumberInstance();
			nf.applyLocalizedPattern("###0");
			result = nf.format(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String fmtDouble0(BigDecimal num) {
		String result = "0";
		try {
			DecimalFormat nf = (DecimalFormat) NumberFormat.getNumberInstance();
			nf.applyLocalizedPattern("###0");
			result = nf.format(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String handleNull(String str) {
		String NewStr = " ";
		if (str != null) {
			NewStr = str;
		}
		return NewStr;
	}

	public static String fmtTime(Timestamp time) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = sdf.format(time);
		} catch (Exception localException) {
		}
		return result;
	}

	public static String fmtDate(Timestamp time) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.format(time);
		} catch (Exception localException) {
		}
		return result;
	}

	public static String fmtOnlyTime(Timestamp time) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			result = sdf.format(time);
		} catch (Exception localException) {
		}
		return result;
	}

	public static String fmtOnlyTime(java.sql.Date time) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			result = sdf.format(time);
		} catch (Exception localException) {
		}
		return result;
	}

	public static String fmtTime(java.sql.Date time) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = sdf.format(time);
		} catch (Exception localException) {
		}
		return result;
	}

	public static String fmtDate(java.sql.Date time) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.format(time);
		} catch (Exception localException) {
		}
		return result;
	}

	public static String fmtTime(java.util.Date time) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = sdf.format(time);
		} catch (Exception localException) {
		}
		return result;
	}

	public static String fmtDate(java.util.Date time) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.format(time);
		} catch (Exception localException) {
		}
		return result;
	}

	public static int strToInt(String str) {
		int result = -1000;
		try {
			if ((str != null) && (str.length() != 0)) {
				result = Integer.parseInt(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static java.util.Date getDate(String time) {
		java.util.Date result = null;
		String str = time.replaceAll(":|-|/|\\\\| ", "");
		result = new java.util.Date();
		if ((str != null) && (str.trim().length() >= 6)) {
			result.setHours(Integer.parseInt(str.substring(0, 2)));
			result.setMinutes(Integer.parseInt(str.substring(2, 4)));
			result.setSeconds(Integer.parseInt(str.substring(4, 6)));
		}
		return result;
	}

	public static double strToDouble(String str) {
		double result = 0.0D;
		try {
			if ((str != null) && (str.length() != 0)) {
				result = Double.parseDouble(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static java.util.Date strToDate(String str) {
		java.util.Date result = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Time convertTime(String time) {
		Time result = null;
		try {
			result = Time.valueOf(time);
		} catch (Exception localException) {
		}
		return result;
	}

	public static Timestamp string2SqlTimestamp(String sDateTimeValue) {
		Timestamp timestamp = null;
		if (sDateTimeValue == null) {
			timestamp = null;
		} else {
			try {
				timestamp = Timestamp.valueOf(sDateTimeValue);
			} catch (Exception e) {
				timestamp = null;
			}
		}
		return timestamp;
	}

	public static String fmtDateTime(java.util.Date time) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			result = sdf.format(time);
		} catch (Exception localException) {
		}
		return result;
	}

	public static String delNull(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

	public static String getCompareTime() {
		return getConfig("CompareTime");
	}

	public String getIsShowPassWord() {
		return getConfig("IsShowPassWord");
	}

	public static String getIsShowExpress() {
		return getConfig("IsShowExpress");
	}

	public static java.util.Date getDateTime(String str) {
		java.util.Date result = new java.util.Date();
		Calendar ca = Calendar.getInstance();
		try {
			Integer[] nums = getInts(str);
			ca.set(nums[0].intValue(), nums[1].intValue() - 1, nums[2].intValue(), nums[3].intValue(), nums[4].intValue(), nums[5].intValue());
			result = ca.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}

	private static Integer[] getInts(String str) throws Exception {
		Integer[] result = { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0) };
		try {
			str = str.replaceAll("-", "");
			str = str.replaceAll("/", "");
			str = str.replaceAll("\\\\", "");
			str = str.replaceAll(" ", "");
			str = str.replaceAll(":", "");
			int length = str.length();
			if (length >= 14) {
				result[5] = Integer.valueOf(Integer.parseInt(str.substring(12, 14)));
			}
			if (length >= 12) {
				result[4] = Integer.valueOf(Integer.parseInt(str.substring(10, 12)));
			}
			if (length >= 10) {
				result[3] = Integer.valueOf(Integer.parseInt(str.substring(8, 10)));
			}
			if (length >= 8) {
				result[2] = Integer.valueOf(Integer.parseInt(str.substring(6, 8)));
			}
			if (length >= 6) {
				result[1] = Integer.valueOf(Integer.parseInt(str.substring(4, 6)));
			}
			if (length >= 4) {
				result[0] = Integer.valueOf(Integer.parseInt(str.substring(0, 4)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public static String getConfig(String key) {
		Properties props = new Configuration().getSection("BANK.Processor");
		return props.getProperty(key);
	}

	public static void main(String[] args) {
		System.out.println(fmtMoney(-4.0D));
	}

	public static String getGSYTTime() throws IOException {
		URL url = new URL("http://www.gopay.com.cn/PGServer/time");

		Reader reader = new InputStreamReader(new BufferedInputStream(url.openStream()));

		String s = "";
		int c;
		while ((c = reader.read()) != -1) {
			s = s + (char) c;
		}
		reader.close();
		return s;
	}

	public static String trim(String str) {
		if (str != null) {
			return str.trim();
		}
		return null;
	}

	public static String getExceptionTrace(Throwable e) {
		if (e != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return sw.toString();
		}
		return "No Exception";
	}

	public static String fmtDateNo(java.util.Date date) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			result = sdf.format(date);
		} catch (Exception localException) {
		}
		return result;
	}

	public static String fmtDateNo2(java.util.Date date) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			result = sdf.format(date);
		} catch (Exception localException) {
		}
		return result;
	}

	public static java.util.Date strToDate1(String str) {
		java.util.Date result = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			result = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static java.util.Date time2Today(String time) {
		Calendar cd = Calendar.getInstance();
		java.util.Date result = cd.getTime();
		String[] strs = time.split(":", -1);
		result.setHours(Integer.parseInt(strs[0]));
		result.setMinutes(Integer.parseInt(strs[1]));
		result.setSeconds(Integer.parseInt(strs[2]));
		return result;
	}
}
