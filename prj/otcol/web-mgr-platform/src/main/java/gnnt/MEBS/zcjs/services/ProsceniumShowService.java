package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.ProsceniumShowDao;
import gnnt.MEBS.zcjs.model.ProsceniumShow;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_prosceniumShowService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ProsceniumShowService
{
  @Autowired
  @Qualifier("z_prosceniumShowDao")
  private ProsceniumShowDao prosceniumShowDao;
  
  public List<ProsceniumShow> getObjectList(QueryConditions paramQueryConditions)
  {
    return this.prosceniumShowDao.getObjectList(paramQueryConditions, null);
  }
  
  public void update(String[] paramArrayOfString)
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("ProsceniumApplication", "=", "QT_C_GoodsOrder");
    List localList = getObjectList(localQueryConditions);
    Object localObject;
    if (localList.size() > 0)
    {
      localObject = localList.iterator();
      while (((Iterator)localObject).hasNext())
      {
        ProsceniumShow localProsceniumShow1 = (ProsceniumShow)((Iterator)localObject).next();
        localProsceniumShow1.setIsShow("N");
        this.prosceniumShowDao.update(localProsceniumShow1);
      }
    }
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0)) {
      for (String str : paramArrayOfString)
      {
        ProsceniumShow localProsceniumShow2 = this.prosceniumShowDao.getObject("QT_C_GoodsOrder", str);
        if (localProsceniumShow2 != null)
        {
          localProsceniumShow2.setIsShow("Y");
          this.prosceniumShowDao.update(localProsceniumShow2);
        }
      }
    }
  }
  
  public List getProsceniumapplicationList(QueryConditions paramQueryConditions)
  {
    return this.prosceniumShowDao.getProsceniumapplicationList(paramQueryConditions);
  }
  
  public void insert(ProsceniumShow paramProsceniumShow)
  {
    this.prosceniumShowDao.insert(paramProsceniumShow);
  }
}
