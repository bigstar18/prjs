package gnnt.MEBS.timebargain.mgr.model.applyGage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class ValidGageBill extends StandardModel
{
  private static final long serialVersionUID = -1L;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="交易商代码", description="")
  private String firmId;

  @ClassDiscription(name="数量", description="")
  private Long quantity;

  @ClassDiscription(name="冻结数量", description="")
  private Long frozenQty;

  public String getCommodityId()
  {
    return this.commodityId;
  }

  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }

  public Long getQuantity()
  {
    return this.quantity;
  }

  public void setQuantity(Long quantity)
  {
    this.quantity = quantity;
  }

  public Long getFrozenQty()
  {
    return this.frozenQty;
  }

  public void setFrozenQty(Long frozenQty)
  {
    this.frozenQty = frozenQty;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}