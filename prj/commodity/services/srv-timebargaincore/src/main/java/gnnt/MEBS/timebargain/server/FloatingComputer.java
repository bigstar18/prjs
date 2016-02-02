package gnnt.MEBS.timebargain.server;

import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FloatingComputer
{
  private Log log = LogFactory.getLog(getClass());
  private long timespace;
  private int firmSection;
  private TradeDAO tradeDAO;
  private List threadList = null;
  private static FloatingComputer instance;
  public static final int STATUS_COMPUTING = 0;
  public static final int STATUS_PAUSE = 1;
  
  public static FloatingComputer getInstance()
  {
    if (instance == null) {
      instance = new FloatingComputer();
    }
    return instance;
  }
  
  public void init(Server paramServer)
  {
    this.timespace = Long.parseLong(DAOBeanFactory.getConfig("FloatingSpace"));
    this.firmSection = Integer.parseInt(DAOBeanFactory.getConfig("FloatingFirmSection"));
    this.tradeDAO = paramServer.getTradeDAO();
    close();
    this.threadList = new ArrayList();
    List localList = paramServer.getServerDAO().getFirmIDList();
    if ((localList != null) && (localList.size() > 0))
    {
      int i = localList.size() / this.firmSection;
      Object localObject;
      if (i == 0)
      {
        localObject = new FloatingComputerThread();
        ((FloatingComputerThread)localObject).init(this, null, null);
        ((FloatingComputerThread)localObject).start();
        this.threadList.add(localObject);
      }
      else
      {
        for (int j = 0; j < this.firmSection; j++)
        {
          FloatingComputerThread localFloatingComputerThread2 = new FloatingComputerThread();
          String str;
          if (j == 0)
          {
            localObject = null;
            str = (String)localList.get((j + 1) * i - 1);
          }
          else if (j == this.firmSection - 1)
          {
            localObject = (String)localList.get(j * i);
            str = null;
          }
          else
          {
            localObject = (String)localList.get(j * i);
            str = (String)localList.get((j + 1) * i - 1);
          }
          localFloatingComputerThread2.init(this, (String)localObject, str);
          localFloatingComputerThread2.start();
          this.threadList.add(localFloatingComputerThread2);
        }
      }
    }
    else
    {
      FloatingComputerThread localFloatingComputerThread1 = new FloatingComputerThread();
      localFloatingComputerThread1.init(this, null, null);
      localFloatingComputerThread1.start();
      this.threadList.add(localFloatingComputerThread1);
    }
  }
  
  public void close()
  {
    if (this.threadList != null) {
      for (int i = 0; i < this.threadList.size(); i++)
      {
        FloatingComputerThread localFloatingComputerThread = (FloatingComputerThread)this.threadList.get(i);
        if (localFloatingComputerThread != null)
        {
          localFloatingComputerThread.close();
          localFloatingComputerThread = null;
        }
      }
    }
    this.threadList = null;
  }
  
  public void setStatus(int paramInt)
  {
    if (this.threadList != null) {
      for (int i = 0; i < this.threadList.size(); i++)
      {
        FloatingComputerThread localFloatingComputerThread = (FloatingComputerThread)this.threadList.get(i);
        if (localFloatingComputerThread != null) {
          localFloatingComputerThread.setStatus(paramInt);
        }
      }
    }
  }
  
  public long getTimespace()
  {
    return this.timespace;
  }
  
  public TradeDAO getTradeDAO()
  {
    return this.tradeDAO;
  }
}
