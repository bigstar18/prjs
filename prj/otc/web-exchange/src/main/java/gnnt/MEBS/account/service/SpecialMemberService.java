package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.FirmProDao;
import gnnt.MEBS.account.dao.SpecialMemberDao;
import gnnt.MEBS.account.dao.SpecialMemberInfoProDao;
import gnnt.MEBS.account.model.SpecialMember;
import gnnt.MEBS.account.model.SpecialMemberStatus;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("specialMemberService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SpecialMemberService
  extends BaseService<SpecialMember>
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberService.class);
  @Autowired
  @Qualifier("specialMemberDao")
  private SpecialMemberDao specialMemberDao;
  @Autowired
  @Qualifier("firmProDao")
  private FirmProDao firmProDao;
  @Autowired
  @Qualifier("specialMemberInfoProDao")
  private SpecialMemberInfoProDao specialMemberInfoProDao;
  
  public BaseDao getDao()
  {
    return this.specialMemberDao;
  }
  
  public int updateSpecialMember(SpecialMember specialMember)
  {
    int result = -1;
    SpecialMember specialMember1 = (SpecialMember)getById(specialMember.getId());
    specialMember1.getSpecialMemberStatus().setStatus(specialMember.getSpecialMemberStatus().getStatus());
    this.specialMemberDao.update(specialMember1);
    result = 3;
    return result;
  }
  
  public int add(SpecialMember obj)
  {
    this.logger.debug("enter add");
    obj.setCreateTime(getSysDate());
    int num = 0;
    getDao().add(obj);
    getDao().flush();
    this.specialMemberInfoProDao.addMemberInfoPro(obj.getS_memberNo());
    this.specialMemberInfoProDao.memberAddToPwd(obj);
    num = 2;
    return num;
  }
  
  public int update(SpecialMember obj)
  {
    int num = 0;
    num = this.firmProDao.firmMod(obj.getS_memberNo(), obj.getPapersType().intValue(), obj.getPapersName(), obj.getName());
    if (num > 0)
    {
      Clone objFor = copyObject(obj);
      if (objFor != null) {
        getDao().update((SpecialMember)objFor);
      }
      num = 3;
    }
    return num;
  }
}
