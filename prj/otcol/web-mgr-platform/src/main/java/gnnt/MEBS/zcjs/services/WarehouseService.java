package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.warehouse.CommodityDao;
import gnnt.MEBS.zcjs.dao.warehouse.CommodityExpansionDao;
import gnnt.MEBS.zcjs.dao.warehouse.WarehouseRegStockDao;
import gnnt.MEBS.zcjs.model.Delivery;
import gnnt.MEBS.zcjs.model.WarehouseCommodity;
import gnnt.MEBS.zcjs.model.WarehouseCommodityExpansion;
import gnnt.MEBS.zcjs.model.WarehouseRegStock;
import gnnt.MEBS.zcjs.model.innerObject.CommodityPropertyObject;
import gnnt.MEBS.zcjs.util.ParseXML;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_warehouseService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class WarehouseService
{
  @Autowired
  @Qualifier("z_warehouseRegStockDao")
  private WarehouseRegStockDao warehouseRegStockDao;
  @Autowired
  @Qualifier("z_commodityPropertyService")
  private CommodityPropertyService commodityPropertyService;
  @Autowired
  @Qualifier("z_qualityService")
  private QualityService qualityService;
  @Autowired
  @Qualifier("z_commodityDao")
  private CommodityDao commodityDao;
  @Autowired
  @Qualifier("z_commodityExpansionDao")
  private CommodityExpansionDao commodityExpansionDao;
  
  public List<Map<String, Object>> getWareRegStockTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.warehouseRegStockDao.getWarehouseTableList(paramQueryConditions, paramPageInfo);
  }
  
  public WarehouseRegStock getWarehouseRegStock(String paramString)
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("regStockID", "=", paramString);
    WarehouseRegStock localWarehouseRegStock = null;
    List localList1 = getWareRegStockTableList(localQueryConditions, null);
    Map localMap = null;
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    if ((localList1 != null) && (localList1.size() > 0))
    {
      localWarehouseRegStock = new WarehouseRegStock();
      localMap = (Map)localList1.get(0);
      localWarehouseRegStock.setBreedId(((BigDecimal)localMap.get("breedId")).longValue());
      localWarehouseRegStock.setCreateTime((Date)localMap.get("createTime"));
      localWarehouseRegStock.setFirmId((String)localMap.get("firmId"));
      localWarehouseRegStock.setFrozenWeight(((BigDecimal)localMap.get("frozenWeight")).doubleValue());
      localWarehouseRegStock.setInitWeight(((BigDecimal)localMap.get("initWeight")).doubleValue());
      localWarehouseRegStock.setModifyTime((Date)localMap.get("modifyTime"));
      localWarehouseRegStock.setName((String)localMap.get("name"));
      localWarehouseRegStock.setRegStockId((String)localMap.get("regStockId"));
      localWarehouseRegStock.setStockId((String)localMap.get("stockId"));
      localWarehouseRegStock.setType(((BigDecimal)localMap.get("type")).intValue());
      localWarehouseRegStock.setUnitWeight(((BigDecimal)localMap.get("unitWeight")).doubleValue());
      localWarehouseRegStock.setWarehouseId((String)localMap.get("warehouseId"));
      localWarehouseRegStock.setWeight(((BigDecimal)localMap.get("weight")).doubleValue());
      CommodityPropertyObject localCommodityPropertyObject1 = new CommodityPropertyObject();
      localCommodityPropertyObject1.setKey("Grade");
      localCommodityPropertyObject1.setName("等级");
      localCommodityPropertyObject1.setValue((String)localMap.get("Grade"));
      localArrayList1.add(localCommodityPropertyObject1);
      CommodityPropertyObject localCommodityPropertyObject2 = new CommodityPropertyObject();
      localCommodityPropertyObject2.setKey("Origin");
      localCommodityPropertyObject2.setName("产地");
      localCommodityPropertyObject2.setValue((String)localMap.get("Origin"));
      localArrayList1.add(localCommodityPropertyObject2);
      CommodityPropertyObject localCommodityPropertyObject3 = new CommodityPropertyObject();
      localCommodityPropertyObject3.setKey("Sort");
      localCommodityPropertyObject3.setValue((String)localMap.get("Sort"));
      localCommodityPropertyObject3.setName("种类");
      localArrayList1.add(localCommodityPropertyObject3);
      List localList2 = this.commodityPropertyService.getPropertyObjectList(localArrayList1);
      localWarehouseRegStock.setPropertyObjectList(localList2);
      List localList3 = ParseXML.addToXml((String)localMap.get("qualityStandard"));
      localWarehouseRegStock.setQualityObjectList(this.qualityService.getQualityObjectList(localList3));
    }
    return localWarehouseRegStock;
  }
  
  public int updateRegstockFrozen(String paramString1, double paramDouble, String paramString2, int paramInt)
  {
    return this.warehouseRegStockDao.updateRegstockFrozen(paramString1, paramDouble, paramString2, paramInt);
  }
  
  public int createSettleMatch(Delivery paramDelivery, String paramString)
  {
    return this.warehouseRegStockDao.createSettleMatch(paramDelivery, paramString);
  }
  
  public void insertRegStock(WarehouseRegStock paramWarehouseRegStock)
  {
    this.warehouseRegStockDao.addRegStock(paramWarehouseRegStock);
  }
  
  public String getRegStockId()
  {
    return this.warehouseRegStockDao.getRegStockId();
  }
  
  public void deleteSaleRegStock(String paramString)
  {
    this.warehouseRegStockDao.deleteRegstock(paramString);
  }
  
  public List<Map<String, Object>> getCommodityList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.commodityDao.getTableList(paramQueryConditions, paramPageInfo);
  }
  
  public WarehouseCommodity getCommodityById(String paramString)
  {
    return this.commodityDao.getObject(paramString);
  }
  
  public List<WarehouseCommodity> getCommodityObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.commodityDao.getObjectList(paramQueryConditions, paramPageInfo);
  }
  
  public List<WarehouseCommodityExpansion> getwcExpansion(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.commodityExpansionDao.getObjectList(paramQueryConditions, paramPageInfo);
  }
}
