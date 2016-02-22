package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.account.model.MemberRelation;
import gnnt.MEBS.account.model.SpecialMember;
import gnnt.MEBS.account.service.CompMemberService;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.account.service.MemberRelationService;
import gnnt.MEBS.account.service.SpecialMemberService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class MemberChangeOrderAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MemberChangeOrderAction.class);
  @Autowired
  @Qualifier("specialMemberService")
  private SpecialMemberService specialMemberService;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  @Autowired
  @Qualifier("memberRelationService")
  private MemberRelationService memberRelationService;
  @Autowired
  @Qualifier("compMemberService")
  private CompMemberService compMemberService;
  
  public SpecialMemberService getSpecialMemberService()
  {
    return this.specialMemberService;
  }
  
  public MemberInfoService getMemberInfoService()
  {
    return this.memberInfoService;
  }
  
  public InService getService()
  {
    return this.memberRelationService;
  }
  
  public String list()
  {
    List<SpecialMember> specialList = this.specialMemberService.getList(new QueryConditions("primary.specialMemberStatus.status", "in", "('N','F')"), null);
    String memberId = this.request.getParameter("id");
    MemberInfo memberInfo = (MemberInfo)this.memberInfoService.getById(memberId);
    List<MemberRelation> memberRelationList = this.memberRelationService.getList(new QueryConditions("primary.memberNo", "=", memberInfo.getId()), 
      new PageInfo(1, 1000000, "primary.sortNo", false));
    String specialMemberNos = "";
    for (MemberRelation relation : memberRelationList) {
      specialMemberNos = specialMemberNos + relation.getS_MemberNo() + ",";
    }
    memberInfo.setSpecialMemberNos(specialMemberNos);
    this.obj = memberInfo;
    this.request.setAttribute("memberId", memberId);
    this.request.setAttribute("memberRelationList", memberRelationList);
    this.request.setAttribute("specialMemberList", specialList);
    return getReturnValue();
  }
  
  public String contactSpecialMember()
  {
    String memberId = this.request.getParameter("memberId");
    MemberInfo memberInfo = (MemberInfo)this.memberInfoService.getById(memberId);
    String selectSpecialMem = this.request.getParameter("selectSpecialMem");
    memberInfo.setSpecialMemberNos(selectSpecialMem);
    int resultValue = this.memberRelationService.contactSpecialMember(memberId, selectSpecialMem);
    this.obj = memberInfo;
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
}
