package gnnt.MEBS.delivery.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.dao.EnterApplyDao;
import gnnt.MEBS.delivery.model.workflow.EnterApply;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_enterApplyService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class EnterApplyService
{
  private final transient Log logger = LogFactory.getLog(EnterApplyService.class);
  @Autowired
  @Qualifier("w_enterApplyDao")
  private EnterApplyDao enterApplyDao;
  
  public void addEnterApply(EnterApply paramEnterApply)
  {
    String str = this.enterApplyDao.getEnterApplyId();
    paramEnterApply.setId(str);
    this.enterApplyDao.addEnterApply(paramEnterApply);
  }
  
  public void updateEnterApply(EnterApply paramEnterApply)
  {
    this.enterApplyDao.updateEnterApply(paramEnterApply);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getEnterApplyList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.enterApplyDao.getEnterApplyList(paramQueryConditions, paramPageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public EnterApply getEnterApplyById(String paramString)
  {
    EnterApply localEnterApply = null;
    QueryConditions localQueryConditions = new QueryConditions("id", "=", paramString);
    List localList = this.enterApplyDao.getEnterApplys(localQueryConditions);
    if (localList.size() > 0) {
      localEnterApply = (EnterApply)localList.get(0);
    }
    return localEnterApply;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<EnterApply> getEnterApplys(QueryConditions paramQueryConditions)
  {
    return this.enterApplyDao.getEnterApplys(paramQueryConditions);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public EnterApply getEnterApplyLock(String paramString)
  {
    return this.enterApplyDao.getEnterApplyLock(paramString);
  }
}
