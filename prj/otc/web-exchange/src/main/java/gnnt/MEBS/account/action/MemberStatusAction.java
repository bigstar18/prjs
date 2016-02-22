package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.CompMember;
import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.account.service.MemberInfoVOService;
import gnnt.MEBS.account.service.MemberStatusService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
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
public class MemberStatusAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MemberStatusAction.class);
  @Autowired
  @Qualifier("memberStatusService")
  private MemberStatusService memberStatusService;
  private String status;
  @Autowired
  @Qualifier("memberInfoVOService")
  private MemberInfoVOService memberInfoVOService;
  @Resource(name="openstatusMap")
  private Map openstatusMap;
  @Resource(name="memberStatusMap")
  private Map memberStatusMap;
  
  public InService getService()
  {
    return this.memberStatusService;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public MemberInfoVOService getMemberInfoVOService()
  {
    return this.memberInfoVOService;
  }
  
  public Map getOpenstatusMap()
  {
    return this.openstatusMap;
  }
  
  public Map getMemberStatusMap()
  {
    return this.memberStatusMap;
  }
  
  public String list()
  {
    String sortName = this.request.getParameter("sortName") != null ? this.request
      .getParameter("sortName") : "primary.id";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request
      .getParameter("sortOrder") : "false";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, 
      new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    this.resultList = this.memberInfoVOService.getList(qc, pageInfo);
    this.logger.debug("resultList  size:" + this.resultList.size());
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    this.obj = null;
    return getReturnValue();
  }
  
  public String update()
  {
    this.logger.debug("enter update");
    int resultValue = -1;
    MemberInfo memberInfo1 = 
      (MemberInfo)this.memberStatusService.getById(((MemberInfo)this.obj).getId());
    resultValue = this.memberStatusService.update((MemberInfo)this.obj);
    try
    {
      if (resultValue > 0)
      {
        AgencyRMIBean remObject = new AgencyRMIBean(this.request);
        if (("U".equals(memberInfo1.getCompMember().getStatus())) && 
          ("N".equals(((MemberInfo)this.obj).getCompMember()
          .getStatus()))) {
          remObject.loadOneMember2Queue(memberInfo1.getId());
        }
      }
      addResultMsg(this.request, resultValue);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.request.setAttribute(ActionConstant.RESULTMSG, e.getCause()
        .getMessage());
      this.request.setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(800));
    }
    return getReturnValue();
  }
}
