package gnnt.MEBS.account.action;

import gnnt.MEBS.account.service.MemberInfoVOService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class MemberInfoVOAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MemberInfoVOAction.class);
  @Autowired
  @Qualifier("memberInfoVOService")
  private MemberInfoVOService memberInfoVOService;
  @Resource(name="openstatusMap")
  private Map openstatusMap;
  @Resource(name="accountMemberTypeMap")
  private Map accountMemberTypeMap;
  @Resource(name="memberStatusMap")
  private Map memberStatusMap;
  @Resource(name="accountPapersTypeMap")
  private Map accountPapersTypeMap;
  
  public Map getOpenstatusMap()
  {
    return this.openstatusMap;
  }
  
  public Map getAccountMemberTypeMap()
  {
    return this.accountMemberTypeMap;
  }
  
  public Map getMemberStatusMap()
  {
    return this.memberStatusMap;
  }
  
  public Map getAccountPapersTypeMap()
  {
    return this.accountPapersTypeMap;
  }
  
  public InService getService()
  {
    return this.memberInfoVOService;
  }
}
