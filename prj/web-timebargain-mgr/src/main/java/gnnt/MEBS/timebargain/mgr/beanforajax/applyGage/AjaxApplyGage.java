package gnnt.MEBS.timebargain.mgr.beanforajax.applyGage;

import com.opensymphony.xwork2.ActionContext;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.applyGage.CustomerA;
import gnnt.MEBS.timebargain.mgr.model.applyGage.CustomerHoldSumA;
import gnnt.MEBS.timebargain.mgr.model.applyGage.ValidGageBill;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxApplyGage")
@Scope("request")
public class AjaxApplyGage
{
  protected String SUCCESS = "success";
  private CustomerA customer;

  @Autowired
  @Qualifier("com_standardService")
  private StandardService standardService;
  protected JSONArray jsonValidateReturn;
  private String opr;

  public StandardService getService()
  {
    return this.standardService;
  }

  public String getOpr()
  {
    return this.opr;
  }

  public JSONArray getJsonValidateReturn()
  {
    return this.jsonValidateReturn;
  }

  private JSONArray getJSONArray(Object[] values)
  {
    JSONArray result = new JSONArray();
    for (Object value : values) {
      result.add(value);
    }
    return result;
  }

  private JSONArray getJSONArrayList(JSONArray[] values)
  {
    JSONArray result = new JSONArray();
    for (Object value : values) {
      result.add(value);
    }
    return result;
  }

  private boolean existCustomerCustomerId(String customerId)
  {
    boolean result = false;
    if ((customerId == null) || (customerId.trim().length() <= 0)) {
      return result;
    }
    this.customer = new CustomerA();
    this.customer.setCustomerId(customerId);
    this.customer = ((CustomerA)getService().get(this.customer));
    if ((this.customer != null) && (this.customer.getFirmId() != null)) {
      result = true;
    }
    return result;
  }

  private boolean haveGageQuantity(String firmId, String commodityId, Long quantity)
  {
    boolean result = false;
    if ((firmId == null) || (firmId.trim().length() <= 0)) {
      return result;
    }
    ValidGageBill validGageBill = new ValidGageBill();
    validGageBill.setFirmId(firmId);
    validGageBill.setCommodityId(commodityId);
    validGageBill = (ValidGageBill)getService().get(validGageBill);
    if ((validGageBill != null) && 
      (validGageBill.getQuantity().longValue() - validGageBill.getFrozenQty().longValue() >= quantity.longValue())) {
      result = true;
    }

    return result;
  }

  private boolean haveCancelGageQuantity(String customerId, String commodityId, Long quantity)
  {
    boolean result = false;
    if ((customerId == null) || (customerId.trim().length() <= 0)) {
      return result;
    }
    CustomerHoldSumA customerHoldSum = new CustomerHoldSumA();
    customerHoldSum.setCustomerId(customerId);
    customerHoldSum.setCommodityId(commodityId);
    customerHoldSum.setBs_Flag(Integer.valueOf(2));
    customerHoldSum = (CustomerHoldSumA)getService().get(customerHoldSum);
    if ((customerHoldSum != null) && 
      (customerHoldSum.getGageQty().longValue() - customerHoldSum.getGageFrozenQty().longValue() >= quantity.longValue())) {
      result = true;
    }

    return result;
  }

  public String formCheck()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String customerId = request.getParameter("entity.customerId");
    String commodityId = request.getParameter("entity.commodityId");
    Long quantity = Long.valueOf(Long.parseLong(request.getParameter("entity.quantity")));
    int applyType = Integer.parseInt(request.getParameter("entity.applyType"));

    boolean flag = existCustomerCustomerId(customerId);
    if (flag) {
      if (applyType == 1) {
        flag = haveGageQuantity(this.customer.getFirmId(), commodityId, quantity);
        if (!flag)
          this.jsonValidateReturn = getJSONArrayList(new JSONArray[] { getJSONArray(new Object[] { "quantity", Boolean.valueOf(false), "可申请的抵顶数量不足" }) });
      } else {
        flag = haveCancelGageQuantity(customerId, commodityId, quantity);
        if (!flag)
          this.jsonValidateReturn = getJSONArrayList(new JSONArray[] { getJSONArray(new Object[] { "quantity", Boolean.valueOf(false), "可撤销的抵顶数量不足" }) });
      }
    }
    else this.jsonValidateReturn = getJSONArrayList(new JSONArray[] { getJSONArray(new Object[] { "customerId", Boolean.valueOf(false), "二级代码不存在" }) });

    if (flag) {
      this.jsonValidateReturn = getJSONArray(new Object[] { "true" });
    }
    return this.SUCCESS;
  }
}