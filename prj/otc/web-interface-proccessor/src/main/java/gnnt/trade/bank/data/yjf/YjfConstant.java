package gnnt.trade.bank.data.yjf;

import gnnt.trade.bank.util.Tool;

public class YjfConstant
{
  public static String bankID = Tool.getConfig("YjfbankID");
  public static String marketID = Tool.getConfig("YJFmarketID");
  public static final boolean nativeFile = false;
  
  public YjfConstant(String bankID, String marketID)
  {
    bankID = bankID;
    marketID = marketID;
  }
}
