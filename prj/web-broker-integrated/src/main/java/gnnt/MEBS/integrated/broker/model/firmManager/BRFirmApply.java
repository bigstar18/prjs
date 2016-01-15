package gnnt.MEBS.integrated.broker.model.firmManager;

import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class BRFirmApply extends StandardModel
{
  private static final long serialVersionUID = -1L;
  private Long applyId;
  private String userId;
  private String brokerAgeId;
  private String brokerId;
  private Date applyDate;
  private MFirmApply mFirmApply;

  public MFirmApply getmFirmApply()
  {
    return this.mFirmApply;
  }

  public void setmFirmApply(MFirmApply paramMFirmApply)
  {
    this.mFirmApply = paramMFirmApply;
  }

  public Long getApplyId()
  {
    return this.applyId;
  }

  public void setApplyId(Long paramLong)
  {
    this.applyId = paramLong;
  }

  public String getUserId()
  {
    return this.userId;
  }

  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }

  public String getBrokerAgeId()
  {
    return this.brokerAgeId;
  }

  public void setBrokerAgeId(String paramString)
  {
    this.brokerAgeId = paramString;
  }

  public String getBrokerId()
  {
    return this.brokerId;
  }

  public void setBrokerId(String paramString)
  {
    this.brokerId = paramString;
  }

  public Date getApplyDate()
  {
    return this.applyDate;
  }

  public void setApplyDate(Date paramDate)
  {
    this.applyDate = paramDate;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "applyId", this.applyId);
  }
}