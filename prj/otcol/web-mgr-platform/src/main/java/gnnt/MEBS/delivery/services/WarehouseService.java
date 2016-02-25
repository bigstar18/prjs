package gnnt.MEBS.delivery.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.dao.UserDao;
import gnnt.MEBS.delivery.dao.WarehouseDao;
import gnnt.MEBS.delivery.model.Commodity;
import gnnt.MEBS.delivery.model.OperateLog;
import gnnt.MEBS.delivery.model.User;
import gnnt.MEBS.delivery.model.Warehouse;
import gnnt.MEBS.delivery.model.inner.WarehouseRelateCommodity;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_warehouseService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class WarehouseService
{
  @Autowired
  @Qualifier("w_warehouseDao")
  private WarehouseDao warehouseDao;
  @Autowired
  @Qualifier("w_userDao")
  private UserDao userDao;
  @Autowired
  @Qualifier("w_commodityService")
  private CommodityService commodityService;
  
  public Warehouse getWarehouse(String paramString)
  {
    Warehouse localWarehouse = null;
    QueryConditions localQueryConditions = null;
    if ((paramString != null) && (!"".equals(paramString)))
    {
      localQueryConditions = new QueryConditions("id", "=", paramString);
      List localList = this.warehouseDao.getWarehouses(localQueryConditions);
      if (localList.size() > 0) {
        localWarehouse = (Warehouse)localList.get(0);
      }
    }
    return localWarehouse;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public String getWarehouseName(String paramString)
  {
    String str = "";
    Warehouse localWarehouse = getWarehouse(paramString);
    if (localWarehouse != null) {
      str = localWarehouse.getName();
    }
    return str;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public Warehouse getWarehouseById(String paramString, boolean paramBoolean1, boolean paramBoolean2)
    throws SQLException
  {
    Warehouse localWarehouse = getWarehouse(paramString);
    if ((paramBoolean1) && (localWarehouse != null))
    {
      List localList = this.warehouseDao.getWarehouseRelateCommodity(localWarehouse);
      HashSet localHashSet = new HashSet();
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        WarehouseRelateCommodity localWarehouseRelateCommodity = (WarehouseRelateCommodity)localIterator.next();
        Commodity localCommodity = this.commodityService.getCommodityById(localWarehouseRelateCommodity.getCommodityId(), paramBoolean2);
        localHashSet.add(localCommodity);
      }
      localWarehouse.addCommoditySet(localHashSet);
    }
    return localWarehouse;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getWarehouseList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.warehouseDao.getWarehouseList(paramQueryConditions, paramPageInfo);
  }
  
  public int addWarehouse(Warehouse paramWarehouse, User paramUser, OperateLog paramOperateLog)
  {
    int i = 0;
    this.warehouseDao.getWarehouseLock(paramWarehouse);
    i = addOrUpdateWarehouse(paramWarehouse, true, paramOperateLog);
    if (i == 0)
    {
      this.warehouseDao.addWarehouse(paramWarehouse);
      this.userDao.deleteUser(paramUser.getUserId());
      this.userDao.addUser(paramUser);
    }
    return i;
  }
  
  public int updateWarehouse(Warehouse paramWarehouse, OperateLog paramOperateLog)
  {
    int i = 0;
    this.warehouseDao.getWarehouseLock(paramWarehouse);
    i = addOrUpdateWarehouse(paramWarehouse, false, paramOperateLog);
    if (i == 0) {
      this.warehouseDao.updateWarehouse(paramWarehouse);
    }
    return i;
  }
  
  public int RelateCommodityWarehouse(Warehouse paramWarehouse, OperateLog paramOperateLog)
  {
    int i = 0;
    this.warehouseDao.deleteRelateCommodityWarehouse(paramWarehouse);
    String str1 = paramWarehouse.getId();
    Set localSet = paramWarehouse.getCommoditySet();
    if ((localSet != null) && (localSet.size() > 0))
    {
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        Commodity localCommodity = (Commodity)localIterator.next();
        String str2 = localCommodity.getId();
        WarehouseRelateCommodity localWarehouseRelateCommodity = new WarehouseRelateCommodity();
        localWarehouseRelateCommodity.setCommodityId(str2);
        localWarehouseRelateCommodity.setWarehouseId(str1);
        this.warehouseDao.relateCommodityWarehouse(localWarehouseRelateCommodity);
      }
    }
    return i;
  }
  
  public int addOrUpdateWarehouse(Warehouse paramWarehouse, boolean paramBoolean, OperateLog paramOperateLog)
  {
    int i = 0;
    List localList = null;
    QueryConditions localQueryConditions = null;
    if ((paramBoolean) && (getWarehouse(paramWarehouse.getId()) != null))
    {
      i = -1;
      return i;
    }
    if (paramBoolean)
    {
      localQueryConditions = new QueryConditions("name", "=", paramWarehouse.getName());
    }
    else
    {
      localQueryConditions = new QueryConditions("id", "!=", paramWarehouse.getId());
      localQueryConditions.addCondition("name", "=", paramWarehouse.getName());
    }
    localList = this.warehouseDao.getWarehouses(localQueryConditions);
    if (localList.size() != 0)
    {
      i = -2;
      return i;
    }
    return i;
  }
  
  public int deleteWareHouseById(String paramString, OperateLog paramOperateLog)
  {
    return this.warehouseDao.deleteWareHouseById(paramString);
  }
  
  public int deleteGoResumeWareHouseById(String paramString, OperateLog paramOperateLog)
  {
    return this.warehouseDao.deleteGoResumeWareHouseById(paramString);
  }
  
  public List getWarehouseList()
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("ability", "=", "0");
    return this.warehouseDao.getWarehouses(localQueryConditions);
  }
  
  public int deleteWarehouseJudge(String paramString)
  {
    int i = 0;
    boolean bool1 = this.warehouseDao.getEnterApply(paramString);
    if (bool1) {
      i = 1;
    }
    boolean bool2 = this.warehouseDao.getEnterWare(paramString);
    if (bool2) {
      i = 2;
    }
    boolean bool3 = this.warehouseDao.getStandardWare(paramString);
    if (bool3) {
      i = 3;
    }
    return i;
  }
  
  public void deleteWarehouse(List<String> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      QueryConditions localQueryConditions1 = new QueryConditions();
      localQueryConditions1.addCondition("id", "=", str);
      QueryConditions localQueryConditions2 = new QueryConditions();
      localQueryConditions2.addCondition("warehouseid", "=", str);
      QueryConditions localQueryConditions3 = new QueryConditions();
      localQueryConditions3.addCondition("manage_id", "=", str);
      this.warehouseDao.deleteWarehouse(localQueryConditions1);
      this.warehouseDao.deleteRelateCommodityWarehouse(localQueryConditions2);
      this.userDao.deleteUser(localQueryConditions3);
    }
  }
}
