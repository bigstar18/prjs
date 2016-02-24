package gnnt.trade.bank.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;


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

	/**把数字转换成资金格式*/
	public static String formatToMoney(double money){
		NumberFormat numberFormate;
		numberFormate=NumberFormat.getCurrencyInstance();
		return numberFormate.format(money);
	}
	/**把数字转换成资金格式*/
	public static String fmtMoney(double money){
		String str = formatToMoney(money);
		String result = str.replaceAll("￥", "");
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
	/**保留整数*/
	public static String fmtDouble0(double num){
		String result = "0";
		try{
			DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
			nf.applyLocalizedPattern("###0");
			result = nf.format(num);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**保留整数*/
	public static String fmtDouble0(BigDecimal num){
		String result = "0";
		try{
			DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
			nf.applyLocalizedPattern("###0");
			result = nf.format(num);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
     * 将null字符串转换成" "
     * @param str
     * @return String
     */
	public static String handleNull(String str)
	{		
		String NewStr = " ";
		if(str!=null)
		{
			NewStr=str;
		}
		return NewStr;
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
     * 格式化日期，HH:mm:ss
     * @param time
     * @return String
     */
	public static String fmtOnlyTime(java.sql.Date time)
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
		int result = -1000;
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
	 * 根据传入的时间获取指定日期格式时间
	 * hh:MM:ss
	 */
	@SuppressWarnings("deprecation")
	public static java.util.Date getDate(String time){
		java.util.Date result = null;
		String str = time.replaceAll(":|-|/|\\\\| ", "");
		result = new java.util.Date();
		if(str!=null && str.trim().length()>=6){
			result.setHours(Integer.parseInt(str.substring(0, 2)));
			result.setMinutes(Integer.parseInt(str.substring(2, 4)));
			result.setSeconds(Integer.parseInt(str.substring(4, 6)));
		}
		return result;
	}
	/**
     * 将字符串转换成double
     * @param str
     * @return double
     */
	public static double strToDouble(String str)
	{			
		double result = 0;
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
		}
		return result;
	}
	/**
	 * 字符串转换为时间类型 Timestamp
	 * @param time 格式HH:mm:ss
	 * @return
	 */
	public static java.sql.Timestamp string2SqlTimestamp(String sDateTimeValue)
    {
        java.sql.Timestamp timestamp = null;
        if (sDateTimeValue == null)
        {
            timestamp = null;
        }
        else
        {
            try
            {
                timestamp = java.sql.Timestamp.valueOf(sDateTimeValue);
            }
            catch(Exception e)
            {
                timestamp = null;
            }
        }
        return timestamp;
    }

	/**
     * 格式化日期，yyyy-MM-dd
     * @param time
     * @return String
     */
	public static String fmtDateTime(java.util.Date time)
	{			
		String result = "";
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); 
			result = sdf.format(time);
		}
		catch(Exception e)
		{
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
	public static String getCompareTime()
	{
		return getConfig("CompareTime");
	}
	/**
	 * 取得在出入金界面是否显示账户密码输入框的值
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public String getIsShowPassWord()
	{
		return getConfig("IsShowPassWord");
	}
	
	
	/**
	 * 取得在出入金界面是否显示账户密码输入框的值
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static String getIsShowExpress()
	{
		return getConfig("IsShowExpress");
	}
	/**根据所传入的年月日时分秒获得相对应的时间*/
	public static java.util.Date getDateTime(String str){
		java.util.Date result=new java.util.Date();
		java.util.Calendar ca =java.util.Calendar.getInstance();
		try{
			Integer[] nums= getInts(str);
			ca.set(nums[0], nums[1]-1, nums[2], nums[3], nums[4], nums[5]);
			result = ca.getTime();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}
	/**
	 * 将时间格式的字符串以年月日时分秒的格式转化成Integer[]返回
	 * @param str
	 * @return
	 * @throws Exception
	 */
	private static Integer[] getInts(String str) throws Exception{
		Integer [] result = {0,0,0,0,0,0};
		try{
			str=str.replaceAll("-","");
			str=str.replaceAll("/","");
			str=str.replaceAll("\\\\","");
			str=str.replaceAll(" ","");
			str=str.replaceAll(":","");
			int length = str.length();
			if(length>=14){
				result[5]=Integer.parseInt(str.substring(12, 14));
			}
			if(length>=12){
				result[4]=Integer.parseInt(str.substring(10, 12));
			}
			if(length>=10){
				result[3]=Integer.parseInt(str.substring(8, 10));
			}
			if(length>=8){
				result[2]=Integer.parseInt(str.substring(6, 8));
			}
			if(length>=6){
				result[1]=Integer.parseInt(str.substring(4, 6));
			}
			if(length>=4){
				result[0]=Integer.parseInt(str.substring(0, 4));
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	/**
	 * 取得配置信息
	 * @param key 配置项
	 * @return String
	 * @throws 
	 */
	public static String getConfig(String key)
	{
		Properties props = new Configuration().getSection("BANK.Processor");
		return props.getProperty(key);
	}
	public static void main(String args[]){
		System.out.println(fmtMoney(-4));
	}
	/**
	 * 获取国付宝时间戳
	 * @throws IOException
	 */
	public static String getGSYTTime() throws IOException { 
        URL url = new URL("http://www.gopay.com.cn/PGServer/time"); 
        //打开到此 URL 的连接并返回一个用于从该连接读入的 InputStream。 
        Reader reader = new InputStreamReader(new BufferedInputStream(url.openStream())); 
        int c; 
        String s="";
        while ((c = reader.read()) != -1) { 
               s+=(char)c;
        } 
        reader.close(); 
        return s;
	} 
	//--------------通讯日志 start -----------------
	/**
	 * 去掉左右空格
	 */
	public static String trim(String str){
		if(str != null){
			return str.trim();
		}
		return null;
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
	//--------------通讯日志 end -----------------
}
