package gnnt.MEBS.announcement.action;

import gnnt.MEBS.announcement.model.MemberInfor;
import gnnt.MEBS.announcement.service.MemberInforService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
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
public class MemberInforAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(AnnouncementAction.class);
  @Autowired
  @Qualifier("memberInforService")
  private MemberInforService memberInforService;
  private MemberInfor memberInfor;
  
  public MemberInfor getMemberInfor()
  {
    return this.memberInfor;
  }
  
  public void setMemberInfor(MemberInfor memberInfor)
  {
    this.memberInfor = memberInfor;
  }
  
  public InService getService()
  {
    return this.memberInforService;
  }
  
  public String queryMemberInfor()
  {
    this.logger.debug("----enter queryMemberInfor----");
    String memberNumber = (String)this.request.getSession().getAttribute("CURRENUSERID");
    String[] subMemberNo = memberNumber.split("_");
    String memberNo = subMemberNo[0];
    this.memberInfor = this.memberInforService.queryMemberInfor(memberNo);
    this.request.setAttribute("memberNo", memberNo);
    this.request.setAttribute("memberInfor", this.memberInfor);
    return getReturnValue();
  }
  
  public String addMemberInfor()
  {
    this.logger.debug("----enter addMemberInfor----");
    this.memberInforService.add(this.memberInfor);
    return getReturnValue();
  }
  
  public String updateMemberInfor()
  {
    this.logger.debug("----enter updateMemberInfor----");
    String memberNo = this.memberInfor.getMemberNo();
    MemberInfor memberInfors = this.memberInforService.queryMemberInfor(memberNo);
    if (memberInfors.getMemberNo() == null) {
      this.memberInforService.addMemberInfor(this.memberInfor);
    } else {
      this.memberInforService.updateMemberInfor(this.memberInfor);
    }
    return getReturnValue();
  }
}
