package gnnt.MEBS.zcjs.action;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.services.ProsceniumShowService;
import gnnt.MEBS.zcjs.util.SysData;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class ProsceniumShowController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(ProsceniumShowController.class);
  
  private ProsceniumShowService getBeanOfProsceniumShowService()
  {
    ProsceniumShowService localProsceniumShowService = null;
    synchronized (ProsceniumShowService.class)
    {
      if (localProsceniumShowService == null) {
        localProsceniumShowService = (ProsceniumShowService)SysData.getBean("z_prosceniumShowService");
      }
    }
    return localProsceniumShowService;
  }
  
  public ModelAndView getList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws Exception
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("ProsceniumApplication", "=", "QT_C_GoodsOrder");
    List localList = getBeanOfProsceniumShowService().getObjectList(localQueryConditions);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "prosceniumShow/prosceniumShow");
    localModelAndView.addObject("resultList", localList);
    return localModelAndView;
  }
  
  public ModelAndView mod(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter mod========");
    String str = "";
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("showProperty");
    try
    {
      getBeanOfProsceniumShowService().update(arrayOfString);
      str = "保存成功";
    }
    catch (RuntimeException localRuntimeException)
    {
      str = "保存失败";
      localRuntimeException.printStackTrace();
    }
    setResultMsg(paramHttpServletRequest, str);
    return new ModelAndView("redirect:" + Condition.PATH + "prosceniumShowController.zcjs?funcflg=getList");
  }
}
