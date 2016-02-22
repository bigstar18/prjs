package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.VSmembersumDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("vSmembersumService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class VSmembersumService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(VSmembersumService.class);
  @Autowired
  @Qualifier("vSmembersumDao")
  private VSmembersumDao vSmembersumDao;
  
  public BaseDao getDao()
  {
    return this.vSmembersumDao;
  }
  
  public List getListAll(String pid)
  {
    String sql = "select  commodityname,smemberno,sum(buyqty) as buyqty,sum(sellqty) as sellqty,sum(buyqty*envenpricebuy)/decode(sum(buyqty),0,1,sum(buyqty)) as envenpricebuy,sum(sellqty*envenpricesell)/decode(sum(sellqty),0,1,sum(sellqty))   as envenpricesell,sum(buyloss) as buyloss,sum(sellloss) as sellloss,sum(netqty) as netqty,sum(netloss) as netloss from v_smembersum where smemberno like '" + pid + "%' group by commodityname,smemberno ";
    List list = null;
    list = this.vSmembersumDao.queryForList(sql);
    return list;
  }
}
