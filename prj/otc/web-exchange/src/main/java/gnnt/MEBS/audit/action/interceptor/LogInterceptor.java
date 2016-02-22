package gnnt.MEBS.audit.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.audit.model.ApplyView;
import gnnt.MEBS.audit.model.ParmaLog;
import gnnt.MEBS.audit.service.ApplyViewService;
import gnnt.MEBS.audit.service.ParmaLogService;
import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.util.SpringContextHelper;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.LogConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.ExecuteObject;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.globalLog.util.Compare;
import gnnt.MEBS.packaging.service.InService;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class LogInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(LogInterceptor.class);
  private String classFullName;
  private String serrviceName;
  
  public void setClassFullName(String classFullName)
  {
    this.classFullName = classFullName;
  }
  
  public void setSerrviceName(String serrviceName)
  {
    this.serrviceName = serrviceName;
  }
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    Clone clone = getObject();
    ParamUtil.bindData(request, clone);
    Clone cloneCopy = (Clone)clone.clone();
    InService inService = (InService)SpringContextHelper.getBean(this.serrviceName);
    Clone cloneOld = inService.get(cloneCopy);
    this.logger.debug("cloneOld:" + cloneOld);
    ExecuteObject executeObject = Compare.getDifferent(cloneOld, clone);
    this.logger.debug("executeObject.getPropertyList().size():" + executeObject.getPropertyList().size());
    String operator = request.getParameter("buttonClick");
    if (executeObject.getPropertyList().size() > 0)
    {
      String operatorName = "";
      if ("apply".equals(operator)) {
        operatorName = "申请记录";
      } else if ("audit".equals(operator)) {
        operatorName = "审核记录";
      } else if ("reject".equals(operator)) {
        operatorName = "驳回记录";
      }
      request.setAttribute("statusDiscribe", Compare.translate(executeObject));
      request.setAttribute("statusDiscribeForParmaLog", Compare.translateForParmaLog(executeObject));
      String description = operatorName + Compare.translate(executeObject);
      this.logger.debug("description:" + description);
      request.setAttribute("descriptionLog", description);
    }
    String result = invocation.invoke();
    this.logger.debug("ThreadStore.get(ThreadStoreConstant.ISLOG):" + ThreadStore.get(ThreadStoreConstant.ISLOG));
    if ((ThreadStore.get(ThreadStoreConstant.ISLOG) != null) && 
      (((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue() > 0))
    {
      this.logger.debug(Boolean.valueOf("request.getAttribute(ActionConstant.RESULTVAULE) != null:" + request.getAttribute(ActionConstant.RESULTVAULE) != null));
      if (((request.getAttribute(ActionConstant.RESULTVAULE) != null) && (((Integer)request.getAttribute(ActionConstant.RESULTVAULE)).intValue() > 0)) || (
        (request.getSession().getAttribute(ActionConstant.RESULTVAULE) != null) && (((Integer)request.getSession().getAttribute(ActionConstant.RESULTVAULE)).intValue() > 0)))
      {
        String operate = (String)ThreadStore.get(ThreadStoreConstant.OPERATE);
        this.logger.debug("operate:" + operate);
        addGolbalLog((String)request.getAttribute("descriptionLog"), clone, operate, AclCtrl.getLogonID(request), request);
      }
    }
    if ("audit".equals(operator))
    {
      String applyId = request.getParameter("applyId");
      ApplyViewService applyViewService = (ApplyViewService)SpringContextHelper.getBean("applyViewService");
      ApplyView applyView = (ApplyView)applyViewService.getById(Long.valueOf(Long.parseLong(applyId)));
      addParamLog((String)request.getAttribute("statusDiscribeForParmaLog"), clone, cloneOld, (String)request.getAttribute("applyType"), applyView.getProposer(), AclCtrl.getLogonID(request), request);
    }
    return result;
  }
  
  private Clone getObject()
  {
    Clone clone = null;
    Class classType = null;
    try
    {
      classType = Class.forName(this.classFullName);
      clone = (Clone)classType.newInstance();
    }
    catch (InstantiationException e)
    {
      e.printStackTrace();
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    return clone;
  }
  
  private void addGolbalLog(String desc, Object obj, String operate, String operator, ServletRequest request)
  {
    OperateLog operateLog = new OperateLog();
    operateLog.setObj(obj);
    operateLog.setOperateContent(desc);
    operateLog.setOperateDate(new Date());
    operateLog.setOperateType(operate);
    operateLog.setOperator(operator);
    operateLog.setOperateIp(request.getRemoteAddr());
    operateLog.setOperatorType(LogConstant.OPERATORTYPE);
    operateLog.setMark((String)((HttpServletRequest)request).getSession().getAttribute(ActionConstant.REGISTERID));
    OperateLogService operateLogService = (OperateLogService)SpringContextHelper.getBean("globalLogService");
    operateLogService.add(operateLog);
  }
  
  private void addParamLog(String desc, Object obj, Object oldObj, String operate, String operator, String oldOperator, ServletRequest request)
  {
    ParmaLog parmaLog = new ParmaLog();
    parmaLog.setMark((String)((HttpServletRequest)request).getSession().getAttribute(ActionConstant.REGISTERID));
    parmaLog.setOperateGflag(operate);
    parmaLog.setOperateTime(new Date());
    parmaLog.setOperator(operator);
    parmaLog.setOldOperator(oldOperator);
    parmaLog.setOperateContent(desc);
    parmaLog.setOperatorType(LogConstant.OPERATORTYPE);
    parmaLog.setObj(obj);
    parmaLog.setOldObj(oldObj);
    ParmaLogService pramaLogService = (ParmaLogService)SpringContextHelper.getBean("pramaLogService");
    pramaLogService.add(parmaLog);
  }
}
