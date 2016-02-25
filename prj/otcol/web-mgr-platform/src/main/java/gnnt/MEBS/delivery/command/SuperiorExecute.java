package gnnt.MEBS.delivery.command;

import java.util.Map;

public class SuperiorExecute
  implements Superior
{
  protected Map<String, Command> commandMap;
  
  public void setCommandMap(Map<String, Command> paramMap)
  {
    this.commandMap = paramMap;
  }
  
  public int startExecuteCommand(Information paramInformation)
  {
    int i = -1000;
    try
    {
      Command localCommand = (Command)this.commandMap.get(paramInformation.getCommandName());
      i = localCommand.execute(paramInformation);
    }
    catch (Exception localException)
    {
      i = -1000;
      localException.printStackTrace();
    }
    return i;
  }
}
