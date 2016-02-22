package gnnt.MEBS.announcement.dwr;

import gnnt.MEBS.announcement.model.Notice;
import gnnt.MEBS.announcement.service.NoticeService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.util.Calendar;
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
public class NoticePromptAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(NoticePromptAction.class);
  @Autowired
  @Qualifier("noticeService")
  private NoticeService noticeService;
  
  public InService getService()
  {
    return this.noticeService;
  }
  
  public long getMaxNoticeId()
  {
    HttpServletRequest request = (HttpServletRequest)ThreadStore.get(ThreadStoreConstant.REQUEST);
    long maxNoticeId = -1L;
    try
    {
      List<Notice> noticeList = null;
      String sortName = request.getParameter("sortName") != null ? request.getParameter("sortName") : "notice.id";
      String sortOrder = request.getParameter("sortOrder") != null ? request.getParameter("sortOrder") : "true";
      Map<String, Object> map = EcsideUtil.getQurey(request, sortName, new Boolean(sortOrder).booleanValue());
      PageInfo pageInfo = getPageInfo(map);
      QueryConditions qc = getQueryConditions(map);
      if (qc == null) {
        qc = new QueryConditions();
      }
      Calendar lastDate = Calendar.getInstance();
      lastDate.add(5, -1);
      qc.addCondition("notice.expiryTime", ">=", lastDate.getTime());
      
      noticeList = this.noticeService.getOrganizationOKNoticeList(qc, pageInfo, AclCtrl.getCurrenuserId(request));
      if ((noticeList != null) && (noticeList.size() > 0)) {
        maxNoticeId = ((Notice)noticeList.get(0)).getId().longValue();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return maxNoticeId;
  }
}
