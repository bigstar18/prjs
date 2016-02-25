package gnnt.MEBS.member.broker.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.finance.base.dao.DaoHelper;
import gnnt.MEBS.finance.base.util.SysData;
import gnnt.MEBS.member.broker.model.BrokerLog;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class BrokerLogDao
  extends DaoHelperImpl
{
  public void insertBroker(BrokerLog paramBrokerLog)
  {
    String str = "insert into M_B_brokerLog  (logid, createtime, brokerid, OperatorId, type, action) values(SEQ_M_BROKERLOG.Nextval, sysdate, ?, ?,?, ?)";
    Object[] arrayOfObject = { paramBrokerLog.getBrokerId(), paramBrokerLog.getOperatorId(), Integer.valueOf(paramBrokerLog.getType()), paramBrokerLog.getAction() };
    int[] arrayOfInt = { 12, 12, 4, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public List<BrokerLog> brokerLogList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    Object localObject = null;
    String str = "select * from M_B_brokerLog where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    return localDaoHelper.queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public BrokerLog brokerLogView(String paramString)
  {
    String str = "select * from M_B_brokerLog where logid=?";
    Object[] arrayOfObject = { paramString };
    List localList = getJdbcTemplate().query(str, arrayOfObject, new CommonRowMapper(new BrokerLog()));
    BrokerLog localBrokerLog = null;
    if (localList.size() > 0) {
      localBrokerLog = (BrokerLog)localList.get(0);
    }
    return localBrokerLog;
  }
}
