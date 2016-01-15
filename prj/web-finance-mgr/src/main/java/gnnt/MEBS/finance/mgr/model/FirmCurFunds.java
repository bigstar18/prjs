package gnnt.MEBS.finance.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class FirmCurFunds extends StandardModel
{
  private static final long serialVersionUID = -2274661640477912680L;

  @ClassDiscription(name="交易商代码", description="")
  private String firmId;

  @ClassDiscription(name="交易商名称", description="")
  private String name;

  @ClassDiscription(name="交易系统当前余额", description="")
  private Double f_balance;

  @ClassDiscription(name="财务结算余额", description="")
  private Double l_balance;

  @ClassDiscription(name="财务未结算金额", description="")
  private Double y_balance;

  @ClassDiscription(name="差额", description="")
  private Double balanceSubtract;

  @ClassDiscription(name="担保金", description="")
  private Double lastwarranty;

  @ClassDiscription(name="临时资金", description="")
  private Double frozenFunds;

  @ClassDiscription(name="可用资金", description="")
  private Double user_balance;
  private FirmAndBroker firmAndBroker;

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public Double getF_balance()
  {
    return this.f_balance;
  }

  public void setF_balance(Double paramDouble)
  {
    this.f_balance = paramDouble;
  }

  public Double getL_balance()
  {
    return this.l_balance;
  }

  public void setL_balance(Double paramDouble)
  {
    this.l_balance = paramDouble;
  }

  public Double getY_balance()
  {
    return this.y_balance;
  }

  public void setY_balance(Double paramDouble)
  {
    this.y_balance = paramDouble;
  }

  public Double getLastwarranty()
  {
    return this.lastwarranty;
  }

  public void setLastwarranty(Double paramDouble)
  {
    this.lastwarranty = paramDouble;
  }

  public Double getFrozenFunds()
  {
    return this.frozenFunds;
  }

  public void setFrozenFunds(Double paramDouble)
  {
    this.frozenFunds = paramDouble;
  }

  public Double getBalanceSubtract()
  {
    return this.balanceSubtract;
  }

  public void setBalanceSubtract(Double paramDouble)
  {
    this.balanceSubtract = paramDouble;
  }

  public Double getUser_balance()
  {
    return this.user_balance;
  }

  public void setUser_balance(Double paramDouble)
  {
    this.user_balance = paramDouble;
  }

  public FirmAndBroker getFirmAndBroker()
  {
    return this.firmAndBroker;
  }

  public void setFirmAndBroker(FirmAndBroker paramFirmAndBroker)
  {
    this.firmAndBroker = paramFirmAndBroker;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "firmId", this.firmId);
  }
}