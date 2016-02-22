package gnnt.MEBS.trade.service;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.trade.dao.TCExchagerateDao;
import gnnt.MEBS.trade.dao.TQLogDao;
import gnnt.MEBS.trade.model.TQLog;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("tqLogService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TQLogService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(TQLogService.class);
  @Autowired
  @Qualifier("tqLogDao")
  private TQLogDao tqLogDao;
  @Autowired
  @Qualifier("tcExchagerateDao")
  private TCExchagerateDao tcExchagerateDao;
  
  public BaseDao getDao()
  {
    return this.tqLogDao;
  }
  
  public int add(TQLog obj, AgencyRMIBean rmi)
  {
    this.logger.debug("enter add");
    int num = 0;
    try
    {
      if (obj != null)
      {
        obj.setActionTime(new Timestamp(getSysDate().getTime()));
        this.tqLogDao.add(obj);
        
        Quotation quotation = new Quotation();
        






        quotation.setCommodityID(obj.getCommodityId());
        quotation.setCurPrice(obj.getQuoprice());
        quotation.setUpdateTime(new Timestamp(new Date().getTime()));
        
        num = rmi.setQuotation(quotation);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      num = -1;
    }
    num = 2;
    return num;
  }
  
  public List getExList()
  {
    return this.tcExchagerateDao.getList();
  }
}
