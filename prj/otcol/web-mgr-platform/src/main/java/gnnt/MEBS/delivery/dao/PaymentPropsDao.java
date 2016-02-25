package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.PaymentProps;
import java.util.List;
import org.apache.commons.logging.Log;

public class PaymentPropsDao
  extends DaoHelperImpl
{
  public List getPaymentPropsList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from (select s.*, c.name breedName from s_PaymentProps s,w_commodity c where s.breedID||'' = c.id)";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public PaymentProps getPaymentPropsByCondition(String paramString1, String paramString2, String paramString3)
  {
    PaymentProps localPaymentProps = null;
    String str = "select * from ( select s.* from s_paymentprops s where s.moduleid = ? and s.breedid = ? and s.settledayno = ?)";
    Object[] arrayOfObject = { paramString1, paramString2, paramString3 };
    this.logger.debug("sql: " + str);
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new PaymentProps()));
    if (localList.size() > 0) {
      localPaymentProps = (PaymentProps)localList.get(0);
    }
    return localPaymentProps;
  }
  
  public void insertPaymentProps(PaymentProps paramPaymentProps)
  {
    String str = "insert into S_PaymentProps (ModuleID,BreedID,SettleDayNo,BuyPayoutPct,SellIncomePct) values (?,?,?,?,?)";
    Object[] arrayOfObject = { paramPaymentProps.getModuleID(), Long.valueOf(paramPaymentProps.getBreedID()), Integer.valueOf(paramPaymentProps.getSettleDayNo()), Double.valueOf(paramPaymentProps.getBuyPayoutPct()), Double.valueOf(paramPaymentProps.getSellIncomePct()) };
    int[] arrayOfInt = { 12, 12, 2, 2, 2 };
    this.logger.debug("sql: " + str);
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void updatePaymentProps(PaymentProps paramPaymentProps)
  {
    String str = "update S_PaymentProps set BuyPayoutPct=?,SellIncomePct=? where ModuleID=? and BreedID=? and SettleDayNo=?";
    Object[] arrayOfObject = { Double.valueOf(paramPaymentProps.getBuyPayoutPct()), Double.valueOf(paramPaymentProps.getSellIncomePct()), paramPaymentProps.getModuleID(), Long.valueOf(paramPaymentProps.getBreedID()), Integer.valueOf(paramPaymentProps.getSettleDayNo()) };
    int[] arrayOfInt = { 2, 2, 12, 12, 12 };
    this.logger.debug("sql: " + str);
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void deletePaymentProps(PaymentProps paramPaymentProps)
  {
    String str = "delete from S_PaymentProps where ModuleID=? and BreedID=? and SettleDayNo=?";
    Object[] arrayOfObject = { paramPaymentProps.getModuleID(), Long.valueOf(paramPaymentProps.getBreedID()), Integer.valueOf(paramPaymentProps.getSettleDayNo()) };
    int[] arrayOfInt = { 12, 2, 2 };
    this.logger.debug("sql: " + str);
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}
