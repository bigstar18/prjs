package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.CustomerFundSearchHis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerFundSearchHisDao")
public class CustomerFundSearchHisDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerFundSearchHisDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerFundSearchHis().getClass();
  }
}
