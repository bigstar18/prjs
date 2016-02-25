package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.FundFlowDao;
import gnnt.MEBS.zcjs.model.FundFlow;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_fundFlowService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class FundFlowService
{
  @Autowired
  @Qualifier("z_fundFlowDao")
  private FundFlowDao fundFlowDao;
  
  public List<Map<String, Object>> getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    return this.fundFlowDao.getTableList(paramQueryConditions, paramPageInfo, paramString);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<FundFlow> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    return this.fundFlowDao.getObjectList(paramQueryConditions, paramPageInfo, paramString);
  }
}
