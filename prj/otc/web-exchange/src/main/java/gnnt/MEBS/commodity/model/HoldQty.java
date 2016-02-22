package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;

public class HoldQty
  extends SpecialSet
{
  private String commodityId;
  private String commodityName;
  private String firmId;
  private String memberName;
  private Integer oneMaxOrderQty;
  private Integer oneMinOrderQty;
  private Integer maxCleanQty;
  private Integer maxHoldQty;
  
  public HoldQty() {}
  
  public HoldQty(String commodityId, String commdityName, String firmId, Integer oneMaxOrderQty, Integer oneMinOrderQty, Integer maxCleanQty, Integer maxHoldQty)
  {
    this.commodityId = commodityId;
    this.commodityName = commdityName;
    this.firmId = firmId;
    this.oneMaxOrderQty = oneMaxOrderQty;
    this.oneMinOrderQty = oneMinOrderQty;
    this.maxCleanQty = maxCleanQty;
    this.maxHoldQty = maxHoldQty;
  }
  
  public HoldQty(String commodityId, String commdityName, String firmId, String memberName, Integer oneMaxOrderQty, Integer oneMinOrderQty, Integer maxCleanQty, Integer maxHoldQty)
  {
    this.commodityId = commodityId;
    this.commodityName = commdityName;
    this.firmId = firmId;
    this.memberName = memberName;
    this.oneMaxOrderQty = oneMaxOrderQty;
    this.oneMinOrderQty = oneMinOrderQty;
    this.maxCleanQty = maxCleanQty;
    this.maxHoldQty = maxHoldQty;
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
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
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
  
  @ClassDiscription(name="名称:", key=true, keyWord=true, isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Customer", value="客户默认"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Member", value="会员默认"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_S_Member", value="特别会员默认")})
  public String getMemberName()
  {
    if (this.memberName != null) {
      return this.memberName;
    }
    return this.firmId;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  @ClassDiscription(name="单笔最大下单量:", isStatus=false)
  public Integer getOneMaxOrderQty()
  {
    return this.oneMaxOrderQty;
  }
  
  public void setOneMaxOrderQty(Integer oneMaxOrderQty)
  {
    this.oneMaxOrderQty = oneMaxOrderQty;
  }
  
  @ClassDiscription(name="单笔最小下单量:")
  public Integer getOneMinOrderQty()
  {
    return this.oneMinOrderQty;
  }
  
  public void setOneMinOrderQty(Integer oneMinOrderQty)
  {
    this.oneMinOrderQty = oneMinOrderQty;
  }
  
  @ClassDiscription(name="最大净持仓量:", isStatus=false)
  public Integer getMaxCleanQty()
  {
    return this.maxCleanQty;
  }
  
  public void setMaxCleanQty(Integer maxCleanQty)
  {
    this.maxCleanQty = maxCleanQty;
  }
  
  public String getId()
  {
    return null;
  }
  
  @ClassDiscription(name="最大持仓量:", isStatus=false)
  public Integer getMaxHoldQty()
  {
    return this.maxHoldQty;
  }
  
  public void setMaxHoldQty(Integer maxHoldQty)
  {
    this.maxHoldQty = maxHoldQty;
  }
  
  public void setPrimary(String primary) {}
}
