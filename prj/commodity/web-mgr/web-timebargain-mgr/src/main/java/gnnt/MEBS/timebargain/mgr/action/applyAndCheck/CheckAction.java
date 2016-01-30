package gnnt.MEBS.timebargain.mgr.action.applyAndCheck;

import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.action.ECSideAction;
import gnnt.MEBS.timebargain.mgr.model.apply.ApplyModel;
import gnnt.MEBS.timebargain.mgr.model.apply.Apply_T;
import gnnt.MEBS.timebargain.mgr.model.apply.Apply_T_CommodityFee;
import gnnt.MEBS.timebargain.mgr.model.apply.Apply_T_CommodityMargin;
import gnnt.MEBS.timebargain.mgr.model.apply.Apply_T_PledgeMoney;
import gnnt.MEBS.timebargain.mgr.model.apply.CommodityFeeApply;
import gnnt.MEBS.timebargain.mgr.model.apply.CommodityMarginApply;
import gnnt.MEBS.timebargain.mgr.model.apply.PledgeApply;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Commodity;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Tariff;
import gnnt.MEBS.timebargain.mgr.service.ApplyAndCheckService;
import gnnt.MEBS.timebargain.mgr.service.TradeParamsService;
import gnnt.MEBS.timebargain.mgr.statictools.CommonDictionary;
import gnnt.MEBS.timebargain.mgr.statictools.ParamUtil;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("request")
public class CheckAction extends ECSideAction
{
  private Apply_T apply;

  @Resource(name="applyAndCheck_algrMap")
  private Map<String, String> applyAndCheck_algrMap;

  @Autowired
  @Qualifier("com_applyAndCheckService")
  private ApplyAndCheckService acService;

  public Apply_T getApply()
  {
    return this.apply;
  }

  private void setApply(String paramString)
  {
    this.apply.setContent(paramString);
  }

  public Map<String, String> getApplyAndCheck_algrMap()
  {
    return this.applyAndCheck_algrMap;
  }

  public void setApplyAndCheck_algrMap(Map<String, String> paramMap)
  {
    this.applyAndCheck_algrMap = paramMap;
  }

  public String listPledgeChe()
    throws Exception
  {
    PageRequest localPageRequest = super.getPageRequest(this.request);
    String str1 = this.request.getParameter("firmID");
    String str2 = this.request.getParameter("status");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str3 = localUser.getUserId();
    Apply_T_PledgeMoney localApply_T_PledgeMoney = new Apply_T_PledgeMoney();
    localApply_T_PledgeMoney.setFirmId(str1);
    if ((str2 != null) && (!"".equals(str2)))
      localApply_T_PledgeMoney.setStatus(Integer.parseInt(str2));
    localApply_T_PledgeMoney.setProposer(str3);
    localApply_T_PledgeMoney.setApplyType(2);
    this.request.setAttribute("VIRTUALFUNDAPPLYTYPE", CommonDictionary.VIRTUALFUNDAPPLYTYPE);
    this.request.setAttribute("VIRTUALFUNDSTATUS", CommonDictionary.VIRTUALFUNDSTATUS);
    this.request.setAttribute("OPERATECHECK", CommonDictionary.OPERATECHECK);
    String str4 = "(" + localApply_T_PledgeMoney.toQury() + " where 1=1 " + localApply_T_PledgeMoney.toFilter() + localApply_T_PledgeMoney.toOrder() + ")";
    return listByLimit(localPageRequest, str4);
  }

  public String updatePledgeChe()
    throws Exception
  {
    Apply_T_PledgeMoney localApply_T_PledgeMoney = new Apply_T_PledgeMoney();
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str = localUser.getUserId();
    ParamUtil.bindData(this.request, localApply_T_PledgeMoney);
    localApply_T_PledgeMoney.setApprover(str);
    int i = this.acService.updatePledgeCheck(localApply_T_PledgeMoney);
    if (i == -1)
      addReturnValue(1, 151103L, new Object[] { "此记录已审核完毕！" });
    else if (i == 0)
      addReturnValue(1, 151103L, new Object[] { "操作成功！" });
    else if (i == 1)
      addReturnValue(1, 151103L, new Object[] { "交易商不存在！" });
    else if (i == 2)
      addReturnValue(1, 151103L, new Object[] { "质押资金总额不能为负，审核失败！" });
    else if (i == 3)
      addReturnValue(1, 151103L, new Object[] { "仓单号不存在，审核失败" });
    return "success";
  }

  public String viewByIdApply()
    throws Exception
  {
    ApplyModel localApplyModel = (ApplyModel)getService().get(this.entity);
    this.entity = localApplyModel;
    this.apply = new PledgeApply();
    setApply(localApplyModel.getContent());
    String str1 = "";
    String str2 = "";
    if (localApplyModel != null)
    {
      if ("1".equals(localApplyModel.getStatus() + ""))
        str1 = "待审核";
      else if ("2".equals(localApplyModel.getStatus() + ""))
        str1 = "审核通过";
      else if ("3".equals(localApplyModel.getStatus() + ""))
        str1 = "审核不通过";
      if (getApply().getType() == 1)
        str2 = "添加";
      else if (getApply().getType() == 2)
        str2 = "删除";
    }
    this.request.setAttribute("status", str1);
    this.request.setAttribute("type", str2);
    return "success";
  }

  public String listCommodityFeeChe()
    throws Exception
  {
    PageRequest localPageRequest = super.getPageRequest(this.request);
    String str1 = this.request.getParameter("commodityID");
    String str2 = this.request.getParameter("status");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str3 = localUser.getUserId();
    Apply_T_CommodityFee localApply_T_CommodityFee = new Apply_T_CommodityFee();
    localApply_T_CommodityFee.setCommodityID(str1);
    if ((str2 != null) && (!"".equals(str2)))
      localApply_T_CommodityFee.setStatus(Integer.parseInt(str2));
    localApply_T_CommodityFee.setProposer(str3);
    localApply_T_CommodityFee.setApplyType(3);
    this.request.setAttribute("FEEALGR", CommonDictionary.FEEALGR);
    this.request.setAttribute("VIRTUALFUNDSTATUS", CommonDictionary.VIRTUALFUNDSTATUS);
    String str4 = "(" + localApply_T_CommodityFee.toQury() + " where 1=1 " + localApply_T_CommodityFee.toFilter() + localApply_T_CommodityFee.toOrder() + ")";
    return listByLimit(localPageRequest, str4);
  }

  public String viewByIdCommodityFee()
    throws Exception
  {
    ApplyModel localApplyModel = (ApplyModel)getService().get(this.entity);
    this.entity = localApplyModel;
    this.apply = new CommodityFeeApply();
    setApply(localApplyModel.getContent());
    String str = "";
    if (localApplyModel != null)
      if ("1".equals(localApplyModel.getStatus() + ""))
        str = "待审核";
      else if ("2".equals(localApplyModel.getStatus() + ""))
        str = "审核通过";
      else if ("3".equals(localApplyModel.getStatus() + ""))
        str = "审核不通过";
    this.request.setAttribute("status", str);
    this.request.setAttribute("commodityFeeApply", this.apply);
    return "success";
  }

  public String updateCommodityFee()
    throws Exception
  {
    Apply_T_CommodityFee localApply_T_CommodityFee = new Apply_T_CommodityFee();
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str = localUser.getUserId();
    ParamUtil.bindData(this.request, localApply_T_CommodityFee);
    localApply_T_CommodityFee.setApprover(str);
    int i = this.acService.updateCommodityFeeCheck(localApply_T_CommodityFee);
    if (localApply_T_CommodityFee.getStatus() == 2)
    {
      QueryConditions localQueryConditions = new QueryConditions("primary.commodityID", "=", localApply_T_CommodityFee.getCommodityID());
      PageRequest localPageRequest = new PageRequest(localQueryConditions);
      localPageRequest.setPageNumber(1);
      localPageRequest.setPageSize(10000);
      Page localPage = getService().getPage(localPageRequest, new Tariff());
      Commodity localCommodity = new Commodity();
      localCommodity.setFeeAlgr(localApply_T_CommodityFee.getFeeAlgr());
      localCommodity.setFeeRate_B(localApply_T_CommodityFee.getFeeRate_B());
      localCommodity.setFeeRate_S(localApply_T_CommodityFee.getFeeRate_S());
      localCommodity.setForceCloseFeeAlgr(localApply_T_CommodityFee.getFeeAlgr());
      localCommodity.setForceCloseFeeRate_B(localApply_T_CommodityFee.getForceCloseFeeRate_B());
      localCommodity.setForceCloseFeeRate_S(localApply_T_CommodityFee.getForceCloseFeeRate_S());
      localCommodity.setTodayCloseFeeRate_B(localApply_T_CommodityFee.getTodayCloseFeeRate_B());
      localCommodity.setTodayCloseFeeRate_S(localApply_T_CommodityFee.getTodayCloseFeeRate_S());
      localCommodity.setHistoryCloseFeeRate_B(localApply_T_CommodityFee.getHistoryCloseFeeRate_B());
      localCommodity.setHistoryCloseFeeRate_S(localApply_T_CommodityFee.getHistoryCloseFeeRate_S());
      this.tps.updateTariff(localCommodity, localPage.getResult(), localUser.getName());
    }
    if (i == -1)
      addReturnValue(1, 151103L, new Object[] { "此记录已审核完毕！" });
    else if (i == 0)
      addReturnValue(1, 151103L, new Object[] { "操作成功！" });
    else if (i == 1)
      addReturnValue(1, 151103L, new Object[] { "商品代码不存在！" });
    return "success";
  }

  public String listCommodityMarginChe()
    throws Exception
  {
    if (this.logger.isDebugEnabled())
      this.logger.debug("enter listCommodityMarginChe method...");
    PageRequest localPageRequest = super.getPageRequest(this.request);
    String str1 = this.request.getParameter("commodityID");
    String str2 = this.request.getParameter("status");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str3 = localUser.getUserId();
    Apply_T_CommodityMargin localApply_T_CommodityMargin = new Apply_T_CommodityMargin();
    localApply_T_CommodityMargin.setCommodityID(str1);
    if ((str2 != null) && (!"".equals(str2)))
      localApply_T_CommodityMargin.setStatus(Integer.parseInt(str2));
    localApply_T_CommodityMargin.setProposer(str3);
    localApply_T_CommodityMargin.setApplyType(4);
    this.request.setAttribute("FEEALGR", CommonDictionary.FEEALGR);
    this.request.setAttribute("MARGINPRICETYPE", CommonDictionary.MARGINPRICETYPE);
    this.request.setAttribute("SETTLEFEEALGR", CommonDictionary.SETTLEFEEALGR);
    this.request.setAttribute("VIRTUALFUNDSTATUS", CommonDictionary.VIRTUALFUNDSTATUS);
    String str4 = "(" + localApply_T_CommodityMargin.toQury() + " where 1=1 " + localApply_T_CommodityMargin.toFilter() + localApply_T_CommodityMargin.toOrder() + ")";
    return listByLimit(localPageRequest, str4);
  }

  public String viewByIdCommodityMargin()
    throws Exception
  {
    ApplyModel localApplyModel = (ApplyModel)getService().get(this.entity);
    this.entity = localApplyModel;
    this.apply = new CommodityMarginApply();
    setApply(localApplyModel.getContent());
    String str1 = "";
    String str2 = "";
    String str3 = "";
    String str4 = "";
    String str5 = "";
    String str6 = "";
    String str7 = "";
    String str8 = "";
    String str9 = "";
    String str10 = "";
    String str11 = "";
    CommodityMarginApply localCommodityMarginApply = (CommodityMarginApply)this.apply;
    if (localApplyModel != null)
    {
      if (localCommodityMarginApply.getMarginAlgr().shortValue() == 1)
        str1 = "按百分比";
      else if (localCommodityMarginApply.getMarginAlgr().shortValue() == 2)
        str1 = "按绝对值";
      if (localCommodityMarginApply.getMarginPriceType().shortValue() == 0)
        str2 = "订立价";
      else if (localCommodityMarginApply.getMarginPriceType().shortValue() == 1)
        str2 = "昨结算价";
      else if (localCommodityMarginApply.getMarginPriceType().shortValue() == 2)
        str2 = "盘中按订立价";
      if (localCommodityMarginApply.getSettleMarginAlgr_B().shortValue() == 1)
        str3 = "按百分比";
      else if (localCommodityMarginApply.getSettleMarginAlgr_B().shortValue() == 2)
        str3 = "按绝对值";
      if (localCommodityMarginApply.getSettleMarginAlgr_S().shortValue() == 1)
        str4 = "按百分比";
      else if (localCommodityMarginApply.getSettleMarginAlgr_S().shortValue() == 2)
        str4 = "按绝对值";
      if (localCommodityMarginApply.getPayoutAlgr().shortValue() == 1)
        str7 = "按百分比";
      else if (localCommodityMarginApply.getPayoutAlgr().shortValue() == 2)
        str7 = "按绝对值";
      if ("1".equals(localApplyModel.getStatus() + ""))
        str8 = "待审核";
      else if ("2".equals(localApplyModel.getStatus() + ""))
        str8 = "审核通过";
      else if ("3".equals(localApplyModel.getStatus() + ""))
        str8 = "审核不通过";
      str9 = localApplyModel.getProposer();
      str10 = localApplyModel.getNote();
      if (str10 == null)
        str10 = "";
      str11 = localApplyModel.getApprover();
    }
    this.request.setAttribute("marginAlgr", str1);
    this.request.setAttribute("marginPriceType", str2);
    this.request.setAttribute("settleMarginAlgr_B", str3);
    this.request.setAttribute("settleMarginAlgr_S", str4);
    this.request.setAttribute("settleMarginRate_B", str5);
    this.request.setAttribute("settleMarginRate_S", str6);
    this.request.setAttribute("payoutAlgr", str7);
    this.request.setAttribute("status", str8);
    this.request.setAttribute("commodityMarginApply", this.apply);
    return "success";
  }

  public String updateCommodityMargin()
    throws Exception
  {
    Apply_T_CommodityMargin localApply_T_CommodityMargin = new Apply_T_CommodityMargin();
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str = localUser.getUserId();
    ParamUtil.bindData(this.request, localApply_T_CommodityMargin);
    localApply_T_CommodityMargin.setApprover(str);
    int i = this.acService.updateCommodityMarginCheck(localApply_T_CommodityMargin);
    if (i == -1)
      addReturnValue(1, 151103L, new Object[] { "此记录已审核完毕！" });
    else if (i == 0)
      addReturnValue(1, 151103L, new Object[] { "操作成功！" });
    else if (i == 1)
      addReturnValue(1, 151103L, new Object[] { "商品代码不存在！" });
    return "success";
  }
}