package gnnt.MEBS.broke.dao;

import gnnt.MEBS.broke.model.OrganizationForZTree;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("organizationZTreeDao")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class OrganizationZTreeDao
  extends BaseDao
{
  public Class getEntityClass()
  {
    return OrganizationForZTree.class;
  }
}
