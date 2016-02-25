package gnnt.MEBS.zcjs.action;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.zcjs.model.Quality;
import gnnt.MEBS.zcjs.services.QualityService;
import gnnt.MEBS.zcjs.util.SysData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class QualityController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(QualityController.class);
  
  private QualityService getBeanOfQualityService()
  {
    QualityService localQualityService = null;
    synchronized (QualityService.class)
    {
      if (localQualityService == null) {
        localQualityService = (QualityService)SysData.getBean("z_qualityService");
      }
    }
    return localQualityService;
  }
  
  public ModelAndView list(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter list========");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "qualityid", false);
    }
    List localList = getBeanOfQualityService().getTableList(localQueryConditions, localPageInfo);
    Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "quality/qualityList");
    localModelAndView.addObject("resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView mod(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter mod========");
    String str1 = "";
    String str2 = paramHttpServletRequest.getParameter("type");
    Quality localQuality1 = new Quality();
    ParamUtil.bindData(paramHttpServletRequest, localQuality1);
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("delCheck");
    ArrayList localArrayList = new ArrayList();
    int i = Integer.parseInt(str2);
    StringBuffer localStringBuffer = new StringBuffer();
    for (String str3 : arrayOfString1)
    {
      String[] arrayOfString3 = str3.split(",");
      Quality localQuality2 = getBeanOfQualityService().getObject(Long.parseLong(arrayOfString3[0]));
      if (i == localQuality2.getStatus()) {
        localStringBuffer.append(arrayOfString3[0] + ",");
      } else {
        localArrayList.add(Long.valueOf(Long.parseLong(arrayOfString3[0])));
      }
    }
    if (localStringBuffer.length() > 0) {
      localStringBuffer.append("无需修改！");
    }
    if (localArrayList.size() > 0)
    {
      for (int j = 0; j < localArrayList.size(); j++) {
        localStringBuffer.append(localArrayList.get(j) + ",");
      }
      try
      {
        getBeanOfQualityService().mod(localArrayList, i);
        localStringBuffer.append("修改成功！");
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        localStringBuffer.append("修改失败！");
      }
    }
    setResultMsg(paramHttpServletRequest, localStringBuffer.toString());
    return new ModelAndView("redirect:" + Condition.PATH + "qualityController.zcjs?funcflg=list");
  }
}
