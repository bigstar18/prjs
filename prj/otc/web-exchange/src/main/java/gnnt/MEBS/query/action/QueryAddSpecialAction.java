package gnnt.MEBS.query.action;

import gnnt.MEBS.account.service.SpecialMemberService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class QueryAddSpecialAction
  extends QueryBaseAction
{
  private final transient Log logger = LogFactory.getLog(QueryAddSpecialAction.class);
  @Autowired
  @Qualifier("specialMemberService")
  private SpecialMemberService specialMemberService;
  
  public String list()
  {
    this.logger.debug("enter list");
    PageInfo pageInfo = new PageInfo(ActionConstant.PAGEINFONO, ActionConstant.PAGEINFOSIZE, "primary.name", false);
    List specialMemberList = this.specialMemberService.getList(null, pageInfo);
    this.request.setAttribute("specialMemberList", specialMemberList);
    super.list();
    return getReturnValue();
  }
}
