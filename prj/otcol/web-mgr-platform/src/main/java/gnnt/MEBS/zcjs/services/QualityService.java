package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.QualityDao;
import gnnt.MEBS.zcjs.model.Quality;
import gnnt.MEBS.zcjs.model.innerObject.KeyValue;
import gnnt.MEBS.zcjs.model.innerObject.QualityObject;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_qualityService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class QualityService
{
  @Autowired
  @Qualifier("z_qualityDao")
  private QualityDao qualityDao;
  
  public List<Map<String, Object>> getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    if (paramQueryConditions == null)
    {
      paramQueryConditions = new QueryConditions();
      paramQueryConditions.addCondition("status", "<>", Integer.valueOf(3));
    }
    else
    {
      paramQueryConditions.addCondition("status", "<>", Integer.valueOf(3));
    }
    return this.qualityDao.getTableList(paramQueryConditions, paramPageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<Quality> getList(QueryConditions paramQueryConditions)
  {
    return this.qualityDao.getObjectList(paramQueryConditions, null);
  }
  
  public void mod(List<Long> paramList, int paramInt)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Long localLong = (Long)localIterator.next();
      Quality localQuality = this.qualityDao.getObject(localLong.longValue());
      localQuality.setStatus(paramInt);
      this.qualityDao.update(localQuality);
    }
  }
  
  public void add(Quality paramQuality)
  {
    this.qualityDao.add(paramQuality);
  }
  
  public List<QualityObject> getQualityObjectList(List<KeyValue> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    Map localMap = getQuality();
    QualityObject localQualityObject = null;
    if ((paramList != null) && (paramList.size() > 0)) {
      for (int i = 0; i < paramList.size(); i++)
      {
        localQualityObject = new QualityObject();
        localQualityObject.setName((String)((KeyValue)paramList.get(i)).getKey());
        localQualityObject.setValue((String)((KeyValue)paramList.get(i)).getValue());
        System.out.println((String)localMap.get(((KeyValue)paramList.get(i)).getKey()));
        System.out.println(localMap);
        localQualityObject.setId(Long.parseLong((String)localMap.get(((KeyValue)paramList.get(i)).getKey())));
        localArrayList.add(localQualityObject);
      }
    }
    return localArrayList;
  }
  
  public Map<String, String> getQuality()
  {
    HashMap localHashMap = new HashMap();
    List localList = this.qualityDao.getTableList(null, null);
    if ((localList != null) && (localList.size() > 0)) {
      for (int i = 0; i < localList.size(); i++)
      {
        Map localMap = (Map)localList.get(i);
        localHashMap.put((String)localMap.get("qualityName"), ((BigDecimal)localMap.get("qualityId")).toString());
      }
    }
    return localHashMap;
  }
  
  public Quality getObject(long paramLong)
  {
    return this.qualityDao.getObject(paramLong);
  }
}
