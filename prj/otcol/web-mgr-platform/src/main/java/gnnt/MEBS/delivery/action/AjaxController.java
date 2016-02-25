package gnnt.MEBS.delivery.action;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.delivery.model.Commodity;
import gnnt.MEBS.delivery.model.CommodityExpansion;
import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.delivery.model.Warehouse;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.EnterApply;
import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.CommodityService;
import gnnt.MEBS.delivery.services.DealerService;
import gnnt.MEBS.delivery.services.EnterApplyService;
import gnnt.MEBS.delivery.services.EnterWareService;
import gnnt.MEBS.delivery.services.RegStockService;
import gnnt.MEBS.delivery.services.SettleMatchService;
import gnnt.MEBS.delivery.services.WarehouseService;
import gnnt.MEBS.delivery.util.SysData;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.springframework.web.servlet.ModelAndView;

public class AjaxController
  extends BaseController
{
  public void ajaxShow(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws IOException
  {
    paramHttpServletResponse.setHeader("Cache-Control", "no-cache");
    paramHttpServletResponse.setHeader("Cache-Control", "no-store");
    paramHttpServletResponse.setHeader("Pragma", "no-cache");
    paramHttpServletResponse.setContentType("text/xml");
    paramHttpServletResponse.setCharacterEncoding("GBK");
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    localStringBuffer.append("<context>");
    localStringBuffer.append(paramString);
    localStringBuffer.append("</context>");
    this.logger.debug("ret:" + localStringBuffer);
    localPrintWriter.flush();
    localPrintWriter.print(localStringBuffer);
    localPrintWriter.close();
  }
  
  public ModelAndView getFirm(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'getFirm' method...");
    String str = delNull(paramHttpServletRequest.getParameter("firmId"));
    DealerService localDealerService = (DealerService)SysData.getBean("w_dealerService");
    Dealer localDealer = localDealerService.getDealerById(str);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<dealer>");
    if (localDealer != null)
    {
      localStringBuffer.append("<result>1</result>");
      localStringBuffer.append("<name>" + delNull(localDealer.getName()) + "</name>");
      localStringBuffer.append("<linkman>" + delNull(localDealer.getLinkman()) + "</linkman>");
      localStringBuffer.append("<tel>" + delNull(localDealer.getTel()) + "</tel>");
    }
    else
    {
      localStringBuffer.append("<result>-1</result>");
    }
    localStringBuffer.append("</dealer>");
    ajaxShow(paramHttpServletRequest, paramHttpServletResponse, localStringBuffer.toString());
    return null;
  }
  
  public ModelAndView getWarehouse(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    try
    {
      this.logger.debug("entering 'getWarehouse' method...");
      String str = delNull(paramHttpServletRequest.getParameter("warehouseId"));
      WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
      Warehouse localWarehouse = localWarehouseService.getWarehouseById(str, true, false);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("<warehouse>");
      if (localWarehouse != null)
      {
        localStringBuffer.append("<result>1</result>");
        Iterator localIterator = localWarehouse.getCommoditySet().iterator();
        while (localIterator.hasNext())
        {
          Commodity localCommodity = (Commodity)localIterator.next();
          localStringBuffer.append("<warehousecommodityid>" + localCommodity.getId() + "</warehousecommodityid>");
          localStringBuffer.append("<warehousecommodityname>" + localCommodity.getName() + "</warehousecommodityname>");
        }
      }
      else
      {
        localStringBuffer.append("<result>-1</result>");
      }
      localStringBuffer.append("</warehouse>");
      ajaxShow(paramHttpServletRequest, paramHttpServletResponse, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public ModelAndView getCommodity(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    Commodity localCommodity = null;
    String str = delNull(paramHttpServletRequest.getParameter("commodityId"));
    this.logger.debug("commodityId:" + str);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<commodity>");
    if ((str != null) && (!str.equals("")))
    {
      localCommodity = localCommodityService.getCommodityById(str, true);
      localStringBuffer.append("<result>1</result>");
      int i = 0;
      int j = localCommodity.getOriginList().size();
      while (i < j)
      {
        localStringBuffer.append("<origin>" + ((CommodityExpansion)localCommodity.getOriginList().get(i)).getName() + "</origin>");
        i++;
      }
      i = 0;
      j = localCommodity.getGradeList().size();
      while (i < j)
      {
        localStringBuffer.append("<grade>" + ((CommodityExpansion)localCommodity.getGradeList().get(i)).getName() + "</grade>");
        i++;
      }
      i = 0;
      j = localCommodity.getSortList().size();
      while (i < j)
      {
        localStringBuffer.append("<sort>" + ((CommodityExpansion)localCommodity.getSortList().get(i)).getName() + "</sort>");
        i++;
      }
      i = 0;
      j = localCommodity.getQualityList().size();
      while (i < j)
      {
        localStringBuffer.append("<quality>" + ((CommodityExpansion)localCommodity.getQualityList().get(i)).getName() + "</quality>");
        i++;
      }
      localStringBuffer.append("<unit>" + localCommodity.getCountType() + "</unit>");
      localStringBuffer.append("<minjs>" + localCommodity.getMinJS() + "</minjs>");
    }
    else
    {
      localStringBuffer.append("<result>-1</result>");
    }
    localStringBuffer.append("</commodity>");
    this.logger.debug("ret:" + localStringBuffer);
    ajaxShow(paramHttpServletRequest, paramHttpServletResponse, localStringBuffer.toString());
    return null;
  }
  
  public ModelAndView enterWareAjax(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------出库单增加ajax---------enter outWareAjax()----//");
    String str1 = paramHttpServletRequest.getParameter("enterWareId");
    EnterWare localEnterWare = new EnterWare();
    List localList = null;
    this.logger.debug("enterWareId:" + str1);
    StringBuffer localStringBuffer = new StringBuffer();
    Dealer localDealer = null;
    Warehouse localWarehouse = null;
    Commodity localCommodity = null;
    String str2 = paramHttpServletRequest.getParameter("valueId");
    String str3 = paramHttpServletRequest.getParameter("outValue");
    this.logger.debug("valueId::" + str2);
    this.logger.debug("keyId::" + str3);
    localStringBuffer.append("<commodity>");
    if ((str1 != null) && (!str1.equals("")))
    {
      EnterWareService localEnterWareService = (EnterWareService)SysData.getBean("w_enterWareService");
      QueryConditions localQueryConditions = new QueryConditions();
      localQueryConditions.addCondition("id", "=", str1);
      if ((str3 != null) && (!"".equalsIgnoreCase(str3))) {
        localQueryConditions.addCondition(str3, "=", str2);
      }
      localList = localEnterWareService.getEnterWareList(localQueryConditions, null);
      if (localList.size() > 0)
      {
        Map localMap = (Map)localList.get(0);
        String str4 = (String)localMap.get("ID");
        localEnterWare = localEnterWareService.getEnterWareById(str4);
        DealerService localDealerService = (DealerService)SysData.getBean("w_dealerService");
        localDealer = localDealerService.getDealerById(localEnterWare.getFirmId());
        WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
        localWarehouse = localWarehouseService.getWarehouseById(localEnterWare.getWarehouseId(), false, false);
        CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
        localCommodity = localCommodityService.getCommodityById(localEnterWare.getCommodityId(), true);
        double d = Arith.sub(localEnterWare.getExistAmount(), localEnterWare.getFrozenAmount());
        String str5 = localCommodity.getCountType();
        if (str5 == null) {
          str5 = "";
        }
        System.out.println(localCommodity.getCountType());
        localStringBuffer.append("<availablenum>" + d + "</availablenum>");
        localStringBuffer.append("<countType>" + str5 + "</countType>");
        localStringBuffer.append("<dealerid>" + localDealer.getFirmId() + "</dealerid>");
        localStringBuffer.append("<dealername>" + localDealer.getName() + "</dealername>");
        localStringBuffer.append("<warehouseid>" + localWarehouse.getId() + "</warehouseid>");
        localStringBuffer.append("<warehousename>" + localWarehouse.getName() + "</warehousename>");
        localStringBuffer.append("<unitWeight>" + localEnterWare.getUnitWeight() + "</unitWeight>");
        localStringBuffer.append("<commodityid>" + localCommodity.getId() + "</commodityid>");
        localStringBuffer.append("<commodityname>" + localCommodity.getName() + "</commodityname>");
        localStringBuffer.append("<agency>" + localEnterWare.getAgency() + "</agency>");
        localStringBuffer.append("<responsibleman>" + localEnterWare.getResponsibleman() + "</responsibleman>");
        localStringBuffer.append("<dealerAgency>" + localEnterWare.getDealerAgency() + "</dealerAgency>");
        if (localEnterWare.getAbility() == 3) {
          localStringBuffer.append("<result>1</result>");
        } else {
          localStringBuffer.append("<result>-2</result>");
        }
      }
      else
      {
        localStringBuffer.append("<result>-1</result>");
      }
    }
    else
    {
      localStringBuffer.append("<result>-1</result>");
    }
    localStringBuffer.append("</commodity>");
    ajaxShow(paramHttpServletRequest, paramHttpServletResponse, localStringBuffer.toString());
    return null;
  }
  
  public ModelAndView dealerAjax(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("--------非标准注册仓单及临时注册仓单增加ajax--------- dealerAjax()----//");
    DealerService localDealerService = (DealerService)SysData.getBean("w_dealerService");
    String str = delNull(paramHttpServletRequest.getParameter("dealerId"));
    Dealer localDealer = localDealerService.getDealerById(str);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<commodity>");
    localStringBuffer.append("<dealer>" + str + "</dealer>");
    if ((localDealer != null) && (!"".equals(localDealer)))
    {
      localStringBuffer.append("<linkman>" + localDealer.getLinkman() + "</linkman>");
      localStringBuffer.append("<tel>" + localDealer.getTel() + "</tel>");
      localStringBuffer.append("<name>" + localDealer.getName() + "</name>");
      localStringBuffer.append("<result>1</result>");
    }
    else
    {
      localStringBuffer.append("<result>-1</result>");
    }
    localStringBuffer.append("</commodity>");
    ajaxShow(paramHttpServletRequest, paramHttpServletResponse, localStringBuffer.toString());
    return null;
  }
  
  public ModelAndView getEnterApplyInform(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter getEnterApplyInform-------------");
    StringBuffer localStringBuffer = new StringBuffer();
    String str1 = paramHttpServletRequest.getParameter("enterInformId");
    this.logger.debug("enterInformId:" + str1);
    String str2 = paramHttpServletRequest.getParameter("conditionName");
    String str3 = paramHttpServletRequest.getParameter("conditionValue");
    String str4 = paramHttpServletRequest.getParameter("warehouseID");
    EnterApplyService localEnterApplyService = (EnterApplyService)SysData.getBean("w_enterApplyService");
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    DealerService localDealerService = (DealerService)SysData.getBean("w_dealerService");
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("id", "=", str1);
    if ((str2 != null) && (!"".equals(str2)) && (str3 != null) && (!"".equals(str3))) {
      localQueryConditions.addCondition(str2, "=", str3);
    }
    if ((str4 != null) && (!"".equals(str4))) {
      localQueryConditions.addCondition("warehouseID", "=", str4);
    }
    List localList = localEnterApplyService.getEnterApplys(localQueryConditions);
    if ((localList != null) && (localList.size() > 0))
    {
      EnterApply localEnterApply = (EnterApply)localList.get(0);
      Commodity localCommodity = localCommodityService.getCommodityById(localEnterApply.getCommodityId(), true);
      Warehouse localWarehouse = localWarehouseService.getWarehouse(localEnterApply.getWarehouseId());
      Dealer localDealer = localDealerService.getDealerById(localEnterApply.getFirmId());
      if ((localEnterApply.getAbility() == 3) && (localEnterApply.getInformAbility() == 0))
      {
        this.logger.debug("ajax result = 1");
        localStringBuffer.append("<result>1</result>");
        if (localDealer != null)
        {
          localStringBuffer.append("<firmId>" + localDealer.getFirmId() + "</firmId>");
          localStringBuffer.append("<firmName>" + localDealer.getName() + "</firmName>");
        }
        if (localWarehouse != null)
        {
          localStringBuffer.append("<warehouseId>" + localWarehouse.getId() + "</warehouseId>");
          localStringBuffer.append("<warehouseName>" + localWarehouse.getName() + "</warehouseName>");
        }
        if (localCommodity != null)
        {
          localStringBuffer.append("<commodityName>" + localCommodity.getName() + "</commodityName>");
          int i = 0;
          int j = localCommodity.getGradeList().size();
          while (i < j)
          {
            localStringBuffer.append("<grade>" + ((CommodityExpansion)localCommodity.getGradeList().get(i)).getName() + "</grade>");
            i++;
          }
          i = 0;
          j = localCommodity.getOriginList().size();
          while (i < j)
          {
            localStringBuffer.append("<origin>" + ((CommodityExpansion)localCommodity.getOriginList().get(i)).getName() + "</origin>");
            i++;
          }
          i = 0;
          j = localCommodity.getSortList().size();
          while (i < j)
          {
            localStringBuffer.append("<sort>" + ((CommodityExpansion)localCommodity.getSortList().get(i)).getName() + "</sort>");
            i++;
          }
          i = 0;
          j = localCommodity.getQualityList().size();
          while (i < j)
          {
            localStringBuffer.append("<quality><![CDATA[" + ((CommodityExpansion)localCommodity.getQualityList().get(i)).getName() + "]]></quality>");
            i++;
          }
          localStringBuffer.append("<countType>" + localCommodity.getCountType() + "</countType>");
        }
        localStringBuffer.append("<commodityId>" + localEnterApply.getCommodityId() + "</commodityId>");
        localStringBuffer.append("<lot>" + localEnterApply.getLot() + "</lot>");
        localStringBuffer.append("<productionDate>" + localEnterApply.getProductionDate() + "</productionDate>");
        localStringBuffer.append("<weight>" + localEnterApply.getWeight() + "</weight>");
        localStringBuffer.append("<quantity>" + localEnterApply.getQuantity() + "</quantity>");
        localStringBuffer.append("<unitWeight>" + localEnterApply.getUnitWeight() + "</unitWeight>");
        localStringBuffer.append("<packaging>" + localEnterApply.getPackaging() + "</packaging>");
        localStringBuffer.append("<chooseGrade>" + localEnterApply.getGrade() + "</chooseGrade>");
        localStringBuffer.append("<chooseSort>" + localEnterApply.getSort() + "</chooseSort>");
        localStringBuffer.append("<chooseOrigin>" + localEnterApply.getOrigin() + "</chooseOrigin>");
      }
      else
      {
        localStringBuffer.append("<result>0</result>");
      }
    }
    else
    {
      localStringBuffer.append("<result>-1</result>");
    }
    this.logger.debug("getEnterApplyInform ajaxRet:" + localStringBuffer.toString());
    ajaxShow(paramHttpServletRequest, paramHttpServletResponse, localStringBuffer.toString());
    return null;
  }
  
  public ModelAndView settleAddMatchIdAjax(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//---添加交收信息ajax--enter settleAddAjax()-------------");
    String str1 = paramHttpServletRequest.getParameter("matchId");
    this.logger.debug("matchId:" + str1);
    StringBuffer localStringBuffer = new StringBuffer();
    SettleMatch localSettleMatch = null;
    SettleMatchService localSettleMatchService = (SettleMatchService)SysData.getBean("w_settleMatchService");
    localSettleMatch = localSettleMatchService.getSettleMatchById(str1);
    double d1 = localSettleMatchService.getRelatedChildAmount(str1);
    double d2 = 0.0D;
    ArrayList localArrayList = (ArrayList)SysData.getBean("w_settleAddUseModuleList");
    double d3 = 0.0D;
    int i = -1;
    int j = 0;
    String str2 = "配对信息非废除状态！";
    if (localSettleMatch != null)
    {
      d2 = localSettleMatch.getWeight() - d1;
      d3 = Arith.sub(localSettleMatch.getWeight(), d1);
      if (localSettleMatch.getStatus() == 3) {
        i = 1;
      }
      if (d3 <= 0.0D)
      {
        i = -2;
        str2 = "配对数量不足！";
      }
      if (!localArrayList.contains(localSettleMatch.getModuleId()))
      {
        i = -4;
        str2 = "该模块不允许增加交收信息!";
      }
    }
    else
    {
      i = -3;
      str2 = "无此配对信息编号！";
    }
    if (i < 0)
    {
      localStringBuffer.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
      localStringBuffer.append("<root>");
      localStringBuffer.append("<resultInt>" + i + "</resultInt>");
      localStringBuffer.append("<resultMsg>" + str2 + "</resultMsg>");
      localStringBuffer.append("</root>");
      this.logger.debug("ajaxCheck:" + localStringBuffer);
    }
    else
    {
      String str3 = localSettleMatch.getInitXml().substring(0, localSettleMatch.getInitXml().length() - 7);
      localSettleMatch.setInitXml(str3 + "<availablenum>" + d2 + "</availablenum></root>");
      localStringBuffer.append(localSettleMatch.getInitXml());
    }
    this.logger.debug("settleAddMatchIdAjax ajaxRet:" + localStringBuffer.toString());
    ajaxShow(paramHttpServletRequest, paramHttpServletResponse, localStringBuffer.toString());
    return null;
  }
  
  public ModelAndView settleAddregStockIdAjax(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//---添加交收信息ajax--enter settleAddregStockIdAjax()-------------");
    String str1 = paramHttpServletRequest.getParameter("regStockId");
    String str2 = paramHttpServletRequest.getParameter("breedId");
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 1;
    String str3 = "";
    RegStock localRegStock = null;
    if ((str2 != null) && (!"".equals(str2)))
    {
      long l = Long.parseLong(str2);
      this.logger.debug("regStockId:" + str1);
      SettleMatchService localSettleMatchService = (SettleMatchService)SysData.getBean("w_settleMatchService");
      SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str1);
      RegStockService localRegStockService = (RegStockService)SysData.getBean("w_regStockService");
      localRegStock = localRegStockService.getRegStockById(str1);
      localStringBuffer.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
      localStringBuffer.append("<context>");
      if (localRegStock == null)
      {
        i = -1;
        str3 = "无此注册仓单!";
      }
      else if (localRegStock.getBreedId() != l)
      {
        i = -2;
        str3 = "该注册仓单品种不匹配!";
      }
    }
    else
    {
      i = -3;
      str3 = "请先填写商品品种代码！";
    }
    if (i < 0)
    {
      localStringBuffer.append("<resultInt>" + i + "</resultInt>");
      localStringBuffer.append("<resultMsg>" + str3 + "</resultMsg>");
      localStringBuffer.append("</context>");
    }
    else
    {
      localStringBuffer.append("<availableweight>");
      localStringBuffer.append(Arith.sub(localRegStock.getWeight(), localRegStock.getFrozenWeight()));
      localStringBuffer.append("</availableweight>");
      localStringBuffer.append("</context>");
    }
    this.logger.debug("settleAddMatchIdAjax ajaxRet:" + localStringBuffer.toString());
    ajaxShow(paramHttpServletRequest, paramHttpServletResponse, localStringBuffer.toString());
    return null;
  }
  
  public ModelAndView settleModifyAjax(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter settleModifyajax-------------");
    String str = paramHttpServletRequest.getParameter("regStockId");
    StringBuffer localStringBuffer = new StringBuffer();
    RegStockService localRegStockService = (RegStockService)SysData.getBean("w_regStockService");
    RegStock localRegStock = localRegStockService.getRegStockById(str);
    localStringBuffer.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    localStringBuffer.append("<root>");
    if (localRegStock != null)
    {
      localStringBuffer.append("<id>");
      localStringBuffer.append(localRegStock.getRegStockId());
      localStringBuffer.append("</id>");
      localStringBuffer.append("<breedid>");
      localStringBuffer.append(localRegStock.getBreedId());
      localStringBuffer.append("</breedid>");
      localStringBuffer.append("<weight>");
      localStringBuffer.append(localRegStock.getWeight());
      localStringBuffer.append("</weight>");
      localStringBuffer.append("<frozenweight>");
      localStringBuffer.append(localRegStock.getFrozenWeight());
      localStringBuffer.append("</frozenweight>");
    }
    localStringBuffer.append("</root>");
    this.logger.debug("settleAddMatchIdAjax ajaxRet:" + localStringBuffer.toString());
    ajaxShow(paramHttpServletRequest, paramHttpServletResponse, localStringBuffer.toString());
    return null;
  }
}
