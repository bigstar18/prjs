package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.workflow.EnterWare;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class EnterWareDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(EnterWareDao.class);
  
  public List getEnterWareList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from (select t.*,c.name commodityName,c.countType countType,h.name wareHouseName,s.name statusName from w_enter_ware t,w_commodity c, w_warehouse h,w_status s where t.commodityid = c.id(+) and t.warehouseid = h.id(+) and t.ability = s.value and s.kind = 'EnterWare')";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public String getEnterWareId()
  {
    String str1 = "";
    String str2 = "select SEQ_w_EnterWare.nextVal from dual";
    str1 = queryForInt(str2, null) + "";
    return str1;
  }
  
  public void addEnterWare(EnterWare paramEnterWare)
  {
    String str = "insert into w_enter_ware (id,ability,enterInformId,firmId,warehouseId,commodityId,createDate,weight,quantity,unitWeight,lot,cargoNo,qualityInspection,origin,grade,sort,qualityStandard,productionDate,packaging,rejectedReasons,xml,frozenAmount,changeFirmId,responsibleman,agency,dealerAgency,remark,enterDate,existAmount,oldEnterWareId) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramEnterWare.getId(), Integer.valueOf(paramEnterWare.getAbility()), paramEnterWare.getEnterInformId(), paramEnterWare.getFirmId(), paramEnterWare.getWarehouseId(), paramEnterWare.getCommodityId(), paramEnterWare.getCreateDate(), Double.valueOf(paramEnterWare.getWeight()), Long.valueOf(paramEnterWare.getQuantity()), Double.valueOf(paramEnterWare.getUnitWeight()), paramEnterWare.getLot(), paramEnterWare.getCargoNo(), Integer.valueOf(paramEnterWare.getQualityInspection()), paramEnterWare.getOrigin(), paramEnterWare.getGrade(), paramEnterWare.getSort(), paramEnterWare.getQualityStandard(), paramEnterWare.getProductionDate(), paramEnterWare.getPackaging(), paramEnterWare.getRejectedReasons(), paramEnterWare.getXml(), Double.valueOf(paramEnterWare.getFrozenAmount()), paramEnterWare.getChangeFirmId(), paramEnterWare.getResponsibleman(), paramEnterWare.getAgency(), paramEnterWare.getDealerAgency(), paramEnterWare.getRemark(), paramEnterWare.getEnterDate(), Double.valueOf(paramEnterWare.getExistAmount()), paramEnterWare.getOldEnterWareId() };
    int[] arrayOfInt = { 12, 4, 12, 12, 12, 12, 93, 2, 2, 2, 12, 12, 4, 12, 12, 12, 12, 12, 12, 12, 12, 2, 12, 12, 12, 12, 12, 91, 2, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public List getEnterWares(QueryConditions paramQueryConditions)
  {
    String str = "select * from w_enter_ware where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new EnterWare()));
  }
  
  public void updateEnterWare(EnterWare paramEnterWare)
  {
    String str = "update w_enter_ware set ability=?,enterInformId=?,firmId=?,warehouseId=?,commodityId=?,createDate=?,weight=?,quantity=?,unitWeight=?,lot=?,cargoNo=?,qualityInspection=?,origin=?,grade=?,sort=?,qualityStandard=?,productionDate=?,packaging=?,rejectedReasons=?,xml=?,frozenAmount=?,changeFirmId=?,responsibleman=?,agency=?,dealerAgency=?,remark=?,enterDate=?,existAmount=?,oldEnterWareId=? where id=?";
    Object[] arrayOfObject = { Integer.valueOf(paramEnterWare.getAbility()), paramEnterWare.getEnterInformId(), paramEnterWare.getFirmId(), paramEnterWare.getWarehouseId(), paramEnterWare.getCommodityId(), paramEnterWare.getCreateDate(), Double.valueOf(paramEnterWare.getWeight()), Long.valueOf(paramEnterWare.getQuantity()), Double.valueOf(paramEnterWare.getUnitWeight()), paramEnterWare.getLot(), paramEnterWare.getCargoNo(), Integer.valueOf(paramEnterWare.getQualityInspection()), paramEnterWare.getOrigin(), paramEnterWare.getGrade(), paramEnterWare.getSort(), paramEnterWare.getQualityStandard(), paramEnterWare.getProductionDate(), paramEnterWare.getPackaging(), paramEnterWare.getRejectedReasons(), paramEnterWare.getXml(), Double.valueOf(paramEnterWare.getFrozenAmount()), paramEnterWare.getChangeFirmId(), paramEnterWare.getResponsibleman(), paramEnterWare.getAgency(), paramEnterWare.getDealerAgency(), paramEnterWare.getRemark(), paramEnterWare.getEnterDate(), Double.valueOf(paramEnterWare.getExistAmount()), paramEnterWare.getOldEnterWareId(), paramEnterWare.getId() };
    int[] arrayOfInt = { 4, 12, 12, 12, 12, 93, 2, 2, 2, 12, 12, 4, 12, 12, 12, 12, 12, 12, 12, 12, 2, 12, 12, 12, 12, 12, 91, 2, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public EnterWare getEnterWareLock(String paramString)
  {
    EnterWare localEnterWare = new EnterWare();
    String str = "select * from w_Enter_Ware where id='" + paramString + "' for update";
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new EnterWare()));
    if ((localList != null) && (localList.size() > 0)) {
      localEnterWare = (EnterWare)localList.get(0);
    }
    return localEnterWare;
  }
  
  public List getEnterWareListById(String paramString)
  {
    Object[] arrayOfObject = { paramString };
    String str = "select * from w_Enter_Ware where Id=?";
    List localList = getJdbcTemplate().query(str, arrayOfObject, new CommonRowMapper(new EnterWare()));
    this.logger.debug("getEnterWareListById.size()" + localList.size());
    return localList;
  }
}
