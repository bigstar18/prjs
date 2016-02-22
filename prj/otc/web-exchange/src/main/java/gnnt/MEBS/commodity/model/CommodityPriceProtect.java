package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.config.constant.NumberDigits;
import java.math.BigDecimal;

public class CommodityPriceProtect
  extends Clone
{
  private String commodityId;
  private String commodityName;
  private BigDecimal screeningPricePoint;
  private Integer timeoutInterval;
  
  public CommodityPriceProtect(String commodityId, String commodityName, BigDecimal screeningPricePoint, int timeoutInterval)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.screeningPricePoint = screeningPricePoint;
    this.timeoutInterval = Integer.valueOf(timeoutInterval);
  }
  
  public CommodityPriceProtect() {}
  
  @ClassDiscription(name="商品代码:", key=true, keyWord=true)
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  @ClassDiscription(name="商品名称:", key=true, keyWord=true)
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  @ClassDiscription(name="商品滤价点差:")
  public BigDecimal getScreeningPricePoint()
  {
    return formatDecimals(this.screeningPricePoint, NumberDigits.SCREENINGPRICEPOINT - 2);
  }
  
  public void setScreeningPricePoint(BigDecimal screeningPricePoint)
  {
    this.screeningPricePoint = screeningPricePoint;
  }
  
  @ClassDiscription(name="超时间隔:")
  public Integer getTimeoutInterval()
  {
    return this.timeoutInterval;
  }
  
  public void setTimeoutInterval(Integer timeoutInterval)
  {
    this.timeoutInterval = timeoutInterval;
  }
  
  public String getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
