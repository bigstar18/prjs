package gnnt.MEBS.timebargain.mgr.dao.impl;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.timebargain.mgr.dao.FirmTariffDao;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("firmTariffDao")
public class FirmTariffDaoImpl extends StandardDao
  implements FirmTariffDao
{
  public List getTariff()
  {
    String sql = "select distinct tariffid,tariffname from t_a_tariff order by tariffid";

    return queryBySql(sql);
  }
}