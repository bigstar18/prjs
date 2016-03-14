package gnnt.MEBS.zcjs.memory.system;

import gnnt.MEBS.zcjs.model.ProsceniumShow;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SystemObject
{
  private final transient Log logger = LogFactory.getLog(SystemObject.class);
  private static SystemObject systemObject;
  private List<TradeTime> tradeTimeList;
  private TradeTime currentTradeTime;
  
  public static SystemObject createInstance()
  {
    if (systemObject == null) {
      synchronized (SystemObject.class)
      {
        if (systemObject == null) {
          systemObject = new SystemObject();
        }
      }
    }
    return systemObject;
  }
  
  private boolean isAuto = true;
  private SystemState currentState;
  private List<SystemState> executedList = new LinkedList();
  private SystemState nextState;
  private RestMsg restMsg;
  private List<ProsceniumShow> prosceniumShowList;
  
  public List<ProsceniumShow> getProsceniumShowList()
  {
    return this.prosceniumShowList;
  }
  
  public void setProsceniumShowList(List<ProsceniumShow> prosceniumShowList)
  {
    this.prosceniumShowList = prosceniumShowList;
  }
  
  public RestMsg getRestMsg()
  {
    return this.restMsg;
  }
  
  public void setRestMsg(RestMsg restMsg)
  {
    this.restMsg = restMsg;
  }
  
  public List<TradeTime> getTradeTimeList()
  {
    return this.tradeTimeList;
  }
  
  public void setTradeTimeList(List<TradeTime> tradeTimeList)
  {
    this.tradeTimeList = tradeTimeList;
  }
  
  public TradeTime getCurrentTradeTime()
  {
    return this.currentTradeTime;
  }
  
  public void setCurrentTradeTime(TradeTime currentTradeTime)
  {
    this.currentTradeTime = currentTradeTime;
  }
  
  public boolean isAuto()
  {
    return this.isAuto;
  }
  
  public void setAuto(boolean isAuto)
  {
    this.isAuto = isAuto;
  }
  
  public SystemState getCurrentState()
  {
    return this.currentState;
  }
  
  public void setCurrentState(SystemState currentState)
  {
    this.currentState = currentState;
  }
  
  public List<SystemState> getExecutedList()
  {
    return this.executedList;
  }
  
  public void setExecutedList(List<SystemState> executedList)
  {
    this.executedList = executedList;
  }
  
  public SystemState getNextState()
  {
    return this.nextState;
  }
  
  public void setNextState(SystemState nextState)
  {
    this.executedList.add(this.currentState);
    this.currentState = this.nextState;
    this.nextState = nextState;
    this.logger.debug("系统当前状态：" + this.currentState.getState());
    this.logger.debug("系统下一个状态：" + this.nextState.getState());
  }
  
  public void setRealNextState(SystemState nextState)
  {
    this.nextState = nextState;
  }
}
