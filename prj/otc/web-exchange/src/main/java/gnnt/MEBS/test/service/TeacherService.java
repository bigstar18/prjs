package gnnt.MEBS.test.service;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.test.dao.TeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("teacherService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TeacherService
  extends BaseService
{
  @Autowired
  @Qualifier("teacherDao")
  private TeacherDao teacherDao;
  
  public BaseDao getDao()
  {
    return this.teacherDao;
  }
}
