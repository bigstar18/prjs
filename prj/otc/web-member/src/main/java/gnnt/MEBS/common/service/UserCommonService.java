package gnnt.MEBS.common.service;

import gnnt.MEBS.account.dao.TraderDao;
import gnnt.MEBS.base.copy.CopyObjectParamUtil;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.broke.dao.OrganizationDao;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.common.dao.OutSideDao;
import gnnt.MEBS.common.dao.RightDao;
import gnnt.MEBS.common.dao.RoleDao;
import gnnt.MEBS.common.dao.TraderProDao;
import gnnt.MEBS.common.dao.UserDao;
import gnnt.MEBS.common.dao.UserProDao;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.LogConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.dao.OperateLogDao;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.packaging.dao.BaseDao;
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
public class UserCommonService
  extends UserService
{
  private final transient Log logger = LogFactory.getLog(UserCommonService.class);
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
  @Qualifier("userProDao")
  private UserProDao userProDao;
  @Autowired
  @Qualifier("traderProDao")
  private TraderProDao traderProDao;
  @Autowired
  @Qualifier("roleDao")
  private RoleDao roleDao;
  @Autowired
  @Qualifier("traderDao")
  private TraderDao traderDao;
  @Autowired
  @Qualifier("operateLogDao")
  private OperateLogDao operateLogDao;
  @Autowired
  @Qualifier("organizationDao")
  private OrganizationDao organizationDao;
  
  public BaseDao getDao()
  {
    return this.userDao;
  }
  
  public int updateForOrg(User user, String organizationNo)
  {
    Organization organization = (Organization)this.organizationDao.getById(organizationNo);
    user.setOrganization(organization);
    this.userDao.update(user);
    return 1;
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
    this.userDao.flush();
    result = this.userProDao.userRelatedRole(userId);
    
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
  
  public int add(User user)
  {
    User userNew = new User();
    CopyObjectParamUtil.bindData(user, userNew);
    userNew.setOrganization(user.getOrganization());
    

    this.userDao.add(userNew);
    this.userDao.flush();
    this.traderProDao.traderAdd(user.getUserId(), user.getMemberNo());
    return 2;
  }
  
  public int update(User obj)
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
