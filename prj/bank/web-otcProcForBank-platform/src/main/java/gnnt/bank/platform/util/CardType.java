package gnnt.bank.platform.util;

import java.util.HashMap;
import java.util.Map;

public class CardType
{
  public static Map<String, String> cardTypeMap = new HashMap();
  
  public void load()
  {
    cardTypeMap.put("1", "身份证");
    cardTypeMap.put("2", "军官证");
    cardTypeMap.put("3", "国内护照");
    cardTypeMap.put("4", "户口本");
    cardTypeMap.put("5", "学员证");
    cardTypeMap.put("6", "退休证");
    cardTypeMap.put("7", "临时身份证");
    cardTypeMap.put("8", "组织机构代码");
    cardTypeMap.put("9", "营业执照");
    cardTypeMap.put("a", "法人代码证");
    cardTypeMap.put("A", "警官证");
    cardTypeMap.put("B", "解放军士兵证");
    cardTypeMap.put("C", "回乡证");
    cardTypeMap.put("D", "外国护照");
    cardTypeMap.put("E", "港澳台居民身份证");
    cardTypeMap.put("F", "台湾通行证及其他有效旅行证");
    cardTypeMap.put("G", "海外客户编号");
    cardTypeMap.put("H", "解放军文职干部证");
    cardTypeMap.put("I", "武警文职干部证");
    cardTypeMap.put("J", "武警士兵证");
    cardTypeMap.put("X", "其他有效证件");
    cardTypeMap.put("Z", "重号身份证");
    cardTypeMap.put("a", "法人代码证");
  }
}
