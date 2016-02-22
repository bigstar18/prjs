package gnnt.MEBS.audit.flowService.common;

import gnnt.MEBS.audit.flowService.OriginalModel;
import gnnt.MEBS.audit.flowService.StatusOperate;
import gnnt.MEBS.audit.model.Apply;
import gnnt.MEBS.audit.model.AuditStatus;
import gnnt.MEBS.audit.service.ApplyService;
import gnnt.MEBS.audit.service.AuditStatusService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class StatusOperateImpl
  implements StatusOperate
{
  @Autowired
  @Qualifier("applyService")
  private ApplyService applyService;
  @Autowired
  @Qualifier("auditStatusService")
  private AuditStatusService auditStatusService;
  private final transient Log logger = LogFactory.getLog(StatusOperateImpl.class);
  
  public void updateStatus(OriginalModel originalModel)
  {
    Apply auditObject = originalModel.getAuditObject();
    AuditStatus log = originalModel.getLog();
    log.setStatus(auditObject.getStatus());
    log.setStatusDiscribe(auditObject.getStatusDiscribe());
    log.setApplyId(auditObject.getId());
    this.logger.debug("auditObject.id:" + auditObject.getId());
    this.logger.debug("auditObject.type:" + auditObject.getApplyType());
    this.logger.debug("auditObject.status:" + auditObject.getStatus());
    this.applyService.update(auditObject);
    this.auditStatusService.add(log);
  }
}
