package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.GoodsOrder;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GoodsOrderDao
  extends DaoHelperImpl
{
  private final transient Log logger = LogFactory.getLog(GoodsOrderDao.class);
  
  public void add(GoodsOrder paramGoodsOrder)
  {
    String str = "insert into Z_goodsOrder (goodsOrderId,tradeCommodityMsgId,goodsOrderNo,firmId,traderId,price,quantity,partBargainQuantity,businessDirection,status,isRegStock,regStockId,tradePoundage,tradeBail,goodsOrderDate,modifyDate,oldGoodsOrderId,isDelist) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramGoodsOrder.getGoodsOrderId()), Long.valueOf(paramGoodsOrder.getTradeCommodityMsgId()), Long.valueOf(paramGoodsOrder.getGoodsOrderNo()), paramGoodsOrder.getFirmId(), paramGoodsOrder.getTraderId(), Double.valueOf(paramGoodsOrder.getPrice()), Double.valueOf(paramGoodsOrder.getQuantity()), Double.valueOf(paramGoodsOrder.getPartBargainQuantity()), paramGoodsOrder.getBusinessDirection(), Integer.valueOf(paramGoodsOrder.getStatus()), paramGoodsOrder.getIsRegStock(), paramGoodsOrder.getRegStockId(), Double.valueOf(paramGoodsOrder.getTradePoundage()), Double.valueOf(paramGoodsOrder.getTradeBail()), paramGoodsOrder.getGoodsOrderDate(), paramGoodsOrder.getModifyDate(), Long.valueOf(paramGoodsOrder.getOldGoodsOrderId()), paramGoodsOrder.getIsDelist() };
    int[] arrayOfInt = { 2, 2, 2, 12, 12, 2, 2, 2, 12, 2, 12, 12, 2, 2, 93, 93, 2, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void delete(long paramLong)
  {
    String str = "delete from Z_GoodsOrder where GoodsOrderId='" + paramLong + "'";
    updateBySQL(str);
  }
  
  public List<GoodsOrder> getObjectList(QueryConditions paramQueryConditions)
  {
    String str = "select * from Z_GoodsOrder where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new GoodsOrder()));
  }
  
  public GoodsOrder getObject(long paramLong)
  {
    String str = "select * from Z_GoodsOrder where GoodsOrderId='" + paramLong + "'";
    GoodsOrder localGoodsOrder = new GoodsOrder();
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new GoodsOrder()));
    if ((localList != null) && (localList.size() > 0)) {
      localGoodsOrder = (GoodsOrder)localList.get(0);
    }
    return localGoodsOrder;
  }
  
  public void update(GoodsOrder paramGoodsOrder)
  {
    String str = "update Z_goodsOrder set tradeCommodityMsgId=?,firmId=?,goodsOrderNo=?,traderId=?,price=?,quantity=?,partBargainQuantity=?,businessDirection=?,status=?,isRegStock=?,regStockId=?,tradePoundage=?,tradeBail=?,goodsOrderDate=?,modifyDate=?,oldGoodsOrderId=?,isDelist=?  where goodsOrderId=?  ";
    Object[] arrayOfObject = { Long.valueOf(paramGoodsOrder.getTradeCommodityMsgId()), paramGoodsOrder.getFirmId(), Long.valueOf(paramGoodsOrder.getGoodsOrderNo()), paramGoodsOrder.getTraderId(), Double.valueOf(paramGoodsOrder.getPrice()), Double.valueOf(paramGoodsOrder.getQuantity()), Double.valueOf(paramGoodsOrder.getPartBargainQuantity()), paramGoodsOrder.getBusinessDirection(), Integer.valueOf(paramGoodsOrder.getStatus()), paramGoodsOrder.getIsRegStock(), paramGoodsOrder.getRegStockId(), Double.valueOf(paramGoodsOrder.getTradePoundage()), Double.valueOf(paramGoodsOrder.getTradeBail()), paramGoodsOrder.getGoodsOrderDate(), paramGoodsOrder.getModifyDate(), Long.valueOf(paramGoodsOrder.getOldGoodsOrderId()), paramGoodsOrder.getIsDelist(), Long.valueOf(paramGoodsOrder.getGoodsOrderId()) };
    int[] arrayOfInt = { 2, 12, 2, 12, 2, 2, 2, 12, 2, 12, 12, 2, 2, 93, 93, 2, 12, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void updateStatus(GoodsOrder paramGoodsOrder)
  {
    String str = "update Z_goodsOrder set status=?,ModifyDate=? where goodsOrderId=?  ";
    Object[] arrayOfObject = { Integer.valueOf(paramGoodsOrder.getStatus()), paramGoodsOrder.getModifyDate(), Long.valueOf(paramGoodsOrder.getGoodsOrderId()) };
    int[] arrayOfInt = { 2, 93, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public long getId()
  {
    long l = 0L;
    String str = "select SEQ_Z_goodsOrder.nextVal from dual";
    l = queryForInt(str, null);
    return l;
  }
  
  public List<GoodsOrder> getListAssociateCommodity(QueryConditions paramQueryConditions)
  {
    String str = "select g.* from Z_goodsOrder g, Z_TradeCommodityMsg t where g.tradeCommodityMsgId = t.tradeCommodityMsgId";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new GoodsOrder()));
    this.logger.debug("sql:" + str);
    return localList;
  }
}
