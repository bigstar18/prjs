package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;
import gnnt.MEBS.timebargain.plugin.condition.rmi.ConditionRMI;
import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
import gnnt.MEBS.timebargain.server.util.SysConfig;
import gnnt.MEBS.timebargain.tradeweb.core.TradeService;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.ResponseResult;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class HttpXmlServlet
  extends HttpServlet
{
  private static final long serialVersionUID = 3906934490856239410L;
  private final Log log = LogFactory.getLog(HttpXmlServlet.class);
  public static TradeRMI tradeRMI = null;
  private static ApplicationContext ctx = null;
  private String strTradeRMI = null;
  public static ServerRMI serverRMI = null;
  protected String strServerRMI = null;
  public ConditionRMI conditionRMI = null;
  protected TradeService taken = null;
  private boolean debugcommodity = false;
  private HashMap sortKeyMap = new HashMap();
  private HashMap orderKeyMap;
  private HashMap holdKeyMap;
  private HashMap tradeKeyMap;
  public ServletContext config = null;
  public static String host = "";
  public static String port = "";
  public LogUserAction logUserAction = null;
  public OrderAction orderAction = null;
  public TradeAction tradeAction = null;
  public QueryAction queryAction = null;
  public ConditionAction conditionAction = null;
  protected static HashMap<String, String> lanaguages = new HashMap();
  Properties properties = new Properties();
  String language = null;
  public static Map<String, Properties> propertiesMap = new HashMap();
  
  public void init()
    throws ServletException
  {
    initRMI();
    OrdersManager localOrdersManager = (OrdersManager)getBean("ordersManager");
    this.taken = TradeService.getInstance(localOrdersManager, this);
    this.sortKeyMap.put("01", this.orderKeyMap);
    this.sortKeyMap.put("02", this.holdKeyMap);
    this.sortKeyMap.put("03", this.tradeKeyMap);
    this.config = getServletContext();
    if (this.logUserAction == null) {
      this.logUserAction = new LogUserAction(this.taken);
    }
    if (this.orderAction == null) {
      this.orderAction = new OrderAction();
    }
    if (this.tradeAction == null) {
      this.tradeAction = new TradeAction(this.taken);
    }
    if (this.queryAction == null) {
      this.queryAction = new QueryAction();
    }
    if (this.conditionAction == null) {
      this.conditionAction = new ConditionAction();
    }
    int i = Integer.parseInt(this.config.getInitParameter("ServiceModule"));
    this.conditionAction.getRmiConf(i);
    loadInternationalProps();
  }
  
  public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException
  {
    execute(paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException
  {
    execute(paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public void execute(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException
  {
    ResponseResult localResponseResult = new ResponseResult();
    String str1 = null;
    String str2 = null;
    try
    {
      str2 = readXMLFromRequestBody(paramHttpServletRequest);
      str1 = getReqNameByXml(str2);
      if (this.language == null)
      {
        this.language = getValueByTagName(str2, "LA");
        international(this.language);
      }
    }
    catch (Exception localException)
    {
      this.log.error("Servlet出错:" + localException);
      errorException(localException);
      localException.printStackTrace();
      localResponseResult.setRetCode(-203);
      localResponseResult.setMessage(this.properties.getProperty("-203"));
    }
    if (str1 == null) {
      str1 = "";
    }
    localResponseResult.setName(str1);
    if (str1.equals("logon"))
    {
      this.logUserAction.logon(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("logoff"))
    {
      this.logUserAction.logoff(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("change_password"))
    {
      this.logUserAction.change_password(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("commodity_query"))
    {
      this.logUserAction.commodity_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("order"))
    {
      this.orderAction.order(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("order_wd"))
    {
      this.orderAction.order_wd(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("tradequery"))
    {
      this.tradeAction.tradequery(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("tradepagingquery"))
    {
      this.tradeAction.tradepagingquery(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("firm_info"))
    {
      this.logUserAction.firm_info(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("sys_time_query"))
    {
      this.logUserAction.sys_time_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("my_order_query"))
    {
      this.orderAction.my_order_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("my_weekorder_query"))
    {
      this.orderAction.my_weekorder_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("my_weekorder_pagingquery"))
    {
      this.orderAction.my_weekorder_pagingquery(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("holding_query"))
    {
      this.queryAction.holding_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("market_query"))
    {
      this.queryAction.market_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("commodity_data_query"))
    {
      this.queryAction.commodity_data_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("check_user"))
    {
      this.logUserAction.check_user(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("directfirmbreed_query"))
    {
      this.queryAction.directfirmbreed_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("holdpositionbyprice"))
    {
      this.queryAction.holdpositionbyprice(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("querydateqty"))
    {
      this.queryAction.queryDateQty(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("verify_version"))
    {
      verify_version(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("conditionorder"))
    {
      this.conditionAction.conditionOrder(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("conditionorder_query"))
    {
      this.conditionAction.conditionOrderPageQuery(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else if (str1.equals("conditionorder_wd"))
    {
      this.conditionAction.withDrawConditionOrder(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "http");
    }
    else
    {
      if ((localResponseResult.getMessage() == null) || (localResponseResult.getMessage().equals("")))
      {
        localResponseResult.setRetCode(-203);
        localResponseResult.setMessage(this.properties.getProperty("-211"));
      }
      renderXML(paramHttpServletResponse, ResponseXml.responseXml("", localResponseResult.getRetCode(), localResponseResult.getMessage()));
    }
  }
  
  public void verify_version(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1, ResponseResult paramResponseResult, String paramString2)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'verify_version' method ");
    }
    int i = 0;
    String str1 = "";
    String str2 = "";
    String str3 = "";
    String str4 = "";
    try
    {
      String str5 = getLogonType(paramString2);
      str2 = getValueByTagName(paramString1, "USER_ID");
      if ("".equals(str2)) {
        str2 = getValueByTagName(paramString1, "TRADER_ID");
      }
      international((String)lanaguages.get(str2));
      long l = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
      str4 = getValueByTagName(paramString1, "MODULE_ID");
      String str6 = getValueByTagName(paramString1, "CLIENTVERSION");
      str3 = getServletContext().getInitParameter("versionInfo");
      Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
      localPrivilege.setClientVersion(str6);
      System.out.println(str2);
      System.out.println(l);
      if (!isLogon(paramHttpServletRequest, str2, l, str5))
      {
        i = -201;
        str1 = this.properties.getProperty("-205");
      }
    }
    catch (ConnectException localConnectException)
    {
      this.log.error("verify_version rmi conection exception" + localConnectException);
      i = -202;
      str1 = this.properties.getProperty("-201");
      initRMI();
    }
    catch (RemoteException localRemoteException)
    {
      this.log.error("verify_version remoteerror:" + localRemoteException);
      errorException(localRemoteException);
      i = -204;
      str1 = this.properties.getProperty("-204");
    }
    catch (Exception localException)
    {
      this.log.error("verify_version error:" + localException);
      errorException(localException);
      i = -203;
      str1 = this.properties.getProperty("-203");
    }
    paramResponseResult.setRetCode(i);
    paramResponseResult.setMessage(str1);
    paramResponseResult.setUserID(str2);
    renderXML(paramHttpServletResponse, ResponseXml.verify_version(paramResponseResult, str4, str3));
  }
  
  protected boolean isLogon(HttpServletRequest paramHttpServletRequest, String paramString1, long paramLong, String paramString2)
    throws Exception
  {
    this.log.debug("traderID=====" + paramString1 + "        sessionID========" + paramLong);
    boolean bool = tradeRMI.isLogon(paramString1, paramLong, paramString2);
    this.log.debug("isLogon==============" + bool);
    if (!bool) {
      return false;
    }
    if (paramHttpServletRequest.getSession().getAttribute("privilege") == null)
    {
      this.log.debug("====>Reload session ..");
      TraderLogonInfo localTraderLogonInfo = tradeRMI.getTraderInfo(paramString1);
      OrdersManager localOrdersManager = (OrdersManager)getBean("ordersManager");
      Privilege localPrivilege = localOrdersManager.getradePrivilege(localTraderLogonInfo);
      paramHttpServletRequest.getSession().setAttribute("privilege", localPrivilege);
    }
    return true;
  }
  
  @Deprecated
  private boolean checkUserExist(String paramString1, long paramLong, String paramString2)
    throws Exception
  {
    String str1 = getLogonType(paramString2);
    String str2 = tradeRMI.getUserID(paramLong, str1);
    return (str2 != null) && (str2.equals(paramString1));
  }
  
  protected void renderXML(HttpServletResponse paramHttpServletResponse, String paramString)
  {
    try
    {
      paramHttpServletResponse.setContentType("text/xml;charset=GBK");
      paramHttpServletResponse.getWriter().print(paramString);
    }
    catch (IOException localIOException)
    {
      this.log.error(localIOException.getMessage(), localIOException);
    }
  }
  
  protected String readXMLFromRequestBody(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    StringBuffer localStringBuffer = new StringBuffer();
    BufferedReader localBufferedReader = paramHttpServletRequest.getReader();
    String str = null;
    while ((str = localBufferedReader.readLine()) != null) {
      localStringBuffer.append(str);
    }
    if (!this.debugcommodity)
    {
      if ((getReqNameByXml(localStringBuffer.toString()).startsWith("commodity_")) || (getReqNameByXml(localStringBuffer.toString()).equals("sys_time_query"))) {
        return localStringBuffer.toString();
      }
      this.log.debug("readXMLFromRequestBody:userid=" + getValueByTagName(localStringBuffer.toString(), "USER_ID") + localStringBuffer.toString());
    }
    else
    {
      this.log.debug("readXMLFromRequestBody:userid=" + getValueByTagName(localStringBuffer.toString(), "USER_ID") + localStringBuffer.toString());
    }
    return localStringBuffer.toString();
  }
  
  public Object getBean(String paramString)
  {
    if (ctx == null) {
      ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
    }
    return ctx.getBean(paramString);
  }
  
  protected Privilege getSessionBean(HttpServletRequest paramHttpServletRequest)
  {
    return (Privilege)paramHttpServletRequest.getSession().getAttribute("privilege");
  }
  
  public void errorException(Exception paramException)
  {
    StackTraceElement[] arrayOfStackTraceElement = paramException.getStackTrace();
    this.log.error(paramException.getMessage());
    for (int i = 0; i < arrayOfStackTraceElement.length; i++) {
      this.log.error(arrayOfStackTraceElement[i].toString());
    }
  }
  
  public ServerRMI getServerRMI()
  {
    if (serverRMI == null) {
      initRMI();
    }
    return serverRMI;
  }
  
  public void initRMI()
  {
    Map localMap = SysConfig.getRMIConfig((DataSource)getBean("dataSource"));
    StringBuffer localStringBuffer1 = new StringBuffer();
    localStringBuffer1.append("rmi://").append(localMap.get("host")).append(":").append(localMap.get("port")).append("/TradeRMI");
    this.strTradeRMI = localStringBuffer1.toString();
    StringBuffer localStringBuffer2 = new StringBuffer();
    localStringBuffer2.append("rmi://").append(localMap.get("host")).append(":").append(localMap.get("port")).append("/ServerRMI");
    this.strServerRMI = localStringBuffer2.toString();
    try
    {
      tradeRMI = (TradeRMI)Naming.lookup(this.strTradeRMI);
      serverRMI = (ServerRMI)Naming.lookup(this.strServerRMI);
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    catch (NotBoundException localNotBoundException)
    {
      localNotBoundException.printStackTrace();
    }
  }
  
  protected String getReqNameByXml(String paramString)
  {
    String str1 = "";
    String str2 = paramString.toUpperCase();
    int i = str2.indexOf("NAME=");
    if (i < 0) {
      return str1;
    }
    int j = str2.indexOf(">", i);
    if (j < 0) {
      return str1;
    }
    String str3 = str2.substring(i + 5, j);
    str3 = str3.replaceAll("'", "");
    str3 = str3.replaceAll("\"", "");
    str3 = str3.trim();
    str1 = str3.toLowerCase();
    return str1;
  }
  
  public String getValueByTagName(String paramString1, String paramString2)
  {
    String str1 = "";
    String str2 = paramString1.replaceAll("</", "<");
    String[] arrayOfString = str2.split("<" + paramString2 + ">");
    if (arrayOfString.length > 1) {
      str1 = arrayOfString[1];
    }
    if (("COMMODITY_ID".equals(paramString2)) && (str1 != null) && (str1.length() > 2)) {
      str1 = str1.substring(2);
    }
    return str1;
  }
  
  protected long parseLong(String paramString)
  {
    try
    {
      return Long.parseLong(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return 0L;
  }
  
  public void international(String paramString)
  {
    if (propertiesMap == null) {
      loadInternationalProps();
    }
    if (paramString == null) {
      paramString = "0";
    }
    int i = 0;
    if (paramString.equalsIgnoreCase("0")) {
      i = 0;
    } else if (paramString.equalsIgnoreCase("1")) {
      i = 1;
    } else if (paramString.equalsIgnoreCase("2")) {
      i = 2;
    }
    switch (i)
    {
    case 0: 
      this.properties = ((Properties)propertiesMap.get("ApplicationResources_C"));
      break;
    case 1: 
      this.properties = ((Properties)propertiesMap.get("ApplicationResources_E"));
      break;
    case 2: 
      this.properties = ((Properties)propertiesMap.get("ApplicationResources_N"));
      break;
    default: 
      this.properties = ((Properties)propertiesMap.get("ApplicationResources_C"));
    }
  }
  
  private void loadInternationalProps()
  {
    Properties localProperties = null;
    InputStream localInputStream = null;
    String[] arrayOfString1 = { "ApplicationResources_C", "ApplicationResources_E", "ApplicationResources_N" };
    try
    {
      for (String str : arrayOfString1)
      {
        localInputStream = getClass().getResourceAsStream("/" + str + ".properties");
        localProperties = new Properties();
        localProperties.load(localInputStream);
        propertiesMap.put(str, localProperties);
      }
      return;
    }
    catch (IOException localIOException2)
    {
      this.log.error("读取国际化资源配置文件失败" + localIOException2);
    }
    finally
    {
      try
      {
        localInputStream.close();
      }
      catch (IOException localIOException4)
      {
        this.log.error("关闭输入流异常" + localIOException4);
      }
    }
  }
  
  public String getLogonType(String paramString)
    throws Exception
  {
    if (paramString.equals("http")) {
      return "pc";
    }
    if (paramString.equals("mobile")) {
      return "mobile";
    }
    throw new NullPointerException();
  }
}
