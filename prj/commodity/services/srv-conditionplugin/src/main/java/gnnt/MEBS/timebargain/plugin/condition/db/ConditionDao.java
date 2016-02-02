package gnnt.MEBS.timebargain.plugin.condition.db;

import gnnt.MEBS.timebargain.plugin.condition.Config;
import gnnt.MEBS.timebargain.plugin.condition.model.ConditionOrder;
import gnnt.MEBS.timebargain.plugin.condition.model.Quotation;
import gnnt.MEBS.timebargain.plugin.condition.model.RmiConf;
import gnnt.MEBS.timebargain.plugin.condition.model.SystemStatus;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract interface ConditionDao
{
  public abstract void getConnection(Config paramConfig);
  
  public abstract List<Quotation> getRuntimeQuotation(Timestamp paramTimestamp);
  
  public abstract Quotation getSingleQuotation(String paramString);
  
  public abstract long insertOrder(ConditionOrder paramConditionOrder);
  
  public abstract void cancelOneOrder(ConditionOrder paramConditionOrder);
  
  public abstract void updateOrder(ConditionOrder paramConditionOrder, Integer paramInteger1, Integer paramInteger2);
  
  public abstract void load_CP_orders(HashMap<String, ArrayList<ConditionOrder>> paramHashMap);
  
  public abstract void load_Buy1_orders(HashMap<String, ArrayList<ConditionOrder>> paramHashMap);
  
  public abstract void load_Sell1_orders(HashMap<String, ArrayList<ConditionOrder>> paramHashMap);
  
  public abstract SystemStatus getSystemStatus();
  
  public abstract void backUpOrder();
  
  public abstract RmiConf getRmiConf(int paramInt);
  
  public abstract RmiConf getTradeRmi(int paramInt);
}
