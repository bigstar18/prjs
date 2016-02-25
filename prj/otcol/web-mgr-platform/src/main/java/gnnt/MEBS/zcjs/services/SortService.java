package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.SortDao;
import gnnt.MEBS.zcjs.model.Sort;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_sortService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SortService
{
  @Autowired
  @Qualifier("z_sortDao")
  private SortDao sortDao;
  
  public void insertSort(Sort paramSort)
  {
    this.sortDao.insert(paramSort);
  }
  
  public List<Sort> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.sortDao.getObjectList(paramQueryConditions, paramPageInfo);
  }
  
  public List<Map<String, Object>> getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.sortDao.getTableList(paramQueryConditions, paramPageInfo);
  }
  
  public void updateSort(Sort paramSort)
  {
    this.sortDao.update(paramSort);
  }
  
  public void delete(String[] paramArrayOfString)
  {
    for (int i = 0; i < paramArrayOfString.length; i++) {
      this.sortDao.delete(Long.parseLong(paramArrayOfString[i]));
    }
  }
  
  public long getId()
  {
    return this.sortDao.getId();
  }
  
  public Sort getObject(long paramLong)
  {
    return this.sortDao.getObject(paramLong);
  }
}
