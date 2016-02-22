package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.MemberInfoDao;
import gnnt.MEBS.account.dao.SpecialMemberDao;
import gnnt.MEBS.account.dao.SpecialMemberInfoProDao;
import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.account.model.SpecialMember;
import gnnt.MEBS.account.model.SpecialMemberStatus;
import gnnt.MEBS.announcement.model.Notice;
import gnnt.MEBS.announcement.service.NoticeService;
import gnnt.MEBS.base.model.util.AnnotationProperty;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.config.constant.NoticeForStatusConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("specialMemberStatusService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SpecialMemberStatusService
  extends BaseService<SpecialMember>
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberStatusService.class);
  @Autowired
  @Qualifier("specialMemberDao")
  private SpecialMemberDao specialMemberDao;
  @Autowired
  @Qualifier("memberInfoDao")
  private MemberInfoDao memberInfoDao;
  @Autowired
  @Qualifier("noticeService")
  private NoticeService noticeService;
  @Autowired
  @Qualifier("specialMemberInfoProDao")
  private SpecialMemberInfoProDao specialMemberInfoProDao;
  
  public BaseDao getDao()
  {
    return this.specialMemberDao;
  }
  
  public int update(SpecialMember specialMember)
  {
    int result = -1;
    SpecialMember specialMember1 = (SpecialMember)getById(specialMember.getId());
    result = this.specialMemberInfoProDao.statusChange(specialMember);
    if (result > 0) {
      sendNotice(specialMember1.getSpecialMemberStatus().getStatus(), specialMember.getSpecialMemberStatus().getStatus(), specialMember.getId(), specialMember1.getName());
    }
    return result;
  }
  
  public void sendNotice(String oldStatus, String newStatus, String id, String name)
  {
    String oldStatusName = "";
    String newStatusName = "";
    try
    {
      oldStatusName = AnnotationProperty.getStatusDescription(SpecialMemberStatus.class, "getStatus", oldStatus);
      newStatusName = AnnotationProperty.getStatusDescription(SpecialMemberStatus.class, "getStatus", newStatus);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    String noticeForStatus = NoticeForStatusConstant.NOTICEFORSTATUS.replaceAll("%%%%%%", oldStatusName);
    String recipientRules = NoticeForStatusConstant.NOTICESPMEMTEMPLATE.replace("##memberNo##", id);
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
    if (("N".equals(oldStatus)) && ("F".equals(newStatus)))
    {
      String noticeForMemberStatus = NoticeForStatusConstant.NOTICEMEMBERFORSTATUS.replaceAll("%%%%%%", oldStatusName);
      noticeForMemberStatus = noticeForMemberStatus.replaceAll("&&&&&&", name);
      QueryConditions qConditions = new QueryConditions();
      qConditions.addCondition("memberRelation.s_MemberNo", "=", id);
      List<MemberInfo> list = this.memberInfoDao.memberInfoListFromSpecial(qConditions, null);
      String memberNos = "";
      boolean flag = false;
      if ((list != null) && (list.size() > 0)) {
        for (MemberInfo member : list) {
          memberNos = memberNos + "'" + member.getId() + "',";
        }
      }
      if (!"".equals(memberNos))
      {
        memberNos = memberNos.substring(0, memberNos.length() - 1);
        flag = true;
      }
      String recipientRules1 = NoticeForStatusConstant.NOTICEMEMTEMPLATE1.replace("##memberNo##", memberNos);
      noticeForMemberStatus = noticeForMemberStatus.replaceAll("######", newStatusName);
      Notice notice1 = new Notice();
      notice1.setTitle(NoticeForStatusConstant.TITLE);
      notice1.setContent(noticeForMemberStatus);
      notice1.setExpiryTime(expiryTime);
      notice1.setCreateTime(date);
      notice1.setNoticeType("3");
      notice1.setSendType(Integer.valueOf(1));
      notice1.setSendTime(date);
      notice1.setSource(NoticeForStatusConstant.SOURCE);
      notice1.setAuthor((String)ThreadStore.get(ThreadStoreConstant.AUTHOR));
      notice1.setRecipientRules(recipientRules1);
      notice1.setIsExecute("N");
      notice1.setAuthorOrganization(NoticeForStatusConstant.AUTHORORGANIZATION);
      if (flag) {
        this.noticeService.add(notice1);
      }
    }
  }
}
