package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.zcjs.dao.HisSubmitDao;
import gnnt.MEBS.zcjs.model.HisSubmit;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_hisSubmitService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class HisSubmitService
{
  @Autowired
  @Qualifier("z_hisSubmitDao")
  private HisSubmitDao hisSubmitDao;
  
  public void add(HisSubmit paramHisSubmit)
  {
    paramHisSubmit.setClearDate(new Date());
    this.hisSubmitDao.add(paramHisSubmit);
  }
}
