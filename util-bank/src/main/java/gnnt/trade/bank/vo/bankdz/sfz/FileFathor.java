package gnnt.trade.bank.vo.bankdz.sfz;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
/**文件信息父类*/
public class FileFathor implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String separator = "&";
	/**传入参数，看是否超出给定长度*/
	protected String overLong(String str,int length) throws Exception{
		if(str==null){
			str = "";
		}
		str = str.trim();
		if(str.getBytes().length>length){
			throw new Exception(str+"给定长度："+length+",实际长度："+str.getBytes().length);
		}
		return str;
	}
	/**格式化double型*/
	protected String fmtDouble(double money){
		String result = "0.00";
        try{
            DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
            nf.applyPattern("0.00");
            result = nf.format(money);
        }catch(Exception exception) { }
        return result;
	}
	/**格式化时间*/
	protected String dateToStr1(java.util.Date date){
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
			result = sdf.format(date);
		} catch(Exception e) {
		}	
		return result;
	}
	/**字符串转化成时间*/
	protected java.util.Date strToDate1(String str){
		java.util.Date result = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			result = sdf.parse(str);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
