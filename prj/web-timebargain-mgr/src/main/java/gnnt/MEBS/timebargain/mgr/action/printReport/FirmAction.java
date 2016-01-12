package gnnt.MEBS.timebargain.mgr.action.printReport;

import com.opensymphony.xwork2.ActionContext;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.printReport.FirmModel;
import gnnt.MEBS.timebargain.mgr.service.BrokerRewardService;
import gnnt.MEBS.timebargain.mgr.service.CommodityIdService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("firmAction")
@Scope("request")
public class FirmAction extends EcsideAction
{
  private static final long serialVersionUID = 5124568190167465621L;
  private List listASC = new ArrayList();
  private List listDesc = new ArrayList();
  private List listCommodityIdDesc = new ArrayList();
  private List listCommodityIdAsc = new ArrayList();
  private String data;

  @Autowired
  @Qualifier("commodityIdService")
  private CommodityIdService commodityIdService;

  @Autowired
  @Qualifier("brokerRewardService")
  private BrokerRewardService brokerRewardService;

  public String getData()
  {
    return this.data;
  }

  public List getListASC()
  {
    return this.listASC;
  }

  public void setListASC(List paramList)
  {
    this.listASC = paramList;
  }

  public List getListDesc()
  {
    return this.listDesc;
  }

  public void setListDesc(List paramList)
  {
    this.listDesc = paramList;
  }

  public List getListCommodityIdDesc()
  {
    return this.listCommodityIdDesc;
  }

  public void setListCommodityIdDesc(List paramList)
  {
    this.listCommodityIdDesc = paramList;
  }

  public List getListCommodityIdAsc()
  {
    return this.listCommodityIdAsc;
  }

  public void setListCommodityIdAsc(List paramList)
  {
    this.listCommodityIdAsc = paramList;
  }

  public String getFirmListOrder()
    throws Exception
  {
    List localList1 = this.commodityIdService.commodityIdList("desc");
    List localList2 = this.commodityIdService.commodityIdList("asc");
    this.request.setAttribute("listDESC", localList1);
    this.request.setAttribute("listAsc", localList2);
    List localList3 = this.commodityIdService.firmIdList("desc");
    List localList4 = this.commodityIdService.firmIdList("asc");
    this.request.setAttribute("listDesc", localList3);
    this.request.setAttribute("listASC", localList4);
    List localList5 = this.brokerRewardService.getBrokerStartList();
    this.request.setAttribute("listBrokerASC", localList5);
    return "success";
  }

  public String getBrokerAndFirmCategoryList()
    throws Exception
  {
    List localList1 = this.brokerRewardService.getBrokerEndList();
    List localList2 = this.brokerRewardService.getBrokerStartList();
    String str = "select id,name from M_FIRMCATEGORY t order by id";
    List localList3 = getService().getListBySql(str);
    this.request.setAttribute("firmcategory", localList3);
    this.request.setAttribute("brokerDescList", localList1);
    this.request.setAttribute("brokerAscList", localList2);
    return "success";
  }

  public Boolean checkFirmId(String paramString)
  {
    FirmModel localFirmModel = new FirmModel();
    localFirmModel.setFirmId(paramString);
    localFirmModel = (FirmModel)getService().get(localFirmModel);
    if (localFirmModel == null)
      return Boolean.valueOf(false);
    return Boolean.valueOf(true);
  }

  public String getTradeUserNotEnoughMoneyQuery()
  {
    return "success";
  }

  public CommodityIdService getCommodityIdService()
  {
    return this.commodityIdService;
  }

  public void setCommodityIdService(CommodityIdService paramCommodityIdService)
  {
    this.commodityIdService = paramCommodityIdService;
  }

  public BrokerRewardService getBrokerRewardService()
  {
    return this.brokerRewardService;
  }

  public void setBrokerRewardService(BrokerRewardService paramBrokerRewardService)
  {
    this.brokerRewardService = paramBrokerRewardService;
  }

  public String checkBreed()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("breedId");
    String str2 = "select t.commodityID from t_commodity t";
    if (str1 != "")
      str2 = str2 + " where t.breedid='" + str1 + "'";
    str2 = str2 + " order by t.commodityID";
    List localList = getService().getListBySql(str2);
    this.data = "";
    for (int i = 0; i < localList.size(); i++)
      this.data = (this.data + localList.get(i) + "|");
    return "success";
  }
}