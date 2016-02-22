package gnnt.MEBS.timebargain.server.dao;

import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.Quotation_RT;
import java.sql.Timestamp;

public abstract interface RiskcontrolDAO
  extends DAO
{
  public abstract void setLossProfit(Long paramLong, Double paramDouble1, Double paramDouble2);
  
  public abstract void withdrawLossProfit(Long paramLong, Short paramShort);
  
  public abstract void updateQuotation(Quotation paramQuotation);
  
  public abstract void updateQuotation_RT(Quotation_RT paramQuotation_RT);
  
  public abstract void hqUpdate(String paramString, double paramDouble);
  
  public abstract Timestamp floatingComputer(String paramString, Timestamp paramTimestamp);
  
  public abstract int stopPL(String paramString);
  
  public abstract int updateRiskRateAll();
  
  public abstract int updateRiskRateHigh();
}
