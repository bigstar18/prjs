package gnnt.MEBS.commodity.action;

import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.Commodity;
import gnnt.MEBS.commodity.model.DelayTrade;
import gnnt.MEBS.commodity.service.CommodityService;
import gnnt.MEBS.commodity.service.DelayTradeService;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("delayTradeAction")
@Scope("request")
public class DelayTradeAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(DelayTradeAction.class);
  @Autowired
  @Qualifier("delayTradeService")
  private DelayTradeService delayTradeService;
  @Autowired
  @Qualifier("commodityService")
  private CommodityService commodityService;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  @Resource(name="delayTradeMap")
  private Map delayTradeMap;
  @Resource(name="isslipPointMap")
  private Map isslipPointMap;
  
  public Map getDelayTradeMap()
  {
    return this.delayTradeMap;
  }
  
  public Map getIsslipPointMap()
  {
    return this.isslipPointMap;
  }
  
  public InService getService()
  {
    return this.delayTradeService;
  }
  
  public String add()
  {
    this.logger.debug("enter add");
    String commodityIdValue = this.request.getParameter("obj.commodityId");
    if ((commodityIdValue != null) && (!commodityIdValue.equals("")))
    {
      super.add();
    }
    else
    {
      QueryConditions conditions = new QueryConditions("status", "in", "(0,1,2)");
      List<Commodity> commodityList = this.commodityService.getList(conditions, null);
      DelayTrade delayTrade = (DelayTrade)this.obj;
      boolean flag = true;
      for (int i = 0; i < commodityList.size(); i++)
      {
        Commodity commodity = (Commodity)commodityList.get(i);
        delayTrade.setCommodityId(commodity.getId());
        delayTrade.setCommodityName(commodity.getName());
        if (getService().get(delayTrade) != null)
        {
          this.request.setAttribute(ActionConstant.RESULTMSG, "添加信息已存在，请重新添加！");
          this.request.setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(-1));
          flag = false;
          break;
        }
        flag = true;
      }
      if (flag) {
        for (int i = 0; i < commodityList.size(); i++)
        {
          Commodity commodity = (Commodity)commodityList.get(i);
          delayTrade.setCommodityId(commodity.getId());
          delayTrade.setCommodityName(commodity.getName());
          int resultValue = getService().add(delayTrade);
          addResultMsg(this.request, resultValue);
          if (resultValue != 2) {
            break;
          }
        }
      }
    }
    return getReturnValue();
  }
  
  public String delete()
  {
    this.logger.debug("enter delete");
    String[] ids = this.request.getParameterValues("ids");
    int resultValue = 1;
    if ((ids != null) && (ids.length > 0)) {
      for (String id : ids)
      {
        String[] primarys = id.split(",");
        DelayTrade delayTrade = new DelayTrade();
        delayTrade.setF_FirmId(primarys[0]);
        delayTrade.setCommodityId(primarys[1]);
        delayTrade = (DelayTrade)getService().get(delayTrade);
        if (delayTrade != null) {
          resultValue = getService().delete(delayTrade);
        }
      }
    } else {
      resultValue = -2;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String updateRMI()
  {
    try
    {
      AgencyRMIBean remObject = new AgencyRMIBean(this.request);
      this.delayTradeService.updateSlipPointPro("M");
      remObject.getMaxDelayTime();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      this.logger.error("updateRMI:调用出错" + ex.getMessage());
    }
    return "success";
  }
  
  public void forwardAttribute()
  {
    this.logger.debug("enter memberTakeFeeParameters");
    this.request.setAttribute("delayTradeMap", this.delayTradeMap);
    this.request.setAttribute("isslipPointMap", "isslipPointMap");
  }
}
