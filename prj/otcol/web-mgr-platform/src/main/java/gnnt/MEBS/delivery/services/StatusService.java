package gnnt.MEBS.delivery.services;

import gnnt.MEBS.delivery.dao.StatusDao;
import gnnt.MEBS.delivery.model.Status;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_statusService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class StatusService
{
  @Autowired
  @Qualifier("w_statusDao")
  private StatusDao statusDao;
  
  public Status getStatus(String paramString, int paramInt)
  {
    return this.statusDao.getStatus(paramString, paramInt);
  }
  
  public void setStatusDao(StatusDao paramStatusDao)
  {
    this.statusDao = paramStatusDao;
  }
  
  public List getStatusMap(String paramString)
  {
    return this.statusDao.getStatusMap(paramString);
  }
}
