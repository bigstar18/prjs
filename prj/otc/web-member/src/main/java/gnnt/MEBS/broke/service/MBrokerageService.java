package gnnt.MEBS.broke.service;

import gnnt.MEBS.broke.dao.MBrokerageDao;
import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.broke.model.MBrokerage;
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

@Service("mBrokerageService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MBrokerageService
  extends BaseService<Brokerage>
{
  private final transient Log logger = LogFactory.getLog(MBrokerageService.class);
  @Autowired
  @Qualifier("mBrokerageDao")
  private MBrokerageDao mBrokerageDao;
  
  public BaseDao getDao()
  {
    return this.mBrokerageDao;
  }
  
  public MBrokerage change(Brokerage brokerage)
  {
    MBrokerage mBrokerage = new MBrokerage();
    if (brokerage != null)
    {
      mBrokerage.setAddress(brokerage.getAddress());
      mBrokerage.setBrokerageNo(brokerage.getBrokerageNo());
      mBrokerage.setEmail(brokerage.getEmail());
      mBrokerage.setMemberNo(brokerage.getMemberNo());
      mBrokerage.setMobile(brokerage.getMobile());
      mBrokerage.setName(brokerage.getName());
      mBrokerage.setNote(brokerage.getNote());
      mBrokerage.setPassword(brokerage.getPassword());
      mBrokerage.setTelephone(brokerage.getTelephone());
    }
    return mBrokerage;
  }
  
  public Brokerage queryByNo(String no, String memberNo)
  {
    this.logger.debug("enter queryByNo.....");
    return this.mBrokerageDao.queryByNo(no, memberNo);
  }
  
  public void updateBroker(String memberNo, List<MBrokerage> list2)
  {
    this.logger.debug("updateBroker....");
    deleteBroker(memberNo);
    if (list2.size() > 0) {
      addMBroker(list2);
    }
  }
  
  public void addMBroker(List<MBrokerage> list)
  {
    this.logger.debug("addMBroker....");
    this.mBrokerageDao.addMBroker(list);
  }
  
  public void deleteBroker(String memberNo)
  {
    this.logger.debug("deleteBroker....");
    List list = queryMBroker(memberNo);
    this.mBrokerageDao.deleteBroker(list);
  }
  
  public List<MBrokerage> queryMBroker(String memberNo)
  {
    this.logger.debug("queryMBroker....");
    return this.mBrokerageDao.queryMBroker(memberNo);
  }
  
  public List<Brokerage> queryBroker(String memberNo)
  {
    this.logger.debug("queryMBroker....");
    return this.mBrokerageDao.queryBroker(memberNo);
  }
}
