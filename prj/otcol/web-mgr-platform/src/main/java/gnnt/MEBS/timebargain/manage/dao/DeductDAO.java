package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.model.Deduct;
import java.util.List;

public abstract interface DeductDAO
  extends DAO
{
  public abstract List getDeductPositionList(Deduct paramDeduct);
  
  public abstract Deduct getDeductPosition(Deduct paramDeduct);
  
  public abstract Deduct updateDeductPosition(Deduct paramDeduct);
  
  public abstract List getDeductDetailList(Deduct paramDeduct);
  
  public abstract List getDeductKeepFirmList(Deduct paramDeduct);
  
  public abstract Deduct insertDeductPosition(Deduct paramDeduct);
  
  public abstract Deduct getDeductKeepFirm(Deduct paramDeduct);
  
  public abstract void updateDeductKeepFirm(Deduct paramDeduct);
  
  public abstract void insertDeductKeepFirm(Deduct paramDeduct);
  
  public abstract void deleteKeepFirm(Deduct paramDeduct);
  
  public abstract Long getDeductQty(Deduct paramDeduct);
  
  public abstract int operateDeduct(Deduct paramDeduct);
  
  public abstract int operateDeductData(Deduct paramDeduct);
  
  public abstract List getDeductKeepFirmListQuery(Deduct paramDeduct);
  
  public abstract List getDeductDetailListQuery(Deduct paramDeduct);
  
  public abstract List getDeductDetailSum(Deduct paramDeduct);
}
