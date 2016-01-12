package gnnt.MEBS.timebargain.mgr.service.impl;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.dao.FirmSpecialDao;
import gnnt.MEBS.timebargain.mgr.service.FirmSpecialService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("firmSpecialService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class FirmSpecialServiceImpl extends StandardService
  implements FirmSpecialService
{

  @Autowired
  @Qualifier("firmSpecialDao")
  private FirmSpecialDao firmSpecialDao;

  public String getSystemStatus()
  {
    String status = null;

    List list = this.firmSpecialDao.getSystemStatus();

    for (int i = 0; i < list.size(); i++) {
      Map map = (Map)list.get(0);
      status = map.get("STATUS").toString();
    }

    return status;
  }

  public FirmSpecialDao getFirmSpecialDao()
  {
    return this.firmSpecialDao;
  }
}