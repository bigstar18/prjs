package gnnt.MEBS.checkLogon.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;


/**
 * <p>Title: 工具类</p>
 *
 * <p>Description:</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: gnnt</p>
 *
 * @author zhangzhongli
 * @version 1.0
 */
public class Tool {
	/**
	   * 将Timestamp类型数据转换为'YYYY-MM-DD'
	   * @param Timestamp 
	   * @return String
	   */
	public static String convertTS(Timestamp ts)
	{
		return ts.toString().substring(0, ts.toString().indexOf(" "));
	}
	
	/**
     * 格式化双精度浮点数，保留小数点后两位
     * @param num
     * @return String
     */
	public static String fmtDouble2(double num)
	{
		String result = "0.00";
		try
		{		
			DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
			nf.applyPattern("###0.00");
			result = nf.format(num); 
		}
		catch(Exception e)
		{
		}	
		return result;
	}

	/**
	 * 
	 * 方法说明：
	 * <br/><br/>
	 * @param num 待格式化的浮点数
	 * @param n 格式化后小数位数，大于等于 0，小于 0 按 0 计算
	 * @return
	 */
	public static String fmtDoublen(double num,int n){
		String result = "0";
		if(n>0){
			result += ".";
			for(int i=0;i<n;i++){
				result += "0";
			}
		}
		try{		
			DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
			nf.applyPattern("###"+result);
			result = nf.format(num); 
		}catch(Exception e){}	
		return result;
	}
	
	/**
	 * 把双精度浮点数转换为字符串
	 * @param num
	 * @return
	 */
	public static String fmtDouble(double num){
		String result = "0";
		try{
			result = String.valueOf(BigDecimal.valueOf(num));
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
     * 格式化时间，yyyy-MM-dd HH:mm:ss
     * @param time
     * @return String
     */
	public static String fmtTime(Timestamp time)
	{			
		String result = "";
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			result = sdf.format(time);
		}
		catch(Exception e)
		{
		}	
		return result;
	}
	
	/**
     * 格式化日期，yyyy-MM-dd
     * @param time
     * @return String
     */
	public static String fmtDate(Timestamp time)
	{					
		String result = "";
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			result = sdf.format(time);
		}
		catch(Exception e)
		{
		}	
		return result;
	}
	
	/**
     * 格式化日期，HH:mm:ss
     * @param time
     * @return String
     */
	public static String fmtOnlyTime(Timestamp time)
	{					
		String result = "";
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); 
			result = sdf.format(time);
		}
		catch(Exception e)
		{
		}	
		return result;
	}
	
	/**
     * 格式化时间，yyyy-MM-dd HH:mm:ss
     * @param time
     * @return String
     */
	public static String fmtTime(Date time)
	{			
		String result = "";
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			result = sdf.format(time);
		}
		catch(Exception e)
		{
		}	
		return result;
	}
	
	/**
     * 格式化日期，yyyy-MM-dd
     * @param time
     * @return String
     */
	public static String fmtDate(java.sql.Date time)
	{			
		String result = "";
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			result = sdf.format(time);
		}
		catch(Exception e)
		{
		}	
		return result;
	}
	
	/**
     * 格式化时间，yyyy-MM-dd HH:mm:ss
     * @param time
     * @return String
     */
	public static String fmtTime(java.util.Date time)
	{			
		String result = "";
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			result = sdf.format(time);
		}
		catch(Exception e)
		{
		}	
		return result;
	}
	
	/**
     * 格式化日期，yyyy-MM-dd
     * @param time
     * @return String
     */
	public static String fmtDate(java.util.Date time)
	{			
		String result = "";
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			result = sdf.format(time);
		}
		catch(Exception e)
		{
		}	
		return result;
	}
	
	/**
     * 将字符串转换成int
     * @param str
     * @return int
     */
	public static int strToInt(String str)
	{			
		return strToInt(str,-1000);
	}
	
	/**
	 * 将字符串转换成int 
	 * @param str
	 * @param defaultV 出现异常后 默认值
	 * @return
	 */
	public static int strToInt(String str,int defaultV)
	{			
		int result = defaultV;
		try
		{
			if(str!=null && str.length()!=0) result = Integer.parseInt(str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	/**
     * 将字符串转换成double
     * @param str
     * @param defaultValue 默认值
     * @return double
     */
	public static double strToDouble(String str,double defaultValue)
	{			
		double result = defaultValue;
		try
		{
			if(str!=null && str.length()!=0) result = Double.parseDouble(str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	/**
     * 将字符串转换成date
     * @param str 格式yyyy-mm-dd
     * @return double
     */
	public static java.util.Date strToDate(String str)
	{			
		java.util.Date result = null;
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.parse(str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 字符串转换为时间类型
	 * @param time 格式HH:mm:ss
	 * @return
	 */
	public static java.sql.Time convertTime(String time)
	{
		java.sql.Time result=null;
		try
		{
			result=java.sql.Time.valueOf(time);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		return result;
	}
	
	/**
     * 屏蔽为null的字符串
     * @param str 
     * @return String
     */
	public static String delNull(String str)
	{
		if(str == null)
		{
			return "";
		}
		else
		{
			return str;
		}
	}

	/**
	 * 将异常信息导入字符串
	 * @param e 异常信息
	 * @return String
	 */
	public static String getExceptionTrace(Throwable e) {
		if (e != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return sw.toString();
		}
       return "No Exception";
    }
	
	/**
	 * 将字符串转换成long
	 * 
	 * @param str
	 * @return int
	 */
	public static long strToLong(String str) {
		return strToLong(str, -1000);
	}

	/**
	 * 将字符串转换成long
	 * 
	 * @param str
	 * @param defaultV
	 *            出现异常后 默认值
	 * @return
	 */
	public static long strToLong(String str, long defaultV) {
		long result = defaultV;
		try {
			if (str != null && str.length() != 0)
				result = Long.parseLong(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
