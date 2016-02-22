package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.QMemberVODao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("qMemberVOService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class QMemberVOService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(QMemberVOService.class);
  @Autowired
  @Qualifier("qMemberVODao")
  private QMemberVODao qMemberVODao;
  
  public BaseDao getDao()
  {
    return this.qMemberVODao;
  }
  
  public List getMemberList(String condition)
  {
    String sql = "select m.memberno, m.name from m_memberinfo m, t_compmember t where m.memberno=t.m_firmid(+) and t.status in ('N','F') and " + condition + " order by m.memberno";
    
    List list = null;
    list = this.qMemberVODao.queryForList(sql);
    return list;
  }
  
  public List getSpecialMemberList(String condition)
  {
    String sql = "select m.memberno, m.name from m_s_memberinfo m, t_specialmember t where m.memberno=t.m_firmid(+) and t.status in ('N','F') and " + condition + " order by memberno";
    
    List list = null;
    list = this.qMemberVODao.queryForList(sql);
    return list;
  }
  
  public List getAgencyList(String condition)
  {
    String sql = "select organizationno, parentorganizationno, memberno, name from m_b_organization start with " + condition + " and parentorganizationno is null connect by prior organizationno = parentorganizationno";
    
    List list = null;
    list = this.qMemberVODao.queryForOrganizationList(sql);
    return list;
  }
  
  public List getProductList()
  {
    String sql = "select t.commodityid as memberno, t.name from t_commodity t where t.status=1";
    
    List list = null;
    list = this.qMemberVODao.queryForList(sql);
    return list;
  }
}
