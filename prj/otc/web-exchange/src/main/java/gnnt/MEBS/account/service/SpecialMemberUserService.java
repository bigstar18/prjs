package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.SpecialMemberUserDao;
import gnnt.MEBS.account.model.SpecialMemberRight;
import gnnt.MEBS.account.model.SpecialMemberUser;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.common.dao.OutSideDao;
import gnnt.MEBS.common.dao.RightDao;
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

@Service("specialMemberUserService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SpecialMemberUserService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberUserService.class);
  @Autowired
  @Qualifier("specialMemberUserDao")
  private SpecialMemberUserDao memberUserDao;
  @Autowired
  @Qualifier("rightDao")
  private RightDao rightDao;
  @Autowired
  @Qualifier("outSideDao")
  private OutSideDao outSideDao;
  
  public BaseDao getDao()
  {
    return this.memberUserDao;
  }
  
  public List<SpecialMemberUser> getUserList(QueryConditions conditions, PageInfo pageInfo, boolean rightSign, boolean roleSign, boolean roleRightSign)
  {
    List<SpecialMemberUser> list = getList(conditions, pageInfo);
    for (SpecialMemberUser user : list)
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
  
  public SpecialMemberUser loadUserById(String userId, boolean rightSign, boolean roleSign, boolean roleRightSign)
  {
    SpecialMemberUser user = 
      (SpecialMemberUser)getById(userId);
    if (rightSign) {
      user.toRightSetIterator();
    }
    if (roleSign) {
      user.toRoleSetIterator(roleRightSign);
    }
    return user;
  }
  
  public List<SpecialMemberUser> findRoleByProperty(String propertyName, Object value)
  {
    return this.memberUserDao.findRoleByProperty(propertyName, value);
  }
  
  public void saveUserRights(String userId, String[] rightIds)
  {
    SpecialMemberUser user = (SpecialMemberUser)getById(userId);
    if ((rightIds != null) && (rightIds.length > 0))
    {
      Set<SpecialMemberRight> rightSet = new HashSet();
      for (int i = 0; i < rightIds.length; i++)
      {
        SpecialMemberRight right = (SpecialMemberRight)this.rightDao.getById(Long.valueOf(Long.parseLong(rightIds[i])));
        rightSet.add(right);
      }
      user.setRightSet(rightSet);
      this.memberUserDao.update(user);
    }
  }
  
  public boolean addUser(SpecialMemberUser user)
  {
    if (getById(user.getUserId()) == null)
    {
      user.setPassword(MD5.getMD5(user.getUserId(), user.getPassword()));
      this.memberUserDao.add(user);
      return true;
    }
    return false;
  }
  
  public List<SpecialMemberUser> getUserNoRoleById(long roleId)
  {
    String sql = "select distinct user from SpecialMemberUser user,SpecialMemberRole role where user not in elements(role.userSet) and role.id=" + roleId;
    List<SpecialMemberUser> list = this.outSideDao.getList(sql);
    return list;
  }
}
