package gnnt.MEBS.packaging.service;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public abstract interface InService<T extends Clone>
{
  public abstract int add(T paramT);
  
  public abstract int update(T paramT);
  
  public abstract int delete(T paramT);
  
  public abstract List<T> getList(QueryConditions paramQueryConditions, PageInfo paramPageInfo);
  
  public abstract T get(T paramT);
  
  public abstract T getById(Serializable paramSerializable);
  
  public abstract int getTotalCount(QueryConditions paramQueryConditions);
  
  public abstract BaseDao getDao();
  
  public abstract Date getSysDate();
}
