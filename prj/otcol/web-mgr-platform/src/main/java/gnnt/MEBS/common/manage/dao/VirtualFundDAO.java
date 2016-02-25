package gnnt.MEBS.common.manage.dao;

import gnnt.MEBS.common.manage.model.Apply_T_VirtualMoney;

public abstract interface VirtualFundDAO
{
  public abstract void updateVirtualFund(Apply_T_VirtualMoney paramApply_T_VirtualMoney);
  
  public abstract int CheckFirmAndVirtualFund(Apply_T_VirtualMoney paramApply_T_VirtualMoney);
}
