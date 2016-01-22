package gnnt.trade.bank.vo.bankdz.sfz;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileFathor
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected String separator = "&";

  protected String overLong(String str, int length) throws Exception {
    if (str == null) {
      str = "";
    }
    str = str.trim();
    if (str.getBytes().length > length) {
      throw new Exception(str + "给定长度：" + length + ",实际长度：" + str.getBytes().length);
    }
    return str;
  }

  protected String fmtDouble(double money) {
    String result = "0.00";
    try {
      DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
      nf.applyPattern("0.00");
      result = nf.format(money); } catch (Exception localException) {
    }
    return result;
  }

  protected String dateToStr1(Date date) {
    String result = "";
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      result = sdf.format(date);
    } catch (Exception localException) {
    }
    return result;
  }

  protected Date strToDate1(String str) {
    Date result = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      result = sdf.parse(str);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
}