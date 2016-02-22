package gnnt.MEBS.audit.flowService;

import gnnt.MEBS.audit.model.Apply;
import gnnt.MEBS.audit.model.AuditStatus;
import java.util.Map;

public class OriginalModel
{
  private AuditStatus log;
  private Apply auditObject;
  private Map<String, String> businessMap;
  private String key;
  
  public AuditStatus getLog()
  {
    return this.log;
  }
  
  public void setLog(AuditStatus log)
  {
    this.log = log;
  }
  
  public Apply getAuditObject()
  {
    return this.auditObject;
  }
  
  public void setAuditObject(Apply auditObject)
  {
    this.auditObject = auditObject;
  }
  
  public Map<String, String> getBusinessMap()
  {
    return this.businessMap;
  }
  
  public void setBusinessMap(Map<String, String> businessMap)
  {
    this.businessMap = businessMap;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
}
