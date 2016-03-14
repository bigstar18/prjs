package gnnt.MEBS.zcjs.memory.system.operate;

import gnnt.MEBS.zcjs.memory.system.RestMsg;
import gnnt.MEBS.zcjs.memory.system.SystemObject;
import gnnt.MEBS.zcjs.memory.system.SystemState;
import gnnt.MEBS.zcjs.memory.system.TradeTime;
import java.util.List;

public class OperateSystemObject
{
  public static int settingNextStatus(int status)
  {
    SystemState nextState = new SystemState();
    nextState.setAuto(false);
    nextState.setState(status);
    SystemObject systemObject = SystemObject.createInstance();
    systemObject.setRealNextState(nextState);
    return 0;
  }
  
  public static void settingStatus(int nextStatus)
  {
    SystemObject systemObject = SystemObject.createInstance();
    SystemState nextState = new SystemState();
    nextState.setAuto(systemObject.isAuto());
    nextState.setState(nextStatus);
    systemObject.setNextState(nextState);
  }
  
  public static int reloadFlowMode(boolean isAuto)
  {
    SystemObject systemObject = SystemObject.createInstance();
    systemObject.setAuto(isAuto);
    return 0;
  }
  
  public static int reloadRestMsg(RestMsg restMsg)
  {
    SystemObject systemObject = SystemObject.createInstance();
    systemObject.setRestMsg(restMsg);
    return 0;
  }
  
  public static int reloadTradeTime(List<TradeTime> tradeTimeList)
  {
    SystemObject systemObject = SystemObject.createInstance();
    systemObject.setTradeTimeList(tradeTimeList);
    return 0;
  }
}
