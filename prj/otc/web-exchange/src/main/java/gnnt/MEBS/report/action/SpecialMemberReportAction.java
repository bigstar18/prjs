package gnnt.MEBS.report.action;

import gnnt.MEBS.account.model.SpecialMember;
import gnnt.MEBS.account.service.SpecialMemberService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SpecialMemberReportAction
  extends BaseReportAction
{
  private List<SpecialMember> resultList = new ArrayList();
  @Resource(name="flagMap")
  private Map flagMap;
  @Resource(name="codeMap")
  private Map codeMap;
  @Resource(name="moldMap")
  private Map moldMap;
  @Resource(name="whetherMap")
  private Map whetherMap;
  @Autowired
  @Qualifier("specialMemberService")
  private SpecialMemberService specialMemberService;
  
  public List<SpecialMember> getResultList()
  {
    return this.resultList;
  }
  
  public Map getFlagMap()
  {
    return this.flagMap;
  }
  
  public Map getCodeMap()
  {
    return this.codeMap;
  }
  
  public Map getMoldMap()
  {
    return this.moldMap;
  }
  
  public Map getWhetherMap()
  {
    return this.whetherMap;
  }
  
  public SpecialMemberService getSpecialMemberService()
  {
    return this.specialMemberService;
  }
  
  public String getSMemberList()
  {
    PageInfo pageInfo = new PageInfo(ActionConstant.PAGEINFONO, ActionConstant.PAGEINFOSIZE, "primary.s_memberNo", false);
    this.resultList = this.specialMemberService.getList(null, pageInfo);
    String date = strDate();
    this.request.setAttribute("date", date);
    return "success";
  }
}
