package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.workflow.OutWare;
import java.util.List;
import org.apache.commons.logging.Log;

public class OutWareDao
  extends DaoHelperImpl
{
  public List getOutWareList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from (select t.*, c.name commodityName, h.name warehouseName,s.name statusName from w_out_ware t, w_commodity c, w_warehouse h,w_status s where t.commodityid = c.id and t.warehouseid = h.id and t.ability = s.value and s.kind = 'OutWare')";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public void addOutWare(OutWare paramOutWare)
  {
    String str = "insert into w_out_ware (id,ability,firmId,warehouseId,commodityId,createDate,weight,quantity,outDate,planOutDate,responsibleman,agency,dealerAgency,remark,tel,enterWareId,rejectedReasons,xml) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramOutWare.getId(), Integer.valueOf(paramOutWare.getAbility()), paramOutWare.getFirmId(), paramOutWare.getWarehouseId(), paramOutWare.getCommodityId(), paramOutWare.getCreateDate(), Double.valueOf(paramOutWare.getWeight()), Long.valueOf(paramOutWare.getQuantity()), paramOutWare.getOutDate(), paramOutWare.getPlanOutDate(), paramOutWare.getResponsibleman(), paramOutWare.getAgency(), paramOutWare.getDealerAgency(), paramOutWare.getRemark(), paramOutWare.getTel(), paramOutWare.getEnterWareId(), paramOutWare.getRejectedReasons(), paramOutWare.getXml() };
    int[] arrayOfInt = { 12, 4, 12, 12, 12, 93, 2, 2, 91, 91, 12, 12, 12, 12, 12, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void updateOutWare(OutWare paramOutWare)
  {
    String str = "update w_out_ware set ability=?,firmId=?,warehouseId=?,commodityId=?,createDate=?,weight=?,quantity=?,outDate=?,planOutDate=?,responsibleman=?,agency=?,dealerAgency=?,remark=?,tel=?,enterWareId=?,rejectedReasons=?,xml=? where id=?";
    Object[] arrayOfObject = { Integer.valueOf(paramOutWare.getAbility()), paramOutWare.getFirmId(), paramOutWare.getWarehouseId(), paramOutWare.getCommodityId(), paramOutWare.getCreateDate(), Double.valueOf(paramOutWare.getWeight()), Long.valueOf(paramOutWare.getQuantity()), paramOutWare.getOutDate(), paramOutWare.getPlanOutDate(), paramOutWare.getResponsibleman(), paramOutWare.getAgency(), paramOutWare.getDealerAgency(), paramOutWare.getRemark(), paramOutWare.getTel(), paramOutWare.getEnterWareId(), paramOutWare.getRejectedReasons(), paramOutWare.getXml(), paramOutWare.getId() };
    int[] arrayOfInt = { 4, 12, 12, 12, 93, 2, 2, 91, 91, 12, 12, 12, 12, 12, 12, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public OutWare getOutWareLock(String paramString)
  {
    OutWare localOutWare = new OutWare();
    String str = "select * from w_out_ware where id='" + paramString + "' for update";
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new OutWare()));
    if ((localList != null) && (localList.size() > 0)) {
      localOutWare = (OutWare)localList.get(0);
    }
    return localOutWare;
  }
  
  public String getOutWareId()
  {
    String str1 = "";
    String str2 = "select SEQ_w_outWare.nextVal from dual";
    str1 = queryForInt(str2, null) + "";
    return str1;
  }
  
  public List getOutWares(QueryConditions paramQueryConditions)
  {
    String str = "select * from w_out_ware where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new OutWare()));
  }
}
