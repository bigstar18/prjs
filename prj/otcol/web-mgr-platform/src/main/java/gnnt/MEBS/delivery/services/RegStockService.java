package gnnt.MEBS.delivery.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.dao.RegStockDao;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_regStockService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class RegStockService
{
  private final transient Log logger = LogFactory.getLog(RegStockService.class);
  @Autowired
  @Qualifier("w_regStockDao")
  private RegStockDao regStockDao;
  
  public String addRegStock(RegStock paramRegStock)
  {
    String str = this.regStockDao.getRegStockId();
    paramRegStock.setRegStockId(str);
    paramRegStock.setCreateTime(new Date());
    paramRegStock.setModifyTime(new Date());
    this.regStockDao.addRegStock(paramRegStock);
    return str;
  }
  
  public void updateRegStock(RegStock paramRegStock)
  {
    paramRegStock.setModifyTime(new Date());
    this.regStockDao.updateRegStock(paramRegStock);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public RegStock getRegStockById(String paramString)
  {
    QueryConditions localQueryConditions = new QueryConditions("RegStockID", "=", paramString);
    RegStock localRegStock = null;
    List localList = this.regStockDao.getRegStocks(localQueryConditions);
    if (localList.size() > 0) {
      localRegStock = (RegStock)localList.get(0);
    }
    return localRegStock;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getRegStockList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.regStockDao.getRegStockList(paramQueryConditions, paramPageInfo);
  }
  
  public void releaseAmount(RegStock paramRegStock, double paramDouble, int paramInt)
  {
    double d1 = paramRegStock.getWeight();
    double d2 = paramRegStock.getFrozenWeight();
    if (paramInt == 3)
    {
      paramRegStock.setFrozenWeight(d2 - paramDouble);
      paramRegStock.setWeight(d1 - paramDouble);
    }
    else if (paramInt == 2)
    {
      paramRegStock.setFrozenWeight(d2 - paramDouble);
    }
    else if (paramInt == 1)
    {
      paramRegStock.setWeight(d1 - paramDouble);
    }
    this.regStockDao.updateRegStock(paramRegStock);
  }
  
  public void frozenAmount(RegStock paramRegStock, double paramDouble)
  {
    double d = paramRegStock.getFrozenWeight();
    paramRegStock.setFrozenWeight(d + paramDouble);
    this.regStockDao.updateRegStock(paramRegStock);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public RegStock getRegStockForUpdate(String paramString)
  {
    return this.regStockDao.getRegStockForUpdate(paramString);
  }
  
  public String copyRegStock(RegStock paramRegStock, double paramDouble, String paramString1, String paramString2)
  {
    String str = null;
    RegStock localRegStock = (RegStock)paramRegStock.clone();
    paramRegStock.setCreateTime(null);
    paramRegStock.setModifyTime(null);
    localRegStock.setCreateTime(new Date());
    localRegStock.setModifyTime(new Date());
    localRegStock.setFrozenWeight(0.0D);
    localRegStock.setWeight(paramDouble);
    localRegStock.setInitWeight(paramDouble);
    localRegStock.setOldRegStockId(paramRegStock.getRegStockId());
    localRegStock.setFirmId(paramString1);
    localRegStock.setStockId(paramString2);
    str = this.regStockDao.getRegStockId();
    localRegStock.setRegStockId(str);
    this.regStockDao.addRegStock(localRegStock);
    return str;
  }
  
  public List getRegStocks(QueryConditions paramQueryConditions)
  {
    return this.regStockDao.getRegStocks(paramQueryConditions);
  }
}
