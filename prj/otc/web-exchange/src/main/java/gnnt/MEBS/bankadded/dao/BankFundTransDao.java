package gnnt.MEBS.bankadded.dao;

import gnnt.MEBS.bankadded.model.BankFundTrans;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("bankFundTransDao")
public class BankFundTransDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(BankFundTransDao.class);
  
  public Class getEntityClass()
  {
    return new BankFundTrans().getClass();
  }
  
  public List<BankFundTrans> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new BankFundTrans(primary.transID, primary.bankCode, banks.bankName, primary.transType, primary.amount, primary.createTime) from BankFundTrans as primary,Banks as banks where primary.bankCode = banks.bankId ";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    this.logger.debug("NoticeDao    hql:" + hql);
    List<BankFundTrans> list = queryByHQL(hql, names, values, pageInfo, null);
    
    return list;
  }
}
