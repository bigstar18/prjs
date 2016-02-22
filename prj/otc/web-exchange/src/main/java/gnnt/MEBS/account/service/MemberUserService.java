package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.MemberUserDao;
import gnnt.MEBS.account.model.MemberRight;
import gnnt.MEBS.account.model.MemberUser;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.common.dao.OutSideDao;
import gnnt.MEBS.common.dao.RightDao;
import gnnt.MEBS.common.dao.TraderProDao;
import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memberUserService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MemberUserService
  extends BaseService<MemberUser>
{
  private final transient Log logger = LogFactory.getLog(MemberUserService.class);
  @Autowired
  @Qualifier("memberUserDao")
  private MemberUserDao memberUserDao;
  @Autowired
  @Qualifier("rightDao")
  private RightDao rightDao;
  @Autowired
  @Qualifier("outSideDao")
  private OutSideDao outSideDao;
  @Autowired
  @Qualifier("traderProDao")
  private TraderProDao traderProDao;
  
  public BaseDao getDao()
  {
    return this.memberUserDao;
  }
  
  public MemberUser loadUserById(String userId, boolean rightSign, boolean roleSign, boolean roleRightSign)
  {
    MemberUser user = (MemberUser)getById(userId);
    if (rightSign) {
      user.toRightSetIterator();
    }
    if (roleSign) {
      user.toRoleSetIterator(roleRightSign);
    }
    return user;
  }
  
  public List<MemberUser> getUserList(QueryConditions conditions, PageInfo pageInfo, boolean rightSign, boolean roleSign, boolean roleRightSign)
  {
    List<MemberUser> list = getList(conditions, pageInfo);
    for (MemberUser user : list)
    {
      if (rightSign) {
        user.toRightSetIterator();
      }
      if (roleSign) {
        user.toRoleSetIterator(roleRightSign);
      }
    }
    return list;
  }
  
  public List<MemberUser> findRoleByProperty(String propertyName, Object value)
  {
    return this.memberUserDao.findRoleByProperty(propertyName, value);
  }
  
  public void saveUserRights(String userId, String[] rightIds)
  {
    MemberUser user = (MemberUser)this.memberUserDao.getById(userId);
    if ((rightIds != null) && (rightIds.length > 0))
    {
      Set<MemberRight> rightSet = new HashSet();
      for (int i = 0; i < rightIds.length; i++)
      {
        MemberRight right = (MemberRight)this.rightDao.getById(Long.valueOf(Long.parseLong(rightIds[i])));
        rightSet.add(right);
      }
      user.setRightSet(rightSet);
      this.memberUserDao.update(user);
    }
  }
  
  public boolean addUser(MemberUser user)
  {
    if (getById(user.getUserId()) == null)
    {
      user.setPassword(MD5.getMD5(user.getUserId(), user.getPassword()));
      this.memberUserDao.add(user);
      return true;
    }
    return false;
  }
  
  public List<MemberUser> getUserNoRoleById(long roleId)
  {
    String sql = "select distinct user from MemberUser user,MemberRole role where user not in elements(role.userSet) and role.id=" + 
      roleId;
    List<MemberUser> list = this.outSideDao.getList(sql);
    return list;
  }
  
  public int update(MemberUser obj)
  {
    int num = 0;
    this.logger.debug("enter update");
    Clone objFor = copyObject(obj);
    if (objFor != null) {
      getDao().update(objFor);
    }
    getDao().flush();
    this.traderProDao.traderUpdate(obj.getUserId());
    num = 3;
    return num;
  }
}
