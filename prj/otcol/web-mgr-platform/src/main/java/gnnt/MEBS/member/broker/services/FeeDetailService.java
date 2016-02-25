package gnnt.MEBS.member.broker.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.finance.firmFunds.ClientLedger;
import gnnt.MEBS.member.broker.dao.FeeDetailDao;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("m_feeDetailService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class FeeDetailService
{
  @Autowired
  @Qualifier("m_feeDetailDao")
  private FeeDetailDao feeDetailDao;
  
  public List brokerUserFeeList(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    return this.feeDetailDao.brokerUserFeeList(paramQueryConditions, paramPageInfo, paramString);
  }
  
  public List hisDealDetailList(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    return this.feeDetailDao.hisDealDetailList(paramQueryConditions, paramInt1, paramInt2);
  }
  
  public int hisDealDetailListCount(QueryConditions paramQueryConditions)
  {
    return this.feeDetailDao.hisDealDetailListCount(paramQueryConditions);
  }
  
  public List hisDealDetailZcjsList(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    return this.feeDetailDao.hisDealDetailZcjsList(paramQueryConditions, paramInt1, paramInt2);
  }
  
  public int hisDealDetailZcjsListCount(QueryConditions paramQueryConditions)
  {
    return this.feeDetailDao.hisDealDetailZcjsListCount(paramQueryConditions);
  }
  
  public List hisDealDetailVdList(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    return this.feeDetailDao.hisDealDetailVdList(paramQueryConditions, paramInt1, paramInt2);
  }
  
  public int hisDealDetailVdListCount(QueryConditions paramQueryConditions)
  {
    return this.feeDetailDao.hisDealDetailVdListCount(paramQueryConditions);
  }
  
  public Map clientLedgerAll(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString1, String paramString2)
  {
    String str = "";
    if (paramQueryConditions != null)
    {
      paramQueryConditions.addCondition("firmId", "in", "(select firmId from m_b_firmandbroker where brokerid='" + paramString2 + "')");
    }
    else
    {
      paramQueryConditions = new QueryConditions();
      paramQueryConditions.addCondition("firmId", "in", "(select firmId from m_b_firmandbroker where brokerid='" + paramString2 + "')");
    }
    Map localMap = ClientLedger.queryClientLedgerTotal(paramQueryConditions, paramPageInfo, paramString1, str);
    return localMap;
  }
  
  public static Map clientLedgerSelf(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    Map localMap = ClientLedger.queryClientLedger(paramQueryConditions, paramPageInfo, paramString);
    return localMap;
  }
}
