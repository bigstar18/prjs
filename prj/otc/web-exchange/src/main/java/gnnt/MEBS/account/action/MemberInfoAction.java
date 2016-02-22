package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.settlement.model.Firm;
import gnnt.MEBS.settlement.service.FirmService;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MemberInfoAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MemberInfoAction.class);
  private InService inService;
  @Autowired
  @Qualifier("firmService")
  private FirmService firmService;
  @Resource(name="accountMemberTypeMap")
  private Map accountMemberTypeMap;
  @Resource(name="memberStatusMap")
  private Map memberStatusMap;
  @Resource(name="accountPapersTypeMap")
  private Map accountPapersTypeMap;
  
  public InService getInService()
  {
    return this.inService;
  }
  
  public void setInService(InService inService)
  {
    this.inService = inService;
  }
  
  public FirmService getFirmService()
  {
    return this.firmService;
  }
  
  public Map getAccountPapersTypeMap()
  {
    return this.accountPapersTypeMap;
  }
  
  public InService getService()
  {
    return this.inService;
  }
  
  public Map getAccountMemberTypeMap()
  {
    return this.accountMemberTypeMap;
  }
  
  public Map getMemberStatusMap()
  {
    return this.memberStatusMap;
  }
  
  public String getMemberInfoList()
  {
    PageInfo pageInfo = new PageInfo(ActionConstant.PAGEINFONO, ActionConstant.PAGEINFOSIZE, "id", false);
    this.resultList = this.inService.getList(null, pageInfo);
    return getReturnValue();
  }
  
  public String updateName()
  {
    MemberInfo memberInfo = (MemberInfo)this.obj;
    Firm firm = (Firm)this.firmService.getById(memberInfo.getId());
    int resultValue = getService().update(memberInfo);
    if (resultValue > 0)
    {
      firm.setFirmName(memberInfo.getName());
      resultValue = this.firmService.update(firm);
    }
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
}
