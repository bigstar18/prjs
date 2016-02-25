package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.CurrentFundDao;
import gnnt.MEBS.zcjs.model.CurrentFund;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_currentFundService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CurrentFundService
{
  @Autowired
  @Qualifier("z_currentFundDao")
  private CurrentFundDao currentFundDao;
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<CurrentFund> getList(QueryConditions paramQueryConditions, String paramString)
  {
    return this.currentFundDao.getList(paramQueryConditions, paramString);
  }
}
