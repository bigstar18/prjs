package gnnt.MEBS.announcement.action;

import gnnt.MEBS.announcement.model.Notice;
import gnnt.MEBS.announcement.service.NoticeService;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class LookAnnouncementAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(LookAnnouncementAction.class);
  @Autowired
  @Qualifier("noticeService")
  private NoticeService noticeService;
  private List<Notice> noticeList = new ArrayList();
  private Long okoticeId;
  
  public Long getOkoticeId()
  {
    return this.okoticeId;
  }
  
  public void setOkoticeId(Long okoticeId)
  {
    this.okoticeId = okoticeId;
  }
  
  public List<Notice> getNoticeList()
  {
    return this.noticeList;
  }
  
  public String okNoticesList()
  {
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "notice.id";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "false";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    if (qc == null) {
      qc = new QueryConditions();
    }
    Calendar lastDate = Calendar.getInstance();
    lastDate.add(5, -1);
    qc.addCondition("notice.expiryTime", ">=", lastDate.getTime());
    if ((this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID) != null) && (!"".equals(this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID)))) {
      this.noticeList = this.noticeService.getOrganizationOKNoticeList(qc, pageInfo, AclCtrl.getUser(this.request).getId());
    } else {
      this.noticeList = this.noticeService.getMemberOKNoticeList(qc, pageInfo, AclCtrl.getUser(this.request).getId());
    }
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    return getReturnValue();
  }
  
  public String getById()
  {
    this.obj = this.noticeService.getById(this.obj.getId());
    return getReturnValue();
  }
  
  public InService getService()
  {
    return this.noticeService;
  }
}
