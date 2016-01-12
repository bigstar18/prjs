package gnnt.MEBS.broker.mgr.action;

import gnnt.MEBS.broker.mgr.model.CustomerModel;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("customerAction")
@Scope("request")
public class CustomerAction extends EcsideAction
{
  private static final long serialVersionUID = 5124568190167465621L;

  @Resource(name="customer_cardTypeMap")
  private Map<String, String> customer_cardTypeMap;

  @Resource(name="customer_typeMap")
  private Map<Integer, String> customer_typeMap;

  @Resource(name="customer_bankCodeMap")
  private Map<String, String> customer_bankCodeMap;

  @Resource(name="customer_statusMap")
  private Map<String, String> customer_statusMap;

  public Map<String, String> getCustomer_cardTypeMap()
  {
    return this.customer_cardTypeMap;
  }

  public void setCustomer_cardTypeMap(Map<String, String> paramMap)
  {
    this.customer_cardTypeMap = paramMap;
  }

  public Map<Integer, String> getCustomer_typeMap()
  {
    return this.customer_typeMap;
  }

  public void setCustomer_typeMap(Map<Integer, String> paramMap)
  {
    this.customer_typeMap = paramMap;
  }

  public Map<String, String> getCustomer_bankCodeMap()
  {
    return this.customer_bankCodeMap;
  }

  public void setCustomer_bankCodeMap(Map<String, String> paramMap)
  {
    this.customer_bankCodeMap = paramMap;
  }

  public Map<String, String> getCustomer_statusMap()
  {
    return this.customer_statusMap;
  }

  public void setCustomer_statusMap(Map<String, String> paramMap)
  {
    this.customer_statusMap = paramMap;
  }

  public String addCustomer()
  {
    this.logger.debug("------------addCustomer 添加客户信息--------------");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    CustomerModel localCustomerModel = (CustomerModel)this.entity;
    localCustomerModel.setUser(localUser);
    localCustomerModel.setStatus("N");
    getService().add(localCustomerModel);
    addReturnValue(1, 119901L);
    return "success";
  }
}