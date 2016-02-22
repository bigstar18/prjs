package gnnt.MEBS.announcement.action;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.account.model.Trader;
import gnnt.MEBS.account.service.CustomerService;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.account.service.TraderService;
import gnnt.MEBS.announcement.service.HisNoticeService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.settlement.model.Firm;
import gnnt.MEBS.settlement.service.FirmService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class LookHisAnnouncementAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(LookHisAnnouncementAction.class);
  @Autowired
  @Qualifier("hisNoticeService")
  private HisNoticeService hisNoticeService;
  private Long okoticeId;
  @Autowired
  @Qualifier("firmService")
  private FirmService firmService;
  @Autowired
  @Qualifier("traderService")
  private TraderService traderService;
  @Autowired
  @Qualifier("customerService")
  private CustomerService customerService;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  
  public Long getOkoticeId()
  {
    return this.okoticeId;
  }
  
  public void setOkoticeId(Long okoticeId)
  {
    this.okoticeId = okoticeId;
  }
  
  public InService getService()
  {
    return this.hisNoticeService;
  }
  
  public String list()
  {
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "notice.expiryTime";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "true";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    if (qc == null) {
      qc = new QueryConditions();
    }
    Trader trader = (Trader)this.traderService.getList(new QueryConditions("traderID", "=", this.request.getSession().getAttribute("username")), null).get(0);
    Firm firm = (Firm)this.firmService.getById(trader.getFirmID());
    if ("C".equals(firm.getFirmType()))
    {
      if (((Customer)this.customerService.getById(firm.getFirmId())).getCreateTime() != null) {
        qc.addCondition("notice.expiryTime", ">=", ((Customer)this.customerService.getById(firm.getFirmId())).getCreateTime(), "date");
      }
      this.resultList = this.hisNoticeService.getCustomerOKNoticeList(qc, pageInfo, firm.getFirmId());
    }
    else if ("M".equals(firm.getFirmType()))
    {
      if (((MemberInfo)this.memberInfoService.getById(firm.getFirmId())).getCreateTime() != null) {
        qc.addCondition("notice.expiryTime", ">=", ((MemberInfo)this.memberInfoService.getById(firm.getFirmId())).getCreateTime(), "date");
      }
      this.resultList = this.hisNoticeService.getMemberOKNoticeList(qc, pageInfo, firm.getFirmId());
    }
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    return getReturnValue();
  }
}
