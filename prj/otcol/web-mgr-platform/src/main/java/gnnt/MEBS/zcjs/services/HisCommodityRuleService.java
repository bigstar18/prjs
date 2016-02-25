package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.zcjs.dao.HisCommodityRuleDao;
import gnnt.MEBS.zcjs.model.HisCommodityRule;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_hisCommodityRuleService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class HisCommodityRuleService
{
  @Autowired
  @Qualifier("z_hisCommodityRuleDao")
  private HisCommodityRuleDao hisCommodityRuleDao;
  
  public void add(HisCommodityRule paramHisCommodityRule)
  {
    paramHisCommodityRule.setClearDate(new Date());
    this.hisCommodityRuleDao.add(paramHisCommodityRule);
  }
  
  public List getObject(long paramLong)
  {
    return this.hisCommodityRuleDao.getObject(paramLong);
  }
}
