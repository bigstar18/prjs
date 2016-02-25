package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.timebargain.manage.model.DirectFirmBreed;
import gnnt.MEBS.timebargain.manage.service.BreedManager;
import gnnt.MEBS.timebargain.manage.service.CmdtySortManager;
import gnnt.MEBS.timebargain.manage.service.DirectFirmBreedManager;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.dao.DataIntegrityViolationException;

public class DirectFirmBreedAction
  extends BaseAction
{
  public ActionForward directFirmBreedForwardTop(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    BreedManager localBreedManager = (BreedManager)getBean("breedManager");
    gnnt.MEBS.timebargain.manage.util.QueryConditions localQueryConditions = new gnnt.MEBS.timebargain.manage.util.QueryConditions();
    localQueryConditions.addCondition("BreedTradeMode", ">=", Integer.valueOf(1));
    localQueryConditions.addCondition("BreedTradeMode", "<=", Integer.valueOf(4));
    List localList1 = localBreedManager.getBreeds(localQueryConditions);
    CmdtySortManager localCmdtySortManager = (CmdtySortManager)getBean("cmdtySortManager");
    List localList2 = localCmdtySortManager.getCmdtySorts(null);
    paramHttpServletRequest.setAttribute("sorts", localList2);
    paramHttpServletRequest.setAttribute("breedList", localList1);
    paramHttpServletRequest.setAttribute("type", "e");
    return paramActionMapping.findForward("directFirmBreedTop");
  }
  
  public ActionForward directFirmBreedList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    DirectFirmBreedManager localDirectFirmBreedManager = (DirectFirmBreedManager)getBean("directFirmBreedManager");
    String str1 = paramHttpServletRequest.getParameter("type");
    List localList = localDirectFirmBreedManager.directFirmBreedList(str1);
    String str2 = "";
    if ("h".equals(str1))
    {
      paramHttpServletRequest.setAttribute("directFirmBreedHisList", localList);
      str2 = "directFirmBreedHisList";
    }
    else if ("e".equals(str1))
    {
      paramHttpServletRequest.setAttribute("directFirmBreedList", localList);
      str2 = "directFirmBreedList";
    }
    return paramActionMapping.findForward(str2);
  }
  
  public ActionForward directFirmBreedGet(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str1 = paramHttpServletRequest.getParameter("type");
    DirectFirmBreedManager localDirectFirmBreedManager = (DirectFirmBreedManager)getBean("directFirmBreedManager");
    gnnt.MEBS.base.query.QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    List localList = localDirectFirmBreedManager.directFirmBreedGet(localQueryConditions, str1);
    String str2 = "";
    if ("e".equals(str1))
    {
      paramHttpServletRequest.setAttribute("directFirmBreedList", localList);
      str2 = "directFirmBreedList";
    }
    else if ("h".equals(str1))
    {
      paramHttpServletRequest.setAttribute("directFirmBreedHisList", localList);
      str2 = "directFirmBreedHisList";
    }
    System.out.println(str2);
    paramHttpServletRequest.setAttribute("type", str1);
    return paramActionMapping.findForward(str2);
  }
  
  public ActionForward directFirmBreedAdd(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str = "";
    DirectFirmBreed localDirectFirmBreed = new DirectFirmBreed();
    localDirectFirmBreed.setBreedId(Integer.parseInt(paramHttpServletRequest.getParameter("breedId")));
    localDirectFirmBreed.setFirmId(paramHttpServletRequest.getParameter("firmId"));
    DirectFirmBreedManager localDirectFirmBreedManager = (DirectFirmBreedManager)getBean("directFirmBreedManager");
    gnnt.MEBS.base.query.QueryConditions localQueryConditions1 = new gnnt.MEBS.base.query.QueryConditions();
    localQueryConditions1.addCondition("e.firmId", "=", localDirectFirmBreed.getFirmId());
    localQueryConditions1.addCondition("e.breedId", "=", Integer.valueOf(localDirectFirmBreed.getBreedId()));
    gnnt.MEBS.base.query.QueryConditions localQueryConditions2 = new gnnt.MEBS.base.query.QueryConditions();
    localQueryConditions2.addCondition("firmId", "=", localDirectFirmBreed.getFirmId());
    List localList1 = localDirectFirmBreedManager.getFirmList(localQueryConditions2);
    if ((localList1 != null) && (localList1.size() > 0))
    {
      List localList2 = localDirectFirmBreedManager.directFirmBreedGet(localQueryConditions1, "e");
      if ((localList2 != null) && (localList2.size() > 0))
      {
        str = "添加失败，已有此条记录!";
      }
      else
      {
        localDirectFirmBreedManager.directFirmBreedAdd(localDirectFirmBreed);
        str = "添加成功!";
      }
    }
    else
    {
      str = "无此交易商!";
    }
    paramHttpServletRequest.setAttribute("msg", str);
    return paramActionMapping.findForward("directFirmBreed");
  }
  
  public ActionForward directFirmBreedAddForward(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    BreedManager localBreedManager = (BreedManager)getBean("breedManager");
    gnnt.MEBS.timebargain.manage.util.QueryConditions localQueryConditions = new gnnt.MEBS.timebargain.manage.util.QueryConditions();
    localQueryConditions.addCondition("BreedTradeMode", ">=", Integer.valueOf(1));
    localQueryConditions.addCondition("BreedTradeMode", "<=", Integer.valueOf(4));
    List localList = localBreedManager.getBreeds(localQueryConditions);
    paramHttpServletRequest.setAttribute("breedList", localList);
    return paramActionMapping.findForward("directFirmBreedAdd");
  }
  
  public ActionForward breedListByBreedTradeMod(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    BreedManager localBreedManager = (BreedManager)getBean("breedManager");
    gnnt.MEBS.timebargain.manage.util.QueryConditions localQueryConditions = new gnnt.MEBS.timebargain.manage.util.QueryConditions();
    String str1 = paramHttpServletRequest.getParameter("breedTradeMode");
    if ((!"".equals(str1)) && (str1 != null))
    {
      int i = Integer.parseInt(str1);
      localQueryConditions.addCondition("BreedTradeMode", "=", Integer.valueOf(i));
    }
    else
    {
      localQueryConditions.addCondition("1", "=", Integer.valueOf(2));
    }
    List localList = localBreedManager.getBreeds(localQueryConditions);
    String str2 = "";
    if ((localList.size() > 0) && (localList != null))
    {
      str2 = "<select id='breedId' name='breedId'>";
      for (int j = 0; j < localList.size(); j++)
      {
        Map localMap = (Map)localList.get(j);
        str2 = str2 + "<option value='" + localMap.get("breedid") + "'>" + localMap.get("breedname") + "</option>";
      }
      str2 = str2 + "</select>";
    }
    else
    {
      str2 = "";
    }
    paramHttpServletResponse.setHeader("Cache-Control", "no-cache");
    paramHttpServletResponse.setHeader("Cache-Control", "no-store");
    paramHttpServletResponse.setHeader("Pragma", "no-cache");
    paramHttpServletResponse.setContentType("text/xml");
    paramHttpServletResponse.setCharacterEncoding("GBK");
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    localPrintWriter.flush();
    localPrintWriter.println(str2);
    localPrintWriter.close();
    return null;
  }
  
  public ActionForward directFirmBreedEditForward(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    BreedManager localBreedManager = (BreedManager)getBean("breedManager");
    DirectFirmBreedManager localDirectFirmBreedManager = (DirectFirmBreedManager)getBean("directFirmBreedManager");
    gnnt.MEBS.timebargain.manage.util.QueryConditions localQueryConditions = new gnnt.MEBS.timebargain.manage.util.QueryConditions();
    localQueryConditions.addCondition("BreedTradeMode", ">=", Integer.valueOf(1));
    localQueryConditions.addCondition("BreedTradeMode", "<=", Integer.valueOf(4));
    List localList1 = localBreedManager.getBreeds(localQueryConditions);
    gnnt.MEBS.base.query.QueryConditions localQueryConditions1 = new gnnt.MEBS.base.query.QueryConditions();
    localQueryConditions1.addCondition("breedId", "=", paramHttpServletRequest.getParameter("breedId"));
    localQueryConditions1.addCondition("firmId", "=", paramHttpServletRequest.getParameter("firmId"));
    List localList2 = localDirectFirmBreedManager.directFirmBreedGet(localQueryConditions1, "e");
    paramHttpServletRequest.setAttribute("directFirmBreed", localList2.get(0));
    paramHttpServletRequest.setAttribute("breedList", localList1);
    return paramActionMapping.findForward("directFirmBreedMod");
  }
  
  public ActionForward directFirmBreedMod(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str = "";
    DirectFirmBreed localDirectFirmBreed1 = new DirectFirmBreed();
    DirectFirmBreed localDirectFirmBreed2 = new DirectFirmBreed();
    localDirectFirmBreed1.setBreedId(Integer.parseInt(paramHttpServletRequest.getParameter("breedId")));
    localDirectFirmBreed1.setFirmId(paramHttpServletRequest.getParameter("firmId"));
    DirectFirmBreedManager localDirectFirmBreedManager = (DirectFirmBreedManager)getBean("directFirmBreedManager");
    gnnt.MEBS.base.query.QueryConditions localQueryConditions = new gnnt.MEBS.base.query.QueryConditions();
    localQueryConditions.addCondition("firmId", "=", localDirectFirmBreed1.getFirmId());
    localQueryConditions.addCondition("breedId", "=", Integer.valueOf(localDirectFirmBreed1.getBreedId()));
    List localList = localDirectFirmBreedManager.directFirmBreedGet(localQueryConditions, "e");
    if ((localList != null) && (localList.size() > 0))
    {
      str = "修改失败，已有此条记录!";
    }
    else
    {
      localDirectFirmBreedManager.directFirmBreedMod(localDirectFirmBreed1, localDirectFirmBreed2);
      str = "修改成功!";
    }
    paramHttpServletRequest.setAttribute("msg", str);
    return paramActionMapping.findForward("directFirmBreed");
  }
  
  public ActionForward directFirmBreedDelete(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    DirectFirmBreedManager localDirectFirmBreedManager = (DirectFirmBreedManager)getBean("directFirmBreedManager");
    DirectFirmBreed localDirectFirmBreed = new DirectFirmBreed();
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
    int i = 0;
    if (arrayOfString1 != null)
    {
      this.log.debug("==ids.length:" + arrayOfString1.length);
      String str2 = "";
      for (int j = 0; j < arrayOfString1.length; j++)
      {
        String str1 = arrayOfString1[j];
        String[] arrayOfString2 = str1.split(",");
        localDirectFirmBreed.setBreedId(Integer.parseInt(arrayOfString2[1]));
        localDirectFirmBreed.setFirmId(arrayOfString2[0]);
        try
        {
          localDirectFirmBreedManager.directFirmBreedDelete(localDirectFirmBreed);
          i++;
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str2 = str2 + str1 + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + str1 + "]与其他数据关联，删除失败！");
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
    return directFirmBreedList(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
}
