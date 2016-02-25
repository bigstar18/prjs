package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.DailySubmitDao;
import gnnt.MEBS.zcjs.model.Submit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_dailySubmitService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class DailySubmitService
{
  @Autowired
  @Qualifier("z_dailySubmitDao")
  private DailySubmitDao dailysubmitDao;
  
  public void add(Submit paramSubmit)
  {
    long l = this.dailysubmitDao.getId();
    paramSubmit.setSubmitId(l);
    this.dailysubmitDao.add(paramSubmit);
  }
  
  public void delete(long paramLong)
  {
    this.dailysubmitDao.delete(paramLong);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<Submit> getList(QueryConditions paramQueryConditions)
  {
    return this.dailysubmitDao.getObjectList(paramQueryConditions);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public Submit getObject(long paramLong)
  {
    return this.dailysubmitDao.getObject(paramLong);
  }
  
  public void update(Submit paramSubmit)
  {
    this.dailysubmitDao.update(paramSubmit);
  }
  
  public Submit getObjectLock(long paramLong)
  {
    return this.dailysubmitDao.getObjectLock(paramLong);
  }
  
  public int selectSubmit()
  {
    return this.dailysubmitDao.selectSubmit();
  }
}
