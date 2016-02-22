package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.MemberRelation;
import gnnt.MEBS.account.model.SpecialMember;
import gnnt.MEBS.account.model.Trader;
import gnnt.MEBS.account.service.MemberRelationService;
import gnnt.MEBS.account.service.SpecialMemberService;
import gnnt.MEBS.account.service.TraderService;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.settlement.model.Firm;
import gnnt.MEBS.settlement.service.FirmService;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class SpecialMemberAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberAction.class);
  @Autowired
  @Qualifier("specialMemberService")
  private SpecialMemberService specialMemberService;
  @Autowired
  @Qualifier("traderService")
  private TraderService traderService;
  @Autowired
  @Qualifier("memberRelationService")
  private MemberRelationService memberRelationService;
  @Autowired
  @Qualifier("firmService")
  private FirmService firmService;
  @Resource(name="accountPapersTypeMap")
  private Map accountPapersTypeMap;
  @Resource(name="memberStatusMap")
  private Map memberStatusMap;
  
  public FirmService getFirmService()
  {
    return this.firmService;
  }
  
  public Map getAccountPapersTypeMap()
  {
    return this.accountPapersTypeMap;
  }
  
  public Map getMemberStatusMap()
  {
    return this.memberStatusMap;
  }
  
  public InService getService()
  {
    return this.specialMemberService;
  }
  
  public String updateSpecialMemberStatus()
  {
    this.logger.debug("enter update");
    try
    {
      SpecialMember specialMember = (SpecialMember)this.obj;
      int resultValue = this.specialMemberService.updateSpecialMember(specialMember);
      addResultMsg(this.request, resultValue);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return getReturnValue();
  }
  
  public String updatePassword()
  {
    List<MemberRelation> memberRelationList = this.memberRelationService.getList(new QueryConditions("primary.s_MemberNo", "=", this.obj.getId()), null);
    Iterator localIterator2;
    for (Iterator localIterator1 = memberRelationList.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      MemberRelation memberRelation = (MemberRelation)localIterator1.next();
      List<Trader> list = this.traderService.getList(new QueryConditions("primary.firmID", "=", memberRelation.getS_MemberNo()), null);
      localIterator2 = list.iterator(); continue;Trader trader = (Trader)localIterator2.next();
      trader.setPassword(MD5.getMD5(trader.getPassword(), this.request.getParameter("password1")));
      this.traderService.update(trader);
    }
    addResultMsg(this.request, 1);
    return getReturnValue();
  }
  
  public String update()
  {
    SpecialMember specialMember = (SpecialMember)this.obj;
    Firm firm = (Firm)this.firmService.getById(specialMember.getId());
    int resultValue = getService().update(specialMember);
    if (resultValue > 0)
    {
      firm.setFirmName(specialMember.getName());
      resultValue = this.firmService.update(firm);
    }
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
}
