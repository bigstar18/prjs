package gnnt.MEBS.timebargain.mgr.model.settle;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class HoldFrozen extends StandardModel
{
  private static final long serialVersionUID = -3154056789599510562L;

  @ClassDiscription(name="主键", description="")
  private Long id;

  @ClassDiscription(name="业务代码", description="")
  private String operation;

  @ClassDiscription(name="交易商ID", description="")
  private String firmId;

  @ClassDiscription(name="交易商ID", description="")
  private String commodityId;

  @ClassDiscription(name="交易商ID", description="1：买  2：卖")
  private int bs_Flag;

  @ClassDiscription(name="冻结类型", description="0:提前交收 1:协议交收 2:非交易过户3:抵顶")
  private Integer frozentype;

  @ClassDiscription(name="冻结数量", description="")
  private long frozenQty;

  @ClassDiscription(name="冻结时间", description="")
  private Date frozenTime;

  @ClassDiscription(name="客户ID", description="")
  private String customerId;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getOperation()
  {
    return this.operation;
  }

  public void setOperation(String operation)
  {
    this.operation = operation;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }

  public String getCommodityId()
  {
    return this.commodityId;
  }

  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }

  public int getBs_Flag()
  {
    return this.bs_Flag;
  }

  public void setBs_Flag(int bsFlag)
  {
    this.bs_Flag = bsFlag;
  }

  public Integer getFrozentype()
  {
    return this.frozentype;
  }

  public void setFrozentype(Integer frozentype)
  {
    this.frozentype = frozentype;
  }

  public long getFrozenQty()
  {
    return this.frozenQty;
  }

  public void setFrozenQty(long frozenQty)
  {
    this.frozenQty = frozenQty;
  }

  public Date getFrozenTime()
  {
    return this.frozenTime;
  }

  public void setFrozenTime(Date frozenTime)
  {
    this.frozenTime = frozenTime;
  }

  public String getCustomerId()
  {
    return this.customerId;
  }

  public void setCustomerId(String customerId)
  {
    this.customerId = customerId;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "id", this.id);
  }
}