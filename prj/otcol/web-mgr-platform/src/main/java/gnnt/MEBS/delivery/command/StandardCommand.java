package gnnt.MEBS.delivery.command;

import java.util.Map;

public class StandardCommand
  implements Command
{
  private Map<String, Receive> receiveMap;
  
  public int execute(Information paramInformation)
  {
    Receive localReceive = (Receive)this.receiveMap.get(paramInformation.getReceiveName());
    return localReceive.deal(paramInformation);
  }
  
  public void setReceiveMap(Map<String, Receive> paramMap)
  {
    this.receiveMap = paramMap;
  }
}
