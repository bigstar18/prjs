package gnnt.MEBS.common.manage.service.impl;

import gnnt.MEBS.common.manage.dao.ApplyDAO;
import gnnt.MEBS.common.manage.dao.VirtualFundDAO;
import gnnt.MEBS.common.manage.model.Apply;
import gnnt.MEBS.common.manage.model.Apply_T_VirtualMoney;
import gnnt.MEBS.common.manage.service.VirtualFundManage;

public class VirtualFundManageImpl
  implements VirtualFundManage
{
  private ApplyDAO applyDao;
  private VirtualFundDAO virtualFundDAO;
  
  public void setApplyDAO(ApplyDAO paramApplyDAO)
  {
    this.applyDao = paramApplyDAO;
  }
  
  public void setVirtualFundDAO(VirtualFundDAO paramVirtualFundDAO)
  {
    this.virtualFundDAO = paramVirtualFundDAO;
  }
  
  public void updateApply(Apply paramApply)
  {
    this.applyDao.updateApply(paramApply);
  }
  
  public void updateVirtualFund(Apply_T_VirtualMoney paramApply_T_VirtualMoney)
  {
    this.virtualFundDAO.updateVirtualFund(paramApply_T_VirtualMoney);
  }
  
  public int CheckFirmAndVirtualFund(Apply_T_VirtualMoney paramApply_T_VirtualMoney)
  {
    return this.virtualFundDAO.CheckFirmAndVirtualFund(paramApply_T_VirtualMoney);
  }
  
  public int updateVirtual(Apply_T_VirtualMoney paramApply_T_VirtualMoney)
  {
    int i = -1;
    int j = paramApply_T_VirtualMoney.getStatus();
    String str1 = paramApply_T_VirtualMoney.getApprover();
    String str2 = paramApply_T_VirtualMoney.getNote();
    if ((paramApply_T_VirtualMoney != null) && (paramApply_T_VirtualMoney.getId() != 0L))
    {
      paramApply_T_VirtualMoney = (Apply_T_VirtualMoney)this.applyDao.getApplyById(paramApply_T_VirtualMoney, true);
      int k = paramApply_T_VirtualMoney.getStatus();
      if ((k != 2) && (k != 3))
      {
        if (j == 2) {
          i = this.virtualFundDAO.CheckFirmAndVirtualFund(paramApply_T_VirtualMoney);
        } else {
          i = 0;
        }
        if (i == 0)
        {
          paramApply_T_VirtualMoney.setStatus(j);
          paramApply_T_VirtualMoney.setApprover(str1);
          paramApply_T_VirtualMoney.setNote(str2);
          if (j == 2) {
            this.virtualFundDAO.updateVirtualFund(paramApply_T_VirtualMoney);
          }
          this.applyDao.updateApply(paramApply_T_VirtualMoney);
        }
      }
    }
    return i;
  }
}
