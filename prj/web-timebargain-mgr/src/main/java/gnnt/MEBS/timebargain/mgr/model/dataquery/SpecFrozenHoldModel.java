package gnnt.MEBS.timebargain.mgr.model.dataquery;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class SpecFrozenHoldModel extends StandardModel
{
  private static final long serialVersionUID = -6441187668883360984L;

  @ClassDiscription(name="主键", description="")
  private Long id;

  @ClassDiscription(name="主键", description="")
  private Long holdNo;

  @ClassDiscription(name="委托编号", description="")
  private Long orderNo;

  @ClassDiscription(name="委托编号", description="")
  private Long frozenQty;
  private OrdersModel ordersModel;

  public OrdersModel getOrdersModel()
  {
    return this.ordersModel;
  }

  public void setOrdersModel(OrdersModel ordersModel) {
    this.ordersModel = ordersModel;
  }

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public Long getHoldNo()
  {
    return this.holdNo;
  }

  public void setHoldNo(Long holdNo)
  {
    this.holdNo = holdNo;
  }

  public Long getFrozenQty()
  {
    return this.frozenQty;
  }

  public void setFrozenQty(Long frozenQty)
  {
    this.frozenQty = frozenQty;
  }

  public Long getOrderNo()
  {
    return this.orderNo;
  }

  public void setOrderNo(Long orderNo)
  {
    this.orderNo = orderNo;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "id", this.id);
  }
}