package gnnt.MEBS.common.broker.service;

import gnnt.MEBS.common.broker.common.Page;
import gnnt.MEBS.common.broker.common.PageRequest;
import gnnt.MEBS.common.broker.dao.StandardDao;
import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.broker.statictools.Tools;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("com_standardService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class StandardService
{
  protected final transient Log logger = LogFactory.getLog(getClass());

  @Autowired
  @Qualifier("com_standardDao")
  private StandardDao standardDao;

  public StandardDao getDao()
  {
    return this.standardDao;
  }

  public void add(StandardModel paramStandardModel)
  {
    this.logger.debug("enter add");
    this.standardDao.add(paramStandardModel);
  }

  public void update(StandardModel paramStandardModel)
  {
    this.logger.debug("enter update");
    update(paramStandardModel, false);
  }

  public void update(StandardModel paramStandardModel, boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      StandardModel localStandardModel = paramStandardModel.clone();
      paramStandardModel = get(paramStandardModel);
      if (paramStandardModel != null)
        Tools.CombinationValue(localStandardModel, paramStandardModel);
      else
        paramStandardModel = localStandardModel;
    }
    this.standardDao.update(paramStandardModel);
  }

  public void delete(StandardModel paramStandardModel)
  {
    this.logger.debug("enter delete");
    this.standardDao.delete(paramStandardModel);
  }

  public void deleteBYBulk(Collection<StandardModel> paramCollection)
  {
    this.logger.debug("enter deleteBYBulk entities");
    this.standardDao.deleteBYBulk(paramCollection);
  }

  public void deleteBYBulk(StandardModel paramStandardModel, Object[] paramArrayOfObject)
  {
    this.logger.debug("enter deleteBYBulk ids");
    if (paramArrayOfObject == null)
      return;
    if (paramStandardModel == null)
      throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量删除！");
    if ((paramStandardModel.fetchPKey() == null) || (paramStandardModel.fetchPKey().getKey() == null) || (paramStandardModel.fetchPKey().getKey().length() == 0))
      throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量删除！");
    this.standardDao.deleteBYBulk(paramStandardModel, paramArrayOfObject);
  }

  public void updateBYBulk(StandardModel paramStandardModel, String paramString, Object[] paramArrayOfObject)
  {
    this.logger.debug("enter updateBYBulk ids");
    if (paramArrayOfObject == null)
      return;
    if ("".equals(paramString))
      throw new IllegalArgumentException("要修改的列以及对应的值未知，不允许通过主键数组批量修改！");
    if (paramStandardModel == null)
      throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量修改！");
    if ((paramStandardModel.fetchPKey() == null) || (paramStandardModel.fetchPKey().getKey() == null) || (paramStandardModel.fetchPKey().getKey().length() == 0))
      throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量修改！");
    this.standardDao.updateBYBulk(paramStandardModel, paramString, paramArrayOfObject);
  }

  public void executeUpdateBySql(String paramString)
  {
    this.standardDao.executeUpdateBySql(paramString);
  }

  public List<StandardModel> getListByBulk(StandardModel paramStandardModel, Object[] paramArrayOfObject)
  {
    this.logger.debug("enter getListByBulk ids");
    return this.standardDao.getListByBulk(paramStandardModel, paramArrayOfObject);
  }

  public StandardModel get(StandardModel paramStandardModel)
  {
    this.logger.debug("enter get");
    return this.standardDao.get(paramStandardModel);
  }

  public Object executeProcedure(String paramString, Object[] paramArrayOfObject)
    throws Exception
  {
    return this.standardDao.executeProcedure(paramString, paramArrayOfObject);
  }

  public Page<StandardModel> getPage(PageRequest<?> paramPageRequest, StandardModel paramStandardModel)
  {
    this.logger.debug("enter getPage");
    return this.standardDao.getPage(paramPageRequest, paramStandardModel);
  }

  public void reload(Object paramObject)
  {
    this.standardDao.getHibernateTemplate().flush();
    this.standardDao.getHibernateTemplate().refresh(paramObject);
  }

  public List<StandardModel> getListBySql(String paramString, StandardModel paramStandardModel)
  {
    this.logger.debug("enter getListBySql getListBySql(final String sql,final StandardModel entity)");
    return this.standardDao.queryBySql(paramString, paramStandardModel);
  }

  public List<Map<Object, Object>> getListBySql(String paramString)
  {
    this.logger.debug("enter getListBySql  getListBySql(final String sql) ");
    return this.standardDao.queryBySql(paramString);
  }

  public Date getSysDate()
    throws Exception
  {
    this.logger.debug("enter getSysDate  getSysDate() ");
    List localList = this.standardDao.queryBySql("select to_char(sysdate,'yyyy-MM-dd HH24:MI:SS') as aa from dual");
    Date localDate = new Date();
    if ((localList != null) && (localList.size() > 0))
    {
      Map localMap = (Map)localList.get(0);
      String str = (String)localMap.get("AA");
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      localDate = localSimpleDateFormat.parse(str);
    }
    return localDate;
  }
}