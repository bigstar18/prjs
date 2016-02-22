package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.SpecialMember;
import gnnt.MEBS.account.model.SpecialMemberStatus;
import gnnt.MEBS.account.service.SpecialMemberStatusService;
import gnnt.MEBS.account.service.SpecialMemberVOService;
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
public class SpecialMemberStatusAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberStatusAction.class);
  @Autowired
  @Qualifier("specialMemberStatusService")
  private SpecialMemberStatusService specialMemberStatusService;
  @Autowired
  @Qualifier("specialMemberVOService")
  private SpecialMemberVOService specialMemberVOService;
  @Resource(name="memberStatusMap")
  private Map firmStatusMap;
  private String status;
  @Resource(name="openstatusMap")
  private Map openstatusMap;
  
  public SpecialMemberVOService getSpecialMemberVOService()
  {
    return this.specialMemberVOService;
  }
  
  public Map getFirmStatusMap()
  {
    return this.firmStatusMap;
  }
  
  public InService getService()
  {
    return this.specialMemberStatusService;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public Map getOpenstatusMap()
  {
    return this.openstatusMap;
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
    this.resultList = this.specialMemberVOService.getList(qc, pageInfo);
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
    SpecialMember specialMember1 = 
      (SpecialMember)this.specialMemberStatusService.getById(((SpecialMember)this.obj).getId());
    resultValue = this.specialMemberStatusService.update((SpecialMember)this.obj);
    try
    {
      if (resultValue > 0)
      {
        AgencyRMIBean remObject = new AgencyRMIBean(this.request);
        if ("U".equals(specialMember1.getSpecialMemberStatus()
          .getStatus())) {
          if ("N".equals(((SpecialMember)this.obj)
            .getSpecialMemberStatus().getStatus())) {
            remObject.loadOneMember2Queue(specialMember1
              .getS_memberNo());
          }
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
