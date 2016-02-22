package gnnt.MEBS.packaging.service;

import gnnt.MEBS.base.copy.CopyObjectParamUtil;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public abstract class BaseService<T extends Clone>
  implements InService<T>
{
  private final transient Log logger = LogFactory.getLog(BaseService.class);
  protected Class<T> type;
  
  public int add(T obj)
  {
    this.logger.debug("enter add");
    int num = 0;
    Clone clone = (Clone)obj.clone();
    getDao().add(clone);
    if (clone.getId() != null) {
      obj.setPrimary(clone.getId().toString());
    }
    num = 2;
    return num;
  }
  
  public int update(T obj)
  {
    int num = 0;
    this.logger.debug("enter update");
    Clone objFor = copyObject(obj);
    if (objFor != null) {
      getDao().update(objFor);
    }
    num = 3;
    return num;
  }
  
  public Clone copyObject(T obj)
  {
    Clone objFor = get(obj);
    this.logger.debug(Boolean.valueOf(objFor == null));
    this.logger.debug("objFor:" + objFor.getClass().getName());
    CopyObjectParamUtil.bindData(obj, objFor);
    return objFor;
  }
  
  public int delete(T obj)
  {
    int num = 0;
    this.logger.debug("enter delete");
    getDao().delete(obj);
    num = 4;
    return num;
  }
  
  public int delete(T[] objs)
  {
    int num = 0;
    if (objs != null) {
      for (T obj : objs) {
        getDao().delete(obj);
      }
    }
    return num;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<T> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    this.logger.debug("enter getList");
    return getDao().getList(conditions, pageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public T get(T clone)
  {
    this.logger.debug("enter get");
    Serializable id = clone.getId();
    if (id == null)
    {
      T cloneCopy = (Clone)clone.clone();
      return getDao().get(cloneCopy);
    }
    return getDao().getById(id);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public int getTotalCount(QueryConditions conditions)
  {
    this.logger.debug("enter getTotalCount");
    return getDao().getTotalCount(conditions);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public T getById(Serializable id)
  {
    this.logger.debug("enter getById");
    return getDao().getById(id);
  }
  
  @PostConstruct
  public void init()
  {
    if (getDao() != null)
    {
      this.logger.debug("init" + getDao().getEntityClass().getName());
      this.type = getDao().getEntityClass();
    }
  }
  
  public Date getSysDate()
  {
    return getDao().getSysDate();
  }
}
