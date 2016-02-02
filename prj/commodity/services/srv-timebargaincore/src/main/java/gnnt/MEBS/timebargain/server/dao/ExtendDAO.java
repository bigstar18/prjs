package gnnt.MEBS.timebargain.server.dao;

import gnnt.MEBS.timebargain.server.model.ApplyBill;
import gnnt.MEBS.timebargain.server.model.Settle;

public abstract interface ExtendDAO
  extends DAO
{
  public abstract int gage(ApplyBill paramApplyBill);
  
  public abstract int gageCancel(ApplyBill paramApplyBill);
  
  public abstract int aheadSettle(ApplyBill paramApplyBill);
  
  public abstract int conferClose(Settle paramSettle);
  
  public abstract int waitSettle(ApplyBill paramApplyBill);
  
  public abstract int waitSettleCancel(ApplyBill paramApplyBill);
}
