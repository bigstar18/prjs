package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class TradeDetailacc extends StandardModel
{
  private static final long serialVersionUID = 4396459089588002496L;

  @ClassDiscription(name="账户类交易对账明细表ID", description="")
  private String batchno;

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}