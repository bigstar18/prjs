package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class MaketMoney extends StandardModel
{
  private static final long serialVersionUID = -753119023534352831L;

  @ClassDiscription(name="市场自有资金利息ID", description="")
  private Long id;

  @ClassDiscription(name="银行", description="")
  private Bank bank;

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "id", this.id);
  }
}