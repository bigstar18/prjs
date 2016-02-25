package gnnt.MEBS.member.firm.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.member.firm.dao.ConfigurationDao;
import gnnt.MEBS.member.firm.dao.ErrorLoginLogDao;
import gnnt.MEBS.member.firm.unit.Configuration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("m_errorLoginLogService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ErrorLoginLogService
{
  private final transient Log logger = LogFactory.getLog(ErrorLoginLogService.class);
  @Autowired
  @Qualifier("m_errorLoginLogDao")
  private ErrorLoginLogDao errorLoginLogDao;
  @Autowired
  @Qualifier("m_configurationDao")
  private ConfigurationDao configurationDao;
  
  public List<Map<String, Object>> getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    if (paramQueryConditions == null)
    {
      paramQueryConditions = new QueryConditions();
      paramQueryConditions.addCondition("counts", ">=", Integer.valueOf(Integer.parseInt(getConfiguration("allowLogonError").getValue())));
    }
    else
    {
      paramQueryConditions.addCondition("counts", ">=", Integer.valueOf(Integer.parseInt(getConfiguration("allowLogonError").getValue())));
    }
    return this.errorLoginLogDao.getErrorLoginTableList(paramQueryConditions, paramPageInfo);
  }
  
  public void delete(String[] paramArrayOfString)
  {
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
    {
      for (String str : paramArrayOfString) {
        this.errorLoginLogDao.delete(str);
      }
    }
    else
    {
      ??? = getTableList(null, null);
      if ((??? != null) && (((List)???).size() > 0))
      {
        Iterator localIterator = ((List)???).iterator();
        while (localIterator.hasNext())
        {
          Map localMap = (Map)localIterator.next();
          this.errorLoginLogDao.delete(localMap.get("traderId").toString());
        }
      }
    }
  }
  
  public List<Map<String, Object>> getErrorLoginById(String paramString)
  {
    return this.errorLoginLogDao.getErrorLoginById(paramString);
  }
  
  public Configuration getConfiguration(String paramString)
  {
    return this.configurationDao.getObject(paramString);
  }
}
