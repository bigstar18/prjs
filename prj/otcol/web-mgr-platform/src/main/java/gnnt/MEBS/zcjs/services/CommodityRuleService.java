package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.CommodityRuleDao;
import gnnt.MEBS.zcjs.model.Breed;
import gnnt.MEBS.zcjs.model.CommodityRule;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_commodityRuleService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CommodityRuleService
{
  @Autowired
  @Qualifier("z_commodityRuleDao")
  private CommodityRuleDao commodityRuleDao;
  @Autowired
  @Qualifier("z_breedService")
  private BreedService breedService;
  
  public void add(CommodityRule paramCommodityRule)
  {
    long l = this.commodityRuleDao.getId();
    paramCommodityRule.setCommodityRuleId(l);
    this.commodityRuleDao.add(paramCommodityRule);
  }
  
  public void delete(long paramLong)
  {
    this.commodityRuleDao.delete(paramLong);
  }
  
  public void deleteSome(String[] paramArrayOfString)
  {
    for (int i = 0; i < paramArrayOfString.length; i++) {
      delete(Long.parseLong(paramArrayOfString[i]));
    }
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<CommodityRule> getList(QueryConditions paramQueryConditions)
  {
    return this.commodityRuleDao.getObjectList(paramQueryConditions);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<Map<String, Object>> getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.commodityRuleDao.getTableList(paramQueryConditions, paramPageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public CommodityRule getObject(long paramLong)
  {
    return this.commodityRuleDao.getObject(paramLong);
  }
  
  public void update(CommodityRule paramCommodityRule)
  {
    this.commodityRuleDao.update(paramCommodityRule);
  }
  
  public CommodityRule getOptimalCommodityRule(String paramString1, long paramLong, String paramString2)
  {
    CommodityRule localCommodityRule = null;
    String str = "select * from Z_commodityRule where (BreedId='-1' or BreedId=" + paramLong + ") " + "and (CommodityRuleFirmId='-1' or CommodityRuleFirmId='" + paramString1 + "') " + "and (CommodityRuleBusinessDirection='N' or CommodityRuleBusinessDirection='" + paramString2 + "') " + "and CommodityRuleStatus=1 " + "order by CommodityRuleFirmId desc,BreedId desc,CommodityRuleBusinessDirection desc";
    List localList = this.commodityRuleDao.queryBySQL(str, null, null, new CommonRowMapper(new CommodityRule()));
    if (localList.size() > 0)
    {
      localCommodityRule = (CommodityRule)localList.get(0);
      double d = 1.0D;
      if (this.breedService.getBreedById(paramLong) != null) {
        d = this.breedService.getBreedById(paramLong).getUnitVolume();
      }
      localCommodityRule.setBail(localCommodityRule.getBail() * d);
      localCommodityRule.setTradePoundage(localCommodityRule.getTradePoundage() * d);
    }
    return localCommodityRule;
  }
  
  public long getId()
  {
    return this.commodityRuleDao.getId();
  }
}
