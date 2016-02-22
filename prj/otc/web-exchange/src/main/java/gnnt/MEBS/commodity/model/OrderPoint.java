package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;

public class OrderPoint
  extends Clone
{
  private String commodityId;
  private String commodityName;
  private String memberFirmId;
  private Integer stopLossPoint;
  private Integer stopProfitPoint;
  private Integer l_Open_Point;
  private Integer m_OrderPoint;
  private Integer min_M_OrderPoint;
  private Integer max_M_OrderPoint;
  
  public OrderPoint(String commodityId, String commodityName, String memberFirmId, Integer stopLossPoint, Integer stopProfitPoint, Integer open_Point, Integer orderPoint, Integer min_M_OrderPoint, Integer max_M_OrderPoint)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.memberFirmId = memberFirmId;
    this.stopLossPoint = stopLossPoint;
    this.stopProfitPoint = stopProfitPoint;
    this.l_Open_Point = open_Point;
    this.m_OrderPoint = orderPoint;
    this.min_M_OrderPoint = min_M_OrderPoint;
    this.max_M_OrderPoint = max_M_OrderPoint;
  }
  
  public OrderPoint() {}
  
  @ClassDiscription(name="商品名称:", key=true, keyWord=true)
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  @ClassDiscription(name="名称:", key=true, keyWord=true, isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Customer", value="客户默认"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Member", value="会员默认"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_S_Member", value="特别会员默认")})
  public String getMemberFirmId()
  {
    return this.memberFirmId;
  }
  
  public void setMemberFirmId(String memberFirmId)
  {
    this.memberFirmId = memberFirmId;
  }
  
  @ClassDiscription(name="止损下单点差:")
  public Integer getStopLossPoint()
  {
    return this.stopLossPoint;
  }
  
  public void setStopLossPoint(Integer stopLossPoint)
  {
    this.stopLossPoint = stopLossPoint;
  }
  
  @ClassDiscription(name="止盈下单点差:")
  public Integer getStopProfitPoint()
  {
    return this.stopProfitPoint;
  }
  
  public void setStopProfitPoint(Integer stopProfitPoint)
  {
    this.stopProfitPoint = stopProfitPoint;
  }
  
  @ClassDiscription(name="限价建仓点差:")
  public Integer getL_Open_Point()
  {
    return this.l_Open_Point;
  }
  
  public void setL_Open_Point(Integer open_Point)
  {
    this.l_Open_Point = open_Point;
  }
  
  @ClassDiscription(name="默认市价点差:")
  public Integer getM_OrderPoint()
  {
    return this.m_OrderPoint;
  }
  
  public void setM_OrderPoint(Integer orderPoint)
  {
    this.m_OrderPoint = orderPoint;
  }
  
  @ClassDiscription(name="市价点差最小值:")
  public Integer getMin_M_OrderPoint()
  {
    return this.min_M_OrderPoint;
  }
  
  public void setMin_M_OrderPoint(Integer min_M_OrderPoint)
  {
    this.min_M_OrderPoint = min_M_OrderPoint;
  }
  
  @ClassDiscription(name="市价点差最大值:")
  public Integer getMax_M_OrderPoint()
  {
    return this.max_M_OrderPoint;
  }
  
  public void setMax_M_OrderPoint(Integer max_M_OrderPoint)
  {
    this.max_M_OrderPoint = max_M_OrderPoint;
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
  
  public String getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
