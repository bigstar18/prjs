package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.timebargain.manage.model.CmdtySort;
import gnnt.MEBS.timebargain.manage.service.CmdtySortManager;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.webapp.form.CmdtySortForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.dao.DataIntegrityViolationException;

public class CmdtySortAction
  extends BaseAction
{
  public ActionForward edit(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'edit' method");
    }
    CmdtySortForm localCmdtySortForm = (CmdtySortForm)paramActionForm;
    String str = localCmdtySortForm.getCrud();
    CmdtySortManager localCmdtySortManager = (CmdtySortManager)getBean("cmdtySortManager");
    CmdtySort localCmdtySort = null;
    try
    {
      if (!str.trim().equals("create"))
      {
        localCmdtySort = localCmdtySortManager.getCmdtySortById(Long.valueOf(localCmdtySortForm.getSortID()));
        this.log.debug("edit CmdtySort.SortName:" + localCmdtySort.getSortName());
      }
      else
      {
        localCmdtySort = new CmdtySort();
      }
      localCmdtySortForm = (CmdtySortForm)convert(localCmdtySort);
      localCmdtySortForm.setCrud(str);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localCmdtySortForm);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    return paramActionMapping.findForward("edit");
  }
  
  public ActionForward save(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'save' method");
    }
    CmdtySortForm localCmdtySortForm = (CmdtySortForm)paramActionForm;
    String str = localCmdtySortForm.getCrud();
    CmdtySortManager localCmdtySortManager = (CmdtySortManager)getBean("cmdtySortManager");
    CmdtySort localCmdtySort = (CmdtySort)convert(localCmdtySortForm);
    try
    {
      if (str.trim().equals("create"))
      {
        localCmdtySortManager.insertCmdtySort(localCmdtySort);
        addSysLog(paramHttpServletRequest, "增加商品分类[" + localCmdtySort.getSortID() + "]");
        paramHttpServletRequest.setAttribute("prompt", "操作成功！");
      }
      else if (str.trim().equals("update"))
      {
        localCmdtySortManager.updateCmdtySort(localCmdtySort);
        addSysLog(paramHttpServletRequest, "修改商品分类[" + localCmdtySort.getSortID() + "]");
        paramHttpServletRequest.setAttribute("prompt", "操作成功！");
      }
    }
    catch (Exception localException)
    {
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return paramActionMapping.findForward("edit");
    }
    return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward delete(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'delete' method");
    }
    CmdtySortManager localCmdtySortManager = (CmdtySortManager)getBean("cmdtySortManager");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("itemlist");
    int i = 0;
    if (arrayOfString != null)
    {
      this.log.debug("==ids.length:" + arrayOfString.length);
      String str2 = "";
      for (int j = 0; j < arrayOfString.length; j++)
      {
        String str1 = arrayOfString[j];
        try
        {
          localCmdtySortManager.deleteCmdtySortById(Long.valueOf(str1));
          addSysLog(paramHttpServletRequest, "删除商品分类[" + str1 + "]");
          i++;
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str2 = str2 + str1 + ",";
          paramHttpServletRequest.setAttribute("prompt", "该[" + str1 + "]品种分类已有品种合约，删除失败！");
        }
        catch (RuntimeException localRuntimeException)
        {
          str2 = str2 + str1 + ",";
          paramHttpServletRequest.setAttribute("prompt", "该[" + str1 + "]品种分类已有品种合约，删除失败！");
        }
      }
      if (!str2.equals(""))
      {
        str2 = str2.substring(0, str2.length() - 1);
        str2 = str2 + "品种分类已有品种合约，不能删除！";
      }
      str2 = str2 + "成功删除" + i + "条纪录！";
      paramHttpServletRequest.setAttribute("prompt", str2);
    }
    return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward search(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'search' method");
    }
    CmdtySortManager localCmdtySortManager = (CmdtySortManager)getBean("cmdtySortManager");
    try
    {
      List localList = localCmdtySortManager.getCmdtySorts(null);
      paramHttpServletRequest.setAttribute("cmdtySortList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询CmdtySort表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("list");
  }
  
  public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward editGroup(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editGroup' method");
    }
    CmdtySortForm localCmdtySortForm = (CmdtySortForm)paramActionForm;
    String str = localCmdtySortForm.getCrud();
    CmdtySortManager localCmdtySortManager = (CmdtySortManager)getBean("cmdtySortManager");
    CmdtySort localCmdtySort = null;
    try
    {
      getSelectAttribute(paramHttpServletRequest);
      if (!str.trim().equals("create")) {
        localCmdtySort = localCmdtySortManager.getGroupSortHoldById(Long.valueOf(localCmdtySortForm.getGroupID()), Long.valueOf(localCmdtySortForm.getSortID()));
      } else {
        localCmdtySort = new CmdtySort();
      }
      localCmdtySortForm = (CmdtySortForm)convert(localCmdtySort);
      localCmdtySortForm.setCrud(str);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localCmdtySortForm);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return searchGroup(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    return paramActionMapping.findForward("editGroup");
  }
  
  public ActionForward saveGroup(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveGroup' method");
    }
    CmdtySortForm localCmdtySortForm = (CmdtySortForm)paramActionForm;
    String str = localCmdtySortForm.getCrud();
    CmdtySortManager localCmdtySortManager = (CmdtySortManager)getBean("cmdtySortManager");
    CmdtySort localCmdtySort = (CmdtySort)convert(localCmdtySortForm);
    try
    {
      if (str.trim().equals("create"))
      {
        localCmdtySortManager.insertGroupSortHold(localCmdtySort);
        addSysLog(paramHttpServletRequest, "增加组分类持仓[" + localCmdtySort.getGroupID() + "," + localCmdtySort.getSortID() + "]");
        paramHttpServletRequest.setAttribute("prompt", "操作成功！");
      }
      else if (str.trim().equals("update"))
      {
        localCmdtySortManager.updateGroupSortHold(localCmdtySort);
        addSysLog(paramHttpServletRequest, "修改组分类持仓[" + localCmdtySort.getGroupID() + "," + localCmdtySort.getSortID() + "]");
        paramHttpServletRequest.setAttribute("prompt", "操作成功！");
      }
    }
    catch (Exception localException)
    {
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      getSelectAttribute(paramHttpServletRequest);
      return paramActionMapping.findForward("editGroup");
    }
    return searchGroup(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward deleteGroup(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deleteGroup' method");
    }
    CmdtySortManager localCmdtySortManager = (CmdtySortManager)getBean("cmdtySortManager");
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
    int i = 0;
    if (arrayOfString1 != null)
    {
      this.log.debug("==ids.length:" + arrayOfString1.length);
      String str = "";
      for (int j = 0; j < arrayOfString1.length; j++)
      {
        String[] arrayOfString2 = arrayOfString1[j].split(":");
        Long localLong1 = new Long(arrayOfString2[0]);
        Long localLong2 = new Long(arrayOfString2[1]);
        try
        {
          localCmdtySortManager.deleteGroupSortHoldById(localLong1, localLong2);
          addSysLog(paramHttpServletRequest, "删除组分类持仓[" + localLong1 + "," + localLong2 + "]");
          i++;
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str = str + arrayOfString1[j] + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + arrayOfString1[j] + "]与其他数据关联，删除失败！");
        }
      }
      if (!str.equals(""))
      {
        str = str.substring(0, str.length() - 1);
        str = str + "与其他数据关联，不能删除！";
      }
      str = str + "成功删除" + i + "条纪录！";
      paramHttpServletRequest.setAttribute("prompt", str);
    }
    return searchGroup(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward searchGroup(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchGroup' method");
    }
    CmdtySortManager localCmdtySortManager = (CmdtySortManager)getBean("cmdtySortManager");
    try
    {
      List localList = localCmdtySortManager.getGroupSortHolds(null);
      paramHttpServletRequest.setAttribute("groupSortHoldList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询GroupSortHold表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listGroup");
  }
  
  public ActionForward editCustomer(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editCustomer' method");
    }
    CmdtySortForm localCmdtySortForm = (CmdtySortForm)paramActionForm;
    String str = localCmdtySortForm.getCrud();
    CmdtySortManager localCmdtySortManager = (CmdtySortManager)getBean("cmdtySortManager");
    CmdtySort localCmdtySort = null;
    try
    {
      LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
      paramHttpServletRequest.setAttribute("cmdtySortSelect", localLookupManager.getSelectLabelValueByTable("CmdtySort", "SortName", "SortID", " order by SortID", (short)1));
      if (!str.trim().equals("create")) {
        localCmdtySort = localCmdtySortManager.getCustomerSortHoldById(localCmdtySortForm.getCustomerID(), Long.valueOf(localCmdtySortForm.getSortID()));
      } else {
        localCmdtySort = new CmdtySort();
      }
      localCmdtySortForm = (CmdtySortForm)convert(localCmdtySort);
      localCmdtySortForm.setCrud(str);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localCmdtySortForm);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return searchCustomer(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    return paramActionMapping.findForward("editCustomer");
  }
  
  public ActionForward saveCustomer(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveCustomer' method");
    }
    CmdtySortForm localCmdtySortForm = (CmdtySortForm)paramActionForm;
    String str = localCmdtySortForm.getCrud();
    CmdtySortManager localCmdtySortManager = (CmdtySortManager)getBean("cmdtySortManager");
    CmdtySort localCmdtySort = (CmdtySort)convert(localCmdtySortForm);
    try
    {
      if (str.trim().equals("create"))
      {
        localCmdtySortManager.insertCustomerSortHold(localCmdtySort);
        addSysLog(paramHttpServletRequest, "增加客户分类持仓[" + localCmdtySort.getCustomerID() + "," + localCmdtySort.getSortID() + "]");
        paramHttpServletRequest.setAttribute("prompt", "操作成功！");
      }
      else if (str.trim().equals("update"))
      {
        localCmdtySortManager.updateCustomerSortHold(localCmdtySort);
        addSysLog(paramHttpServletRequest, "修改客户分类持仓[" + localCmdtySort.getCustomerID() + "," + localCmdtySort.getSortID() + "]");
        paramHttpServletRequest.setAttribute("prompt", "操作成功！");
      }
    }
    catch (Exception localException)
    {
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
      paramHttpServletRequest.setAttribute("cmdtySortSelect", localLookupManager.getSelectLabelValueByTable("CmdtySort", "SortName", "SortID", " order by SortID", (short)1));
      return paramActionMapping.findForward("editCustomer");
    }
    return searchCustomer(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward deleteCustomer(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deleteCustomer' method");
    }
    CmdtySortManager localCmdtySortManager = (CmdtySortManager)getBean("cmdtySortManager");
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
    int i = 0;
    if (arrayOfString1 != null)
    {
      this.log.debug("==ids.length:" + arrayOfString1.length);
      String str2 = "";
      for (int j = 0; j < arrayOfString1.length; j++)
      {
        String[] arrayOfString2 = arrayOfString1[j].split(":");
        String str1 = arrayOfString2[0];
        Long localLong = new Long(arrayOfString2[1]);
        try
        {
          localCmdtySortManager.deleteCustomerSortHoldById(str1, localLong);
          addSysLog(paramHttpServletRequest, "删除客户分类持仓[" + str1 + "," + localLong + "]");
          i++;
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str2 = str2 + arrayOfString1[j] + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + arrayOfString1[j] + "]与其他数据关联，删除失败！");
        }
      }
      if (!str2.equals(""))
      {
        str2 = str2.substring(0, str2.length() - 1);
        str2 = str2 + "与其他数据关联，不能删除！";
      }
      str2 = str2 + "成功删除" + i + "条纪录！";
      paramHttpServletRequest.setAttribute("prompt", str2);
    }
    return searchCustomer(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward searchCustomer(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchCustomer' method");
    }
    CmdtySortManager localCmdtySortManager = (CmdtySortManager)getBean("cmdtySortManager");
    try
    {
      List localList = localCmdtySortManager.getCustomerSortHolds(null);
      paramHttpServletRequest.setAttribute("customerSortHoldList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询CustomerSortHold表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listCustomer");
  }
  
  private void getSelectAttribute(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
    paramHttpServletRequest.setAttribute("cmdtySortSelect", localLookupManager.getSelectLabelValueByTable("T_A_CMDTYSORT", "SortName", "SortID", " order by SortID", (short)1));
    paramHttpServletRequest.setAttribute("customerGroupSelect", localLookupManager.getSelectLabelValueByTable("CustomerGroup", "GroupName", "GroupID", " order by GroupID", (short)1));
  }
}
