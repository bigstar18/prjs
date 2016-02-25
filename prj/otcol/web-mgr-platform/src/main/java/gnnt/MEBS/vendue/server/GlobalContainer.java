package gnnt.MEBS.vendue.server;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.vendue.server.dao.TradeDAO;
import gnnt.MEBS.vendue.server.dao.TradeDAOFactory;
import gnnt.MEBS.vendue.server.rmi.KernelEngineRMIImpl;
import gnnt.MEBS.vendue.server.vo.SysPartitionVO;
import gnnt.MEBS.vendue.util.Configuration;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

public class GlobalContainer
{
  private static Hashtable<String, SubmitAction> SUBMITACTIONTABLE = new Hashtable();
  private static Hashtable<String, KernelEngine> ENGINETABLE = new Hashtable();
  private static boolean LOADFLAG = false;
  
  public static synchronized void loadContainer()
    throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, Exception
  {
    if (!LOADFLAG)
    {
      Logger localLogger = Logger.getLogger("Kernellog");
      TradeDAO localTradeDAO = TradeDAOFactory.getDAO();
      SysPartitionVO[] arrayOfSysPartitionVO = localTradeDAO.getSysPartitionList("where validFlag=1");
      DataSource localDataSource = localTradeDAO.getDataSource();
      Map localMap = LogonManager.getRMIConfig("4", localDataSource);
      String str1 = (String)localMap.get("host");
      int i = ((Integer)localMap.get("port")).intValue();
      if (arrayOfSysPartitionVO != null)
      {
        Properties localProperties = new Configuration().getSection("MEBS.RMIServer");
        boolean bool = Boolean.valueOf(localProperties.getProperty("IsRMIServer")).booleanValue();
        if (bool)
        {
          Registry localRegistry = LocateRegistry.getRegistry(i);
          if (localRegistry != null) {
            localRegistry = LocateRegistry.createRegistry(i);
          }
        }
        for (int j = 0; j < arrayOfSysPartitionVO.length; j++)
        {
          SysPartitionVO localSysPartitionVO = arrayOfSysPartitionVO[j];
          String str2 = localSysPartitionVO.engineClass;
          String str3 = localSysPartitionVO.submitActionClass;
          int k = localSysPartitionVO.partitionID;
          KernelEngine localKernelEngine = null;
          localKernelEngine = (KernelEngine)Class.forName(str2).newInstance();
          localKernelEngine.setTradeDAO(localTradeDAO);
          localKernelEngine.setPartition(k);
          localKernelEngine.setTradeStatus();
          localKernelEngine.reLoadCommodity();
          localKernelEngine.resumeTradeStatus();
          ENGINETABLE.put(String.valueOf(k), localKernelEngine);
          localLogger.debug("========================================================================");
          localLogger.debug("==============>" + localSysPartitionVO.description + "板块交易核心对象已加载。");
          if (bool)
          {
            localObject = new Thread(localKernelEngine, "kernelThread" + k);
            ((Thread)localObject).start();
            localLogger.debug("==============>" + localSysPartitionVO.description + "板块系统监控进程已启动。");
            KernelEngineRMIImpl localKernelEngineRMIImpl = new KernelEngineRMIImpl(k);
            Naming.rebind("//" + str1 + ":" + i + "/KernelEngineRMI" + k, localKernelEngineRMIImpl);
            localLogger.debug("==============>" + localSysPartitionVO.description + "板块RMI服务已启动。" + "//" + str1 + ":" + i + "/KernelEngineRMI" + k);
          }
          Object localObject = null;
          localObject = (SubmitAction)Class.forName(str3).newInstance();
          ((SubmitAction)localObject).setTradeDAO(localTradeDAO);
          ((SubmitAction)localObject).setPartition(k);
          SUBMITACTIONTABLE.put(k + "", localObject);
          localLogger.debug("==============>" + localSysPartitionVO.description + "板块委托管理对象已加载。");
          localLogger.debug("========================================================================");
        }
      }
      LogonManager.createInstance("4", localDataSource, 30);
      localLogger.debug("==============>LogonManager对象已加载。");
    }
  }
  
  public static KernelEngine getEngine(int paramInt)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, NamingException
  {
    return (KernelEngine)ENGINETABLE.get(paramInt + "");
  }
  
  public static SubmitAction getSubmitAction(int paramInt)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, NamingException
  {
    return (SubmitAction)SUBMITACTIONTABLE.get(paramInt + "");
  }
}
