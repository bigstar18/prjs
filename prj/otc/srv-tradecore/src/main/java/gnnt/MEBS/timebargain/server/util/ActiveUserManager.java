package gnnt.MEBS.timebargain.server.util;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

public class ActiveUserManager
{
  private static int SINGLE_MODE = 1;
  private static int MULTI_MODE = 2;
  private Hashtable activeStore = new Hashtable(600);
  private AUThread auThread = null;
  private Random random = new Random();
  private int userCount = 0;
  private Object countLock = new Object();
  private boolean auThreadFlag = false;
  private int mode = MULTI_MODE;
  private int space = 30;
  private int expireTime = 15;
  
  public ActiveUserManager()
  {
    if (!this.auThreadFlag)
    {
      this.auThreadFlag = true;
      this.auThread = new AUThread(this.activeStore, this.space * 1000, this.expireTime * 60000);
      this.auThread.start();
    }
  }
  
  public ActiveUserManager(int space, int expireTime, int mode)
  {
    if (!this.auThreadFlag)
    {
      this.space = space;
      this.expireTime = expireTime;
      this.mode = mode;
      

      this.auThreadFlag = true;
      this.auThread = new AUThread(this.activeStore, space * 1000, expireTime * 60000);
      this.auThread.start();
    }
  }
  
  public long logon(String userID)
  {
    return logon(userID, null);
  }
  
  public long logon(String userID, String logonIP)
  {
    AUValue au = new AUValue();
    au.setUserName(userID);
    au.setLogonIP(logonIP);
    

    long sessionID = createSessionID();
    while (this.activeStore.containsKey(new Long(sessionID))) {
      sessionID = createSessionID();
    }
    if (this.mode == SINGLE_MODE)
    {
      Enumeration sessionKeys = this.activeStore.keys();
      
      AUValue av = null;
      while (sessionKeys.hasMoreElements())
      {
        Long keyObject = (Long)sessionKeys.nextElement();
        
        av = (AUValue)this.activeStore.get(keyObject);
        if (av.getUserName().equals(userID))
        {
          this.activeStore.remove(keyObject);
          decreaseUserCount();
        }
      }
      this.activeStore.put(new Long(sessionID), au);
    }
    else if (this.mode == MULTI_MODE)
    {
      this.activeStore.put(new Long(sessionID), au);
    }
    else
    {
      return -1L;
    }
    addUserCount();
    return sessionID;
  }
  
  public void logoff(long sessionID)
  {
    this.activeStore.remove(new Long(sessionID));
    decreaseUserCount();
  }
  
  public int logoffUser(String userID)
  {
    int n = 0;
    Enumeration sessionKeys = this.activeStore.keys();
    
    AUValue av = null;
    while (sessionKeys.hasMoreElements())
    {
      Long keyObject = (Long)sessionKeys.nextElement();
      
      av = (AUValue)this.activeStore.get(keyObject);
      if (av.getUserName().equals(userID))
      {
        this.activeStore.remove(keyObject);
        decreaseUserCount();
        n++;
      }
    }
    return n;
  }
  
  public String getUserID(long sessionID)
  {
    AUValue av = (AUValue)this.activeStore.get(new Long(sessionID));
    if (av == null) {
      return null;
    }
    av.setLastTime(System.currentTimeMillis());
    return av.getUserName();
  }
  
  public String[] getAllUsers()
  {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    Enumeration en = this.activeStore.elements();
    
    Vector v1 = new Vector();
    AUValue av = null;
    StringBuffer tempBuffer = null;
    while (en.hasMoreElements())
    {
      av = (AUValue)en.nextElement();
      tempBuffer = new StringBuffer(av.getUserName());
      tempBuffer.append(",");
      tempBuffer.append(formatter.format(av.getLogonTime()));
      tempBuffer.append(",");
      tempBuffer.append(av.getLogonIP());
      v1.addElement(tempBuffer.toString());
    }
    String[] tmp = new String[v1.size()];
    v1.toArray(tmp);
    return tmp;
  }
  
  public Object getPrivateDate(long sessionID, String key)
  {
    return ((AUValue)this.activeStore.get(new Long(sessionID))).getPersonalData(key);
  }
  
  public void setPrivateDate(long sessionID, String key, Object obj)
  {
    ((AUValue)this.activeStore.get(new Long(sessionID))).setPersonalData(key, obj);
  }
  
  private long createSessionID()
  {
    long t1 = 0x7FFFFFFF & System.currentTimeMillis();
    return t1 << 32 | Math.abs(this.random.nextInt());
  }
  
  protected void addUserCount()
  {
    synchronized (this.countLock)
    {
      this.userCount += 1;
    }
  }
  
  protected void decreaseUserCount()
  {
    synchronized (this.countLock)
    {
      if (this.userCount > 0) {
        this.userCount -= 1;
      }
    }
  }
  
  class AUThread
    extends Thread
  {
    private Hashtable activeStore = null;
    private int space;
    private long expireTime;
    
    public AUThread(Hashtable activeStore, int space, long expireTime)
    {
      this.space = space;
      this.expireTime = expireTime;
      this.activeStore = activeStore;
    }
    
    public void run()
    {
      while (ActiveUserManager.this.auThreadFlag) {
        try
        {
          sleep(this.space);
          checkExpire();
        }
        catch (InterruptedException e)
        {
          System.out.println("AU 监视线程失败");
        }
        catch (NullPointerException e)
        {
          System.out.println("checkExire catch NullPointer");
        }
      }
    }
    
    private void checkExpire()
    {
      Long sessionID = null;
      ActiveUserManager.AUValue av = null;
      Enumeration sessionKeys = this.activeStore.keys();
      long curTime = System.currentTimeMillis();
      while (sessionKeys.hasMoreElements())
      {
        sessionID = (Long)sessionKeys.nextElement();
        av = (ActiveUserManager.AUValue)this.activeStore.get(sessionID);
        if (curTime - av.getLastTime() > this.expireTime)
        {
          if (av.getUserName() != null) {
            ActiveUserManager.this.decreaseUserCount();
          }
          this.activeStore.remove(sessionID);
        }
      }
    }
    
    public synchronized void wakeup()
    {
      notify();
    }
  }
  
  class AUValue
  {
    private String userName = null;
    private long lastTime = 0L;
    private Hashtable personalStore = new Hashtable();
    private Date logonTime = null;
    private String logonIP = null;
    
    public AUValue() {}
    
    protected void setUserName(String userName)
    {
      this.userName = userName;
    }
    
    protected String getUserName()
    {
      return this.userName;
    }
    
    protected void setPersonalData(String key, Object data)
    {
      this.personalStore.put(key, data);
    }
    
    protected Object getPersonalData(String key)
    {
      return this.personalStore.get(key);
    }
    
    protected void setLastTime(long lastTime)
    {
      this.lastTime = lastTime;
    }
    
    protected long getLastTime()
    {
      return this.lastTime;
    }
    
    protected void clearData()
    {
      this.personalStore.clear();
    }
    
    protected Date getLogonTime()
    {
      return this.logonTime;
    }
    
    protected void setLogonIP(String logonIP)
    {
      this.logonIP = logonIP;
    }
    
    protected String getLogonIP()
    {
      return this.logonIP;
    }
  }
}
