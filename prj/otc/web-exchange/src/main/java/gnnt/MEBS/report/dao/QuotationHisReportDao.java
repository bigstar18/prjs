package gnnt.MEBS.report.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.report.model.QuotationHisReport;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("quotationHisReportDao")
public class QuotationHisReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new QuotationHisReport().getClass();
  }
  
  public List getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new QuotationHisReport(primary.clearDate, primary.commodityId,commodity.name,primary.lowPrice,primary.highPrice,primary.yesterBalancePrice,primary.price) from QuotationHisReport as primary,gnnt.MEBS.commodity.model.Commodity as commodity where primary.commodityId=commodity.id ";
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
