package gnnt.MEBS.trade.action;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.account.model.Trader;
import gnnt.MEBS.account.service.CustomerService;
import gnnt.MEBS.account.service.TraderService;
import gnnt.MEBS.base.copy.CopyObjectParamUtil;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.Commodity;
import gnnt.MEBS.commodity.service.CommodityService;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.timebargain.server.model.LimitOrder;
import gnnt.MEBS.timebargain.server.model.MarketOrder;
import gnnt.MEBS.trade.model.OrdersForm;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class InsteadOrderAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(InsteadOrderAction.class);
  private OrdersForm ordersForm;
  @Autowired
  @Qualifier("customerService")
  private CustomerService customerService;
  @Autowired
  @Qualifier("commodityService")
  private CommodityService commodityService;
  @Autowired
  @Qualifier("traderService")
  private TraderService traderService;
  @Resource(name="consignerMarketOrderRMIMap")
  protected Map<Integer, String> consignerMarketOrderRMIMap;
  @Resource(name="consignerLimitOrderRMIMap")
  protected Map<Integer, String> consignerLimitOrderRMIMap;
  
  public OrdersForm getOrdersForm()
  {
    return this.ordersForm;
  }
  
  public void setOrdersForm(OrdersForm ordersForm)
  {
    this.ordersForm = ordersForm;
  }
  
  public InService getService()
  {
    return this.customerService;
  }
  
  public String login()
  {
    this.logger.debug("---------login-----------");
    List<Commodity> commodityList = this.commodityService.getList(null, null);
    this.request.setAttribute("commodityList", commodityList);
    String result = "";
    int resultValue = 1;
    String traderID = this.request.getParameter("traderID");
    String password = this.request.getParameter("password");
    if (traderID == null)
    {
      traderID = (String)this.request.getSession().getAttribute("traderID");
      password = (String)this.request.getSession().getAttribute("password");
    }
    QueryConditions qc = new QueryConditions();
    String memberNo = (String)this.request.getSession().getAttribute(ActionConstant.REGISTERID);
    this.logger.debug("memberNO:" + memberNo);
    
    qc.addCondition("primary.memberNo", "=", memberNo);
    List<Customer> list = this.customerService.getList(qc, null);
    if (list.size() == 0)
    {
      resultValue = -7;
      this.request.getSession().removeAttribute("traderID");
      addResultSessionMsg(this.request, resultValue);
      return "error";
    }
    boolean flag = false;
    if (list.size() > 0) {
      for (Customer customer : list)
      {
        if (customer.getCustomerNo().equals(traderID))
        {
          flag = true;
          resultValue = 1;
          break;
        }
        resultValue = -6;
      }
    }
    if (resultValue == -6)
    {
      this.request.getSession().removeAttribute("traderID");
      addResultSessionMsg(this.request, resultValue);
      return "error";
    }
    if (flag)
    {
      Customer queryedUser = (Customer)this.customerService.getById(traderID);
      if (!MD5.getMD5(traderID, password).equals(queryedUser.getPhonePWD()))
      {
        resultValue = -8;
        this.request.getSession().removeAttribute("traderID");
        addResultSessionMsg(this.request, resultValue);
        return "error";
      }
    }
    if (this.request.getSession().getAttribute("traderID") == null)
    {
      this.request.getSession().setAttribute("traderID", traderID);
      this.request.getSession().setAttribute("password", password);
    }
    if (resultValue != 1)
    {
      this.request.getSession().removeAttribute("traderID");
      addResultSessionMsg(this.request, resultValue);
      result = "error";
    }
    else
    {
      result = getReturnValue();
    }
    return result;
  }
  
  public String logoff()
  {
    this.request.getSession().removeAttribute("traderID");
    addResultMsg(this.request, -5);
    return getReturnValue();
  }
  
  public String edit()
  {
    int resultValue = 0;
    try
    {
      AgencyRMIBean remObject = new AgencyRMIBean(this.request);
      if (1 == this.ordersForm.getOrderType().shortValue())
      {
        MarketOrder marketOrder = new MarketOrder();
        CopyObjectParamUtil.bindData(this.ordersForm, marketOrder);
        marketOrder.setConsignerID(AclCtrl.getUser(this.request).getMemberInfo().getId());
        marketOrder.setConsignFirmID(((Trader)this.traderService.getById(this.ordersForm.getTraderID())).getFirmID());
        resultValue = remObject.consignerMarketOrder(0L, marketOrder);
        resultValue = Integer.parseInt((String)this.consignerMarketOrderRMIMap.get(Integer.valueOf(resultValue)));
      }
      else if (2 == this.ordersForm.getOrderType().shortValue())
      {
        LimitOrder limitOrder = new LimitOrder();
        CopyObjectParamUtil.bindData(this.ordersForm, limitOrder);
        limitOrder.setConsignerID(AclCtrl.getUser(this.request).getMemberInfo().getId());
        limitOrder.setConsignFirmID(((Trader)this.traderService.getById(this.ordersForm.getTraderID())).getFirmID());
        resultValue = remObject.consignerLimitOrder(0L, limitOrder);
        resultValue = Integer.parseInt((String)this.consignerLimitOrderRMIMap.get(Integer.valueOf(resultValue)));
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      resultValue = -1;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String forwardAdd()
  {
    this.logger.debug("---------forwardAdd-----------");
    if (this.request.getSession().getAttribute("traderID") != null) {
      return "login";
    }
    return getReturnValue();
  }
}
