package gnnt.MEBS.finance.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class FirmAndBroker extends StandardModel
{
  private static final long serialVersionUID = -6787192677855085864L;

  @ClassDiscription(name="交易商代码", description="")
  private String firmID;

  @ClassDiscription(name="会员编号", description="")
  private String brokerID;

  @ClassDiscription(name="绑定时间", description="")
  private Date bindTime;

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String paramString)
  {
    this.firmID = paramString;
  }

  public String getBrokerID()
  {
    return this.brokerID;
  }

  public void setBrokerID(String paramString)
  {
    this.brokerID = paramString;
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
    return new StandardModel.PrimaryInfo( "firmID", this.firmID);
  }
}