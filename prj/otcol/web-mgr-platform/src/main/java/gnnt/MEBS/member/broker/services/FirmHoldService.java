package gnnt.MEBS.member.broker.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.member.broker.dao.FirmHoldDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("m_firmHoldService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class FirmHoldService
{
  @Autowired
  @Qualifier("m_firmHoldDao")
  private FirmHoldDao firmHoldDao;
  
  public List<?> firmHoldList(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    return this.firmHoldDao.firmHoldList(paramQueryConditions, paramPageInfo, paramString);
  }
}
