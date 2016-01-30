package gnnt.MEBS.integrated.mgr.model.usermanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class TraderModule
  extends StandardModel
{
  @ClassDiscription(name="", description="")
  private static final long serialVersionUID = -8054488919579229963L;
  @ClassDiscription(name="模块号", description="1：财务资金  2：中远期  3：现货  4：竞价")
  private Integer moduleId;
  @ClassDiscription(name="交易员代码", description="")
  private Trader trader;
  @ClassDiscription(name="是否启用，默认禁用", description="Y：启用  N：禁用")
  private String enabled;
  
  public Integer getModuleId()
  {
    return this.moduleId;
  }
  
  public void setModuleId(Integer paramInteger)
  {
    this.moduleId = paramInteger;
  }
  
  public Trader getTrader()
  {
    return this.trader;
  }
  
  public void setTrader(Trader paramTrader)
  {
    this.trader = paramTrader;
  }
  
  public String getEnabled()
  {
    return this.enabled;
  }
  
  public void setEnabled(String paramString)
  {
    this.enabled = paramString;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
