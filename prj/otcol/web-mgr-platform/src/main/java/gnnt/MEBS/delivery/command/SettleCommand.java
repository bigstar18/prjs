package gnnt.MEBS.delivery.command;

import gnnt.MEBS.delivery.command.model.SettleObject;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SettleCommand
  implements Command
{
  private final transient Log logger = LogFactory.getLog(SettleCommand.class);
  private Map<String, Receive> receiveMap;
  
  public void setReceiveMap(Map<String, Receive> paramMap)
  {
    this.receiveMap = paramMap;
  }
  
  public int execute(Information paramInformation)
  {
    int i = -1000;
    this.logger.debug(" enter execute");
    if (paramInformation != null)
    {
      this.logger.debug(" enter information");
      SettleObject localSettleObject = (SettleObject)paramInformation.getObject();
      Receive localReceive = (Receive)this.receiveMap.get(paramInformation.getReceiveName());
      i = localReceive.deal(paramInformation);
    }
    else
    {
      i = -99;
    }
    return i;
  }
}
