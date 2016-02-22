package gnnt.MEBS.query.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.query.model.BankInternalTransSearch;
import gnnt.MEBS.query.model.SettlementBankFundSearch;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("bankInternalTransSearchDao")
public class BankInternalTransSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(BankInternalTransSearchDao.class);
  
  public Class getEntityClass()
  {
    return new BankInternalTransSearch().getClass();
  }
  
  public List<SettlementBankFundSearch> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new BankInternalTransSearch(primary.transId,primary.firmId,firm.firmName,primary.bankCode,primary.bankCode_target,primary.amount,bank.bankName,firm.firmType,bankTarget.bankName)from BankInternalTransSearch as primary,Banks as bank,Banks as bankTarget,Firm as firm where primary.firmId = firm.firmId and primary.bankCode =bank.bankId and primary.bankCode_target=bankTarget.bankId ";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null) && (!"".equals(conditions.getFieldsSqlClause())))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    return queryByHQL(hql, names, values, pageInfo, null);
  }
}
