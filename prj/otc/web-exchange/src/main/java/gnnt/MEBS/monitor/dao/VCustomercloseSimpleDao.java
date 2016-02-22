package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VCustomercloseSimple;
import org.springframework.stereotype.Repository;

@Repository("vCustomercloseSimpleDao")
public class VCustomercloseSimpleDao
  extends BaseDao
{
  public Class getEntityClass()
  {
    return VCustomercloseSimple.class;
  }
}
