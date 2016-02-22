package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VCustomerfundTip;
import org.springframework.stereotype.Repository;

@Repository("vCustomerfundTipDao")
public class VCustomerfundTipDao
  extends BaseDao
{
  public Class getEntityClass()
  {
    return VCustomerfundTip.class;
  }
}
