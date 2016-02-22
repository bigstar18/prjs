package gnnt.MEBS.announcement.service;

import gnnt.MEBS.announcement.dao.MemberInforDao;
import gnnt.MEBS.announcement.model.MemberInfor;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memberInforService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MemberInforService
  extends BaseService<MemberInfor>
{
  @Autowired
  @Qualifier("memberInforDao")
  private MemberInforDao memberInforDao;
  
  public BaseDao getDao()
  {
    return this.memberInforDao;
  }
  
  public MemberInfor queryMemberInfor(String memberNo)
  {
    return this.memberInforDao.queryMemberInfor(memberNo);
  }
  
  public void addMemberInfor(MemberInfor memberInfor)
  {
    this.memberInforDao.addMemberInfor(memberInfor);
  }
  
  public void updateMemberInfor(MemberInfor memberInfor)
  {
    this.memberInforDao.update(memberInfor);
  }
}
