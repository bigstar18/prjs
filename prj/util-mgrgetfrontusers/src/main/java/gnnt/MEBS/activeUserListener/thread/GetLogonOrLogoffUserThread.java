package gnnt.MEBS.activeUserListener.thread;

import gnnt.MEBS.activeUserListener.actualize.LogonUserActualize;
import gnnt.MEBS.logonService.dao.LogonManagerDAO;
import gnnt.MEBS.logonService.kernel.ILogonService;
import gnnt.MEBS.logonService.po.LogonConfigPO;
import gnnt.MEBS.logonService.vo.LogonOrLogoffUserVO;
import gnnt.MEBS.logonService.vo.RemoteLogonServerVO;
import gnnt.MEBS.logonService.vo.UserManageVO;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;

public class GetLogonOrLogoffUserThread
  extends BaseThread
{
  public GetLogonOrLogoffUserThread(String paramString)
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
          getLogonOrLogoffUser();
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
          this.logger.error("执行查询最近登录退出用户信息线程异常", localException2);
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
  
  public void getLogonOrLogoffUser()
  {
    Map localMap = LogonUserActualize.getInstance(this.sysname).getLogonManagerMap();
    Iterator localIterator = localMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      LogonOrLogoffUserClass localLogonOrLogoffUserClass = new LogonOrLogoffUserClass((RemoteLogonServerVO)localMap.get(localInteger));
      localLogonOrLogoffUserClass.start();
    }
  }
  
  private class LogonOrLogoffUserClass
    extends Thread
  {
    private RemoteLogonServerVO remoteLogonServerVO;
    
    public LogonOrLogoffUserClass(RemoteLogonServerVO paramRemoteLogonServerVO)
    {
      this.remoteLogonServerVO = paramRemoteLogonServerVO;
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
          if ((LogonUserActualize.getInstance(GetLogonOrLogoffUserThread.this.sysname).getClearRMITimes() > 0) && (i > LogonUserActualize.getInstance(GetLogonOrLogoffUserThread.this.sysname).getClearRMITimes()))
          {
            LogonConfigPO localLogonConfigPO = LogonUserActualize.getInstance(GetLogonOrLogoffUserThread.this.sysname).getLogonManagerDAO().getLogonConfigByID(this.remoteLogonServerVO.getLogonConfigPO().getConfigID().intValue());
            if (localLogonConfigPO != null) {
              this.remoteLogonServerVO.setLogonConfigPO(localLogonConfigPO);
            }
          }
          getMessage();
        }
        catch (RemoteException localRemoteException2)
        {
          GetLogonOrLogoffUserThread.this.logger.error("获取 AU 登录退出信息时连接 AU 异常", localRemoteException1);
        }
        catch (Exception localException2)
        {
          GetLogonOrLogoffUserThread.this.logger.error("获取 AU 登录退出信息异常", localRemoteException1);
        }
      }
      catch (Exception localException1)
      {
        GetLogonOrLogoffUserThread.this.logger.error("获取 AU 登录退出信息异常", localException1);
      }
    }
    
    public void getMessage()
      throws RemoteException, Exception
    {
      List localList = this.remoteLogonServerVO.getRmiService().getLogonOrLogoffUserList();
      if ((localList != null) && (localList.size() > 0))
      {
        Map localMap = LogonUserActualize.getInstance(GetLogonOrLogoffUserThread.this.sysname).getLogonUserMap();
        synchronized (localMap)
        {
          Object localObject1 = (Map)localMap.get(this.remoteLogonServerVO.getLogonConfigPO().getConfigID());
          if (localObject1 == null)
          {
            localObject1 = new HashMap();
            localMap.put(this.remoteLogonServerVO.getLogonConfigPO().getConfigID(), localObject1);
          }
          Iterator localIterator1 = localList.iterator();
          LogonOrLogoffUserVO localLogonOrLogoffUserVO;
          UserManageVO localUserManageVO1;
          Object localObject2;
          Object localObject3;
          Object localObject4;
          while (localIterator1.hasNext())
          {
            localLogonOrLogoffUserVO = (LogonOrLogoffUserVO)localIterator1.next();
            if (localLogonOrLogoffUserVO.getLogonOrlogOff() == 1)
            {
              localUserManageVO1 = localLogonOrLogoffUserVO.getUserManageVO();
              localObject2 = (Map)((Map)localObject1).get(localUserManageVO1.getLogonType());
              if (localObject2 == null)
              {
                localObject2 = new HashMap();
                ((Map)localObject1).put(localUserManageVO1.getLogonType(), localObject2);
              }
              localObject3 = (List)((Map)localObject2).get(localUserManageVO1.getUserID());
              if (localObject3 == null)
              {
                localObject3 = new ArrayList();
                ((Map)localObject2).put(localUserManageVO1.getUserID(), localObject3);
              }
              int i = 0;
              localObject4 = ((List)localObject3).iterator();
              while (((Iterator)localObject4).hasNext())
              {
                UserManageVO localUserManageVO2 = (UserManageVO)((Iterator)localObject4).next();
                if (localUserManageVO2.getSessionID() == localUserManageVO1.getSessionID())
                {
                  i = 1;
                  break;
                }
              }
              if (i == 0)
              {
                GetLogonOrLogoffUserThread.this.logger.debug("添加用户[" + localUserManageVO1.getUserID() + "]在[" + localUserManageVO1.getLogonType() + "]的登录[" + localUserManageVO1.getSessionID() + "]，登录IP[" + localUserManageVO1.getLogonIp() + "]");
                ((List)localObject3).add(localUserManageVO1);
              }
            }
          }
          localIterator1 = localList.iterator();
          while (localIterator1.hasNext())
          {
            localLogonOrLogoffUserVO = (LogonOrLogoffUserVO)localIterator1.next();
            if (localLogonOrLogoffUserVO.getLogonOrlogOff() == 2)
            {
              localUserManageVO1 = localLogonOrLogoffUserVO.getUserManageVO();
              GetLogonOrLogoffUserThread.this.logger.debug("预退出用户：sessionID[" + localUserManageVO1.getSessionID() + "]userID[" + localUserManageVO1.getUserID() + "]type[" + localUserManageVO1.getLogonType() + "]");
              localObject2 = (Map)((Map)localObject1).get(localUserManageVO1.getLogonType());
              if (localObject2 == null)
              {
                GetLogonOrLogoffUserThread.this.logger.debug(localUserManageVO1.getLogonType() + "类型的userMap为空");
              }
              else
              {
                localObject3 = (List)((Map)localObject2).get(localUserManageVO1.getUserID());
                if ((localObject3 == null) || (((List)localObject3).size() <= 0))
                {
                  GetLogonOrLogoffUserThread.this.logger.debug(localUserManageVO1.getUserID() + "用户的 userList 为空");
                }
                else
                {
                  Iterator localIterator2 = ((List)localObject3).iterator();
                  while (localIterator2.hasNext())
                  {
                    localObject4 = (UserManageVO)localIterator2.next();
                    if (((UserManageVO)localObject4).getSessionID() == localUserManageVO1.getSessionID())
                    {
                      ((List)localObject3).remove(localObject4);
                      GetLogonOrLogoffUserThread.this.logger.debug("退出用户：sessionID[" + localUserManageVO1.getSessionID() + "]userID[" + localUserManageVO1.getUserID() + "]type[" + localUserManageVO1.getLogonType() + "]" + "logonIP[" + localUserManageVO1.getLogonIp() + "]");
                      if (((List)localObject3).size() > 0) {
                        break;
                      }
                      ((Map)localObject2).remove(localUserManageVO1.getUserID());
                      GetLogonOrLogoffUserThread.this.logger.debug("用户[" + localUserManageVO1.getUserID() + "]集合已经清空，去除用户 map");
                      break;
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
