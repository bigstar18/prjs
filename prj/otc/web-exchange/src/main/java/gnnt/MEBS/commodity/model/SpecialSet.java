package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;

public abstract class SpecialSet
  extends Clone
{
  protected String operate = "P";
  
  @ClassDiscription(name="控制方式:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="P", value="个性化"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="D", value="默认")})
  public String getOperate()
  {
    return this.operate;
  }
  
  public void setOperate(String operate)
  {
    this.operate = operate;
  }
}
