package gnnt.MEBS.integrated.broker.model.fundsQuery;

import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class BrokerReward extends StandardModel
{
  private static final long serialVersionUID = -9179031058259779625L;
  private String brokerId;
  private Date occurDate;
  private Long moduleId;
  private Double amount;
  private Double paidAmount;
  private Date payDate;

  public String getBrokerId()
  {
    return this.brokerId;
  }

  public void setBrokerId(String paramString)
  {
    this.brokerId = paramString;
  }

  public Date getOccurDate()
  {
    return this.occurDate;
  }

  public void setOccurDate(Date paramDate)
  {
    this.occurDate = paramDate;
  }

  public Long getModuleId()
  {
    return this.moduleId;
  }

  public void setModuleId(Long paramLong)
  {
    this.moduleId = paramLong;
  }

  public Double getAmount()
  {
    return this.amount;
  }

  public void setAmount(Double paramDouble)
  {
    this.amount = paramDouble;
  }

  public Double getPaidAmount()
  {
    return this.paidAmount;
  }

  public void setPaidAmount(Double paramDouble)
  {
    this.paidAmount = paramDouble;
  }

  public Date getPayDate()
  {
    return this.payDate;
  }

  public void setPayDate(Date paramDate)
  {
    this.payDate = paramDate;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}