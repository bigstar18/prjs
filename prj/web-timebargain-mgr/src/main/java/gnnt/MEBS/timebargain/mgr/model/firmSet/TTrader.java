package gnnt.MEBS.timebargain.mgr.model.firmSet;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class TTrader extends StandardModel
{
  private static final long serialVersionUID = 7646840361999404746L;

  @ClassDiscription(name=" 交易员ID", description="")
  private String traderID;

  @ClassDiscription(name="可操作交易代码", description="")
  private String operateCode;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  public String getTraderID()
  {
    return this.traderID;
  }

  public void setTraderID(String traderID)
  {
    this.traderID = traderID;
  }

  public String getOperateCode()
  {
    return this.operateCode;
  }

  public void setOperateCode(String operateCode)
  {
    this.operateCode = operateCode;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "traderID", this.traderID);
  }
}