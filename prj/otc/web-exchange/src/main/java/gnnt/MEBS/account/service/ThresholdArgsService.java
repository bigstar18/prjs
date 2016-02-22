package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.MemberInfoProDao;
import gnnt.MEBS.account.dao.ThresholdArgsDao;
import gnnt.MEBS.account.model.ThresholdArgs;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("thresholdArgsService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ThresholdArgsService
  extends BaseService<ThresholdArgs>
{
  @Autowired
  @Qualifier("thresholdArgsDao")
  private ThresholdArgsDao thresholdArgsDao;
  @Autowired
  @Qualifier("memberInfoProDao")
  private MemberInfoProDao memberInfoProDao;
  
  public BaseDao getDao()
  {
    return this.thresholdArgsDao;
  }
  
  public ThresholdArgs get(ThresholdArgs clone)
  {
    List list = getDao().getList(null, null);
    ThresholdArgs thresholdArgs = null;
    if ((list != null) && (list.size() > 0)) {
      thresholdArgs = (ThresholdArgs)list.get(0);
    }
    return thresholdArgs;
  }
  
  public int updateClosePosition(ThresholdArgs obj)
  {
    int num = 0;
    ThresholdArgs thresholdArgs = get(obj);
    delete(thresholdArgs);
    add(thresholdArgs);
    num = 3;
    return num;
  }
  
  public int forceClose(String customerNo, String operator)
  {
    int result = this.memberInfoProDao.forceClose(customerNo, operator);
    return result;
  }
}
