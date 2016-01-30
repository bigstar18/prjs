package gnnt.MEBS.finance.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class Account extends StandardModel
{
  private static final long serialVersionUID = 5784146412311672026L;

  @ClassDiscription(name="科目代码", description="")
  private String code;

  @ClassDiscription(name="科目名称", description="")
  private String name;

  @ClassDiscription(name="科目级别", description="")
  private String accountLevel;

  @ClassDiscription(name="借贷方向标志", description="")
  private String dcFlag;

  @ClassDiscription(name="是否初始化", description="")
  private String isInit;

  public String getCode()
  {
    return this.code;
  }

  public String getName()
  {
    return this.name;
  }

  public String getAccountLevel()
  {
    return this.accountLevel;
  }

  public String getDcFlag()
  {
    return this.dcFlag;
  }

  public String getIsInit()
  {
    return this.isInit;
  }

  public void setCode(String paramString)
  {
    this.code = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setAccountLevel(String paramString)
  {
    this.accountLevel = paramString;
  }

  public void setDcFlag(String paramString)
  {
    this.dcFlag = paramString;
  }

  public void setIsInit(String paramString)
  {
    this.isInit = paramString;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "code", this.code);
  }
}