package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.bank.mgr.model.integrated.MFirm;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class Quanyi extends StandardModel
{
  private static final long serialVersionUID = 7012721963297425934L;

  @ClassDiscription(name="交易权益表ID", description="")
  private Long id;

  @ClassDiscription(name="交易商", description="")
  private MFirm firm;

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "id", this.id);
  }
}