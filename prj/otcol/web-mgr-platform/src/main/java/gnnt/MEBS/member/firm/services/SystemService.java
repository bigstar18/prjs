package gnnt.MEBS.member.firm.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.member.firm.dao.SystemDao;
import gnnt.MEBS.member.firm.unit.TradeModule;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("m_systemService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SystemService
{
  private final transient Log logger = LogFactory.getLog(SystemService.class);
  @Autowired
  @Qualifier("m_systemDao")
  private SystemDao systemDao;
  
  public List getTradeModuleList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.systemDao.getTradeModuleList(paramQueryConditions, paramPageInfo);
  }
  
  public List getSystemList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.systemDao.getSystemList(paramQueryConditions, paramPageInfo);
  }
  
  public void updateSystem(TradeModule paramTradeModule)
  {
    this.systemDao.updateSystem(paramTradeModule);
  }
  
  public TradeModule getSystem(String paramString)
  {
    return this.systemDao.getSystemById(paramString);
  }
  
  public List getBankList()
  {
    return this.systemDao.getBankList();
  }
}
