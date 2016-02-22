package gnnt.trade.bank.data.boc.vo;

import gnnt.trade.bank.util.Tool;

public class BOCConstant
{
  public static String bankID = Tool.getConfig("BOCbankID");
  public static String MarketCode = Tool.getConfig("BOCmarketID");
  
  public BOCConstant(String bankID, String MarketCode)
  {
    bankID = bankID;
    MarketCode = MarketCode;
  }
}
