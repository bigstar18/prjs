package gnnt.bank.adapter.util;



import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 通用方法类
 * @author gaobo
 *
 */
public class Common {
	
	//时间格式
	public static DateFormat df=new SimpleDateFormat("yyyyMMdd HHmmss");
	public static DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat df3=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static DateFormat df4 = new SimpleDateFormat("HH:mm:ss");
	public static DateFormat df5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static DateFormat df6 = new SimpleDateFormat("yyyyMMddHHmmss");
	public static DateFormat df7 = new SimpleDateFormat("yyyyMMdd");
	//每一项后跟随一个分隔符
	public static final String endSign = ".";
	/**
	 * 取当前时间
	 * @author gaobo
	 * @return
	 */
	public static Date getDate(){
		Calendar ca = Calendar.getInstance();
		Date date = ca.getTime();
		return date;
	}
	
	/**
	 * 从字符串yyyyMMddHHmmss转换成yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public static String strTime(String time){
		String line = "-";
		String s = ":";
		String strTime = time.substring(0,4)+line+time.substring(4,6)+line+time.substring(6,8)
		+" "+time.substring(8,10)+s+time.substring(10,12)+s+time.substring(12,14);
		return strTime;
	}
	
	/**
	 * 根据各个银行的要求不同生成特定位数的市场流水号
	 * @author gaobo
	 * @param key
	 * @return
	 */
	public static String MakeMarketSer(long marketWater,int num){
		String marketSer = String.valueOf(marketWater);
		int strNum = marketSer.length();
		for(int i = 0;i < num - strNum;i++){
			marketSer = "0"+marketSer;
		}
		return marketSer;
	}
	
	private static final int mLen = 8;
	/**
	 * string转double
	 * @param Amt
	 * @return
	 */
	public static double TrDouble(String Amt) {
		if(Amt== null)
			Amt = "0";
        else if(Amt.trim().equals(""))
        	Amt= "0";
		double amtInt = 0;
		amtInt = Double.parseDouble(Amt);
		return amtInt;
	}
	/**
	 * 按字段长度设置为空格
	 * @param len
	 * @return
	 */
	public static String Spell(int len) {
		String str = "";
		for(int i=1;i<=len; i++) {
			str += " ";
		}
		return str;
	}
	/**
	 * 按已有的字段长度设置实际的长度，补空格
	 * @param len
	 * @return
	 */
	public static String Spell(String str,int len) {
		if(str==null) {
			str = "";
			for(int i=1;i<=len; i++) {
				str += " ";
			}
		} else {
			int slen = str.length();
			for(int i=1;i<=len-slen; i++) {
				str += " ";
			}
		}
		return str;
	}
	
	/**
	 * 数据包长度共8位，不够位数前补0
	 * @param len
	 * @return
	 */
	public static String setFill(int len) {
		String str = "";
		int len1 = String.valueOf(len).length();
		if(len1<mLen) {
			for(int i=1;i<=mLen-len1; i++) {
				str += "0";
			}
		}
		str = str + len;
		return str;
	}
	public static String MakeMarketSer(int key){
		String marketSer = String.valueOf(key);
		int strNum = marketSer.length();
		int Num = 6;
		for(int i = 0;i < Num - strNum;i++){
			marketSer = "0"+marketSer;
		}
		return marketSer;
	}
	public static boolean isNum(String msg){
		if(java.lang.Character.isDigit(msg.charAt(0))){
		return true;
		}
		return false;
	}
	public static String disDouble2(double f)
    {
        String result = "0.00";
        try
        {
            DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
            nf.applyPattern("0.00");
            result = nf.format(f);
        }
        catch(Exception exception) { }
        return result;
    }
}
