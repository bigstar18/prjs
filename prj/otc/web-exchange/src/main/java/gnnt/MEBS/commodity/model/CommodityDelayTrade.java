package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;

public class CommodityDelayTrade
  extends SpecialSet
{
  private String commodityId;
  private String f_FirmId;
  private String commodityName;
  private Integer delayTradeType;
  private Integer delayTradeTime;
  private String isslipPoint;
  
  public CommodityDelayTrade() {}
  
  public CommodityDelayTrade(String commodityId, String commodityName, String firmId, Integer delayTradeType, Integer delaytradeTime, String isslipPoint)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.f_FirmId = firmId;
    this.delayTradeType = delayTradeType;
    this.delayTradeTime = delaytradeTime;
    this.isslipPoint = isslipPoint;
  }
  
  @ClassDiscription(name="商品代码:")
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  @ClassDiscription(name="交易商类型:", isStatus=true, key=true, keyWord=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Customer", value="客户默认"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Member", value="会员默认")})
  public String getF_FirmId()
  {
    return this.f_FirmId;
  }
  
  public void setF_FirmId(String fFirmId)
  {
    this.f_FirmId = fFirmId;
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
  
  @ClassDiscription(name="延期成交类型:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="都不延迟"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="仅开仓时延迟"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="仅平仓时延迟"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="3", value="双边建平")})
  public Integer getDelayTradeType()
  {
    return this.delayTradeType;
  }
  
  public void setDelayTradeType(Integer delayTradeType)
  {
    this.delayTradeType = delayTradeType;
  }
  
  @ClassDiscription(name="延时成交时间:")
  public Integer getDelayTradeTime()
  {
    return this.delayTradeTime;
  }
  
  public void setDelayTradeTime(Integer delayTradeTime)
  {
    this.delayTradeTime = delayTradeTime;
  }
  
  @ClassDiscription(name="是否滑点成交:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="N", value="否"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Y", value="是"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="T", value="不能成交")})
  public String getIsslipPoint()
  {
    return this.isslipPoint;
  }
  
  public void setIsslipPoint(String isslipPoint)
  {
    this.isslipPoint = isslipPoint;
  }
  
  public String getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
