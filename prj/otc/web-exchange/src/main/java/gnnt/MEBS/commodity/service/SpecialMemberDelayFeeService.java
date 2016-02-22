package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.SpecialMemberDelayFeeDao;
import gnnt.MEBS.commodity.dao.StepDictionaryDao;
import gnnt.MEBS.commodity.model.TCDelayFee;
import gnnt.MEBS.commodity.model.vo.TCDelayFeeVO;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("specialMemberDelayFeeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SpecialMemberDelayFeeService
  extends BaseService<TCDelayFeeVO>
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberDelayFeeService.class);
  @Autowired
  @Qualifier("specialMemberDelayFeeDao")
  private SpecialMemberDelayFeeDao specialMemberDelayFeeDao;
  @Autowired
  @Qualifier("stepDictionaryDao")
  private StepDictionaryDao stepDictionaryDao;
  
  public SpecialMemberDelayFeeDao getSpecialMemberDelayFeeDao()
  {
    return this.specialMemberDelayFeeDao;
  }
  
  public StepDictionaryDao getStepDictionaryDao()
  {
    return this.stepDictionaryDao;
  }
  
  public List<TCDelayFeeVO> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    List<TCDelayFeeVO> voList = new ArrayList();
    List<Object[]> list = this.specialMemberDelayFeeDao.getTCDelayList(conditions, pageInfo);
    if ((list != null) && (list.size() > 0)) {
      for (Object[] delayFee : list)
      {
        TCDelayFeeVO delayFeeVO = new TCDelayFeeVO();
        delayFeeVO.setCommodityId((String)delayFee[0]);
        delayFeeVO.setFirmId((String)delayFee[1]);
        delayFeeVO.setCommodityName((String)delayFee[2]);
        delayFeeVO.setFirmName((String)delayFee[3]);
        delayFeeVO.setFeeAlgr_v(delayFee[4]);
        QueryConditions qc = new QueryConditions();
        qc.addCondition("primary.commodityId", "=", (String)delayFee[0]);
        qc.addCondition("primary.firmId", "=", (String)delayFee[1]);
        PageInfo pInfo = new PageInfo(1, 10000, "stepNo", false);
        List<TCDelayFee> childTCDelayFee = this.specialMemberDelayFeeDao.getList(qc, pInfo);
        delayFeeVO.setTcDelayFeeList(childTCDelayFee);
        voList.add(delayFeeVO);
      }
    }
    return voList;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public TCDelayFeeVO get(TCDelayFeeVO clone)
  {
    this.logger.debug("enter get");
    QueryConditions qc = new QueryConditions();
    qc.addCondition("firmId", "=", clone.getFirmId());
    qc.addCondition("primary.commodityId", "=", clone.getCommodityId());
    PageInfo pageInfo = new PageInfo(1, 10000, "primary.commodityId", false);
    List<TCDelayFeeVO> list = getList(qc, pageInfo);
    TCDelayFeeVO vo = null;
    if ((list != null) && (list.size() > 0)) {
      vo = (TCDelayFeeVO)list.get(0);
    } else {
      vo = null;
    }
    return vo;
  }
  
  public int update(TCDelayFeeVO clone)
  {
    int num = 0;
    List<TCDelayFee> list = clone.getTcDelayFeeList();
    for (TCDelayFee tcDelayFee : list)
    {
      this.specialMemberDelayFeeDao.delete(tcDelayFee);
      this.specialMemberDelayFeeDao.add(tcDelayFee);
    }
    num = 5;
    return num;
  }
  
  public int dictionaryTotal()
  {
    return this.stepDictionaryDao.getDelayTotal();
  }
  
  public BaseDao getDao()
  {
    return null;
  }
  
  public int delete(TCDelayFee delayFee)
  {
    QueryConditions conditions = new QueryConditions();
    conditions.addCondition("primary.firmId", "=", delayFee.getFirmId());
    conditions.addCondition("primary.commodityId", "=", delayFee.getCommodityId());
    List<TCDelayFee> list = new ArrayList();
    list = this.specialMemberDelayFeeDao.getList(conditions, null);
    for (TCDelayFee tcDelayFee : list) {
      this.specialMemberDelayFeeDao.delete(tcDelayFee);
    }
    return 4;
  }
  
  public int audit(TCDelayFeeVO clone)
  {
    int num = 0;
    
    List<TCDelayFee> list = clone.getTcDelayFeeList();
    if (list != null) {
      for (TCDelayFee tc : list) {
        this.specialMemberDelayFeeDao.delete(tc);
      }
    }
    if (!"D".equals(clone.getOperate())) {
      update(clone);
    }
    num = 3;
    return num;
  }
}
