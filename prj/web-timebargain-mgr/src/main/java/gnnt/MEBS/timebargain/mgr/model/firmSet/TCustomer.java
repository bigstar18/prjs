package gnnt.MEBS.timebargain.mgr.model.firmSet;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class TCustomer extends StandardModel
{
  private static final long serialVersionUID = -1521571893311589266L;

  @ClassDiscription(name="交易客户ID", description="")
  private String customerID;

  @ClassDiscription(name="交易客户ID", description="")
  private String firmIDs;

  @ClassDiscription(name="交易代码", description="")
  private String code;

  @ClassDiscription(name="交易客户名称", description="")
  private String name;

  @ClassDiscription(name="交易状态", description="")
  private Integer status;

  @ClassDiscription(name="创建时间", description="")
  private Date createTime;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  @ClassDiscription(name="引用交易商对象，多对一", description="")
  private TFirm firm;

  public String getCustomerID()
  {
    return this.customerID;
  }

  public void setCustomerID(String customerID)
  {
    this.customerID = customerID;
  }

  public String getFirmIDs()
  {
    return this.firmIDs;
  }

  public void setFirmIDs(String firmIDs) {
    this.firmIDs = firmIDs;
  }

  public String getCode()
  {
    return this.code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }

  public TFirm getFirm()
  {
    return this.firm;
  }

  public void setFirm(TFirm firm)
  {
    this.firm = firm;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "customerID", this.customerID);
  }
}