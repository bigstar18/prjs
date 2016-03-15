package cn.com.agree.eteller.usermanager.spring;

import cn.com.agree.eteller.generic.spring.IGenericManager;
import cn.com.agree.eteller.usermanager.dao.IAppinfoDao;
import cn.com.agree.eteller.usermanager.dao.IDepartmentDao;
import cn.com.agree.eteller.usermanager.dao.IFuncListDao;
import cn.com.agree.eteller.usermanager.dao.IRolelistDao;
import cn.com.agree.eteller.usermanager.dao.ISubAppinfoDao;
import cn.com.agree.eteller.usermanager.dao.IUserDao;
import cn.com.agree.eteller.usermanager.dao.IUserManagerDao;
import cn.com.agree.eteller.usermanager.persistence.Userlist;

public abstract interface IUserManager
  extends IAppinfoDao, IFuncListDao, IRolelistDao, IUserDao, IUserManagerDao, IDepartmentDao, ISubAppinfoDao, IGenericManager
{
  public abstract Userlist[] getUserlistBysql2(String paramString);
}
