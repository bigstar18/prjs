package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.DiscussPriceDao;
import gnnt.MEBS.zcjs.model.DiscussPrice;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_discussPriceService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class DiscussPriceService
{
  @Autowired
  @Qualifier("z_discussPriceDao")
  private DiscussPriceDao discussPriceDao;
  
  public void add(DiscussPrice paramDiscussPrice)
  {
    long l = this.discussPriceDao.getId();
    paramDiscussPrice.setDiscussPriceId(l);
    paramDiscussPrice.setDiscussPriceDate(new Date());
    paramDiscussPrice.setModifyDate(new Date());
    this.discussPriceDao.add(paramDiscussPrice);
  }
  
  public void delete(long paramLong)
  {
    this.discussPriceDao.delete(paramLong);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<DiscussPrice> getObjectList(QueryConditions paramQueryConditions)
  {
    return this.discussPriceDao.getObjectList(paramQueryConditions);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public DiscussPrice getObject(long paramLong)
  {
    return this.discussPriceDao.getObject(paramLong);
  }
  
  public void update(DiscussPrice paramDiscussPrice)
  {
    paramDiscussPrice.setModifyDate(new Date());
    this.discussPriceDao.update(paramDiscussPrice);
  }
  
  public void updateParameter(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3)
  {
    this.discussPriceDao.updateParameter(paramLong1, paramLong2, paramInt1, paramInt2, paramInt3, new Date());
  }
}
