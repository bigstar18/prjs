package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.zcjs.dao.HisTradeCommodityMsgDao;
import gnnt.MEBS.zcjs.model.HisTradeCommodityMsg;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_hisTradeCommodityMsgService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class HisTradeCommodityMsgService
{
  @Autowired
  @Qualifier("z_hisTradeCommodityMsgDao")
  private HisTradeCommodityMsgDao hisTradeCommodityMsgDao;
  
  public void add(HisTradeCommodityMsg paramHisTradeCommodityMsg)
  {
    paramHisTradeCommodityMsg.setClearDate(new Date());
    this.hisTradeCommodityMsgDao.add(paramHisTradeCommodityMsg);
    if (paramHisTradeCommodityMsg.getUploadingName() != null) {
      this.hisTradeCommodityMsgDao.updateBlob(paramHisTradeCommodityMsg);
    }
  }
  
  public List getObject(long paramLong)
  {
    return this.hisTradeCommodityMsgDao.getObject(paramLong);
  }
}
