package gnnt.MEBS.timebargain.mgr.model.tradeparams;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class SubSystem extends StandardModel
{

  @ClassDiscription(name="", description="15:撮合，16:连续现货，17:专场")
  private Short moduleID;

  @ClassDiscription(name="是否启用", description="")
  private String enabled;

  public Short getModuleID()
  {
    return this.moduleID;
  }

  public void setModuleID(Short moduleID) {
    this.moduleID = moduleID;
  }

  public String getEnabled() {
    return this.enabled;
  }

  public void setEnabled(String enabled) {
    this.enabled = enabled;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "moduleID", this.moduleID);
  }
}