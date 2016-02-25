package gnnt.MEBS.delivery.action;

import gnnt.MEBS.delivery.util.SysData;

public class Condition
{
  public static String PATH = "/delivery/";
  public static String DELIVERYPATH = PATH + "martModule/";
  public static String SETTLEPATH = DELIVERYPATH + "settle/";
  public static int PAGESIZE = 20;
  public static int popedom = 0;
  public static String POSTFIX = SysData.getConfig("postfix");
  public static final String MODULE = "0";
}
