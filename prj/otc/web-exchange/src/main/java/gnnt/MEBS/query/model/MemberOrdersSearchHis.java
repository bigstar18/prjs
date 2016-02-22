package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class MemberOrdersSearchHis
  extends Clone
{
  private Date clearDate;
  private String memberNo;
  private String memberName;
  private String membersignNo;
  private String oc_Flag;
  private Integer tradeno;
  private Integer holdno;
  private Timestamp tradeTime;
  private String commodityId;
  private String commodityName;
  private Integer quantity;
  private Integer bs_flag;
  private Double openprice;
  private Double closeprice;
  private Double holdprice;
  private Double tradefee;
  private Double close_pl;
  private String traderId;
  private String s_memberNo;
  private String s_Name;
  private String s_signNo;
  private Integer tradeType;
  private Integer operatetype;
  private Double tradefunds;
  private Double actualpl;
  
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
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
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
  
  public String getMembersignNo()
  {
    return this.membersignNo;
  }
  
  public void setMembersignNo(String membersignNo)
  {
    this.membersignNo = membersignNo;
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
  
  public Integer getTradeno()
  {
    return this.tradeno;
  }
  
  public void setTradeno(Integer tradeno)
  {
    this.tradeno = tradeno;
  }
  
  public Integer getHoldno()
  {
    return this.holdno;
  }
  
  public void setHoldno(Integer holdno)
  {
    this.holdno = holdno;
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
  
  public Integer getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(Integer quantity)
  {
    this.quantity = quantity;
  }
  
  @ClassDiscription(name="买卖方向", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="买入"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="卖出")})
  public Integer getBs_flag()
  {
    return this.bs_flag;
  }
  
  public void setBs_flag(Integer bs_flag)
  {
    this.bs_flag = bs_flag;
  }
  
  public Double getOpenprice()
  {
    return this.openprice;
  }
  
  public void setOpenprice(Double openprice)
  {
    this.openprice = openprice;
  }
  
  public Double getCloseprice()
  {
    return this.closeprice;
  }
  
  public void setCloseprice(Double closeprice)
  {
    this.closeprice = closeprice;
  }
  
  public Double getHoldprice()
  {
    return this.holdprice;
  }
  
  public void setHoldprice(Double holdprice)
  {
    this.holdprice = holdprice;
  }
  
  public Double getTradefee()
  {
    return this.tradefee;
  }
  
  public void setTradefee(Double tradefee)
  {
    this.tradefee = tradefee;
  }
  
  public Double getClose_pl()
  {
    return this.close_pl;
  }
  
  public void setClose_pl(Double close_pl)
  {
    this.close_pl = close_pl;
  }
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String traderId)
  {
    this.traderId = traderId;
  }
  
  public String getS_memberNo()
  {
    return this.s_memberNo;
  }
  
  public void setS_memberNo(String s_memberNo)
  {
    this.s_memberNo = s_memberNo;
  }
  
  public String getS_Name()
  {
    return this.s_Name;
  }
  
  public void setS_Name(String s_Name)
  {
    this.s_Name = s_Name;
  }
  
  public String getS_signNo()
  {
    return this.s_signNo;
  }
  
  public void setS_signNo(String s_signNo)
  {
    this.s_signNo = s_signNo;
  }
  
  @ClassDiscription(name="成交类型", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="市价成交"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="市价成交"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="3", value="市价成交"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="4", value="自动强平"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="5", value="手动强平"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="6", value="指价成交"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="7", value="指价成交")})
  public Integer getTradeType()
  {
    return this.tradeType;
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
  
  public void setTradeType(Integer tradeType)
  {
    this.tradeType = tradeType;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
