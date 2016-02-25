package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.BreedDao;
import gnnt.MEBS.zcjs.dao.CommodityPropertyDao;
import gnnt.MEBS.zcjs.dao.QualityDao;
import gnnt.MEBS.zcjs.dao.RegStockDao;
import gnnt.MEBS.zcjs.model.Breed;
import gnnt.MEBS.zcjs.model.CommodityProperty;
import gnnt.MEBS.zcjs.model.Quality;
import gnnt.MEBS.zcjs.model.ValidRegstock;
import gnnt.MEBS.zcjs.model.WarehouseRegStock;
import gnnt.MEBS.zcjs.model.innerObject.KeyValue;
import gnnt.MEBS.zcjs.util.ParseXML;
import gnnt.MEBS.zcjs.util.QualityRatio;
import gnnt.MEBS.zcjs.util.QualityUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_validRegstockService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ValidRegstockService
{
  @Autowired
  @Qualifier("z_validRegstockDao")
  private RegStockDao regStockDao;
  @Autowired
  @Qualifier("z_warehouseService")
  private WarehouseService whregStockService;
  @Autowired
  @Qualifier("z_commodityPropertyDao")
  private CommodityPropertyDao commodityPropertyDao;
  @Autowired
  @Qualifier("z_qualityDao")
  private QualityDao qualityDao;
  @Autowired
  @Qualifier("z_reGetService")
  private ReGetService reGetService;
  @Autowired
  @Qualifier("z_breedDao")
  private BreedDao breedDao;
  
  public List getRegStockList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.regStockDao.getTableList(paramQueryConditions, paramPageInfo);
  }
  
  public ValidRegstock getRegStock(String paramString)
  {
    return this.regStockDao.getObject(paramString);
  }
  
  public List<Map<String, Object>> getWHRegStockList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.whregStockService.getWareRegStockTableList(paramQueryConditions, paramPageInfo);
  }
  
  public void addRegStock(ValidRegstock paramValidRegstock, String paramString1, String paramString2)
    throws Exception
  {
    this.regStockDao.add(paramValidRegstock);
    Breed localBreed = this.breedDao.getObject(paramValidRegstock.getBreedId());
    double d = QualityUnit.uintObverse(paramValidRegstock.getQuantity(), localBreed.getUnitVolume());
    d = QualityRatio.ratioObverse(d, localBreed.getDeliveryRatio());
    int i = this.whregStockService.updateRegstockFrozen(paramString1, d, paramString2, 1);
    if (i != 1) {
      throw new Exception();
    }
  }
  
  public void deleteRegStock(String[] paramArrayOfString, String paramString)
    throws Exception
  {
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      ValidRegstock localValidRegstock = this.regStockDao.getObject(paramArrayOfString[i]);
      this.regStockDao.delete(paramArrayOfString[i]);
      Breed localBreed = this.breedDao.getObject(localValidRegstock.getBreedId());
      double d = QualityUnit.uintObverse(localValidRegstock.getQuantity(), localBreed.getUnitVolume());
      d = QualityRatio.ratioObverse(d, localBreed.getDeliveryRatio());
      int j = this.whregStockService.updateRegstockFrozen(paramArrayOfString[i], d, paramString, 0);
      if (j != 1) {
        throw new Exception();
      }
    }
  }
  
  public WarehouseRegStock getWHRegStock(String paramString)
  {
    WarehouseRegStock localWarehouseRegStock = this.whregStockService.getWarehouseRegStock(paramString);
    localWarehouseRegStock = this.reGetService.reGetWarehouseRegStock(localWarehouseRegStock);
    return localWarehouseRegStock;
  }
  
  public Map<String, Object> getRegStockView(String paramString)
  {
    List localList1 = this.regStockDao.getObjectAndBreedName(paramString);
    ArrayList localArrayList = null;
    HashMap localHashMap = new HashMap();
    if (((localList1 != null ? 1 : 0) & (localList1.size() > 0 ? 1 : 0)) != 0)
    {
      Map localMap = (Map)localList1.get(0);
      localArrayList = new ArrayList();
      KeyValue localKeyValue = new KeyValue();
      String str1 = (String)localMap.get("commodityProperties");
      List localList2 = ParseXML.addCommodityPropertyObjectToXml(str1);
      String str2 = (String)localMap.get("quality");
      List localList3 = ParseXML.addQualityObjectToXml(str2);
      localHashMap = new HashMap();
      localHashMap.put("quality", localList3);
      localHashMap.put("resultMap", localMap);
      localHashMap.put("property", localList2);
    }
    return localHashMap;
  }
  
  public void addSendRegStock(ValidRegstock paramValidRegstock, String paramString)
    throws Exception
  {
    WarehouseRegStock localWarehouseRegStock = new WarehouseRegStock();
    localWarehouseRegStock.setRegStockId(paramValidRegstock.getRegstockId());
    localWarehouseRegStock.setBreedId(paramValidRegstock.getBreedId());
    localWarehouseRegStock.setCreateTime(new Date());
    localWarehouseRegStock.setFirmId(paramValidRegstock.getFirmId());
    localWarehouseRegStock.setWarehouseId("");
    localWarehouseRegStock.setFrozenWeight(0.0D);
    localWarehouseRegStock.setInitWeight(paramValidRegstock.getQuantity());
    localWarehouseRegStock.setModifyTime(new Date());
    localWarehouseRegStock.setStockId("");
    localWarehouseRegStock.setWeight(paramValidRegstock.getQuantity());
    localWarehouseRegStock.setUnitWeight(1.0D);
    localWarehouseRegStock.setType(3);
    localWarehouseRegStock = this.reGetService.reGetWarehouseRegStockObverse(localWarehouseRegStock);
    this.whregStockService.insertRegStock(localWarehouseRegStock);
    int i = this.whregStockService.updateRegstockFrozen(localWarehouseRegStock.getRegStockId(), localWarehouseRegStock.getWeight(), paramString, 1);
    if (i != 1) {
      throw new Exception();
    }
    this.regStockDao.add(paramValidRegstock);
  }
  
  public void deleteSaleRegstock(String[] paramArrayOfString, String paramString)
  {
    for (String str : paramArrayOfString)
    {
      ValidRegstock localValidRegstock = getRegStock(str);
      this.whregStockService.deleteSaleRegStock(str);
      this.regStockDao.delete(str);
    }
  }
  
  public Map<String, Object> XMLProperty()
  {
    List localList1 = this.commodityPropertyDao.getObjectList(null, null);
    List localList2 = this.qualityDao.getObjectList(null, null);
    HashMap localHashMap = new HashMap();
    Iterator localIterator = localList1.iterator();
    Object localObject;
    while (localIterator.hasNext())
    {
      localObject = (CommodityProperty)localIterator.next();
      localHashMap.put(((CommodityProperty)localObject).getKey(), Long.valueOf(((CommodityProperty)localObject).getPropertyId()));
    }
    localIterator = localList2.iterator();
    while (localIterator.hasNext())
    {
      localObject = (Quality)localIterator.next();
      localHashMap.put(((Quality)localObject).getQualityName(), Long.valueOf(((Quality)localObject).getQualityId()));
    }
    return localHashMap;
  }
}
