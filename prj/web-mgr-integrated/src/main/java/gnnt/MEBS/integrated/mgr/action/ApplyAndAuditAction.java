package gnnt.MEBS.integrated.mgr.action;

import gnnt.MEBS.common.mgr.action.StandardAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.Apply;
import gnnt.MEBS.common.mgr.model.Audit;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.QueryService;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.Serialize;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.service.ApplyAndAuditService;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("applyAndAuditAction")
@Scope("request")
public class ApplyAndAuditAction
  extends StandardAction
{
  private static final long serialVersionUID = -3098756151877918720L;
  @Resource(name="auditOperateMap")
  Map<String, String> auditOperateMap;
  @Autowired
  @Qualifier("applyAndAuditService")
  private ApplyAndAuditService applyAndAuditService;
  
  public StandardService getService()
  {
    return this.applyAndAuditService;
  }
  
  public Map<String, String> getAuditOperateMap()
  {
    return this.auditOperateMap;
  }
  
  public String waitAuditList()
  {
    this.logger.debug("enter waitAuditList");
    PageRequest localPageRequest = new PageRequest(" and status=0 ");
    localPageRequest.setSortColumns(" order by createTime desc");
    Page localPage = getQueryService().getPage(localPageRequest, this.entity);
    this.request.setAttribute("pageInfo", localPage);
    return "success";
  }
  
  public String passAuditList()
  {
    this.logger.debug("enter passAuditList");
    PageRequest localPageRequest = new PageRequest(" and status=1 ");
    localPageRequest.setSortColumns(" order by apply.createTime desc");
    Page localPage = getService().getPage(localPageRequest, this.entity);
    this.request.setAttribute("pageInfo", localPage);
    return "success";
  }
  
  public String rejectAuditList()
  {
    this.logger.debug("enter rejectAuditList");
    PageRequest localPageRequest = new PageRequest(" and status=2 ");
    localPageRequest.setSortColumns(" order by apply.createTime desc");
    Page localPage = getService().getPage(localPageRequest, this.entity);
    this.request.setAttribute("pageInfo", localPage);
    return "success";
  }
  
  public String canWithdrawList()
  {
    this.logger.debug("enter canWithdrawList");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    PageRequest localPageRequest = new PageRequest(" and status=0 and applyUser='" + localUser.getUserId() + "'");
    localPageRequest.setSortColumns(" order by createTime desc");
    Page localPage = getService().getPage(localPageRequest, this.entity);
    this.request.setAttribute("pageInfo", localPage);
    return "success";
  }
  
  public String withdrawedList()
  {
    this.logger.debug("enter withdrawedList");
    PageRequest localPageRequest = new PageRequest(" and status=3 ");
    localPageRequest.setSortColumns(" order by modTime desc");
    Page localPage = getQueryService().getPage(localPageRequest, this.entity);
    this.request.setAttribute("pageInfo", localPage);
    return "success";
  }
  
  public String applyDetails()
  {
    this.logger.debug("enter applyDetails");
    this.entity = getQueryService().get(this.entity);
    Apply localApply = (Apply)this.entity.clone();
    this.request.setAttribute("apply", localApply);
    if ((localApply.getOperateType().equals("add")) || (localApply.getOperateType().equals("update"))) {
      this.entity = Serialize.deSerializeFromXml(((Apply)this.entity).getContent());
    }
    return "success";
  }
  
  public String auditDetails()
  {
    this.logger.debug("enter auditDetails");
    this.entity = getService().get(this.entity);
    Audit localAudit = (Audit)this.entity.clone();
    this.request.setAttribute("audit", localAudit);
    this.entity = Serialize.deSerializeFromXml(((Audit)this.entity).getApply().getContent());
    return "success";
  }
  
  public String rejectApply()
  {
    String str = this.request.getParameter("applyID");
    if (str == null)
    {
      addReturnValue(-1, 130901L);
      return "success";
    }
    Long localLong = Long.valueOf(Tools.strToLong(str, -99L));
    if (localLong.longValue() == -99L)
    {
      addReturnValue(-1, 130902L, new Object[] { str });
      return "success";
    }
    Apply localApply = new Apply();
    localApply.setId(localLong);
    localApply = (Apply)getService().get(localApply);
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    this.applyAndAuditService.rejectApply(localApply, localUser.getUserId());
    addReturnValue(1, 110901L, new Object[] { str });
    return "success";
  }
  
  public String withdrawApply()
  {
    String str = this.request.getParameter("applyID");
    if (str == null)
    {
      addReturnValue(-1, 130903L);
      return "success";
    }
    Long localLong = Long.valueOf(Tools.strToLong(str, -99L));
    if (localLong.longValue() == -99L)
    {
      addReturnValue(-1, 130904L, new Object[] { str });
      return "success";
    }
    Apply localApply = new Apply();
    localApply.setId(localLong);
    localApply = (Apply)getService().get(localApply);
    localApply.setStatus(Integer.valueOf(3));
    localApply.setModTime(new Date());
    getService().update(localApply);
    addReturnValue(1, 110902L, new Object[] { str });
    return "success";
  }
  
  public String passApply()
    throws SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException
  {
    String str = this.request.getParameter("applyID");
    if (str == null)
    {
      addReturnValue(-1, 130905L);
      return "success";
    }
    Long localLong = Long.valueOf(Tools.strToLong(str, -99L));
    if (localLong.longValue() == -99L)
    {
      addReturnValue(-1, 130906L, new Object[] { str });
      return "success";
    }
    Apply localApply = new Apply();
    localApply.setId(localLong);
    localApply = (Apply)getService().get(localApply);
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    this.applyAndAuditService.passApply(localApply, localUser.getUserId());
    addReturnValue(1, 110903L, new Object[] { str });
    return "success";
  }
}
