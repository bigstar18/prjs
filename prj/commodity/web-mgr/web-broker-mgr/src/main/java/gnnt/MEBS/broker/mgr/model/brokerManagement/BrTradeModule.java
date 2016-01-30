package gnnt.MEBS.broker.mgr.model.brokerManagement;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;

public class BrTradeModule extends StandardModel
{
  private static final long serialVersionUID = -5120867442493919170L;
  private Integer moduleId;
  private String cnName;
  private String enName;
  private String shortName;

  public Integer getModuleId()
  {
    return this.moduleId;
  }

  public void setModuleId(Integer paramInteger)
  {
    this.moduleId = paramInteger;
  }

  public String getCnName()
  {
    return this.cnName;
  }

  public void setCnName(String paramString)
  {
    this.cnName = paramString;
  }

  public String getEnName()
  {
    return this.enName;
  }

  public void setEnName(String paramString)
  {
    this.enName = paramString;
  }

  public String getShortName()
  {
    return this.shortName;
  }

  public void setShortName(String paramString)
  {
    this.shortName = paramString;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "moduleId", this.moduleId);
  }
}