package gnnt.MEBS.timebargain.broker.service.report;

import gnnt.MEBS.common.broker.service.StandardService;
import gnnt.MEBS.timebargain.broker.dao.report.CommReportDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("commReportService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class CommReportService extends StandardService
{

  @Autowired
  @Qualifier("commReportDao")
  public CommReportDao commReportDao;

  public List getCommodityList(String paramString)
  {
    return this.commReportDao.getCommodityList(paramString);
  }

  public List getBrokerageList(String paramString)
  {
    return this.commReportDao.getBrokerageList(paramString);
  }
}