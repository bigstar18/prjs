package gnnt.MEBS.common.manage.web;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.common.manage.model.Apply_T_VirtualMoney;
import gnnt.MEBS.common.manage.service.ApplyManage;
import gnnt.MEBS.common.manage.service.VirtualFundManage;
import gnnt.MEBS.common.manage.util.CommonDictionary;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.finance.base.util.SysData;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class VirtualFundController
  extends MultiActionController
{
  private final transient Log logger = LogFactory.getLog(VirtualFundController.class);
  
  public ModelAndView listVirtualFund(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'listVirtualFund' method");
    }
    ApplyManage localApplyManage = (ApplyManage)SysData.getBean("applyManager");
    String str1 = paramHttpServletRequest.getParameter("firmID");
    String str2 = paramHttpServletRequest.getParameter("status");
    String str3 = AclCtrl.getLogonID(paramHttpServletRequest);
    Apply_T_VirtualMoney localApply_T_VirtualMoney = new Apply_T_VirtualMoney();
    localApply_T_VirtualMoney.setFirmId(str1);
    if ((str2 != null) && (!"".equals(str2)))
    {
      localApply_T_VirtualMoney.setStatus(Integer.parseInt(str2));
      this.logger.debug(str2);
    }
    localApply_T_VirtualMoney.setProposer(str3);
    localApply_T_VirtualMoney.setApplyType(1);
    ModelAndView localModelAndView = null;
    localModelAndView = new ModelAndView("common/timebargain/virtualfund/app/virtualFundApp_list", "resultList", "");
    try
    {
      List localList = localApplyManage.getApplys(localApply_T_VirtualMoney);
      localModelAndView.addObject("virtualFundList", localList);
      localModelAndView.addObject("VIRTUALFUNDAPPLYTYPE", CommonDictionary.VIRTUALFUNDAPPLYTYPE);
      localModelAndView.addObject("VIRTUALFUNDSTATUS", CommonDictionary.VIRTUALFUNDSTATUS);
    }
    catch (Exception localException)
    {
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    return localModelAndView;
  }
  
  public ModelAndView editVirtualFund(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'editVirtualFund' method");
    }
    ModelAndView localModelAndView = new ModelAndView("common/timebargain/virtualfund/app/virtualFundApp_form", "resultList", "");
    return localModelAndView;
  }
  
  public ModelAndView saveVirtualFund(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'saveVirtualFund' method");
    }
    ApplyManage localApplyManage = (ApplyManage)SysData.getBean("applyManager");
    String str = AclCtrl.getLogonID(paramHttpServletRequest);
    Apply_T_VirtualMoney localApply_T_VirtualMoney = new Apply_T_VirtualMoney();
    ModelAndView localModelAndView = null;
    localModelAndView = new ModelAndView("common/timebargain/virtualfund/app/virtualFundApp_form_save", "resultList", "");
    try
    {
      ParamUtil.bindData(paramHttpServletRequest, localApply_T_VirtualMoney);
      this.logger.debug(localApply_T_VirtualMoney.getFirmId());
      this.logger.debug(Double.valueOf(localApply_T_VirtualMoney.getMoney()));
      localApply_T_VirtualMoney.setApplyType(1);
      localApply_T_VirtualMoney.setStatus(1);
      localApply_T_VirtualMoney.setProposer(str);
      localApplyManage.insertApply(localApply_T_VirtualMoney);
    }
    catch (Exception localException)
    {
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    return localModelAndView;
  }
  
  public ModelAndView listVirtualFundChe(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'listVirtualFundChe' method");
    }
    ApplyManage localApplyManage = (ApplyManage)SysData.getBean("applyManager");
    String str1 = paramHttpServletRequest.getParameter("firmID");
    String str2 = paramHttpServletRequest.getParameter("status");
    Apply_T_VirtualMoney localApply_T_VirtualMoney = new Apply_T_VirtualMoney();
    localApply_T_VirtualMoney.setFirmId(str1);
    if ((str2 != null) && (!"".equals(str2))) {
      localApply_T_VirtualMoney.setStatus(Integer.parseInt(str2));
    }
    localApply_T_VirtualMoney.setApplyType(1);
    ModelAndView localModelAndView = null;
    localModelAndView = new ModelAndView("common/timebargain/virtualfund/check/virtualFundChe_list", "resultList", "");
    try
    {
      List localList = localApplyManage.getApplys(localApply_T_VirtualMoney);
      localModelAndView.addObject("virtualFundCheList", localList);
      localModelAndView.addObject("VIRTUALFUNDAPPLYTYPE", CommonDictionary.VIRTUALFUNDAPPLYTYPE);
      localModelAndView.addObject("VIRTUALFUNDSTATUS", CommonDictionary.VIRTUALFUNDSTATUS);
    }
    catch (Exception localException)
    {
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    return localModelAndView;
  }
  
  public ModelAndView checkVirtualFund(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'checkVirtualFund' method");
    }
    VirtualFundManage localVirtualFundManage = (VirtualFundManage)SysData.getBean("virtualFundManager");
    String str1 = paramHttpServletRequest.getParameter("firmID");
    String str2 = paramHttpServletRequest.getParameter("virtualFunds");
    String str3 = paramHttpServletRequest.getParameter("id");
    String str4 = paramHttpServletRequest.getParameter("status");
    String str5 = paramHttpServletRequest.getParameter("note");
    String str6 = AclCtrl.getLogonID(paramHttpServletRequest);
    Apply_T_VirtualMoney localApply_T_VirtualMoney = new Apply_T_VirtualMoney();
    if ((str3 != null) && (!"".equals(str3))) {
      localApply_T_VirtualMoney.setId(Long.parseLong(str3));
    }
    if ((str4 != null) && (!"".equals(str4))) {
      localApply_T_VirtualMoney.setStatus(Integer.parseInt(str4));
    }
    localApply_T_VirtualMoney.setNote(str5);
    localApply_T_VirtualMoney.setApprover(str6);
    ModelAndView localModelAndView = null;
    localModelAndView = new ModelAndView("common/timebargain/virtualfund/check/virtualFundChe_form_save", "resultList", "");
    try
    {
      int i = localVirtualFundManage.updateVirtual(localApply_T_VirtualMoney);
      if (i == -1) {
        localModelAndView.addObject("prompt", "此记录已审核完毕！");
      } else if (i != 0) {
        if (i == 1) {
          localModelAndView.addObject("prompt", "交易商不存在！");
        } else if (i == 2) {
          localModelAndView.addObject("prompt", "虚拟资金总额不能为负！");
        }
      }
    }
    catch (Exception localException)
    {
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    return localModelAndView;
  }
}
