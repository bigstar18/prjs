package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.service.CustomerService;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.account.service.SpecialMemberService;
import gnnt.MEBS.audit.model.Apply;
import gnnt.MEBS.audit.model.ApplyView;
import gnnt.MEBS.audit.service.ApplyService;
import gnnt.MEBS.audit.service.ApplyViewService;
import gnnt.MEBS.bankadded.service.MoneyIntoService;
import gnnt.MEBS.base.copy.XmlToMap;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.service.CommodityDelayTradeService;
import gnnt.MEBS.commodity.service.CommodityService;
import gnnt.MEBS.commodity.service.CustomerHoldQtyService;
import gnnt.MEBS.commodity.service.CustomerMarginService;
import gnnt.MEBS.commodity.service.CustomerTakeFeeService;
import gnnt.MEBS.commodity.service.MemCustomerDelayTradeService;
import gnnt.MEBS.commodity.service.MemCustomerTakeFeeService;
import gnnt.MEBS.commodity.service.MemberDelayFeeService;
import gnnt.MEBS.commodity.service.MemberFundsLadderService;
import gnnt.MEBS.commodity.service.MemberHoldQtyService;
import gnnt.MEBS.commodity.service.MemberMarginService;
import gnnt.MEBS.commodity.service.MemberQuotePointService;
import gnnt.MEBS.commodity.service.MemberTradeAuthService;
import gnnt.MEBS.commodity.service.SpecialMemberDelayFeeService;
import gnnt.MEBS.commodity.service.SpecialMemberQuotePointService;
import gnnt.MEBS.commodity.service.SpecialMemberTradeAuthService;
import gnnt.MEBS.commodity.service.SpecialTakeFeeService;
import gnnt.MEBS.commodity.service.TakeFeeService;
import gnnt.MEBS.commodity.service.TraderTradeAuthService;
import gnnt.MEBS.common.service.RoleService;
import gnnt.MEBS.common.service.UserService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.settlement.service.FirmService;
import gnnt.MEBS.trade.service.TradeTimeService;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class CheckIdAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CheckIdAction.class);
  @Autowired
  @Qualifier("applyService")
  private ApplyService applyService;
  @Autowired
  @Qualifier("applyViewService")
  protected ApplyViewService applyViewService;
  @Autowired
  @Qualifier("moneyIntoService")
  private MoneyIntoService moneyIntoService;
  @Autowired
  @Qualifier("customerService")
  private CustomerService customerService;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  @Autowired
  @Qualifier("specialMemberService")
  private SpecialMemberService specialMemberService;
  @Autowired
  @Qualifier("tradeTimeService")
  private TradeTimeService tradeTimeService;
  @Autowired
  @Qualifier("userService")
  private UserService userService;
  @Autowired
  @Qualifier("roleService")
  private RoleService roleService;
  @Autowired
  @Qualifier("firmService")
  private FirmService firmService;
  @Autowired
  @Qualifier("memberMarginService")
  private MemberMarginService memberMarginService;
  @Autowired
  @Qualifier("takeFeeService")
  private TakeFeeService takeFeeService;
  @Autowired
  @Qualifier("specialTakeFeeService")
  private SpecialTakeFeeService specialTakeFeeService;
  @Autowired
  @Qualifier("customerTakeFeeService")
  private CustomerTakeFeeService customerTakeFeeService;
  @Autowired
  @Qualifier("memCustomerTakeFeeService")
  private MemCustomerTakeFeeService memCustomerTakeFeeService;
  @Autowired
  @Qualifier("memberDelayFeeService")
  private MemberDelayFeeService memberDelayFeeService;
  @Autowired
  @Qualifier("specialMemberDelayFeeService")
  private SpecialMemberDelayFeeService specialMemberDelayFeeService;
  @Autowired
  @Qualifier("memberQuotePointService")
  private MemberQuotePointService memberQuotePointService;
  @Autowired
  @Qualifier("specialMemberQuotePointService")
  private SpecialMemberQuotePointService specialMemberQuotePointService;
  @Autowired
  @Qualifier("memberHoldQtyService")
  private MemberHoldQtyService memberHoldQtyService;
  @Autowired
  @Qualifier("customerHoldQtyService")
  private CustomerHoldQtyService customerHoldQtyService;
  @Autowired
  @Qualifier("memberTradeAuthService")
  private MemberTradeAuthService memberTradeAuthService;
  @Autowired
  @Qualifier("specialMemberTradeAuthService")
  private SpecialMemberTradeAuthService specialMemberTradeAuthService;
  @Autowired
  @Qualifier("traderTradeAuthService")
  private TraderTradeAuthService traderTradeAuthService;
  @Autowired
  @Qualifier("memberFundsLadderService")
  private MemberFundsLadderService memberFundsLadderService;
  @Autowired
  @Qualifier("commodityService")
  private CommodityService commodityService;
  @Autowired
  @Qualifier("customerMarginService")
  private CustomerMarginService customerMarginService;
  @Autowired
  @Qualifier("commodityDelayTradeService")
  private CommodityDelayTradeService commodityDelayTradeService;
  @Autowired
  @Qualifier("memCustomerDelayTradeService")
  private MemCustomerDelayTradeService memCustomerDelayTradeService;
  protected String applyType;
  
  public void setApplyType(String applyType)
  {
    this.applyType = applyType;
  }
  
  public String getApplyType()
  {
    return this.applyType;
  }
  
  public FirmService getFirmService()
  {
    return this.firmService;
  }
  
  public RoleService getRoleService()
  {
    return this.roleService;
  }
  
  public UserService getUserService()
  {
    return this.userService;
  }
  
  public CommodityService getCommodityService()
  {
    return this.commodityService;
  }
  
  public SpecialMemberService getSpecialMemberService()
  {
    return this.specialMemberService;
  }
  
  public MemberInfoService getMemberInfoService()
  {
    return this.memberInfoService;
  }
  
  public CustomerService getCustomerService()
  {
    return this.customerService;
  }
  
  public MoneyIntoService getMoneyIntoService()
  {
    return this.moneyIntoService;
  }
  
  public void setMoneyIntoService(MoneyIntoService moneyIntoService)
  {
    this.moneyIntoService = moneyIntoService;
  }
  
  public boolean existCustomerPapers(String papersName, String papersType, String customerNo)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.papersName", "=", papersName);
    qc.addCondition("primary.papersType", "=", Integer.valueOf(Integer.parseInt(papersType)));
    if (!"".equals(customerNo)) {
      qc.addCondition("primary.customerNo", "!=", customerNo);
    }
    List list = null;
    list = this.customerService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existMemberPapers(String papersName, String papersType, String memberNo)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.papersName", "=", papersName);
    qc.addCondition("primary.papersType", "=", Integer.valueOf(Integer.parseInt(papersType)));
    if (!"".equals(memberNo)) {
      qc.addCondition("primary.id", "!=", memberNo);
    }
    List list = null;
    list = this.memberInfoService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existId(String id)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.firmId", "=", id);
    List list = this.firmService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existUserId(String userId)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("userId", "=", userId);
    List list = this.userService.getUserList(qc, null, false, false, false);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existRoleId(String RoleId)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.id", "=", Long.valueOf(Long.parseLong(RoleId)));
    List list = this.roleService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existUserName(String name)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.name", "=", name);
    List list = this.userService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existRoleName(String name)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.name", "=", name);
    List list = this.roleService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existMemberName(String name)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.name", "=", name);
    List list = this.memberInfoService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existSpecialMemberName(String name)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.name", "=", name);
    List list = this.specialMemberService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existSectionid(Long sectionid)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.sectionId", "=", sectionid);
    List list = this.tradeTimeService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existFirmId(String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.customerNo", "=", firmId);
    List list = this.customerService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public ApplyView existApplyAdd(String commodityId, String firmId, String applyType)
  {
    ApplyView applyView = null;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("applyType", "=", applyType);
    qc.addCondition("status", "=", Integer.valueOf(1));
    List<Apply> applyList = this.applyService.getList(qc, null);
    if (applyList.size() > 0)
    {
      Apply a = null;
      for (Apply apply1 : applyList)
      {
        String xml = apply1.getContent();
        Map<String, String> map1 = XmlToMap.xmlToMap(xml);
        String commodityIdXML = (String)map1.get("commodityId");
        String firmIdXML = (String)map1.get("firmId");
        if ((commodityId.equals(commodityIdXML)) && (firmId.equals(firmIdXML)))
        {
          a = apply1;
          break;
        }
      }
      if (a != null) {
        applyView = (ApplyView)this.applyViewService.getById(a.getId());
      }
    }
    return applyView;
  }
  
  public ApplyView existApplyAddAnOther(String commodityId, String firmId, String applyType)
  {
    ApplyView applyView = null;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("applyType", "=", applyType);
    qc.addCondition("status", "=", Integer.valueOf(1));
    List<Apply> applyList = this.applyService.getList(qc, null);
    if (applyList.size() > 0)
    {
      Apply a = null;
      for (Apply apply1 : applyList)
      {
        String xml = apply1.getContent();
        Map<String, String> map1 = XmlToMap.xmlToMap(xml);
        String commodityIdXML = (String)map1.get("commodityId");
        String firmIdXML = (String)map1.get("m_FirmId");
        if ((commodityId.equals(commodityIdXML)) && (firmId.equals(firmIdXML)))
        {
          a = apply1;
          break;
        }
      }
      if (a != null) {
        applyView = (ApplyView)this.applyViewService.getById(a.getId());
      }
    }
    return applyView;
  }
  
  public ApplyView existApplyAddThread(String commodityId, String firmId, String applyType)
  {
    ApplyView applyView = null;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("applyType", "=", applyType);
    qc.addCondition("status", "=", Integer.valueOf(1));
    List<Apply> applyList = this.applyService.getList(qc, null);
    if (applyList.size() > 0)
    {
      Apply a = null;
      for (Apply apply1 : applyList)
      {
        String xml = apply1.getContent();
        Map<String, String> map1 = XmlToMap.xmlToMap(xml);
        String commodityIdXML = (String)map1.get("commodityId");
        String firmIdXML = (String)map1.get("m_firmId");
        if ((commodityId.equals(commodityIdXML)) && (firmId.equals(firmIdXML)))
        {
          a = apply1;
          break;
        }
      }
      if (a != null) {
        applyView = (ApplyView)this.applyViewService.getById(a.getId());
      }
    }
    return applyView;
  }
  
  public boolean existMemberMargin(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.firmId", "=", firmId);
    
    List list = this.memberMarginService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existCustomerMargin(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.firmId", "=", firmId);
    
    List list = this.customerMarginService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existMemberTakeFee(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.m_FirmId", "=", firmId);
    
    List list = this.takeFeeService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public String getFirmNameByFirmId(String firmId)
  {
    String name = "";
    Customer customer = (Customer)this.customerService.getById(firmId);
    if (customer != null) {
      name = customer.getName();
    }
    return name;
  }
  
  public boolean existCustomerTakeFee(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.m_FirmId", "=", firmId);
    
    List list = this.customerTakeFeeService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existMemCustomerTakeFee(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.m_FirmId", "=", firmId);
    

    List list = this.memCustomerTakeFeeService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existSpecialMemberTakeFee(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.m_FirmId", "=", firmId);
    
    List list = this.specialTakeFeeService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existMemberDelayFee(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.firmId", "=", firmId);
    
    List list = this.memberDelayFeeService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existSpecialMemberDelayFee(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.firmId", "=", firmId);
    
    List list = this.specialMemberDelayFeeService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existMemberQuotePoint(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.m_firmId", "=", firmId);
    
    List list = this.memberQuotePointService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existSpecialMemberQuotePoint(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.m_firmId", "=", firmId);
    
    List list = this.specialMemberQuotePointService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existMemberHoldQuantily(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.firmId", "=", firmId);
    
    List list = this.memberHoldQtyService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existCustomerHoldQuantily(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.firmId", "=", firmId);
    
    List list = this.customerHoldQtyService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existMemberTradeAuth(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.firmId", "=", firmId);
    
    List list = this.memberTradeAuthService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existSpecialMemberTradeAuth(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.firmId", "=", firmId);
    
    List list = this.specialMemberTradeAuthService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existCustomerTradeAuth(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.firmId", "=", firmId);
    
    List list = this.traderTradeAuthService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existMemberFundsLadder(String memberNo)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.memberNo", "=", memberNo);
    
    List list = this.memberFundsLadderService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existCommodityId(String id)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.id", "=", id);
    
    List list = this.commodityService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existCommodityName(String name)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.name", "=", name);
    
    List list = this.commodityService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existCommodityNameSecond(String id, String name)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.id", "<>", id);
    qc.addCondition("primary.name", "=", name);
    
    List list = this.commodityService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public String getBankId(String bankId)
  {
    List list = this.moneyIntoService.getBankId(bankId);
    String bankids = "";
    if ((list != null) && (list.size() > 0))
    {
      for (int i = 0; i < list.size(); i++)
      {
        Map map = (Map)list.get(i);
        bankids = bankids + map.get("bankid");
        bankids = bankids + ";";
        bankids = bankids + map.get("bankName");
        bankids = bankids + ",";
      }
      bankids = bankids.substring(0, bankids.length() - 1);
    }
    return bankids;
  }
  
  public boolean existCommodityDelayTrade(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.f_FirmId", "=", firmId);
    
    List list = this.commodityDelayTradeService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existMemCustomerDelayTrade(String commodityId, String firmId)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", commodityId);
    qc.addCondition("primary.f_FirmId", "=", firmId);
    
    List list = this.memCustomerDelayTradeService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public ApplyView existDelayTradeApplyAdd(String commodityId, String firmId, String applyType)
  {
    ApplyView applyView = null;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("applyType", "=", applyType);
    qc.addCondition("status", "=", Integer.valueOf(1));
    List<Apply> applyList = this.applyService.getList(qc, null);
    if (applyList.size() > 0)
    {
      Apply a = null;
      for (Apply apply1 : applyList)
      {
        String xml = apply1.getContent();
        Map<String, String> map1 = XmlToMap.xmlToMap(xml);
        String commodityIdXML = (String)map1.get("commodityId");
        String firmIdXML = (String)map1.get("f_FirmId");
        if ((commodityId.equals(commodityIdXML)) && (firmId.equals(firmIdXML)))
        {
          a = apply1;
          break;
        }
      }
      if (a != null) {
        applyView = (ApplyView)this.applyViewService.getById(a.getId());
      }
    }
    return applyView;
  }
  
  public InService getService()
  {
    return this.memberInfoService;
  }
}
