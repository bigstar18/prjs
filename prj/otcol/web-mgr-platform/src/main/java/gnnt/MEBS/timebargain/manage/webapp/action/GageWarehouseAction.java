package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.timebargain.manage.service.BreedManager;
import gnnt.MEBS.timebargain.manage.service.GageWarehouseManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GageWarehouseAction
  extends BaseAction
{
  private Log logger = LogFactory.getLog(GageWarehouseAction.class);
  
  private boolean whetherIsNull(String paramString)
  {
    return (paramString != null) && (!paramString.equals(""));
  }
  
  public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---enter  默认方法 unspecified()-----do nothing---------return null----------");
    return gageWarehouseList(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward gageWarehouseList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---enter  gageWarehouseList()--------------");
    BreedManager localBreedManager = (BreedManager)getBean("breedManager");
    GageWarehouseManager localGageWarehouseManager = (GageWarehouseManager)getBean("gageWarehouseManager");
    QueryConditions localQueryConditions = new QueryConditions();
    String str1 = paramHttpServletRequest.getParameter("BillID");
    String str2 = paramHttpServletRequest.getParameter("BreedID");
    String str3 = paramHttpServletRequest.getParameter("FirmID");
    if (whetherIsNull(str1)) {
      localQueryConditions.addCondition("a.BillID", "like", "%" + str1 + "%");
    }
    if (whetherIsNull(str2)) {
      localQueryConditions.addCondition("a.BreedID", "like", str2);
    }
    if (whetherIsNull(str3)) {
      localQueryConditions.addCondition("a.FIRMID", "like", "%" + str3 + "%");
    }
    List localList1 = localGageWarehouseManager.gageWarehouseList(localQueryConditions);
    List localList2 = localBreedManager.getBreeds(null);
    paramHttpServletRequest.setAttribute("gageWarehouseList", localList1);
    paramHttpServletRequest.setAttribute("breedList", localList2);
    paramHttpServletRequest.setAttribute("BillID", str1);
    paramHttpServletRequest.setAttribute("BreedID", str2);
    paramHttpServletRequest.setAttribute("FirmID", str3);
    return paramActionMapping.findForward("gageWarehouseList");
  }
  
  public ActionForward revocation(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---enter  revocation()--------------");
    String str1 = "撤消仓单抵顶:";
    GageWarehouseManager localGageWarehouseManager = (GageWarehouseManager)getBean("gageWarehouseManager");
    QueryConditions localQueryConditions = new QueryConditions();
    String str2 = AclCtrl.getLogonID(paramHttpServletRequest);
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
    for (String str3 : arrayOfString1)
    {
      String[] arrayOfString3 = str3.split("&");
      long l = localGageWarehouseManager.revocationNew(arrayOfString3[0], arrayOfString3[1], arrayOfString3[2], arrayOfString3[3], str2);
      if (l == 1L)
      {
        addSysLog(paramHttpServletRequest, "撤消仓单抵顶:仓单号" + arrayOfString3[0] + ",交易商" + arrayOfString3[1] + ",商品品种" + arrayOfString3[2] + ",数量" + arrayOfString3[3]);
        str1 = str1 + "仓单号" + arrayOfString3[0] + ",交易商" + arrayOfString3[1] + ",商品品种" + arrayOfString3[2] + ",数量" + arrayOfString3[3] + " 成功！    ";
      }
      else
      {
        str1 = str1 + "仓单号" + arrayOfString3[0] + ",交易商" + arrayOfString3[1] + ",商品品种" + arrayOfString3[2] + ",数量" + arrayOfString3[3] + " 失败！    ";
      }
    }
    paramHttpServletRequest.setAttribute("prompt", str1);
    return gageWarehouseList(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward addGageWarehouse(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---enter  addGageWarehouse()--------------");
    GageWarehouseManager localGageWarehouseManager = (GageWarehouseManager)getBean("gageWarehouseManager");
    String str1 = paramHttpServletRequest.getParameter("billid");
    String str2 = paramHttpServletRequest.getParameter("firmid");
    String str3 = paramHttpServletRequest.getParameter("breedid");
    String str4 = paramHttpServletRequest.getParameter("quantity");
    String str5 = paramHttpServletRequest.getParameter("remark1");
    String str6 = paramHttpServletRequest.getParameter("issettle");
    String str7 = AclCtrl.getLogonID(paramHttpServletRequest);
    String str8 = "添加仓单抵顶成功！";
    QueryConditions localQueryConditions = new QueryConditions("billid", "=", str1);
    List localList = localGageWarehouseManager.gageWarehouseList(localQueryConditions);
    if ((localList == null) || (localList.size() == 0))
    {
      if ((str4 != null) && (Double.parseDouble(str4) > 0.0D))
      {
        long l = localGageWarehouseManager.addGageWarehouse(str1, str2, str3, str4, str5, str7, str6);
        if (l == 1L) {
          addSysLog(paramHttpServletRequest, "添加仓单抵顶:仓单号" + str1 + ",交易商" + str2 + ",商品品种" + str3 + ",数量" + str4);
        } else {
          str8 = "添加仓单抵顶失败！";
        }
      }
      else
      {
        str8 = "仓单数量不符合条件!";
      }
    }
    else {
      str8 = "同一仓单不能重复添加！";
    }
    paramHttpServletRequest.setAttribute("prompt", str8);
    return gageWarehouseList(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward validGageBillList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---enter  validGageBillList()--------------");
    BreedManager localBreedManager = (BreedManager)getBean("breedManager");
    GageWarehouseManager localGageWarehouseManager = (GageWarehouseManager)getBean("gageWarehouseManager");
    QueryConditions localQueryConditions = new QueryConditions();
    String str1 = paramHttpServletRequest.getParameter("BreedID");
    String str2 = paramHttpServletRequest.getParameter("FirmID");
    if (whetherIsNull(str1)) {
      localQueryConditions.addCondition("a.BreedID", "=", str1);
    }
    if (whetherIsNull(str2)) {
      localQueryConditions.addCondition("a.FIRMID", "like", "%" + str2 + "%");
    }
    List localList1 = localGageWarehouseManager.validGageBillList(localQueryConditions);
    List localList2 = localBreedManager.getBreeds(null);
    paramHttpServletRequest.setAttribute("breedList", localList2);
    paramHttpServletRequest.setAttribute("validGageBillList", localList1);
    paramHttpServletRequest.setAttribute("BreedID", str1);
    paramHttpServletRequest.setAttribute("FirmID", str2);
    return paramActionMapping.findForward("validGageBillList");
  }
}
