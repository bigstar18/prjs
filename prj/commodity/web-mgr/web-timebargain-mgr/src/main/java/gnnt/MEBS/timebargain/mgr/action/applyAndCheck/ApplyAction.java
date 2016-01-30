package gnnt.MEBS.timebargain.mgr.action.applyAndCheck;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.timebargain.mgr.model.apply.Apply_T_CommodityFee;
import gnnt.MEBS.timebargain.mgr.model.apply.Apply_T_CommodityMargin;
import gnnt.MEBS.timebargain.mgr.model.apply.Apply_T_PledgeMoney;
import gnnt.MEBS.timebargain.mgr.service.ApplyAndCheckService;
import gnnt.MEBS.timebargain.mgr.statictools.CommonDictionary;
import gnnt.MEBS.timebargain.mgr.statictools.ParamUtil;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("request")
public class ApplyAction extends EcsideAction
{
  private final transient Log logger = LogFactory.getLog(ApplyAction.class);
  private InputStream inputStream;

  @Autowired
  @Qualifier("com_applyAndCheckService")
  private ApplyAndCheckService acService;

  public InputStream getResult()
  {
    return this.inputStream;
  }

  public String listPledgeApp()
    throws Exception
  {
    if (this.logger.isDebugEnabled())
      this.logger.debug("enter listPledgeApp method...");
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
    String str4 = new StringBuilder().append("(").append(localApply_T_PledgeMoney.toQury()).append(" where 1=1 ").append(localApply_T_PledgeMoney.toFilter()).append(localApply_T_PledgeMoney.toOrder()).append(")").toString();
    return null;
  }

  public String addForwardApply()
  {
    return "success";
  }

  public String addPledgeApp()
  {
    if (this.logger.isDebugEnabled())
      this.logger.debug("enter addPledgeApp method...");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str = localUser.getUserId();
    Apply_T_PledgeMoney localApply_T_PledgeMoney = new Apply_T_PledgeMoney();
    ParamUtil.bindData(this.request, localApply_T_PledgeMoney);
    localApply_T_PledgeMoney.setApplyType(2);
    localApply_T_PledgeMoney.setStatus(1);
    localApply_T_PledgeMoney.setProposer(str);
    this.acService.addPledgeApp(localApply_T_PledgeMoney);
    addReturnValue(1, 119901L);
    return "success";
  }

  public String listCommodityFeeApp()
    throws Exception
  {
    if (this.logger.isDebugEnabled())
      this.logger.debug("Entering 'listCommodityFeeApp' method");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str1 = localUser.getUserId();
    String str2 = this.request.getParameter("commodityID");
    if ((str2 != null) && (!"".equals(str2)))
    {
      this.request.setAttribute("gnnt_primary.content[allLike]", new StringBuilder().append(">").append(str2).toString());
      this.request.setAttribute("commodityID", str2);
    }
    this.request.setAttribute("gnnt_primary.applyType[=][int]", "3");
    this.request.setAttribute("gnnt_primary.proposer[=]", str1);
    this.request.setAttribute("FEEALGR", CommonDictionary.FEEALGR);
    this.request.setAttribute("VIRTUALFUNDSTATUS", CommonDictionary.VIRTUALFUNDSTATUS);
    return listByLimit();
  }

  public String addCommodityFeeApp()
  {
    if (this.logger.isDebugEnabled())
      this.logger.debug("enter addCommodityFeeApp method...");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str = localUser.getUserId();
    Apply_T_CommodityFee localApply_T_CommodityFee = new Apply_T_CommodityFee();
    ParamUtil.bindData(this.request, localApply_T_CommodityFee);
    localApply_T_CommodityFee.setApplyType(3);
    localApply_T_CommodityFee.setStatus(1);
    localApply_T_CommodityFee.setProposer(str);
    this.acService.addPledgeApp(localApply_T_CommodityFee);
    addReturnValue(1, 119901L);
    return "success";
  }

  public String listCommodityMarginApp()
    throws Exception
  {
    if (this.logger.isDebugEnabled())
      this.logger.debug("enter listCommodityMarginApp method...");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str1 = localUser.getUserId();
    String str2 = this.request.getParameter("commodityID");
    if ((str2 != null) && (!"".equals(str2)))
    {
      this.request.setAttribute("gnnt_primary.content[allLike]", new StringBuilder().append(">").append(str2).toString());
      this.request.setAttribute("commodityID", str2);
    }
    this.request.setAttribute("gnnt_primary.applyType[=][int]", "4");
    this.request.setAttribute("gnnt_primary.proposer[=]", str1);
    this.request.setAttribute("FEEALGR", CommonDictionary.FEEALGR);
    this.request.setAttribute("MARGINPRICETYPE", CommonDictionary.MARGINPRICETYPE);
    this.request.setAttribute("SETTLEFEEALGR", CommonDictionary.SETTLEFEEALGR);
    this.request.setAttribute("VIRTUALFUNDSTATUS", CommonDictionary.VIRTUALFUNDSTATUS);
    return listByLimit();
  }

  public String addCommodityMarginApp()
  {
    if (this.logger.isDebugEnabled())
      this.logger.debug("enter addCommodityMarginApp method...");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str1 = localUser.getUserId();
    Apply_T_CommodityMargin localApply_T_CommodityMargin = new Apply_T_CommodityMargin();
    ParamUtil.bindData(this.request, localApply_T_CommodityMargin);
    String str2 = this.request.getParameter("type");
    String str3 = this.request.getParameter("type1");
    String str4 = this.request.getParameter("type2");
    String str5 = this.request.getParameter("type3");
    String str6 = this.request.getParameter("type4");
    if (("1".equals(str2)) && (localApply_T_CommodityMargin.getMarginItem1() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItem1())) && (localApply_T_CommodityMargin.getMarginItemAssure1() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItemAssure1())))
    {
      localApply_T_CommodityMargin.setMarginItem1_S(localApply_T_CommodityMargin.getMarginItem1());
      localApply_T_CommodityMargin.setMarginItemAssure1_S(localApply_T_CommodityMargin.getMarginItemAssure1());
    }
    if (("1".equals(str3)) && (localApply_T_CommodityMargin.getMarginItem2() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItem2())) && (localApply_T_CommodityMargin.getMarginItemAssure2() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItemAssure2())))
    {
      localApply_T_CommodityMargin.setMarginItem2_S(localApply_T_CommodityMargin.getMarginItem2());
      localApply_T_CommodityMargin.setMarginItemAssure2_S(localApply_T_CommodityMargin.getMarginItemAssure2());
    }
    if (("1".equals(str4)) && (localApply_T_CommodityMargin.getMarginItem3() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItem3())) && (localApply_T_CommodityMargin.getMarginItemAssure3() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItemAssure3())))
    {
      localApply_T_CommodityMargin.setMarginItem3_S(localApply_T_CommodityMargin.getMarginItem3());
      localApply_T_CommodityMargin.setMarginItemAssure3_S(localApply_T_CommodityMargin.getMarginItemAssure3());
    }
    if (("1".equals(str5)) && (localApply_T_CommodityMargin.getMarginItem4() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItem4())) && (localApply_T_CommodityMargin.getMarginItemAssure4() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItemAssure4())))
    {
      localApply_T_CommodityMargin.setMarginItem4_S(localApply_T_CommodityMargin.getMarginItem4());
      localApply_T_CommodityMargin.setMarginItemAssure4_S(localApply_T_CommodityMargin.getMarginItemAssure4());
    }
    if (("1".equals(str6)) && (localApply_T_CommodityMargin.getMarginItem5() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItem5())) && (localApply_T_CommodityMargin.getMarginItemAssure5() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItemAssure5())))
    {
      localApply_T_CommodityMargin.setMarginItem5_S(localApply_T_CommodityMargin.getMarginItem5());
      localApply_T_CommodityMargin.setMarginItemAssure5_S(localApply_T_CommodityMargin.getMarginItemAssure5());
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String str7 = this.request.getParameter("settleDate7");
    String str8 = this.request.getParameter("settleDate1");
    String str9 = this.request.getParameter("settleDate2");
    String str10 = this.request.getParameter("settleDate3");
    String str11 = this.request.getParameter("settleDate4");
    String str12 = this.request.getParameter("settleDate5");
    if ((str7 != null) && (!"".equals(str7)))
      localApply_T_CommodityMargin.setSettleDate(str7);
    if ((str8 != null) && (!"".equals(str8)))
    {
      localApply_T_CommodityMargin.setSettleDate1(str8);
      localApply_T_CommodityMargin.setMarketDate(str8);
    }
    if ((str9 != null) && (!"".equals(str9)))
      localApply_T_CommodityMargin.setSettleDate2(str9);
    if ((str10 != null) && (!"".equals(str10)))
      localApply_T_CommodityMargin.setSettleDate3(str10);
    if ((str11 != null) && (!"".equals(str11)))
      localApply_T_CommodityMargin.setSettleDate4(str11);
    if ((str12 != null) && (!"".equals(str12)))
      localApply_T_CommodityMargin.setSettleDate5(str12);
    localApply_T_CommodityMargin.setApplyType(4);
    localApply_T_CommodityMargin.setStatus(1);
    localApply_T_CommodityMargin.setProposer(str1);
    this.acService.addPledgeApp(localApply_T_CommodityMargin);
    addReturnValue(1, 119901L);
    return "success";
  }

  public String ajaxRequest()
  {
    try
    {
      String str1 = this.request.getParameter("billID");
      String str2 = this.request.getParameter("typeOperate");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
      localStringBuilder.append("<context>");
      if ((str1 != null) && (!str1.equals("")))
      {
        String str3 = "";
        if ("1".equals(str2))
          str3 = new StringBuilder().append("select s.*,t.breedName as commodityName from s_regstock s inner join t_a_breed t on s.breedid=t.breedid where 1=1 and regstockId= '").append(str1).append("'").toString();
        else
          str3 = new StringBuilder().append("select s.*,t.breedName as commodityName,e.billfund as billFund,e.quantity as quantity from s_regstock s inner join t_a_breed t on s.breedid=t.breedid ,t_e_pledge e where s.regstockid=e.billid and '").append(str1).append("' = regstockId ").toString();
        ArrayList localArrayList = new ArrayList();
        if (localArrayList.size() == 0)
        {
          localStringBuilder.append("<billResult>0</billResult>");
        }
        else
        {
          localStringBuilder.append("<billResult>1</billResult>");
          localStringBuilder.append(new StringBuilder().append("<firmId>").append(((Map)localArrayList.get(0)).get("firmID").toString()).append("</firmId>").toString());
          localStringBuilder.append(new StringBuilder().append("<commodityName>").append(((Map)localArrayList.get(0)).get("commodityName").toString()).append("</commodityName>").toString());
          localStringBuilder.append(new StringBuilder().append("<weight>").append(((Map)localArrayList.get(0)).get("weight").toString()).append("</weight>").toString());
          localStringBuilder.append(new StringBuilder().append("<frozenWeight>").append(((Map)localArrayList.get(0)).get("frozenWeight").toString()).append("</frozenWeight>").toString());
          if ("2".equals(str2))
          {
            localStringBuilder.append(new StringBuilder().append("<quantity>").append(((Map)localArrayList.get(0)).get("quantity").toString()).append("</quantity>").toString());
            localStringBuilder.append(new StringBuilder().append("<billFund>").append(((Map)localArrayList.get(0)).get("billFund").toString()).append("</billFund>").toString());
          }
        }
      }
      else
      {
        localStringBuilder.append("<billResult>0</billResult>");
      }
      localStringBuilder.append("</context>");
      this.inputStream = new ByteArrayInputStream(localStringBuilder.toString().getBytes("GBK"));
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "success";
  }
}