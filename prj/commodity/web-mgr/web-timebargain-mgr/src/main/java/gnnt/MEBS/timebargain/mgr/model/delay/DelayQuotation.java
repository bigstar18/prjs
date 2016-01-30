package gnnt.MEBS.timebargain.mgr.model.delay;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class DelayQuotation extends StandardModel
{
  private static final long serialVersionUID = 1615530487982556239L;
  private String commodityID;
  private Integer buySettleQty;
  private Integer sellSettleQty;
  private Integer buyNeutralQty;
  private Integer sellNeutralQty;
  private Date createTime;
  private Integer neutralSide;
  private Integer delayCleanHoldQty;

  public String getCommodityID()
  {
    return this.commodityID;
  }

  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }

  public Integer getBuySettleQty()
  {
    return this.buySettleQty;
  }

  public void setBuySettleQty(Integer buySettleQty)
  {
    this.buySettleQty = buySettleQty;
  }

  public Integer getSellSettleQty()
  {
    return this.sellSettleQty;
  }

  public void setSellSettleQty(Integer sellSettleQty)
  {
    this.sellSettleQty = sellSettleQty;
  }

  public Integer getBuyNeutralQty()
  {
    return this.buyNeutralQty;
  }

  public void setBuyNeutralQty(Integer buyNeutralQty)
  {
    this.buyNeutralQty = buyNeutralQty;
  }

  public Integer getSellNeutralQty()
  {
    return this.sellNeutralQty;
  }

  public void setSellNeutralQty(Integer sellNeutralQty)
  {
    this.sellNeutralQty = sellNeutralQty;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public Integer getNeutralSide()
  {
    return this.neutralSide;
  }

  public void setNeutralSide(Integer neutralSide)
  {
    this.neutralSide = neutralSide;
  }

  public Integer getDelayCleanHoldQty()
  {
    return this.delayCleanHoldQty;
  }

  public void setDelayCleanHoldQty(Integer delayCleanHoldQty)
  {
    this.delayCleanHoldQty = delayCleanHoldQty;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}