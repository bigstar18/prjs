package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.io.Serializable;
import java.sql.Timestamp;

public class CustomerTransactionSearch
  extends Clone
{
  private String memberNo;
  private String memberName;
  private String organizationNo;
  private String organizationName;
  private String brokerageNo;
  private String brokerageName;
  private String customerNo;
  private String customerName;
  private String oc_Flag;
  private Integer tradeNo;
  private Integer holdNo;
  private Timestamp tradeTime;
  private String commodityId;
  private String commodityName;
  private Integer quanTity;
  private Integer bs_Flag;
  private Double openprice;
  private Double holdprice;
  private Double closeprice;
  private Double close_pl;
  private Double tradefee;
  private Integer tradetype;
  private Integer operatetype;
  private String traderId;
  private Double tradefunds;
  private Integer orderno;
  private Double actualpl;
  
  public Integer getOrderno()
  {
    return this.orderno;
  }
  
  public void setOrderno(Integer orderno)
  {
    this.orderno = orderno;
  }
  
  public Double getTradefunds()
  {
    return this.tradefunds;
  }
  
  public void setTradefunds(Double tradefunds)
  {
    this.tradefunds = tradefunds;
  }
  
  public Double getActualpl()
  {
    return this.actualpl;
  }
  
  public void setActualpl(Double actualpl)
  {
    this.actualpl = actualpl;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  public String getOrganizationNo()
  {
    return this.organizationNo;
  }
  
  public void setOrganizationNo(String organizationNo)
  {
    this.organizationNo = organizationNo;
  }
  
  public String getOrganizationName()
  {
    return this.organizationName;
  }
  
  public void setOrganizationName(String organizationName)
  {
    this.organizationName = organizationName;
  }
  
  public String getBrokerageNo()
  {
    return this.brokerageNo;
  }
  
  public void setBrokerageNo(String brokerageNo)
  {
    this.brokerageNo = brokerageNo;
  }
  
  public String getBrokerageName()
  {
    return this.brokerageName;
  }
  
  public void setBrokerageName(String brokerageName)
  {
    this.brokerageName = brokerageName;
  }
  
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String customerNo)
  {
    this.customerNo = customerNo;
  }
  
  public String getCustomerName()
  {
    return this.customerName;
  }
  
  public void setCustomerName(String customerName)
  {
    this.customerName = customerName;
  }
  
  @ClassDiscription(name="建仓类型", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="O", value="建仓"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="C", value="平仓")})
  public String getOc_Flag()
  {
    return this.oc_Flag;
  }
  
  public void setOc_Flag(String oc_Flag)
  {
    this.oc_Flag = oc_Flag;
  }
  
  public Integer getTradeNo()
  {
    return this.tradeNo;
  }
  
  public void setTradeNo(Integer tradeNo)
  {
    this.tradeNo = tradeNo;
  }
  
  public Integer getHoldNo()
  {
    return this.holdNo;
  }
  
  public void setHoldNo(Integer holdNo)
  {
    this.holdNo = holdNo;
  }
  
  public Timestamp getTradeTime()
  {
    return this.tradeTime;
  }
  
  public void setTradeTime(Timestamp tradeTime)
  {
    this.tradeTime = tradeTime;
  }
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  public Integer getQuanTity()
  {
    return this.quanTity;
  }
  
  public void setQuanTity(Integer quanTity)
  {
    this.quanTity = quanTity;
  }
  
  @ClassDiscription(name="买卖方向", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="买入"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="卖出")})
  public Integer getBs_Flag()
  {
    return this.bs_Flag;
  }
  
  public void setBs_Flag(Integer bs_Flag)
  {
    this.bs_Flag = bs_Flag;
  }
  
  public Double getOpenprice()
  {
    return this.openprice;
  }
  
  public void setOpenprice(Double openprice)
  {
    this.openprice = openprice;
  }
  
  public Double getHoldprice()
  {
    return this.holdprice;
  }
  
  public void setHoldprice(Double holdprice)
  {
    this.holdprice = holdprice;
  }
  
  public Double getCloseprice()
  {
    return this.closeprice;
  }
  
  public void setCloseprice(Double closeprice)
  {
    this.closeprice = closeprice;
  }
  
  public Double getClose_pl()
  {
    return this.close_pl;
  }
  
  public void setClose_pl(Double close_pl)
  {
    this.close_pl = close_pl;
  }
  
  public Double getTradefee()
  {
    return this.tradefee;
  }
  
  public void setTradefee(Double tradefee)
  {
    this.tradefee = tradefee;
  }
  
  @ClassDiscription(name="成交类型", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="市价成交"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="市价成交"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="3", value="市价成交"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="4", value="自动强平"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="5", value="手动强平"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="6", value="指价成交"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="7", value="指价成交")})
  public Integer getTradetype()
  {
    return this.tradetype;
  }
  
  @ClassDiscription(name="操作类型", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="用户下单"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="电话下单"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="3", value="系统下单"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="4", value="系统下单"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="5", value="系统下单"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="6", value="系统下单"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="7", value="电话下单")})
  public Integer getOperatetype()
  {
    return this.operatetype;
  }
  
  public void setOperatetype(Integer operatetype)
  {
    this.operatetype = operatetype;
  }
  
  public void setTradetype(Integer tradetype)
  {
    this.tradetype = tradetype;
  }
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String traderId)
  {
    this.traderId = traderId;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
