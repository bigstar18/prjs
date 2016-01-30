package gnnt.MEBS.activeUserListener.thread;

import gnnt.MEBS.activeUserListener.actualize.LogonUserActualize;
import gnnt.MEBS.logonService.dao.LogonManagerDAO;
import gnnt.MEBS.logonService.kernel.ILogonService;
import gnnt.MEBS.logonService.po.LogonConfigPO;
import gnnt.MEBS.logonService.util.Tool;
import gnnt.MEBS.logonService.vo.LogonOrLogoffUserVO;
import gnnt.MEBS.logonService.vo.RemoteLogonServerVO;
import gnnt.MEBS.logonService.vo.UserManageVO;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;

public class GetAllLogonUserThread
  extends BaseThread
{
  private int justStart = 1;
  
  public GetAllLogonUserThread(String paramString)
  {
    this.sysname = paramString;
    this.timeSpace = 1000L;
  }
  
  public void run()
  {
    this.stop = false;
    for (;;)
    {
      if (!this.stop) {
        try
        {
          getLogonUser();
          try
          {
            Thread.sleep(this.timeSpace);
          }
          catch (Exception localException1)
          {
            this.logger.debug("执行线程休眠异常", localException1);
          }
        }
        catch (Exception localException2)
        {
          this.logger.error("执行查询当前所有登录用户线程异常", localException2);
        }
        finally
        {
          try
          {
            Thread.sleep(this.timeSpace);
          }
          catch (Exception localException4)
          {
            this.logger.debug("执行线程休眠异常", localException4);
          }
        }
      }
    }
  }
  
  private void getLogonUser()
  {
    Map localMap = LogonUserActualize.getInstance(this.sysname).getLogonManagerMap();
    Iterator localIterator = localMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      GetLogonUserClass localGetLogonUserClass = new GetLogonUserClass((RemoteLogonServerVO)localMap.get(localInteger), this.justStart);
      localGetLogonUserClass.start();
    }
    this.justStart = 2;
  }
  
  private class GetLogonUserClass
    extends Thread
  {
    private RemoteLogonServerVO remoteLogonServerVO;
    private int start;
    
    public GetLogonUserClass(RemoteLogonServerVO paramRemoteLogonServerVO, int paramInt)
    {
      this.remoteLogonServerVO = paramRemoteLogonServerVO;
      this.start = paramInt;
    }
    
    public void run()
    {
      try
      {
        getMessage();
      }
      catch (RemoteException localRemoteException1)
      {
        int i = this.remoteLogonServerVO.clearRMI();
        try
        {
          if ((LogonUserActualize.getInstance(GetAllLogonUserThread.this.sysname).getClearRMITimes() > 0) && (i > LogonUserActualize.getInstance(GetAllLogonUserThread.this.sysname).getClearRMITimes()))
          {
            LogonConfigPO localLogonConfigPO = LogonUserActualize.getInstance(GetAllLogonUserThread.this.sysname).getLogonManagerDAO().getLogonConfigByID(this.remoteLogonServerVO.getLogonConfigPO().getConfigID().intValue());
            if (localLogonConfigPO != null) {
              this.remoteLogonServerVO.setLogonConfigPO(localLogonConfigPO);
            }
          }
          getMessage();
        }
        catch (RemoteException localRemoteException2)
        {
          GetAllLogonUserThread.this.logger.error("获取 AU 所有登录信息时连接 AU 异常", localRemoteException1);
        }
        catch (Exception localException2)
        {
          GetAllLogonUserThread.this.logger.error("获取 AU 所有登录信息异常", localRemoteException1);
        }
      }
      catch (Exception localException1)
      {
        GetAllLogonUserThread.this.logger.error("获取 AU 所有登录信息异常", localException1);
      }
    }
    
    private void getMessage()
      throws RemoteException, Exception
    {
      int i = this.remoteLogonServerVO.getRmiService().isRestartStartRMI();
      if ((i == 1) || (this.start == 1))
      {
        Map localMap = LogonUserActualize.getInstance(GetAllLogonUserThread.this.sysname).getLogonUserMap();
        LinkedHashMap localLinkedHashMap = new LinkedHashMap();
        localMap.put(this.remoteLogonServerVO.getLogonConfigPO().getConfigID(), localLinkedHashMap);
        List localList = this.remoteLogonServerVO.getRmiService().getAllLogonUserList();
        if ((localList != null) && (localList.size() > 0)) {
          synchronized (localMap)
          {
            Iterator localIterator1 = localList.iterator();
            while (localIterator1.hasNext())
            {
              LogonOrLogoffUserVO localLogonOrLogoffUserVO = (LogonOrLogoffUserVO)localIterator1.next();
              UserManageVO localUserManageVO1 = localLogonOrLogoffUserVO.getUserManageVO();
              Object localObject1 = (Map)localLinkedHashMap.get(localUserManageVO1.getLogonType());
              if (localObject1 == null)
              {
                localObject1 = new HashMap();
                localLinkedHashMap.put(localUserManageVO1.getLogonType(), localObject1);
              }
              Object localObject2 = (List)((Map)localObject1).get(localUserManageVO1.getUserID());
              if (localObject2 == null)
              {
                localObject2 = new ArrayList();
                ((Map)localObject1).put(localUserManageVO1.getUserID(), localObject2);
              }
              int j = 0;
              Iterator localIterator2 = ((List)localObject2).iterator();
              while (localIterator2.hasNext())
              {
                UserManageVO localUserManageVO2 = (UserManageVO)localIterator2.next();
                if (localUserManageVO2.getSessionID() == localUserManageVO1.getSessionID())
                {
                  j = 1;
                  break;
                }
              }
              if (j == 0)
              {
                GetAllLogonUserThread.this.logger.debug("添加用户[" + localUserManageVO1.getUserID() + "]在[" + localUserManageVO1.getLogonType() + "]的登录[" + localUserManageVO1.getSessionID() + "]" + "登录时间[" + Tool.fmtTime(localUserManageVO1.getLogonTime()) + "]" + "登录IP: [" + localUserManageVO1.getLogonIp() + "}");
                ((List)localObject2).add(localUserManageVO1);
              }
            }
          }
        }
      }
    }
  }
}
