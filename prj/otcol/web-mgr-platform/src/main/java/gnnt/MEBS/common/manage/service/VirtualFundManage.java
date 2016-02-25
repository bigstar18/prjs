package gnnt.MEBS.common.manage.service;

import gnnt.MEBS.common.manage.dao.ApplyDAO;
import gnnt.MEBS.common.manage.dao.VirtualFundDAO;
import gnnt.MEBS.common.manage.model.Apply;
import gnnt.MEBS.common.manage.model.Apply_T_VirtualMoney;

public abstract interface VirtualFundManage
{
  public abstract void setApplyDAO(ApplyDAO paramApplyDAO);
  
  public abstract void setVirtualFundDAO(VirtualFundDAO paramVirtualFundDAO);
  
  public abstract void updateApply(Apply paramApply);
  
  public abstract void updateVirtualFund(Apply_T_VirtualMoney paramApply_T_VirtualMoney);
  
  public abstract int CheckFirmAndVirtualFund(Apply_T_VirtualMoney paramApply_T_VirtualMoney);
  
  public abstract int updateVirtual(Apply_T_VirtualMoney paramApply_T_VirtualMoney);
}
