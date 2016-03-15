package cn.com.agree.eteller.usermanager.dao;

import cn.com.agree.eteller.generic.utils.Pagination;
import cn.com.agree.eteller.usermanager.persistence.Department;
import cn.com.agree.eteller.usermanager.persistence.EtellerSubappinfo;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract interface IUserManagerDao
{
  public abstract void add(Object paramObject);
  
  public abstract void update(Object paramObject);
  
  public abstract void delete(Object paramObject);
  
  public abstract List getBranchList();
  
  public abstract List getRootDepartmentList();
  
  public abstract boolean hasRoleUnderling(Serializable paramSerializable);
  
  public abstract List getRoleUnderlingList(Long paramLong);
  
  public abstract List getRoleUnderlingWithCurrentList(Long paramLong);
  
  public abstract List getRoleUnderlingWithCurrentList(Long paramLong, boolean paramBoolean);
  
  public abstract List getRoleUnderlingListInDepartment(Long paramLong, String paramString);
  
  public abstract int countDepartmentDeepness(String paramString);
  
  public abstract int countDepartmentDeepness(Department paramDepartment);
  
  public abstract List getDepartmentList();
  
  public abstract List getRoleList();
  
  public abstract List getRoleListInDepartment(String paramString);
  
  public abstract List getSubDepartmentList(String paramString, Map<String, String> paramMap);
  
  public abstract List getSubDepartmentList1(String paramString, Department paramDepartment);
  
  public abstract List getSubDepartmentWithCurrentList_final(String paramString);
  
  public abstract List getSubDepartmentWithCurrentList(String paramString);
  
  public abstract List getDirectSubDepartmentList(String paramString);
  
  public abstract List getDirectSubDepartmentWithCurrentList(String paramString);
  
  public abstract Department getDepartment(Serializable paramSerializable);
  
  public abstract List getUnderlingList(Serializable paramSerializable, Map paramMap);
  
  public abstract Department getDepartmentByNewSystemNo(String paramString);
  
  public abstract List getRoleList(String paramString);
  
  public abstract List getDeptListByMap(Map<String, Object> paramMap, Pagination paramPagination);
  
  public abstract List<EtellerSubappinfo> getBySubAppCondition(EtellerSubappinfo paramEtellerSubappinfo, Pagination paramPagination);
}
