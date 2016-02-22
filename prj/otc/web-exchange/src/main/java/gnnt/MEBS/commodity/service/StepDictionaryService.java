package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.StepDictionaryDao;
import gnnt.MEBS.commodity.dao.StepDictionaryProDao;
import gnnt.MEBS.commodity.model.StepDictionary;
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

@Service("stepDictionaryService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class StepDictionaryService
  extends BaseService<StepDictionary>
{
  private final transient Log logger = LogFactory.getLog(StepDictionaryService.class);
  @Autowired
  @Qualifier("stepDictionaryDao")
  private StepDictionaryDao stepDictionaryDao;
  @Autowired
  @Qualifier("stepDictionaryProDao")
  private StepDictionaryProDao stepDictionaryProDao;
  
  public BaseDao getDao()
  {
    return this.stepDictionaryDao;
  }
  
  public List<StepDictionary> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    if (conditions == null)
    {
      conditions = new QueryConditions();
      conditions.addCondition(
        "primary.ladderCode in (select ladderCode from StepDictionary where ladderCode in(%composite%))", 
        "composite", 
        "'DelayDays'");
    }
    else
    {
      conditions.addCondition(
        "primary.ladderCode in (select ladderCode from StepDictionary where ladderCode in(%composite%))", 
        "composite", 
        "'DelayDays'");
    }
    return getDao().getList(conditions, pageInfo);
  }
  
  public List<StepDictionary> getFundsList(QueryConditions conditions, PageInfo pageInfo)
  {
    if (conditions == null)
    {
      conditions = new QueryConditions();
      conditions.addCondition("primary.ladderCode in (select ladderCode from StepDictionary where ladderCode in(%composite%))", 
        "composite", 
        "'MemberFunds'");
    }
    else
    {
      conditions.addCondition("primary.ladderCode in (select ladderCode from StepDictionary where ladderCode in(%composite%))", 
        "composite", 
        "'MemberFunds'");
    }
    return getDao().getList(conditions, pageInfo);
  }
}
