package gnnt.MEBS.announcement.promptHandler;

import gnnt.MEBS.announcement.model.Notice;
import gnnt.MEBS.announcement.service.NoticeService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class NoticeHandler
  extends AbstractTypeHandler
{
  @Autowired
  @Qualifier("noticeService")
  private NoticeService noticeService;
  
  public int handle(Map promptMap, Long oldTradeNo)
  {
    HttpServletRequest request = (HttpServletRequest)ThreadStore.get(ThreadStoreConstant.REQUEST);
    int result = 0;
    long maxNoticeId = -1L;
    boolean flag = false;
    User user = (User)request.getSession().getAttribute("CURRENUSER");
    Set<Role> roleSet = user.getRoleSet();
    for (Role role : roleSet)
    {
      Set<Right> rightSet = role.getRightSet();
      for (Right right : rightSet) {
        if (303000L == right.getId().longValue())
        {
          flag = true;
          break;
        }
      }
    }
    if (flag)
    {
      List<Notice> noticeList = null;
      QueryConditions qc = new QueryConditions();
      PageInfo pageInfo = new PageInfo(1, 10000, "notice.id", true);
      Calendar lastDate = Calendar.getInstance();
      lastDate.add(5, -1);
      qc.addCondition("notice.expiryTime", ">=", lastDate.getTime());
      if ((request.getSession().getAttribute(ActionConstant.ORGANIZATIONID) != null) && (!"".equals(request.getSession().getAttribute(ActionConstant.ORGANIZATIONID))))
      {
        noticeList = this.noticeService.getOrganizationOKNoticeList(qc, pageInfo, AclCtrl.getUser(request).getId());
        if ((noticeList != null) && (noticeList.size() > 0)) {
          maxNoticeId = ((Notice)noticeList.get(0)).getId().longValue();
        }
      }
      else
      {
        noticeList = this.noticeService.getMemberOKNoticeList(qc, pageInfo, AclCtrl.getUser(request).getId());
        if ((noticeList != null) && (noticeList.size() > 0)) {
          maxNoticeId = ((Notice)noticeList.get(0)).getId().longValue();
        }
      }
    }
    promptMap.put("maxNoticeId", Long.valueOf(maxNoticeId));
    return result;
  }
}
