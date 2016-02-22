package gnnt.MEBS.query.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.query.model.SettlementBankFundSearch;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("settlementBankFundSearchDao")
public class SettlementBankFundSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(SettlementBankFundSearchDao.class);
  
  public Class getEntityClass()
  {
    return new SettlementBankFundSearch().getClass();
  }
  
  public List<SettlementBankFundSearch> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new SettlementBankFundSearch(primary.b_date,primary.firmId,firm.firmName,bank.bankName,primary.bankCode,primary.fundio,primary.closepl,primary.holdpl,primary.tradefee,primary.delayfee,primary.capital,primary.lastcapital,firm.firmType)from SettlementBankFundSearch as primary,Banks as bank,Firm as firm where primary.firmId = firm.firmId and primary.bankCode =bank.bankId ";
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
