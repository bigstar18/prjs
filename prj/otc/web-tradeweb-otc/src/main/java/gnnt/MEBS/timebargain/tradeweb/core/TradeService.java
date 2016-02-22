package gnnt.MEBS.timebargain.tradeweb.core;

import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.tradeweb.model.Trade;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersManager;
import gnnt.MEBS.timebargain.tradeweb.util.DateUtil;
import gnnt.MEBS.timebargain.tradeweb.webapp.action.HttpXmlServlet;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeService
{
  private static final transient Log log = LogFactory.getLog(TradeService.class);
  private Map tradeMap = new HashMap();
  private Map broadcastMap = new HashMap();
  private OrdersManager mgr;
  private ServerRMI rmi;
  private long maxNo;
  private long BroadcastMaxNo;
  public static long diff;
  private Date dbTime = null;
  private static TradeService service;
  private HttpXmlServlet xmlServlet;
  private int isUpdate;
  private Date localDate = null;
  public String tradeDay = null;
  SystemStatus sysStatus = null;
  
  public static TradeService getInstance(OrdersManager mgr, HttpXmlServlet xmlServlet)
  {
    if (service == null) {
      synchronized (TradeService.class)
      {
        service = new TradeService(mgr, xmlServlet);
      }
    }
    return service;
  }
  
  private TradeService(OrdersManager mgr, HttpXmlServlet xmlServlet)
  {
    this.mgr = mgr;
    

    this.xmlServlet = xmlServlet;
    this.rmi = xmlServlet.getServerRMI();
    new TradeTaken(null).start();
    new CheckTime(null).start();
    new TradeDayThread(null).start();
    new BroadcastTaken(null).start();
  }
  
  private class TradeDayThread
    extends Thread
  {
    private TradeDayThread() {}
    
    public void run()
    {
      for (;;)
      {
        try
        {
          TradeService.this.sysStatus = TradeService.this.rmi.getSystemStatus();
          
          Date tradeDate = TradeService.this.sysStatus.getTradeDate();
          




          TradeService.this.tradeDay = new SimpleDateFormat("dd").format(tradeDate);
        }
        catch (ConnectException e)
        {
          TradeService.log.error("TradeDayThread Thread ConnectException" + 
            e.getMessage());
          e.printStackTrace();
          TradeService.errorException(e);
          TradeService.this.xmlServlet.setServerRMI(null);
          TradeService.this.rmi = TradeService.this.xmlServlet.getServerRMI();
          try
          {
            Thread.sleep(30000L);
          }
          catch (InterruptedException e)
          {
            TradeService.log.error("TradeDayThread Thread InterruptedException" + 
              e.getMessage());
            e.printStackTrace();
          }
          TradeService.errorException(e); continue;
        }
        catch (RemoteException e1)
        {
          TradeService.log.error("TradeDayThread Thread RemoteException" + 
            e1.getMessage());
          e1.printStackTrace();
          TradeService.errorException(e1);
          TradeService.this.xmlServlet.setServerRMI(null);
          TradeService.this.rmi = TradeService.this.xmlServlet.getServerRMI();
          try
          {
            Thread.sleep(30000L);
          }
          catch (InterruptedException e)
          {
            TradeService.log.error("TradeDayThread Thread InterruptedException" + 
              e.getMessage());
            e.printStackTrace();
          }
          TradeService.errorException(e); continue;
        }
        catch (NullPointerException e)
        {
          if (TradeService.this.rmi == null)
          {
            TradeService.log.error("rmi  NullPointerException" + e.getMessage());
            TradeService.this.rmi = TradeService.this.xmlServlet.getServerRMI();
          }
          else
          {
            TradeService.log.error("TradeDayThread Thread NullPointerException[系统状态对象为空]" + 
              e.getMessage());
          }
          e.printStackTrace();
          TradeService.errorException(e);
          try
          {
            Thread.sleep(30000L);
          }
          catch (InterruptedException e)
          {
            TradeService.log.error("TradeDayThread Thread InterruptedException" + 
              e.getMessage());
            e.printStackTrace();
          }
          TradeService.errorException(e); continue;
        }
        finally
        {
          try
          {
            Thread.sleep(30000L);
          }
          catch (InterruptedException e)
          {
            TradeService.log.error("TradeDayThread Thread InterruptedException" + 
              e.getMessage());
            e.printStackTrace();
            TradeService.errorException(e);
          }
        }
        try
        {
          Thread.sleep(30000L);
        }
        catch (InterruptedException e)
        {
          TradeService.log.error("TradeDayThread Thread InterruptedException" + 
            e.getMessage());
          e.printStackTrace();
          TradeService.errorException(e);
        }
      }
    }
  }
  
  private class TradeTaken
    extends Thread
  {
    long max = 0L;
    List tradeList = new LinkedList();
    int checkTime = 0;
    
    private TradeTaken() {}
    
    public void run()
    {
      TradeService.log.debug("The TradeTaken Thread has start..");
      for (;;)
      {
        try
        {
          this.max = TradeService.this.getMaxNo();
          
          long start = System.currentTimeMillis();
          this.tradeList = ((LinkedList)TradeService.this.mgr.tradequery(this.max));
          if ((this.tradeList != null) && (this.tradeList.size() > 0))
          {
            Trade trade0 = (Trade)this.tradeList.get(0);
            if ((trade0.getTradeNo().longValue() - this.max > 1L) && 
              (this.checkTime < 3))
            {
              this.checkTime += 1;
              try
              {
                Thread.sleep(1000L);
              }
              catch (InterruptedException e)
              {
                e.printStackTrace();
              }
              continue;
            }
            this.checkTime = 0;
            
            LinkedList linked = null;
            int i = 0; continue;
            Trade trade = (Trade)this.tradeList.get(i);
            String firmID = trade.getFirmID();
            linked = (LinkedList)TradeService.this.tradeMap.get(firmID);
            if (linked == null)
            {
              linked = new LinkedList();
              TradeService.this.tradeMap.put(firmID, linked);
            }
            linked.add(trade);i++;
            if (i < this.tradeList.size()) {
              continue;
            }
            TradeService.this.setMaxNo(((Trade)((LinkedList)this.tradeList).getLast())
              .getTradeNo().longValue());
          }
        }
        catch (Exception e)
        {
          TradeService.log.error("TradeTaken Thread Error" + e.getMessage());
          e.printStackTrace();
          TradeService.errorException(e);
          try
          {
            Thread.sleep(1000L);
          }
          catch (InterruptedException e) {}
          e.printStackTrace(); continue;
        }
        finally
        {
          try
          {
            Thread.sleep(1000L);
          }
          catch (InterruptedException e)
          {
            e.printStackTrace();
          }
        }
        try
        {
          Thread.sleep(1000L);
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    }
  }
  
  private class BroadcastTaken
    extends Thread
  {
    long max = 0L;
    List broadcastList = new LinkedList();
    
    private BroadcastTaken() {}
    
    public void run()
    {
      TradeService.log.info("The BroadcastTaken Thread has start..");
      for (;;)
      {
        try
        {
          this.max = TradeService.this.getBroadcastMaxNo();
          
          long start = System.currentTimeMillis();
          this.broadcastList = ((LinkedList)TradeService.this.mgr.getBroadcastList(this.max));
          if ((this.broadcastList != null) && (this.broadcastList.size() > 0))
          {
            int i = 0; continue;
            Map map = (Map)this.broadcastList.get(i);
            String firmID = (String)map.get("firmid");
            BigDecimal maxID = (BigDecimal)map.get("ID");
            TradeService.this.broadcastMap.put(firmID, maxID);i++;
            if (i < this.broadcastList.size()) {
              continue;
            }
            Map lastMap = 
              (Map)((LinkedList)this.broadcastList).getLast();
            
            TradeService.this.setBroadcastMaxNo(((BigDecimal)lastMap.get("ID"))
              .longValue());
          }
        }
        catch (Exception e)
        {
          TradeService.log.error("TradeTaken Thread Error" + e.getMessage());
          e.printStackTrace();
          TradeService.errorException(e);
          try
          {
            Thread.sleep(1000L);
          }
          catch (InterruptedException e) {}
          e.printStackTrace();
        }
        finally
        {
          try
          {
            Thread.sleep(1000L);
          }
          catch (InterruptedException e)
          {
            e.printStackTrace();
          }
        }
      }
    }
  }
  
  private class CheckTime
    extends Thread
  {
    private CheckTime() {}
    
    public void run()
    {
      TradeService.this.dbTime = TradeService.this.mgr.getDBTime();
      TradeService.log.debug("dbTime :" + TradeService.this.dbTime);
      
      TradeService.diff = TradeService.this.dbTime.getTime() - System.currentTimeMillis();
      TradeService.log.debug("当前时间-数据库时间 diff :" + TradeService.this.dbTime.getTime() + " - " + 
        System.currentTimeMillis() + "=" + TradeService.diff);
      
      long beatTime = new Date().getTime();
      SystemStatus sysStatus = null;
      for (;;)
      {
        try
        {
          if (TradeService.this.rmi == null)
          {
            TradeService.this.xmlServlet.setServerRMI(null);
            TradeService.this.rmi = TradeService.this.xmlServlet.getServerRMI();
            Thread.sleep(1000L);
            try
            {
              Thread.sleep(1000L);
            }
            catch (InterruptedException e1)
            {
              e1.printStackTrace();
            }
            continue;
          }
          sysStatus = TradeService.this.rmi.getSystemStatus();
          if (System.currentTimeMillis() - beatTime > 60000L)
          {
            TradeService.log.info("TradeDate =" + sysStatus.getTradeDate());
            beatTime = System.currentTimeMillis();
          }
          Date sysdate = sysStatus.getTradeDate();
          if (sysdate == null)
          {
            TradeService.log.info("系统还未设置交易时间");
            Thread.sleep(1000L);
            try
            {
              Thread.sleep(1000L);
            }
            catch (InterruptedException e1)
            {
              e1.printStackTrace();
            }
            continue;
          }
          if (TradeService.this.localDate == null) {
            TradeService.this.localDate = sysdate;
          }
          if (TradeService.this.date2int(sysdate) != TradeService.this.date2int(TradeService.this.localDate))
          {
            TradeService.log.info("交易日切换，重新校正DIFF，并清空成交缓存");
            TradeService.this.localDate = sysdate;
            TradeService.this.dbTime = TradeService.this.mgr.getDBTime();
            TradeService.diff = TradeService.this.dbTime.getTime() - System.currentTimeMillis();
            TradeService.this.tradeMap.clear();
            

            TradeService.this.setMaxNo(0L);
          }
        }
        catch (ConnectException e)
        {
          TradeService.log.error("CheckTime Thread ConnectException" + 
            e.getMessage());
          e.printStackTrace();
          TradeService.errorException(e);
          TradeService.this.xmlServlet.setServerRMI(null);
          TradeService.this.rmi = TradeService.this.xmlServlet.getServerRMI();
          try
          {
            Thread.sleep(1000L);
          }
          catch (InterruptedException e1) {}
          e1.printStackTrace(); continue;
        }
        catch (RemoteException e1)
        {
          TradeService.log.error("CheckTime Thread RemoteException" + 
            e1.getMessage());
          e1.printStackTrace();
          TradeService.errorException(e1);
          TradeService.this.xmlServlet.setServerRMI(null);
          TradeService.this.rmi = TradeService.this.xmlServlet.getServerRMI();
          try
          {
            Thread.sleep(1000L);
          }
          catch (InterruptedException e1) {}
          e1.printStackTrace(); continue;
        }
        catch (Exception ex)
        {
          TradeService.log.error("CheckTime Thread Error" + ex.getMessage());
          TradeService.errorException(ex);
          ex.printStackTrace();
          try
          {
            Thread.sleep(1000L);
          }
          catch (InterruptedException e1) {}
          e1.printStackTrace(); continue;
        }
        finally
        {
          try
          {
            Thread.sleep(1000L);
          }
          catch (InterruptedException e1)
          {
            e1.printStackTrace();
          }
        }
        try
        {
          Thread.sleep(1000L);
        }
        catch (InterruptedException e1)
        {
          e1.printStackTrace();
        }
      }
    }
  }
  
  public SystemStatus getSysStatus()
  {
    return this.sysStatus;
  }
  
  public long getBroadcastMaxNo()
  {
    return this.BroadcastMaxNo;
  }
  
  public void setBroadcastMaxNo(long broadcastMaxNo)
  {
    this.BroadcastMaxNo = broadcastMaxNo;
  }
  
  public Map getTradeMap()
  {
    return this.tradeMap;
  }
  
  public void setTradeMap(Map tradeMap)
  {
    this.tradeMap = tradeMap;
  }
  
  public Map getBroadcastMap()
  {
    return this.broadcastMap;
  }
  
  public void setBroadcastMap(Map broadcastMap)
  {
    this.broadcastMap = broadcastMap;
  }
  
  public long getMaxNo()
  {
    return this.maxNo;
  }
  
  public void setMaxNo(long maxNo)
  {
    this.maxNo = maxNo;
  }
  
  private int date2int(Date date)
  {
    String s_date = DateUtil.formatDate(date, "yyyyMMdd");
    
    return Integer.valueOf(s_date).intValue();
  }
  
  public void setServerRMI(ServerRMI rmi)
  {
    this.rmi = rmi;
  }
  
  public int getIsUpdate()
  {
    return this.isUpdate;
  }
  
  public void setIsUpdate(int isUpdate)
  {
    this.isUpdate = isUpdate;
  }
  
  public static void errorException(Exception e)
  {
    StackTraceElement[] ste = e.getStackTrace();
    log.error(e.getMessage());
    for (int i = 0; i < ste.length; i++) {
      log.error(ste[i].toString());
    }
  }
  
  public static void main(String[] args)
  {
    Map broadcastMap = new HashMap();
    BigDecimal b = new BigDecimal(1000);
    broadcastMap.put("111", b);
    broadcastMap.put("111", b);
    broadcastMap.put("111", b);
    broadcastMap.put("111", b);
    BigDecimal b1 = new BigDecimal(12345);
    broadcastMap.put("111", b1);
    System.out.println(broadcastMap.get("111"));
  }
}
