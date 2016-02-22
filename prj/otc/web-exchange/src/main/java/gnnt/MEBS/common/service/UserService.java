package gnnt.MEBS.common.service;

import gnnt.MEBS.base.copy.CopyObjectParamUtil;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.dao.OutSideDao;
import gnnt.MEBS.common.dao.RightDao;
import gnnt.MEBS.common.dao.RoleDao;
import gnnt.MEBS.common.dao.UserDao;
import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.LogConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.dao.OperateLogDao;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class UserService
  extends BaseService<User>
{
  private final transient Log logger = LogFactory.getLog(UserService.class);
  @Autowired
  @Qualifier("userDao")
  private UserDao userDao;
  @Autowired
  @Qualifier("rightDao")
  private RightDao rightDao;
  @Autowired
  @Qualifier("outSideDao")
  private OutSideDao outSideDao;
  @Autowired
  @Qualifier("roleDao")
  private RoleDao roleDao;
  @Autowired
  @Qualifier("operateLogDao")
  private OperateLogDao operateLogDao;
  
  public BaseDao getDao()
  {
    return this.userDao;
  }
  
  public String authenticateUser(User user, String securityCode, String securityCodeFilledIn)
  {
    String result = null;
    boolean passOrNot = true;
    if ((securityCode == null) || ((securityCode != null) && (!securityCode.toUpperCase().equals(securityCodeFilledIn.toUpperCase()))))
    {
      result = "验证码错误,请重新登录!";
      passOrNot = false;
    }
    if (passOrNot)
    {
      User queryedUser = (User)this.userDao.getById(user.getUserId());
      if (queryedUser == null)
      {
        result = "用户名或密码错误,请重新登录!";
        passOrNot = false;
      }
      else
      {
        if (!queryedUser.getPassword().equals(user.getPassword()))
        {
          result = "用户名或密码错误,请重新登录!";
          passOrNot = false;
        }
        if ((!"0123456789ABCDE".equals(queryedUser.getKeyCode())) && 
          (!queryedUser.getKeyCode().equals(user.getKeyCode())))
        {
          result = "key盘身份验证失败,请重新登录!";
          passOrNot = false;
        }
        if (passOrNot)
        {
          if ("Y".equals(queryedUser.getIsForbid()))
          {
            result = "用户已禁用,请重新登录!";
            passOrNot = false;
          }
          if (passOrNot) {
            result = queryedUser.getSkin();
          }
        }
      }
    }
    return result;
  }
  
  public List<User> getUserList(QueryConditions conditions, PageInfo pageInfo, boolean rightSign, boolean roleSign, boolean roleRightSign)
  {
    List<User> list = this.userDao.getList(conditions, pageInfo);
    for (User user : list)
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
  
  public User loadUserById(String userId, boolean rightSign, boolean roleSign, boolean roleRightSign)
  {
    User user = (User)this.userDao.getById(userId);
    if (rightSign) {
      user.toRightSetIterator();
    }
    if (roleSign)
    {
      user.toRoleSetIterator(roleRightSign);
      user.toOperateRoleSetIterator();
    }
    return user;
  }
  
  public List<User> findRoleByProperty(String propertyName, Object value)
  {
    return this.userDao.findRoleByProperty(propertyName, value);
  }
  
  public void saveUserRights(String userId, String[] rightIds)
  {
    User user = (User)this.userDao.getById(userId);
    if ((rightIds != null) && (rightIds.length > 0))
    {
      Set<Right> rightSet = new HashSet();
      for (int i = 0; i < rightIds.length; i++)
      {
        Right right = (Right)this.rightDao.getById(
          Long.valueOf(Long.parseLong(rightIds[i])));
        
        rightSet.add(right);
      }
      user.setRightSet(rightSet);
    }
    else
    {
      user.setRightSet(null);
    }
    this.userDao.update(user);
  }
  
  public int add(User user)
  {
    User userNew = new User();
    CopyObjectParamUtil.bindData(user, userNew);
    

    this.userDao.add(userNew);
    return 2;
  }
  
  public List<User> getUserNoRoleById(long roleId, QueryConditions conditions)
  {
    String hql = "select distinct primary from User primary,Role role where primary not in elements(role.userSet) and role.id=" + 
      roleId;
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    PageInfo pageInfo = new PageInfo(1, 10000, "primary.userId", true);
    List<User> list = this.rightDao.queryByHQL(hql, names, values, 
      pageInfo, null);
    this.logger.debug("hql:" + hql);
    return list;
  }
  
  public List<List<String>> getRelatedIds(String userId, String logonId, String[] cks, QueryConditions qc, String[] operateCks)
  {
    User logonUser = loadUserById(logonId, false, true, false);
    User user = loadUserById(userId, false, true, false);
    

    Set<Role> roleSet = new HashSet();
    boolean flag = false;
    for (Role role : logonUser.getRoleSet()) {
      if (role.getType().equals(ActionConstant.DEF_SUPERADMIN)) {
        flag = true;
      }
    }
    if (!flag)
    {
      roleSet = logonUser.getOperateRoleSet();
    }
    else
    {
      List<Role> roleList = this.roleDao.getList(qc, null);
      for (Role role : roleList) {
        roleSet.add(role);
      }
    }
    List<String> returnRights = new ArrayList();
    Object operateRights = new ArrayList();
    for (Role role : user.getRoleSet())
    {
      boolean onlyFlag = false;
      for (Role logonRole : roleSet) {
        if (role.getId() == logonRole.getId()) {
          onlyFlag = true;
        }
      }
      if (!onlyFlag) {
        returnRights.add(role.getId());
      }
    }
    boolean operateFlag;
    Role logonRole;
    for (Role role : user.getOperateRoleSet())
    {
      operateFlag = false;
      for (??? = roleSet.iterator(); ???.hasNext();)
      {
        logonRole = (Role)???.next();
        if (role.getId() == logonRole.getId()) {
          operateFlag = true;
        }
      }
      if (!operateFlag) {
        ((List)operateRights).add(role.getId());
      }
    }
    boolean bool1;
    if ((cks != null) && (cks.length > 0))
    {
      operateFlag = (logonRole = cks).length;
      for (bool1 = false; bool1 < operateFlag; bool1++)
      {
        String ck = logonRole[bool1];
        returnRights.add(ck);
        this.logger.debug("ck:" + ck);
      }
    }
    if ((operateCks != null) && (operateCks.length > 0))
    {
      operateFlag = (logonRole = operateCks).length;
      for (bool1 = false; bool1 < operateFlag; bool1++)
      {
        String operate = logonRole[bool1];
        ((List)operateRights).add(operate);
      }
    }
    Object returnRightList = new ArrayList();
    ((List)returnRightList).add(returnRights);
    ((List)returnRightList).add(operateRights);
    return returnRightList;
  }
  
  public int relatedRight(String userId, String logonId, List<List<String>> cks, String mark)
  {
    int result = -1;
    User user = (User)this.userDao.getById(userId);
    Set<Role> roleSet = new HashSet();
    Set<Role> operateRoleSet = new HashSet();
    for (String ck : (List)cks.get(0))
    {
      Role role = new Role();
      role.setId(Long.valueOf(Long.parseLong(ck)));
      roleSet.add(role);
    }
    for (String operate : (List)cks.get(1))
    {
      Role role = new Role();
      role.setId(Long.valueOf(Long.parseLong(operate)));
      operateRoleSet.add(role);
    }
    user.setRoleSet(roleSet);
    user.setOperateRoleSet(operateRoleSet);
    this.userDao.update(user);
    result = 5;
    

    String description = "";
    String idsString = "";
    for (String id : (List)cks.get(0)) {
      idsString = idsString + id + ",";
    }
    String id1 = "";
    for (String id : (List)cks.get(1)) {
      id1 = id1 + id + ",";
    }
    description = description + "修改用户Id为" + userId + "的关联角色为：" + idsString + "；并且修改此用户的操作角色为：" + id1;
    OperateLog operateLog = new OperateLog();
    operateLog.setOperator(logonId);
    operateLog.setMark(mark);
    operateLog.setOperateDate(new Date());
    operateLog.setOperateIp((String)ThreadStore.get(ThreadStoreConstant.OPERATEIP));
    operateLog.setOperatorType(LogConstant.OPERATORTYPE);
    operateLog.setOperateContent(description);
    operateLog.setOperateType("用户关联角色");
    if (ThreadStore.get(ThreadStoreConstant.ISLOG) != null) {
      operateLog.setOperateLogType(((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue());
    }
    operateLog.setOperateDate(this.operateLogDao.getSysDate());
    this.operateLogDao.add(operateLog);
    return result;
  }
}
