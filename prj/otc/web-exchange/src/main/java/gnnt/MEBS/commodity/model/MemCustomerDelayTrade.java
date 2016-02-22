package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;

public class MemCustomerDelayTrade
  extends SpecialSet
{
  private String commodityId;
  private String f_FirmId;
  private String memberName;
  private String commodityName;
  private Integer delayTradeType;
  private Integer delayTradeTime;
  private String isslipPoint;
  
  public MemCustomerDelayTrade() {}
  
  public MemCustomerDelayTrade(String commodityId, String f_FirmId, String commodityName, String memberName, Integer delayTradeType, Integer delaytradeTime, String isslipPoint)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.f_FirmId = f_FirmId;
    this.memberName = memberName;
    this.delayTradeType = delayTradeType;
    this.delayTradeTime = delaytradeTime;
    this.isslipPoint = isslipPoint;
  }
  
  @ClassDiscription(name="商品代码:", key=true, keyWord=true)
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
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
  
  @ClassDiscription(name="名称:", key=true, keyWord=true)
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  public String getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
