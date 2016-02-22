package gnnt.MEBS.commodity.service;

import gnnt.MEBS.commodity.dao.CommoditytradepropDao;
import gnnt.MEBS.commodity.model.Commodity;
import gnnt.MEBS.commodity.model.Commoditytradeprop;
import gnnt.MEBS.commodity.model.CommoditytradepropKey;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("commoditytradepropService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CommoditytradepropService
  extends BaseService<Commodity>
{
  private final transient Log logger = LogFactory.getLog(CommodityService.class);
  @Autowired
  @Qualifier("commoditytradepropDao")
  private CommoditytradepropDao commoditytradepropDao;
  
  public int handleSaveDel(List<Commoditytradeprop> saveObj, List<Commoditytradeprop> delObj)
  {
    try
    {
      if (saveObj != null) {
        for (Commoditytradeprop aaa : saveObj) {
          this.commoditytradepropDao.save(aaa);
        }
      }
      if (delObj != null) {
        for (Commoditytradeprop aaa : delObj) {
          this.commoditytradepropDao.del(aaa);
        }
      }
      return 1;
    }
    catch (Exception e) {}
    return 0;
  }
  
  public Set<String> getListBySectionId(int sid)
  {
    List<Commoditytradeprop> ll = this.commoditytradepropDao.getList(sid);
    Set<String> set = new HashSet();
    for (Commoditytradeprop p : ll) {
      set.add(p.getMyId().getCommidityid());
    }
    return set;
  }
  
  public BaseDao getDao()
  {
    return this.commoditytradepropDao;
  }
}
