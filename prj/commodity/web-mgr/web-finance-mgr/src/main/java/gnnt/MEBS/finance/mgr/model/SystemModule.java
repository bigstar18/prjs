package gnnt.MEBS.finance.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class SystemModule extends StandardModel
{
  private static final long serialVersionUID = -5348734777544440099L;

  @ClassDiscription(name="模块号", description="")
  private String moduleId;

  @ClassDiscription(name="模块名称", description="")
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