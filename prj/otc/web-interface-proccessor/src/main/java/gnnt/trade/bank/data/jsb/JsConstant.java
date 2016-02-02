package gnnt.trade.bank.data.jsb;

import gnnt.trade.bank.util.Tool;

public class JsConstant
{
  public static String bankID = Tool.getConfig("JsbankID");
  public static String marketID = Tool.getConfig("JsmarketID");
  public static final boolean nativeFile = false;
  
  public JsConstant(String bankID, String marketID)
  {
    bankID = bankID;
    marketID = marketID;
  }
}
