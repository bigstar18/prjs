package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class HXQS extends StandardModel
{
  private static final long serialVersionUID = -334606766704989618L;

  @ClassDiscription(name="银行", description="")
  private Bank bank;

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}