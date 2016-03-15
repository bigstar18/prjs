package cn.com.agree.eteller.generic.utils;

import cn.com.agree.eteller.generic.nnatp.impl.CommunicationNatp;
import java.io.EOFException;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendDataThread
  implements Runnable
{
  private static final Log logger = LogFactory.getLog(SendDataThread.class);
  private String connect = "pics_afa";
  private int natpVersion = 16;
  private String transCode;
  private String templateCode;
  private String reservedCode = "";
  private HttpSession session = null;
  private String MAPTYPE = null;
  private String sysId;
  private String unitno;
  private String function;
  private String procType;
  private String checkdate;
  private String jobcode;
  public Map map;
  private String[] filedname;
  private String[] filedvalue;
  private int i;
  
  public SendDataThread() {}
  
  public SendDataThread(String[] filedname, String[] filedvalue, Map map)
  {
    this.filedname = filedname;
    this.filedvalue = filedvalue;
    this.map = map;
  }
  
  public SendDataThread(String transCode, String templateCode, String[] filedname, String[] filedvalue, Map map)
  {
    this.transCode = transCode;
    this.templateCode = templateCode;
    this.filedname = filedname;
    this.filedvalue = filedvalue;
    this.map = map;
  }
  
  public SendDataThread(String connect, String transCode, String templateCode, String[] filedname, String[] filedvalue, Map map)
  {
    this.connect = connect;
    this.transCode = transCode;
    this.templateCode = templateCode;
    this.filedname = filedname;
    this.filedvalue = filedvalue;
    this.map = map;
  }
  
  public void run()
  {
    try
    {
      StarNatp();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void StarNatp()
    throws Exception
  {
    try
    {
      CommunicationNatp natp = new CommunicationNatp();
      natp.init(this.natpVersion, this.transCode, this.templateCode, this.reservedCode);
      for (int i = 0; i < this.filedname.length; i++) {
        natp.pack(this.filedname[i], this.filedvalue[i]);
      }
      natp.exchange(this.connect);
      String retcode = natp.unpack("errorCode", 1);
      String retmsg = natp.unpack("errorMsg", 1);
      this.map.put(this.map.size() + this.filedvalue[0] + "-" + this.filedvalue[1], retcode + retmsg);
    }
    catch (EOFException e)
    {
      String retmsg = e.getMessage();
      logger.info("与平台通讯错：[" + retmsg + "]");
      this.map.put(this.filedvalue[0] + "-" + this.filedvalue[1], "交易结果未知,请去\"查询日终作业处理状态\"中查询!");
      e.printStackTrace();
      throw e;
    }
    catch (Exception e)
    {
      String retmsg = e.getMessage();
      logger.info("与平台通讯错：[" + retmsg + "]");
      this.map.put(this.filedvalue[0] + "-" + this.filedvalue[1], retmsg);
      e.printStackTrace();
      throw e;
    }
  }
  
  public String[] getFiledname()
  {
    return this.filedname;
  }
  
  public void setFiledname(String[] filedname)
  {
    this.filedname = filedname;
  }
  
  public String[] getFiledvalue()
  {
    return this.filedvalue;
  }
  
  public void setFiledvalue(String[] filedvalue)
  {
    this.filedvalue = filedvalue;
  }
  
  public String getTemplateCode()
  {
    return this.templateCode;
  }
  
  public void setTemplateCode(String templateCode)
  {
    this.templateCode = templateCode;
  }
  
  public String getTransCode()
  {
    return this.transCode;
  }
  
  public void setTransCode(String transCode)
  {
    this.transCode = transCode;
  }
  
  public String getConnect()
  {
    return this.connect;
  }
  
  public void setConnect(String connect)
  {
    this.connect = connect;
  }
  
  public int getNatpVersion()
  {
    return this.natpVersion;
  }
  
  public void setNatpVersion(int natpVersion)
  {
    this.natpVersion = natpVersion;
  }
  
  public String getReservedCode()
  {
    return this.reservedCode;
  }
  
  public void setReservedCode(String reservedCode)
  {
    this.reservedCode = reservedCode;
  }
  
  public Map getMap()
  {
    return this.map;
  }
  
  public void setMap(Map map)
  {
    this.map = map;
  }
  
  public HttpSession getSession()
  {
    return this.session;
  }
  
  public void setSession(HttpSession session)
  {
    this.session = session;
  }
  
  public String getMAPTYPE()
  {
    return this.MAPTYPE;
  }
  
  public void setMAPTYPE(String maptype)
  {
    this.MAPTYPE = maptype;
  }
}
