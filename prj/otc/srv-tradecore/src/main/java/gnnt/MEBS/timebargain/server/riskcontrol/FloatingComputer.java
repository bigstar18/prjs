package gnnt.MEBS.timebargain.server.riskcontrol;

import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.model.Member;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FloatingComputer
{
  private Log log = LogFactory.getLog(getClass());
  private long timespace;
  private Map<String, FloatingComputerThread> threadMap;
  private static FloatingComputer instance;
  
  public static FloatingComputer getInstance()
  {
    if (instance == null) {
      instance = new FloatingComputer();
    }
    return instance;
  }
  
  public void init()
  {
    close();
    
    this.timespace = Long.parseLong(
      DAOBeanFactory.getConfig("FloatingSpace"));
    this.threadMap = new HashMap();
    for (String m_FirmID : ServerInit.getMemberQueue().keySet())
    {
      FloatingComputerThread thread = new FloatingComputerThread();
      thread.setPriority(1);
      thread.init(this.timespace, m_FirmID);
      thread.start();
      this.threadMap.put(m_FirmID, thread);
    }
    this.log.info("启动了【" + this.threadMap.size() + "】个浮亏计算线程！");
  }
  
  public void addOneFloatingComputerThread(Member member)
  {
    this.log.info("addOneFloatingComputerThread member:" + member);
    FloatingComputerThread thread = new FloatingComputerThread();
    thread.init(this.timespace, member.getM_FirmID());
    thread.setStatus(RiskcontrolEngine.getInstance().getStatus());
    thread.start();
    this.threadMap.put(member.getM_FirmID(), thread);
  }
  
  public void close()
  {
    if (this.threadMap != null) {
      for (FloatingComputerThread thread : this.threadMap.values()) {
        if (thread != null)
        {
          thread.close();
          thread = null;
        }
      }
    }
    this.threadMap = null;
  }
  
  public void setStatus(int status)
  {
    if (this.threadMap != null) {
      for (FloatingComputerThread thread : this.threadMap.values()) {
        if (thread != null) {
          thread.setStatus(status);
        }
      }
    }
  }
  
  public long getTimespace()
  {
    return this.timespace;
  }
}
