package gnnt.trade.bank.data.hz.vo;

import gnnt.trade.bank.util.Tool;

public class HZConstant
{
  public static String bankID = Tool.getConfig("HZbankID");
  public static String MarketCode = Tool.getConfig("HZmarketID");
  
  public HZConstant(String bankID, String MarketCode)
  {
    bankID = bankID;
    MarketCode = MarketCode;
  }
}
