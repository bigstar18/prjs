package gnnt.MEBS.account.action;

import com.opensymphony.xwork2.ActionSupport;
import gnnt.MEBS.account.service.ErrorLoginService;
import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.base.query.jdbc.QueryHelper;
import gnnt.MEBS.base.util.WebUtils;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.LogConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.packaging.action.util.EcsideJDBCUtil;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class ErrorLoginAction
  extends ActionSupport
  implements ServletRequestAware
{
  private final transient Log logger = LogFactory.getLog(ErrorLoginAction.class);
  @Autowired
  @Qualifier("errorLoginService")
  private ErrorLoginService errorLoginService;
  @Autowired
  @Qualifier("globalLogService")
  private OperateLogService globalLogService;
  protected HttpServletRequest request;
  @Resource(name="returnValueMap")
  protected Map<String, String> returnValueMap;
  
  public void setServletRequest(HttpServletRequest request)
  {
    this.request = request;
  }
  
  protected void returnBaseMsg(PageInfo pageInfo)
  {
    this.request.setAttribute(ActionConstant.OLDPARAMS, 
      WebUtils.getParametersStartingWith(this.request, ActionConstant.GNNT_));
    this.request.setAttribute(ActionConstant.PAGEINFO, pageInfo);
  }
  
  protected PageInfo getPageInfo(Map<String, Object> map)
  {
    return (PageInfo)map.get(ActionConstant.PAGEINFO);
  }
  
  protected QueryConditions getQueryConditions(Map<String, Object> map)
  {
    return (QueryConditions)map.get(ActionConstant.QUERYCONDITIONS);
  }
  
  public String getReturnValue()
  {
    String type = this.request.getParameter("type") != null ? this.request
      .getParameter("type") : (String)this.request.getAttribute("type");
    type = type == null ? "page" : type;
    String returnValue = (String)this.returnValueMap.get(type);
    this.logger.debug("returnValue:" + returnValue);
    this.request.removeAttribute("type");
    return returnValue;
  }
  
  public String getList()
  {
    this.logger.debug("enter list");
    String sortName = (this.request.getParameter("sortName") != null) || ("".equals(this.request.getParameter("sortName"))) ? this.request
      .getParameter("sortName") : "traderid,ip";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request
      .getParameter("sortOrder") : "false";
    QueryConditions qc = new QueryConditions();
    qc.addCondition("traderid", "=", this.request.getParameter("traderid"));
    List<Map<String, Object>> list = this.errorLoginService.getList(qc, null);
    this.request.setAttribute("list", list);
    return getReturnValue();
  }
  
  public String getExList()
  {
    this.logger.debug("enter list");
    String sortName = (this.request.getParameter("sortName") != null) || ("".equals(this.request.getParameter("sortName"))) ? this.request
      .getParameter("sortName") : "traderid";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request
      .getParameter("sortOrder") : "false";
    Map<String, Object> map = EcsideJDBCUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    Map oldParams = new HashMap();
    oldParams = 
      QueryHelper.getMapFromRequest(this.request, ActionConstant.GNNT_);
    List<Map<String, Object>> list = this.errorLoginService.getExList(qc, pageInfo);
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    this.request.setAttribute("list", list);
    return getReturnValue();
  }
  
  public String active()
  {
    String manyOrAll = this.request.getParameter("manyOrAll");
    String[] ids = new String[0];
    if ("many".equals(manyOrAll))
    {
      ids = this.request.getParameterValues("ids");
    }
    else
    {
      List<Map<String, Object>> list = this.errorLoginService.getExList(null, null);
      if ((list != null) && (list.size() > 0))
      {
        ids = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
          ids[i] = ((Map)list.get(i)).get("traderId").toString();
        }
      }
    }
    this.errorLoginService.delete(ids);
    this.request.getSession().setAttribute(ActionConstant.RESULTMSG, "操作成功！");
    this.request.getSession().setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(1));
    String desc = "";
    for (String id : ids) {
      desc = desc + id + ",";
    }
    desc = "对登陆账号是：" + desc + "的解锁操作";
    OperateLog operateLog = new OperateLog();
    operateLog.setOperator(AclCtrl.getLogonID(this.request));
    operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
    operateLog.setOperateContent(desc);
    operateLog.setOperateDate(this.errorLoginService.getSysDate());
    operateLog.setOperateIp(this.request.getRemoteAddr());
    operateLog.setOperateLogType(3001);
    operateLog.setOperatorType(LogConstant.OPERATORTYPE);
    this.globalLogService.add(operateLog);
    return getReturnValue();
  }
}
