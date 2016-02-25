package gnnt.MEBS.delivery.model.settle;

import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import java.util.Date;

public class SettleMatch
  extends WorkFlowClone
{
  private String matchId;
  private String moduleId;
  private long breedId;
  private String commodityId;
  private double weight;
  private double HL_Amount;
  private int status;
  private int result;
  private String firmID_B;
  private double buyPrice;
  private double buyPayout_Ref;
  private double buyPayout;
  private double buyMargin;
  private double settlePL_B;
  private double penalty_B;
  private String firmID_S;
  private double sellPrice;
  private double sellIncome_Ref;
  private double sellIncome;
  private double sellMargin;
  private double settlePL_S;
  private double penalty_S;
  private String regStockId;
  private int recvInvoice;
  private Date createTime;
  private Date modifyTime;
  private long contractId;
  private int isChangeOwner;
  private String xml;
  private String initXml;
  
  public void setInitXml(String paramString)
  {
    this.initXml = paramString;
  }
  
  public String getInitXml()
  {
    return this.initXml;
  }
  
  public long getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(long paramLong)
  {
    this.breedId = paramLong;
  }
  
  public double getBuyMargin()
  {
    return this.buyMargin;
  }
  
  public void setBuyMargin(double paramDouble)
  {
    this.buyMargin = paramDouble;
  }
  
  public double getBuyPayout()
  {
    return this.buyPayout;
  }
  
  public void setBuyPayout(double paramDouble)
  {
    this.buyPayout = paramDouble;
  }
  
  public double getBuyPayout_Ref()
  {
    return this.buyPayout_Ref;
  }
  
  public void setBuyPayout_Ref(double paramDouble)
  {
    this.buyPayout_Ref = paramDouble;
  }
  
  public double getBuyPrice()
  {
    return this.buyPrice;
  }
  
  public void setBuyPrice(double paramDouble)
  {
    this.buyPrice = paramDouble;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public String getFirmID_B()
  {
    return this.firmID_B;
  }
  
  public void setFirmID_B(String paramString)
  {
    this.firmID_B = paramString;
  }
  
  public String getFirmID_S()
  {
    return this.firmID_S;
  }
  
  public void setFirmID_S(String paramString)
  {
    this.firmID_S = paramString;
  }
  
  public double getHL_Amount()
  {
    return this.HL_Amount;
  }
  
  public void setHL_Amount(double paramDouble)
  {
    this.HL_Amount = paramDouble;
  }
  
  public String getMatchId()
  {
    return this.matchId;
  }
  
  public void setMatchId(String paramString)
  {
    this.matchId = paramString;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Date paramDate)
  {
    this.modifyTime = paramDate;
  }
  
  public String getModuleId()
  {
    return this.moduleId;
  }
  
  public void setModuleId(String paramString)
  {
    this.moduleId = paramString;
  }
  
  public double getPenalty_B()
  {
    return this.penalty_B;
  }
  
  public void setPenalty_B(double paramDouble)
  {
    this.penalty_B = paramDouble;
  }
  
  public double getPenalty_S()
  {
    return this.penalty_S;
  }
  
  public void setPenalty_S(double paramDouble)
  {
    this.penalty_S = paramDouble;
  }
  
  public int getRecvInvoice()
  {
    return this.recvInvoice;
  }
  
  public void setRecvInvoice(int paramInt)
  {
    this.recvInvoice = paramInt;
  }
  
  public String getRegStockId()
  {
    return this.regStockId;
  }
  
  public void setRegStockId(String paramString)
  {
    this.regStockId = paramString;
  }
  
  public int getResult()
  {
    return this.result;
  }
  
  public void setResult(int paramInt)
  {
    this.result = paramInt;
  }
  
  public double getSellIncome()
  {
    return this.sellIncome;
  }
  
  public void setSellIncome(double paramDouble)
  {
    this.sellIncome = paramDouble;
  }
  
  public double getSellIncome_Ref()
  {
    return this.sellIncome_Ref;
  }
  
  public void setSellIncome_Ref(double paramDouble)
  {
    this.sellIncome_Ref = paramDouble;
  }
  
  public double getSellMargin()
  {
    return this.sellMargin;
  }
  
  public void setSellMargin(double paramDouble)
  {
    this.sellMargin = paramDouble;
  }
  
  public double getSellPrice()
  {
    return this.sellPrice;
  }
  
  public void setSellPrice(double paramDouble)
  {
    this.sellPrice = paramDouble;
  }
  
  public double getSettlePL_B()
  {
    return this.settlePL_B;
  }
  
  public void setSettlePL_B(double paramDouble)
  {
    this.settlePL_B = paramDouble;
  }
  
  public double getSettlePL_S()
  {
    return this.settlePL_S;
  }
  
  public void setSettlePL_S(double paramDouble)
  {
    this.settlePL_S = paramDouble;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public double getWeight()
  {
    return this.weight;
  }
  
  public void setWeight(double paramDouble)
  {
    this.weight = paramDouble;
  }
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String paramString)
  {
    this.commodityId = paramString;
  }
  
  public long getContractId()
  {
    return this.contractId;
  }
  
  public void setContractId(long paramLong)
  {
    this.contractId = paramLong;
  }
  
  public String getXml()
  {
    return this.xml;
  }
  
  public void setXml(String paramString)
  {
    this.xml = paramString;
  }
  
  public int getIsChangeOwner()
  {
    return this.isChangeOwner;
  }
  
  public void setIsChangeOwner(int paramInt)
  {
    this.isChangeOwner = paramInt;
  }
}
