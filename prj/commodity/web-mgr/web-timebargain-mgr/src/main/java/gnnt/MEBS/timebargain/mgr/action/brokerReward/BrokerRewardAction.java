package gnnt.MEBS.timebargain.mgr.action.brokerReward;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.service.BrokerRewardService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("brokerRewardAction")
@Scope("request")
public class BrokerRewardAction extends EcsideAction
{
  private static final long serialVersionUID = -125029325028903028L;

  @Autowired
  @Qualifier("brokerRewardService")
  private BrokerRewardService brokerRewardService;
  private Map<String, Object> map;

  public String listFirmRewardSum()
    throws Exception
  {
    this.logger.debug("enter listFirmRewardSum");
    String str1 = this.request.getParameter("isPartition");
    String str2 = this.request.getParameter("startBreed");
    String str3 = this.request.getParameter("endBreed");
    String str4 = this.request.getParameter("startFirm");
    String str5 = this.request.getParameter("endFirm");
    String str6 = this.request.getParameter("beginDate");
    String str7 = this.request.getParameter("endDate");
    PageRequest localPageRequest = super.getPageRequest(this.request);
    if (str1 == null)
      str1 = "N";
    List localList1 = this.brokerRewardService.getBreedStartList();
    List localList2 = this.brokerRewardService.getBreedEndList();
    List localList3 = this.brokerRewardService.getFirmRewardSum(str1, str2, str3, str4, str5, str6, str7);
    Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList3.size(), localList3);
    this.request.setAttribute("pageInfo", localPage);
    this.request.setAttribute("breedStartList", localList1);
    this.request.setAttribute("breedEndList", localList2);
    this.request.setAttribute("isPartition", str1);
    this.request.setAttribute("startBreed", str2);
    this.request.setAttribute("endBreed", str3);
    this.request.setAttribute("startFirm", str4);
    this.request.setAttribute("endFirm", str5);
    this.request.setAttribute("beginDate", str6);
    this.request.setAttribute("endDate", str7);
    return "success";
  }

  public String brokerRewardSum()
    throws Exception
  {
    this.logger.debug("enter brokerRewardSum");
    String str1 = this.request.getParameter("startBroker");
    String str2 = this.request.getParameter("endBroker");
    String str3 = this.request.getParameter("beginDate");
    String str4 = this.request.getParameter("endDate");
    PageRequest localPageRequest = super.getPageRequest(this.request);
    List localList1 = this.brokerRewardService.getBrokerStartList();
    List localList2 = this.brokerRewardService.getBrokerEndList();
    List localList3 = this.brokerRewardService.getBrokerRewardSum(str1, str2, str3, str4);
    Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList3.size(), localList3);
    this.request.setAttribute("pageInfo", localPage);
    this.request.setAttribute("brokerStartList", localList1);
    this.request.setAttribute("brokerEndList", localList2);
    this.request.setAttribute("startBroker", str1);
    this.request.setAttribute("endBroker", str2);
    this.request.setAttribute("beginDate", str3);
    this.request.setAttribute("endDate", str4);
    return "success";
  }

  public String breedRewardSum()
    throws Exception
  {
    this.logger.debug("enter breedRewardSum");
    String str1 = this.request.getParameter("startBreed");
    String str2 = this.request.getParameter("endBreed");
    String str3 = this.request.getParameter("beginDate");
    String str4 = this.request.getParameter("endDate");
    PageRequest localPageRequest = super.getPageRequest(this.request);
    List localList1 = this.brokerRewardService.getBreedStartList();
    List localList2 = this.brokerRewardService.getBreedEndList();
    List localList3 = this.brokerRewardService.getBreedRewardSum(str1, str2, str3, str4);
    Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList3.size(), localList3);
    this.request.setAttribute("pageInfo", localPage);
    this.request.setAttribute("breedStartList", localList1);
    this.request.setAttribute("breedEndList", localList2);
    this.request.setAttribute("startBreed", str1);
    this.request.setAttribute("endBreed", str2);
    this.request.setAttribute("beginDate", str3);
    this.request.setAttribute("endDate", str4);
    return "success";
  }

  public String listBrokerTradeFee()
    throws Exception
  {
    this.logger.debug("enter listBrokerTradeFee");
    getBrokerList();
    this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));
    List localList = this.brokerRewardService.brokerTradeFee(this.request);
    this.logger.debug(localList);
    this.request.setAttribute("resultList", localList);
    return "success";
  }

  public String listBreedTradeFee()
    throws Exception
  {
    getBreedList();
    this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));
    this.logger.debug("enter listCommodityTradeFee");
    List localList = this.brokerRewardService.breedTradeFee(this.request);
    this.request.setAttribute("resultList", localList);
    return "success";
  }

  public String listFirmTradeFee()
    throws Exception
  {
    getFirmList();
    this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));
    this.logger.debug("enter listFirmTradeFee");
    List localList = this.brokerRewardService.firmTradeFee(this.request);
    this.request.setAttribute("resultList", localList);
    return "success";
  }

  public void getBrokerList()
    throws Exception
  {
    this.logger.debug("enter getlist from Broker");
    List localList1 = getService().getListBySql("select br.brokerid from br_broker br order by br.brokerid");
    List localList2 = getService().getListBySql("select br.brokerid from br_broker br order by br.brokerid desc");
    this.request.setAttribute("brokerAscList", localList1);
    this.request.setAttribute("brokerDescList", localList2);
  }

  public void getFirmList()
    throws Exception
  {
    this.logger.debug("enter getlist from firm");
    List localList1 = getService().getListBySql("select m.firmid from m_firm m  order by m.firmid");
    List localList2 = getService().getListBySql("select m.firmid from m_firm m  order by m.firmid desc");
    this.request.setAttribute("firmAscList", localList1);
    this.request.setAttribute("firmDescList", localList2);
  }

  public void getBreedList()
    throws Exception
  {
    this.logger.debug("enter getlist from breed");
    List localList1 = getService().getListBySql("select ta.breedid,breedname from t_a_breed ta order by ta.breedid");
    List localList2 = getService().getListBySql("select ta.breedid,breedname from t_a_breed ta order by ta.breedid desc");
    this.request.setAttribute("breedAscList", localList1);
    this.request.setAttribute("breedDescList", localList2);
  }

  public BrokerRewardService getBrokerRewardService()
  {
    return this.brokerRewardService;
  }

  public String brokerUserFeeList()
    throws Exception
  {
    this.logger.debug("enter breedRewardSum");
    String str1 = this.request.getParameter("startFirm");
    String str2 = this.request.getParameter("endFirm");
    String str3 = this.request.getParameter("beginDate");
    String str4 = this.request.getParameter("endDate");
    String str5 = this.request.getParameter("summarizingWay");
    String str6 = this.request.getParameter("brokerid");
    String str7 = this.request.getParameter("breedId");
    String str8 = this.request.getParameter("summarizingWay1");
    String str9 = this.request.getParameter("summarizingWay2");
    if ((str8 == null) || (str9 == null) || ("".equals(str8)) || ("".equals(str9)))
    {
      str5 = "summarizingFirmAndSummarizing";
      str8 = "summarizingFirm";
      str9 = "Summarizing";
    }
    else
    {
      str5 = str8 + "And" + str9;
    }
    List localList1 = this.brokerRewardService.getBreedStartList();
    PageRequest localPageRequest = super.getPageRequest(this.request);
    List localList2 = this.brokerRewardService.getBrokerUserFeeList(str1, str2, str3, str4, str5, str6, str7);
    Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList2.size(), localList2);
    this.request.setAttribute("pageInfo", localPage);
    this.request.setAttribute("breedStartList", localList1);
    this.request.setAttribute("startFirm", str1);
    this.request.setAttribute("endFirm", str2);
    this.request.setAttribute("beginDate", str3);
    this.request.setAttribute("endDate", str4);
    this.request.setAttribute("summarizingWay", str5);
    this.request.setAttribute("summarizingWay1", str8);
    this.request.setAttribute("summarizingWay2", str9);
    this.request.setAttribute("brokerid", str6);
    this.request.setAttribute("breedId", str7);
    return "success";
  }

  public String hisDealDetailList()
    throws Exception
  {
    this.logger.debug("enter breedRewardSum");
    String str1 = this.request.getParameter("brokerId");
    String str2 = this.request.getParameter("breedId");
    String str3 = this.request.getParameter("firmId");
    String str4 = this.request.getParameter("bsFlag");
    String str5 = this.request.getParameter("beginDate");
    String str6 = this.request.getParameter("endDate");
    PageRequest localPageRequest = super.getPageRequest(this.request);
    List localList1 = this.brokerRewardService.getBreedStartList();
    List localList2 = this.brokerRewardService.getHisDealDetailList(str1, str2, str3, str4, str5, str6);
    Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList2.size(), localList2);
    this.request.setAttribute("pageInfo", localPage);
    this.request.setAttribute("breedStartList", localList1);
    this.request.setAttribute("brokerId", str1);
    this.request.setAttribute("breedId", str2);
    this.request.setAttribute("firmId", str3);
    this.request.setAttribute("bsFlag", str4);
    this.request.setAttribute("beginDate", str5);
    this.request.setAttribute("endDate", str6);
    return "success";
  }
}