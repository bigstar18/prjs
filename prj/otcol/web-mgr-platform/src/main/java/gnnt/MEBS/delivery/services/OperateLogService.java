package gnnt.MEBS.delivery.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.dao.OperateLogDao;
import gnnt.MEBS.delivery.model.OperateLog;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_operateLogService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class OperateLogService
{
  @Autowired
  @Qualifier("w_operateLogDao")
  private OperateLogDao operateLogDao;
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getOprLogList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.operateLogDao.getOprLogList(paramQueryConditions, paramPageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
  public void addOprLog(OperateLog paramOperateLog)
  {
    this.operateLogDao.addOprLog(paramOperateLog);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public OperateLog getOprLogById(String paramString)
  {
    return this.operateLogDao.getOprLogById(paramString);
  }
  
  public void addLoginLog(String paramString1, String paramString2, String paramString3)
  {
    this.operateLogDao.addLoginLog(paramString1, paramString2, paramString3);
  }
}
