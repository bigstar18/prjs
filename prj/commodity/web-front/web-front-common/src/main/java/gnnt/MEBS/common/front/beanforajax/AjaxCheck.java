package gnnt.MEBS.common.front.beanforajax;

import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.common.QueryConditions;
import gnnt.MEBS.common.front.model.integrated.MFirmApply;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.service.StandardService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("com_ajaxCheck")
@Scope("request")
public class AjaxCheck
  extends BaseAjax
{
  private boolean getTraderUserID(String paramString)
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("userID", "=", paramString);
    PageRequest localPageRequest = new PageRequest(1, 2, localQueryConditions, "");
    Page localPage = getService().getPage(localPageRequest, new User());
    return (localPage != null) && (localPage.getResult() != null) && (localPage.getResult().size() > 0);
  }
  
  private boolean getFirmApplyUserID(String paramString)
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("userID", "=", paramString);
    localQueryConditions.addCondition("status", "<>", Integer.valueOf(2));
    PageRequest localPageRequest = new PageRequest(1, 2, localQueryConditions, "");
    Page localPage = getService().getPage(localPageRequest, new MFirmApply());
    return (localPage != null) && (localPage.getResult() != null) && (localPage.getResult().size() > 0);
  }
  
  public String checkTraderUserID()
  {
    HttpServletRequest localHttpServletRequest = getRequest();
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    if (getTraderUserID(str2)) {
      this.jsonValidateReturn = createJSONArray(new Object[] { str1, Boolean.valueOf(false), "用户名已被占用" });
    } else if (getFirmApplyUserID(str2)) {
      this.jsonValidateReturn = createJSONArray(new Object[] { str1, Boolean.valueOf(false), "用户名已经有人申请" });
    } else {
      this.jsonValidateReturn = createJSONArray(new Object[] { str1, Boolean.valueOf(true), "用户名可以使用" });
    }
    return "success";
  }
  
  public String checkFirmApplyForm()
  {
    HttpServletRequest localHttpServletRequest = getRequest();
    String str = localHttpServletRequest.getParameter("entity.userID");
    BaseAjax.AjaxJSONArrayResponse localAjaxJSONArrayResponse = new BaseAjax.AjaxJSONArrayResponse(this, new Object[0]);
    if (getTraderUserID(str)) {
      localAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "userID", Boolean.valueOf(false), "用户名已被其他交易员使用" }) });
    } else if (getFirmApplyUserID(str)) {
      localAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "userID", Boolean.valueOf(false), "用户名已经有人申请" }) });
    } else {
      localAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "userID", Boolean.valueOf(true), "用户名可以使用" }) });
    }
    this.jsonValidateReturn = localAjaxJSONArrayResponse.toJSONArray();
    return "success";
  }
}
