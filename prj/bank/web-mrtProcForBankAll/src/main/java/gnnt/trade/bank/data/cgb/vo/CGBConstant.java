package gnnt.trade.bank.data.cgb.vo;

import gnnt.trade.bank.util.Tool;

public class CGBConstant
{
  public static String bankID = Tool.getConfig("CGBbankID");
  public static String MarketCode = Tool.getConfig("CGBmarketID");
  
  public CGBConstant(String bankID, String MarketCode)
  {
    bankID = bankID;
    MarketCode = MarketCode;
  }
}
