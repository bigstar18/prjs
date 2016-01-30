package gnnt.MEBS.common.front.service;

import gnnt.MEBS.common.front.dao.StandardDao;
import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.statictools.Tools;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("com_standardService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class StandardService
  extends AbstractService
{
  protected final transient Log logger = LogFactory.getLog(getClass());
  private StandardDao standardDao;
  
  @Resource(name="com_standardDao")
  public void setDao(StandardDao standardDao)
  {
    super.setAbstractDao(standardDao);
    this.standardDao = standardDao;
  }
  
  public StandardDao getDao()
  {
    return this.standardDao;
  }
  
  public void add(StandardModel entity)
  {
    this.logger.debug("enter add");
    this.standardDao.add(entity);
  }
  
  public void update(StandardModel entity)
  {
    this.logger.debug("enter update");
    update(entity, false);
  }
  
  public void updateForContainNull(StandardModel entity)
  {
    this.logger.debug("enter updateForContainNull");
    update(entity, true);
  }
  
  public void update(StandardModel entity, boolean isForceUpdate)
  {
    if (!isForceUpdate)
    {
      StandardModel model = entity.clone();
      
      entity = get(entity);
      if (entity != null) {
        Tools.CombinationValue(model, entity);
      } else {
        entity = model;
      }
    }
    this.standardDao.update(entity);
  }
  
  public void delete(StandardModel entity)
  {
    this.logger.debug("enter delete");
    this.standardDao.delete(entity);
  }
  
  public void deleteBYBulk(Collection<StandardModel> entities)
  {
    this.logger.debug("enter deleteBYBulk entities");
    this.standardDao.deleteBYBulk(entities);
  }
  
  public void deleteBYBulk(StandardModel entity, Object[] ids)
  {
    this.logger.debug("enter deleteBYBulk ids");
    if (ids == null) {
      return;
    }
    if (entity == null) {
      throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量删除！");
    }
    if ((entity.fetchPKey() == null) || (entity.fetchPKey().getKey() == null) || 
      (entity.fetchPKey().getKey().length() == 0)) {
      throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量删除！");
    }
    this.standardDao.deleteBYBulk(entity, ids);
  }
  
  public Object executeProcedure(String procedureName, Object[] params)
    throws Exception
  {
    return this.standardDao.executeProcedure(procedureName, params);
  }
  
  public List<Map<String, String>> getBrokerName()
    throws SQLException
  {
    this.logger.debug("enter query broker name");
    return this.standardDao.getBrokerName();
  }
}
