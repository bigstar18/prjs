package gnnt.MEBS.timebargain.mgr.service.impl;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.dao.FirmTariffDao;
import gnnt.MEBS.timebargain.mgr.service.FirmTariffService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("firmTariffService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class FirmTariffServiceImpl extends StandardService
  implements FirmTariffService
{

  @Autowired
  @Qualifier("firmTariffDao")
  private FirmTariffDao firmTariffDao;

  public List getTariff()
  {
    return this.firmTariffDao.getTariff();
  }

  public FirmTariffDao getFirmTariffDao() {
    return this.firmTariffDao;
  }
}