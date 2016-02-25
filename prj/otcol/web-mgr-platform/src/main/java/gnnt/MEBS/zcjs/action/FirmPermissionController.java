package gnnt.MEBS.zcjs.action;

import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.member.ActiveUser.ActiveUserRMI;
import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.zcjs.model.FirmPermission;
import gnnt.MEBS.zcjs.services.FirmPermissionService;
import gnnt.MEBS.zcjs.util.OnLine;
import gnnt.MEBS.zcjs.util.SysData;
import java.rmi.Naming;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class FirmPermissionController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(BreedController.class);
  
  private FirmPermissionService getBeanService()
  {
    FirmPermissionService localFirmPermissionService = null;
    synchronized (FirmPermissionService.class)
    {
      if (localFirmPermissionService == null) {
        localFirmPermissionService = (FirmPermissionService)SysData.getBean("z_firmPermissionService");
      }
    }
    return localFirmPermissionService;
  }
  
  public ModelAndView list(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'list' method...");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "firmId", false);
    }
    List localList = getBeanService().getTableList(localQueryConditions, localPageInfo);
    Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "firmPermission/firmPermissionList");
    localModelAndView.addObject("resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView updateForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str = paramHttpServletRequest.getParameter("firmId");
    FirmPermission localFirmPermission = getBeanService().getFirmPermissionById(str);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "firmPermission/firmPermissionMod");
    localModelAndView.addObject("firmPermission", localFirmPermission);
    return localModelAndView;
  }
  
  public ModelAndView updateFirmPermission(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter updatefirmPermission========");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("purview");
    String str1 = "";
    FirmPermission localFirmPermission = new FirmPermission();
    localFirmPermission.setFirmId(paramHttpServletRequest.getParameter("firmId"));
    localFirmPermission.setBuyDelist("N");
    localFirmPermission.setBuyListing("N");
    localFirmPermission.setSellDelist("N");
    localFirmPermission.setSellListing("N");
    localFirmPermission.setSellRegstock("N");
    if ((arrayOfString != null) && (arrayOfString.length > 0)) {
      for (int i = 0; i < arrayOfString.length; i++)
      {
        str1 = arrayOfString[i];
        localFirmPermission.set(str1, "Y");
      }
    }
    String str2 = "";
    try
    {
      getBeanService().update(localFirmPermission);
      str2 = "修改成功";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str2 = "修改失败";
    }
    setResultMsg(paramHttpServletRequest, str2);
    return new ModelAndView("redirect:" + Condition.PATH + "firmPermissionController.zcjs" + "?funcflg=list");
  }
  
  public ModelAndView commonUserOnLineList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserOnLineList()---//");
    String str1 = paramHttpServletRequest.getParameter("userId");
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    DataSource localDataSource = localDaoHelper.getDataSource();
    Map localMap1 = LogonManager.getRMIConfig("3", localDataSource);
    String str2 = (String)localMap1.get("host");
    int i = ((Integer)localMap1.get("port")).intValue();
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "firmPermission/onLineUsers");
    try
    {
      ActiveUserRMI localActiveUserRMI = (ActiveUserRMI)Naming.lookup("rmi://" + str2 + ":" + i + "/ActiveUserService");
      String str3 = localActiveUserRMI.getAllUsers();
      String[] arrayOfString = str3.split(";");
      Map localMap2 = OnLine.transforArrayToMap(arrayOfString);
      localModelAndView.addObject("onLineUsersMap", localMap2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localModelAndView;
  }
}
