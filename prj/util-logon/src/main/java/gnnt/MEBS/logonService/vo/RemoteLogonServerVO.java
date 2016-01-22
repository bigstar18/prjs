package gnnt.MEBS.logonService.vo;

import gnnt.MEBS.logonService.kernel.ILogonService;
import gnnt.MEBS.logonService.po.LogonConfigPO;
import java.rmi.Naming;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RemoteLogonServerVO
{
  private final transient Log logger = LogFactory.getLog(getClass());
  public static final String serviceEnd = "LogonService";
  private LogonConfigPO logonConfigPO;
  private ILogonService rmiService;
  private int clearTimes = 0;

  public LogonConfigPO getLogonConfigPO()
  {
    return this.logonConfigPO;
  }

  public void setLogonConfigPO(LogonConfigPO paramLogonConfigPO)
  {
    clearRMI();
    this.clearTimes = 0;
    this.logonConfigPO = paramLogonConfigPO;
  }

  public ILogonService getRmiService()
    throws Exception
  {
    if (this.rmiService != null)
      return this.rmiService;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("rmi://").append(this.logonConfigPO.getHostIP()).append(":").append(this.logonConfigPO.getPort()).append("/").append(this.logonConfigPO.getServiceName()).append("LogonService");
    this.logger.info("连接 RMI 服务：" + localStringBuilder.toString());
    this.rmiService = ((ILogonService)Naming.lookup(localStringBuilder.toString()));
    return this.rmiService;
  }

  public void setRmiService(ILogonService paramILogonService)
  {
    this.rmiService = paramILogonService;
  }

  public int clearRMI()
  {
    this.clearTimes += 1;
    this.rmiService = null;
    return this.clearTimes;
  }
}