package gnnt.MEBS.member.broker.model;

public class ModuleConfiguration
  extends Cloneable
{
  private String moduleId;
  private String oprcode;
  
  public String getModuleId()
  {
    return this.moduleId;
  }
  
  public void setModuleId(String paramString)
  {
    this.moduleId = paramString;
  }
  
  public String getOprcode()
  {
    return this.oprcode;
  }
  
  public void setOprcode(String paramString)
  {
    this.oprcode = paramString;
  }
}
