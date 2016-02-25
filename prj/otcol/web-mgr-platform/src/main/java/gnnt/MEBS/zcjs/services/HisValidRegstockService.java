package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.zcjs.dao.HisValidRegstockDao;
import gnnt.MEBS.zcjs.model.HisValidRegstock;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_hisValidRegstockService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class HisValidRegstockService
{
  @Autowired
  @Qualifier("z_hisValidRegstockDao")
  private HisValidRegstockDao hisValidRegstockDao;
  
  public void add(HisValidRegstock paramHisValidRegstock)
  {
    paramHisValidRegstock.setClearDate(new Date());
    this.hisValidRegstockDao.add(paramHisValidRegstock);
  }
}
