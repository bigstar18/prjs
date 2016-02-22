package gnnt.MEBS.bankadded.dao;

import gnnt.MEBS.bankadded.model.BankFunds;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("bankFundsDao")
public class BankFundsDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(BankFundsDao.class);
  
  public Class getEntityClass()
  {
    return new BankFunds().getClass();
  }
  
  public List<BankFunds> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new BankFunds(primary.bankCode, banks.bankName, primary.marketFeeBalance, primary.lastMarketFeeBalance, primary.marketFeeNew,primary.marketdelayFeeNew) from BankFunds as primary,Banks as banks where primary.bankCode = banks.bankId ";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    this.logger.debug("NoticeDao    hql:" + hql);
    List<BankFunds> list = queryByHQL(hql, names, values, pageInfo, null);
    
    return list;
  }
}
