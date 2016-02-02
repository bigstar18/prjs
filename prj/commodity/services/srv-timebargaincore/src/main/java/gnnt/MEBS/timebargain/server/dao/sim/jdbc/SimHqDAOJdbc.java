package gnnt.MEBS.timebargain.server.dao.sim.jdbc;

import gnnt.MEBS.timebargain.server.dao.jdbc.BaseDAOJdbc;
import gnnt.MEBS.timebargain.server.dao.sim.SimHqDAO;
import gnnt.MEBS.timebargain.server.model.Quotation;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class SimHqDAOJdbc
  extends BaseDAOJdbc
  implements SimHqDAO
{
  public List getQuotationList()
  {
    return getQuotationList(null);
  }
  
  public List getQuotationList(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer("select * from T_Quotation where 1=1 ");
    Object[] arrayOfObject = null;
    ArrayList localArrayList = new ArrayList();
    if (paramString != null)
    {
      localStringBuffer.append(" and CommodityID=?");
      localArrayList.add(paramString);
    }
    arrayOfObject = localArrayList.toArray();
    this.log.debug("sql:" + localStringBuffer.toString());
    List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    return dbList2QuotationList(localList);
  }
  
  public List getCommodityList()
  {
    return getCommodityList(null);
  }
  
  public List getCommodityList(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer("select * from T_Commodity where 1=1 ");
    Object[] arrayOfObject = null;
    ArrayList localArrayList = new ArrayList();
    if (paramString != null)
    {
      localStringBuffer.append(" and CommodityID=?");
      localArrayList.add(paramString);
    }
    arrayOfObject = localArrayList.toArray();
    this.log.debug("sql:" + localStringBuffer.toString());
    List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    return dbList2QuotationList(localList);
  }
  
  private List dbList2QuotationList(List paramList)
  {
    if (paramList == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramList.size(); i++)
    {
      Quotation localQuotation = new Quotation();
      Map localMap = (Map)paramList.get(i);
      localQuotation.setCommodityID((String)localMap.get("CommodityID"));
      localQuotation.setYesterBalancePrice(Double.valueOf(((BigDecimal)localMap.get("YesterBalancePrice")).doubleValue()));
      localQuotation.setClosePrice(Double.valueOf(((BigDecimal)localMap.get("ClosePrice")).doubleValue()));
      localQuotation.setOpenPrice(Double.valueOf(((BigDecimal)localMap.get("OpenPrice")).doubleValue()));
      localQuotation.setHighPrice(Double.valueOf(((BigDecimal)localMap.get("HighPrice")).doubleValue()));
      localQuotation.setLowPrice(Double.valueOf(((BigDecimal)localMap.get("LowPrice")).doubleValue()));
      localQuotation.setCurPrice(Double.valueOf(((BigDecimal)localMap.get("CurPrice")).doubleValue()));
      localQuotation.setCurAmount(Long.valueOf(((BigDecimal)localMap.get("CurAmount")).longValue()));
      localQuotation.setOpenAmount(Long.valueOf(((BigDecimal)localMap.get("OpenAmount")).longValue()));
      localQuotation.setBuyOpenAmount(Long.valueOf(((BigDecimal)localMap.get("BuyOpenAmount")).longValue()));
      localQuotation.setSellOpenAmount(Long.valueOf(((BigDecimal)localMap.get("SellOpenAmount")).longValue()));
      localQuotation.setCloseAmount(Long.valueOf(((BigDecimal)localMap.get("CloseAmount")).longValue()));
      localQuotation.setBuyCloseAmount(Long.valueOf(((BigDecimal)localMap.get("BuyCloseAmount")).longValue()));
      localQuotation.setSellCloseAmount(Long.valueOf(((BigDecimal)localMap.get("SellCloseAmount")).longValue()));
      localQuotation.setReserveCount(Long.valueOf(((BigDecimal)localMap.get("ReserveCount")).longValue()));
      localQuotation.setReserveChange(Long.valueOf(((BigDecimal)localMap.get("ReserveChange")).longValue()));
      localQuotation.setPrice(Double.valueOf(((BigDecimal)localMap.get("Price")).doubleValue()));
      localQuotation.setTotalMoney(Double.valueOf(((BigDecimal)localMap.get("TotalMoney")).doubleValue()));
      localQuotation.setTotalAmount(Long.valueOf(((BigDecimal)localMap.get("TotalAmount")).longValue()));
      localQuotation.setSpread(Double.valueOf(((BigDecimal)localMap.get("Spread")).doubleValue()));
      localQuotation.buy[0] = ((BigDecimal)localMap.get("BuyPrice1")).doubleValue();
      localQuotation.sell[0] = ((BigDecimal)localMap.get("SellPrice1")).doubleValue();
      localQuotation.buyqty[0] = ((BigDecimal)localMap.get("BuyAmount1")).longValue();
      localQuotation.sellqty[0] = ((BigDecimal)localMap.get("SellAmount1")).longValue();
      localQuotation.buy[1] = ((BigDecimal)localMap.get("BuyPrice2")).doubleValue();
      localQuotation.sell[1] = ((BigDecimal)localMap.get("SellPrice2")).doubleValue();
      localQuotation.buyqty[1] = ((BigDecimal)localMap.get("BuyAmount2")).longValue();
      localQuotation.sellqty[1] = ((BigDecimal)localMap.get("SellAmount2")).longValue();
      localQuotation.buy[2] = ((BigDecimal)localMap.get("BuyPrice3")).doubleValue();
      localQuotation.sell[2] = ((BigDecimal)localMap.get("SellPrice3")).doubleValue();
      localQuotation.buyqty[2] = ((BigDecimal)localMap.get("BuyAmount3")).longValue();
      localQuotation.sellqty[2] = ((BigDecimal)localMap.get("SellAmount3")).longValue();
      localQuotation.buy[3] = ((BigDecimal)localMap.get("BuyPrice4")).doubleValue();
      localQuotation.sell[3] = ((BigDecimal)localMap.get("SellPrice4")).doubleValue();
      localQuotation.buyqty[3] = ((BigDecimal)localMap.get("BuyAmount4")).longValue();
      localQuotation.sellqty[3] = ((BigDecimal)localMap.get("SellAmount4")).longValue();
      localQuotation.buy[4] = ((BigDecimal)localMap.get("BuyPrice5")).doubleValue();
      localQuotation.sell[4] = ((BigDecimal)localMap.get("SellPrice5")).doubleValue();
      localQuotation.buyqty[4] = ((BigDecimal)localMap.get("BuyAmount5")).longValue();
      localQuotation.sellqty[4] = ((BigDecimal)localMap.get("SellAmount5")).longValue();
      localQuotation.setOutAmount(Long.valueOf(((BigDecimal)localMap.get("OutAmount")).longValue()));
      localQuotation.setInAmount(Long.valueOf(((BigDecimal)localMap.get("InAmount")).longValue()));
      localQuotation.setTradeCue(Long.valueOf(((BigDecimal)localMap.get("TradeCue")).longValue()));
      localQuotation.setNo(Long.valueOf(((BigDecimal)localMap.get("NO")).longValue()));
      localQuotation.updateTime = ((Timestamp)localMap.get("CreateTime"));
      localArrayList.add(localQuotation);
    }
    return localArrayList;
  }
  
  public Date getCurDbDate()
  {
    String str = "select sysdate from dual";
    this.log.debug("sql: " + str);
    return (Date)getJdbcTemplate().queryForObject(str, Date.class);
  }
}
