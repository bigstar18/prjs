package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.TradeInfoDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_tradeInfoService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TradeInfoService
{
  @Autowired
  @Qualifier("z_tradeInfoDao")
  private TradeInfoDao tradeInfoDao;
  
  public List getTradeInfoList(String paramString, QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.tradeInfoDao.getTradeInfoList(paramString, paramQueryConditions, paramPageInfo);
  }
}
