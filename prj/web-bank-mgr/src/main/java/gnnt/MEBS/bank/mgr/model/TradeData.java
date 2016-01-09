package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.bank.mgr.model.integrated.MFirm;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class TradeData extends StandardModel
{
  private static final long serialVersionUID = -2779441135412532489L;

  @ClassDiscription(name="交易数据表ID", description="")
  private String actionID;

  @ClassDiscription(name="交易商", description="")
  private MFirm firm;

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "actionID", this.actionID);
  }
}