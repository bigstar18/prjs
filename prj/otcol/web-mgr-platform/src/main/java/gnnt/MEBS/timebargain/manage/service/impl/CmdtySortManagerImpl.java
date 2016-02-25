package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.CmdtySortDAO;
import gnnt.MEBS.timebargain.manage.model.CmdtySort;
import gnnt.MEBS.timebargain.manage.service.CmdtySortManager;
import java.util.List;

public class CmdtySortManagerImpl
  extends BaseManager
  implements CmdtySortManager
{
  private CmdtySortDAO dao;
  
  public void setCmdtySortDAO(CmdtySortDAO paramCmdtySortDAO)
  {
    this.dao = paramCmdtySortDAO;
  }
  
  public CmdtySort getCmdtySortById(Long paramLong)
  {
    return this.dao.getCmdtySortById(paramLong);
  }
  
  public List getCmdtySorts(CmdtySort paramCmdtySort)
  {
    return this.dao.getCmdtySorts(paramCmdtySort);
  }
  
  public void insertCmdtySort(CmdtySort paramCmdtySort)
  {
    this.dao.insertCmdtySort(paramCmdtySort);
  }
  
  public void updateCmdtySort(CmdtySort paramCmdtySort)
  {
    this.dao.updateCmdtySort(paramCmdtySort);
  }
  
  public void deleteCmdtySortById(Long paramLong)
  {
    this.dao.deleteCmdtySortById(paramLong);
  }
  
  public CmdtySort getGroupSortHoldById(Long paramLong1, Long paramLong2)
  {
    return this.dao.getGroupSortHoldById(paramLong1, paramLong2);
  }
  
  public List getGroupSortHolds(CmdtySort paramCmdtySort)
  {
    return this.dao.getGroupSortHolds(paramCmdtySort);
  }
  
  public void insertGroupSortHold(CmdtySort paramCmdtySort)
  {
    this.dao.insertGroupSortHold(paramCmdtySort);
  }
  
  public void updateGroupSortHold(CmdtySort paramCmdtySort)
  {
    this.dao.updateGroupSortHold(paramCmdtySort);
  }
  
  public void deleteGroupSortHoldById(Long paramLong1, Long paramLong2)
  {
    this.dao.deleteGroupSortHoldById(paramLong1, paramLong2);
  }
  
  public CmdtySort getCustomerSortHoldById(String paramString, Long paramLong)
  {
    return this.dao.getCustomerSortHoldById(paramString, paramLong);
  }
  
  public List getCustomerSortHolds(CmdtySort paramCmdtySort)
  {
    return this.dao.getCustomerSortHolds(paramCmdtySort);
  }
  
  public void insertCustomerSortHold(CmdtySort paramCmdtySort)
  {
    this.dao.insertCustomerSortHold(paramCmdtySort);
  }
  
  public void updateCustomerSortHold(CmdtySort paramCmdtySort)
  {
    this.dao.updateCustomerSortHold(paramCmdtySort);
  }
  
  public void deleteCustomerSortHoldById(String paramString, Long paramLong)
  {
    this.dao.deleteCustomerSortHoldById(paramString, paramLong);
  }
}
