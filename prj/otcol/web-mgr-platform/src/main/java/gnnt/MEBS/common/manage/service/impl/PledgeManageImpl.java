package gnnt.MEBS.common.manage.service.impl;

import gnnt.MEBS.common.manage.dao.ApplyDAO;
import gnnt.MEBS.common.manage.dao.PledgeDAO;
import gnnt.MEBS.common.manage.model.Apply;
import gnnt.MEBS.common.manage.model.Apply_T_PledgeMoney;
import gnnt.MEBS.common.manage.service.PledgeManage;
import java.io.PrintStream;

public class PledgeManageImpl
  implements PledgeManage
{
  private ApplyDAO applyDao;
  private PledgeDAO pledgeDAO;
  
  public void setApplyDAO(ApplyDAO paramApplyDAO)
  {
    this.applyDao = paramApplyDAO;
  }
  
  public void setPledgeDAO(PledgeDAO paramPledgeDAO)
  {
    this.pledgeDAO = paramPledgeDAO;
  }
  
  public void updateApply(Apply paramApply)
  {
    this.applyDao.updateApply(paramApply);
  }
  
  public void updatePledge(Apply_T_PledgeMoney paramApply_T_PledgeMoney)
  {
    this.pledgeDAO.updatePledge(paramApply_T_PledgeMoney);
  }
  
  public int updatePledgeCheck(Apply_T_PledgeMoney paramApply_T_PledgeMoney)
  {
    int i = -1;
    int j = paramApply_T_PledgeMoney.getStatus();
    String str1 = paramApply_T_PledgeMoney.getApprover();
    String str2 = paramApply_T_PledgeMoney.getNote();
    if ((paramApply_T_PledgeMoney != null) && (paramApply_T_PledgeMoney.getId() != 0L))
    {
      paramApply_T_PledgeMoney = (Apply_T_PledgeMoney)this.applyDao.getApplyById(paramApply_T_PledgeMoney, true);
      System.out.println("Approver: " + paramApply_T_PledgeMoney.getApprover());
      int k = paramApply_T_PledgeMoney.getStatus();
      if ((k != 2) && (k != 3))
      {
        paramApply_T_PledgeMoney.setApprover(str1);
        paramApply_T_PledgeMoney.setNote(str2);
        if (j == 2)
        {
          if (paramApply_T_PledgeMoney.getType() == 2) {
            i = this.pledgeDAO.deletePledgeChe(paramApply_T_PledgeMoney);
          }
          if (paramApply_T_PledgeMoney.getType() == 1)
          {
            i = this.pledgeDAO.CheckFirmAndBillFund(paramApply_T_PledgeMoney);
            if (i == 0) {
              this.pledgeDAO.updatePledge(paramApply_T_PledgeMoney);
            }
          }
        }
        else
        {
          i = 0;
        }
        if (i == 0)
        {
          paramApply_T_PledgeMoney.setStatus(j);
          this.applyDao.updateApply(paramApply_T_PledgeMoney);
        }
      }
    }
    return i;
  }
  
  public Apply_T_PledgeMoney getApply_T_PledgeMoneyByBillID(Apply_T_PledgeMoney paramApply_T_PledgeMoney)
  {
    return this.pledgeDAO.getApply_T_PledgeMoneyByBillID(paramApply_T_PledgeMoney);
  }
}
