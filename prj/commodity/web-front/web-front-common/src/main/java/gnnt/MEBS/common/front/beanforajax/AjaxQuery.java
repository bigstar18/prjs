package gnnt.MEBS.common.front.beanforajax;

import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.common.QueryConditions;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.service.StandardService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("com_ajaxQuery")
@Scope("request")
public class AjaxQuery
  extends BaseAjax
{
  private User getTraderUserID(String paramString)
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("userID", "=", paramString);
    PageRequest localPageRequest = new PageRequest(1, 2, localQueryConditions, "");
    Page localPage = getService().getPage(localPageRequest, new User());
    if ((localPage != null) && (localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
      return (User)localPage.getResult().get(0);
    }
    return null;
  }
  
  public String getTraderIDByUserID()
  {
    HttpServletRequest localHttpServletRequest = getRequest();
    String str = localHttpServletRequest.getParameter("userID");
    if (str != null)
    {
      User localUser = getTraderUserID(str);
      if (localUser != null) {
        this.jsonValidateReturn = createJSONArray(new Object[] { localUser.getTraderID() });
      }
    }
    return "success";
  }
}
