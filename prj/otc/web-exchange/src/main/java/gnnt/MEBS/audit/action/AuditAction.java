package gnnt.MEBS.audit.action;

import com.opensymphony.xwork2.ActionSupport;
import gnnt.MEBS.audit.dao.ApplyJDBCDao;
import gnnt.MEBS.audit.flowService.OriginalModel;
import gnnt.MEBS.audit.flowService.WorkflowFacade;
import gnnt.MEBS.audit.model.Apply;
import gnnt.MEBS.audit.model.AuditStatus;
import gnnt.MEBS.base.copy.MapToXml;
import gnnt.MEBS.base.copy.XmlToMap;
import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryHelper;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideJDBCUtil;
import gnnt.MEBS.packaging.service.InService;
import java.util.Date;
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
public class AuditAction
  extends ActionSupport
  implements ServletRequestAware
{
  private final transient Log logger = LogFactory.getLog(ApplyAction.class);
  protected BaseAction action;
  @Autowired
  @Qualifier("applyService")
  protected InService applyService;
  @Autowired
  @Qualifier("auditStatusService")
  protected InService auditStatusService;
  @Autowired
  @Qualifier("workflowFacade")
  protected WorkflowFacade workflowFacade;
  protected String applyType;
  protected int operateStatus;
  @Resource(name="statusMapAudit")
  protected Map statusMapAudit;
  protected ApplyJDBCDao applyJDBCDao;
  @Resource(name="returnValueMap")
  protected Map<String, String> returnValueMap;
  @Resource(name="returnOperationMap")
  protected Map<String, String> returnOperationMap;
  protected HttpServletRequest request;
  
  public void setAction(BaseAction action)
  {
    this.action = action;
  }
  
  public void setOperateStatus(int operateStatus)
  {
    this.operateStatus = operateStatus;
  }
  
  public void setApplyType(String applyType)
  {
    this.logger.debug("setApplyType:" + applyType);
    this.applyType = applyType;
  }
  
  public void setApplyJDBCDao(ApplyJDBCDao applyJDBCDao)
  {
    this.applyJDBCDao = applyJDBCDao;
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
    

    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "primary.id";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "false";
    Map<String, Object> map = EcsideJDBCUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    gnnt.MEBS.base.query.jdbc.QueryConditions qc = getQueryConditions(map);
    if (qc != null)
    {
      qc.addCondition("applyType", "=", this.applyType);
    }
    else
    {
      qc = new gnnt.MEBS.base.query.jdbc.QueryConditions();
      qc.addCondition("applyType", "=", this.applyType);
    }
    this.logger.debug("applyType:" + this.applyType);
    Map oldParams = QueryHelper.getMapFromRequest(this.request, 
      ActionConstant.GNNT_);
    List list = this.applyJDBCDao.getApplyList(qc, pageInfo);
    EcsideJDBCUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    this.request.setAttribute("list", list);
    return getReturnValue();
  }
  
  public String auditDetails()
  {
    this.logger.debug("进入审核详情");
    this.action.setServletRequest(this.request);
    this.action.forwardAttribute();
    int result = 1;
    String applyId = this.request.getParameter("applyId");
    Apply obj = new Apply();
    
    this.logger.debug("详情中applyType:" + this.applyType);
    obj.setId(Long.valueOf(Long.parseLong(applyId)));
    obj = (Apply)this.applyService.get(obj);
    String xml = obj.getContent();
    Map map = XmlToMap.xmlToMap(xml);
    map.put("applyId", applyId);
    
    gnnt.MEBS.base.query.hibernate.QueryConditions qc = new gnnt.MEBS.base.query.hibernate.QueryConditions();
    qc.addCondition("applyId", "=", Long.valueOf(Long.parseLong(applyId)));
    List<AuditStatus> auditList = this.auditStatusService
      .getList(qc, null);
    this.request.setAttribute("auditList", auditList);
    this.request.setAttribute("obj", map);
    
    AuditStatus auditStatus = (AuditStatus)auditList.get(0);
    String proposer = auditStatus.getProposer();
    if (!proposer.equals(AclCtrl.getLogonID(this.request)))
    {
      List<Map<String, String>> buttonList = null;
      try
      {
        buttonList = this.workflowFacade.getOperateMsg(obj.getApplyType(), 
          obj.getStatus().intValue(), this.operateStatus);
      }
      catch (Exception e)
      {
        e.printStackTrace();
        result = -100;
      }
      this.request.setAttribute("buttonList", buttonList);
    }
    if (proposer.equals("admin"))
    {
      List<Map<String, String>> buttonList = null;
      try
      {
        buttonList = this.workflowFacade.getOperateMsg(obj.getApplyType(), 
          obj.getStatus().intValue(), this.operateStatus);
      }
      catch (Exception e)
      {
        e.printStackTrace();
        result = -100;
      }
      this.request.setAttribute("buttonList", buttonList);
    }
    this.request.setAttribute("statusMap", this.statusMapAudit);
    this.request.setAttribute("apply", obj);
    if (result != 1) {
      addResultMsg(this.request, result);
    }
    return getReturnValue();
  }
  
  public String audit()
  {
    OriginalModel originalModel = new OriginalModel();
    Map map = QueryHelper.getMapFromRequest(this.request, "obj.");
    String operator = this.request.getParameter("buttonClick");
    Apply apply = new Apply();
    if ((this.request.getParameter("applyId") != null) && 
      (!"".equals(this.request.getParameter("applyId"))))
    {
      apply.setId(Long.valueOf(Long.parseLong(this.request.getParameter("applyId"))));
      apply = (Apply)this.applyService.get(apply);
    }
    else
    {
      apply.setId(Long.valueOf(0L));
      apply.setApplyType(this.applyType);
      apply.setStatus(Integer.valueOf(0));
    }
    this.logger.debug("operate:type:" + operator + "    " + this.applyType);
    AuditStatus auditStatus = new AuditStatus();
    apply.setContent(MapToXml.mapToXml(map));
    auditStatus.setApplyId(apply.getId());
    auditStatus.setModTime(new Date());
    apply.setStatusDiscribe(this.request.getAttribute("statusDiscribe") != null ? (String)this.request.getAttribute("statusDiscribe") : null);
    auditStatus.setProposer(AclCtrl.getLogonID(this.request));
    originalModel.setAuditObject(apply);
    originalModel.setBusinessMap(map);
    originalModel.setKey(operator);
    originalModel.setLog(auditStatus);
    int result = this.workflowFacade.dealWorkflow(originalModel, this.applyType);
    if ((operator.endsWith("reject")) && (result == 3)) {
      result = 7;
    }
    addResultMsg(this.request, result);
    this.logger.debug("end audit");
    this.logger.debug("result:" + result);
    this.request.setAttribute("applyType", this.applyType);
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
  
  protected gnnt.MEBS.base.query.jdbc.QueryConditions getQueryConditions(Map<String, Object> map)
  {
    return (gnnt.MEBS.base.query.jdbc.QueryConditions)map.get(ActionConstant.QUERYCONDITIONS);
  }
}
