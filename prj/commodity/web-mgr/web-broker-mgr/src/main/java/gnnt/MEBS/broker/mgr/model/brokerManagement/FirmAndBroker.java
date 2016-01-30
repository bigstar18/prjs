package gnnt.MEBS.broker.mgr.model.brokerManagement;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class FirmAndBroker extends StandardModel
{
  private static final long serialVersionUID = 7680680645649905688L;
  private String firmId;
  private String brokerId;
  private Date bindTime;
  private Firm firm;

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

  public Firm getFirm()
  {
    return this.firm;
  }

  public void setFirm(Firm paramFirm)
  {
    this.firm = paramFirm;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "firmId", this.firmId);
  }
}