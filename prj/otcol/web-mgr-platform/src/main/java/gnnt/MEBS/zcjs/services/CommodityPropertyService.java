package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.CommodityPropertyDao;
import gnnt.MEBS.zcjs.model.CommodityProperty;
import gnnt.MEBS.zcjs.model.innerObject.CommodityPropertyObject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_commodityPropertyService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CommodityPropertyService
{
  @Autowired
  @Qualifier("z_commodityPropertyDao")
  private CommodityPropertyDao commodityPropertyDao;
  @Autowired
  @Qualifier("z_commodityParameterService")
  private CommodityParameterService commodityParameterService;
  
  public List<Map<String, Object>> getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.commodityPropertyDao.getTableList(paramQueryConditions, paramPageInfo);
  }
  
  public List<CommodityPropertyObject> getPropertyObjectList(List<CommodityPropertyObject> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    Map localMap = getProperty();
    CommodityPropertyObject localCommodityPropertyObject = null;
    for (int i = 0; i < paramList.size(); i++)
    {
      localCommodityPropertyObject = (CommodityPropertyObject)paramList.get(i);
      localCommodityPropertyObject.setId(Long.parseLong((String)localMap.get(localCommodityPropertyObject.getKey())));
      localArrayList.add(localCommodityPropertyObject);
    }
    return localArrayList;
  }
  
  public void update(List<Long> paramList, String paramString)
  {
    CommodityProperty localCommodityProperty = null;
    for (int i = 0; i < paramList.size(); i++)
    {
      localCommodityProperty = this.commodityPropertyDao.getObject(((Long)paramList.get(i)).longValue());
      localCommodityProperty.setStatus(Integer.parseInt(paramString));
      this.commodityPropertyDao.update(localCommodityProperty);
    }
  }
  
  public Map<String, String> getProperty()
  {
    HashMap localHashMap = new HashMap();
    List localList = getTableList(null, null);
    Object localObject = null;
    if ((localList != null) && (localList.size() > 0)) {
      for (int i = 0; i < localList.size(); i++)
      {
        Map localMap = (Map)localList.get(i);
        localHashMap.put((String)localMap.get("key"), ((BigDecimal)localMap.get("propertyid")).toString());
      }
    }
    return localHashMap;
  }
  
  public List<CommodityProperty> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.commodityPropertyDao.getObjectList(paramQueryConditions, paramPageInfo);
  }
}
