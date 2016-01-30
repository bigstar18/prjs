package gnnt.MEBS.common.front.service;

import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.dao.AbstractDao;
import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.statictools.Tools;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

public abstract class AbstractService
{
  protected final transient Log logger = LogFactory.getLog(getClass());
  private AbstractDao abstractDao;
  private static long retime = 0L;
  private static long lasttime = 0L;
  
  public void setAbstractDao(AbstractDao paramAbstractDao)
  {
    this.abstractDao = paramAbstractDao;
  }
  
  public List<StandardModel> getListByBulk(StandardModel paramStandardModel, Object[] paramArrayOfObject)
  {
    this.logger.debug("enter getListByBulk ids");
    return this.abstractDao.getListByBulk(paramStandardModel, paramArrayOfObject);
  }
  
  public StandardModel get(StandardModel paramStandardModel)
  {
    this.logger.debug("enter get");
    return this.abstractDao.get(paramStandardModel);
  }
  
  public Page<StandardModel> getPage(PageRequest<?> paramPageRequest, StandardModel paramStandardModel)
  {
    this.logger.debug("enter getPage");
    return this.abstractDao.getPage(paramPageRequest, paramStandardModel);
  }
  
  public List<StandardModel> getListBySql(String paramString, StandardModel paramStandardModel)
  {
    this.logger.debug("enter getListBySql getListBySql(final String sql,final StandardModel entity)");
    return this.abstractDao.queryBySql(paramString, paramStandardModel);
  }
  
  public List<Map<Object, Object>> getListBySql(String paramString)
  {
    this.logger.debug("enter getListBySql  getListBySql(final String sql) ");
    return this.abstractDao.queryBySql(paramString);
  }
  
  public Date getSysDate()
  {
    Date localDate1 = new Date();
    long l = 60000L;
    Date localDate2 = new Date();
    if ((localDate1.getTime() - lasttime > l) || (lasttime - localDate1.getTime() > l))
    {
      lasttime = localDate1.getTime();
      this.logger.debug("enter getSysDate  getSysDate() ");
      List localList = this.abstractDao.queryBySql("select to_char(sysdate,'yyyy-MM-dd HH24:MI:SS') dbtime from dual");
      if ((localList != null) && (localList.size() > 0))
      {
        Map localMap = (Map)localList.get(0);
        String str = (String)localMap.get("DBTIME");
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
          localDate2 = localSimpleDateFormat.parse(str);
          retime = localDate2.getTime() - localDate1.getTime();
        }
        catch (ParseException localParseException)
        {
          this.logger.error(Tools.getExceptionTrace(localParseException));
        }
      }
    }
    else
    {
      localDate2 = new Date(localDate1.getTime() + retime);
    }
    return localDate2;
  }
  
  public void reload(Object paramObject)
  {
    this.abstractDao.getHibernateTemplate().flush();
    this.abstractDao.getHibernateTemplate().refresh(paramObject);
  }
}
