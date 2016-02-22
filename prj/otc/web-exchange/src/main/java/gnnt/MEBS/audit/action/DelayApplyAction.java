package gnnt.MEBS.audit.action;

import gnnt.MEBS.audit.flowService.OriginalModel;
import gnnt.MEBS.audit.flowService.WorkflowFacade;
import gnnt.MEBS.audit.model.Apply;
import gnnt.MEBS.audit.model.AuditStatus;
import gnnt.MEBS.base.copy.MapToXml;
import gnnt.MEBS.base.query.jdbc.QueryHelper;
import gnnt.MEBS.common.security.AclCtrl;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DelayApplyAction
  extends ApplyAction
{
  private final transient Log logger = LogFactory.getLog(DelayApplyAction.class);
  
  public String apply()
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
    apply.setId(Long.valueOf(0L));
    apply.setApplyType(this.applyType);
    apply.setStatus(Integer.valueOf(0));
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
    if ((!operator.isEmpty()) && (result == 3)) {
      result = 8;
    }
    addResultMsg(this.request, result);
    return getReturnValue();
  }
}
