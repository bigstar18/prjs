package gnnt.MEBS.timebargain.server;

public class Constants
{
  public static final int SYSTEM_STATUS_INIT_SUCCESS = 0;
  public static final int SYSTEM_STATUS_CLOSE = 1;
  public static final int SYSTEM_STATUS_CALCING = 2;
  public static final int SYSTEM_STATUS_CALCOVER = 3;
  public static final int SYSTEM_STATUS_PAUSE = 4;
  public static final int SYSTEM_STATUS_SECTION_OPEN = 5;
  public static final int SYSTEM_STATUS_SECTION_PAUSE = 6;
  public static final int SYSTEM_STATUS_SECTION_CLOSE = 7;
  public static final int SYSTEM_STATUS_BID_OPEN = 8;
  public static final int SYSTEM_STATUS_BID_CLOSE = 9;
  public static final int SYSTEM_STATUS_TRADECALCOVER = 10;
  public static final String[] SYSTEM_STATUS = { "初始化完成", "闭市状态", "结算中", "资金结算完成", "暂停交易", "交易中", "节间休息", "交易结束", "集合竞价交易中", "集合竞价交易结束", "交易结算完成" };
  public static final String ENC_ALGORITHM = "MD5";
}
