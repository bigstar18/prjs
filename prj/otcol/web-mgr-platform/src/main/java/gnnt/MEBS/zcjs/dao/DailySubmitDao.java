package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.Submit;
import java.util.List;

public class DailySubmitDao
  extends DaoHelperImpl
{
  public void add(Submit paramSubmit)
  {
    String str = "insert into Z_Submit (submitId,traderId,firmId,tradeCommodityMsgId,submitQuantity,submitPrice,businessDirection,isRegstock,regStockId,tradeBail,tradePoundage,discussPriceId,dailySubmitDate) values(?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
    Object[] arrayOfObject = { Long.valueOf(paramSubmit.getSubmitId()), paramSubmit.getTraderId(), paramSubmit.getFirmId(), Long.valueOf(paramSubmit.getTradeCommodityMsgId()), Double.valueOf(paramSubmit.getSubmitQuantity()), Double.valueOf(paramSubmit.getSubmitPrice()), paramSubmit.getBusinessDirection(), paramSubmit.getIsRegstock(), paramSubmit.getRegStockId(), Double.valueOf(paramSubmit.getTradeBail()), Double.valueOf(paramSubmit.getTradePoundage()), Long.valueOf(paramSubmit.getDiscussPriceId()) };
    int[] arrayOfInt = { 2, 12, 12, 2, 2, 2, 12, 12, 12, 2, 2, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void delete(long paramLong)
  {
    String str = "delete from Z_Submit where SubmitId=" + paramLong + "";
    updateBySQL(str);
  }
  
  public List<Submit> getObjectList(QueryConditions paramQueryConditions)
  {
    String str = "select * from Z_Submit where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new Submit()));
  }
  
  public Submit getObject(long paramLong)
  {
    String str = "select * from Z_Submit where SubmitId=" + paramLong + "";
    Submit localSubmit = new Submit();
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new Submit()));
    if ((localList != null) && (localList.size() > 0)) {
      localSubmit = (Submit)localList.get(0);
    }
    return localSubmit;
  }
  
  public void update(Submit paramSubmit)
  {
    String str = "update Z_Submit set traderId=?,firmId=?,tradeCommodityMsgId=?,submitQuantity=?,submitPrice=?,businessDirection=?,isRegstock=?,regStockId=?,tradeBail=?,tradePoundage=?,discussPriceId=?,status=? where submitId=? ";
    Object[] arrayOfObject = { paramSubmit.getTraderId(), paramSubmit.getFirmId(), Long.valueOf(paramSubmit.getTradeCommodityMsgId()), Double.valueOf(paramSubmit.getSubmitQuantity()), Double.valueOf(paramSubmit.getSubmitPrice()), paramSubmit.getBusinessDirection(), paramSubmit.getIsRegstock(), paramSubmit.getRegStockId(), Double.valueOf(paramSubmit.getTradeBail()), Double.valueOf(paramSubmit.getTradePoundage()), Long.valueOf(paramSubmit.getDiscussPriceId()), Integer.valueOf(paramSubmit.getStatus()), Long.valueOf(paramSubmit.getSubmitId()) };
    int[] arrayOfInt = { 12, 12, 2, 2, 2, 12, 12, 12, 2, 2, 2, 2, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public long getId()
  {
    long l = 0L;
    String str = "select SEQ_Z_Submit.nextVal from dual";
    l = queryForInt(str, null);
    return l;
  }
  
  public Submit getObjectLock(long paramLong)
  {
    String str = "select * from Z_Submit where SubmitId=" + paramLong + " for update";
    Submit localSubmit = new Submit();
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new Submit()));
    if ((localList != null) && (localList.size() > 0)) {
      localSubmit = (Submit)localList.get(0);
    }
    return localSubmit;
  }
  
  public int selectSubmit()
  {
    String str = "select count(*) from Z_Submit where status<>2";
    int i = queryForInt(str);
    return i;
  }
}
