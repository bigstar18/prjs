package gnnt.MEBS.integrated.broker.model.brokerage;

import gnnt.MEBS.common.broker.model.BrokerAge;
import gnnt.MEBS.common.broker.model.MFirm;
import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class BrokerageAndFirm extends StandardModel
{
  private static final long serialVersionUID = 1L;
  private String brokerAgeId;
  private String firmId;
  private String bindType;
  private Date bindTime;
  private BrokerAge brokerAge;
  private MFirm mfirm;

  public String getBrokerAgeId()
  {
    return this.brokerAgeId;
  }

  public void setBrokerAgeId(String paramString)
  {
    this.brokerAgeId = paramString;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }

  public String getBindType()
  {
    return this.bindType;
  }

  public void setBindType(String paramString)
  {
    this.bindType = paramString;
  }

  public Date getBindTime()
  {
    return this.bindTime;
  }

  public void setBindTime(Date paramDate)
  {
    this.bindTime = paramDate;
  }

  public BrokerAge getBrokerAge()
  {
    return this.brokerAge;
  }

  public void setBrokerAge(BrokerAge paramBrokerAge)
  {
    this.brokerAge = paramBrokerAge;
  }

  public MFirm getMfirm()
  {
    return this.mfirm;
  }

  public void setMfirm(MFirm paramMFirm)
  {
    this.mfirm = paramMFirm;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "brokerAgeId", this.brokerAgeId);
  }
}