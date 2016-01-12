package gnnt.MEBS.timebargain.mgr.model.deduct;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class DeductDetail extends StandardModel
{
  private static final long serialVersionUID = -1L;

  @ClassDiscription(name="强减ID", description="")
  private Long deductId;

  @ClassDiscription(name="交易客户代码", description="")
  private String customerId;

  @ClassDiscription(name="盈亏方标志", description="W：盈利方（Winner）   L：亏损方（Loser）")
  private String wL;

  @ClassDiscription(name="买订货量", description="")
  private Long buyQty;

  @ClassDiscription(name="买订货量", description="")
  private Long sellQty;

  @ClassDiscription(name=" 买保留数量", description="")
  private Long buyKeepQty;

  @ClassDiscription(name="卖保留数量", description="")
  private Long sellKeepQty;

  @ClassDiscription(name="净订货量", description="")
  private Long pureHoldQty;

  @ClassDiscription(name="盈亏合计", description="")
  private Double pL;

  @ClassDiscription(name="盈亏合计", description="")
  private Double pL_Ratio;

  @ClassDiscription(name="盈亏合计", description="")
  private Long counteractQty;

  @ClassDiscription(name="盈亏合计", description="")
  private Long orderQty;

  @ClassDiscription(name=" 可强减数量", description="")
  private Long deductableQty;

  @ClassDiscription(name=" 可强减数量", description=" 按比例，所以可以有小数。")
  private Double estimateQty;

  @ClassDiscription(name="应强减数量", description="")
  private Long deductQty;

  @ClassDiscription(name="已强减数量", description="")
  private Long deductedQty;

  @ClassDiscription(name="已对冲数量", description="")
  private Long counteractedQty;

  @ClassDiscription(name="已对冲数量", description="")
  private Deduct deduct;

  public Deduct getDeduct()
  {
    return this.deduct;
  }

  public void setDeduct(Deduct deduct)
  {
    this.deduct = deduct;
  }

  public Long getDeductId()
  {
    return this.deductId;
  }

  public void setDeductId(Long deductId)
  {
    this.deductId = deductId;
  }

  public String getCustomerId()
  {
    return this.customerId;
  }

  public void setCustomerId(String customerId)
  {
    this.customerId = customerId;
  }

  public String getwL()
  {
    return this.wL;
  }

  public void setwL(String wL)
  {
    this.wL = wL;
  }

  public Long getBuyQty()
  {
    return this.buyQty;
  }

  public void setBuyQty(Long buyQty)
  {
    this.buyQty = buyQty;
  }

  public Long getSellQty()
  {
    return this.sellQty;
  }

  public void setSellQty(Long sellQty)
  {
    this.sellQty = sellQty;
  }

  public Long getBuyKeepQty()
  {
    return this.buyKeepQty;
  }

  public void setBuyKeepQty(Long buyKeepQty)
  {
    this.buyKeepQty = buyKeepQty;
  }

  public Long getSellKeepQty()
  {
    return this.sellKeepQty;
  }

  public void setSellKeepQty(Long sellKeepQty)
  {
    this.sellKeepQty = sellKeepQty;
  }

  public Long getPureHoldQty()
  {
    return this.pureHoldQty;
  }

  public void setPureHoldQty(Long pureHoldQty)
  {
    this.pureHoldQty = pureHoldQty;
  }

  public Double getpL()
  {
    return this.pL;
  }

  public void setpL(Double pL)
  {
    this.pL = pL;
  }

  public Double getpL_Ratio()
  {
    return this.pL_Ratio;
  }

  public void setpL_Ratio(Double pLRatio)
  {
    this.pL_Ratio = pLRatio;
  }

  public Long getCounteractQty()
  {
    return this.counteractQty;
  }

  public void setCounteractQty(Long counteractQty)
  {
    this.counteractQty = counteractQty;
  }

  public Long getOrderQty()
  {
    return this.orderQty;
  }

  public void setOrderQty(Long orderQty)
  {
    this.orderQty = orderQty;
  }

  public Long getDeductableQty()
  {
    return this.deductableQty;
  }

  public void setDeductableQty(Long deductableQty)
  {
    this.deductableQty = deductableQty;
  }

  public Double getEstimateQty()
  {
    return this.estimateQty;
  }

  public void setEstimateQty(Double estimateQty)
  {
    this.estimateQty = estimateQty;
  }

  public Long getDeductQty()
  {
    return this.deductQty;
  }

  public void setDeductQty(Long deductQty)
  {
    this.deductQty = deductQty;
  }

  public Long getDeductedQty()
  {
    return this.deductedQty;
  }

  public void setDeductedQty(Long deductedQty)
  {
    this.deductedQty = deductedQty;
  }

  public Long getCounteractedQty()
  {
    return this.counteractedQty;
  }

  public void setCounteractedQty(Long counteractedQty)
  {
    this.counteractedQty = counteractedQty;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}