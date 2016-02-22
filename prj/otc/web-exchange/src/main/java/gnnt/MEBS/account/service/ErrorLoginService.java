package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.ErrorLoginDao;
import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("errorLoginService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ErrorLoginService
{
  private final transient Log logger = LogFactory.getLog(ErrorLoginService.class);
  @Autowired
  @Qualifier("errorLoginDao")
  private ErrorLoginDao errorLoginDao;
  
  public List<Map<String, Object>> getExList(QueryConditions qc, PageInfo pageInfo)
  {
    if (qc == null) {
      qc = new QueryConditions();
    }
    qc.addCondition("loginDate", "=", this.errorLoginDao.getSysDate(), "date");
    qc.addCondition("counts", ">=", Integer.valueOf(this.errorLoginDao.getCounts()));
    return this.errorLoginDao.getExList(qc, pageInfo);
  }
  
  public List<Map<String, Object>> getList(QueryConditions qc, PageInfo pageInfo)
  {
    if (qc == null) {
      qc = new QueryConditions();
    }
    qc.addCondition("loginDate", "=", this.errorLoginDao.getSysDate(), "date");
    return this.errorLoginDao.getList(qc, pageInfo);
  }
  
  public void delete(String[] ids)
  {
    for (String id : ids) {
      this.errorLoginDao.delete(id);
    }
  }
  
  public Date getSysDate()
  {
    return this.errorLoginDao.getSysDate();
  }
}
