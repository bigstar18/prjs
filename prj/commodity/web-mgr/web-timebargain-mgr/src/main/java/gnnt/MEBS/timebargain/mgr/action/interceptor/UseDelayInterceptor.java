package gnnt.MEBS.timebargain.mgr.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.SubSystem;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class UseDelayInterceptor extends AbstractInterceptor
{

  @Autowired
  @Qualifier("com_standardService")
  private StandardService standardService;
  private String useDelay = "N";
  private String typicalTradeMode = "N";

  public void setStandardService(StandardService paramStandardService)
  {
    this.standardService = paramStandardService;
  }

  public String intercept(ActionInvocation paramActionInvocation)
    throws Exception
  {
    HttpServletRequest localHttpServletRequest = ServletActionContext.getRequest();
    PageRequest localPageRequest = new PageRequest();
    localPageRequest.setPageNumber(1);
    localPageRequest.setPageSize(100);
    Page localPage = this.standardService.getPage(localPageRequest, new SubSystem());
    List localList = localPage.getResult();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      SubSystem localSubSystem = (SubSystem)localIterator.next();
      if ((localSubSystem.getModuleID().shortValue() == 16) && ("Y".equals(localSubSystem.getEnabled())))
        this.useDelay = "Y";
      if ((localSubSystem.getModuleID().shortValue() == 17) && ("Y".equals(localSubSystem.getEnabled())))
        this.typicalTradeMode = "Y";
    }
    localHttpServletRequest.setAttribute("typicalTradeMode", this.typicalTradeMode);
    localHttpServletRequest.setAttribute("useDelay", this.useDelay);
    return paramActionInvocation.invoke();
  }
}