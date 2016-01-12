package gnnt.MEBS.timebargain.mgr.model.tradeparams;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class CommodityTradeProp
  implements Comparable
{

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  @ClassDiscription(name="商品model", description="")
  private Commodity commodity;

  @ClassDiscription(name="交易时间", description="")
  private TradeTime tradeTime;

  public Commodity getCommodity()
  {
    return this.commodity;
  }

  public void setCommodity(Commodity commodity) {
    this.commodity = commodity;
  }

  public TradeTime getTradeTime() {
    return this.tradeTime;
  }

  public void setTradeTime(TradeTime tradeTime) {
    this.tradeTime = tradeTime;
  }

  public Date getModifyTime() {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public int compareTo(Object o) {
    if (!(o instanceof CommodityTradeProp)) {
      return -1;
    }
    CommodityTradeProp target = (CommodityTradeProp)o;
    return getTradeTime().getSectionID().compareTo(target.getTradeTime().getSectionID());
  }

  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (
      (this.commodity.getCommodityID() == null) || (this.tradeTime.getSectionID() == null) ? 0 : this.commodity.getCommodityID().hashCode() + this.tradeTime.getSectionID().hashCode());
    return result;
  }

  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CommodityTradeProp other = (CommodityTradeProp)obj;
    if ((this.commodity.getCommodityID() == null) || (this.tradeTime.getSectionID() == null)) {
      return false;
    }
    if ((other.getCommodity().getCommodityID() == null) || (other.getTradeTime().getSectionID() == null)) {
      return false;
    }
    if ((this.commodity.getCommodityID().equals(other.getCommodity().getCommodityID())) && (this.tradeTime.getSectionID() == other.getTradeTime().getSectionID())) {
      return true;
    }
    return false;
  }
}