package gnnt.MEBS.report.action;

import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MemberReportAction
  extends BaseReportAction
{
  private List<MemberInfo> memberInfoList = new ArrayList();
  private String oldMemberIds;
  @Resource(name="flagMap")
  private Map flagMap;
  @Resource(name="codeMap")
  private Map codeMap;
  @Resource(name="whetherMap")
  private Map whetherMap;
  @Resource(name="moldMap")
  private Map moldMap;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  
  public Map getFlagMap()
  {
    return this.flagMap;
  }
  
  public Map getCodeMap()
  {
    return this.codeMap;
  }
  
  public Map getWhetherMap()
  {
    return this.whetherMap;
  }
  
  public Map getMoldMap()
  {
    return this.moldMap;
  }
  
  public String getOldMemberIds()
  {
    return this.oldMemberIds;
  }
  
  public void setOldMemberIds(String oldMemberIds)
  {
    this.oldMemberIds = oldMemberIds;
  }
  
  public List<MemberInfo> getMemberInfoList()
  {
    return this.memberInfoList;
  }
  
  public String getMemberList()
  {
    PageInfo pageInfo = new PageInfo(ActionConstant.PAGEINFONO, ActionConstant.PAGEINFOSIZE, "primary.id", false);
    this.memberInfoList = this.memberInfoService.getList(null, 
      pageInfo);
    String date = strDate();
    this.request.setAttribute("date", date);
    return "success";
  }
}
