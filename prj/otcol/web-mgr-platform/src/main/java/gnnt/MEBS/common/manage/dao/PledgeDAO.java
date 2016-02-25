package gnnt.MEBS.common.manage.dao;

import gnnt.MEBS.common.manage.model.Apply_T_PledgeMoney;

public abstract interface PledgeDAO
{
  public abstract void updatePledge(Apply_T_PledgeMoney paramApply_T_PledgeMoney);
  
  public abstract int CheckFirmAndBillFund(Apply_T_PledgeMoney paramApply_T_PledgeMoney);
  
  public abstract int deletePledgeChe(Apply_T_PledgeMoney paramApply_T_PledgeMoney);
  
  public abstract Apply_T_PledgeMoney getApply_T_PledgeMoneyByBillID(Apply_T_PledgeMoney paramApply_T_PledgeMoney);
}
