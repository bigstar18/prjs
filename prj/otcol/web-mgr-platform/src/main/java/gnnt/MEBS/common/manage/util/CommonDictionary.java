package gnnt.MEBS.common.manage.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommonDictionary
{
  public static Map VIRTUALFUNDAPPLYTYPE = new LinkedHashMap();
  public static Map VIRTUALFUNDSTATUS;
  public static Map OPERATECHECK;
  public static Map FEEALGR;
  public static Map SETTLEFEEALGR;
  public static Map MARGINPRICETYPE;
  
  static
  {
    VIRTUALFUNDAPPLYTYPE.put("1", "虚拟资金");
    VIRTUALFUNDAPPLYTYPE.put("2", "质押");
    VIRTUALFUNDSTATUS = new LinkedHashMap();
    VIRTUALFUNDSTATUS.put("1", "待审核");
    VIRTUALFUNDSTATUS.put("2", "审核通过");
    VIRTUALFUNDSTATUS.put("3", "审核不通过");
    OPERATECHECK = new LinkedHashMap();
    OPERATECHECK.put("1", "添加");
    OPERATECHECK.put("2", "删除");
    FEEALGR = new LinkedHashMap();
    FEEALGR.put("1", "按百分比");
    FEEALGR.put("2", "按绝对值");
    SETTLEFEEALGR = new LinkedHashMap();
    SETTLEFEEALGR.put("1", "按百分比");
    SETTLEFEEALGR.put("2", "按绝对值");
    MARGINPRICETYPE = new LinkedHashMap();
    MARGINPRICETYPE.put("0", "订立价");
    MARGINPRICETYPE.put("1", "昨结算价");
    MARGINPRICETYPE.put("2", "盘中按订立价");
  }
}
