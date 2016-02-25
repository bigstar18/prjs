package gnnt.MEBS.delivery.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.dao.OutWareDao;
import gnnt.MEBS.delivery.model.workflow.OutWare;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_outWareService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class OutWareService
{
  @Autowired
  @Qualifier("w_outWareDao")
  private OutWareDao outWareDao;
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getOutWareList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.outWareDao.getOutWareList(paramQueryConditions, paramPageInfo);
  }
  
  public void addOutEnter(OutWare paramOutWare)
  {
    String str = this.outWareDao.getOutWareId();
    paramOutWare.setId(str);
    this.outWareDao.addOutWare(paramOutWare);
  }
  
  public void updateOutEnter(OutWare paramOutWare)
  {
    this.outWareDao.updateOutWare(paramOutWare);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public OutWare getOutWareById(String paramString)
  {
    QueryConditions localQueryConditions = new QueryConditions("id", "=", paramString);
    OutWare localOutWare = null;
    List localList = this.outWareDao.getOutWares(localQueryConditions);
    if (localList.size() > 0) {
      localOutWare = (OutWare)localList.get(0);
    }
    return localOutWare;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public OutWare getOutWareForUpdate(String paramString)
  {
    return this.outWareDao.getOutWareLock(paramString);
  }
  
  public List<OutWare> getRegStockApplys(QueryConditions paramQueryConditions)
  {
    return this.outWareDao.getOutWares(paramQueryConditions);
  }
}
