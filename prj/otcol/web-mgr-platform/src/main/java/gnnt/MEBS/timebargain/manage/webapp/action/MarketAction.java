package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.timebargain.manage.model.Market;
import gnnt.MEBS.timebargain.manage.service.CustomerManager;
import gnnt.MEBS.timebargain.manage.service.MarketManager;
import gnnt.MEBS.timebargain.manage.service.TraderManager;
import gnnt.MEBS.timebargain.manage.webapp.form.MarketForm;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.springframework.dao.DataIntegrityViolationException;

public class MarketAction
  extends BaseAction
{
  public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'edit' method");
    }
    String useDelay = getServlet().getServletContext().getInitParameter("useDelay");
    request.setAttribute("useDelay", useDelay);
    
    MarketForm marketForm = (MarketForm)form;
    
    String crud = "";
    MarketManager mgr = (MarketManager)getBean("marketManager");
    Market market = null;
    try
    {
      String type = "";
      String typeFloat = "";
      List list = mgr.getMarketById(null);
      if ((list != null) && (list.size() > 0))
      {
        Map map = (Map)list.get(0);
        market = new Market();
        if (map.get("MarketCode") != null)
        {
          String s = map.get("MarketCode").toString();
          




          market.setMarketCode(s);
        }
        if (map.get("Status") != null) {
          market.setStatus(Short.valueOf(Short.parseShort(map.get("Status").toString())));
        }
        if (map.get("MarginFBFlag") != null) {
          market.setMarginFBFlag(Short.valueOf(Short.parseShort(map.get("MarginFBFlag").toString())));
        }
        if (map.get("ShortName") != null) {
          market.setShortName(map.get("ShortName").toString());
        }
        if (map.get("MarginPriceType") != null) {
          market.setMarginPriceType(Short.valueOf(Short.parseShort(map.get("MarginPriceType").toString())));
        }
        if (map.get("FloatingLossComputeType") != null) {
          market.setFloatingLossComputeType(Short.valueOf(Short.parseShort(map.get("FloatingLossComputeType").toString())));
        }
        if (map.get("RunMode") != null) {
          market.setRunMode(Short.valueOf(Short.parseShort(map.get("RunMode").toString())));
        }
        if (map.get("CloseAlgr") != null) {
          market.setCloseAlgr(Short.valueOf(Short.parseShort(map.get("CloseAlgr").toString())));
        }
        if (map.get("SettleMode") != null) {
          market.setSettleMode(Short.valueOf(Short.parseShort(map.get("SettleMode").toString())));
        }
        if (map.get("FloatingProfitSubTax") != null) {
          market.setFloatingProfitSubTax(Short.valueOf(Short.parseShort(map.get("FloatingProfitSubTax").toString())));
        }
        if (map.get("GageMode") != null) {
          market.setGageMode(Short.valueOf(Short.parseShort(map.get("GageMode").toString())));
        }
        if (map.get("TradeTimeType") != null) {
          market.setTradeTimeType(Short.valueOf(Short.parseShort(map.get("TradeTimeType").toString())));
        }
        if (map.get("DelayQuoShowType") != null) {
          market.setDelayQuoShowType(Short.valueOf(Short.parseShort(map.get("DelayQuoShowType").toString())));
        }
        if (map.get("NeutralFeeWay") != null) {
          market.setNeutralFeeWay(Short.valueOf(Short.parseShort(map.get("NeutralFeeWay").toString())));
        }
        if (map.get("ASMarginType") != null) {
          market.setAsMarginType(Short.valueOf(Short.parseShort(map.get("ASMarginType").toString())));
        }
        if (map.get("DelayOrderIsPure") != null) {
          market.setDelayOrderIsPure(Short.valueOf(Short.parseShort(map.get("DelayOrderIsPure").toString())));
        }
        if (map.get("ChargeDelayFeeType") != null) {
          market.setChargeDelayFeeType(Short.valueOf(Short.parseShort(map.get("ChargeDelayFeeType").toString())));
        }
        if (map.get("IsCPriceCpFloatingLoss") != null) {
          market.setIsCPriceCpFloatingLoss(Short.valueOf(Short.parseShort(map.get("IsCPriceCpFloatingLoss").toString())));
        }
      }
      if (market != null)
      {
        this.log.debug("edit Market.MarketName:" + market.getMarketName());
        crud = "update";
      }
      else
      {
        market = new Market();
        crud = "create";
      }
      request.setAttribute("type", type);
      request.setAttribute("isCPriceCpFloatingLoss", market.getIsCPriceCpFloatingLoss());
      marketForm = (MarketForm)convert(market);
      if (market.getFloatingLossComputeType() != null)
      {
        String temp = market.getFloatingLossComputeType().toString();
        if (("0".equals(temp)) || ("1".equals(temp)) || ("2".equals(temp)))
        {
          marketForm.setFloatingLossComputeType1(temp);
          typeFloat = "1";
        }
        if (("3".equals(temp)) || ("4".equals(temp)))
        {
          marketForm.setFloatingLossComputeType2(temp);
          typeFloat = "2";
        }
      }
      request.setAttribute("typeFloat", typeFloat);
      marketForm.setCrud(crud);
      updateFormBean(mapping, request, marketForm);
    }
    catch (Exception err)
    {
      this.log.error("==err:" + err);
      err.printStackTrace();
      request.setAttribute("prompt", err.getMessage());
    }
    return mapping.findForward("edit");
  }
  
  public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'save' method");
    }
    MarketForm marketForm = (MarketForm)form;
    String crud = marketForm.getCrud();
    MarketManager mgr = (MarketManager)getBean("marketManager");
    Market market = (Market)convert(marketForm);
    String isCPriceCpFloatingLosss = request.getParameter("isCPriceCpFloatingLosss");
    market.setIsCPriceCpFloatingLoss(Short.valueOf(Short.parseShort(isCPriceCpFloatingLosss)));
    if ((marketForm.getFloatingLossComputeType1() != null) && (!"".equals(marketForm.getFloatingLossComputeType1()))) {
      market.setFloatingLossComputeType(Short.valueOf(Short.parseShort(marketForm.getFloatingLossComputeType1())));
    }
    if ((marketForm.getFloatingLossComputeType2() != null) && (!"".equals(marketForm.getFloatingLossComputeType2()))) {
      market.setFloatingLossComputeType(Short.valueOf(Short.parseShort(marketForm.getFloatingLossComputeType2())));
    }
    try
    {
      if (crud.trim().equals("create"))
      {
        mgr.insertMarket(market);
        addSysLog(request, "增加市场[" + market.getMarketCode() + "]");
        request.setAttribute("prompt", "操作成功！");
      }
      else if (crud.trim().equals("update"))
      {
        mgr.updateMarket(market);
        addSysLog(request, "修改市场[" + market.getMarketCode() + "]");
        request.setAttribute("prompt", "操作成功！");
      }
    }
    catch (Exception err)
    {
      err.printStackTrace();
      this.log.error("===>save err：" + err);
      request.setAttribute("prompt", err.getMessage());
      return mapping.findForward("edit");
    }
    return edit(mapping, form, request, response);
  }
  
  public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'delete' method");
    }
    MarketManager mgr = (MarketManager)getBean("marketManager");
    String[] ids = request.getParameterValues("itemlist");
    int success = 0;
    if (ids != null)
    {
      this.log.debug("==ids.length:" + ids.length);
      
      String prompt = "";
      for (int i = 0; i < ids.length; i++)
      {
        String id = ids[i];
        try
        {
          mgr.deleteMarketById(id);
          addSysLog(request, "删除市场[" + id + "]");
          success++;
        }
        catch (DataIntegrityViolationException e)
        {
          prompt = prompt + id + ",";
          request.setAttribute("prompt", "[" + id + "]与其他数据关联，删除失败！");
        }
        catch (RuntimeException e)
        {
          prompt = prompt + id + ",";
          request.setAttribute("prompt", "[" + id + "]与其他数据关联，删除失败！");
        }
      }
      if (!prompt.equals(""))
      {
        prompt = prompt.substring(0, prompt.length() - 1);
        prompt = prompt + "与其他数据关联，不能删除！";
      }
      prompt = prompt + "成功删除" + success + "条纪录！";
      request.setAttribute("prompt", prompt);
    }
    return search(mapping, form, request, response);
  }
  
  public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'search' method");
    }
    MarketManager mgr = (MarketManager)getBean("marketManager");
    Market market = new Market();
    try
    {
      List lst = mgr.getMarkets(market);
      request.setAttribute("marketList", lst);
      request.setAttribute("MARKET_STATUS", CommonDictionary.MARKET_STATUS);
    }
    catch (Exception err)
    {
      this.log.error("查询Market表出错：" + err.getMessage());
      request.setAttribute("prompt", err.getMessage());
    }
    return mapping.findForward("list");
  }
  
  public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return edit(mapping, form, request, response);
  }
  
  public ActionForward modifyPwd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'modifyPwd' method");
    }
    String type = request.getParameter("type") == null ? "" : request.getParameter("type");
    String oldpwd = request.getParameter("oldpwd") == null ? "" : request.getParameter("oldpwd");
    String newpwd = request.getParameter("newpwd") == null ? "" : request.getParameter("newpwd");
    String prikey = request.getParameter("prikey") == null ? "" : request.getParameter("prikey");
    try
    {
      if (type.equals("market"))
      {
        String type1 = request.getParameter("type1") == null ? "" : request.getParameter("type1");
        AgencyRMIBean rmiBean;
        if (type1.equals("1"))
        {
          rmiBean = new AgencyRMIBean(request);
        }
        else if (type1.equals("2"))
        {
          MarketManager mgr = (MarketManager)getBean("marketManager");
          mgr.updateTradePassword(oldpwd, newpwd, prikey, type1);
          addSysLog(request, "修改市场[" + prikey + "]的本地摊位口令");
        }
      }
      else if (type.equals("customer"))
      {
        CustomerManager mgr = (CustomerManager)getBean("customerManager");
        mgr.updateCustomerPassword(oldpwd, newpwd, prikey);
        addSysLog(request, "修改客户[" + prikey + "]的口令");
      }
      else if (type.equals("trader"))
      {
        TraderManager mgr = (TraderManager)getBean("traderManager");
        mgr.updateTraderPassword(oldpwd, newpwd, prikey);
        addSysLog(request, "修改交易员[" + prikey + "]的口令");
      }
      else if (type.equals("consigner"))
      {
        TraderManager mgr = (TraderManager)getBean("traderManager");
        mgr.updateConsignerPassword(oldpwd, newpwd, prikey);
        addSysLog(request, "修改代为委托员[" + prikey + "]的口令");
      }
      else if (!type.equals("user"))
      {
        throw new Exception("未指定修改口令类型！");
      }
    }
    catch (Exception err)
    {
      err.printStackTrace();
      this.log.error("===>modifyPwd err：" + err);
      
      request.setAttribute("prompt", err.getMessage());
    }
    return mapping.findForward("modifyConsigner");
  }
  
  public ActionForward modifyPwd2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'modifyPwd2' method");
    }
    String type = request.getParameter("type") == null ? "" : request.getParameter("type");
    String oldpwd = request.getParameter("oldpwd") == null ? "" : request.getParameter("oldpwd");
    String newpwd = request.getParameter("newpwd") == null ? "" : request.getParameter("newpwd");
    String prikey = request.getParameter("prikey") == null ? "" : request.getParameter("prikey");
    try
    {
      if (type.equals("market"))
      {
        String type1 = request.getParameter("type1") == null ? "" : request.getParameter("type1");
        AgencyRMIBean rmiBean;
        if (type1.equals("1"))
        {
          rmiBean = new AgencyRMIBean(request);
        }
        else if (type1.equals("2"))
        {
          MarketManager mgr = (MarketManager)getBean("marketManager");
          mgr.updateTradePassword(oldpwd, newpwd, prikey, type1);
          addSysLog(request, "修改市场[" + prikey + "]的本地摊位口令");
        }
      }
      else if (type.equals("customer"))
      {
        CustomerManager mgr = (CustomerManager)getBean("customerManager");
        mgr.updateCustomerPassword(oldpwd, newpwd, prikey);
        addSysLog(request, "修改客户[" + prikey + "]的口令");
      }
      else if (type.equals("trader"))
      {
        TraderManager mgr = (TraderManager)getBean("traderManager");
        mgr.updateTraderPassword(oldpwd, newpwd, prikey);
        addSysLog(request, "修改交易员[" + prikey + "]的口令");
      }
      else if (type.equals("consigner"))
      {
        TraderManager mgr = (TraderManager)getBean("traderManager");
        mgr.updateConsignerPassword(oldpwd, newpwd, prikey);
        addSysLog(request, "修改代为委托员[" + prikey + "]的口令");
      }
      else if (!type.equals("user"))
      {
        throw new Exception("未指定修改口令类型！");
      }
    }
    catch (Exception err)
    {
      err.printStackTrace();
      this.log.error("===>modifyPwd err：" + err);
      
      request.setAttribute("prompt", err.getMessage());
    }
    return mapping.findForward("modifyConsigner2");
  }
}
