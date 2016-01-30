package gnnt.MEBS.integrated.broker.model.firmManager;

import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class FirmAndBroker extends StandardModel
{
  private static final long serialVersionUID = -1L;
  private String firmId;
  private String brokerId;
  private Date bindTime;

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }

  public String getBrokerId()
  {
    return this.brokerId;
  }

  public void setBrokerId(String paramString)
  {
    this.brokerId = paramString;
  }

  public Date getBindTime()
  {
    return this.bindTime;
  }

  public void setBindTime(Date paramDate)
  {
    this.bindTime = paramDate;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "firmId", this.firmId);
  }
}