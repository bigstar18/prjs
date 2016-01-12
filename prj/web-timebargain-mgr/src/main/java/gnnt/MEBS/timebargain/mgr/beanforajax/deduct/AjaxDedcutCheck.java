package gnnt.MEBS.timebargain.mgr.beanforajax.deduct;

import com.opensymphony.xwork2.ActionContext;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.deduct.DeductKeep;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxCheckDeduct")
@Scope("request")
public class AjaxDedcutCheck
{
  private String SUCCESS = "success";

  @Autowired
  @Qualifier("com_standardService")
  private StandardService standardService;
  private JSONArray jsonValidateReturn;

  public StandardService getService()
  {
    return this.standardService;
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

  private boolean existDeductKeep(Long deductId, Integer bs_Flag, String customerId)
  {
    boolean result = false;
    PageRequest pageRequest = new PageRequest(" and primary.deductId='" + deductId + "' and primary.bs_Flag=" + bs_Flag + " and primary.customerId='" + customerId + "'");
    Page page = getService().getPage(pageRequest, new DeductKeep());
    if ((page.getResult() != null) && (page.getResult().size() > 0)) {
      result = true;
    }
    return result;
  }

  public String formCheckDeductKeepByIds()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    Long deductId = new Long(request.getParameter("entity.deductId"));
    Integer bs_Flag = new Integer(request.getParameter("entity.bs_Flag"));
    String customerId = request.getParameter("entity.customerId");

    boolean flag = existDeductKeep(deductId, bs_Flag, customerId);
    if (flag)
      this.jsonValidateReturn = getJSONArrayList(new JSONArray[] { getJSONArray(new Object[] { "keepQty", Boolean.valueOf(false), "该记录已存在请更换交易客户或买卖标志！" }) });
    else {
      this.jsonValidateReturn = getJSONArray(new Object[] { "true" });
    }

    return this.SUCCESS;
  }
}