package gnnt.MEBS.delivery.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.dao.RegStockApplyDao;
import gnnt.MEBS.delivery.model.workflow.RegStockApply;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_regStockApplyService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class RegStockApplyService
{
  private final transient Log logger = LogFactory.getLog(RegStockApplyService.class);
  @Autowired
  @Qualifier("w_regStockApplyDao")
  private RegStockApplyDao regStockApplyDao;
  
  public void addRegStockApply(RegStockApply paramRegStockApply)
  {
    String str = this.regStockApplyDao.getRegStockApplyId();
    paramRegStockApply.setApplyId(str);
    this.regStockApplyDao.addRegStockApply(paramRegStockApply);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public RegStockApply getRegStockApplyById(String paramString)
  {
    QueryConditions localQueryConditions = new QueryConditions("ApplyID", "=", paramString);
    RegStockApply localRegStockApply = null;
    List localList = this.regStockApplyDao.getRegStockApplys(localQueryConditions);
    if (localList.size() > 0) {
      localRegStockApply = (RegStockApply)localList.get(0);
    }
    return localRegStockApply;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public RegStockApply getRegStockApplyForUpdate(String paramString)
  {
    return this.regStockApplyDao.getRegStockApplyLock(paramString);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getRegStockApplyList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.regStockApplyDao.getRegStockApplyList(paramQueryConditions, paramPageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<RegStockApply> getRegStockApplys(QueryConditions paramQueryConditions)
  {
    return this.regStockApplyDao.getRegStockApplys(paramQueryConditions);
  }
  
  public void updateRegStockApply(RegStockApply paramRegStockApply)
  {
    this.regStockApplyDao.updateRegStockApply(paramRegStockApply);
  }
}
