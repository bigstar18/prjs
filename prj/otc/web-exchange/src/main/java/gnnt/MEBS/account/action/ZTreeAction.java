package gnnt.MEBS.account.action;

import gnnt.MEBS.account.service.ZTreeService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("zTreeAction")
@Scope("request")
public class ZTreeAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(ZTreeAction.class);
  @Autowired
  @Qualifier("zTreeService")
  private ZTreeService zTreeService;
  
  public InService getService()
  {
    return null;
  }
  
  public String zTreeString()
  {
    this.logger.debug("enter list");
    String isRelated = this.request.getParameter("isRelated");
    this.logger.debug("isRelated:" + isRelated);
    this.request.setAttribute("isRelated", isRelated);
    String treeString = "[" + this.zTreeService.getZTree() + "]";
    this.request.setAttribute("treeString", treeString);
    return getReturnValue();
  }
}
