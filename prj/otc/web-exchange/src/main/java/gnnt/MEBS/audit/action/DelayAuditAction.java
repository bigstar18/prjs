package gnnt.MEBS.audit.action;

import gnnt.MEBS.audit.flowService.OriginalModel;
import gnnt.MEBS.audit.flowService.WorkflowFacade;
import gnnt.MEBS.audit.model.Apply;
import gnnt.MEBS.audit.model.AuditStatus;
import gnnt.MEBS.base.copy.MapToXml;
import gnnt.MEBS.base.copy.XmlToMap;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.query.jdbc.QueryHelper;
import gnnt.MEBS.commodity.service.StepDictionaryService;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class DelayAuditAction
  extends AuditAction
{
  private final transient Log logger = LogFactory.getLog(DelayAuditAction.class);
  @Autowired
  @Qualifier("stepDictionaryService")
  private StepDictionaryService stepDictionaryService;
  @Resource(name="statusMapAudit")
  protected Map statusMapAudit;
  @Resource(name="commodityFeeAlgrMap")
  private Map commodityFeeAlgrMap;
  
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
    Map<String, String> map = XmlToMap.xmlToMap(xml);
    int total = 0;
    for (String key : map.keySet()) {
      if (key.indexOf("mkt_stepNO") >= 0) {
        total++;
      }
    }
    map.put("applyId", applyId);
    
    QueryConditions qc = new QueryConditions();
    qc.addCondition("applyId", "=", Long.valueOf(Long.parseLong(applyId)));
    Object auditList = this.auditStatusService
      .getList(qc, null);
    this.request.setAttribute("auditList", auditList);
    this.request.setAttribute("tcDelayFeeMap", map);
    this.request.setAttribute("total", Integer.valueOf(total));
    
    AuditStatus auditStatus = (AuditStatus)((List)auditList).get(0);
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
    this.request.setAttribute("commodityFeeAlgrMap", this.commodityFeeAlgrMap);
    this.request.setAttribute("statusMap", this.statusMapAudit);
    this.request.setAttribute("apply", obj);
    List ladderList = this.stepDictionaryService.getList(null, null);
    this.request.setAttribute("ladderList", ladderList);
    if (result != 1) {
      addResultMsg(this.request, result);
    }
    return getReturnValue();
  }
  
  public String audit()
  {
    OriginalModel originalModel = new OriginalModel();
    Map map = QueryHelper.getMapFromRequest(this.request, "obj.");
    String[] delayFees = this.request.getParameterValues("specialforAudit.delayFee_v");
    if ((delayFees != null) && (delayFees.length > 0))
    {
      long i = 1L;
      for (String delayFee : delayFees)
      {
        map.put("stepNO" + i, delayFee);
        i += 1L;
      }
    }
    String[] mkt_delayFeeRates = this.request.getParameterValues("specialforAudit.mkt_delayFeeRate_v");
    if ((mkt_delayFeeRates != null) && (mkt_delayFeeRates.length > 0))
    {
      long i = 1L;
      for (String mkt_delayFee : mkt_delayFeeRates)
      {
        map.put("mkt_stepNO" + i, mkt_delayFee);
        i += 1L;
      }
    }
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
    apply.setStatusDiscribe(this.request.getAttribute("statusDiscribe") != null ? (String)this.request.getAttribute("statusDiscribe") : null);
    auditStatus.setApplyId(apply.getId());
    auditStatus.setModTime(new Date());
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
}
