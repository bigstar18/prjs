package gnnt.MEBS.test.action;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.test.service.TeacherService;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class TeacherAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(TeacherAction.class);
  @Autowired
  @Qualifier("teacherService")
  private TeacherService teacherService;
  
  public InService getService()
  {
    return this.teacherService;
  }
  
  public String delete()
  {
    addResultSessionMsg(this.request, 1);
    this.logger.debug("enter delete");
    HttpServletRequest request = ServletActionContext.getRequest();
    String[] ids = request.getParameterValues("ids");
    int resultValue = 1;
    for (String id : ids)
    {
      Clone obj = getService().getById(Long.valueOf(Long.parseLong(id)));
      OperateLog log = new OperateLog();
      log.setOperateDate(new Date());
      log.setOperator(AclCtrl.getUser(request).getName());
      
      OperateLog operateLog = new OperateLog();
      
      operateLog.setObj(obj);
      ThreadStore.put("operateLog", operateLog);
      resultValue = getService().delete(obj);
    }
    addResultSessionMsg(request, resultValue);
    return getReturnValue();
  }
}
