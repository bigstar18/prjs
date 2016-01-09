package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class InterfaceLog extends StandardModel
{
  private static final long serialVersionUID = 1984409473639991737L;

  @ClassDiscription(name="连接日志ID", description="")
  private Long logID;

  @ClassDiscription(name="银行", description="")
  private Bank bank;

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "logID", this.logID);
  }
}