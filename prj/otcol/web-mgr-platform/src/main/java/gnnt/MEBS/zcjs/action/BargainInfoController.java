package gnnt.MEBS.zcjs.action;

import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.zcjs.model.DiscussPrice;
import gnnt.MEBS.zcjs.model.GoodsOrder;
import gnnt.MEBS.zcjs.model.HisDiscussPrice;
import gnnt.MEBS.zcjs.model.HisGoodsOrder;
import gnnt.MEBS.zcjs.model.HisTrade;
import gnnt.MEBS.zcjs.model.HisTradeCommodityMsg;
import gnnt.MEBS.zcjs.model.ProsceniumShow;
import gnnt.MEBS.zcjs.model.Trade;
import gnnt.MEBS.zcjs.model.TradeCommodityMsg;
import gnnt.MEBS.zcjs.rmi.SystemSettingServerRmi;
import gnnt.MEBS.zcjs.services.DiscussPriceService;
import gnnt.MEBS.zcjs.services.GoodsOrderService;
import gnnt.MEBS.zcjs.services.HisDiscussPriceService;
import gnnt.MEBS.zcjs.services.HisGoodsOrderService;
import gnnt.MEBS.zcjs.services.HisTradeCommodityMsgService;
import gnnt.MEBS.zcjs.services.HisTradeService;
import gnnt.MEBS.zcjs.services.ProsceniumShowService;
import gnnt.MEBS.zcjs.services.TradeCommodityMsgService;
import gnnt.MEBS.zcjs.services.TradeInfoService;
import gnnt.MEBS.zcjs.services.TradeService;
import gnnt.MEBS.zcjs.util.SysData;
import java.rmi.Naming;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.servlet.ModelAndView;

public class BargainInfoController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(BargainInfoController.class);
  
  private ProsceniumShowService getBeanOfProsceniumShowService()
  {
    ProsceniumShowService localProsceniumShowService = null;
    synchronized (ProsceniumShowService.class)
    {
      if (localProsceniumShowService == null) {
        localProsceniumShowService = (ProsceniumShowService)SysData.getBean("z_prosceniumShowService");
      }
    }
    return localProsceniumShowService;
  }
  
  private TradeInfoService getBeanOfTradeInfoService()
  {
    TradeInfoService localTradeInfoService = null;
    synchronized (TradeInfoService.class)
    {
      if (localTradeInfoService == null) {
        localTradeInfoService = (TradeInfoService)SysData.getBean("z_tradeInfoService");
      }
    }
    return localTradeInfoService;
  }
  
  private HisTradeService getBeanOfHisTradeService()
  {
    HisTradeService localHisTradeService = null;
    synchronized (HisTradeService.class)
    {
      if (localHisTradeService == null) {
        localHisTradeService = (HisTradeService)SysData.getBean("z_hisTradeService");
      }
    }
    return localHisTradeService;
  }
  
  private TradeService getBeanOfTradeService()
  {
    TradeService localTradeService = null;
    synchronized (TradeService.class)
    {
      if (localTradeService == null) {
        localTradeService = (TradeService)SysData.getBean("z_tradeService");
      }
    }
    return localTradeService;
  }
  
  private HisDiscussPriceService getBeanOfHisDiscussPriceService()
  {
    HisDiscussPriceService localHisDiscussPriceService = null;
    synchronized (HisDiscussPriceService.class)
    {
      if (localHisDiscussPriceService == null) {
        localHisDiscussPriceService = (HisDiscussPriceService)SysData.getBean("z_hisDiscussPriceService");
      }
    }
    return localHisDiscussPriceService;
  }
  
  private HisTradeCommodityMsgService getBeanOfHisTradeCommodityMsgService()
  {
    HisTradeCommodityMsgService localHisTradeCommodityMsgService = null;
    synchronized (HisTradeCommodityMsgService.class)
    {
      if (localHisTradeCommodityMsgService == null) {
        localHisTradeCommodityMsgService = (HisTradeCommodityMsgService)SysData.getBean("z_hisTradeCommodityMsgService");
      }
    }
    return localHisTradeCommodityMsgService;
  }
  
  private TradeCommodityMsgService getBeanOfTradeCommodityMsgService()
  {
    TradeCommodityMsgService localTradeCommodityMsgService = null;
    synchronized (TradeCommodityMsgService.class)
    {
      if (localTradeCommodityMsgService == null) {
        localTradeCommodityMsgService = (TradeCommodityMsgService)SysData.getBean("z_tradeCommodityMsgService");
      }
    }
    return localTradeCommodityMsgService;
  }
  
  private GoodsOrderService getBeanOfGoodsOrderService()
  {
    GoodsOrderService localGoodsOrderService = null;
    synchronized (GoodsOrderService.class)
    {
      if (localGoodsOrderService == null) {
        localGoodsOrderService = (GoodsOrderService)SysData.getBean("z_goodsOrderService");
      }
    }
    return localGoodsOrderService;
  }
  
  private HisGoodsOrderService getBeanOfHisGoodsOrderService()
  {
    HisGoodsOrderService localHisGoodsOrderService = null;
    synchronized (HisGoodsOrderService.class)
    {
      if (localHisGoodsOrderService == null) {
        localHisGoodsOrderService = (HisGoodsOrderService)SysData.getBean("z_hisGoodsOrderService");
      }
    }
    return localHisGoodsOrderService;
  }
  
  private DiscussPriceService getBeanOfDiscussPriceService()
  {
    DiscussPriceService localDiscussPriceService = null;
    synchronized (DiscussPriceService.class)
    {
      if (localDiscussPriceService == null) {
        localDiscussPriceService = (DiscussPriceService)SysData.getBean("z_discussPriceService");
      }
    }
    return localDiscussPriceService;
  }
  
  public List<ProsceniumShow> getC_GList()
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("ProsceniumApplication", "=", "BG_C_GoodsOrder");
    return getBeanOfProsceniumShowService().getObjectList(localQueryConditions);
  }
  
  public ModelAndView getC_GList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws Exception
  {
    this.logger.debug("entering 'list' method...");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "GoodsOrderId", false);
    }
    Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    List localList = getC_GList();
    String str = "";
    Object localObject1 = localList.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (ProsceniumShow)((Iterator)localObject1).next();
      str = str + ((ProsceniumShow)localObject2).getNodeKey() + " " + ((ProsceniumShow)localObject2).getShowProperty() + ",";
    }
    str = str.substring(0, str.length() - 1);
    localObject1 = "select * from (select " + str + paramString + " ) ";
    Object localObject2 = getBeanOfTradeInfoService().getTradeInfoList((String)localObject1, localQueryConditions, localPageInfo);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "bargainInfo/goodsOrderList");
    localModelAndView.addObject("resultList", localObject2);
    localModelAndView.addObject("prosceniumShowList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    localModelAndView.addObject("count", Integer.valueOf(localList.size()));
    return localModelAndView;
  }
  
  public ModelAndView getC_GoodsOrderList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str = " from Z_GoodsOrder cg,Z_TradeCommodityMsg t,Z_Breed b where cg.TradeCommodityMsgId=t.TradeCommodityMsgId and t.BreedId=b.breedId";
    return getC_GList(paramHttpServletRequest, paramHttpServletResponse, str);
  }
  
  public List<ProsceniumShow> getC_DPList()
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("ProsceniumApplication", "=", "BG_C_DiscussPrice");
    return getBeanOfProsceniumShowService().getObjectList(localQueryConditions);
  }
  
  public ModelAndView getC_DPConList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str = " from Z_DiscussPrice dp,Z_TradeCommodityMsg t,Z_Breed b where dp.TradeCommodityMsgId=t.TradeCommodityMsgId and t.BreedId=b.breedId";
    return getC_DPList(paramHttpServletRequest, paramHttpServletResponse, str);
  }
  
  public ModelAndView getC_DPList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws Exception
  {
    this.logger.debug("entering 'list' method...");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "DiscussPriceId", false);
    }
    Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    List localList = getC_DPList();
    String str = "";
    Object localObject1 = localList.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (ProsceniumShow)((Iterator)localObject1).next();
      str = str + ((ProsceniumShow)localObject2).getNodeKey() + " " + ((ProsceniumShow)localObject2).getShowProperty() + ",";
    }
    str = str.substring(0, str.length() - 1);
    localObject1 = "select * from (select " + str + paramString + " ) ";
    Object localObject2 = getBeanOfTradeInfoService().getTradeInfoList((String)localObject1, localQueryConditions, localPageInfo);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "bargainInfo/discussPriceList");
    localModelAndView.addObject("resultList", localObject2);
    localModelAndView.addObject("prosceniumShowList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    localModelAndView.addObject("count", Integer.valueOf(localList.size()));
    return localModelAndView;
  }
  
  public List<ProsceniumShow> getH_GOPSList()
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("ProsceniumApplication", "=", "BG_H_GoodsOrder");
    return getBeanOfProsceniumShowService().getObjectList(localQueryConditions);
  }
  
  public ModelAndView getH_GOConList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str = " from Z_H_GoodsOrder hg,Z_h_TradeCommodityMsg t,Z_Breed b where hg.TradeCommodityMsgId=t.TradeCommodityMsgId and t.BreedId=b.breedId";
    return getH_GOList(paramHttpServletRequest, paramHttpServletResponse, str);
  }
  
  public ModelAndView getH_GOList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws Exception
  {
    this.logger.debug("entering 'list' method...");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "GoodsOrderId", false);
    }
    Map localMap1 = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    List localList = getH_GOPSList();
    String str1 = "";
    Object localObject1 = localList.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (ProsceniumShow)((Iterator)localObject1).next();
      str1 = str1 + ((ProsceniumShow)localObject2).getNodeKey() + " " + ((ProsceniumShow)localObject2).getShowProperty() + ",";
    }
    str1 = str1.substring(0, str1.length() - 1);
    localObject1 = "select * from (select " + str1 + paramString + " ) ";
    Object localObject2 = getBeanOfTradeInfoService().getTradeInfoList((String)localObject1, localQueryConditions, localPageInfo);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    for (int i = 0; i < ((List)localObject2).size(); i++)
    {
      Map localMap2 = (Map)((List)localObject2).get(i);
      Set localSet = localMap2.keySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        if ("CLEARDATE".equals(str2))
        {
          Timestamp localTimestamp = (Timestamp)localMap2.get(str2);
          localMap2.put("CLEARDATE", localSimpleDateFormat.format(localTimestamp));
          ((List)localObject2).set(i, localMap2);
          break;
        }
      }
    }
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "bargainInfo/goodsOrderHisList");
    localModelAndView.addObject("resultList", localObject2);
    localModelAndView.addObject("prosceniumShowList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap1);
    localModelAndView.addObject("count", Integer.valueOf(localList.size()));
    return localModelAndView;
  }
  
  public List<ProsceniumShow> getH_GList()
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("ProsceniumApplication", "=", "BG_H_DiscussPrice");
    return getBeanOfProsceniumShowService().getObjectList(localQueryConditions);
  }
  
  public ModelAndView getH_DisList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str = " from Z_H_DiscussPrice hg,Z_h_TradeCommodityMsg t,Z_Breed b where hg.TradeCommodityMsgId=t.TradeCommodityMsgId and t.BreedId=b.breedId";
    return getH_GList(paramHttpServletRequest, paramHttpServletResponse, str);
  }
  
  public ModelAndView getH_GList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws Exception
  {
    this.logger.debug("entering 'list' method...");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "DiscussPriceId", false);
    }
    Map localMap1 = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    List localList = getH_GList();
    String str1 = "";
    Object localObject1 = localList.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (ProsceniumShow)((Iterator)localObject1).next();
      str1 = str1 + ((ProsceniumShow)localObject2).getNodeKey() + " " + ((ProsceniumShow)localObject2).getShowProperty() + ",";
    }
    str1 = str1.substring(0, str1.length() - 1);
    localObject1 = "select * from (select " + str1 + paramString + " ) ";
    Object localObject2 = getBeanOfTradeInfoService().getTradeInfoList((String)localObject1, localQueryConditions, localPageInfo);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    for (int i = 0; i < ((List)localObject2).size(); i++)
    {
      Map localMap2 = (Map)((List)localObject2).get(i);
      Set localSet = localMap2.keySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        if ("CLEARDATE".equals(str2))
        {
          Timestamp localTimestamp = (Timestamp)localMap2.get(str2);
          localMap2.put("CLEARDATE", localSimpleDateFormat.format(localTimestamp));
          ((List)localObject2).set(i, localMap2);
          break;
        }
      }
    }
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "bargainInfo/discussPriceHisList");
    localModelAndView.addObject("resultList", localObject2);
    localModelAndView.addObject("prosceniumShowList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap1);
    localModelAndView.addObject("count", Integer.valueOf(localList.size()));
    return localModelAndView;
  }
  
  public List<ProsceniumShow> getD_GList()
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("ProsceniumApplication", "=", "BG_D_TradeResult");
    return getBeanOfProsceniumShowService().getObjectList(localQueryConditions);
  }
  
  public ModelAndView getD_TradeResultList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str = " from Z_Trade t,Z_Breed b where t.BreedId=b.breedId";
    return getD_TRList(paramHttpServletRequest, paramHttpServletResponse, str);
  }
  
  public ModelAndView getD_TRList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws Exception
  {
    this.logger.debug("entering 'list' method...");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "TradeNo", false);
    }
    Map localMap1 = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    List localList = getD_GList();
    String str1 = "";
    Object localObject1 = localList.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (ProsceniumShow)((Iterator)localObject1).next();
      str1 = str1 + ((ProsceniumShow)localObject2).getNodeKey() + " " + ((ProsceniumShow)localObject2).getShowProperty() + ",";
    }
    str1 = str1.substring(0, str1.length() - 1);
    localObject1 = "select * from (select " + str1 + paramString + " ) ";
    Object localObject2 = getBeanOfTradeInfoService().getTradeInfoList((String)localObject1, localQueryConditions, localPageInfo);
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    for (int i = 0; i < ((List)localObject2).size(); i++)
    {
      Map localMap2 = (Map)((List)localObject2).get(i);
      Set localSet = localMap2.keySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        Timestamp localTimestamp;
        if ("TRADEDATE".equals(str2))
        {
          localTimestamp = (Timestamp)localMap2.get(str2);
          localMap2.put("TRADEDATE", localSimpleDateFormat2.format(localTimestamp));
          ((List)localObject2).set(i, localMap2);
        }
        else if ("DELIVERYDATE".equals(str2))
        {
          localTimestamp = (Timestamp)localMap2.get(str2);
          localMap2.put("DELIVERYDATE", localSimpleDateFormat2.format(localTimestamp));
          ((List)localObject2).set(i, localMap2);
        }
        else if ("CLEARDATE".equals(str2))
        {
          localTimestamp = (Timestamp)localMap2.get(str2);
          localMap2.put("CLEARDATE", localSimpleDateFormat1.format(localTimestamp));
          ((List)localObject2).set(i, localMap2);
          break;
        }
      }
    }
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "bargainInfo/tradeResultList");
    localModelAndView.addObject("resultList", localObject2);
    localModelAndView.addObject("prosceniumShowList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap1);
    localModelAndView.addObject("count", Integer.valueOf(localList.size()));
    return localModelAndView;
  }
  
  public List<ProsceniumShow> getH_TList()
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("ProsceniumApplication", "=", "BG_H_Trade");
    return getBeanOfProsceniumShowService().getObjectList(localQueryConditions);
  }
  
  public ModelAndView getH_TradeResultList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str = " from Z_H_Trade ht,Z_Breed b where ht.BreedId=b.breedId";
    return getH_TList(paramHttpServletRequest, paramHttpServletResponse, str);
  }
  
  public ModelAndView getH_TList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws Exception
  {
    this.logger.debug("entering 'list' method...");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "TradeNo", false);
    }
    Map localMap1 = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    List localList = getH_TList();
    String str1 = "";
    Object localObject1 = localList.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (ProsceniumShow)((Iterator)localObject1).next();
      str1 = str1 + ((ProsceniumShow)localObject2).getNodeKey() + " " + ((ProsceniumShow)localObject2).getShowProperty() + ",";
    }
    str1 = str1.substring(0, str1.length() - 1);
    localObject1 = "select * from (select " + str1 + paramString + " ) ";
    Object localObject2 = getBeanOfTradeInfoService().getTradeInfoList((String)localObject1, localQueryConditions, localPageInfo);
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    for (int i = 0; i < ((List)localObject2).size(); i++)
    {
      Map localMap2 = (Map)((List)localObject2).get(i);
      Set localSet = localMap2.keySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        Timestamp localTimestamp;
        if ("TRADEDATE".equals(str2))
        {
          localTimestamp = (Timestamp)localMap2.get(str2);
          localMap2.put("TRADEDATE", localSimpleDateFormat1.format(localTimestamp));
          ((List)localObject2).set(i, localMap2);
        }
        else if ("DELIVERYDATE".equals(str2))
        {
          localTimestamp = (Timestamp)localMap2.get(str2);
          localMap2.put("DELIVERYDATE", localSimpleDateFormat1.format(localTimestamp));
          ((List)localObject2).set(i, localMap2);
        }
        else if ("CLEARDATE".equals(str2))
        {
          localTimestamp = (Timestamp)localMap2.get(str2);
          localMap2.put("CLEARDATE", localSimpleDateFormat2.format(localTimestamp));
          ((List)localObject2).set(i, localMap2);
          break;
        }
      }
    }
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "bargainInfo/tradeResultHisList");
    localModelAndView.addObject("resultList", localObject2);
    localModelAndView.addObject("prosceniumShowList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap1);
    localModelAndView.addObject("count", Integer.valueOf(localList.size()));
    return localModelAndView;
  }
  
  public ModelAndView getH_TradeResult(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str = " from Z_H_Trade ht,Z_Breed b,m_firm bf,m_firm sf where ht.BreedId=b.breedId and ht.firmid_s = sf.firmid(+) and ht.firmid_b = bf.firmid(+)";
    return getH_T(paramHttpServletRequest, paramHttpServletResponse, str);
  }
  
  public ModelAndView getH_T(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws Exception
  {
    this.logger.debug("entering 'list' method...");
    String str1 = paramHttpServletRequest.getParameter("contractid");
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("tradeNo", "=", str1);
    Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    List localList = getH_TList();
    String str2 = "";
    Object localObject1 = localList.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (ProsceniumShow)((Iterator)localObject1).next();
      str2 = str2 + ((ProsceniumShow)localObject2).getNodeKey() + " " + ((ProsceniumShow)localObject2).getShowProperty() + ",";
    }
    str2 = str2.substring(0, str2.length() - 1);
    localObject1 = "select * from (select " + str2 + ",ht.note note,ht.deliveryplace deliveryplace,extractValue(xmlType(ht.quality),'/root/quality[name=" + '"' + "生产日期" + '"' + "]/value') shengchanriqi,extractValue(xmlType(ht.quality),'/root/quality[name=" + '"' + "交货日" + '"' + "]/value') jiaohuori,extractValue(xmlType(ht.quality),'/root/quality[name=" + '"' + "产地（生产厂家）" + '"' + "]/value') chandi,bf.fullname bfullname,bf.address baddress,extractValue(xmlType(bf.extenddata),'/root/keyValue[key=" + '"' + "businessContacter" + '"' + "]/value') bbusinessContacter,extractValue(xmlType(bf.extenddata),'/root/keyValue[key=" + '"' + "legalRepresentative" + '"' + "]/value') blegalRepresentative,extractValue(xmlType(bf.extenddata),'/root/keyValue[key=" + '"' + "LRphoneNo" + '"' + "]/value') bLRphoneNo,sf.fullname sfullname,sf.address saddress,extractValue(xmlType(sf.extenddata),'/root/keyValue[key=" + '"' + "businessContacter" + '"' + "]/value') sbusinessContacter,extractValue(xmlType(sf.extenddata),'/root/keyValue[key=" + '"' + "legalRepresentative" + '"' + "]/value') slegalRepresentative,extractValue(xmlType(sf.extenddata),'/root/keyValue[key=" + '"' + "LRphoneNo" + '"' + "]/value') sLRphoneNo ,TO_CHAR(ht.tradedate,'YYYY" + '"' + "年" + '"' + "MM" + '"' + "月" + '"' + "DD" + '"' + "日" + '"' + "') chengjiaoshijian    " + paramString + " ) ";
    Object localObject2 = getBeanOfTradeInfoService().getTradeInfoList((String)localObject1, localQueryConditions, null);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "bargainInfo/ContractHis");
    localModelAndView.addObject("resultList", localObject2);
    return localModelAndView;
  }
  
  public ModelAndView getHisCommodityParameter(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'getCommodityParameter' method...");
    long l = Long.parseLong(paramHttpServletRequest.getParameter("TradeNo"));
    HisTrade localHisTrade = getBeanOfHisTradeService().getObject(l);
    String str1 = localHisTrade.getCommodityProperties();
    String str2 = localHisTrade.getQuality();
    SAXReader localSAXReader = new SAXReader();
    Document localDocument1 = DocumentHelper.parseText(str1);
    Document localDocument2 = DocumentHelper.parseText(str2);
    Element localElement1 = localDocument1.getRootElement();
    Iterator localIterator1 = localElement1.elements().iterator();
    Element localElement2 = localDocument2.getRootElement();
    Iterator localIterator2 = localElement2.elements().iterator();
    ArrayList localArrayList = new ArrayList();
    Element localElement3;
    while (localIterator1.hasNext())
    {
      localObject = new HashMap();
      localElement3 = (Element)localIterator1.next();
      ((Map)localObject).put("id", localElement3.elementText("id"));
      ((Map)localObject).put("name", localElement3.elementText("name"));
      ((Map)localObject).put("value", localElement3.elementText("value"));
      localArrayList.add(localObject);
    }
    while (localIterator2.hasNext())
    {
      localObject = new HashMap();
      localElement3 = (Element)localIterator2.next();
      ((Map)localObject).put("name", localElement3.elementText("name"));
      ((Map)localObject).put("value", localElement3.elementText("value"));
      localArrayList.add(localObject);
    }
    Object localObject = new ModelAndView(Condition.PATH + "bargainInfo/commodityParameter");
    ((ModelAndView)localObject).addObject("list", localArrayList);
    ((ModelAndView)localObject).addObject("tradeNo", Long.valueOf(l));
    return localObject;
  }
  
  public ModelAndView getCommodityParameter(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'getCommodityParameter' method...");
    long l = Long.parseLong(paramHttpServletRequest.getParameter("TradeNo"));
    Trade localTrade = getBeanOfTradeService().getObject(l);
    String str1 = localTrade.getCommodityProperties();
    String str2 = localTrade.getQuality();
    SAXReader localSAXReader = new SAXReader();
    Document localDocument1 = DocumentHelper.parseText(str1);
    Document localDocument2 = DocumentHelper.parseText(str2);
    Element localElement1 = localDocument1.getRootElement();
    Iterator localIterator1 = localElement1.elements().iterator();
    Element localElement2 = localDocument2.getRootElement();
    Iterator localIterator2 = localElement2.elements().iterator();
    ArrayList localArrayList = new ArrayList();
    Element localElement3;
    while (localIterator1.hasNext())
    {
      localObject = new HashMap();
      localElement3 = (Element)localIterator1.next();
      ((Map)localObject).put("id", localElement3.elementText("id"));
      ((Map)localObject).put("name", localElement3.elementText("name"));
      ((Map)localObject).put("value", localElement3.elementText("value"));
      localArrayList.add(localObject);
    }
    while (localIterator2.hasNext())
    {
      localObject = new HashMap();
      localElement3 = (Element)localIterator2.next();
      ((Map)localObject).put("name", localElement3.elementText("name"));
      ((Map)localObject).put("value", localElement3.elementText("value"));
      localArrayList.add(localObject);
    }
    Object localObject = new ModelAndView(Condition.PATH + "bargainInfo/commodityParameter");
    ((ModelAndView)localObject).addObject("list", localArrayList);
    ((ModelAndView)localObject).addObject("tradeNo", Long.valueOf(l));
    return localObject;
  }
  
  public ModelAndView getH_DisParameter(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'getCommodityParameter' method...");
    long l = Long.parseLong(paramHttpServletRequest.getParameter("DiscussPriceId"));
    HisDiscussPrice localHisDiscussPrice = getBeanOfHisDiscussPriceService().getObject(l);
    List localList = getBeanOfHisTradeCommodityMsgService().getObject(localHisDiscussPrice.getTradeCommodityMsgId());
    HisTradeCommodityMsg localHisTradeCommodityMsg = (HisTradeCommodityMsg)localList.get(0);
    String str1 = localHisTradeCommodityMsg.getCommodityProperties();
    String str2 = localHisTradeCommodityMsg.getQuality();
    SAXReader localSAXReader = new SAXReader();
    Document localDocument1 = DocumentHelper.parseText(str1);
    Document localDocument2 = DocumentHelper.parseText(str2);
    Element localElement1 = localDocument1.getRootElement();
    Iterator localIterator1 = localElement1.elements().iterator();
    Element localElement2 = localDocument2.getRootElement();
    Iterator localIterator2 = localElement2.elements().iterator();
    ArrayList localArrayList = new ArrayList();
    Element localElement3;
    while (localIterator1.hasNext())
    {
      localObject = new HashMap();
      localElement3 = (Element)localIterator1.next();
      ((Map)localObject).put("id", localElement3.elementText("id"));
      ((Map)localObject).put("name", localElement3.elementText("name"));
      ((Map)localObject).put("value", localElement3.elementText("value"));
      localArrayList.add(localObject);
    }
    while (localIterator2.hasNext())
    {
      localObject = new HashMap();
      localElement3 = (Element)localIterator2.next();
      ((Map)localObject).put("name", localElement3.elementText("name"));
      ((Map)localObject).put("value", localElement3.elementText("value"));
      localArrayList.add(localObject);
    }
    Object localObject = new ModelAndView(Condition.PATH + "bargainInfo/commodityParameter");
    ((ModelAndView)localObject).addObject("list", localArrayList);
    ((ModelAndView)localObject).addObject("tradeNo", Long.valueOf(l));
    return localObject;
  }
  
  public ModelAndView getDisParameter(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'getCommodityParameter' method...");
    long l = Long.parseLong(paramHttpServletRequest.getParameter("DiscussPriceId"));
    DiscussPrice localDiscussPrice = getBeanOfDiscussPriceService().getObject(l);
    TradeCommodityMsg localTradeCommodityMsg = getBeanOfTradeCommodityMsgService().getObject(localDiscussPrice.getTradeCommodityMsgId());
    String str1 = localTradeCommodityMsg.getCommodityProperties();
    String str2 = localTradeCommodityMsg.getQuality();
    SAXReader localSAXReader = new SAXReader();
    Document localDocument1 = DocumentHelper.parseText(str1);
    Document localDocument2 = DocumentHelper.parseText(str2);
    Element localElement1 = localDocument1.getRootElement();
    Iterator localIterator1 = localElement1.elements().iterator();
    Element localElement2 = localDocument2.getRootElement();
    Iterator localIterator2 = localElement2.elements().iterator();
    ArrayList localArrayList = new ArrayList();
    Element localElement3;
    while (localIterator1.hasNext())
    {
      localObject = new HashMap();
      localElement3 = (Element)localIterator1.next();
      ((Map)localObject).put("id", localElement3.elementText("id"));
      ((Map)localObject).put("name", localElement3.elementText("name"));
      ((Map)localObject).put("value", localElement3.elementText("value"));
      localArrayList.add(localObject);
    }
    while (localIterator2.hasNext())
    {
      localObject = new HashMap();
      localElement3 = (Element)localIterator2.next();
      ((Map)localObject).put("name", localElement3.elementText("name"));
      ((Map)localObject).put("value", localElement3.elementText("value"));
      localArrayList.add(localObject);
    }
    Object localObject = new ModelAndView(Condition.PATH + "bargainInfo/commodityParameter");
    ((ModelAndView)localObject).addObject("list", localArrayList);
    ((ModelAndView)localObject).addObject("tradeNo", Long.valueOf(l));
    return localObject;
  }
  
  public ModelAndView getGoodsOrderParameter(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'getCommodityParameter' method...");
    long l = Long.parseLong(paramHttpServletRequest.getParameter("goodsOrderId"));
    GoodsOrder localGoodsOrder = getBeanOfGoodsOrderService().getObject(l);
    TradeCommodityMsg localTradeCommodityMsg = getBeanOfTradeCommodityMsgService().getObject(localGoodsOrder.getTradeCommodityMsgId());
    String str1 = localTradeCommodityMsg.getCommodityProperties();
    String str2 = localTradeCommodityMsg.getQuality();
    SAXReader localSAXReader = new SAXReader();
    Document localDocument1 = DocumentHelper.parseText(str1);
    Document localDocument2 = DocumentHelper.parseText(str2);
    Element localElement1 = localDocument1.getRootElement();
    Iterator localIterator1 = localElement1.elements().iterator();
    Element localElement2 = localDocument2.getRootElement();
    Iterator localIterator2 = localElement2.elements().iterator();
    ArrayList localArrayList = new ArrayList();
    Element localElement3;
    while (localIterator1.hasNext())
    {
      localObject = new HashMap();
      localElement3 = (Element)localIterator1.next();
      ((Map)localObject).put("id", localElement3.elementText("id"));
      ((Map)localObject).put("name", localElement3.elementText("name"));
      ((Map)localObject).put("value", localElement3.elementText("value"));
      localArrayList.add(localObject);
    }
    while (localIterator2.hasNext())
    {
      localObject = new HashMap();
      localElement3 = (Element)localIterator2.next();
      ((Map)localObject).put("name", localElement3.elementText("name"));
      ((Map)localObject).put("value", localElement3.elementText("value"));
      localArrayList.add(localObject);
    }
    Object localObject = new ModelAndView(Condition.PATH + "bargainInfo/commodityParameter");
    ((ModelAndView)localObject).addObject("list", localArrayList);
    ((ModelAndView)localObject).addObject("tradeNo", Long.valueOf(l));
    return localObject;
  }
  
  public ModelAndView getH_GoodsOrderParameter(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'getCommodityParameter' method...");
    long l = Long.parseLong(paramHttpServletRequest.getParameter("goodsOrderId"));
    HisGoodsOrder localHisGoodsOrder = getBeanOfHisGoodsOrderService().getObject(l);
    List localList = getBeanOfHisTradeCommodityMsgService().getObject(localHisGoodsOrder.getTradeCommodityMsgId());
    HisTradeCommodityMsg localHisTradeCommodityMsg = (HisTradeCommodityMsg)localList.get(0);
    String str1 = localHisTradeCommodityMsg.getCommodityProperties();
    String str2 = localHisTradeCommodityMsg.getQuality();
    SAXReader localSAXReader = new SAXReader();
    Document localDocument1 = DocumentHelper.parseText(str1);
    Document localDocument2 = DocumentHelper.parseText(str2);
    Element localElement1 = localDocument1.getRootElement();
    Iterator localIterator1 = localElement1.elements().iterator();
    Element localElement2 = localDocument2.getRootElement();
    Iterator localIterator2 = localElement2.elements().iterator();
    ArrayList localArrayList = new ArrayList();
    Element localElement3;
    while (localIterator1.hasNext())
    {
      localObject = new HashMap();
      localElement3 = (Element)localIterator1.next();
      ((Map)localObject).put("id", localElement3.elementText("id"));
      ((Map)localObject).put("name", localElement3.elementText("name"));
      ((Map)localObject).put("value", localElement3.elementText("value"));
      localArrayList.add(localObject);
    }
    while (localIterator2.hasNext())
    {
      localObject = new HashMap();
      localElement3 = (Element)localIterator2.next();
      ((Map)localObject).put("name", localElement3.elementText("name"));
      ((Map)localObject).put("value", localElement3.elementText("value"));
      localArrayList.add(localObject);
    }
    Object localObject = new ModelAndView(Condition.PATH + "bargainInfo/commodityParameter");
    ((ModelAndView)localObject).addObject("list", localArrayList);
    ((ModelAndView)localObject).addObject("tradeNo", Long.valueOf(l));
    return localObject;
  }
  
  public ModelAndView SystemCancleSub(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    long l = Long.parseLong(paramHttpServletRequest.getParameter("goodsOrderId"));
    String str1 = "";
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    DataSource localDataSource = localDaoHelper.getDataSource();
    Map localMap = LogonManager.getRMIConfig("3", localDataSource);
    String str2 = (String)localMap.get("host");
    int i = ((Integer)localMap.get("port")).intValue();
    SystemSettingServerRmi localSystemSettingServerRmi = (SystemSettingServerRmi)Naming.lookup("rmi://" + str2 + ":" + i + "/SystemSettingServerRmi");
    try
    {
      str1 = localSystemSettingServerRmi.systemCancleSubRmi(Long.valueOf(l));
    }
    catch (Exception localException)
    {
      str1 = "操作失败！";
      localException.printStackTrace();
    }
    Thread.sleep(2000L);
    setResultMsg(paramHttpServletRequest, str1);
    return getC_GoodsOrderList(paramHttpServletRequest, paramHttpServletResponse);
  }
}
