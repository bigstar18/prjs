package gnnt.MEBS.finance.mgr.action;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.finance.mgr.model.FattachExplain;
import gnnt.MEBS.finance.mgr.model.FhAttachExplain;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("attachExplainQueryAction")
@Scope("request")
public class AttachExplainQueryAction extends EcsideAction
{
  private static final long serialVersionUID = 705820473660297118L;

  public String attachExplainQuery()
    throws Exception
  {
    this.logger.debug("enter attachExplainQuery");
    String str = this.request.getParameter("type");
    if ((str != null) && (str.equals("H")))
    {
      this.entity = new FhAttachExplain();
      str = "H";
    }
    else
    {
      this.entity = new FattachExplain();
      str = "D";
    }
    list();
    this.request.setAttribute("type", str);
    return "success";
  }
}