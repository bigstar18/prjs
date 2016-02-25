package gnnt.MEBS.common.manage.service;

import gnnt.MEBS.common.manage.dao.ApplyDAO;
import gnnt.MEBS.common.manage.dao.PledgeDAO;
import gnnt.MEBS.common.manage.model.Apply;
import gnnt.MEBS.common.manage.model.Apply_T_PledgeMoney;

public abstract interface PledgeManage
{
  public abstract void setApplyDAO(ApplyDAO paramApplyDAO);
  
  public abstract void setPledgeDAO(PledgeDAO paramPledgeDAO);
  
  public abstract void updateApply(Apply paramApply);
  
  public abstract void updatePledge(Apply_T_PledgeMoney paramApply_T_PledgeMoney);
  
  public abstract int updatePledgeCheck(Apply_T_PledgeMoney paramApply_T_PledgeMoney);
  
  public abstract Apply_T_PledgeMoney getApply_T_PledgeMoneyByBillID(Apply_T_PledgeMoney paramApply_T_PledgeMoney);
}
