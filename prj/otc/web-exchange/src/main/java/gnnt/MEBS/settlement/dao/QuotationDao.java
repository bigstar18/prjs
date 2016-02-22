package gnnt.MEBS.settlement.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.settlement.model.Quotation;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("quotationDao")
public class QuotationDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(QuotationDao.class);
  
  public Class getEntityClass()
  {
    return new Quotation().getClass();
  }
  
  public List<Quotation> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new Quotation(primary.commodityId,commodity.minPriceMove,commodity.name,commodity.lastPrice,quotepoint.curPrice_B,quotepoint.curPrice_S,primary.yesterBalancePrice,primary.closePrice,primary.openPrice,primary.highPrice,primary.lowPrice,primary.curPrice,primary.curAmount,primary.openAmount,primary.buyOpenAmount,primary.sellOpenAmount,primary.closeAmount,primary.buyCloseAmount,primary.sellCloseAmount,primary.reserveCount,primary.reserveChange,primary.price,primary.totalMoney,primary.totalAmount,primary.spread,primary.createTime)from Quotation as primary ,Commodity as commodity, QuotationRunTime as quotepoint where primary.commodityId=commodity.id and quotepoint.commodityId = commodity.id and commodity.status = 1";
    



























    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    List<Quotation> list = queryByHQL(hql, names, values, pageInfo, null);
    return list;
  }
}
