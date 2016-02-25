package gnnt.MEBS.common.manage.web;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.common.manage.model.Apply_T_PledgeMoney;
import gnnt.MEBS.common.manage.service.ApplyManage;
import gnnt.MEBS.common.manage.service.PledgeManage;
import gnnt.MEBS.common.manage.util.CommonDictionary;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.RegStockService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class PledgeController
  extends MultiActionController
{
  private final transient Log logger = LogFactory.getLog(PledgeController.class);
  
  public ModelAndView listPledgeApp(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'listPledgeApp' method");
    }
    ApplyManage localApplyManage = (ApplyManage)gnnt.MEBS.finance.base.util.SysData.getBean("applyManager");
    String str1 = paramHttpServletRequest.getParameter("firmID");
    String str2 = paramHttpServletRequest.getParameter("status");
    String str3 = AclCtrl.getLogonID(paramHttpServletRequest);
    Apply_T_PledgeMoney localApply_T_PledgeMoney = new Apply_T_PledgeMoney();
    localApply_T_PledgeMoney.setFirmId(str1);
    if ((str2 != null) && (!"".equals(str2))) {
      localApply_T_PledgeMoney.setStatus(Integer.parseInt(str2));
    }
    localApply_T_PledgeMoney.setProposer(str3);
    localApply_T_PledgeMoney.setApplyType(2);
    ModelAndView localModelAndView = null;
    localModelAndView = new ModelAndView("common/timebargain/pledge/app/pledgeApp_list", "resultList", "");
    try
    {
      List localList = localApplyManage.getApplys(localApply_T_PledgeMoney);
      localModelAndView.addObject("pledgeList", localList);
      localModelAndView.addObject("VIRTUALFUNDAPPLYTYPE", CommonDictionary.VIRTUALFUNDAPPLYTYPE);
      localModelAndView.addObject("VIRTUALFUNDSTATUS", CommonDictionary.VIRTUALFUNDSTATUS);
      localModelAndView.addObject("OPERATECHECK", CommonDictionary.OPERATECHECK);
    }
    catch (Exception localException)
    {
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    return localModelAndView;
  }
  
  public ModelAndView editPledgeApp(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'editPledgeApp' method");
    }
    ModelAndView localModelAndView = new ModelAndView("common/timebargain/pledge/app/pledgeApp_form", "resultList", "");
    return localModelAndView;
  }
  
  public ModelAndView savePledgeApp(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'savePledgeApp' method");
    }
    ApplyManage localApplyManage = (ApplyManage)gnnt.MEBS.finance.base.util.SysData.getBean("applyManager");
    String str = AclCtrl.getLogonID(paramHttpServletRequest);
    Apply_T_PledgeMoney localApply_T_PledgeMoney = new Apply_T_PledgeMoney();
    ModelAndView localModelAndView = null;
    localModelAndView = new ModelAndView("common/timebargain/pledge/app/pledgeApp_form_save", "resultList", "");
    try
    {
      ParamUtil.bindData(paramHttpServletRequest, localApply_T_PledgeMoney);
      localApply_T_PledgeMoney.setApplyType(2);
      localApply_T_PledgeMoney.setStatus(1);
      localApply_T_PledgeMoney.setProposer(str);
      localApplyManage.insertApply(localApply_T_PledgeMoney);
    }
    catch (Exception localException)
    {
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    return localModelAndView;
  }
  
  public ModelAndView listPledgeChe(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'listPledgeChe' method");
    }
    ApplyManage localApplyManage = (ApplyManage)gnnt.MEBS.finance.base.util.SysData.getBean("applyManager");
    String str1 = paramHttpServletRequest.getParameter("firmID");
    String str2 = paramHttpServletRequest.getParameter("status");
    Apply_T_PledgeMoney localApply_T_PledgeMoney = new Apply_T_PledgeMoney();
    localApply_T_PledgeMoney.setFirmId(str1);
    if ((str2 != null) && (!"".equals(str2))) {
      localApply_T_PledgeMoney.setStatus(Integer.parseInt(str2));
    }
    localApply_T_PledgeMoney.setApplyType(2);
    ModelAndView localModelAndView = null;
    localModelAndView = new ModelAndView("common/timebargain/pledge/check/pledgeChe_list", "resultList", "");
    try
    {
      List localList = localApplyManage.getApplys(localApply_T_PledgeMoney);
      localModelAndView.addObject("pledgeList", localList);
      localModelAndView.addObject("VIRTUALFUNDAPPLYTYPE", CommonDictionary.VIRTUALFUNDAPPLYTYPE);
      localModelAndView.addObject("VIRTUALFUNDSTATUS", CommonDictionary.VIRTUALFUNDSTATUS);
      localModelAndView.addObject("OPERATECHECK", CommonDictionary.OPERATECHECK);
    }
    catch (Exception localException)
    {
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    return localModelAndView;
  }
  
  public ModelAndView checkPledge(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'checkPledge' method");
    }
    Apply_T_PledgeMoney localApply_T_PledgeMoney = new Apply_T_PledgeMoney();
    String str1 = AclCtrl.getLogonID(paramHttpServletRequest);
    String str2 = paramHttpServletRequest.getParameter("billID");
    double d1 = Double.parseDouble(paramHttpServletRequest.getParameter("quantity"));
    String str3 = paramHttpServletRequest.getParameter("type");
    RegStockService localRegStockService = (RegStockService)gnnt.MEBS.delivery.util.SysData.getBean("w_regStockService");
    RegStock localRegStock = localRegStockService.getRegStockById(str2);
    double d2 = localRegStock.getWeight();
    double d3 = localRegStock.getFrozenWeight();
    ModelAndView localModelAndView = null;
    localModelAndView = new ModelAndView("common/timebargain/pledge/check/pledgeChe_form_save", "resultList", "");
    try
    {
      if (d2 == 0.0D)
      {
        localModelAndView.addObject("prompt", "仓单不存在！");
        return localModelAndView;
      }
      if (d2 - d3 <= d1)
      {
        localModelAndView.addObject("prompt", "质押数量大于仓单可用数量！");
        return localModelAndView;
      }
      ParamUtil.bindData(paramHttpServletRequest, localApply_T_PledgeMoney);
      localApply_T_PledgeMoney.setApprover(str1);
      PledgeManage localPledgeManage = (PledgeManage)gnnt.MEBS.finance.base.util.SysData.getBean("pledgeManager");
      int i = localPledgeManage.updatePledgeCheck(localApply_T_PledgeMoney);
      if (i == -1) {
        localModelAndView.addObject("prompt", "此记录已审核完毕！");
      } else if (i != 0) {
        if (i == 1) {
          localModelAndView.addObject("prompt", "交易商不存在！");
        } else if (i == 2) {
          localModelAndView.addObject("prompt", "质押资金总额不能为负，审核失败！");
        } else if (i == 3) {
          localModelAndView.addObject("prompt", "仓单号不存在，审核失败");
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    if ("1".equals(str3)) {
      localRegStockService.frozenAmount(localRegStock, d1);
    } else if ("2".equals(str3)) {
      localRegStockService.releaseAmount(localRegStock, d1, 2);
    }
    return localModelAndView;
  }
}
