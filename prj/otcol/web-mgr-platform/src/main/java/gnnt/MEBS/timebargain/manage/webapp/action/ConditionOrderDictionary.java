package gnnt.MEBS.timebargain.manage.webapp.action;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConditionOrderDictionary
{
  public static Map BS_FLAG = new LinkedHashMap();
  public static Map ORDERTYPE;
  public static Map CONDITIONOPERATION;
  
  static
  {
    BS_FLAG.put("1", "买");
    BS_FLAG.put("2", "卖");
    ORDERTYPE = new LinkedHashMap();
    ORDERTYPE.put("1", "订立");
    ORDERTYPE.put("2", "转让");
    CONDITIONOPERATION = new LinkedHashMap();
    CONDITIONOPERATION.put("-1", "小于");
    CONDITIONOPERATION.put("1", "大于");
    CONDITIONOPERATION.put("0", "等于");
    CONDITIONOPERATION.put("-2", "小于等于");
    CONDITIONOPERATION.put("2", "大于等于");
  }
}
