package gnnt.MEBS.integrated.front.action.noticeandmessage;

import gnnt.MEBS.common.front.action.StandardAction;
import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.common.QueryConditions;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.service.StandardService;
import gnnt.MEBS.common.front.statictools.ActionUtil;
import gnnt.MEBS.common.front.statictools.Tools;
import gnnt.MEBS.integrated.front.model.Message;
import gnnt.MEBS.integrated.front.model.Notice;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("noticeAndMessageAction")
@Scope("request")
public class NoticeAndMessageAction
  extends StandardAction
{
  private static final long serialVersionUID = -5223644608520869623L;
  
  public String noticeList()
  {
    this.logger.debug("查询公告列表");
    PageRequest localPageRequest = null;
    try
    {
      localPageRequest = ActionUtil.getPageRequest(this.request);
    }
    catch (Exception localException)
    {
      this.logger.error(Tools.getExceptionTrace(localException));
    }
    Page localPage = getService().getPage(localPageRequest, new Notice());
    this.request.setAttribute("pageInfo", localPage);
    this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));
    return "success";
  }
  
  public String noticeDetails()
  {
    this.logger.debug("查询公告明细");
    long l = -1000L;
    if (this.request.getParameter("noticeId") != null) {
      l = Tools.strToLong(this.request.getParameter("noticeId"), -1000L);
    }
    if (l > 0L)
    {
      ((Notice)this.entity).setNoticeId(Long.valueOf(l));
      this.entity = getService().get(this.entity);
      this.request.setAttribute("notice", this.entity);
    }
    return "success";
  }
  
  public String messageList()
  {
    this.logger.debug("查询信息列表");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    PageRequest localPageRequest = null;
    try
    {
      localPageRequest = ActionUtil.getPageRequest(this.request);
    }
    catch (Exception localException)
    {
      this.logger.error(Tools.getExceptionTrace(localException));
    }
    QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
    localQueryConditions.addCondition("recieverType", "=", Integer.valueOf(4));
    localQueryConditions.addCondition("traderID", "=", localUser.getTraderID());
    localPageRequest.setSortColumns(" order by createTime desc");
    Page localPage = getService().getPage(localPageRequest, new Message());
    this.request.setAttribute("pageInfo", localPage);
    this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));
    return "success";
  }
  
  public String messageDetails()
  {
    this.logger.debug("查询信息明细");
    long l = -1000L;
    if (this.request.getParameter("messageId") != null) {
      l = Tools.strToLong(this.request.getParameter("messageId"), -1000L);
    }
    if (l > 0L)
    {
      ((Message)this.entity).setMessageId(Long.valueOf(l));
      this.entity = getService().get(this.entity);
      this.request.setAttribute("message", this.entity);
    }
    return "success";
  }
}
