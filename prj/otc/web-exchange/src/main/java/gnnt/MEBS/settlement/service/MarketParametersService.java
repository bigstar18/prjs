package gnnt.MEBS.settlement.service;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.settlement.dao.MarketParametersDao;
import gnnt.MEBS.settlement.model.MarketParameters;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("marketParametersService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MarketParametersService
  extends BaseService<MarketParameters>
{
  private final transient Log logger = LogFactory.getLog(MarketParametersService.class);
  @Autowired
  @Qualifier("marketParametersDao")
  private MarketParametersDao marketParametersDao;
  
  public BaseDao getDao()
  {
    return this.marketParametersDao;
  }
  
  public MarketParameters get(MarketParameters clone)
  {
    List list = getDao().getList(null, null);
    MarketParameters marketParameters = null;
    if ((list != null) && (list.size() > 0)) {
      marketParameters = (MarketParameters)list.get(0);
    }
    return marketParameters;
  }
}
