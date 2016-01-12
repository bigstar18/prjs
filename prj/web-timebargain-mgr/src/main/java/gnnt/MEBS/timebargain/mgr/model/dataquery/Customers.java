package gnnt.MEBS.timebargain.mgr.model.dataquery;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class Customers extends StandardModel
{
  private static final long serialVersionUID = -7526207112380782610L;

  @ClassDiscription(name="交易客户ID", description="")
  private String customerId;

  @ClassDiscription(name="交易商ID", description="")
  private String firmId;

  @ClassDiscription(name="交易代码", description="")
  private String code;

  @ClassDiscription(name="交易客户名称", description="")
  private String name;

  @ClassDiscription(name="状态", description="")
  private Long status;

  @ClassDiscription(name="建时间", description="")
  private Date createTime;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;
  private CustomerHoldSumModel customerHoldSumModel;
  private HistoryCustomerHoldSumModel historyCustomerHoldSumModel;

  public String getCustomerId()
  {
    return this.customerId;
  }

  public void setCustomerId(String customerId)
  {
    this.customerId = customerId;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
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

  public Long getStatus()
  {
    return this.status;
  }

  public void setStatus(Long status)
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

  public CustomerHoldSumModel getCustomerHoldSumModel()
  {
    return this.customerHoldSumModel;
  }

  public void setCustomerHoldSumModel(CustomerHoldSumModel customerHoldSumModel)
  {
    this.customerHoldSumModel = customerHoldSumModel;
  }

  public HistoryCustomerHoldSumModel getHistoryCustomerHoldSumModel()
  {
    return this.historyCustomerHoldSumModel;
  }

  public void setHistoryCustomerHoldSumModel(HistoryCustomerHoldSumModel historyCustomerHoldSumModel)
  {
    this.historyCustomerHoldSumModel = historyCustomerHoldSumModel;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}