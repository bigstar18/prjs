package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.StepDictionaryDao;
import gnnt.MEBS.commodity.dao.TCDelayFeeDao;
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

@Service("tcDelayFeeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TCDelayFeeService
  extends BaseService<TCDelayFeeVO>
{
  private final transient Log logger = LogFactory.getLog(TCDelayFeeService.class);
  @Autowired
  @Qualifier("tcDelayFeeDao")
  private TCDelayFeeDao tcDelayFeeDao;
  @Autowired
  @Qualifier("stepDictionaryDao")
  private StepDictionaryDao stepDictionaryDao;
  
  public StepDictionaryDao getStepDictionaryDao()
  {
    return this.stepDictionaryDao;
  }
  
  public TCDelayFeeDao getTcDelayFeeDao()
  {
    return this.tcDelayFeeDao;
  }
  
  public List<TCDelayFeeVO> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    List<TCDelayFeeVO> voList = new ArrayList();
    List<Object[]> list = this.tcDelayFeeDao.getTCDelayList(conditions, pageInfo);
    if ((list != null) && (list.size() > 0)) {
      for (Object[] delayFee : list)
      {
        TCDelayFeeVO delayFeeVO = new TCDelayFeeVO();
        delayFeeVO.setCommodityId((String)delayFee[0]);
        delayFeeVO.setFirmId((String)delayFee[1]);
        delayFeeVO.setCommodityName((String)delayFee[2]);
        delayFeeVO.setFeeAlgr_v(delayFee[3]);
        QueryConditions qc = new QueryConditions();
        qc.addCondition("primary.commodityId", "=", (String)delayFee[0]);
        qc.addCondition("primary.firmId", "=", (String)delayFee[1]);
        PageInfo pInfo = new PageInfo(1, 10000, "stepNo", false);
        List<TCDelayFee> childTCDelayFee = this.tcDelayFeeDao.getList(qc, pInfo);
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
      this.tcDelayFeeDao.delete(tcDelayFee);
      this.tcDelayFeeDao.add(tcDelayFee);
    }
    num = 3;
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
}
