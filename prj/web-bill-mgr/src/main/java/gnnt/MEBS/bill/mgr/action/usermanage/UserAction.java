package gnnt.MEBS.bill.mgr.action.usermanage;

import gnnt.MEBS.bill.mgr.model.warehouse.Wuser;
import gnnt.MEBS.checkLogon.util.MD5;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("userAction")
@Scope("request")
public class UserAction
  extends EcsideAction
{
  private static final long serialVersionUID = 1L;
  @Resource(name="com_isForbidMap")
  Map<String, String> com_isForbidMap;
  
  public UserAction()
  {
    super.setEntityName(Wuser.class.getName());
  }
  
  public void setEntityName(String paramString) {}
  
  public Map<String, String> getCom_isForbidMap()
  {
    return this.com_isForbidMap;
  }
  
  public String addForward()
  {
    this.logger.debug("enter addForward");
    String str = this.request.getParameter("warehouseId");
    this.request.setAttribute("warehouseId", str);
    forwardAdd();
    return "success";
  }
  
  public String addUser()
  {
    Wuser localWuser = (Wuser)this.entity;
    localWuser.setPassword(MD5.getMD5(localWuser.getUserId(), localWuser.getPassword()));
    localWuser.setType("DEFAULT_SUPER_ADMIN");
    getService().add(localWuser);
    addReturnValue(1, 130201L, new Object[] { localWuser.getUserId() });
    writeOperateLog(1320, "仓库超级管理员" + localWuser.getUserId() + "添加成功", 1, "");
    return "success";
  }
  
  public String updatePassword()
  {
    this.logger.debug("enter password");
    Wuser localWuser = (Wuser)getService().get(this.entity);
    localWuser.setPassword(MD5.getMD5(localWuser.getUserId(), ((Wuser)this.entity).getPassword()));
    getService().update(localWuser);
    addReturnValue(1, 130202L, new Object[] { localWuser.getUserId() });
    writeOperateLog(1320, "修改仓库超级管理员" + localWuser.getUserId() + "的密码成功", 1, "");
    return "success";
  }
}
