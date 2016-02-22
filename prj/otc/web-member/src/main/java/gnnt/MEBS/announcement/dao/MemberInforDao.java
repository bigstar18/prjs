package gnnt.MEBS.announcement.dao;

import gnnt.MEBS.announcement.model.MemberInfor;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("memberInforDao")
public class MemberInforDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(NoticeDao.class);
  
  public Class getEntityClass()
  {
    return new MemberInfor().getClass();
  }
  
  public MemberInfor queryMemberInfor(String memberNo)
  {
    MemberInfor memberInfor = new MemberInfor();
    List<MemberInfor> list = getHibernateTemplate().find("from MemberInfor t where t.memberNo=?", memberNo);
    if ((list != null) && (list.size() > 0)) {
      memberInfor = (MemberInfor)list.get(0);
    }
    return memberInfor;
  }
  
  public void addMemberInfor(MemberInfor memberInfor)
  {
    getHibernateTemplate().save(memberInfor);
  }
  
  public void updateMemberInfor(MemberInfor memberInfor)
  {
    getHibernateTemplate().update(memberInfor);
  }
}
