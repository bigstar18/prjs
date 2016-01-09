package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class FirmTradeStatus extends StandardModel
{
  private static final long serialVersionUID = -6992011276847033328L;

  @ClassDiscription(name="银行", description="")
  private Bank bank;

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}