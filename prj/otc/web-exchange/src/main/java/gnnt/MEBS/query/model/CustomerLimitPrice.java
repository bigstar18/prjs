package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class CustomerLimitPrice
  extends Clone
{
  private Integer orderno;
  private String customerName;
  private String customerNo;
  private String commodityName;
  private String commodityId;
  private int bs_flag;
  private String oc_flag;
  private Integer qty;
  private Double stopLossPrice;
  private Double stopProfitPrice;
  private Timestamp orderTime;
  private String ordermarkerprice;
  private String throughPrice;
  private Date atClearDate;
  private String memberNo;
  private Integer holdno;
  private String memberName;
  private String organizationno;
  private String organizationnname;
  private String brokerageno;
  private String brokeragename;
  private Timestamp withdrawtime;
  private Integer tradeno;
  private Double tradeprice;
  private String tradetype;
  private Integer status;
  private Timestamp tradetime;
  private String consignerid;
  private String withdrawerid;
  private String ordertype;
  private Double price;
  
  public Double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(Double price)
  {
    this.price = price;
  }
  
  @ClassDiscription(name="委托类型", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="市价委托"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="指价委托")})
  public String getOrdertype()
  {
    return this.ordertype;
  }
  
  public void setOrdertype(String ordertype)
  {
    this.ordertype = ordertype;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public Date getAtClearDate()
  {
    return this.atClearDate;
  }
  
  public void setAtClearDate(Date atClearDate)
  {
    this.atClearDate = atClearDate;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String customerNo)
  {
    this.customerNo = customerNo;
  }
  
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  @ClassDiscription(name="买卖方向", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="买入"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="卖出")})
  public int getBs_flag()
  {
    return this.bs_flag;
  }
  
  public void setBs_flag(int bs_flag)
  {
    this.bs_flag = bs_flag;
  }
  
  public Integer getOrderno()
  {
    return this.orderno;
  }
  
  public void setOrderno(Integer orderno)
  {
    this.orderno = orderno;
  }
  
  public String getCustomerName()
  {
    return this.customerName;
  }
  
  public void setCustomerName(String customerName)
  {
    this.customerName = customerName;
  }
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  @ClassDiscription(name="建仓类型", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="O", value="建仓"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="C", value="平仓")})
  public String getOc_flag()
  {
    return this.oc_flag;
  }
  
  public void setOc_flag(String oc_flag)
  {
    this.oc_flag = oc_flag;
  }
  
  public Integer getQty()
  {
    return this.qty;
  }
  
  public void setQty(Integer qty)
  {
    this.qty = qty;
  }
  
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  public String getOrganizationno()
  {
    return this.organizationno;
  }
  
  public void setOrganizationno(String organizationno)
  {
    this.organizationno = organizationno;
  }
  
  public String getOrganizationnname()
  {
    return this.organizationnname;
  }
  
  public void setOrganizationnname(String organizationnname)
  {
    this.organizationnname = organizationnname;
  }
  
  public String getBrokerageno()
  {
    return this.brokerageno;
  }
  
  public void setBrokerageno(String brokerageno)
  {
    this.brokerageno = brokerageno;
  }
  
  public String getBrokeragename()
  {
    return this.brokeragename;
  }
  
  public void setBrokeragename(String brokeragename)
  {
    this.brokeragename = brokeragename;
  }
  
  public Integer getHoldno()
  {
    return this.holdno;
  }
  
  public void setHoldno(Integer holdno)
  {
    this.holdno = holdno;
  }
  
  public Integer getTradeno()
  {
    return this.tradeno;
  }
  
  public void setTradeno(Integer tradeno)
  {
    this.tradeno = tradeno;
  }
  
  @ClassDiscription(name="成交类型", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="委托"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="强平"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="3", value="止损"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="4", value="止盈"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="5", value="对冲")})
  public String getTradetype()
  {
    return this.tradetype;
  }
  
  public void setTradetype(String tradetype)
  {
    this.tradetype = tradetype;
  }
  
  @ClassDiscription(name="委托状态", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="已委托"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="已成交"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="3", value="已撤单")})
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  public Timestamp getWithdrawtime()
  {
    return this.withdrawtime;
  }
  
  public void setWithdrawtime(Timestamp withdrawtime)
  {
    this.withdrawtime = withdrawtime;
  }
  
  public Double getTradeprice()
  {
    return this.tradeprice;
  }
  
  public void setTradeprice(Double tradeprice)
  {
    this.tradeprice = tradeprice;
  }
  
  public Timestamp getTradetime()
  {
    return this.tradetime;
  }
  
  public void setTradetime(Timestamp tradetime)
  {
    this.tradetime = tradetime;
  }
  
  public String getConsignerid()
  {
    return this.consignerid;
  }
  
  public void setConsignerid(String consignerid)
  {
    this.consignerid = consignerid;
  }
  
  public String getWithdrawerid()
  {
    return this.withdrawerid;
  }
  
  public void setWithdrawerid(String withdrawerid)
  {
    this.withdrawerid = withdrawerid;
  }
  
  public Double getStopLossPrice()
  {
    return this.stopLossPrice;
  }
  
  public void setStopLossPrice(Double stopLossPrice)
  {
    this.stopLossPrice = stopLossPrice;
  }
  
  public Double getStopProfitPrice()
  {
    return this.stopProfitPrice;
  }
  
  public void setStopProfitPrice(Double stopProfitPrice)
  {
    this.stopProfitPrice = stopProfitPrice;
  }
  
  public Timestamp getOrderTime()
  {
    return this.orderTime;
  }
  
  public void setOrderTime(Timestamp orderTime)
  {
    this.orderTime = orderTime;
  }
  
  public String getOrdermarkerprice()
  {
    return this.ordermarkerprice;
  }
  
  public void setOrdermarkerprice(String ordermarkerprice)
  {
    this.ordermarkerprice = ordermarkerprice;
  }
  
  public String getThroughPrice()
  {
    return this.throughPrice;
  }
  
  public void setThroughPrice(String throughPrice)
  {
    this.throughPrice = throughPrice;
  }
  
  public void setPrimary(String primary) {}
}
