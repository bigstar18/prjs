package gnnt.MEBS.broker.mgr.action.configparam;

import gnnt.MEBS.broker.mgr.model.brokerManagement.BrTradeModule;
import gnnt.MEBS.broker.mgr.model.configparam.BrokerRewardProps;
import gnnt.MEBS.broker.mgr.model.configparam.RewardParam;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("request")
public class ConfigParamAction extends EcsideAction
{
  public static final short timebargain_moduleId = 15;
  public boolean existValidate = false;

  @Resource(name="config_rewardTypeMap")
  private Map config_rewardTypeMap;

  public boolean getExistValidate() { return this.existValidate; }


  public Map getConfig_rewardTypeMap()
  {
    return this.config_rewardTypeMap;
  }

  public void setConfig_rewardTypeMap(Map configRewardTypeMap) {
    this.config_rewardTypeMap = configRewardTypeMap;
  }

  public String getRewardParam()
    throws Exception
  {
    this.logger.debug("enter getRewardParam --------------");
    PageRequest pageRequest = getPageRequest(this.request);
    Page page = getService().getPage(pageRequest, this.entity);
    List list = page.getResult();

    if ((list != null) && (list.size() > 0)) {
      this.entity = ((StandardModel)list.get(0));
      return "success";
    }
    addReturnValue(-1, 139904L);
    return "error";
  }

  public String updateRewardParam()
    throws Exception
  {
    this.logger.debug("enter update --------------");

    RewardParam rp = (RewardParam)this.entity;
    String updateSql = "update BR_Rewardparameterprops set autoPay = '" + rp.getAutoPay() + "', payPeriod = " + rp.getPayPeriod() + ", payPeriodDate = " + rp.getPayPeriodDate();
    getService().executeUpdateBySql(updateSql);
    addReturnValue(1, 119902L);
    return "success";
  }

  public String deleteSpecialParam()
    throws Exception
  {
    this.logger.debug("enter deleteSpecialParam --------------");
    String[] ids = this.request.getParameterValues("ids");
    if (ids != null) {
      Collection deleteList = new ArrayList();
      for (int i = 0; i < ids.length; i++) {
        BrokerRewardProps brp = new BrokerRewardProps();
        String[] arr = ids[i].split(",");
        brp.setBrokerId(arr[0]);
        brp.setModuleId(Integer.valueOf(arr[1]));
        brp.setCommodityId(arr[2]);
        brp.setRewardType(Short.valueOf(arr[3]));
        deleteList.add(brp);
      }

      getService().deleteBYBulk(deleteList);
      addReturnValue(1, 119903L);
    }

    return "success";
  }

  public String forwardAdd() {
    this.logger.debug("enter skip");
    String flag = this.request.getParameter("flag");
    this.logger.debug(flag);

    String sql = "select brokerId from br_broker";
    List brokerList = getService().getListBySql(sql);
    this.request.setAttribute("brokerList", brokerList);
    if ("common".equals(flag)) {
      return "common";
    }
    return "batch";
  }

  public String add()
  {
    this.logger.debug("enter add");
    String flag = this.request.getParameter("flag");
    this.logger.debug(flag);
    BigDecimal rate = new BigDecimal("100");
    if ("common".equals(flag)) {
      getService().add(this.entity);
      addReturnValue(1, 119901L);
    } else {
      String brokerids = this.request.getParameter("brokerids");
      String commodityId = this.request.getParameter("commodityId");
      Integer moduleId = Integer.valueOf(Integer.parseInt(this.request.getParameter("moduleId")));
      Short rewardType = Short.valueOf((short)0);
      BigDecimal rewardRate = new BigDecimal(this.request.getParameter("rewardRate"));
      BigDecimal firstPayRate = new BigDecimal(this.request.getParameter("firstPayRate"));
      BigDecimal secondPayRate = new BigDecimal(this.request.getParameter("secondPayRate"));

      String[] brokerid = brokerids.split(",");
      for (int i = 0; i < brokerid.length; i++) {
        this.entity = new BrokerRewardProps(brokerid[i], commodityId, moduleId, rewardType, rewardRate.divide(rate), firstPayRate.divide(rate), secondPayRate.divide(rate));
        if (!checkId(brokerid[i], moduleId, rewardType, commodityId)) {
          getService().update(this.entity);
          addReturnValue(1, 119902L);
        }
        else {
          getService().add(this.entity);
          addReturnValue(1, 119901L);
        }
      }
    }
    return "success";
  }

  public String checkBroker() throws Exception {
    String brokerId = this.request.getParameter("brokerId");
    String sql = "select * from BR_Broker where brokerId = '" + brokerId.trim() + "'";
    List list = getService().getListBySql(sql);
    if (list.size() > 0)
      this.existValidate = true;
    String moduleId = this.request.getParameter("moduleId");
    String rewardType = this.request.getParameter("rewardType");
    String commodityId = this.request.getParameter("commodityId");
    StringBuilder sql2 = new StringBuilder("select * from BR_BrokerRewardProps where brokerId = '" + brokerId.trim() + "'");
    sql2.append(" and moduleId=").append(moduleId)
      .append(" and rewardType=").append(rewardType)
      .append(" and commodityId='").append(commodityId).append("'");
    List list2 = getService().getListBySql(sql2.toString());
    if (list2.size() > 0)
      this.existValidate = false;
    return "success";
  }

  public boolean checkId(String brokerId, Integer moduleId, Short rewardType, String commodityId)
  {
    String sql = "select * from br_brokerrewardprops where brokerId = '" + brokerId.trim() + 
      "' and moduleId = " + moduleId + 
      " and rewardType = " + rewardType + 
      " and commodityId = '" + commodityId + "'";
    List list = getService().getListBySql(sql);
    if (list.size() > 0) {
      return false;
    }
    return true;
  }

  public List getBrTradeModule()
  {
    return getService().getListBySql("select * from BR_TradeModule", new BrTradeModule());
  }

  public String viewRPById() throws Exception {
    this.logger.debug("------修改特殊佣金参数 跳转--------");
    viewById();
    BrokerRewardProps brp = (BrokerRewardProps)this.entity;
    Integer moduleId = brp.getModuleId();
    String commodity = brp.getCommodityId();
    String sql = "";
    String sql1 = "";
    if (moduleId.intValue() == 18) {
      sql = "select name from k_commodity where commodityid = '" + commodity + "'";
      sql1 = "select cnname shortname  from BR_TradeModule where moduleid=18 ";
    } else if (moduleId.intValue() == 15) {
      sql = "select name from t_commodity where commodityid = '" + commodity + "'";
      sql1 = "select cnname shortname  from BR_TradeModule where moduleid=15 ";
    } else {
      throw new IllegalArgumentException("没有此模块号:" + moduleId);
    }
    List list = getService().getListBySql(sql);
    List list1 = getService().getListBySql(sql1);
    String name = ((Map)list.get(0)).get("NAME").toString();
    String shortname = ((Map)list1.get(0)).get("SHORTNAME").toString();
    this.request.setAttribute("commodityName", name);
    this.request.setAttribute("moduleName", shortname);
    return "success";
  }
}