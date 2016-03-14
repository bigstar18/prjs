package gnnt.MEBS.zcjs.memory.scheduling;

import gnnt.MEBS.zcjs.model.Broadcast;
import gnnt.MEBS.zcjs.model.TradeMsgFeedback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message
{
  private static Message message;
  private Map<String, Map<String, List<TradeMsgFeedback>>> tradeMsgFeedbackmap = new HashMap();
  private List<Broadcast> broadcastList = new ArrayList();
  
  public static Message createInstance()
  {
    if (message == null) {
      synchronized (Message.class)
      {
        if (message == null) {
          message = new Message();
        }
      }
    }
    return message;
  }
  
  public Map<String, Map<String, List<TradeMsgFeedback>>> getTradeMsgFeedbackmap()
  {
    return this.tradeMsgFeedbackmap;
  }
  
  public void setTradeMsgFeedbackmap(Map<String, Map<String, List<TradeMsgFeedback>>> tradeMsgFeedbackmap)
  {
    this.tradeMsgFeedbackmap = tradeMsgFeedbackmap;
  }
  
  public List<Broadcast> getBroadcastList()
  {
    return this.broadcastList;
  }
  
  public void setBroadcastList(List<Broadcast> broadcastList)
  {
    this.broadcastList = broadcastList;
  }
}
