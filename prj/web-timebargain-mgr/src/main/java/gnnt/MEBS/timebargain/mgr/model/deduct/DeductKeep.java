package gnnt.MEBS.timebargain.mgr.model.deduct;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class DeductKeep extends StandardModel
{
  private static final long serialVersionUID = -1L;

  @ClassDiscription(name="强减ID", description="")
  private Long deductId;

  @ClassDiscription(name=" 买卖标志", description="1:买 buy，2:卖 sell")
  private Integer bs_Flag;

  @ClassDiscription(name="交易客户代码", description="")
  private String customerId;

  @ClassDiscription(name="保留数量", description="")
  private Long keepQty;

  @ClassDiscription(name="强减", description="")
  private Deduct deduct;

  public Long getDeductId()
  {
    return this.deductId;
  }

  public void setDeductId(Long deductId)
  {
    this.deductId = deductId;
  }

  public Integer getBs_Flag()
  {
    return this.bs_Flag;
  }

  public void setBs_Flag(Integer bsFlag)
  {
    this.bs_Flag = bsFlag;
  }

  public String getCustomerId()
  {
    return this.customerId;
  }

  public void setCustomerId(String customerId)
  {
    this.customerId = customerId;
  }

  public Long getKeepQty()
  {
    return this.keepQty;
  }

  public void setKeepQty(Long keepQty)
  {
    this.keepQty = keepQty;
  }

  public Deduct getDeduct()
  {
    return this.deduct;
  }

  public void setDeduct(Deduct deduct)
  {
    this.deduct = deduct;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}