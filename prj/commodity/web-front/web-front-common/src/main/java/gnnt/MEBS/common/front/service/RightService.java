package gnnt.MEBS.common.front.service;

import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.dao.RightDao;
import gnnt.MEBS.common.front.dao.StandardDao;
import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.front.Right;
import gnnt.MEBS.common.front.model.integrated.User;
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
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class RightService
  extends StandardService
{
  @Autowired
  @Qualifier("com_rightDao")
  private RightDao com_rightDao;
  
  public StandardDao getDao()
  {
    return this.com_rightDao;
  }
  
  public Right getRightBySubFilter(long paramLong)
  {
    return this.com_rightDao.getRightBySubFilter(paramLong, -1, 0);
  }
  
  public Right loadRightTree()
  {
    return this.com_rightDao.loadRightTree(0L, -2, 0);
  }
  
  public void saveUserRights(User paramUser, String[] paramArrayOfString)
  {
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
    {
      HashSet localHashSet = new HashSet();
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        Right localRight = new Right();
        localRight.setId(Long.valueOf(Long.parseLong(paramArrayOfString[i])));
        localRight = (Right)get(localRight);
        if (localRight != null) {
          localHashSet.add(localRight);
        }
      }
      paramUser.setRightSet(localHashSet);
    }
    else
    {
      paramUser.setRightSet(null);
    }
    update(paramUser);
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
