package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.DeductDAO;
import gnnt.MEBS.timebargain.manage.model.Deduct;
import gnnt.MEBS.timebargain.manage.service.DeductManager;
import java.util.List;

public class DeductManagerImpl
  extends BaseManager
  implements DeductManager
{
  private DeductDAO dao;
  
  public void setDeductDAO(DeductDAO paramDeductDAO)
  {
    this.dao = paramDeductDAO;
  }
  
  public List getDeductPositionList(Deduct paramDeduct)
  {
    return this.dao.getDeductPositionList(paramDeduct);
  }
  
  public Deduct getDeductPosition(Deduct paramDeduct)
  {
    return this.dao.getDeductPosition(paramDeduct);
  }
  
  public Deduct updateDeductPosition(Deduct paramDeduct)
  {
    return this.dao.updateDeductPosition(paramDeduct);
  }
  
  public Deduct insertDeductPosition(Deduct paramDeduct)
  {
    return this.dao.insertDeductPosition(paramDeduct);
  }
  
  public List getDeductDetailList(Deduct paramDeduct)
  {
    return this.dao.getDeductDetailList(paramDeduct);
  }
  
  public List getDeductKeepFirmList(Deduct paramDeduct)
  {
    return this.dao.getDeductKeepFirmList(paramDeduct);
  }
  
  public Deduct getDeductKeepFirm(Deduct paramDeduct)
  {
    return this.dao.getDeductKeepFirm(paramDeduct);
  }
  
  public void updateDeductKeepFirm(Deduct paramDeduct)
  {
    this.dao.updateDeductKeepFirm(paramDeduct);
  }
  
  public void insertDeductKeepFirm(Deduct paramDeduct)
  {
    this.dao.insertDeductKeepFirm(paramDeduct);
  }
  
  public void deleteKeepFirm(Deduct paramDeduct)
  {
    this.dao.deleteKeepFirm(paramDeduct);
  }
  
  public Long getDeductQty(Deduct paramDeduct)
  {
    return this.dao.getDeductQty(paramDeduct);
  }
  
  public int operateDeduct(Deduct paramDeduct)
  {
    return this.dao.operateDeduct(paramDeduct);
  }
  
  public int operateDeductData(Deduct paramDeduct)
  {
    return this.dao.operateDeductData(paramDeduct);
  }
  
  public List getDeductKeepFirmListQuery(Deduct paramDeduct)
  {
    return this.dao.getDeductKeepFirmListQuery(paramDeduct);
  }
  
  public List getDeductDetailListQuery(Deduct paramDeduct)
  {
    return this.dao.getDeductDetailListQuery(paramDeduct);
  }
  
  public List getDeductDetailSum(Deduct paramDeduct)
  {
    return this.dao.getDeductDetailSum(paramDeduct);
  }
}
