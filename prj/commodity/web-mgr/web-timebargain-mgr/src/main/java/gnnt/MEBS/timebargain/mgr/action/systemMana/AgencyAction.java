package gnnt.MEBS.timebargain.mgr.action.systemMana;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.systemMana.CommodityModel;
import gnnt.MEBS.timebargain.mgr.model.systemMana.FirmMargin;
import gnnt.MEBS.timebargain.mgr.service.systemMana.SystemStatusService;
import gnnt.MEBS.timebargain.server.rmi.DelayRMI;
import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("agencyAction")
@Scope("request")
public class AgencyAction extends EcsideAction
{
  protected final transient Log logger = LogFactory.getLog(getClass());
  private static final long serialVersionUID = 5124568190167465621L;

  @Autowired
  @Qualifier("systemStatusService")
  private SystemStatusService systemStatusService;

  @Autowired
  @Qualifier("ServerRMI")
  private ServerRMI serverRMI;

  @Autowired
  @Qualifier("TradeRMI")
  private TradeRMI tradeRMI;

  @Autowired
  @Qualifier("DelayRMI")
  private DelayRMI delayRMI;
  private String sqlSpacMargin;
  private List spacMarginList = new ArrayList();

  public List getSpacMarginList()
  {
    return this.spacMarginList;
  }

  public void setSpacMarginList(List paramList)
  {
    this.spacMarginList = paramList;
  }

  public String getSqlSpacMargin()
  {
    return this.sqlSpacMargin;
  }

  public void setSqlSpacMargin(String paramString)
  {
    this.sqlSpacMargin = paramString;
  }

  public String edit2()
  {
    try
    {
      String str1 = this.systemStatusService.getSystemStatus();
      HttpSession localHttpSession = this.request.getSession();
      localHttpSession.setAttribute("status", str1);
      String str2 = this.request.getSession().getServletContext().getInitParameter("useDelay");
      localHttpSession.setAttribute("useDelay", str2);
    }
    catch (Exception localException)
    {
      this.logger.error("==err:" + localException);
    }
    return "success";
  }

  public String updateOnlineData()
  {
    try
    {
      this.logger.debug("----------在线更新交易数据-----------");
      this.serverRMI.refreshMemory();
      addReturnValue(1, 119907L);
      writeOperateLog(1512, "异常处理，操作在线更新交易数据", 1, "");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      writeOperateLog(1512, "异常处理，操作在线更新交易数据", 0, "");
      addReturnValue(-1, 119908L);
    }
    return "success";
  }

  public String updateMargin()
  {
    this.logger.debug("----------显示修改保证金页面-----------");
    String str1 = this.request.getParameter("commodityID");
    String str2 = this.request.getParameter("marginRate_B");
    String str3 = this.request.getParameter("marginRate_S");
    String str4 = this.request.getParameter("marginAssure_B");
    String str5 = this.request.getParameter("marginAssure_S");
    String str6 = this.request.getParameter("marginAlgr");
    CommodityModel localCommodityModel = new CommodityModel();
    localCommodityModel.setCommodityId(str1);
    localCommodityModel = (CommodityModel)getService().get(localCommodityModel);
    try
    {
      if ((str1 != null) && (!"".equals(str1)))
        localCommodityModel.setCommodityId(str1);
      if ((str6 != null) && (!"".equals(str6)) && ("1".equals(str6)))
      {
        if ((str2 != null) && (!"".equals(str2)))
          localCommodityModel.setMarginRate_B(Double.valueOf(Double.parseDouble(str2) / 100.0D));
        if ((str3 != null) && (!"".equals(str3)))
          localCommodityModel.setMarginRate_S(Double.valueOf(Double.parseDouble(str3) / 100.0D));
        if ((str4 != null) && (!"".equals(str4)))
          localCommodityModel.setMarginAssure_B(Double.valueOf(Double.parseDouble(str4) / 100.0D));
        if ((str5 != null) && (!"".equals(str5)))
          localCommodityModel.setMarginAssure_S(Double.valueOf(Double.parseDouble(str5) / 100.0D));
      }
      else
      {
        if ((str2 != null) && (!"".equals(str2)))
          localCommodityModel.setMarginRate_B(Double.valueOf(Double.parseDouble(str2)));
        if ((str3 != null) && (!"".equals(str3)))
          localCommodityModel.setMarginRate_S(Double.valueOf(Double.parseDouble(str3)));
        if ((str4 != null) && (!"".equals(str4)))
          localCommodityModel.setMarginAssure_B(Double.valueOf(Double.parseDouble(str4)));
        if ((str5 != null) && (!"".equals(str5)))
          localCommodityModel.setMarginAssure_S(Double.valueOf(Double.parseDouble(str5)));
      }
      getService().update(localCommodityModel);
      addReturnValue(1, 119907L);
      writeOperateLog(1512, "异常处理，操作在线设置保证金", 1, "");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      writeOperateLog(1512, "异常处理，操作在线设置保证金", 0, "");
      addReturnValue(-1, 119908L);
    }
    return "success";
  }

  public String getFirmMarginList()
    throws Exception
  {
    this.logger.debug("----------获取商品代码List-----------");
    try
    {
      PageRequest localPageRequest = getPageRequest(this.request);
      localPageRequest.setSortColumns("order by commodityID");
      Page localPage = getService().getPage(localPageRequest, this.entity);
      List localList = localPage.getResult();
      for (int i = 0; i < localList.size(); i++)
      {
        FirmMargin localFirmMargin = (FirmMargin)localList.get(i);
        this.spacMarginList.add(localFirmMargin);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "success";
  }

  public String updateSpacMargin()
  {
    String str1 = this.request.getParameter("customerID");
    String str2 = this.request.getParameter("commodityID");
    String str3 = this.request.getParameter("marginRate_B");
    String str4 = this.request.getParameter("marginRate_S");
    String str5 = this.request.getParameter("marginAssure_B");
    String str6 = this.request.getParameter("marginAssure_S");
    String str7 = this.request.getParameter("marginAlgr");
    FirmMargin localFirmMargin = new FirmMargin();
    localFirmMargin.setCommodityId(str2);
    localFirmMargin = (FirmMargin)getService().get(localFirmMargin);
    try
    {
      if ((str2 != null) && (!"".equals(str2)))
        localFirmMargin.setCommodityId(str2);
      localFirmMargin.setFirmId(str1);
      if ("1".equals(str7))
      {
        if ((str3 != null) && (!"".equals(str3)))
          localFirmMargin.setMarginRate_B(Double.valueOf(Double.parseDouble(str3) / 100.0D));
        if ((str4 != null) && (!"".equals(str4)))
          localFirmMargin.setMarginRate_S(Double.valueOf(Double.parseDouble(str4) / 100.0D));
        if ((str5 != null) && (!"".equals(str5)))
          localFirmMargin.setMarginAssure_B(Double.valueOf(Double.parseDouble(str5) / 100.0D));
        if ((str6 != null) && (!"".equals(str6)))
          localFirmMargin.setMarginAssure_S(Double.valueOf(Double.parseDouble(str6) / 100.0D));
      }
      else
      {
        if ((str3 != null) && (!"".equals(str3)))
          localFirmMargin.setMarginRate_B(Double.valueOf(Double.parseDouble(str3)));
        if ((str4 != null) && (!"".equals(str4)))
          localFirmMargin.setMarginRate_S(Double.valueOf(Double.parseDouble(str4)));
        if ((str5 != null) && (!"".equals(str5)))
          localFirmMargin.setMarginAssure_B(Double.valueOf(Double.parseDouble(str5)));
        if ((str6 != null) && (!"".equals(str6)))
          localFirmMargin.setMarginAssure_S(Double.valueOf(Double.parseDouble(str6)));
      }
      getService().update(localFirmMargin);
      addReturnValue(1, 119907L);
      writeOperateLog(1512, "异常处理，操作在线设置特殊保证金", 1, "");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      writeOperateLog(1512, "异常处理，操作在线设置特殊保证金", 0, "");
      addReturnValue(-1, 119908L);
    }
    return "success";
  }

  public String updateRerunBail()
  {
    try
    {
      this.logger.debug("----------重算保证金-----------");
      int i = this.tradeRMI.tradingReComputeFunds();
      if (i == 1)
        addReturnValue(1, 119907L);
      if (i == -100)
        addReturnValue(1, 119909L);
      if (i == -204)
        addReturnValue(1, 119910L);
      if (i == -207)
        addReturnValue(1, 119911L);
      writeOperateLog(1512, "异常处理，操作重算保证金", 1, "");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      writeOperateLog(1512, "异常处理，操作重算保证金", 0, "");
      addReturnValue(1, 119908L);
    }
    return "success";
  }

  public String updateOnline()
  {
    this.logger.debug("----------在线更新交易节设置-----------");
    try
    {
      this.serverRMI.refreshTradeTime();
      addReturnValue(1, 119907L);
      writeOperateLog(1512, "异常处理，操作在线更新交易节设置", 1, "");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      addReturnValue(1, 119908L);
      writeOperateLog(1512, "异常处理，操作在线更新交易节设置", 0, "");
    }
    return "success";
  }

  public String updateRecoverDelayTrade()
  {
    this.logger.debug("----------交易结束转恢复延期交易-----------");
    try
    {
      this.delayRMI.recoverTrade();
      addReturnValue(1, 119907L);
      writeOperateLog(1512, "异常处理，操作交易结束转恢复延期交易", 1, "");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      addReturnValue(1, 119908L);
      writeOperateLog(1512, "异常处理，操作交易结束转恢复延期交易", 0, "");
    }
    return "success";
  }

  public String updateOnlineDelay()
  {
    this.logger.debug("----------在线更新延期交易节设置-----------");
    try
    {
      this.delayRMI.refreshDelayTradeTime();
      addReturnValue(1, 119907L);
      writeOperateLog(1512, "异常处理，操作在线更新延期交易节设置", 1, "");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      addReturnValue(1, 119908L);
      writeOperateLog(1512, "异常处理，操作在线更新延期交易节设置", 0, "");
    }
    return "success";
  }

  public String updateBalanceChkFroenFundEXC()
  {
    try
    {
      int i = this.tradeRMI.checkFrozenQtyAtBalance();
      addReturnValue(1, 119907L);
      writeOperateLog(1512, "异常处理，操作重做交易系统结算", 1, "");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      addReturnValue(1, 119908L);
      writeOperateLog(1512, "异常处理，操作重做交易系统结算", 0, "");
    }
    return "success";
  }

  public String updateLastPrice()
  {
    this.logger.debug("----------修改开盘指导价-----------");
    String str1 = this.request.getParameter("commodityID");
    String str2 = this.request.getParameter("openingPrice");
    Double localDouble = Double.valueOf(str2);
    CommodityModel localCommodityModel = new CommodityModel();
    localCommodityModel.setCommodityId(str1);
    localCommodityModel = (CommodityModel)getService().get(localCommodityModel);
    try
    {
      localCommodityModel.setCommodityId(str1);
      localCommodityModel.setLastPrice(localDouble);
      getService().update(localCommodityModel);
      addReturnValue(1, 119907L);
      writeOperateLog(1512, "异常处理，操作修改开盘指导价", 1, "");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      writeOperateLog(1512, "异常处理，操作修改开盘指导价", 0, "");
      addReturnValue(-1, 119908L);
    }
    return "success";
  }

  public Map getCommodity(String paramString)
  {
    HashMap localHashMap = new HashMap();
    CommodityModel localCommodityModel = new CommodityModel();
    localCommodityModel.setCommodityId(paramString);
    localCommodityModel = (CommodityModel)getService().get(localCommodityModel);
    String str = "";
    if ((localCommodityModel != null) && (localCommodityModel.getMarginAlgr() != null))
      str = localCommodityModel.getMarginAlgr().toString();
    if (str.equals("1"))
    {
      localHashMap.put("marginRate_B", Double.valueOf(Double.parseDouble(localCommodityModel.getMarginRate_B().toString()) * 100.0D));
      localHashMap.put("marginRate_S", Double.valueOf(Double.parseDouble(localCommodityModel.getMarginRate_S().toString()) * 100.0D));
      localHashMap.put("marginAssure_B", Double.valueOf(Double.parseDouble(localCommodityModel.getMarginAssure_B().toString()) * 100.0D));
      localHashMap.put("marginAssure_S", Double.valueOf(Double.parseDouble(localCommodityModel.getMarginAssure_S().toString()) * 100.0D));
      localHashMap.put("marginAlgr", localCommodityModel.getMarginAlgr());
    }
    else
    {
      localHashMap.put("marginRate_B", localCommodityModel.getMarginRate_B());
      localHashMap.put("marginRate_S", localCommodityModel.getMarginRate_S());
      localHashMap.put("marginAssure_B", localCommodityModel.getMarginAssure_B());
      localHashMap.put("marginAssure_S", localCommodityModel.getMarginAssure_S());
      localHashMap.put("marginAlgr", localCommodityModel.getMarginAlgr());
    }
    localHashMap.put("lastPrice", localCommodityModel.getLastPrice());
    return localHashMap;
  }

  public Map getFirmMargin(String paramString)
  {
    HashMap localHashMap = new HashMap();
    FirmMargin localFirmMargin = new FirmMargin();
    localFirmMargin.setCommodityId(paramString);
    localFirmMargin = (FirmMargin)getService().get(localFirmMargin);
    String str = "";
    if ((localFirmMargin != null) && (localFirmMargin.getMarginAlgr() != null))
      str = localFirmMargin.getMarginAlgr().toString();
    if (str.equals("1"))
    {
      localHashMap.put("marginRate_B", Double.valueOf(Double.parseDouble(localFirmMargin.getMarginRate_B().toString()) * 100.0D));
      localHashMap.put("marginRate_S", Double.valueOf(Double.parseDouble(localFirmMargin.getMarginRate_S().toString()) * 100.0D));
      localHashMap.put("marginAssure_B", Double.valueOf(Double.parseDouble(localFirmMargin.getMarginAssure_B().toString()) * 100.0D));
      localHashMap.put("marginAssure_S", Double.valueOf(Double.parseDouble(localFirmMargin.getMarginAssure_S().toString()) * 100.0D));
      localHashMap.put("marginAlgr", localFirmMargin.getMarginAlgr());
    }
    else
    {
      localHashMap.put("marginRate_B", localFirmMargin.getMarginRate_B());
      localHashMap.put("marginRate_S", localFirmMargin.getMarginRate_S());
      localHashMap.put("marginAssure_B", localFirmMargin.getMarginAssure_B());
      localHashMap.put("marginAssure_S", localFirmMargin.getMarginAssure_S());
      localHashMap.put("marginAlgr", localFirmMargin.getMarginAlgr());
    }
    return localHashMap;
  }

  public ServerRMI getServerRMI()
  {
    return this.serverRMI;
  }

  public void setServerRMI(ServerRMI paramServerRMI)
  {
    this.serverRMI = paramServerRMI;
  }

  public TradeRMI getTradeRMI()
  {
    return this.tradeRMI;
  }

  public void setTradeRMI(TradeRMI paramTradeRMI)
  {
    this.tradeRMI = paramTradeRMI;
  }

  public DelayRMI getDelayRMI()
  {
    return this.delayRMI;
  }

  public void setDelayRMI(DelayRMI paramDelayRMI)
  {
    this.delayRMI = paramDelayRMI;
  }
}