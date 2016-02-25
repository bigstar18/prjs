package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.workflow.EnterApply;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EnterApplyDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(EnterApplyDao.class);
  
  public void addEnterApply(EnterApply paramEnterApply)
  {
    String str = "insert into w_Enter_Apply (id,ability,firmId,warehouseId,commodityId,createDate,enterDate,weight,quantity,unitWeight,lot,qualityInspection,origin,grade,sort,productionDate,packaging,remark,rejectedReasons,xml,informAbility) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramEnterApply.getId(), Integer.valueOf(paramEnterApply.getAbility()), paramEnterApply.getFirmId(), paramEnterApply.getWarehouseId(), paramEnterApply.getCommodityId(), paramEnterApply.getCreateDate(), paramEnterApply.getEnterDate(), Double.valueOf(paramEnterApply.getWeight()), Long.valueOf(paramEnterApply.getQuantity()), Double.valueOf(paramEnterApply.getUnitWeight()), paramEnterApply.getLot(), Integer.valueOf(paramEnterApply.getQualityInspection()), paramEnterApply.getOrigin(), paramEnterApply.getGrade(), paramEnterApply.getSort(), paramEnterApply.getProductionDate(), paramEnterApply.getPackaging(), paramEnterApply.getRemark(), paramEnterApply.getRejectedReasons(), paramEnterApply.getXml(), Integer.valueOf(paramEnterApply.getInformAbility()) };
    int[] arrayOfInt = { 12, 4, 12, 12, 12, 93, 91, 8, 2, 8, 12, 4, 12, 12, 12, 12, 12, 12, 12, 12, 4 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void updateEnterApply(EnterApply paramEnterApply)
  {
    String str = "update w_enter_apply set ability=?,firmId=?,warehouseId=?,commodityId=?,createDate=?,enterDate=?,weight=?,quantity=?,unitWeight=?,lot=?,qualityInspection=?,origin=?,grade=?,sort=?,productionDate=?,packaging=?,remark=?,rejectedReasons=?,xml=?,informAbility=? where id=? ";
    Object[] arrayOfObject = { Integer.valueOf(paramEnterApply.getAbility()), paramEnterApply.getFirmId(), paramEnterApply.getWarehouseId(), paramEnterApply.getCommodityId(), paramEnterApply.getCreateDate(), paramEnterApply.getEnterDate(), Double.valueOf(paramEnterApply.getWeight()), Long.valueOf(paramEnterApply.getQuantity()), Double.valueOf(paramEnterApply.getUnitWeight()), paramEnterApply.getLot(), Integer.valueOf(paramEnterApply.getQualityInspection()), paramEnterApply.getOrigin(), paramEnterApply.getGrade(), paramEnterApply.getSort(), paramEnterApply.getProductionDate(), paramEnterApply.getPackaging(), paramEnterApply.getRemark(), paramEnterApply.getRejectedReasons(), paramEnterApply.getXml(), Integer.valueOf(paramEnterApply.getInformAbility()), paramEnterApply.getId() };
    int[] arrayOfInt = { 4, 12, 12, 12, 93, 91, 8, 2, 8, 12, 4, 12, 12, 12, 12, 12, 12, 12, 12, 4, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public List getEnterApplyList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from (select e.*,c.name commodityName,m.name firmName,w.name warehouseName,c.countType,s.name status,ws.name informStatus from w_Enter_Apply e,m_firm m,w_commodity c,w_warehouse w,w_status s,w_status ws where ws.kind='EnterInform' and ws.value=e.informAbility and e.firmId=m.firmId and e.commodityId=c.id(+) and e.warehouseId=w.id(+) and s.kind='EnterApply' and s.value=e.ability)";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("---------sql:-------- " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List getEnterApplys(QueryConditions paramQueryConditions)
  {
    String str = "select * from w_Enter_Apply where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new EnterApply()));
  }
  
  public String getEnterApplyId()
  {
    String str1 = "";
    String str2 = "select SEQ_W_ENTERAPPLY.nextVal from dual";
    str1 = queryForInt(str2, null) + "";
    return str1;
  }
  
  public EnterApply getEnterApplyLock(String paramString)
  {
    EnterApply localEnterApply = new EnterApply();
    String str = "select * from w_Enter_Apply where id='" + paramString + "' for update";
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new EnterApply()));
    if ((localList != null) && (localList.size() > 0)) {
      localEnterApply = (EnterApply)localList.get(0);
    }
    return localEnterApply;
  }
}
