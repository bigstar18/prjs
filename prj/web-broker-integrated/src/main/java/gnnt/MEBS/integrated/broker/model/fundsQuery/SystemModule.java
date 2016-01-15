package gnnt.MEBS.integrated.broker.model.fundsQuery;

import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.StandardModel.PrimaryInfo;

public class SystemModule extends StandardModel
{
  private static final long serialVersionUID = -5348734777544440099L;
  private String moduleId;
  private String name;

  public String getModuleId()
  {
    return this.moduleId;
  }

  public void setModuleId(String paramString)
  {
    this.moduleId = paramString;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "moduleId", this.moduleId);
  }
}