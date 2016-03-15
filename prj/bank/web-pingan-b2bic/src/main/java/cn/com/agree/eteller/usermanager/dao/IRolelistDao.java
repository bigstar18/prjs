package cn.com.agree.eteller.usermanager.dao;

import cn.com.agree.eteller.usermanager.persistence.Funclist;
import cn.com.agree.eteller.usermanager.persistence.Rolelist;
import java.io.Serializable;
import java.util.List;

public abstract interface IRolelistDao
{
  public abstract Rolelist getRole(Serializable paramSerializable);
  
  public abstract List getRoles(Long[] paramArrayOfLong);
  
  public abstract void checkCycleSuperior(Rolelist paramRolelist)
    throws Exception;
  
  public abstract void deleteRole(Serializable paramSerializable)
    throws Exception;
  
  public abstract List getRoleNotFinalList();
  
  public abstract List getRootRoleList();
  
  public abstract List getRoleDepartmentList(Serializable paramSerializable, List paramList);
  
  public abstract boolean addRole(Rolelist paramRolelist, Funclist[] paramArrayOfFunclist);
  
  public abstract Rolelist[] getAllRole();
}
