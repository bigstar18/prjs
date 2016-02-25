package gnnt.MEBS.delivery.command;

import gnnt.MEBS.delivery.model.LogValue;

public class Information
{
  private String commandName;
  private String receiveName;
  private Object object;
  private LogValue logValue;
  
  public LogValue getLogValue()
  {
    return this.logValue;
  }
  
  public void setLogValue(LogValue paramLogValue)
  {
    this.logValue = paramLogValue;
  }
  
  public String getCommandName()
  {
    return this.commandName;
  }
  
  public void setCommandName(String paramString)
  {
    this.commandName = paramString;
  }
  
  public String getReceiveName()
  {
    return this.receiveName;
  }
  
  public void setReceiveName(String paramString)
  {
    this.receiveName = paramString;
  }
  
  public Object getObject()
  {
    return this.object;
  }
  
  public void setObject(Object paramObject)
  {
    this.object = paramObject;
  }
}
