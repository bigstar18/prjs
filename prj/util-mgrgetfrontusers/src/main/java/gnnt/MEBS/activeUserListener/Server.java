package gnnt.MEBS.activeUserListener;

import gnnt.MEBS.activeUserListener.actualize.LogonUserActualize;
import gnnt.MEBS.activeUserListener.dao.SelfLogonManagerDAO;
import gnnt.MEBS.activeUserListener.kernel.impl.LogonService_Standard;
import gnnt.MEBS.activeUserListener.po.Dictionary;
import gnnt.MEBS.logonService.util.GnntBeanFactory;
import gnnt.MEBS.logonService.util.Tool;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Server
{
  private final transient Log logger = LogFactory.getLog(getClass());
  private static volatile Server instance;
  
  public static Server getInstance()
  {
    if (instance == null) {
      synchronized (Server.class)
      {
        if (instance == null) {
          instance = (Server)GnntBeanFactory.getBean("server");
        }
      }
    }
    return instance;
  }
  
  public void initServer()
    throws Exception
  {
    SelfLogonManagerDAO localSelfLogonManagerDAO = (SelfLogonManagerDAO)GnntBeanFactory.getBean("selfLogonManagerDAO");
    List localList = localSelfLogonManagerDAO.getSysname();
    if ((localList != null) && (localList.size() > 0))
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Map localMap = (Map)localIterator.next();
        this.logger.info("--------监听的系统名称----------------" + localMap.get("SYSNAME"));
        DataSource localDataSource = (DataSource)GnntBeanFactory.getBean("dataSource");
        int i = Tool.strToInt(GnntBeanFactory.getConfig("clearRMITimes"), -1);
        LogonUserActualize.createInstance(localDataSource, localMap.get("SYSNAME").toString(), i);
      }
    }
    startRMI(localSelfLogonManagerDAO);
  }
  
  private void startRMI(SelfLogonManagerDAO paramSelfLogonManagerDAO)
    throws Exception
  {
    if (System.getSecurityManager() != null) {
      System.setSecurityManager(new RMISecurityManager());
    }
    String str1 = "";
    int i = 0;
    int j = 0;
    String str2 = "logonService";
    Dictionary localDictionary1 = paramSelfLogonManagerDAO.getLogonConfigByID("getLogonUser_ip");
    if (localDictionary1 != null) {
      str1 = localDictionary1.getValue();
    }
    Dictionary localDictionary2 = paramSelfLogonManagerDAO.getLogonConfigByID("getLogonUser_port");
    if (localDictionary2 != null) {
      i = Tool.strToInt(localDictionary2.getValue());
    }
    Dictionary localDictionary3 = paramSelfLogonManagerDAO.getLogonConfigByID("getLogonUser_dataPort");
    if (localDictionary3 != null) {
      j = Tool.strToInt(localDictionary3.getValue());
    }
    this.logger.info("-----------sysname---ip-------" + str1);
    this.logger.info("-----------sysname---port-------" + i);
    this.logger.info("-----------sysname---dataPort-------" + j);
    this.logger.info("-----------sysname---serverName-------" + str2);
    RMISocketFactory.setSocketFactory(new SMRMISocket(j));
    Registry localRegistry = LocateRegistry.getRegistry(i);
    if (localRegistry != null) {
      localRegistry = LocateRegistry.createRegistry(i);
    }
    LogonService_Standard localLogonService_Standard = new LogonService_Standard();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("rmi://").append(str1).append(":").append(i).append("/").append(str2);
    this.logger.info("启动 RMI 服务：" + localStringBuilder.toString());
    Naming.rebind(localStringBuilder.toString(), localLogonService_Standard);
  }
  
  private class SMRMISocket
    extends RMISocketFactory
  {
    private int dataPort = 1099;
    
    public SMRMISocket(int paramInt)
    {
      this.dataPort = paramInt;
    }
    
    public ServerSocket createServerSocket(int paramInt)
      throws IOException
    {
      if (paramInt <= 0) {
        paramInt = this.dataPort;
      }
      return new ServerSocket(paramInt);
    }
    
    public Socket createSocket(String paramString, int paramInt)
      throws IOException
    {
      return new Socket(paramString, paramInt);
    }
  }
}
