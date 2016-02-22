package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.MemberInfoDao;
import gnnt.MEBS.account.dao.MemberInfoProDao;
import gnnt.MEBS.account.dao.ThresholdArgsDao;
import gnnt.MEBS.account.model.CompMember;
import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.account.model.ThresholdArgs;
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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memberStatusService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MemberStatusService
  extends BaseService<MemberInfo>
{
  @Autowired
  @Qualifier("thresholdArgsDao")
  private ThresholdArgsDao thresholdArgsDao;
  @Autowired
  @Qualifier("memberInfoDao")
  private MemberInfoDao memberInfoDao;
  @Autowired
  @Qualifier("noticeService")
  private NoticeService noticeService;
  @Autowired
  @Qualifier("memberInfoProDao")
  private MemberInfoProDao memberInfoProDao;
  
  public BaseDao getDao()
  {
    return this.memberInfoDao;
  }
  
  public int update(MemberInfo memberInfo)
  {
    int result = 1;
    MemberInfo memberInfo1 = (MemberInfo)getById(memberInfo.getId());
    result = this.memberInfoProDao.statusChange(memberInfo);
    if (result > 0) {
      sendNotice(memberInfo1.getCompMember().getStatus(), memberInfo.getCompMember().getStatus(), memberInfo.getId());
    }
    return result;
  }
  
  public void sendNotice(String oldStatus, String newStatus, String id)
  {
    String oldStatusName = "";
    String newStatusName = "";
    try
    {
      oldStatusName = AnnotationProperty.getStatusDescription(CompMember.class, "getStatus", oldStatus);
      newStatusName = AnnotationProperty.getStatusDescription(CompMember.class, "getStatus", newStatus);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    String noticeForStatus = NoticeForStatusConstant.NOTICEFORSTATUS.replaceAll("%%%%%%", oldStatusName);
    String recipientRules = NoticeForStatusConstant.NOTICEMEMTEMPLATE.replace("##memberNo##", id);
    noticeForStatus = noticeForStatus.replaceAll("######", newStatusName);
    Notice notice = new Notice();
    notice.setTitle(NoticeForStatusConstant.TITLE);
    notice.setContent(noticeForStatus);
    Date date = new Date();
    Calendar calender = new GregorianCalendar();
    calender.add(5, NoticeForStatusConstant.EXPIRYTIME);
    Date expiryTime = calender.getTime();
    notice.setExpiryTime(expiryTime);
    notice.setCreateTime(date);
    notice.setNoticeType("3");
    notice.setSendType(Integer.valueOf(1));
    notice.setSendTime(date);
    notice.setSource(NoticeForStatusConstant.SOURCE);
    notice.setAuthor((String)ThreadStore.get(ThreadStoreConstant.AUTHOR));
    notice.setRecipientRules(recipientRules);
    notice.setIsExecute("N");
    notice.setAuthorOrganization(NoticeForStatusConstant.AUTHORORGANIZATION);
    this.noticeService.add(notice);
    

    String noticeForStatus1 = NoticeForStatusConstant.NOTICEFORCUSTOMERSTATUS.replaceAll("%%%%%%", oldStatusName);
    noticeForStatus1 = noticeForStatus1.replace("##memberNo##", id);
    noticeForStatus1 = noticeForStatus1.replaceAll("######", newStatusName);
    
    String recipientRules1 = NoticeForStatusConstant.NOTICEMEMBERFORCOUSOMERTEM.replace("##memberNo##", id);
    Notice customerNotice = new Notice();
    customerNotice.setTitle(NoticeForStatusConstant.TITLE);
    customerNotice.setContent(noticeForStatus1);
    calender.add(5, NoticeForStatusConstant.EXPIRYTIME);
    customerNotice.setExpiryTime(expiryTime);
    customerNotice.setCreateTime(date);
    customerNotice.setNoticeType("3");
    customerNotice.setSendType(Integer.valueOf(1));
    customerNotice.setSendTime(date);
    customerNotice.setSource(NoticeForStatusConstant.SOURCE);
    customerNotice.setAuthor((String)ThreadStore.get(ThreadStoreConstant.AUTHOR));
    customerNotice.setRecipientRules(recipientRules1);
    customerNotice.setIsExecute("N");
    customerNotice.setAuthorOrganization(NoticeForStatusConstant.AUTHORORGANIZATION);
    if (!"U".equals(oldStatus))
    {
      Class classType = this.thresholdArgsDao.getEntityClass();
      try
      {
        ThresholdArgs t = getT((ThresholdArgs)classType.newInstance());
        if ((t != null) && ("Y".equals(t.getMchangeStatus()))) {
          this.noticeService.add(customerNotice);
        }
      }
      catch (InstantiationException e)
      {
        e.printStackTrace();
      }
      catch (IllegalAccessException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  private ThresholdArgs getT(ThresholdArgs clone)
  {
    List list = this.thresholdArgsDao.getList(null, null);
    ThresholdArgs thresholdArgs = null;
    if ((list != null) && (list.size() > 0)) {
      thresholdArgs = (ThresholdArgs)list.get(0);
    }
    return thresholdArgs;
  }
}
