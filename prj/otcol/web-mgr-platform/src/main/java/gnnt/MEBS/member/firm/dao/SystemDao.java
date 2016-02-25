package gnnt.MEBS.member.firm.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.member.firm.unit.TradeModule;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SystemDao
  extends DaoHelperImpl
{
  private final transient Log logger = LogFactory.getLog(SystemDao.class);
  
  public List getTradeModuleList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from m_TradeModule where Enabled='Y' and moduleid<>'1' order by to_number(ModuleID)";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List getTradeModuleList1(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from m_TradeModule where moduleid<>'1' order by to_number(ModuleID)";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List getSystemList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from M_TradeModule  ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    str = str + " order by to_number(ModuleID)";
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public void updateSystem(TradeModule paramTradeModule)
  {
    String str = "update M_TradeModule set name = ? ,Enabled=?,hostip=?,rmi_port=?,mutex=?,issettle=? where moduleID=?";
    Object[] arrayOfObject = { paramTradeModule.getName(), paramTradeModule.getEnabled(), paramTradeModule.getHostip(), Long.valueOf(paramTradeModule.getRmi_Port()), paramTradeModule.getMutex(), paramTradeModule.getIssettle(), paramTradeModule.getModuleId() };
    int[] arrayOfInt = { 12, 12, 12, 8, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public TradeModule getSystemById(String paramString)
  {
    TradeModule localTradeModule = new TradeModule();
    String str = "select * from M_TradeModule where moduleID = ?";
    Object[] arrayOfObject = { paramString };
    localTradeModule = (TradeModule)queryForObject(str, arrayOfObject, new CommonRowMapper(new TradeModule()));
    return localTradeModule;
  }
  
  public List getBankList()
  {
    String str = "select * from F_B_banks  where validFlag=0 ";
    return queryBySQL(str);
  }
}
