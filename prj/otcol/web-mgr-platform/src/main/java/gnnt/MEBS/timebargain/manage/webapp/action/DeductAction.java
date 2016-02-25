package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.timebargain.manage.model.Deduct;
import gnnt.MEBS.timebargain.manage.service.DeductManager;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.util.Arith;
import gnnt.MEBS.timebargain.manage.webapp.form.DeductForm;
import gnnt.MEBS.timebargain.server.model.Order;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeductAction
  extends BaseAction
{
  public ActionForward deductPositionList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deductPositionList' method");
    }
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    Deduct localDeduct = new Deduct();
    try
    {
      String str = paramHttpServletRequest.getParameter("deductDate");
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date localDate = null;
      if ((str != null) && (!"".equals(str))) {
        localDate = localSimpleDateFormat.parse(str);
      }
      localDeduct.setDeductDate(localDate);
      List localList = localDeductManager.getDeductPositionList(localDeduct);
      paramHttpServletRequest.setAttribute("deductPositionList", localList);
      paramHttpServletRequest.setAttribute("UPDOWNFLAG", CommonDictionary.UPDOWNFLAG);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("deductPositionList");
  }
  
  public ActionForward editUpdate(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editUpdate' method");
    }
    DeductForm localDeductForm = (DeductForm)paramActionForm;
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    String str1 = paramHttpServletRequest.getParameter("crud");
    Deduct localDeduct = new Deduct();
    try
    {
      if ("update".equals(str1))
      {
        String str2 = paramHttpServletRequest.getParameter("date");
        String str3 = paramHttpServletRequest.getParameter("code");
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date localDate = null;
        if ((str2 != null) && (!"".equals(str2))) {
          localDate = localSimpleDateFormat.parse(str2);
        }
        localDeduct.setDeductDate(localDate);
        localDeduct.setUni_Cmdty_Code(str3);
        localDeduct = localDeductManager.getDeductPosition(localDeduct);
      }
      else if ("create".equals(str1))
      {
        localDeduct = new Deduct();
      }
      localDeductForm.setCrud(str1);
      BeanUtils.copyProperties(localDeductForm, localDeduct);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localDeductForm);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("editUpdate");
  }
  
  public ActionForward saveUpdate(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveUpdate' method");
    }
    DeductForm localDeductForm = (DeductForm)paramActionForm;
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    String str1 = paramHttpServletRequest.getParameter("crud");
    Deduct localDeduct1 = new Deduct();
    BeanUtils.copyProperties(localDeduct1, localDeductForm);
    String str2 = localDeduct1.getCommodityID();
    localDeduct1.setCommodityID(str2);
    localDeduct1.setLossRate(Double.valueOf(Arith.div(localDeduct1.getLossRate().doubleValue(), 100.0F)));
    localDeduct1.setProfitLvl1(Double.valueOf(Arith.div(localDeduct1.getProfitLvl1().doubleValue(), 100.0F)));
    localDeduct1.setProfitLvl2(Double.valueOf(Arith.div(localDeduct1.getProfitLvl2().doubleValue(), 100.0F)));
    localDeduct1.setProfitLvl3(Double.valueOf(Arith.div(localDeduct1.getProfitLvl3().doubleValue(), 100.0F)));
    localDeduct1.setProfitLvl4(Double.valueOf(Arith.div(localDeduct1.getProfitLvl4().doubleValue(), 100.0F)));
    localDeduct1.setProfitLvl5(Double.valueOf(Arith.div(localDeduct1.getProfitLvl5().doubleValue(), 100.0F)));
    Deduct localDeduct2 = null;
    try
    {
      if ("update".equals(str1))
      {
        localDeduct2 = localDeductManager.updateDeductPosition(localDeduct1);
        paramHttpServletRequest.setAttribute("prompt", "修改成功！");
        addSysLog(paramHttpServletRequest, "修改强减参数记录");
        if (localDeduct2 != null)
        {
          paramHttpServletRequest.setAttribute("id", localDeduct2.getDeductID().toString());
          SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
          String str3 = "";
          if (localDeduct2.getDeductDate() != null) {
            str3 = localSimpleDateFormat.format(localDeduct2.getDeductDate());
          }
          paramHttpServletRequest.setAttribute("date", str3);
          paramHttpServletRequest.setAttribute("code", localDeduct2.getCommodityID());
        }
      }
      else if ("create".equals(str1))
      {
        localDeduct2 = localDeductManager.insertDeductPosition(localDeduct1);
        paramHttpServletRequest.setAttribute("prompt", "添加成功！");
        addSysLog(paramHttpServletRequest, "添加强减参数记录");
        if (localDeduct2 != null)
        {
          paramHttpServletRequest.setAttribute("id", localDeduct2.getDeductID().toString());
          paramHttpServletRequest.setAttribute("date", localDeduct2.getDeductDate().toString().split(" ")[0]);
          paramHttpServletRequest.setAttribute("code", localDeduct2.getCommodityID());
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("savePosition");
  }
  
  public ActionForward deductDetailList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deductDetailList' method");
    }
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    Deduct localDeduct = new Deduct();
    try
    {
      String str = paramHttpServletRequest.getParameter("deductDate");
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date localDate = null;
      if ((str != null) && (!"".equals(str))) {
        localDate = localSimpleDateFormat.parse(str);
      }
      localDeduct.setDeductDate(localDate);
      List localList = localDeductManager.getDeductDetailList(localDeduct);
      paramHttpServletRequest.setAttribute("deductDetailList", localList);
      paramHttpServletRequest.setAttribute("BS_FLAG", CommonDictionary.BS_FLAG);
      paramHttpServletRequest.setAttribute("WL", CommonDictionary.WL);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("deductDetailList");
  }
  
  public ActionForward deductKeepFirmList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deductKeepFirmList' method");
    }
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    String str1 = (String)paramHttpServletRequest.getAttribute("save");
    Deduct localDeduct = new Deduct();
    String str2 = "";
    String str3 = "";
    String str4 = "";
    try
    {
      if ("save".equals(str1))
      {
        str2 = (String)paramHttpServletRequest.getAttribute("date");
        str3 = (String)paramHttpServletRequest.getAttribute("code");
        str4 = (String)paramHttpServletRequest.getAttribute("id");
      }
      else
      {
        str2 = paramHttpServletRequest.getParameter("date");
        str3 = paramHttpServletRequest.getParameter("code");
        str4 = paramHttpServletRequest.getParameter("id");
      }
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date localDate = null;
      if ((str2 != null) && (!"".equals(str2))) {
        localDate = localSimpleDateFormat.parse(str2);
      }
      localDeduct.setDeductDate(localDate);
      localDeduct.setUni_Cmdty_Code(str3);
      Long localLong = null;
      if ((str4 != null) && (!"".equals(str4))) {
        localLong = Long.valueOf(Long.parseLong(str4));
      }
      localDeduct.setDeductID(localLong);
      List localList = localDeductManager.getDeductKeepFirmList(localDeduct);
      paramHttpServletRequest.setAttribute("deductKeepFirmList", localList);
      paramHttpServletRequest.setAttribute("BS_FLAG", CommonDictionary.BS_FLAG);
      paramHttpServletRequest.setAttribute("WL", CommonDictionary.WL);
      paramHttpServletRequest.setAttribute("id", str4);
      paramHttpServletRequest.setAttribute("code", str3);
      paramHttpServletRequest.setAttribute("date", str2);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("deductKeepFirmList");
  }
  
  public ActionForward editDeductKeepFirm(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editDeductKeepFirm' method");
    }
    DeductForm localDeductForm = (DeductForm)paramActionForm;
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    String str1 = paramHttpServletRequest.getParameter("crud");
    Deduct localDeduct = new Deduct();
    try
    {
      if ("update".equals(str1))
      {
        String str2 = paramHttpServletRequest.getParameter("date");
        String str3 = paramHttpServletRequest.getParameter("code");
        String str4 = paramHttpServletRequest.getParameter("customerID");
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date localDate = null;
        if ((str2 != null) && (!"".equals(str2))) {
          localDate = localSimpleDateFormat.parse(str2);
        }
        localDeduct.setDeductDate(localDate);
        localDeduct.setUni_Cmdty_Code(str3);
        localDeduct.setCustomerID(str4);
        localDeduct = localDeductManager.getDeductKeepFirm(localDeduct);
      }
      else if ("create".equals(str1))
      {
        localDeduct = new Deduct();
      }
      BeanUtils.copyProperties(localDeductForm, localDeduct);
      localDeductForm.setCrud(str1);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localDeductForm);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("editDeductKeepFirm");
  }
  
  public ActionForward saveKeepFirmUpdate(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveKeepFirmUpdate' method");
    }
    DeductForm localDeductForm = (DeductForm)paramActionForm;
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    String str1 = paramHttpServletRequest.getParameter("crud");
    String str2 = paramHttpServletRequest.getParameter("deductDate");
    String str3 = paramHttpServletRequest.getParameter("commodityID");
    String str4 = str3;
    String str5 = paramHttpServletRequest.getParameter("deductID");
    paramHttpServletRequest.setAttribute("id", str5);
    paramHttpServletRequest.setAttribute("date", str2);
    paramHttpServletRequest.setAttribute("code", str3);
    paramHttpServletRequest.setAttribute("save", "save");
    Deduct localDeduct = new Deduct();
    BeanUtils.copyProperties(localDeduct, localDeductForm);
    try
    {
      localDeduct.setCommodityID(str4);
      localDeductManager.insertDeductKeepFirm(localDeduct);
      paramHttpServletRequest.setAttribute("prompt", "添加成功！");
      addSysLog(paramHttpServletRequest, "添加强减保留交易商记录");
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return deductKeepFirmList(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward deleteDeductKeepFirm(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deleteDeductKeepFirm' method");
    }
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
    String str1 = paramHttpServletRequest.getParameter("commodityID");
    String str2 = paramHttpServletRequest.getParameter("deductDate");
    String str3 = str1;
    Deduct localDeduct = new Deduct();
    int i = 0;
    if (arrayOfString1 != null)
    {
      this.log.debug("==ids.length:" + arrayOfString1.length);
      String str5 = "";
      for (int j = 0; j < arrayOfString1.length; j++)
      {
        String str4 = arrayOfString1[j];
        try
        {
          String[] arrayOfString2 = str4.split(",");
          Long localLong = null;
          if ((arrayOfString2[0] != null) && (!"".equals(arrayOfString2[0]))) {
            localLong = Long.valueOf(Long.parseLong(arrayOfString2[0]));
          }
          localDeduct.setDeductID(localLong);
          localDeduct.setCustomerID(arrayOfString2[1]);
          Short localShort = null;
          if ((arrayOfString2[2] != null) && (!"".equals(arrayOfString2[2]))) {
            localShort = Short.valueOf(Short.parseShort(arrayOfString2[2]));
          }
          localDeduct.setBS_Flag(localShort);
          paramHttpServletRequest.setAttribute("save", "save");
          paramHttpServletRequest.setAttribute("id", arrayOfString2[0]);
          localDeductManager.deleteKeepFirm(localDeduct);
          addSysLog(paramHttpServletRequest, "删除保留交易商[" + str4 + "]");
          i++;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          str5 = str5 + str4 + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + str4 + "]与其他数据关联，删除失败！");
        }
      }
      if (!str5.equals(""))
      {
        str5 = str5.substring(0, str5.length() - 1);
        str5 = str5 + "与其他数据关联，不能删除！";
      }
      str5 = str5 + "成功删除" + i + "条纪录！";
      paramHttpServletRequest.setAttribute("prompt", str5);
      paramHttpServletRequest.setAttribute("date", str2);
      paramHttpServletRequest.setAttribute("code", str1);
    }
    return deductKeepFirmList(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward editDeductGuide(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editDeductGuide' method");
    }
    DeductForm localDeductForm = (DeductForm)paramActionForm;
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    String str1 = paramHttpServletRequest.getParameter("deductID");
    Deduct localDeduct1 = new Deduct();
    Deduct localDeduct2 = new Deduct();
    try
    {
      if ((str1 != null) && (!"".equals(str1)))
      {
        localDeduct1.setDeductID(Long.valueOf(Long.parseLong(str1)));
        localDeduct2 = localDeductManager.getDeductPosition(localDeduct1);
        localDeduct2.setCrud("update");
        localDeduct2.setDeductID(Long.valueOf(Long.parseLong(str1)));
        localDeduct2.setLossRate(Double.valueOf(Arith.mul(localDeduct2.getLossRate().doubleValue(), 100.0F)));
        localDeduct2.setProfitLvl1(Double.valueOf(Arith.mul(localDeduct2.getProfitLvl1().doubleValue(), 100.0F)));
        localDeduct2.setProfitLvl2(Double.valueOf(Arith.mul(localDeduct2.getProfitLvl2().doubleValue(), 100.0F)));
        localDeduct2.setProfitLvl3(Double.valueOf(Arith.mul(localDeduct2.getProfitLvl3().doubleValue(), 100.0F)));
        localDeduct2.setProfitLvl4(Double.valueOf(Arith.mul(localDeduct2.getProfitLvl4().doubleValue(), 100.0F)));
        localDeduct2.setProfitLvl5(Double.valueOf(Arith.mul(localDeduct2.getProfitLvl5().doubleValue(), 100.0F)));
        BeanUtils.copyProperties(localDeductForm, localDeduct2);
        updateFormBean(paramActionMapping, paramHttpServletRequest, localDeductForm);
      }
      else
      {
        localDeduct2.setCrud("create");
        BeanUtils.copyProperties(localDeductForm, localDeduct2);
        updateFormBean(paramActionMapping, paramHttpServletRequest, localDeductForm);
      }
      String str2 = "";
      String str3 = "";
      if (localDeduct2.getSelfCounteract() != null)
      {
        if ("0".equals(localDeduct2.getSelfCounteract().toString())) {
          str2 = "0";
        }
        if ("1".equals(localDeduct2.getSelfCounteract().toString())) {
          str2 = "1";
        }
        if ("2".equals(localDeduct2.getSelfCounteract().toString())) {
          str2 = "2";
        }
      }
      if (localDeduct2.getLoserMode() != null)
      {
        if ("1".equals(localDeduct2.getLoserMode().toString())) {
          str3 = "1";
        }
        if ("2".equals(localDeduct2.getLoserMode().toString())) {
          str3 = "2";
        }
      }
      paramHttpServletRequest.setAttribute("type3", str2);
      paramHttpServletRequest.setAttribute("typeMode", str3);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("editUpdate");
  }
  
  public ActionForward nextDeductDetail(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'nextDeductDetail' method");
    }
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    Deduct localDeduct = new Deduct();
    String str1 = (String)paramHttpServletRequest.getAttribute("save");
    String str2 = "";
    String str3 = "";
    String str4 = "";
    try
    {
      if ("save".equals(str1))
      {
        str2 = (String)paramHttpServletRequest.getAttribute("code");
        str3 = (String)paramHttpServletRequest.getAttribute("date");
        str4 = (String)paramHttpServletRequest.getAttribute("id");
      }
      else
      {
        str2 = paramHttpServletRequest.getParameter("code");
        str3 = paramHttpServletRequest.getParameter("date");
        str4 = paramHttpServletRequest.getParameter("id");
      }
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date localDate = null;
      if ((str3 != null) && (!"".equals(str3))) {
        localDate = localSimpleDateFormat.parse(str3);
      }
      localDeduct.setDeductDate(localDate);
      localDeduct.setCommodityID(str2);
      Long localLong = null;
      if ((str4 != null) && (!"".equals(str4))) {
        localLong = Long.valueOf(Long.parseLong(str4));
      }
      localDeduct.setDeductID(localLong);
      List localList = localDeductManager.getDeductDetailList(localDeduct);
      paramHttpServletRequest.setAttribute("deductDetailList", localList);
      paramHttpServletRequest.setAttribute("BS_FLAG", CommonDictionary.BS_FLAG);
      paramHttpServletRequest.setAttribute("WL", CommonDictionary.WL);
      paramHttpServletRequest.setAttribute("code", str2);
      paramHttpServletRequest.setAttribute("date", str3);
      paramHttpServletRequest.setAttribute("id", str4);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("nextDeductDetail");
  }
  
  public ActionForward nextDeductDetailQuery(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'nextDeductDetailQuery' method");
    }
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    Deduct localDeduct = new Deduct();
    String str1 = paramHttpServletRequest.getParameter("code");
    String str2 = paramHttpServletRequest.getParameter("date");
    String str3 = paramHttpServletRequest.getParameter("id");
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date localDate = null;
      if ((str2 != null) && (!"".equals(str2))) {
        localDate = localSimpleDateFormat.parse(str2);
      }
      localDeduct.setDeductDate(localDate);
      localDeduct.setCommodityID(str1);
      Long localLong1 = null;
      if ((str3 != null) && (!"".equals(str3))) {
        localLong1 = Long.valueOf(Long.parseLong(str3));
      }
      localDeduct.setDeductID(localLong1);
      int i = localDeductManager.operateDeductData(localDeduct);
      if (i == 1)
      {
        Long localLong2 = localDeductManager.getDeductQty(localDeduct);
        if (localLong2 == null) {
          paramHttpServletRequest.setAttribute("deductQty", "0");
        } else {
          paramHttpServletRequest.setAttribute("deductQty", localLong2.toString());
        }
        paramHttpServletRequest.setAttribute("code", str1);
        paramHttpServletRequest.setAttribute("date", str2);
        paramHttpServletRequest.setAttribute("id", str3);
        paramHttpServletRequest.setAttribute("prompt", "生成强减数据成功！");
      }
      else if (i == -1)
      {
        paramHttpServletRequest.setAttribute("code", str1);
        paramHttpServletRequest.setAttribute("date", str2);
        paramHttpServletRequest.setAttribute("id", str3);
        paramHttpServletRequest.setAttribute("prompt", "只能闭市操作状态强减！");
      }
      else if (i == -2)
      {
        paramHttpServletRequest.setAttribute("code", str1);
        paramHttpServletRequest.setAttribute("date", str2);
        paramHttpServletRequest.setAttribute("id", str3);
        paramHttpServletRequest.setAttribute("prompt", "没有符合条件的盈利方！");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("code", str1);
      paramHttpServletRequest.setAttribute("date", str2);
      paramHttpServletRequest.setAttribute("id", str3);
      paramHttpServletRequest.setAttribute("prompt", "生成明细失败！");
    }
    return paramActionMapping.findForward("nextDeductDetailQuery");
  }
  
  public ActionForward deductGo(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deductGo' method");
    }
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    Deduct localDeduct = new Deduct();
    try
    {
      String str1 = paramHttpServletRequest.getParameter("id");
      String str2 = paramHttpServletRequest.getParameter("code");
      String str3 = paramHttpServletRequest.getParameter("date");
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date localDate = null;
      if ((str3 != null) && (!"".equals(str3))) {
        localDate = localSimpleDateFormat.parse(str3);
      }
      localDeduct.setDeductDate(localDate);
      localDeduct.setCommodityID(str2);
      Long localLong = null;
      if ((str1 != null) && (!"".equals(str1))) {
        localLong = Long.valueOf(Long.parseLong(str1));
      }
      localDeduct.setDeductID(localLong);
      int i = localDeductManager.operateDeduct(localDeduct);
      if (i != 1) {
        if (i == -1) {
          paramHttpServletRequest.setAttribute("prompt", "应在闭市之前，结算之后做强减！");
        } else {
          paramHttpServletRequest.setAttribute("prompt", "强减日期应为当前日期！");
        }
      }
      paramHttpServletRequest.setAttribute("save", "save");
      paramHttpServletRequest.setAttribute("code", str2);
      paramHttpServletRequest.setAttribute("date", str3);
      paramHttpServletRequest.setAttribute("id", str1);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("deductGo");
  }
  
  public ActionForward deductPositionListQuery(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deductPositionListQuery' method");
    }
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    Deduct localDeduct = new Deduct();
    try
    {
      String str = paramHttpServletRequest.getParameter("deductDate");
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date localDate = null;
      if ((str != null) && (!"".equals(str))) {
        localDate = localSimpleDateFormat.parse(str);
      }
      localDeduct.setDeductDate(localDate);
      List localList = localDeductManager.getDeductPositionList(localDeduct);
      paramHttpServletRequest.setAttribute("deductPositionListQuery", localList);
      paramHttpServletRequest.setAttribute("LOSERMODE", CommonDictionary.LOSERMODE);
      paramHttpServletRequest.setAttribute("DEDUCTSTATUS", CommonDictionary.DEDUCTSTATUS);
    }
    catch (ParseException localParseException)
    {
      paramHttpServletRequest.setAttribute("prompt", "日期格式有误");
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("deductPositionListQuery");
  }
  
  public ActionForward deductKeepFirmListQuery(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deductKeepFirmListQuery' method");
    }
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    Deduct localDeduct = new Deduct();
    try
    {
      String str = paramHttpServletRequest.getParameter("deductID");
      Long localLong = null;
      if ((str != null) && (!"".equals(str))) {
        localLong = Long.valueOf(Long.parseLong(str));
      }
      localDeduct.setDeductID(localLong);
      List localList = localDeductManager.getDeductKeepFirmListQuery(localDeduct);
      paramHttpServletRequest.setAttribute("deductKeepFirmListQuery", localList);
      paramHttpServletRequest.setAttribute("BS_FLAG", CommonDictionary.BS_FLAG);
      paramHttpServletRequest.setAttribute("WL", CommonDictionary.WL);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("deductKeepFirmListQuery");
  }
  
  public ActionForward nextDeductDetailQueryMenu(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'nextDeductDetailQueryMenu' method");
    }
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    Deduct localDeduct = new Deduct();
    try
    {
      String str = paramHttpServletRequest.getParameter("deductID");
      Long localLong = null;
      if ((str != null) && (!"".equals(str))) {
        localLong = Long.valueOf(Long.parseLong(str));
      }
      localDeduct.setDeductID(localLong);
      List localList = localDeductManager.getDeductDetailListQuery(localDeduct);
      paramHttpServletRequest.setAttribute("nextDeductDetailQueryMenu", localList);
      paramHttpServletRequest.setAttribute("WL", CommonDictionary.WL);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("nextDeductDetailQueryMenu");
  }
  
  public ActionForward deductPositionInfo(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deductPositionInfo' method");
    }
    DeductManager localDeductManager = (DeductManager)getBean("deductManager");
    Deduct localDeduct1 = new Deduct();
    try
    {
      String str1 = paramHttpServletRequest.getParameter("deductID");
      Long localLong = null;
      if ((str1 != null) && (!"".equals(str1))) {
        localLong = Long.valueOf(Long.parseLong(str1));
      }
      localDeduct1.setDeductID(localLong);
      Deduct localDeduct2 = localDeductManager.getDeductPosition(localDeduct1);
      paramHttpServletRequest.setAttribute("dd", localDeduct2);
      List localList = localDeductManager.getDeductDetailSum(localDeduct1);
      String str2 = null;
      String str3 = null;
      if ((localList != null) && (localList.size() > 0))
      {
        Map localMap = (Map)localList.get(0);
        if (localMap.get("deductedQty") != null) {
          str2 = localMap.get("deductedQty").toString();
        } else {
          str2 = "0";
        }
        if (localMap.get("counteractedQty") != null) {
          str3 = localMap.get("counteractedQty").toString();
        } else {
          str3 = "0";
        }
      }
      paramHttpServletRequest.setAttribute("deductedQty", str2);
      paramHttpServletRequest.setAttribute("counteractedQty", str3);
      paramHttpServletRequest.setAttribute("BS_FLAG", CommonDictionary.BS_FLAG);
      paramHttpServletRequest.setAttribute("WL", CommonDictionary.WL);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("deductPositionInfo");
  }
  
  public ActionForward saveDeductWrite(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveDeductWrite' method");
    }
    String str1 = paramHttpServletRequest.getParameter("customerID");
    String str2 = paramHttpServletRequest.getParameter("commodityID");
    String str3 = paramHttpServletRequest.getParameter("BS_Flag");
    String str4 = paramHttpServletRequest.getParameter("deductPrice");
    String str5 = paramHttpServletRequest.getParameter("keepQty");
    this.log.debug("customerID: " + str1);
    this.log.debug("commodityID: " + str2);
    this.log.debug("bs_flag: " + str3);
    this.log.debug("price: " + str4);
    this.log.debug("quantity: " + str5);
    Order localOrder = new Order();
    try
    {
      localOrder.setCustomerID(str1);
      localOrder.setCommodityID(str2);
      if ((str3 != null) && (!"".equals(str3))) {
        localOrder.setBuyOrSell(Short.valueOf(Short.parseShort(str3)));
      }
      if ((str4 != null) && (!"".equals(str4))) {
        localOrder.setPrice(Double.valueOf(Double.parseDouble(str4)));
      }
      if ((str5 != null) && (!"".equals(str5))) {
        localOrder.setQuantity(Long.valueOf(Long.parseLong(str5)));
      }
      AgencyRMIBean localAgencyRMIBean = new AgencyRMIBean(paramHttpServletRequest);
      int i = localAgencyRMIBean.deductCloseOrder(localOrder);
      this.log.debug("ret: " + i);
      if (i == 0) {
        addSysLog(paramHttpServletRequest, "录入强减委托信息" + str1 + "," + str2 + "," + str3 + "," + str4 + "," + str5);
      } else if (i == -1) {
        paramHttpServletRequest.setAttribute("prompt", "此交易客户不存在！");
      } else if (i == -2) {
        paramHttpServletRequest.setAttribute("prompt", "买卖标志错误！");
      } else if (i == -3) {
        paramHttpServletRequest.setAttribute("prompt", "此商品不存在！");
      } else if (i == -202) {
        paramHttpServletRequest.setAttribute("prompt", "不是闭市状态，不能录入强减委托！");
      }
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", "请输入正确的录入信息!");
    }
    return paramActionMapping.findForward("deductWrite");
  }
  
  private void getSelectAttribute(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
    paramHttpServletRequest.setAttribute("commoditySelect", localLookupManager.getSelectLabelValueByTable("T_COMMODITY", "commodityID", "commodityID", " order by commodityID "));
  }
}
