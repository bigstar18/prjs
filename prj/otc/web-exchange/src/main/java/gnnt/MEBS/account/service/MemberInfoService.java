package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.FirmProDao;
import gnnt.MEBS.account.dao.MemberInfoDao;
import gnnt.MEBS.account.dao.MemberInfoProDao;
import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memberInfoService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MemberInfoService
  extends BaseService<MemberInfo>
{
  private final transient Log logger = LogFactory.getLog(MemberInfoService.class);
  @Autowired
  @Qualifier("memberInfoProDao")
  private MemberInfoProDao memberInfoProDao;
  @Autowired
  @Qualifier("firmProDao")
  private FirmProDao firmProDao;
  @Autowired
  @Qualifier("memberInfoDao")
  private MemberInfoDao memberInfoDao;
  
  public BaseDao<MemberInfo> getDao()
  {
    return this.memberInfoDao;
  }
  
  public int add(MemberInfo obj)
  {
    int num = 0;
    this.logger.debug("enter add");
    obj.setCreateTime(getSysDate());
    getDao().add(obj);
    this.logger.debug("add");
    getDao().flush();
    this.logger.debug("flush");
    this.memberInfoProDao.addMemberInfoPro(obj.getId());
    this.memberInfoProDao.memberAddToPwd(obj);
    num = 2;
    return num;
  }
  
  public List<MemberInfo> memberInfoListFromSpecial(QueryConditions qc, PageInfo pageInfo)
  {
    List<MemberInfo> list = this.memberInfoDao.memberInfoListFromSpecial(qc, pageInfo);
    return list;
  }
  
  public int update(MemberInfo obj)
  {
    int num = 0;
    num = this.firmProDao.firmMod(obj.getMemberNo(), obj.getPapersType().intValue(), obj.getPapersName(), obj.getName());
    if (num > 0)
    {
      Clone objFor = copyObject(obj);
      if (objFor != null) {
        getDao().update((MemberInfo)objFor);
      }
      num = 3;
    }
    return num;
  }
}
