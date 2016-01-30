package gnnt.MEBS.broker.mgr.service;

import gnnt.MEBS.broker.mgr.dao.BaseDao;
import gnnt.MEBS.common.mgr.model.StandardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("baseService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BaseService
{

  @Autowired
  @Qualifier("baseDao")
  private BaseDao baseDao;

  public void add(StandardModel paramStandardModel)
  {
    this.baseDao.add(paramStandardModel);
  }

  public void update(StandardModel paramStandardModel)
  {
    this.baseDao.update(paramStandardModel);
  }
}