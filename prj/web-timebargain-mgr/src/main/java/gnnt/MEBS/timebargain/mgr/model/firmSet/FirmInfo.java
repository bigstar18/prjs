package gnnt.MEBS.timebargain.mgr.model.firmSet;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class FirmInfo extends StandardModel
{
  private static final long serialVersionUID = -2616108349371284695L;

  @ClassDiscription(name="交易商ID", description="")
  private String firmID;

  @ClassDiscription(name="交易商名称", description="")
  private String firmName;

  @ClassDiscription(name="交易商状态", description="")
  private Integer status;

  @ClassDiscription(name="二级客户数量", description="")
  private Integer customerCounts;

  @ClassDiscription(name="二级客户数量", description="")
  private Integer tcounts;

  @ClassDiscription(name="创建时间", description="")
  private Date createTime;

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }

  public String getFirmName()
  {
    return this.firmName;
  }

  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public Integer getCustomerCounts()
  {
    return this.customerCounts;
  }

  public void setCustomerCounts(Integer customerCounts)
  {
    this.customerCounts = customerCounts;
  }

  public Integer getTcounts()
  {
    return this.tcounts;
  }

  public void setTcounts(Integer tcounts)
  {
    this.tcounts = tcounts;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "firmID", this.firmID);
  }
}