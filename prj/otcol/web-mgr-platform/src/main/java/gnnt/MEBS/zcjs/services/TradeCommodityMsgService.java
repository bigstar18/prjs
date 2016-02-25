package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.TradeCommodityMsgDao;
import gnnt.MEBS.zcjs.model.TradeCommodityMsg;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_tradeCommodityMsgService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TradeCommodityMsgService
{
  @Autowired
  @Qualifier("z_tradeCommodityMsgDao")
  private TradeCommodityMsgDao tradeCommodityMsgDao;
  
  public void add(TradeCommodityMsg paramTradeCommodityMsg)
  {
    long l = this.tradeCommodityMsgDao.getId();
    paramTradeCommodityMsg.setTradeCommodityMsgId(l);
    this.tradeCommodityMsgDao.add(paramTradeCommodityMsg);
    if (paramTradeCommodityMsg.getUploadingName() != null) {
      this.tradeCommodityMsgDao.updateBlob(paramTradeCommodityMsg);
    }
  }
  
  public void delete(long paramLong)
  {
    this.tradeCommodityMsgDao.delete(paramLong);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<TradeCommodityMsg> getList(QueryConditions paramQueryConditions)
  {
    return this.tradeCommodityMsgDao.getObjectList(paramQueryConditions);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public TradeCommodityMsg getObject(long paramLong)
  {
    return this.tradeCommodityMsgDao.getObject(paramLong);
  }
  
  public void update(TradeCommodityMsg paramTradeCommodityMsg)
  {
    this.tradeCommodityMsgDao.update(paramTradeCommodityMsg);
  }
  
  public TradeCommodityMsg getObjectLock(long paramLong)
  {
    return this.tradeCommodityMsgDao.getObjectLock(paramLong);
  }
  
  public TradeCommodityMsg getObjectBig(long paramLong)
  {
    TradeCommodityMsg localTradeCommodityMsg1 = this.tradeCommodityMsgDao.getObject(paramLong);
    TradeCommodityMsg localTradeCommodityMsg2 = this.tradeCommodityMsgDao.getObjectBig(paramLong);
    localTradeCommodityMsg1.addUploading(localTradeCommodityMsg2.getUploading());
    return localTradeCommodityMsg1;
  }
}
