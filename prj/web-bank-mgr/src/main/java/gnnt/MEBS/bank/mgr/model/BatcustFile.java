package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class BatcustFile extends StandardModel
{
  private static final long serialVersionUID = 6288905827482742696L;

  @ClassDiscription(name="对账不平记录ID", description="")
  private String custacctID;

  @ClassDiscription(name="对账不平记录名称", description="")
  private String custName;

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "custacctID", this.custacctID);
  }
}