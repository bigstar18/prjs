package gnnt.MEBS.timebargain.mgr.model.applyGage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class CustomerA extends StandardModel
{
  private static final long serialVersionUID = -1L;

  @ClassDiscription(name="交易客户代码", description="")
  private String customerId;

  @ClassDiscription(name="交易商代码", description="")
  private String firmId;

  @ClassDiscription(name="交易客户名称", description="")
  private String name;

  public String getCustomerId()
  {
    return this.customerId;
  }

  public void setCustomerId(String customerId)
  {
    this.customerId = customerId;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "customerId", this.customerId);
  }
}