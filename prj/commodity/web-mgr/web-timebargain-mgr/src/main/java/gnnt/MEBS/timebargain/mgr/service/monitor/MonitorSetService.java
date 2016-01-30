package gnnt.MEBS.timebargain.mgr.service.monitor;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.dao.monitor.MonitorSetDao;
import gnnt.MEBS.timebargain.mgr.util.ObjSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("monitorSetService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class MonitorSetService extends StandardService
{

  @Autowired
  @Qualifier("monitorSetDao")
  private MonitorSetDao dao;

  public ObjSet getQuotation(int pageSize, int pageIndex, String filter, String orderBy)
  {
    return this.dao.getQuotation(pageSize, pageIndex, filter, orderBy);
  }

  public ObjSet getOrders(int pageSize, int pageIndex, String filter, String orderBy)
  {
    return this.dao.getOrders(pageSize, pageIndex, filter, orderBy);
  }

  public ObjSet getTrade(int pageSize, int pageIndex, String filter, String orderBy)
  {
    return this.dao.getTrade(pageSize, pageIndex, filter, orderBy);
  }

  public ObjSet getTradeStatistic(int pageSize, int pageIndex, String filter, String orderBy)
  {
    return this.dao.getTradeStatistic(pageSize, pageIndex, filter, orderBy);
  }

  public ObjSet getFirmHoldSum(int pageSize, int pageIndex, String filter, String orderBy)
  {
    return this.dao.getFirmHoldSum(pageSize, pageIndex, filter, orderBy);
  }

  public ObjSet getFirmHoldSumByCmd(int pageSize, int pageIndex, String filter, String orderBy)
  {
    return this.dao.getFirmHoldSumByCmd(pageSize, pageIndex, filter, orderBy);
  }

  public ObjSet getFundsAnalysis(int pageSize, int pageIndex, String filter, String orderBy)
  {
    return this.dao.getFundsAnalysis(pageSize, pageIndex, filter, orderBy);
  }

  public ObjSet getAnalyseInfo(int pageSize, int pageIndex, String filter, String orderBy)
  {
    return this.dao.getAnalyseInfo(pageSize, pageIndex, filter, orderBy);
  }

  public ObjSet getFirmTradeQuantity(int pageSize, int pageIndex, String filter, String orderBy)
  {
    return this.dao.getFirmTradeQuantity(pageSize, pageIndex, filter, orderBy);
  }

  public List getFirmInfo(String firmID)
  {
    return this.dao.getFirmInfo(firmID);
  }

  public List getFirmHold(String firmID)
  {
    return this.dao.getFirmHold(firmID);
  }
}