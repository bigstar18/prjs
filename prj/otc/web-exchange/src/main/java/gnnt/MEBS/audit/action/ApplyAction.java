package gnnt.MEBS.audit.action;

import com.opensymphony.xwork2.ActionSupport;
import gnnt.MEBS.audit.dao.ApplyJDBCDao;
import gnnt.MEBS.audit.flowService.OriginalModel;
import gnnt.MEBS.audit.flowService.WorkflowFacade;
import gnnt.MEBS.audit.model.Apply;
import gnnt.MEBS.audit.model.ApplyView;
import gnnt.MEBS.audit.model.AuditStatus;
import gnnt.MEBS.base.copy.MapToXml;
import gnnt.MEBS.base.copy.XmlToMap;
import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryHelper;
import gnnt.MEBS.base.util.WebUtils;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideJDBCUtil;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ApplyAction
  extends ActionSupport
  implements ServletRequestAware
{
  private final transient Log logger = LogFactory.getLog(ApplyAction.class);
  @Autowired
  @Qualifier("applyService")
  protected InService applyService;
  @Autowired
  @Qualifier("applyViewService")
  protected InService applyViewService;
  @Autowired
  @Qualifier("auditStatusService")
  protected InService auditStatusService;
  @Autowired
  @Qualifier("workflowFacade")
  protected WorkflowFacade workflowFacade;
  protected BaseAction action;
  protected ApplyJDBCDao applyJDBCDao;
  protected String applyType;
  protected int operateStatus;
  @Resource(name="returnValueMap")
  protected Map<String, String> returnValueMap;
  @Resource(name="returnOperationMap")
  protected Map<String, String> returnOperationMap;
  protected HttpServletRequest request;
  
  public void setApplyJDBCDao(ApplyJDBCDao applyJDBCDao)
  {
    this.applyJDBCDao = applyJDBCDao;
  }
  
  public void setAction(BaseAction action)
  {
    this.action = action;
  }
  
  public void setApplyType(String applyType)
  {
    this.applyType = applyType;
  }
  
  public String getApplyType()
  {
    return this.applyType;
  }
  
  public void setOperateStatus(int operateStatus)
  {
    this.operateStatus = operateStatus;
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
  
  public String applyList()
  {
    this.logger.debug("enter applyList");
    String sortName = this.request.getParameter("sortName") != null ? this.request
      .getParameter("sortName") : "primary.id";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request
      .getParameter("sortOrder") : "false";
    Map<String, Object> map = EcsideJDBCUtil.getQurey(this.request, sortName, 
      new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    gnnt.MEBS.base.query.jdbc.QueryConditions qc = getQueryConditions(map);
    this.logger.debug("applyType:" + this.applyType);
    if (qc != null)
    {
      qc.addCondition("applyType", "=", this.applyType);
    }
    else
    {
      qc = new gnnt.MEBS.base.query.jdbc.QueryConditions();
      qc.addCondition("applyType", "=", this.applyType);
    }
    Map oldParams = new HashMap();
    oldParams = 
      QueryHelper.getMapFromRequest(this.request, ActionConstant.GNNT_);
    List list = this.applyJDBCDao.getApplyList(qc, pageInfo);
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    this.request.setAttribute("list", list);
    this.logger.debug("list:" + list.size());
    this.request.setAttribute("applyType", this.applyType);
    return getReturnValue();
  }
  
  public String applyDetails()
  {
    int result = 1;
    Apply apply = new Apply();
    apply.setApplyType(this.applyType);
    apply.setStatus(Integer.valueOf(0));
    
    List<Map<String, String>> buttonList = null;
    try
    {
      buttonList = this.workflowFacade.getOperateMsg(apply.getApplyType(), 
        apply.getStatus().intValue(), this.operateStatus);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result = -100;
    }
    this.request.setAttribute("buttonList", buttonList);
    
    gnnt.MEBS.base.query.hibernate.QueryConditions qc = new gnnt.MEBS.base.query.hibernate.QueryConditions();
    qc.addCondition("applyType", "=", this.applyType);
    qc.addCondition("status", "=", Integer.valueOf(1));
    Map<String, String> map = 
      QueryHelper.getMapFromRequest(this.request, "obj.");
    List<Apply> applyList = this.applyService.getList(qc, null);
    if (applyList.size() > 0)
    {
      Apply a = null;
      int size = map.size();
      for (Apply apply1 : applyList)
      {
        int s = 0;
        String xml = apply1.getContent();
        Map<String, String> map1 = XmlToMap.xmlToMap(xml);
        for (String key : map.keySet())
        {
          String value = (String)map1.get(key);
          String value1 = (String)map.get(key);
          if (value1.equals(value))
          {
            s++;
            if (size == s)
            {
              a = apply1;
              break;
            }
          }
        }
      }
      if (a != null)
      {
        ApplyView applyView = (ApplyView)this.applyViewService.getById(a.getId());
        this.request.setAttribute("applyObj", applyView);
      }
    }
    if (result != 1) {
      addResultMsg(this.request, result);
    }
    return getReturnValue();
  }
  
  public String apply()
  {
    OriginalModel originalModel = new OriginalModel();
    Map map = QueryHelper.getMapFromRequest(this.request, "obj.");
    this.logger.debug("map:" + map);
    String operator = this.request.getParameter("buttonClick");
    Apply apply = new Apply();
    apply.setId(Long.valueOf(0L));
    apply.setApplyType(this.applyType);
    apply.setStatus(Integer.valueOf(0));
    apply.setStatusDiscribe(this.request.getAttribute("statusDiscribe") != null ? (String)this.request.getAttribute("statusDiscribe") : null);
    AuditStatus auditStatus = new AuditStatus();
    apply.setContent(MapToXml.mapToXml(map));
    auditStatus.setApplyId(apply.getId());
    auditStatus.setModTime(new Date());
    auditStatus.setProposer(AclCtrl.getLogonID(this.request));
    originalModel.setAuditObject(apply);
    originalModel.setBusinessMap(map);
    originalModel.setKey(operator);
    originalModel.setLog(auditStatus);
    int result = this.workflowFacade.dealWorkflow(originalModel, this.applyType);
    if ((!operator.isEmpty()) && (result == 3)) {
      result = 8;
    }
    addResultMsg(this.request, result);
    return getReturnValue();
  }
  
  public String details()
  {
    int result = 1;
    String id = this.request.getParameter("id");
    Apply obj = new Apply();
    obj.setId(Long.valueOf(Long.parseLong(id)));
    obj = (Apply)this.applyService.get(obj);
    String xml = obj.getContent();
    Map map = XmlToMap.xmlToMap(xml);
    this.action.setServletRequest(this.request);
    this.action.forwardAttribute();
    
    gnnt.MEBS.base.query.hibernate.QueryConditions qc = new gnnt.MEBS.base.query.hibernate.QueryConditions();
    qc.addCondition("applyId", "=", Long.valueOf(Long.parseLong(id)));
    List auditList = this.auditStatusService
      .getList(qc, null);
    
    this.request.setAttribute("auditList", auditList);
    this.request.setAttribute("obj", map);
    this.request.setAttribute("apply", obj);
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
