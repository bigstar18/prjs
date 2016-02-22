package gnnt.MEBS.report.service;

import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.report.dao.MemberCustomerAcountDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memberCustomerAcountService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class MemberCustomerAcountService
{
  @Autowired
  @Qualifier("memberCustomerAcountDao")
  private MemberCustomerAcountDao memberCustomerAcountDao;
  
  public List getDistinctList(QueryConditions qc)
  {
    return this.memberCustomerAcountDao.getDistinctData(qc);
  }
  
  public List getAllList(QueryConditions qc)
  {
    try
    {
      return this.memberCustomerAcountDao.getAllData(qc);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
