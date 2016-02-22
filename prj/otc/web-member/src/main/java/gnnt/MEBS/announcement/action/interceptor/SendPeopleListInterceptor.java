package gnnt.MEBS.announcement.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.account.model.SpecialMember;
import gnnt.MEBS.account.service.CustomerService;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.account.service.SpecialMemberService;
import gnnt.MEBS.announcement.model.Notice;
import gnnt.MEBS.announcement.service.NoticeService;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.broke.service.BrokerageService;
import gnnt.MEBS.broke.service.CustomerMappingService;
import gnnt.MEBS.broke.service.OrganizationService;
import gnnt.MEBS.config.constant.AnnouncementConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SendPeopleListInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(SendPeopleListInterceptor.class);
  @Autowired
  @Qualifier("noticeService")
  private NoticeService noticeService;
  @Autowired
  @Qualifier("specialMemberService")
  private SpecialMemberService specialMemberService;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  @Autowired
  @Qualifier("organizationService")
  private OrganizationService organizationService;
  @Autowired
  @Qualifier("brokerageService")
  private BrokerageService brokerageService;
  @Autowired
  @Qualifier("customerService")
  private CustomerService customerService;
  @Autowired
  @Qualifier("customerMappingService")
  private CustomerMappingService customerMappingService;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    String noticeId = request.getParameter("obj.id");
    Notice notice = (Notice)this.noticeService.getById(Long.valueOf(Long.parseLong(noticeId)));
    notice.setIsExecute("Y");
    this.noticeService.update(notice);
    String recipientRules = notice.getRecipientRules();
    List<Element> rangeList = paseXML(recipientRules);
    



    String specialMemberNames = "";
    String[] specialIds;
    if ((rangeList != null) && (rangeList.size() > 0)) {
      for (Element range : rangeList)
      {
        String specialMemeber = "";
        if (AnnouncementConstant.SPECIALMEMEBER.equals(range.attributeValue("type")))
        {
          Element group = range.element("group");
          if ((group != null) && ("Y".equalsIgnoreCase(group.attributeValue("isNotAllSpecialMemeber")))) {
            specialMemeber = group.elementText("specialMemeber");
          } else if ((group != null) && ("N".equalsIgnoreCase(group.attributeValue("isNotAllSpecialMemeber")))) {
            specialMemberNames = "全部特别会员";
          }
          if (!"".equals(specialMemeber))
          {
            specialIds = specialMemeber.split(",");
            for (String string : specialIds) {
              if ((!"".equals(string)) && (string != null) && (string.length() > 2))
              {
                if (string.contains("'")) {
                  string = string.substring(1, string.length() - 1);
                }
                SpecialMember specialMember = (SpecialMember)this.specialMemberService.getById(string);
                if (specialMember != null) {
                  specialMemberNames = specialMemberNames + specialMember.getName() + ",";
                }
              }
            }
          }
        }
      }
    }
    String memberNames = "";
    String organizationNames = "";
    String brokerageNames = "";
    Object memberIds;
    List<Brokerage> brokerageList;
    Brokerage brokerage2;
    if ((rangeList != null) && (rangeList.size() > 0)) {
      for (Element range : rangeList)
      {
        String member = "";
        String relation = "Y";
        if (AnnouncementConstant.MEMBER.equals(range.attributeValue("type")))
        {
          Element group = range.element("group");
          if ((group != null) && ("Y".equalsIgnoreCase(group.attributeValue("isNotAllMember")))) {
            member = group.elementText("member");
          } else if ((group != null) && ("N".equalsIgnoreCase(group.attributeValue("isNotAllMember")))) {
            memberNames = "全部会员";
          }
          relation = range.elementText("relation");
          if (!"".equals(member))
          {
            memberIds = member.split(",");
            for (String string : memberIds)
            {
              if ((!"".equals(string)) && (string != null) && (string.length() > 2))
              {
                if (string.contains("'")) {
                  string = string.substring(1, string.length() - 1);
                }
                if (!"N".equalsIgnoreCase(group.attributeValue("isNotAllMember")))
                {
                  MemberInfo memberForName = (MemberInfo)this.memberInfoService.getById(string);
                  memberNames = memberNames + memberForName.getName() + ",";
                  if ("N".equalsIgnoreCase(group.attributeValue("isNotAllMember"))) {
                    memberNames = "全部会员";
                  }
                }
              }
              if (("Y".equals(relation)) && (string.length() > 2))
              {
                if (string.contains("'")) {
                  string = string.substring(1, string.length() - 1);
                }
                if ((!"N".equalsIgnoreCase(group.attributeValue("isNotAllMember"))) && (!"Y".equals(relation)))
                {
                  List<Organization> organizationList = this.organizationService.getList(new QueryConditions("memberNo", "=", string), null);
                  for (Organization organization2 : organizationList) {
                    organizationNames = organizationNames + organization2.getName() + ",";
                  }
                }
                else
                {
                  organizationNames = "全部机构";
                }
              }
              if (("Y".equals(relation)) && (string.length() > 2))
              {
                if (string.contains("'")) {
                  string = string.substring(1, string.length() - 1);
                }
                if ((!"N".equalsIgnoreCase(group.attributeValue("isNotAllMember"))) && (!"Y".equals(relation)))
                {
                  brokerageList = this.brokerageService.getList(new QueryConditions("memberNo", "=", string), null);
                  for (??? = brokerageList.iterator(); ???.hasNext();)
                  {
                    brokerage2 = (Brokerage)???.next();
                    brokerageNames = brokerageNames + brokerage2.getName() + ",";
                  }
                }
                else
                {
                  brokerageNames = "全部居间";
                }
              }
            }
          }
          else if ("Y".equals(relation))
          {
            brokerageNames = "全部居间";
            organizationNames = "全部机构";
            memberNames = "全部会员";
          }
        }
      }
    }
    String memberBrokerageNames = "";
    String memberBrokerage = "";
    String brokerageRelation = "Y";
    String brokerageCustomerNames = "";
    Object localObject2;
    if ((rangeList != null) && (rangeList.size() > 0)) {
      for (memberIds = rangeList.iterator(); ((Iterator)memberIds).hasNext();)
      {
        Element range = (Element)((Iterator)memberIds).next();
        if (AnnouncementConstant.BROKER.equals(range.attributeValue("type")))
        {
          Element group = range.element("group");
          if ((group != null) && ("Y".equalsIgnoreCase(group.attributeValue("isNotAllBrokerage"))))
          {
            memberBrokerage = group.elementText("brokerage");
            brokerageRelation = group.elementText("relation");
          }
          else if ((group != null) && ("N".equalsIgnoreCase(group.attributeValue("isNotAllBrokerage"))))
          {
            Object brokerageList = this.brokerageService.getList(new QueryConditions("memberNo", "=", range.elementText("memberId")), null);
            if ((brokerageList != null) && (((List)brokerageList).size() > 0)) {
              for (??? = ((List)brokerageList).iterator(); ((Iterator)???).hasNext();)
              {
                Brokerage brokerage = (Brokerage)((Iterator)???).next();
                memberBrokerage = memberBrokerage + brokerage.getId() + ",";
              }
            }
          }
          String[] brokerageIds = memberBrokerage.split(",");
          brokerageList = (brokerage2 = brokerageIds).length;
          for (localObject2 = 0; localObject2 < brokerageList; localObject2++)
          {
            String string = brokerage2[localObject2];
            if ((!"".equals(string)) && (string != null))
            {
              if (string.contains("'")) {
                string = string.substring(1, string.length() - 1);
              }
              Brokerage brokerageForName = (Brokerage)this.brokerageService.getById(string);
              if (brokerageForName != null) {
                memberBrokerageNames = memberBrokerageNames + brokerageForName.getName() + ",";
              }
              if ("N".equalsIgnoreCase(group.attributeValue("isNotAllBrokerage"))) {
                memberBrokerageNames = range.elementText("memberId") + "下全部居间";
              }
            }
          }
          if ("Y".equals(brokerageRelation)) {
            brokerageCustomerNames = memberBrokerageNames;
          }
        }
      }
    }
    String memberOrganizationNames = "";
    String memberOrganization = "";
    String organizationRelation = "Y";
    String organizationCustomerNames = "";
    Object string;
    Organization organizationForName;
    if ((rangeList != null) && (rangeList.size() > 0)) {
      for (localObject2 = rangeList.iterator(); ((Iterator)localObject2).hasNext();)
      {
        Element range = (Element)((Iterator)localObject2).next();
        if (AnnouncementConstant.ORGANIZATION.equals(range.attributeValue("type")))
        {
          Element group = range.element("group");
          if ((group != null) && ("Y".equalsIgnoreCase(group.attributeValue("isNotAllOrganization"))))
          {
            memberOrganization = group.elementText("organization");
            organizationRelation = range.elementText("relation");
          }
          else if ((group != null) && ("N".equalsIgnoreCase(group.attributeValue("isNotAllOrganization"))))
          {
            organizationRelation = range.elementText("relation");
            





            memberOrganizationNames = range.elementText("memberId") + "下全部机构";
          }
          if (!"".equals(memberOrganization))
          {
            String[] organizationIds = memberOrganization.split(",");
            for (string : organizationIds) {
              if ((!"".equals(string)) && (string != null) && (((String)string).length() > 2))
              {
                if (((String)string).contains("'")) {
                  string = ((String)string).substring(1, ((String)string).length() - 1);
                }
                organizationForName = (Organization)this.organizationService.getById((Serializable)string);
                if (organizationForName != null) {
                  memberOrganizationNames = memberOrganizationNames + organizationForName.getName() + ",";
                }
                "N".equalsIgnoreCase(group.attributeValue("isNotAllOrganization"));
              }
            }
          }
          if ("Y".equals(organizationRelation)) {
            organizationCustomerNames = memberOrganizationNames;
          }
        }
      }
    }
    if ((rangeList != null) && (rangeList.size() > 0)) {
      for (localObject2 = rangeList.iterator(); ((Iterator)localObject2).hasNext();)
      {
        Element range = (Element)((Iterator)localObject2).next();
        if (AnnouncementConstant.TRADERMEMBERNOTICE.equals(range.attributeValue("type")))
        {
          Element group = range.element("group");
          if ((group != null) && ("Y".equalsIgnoreCase(group.attributeValue("isNotAllCustomers"))))
          {
            request.setAttribute("tradermembernoticeNames", group.elementText("trader"));
          }
          else if (range.elementText("memberId") != null)
          {
            MemberInfo memberForName = (MemberInfo)this.memberInfoService.getById(range.elementText("memberId"));
            request.setAttribute("tradermembernoticeNames", memberForName.getName() + "的全部客户");
          }
        }
      }
    }
    String customerNames = "";
    String memberForCustomer = "";
    String memberForCustomerNames = "";
    if ((rangeList != null) && (rangeList.size() > 0)) {
      for (string = rangeList.iterator(); ((Iterator)string).hasNext();)
      {
        Element range = (Element)((Iterator)string).next();
        if (AnnouncementConstant.TRADER.equals(range.attributeValue("type")))
        {
          Element group = range.element("group");
          if ((group != null) && ("Y".equalsIgnoreCase(group.attributeValue("isNotAllCustomers")))) {
            memberForCustomer = group.elementText("member");
          } else if ((group != null) && ("N".equalsIgnoreCase(group.attributeValue("isNotAllCustomers")))) {
            memberForCustomerNames = "全部";
          }
          MemberInfo memberForName;
          if (!"".equals(memberForCustomer))
          {
            String[] memberIds = memberForCustomer.split(",");
            String[] arrayOfString3;
            Organization localOrganization1 = (arrayOfString3 = memberIds).length;
            for (organizationForName = 0; organizationForName < localOrganization1; organizationForName++)
            {
              String string = arrayOfString3[organizationForName];
              if ((!"".equals(string)) && (string != null) && (string.length() > 2))
              {
                if (string.contains("'")) {
                  string = string.substring(1, string.length() - 1);
                }
                memberForName = (MemberInfo)this.memberInfoService.getById(string);
                memberForCustomerNames = memberForCustomerNames + memberForName.getName() + ",";
                "N".equalsIgnoreCase(group.attributeValue("isNotAllCustomers"));
              }
            }
          }
          String customer = range.elementText("appoint");
          if (customer != null)
          {
            String[] customerIds = customer.split(",");
            for (String string : customerIds) {
              if ((!"".equals(string)) && (string != null))
              {
                if (string.contains("'")) {
                  string = string.substring(1, string.length() - 1);
                }
                Customer customerForName = (Customer)this.customerService.getById(string);
                customerNames = customerNames + customerForName.getName() + ",";
              }
            }
          }
        }
      }
    }
    if ((!"".equals(specialMemberNames)) && (specialMemberNames.endsWith(","))) {
      specialMemberNames = specialMemberNames.substring(0, specialMemberNames.length() - 1);
    }
    if ((!"".equals(memberNames)) && (memberNames.endsWith(","))) {
      memberNames = memberNames.substring(0, memberNames.length() - 1);
    }
    if ((!"".equals(organizationNames)) && (organizationNames.endsWith(","))) {
      organizationNames = organizationNames.substring(0, organizationNames.length() - 1);
    }
    if ((!"".equals(customerNames)) && (customerNames.endsWith(","))) {
      customerNames = customerNames.substring(0, customerNames.length() - 1);
    }
    if ((!"".equals(brokerageNames)) && (brokerageNames.endsWith(","))) {
      brokerageNames = brokerageNames.substring(0, brokerageNames.length() - 1);
    }
    if ((!"".equals(memberForCustomerNames)) && (memberForCustomerNames.endsWith(","))) {
      memberForCustomerNames = memberForCustomerNames.substring(0, memberForCustomerNames.length() - 1);
    }
    if ((!"".equals(memberBrokerageNames)) && (memberBrokerageNames.endsWith(","))) {
      memberBrokerageNames = memberBrokerageNames.substring(0, memberBrokerageNames.length() - 1);
    }
    if ((!"".equals(memberOrganizationNames)) && (memberOrganizationNames.endsWith(","))) {
      memberOrganizationNames = memberOrganizationNames.substring(0, memberOrganizationNames.length() - 1);
    }
    if ((!"".equals(brokerageCustomerNames)) && (brokerageCustomerNames.endsWith(","))) {
      brokerageCustomerNames = brokerageCustomerNames.substring(0, brokerageCustomerNames.length() - 1);
    }
    if ((!"".equals(organizationCustomerNames)) && (organizationCustomerNames.endsWith(","))) {
      organizationCustomerNames = organizationCustomerNames.substring(0, organizationCustomerNames.length() - 1);
    }
    request.setAttribute("specialMemberNames", specialMemberNames);
    request.setAttribute("memberNames", memberNames);
    request.setAttribute("organizationNames", organizationNames);
    request.setAttribute("brokerageNames", brokerageNames);
    request.setAttribute("customerNames", customerNames);
    request.setAttribute("memberForCustomerNames", memberForCustomerNames);
    request.setAttribute("memberBrokerageNames", memberBrokerageNames);
    request.setAttribute("memberOrganizationNames", memberOrganizationNames);
    request.setAttribute("brokerageCustomerNames", brokerageCustomerNames);
    request.setAttribute("organizationCustomerNames", organizationCustomerNames);
    String result = invocation.invoke();
    return result;
  }
  
  public void deal(Notice notice) {}
  
  public List<Element> paseXML(String xml)
  {
    List<Element> rangeList = new ArrayList();
    try
    {
      if (xml != null)
      {
        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();
        rangeList = root.elements("range");
      }
    }
    catch (DocumentException e)
    {
      e.printStackTrace();
    }
    return rangeList;
  }
}
