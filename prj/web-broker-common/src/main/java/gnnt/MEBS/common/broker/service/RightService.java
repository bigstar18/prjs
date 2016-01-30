package gnnt.MEBS.common.broker.service;

import gnnt.MEBS.common.broker.common.Global;
import gnnt.MEBS.common.broker.common.Page;
import gnnt.MEBS.common.broker.common.PageRequest;
import gnnt.MEBS.common.broker.dao.RightDao;
import gnnt.MEBS.common.broker.dao.StandardDao;
import gnnt.MEBS.common.broker.model.Right;
import gnnt.MEBS.common.broker.model.StandardModel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("com_rightService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class RightService extends StandardService
{

  @Autowired
  @Qualifier("com_rightDao")
  private RightDao rightDao;

  public StandardDao getDao()
  {
    return this.rightDao;
  }

  public Right getRightBySubFilter(long paramLong)
  {
    return this.rightDao.getRightBySubFilter(paramLong, -1, 0);
  }

  public Right loadRightTree()
  {
    return this.rightDao.loadRightTree(Global.ROOTRIGHTID, -2, 0);
  }

  public Set<Right> getAllRight()
  {
    HashSet localHashSet = new HashSet();
    PageRequest localPageRequest = new PageRequest(" and type!=-2 ");
    localPageRequest.setPageSize(100000);
    Page localPage = getPage(localPageRequest, new Right());
    List localList = localPage.getResult();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      StandardModel localStandardModel = (StandardModel)localIterator.next();
      localHashSet.add((Right)localStandardModel);
    }
    return localHashSet;
  }
}