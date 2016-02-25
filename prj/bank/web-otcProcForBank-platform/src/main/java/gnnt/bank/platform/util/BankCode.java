package gnnt.bank.platform.util;

import java.util.HashMap;
import java.util.Map;

public class BankCode
{
  public static final String NBCB = "宁波银行";
  public static final String BOCOMM = "交通银行";
  public static final String CIB = "兴业银行";
  public static final String BOC = "中国银行";
  public static final String CEB = "光大银行";
  public static final String CCB = "建设银行";
  public static final String ABC = "农业银行";
  public static final String ICBC = "工商银行";
  public static final String CMB = "招商银行";
  public static final String CMBC = "民生银行";
  public static final String CITIC = "中信银行";
  public static final String SPDB = "浦发银行";
  public static final String UNION = "银联";
  public static final String SDB = "深圳发展银行";
  public static final String PINGANBANK = "平安银行";
  public static final String HXB = "华夏银行";
  public static final String GDB = "重庆三峡银行";
  public static final String CQRCB = "重庆农村商业银行";
  public static final String PSBC = "邮政储蓄银行";
  public static Map<String, String> codeForBank = new HashMap();
  
  public void load()
  {
    codeForBank.put("NBCB", "宁波银行");
    codeForBank.put("BOCOMM", "交通银行");
    codeForBank.put("CIB", "兴业银行");
    codeForBank.put("BOC", "中国银行");
    codeForBank.put("CEB", "光大银行");
    codeForBank.put("CCB", "建设银行");
    codeForBank.put("ABC", "农业银行");
    codeForBank.put("ICBC", "工商银行");
    codeForBank.put("CMB", "招商银行");
    codeForBank.put("CMBC", "民生银行");
    codeForBank.put("CITIC", "中信银行");
    codeForBank.put("SPDB", "浦发银行");
    codeForBank.put("SDB", "深圳发展银行");
    codeForBank.put("PINGANBANK", "平安银行");
    codeForBank.put("HXB", "华夏银行");
    codeForBank.put("GDB", "重庆三峡银行");
    codeForBank.put("CQRCB", "重庆农村商业银行");
    codeForBank.put("PSBC", "邮政储蓄银行");
  }
}
