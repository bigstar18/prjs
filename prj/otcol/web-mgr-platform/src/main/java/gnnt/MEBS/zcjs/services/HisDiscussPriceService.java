package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.zcjs.dao.HisDicussPriceDao;
import gnnt.MEBS.zcjs.model.HisDiscussPrice;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_hisDiscussPriceService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class HisDiscussPriceService
{
  @Autowired
  @Qualifier("z_hisDicussPriceDao")
  private HisDicussPriceDao hisDicussPriceDao;
  
  public void add(HisDiscussPrice paramHisDiscussPrice)
  {
    paramHisDiscussPrice.setClearDate(new Date());
    this.hisDicussPriceDao.add(paramHisDiscussPrice);
  }
  
  public HisDiscussPrice getObject(long paramLong)
  {
    return this.hisDicussPriceDao.getObject(paramLong);
  }
}
