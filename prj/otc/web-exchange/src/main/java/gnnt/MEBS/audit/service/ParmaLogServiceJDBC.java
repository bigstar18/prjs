package gnnt.MEBS.audit.service;

import gnnt.MEBS.audit.dao.ParmaLogJDBCDao;
import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("parmaLogServiceJDBC")
public class ParmaLogServiceJDBC
{
  @Autowired
  @Qualifier("parmaLogJDBCDao")
  private ParmaLogJDBCDao parmaLogJDBCDao;
  
  public List getApplyList(QueryConditions conditions, PageInfo pageInfo)
  {
    return this.parmaLogJDBCDao.getApplyList(conditions, pageInfo);
  }
  
  public List getSystemStatusDate(QueryConditions conditions, PageInfo pageInfo)
  {
    return this.parmaLogJDBCDao.getSystemStatusDate(conditions, pageInfo);
  }
  
  public void delete(String mySql)
  {
    this.parmaLogJDBCDao.delete(mySql);
  }
  
  public List getComodityStatus(QueryConditions conditions, PageInfo pageInfo)
  {
    return this.parmaLogJDBCDao.getComodityStatus(conditions, pageInfo);
  }
}
