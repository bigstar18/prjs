package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.CustomerFundFlowSearchHis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerFundFlowSearchHisDao")
public class CustomerFundFlowSearchHisDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerFundFlowSearchHisDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerFundFlowSearchHis().getClass();
  }
}
