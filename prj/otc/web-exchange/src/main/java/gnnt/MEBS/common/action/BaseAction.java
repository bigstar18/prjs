package gnnt.MEBS.common.action;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.packaging.service.InService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseAction
  extends gnnt.MEBS.packaging.action.BaseAction
{
  private final transient Log logger = LogFactory.getLog(BaseAction.class);
  
  public String delete()
  {
    this.logger.debug("enter delete");
    String[] ids = this.request.getParameterValues("ids");
    int resultValue = 1;
    if ((ids != null) && (ids.length > 0)) {
      for (String id : ids)
      {
        this.obj.setPrimary(id);
        Clone clone = getService().get(this.obj);
        
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(clone);
        operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
        this.logger.debug("enter delete operateLog:" + this.obj);
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
        
        resultValue = getService().delete(this.obj);
      }
    } else {
      resultValue = -2;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public void saveLog(String description, Clone obj)
    throws Exception
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleSaveAssociation()---//");
    if (((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue() > 0)
    {
      OperateLog operateLog = new OperateLog();
      operateLog.setObj(obj);
      operateLog.setOperateContent(description);
      



      operateLog.setOperator(AclCtrl.getLogonID(this.request));
      operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
      
      ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
    }
  }
}
