package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.CommodityParameterDao;
import gnnt.MEBS.zcjs.model.CommodityParameter;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_commodityParameterService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CommodityParameterService
{
  @Autowired
  @Qualifier("z_commodityParameterDao")
  private CommodityParameterDao commodityParameterDao;
  
  public List<Map<String, Object>> getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    if (paramQueryConditions == null)
    {
      paramQueryConditions = new QueryConditions();
      paramQueryConditions.addCondition("parameterStatus", "<>", Integer.valueOf(3));
    }
    else
    {
      paramQueryConditions.addCondition("parameterStatus", "<>", Integer.valueOf(3));
    }
    return this.commodityParameterDao.getTableList(paramQueryConditions, paramPageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<CommodityParameter> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.commodityParameterDao.getObjectList(paramQueryConditions, null);
  }
  
  public void update(List<Long> paramList, String paramString)
  {
    for (int i = 0; i < paramList.size(); i++)
    {
      CommodityParameter localCommodityParameter = this.commodityParameterDao.getObject(((Long)paramList.get(i)).longValue());
      localCommodityParameter.setParameterStatus(Integer.parseInt(paramString));
      this.commodityParameterDao.update(localCommodityParameter);
    }
  }
  
  public void add(CommodityParameter paramCommodityParameter)
  {
    this.commodityParameterDao.add(paramCommodityParameter);
  }
  
  public void del(CommodityParameter paramCommodityParameter)
  {
    this.commodityParameterDao.delete(paramCommodityParameter);
  }
  
  public void updateObject(CommodityParameter paramCommodityParameter)
  {
    this.commodityParameterDao.update(paramCommodityParameter);
  }
  
  public CommodityParameter getObjectById(long paramLong)
  {
    return this.commodityParameterDao.getObject(paramLong);
  }
}
