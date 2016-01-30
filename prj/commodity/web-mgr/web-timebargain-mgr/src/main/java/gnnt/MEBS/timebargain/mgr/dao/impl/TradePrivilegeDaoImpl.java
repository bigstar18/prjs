package gnnt.MEBS.timebargain.mgr.dao.impl;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.timebargain.mgr.dao.TradePrivilegeDao;
import gnnt.MEBS.timebargain.mgr.model.firmSet.TradePrivilege;
import gnnt.MEBS.timebargain.mgr.util.SqlUtil;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Repository;

@Repository("tradePrivilegeDao")
public class TradePrivilegeDaoImpl extends StandardDao
  implements TradePrivilegeDao
{
  public List getFirmPrivilege(String firmID)
  {
    String sql = "select t.*, case when t.kind = 1 then (select breedname from t_a_breed  where to_char(breedid) = t.kindid) end breedName,case when t.kind = 2 then (select name from t_commodity  where commodityid = t.kindid) end commodityName from t_a_tradeprivilege t  where t.type = 1 and t.typeid = '" + 
      firmID + "'";

    return queryBySql(sql);
  }

  public List getCustomerPrivilege(String customerID)
  {
    String sql = "select t.*, case when t.kind = 1 then (select breedname from t_a_breed  where to_char(breedid) = t.kindid) end breedName,case when t.kind = 2 then (select name from t_commodity  where commodityid = t.kindid) end commodityName from t_a_tradeprivilege t  where t.type = 2 and t.typeid = '" + 
      customerID + "'";

    return queryBySql(sql);
  }

  public List getTraderPrivilege(String traderID)
  {
    String sql = "select t.*, b.breedName breedName from T_A_TRADEPRIVILEGE t, t_a_breed b where t.kindID = b.breedID and t.Type = 3 and t.Kind = 1 and t.TypeID = '" + 
      traderID + "'";

    return queryBySql(sql);
  }

  public List getBreed()
  {
    String sql = "select breedID,breedName from T_A_BREED order by breedID";

    return queryBySql(sql);
  }

  public List getCommodity()
  {
    String sql = "select commodityID,name from T_commodity order by commodityID";

    return queryBySql(sql);
  }

  public String getOperateCode(String traderID)
  {
    String sql = "select t.OperateCode OperateCode from T_TRADER t where t.traderID = '" + traderID + "'";

    List list = queryBySql(sql);

    String operateCode = "";
    if ((list != null) && (list.size() > 0)) {
      Map map = (Map)list.get(0);
      if (map.get("OPERATECODE") != null) {
        operateCode = map.get("OPERATECODE").toString();
      }
    }

    return operateCode;
  }

  public List getCodeNotChoose(String firmID, String traderID)
  {
    String operateCode = getOperateCode(traderID);

    StringBuffer sb = new StringBuffer();

    if ((operateCode != null) && (!"".equals(operateCode))) {
      String[] ops = operateCode.split(",");
      for (int i = 0; i < ops.length; i++) {
        if (i != ops.length - 1)
          sb.append("'").append(ops[i]).append("',");
        else {
          sb.append("'").append(ops[i]).append("'");
        }
      }
    }

    String relOperateCode = sb.toString();
    StringBuffer relSb = new StringBuffer();
    relSb.append("select c.code code from M_TRADER t,T_CUSTOMER c where t.firmID=c.firmID");

    if ((relOperateCode != null) && (!"".equals(relOperateCode))) {
      relSb.append(" and c.code not in(" + relOperateCode + ")");
    }
    if ((firmID != null) && (!"".equals(firmID))) {
      relSb.append(" and t.firmID = '" + firmID + "'");
    }

    if ((traderID != null) && (!"".equals(traderID))) {
      relSb.append(" and t.traderID = '" + traderID + "'");
    }

    return queryBySql(relSb.toString());
  }

  public void deleteTradePrivilege(TradePrivilege tradePrivilege)
    throws Exception
  {
    String sql = "delete from t_a_tradeprivilege  where type = " + 
      tradePrivilege.getType() + 
      " and typeid = '" + tradePrivilege.getTypeID() + "'" + 
      " and kind = " + tradePrivilege.getKind() + 
      " and kindid = '" + tradePrivilege.getKindID() + "'";
    executeUpdateBySql(sql);
  }

  public void batchSetInsertFirmPrivilege(int type, String typeIdString, int kind, String kindIdString, int pb, int ps)
  {
    String sql = makeSql_tradeprivilege_insert(type, typeIdString, kind, kindIdString, pb, ps);
    System.out.println("sql:  " + sql);
    this.logger.debug("sql: " + sql);
    try
    {
      executeUpdateBySql(sql);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("添加失败！");
    }
  }

  public void batchEmptyDeleteFirmPrivilege(int type, String typeIdString, int kind, String kindIdString)
  {
    String sql = makeSql_tradeprivilege_delete(type, typeIdString, kind, kindIdString);

    this.logger.debug("sql: " + sql);
    try
    {
      executeUpdateBySql(sql);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("清空失败！");
    }
  }

  private static String makeSql_tradeprivilege_delete(int type, String typeIdString, int kind, String kindIdString)
  {
    String sqlTypeIdFilter = "";
    if (type == 1) {
      String sqlBrokerIdFilter = SqlUtil.makeFilterCondition("brokerid", typeIdString, false);
      sqlTypeIdFilter = "typeid in ( select firmid from m_b_firmandbroker where " + sqlBrokerIdFilter + ") ";
    } else if (type == 2) {
      sqlTypeIdFilter = SqlUtil.makeFilterCondition("typeid", typeIdString, false);
    }

    String sqlKindIdFilter = SqlUtil.makeFilterCondition("kindid", kindIdString, false);
    String sql = "delete from t_a_tradeprivilege where type=1 and " + sqlTypeIdFilter + " and kind=" + kind + " and " + sqlKindIdFilter;
    return sql;
  }

  private static String makeSql_tradeprivilege_insert(int type, String typeIdString, int kind, String kindIdString, int pb, int ps)
  {
    String sqlFirmIdFilter = "";
    if (type == 1) {
      String sqlBrokerIdFilter = SqlUtil.makeFilterCondition("brokerid", typeIdString, false);
      sqlFirmIdFilter = "firmid in ( select firmid from m_b_firmandbroker where " + sqlBrokerIdFilter + ") ";
    } else if (type == 2) {
      sqlFirmIdFilter = SqlUtil.makeFilterCondition("firmid", typeIdString, false);
    }

    String insertTable = "insert into t_a_tradeprivilege (id, type, typeid, kind, kindid, privilegecode_b, privilegecode_s) ";
    String selectTable = "";
    String sqlKindIdFilter = "";
    if (kind == 1) {
      selectTable = "select seq_t_a_tradeprivilege.nextval,1,f.firmid,1,b.BREEDid," + pb + "," + ps + " from m_firm f,T_A_BREED b";
      sqlKindIdFilter = SqlUtil.makeFilterCondition("BREEDid", kindIdString, true);
    } else if (kind == 2) {
      selectTable = "select seq_t_a_tradeprivilege.nextval,1,f.firmid,2,c.commodityid," + pb + "," + ps + " from m_firm f,t_commodity c";
      sqlKindIdFilter = SqlUtil.makeFilterCondition("commodityid", kindIdString, false);
    }

    String sql = insertTable + selectTable + " where " + sqlFirmIdFilter + " and " + sqlKindIdFilter;
    return sql;
  }
}