package gnnt.MEBS.broker.mgr.action;

import gnnt.MEBS.broker.mgr.model.brokerDataquery.BrokerReward;
import gnnt.MEBS.broker.mgr.model.brokerManagement.BrTradeModule;
import gnnt.MEBS.broker.mgr.service.BrokerDataqueryService;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("brokerDataqueryAction")
@Scope("request")
public class BrokerDataqueryAction extends EcsideAction
{
  private static final long serialVersionUID = -5947792580801560638L;

  @Autowired
  @Qualifier("com_brokerDataqueryService")
  public BrokerDataqueryService brokerDataqueryService;

  @Resource(name="moduleIdMap")
  Map<String, String> moduleIdMap;

  public Map<String, String> getModuleIdMap()
  {
    return this.moduleIdMap;
  }

  public void setModuleIdMap(Map<String, String> moduleIdMap)
  {
    this.moduleIdMap = moduleIdMap;
  }

  public String listBrokerReward()
    throws Exception
  {
    this.logger.debug("enter listBrokerReward");
    PageRequest pageRequest = getPageRequest(this.request);

    QueryConditions qc = (QueryConditions)pageRequest.getFilters();
    qc.addCondition("amount", ">", new Double(0.0D));

    Page page = getService().getPage(pageRequest, this.entity);
    this.request.setAttribute("pageInfo", page);

    this.request.setAttribute("oldParams", getParametersStartingWith(this.request, 
      "gnnt_"));
    return "success";
  }

  public String brokerRewardPayMoney()
  {
    int result = 1;

    String changeMoney = this.request.getParameter("money");
    BrokerReward brokerReward = (BrokerReward)this.entity;

    String brokerId = brokerReward.getBrokerId();

    List list = getService().getListBySql("select firmId from  br_broker where brokerId ='" + brokerId + "'");
    System.out.println("---------" + ((Map)list.get(0)).get("FIRMID").toString());

    String firmID = ((Map)list.get(0)).get("FIRMID").toString();
    double money = Double.parseDouble(changeMoney);

    result = this.brokerDataqueryService.handleReward(brokerReward, money, firmID);

    if (result == 1)
      addReturnValue(1, 119902L);
    else if (result == -1)
      addReturnValue(-1, 121901L, new String[] { "支付佣金不能大于待付佣金 ，请重新设置" });
    else {
      addReturnValue(-1, 121901L, new String[] { "生成资金流水失败,请联系管理员" });
    }

    return "success";
  }

  public String listBrokerTradeFee()
    throws Exception
  {
    this.logger.debug("enter listBrokerTradeFee");
    getBrokerList();

    this.request.setAttribute("oldParams", getParametersStartingWith(this.request, 
      "gnnt_"));
    List brokerTradeFee = this.brokerDataqueryService.brokerTradeFee(this.request);
    this.logger.debug(brokerTradeFee);
    this.request.setAttribute("resultList", brokerTradeFee);
    return "success";
  }

  public String listBreedTradeFee()
    throws Exception
  {
    getBreedList();

    this.request.setAttribute("oldParams", getParametersStartingWith(this.request, 
      "gnnt_"));
    this.logger.debug("enter listCommodityTradeFee");
    List BreedTradeFee = this.brokerDataqueryService.breedTradeFee(this.request);

    this.request.setAttribute("resultList", BreedTradeFee);
    return "success";
  }

  public String listFirmTradeFee()
    throws Exception
  {
    getFirmList();

    this.request.setAttribute("oldParams", getParametersStartingWith(this.request, 
      "gnnt_"));
    this.logger.debug("enter listFirmTradeFee");
    List firmTradeFee = this.brokerDataqueryService.firmTradeFee(this.request);
    this.request.setAttribute("resultList", firmTradeFee);
    return "success";
  }

  public void getBrokerList()
    throws Exception
  {
    this.logger.debug("enter getlist from Broker");
    List brokerAscList = getService().getListBySql("select br.brokerid from br_broker br order by br.brokerid");
    List brokerDescList = getService().getListBySql("select br.brokerid from br_broker br order by br.brokerid desc");
    this.request.setAttribute("brokerAscList", brokerAscList);
    this.request.setAttribute("brokerDescList", brokerDescList);
  }

  public void getFirmList()
    throws Exception
  {
    this.logger.debug("enter getlist from firm");
    List firmAscList = getService().getListBySql("select m.firmid from m_firm m  order by m.firmid");
    List firmDescList = getService().getListBySql("select m.firmid from m_firm m  order by m.firmid desc");
    this.request.setAttribute("firmAscList", firmAscList);
    this.request.setAttribute("firmDescList", firmDescList);
  }

  public void getBreedList()
    throws Exception
  {
    this.logger.debug("enter getlist from breed");
    List breedAscList = getService().getListBySql("select ta.breedid from t_a_breed ta order by ta.breedid");
    List breedDescList = getService().getListBySql("select ta.breedid from t_a_breed ta order by ta.breedid desc");
    this.request.setAttribute("breedAscList", breedAscList);
    this.request.setAttribute("breedDescList", breedDescList);
  }

  public BrokerDataqueryService getBrokerDataqueryService() {
    return this.brokerDataqueryService;
  }

  public List getBrTradeModule()
  {
    return getService().getListBySql("select * from BR_TradeModule", new BrTradeModule());
  }
}