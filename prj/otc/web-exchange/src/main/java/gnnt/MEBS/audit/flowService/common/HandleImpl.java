package gnnt.MEBS.audit.flowService.common;

import gnnt.MEBS.audit.flowService.Behaviour;
import gnnt.MEBS.audit.flowService.Handle;
import gnnt.MEBS.audit.model.Apply;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HandleImpl
  implements Handle
{
  private final transient Log logger = LogFactory.getLog(HandleImpl.class);
  private int finalStatus;
  private Behaviour behaviour;
  private String beanName;
  private String key;
  
  public int getFinalStatus()
  {
    return this.finalStatus;
  }
  
  public void setFinalStatus(int finalStatus)
  {
    this.finalStatus = finalStatus;
  }
  
  public String getBeanName()
  {
    return this.beanName;
  }
  
  public void setBeanName(String beanName)
  {
    this.beanName = beanName;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
  
  public void setBehaviour(Behaviour behaviour)
  {
    this.behaviour = behaviour;
  }
  
  public int dealBehaviour(Apply auditObject)
  {
    int result = 1;
    this.logger.debug("dealBehaviour");
    if (this.behaviour != null)
    {
      this.logger.debug("behaviour:" + this.behaviour);
      result = this.behaviour.deal(auditObject);
    }
    this.logger.debug("result:" + result);
    if (result > 0) {
      this.logger.debug("finalStatus:" + this.finalStatus);
    }
    auditObject.setStatus(Integer.valueOf(this.finalStatus));
    

    return result;
  }
}
