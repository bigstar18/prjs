package gnnt.MEBS.announcement.action;

import gnnt.MEBS.announcement.model.Notice;
import gnnt.MEBS.announcement.service.NoticeService;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "okNotice.id";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "true";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    if (qc == null) {
      qc = new QueryConditions();
    }
    qc.addCondition("notice.expiryTime", ">=", new Date());
    this.noticeList = this.noticeService.getOKNoticeList(qc, pageInfo);
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    return getReturnValue();
  }
  
  public String viewById()
  {
    this.obj = this.noticeService.get(this.obj, this.okoticeId);
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
