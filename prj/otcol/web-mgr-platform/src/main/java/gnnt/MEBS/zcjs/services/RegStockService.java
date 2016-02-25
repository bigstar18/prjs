package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.RegStockDao;
import gnnt.MEBS.zcjs.model.ValidRegstock;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_regStockService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class RegStockService
{
  @Autowired
  @Qualifier("z_regStockDao")
  private RegStockDao regStockDao;
  
  public ValidRegstock getObjectById(String paramString)
  {
    return this.regStockDao.getObject(paramString);
  }
  
  public List getRegStockTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.regStockDao.getTableList(paramQueryConditions, paramPageInfo);
  }
  
  public int mod(ValidRegstock paramValidRegstock)
  {
    return this.regStockDao.mod(paramValidRegstock);
  }
  
  public List<ValidRegstock> getRegStockList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.regStockDao.getRegStockList(paramQueryConditions, paramPageInfo);
  }
  
  public void delete(String paramString)
  {
    this.regStockDao.delete(paramString);
  }
}
