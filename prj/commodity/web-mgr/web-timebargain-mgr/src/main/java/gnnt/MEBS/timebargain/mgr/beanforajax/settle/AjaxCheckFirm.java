package gnnt.MEBS.timebargain.mgr.beanforajax.settle;

import com.opensymphony.xwork2.ActionContext;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.dataquery.MFirmModel;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxCheckFirm")
@Scope("request")
public class AjaxCheckFirm
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

  public String mouseCheckFirmByFirmId()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");

    String fieldId = request.getParameter("fieldId");
    String fieldValue = request.getParameter("fieldValue");

    this.jsonValidateReturn = getJSONArray(new Object[] { fieldId, Boolean.valueOf(commodityIdExist(fieldValue)) });

    return this.SUCCESS;
  }

  private JSONArray getJSONArray(Object[] values)
  {
    JSONArray result = new JSONArray();
    for (Object value : values) {
      result.add(value);
    }
    return result;
  }

  private boolean commodityIdExist(String firmId)
  {
    boolean result = false;
    if ((firmId == null) || (firmId.trim().length() <= 0)) {
      return result;
    }
    PageRequest pageRequest = new PageRequest(" and primary.firmId='" + firmId + "'");
    Page page = getService().getPage(pageRequest, new MFirmModel());
    if ((page.getResult() != null) && (page.getResult().size() > 0)) {
      result = true;
    }
    return result;
  }

  public String checkFirmExistsById()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac
      .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");

    String firmID = request.getParameter("firmID");
    boolean flag = commodityIdExist(firmID);

    if (flag)
      this.jsonValidateReturn = getJSONArray(new Object[] { "true" });
    else {
      this.jsonValidateReturn = getJSONArray(new Object[] { "false" });
    }
    return this.SUCCESS;
  }
}