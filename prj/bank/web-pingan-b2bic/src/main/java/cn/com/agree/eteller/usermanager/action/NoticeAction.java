package cn.com.agree.eteller.usermanager.action;

import cn.com.agree.eteller.generic.action.GenericAction;
import cn.com.agree.eteller.generic.vo.DwzResponse;
import cn.com.agree.eteller.usermanager.persistence.Notice;
import cn.com.agree.eteller.usermanager.spring.NoticeManager;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

public class NoticeAction
  extends GenericAction
{
  private static final long serialVersionUID = 1L;
  @Resource(name="noticeManagerTarget")
  private NoticeManager noticeManager;
  private Notice notice;
  
  @Action(value="NoticeDisplay", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/usermanager/NoticeContent.jsp")})
  public String noticeDisplay()
    throws Exception
  {
    this.notice = ((Notice)this.noticeManager.getById(this.notice.getId()));
    return "success";
  }
  
  @Action(value="ModifyNoticeDisplay", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/usermanager/Notice.jsp")})
  public String modifyDisplay()
    throws Exception
  {
    this.notice = ((Notice)this.noticeManager.getById(this.notice.getId()));
    return "success";
  }
  
  @Action("ModifyNotice")
  public String modify()
    throws Exception
  {
    try
    {
      this.noticeManager.addOrModify(this.notice);
      this.dwzResp.successForward("修改成功");
    }
    catch (Exception e)
    {
      logger.error(e);
      e.printStackTrace();
      this.dwzResp.errorForward("修改失败");
    }
    return "dwz";
  }
  
  public Notice getNotice()
  {
    return this.notice;
  }
  
  public void setNotice(Notice notice)
  {
    this.notice = notice;
  }
}
