package gnnt.MEBS.common.front.service;

import gnnt.MEBS.common.front.dao.QueryDao;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service("com_queryService")
public class QueryService
  extends AbstractService
{
  protected final transient Log logger = LogFactory.getLog(getClass());
  
  @Resource(name="com_queryDao")
  public void setDao(QueryDao paramQueryDao)
  {
    super.setAbstractDao(paramQueryDao);
  }
}
