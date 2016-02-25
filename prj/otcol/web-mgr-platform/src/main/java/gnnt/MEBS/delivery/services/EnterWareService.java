package gnnt.MEBS.delivery.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.dao.EnterWareDao;
import gnnt.MEBS.delivery.model.workflow.EnterWare;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_enterWareService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class EnterWareService
{
  private final transient Log logger = LogFactory.getLog(EnterWareService.class);
  @Autowired
  @Qualifier("w_enterWareDao")
  private EnterWareDao enterWareDao;
  
  public void addEnterWare(EnterWare paramEnterWare)
  {
    String str = this.enterWareDao.getEnterWareId();
    paramEnterWare.setId(str);
    this.enterWareDao.addEnterWare(paramEnterWare);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getEnterWareList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.enterWareDao.getEnterWareList(paramQueryConditions, paramPageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public EnterWare getEnterWareById(String paramString)
  {
    EnterWare localEnterWare = null;
    QueryConditions localQueryConditions = null;
    if (paramString != null) {
      localQueryConditions = new QueryConditions("id", "=", paramString);
    } else {
      localQueryConditions = new QueryConditions();
    }
    List localList = this.enterWareDao.getEnterWares(localQueryConditions);
    if (localList.size() > 0) {
      localEnterWare = (EnterWare)localList.get(0);
    }
    return localEnterWare;
  }
  
  public void updateEnterWare(EnterWare paramEnterWare)
  {
    this.enterWareDao.updateEnterWare(paramEnterWare);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public EnterWare getEnterWareLock(String paramString)
  {
    return this.enterWareDao.getEnterWareLock(paramString);
  }
  
  public void releaseAmount(EnterWare paramEnterWare, double paramDouble, int paramInt)
  {
    double d1 = paramEnterWare.getExistAmount();
    double d2 = paramEnterWare.getFrozenAmount();
    if (paramInt == 3)
    {
      paramEnterWare.setFrozenAmount(d2 - paramDouble);
      paramEnterWare.setExistAmount(d1 - paramDouble);
      paramEnterWare.setQuantity(((d1 - paramDouble) / paramEnterWare.getUnitWeight()));
    }
    else if (paramInt == 2)
    {
      paramEnterWare.setFrozenAmount(d2 - paramDouble);
    }
    else if (paramInt == 1)
    {
      paramEnterWare.setExistAmount(d1 - paramDouble);
      paramEnterWare.setQuantity(((d1 - paramDouble) / paramEnterWare.getUnitWeight()));
    }
    this.enterWareDao.updateEnterWare(paramEnterWare);
  }
  
  public List getEnterWareListById(String paramString)
  {
    return this.enterWareDao.getEnterWareListById(paramString);
  }
  
  public String copyEnterWare(EnterWare paramEnterWare, double paramDouble, String paramString, boolean paramBoolean)
  {
    String str = null;
    EnterWare localEnterWare = (EnterWare)paramEnterWare.clone();
    str = this.enterWareDao.getEnterWareId();
    localEnterWare.setId(str);
    localEnterWare.setWeight(paramDouble);
    localEnterWare.setExistAmount(paramDouble);
    localEnterWare.setFirmId(paramString);
    localEnterWare.setQuantity((paramDouble / localEnterWare.getUnitWeight()));
    double d = 0.0D;
    if (!paramBoolean) {
      d = paramDouble;
    }
    localEnterWare.setFrozenAmount(d);
    localEnterWare.addQualityStandardList(paramEnterWare.getQualityStandardList());
    localEnterWare.setOldEnterWareId(paramEnterWare.getId());
    this.enterWareDao.addEnterWare(localEnterWare);
    return str;
  }
}
