package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class ProperBalance extends StandardModel
{
  private static final long serialVersionUID = -3818214868478323337L;

  @ClassDiscription(name="总分平衡表的银行信息", description="")
  private Bank bank;

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}