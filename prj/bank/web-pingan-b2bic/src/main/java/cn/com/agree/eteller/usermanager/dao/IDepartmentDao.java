package cn.com.agree.eteller.usermanager.dao;

import cn.com.agree.eteller.usermanager.persistence.Department;
import java.io.Serializable;
import java.util.List;

public abstract interface IDepartmentDao
{
  public abstract boolean addDepartment(Department paramDepartment);
  
  public abstract boolean deleteDepartment(Department paramDepartment);
  
  public abstract boolean updateDepartment(Department paramDepartment);
  
  public abstract Department[] getAllDepartment();
  
  public abstract Department[] getAllDepartment1(Department paramDepartment);
  
  public abstract boolean isHaveTeller(String paramString);
  
  public abstract boolean isHaveSubDep(String paramString);
  
  public abstract String getNewSysDepNo(String paramString);
  
  public abstract String getId(String paramString);
  
  public abstract Department[] getDepartmentBySupDepId(String paramString);
  
  public abstract Department[] getDepartmentByDepId(String paramString);
  
  public abstract void checkCycleSuperior(Department paramDepartment)
    throws Exception;
  
  public abstract List getDepartmentRoleList(Serializable paramSerializable);
  
  public abstract List getDep_finalFlag();
  
  public abstract void checkRoleUserList(String paramString, List paramList1, List paramList2)
    throws Exception;
  
  public abstract Department getUserDepnoInfo(String paramString);
}
