package gnnt.MEBS.firm.service;

import gnnt.MEBS.account.model.CompMember;
import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.broke.model.CustomerMappingBroker;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.firm.action.FirmController;
import gnnt.MEBS.firm.common.MessageProperties;
import gnnt.MEBS.firm.common.SysData;
import gnnt.MEBS.firm.dao.FirmDao;
import gnnt.MEBS.firm.dao.ZSFirmDao;
import gnnt.MEBS.firm.model.CustomerInfoAudit;
import gnnt.MEBS.firm.model.MessageBean;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.ReturnValue;
import java.io.PrintStream;
import java.rmi.Naming;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("m_firmService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class FirmService
{
  private final transient Log logger = LogFactory.getLog(FirmController.class);
  @Autowired
  @Qualifier("m_firmDao")
  private FirmDao firmDao;
  @Autowired
  @Qualifier("m_zsfirmDao")
  private ZSFirmDao zsfirmDao;
  @Autowired
  @Qualifier("messageBean")
  private MessageBean messageBean;
  
  public boolean checkMemberStatue(String memberNo)
  {
    MemberInfo memberInfo = this.zsfirmDao.getById(memberNo);
    boolean flag = false;
    if ((memberInfo != null) && (
      ("N".equals(memberInfo.getCompMember().getStatus())) || ("F".equals(memberInfo.getCompMember().getStatus())))) {
      flag = true;
    }
    return flag;
  }
  
  public int goZSSuccess(String moNiCustomNo, String organization, String broker, Customer customer, CustomerMappingBroker customerMappingBroker)
  {
    int n = 0;
    try
    {
      n = addZS(organization, broker, customer, customerMappingBroker, broker, broker);
      insertCustomNo(moNiCustomNo);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return n;
  }
  
  public Map getMemberInfors(String memberNo)
  {
    return this.zsfirmDao.getMemberInfors(memberNo);
  }
  
  public Map getMemberInforName(String memberNo)
  {
    return this.firmDao.getMemberInfors(memberNo);
  }
  
  public int insertCustomNo(String moNiCustomNo)
  {
    return this.firmDao.insertCustomNo(moNiCustomNo);
  }
  
  public List checkCustomNo(String moNiCustomNo)
  {
    System.out.println("firmDao.checkCustomNo(moNiCustomNo)----------------------->" + this.firmDao.checkCustomNo(moNiCustomNo));
    return this.firmDao.checkCustomNo(moNiCustomNo);
  }
  
  public List checkMember(String memberNo)
  {
    return this.zsfirmDao.checkMember(memberNo);
  }
  
  public int insertCount(String bankid, String firmId, String bankAccout, String name, String cardNumber, String carType)
    throws Exception
  {
    ReturnValue rv = new ReturnValue();
    rv.result = 0L;
    this.zsfirmDao.insertFirm(firmId, name);
    this.zsfirmDao.insertFirmUser(firmId, name, cardNumber, carType);
    long id = this.zsfirmDao.insertCount(bankid, firmId, bankAccout, name, cardNumber, carType);
    
    CapitalProcessorRMI capitalProcessorRMI = (CapitalProcessorRMI)Naming.lookup(MessageProperties.getBankRMI());
    CorrespondValue cv = new CorrespondValue("010", firmId, bankAccout);
    cv.contact = firmId;
    cv.cardType = "1";
    cv.card = cardNumber;
    cv.accountName = name;
    rv = capitalProcessorRMI.openAccountMarket(cv);
    
    this.zsfirmDao.deleteCount(id);
    this.zsfirmDao.deleteFirmUser(firmId);
    this.zsfirmDao.deleteFirm(firmId);
    System.out.println("银行校验结果==========" + (int)rv.result + ";  rv.remark= " + rv.remark);
    return (int)rv.result;
  }
  
  public String getFirmId(String memNo, String organization, String broker)
  {
    return this.zsfirmDao.getFirmId(memNo, organization, broker);
  }
  
  public List<Organization> getOrganization(String memberNo)
  {
    return this.zsfirmDao.getOrganization(memberNo);
  }
  
  public List<Brokerage> getOrgBroker(String organizationno)
  {
    return this.zsfirmDao.getOrgBroker(organizationno);
  }
  
  public List getBroker(String memberNo)
  {
    return this.zsfirmDao.getBroker(memberNo);
  }
  
  public Map getCustomInfo(String customerno)
  {
    return this.firmDao.getCustomInfo(customerno);
  }
  
  public Map getCustomerinfor(String customerno)
  {
    return this.firmDao.getCustomerinfor(customerno);
  }
  
  public Map getMemberInfor(String memberNo)
  {
    return this.firmDao.getMemberInfor(memberNo);
  }
  
  public List getAgreements()
  {
    return this.firmDao.getAgreements();
  }
  
  public List getMemberIdAndName()
  {
    List list = this.firmDao.getMemberIdAndName();
    
    return list;
  }
  
  public String getMemberClobById(String id)
  {
    return this.firmDao.getMemberClobById(id);
  }
  
  public Map getMemberForBrokerage(String brokerage)
  {
    return this.zsfirmDao.getMemberForBrokerage(brokerage);
  }
  
  public void addAudit(CustomerInfoAudit audit)
  {
    this.firmDao.addAudit(audit);
  }
  
  public int add(Customer obj, CustomerMappingBroker customerMappingBroker, String openid, String nikeName)
  {
    this.logger.debug("enter add");
    int num = 0;
    String memberNo = obj.getMemberNo();
    System.out.println(obj.getCustomerNo());
    MemberInfo memberInfo = this.firmDao.getById(memberNo);
    if (memberInfo != null) {
      if (("N".equals(memberInfo.getCompMember().getStatus())) || 
        ("F".equals(memberInfo.getCompMember().getStatus())))
      {
        String customerNo = "";
        
        this.firmDao.lockUserRole();
        
        MessageBean messageBean = (MessageBean)SysData.getBean("messageBean");
        String accountNum = this.firmDao.getMaxNoByMemNo(obj.getMemberNo());
        if (accountNum.length() <= messageBean.getAccountNum())
        {
          while (accountNum.length() < messageBean.getAccountNum()) {
            accountNum = "0" + accountNum;
          }
          customerNo = obj.getMemberNo() + accountNum;
        }
        else
        {
          do
          {
            accountNum = (Math.random() * 1000000000000.0D + 1.0D);
            customerNo = obj.getMemberNo() + accountNum;
          } while (
          

            this.firmDao.getFirm(customerNo));
        }
        obj.setCustomerNo(customerNo);
        this.firmDao.addCustomer(obj);
        



        this.firmDao.addCustomerInfoPro(obj.getCustomerNo());
        this.firmDao.customerAddToPwd(obj);
        customerMappingBroker.setCustomerNo(customerNo);
        if (this.firmDao.getCustomerMappingBrokerById(customerMappingBroker.getCustomerNo()) == null) {
          this.firmDao.addCustomerMappingBroker(customerMappingBroker);
        } else {
          this.firmDao.updateCustomerMappingBroker(customerMappingBroker);
        }
        num = 2;
        
        this.firmDao.addFrimBindingInfo(customerNo, openid, nikeName);
      }
      else
      {
        num = -600;
      }
    }
    return num;
  }
  
  public int addZS(String organization, String broker, Customer obj, CustomerMappingBroker customerMappingBroker, String openid, String nikeName)
  {
    this.logger.debug("enter addZS");
    int num = 0;
    String memberNo = obj.getMemberNo();
    MemberInfo memberInfo = this.zsfirmDao.getById(memberNo);
    if (memberInfo != null) {
      if (("N".equals(memberInfo.getCompMember().getStatus())) || 
        ("F".equals(memberInfo.getCompMember().getStatus())))
      {
        String customerNo = "";
        this.zsfirmDao.lockUserRole();
        
        MessageBean messageBean = (MessageBean)SysData.getBean("messageBean");
        String accountNum = this.zsfirmDao.getMaxNoByMemNo(obj.getMemberNo());
        if (accountNum.length() <= messageBean.getAccountNum())
        {
          while (accountNum.length() < messageBean.getAccountNum()) {
            accountNum = "0" + accountNum;
          }
          customerNo = obj.getMemberNo() + accountNum;
        }
        else
        {
          do
          {
            accountNum = (Math.random() * 1000000000000.0D + 1.0D);
            customerNo = obj.getMemberNo() + accountNum;
          } while (
          

            this.firmDao.getFirm(customerNo));
        }
        obj.setCustomerNo(customerNo);
        customerNo = obj.getCustomerNo();
        obj.setCustomerNo(customerNo);
        this.zsfirmDao.addCustomer(obj);
        


        this.zsfirmDao.addCustomerInfoPro(obj.getCustomerNo());
        this.zsfirmDao.customerAddToPwd(obj);
        customerMappingBroker.setCustomerNo(customerNo);
        if (this.zsfirmDao.getCustomerMappingBrokerById(customerMappingBroker.getCustomerNo()) == null) {
          this.zsfirmDao.addCustomerMappingBroker(customerMappingBroker);
        } else {
          this.zsfirmDao.updateCustomerMappingBroker(customerMappingBroker);
        }
        num = 2;
        
        this.zsfirmDao.addFrimBindingInfo(customerNo, openid, nikeName);
      }
      else
      {
        num = -600;
      }
    }
    return num;
  }
  
  private void updateCustomerMappingBroker(CustomerMappingBroker customerMappingBroker)
  {
    if (this.firmDao.getCustomerMappingBrokerById(customerMappingBroker.getCustomerNo()) == null) {
      this.firmDao.addCustomerMappingBroker(customerMappingBroker);
    } else {
      this.firmDao.updateCustomerMappingBroker(customerMappingBroker);
    }
  }
  
  public boolean checkAccountNum(String bankaccount)
  {
    return this.zsfirmDao.checkAccountNum(bankaccount);
  }
  
  public boolean checkedCardNumber(String cardNumber, String memNo)
  {
    return this.firmDao.checkedCardNumber(cardNumber, memNo);
  }
  
  public boolean zscheckedCardNumber(String cardNumber, String memNo)
  {
    return this.zsfirmDao.checkedCardNumber(cardNumber, memNo);
  }
  
  public List getAllBank()
  {
    return this.zsfirmDao.getAllBank();
  }
  
  public boolean checkedContacterPhoneNo(String ContacterPhoneNo)
  {
    return this.firmDao.ContacterPhoneNo(ContacterPhoneNo);
  }
  
  public boolean PersonCode(String memberNo, String personNo)
  {
    return this.zsfirmDao.PersonCode(memberNo, personNo);
  }
  
  public boolean checkedZSContacterPhoneNo(String ContacterPhoneNo)
  {
    System.out.println("?????????--------->" + this.zsfirmDao.ContacterPhoneNo(ContacterPhoneNo));
    return this.zsfirmDao.ContacterPhoneNo(ContacterPhoneNo);
  }
  
  public String createCustomerInfoId(String memberId)
  {
    String firmid = "";
    Random ran = new Random();
    StringBuffer sb = new StringBuffer();
    int i = 0;
    boolean a = true;
    do
    {
      while (i < 15 - memberId.length())
      {
        int t = ran.nextInt(10);
        sb.append(t);
        i++;
      }
      firmid = memberId + sb.toString();
      a = this.firmDao.getFirm(firmid);
    } while (
    






      a);
    return firmid;
  }
  
  public String createCustomerNoByMemNo(String memNo)
  {
    String maxCustomerNoString = this.firmDao.getMaxNoByMemNo(memNo);
    String customerNo = "";
    if (!maxCustomerNoString.equals("000000000000000"))
    {
      long temp = new Long(maxCustomerNoString).longValue();
      this.logger.debug(Long.valueOf(temp));
      temp += 1L;
      String tempsString = new Long(temp).toString();
      customerNo = memNo + tempsString.substring(tempsString.length() - 12, tempsString.length());
      this.logger.error(customerNo);
    }
    else
    {
      Random ran = new Random();
      StringBuffer sb = new StringBuffer();
      int i = 0;
      boolean a = true;
      do
      {
        while (i < 15 - memNo.length())
        {
          int t = ran.nextInt(10);
          sb.append(t);
          i++;
        }
        customerNo = memNo + sb.toString();
        a = this.firmDao.getFirm(customerNo);
      } while (
      






        a);
    }
    return customerNo;
  }
  
  public String getIpAddr(HttpServletRequest request)
  {
    String ipAddress = null;
    ipAddress = request.getRemoteAddr();
    ipAddress = request.getHeader("x-forwarded-for");
    if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
      ipAddress = request.getHeader("Proxy-Client-IP");
    }
    if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
      ipAddress = request.getHeader("WL-Proxy-Client-IP");
    }
    if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
      ipAddress = request.getRemoteAddr();
    }
    if ((ipAddress != null) && (ipAddress.length() > 15) && 
      (ipAddress.indexOf(",") > 0)) {
      ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
    }
    return ipAddress;
  }
  
  public boolean checkCustomerNo(String id)
  {
    return this.firmDao.getFirm(id);
  }
  
  public int deleteAccount(String firmId)
  {
    return this.firmDao.deleteAccount(firmId);
  }
  
  public int addFrimBindingInfo(String customerNo, String wxno, String wxName)
  {
    return this.firmDao.addFrimBindingInfo(customerNo, wxno, wxName);
  }
  
  public List getFrimBindingInfo(String wxNo)
  {
    return this.firmDao.getFrimBindingInfo(wxNo);
  }
}
