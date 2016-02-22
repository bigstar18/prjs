package gnnt.MEBS.announcement.action;

import gnnt.MEBS.announcement.service.HisNoticeService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
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
public class LookHisAnnouncementAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(LookHisAnnouncementAction.class);
  @Autowired
  @Qualifier("hisNoticeService")
  private HisNoticeService hisNoticeService;
  private Long okoticeId;
  
  public Long getOkoticeId()
  {
    return this.okoticeId;
  }
  
  public void setOkoticeId(Long okoticeId)
  {
    this.okoticeId = okoticeId;
  }
  
  public InService getService()
  {
    return this.hisNoticeService;
  }
  
  public String list()
  {
    String sortName = this.request.getParameter("sortName") != null ? this.request
      .getParameter("sortName") : "notice.id";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request
      .getParameter("sortOrder") : "false";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, 
      new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    this.resultList = this.hisNoticeService.getList(qc, pageInfo);
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    return getReturnValue();
  }
  
  public String viewById()
  {
    this.obj = this.hisNoticeService.get(this.obj, this.okoticeId);
    return getReturnValue();
  }
}
