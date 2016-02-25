package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.dao.CmdtySortDAO;
import gnnt.MEBS.timebargain.manage.model.CmdtySort;
import java.util.List;

public abstract interface CmdtySortManager
{
  public abstract void setCmdtySortDAO(CmdtySortDAO paramCmdtySortDAO);
  
  public abstract CmdtySort getCmdtySortById(Long paramLong);
  
  public abstract List getCmdtySorts(CmdtySort paramCmdtySort);
  
  public abstract void insertCmdtySort(CmdtySort paramCmdtySort);
  
  public abstract void updateCmdtySort(CmdtySort paramCmdtySort);
  
  public abstract void deleteCmdtySortById(Long paramLong);
  
  public abstract CmdtySort getGroupSortHoldById(Long paramLong1, Long paramLong2);
  
  public abstract List getGroupSortHolds(CmdtySort paramCmdtySort);
  
  public abstract void insertGroupSortHold(CmdtySort paramCmdtySort);
  
  public abstract void updateGroupSortHold(CmdtySort paramCmdtySort);
  
  public abstract void deleteGroupSortHoldById(Long paramLong1, Long paramLong2);
  
  public abstract CmdtySort getCustomerSortHoldById(String paramString, Long paramLong);
  
  public abstract List getCustomerSortHolds(CmdtySort paramCmdtySort);
  
  public abstract void insertCustomerSortHold(CmdtySort paramCmdtySort);
  
  public abstract void updateCustomerSortHold(CmdtySort paramCmdtySort);
  
  public abstract void deleteCustomerSortHoldById(String paramString, Long paramLong);
}
