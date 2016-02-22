package gnnt.MEBS.report.service;

import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.report.dao.TradeDateListDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("tradeDateListService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TradeDateListService
{
  @Autowired
  @Qualifier("tradeDateListDao")
  private TradeDateListDao tradeDateListDao;
  
  public List getList(QueryConditions qc)
  {
    return this.tradeDateListDao.getList(qc);
  }
}
