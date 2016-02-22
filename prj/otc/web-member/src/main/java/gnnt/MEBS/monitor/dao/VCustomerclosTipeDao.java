package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VCustomercloseTip;
import org.springframework.stereotype.Repository;

@Repository("vCustomerclosTipeDao")
public class VCustomerclosTipeDao
  extends BaseDao
{
  public Class getEntityClass()
  {
    return VCustomercloseTip.class;
  }
}
