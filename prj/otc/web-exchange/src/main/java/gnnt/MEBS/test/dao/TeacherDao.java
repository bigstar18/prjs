package gnnt.MEBS.test.dao;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.test.model.Teacher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("teacherDao")
public class TeacherDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(TeacherDao.class);
  
  public Class getEntityClass()
  {
    return new Teacher().getClass();
  }
}
