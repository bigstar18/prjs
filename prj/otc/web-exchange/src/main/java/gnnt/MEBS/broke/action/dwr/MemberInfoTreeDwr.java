package gnnt.MEBS.broke.action.dwr;

import gnnt.MEBS.broke.service.MemberInfoTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class MemberInfoTreeDwr
{
  @Autowired
  @Qualifier("memberInfoTreeService")
  private MemberInfoTreeService memberInfoTreeService;
  
  public String treeForMemberInfo(String memberNo)
  {
    String treeString = "";
    treeString = this.memberInfoTreeService.treeForMemberInfo1(memberNo);
    return treeString;
  }
}
