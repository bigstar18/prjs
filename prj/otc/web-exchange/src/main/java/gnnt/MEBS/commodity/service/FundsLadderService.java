package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.FundsLadderDao;
import gnnt.MEBS.commodity.dao.StepDictionaryDao;
import gnnt.MEBS.commodity.model.FundsLadder;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("fundsLadderService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class FundsLadderService
  extends BaseService<FundsLadder>
{
  private final transient Log logger = LogFactory.getLog(FundsLadderService.class);
  @Autowired
  @Qualifier("fundsLadderDao")
  private FundsLadderDao fundsLadderDao;
  @Autowired
  @Qualifier("stepDictionaryDao")
  private StepDictionaryDao stepDictionaryDao;
  
  public BaseDao getDao()
  {
    return this.fundsLadderDao;
  }
  
  public List getFundsLadderList(QueryConditions conditions, PageInfo pageInfo)
  {
    List firmidList = this.fundsLadderDao.getFundsLadderList(conditions, pageInfo);
    List resultList = new ArrayList();
    if (conditions == null) {
      conditions = new QueryConditions();
    }
    for (Object object : firmidList)
    {
      Map dataMap = new HashMap();
      conditions.addCondition("primary.memberNo", "=", object);
      List<FundsLadder> list = new ArrayList();
      pageInfo = new PageInfo(1, 10000, "primary.stepNo", false);
      list = this.fundsLadderDao.getList(conditions, pageInfo);
      dataMap.put("memberNo", object);
      for (FundsLadder fundsLadder : list) {
        dataMap.put("stepNo" + fundsLadder.getStepNo(), fundsLadder.getStepRate_v());
      }
      resultList.add(dataMap);
    }
    return resultList;
  }
  
  public int update(List<FundsLadder> list)
  {
    int num = 0;
    if ((list != null) && (list.size() > 0)) {
      for (FundsLadder fundsLadder : list)
      {
        this.fundsLadderDao.delete(fundsLadder);
        this.fundsLadderDao.add(fundsLadder);
      }
    }
    num = 3;
    return num;
  }
  
  public int delete(FundsLadder funds)
  {
    QueryConditions conditions = new QueryConditions();
    conditions.addCondition("primary.memberNo", "=", funds.getMemberNo());
    List<FundsLadder> list = new ArrayList();
    list = this.fundsLadderDao.getList(conditions, null);
    for (FundsLadder fundsLadder : list) {
      this.fundsLadderDao.delete(fundsLadder);
    }
    return 4;
  }
  
  public int dictionaryTotal()
  {
    return this.stepDictionaryDao.getTotal();
  }
}
