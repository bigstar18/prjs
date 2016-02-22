package gnnt.MEBS.timebargain.server.dao;

import gnnt.MEBS.timebargain.server.model.Fee_RT;
import gnnt.MEBS.timebargain.server.model.Firm;
import gnnt.MEBS.timebargain.server.model.HoldQty_RT;
import gnnt.MEBS.timebargain.server.model.Margin_RT;
import gnnt.MEBS.timebargain.server.model.Member;
import gnnt.MEBS.timebargain.server.model.Quotation_RT;
import gnnt.MEBS.timebargain.server.model.QuotePoint_RT;
import gnnt.MEBS.timebargain.server.model.Trader;
import java.util.Map;

public abstract interface FirmDAO
  extends DAO
{
  public abstract Map<String, Member> getMemberMap();
  
  public abstract Member getOneMember(String paramString);
  
  public abstract Map<String, Margin_RT> getMarginMap(String paramString);
  
  public abstract Map<String, Fee_RT> getFeeMap(String paramString);
  
  public abstract Map<String, HoldQty_RT> getHoldQtyMap(String paramString);
  
  public abstract Map<String, QuotePoint_RT> getQuotePointMap(String paramString);
  
  public abstract Map<String, Quotation_RT> getQuotationMap(String paramString);
  
  public abstract Trader getOneTrader(String paramString);
  
  public abstract Firm getOneFirm(String paramString);
  
  public abstract int changePhonePassowrd(String paramString1, String paramString2, String paramString3);
  
  public abstract int changePassowrd(String paramString1, String paramString2, String paramString3);
  
  public abstract long checkDelegateInfo(String paramString1, String paramString2, String paramString3);
  
  public abstract long checkDelegateInfo(String paramString1, String paramString2);
  
  public abstract void updateTraderOnLineInfo(String paramString1, String paramString2, int paramInt);
  
  public abstract void updateTraderDownLine();
  
  public abstract void addGlobalLog(String paramString1, String paramString2, int paramInt1, String paramString3, int paramInt2);
}
