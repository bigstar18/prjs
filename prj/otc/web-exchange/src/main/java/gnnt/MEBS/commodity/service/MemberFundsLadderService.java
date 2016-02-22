package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.MemberFundsLadderDao;
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

@Service("memberFundsLadderService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MemberFundsLadderService
  extends BaseService<FundsLadder>
{
  private final transient Log logger = LogFactory.getLog(MemberFundsLadderService.class);
  @Autowired
  @Qualifier("memberFundsLadderDao")
  private MemberFundsLadderDao memberFundsLadderDao;
  @Autowired
  @Qualifier("stepDictionaryDao")
  private StepDictionaryDao stepDictionaryDao;
  
  public BaseDao getDao()
  {
    return this.memberFundsLadderDao;
  }
  
  public List getFundsLadderList(QueryConditions conditions, PageInfo pageInfo)
  {
    List<Object[]> firmidList = this.memberFundsLadderDao.getFundsLadderList(conditions, pageInfo);
    List resultList = new ArrayList();
    for (Object[] object : firmidList)
    {
      Map dataMap = new HashMap();
      QueryConditions qc = new QueryConditions();
      qc.addCondition("primary.memberNo", "=", object[0]);
      List<FundsLadder> list = new ArrayList();
      pageInfo = new PageInfo(1, 10000, "primary.stepNo", false);
      list = this.memberFundsLadderDao.getList(qc, pageInfo);
      dataMap.put("memberNo", object[0]);
      dataMap.put("memberName", object[1]);
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
    this.logger.debug("enter update");
    if ((list != null) && (list.size() > 0)) {
      for (FundsLadder fundsLadder : list)
      {
        this.logger.debug("fundsLadder.memberNo:" + fundsLadder.getMemberNo());
        this.logger.debug("fundsLadder.stepNo:" + fundsLadder.getStepNo());
        this.logger.debug("fundsLadder.getStepRate():" + fundsLadder.getStepRate());
        this.memberFundsLadderDao.delete(fundsLadder);
        this.memberFundsLadderDao.add(fundsLadder);
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
    list = this.memberFundsLadderDao.getList(conditions, null);
    for (FundsLadder fundsLadder : list) {
      this.memberFundsLadderDao.delete(fundsLadder);
    }
    return 4;
  }
  
  public int dictionaryTotal()
  {
    return this.stepDictionaryDao.getTotal();
  }
}
