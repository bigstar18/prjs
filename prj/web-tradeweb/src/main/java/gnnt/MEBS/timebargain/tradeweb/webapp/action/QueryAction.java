package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import gnnt.MEBS.timebargain.tradeweb.core.TradeService;
import gnnt.MEBS.timebargain.tradeweb.model.Market;
import gnnt.MEBS.timebargain.tradeweb.model.Orders;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.ResponseResult;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersManager;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersPagingManager;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QueryAction
  extends HttpXmlServlet
{
  private static final long serialVersionUID = -7168058428128821613L;
  private final Log log = LogFactory.getLog(HttpXmlServlet.class);
  private TradeService taken = null;
  private HashMap sortKeyMap = new HashMap();
  private HashMap orderKeyMap;
  private HashMap holdKeyMap;
  private HashMap tradeKeyMap;
  public ServletContext config = null;
  public static String host = "";
  public static String port = "";
  public MobileServlet mobileServlet = new MobileServlet();
  
  public QueryAction()
  {
    if ((serverRMI == null) || (tradeRMI == null)) {
      initRMI();
    }
    initHoldKeyMap();
  }
  
  protected void holding_query(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1, ResponseResult paramResponseResult, String paramString2)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'holding_query' method ");
    }
    int i = 0;
    String str1 = "";
    String str2 = "";
    try
    {
      String str3 = getLogonType(paramString2);
      str2 = getValueByTagName(paramString1, "USER_ID");
      if ("".equals(str2)) {
        str2 = getValueByTagName(paramString1, "TRADER_ID");
      }
      international((String)lanaguages.get(str2));
      String str4 = getValueByTagName(paramString1, "COMMODITY_ID");
      String str5 = getValueByTagName(paramString1, "STARTNUM");
      String str6 = getValueByTagName(paramString1, "RECCNT");
      String str7 = getValueByTagName(paramString1, "SORTFLD");
      String str8 = getValueByTagName(paramString1, "ISDESC") == "" ? "0" : "1";
      long l = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
      if (!isLogon(paramHttpServletRequest, str2, l, str3))
      {
        i = -201;
        str1 = this.properties.getProperty("-205");
      }
      else
      {
        OrdersManager localOrdersManager = (OrdersManager)getBean("ordersManager");
        Orders localOrders = new Orders();
        localOrders.setTraderID(str2);
        localOrders.setCommodityID(str4);
        SortCondition localSortCondition = new SortCondition();
        localSortCondition.setStartNu(str5);
        localSortCondition.setIsdesc(Integer.parseInt(str8));
        localSortCondition.setReccnt(str6);
        localSortCondition.setSortfLD((String)this.holdKeyMap.get(str7));
        Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
        List localList = localOrdersManager.holding_query(localOrders, localPrivilege, localSortCondition);
        if ((localList == null) || (localList.size() == 0))
        {
          i = -202;
          str1 = this.properties.getProperty("-209");
        }
        else
        {
          paramResponseResult.setResultList(localList);
        }
      }
    }
    catch (ConnectException localConnectException)
    {
      this.log.error("holding_query rmi conection exception" + localConnectException);
      i = -202;
      str1 = this.properties.getProperty("-201");
      initRMI();
    }
    catch (RemoteException localRemoteException)
    {
      this.log.error("holding_query remoteerror:" + localRemoteException);
      errorException(localRemoteException);
      i = -204;
      str1 = this.properties.getProperty("-204");
    }
    catch (Exception localException)
    {
      this.log.error("holding_query error:" + localException);
      errorException(localException);
      i = -203;
      str1 = this.properties.getProperty("-203");
    }
    paramResponseResult.setRetCode(i);
    paramResponseResult.setMessage(str1);
    paramResponseResult.setUserID(str2);
    if ("mobile".equals(paramString2)) {
      this.mobileServlet.renderXML(paramHttpServletResponse, ResponseXml.holding_query(paramResponseResult));
    } else {
      renderXML(paramHttpServletResponse, ResponseXml.holding_query(paramResponseResult));
    }
  }
  
  protected void market_query(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1, ResponseResult paramResponseResult, String paramString2)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'market_query' method ");
    }
    int i = 0;
    String str1 = "";
    String str2 = "";
    try
    {
      String str3 = getLogonType(paramString2);
      str2 = getValueByTagName(paramString1, "USER_ID");
      if ("".equals(str2)) {
        str2 = getValueByTagName(paramString1, "TRADER_ID");
      }
      international((String)lanaguages.get(str2));
      String str4 = getValueByTagName(paramString1, "MARKET_ID");
      long l = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
      if (!isLogon(paramHttpServletRequest, str2, l, str3))
      {
        i = -201;
        str1 = this.properties.getProperty("-205");
      }
      else
      {
        OrdersManager localOrdersManager = (OrdersManager)getBean("ordersManager");
        Market localMarket = new Market();
        localMarket.setMarketCode(str4);
        List localList = localOrdersManager.market_query(localMarket);
        if ((localList == null) || (localList.size() == 0))
        {
          i = -202;
          str1 = this.properties.getProperty("-209");
        }
        else
        {
          paramResponseResult.setResultList(localList);
        }
      }
    }
    catch (ConnectException localConnectException)
    {
      this.log.error("market_query rmi conection exception" + localConnectException);
      i = -202;
      str1 = this.properties.getProperty("-201");
      initRMI();
    }
    catch (RemoteException localRemoteException)
    {
      this.log.error("market_query remoteerror:" + localRemoteException);
      errorException(localRemoteException);
      i = -204;
      str1 = this.properties.getProperty("-204");
    }
    catch (Exception localException)
    {
      this.log.error("market_query error:" + localException);
      errorException(localException);
      i = -203;
      str1 = this.properties.getProperty("-203");
    }
    paramResponseResult.setRetCode(i);
    paramResponseResult.setMessage(str1);
    paramResponseResult.setUserID(str2);
    if ("mobile".equals(paramString2)) {
      this.mobileServlet.renderXML(paramHttpServletResponse, ResponseXml.market_query(paramResponseResult));
    } else {
      renderXML(paramHttpServletResponse, ResponseXml.market_query(paramResponseResult));
    }
  }
  
  protected void commodity_data_query(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1, ResponseResult paramResponseResult, String paramString2)
  {
    int i = 0;
    String str1 = "";
    String str2 = "";
    try
    {
      String str3 = getLogonType(paramString2);
      str2 = getValueByTagName(paramString1, "USER_ID");
      if ("".equals(str2)) {
        str2 = getValueByTagName(paramString1, "TRADER_ID");
      }
      international((String)lanaguages.get(str2));
      String str4 = getValueByTagName(paramString1, "COMMODITY_ID");
      long l = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
      if (!isLogon(paramHttpServletRequest, str2, l, str3))
      {
        i = -201;
        str1 = this.properties.getProperty("-205");
      }
      else
      {
        OrdersManager localOrdersManager = (OrdersManager)getBean("ordersManager");
        ArrayList localArrayList = new ArrayList();
        Map localMap = (Map)localOrdersManager.getQuotationMap().get(str4);
        if (localMap != null) {
          localArrayList.add(localMap);
        }
        if ((localArrayList == null) || (localArrayList.size() == 0))
        {
          i = -202;
          str1 = this.properties.getProperty("-209");
        }
        else
        {
          paramResponseResult.setResultList(localArrayList);
        }
      }
    }
    catch (ConnectException localConnectException)
    {
      this.log.error("commodity_data_query rmi conection exception" + localConnectException);
      i = -202;
      str1 = this.properties.getProperty("-201");
      initRMI();
    }
    catch (RemoteException localRemoteException)
    {
      this.log.error("commodity_data_query remoteerror:" + localRemoteException);
      errorException(localRemoteException);
      i = -204;
      str1 = this.properties.getProperty("-204");
    }
    catch (Exception localException)
    {
      this.log.error("commodity_data_query error:" + localException);
      localException.printStackTrace();
      errorException(localException);
      i = -203;
      str1 = this.properties.getProperty("-203");
    }
    paramResponseResult.setRetCode(i);
    paramResponseResult.setMessage(str1);
    paramResponseResult.setUserID(str2);
    if ("mobile".equals(paramString2)) {
      this.mobileServlet.renderXML(paramHttpServletResponse, ResponseXml.commodity_data_query(paramResponseResult));
    } else {
      renderXML(paramHttpServletResponse, ResponseXml.commodity_data_query(paramResponseResult));
    }
  }
  
  protected void directfirmbreed_query(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1, ResponseResult paramResponseResult, String paramString2)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'directfirmbreed_query' method ");
    }
    int i = 0;
    String str1 = "";
    String str2 = "";
    try
    {
      String str3 = getLogonType(paramString2);
      str2 = getValueByTagName(paramString1, "USER_ID");
      if ("".equals(str2)) {
        str2 = getValueByTagName(paramString1, "TRADER_ID");
      }
      international((String)lanaguages.get(str2));
      long l = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
      if (!isLogon(paramHttpServletRequest, str2, l, str3))
      {
        i = -201;
        str1 = this.properties.getProperty("-205");
      }
      else
      {
        OrdersManager localOrdersManager = (OrdersManager)getBean("ordersManager");
        Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
        List localList = localPrivilege.getDirectFirmBreeds();
        if ((localList == null) || (localList.size() == 0))
        {
          i = -202;
          str1 = this.properties.getProperty("-209");
        }
        else
        {
          paramResponseResult.setResultList(localList);
        }
      }
    }
    catch (ConnectException localConnectException)
    {
      this.log.error("directfirmbreed_query rmi conection exception" + localConnectException);
      i = -202;
      str1 = this.properties.getProperty("-201");
      initRMI();
    }
    catch (RemoteException localRemoteException)
    {
      this.log.error("directfirmbreed_query remoteerror:" + localRemoteException);
      errorException(localRemoteException);
      i = -204;
      str1 = this.properties.getProperty("-204");
    }
    catch (Exception localException)
    {
      this.log.error("directfirmbreed_query error:" + localException);
      localException.printStackTrace();
      errorException(localException);
      i = -203;
      str1 = this.properties.getProperty("-203");
    }
    paramResponseResult.setRetCode(i);
    paramResponseResult.setMessage(str1);
    paramResponseResult.setUserID(str2);
    if ("mobile".equals(paramString2)) {
      this.mobileServlet.renderXML(paramHttpServletResponse, ResponseXml.directfirmbreed_query(paramResponseResult));
    } else {
      renderXML(paramHttpServletResponse, ResponseXml.directfirmbreed_query(paramResponseResult));
    }
  }
  
  protected void queryDateQty(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1, ResponseResult paramResponseResult, String paramString2)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'queryDateQty' method ");
    }
    int i = 0;
    String str1 = "";
    String str2 = "";
    HashMap localHashMap = null;
    try
    {
      String str3 = getLogonType(paramString2);
      str2 = getValueByTagName(paramString1, "USER_ID");
      Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
      international((String)lanaguages.get(str2));
      long l = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
      if (!isLogon(paramHttpServletRequest, str2, l, str3))
      {
        i = -201;
        str1 = this.properties.getProperty("-205");
      }
      OrdersPagingManager localOrdersPagingManager = (OrdersPagingManager)getBean("ordersPagingManager");
      String str4 = getValueByTagName(paramString1, "QUERYNAME");
      String str5 = getValueByTagName(paramString1, "PARAMETER");
      if ((str5 != null) && (!"".equals(str5)))
      {
        localHashMap = new HashMap();
        localHashMap.put("parameter", str5);
      }
      List localList = localOrdersPagingManager.totalDateQuery(str4, localPrivilege, localHashMap);
      if ((localList == null) || (localList.size() == 0))
      {
        i = -202;
        str1 = this.properties.getProperty("-209");
      }
      paramResponseResult.setResultList(localList);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("queryDateQty error:" + localException);
      errorException(localException);
      i = -203;
      str1 = this.properties.getProperty("-203");
    }
    paramResponseResult.setRetCode(i);
    paramResponseResult.setMessage(str1);
    paramResponseResult.setUserID(str2);
    renderXML(paramHttpServletResponse, ResponseXml.queryDateQty(paramResponseResult));
  }
  
  protected void holdpositionbyprice(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1, ResponseResult paramResponseResult, String paramString2)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'holdpositionbyprice' method ");
    }
    int i = 0;
    String str1 = "";
    String str2 = "";
    try
    {
      String str3 = getLogonType(paramString2);
      str2 = getValueByTagName(paramString1, "USER_ID");
      if ("".equals(str2)) {
        str2 = getValueByTagName(paramString1, "TRADER_ID");
      }
      international((String)lanaguages.get(str2));
      String str4 = getValueByTagName(paramString1, "COMMODITY_ID");
      long l = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
      if (!isLogon(paramHttpServletRequest, str2, l, str3))
      {
        i = -201;
        str1 = this.properties.getProperty("-205");
      }
      else
      {
        OrdersManager localOrdersManager = (OrdersManager)getBean("ordersManager");
        Orders localOrders = new Orders();
        localOrders.setTraderID(str2);
        localOrders.setCommodityID(str4);
        SortCondition localSortCondition = new SortCondition();
        Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
        List localList = localOrdersManager.holdpositionbyprice(localOrders, localPrivilege, localSortCondition);
        if ((localList == null) || (localList.size() == 0))
        {
          i = -202;
          str1 = this.properties.getProperty("-209");
        }
        else
        {
          paramResponseResult.setResultList(localList);
        }
      }
    }
    catch (ConnectException localConnectException)
    {
      this.log.error("holdpositionbyprice rmi conection exception" + localConnectException);
      i = -202;
      str1 = this.properties.getProperty("-201");
      initRMI();
    }
    catch (RemoteException localRemoteException)
    {
      this.log.error("holdpositionbyprice remoteerror:" + localRemoteException);
      errorException(localRemoteException);
      i = -204;
      str1 = this.properties.getProperty("-204");
    }
    catch (Exception localException)
    {
      this.log.error("holdpositionbyprice error:" + localException);
      errorException(localException);
      i = -203;
      str1 = this.properties.getProperty("-203");
    }
    paramResponseResult.setRetCode(i);
    paramResponseResult.setMessage(str1);
    paramResponseResult.setUserID(str2);
    renderXML(paramHttpServletResponse, ResponseXml.holdpositionbyprice(paramResponseResult));
  }
  
  public void initHoldKeyMap()
  {
    this.holdKeyMap = new HashMap();
    this.holdKeyMap.put("CO_I", "commodityid");
    this.holdKeyMap.put("CU_I", "customerid");
    this.holdKeyMap.put("BU_H", "buyQty");
    this.holdKeyMap.put("SE_H", "sellQty");
    this.holdKeyMap.put("B_V_H", "buyAvailQty");
    this.holdKeyMap.put("S_V_H", "sellAvailQty");
    this.holdKeyMap.put("BU_A", "buyEvenPrice");
    this.holdKeyMap.put("SE_A", "sellEvenPrice");
    this.holdKeyMap.put("GO_Q", "gageqty");
    this.holdKeyMap.put("FL_P", "bsPL");
    this.holdKeyMap.put("MAR", "holdmargin");
  }
}
