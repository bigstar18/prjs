package gnnt.MEBS.delivery.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.dao.CommodityDao;
import gnnt.MEBS.delivery.dao.entity.EntityBreedDao;
import gnnt.MEBS.delivery.model.Commodity;
import gnnt.MEBS.delivery.model.CommodityExpansion;
import gnnt.MEBS.delivery.model.OperateLog;
import gnnt.MEBS.delivery.model.entity.Breed;
import gnnt.MEBS.delivery.model.entity.BreedParameters;
import gnnt.MEBS.delivery.model.entity.BreedQuality;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_commodityService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CommodityService
{
  private final transient Log logger = LogFactory.getLog(CommodityService.class);
  @Autowired
  @Qualifier("w_commodityDao")
  private CommodityDao commodityDao;
  @Autowired
  @Qualifier("w_breedDao")
  private EntityBreedDao entityBreedDao;
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getCommodityListMap()
  {
    QueryConditions localQueryConditions = new QueryConditions("ability", "=", Integer.valueOf(0));
    return this.commodityDao.getCommoditys(localQueryConditions);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public Commodity getCommodityById(String paramString, boolean paramBoolean)
  {
    Commodity localCommodity = getCommodityById(paramString);
    if (paramBoolean)
    {
      List localList1 = this.commodityDao.getCommodityExpansiones(paramString, "Quality");
      Object localObject1 = localList1.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (CommodityExpansion)((Iterator)localObject1).next();
        this.logger.debug(((CommodityExpansion)localObject2).getName());
      }
      localCommodity.addQualityList(localList1);
      localObject1 = this.commodityDao.getCommodityExpansiones(paramString, "Grade");
      localCommodity.addGradeList((List)localObject1);
      Object localObject2 = this.commodityDao.getCommodityExpansiones(paramString, "Origin");
      localCommodity.addOriginList((List)localObject2);
      List localList2 = this.commodityDao.getCommodityExpansiones(paramString, "Sort");
      localCommodity.addSortList(localList2);
    }
    return localCommodity;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getCommodityList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.commodityDao.getCommodityList(paramQueryConditions, paramPageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public Commodity getCommodityById(String paramString)
  {
    QueryConditions localQueryConditions = new QueryConditions("id", "=", paramString);
    Commodity localCommodity = null;
    List localList = this.commodityDao.getCommoditys(localQueryConditions);
    if (localList.size() > 0) {
      localCommodity = (Commodity)localList.get(0);
    }
    return localCommodity;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getT_CommodityList(String paramString)
  {
    QueryConditions localQueryConditions = new QueryConditions("commodityid", "=", paramString);
    List localList = this.commodityDao.getT_Commoditys(localQueryConditions);
    if (localList.size() == 0) {
      localList = this.commodityDao.getSettleCommodity(localQueryConditions);
    }
    return localList;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getT_BreedList(long paramLong)
  {
    QueryConditions localQueryConditions = new QueryConditions("BreedID", "=", Long.valueOf(paramLong));
    List localList = this.commodityDao.getT_Breeds(localQueryConditions);
    return localList;
  }
  
  public int addCommondity(Commodity paramCommodity)
  {
    int i = 0;
    List localList1 = null;
    localList1 = this.commodityDao.checkIdCommodity(paramCommodity);
    if (localList1.size() != 0) {
      i = -1;
    }
    localList1 = this.commodityDao.checkNameCommodity(paramCommodity);
    if (localList1.size() != 0) {
      i = -2;
    }
    if (i == 0)
    {
      this.commodityDao.addCommodity(paramCommodity);
      QueryConditions localQueryConditions = new QueryConditions();
      localQueryConditions.addCondition("breedId", "=", paramCommodity.getId());
      List localList2 = this.entityBreedDao.getEntityParameterList(localQueryConditions, null);
      Object localObject2;
      Object localObject3;
      if ((localList2 != null) && (localList2.size() > 0))
      {
        localObject1 = localList2.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (BreedParameters)((Iterator)localObject1).next();
          localObject3 = new CommodityExpansion();
          ((CommodityExpansion)localObject3).setCommodityId(paramCommodity.getId());
          ((CommodityExpansion)localObject3).setKind(((BreedParameters)localObject2).getPropertyKey());
          ((CommodityExpansion)localObject3).setName(((BreedParameters)localObject2).getName());
          ((CommodityExpansion)localObject3).setNo(((BreedParameters)localObject2).getNo());
          this.commodityDao.addCommodityExpansion((CommodityExpansion)localObject3);
        }
      }
      Object localObject1 = this.entityBreedDao.getEntityQualityList(localQueryConditions, null);
      if ((localObject1 != null) && (((List)localObject1).size() > 0))
      {
        localObject2 = ((List)localObject1).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = (BreedQuality)((Iterator)localObject2).next();
          CommodityExpansion localCommodityExpansion = new CommodityExpansion();
          localCommodityExpansion.setCommodityId(paramCommodity.getId());
          localCommodityExpansion.setKind("Quality");
          localCommodityExpansion.setName(((BreedQuality)localObject3).getQualityName());
          localCommodityExpansion.setNo(((BreedQuality)localObject3).getNo());
          this.commodityDao.addCommodityExpansion(localCommodityExpansion);
        }
      }
    }
    return i;
  }
  
  public void refurbish(String[] paramArrayOfString)
  {
    for (String str : paramArrayOfString)
    {
      Commodity localCommodity = getCommodityById(str);
      this.commodityDao.updateCommodity(localCommodity);
      QueryConditions localQueryConditions = new QueryConditions();
      localQueryConditions.addCondition("breedId", "=", localCommodity.getId());
      List localList = this.entityBreedDao.getEntityParameterList(localQueryConditions, null);
      this.commodityDao.delCommodityExpansiones(localCommodity.getId());
      Object localObject2;
      Object localObject3;
      if ((localList != null) && (localList.size() > 0))
      {
        localObject1 = localList.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (BreedParameters)((Iterator)localObject1).next();
          localObject3 = new CommodityExpansion();
          ((CommodityExpansion)localObject3).setCommodityId(localCommodity.getId());
          ((CommodityExpansion)localObject3).setKind(((BreedParameters)localObject2).getPropertyKey());
          ((CommodityExpansion)localObject3).setName(((BreedParameters)localObject2).getName());
          ((CommodityExpansion)localObject3).setNo(((BreedParameters)localObject2).getNo());
          this.commodityDao.addCommodityExpansion((CommodityExpansion)localObject3);
        }
      }
      Object localObject1 = this.entityBreedDao.getEntityQualityList(localQueryConditions, null);
      if ((localObject1 != null) && (((List)localObject1).size() > 0))
      {
        localObject2 = ((List)localObject1).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = (BreedQuality)((Iterator)localObject2).next();
          CommodityExpansion localCommodityExpansion = new CommodityExpansion();
          localCommodityExpansion.setCommodityId(localCommodity.getId());
          localCommodityExpansion.setKind("Quality");
          localCommodityExpansion.setName(((BreedQuality)localObject3).getQualityName());
          localCommodityExpansion.setNo(((BreedQuality)localObject3).getNo());
          this.commodityDao.addCommodityExpansion(localCommodityExpansion);
        }
      }
    }
  }
  
  public void updateCommodity(Commodity paramCommodity)
  {
    this.commodityDao.updateCommodity(paramCommodity);
  }
  
  public int deleteCommodity(Commodity paramCommodity, OperateLog paramOperateLog)
  {
    int i = 1;
    this.commodityDao.updateCommodity(paramCommodity);
    return i;
  }
  
  public List getEntityBreedList()
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("status", "=", Integer.valueOf(0));
    return this.entityBreedDao.getEntityBreedList(localQueryConditions, null);
  }
  
  public Breed getEntityBreed(String paramString)
  {
    return this.entityBreedDao.getEntityBreed(paramString);
  }
}
