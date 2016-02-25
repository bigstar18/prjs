package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.DiscussPrice;
import java.util.Date;
import java.util.List;

public class DiscussPriceDao
  extends DaoHelperImpl
{
  public void add(DiscussPrice paramDiscussPrice)
  {
    String str = "insert into Z_discussPrice (discussPriceId,tradeCommodityMsgId,goodsOrderId,followFirmId,followTraderId,discussPriceFirmId,discussPrice,quantity,businessDirection,tradeBail,tradePoundage,type,status,isRegStock,regStockId,discussPriceDate,ModifyDate,note) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramDiscussPrice.getDiscussPriceId()), Long.valueOf(paramDiscussPrice.getTradeCommodityMsgId()), Long.valueOf(paramDiscussPrice.getGoodsOrderId()), paramDiscussPrice.getFollowFirmId(), paramDiscussPrice.getFollowTraderId(), paramDiscussPrice.getDiscussPriceFirmId(), Double.valueOf(paramDiscussPrice.getDiscussPrice()), Double.valueOf(paramDiscussPrice.getQuantity()), paramDiscussPrice.getBusinessDirection(), Double.valueOf(paramDiscussPrice.getTradeBail()), Double.valueOf(paramDiscussPrice.getTradePoundage()), Integer.valueOf(paramDiscussPrice.getType()), Integer.valueOf(paramDiscussPrice.getStatus()), paramDiscussPrice.getIsRegStock(), paramDiscussPrice.getRegStockId(), paramDiscussPrice.getDiscussPriceDate(), paramDiscussPrice.getModifyDate(), paramDiscussPrice.getNote() };
    int[] arrayOfInt = { 2, 2, 2, 12, 12, 12, 2, 2, 12, 2, 2, 2, 2, 12, 12, 93, 93, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void delete(long paramLong)
  {
    String str = "delete from Z_DiscussPrice where DiscussPriceId='" + paramLong + "'";
    updateBySQL(str);
  }
  
  public List<DiscussPrice> getObjectList(QueryConditions paramQueryConditions)
  {
    String str = "select * from Z_DiscussPrice where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new DiscussPrice()));
  }
  
  public DiscussPrice getObject(long paramLong)
  {
    String str = "select * from Z_DiscussPrice where DiscussPriceId='" + paramLong + "'";
    DiscussPrice localDiscussPrice = new DiscussPrice();
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new DiscussPrice()));
    if ((localList != null) && (localList.size() > 0)) {
      localDiscussPrice = (DiscussPrice)localList.get(0);
    }
    return localDiscussPrice;
  }
  
  public void update(DiscussPrice paramDiscussPrice)
  {
    String str = "update Z_discussPrice set tradeCommodityMsgId=?,goodsOrderId=?,followFirmId=?,followTraderId=?,discussPriceFirmId=?,discussPrice=?,quantity=?,businessDirection=?,tradeBail=?,tradePoundage=?,type=?,status=?,isRegStock=?,regStockId=?,discussPriceDate=?,ModifyDate=?,note=?  where discussPriceId=? ";
    Object[] arrayOfObject = { Long.valueOf(paramDiscussPrice.getTradeCommodityMsgId()), Long.valueOf(paramDiscussPrice.getGoodsOrderId()), paramDiscussPrice.getFollowFirmId(), paramDiscussPrice.getFollowTraderId(), paramDiscussPrice.getDiscussPriceFirmId(), Double.valueOf(paramDiscussPrice.getDiscussPrice()), Double.valueOf(paramDiscussPrice.getQuantity()), paramDiscussPrice.getBusinessDirection(), Double.valueOf(paramDiscussPrice.getTradeBail()), Double.valueOf(paramDiscussPrice.getTradePoundage()), Integer.valueOf(paramDiscussPrice.getType()), Integer.valueOf(paramDiscussPrice.getStatus()), paramDiscussPrice.getIsRegStock(), paramDiscussPrice.getRegStockId(), paramDiscussPrice.getDiscussPriceDate(), paramDiscussPrice.getModifyDate(), paramDiscussPrice.getNote(), Long.valueOf(paramDiscussPrice.getDiscussPriceId()) };
    int[] arrayOfInt = { 2, 2, 12, 12, 12, 2, 2, 12, 2, 2, 2, 2, 12, 12, 93, 93, 12, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void updateParameter(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, Date paramDate)
  {
    String str = "update Z_discussPrice set goodsOrderId=?,status=?,discussPriceDate=?,ModifyDate=? where goodsOrderId=? and type=? and status=? ";
    Object[] arrayOfObject = { Long.valueOf(paramLong2), Integer.valueOf(paramInt3), paramDate, paramDate, Long.valueOf(paramLong1), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) };
    int[] arrayOfInt = { 2, 2, 93, 93, 2, 2, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public long getId()
  {
    long l = 0L;
    String str = "select SEQ_Z_discussPrice.nextVal from dual";
    l = queryForInt(str, null);
    return l;
  }
}
