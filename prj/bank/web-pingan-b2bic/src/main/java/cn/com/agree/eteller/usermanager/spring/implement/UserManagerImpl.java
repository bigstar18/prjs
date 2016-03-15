package cn.com.agree.eteller.usermanager.spring.implement;

import cn.com.agree.eteller.generic.spring.implement.GenericManager;
import cn.com.agree.eteller.generic.utils.Base64Encoder;
import cn.com.agree.eteller.generic.utils.Pagination;
import cn.com.agree.eteller.generic.utils.UserContext;
import cn.com.agree.eteller.generic.vo.DataTree;
import cn.com.agree.eteller.generic.vo.LoginUser;
import cn.com.agree.eteller.usermanager.dao.IAppinfoDao;
import cn.com.agree.eteller.usermanager.dao.IDepartmentDao;
import cn.com.agree.eteller.usermanager.dao.IFuncListDao;
import cn.com.agree.eteller.usermanager.dao.IRolelistDao;
import cn.com.agree.eteller.usermanager.dao.ISubAppinfoDao;
import cn.com.agree.eteller.usermanager.dao.IUserDao;
import cn.com.agree.eteller.usermanager.dao.IUserManagerDao;
import cn.com.agree.eteller.usermanager.exception.PwdException;
import cn.com.agree.eteller.usermanager.exception.UsernameException;
import cn.com.agree.eteller.usermanager.persistence.Appinfo;
import cn.com.agree.eteller.usermanager.persistence.Department;
import cn.com.agree.eteller.usermanager.persistence.EtellerSubappinfo;
import cn.com.agree.eteller.usermanager.persistence.Funclist;
import cn.com.agree.eteller.usermanager.persistence.Rolelist;
import cn.com.agree.eteller.usermanager.persistence.Userlist;
import cn.com.agree.eteller.usermanager.spring.IUserManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserManagerImpl
  extends GenericManager
  implements IUserManager
{
  private IUserManagerDao userManagerDao;
  private IUserDao userDao;
  private IAppinfoDao appinfoDao;
  private IFuncListDao funcListDao;
  private IRolelistDao rolelistDao;
  private IDepartmentDao departmentDao;
  private ISubAppinfoDao subAppinfoDao;
  
  public void updateLoginUser(Object obj)
  {
    this.userDao.updateLoginUser(obj);
  }
  
  public ISubAppinfoDao getSubAppinfoDao()
  {
    return this.subAppinfoDao;
  }
  
  public void setSubAppinfoDao(ISubAppinfoDao subAppinfoDao)
  {
    this.subAppinfoDao = subAppinfoDao;
  }
  
  public IUserDao getUserDao()
  {
    return this.userDao;
  }
  
  public void setUserDao(IUserDao userDao)
  {
    this.userDao = userDao;
  }
  
  public void addUser(Object obj)
  {
    this.userDao.addUser(obj);
  }
  
  public void add(Object obj)
  {
    this.userManagerDao.add(obj);
  }
  
  public void delete(Object obj)
  {
    this.userManagerDao.delete(obj);
  }
  
  public void update(Object obj)
  {
    this.userManagerDao.update(obj);
  }
  
  public Userlist[] getAllUser()
  {
    return this.userDao.getAllUser();
  }
  
  public void login(LoginUser user)
    throws Exception
  {
    if ((UserContext.isSingleLogin()) && (UserContext.hasUser(user))) {
      throw new UsernameException("该账号已登录");
    }
    Userlist u = this.userDao.getUser(user.getUserId());
    if (u == null) {
      throw new UsernameException("用户不存在");
    }
    if (u.getTellerState().equals("1")) {
      throw new Exception("该用户已被冻结!");
    }
    String pwd = Base64Encoder.encode(user.getUserPwd());
    
    user.setUsername(u.getTellerName());
    user.setUserPwd(pwd);
    
    user.setUserType(u.getRoleId().toString());
    if (!pwd.equals(u.getTellerPasswd())) {
      throw new PwdException("用户密码错误");
    }
    try
    {
      if (!"root".equals(user.getUserId()))
      {
        user.setRole(getRole(u.getRoleId()));
        user.setDept(getDepartment(u.getDepartmentId()));
        if (user.getDept().getSuperiorDepartmentId() != null) {
          user.getDept().setSuperiorDept(
            getDepartment(user.getDept()
            .getSuperiorDepartmentId()));
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new Exception("用户登录出错");
    }
  }
  
  public DataTree getFunctionTree(String userId)
    throws Exception
  {
    return this.userDao.getFunctionTree(userId);
  }
  
  public Userlist getUserlistBysql(String sql)
  {
    return this.userDao.getUserlistBysql(sql);
  }
  
  public boolean addAppinfo(Appinfo ca)
  {
    return this.appinfoDao.addAppinfo(ca);
  }
  
  public boolean updateAppinfo(Appinfo ca)
  {
    return this.appinfoDao.updateAppinfo(ca);
  }
  
  public boolean deleteAppinfo(Appinfo ca)
  {
    return this.appinfoDao.deleteAppinfo(ca);
  }
  
  public Appinfo[] getAllAppinfo()
  {
    return this.appinfoDao.getAllAppinfo();
  }
  
  public IAppinfoDao getAppinfoDao()
  {
    return this.appinfoDao;
  }
  
  public void setAppinfoDao(IAppinfoDao appinfoDao)
  {
    this.appinfoDao = appinfoDao;
  }
  
  public IFuncListDao getFuncListDao()
  {
    return this.funcListDao;
  }
  
  public void setFuncListDao(IFuncListDao funcListDao)
  {
    this.funcListDao = funcListDao;
  }
  
  public boolean addFunction(Funclist bf)
  {
    return this.funcListDao.addFunction(bf);
  }
  
  public boolean deleteFunction(long fcid)
  {
    return this.funcListDao.deleteFunction(fcid);
  }
  
  public Funclist[] getFunctionByAppId(String appid, Pagination page)
  {
    return this.funcListDao.getFunctionByAppId(appid, page);
  }
  
  public Funclist[] getFunctionBySubAppId(String appid, String subappid)
  {
    return this.funcListDao.getFunctionBySubAppId(appid, subappid);
  }
  
  public Funclist[] getFunctionList(Funclist chkFunc)
  {
    return this.funcListDao.getFunctionList(chkFunc);
  }
  
  public boolean updateFunction(Funclist bf)
  {
    return this.funcListDao.updateFunction(bf);
  }
  
  public Funclist[] getFunctionByRoleId(Serializable roleId)
  {
    return this.funcListDao.getFunctionByRoleId(roleId);
  }
  
  public Funclist[] getAllFunction()
  {
    return this.funcListDao.getAllFunction();
  }
  
  public boolean addRole(Rolelist role, Funclist[] funclist)
  {
    return this.rolelistDao.addRole(role, funclist);
  }
  
  public IRolelistDao getRolelistDao()
  {
    return this.rolelistDao;
  }
  
  public void setRolelistDao(IRolelistDao rolelistDao)
  {
    this.rolelistDao = rolelistDao;
  }
  
  public Rolelist[] getAllRole()
  {
    return this.rolelistDao.getAllRole();
  }
  
  public boolean resetPassword(Userlist user, String npsw)
  {
    return this.userDao.resetPassword(user, npsw);
  }
  
  public Userlist getUser(String userid)
  {
    return this.userDao.getUser(userid);
  }
  
  public String getMaxUserId()
  {
    return this.userDao.getMaxUserId();
  }
  
  public Userlist[] getAllUserFormDB()
  {
    return this.userDao.getAllUserFormDB();
  }
  
  public List getRoles(Long[] roleIds)
  {
    return this.rolelistDao.getRoles(roleIds);
  }
  
  public IUserManagerDao getUserManagerDao()
  {
    return this.userManagerDao;
  }
  
  public void setUserManagerDao(IUserManagerDao userManagerDao)
  {
    this.userManagerDao = userManagerDao;
  }
  
  public Rolelist getRole(Serializable id)
  {
    return this.rolelistDao.getRole(id);
  }
  
  public Set getRoleFunctions(Serializable roleId)
  {
    return this.funcListDao.getRoleFunctions(roleId);
  }
  
  public void checkCycleSuperior(Rolelist role)
    throws Exception
  {
    this.rolelistDao.checkCycleSuperior(role);
  }
  
  public List getUnderlingList(Serializable userId, Map condition)
  {
    return this.userManagerDao.getUnderlingList(userId, condition);
  }
  
  public void deleteRole(Serializable roleId)
    throws Exception
  {
    this.rolelistDao.deleteRole(roleId);
  }
  
  public String generateUserId(String depno)
  {
    return this.userDao.generateUserId(depno);
  }
  
  public Userlist[] getUserBydptid(String dptid)
  {
    return this.userDao.getUserBydptid(dptid);
  }
  
  public List getBranchList()
  {
    return this.userManagerDao.getBranchList();
  }
  
  public List getDepartmentList()
  {
    return this.userManagerDao.getDepartmentList();
  }
  
  public List getSubDepartmentList(String departmentId, Map<String, String> map)
  {
    return this.userManagerDao.getSubDepartmentList(departmentId, map);
  }
  
  public List getSubDepartmentList1(String departmentId, Department dep)
  {
    return this.userManagerDao.getSubDepartmentList1(departmentId, dep);
  }
  
  public Department getDepartment(Serializable departmentId)
  {
    return this.userManagerDao.getDepartment(departmentId);
  }
  
  public List getRoleList(String departmentId)
  {
    return this.userManagerDao.getRoleList(departmentId);
  }
  
  public int countDepartmentDeepness(String departmentId)
  {
    return this.userManagerDao.countDepartmentDeepness(departmentId);
  }
  
  public List getRootDepartmentList()
  {
    return this.userManagerDao.getRootDepartmentList();
  }
  
  public IDepartmentDao getDepartmentDao()
  {
    return this.departmentDao;
  }
  
  public void setDepartmentDao(IDepartmentDao departmentDao)
  {
    this.departmentDao = departmentDao;
  }
  
  public void checkRoleUserList(String depid, List oldrole, List newrole)
    throws Exception
  {
    this.departmentDao.checkRoleUserList(depid, oldrole, newrole);
  }
  
  public boolean addDepartment(Department dep)
  {
    return this.departmentDao.addDepartment(dep);
  }
  
  public boolean deleteDepartment(Department dep)
  {
    if (isHaveTeller(dep.getId())) {
      return false;
    }
    return this.departmentDao.deleteDepartment(dep);
  }
  
  public boolean updateDepartment(Department dep)
  {
    Department[] olds = getDepartmentByDepId(dep.getId());
    Department old = null;
    if (olds != null) {
      old = olds[0];
    } else {
      return false;
    }
    if ((old.getSuperiorDepartmentId() != null) && (dep.getSuperiorDepartmentId() != null) && 
      (!old.getSuperiorDepartmentId().equals(
      dep.getSuperiorDepartmentId())))
    {
      if (isHaveSubDep(dep.getId())) {
        return false;
      }
      if (isHaveTeller(dep.getId())) {
        return false;
      }
    }
    if ((dep.getFinalFlag() == '1') && (isHaveSubDep(dep.getId()))) {
      return false;
    }
    return this.departmentDao.updateDepartment(dep);
  }
  
  public Department[] getAllDepartment()
  {
    return this.departmentDao.getAllDepartment();
  }
  
  public Department[] getAllDepartment1(Department dep)
  {
    return this.departmentDao.getAllDepartment1(dep);
  }
  
  public boolean isHaveTeller(String depid)
  {
    return this.departmentDao.isHaveTeller(depid);
  }
  
  public boolean isHaveSubDep(String depid)
  {
    return this.departmentDao.isHaveSubDep(depid);
  }
  
  public List getSubDepartmentWithCurrentList(String departmentId)
  {
    return 
      this.userManagerDao.getSubDepartmentWithCurrentList(departmentId);
  }
  
  public String getNewSysDepNo(String depno)
  {
    return this.departmentDao.getNewSysDepNo(depno);
  }
  
  public String getId(String newSysDepNo)
  {
    return this.departmentDao.getId(newSysDepNo);
  }
  
  public Department getUserDepnoInfo(String dptid)
  {
    return this.departmentDao.getUserDepnoInfo(dptid);
  }
  
  public Department getDepartmentByNewSystemNo(String newSystemNo)
  {
    return this.userManagerDao.getDepartmentByNewSystemNo(newSystemNo);
  }
  
  public List getRoleUnderlingList(Long roleId)
  {
    return this.userManagerDao.getRoleUnderlingList(roleId);
  }
  
  public List getRoleList()
  {
    return this.userManagerDao.getRoleList();
  }
  
  public List getRoleDepartmentList(Serializable roleId, List departmentList)
  {
    return this.rolelistDao.getRoleDepartmentList(roleId, departmentList);
  }
  
  public List getRoleUnderlingListInDepartment(Long roleId, String departmentId)
  {
    return this.userManagerDao.getRoleUnderlingListInDepartment(roleId, 
      departmentId);
  }
  
  public List getRoleListInDepartment(String departmentId)
  {
    return this.userManagerDao.getRoleListInDepartment(departmentId);
  }
  
  public Department[] getDepartmentBySupDepId(String supDepId)
  {
    return this.departmentDao.getDepartmentBySupDepId(supDepId);
  }
  
  public Department[] getDepartmentByDepId(String depId)
  {
    return this.departmentDao.getDepartmentByDepId(depId);
  }
  
  public void checkCycleSuperior(Department dep)
    throws Exception
  {
    this.departmentDao.checkCycleSuperior(dep);
  }
  
  public int countDepartmentDeepness(Department department)
  {
    return this.userManagerDao.countDepartmentDeepness(department);
  }
  
  public List getDirectSubDepartmentList(String departmentId)
  {
    return this.userManagerDao.getDirectSubDepartmentList(departmentId);
  }
  
  public List getDirectSubDepartmentWithCurrentList(String departmentId)
  {
    return 
      this.userManagerDao.getDirectSubDepartmentWithCurrentList(departmentId);
  }
  
  public List getDepartmentRoleList(Serializable departmentId)
  {
    return this.departmentDao.getDepartmentRoleList(departmentId);
  }
  
  public List getRootRoleList()
  {
    return this.rolelistDao.getRootRoleList();
  }
  
  public boolean hasRoleUnderling(Serializable roleId)
  {
    return this.userManagerDao.hasRoleUnderling(roleId);
  }
  
  public List getRoleNotFinalList()
  {
    return this.rolelistDao.getRoleNotFinalList();
  }
  
  public List getSubDepartmentWithCurrentList_final(String departmentId)
  {
    return getUserManagerDao().getSubDepartmentWithCurrentList_final(
      departmentId);
  }
  
  public List getDep_finalFlag()
  {
    return getDepartmentDao().getDep_finalFlag();
  }
  
  public List getRoleUnderlingWithCurrentList(Long roleId)
  {
    return getUserManagerDao().getRoleUnderlingWithCurrentList(roleId);
  }
  
  public List getRoleUnderlingWithCurrentList(Long roleId, boolean isFinal)
  {
    return getUserManagerDao().getRoleUnderlingWithCurrentList(roleId, 
      isFinal);
  }
  
  public Appinfo getAppinfo(String appid)
  {
    return getAppinfoDao().getAppinfo(appid);
  }
  
  public EtellerSubappinfo getSubAppinfo(String appid)
  {
    return this.subAppinfoDao.getSubAppinfo(appid);
  }
  
  public boolean addSubAppinfo(EtellerSubappinfo mda)
  {
    return this.subAppinfoDao.addSubAppinfo(mda);
  }
  
  public boolean updateSubAppinfo(EtellerSubappinfo mda)
  {
    return this.subAppinfoDao.updateSubAppinfo(mda);
  }
  
  public boolean deleteSubAppinfo(EtellerSubappinfo mda)
  {
    return this.subAppinfoDao.deleteSubAppinfo(mda);
  }
  
  public EtellerSubappinfo[] getAllSubAppinfo()
  {
    return this.subAppinfoDao.getAllSubAppinfo();
  }
  
  public EtellerSubappinfo[] getSubAppinfoByappid(String appid)
  {
    return this.subAppinfoDao.getSubAppinfoByappid(appid);
  }
  
  public int getMaxSubappid()
  {
    return this.subAppinfoDao.getMaxSubappid();
  }
  
  public int getMaxAppid()
  {
    return this.appinfoDao.getMaxAppid();
  }
  
  public DataTree getFunctionTreeByLevel(String levelid)
  {
    return this.userDao.getFunctionTreeByLevel(levelid);
  }
  
  public Funclist[] getFunclistByMap(Map map, Pagination page)
    throws Exception
  {
    return this.funcListDao.getFunclistByMap(map, page);
  }
  
  public Userlist getUserByName(String name)
    throws Exception
  {
    return this.userDao.getUserByName(name);
  }
  
  public List getDeptListByMap(Map<String, Object> map, Pagination page)
  {
    return this.userManagerDao.getDeptListByMap(map, page);
  }
  
  public List<Funclist> getFunclistByMap2(Map<String, String> map, Pagination page)
  {
    return this.funcListDao.getFunclistByMap2(map, page);
  }
  
  public List<EtellerSubappinfo> getBySubAppCondition(EtellerSubappinfo subApp, Pagination page)
  {
    return this.userManagerDao.getBySubAppCondition(subApp, page);
  }
  
  public List<Appinfo> getAppinfosByCondition(Appinfo appinfo, Pagination page)
    throws Exception
  {
    return this.appinfoDao.getAppinfosByCondition(appinfo, page);
  }
  
  public Userlist[] getUserlistBysql2(String string)
  {
    return this.userDao.getUserlistBysql2(string);
  }
}
