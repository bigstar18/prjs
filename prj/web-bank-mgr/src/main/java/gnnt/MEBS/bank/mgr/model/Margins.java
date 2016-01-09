package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class Margins extends StandardModel
{
  private static final long serialVersionUID = 7688124867496382479L;

  @ClassDiscription(name="交易商资金冻结解冻表id", description="")
  private String serial_id;

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}