package gnnt.MEBS.integrated.mgr.service;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.integrated.mgr.dao.FundDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("fundService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class FundService
  extends StandardService
{
  @Autowired
  @Qualifier("fundDao")
  private FundDao fundDao;
  
  public FundDao getFundDao()
  {
    return this.fundDao;
  }
  
  public double inmoneymodel(String paramString, double paramDouble)
    throws Exception
  {
    return this.fundDao.inmoneymodel(paramString, paramDouble);
  }
}
