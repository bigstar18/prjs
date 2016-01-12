package gnnt.MEBS.timebargain.mgr.action;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.dataquery.CommodityF;
import gnnt.MEBS.timebargain.mgr.model.dataquery.CustomerFundsModel;
import gnnt.MEBS.timebargain.mgr.model.dataquery.CustomerHoldSumModel;
import gnnt.MEBS.timebargain.mgr.model.dataquery.FirmHoldSumModel;
import gnnt.MEBS.timebargain.mgr.model.dataquery.HistoryCustomerHoldSumModel;
import gnnt.MEBS.timebargain.mgr.model.dataquery.HistoryFirmHoldSumModel;
import gnnt.MEBS.timebargain.mgr.model.dataquery.HistoryHoldPositionModel;
import gnnt.MEBS.timebargain.mgr.model.dataquery.HistoryOrdersModel;
import gnnt.MEBS.timebargain.mgr.model.dataquery.HistoryQuotationModel;
import gnnt.MEBS.timebargain.mgr.model.dataquery.HistoryTradeModel;
import gnnt.MEBS.timebargain.mgr.model.dataquery.HoldPositionModel;
import gnnt.MEBS.timebargain.mgr.model.dataquery.OrdersModel;
import gnnt.MEBS.timebargain.mgr.model.dataquery.QuotationModel;
import gnnt.MEBS.timebargain.mgr.model.dataquery.SettleCommodityModel;
import gnnt.MEBS.timebargain.mgr.model.dataquery.SystemStatusModel;
import gnnt.MEBS.timebargain.mgr.model.dataquery.TradeModel;
import gnnt.MEBS.timebargain.mgr.service.BrokerCountService;
import gnnt.MEBS.timebargain.mgr.service.CustomerFundsService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("dataQueryAction")
@Scope("request")
public class DataQueryAction extends EcsideAction
{
  private static final long serialVersionUID = 6405710709936905749L;

  @Autowired
  @Qualifier("customerFundsService")
  private CustomerFundsService customerFundsService;

  @Autowired
  @Qualifier("brokerCountService")
  private BrokerCountService brokerCountService;
  private CustomerFundsModel customerFunds;

  @Resource(name="BS_flagMap")
  Map<String, String> BS_flagMap;

  @Resource(name="orderTypeMap")
  Map<String, String> orderTypeMap;

  @Resource(name="closeFlagMap")
  Map<String, String> closeFlagMap;

  @Resource(name="statusMap1")
  Map<String, String> statusMap1;

  @Resource(name="withdrawTypeMap")
  Map<String, String> withdrawTypeMap;

  @Resource(name="billTradeTypeMap")
  Map<String, String> billTradeTypeMap;

  @Resource(name="statusMap")
  Map<String, String> statusMap;

  @Resource(name="tradeTypeMap")
  Map<String, String> tradeTypeMap;

  @Resource(name="closeModeMap")
  Map<Long, String> closeModeMap;

  @Resource(name="timeFlagMap")
  Map<Integer, String> timeFlagMap;

  public Map<String, String> getBS_flagMap()
  {
    return this.BS_flagMap;
  }

  public void setBS_flagMap(Map<String, String> paramMap)
  {
    this.BS_flagMap = paramMap;
  }

  public Map<String, String> getOrderTypeMap()
  {
    return this.orderTypeMap;
  }

  public void setOrderTypeMap(Map<String, String> paramMap)
  {
    this.orderTypeMap = paramMap;
  }

  public Map<String, String> getCloseFlagMap()
  {
    return this.closeFlagMap;
  }

  public void setCloseFlagMap(Map<String, String> paramMap)
  {
    this.closeFlagMap = paramMap;
  }

  public Map<String, String> getStatusMap1()
  {
    return this.statusMap1;
  }

  public void setStatusMap1(Map<String, String> paramMap)
  {
    this.statusMap1 = paramMap;
  }

  public Map<String, String> getWithdrawTypeMap()
  {
    return this.withdrawTypeMap;
  }

  public void setWithdrawTypeMap(Map<String, String> paramMap)
  {
    this.withdrawTypeMap = paramMap;
  }

  public Map<String, String> getBillTradeTypeMap()
  {
    return this.billTradeTypeMap;
  }

  public void setBillTradeTypeMap(Map<String, String> paramMap)
  {
    this.billTradeTypeMap = paramMap;
  }

  public Map<String, String> getStatusMap()
  {
    return this.statusMap;
  }

  public void setStatusMap(Map<String, String> paramMap)
  {
    this.statusMap = paramMap;
  }

  public Map<String, String> getTradeTypeMap()
  {
    return this.tradeTypeMap;
  }

  public void setTradeTypeMap(Map<String, String> paramMap)
  {
    this.tradeTypeMap = paramMap;
  }

  public Map<Long, String> getCloseModeMap()
  {
    return this.closeModeMap;
  }

  public void setCloseModeMap(Map<Long, String> paramMap)
  {
    this.closeModeMap = paramMap;
  }

  public Map<Integer, String> getTimeFlagMap()
  {
    return this.timeFlagMap;
  }

  public void setTimeFlagMap(Map<Integer, String> paramMap)
  {
    this.timeFlagMap = paramMap;
  }

  public String listHold()
    throws Exception
  {
    String str = this.request.getParameter("isQryHisHidd");
    this.request.setAttribute("isQryHisHidd", str);
    if ((str == null) || (str.equals("no")))
    {
      this.entity = new HoldPositionModel();
      this.request.setAttribute("isHiss", "no");
    }
    else
    {
      this.entity = new HistoryHoldPositionModel();
      this.request.setAttribute("isHis", "yes");
    }
    this.logger.debug("enter listHold");
    PageRequest localPageRequest = getPageRequest(this.request);
    listByLimit(localPageRequest);
    return "success";
  }

  public String listHoldPosition()
    throws Exception
  {
    String str = this.request.getParameter("isQryHisHidd");
    this.request.setAttribute("isQryHisHidd", str);
    if ((str == null) || (str.equals("no")))
    {
      this.entity = new CustomerHoldSumModel();
      this.request.setAttribute("isHiss", "no");
    }
    else
    {
      this.entity = new HistoryCustomerHoldSumModel();
      this.request.setAttribute("isHis", "yes");
    }
    this.logger.debug("enter listHoldPosition");
    PageRequest localPageRequest = getPageRequest(this.request);
    listByLimit(localPageRequest);
    return "success";
  }

  public String listFirmHoldPosition()
    throws Exception
  {
    String str = this.request.getParameter("isQryHisHidd");
    this.request.setAttribute("isQryHisHidd", str);
    if ((str == null) || (str.equals("no")))
    {
      this.entity = new FirmHoldSumModel();
      this.request.setAttribute("isHiss", "no");
    }
    else
    {
      this.entity = new HistoryFirmHoldSumModel();
      this.request.setAttribute("isHis", "yes");
    }
    this.logger.debug("enter listFirmHoldPosition");
    PageRequest localPageRequest = getPageRequest(this.request);
    listByLimit(localPageRequest);
    return "success";
  }

  public String listTrade()
    throws Exception
  {
    String str = this.request.getParameter("isQryHisHidd");
    this.request.setAttribute("isQryHisHidd", str);
    if ((str == null) || (str.equals("no")))
      this.entity = new TradeModel();
    else
      this.entity = new HistoryTradeModel();
    this.logger.debug("enter listTrade");
    PageRequest localPageRequest = getPageRequest(this.request);
    listByLimit(localPageRequest);
    return "success";
  }

  public String listQuotation()
    throws Exception
  {
    String str = this.request.getParameter("isQryHisHidd");
    this.request.setAttribute("isQryHisHidd", str);
    if ((str == null) || (str.equals("no")))
      this.entity = new QuotationModel();
    else
      this.entity = new HistoryQuotationModel();
    this.logger.debug("enter listQuotation");
    PageRequest localPageRequest = getPageRequest(this.request);
    listByLimit(localPageRequest);
    return "success";
  }

  public String listOrder()
    throws Exception
  {
    String str1 = this.request.getParameter("isQryHisHidd");
    String str2 = this.request.getParameter("isConsignerHidd");
    this.request.setAttribute("isQryHisHidd", str1);
    this.request.setAttribute("isConsignerHidd", str2);
    this.logger.debug("enter listOrder");
    PageRequest localPageRequest = getPageRequest(this.request);
    if ((str1 == null) || (str1.equals("no")))
      this.entity = new OrdersModel();
    else
      this.entity = new HistoryOrdersModel();
    if ((str2 != null) && (str2.equals("yes")))
    {
      QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
      localQueryConditions.addCondition("consignerId", "is", "not null");
    }
    listByLimit(localPageRequest);
    return "success";
  }

  public String listQueryCommodity()
    throws Exception
  {
    String str1 = this.request.getParameter("isQryHisHidd");
    this.request.setAttribute("isQryHisHidd", str1);
    String str2 = this.request.getParameter("year");
    String str3 = this.request.getParameter("month");
    PageRequest localPageRequest1;
    Object localObject1;
    Object localObject2;
    if ((str1 == null) || (str1.equals("no")))
    {
      this.entity = new CommodityF();
      this.request.setAttribute("isHis", "no");
      localPageRequest1 = getPageRequest(this.request);
      localObject1 = (QueryConditions)localPageRequest1.getFilters();
      if ((str2 != null) && (!"".equals(str2)) && (str3 != null) && (!"".equals(str3)))
        ((QueryConditions)localObject1).addCondition("to_char(settleDate,'yyyyMM')", "=", str2 + str3);
      else if ((str2 != null) && (!"".equals(str2)))
        ((QueryConditions)localObject1).addCondition("to_char(settleDate,'yyyy')", "=", str2);
      else if ((str3 != null) && (!"".equals(str3)))
        ((QueryConditions)localObject1).addCondition("to_char(settleDate,'MM')", "=", str3);
      localObject2 = getService().getPage(localPageRequest1, this.entity);
      this.request.setAttribute("pageInfo", localObject2);
      this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));
    }
    else
    {
      this.entity = new SettleCommodityModel();
      localPageRequest1 = new PageRequest();
      localPageRequest1.setPageNumber(1);
      localPageRequest1.setPageSize(1);
      localObject1 = getService().getPage(localPageRequest1, new SystemStatusModel());
      localObject2 = (SystemStatusModel)((Page)localObject1).getResult().get(0);
      PageRequest localPageRequest2 = getPageRequest(this.request);
      QueryConditions localQueryConditions = (QueryConditions)localPageRequest2.getFilters();
      if ((str2 != null) && (!"".equals(str2)) && (str3 != null) && (!"".equals(str3)))
        localQueryConditions.addCondition("to_char(settleDate,'yyyyMM')", "=", str2 + str3);
      else if ((str2 != null) && (!"".equals(str2)))
        localQueryConditions.addCondition("to_char(settleDate,'yyyy')", "=", str2);
      else if ((str3 != null) && (!"".equals(str3)))
        localQueryConditions.addCondition("to_char(settleDate,'MM')", "=", str3);
      localQueryConditions.addCondition("primary.settleDate", "<", ((SystemStatusModel)localObject2).getTradeDate());
      localQueryConditions.addCondition("primary.commodityId", "not in", "(select commodityId from CommodityF)");
      Page localPage = getService().getPage(localPageRequest2, this.entity);
      this.request.setAttribute("pageInfo", localPage);
      this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));
    }
    this.request.setAttribute("year", str2);
    this.request.setAttribute("month", str3);
    return "success";
  }

  public String listCustomerFunds()
    throws Exception
  {
    this.entity = new CustomerFundsModel();
    this.logger.debug("enter listCustomerFunds");
    PageRequest localPageRequest = getPageRequest(this.request);
    listByLimit(localPageRequest);
    return "success";
  }

  public String customerFunds()
    throws Exception
  {
    String str = this.request.getParameter("firmId");
    List localList = this.customerFundsService.customerFundsTable(str);
    this.request.setAttribute("firmID", str);
    this.request.setAttribute("result", localList);
    return "success";
  }

  public CustomerFundsService getCustomerFundsService()
  {
    return this.customerFundsService;
  }

  public CustomerFundsModel getCustomerFunds()
  {
    return this.customerFunds;
  }

  public void setCustomerFunds(CustomerFundsModel paramCustomerFundsModel)
  {
    this.customerFunds = paramCustomerFundsModel;
  }

  public String brokerFundsCount()
    throws Exception
  {
    this.logger.debug("enter brokerFundsCount");
    String str1 = this.request.getParameter("brokerID");
    String str2 = this.request.getParameter("brokerName");
    PageRequest localPageRequest = super.getPageRequest(this.request);
    this.logger.debug("brokerID=======" + str1);
    List localList = this.brokerCountService.brokerFundsTable(str1, str2);
    Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList.size(), localList);
    this.request.setAttribute("pageInfo", localPage);
    this.request.setAttribute("brokerID", str1);
    this.request.setAttribute("brokerName", str2);
    return "success";
  }

  public String brokerIndentCount()
    throws Exception
  {
    this.logger.debug("enter brokerIndentCount");
    String str1 = this.request.getParameter("isQryHisHidd");
    this.request.setAttribute("isQryHisHidd", str1);
    String str2 = this.request.getParameter("brokerID");
    String str3 = this.request.getParameter("commodityId");
    String str4 = this.request.getParameter("flag");
    String str5 = this.request.getParameter("clearDate");
    PageRequest localPageRequest;
    List localList;
    Page localPage;
    if ((str1 == null) || (str1.equals("no")))
    {
      localPageRequest = super.getPageRequest(this.request);
      localList = this.brokerCountService.brokerIndentTable(str2, str3, str4);
      localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList.size(), localList);
      this.request.setAttribute("pageInfo", localPage);
    }
    else
    {
      localPageRequest = super.getPageRequest(this.request);
      localList = this.brokerCountService.historyBrokerIndentTable(str2, str3, str4, str5);
      localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList.size(), localList);
      this.request.setAttribute("pageInfo", localPage);
    }
    this.request.setAttribute("brokerID", str2);
    this.request.setAttribute("commodityId", str3);
    this.request.setAttribute("flag", str4);
    this.request.setAttribute("clearDate", str5);
    return "success";
  }

  public String brokerTradeCount()
    throws Exception
  {
    this.logger.debug("enter brokerTradeCount");
    String str1 = this.request.getParameter("isQryHisHidd");
    this.request.setAttribute("isQryHisHidd", str1);
    String str2 = this.request.getParameter("brokerID");
    String str3 = this.request.getParameter("brokerName");
    String str4 = this.request.getParameter("flag");
    String str5 = this.request.getParameter("tradeType");
    String str6 = this.request.getParameter("commodityId");
    String str7 = this.request.getParameter("orderType");
    String str8 = this.request.getParameter("beginDate");
    String str9 = this.request.getParameter("endDate");
    PageRequest localPageRequest;
    List localList;
    Page localPage;
    if ((str1 == null) || (str1.equals("no")))
    {
      localPageRequest = super.getPageRequest(this.request);
      localList = this.brokerCountService.brokerTradeTable(str2, str3, str4, str5, str6, str7);
      localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList.size(), localList);
      this.request.setAttribute("pageInfo", localPage);
    }
    else
    {
      localPageRequest = super.getPageRequest(this.request);
      localList = this.brokerCountService.historyBrokerTradeTable(str2, str3, str4, str5, str6, str7, str8, str9);
      localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList.size(), localList);
      this.request.setAttribute("pageInfo", localPage);
    }
    this.request.setAttribute("brokerID", str2);
    this.request.setAttribute("brokerName", str3);
    this.request.setAttribute("flag", str4);
    this.request.setAttribute("tradeType", str5);
    this.request.setAttribute("commodityId", str6);
    this.request.setAttribute("orderType", str7);
    this.request.setAttribute("beginDate", str8);
    this.request.setAttribute("endDate", str9);
    return "success";
  }

  public BrokerCountService getBrokerCountService()
  {
    return this.brokerCountService;
  }
}