package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class MemberTradeSearchHis
  extends Clone
{
  private String memberNo;
  private String memberName;
  private String builderId;
  private String de_builderId;
  private Integer mem_tradeId;
  private Integer buildTradeType;
  private String commodityId;
  private String commodityName;
  private Integer holdQty;
  private String s_memberNo;
  private String o_firmName;
  private Double openPrice;
  private Double holdPrice;
  private Date buildDate;
  private String de_buildNo;
  private Integer de_build_flag;
  private Integer build_flag;
  private Double de_buildprice;
  private Date de_buildDate;
  private Double floatingLoss;
  private Double netfloatingLoss;
  private Double tradefee;
  private Double delayfee;
  private Date atClearDate;
  private String opentraderid;
  private String closetraderid;
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getOpentraderid()
  {
    return this.opentraderid;
  }
  
  public void setOpentraderid(String opentraderid)
  {
    this.opentraderid = opentraderid;
  }
  
  public String getClosetraderid()
  {
    return this.closetraderid;
  }
  
  public void setClosetraderid(String closetraderid)
  {
    this.closetraderid = closetraderid;
  }
  
  public Date getAtClearDate()
  {
    return transformData(this.atClearDate);
  }
  
  public void setAtClearDate(Date atClearDate)
  {
    this.atClearDate = atClearDate;
  }
  
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  public String getBuilderId()
  {
    return this.builderId;
  }
  
  public void setBuilderId(String builderId)
  {
    this.builderId = builderId;
  }
  
  public String getDe_builderId()
  {
    return this.de_builderId;
  }
  
  public void setDe_builderId(String de_builderId)
  {
    this.de_builderId = de_builderId;
  }
  
  public Integer getMem_tradeId()
  {
    return this.mem_tradeId;
  }
  
  public void setMem_tradeId(Integer mem_tradeId)
  {
    this.mem_tradeId = mem_tradeId;
  }
  
  public Integer getBuildTradeType()
  {
    return this.buildTradeType;
  }
  
  public void setBuildTradeType(Integer buildTradeType)
  {
    this.buildTradeType = buildTradeType;
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
  
  public Integer getHoldQty()
  {
    return this.holdQty;
  }
  
  public void setHoldQty(Integer holdQty)
  {
    this.holdQty = holdQty;
  }
  
  public String getS_memberNo()
  {
    return this.s_memberNo;
  }
  
  public void setS_memberNo(String no)
  {
    this.s_memberNo = no;
  }
  
  public String getO_firmName()
  {
    return this.o_firmName;
  }
  
  public void setO_firmName(String o_firmName)
  {
    this.o_firmName = o_firmName;
  }
  
  public Double getOpenPrice()
  {
    return this.openPrice;
  }
  
  public void setOpenPrice(Double openPrice)
  {
    this.openPrice = openPrice;
  }
  
  public Double getHoldPrice()
  {
    return this.holdPrice;
  }
  
  public void setHoldPrice(Double holdPrice)
  {
    this.holdPrice = holdPrice;
  }
  
  public Date getBuildDate()
  {
    return this.buildDate;
  }
  
  public void setBuildDate(Date buildDate)
  {
    this.buildDate = buildDate;
  }
  
  public String getDe_buildNo()
  {
    return this.de_buildNo;
  }
  
  public void setDe_buildNo(String de_buildNo)
  {
    this.de_buildNo = de_buildNo;
  }
  
  public Integer getDe_build_flag()
  {
    return this.de_build_flag;
  }
  
  public void setDe_build_flag(Integer de_build_flag)
  {
    this.de_build_flag = de_build_flag;
  }
  
  public Double getDe_buildprice()
  {
    return this.de_buildprice;
  }
  
  public void setDe_buildprice(Double de_buildprice)
  {
    this.de_buildprice = de_buildprice;
  }
  
  public Date getDe_buildDate()
  {
    return this.de_buildDate;
  }
  
  public void setDe_buildDate(Date de_buildDate)
  {
    this.de_buildDate = de_buildDate;
  }
  
  public Double getFloatingLoss()
  {
    return this.floatingLoss;
  }
  
  public void setFloatingLoss(Double floatingLoss)
  {
    this.floatingLoss = floatingLoss;
  }
  
  public Double getNetfloatingLoss()
  {
    return this.netfloatingLoss;
  }
  
  public void setNetfloatingLoss(Double netfloatingLoss)
  {
    this.netfloatingLoss = netfloatingLoss;
  }
  
  public Double getTradefee()
  {
    return this.tradefee;
  }
  
  public void setTradefee(Double tradefee)
  {
    this.tradefee = tradefee;
  }
  
  public Double getDelayfee()
  {
    return this.delayfee;
  }
  
  public void setDelayfee(Double delayfee)
  {
    this.delayfee = delayfee;
  }
  
  public Integer getBuild_flag()
  {
    return this.build_flag;
  }
  
  public void setBuild_flag(Integer build_flag)
  {
    this.build_flag = build_flag;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
