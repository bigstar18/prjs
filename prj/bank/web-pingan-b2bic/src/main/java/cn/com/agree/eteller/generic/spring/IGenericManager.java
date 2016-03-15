package cn.com.agree.eteller.generic.spring;

import cn.com.agree.eteller.generic.dao.IGenericDao;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.Collection;
import java.util.List;

public abstract interface IGenericManager
  extends IGenericDao
{
  public abstract List getCollection(Collection paramCollection, Class paramClass, Pagination paramPagination)
    throws Exception;
}
