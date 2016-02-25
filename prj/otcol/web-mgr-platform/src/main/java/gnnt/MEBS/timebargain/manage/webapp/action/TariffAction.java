package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.timebargain.manage.model.Tariff;
import gnnt.MEBS.timebargain.manage.service.CommodityManager;
import gnnt.MEBS.timebargain.manage.service.TariffManager;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.dao.DataIntegrityViolationException;

public class TariffAction
  extends BaseAction
{
  public ActionForward getTariffPage(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    TariffManager localTariffManager = (TariffManager)getBean("tariffManager");
    paramHttpServletRequest.setAttribute("tariffList", localTariffManager.getTariffPage());
    return paramActionMapping.findForward("tariffList");
  }
  
  public ActionForward getTariffList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    TariffManager localTariffManager = (TariffManager)getBean("tariffManager");
    paramHttpServletRequest.setAttribute("tariffList", localTariffManager.getTariffList());
    paramHttpServletRequest.setAttribute("allList", localTariffManager.getTariffPage());
    return paramActionMapping.findForward("tariffList");
  }
  
  public ActionForward getTariffListQuery(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str1 = paramHttpServletRequest.getParameter("tariffID");
    String str2 = paramHttpServletRequest.getParameter("status");
    TariffManager localTariffManager = (TariffManager)getBean("tariffManager");
    paramHttpServletRequest.setAttribute("tariffList", localTariffManager.getTariffListQuery(str1, str2));
    paramHttpServletRequest.setAttribute("oldStatus", str2);
    paramHttpServletRequest.setAttribute("oldtariffID", str1);
    paramHttpServletRequest.setAttribute("allList", localTariffManager.getTariffPage());
    return paramActionMapping.findForward("tariffList");
  }
  
  public ActionForward getCommodityTariffList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    TariffManager localTariffManager = (TariffManager)getBean("tariffManager");
    String str = paramHttpServletRequest.getParameter("tariffID");
    List localList = null;
    try
    {
      localList = localTariffManager.getTariffById(str);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    paramHttpServletRequest.setAttribute("tariffList", localList);
    paramHttpServletRequest.setAttribute("tariffID", str);
    paramHttpServletRequest.setAttribute("tariffName", "加收" + str.substring(1) + "%");
    paramHttpServletRequest.setAttribute("ocrr", paramHttpServletRequest.getParameter("isFirm"));
    return paramActionMapping.findForward("commodityTariffList");
  }
  
  public ActionForward addTariff(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    TariffManager localTariffManager = (TariffManager)getBean("tariffManager");
    CommodityManager localCommodityManager = (CommodityManager)getBean("commodityManager");
    String str1 = "";
    try
    {
      Object localObject = new ArrayList();
      localObject = localCommodityManager.getCommodityList();
      ArrayList localArrayList = new ArrayList();
      for (int i = 0; i < ((List)localObject).size(); i++) {
        localArrayList.add(localCommodityManager.getCommodityById((String)((Map)((List)localObject).get(i)).get("commodityid")));
      }
      String str2 = paramHttpServletRequest.getParameter("tariffID");
      Tariff localTariff = new Tariff();
      localTariff.setCommodityList(localArrayList);
      localTariff.setTariffID(str2);
      localTariff.setTariffName("加收" + str2.substring(1) + "%");
      localTariff.setTariffRate(Double.parseDouble(str2.substring(1)) / new Double(100.0D).doubleValue() + 1.0D);
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String str3 = localSimpleDateFormat.format(new Date());
      localTariff.setCreateTime(str3);
      localTariff.setModifyTime(str3);
      localTariff.setCreateUser(AclCtrl.getLogonID(paramHttpServletRequest));
      localTariff.setModifyUser(AclCtrl.getLogonID(paramHttpServletRequest));
      str1 = localTariffManager.insertTariff(localTariff);
      addSysLog(paramHttpServletRequest, "添加手续费套餐：[" + str2 + "]");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str1 = "操作异常";
    }
    paramHttpServletRequest.setAttribute("prompt", str1);
    return getTariffList(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward deleteTariff(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    TariffManager localTariffManager = (TariffManager)getBean("tariffManager");
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
          localTariffManager.deleteTariffById(str1);
          addSysLog(paramHttpServletRequest, "删除手续费套餐[" + str1 + "]");
          i++;
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str2 = str2 + str1 + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + str1 + "]与其他数据关联，删除失败！");
        }
        catch (RuntimeException localRuntimeException)
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
    return getTariffList(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward editTariff(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    TariffManager localTariffManager = (TariffManager)getBean("tariffManager");
    try
    {
      String str1 = paramHttpServletRequest.getParameter("tariffID");
      String str2 = paramHttpServletRequest.getParameter("commodityID");
      paramHttpServletRequest.setAttribute("commodityTariffMap", localTariffManager.getTariffByCommodityId(str1, str2));
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramActionMapping.findForward("tariff_view");
  }
  
  public ActionForward saveCommodityTariff(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    TariffManager localTariffManager = (TariffManager)getBean("tariffManager");
    String str1 = "";
    try
    {
      Tariff localTariff = new Tariff();
      String str2 = paramHttpServletRequest.getParameter("tariffID");
      String str3 = paramHttpServletRequest.getParameter("commodityID");
      Map localMap = localTariffManager.getTariffByCommodityId(str2, str3);
      localTariff.setTariffID(str2);
      localTariff.setCommodityID(str3);
      localTariff.setTariffName((String)localMap.get("TARIFFNAME"));
      localTariff.setTariffRate(((BigDecimal)localMap.get("TARIFFRATE")).doubleValue());
      localTariff.setFeeAlgr(Short.parseShort(paramHttpServletRequest.getParameter("feeAlgr")));
      int i;
      if ("1".equals(paramHttpServletRequest.getParameter("feeAlgr"))) {
        i = 100;
      } else {
        i = 1;
      }
      localTariff.setFeeRate_B(Double.parseDouble(paramHttpServletRequest.getParameter("feeRate_B")) / i);
      localTariff.setFeeRate_S(Double.parseDouble(paramHttpServletRequest.getParameter("feeRate_S")) / i);
      localTariff.setTodayCloseFeeRate_B(Double.parseDouble(paramHttpServletRequest.getParameter("todayCloseFeeRate_B")) / i);
      localTariff.setTodayCloseFeeRate_S(Double.parseDouble(paramHttpServletRequest.getParameter("todayCloseFeeRate_S")) / i);
      localTariff.setHistoryCloseFeeRate_B(Double.parseDouble(paramHttpServletRequest.getParameter("historyCloseFeeRate_B")) / i);
      localTariff.setHistoryCloseFeeRate_S(Double.parseDouble(paramHttpServletRequest.getParameter("historyCloseFeeRate_S")) / i);
      localTariff.setSettleFeeAlgr(Short.parseShort(paramHttpServletRequest.getParameter("settleFeeAlgr")));
      if ("1".equals(paramHttpServletRequest.getParameter("settleFeeAlgr"))) {
        i = 100;
      } else {
        i = 1;
      }
      localTariff.setSettleFeeRate_B(Double.parseDouble(paramHttpServletRequest.getParameter("settleFeeRate_B")) / i);
      localTariff.setSettleFeeRate_S(Double.parseDouble(paramHttpServletRequest.getParameter("settleFeeRate_S")) / i);
      localTariff.setForceCloseFeeAlgr(Short.parseShort(paramHttpServletRequest.getParameter("forceSettleFeeAlgr")));
      if ("1".equals(paramHttpServletRequest.getParameter("forceSettleFeeAlgr"))) {
        i = 100;
      } else {
        i = 1;
      }
      localTariff.setForceCloseFeeRate_B(Double.parseDouble(paramHttpServletRequest.getParameter("forceCloseFeeRate_B")) / i);
      localTariff.setForceCloseFeeRate_S(Double.parseDouble(paramHttpServletRequest.getParameter("forceCloseFeeRate_S")) / i);
      localTariff.setCreateTime(((BigDecimal)localMap.get("TARIFFRATE")).toString());
      localTariff.setCreateUser((String)localMap.get("createrUser"));
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String str4 = localSimpleDateFormat.format(new Date());
      localTariff.setModifyTime(str4);
      localTariff.setModifyUser(AclCtrl.getLogonID(paramHttpServletRequest));
      localTariff.setBrokerID((String)localMap.get("brokerID"));
      localTariffManager.updateTariff(localTariff);
      DecimalFormat localDecimalFormat = new DecimalFormat("#,##0.00######");
      StringBuffer localStringBuffer = new StringBuffer("由(" + localMap.get("FEEALGR") + "," + localDecimalFormat.format(localMap.get("feeRate_B")) + "," + localDecimalFormat.format(localMap.get("feeRate_S")) + "," + localDecimalFormat.format(localMap.get("todayCloseFeeRate_B")) + "," + localDecimalFormat.format(localMap.get("todayCloseFeeRate_S")) + "," + localDecimalFormat.format(localMap.get("historyCloseFeeRate_B")) + "," + localDecimalFormat.format(localMap.get("historyCloseFeeRate_S")) + "," + localMap.get("settleFeeAlgr") + "," + localDecimalFormat.format(localMap.get("settleFeeRate_B")) + "," + localDecimalFormat.format(localMap.get("settleFeeRate_S")) + "," + localMap.get("FORCECLOSEFEEALGR") + "," + localDecimalFormat.format(localMap.get("forceCloseFeeRate_B")) + "," + localDecimalFormat.format(localMap.get("forceCloseFeeRate_S")) + ")调整为(");
      localStringBuffer.append(localTariff.getFeeAlgr() + "," + localDecimalFormat.format(localTariff.getFeeRate_B()) + "," + localDecimalFormat.format(localTariff.getFeeRate_S()) + "," + localDecimalFormat.format(localTariff.getTodayCloseFeeRate_B()) + "," + localDecimalFormat.format(localTariff.getTodayCloseFeeRate_S()) + "," + localDecimalFormat.format(localTariff.getHistoryCloseFeeRate_B()) + "," + localDecimalFormat.format(localTariff.getHistoryCloseFeeRate_S()) + "," + localTariff.getSettleFeeAlgr() + "," + localDecimalFormat.format(localTariff.getSettleFeeRate_B()) + "," + localDecimalFormat.format(localTariff.getSettleFeeRate_S()) + "," + localTariff.getForceCloseFeeAlgr() + "," + localDecimalFormat.format(localTariff.getForceCloseFeeRate_B()) + "," + localDecimalFormat.format(localTariff.getForceCloseFeeRate_S()) + " )");
      addSysLog(paramHttpServletRequest, "修改手续费套餐：[" + str2 + " 商品" + str3 + "]" + localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return editTariff(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
}
