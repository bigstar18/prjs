package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.CustomerDao;
import gnnt.MEBS.account.dao.CustomerInfoProDao;
import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.model.CustomerStatus;
import gnnt.MEBS.announcement.model.Notice;
import gnnt.MEBS.announcement.service.NoticeService;
import gnnt.MEBS.base.model.util.AnnotationProperty;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.config.constant.NoticeForStatusConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("customerStatusService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CustomerStatusService
  extends BaseService<Customer>
{
  @Autowired
  @Qualifier("customerDao")
  private CustomerDao customerDao;
  @Autowired
  @Qualifier("customerInfoProDao")
  private CustomerInfoProDao customerInfoProDao;
  @Autowired
  @Qualifier("noticeService")
  private NoticeService noticeService;
  
  public BaseDao getDao()
  {
    return this.customerDao;
  }
  
  private final transient Log logger = LogFactory.getLog(CustomerStatusService.class);
  
  public int update(Customer customer)
  {
    int result = 1;
    Customer customer1 = (Customer)getById(customer.getId());
    this.logger.debug(" new status：" + customer.getCustomerStatus().getStatus());
    result = this.customerInfoProDao.statusChange(customer);
    if (result > 0) {
      sendNotice(customer1.getCustomerStatus().getStatus(), customer.getCustomerStatus().getStatus(), customer.getId());
    }
    return result;
  }
  
  public void sendNotice(String oldStatus, String newStatus, String id)
  {
    String oldStatusName = "";
    String newStatusName = "";
    try
    {
      oldStatusName = AnnotationProperty.getStatusDescription(CustomerStatus.class, "getStatus", oldStatus);
      newStatusName = AnnotationProperty.getStatusDescription(CustomerStatus.class, "getStatus", newStatus);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    String noticeForStatus = NoticeForStatusConstant.NOTICEFORSTATUS.replaceAll("%%%%%%", oldStatusName);
    String recipientRules = NoticeForStatusConstant.NOTICECOUSOMERTEMPLATE.replace("##customerNo##", id);
    noticeForStatus = noticeForStatus.replaceAll("######", newStatusName);
    Notice notice = new Notice();
    notice.setTitle(NoticeForStatusConstant.TITLE);
    notice.setContent(noticeForStatus);
    Date date = new Date();
    Calendar calender = new GregorianCalendar();
    calender.add(5, NoticeForStatusConstant.EXPIRYTIME);
    Date expiryTime = calender.getTime();
    notice.setNoticeType("3");
    notice.setSendTime(date);
    notice.setExpiryTime(expiryTime);
    notice.setSource(NoticeForStatusConstant.SOURCE);
    notice.setAuthor((String)ThreadStore.get(ThreadStoreConstant.AUTHOR));
    notice.setRecipientRules(recipientRules);
    notice.setAuthorOrganization(NoticeForStatusConstant.AUTHORORGANIZATION);
    this.noticeService.add(notice);
  }
}
