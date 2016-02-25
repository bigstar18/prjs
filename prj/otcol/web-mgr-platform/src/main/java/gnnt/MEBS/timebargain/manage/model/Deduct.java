package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import java.util.Date;

public class Deduct
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Date deductDate;
  private String uni_Cmdty_Code;
  private String commodityID;
  private String deductStatus;
  private Double deductPrice;
  private Short loserBSFlag;
  private Short upDownFlag;
  private Short loserMode;
  private Double lossRate;
  private Short selfCounteract;
  private Double profitLvl1;
  private Double profitLvl2;
  private Double profitLvl3;
  private Double profitLvl4;
  private Double profitLvl5;
  private String crud = "";
  private String customerID;
  private Short bS_Flag;
  private Long keepQty;
  private Long deductID;
  private Date execTime;
  private String alert;
  private Long deductedQty;
  private Long counteractedQty;
  
  public Long getCounteractedQty()
  {
    return this.counteractedQty;
  }
  
  public void setCounteractedQty(Long paramLong)
  {
    this.counteractedQty = paramLong;
  }
  
  public Long getDeductedQty()
  {
    return this.deductedQty;
  }
  
  public void setDeductedQty(Long paramLong)
  {
    this.deductedQty = paramLong;
  }
  
  public String getAlert()
  {
    return this.alert;
  }
  
  public void setAlert(String paramString)
  {
    this.alert = paramString;
  }
  
  public Date getExecTime()
  {
    return this.execTime;
  }
  
  public void setExecTime(Date paramDate)
  {
    this.execTime = paramDate;
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String paramString)
  {
    this.crud = paramString;
  }
  
  public boolean equals(Object paramObject)
  {
    return false;
  }
  
  public int hashCode()
  {
    return 0;
  }
  
  public String toString()
  {
    return null;
  }
  
  public Date getDeductDate()
  {
    return this.deductDate;
  }
  
  public void setDeductDate(Date paramDate)
  {
    this.deductDate = paramDate;
  }
  
  public Double getDeductPrice()
  {
    return this.deductPrice;
  }
  
  public void setDeductPrice(Double paramDouble)
  {
    this.deductPrice = paramDouble;
  }
  
  public String getDeductStatus()
  {
    return this.deductStatus;
  }
  
  public void setDeductStatus(String paramString)
  {
    this.deductStatus = paramString;
  }
  
  public Short getLoserMode()
  {
    return this.loserMode;
  }
  
  public void setLoserMode(Short paramShort)
  {
    this.loserMode = paramShort;
  }
  
  public Double getLossRate()
  {
    return this.lossRate;
  }
  
  public void setLossRate(Double paramDouble)
  {
    this.lossRate = paramDouble;
  }
  
  public Double getProfitLvl1()
  {
    return this.profitLvl1;
  }
  
  public void setProfitLvl1(Double paramDouble)
  {
    this.profitLvl1 = paramDouble;
  }
  
  public Double getProfitLvl2()
  {
    return this.profitLvl2;
  }
  
  public void setProfitLvl2(Double paramDouble)
  {
    this.profitLvl2 = paramDouble;
  }
  
  public Double getProfitLvl3()
  {
    return this.profitLvl3;
  }
  
  public void setProfitLvl3(Double paramDouble)
  {
    this.profitLvl3 = paramDouble;
  }
  
  public Double getProfitLvl4()
  {
    return this.profitLvl4;
  }
  
  public void setProfitLvl4(Double paramDouble)
  {
    this.profitLvl4 = paramDouble;
  }
  
  public Double getProfitLvl5()
  {
    return this.profitLvl5;
  }
  
  public void setProfitLvl5(Double paramDouble)
  {
    this.profitLvl5 = paramDouble;
  }
  
  public Short getSelfCounteract()
  {
    return this.selfCounteract;
  }
  
  public void setSelfCounteract(Short paramShort)
  {
    this.selfCounteract = paramShort;
  }
  
  public String getUni_Cmdty_Code()
  {
    return this.uni_Cmdty_Code;
  }
  
  public void setUni_Cmdty_Code(String paramString)
  {
    this.uni_Cmdty_Code = paramString;
  }
  
  public Short getUpDownFlag()
  {
    return this.upDownFlag;
  }
  
  public void setUpDownFlag(Short paramShort)
  {
    this.upDownFlag = paramShort;
  }
  
  public Short getBS_Flag()
  {
    return this.bS_Flag;
  }
  
  public void setBS_Flag(Short paramShort)
  {
    this.bS_Flag = paramShort;
  }
  
  public Long getKeepQty()
  {
    return this.keepQty;
  }
  
  public void setKeepQty(Long paramLong)
  {
    this.keepQty = paramLong;
  }
  
  public Short getLoserBSFlag()
  {
    return this.loserBSFlag;
  }
  
  public void setLoserBSFlag(Short paramShort)
  {
    this.loserBSFlag = paramShort;
  }
  
  public Long getDeductID()
  {
    return this.deductID;
  }
  
  public void setDeductID(Long paramLong)
  {
    this.deductID = paramLong;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
  }
}
