package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.TradeMsgFeedback;
import java.util.List;

public class TradeMsgFeedbackDao
  extends DaoHelperImpl
{
  public void add(TradeMsgFeedback paramTradeMsgFeedback)
  {
    String str = "insert into z_trademsgfeedback (tradeMsgFeedbackId,goodsOrderNo,tradeCommodityMsgId,firmId,discussPriceId,price,quantity,businessDirection,type,occurTime,isRead,BehaviourFirmId,traderId) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramTradeMsgFeedback.getTradeMsgFeedbackId()), Long.valueOf(paramTradeMsgFeedback.getGoodsOrderNo()), Long.valueOf(paramTradeMsgFeedback.getTradeCommodityMsgId()), paramTradeMsgFeedback.getFirmId(), Long.valueOf(paramTradeMsgFeedback.getDiscussPriceId()), Double.valueOf(paramTradeMsgFeedback.getPrice()), Double.valueOf(paramTradeMsgFeedback.getQuantity()), paramTradeMsgFeedback.getBusinessDirection(), Integer.valueOf(paramTradeMsgFeedback.getType()), paramTradeMsgFeedback.getOccurTime(), paramTradeMsgFeedback.getIsRead(), paramTradeMsgFeedback.getBehaviourFirmId(), paramTradeMsgFeedback.getTraderId() };
    int[] arrayOfInt = { 2, 2, 2, 12, 2, 2, 2, 12, 2, 93, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public long getId()
  {
    long l = 0L;
    String str = "select SEQ_Z_TRADEMSGFEEDBACK.nextVal from dual";
    l = queryForInt(str, null);
    return l;
  }
  
  public void allDelete()
  {
    String str = "delete from z_trademsgfeedback";
    updateBySQL(str);
  }
  
  public List<TradeMsgFeedback> getObject(QueryConditions paramQueryConditions)
  {
    String str = "select * from Z_tradeMsgFeedback where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new TradeMsgFeedback()));
  }
}
