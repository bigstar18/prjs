package gnnt.MEBS.delivery.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.dao.RegStockToEnterWareDao;
import gnnt.MEBS.delivery.model.workflow.RegStockToEnterWare;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_regStockToEnterWareService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class RegStockToEnterWareService
{
  private final transient Log logger = LogFactory.getLog(EnterWareService.class);
  @Autowired
  @Qualifier("w_regStockToEnterWareDao")
  private RegStockToEnterWareDao regStockToEnterWareDao;
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getRegStockToEnterWareList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.regStockToEnterWareDao.getRegStockToEnterWareList(paramQueryConditions, paramPageInfo);
  }
  
  public void addRegStockToEnterWare(RegStockToEnterWare paramRegStockToEnterWare)
  {
    String str = this.regStockToEnterWareDao.getRegStockToEnterWareId();
    paramRegStockToEnterWare.setId(str);
    this.regStockToEnterWareDao.addRegStockToEnterWare(paramRegStockToEnterWare);
  }
  
  public void updateRegStockToEnterWare(RegStockToEnterWare paramRegStockToEnterWare)
  {
    this.regStockToEnterWareDao.updateRegStockToEnterWare(paramRegStockToEnterWare);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public RegStockToEnterWare getRegStockToEnterWareById(String paramString)
  {
    QueryConditions localQueryConditions = new QueryConditions("id", "=", paramString);
    RegStockToEnterWare localRegStockToEnterWare = null;
    List localList = this.regStockToEnterWareDao.getRegStockToEnterWares(localQueryConditions);
    if (localList.size() > 0) {
      localRegStockToEnterWare = (RegStockToEnterWare)localList.get(0);
    }
    return localRegStockToEnterWare;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public RegStockToEnterWare getRegStockToEnterWareLock(String paramString)
  {
    return this.regStockToEnterWareDao.getRegStockToEnterWareLock(paramString);
  }
}
