package gnnt.MEBS.broke.action;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.broke.model.MemberInfoOnly;
import gnnt.MEBS.broke.service.MemberInfoTreeService;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("memberInfoTreeAction")
@Scope("request")
public class MemberInfoTreeAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MemberInfoTreeAction.class);
  @Autowired
  @Qualifier("memberInfoTreeService")
  private MemberInfoTreeService memberInfoTreeService;
  
  public InService getService()
  {
    return this.memberInfoTreeService;
  }
  
  public String treeString()
  {
    this.logger.debug("enter list");
    String isRelated = this.request.getParameter("isRelated");
    this.logger.debug("isRelated:" + isRelated);
    this.request.setAttribute("isRelated", isRelated);
    String treeString = this.memberInfoTreeService.treeString();
    this.request.setAttribute("treeString", treeString);
    return getReturnValue();
  }
  
  public String treeForMemberInfo()
  {
    this.logger.debug("enter list");
    String treeString = "";
    String isRelated = this.request.getParameter("isRelated");
    this.logger.debug("isRelated:" + isRelated);
    String organizationNo = (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID);
    treeString = "[" + this.memberInfoTreeService.zTreeForMemberInfo(organizationNo, (String)this.request.getSession().getAttribute(ActionConstant.REGISTERID)) + "]";
    this.request.setAttribute("treeString", treeString);
    this.request.setAttribute("isRelated", isRelated);
    return getReturnValue();
  }
  
  public String treeStringSyn()
  {
    this.logger.debug("enter list");
    PageInfo pageInfo = new PageInfo(1, 1000000, "id", false);
    String tree = this.request.getParameter("tree");
    String isRelated = this.request.getParameter("isRelated");
    this.logger.debug("isRelated:" + isRelated);
    this.request.setAttribute("isRelated", isRelated);
    List<MemberInfoOnly> list = this.memberInfoTreeService.getMemberInfoTreeList(null, pageInfo);
    this.logger.debug("list  size:" + list.size());
    String treeString = "";
    if ((tree == null) || ("".equals(tree))) {
      for (MemberInfoOnly m : list) {
        treeString = treeString + m.forTreeSyn();
      }
    } else {
      treeString = tree;
    }
    this.request.setAttribute("treeString", treeString);
    this.request.setAttribute("tree", this.request.getParameter("tree"));
    return getReturnValue();
  }
  
  public String zTreeString()
  {
    this.logger.debug("enter list");
    String isRelated = this.request.getParameter("isRelated");
    this.logger.debug("isRelated:" + isRelated);
    this.request.setAttribute("isRelated", isRelated);
    String treeString = "[" + this.memberInfoTreeService.zTreeForAllMemberInfo() + "]";
    this.request.setAttribute("treeString", treeString);
    return getReturnValue();
  }
}
