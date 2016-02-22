package gnnt.MEBS.audit.action;

import com.opensymphony.xwork2.ActionSupport;
import gnnt.MEBS.audit.flowService.WorkflowFacade;
import gnnt.MEBS.audit.model.Apply;
import gnnt.MEBS.audit.service.ApplyService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.util.WebUtils;

@Scope("request")
public class CommonAuditAction
  extends ActionSupport
  implements ServletRequestAware
{
  private final transient Log logger = LogFactory.getLog(CommonAuditAction.class);
  @Autowired
  @Qualifier("applyViewService")
  protected InService applyViewService;
  @Autowired
  @Qualifier("applyService")
  protected ApplyService applyService;
  @Autowired
  @Qualifier("auditStatusService")
  protected InService auditStatusService;
  @Autowired
  @Qualifier("workflowFacade")
  protected WorkflowFacade workflowFacade;
  @Resource(name="auditOperateMap")
  private Map auditOperateMap;
  @Resource(name="returnValueMap")
  protected Map<String, String> returnValueMap;
  @Resource(name="returnOperationMap")
  protected Map<String, String> returnOperationMap;
  protected HttpServletRequest request;
  
  public Map getAuditOperateMap()
  {
    return this.auditOperateMap;
  }
  
  public void setReturnValueMap(Map<String, String> returnValueMap)
  {
    this.returnValueMap = returnValueMap;
  }
  
  public void setReturnOperationMap(Map<String, String> returnOperationMap)
  {
    this.returnOperationMap = returnOperationMap;
  }
  
  public void setServletRequest(HttpServletRequest request)
  {
    this.request = request;
  }
  
  public String auditList()
  {
    this.logger.debug("进入审核列表");
    
    String sortName = this.request.getParameter("sortName") != null ? this.request
      .getParameter("sortName") : "primary.auditModtime";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request
      .getParameter("sortOrder") : "false";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, 
      new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    List list = this.applyViewService.getList(qc, pageInfo);
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    this.request.setAttribute("list", list);
    return getReturnValue();
  }
  
  public String destroy()
  {
    String id = this.request.getParameter("applyId");
    Apply apply = (Apply)this.applyService.getById(Long.valueOf(Long.parseLong(id)));
    apply.setStatus(Integer.valueOf(-1));
    apply.setStatusDiscribe("申请撤销");
    int num = this.applyService.update(apply);
    if (num > 0) {
      num = 9;
    }
    addResultMsg(this.request, num);
    return getReturnValue();
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
  
  public void addResultMsg(HttpServletRequest request, int resultValue)
  {
    String resultMsg = (String)this.returnOperationMap.get(Integer.valueOf(resultValue));
    request.setAttribute(ActionConstant.RESULTMSG, resultMsg);
    request.setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(resultValue));
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
}
