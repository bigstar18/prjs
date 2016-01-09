package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class QSResult extends StandardModel
{
  private static final long serialVersionUID = 5563274147649249026L;

  @ClassDiscription(name="对账不平纪录信息ID", description="")
  private Long resultID;

  @ClassDiscription(name="银行", description="")
  private Bank bank;

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "resultID", this.resultID);
  }
}