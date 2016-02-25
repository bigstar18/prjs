package gnnt.MEBS.member.broker.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.member.broker.model.Broker;
import gnnt.MEBS.member.broker.model.Firm;
import java.io.PrintStream;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class BrokerDao
  extends DaoHelperImpl
{
  public List<Broker> getBrokerList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from M_B_broker b where 1=1";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new Broker()));
  }
  
  public List<Broker> getBrokerLists(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select distinct b.* from m_b_broker b,m_b_firmandbroker fb where b.brokerid = fb.brokerid ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new Broker()));
  }
  
  public List<Broker> getfirmList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from m_B_firmandbroker a,m_firm b where a.firmid=b.firmid ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public boolean checkFirmandbroke(String paramString)
  {
    String str = "select count(*) from m_B_firmandbroker where firmid=?";
    boolean bool = false;
    Object[] arrayOfObject = { paramString };
    int i = queryForInt(str, arrayOfObject);
    if (i > 0) {
      bool = true;
    }
    return bool;
  }
  
  public boolean checkFirmById(String paramString)
  {
    String str = "select count(*) from M_FIRM where firmid=?";
    boolean bool = false;
    Object[] arrayOfObject = { paramString };
    int i = queryForInt(str, arrayOfObject);
    if (i > 0) {
      bool = true;
    }
    return bool;
  }
  
  public void insertBroker(Broker paramBroker)
  {
    String str = "insert into M_B_broker  (brokerid, password, name, telephone, mobile, email, address, note,firmid,marketManager,locationProvince,createdate) values(?, ?, ?, ?,?, ?, ?, ?,?,?,?,sysdate)";
    Object[] arrayOfObject = { paramBroker.getBrokerid(), MD5.encodePassword(paramBroker.getPassword(), "MD5"), paramBroker.getName(), paramBroker.getTelephone(), paramBroker.getMobile(), paramBroker.getEmail(), paramBroker.getAddress(), paramBroker.getNote(), paramBroker.getFirmId(), paramBroker.getMarketManager(), paramBroker.getLocationProvince() };
    int[] arrayOfInt = { 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void deleteBroker(String paramString)
  {
    String str = "delete from M_B_broker where brokerid=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void updateBroker(Broker paramBroker)
  {
    String str = "update M_B_broker set name = ?,telephone = ?,mobile = ?,email = ?,address = ?,note = ?, firmid = ?, marketManager = ?, locationProvince = ? where brokerid = ?";
    Object[] arrayOfObject = { paramBroker.getName(), paramBroker.getTelephone(), paramBroker.getMobile(), paramBroker.getEmail(), paramBroker.getAddress(), paramBroker.getNote(), paramBroker.getFirmId(), paramBroker.getMarketManager(), paramBroker.getLocationProvince(), paramBroker.getBrokerid() };
    int[] arrayOfInt = { 12, 12, 12, 12, 12, 12, 12, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void setBrokerPwd(String paramString1, String paramString2)
  {
    String str = "update M_B_broker set password =? where brokerid =?";
    System.out.println(str);
    Object[] arrayOfObject = { MD5.encodePassword(paramString2, "MD5"), paramString1 };
    int[] arrayOfInt = { 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public Broker getBrokerById(String paramString)
  {
    String str = "select * from M_B_broker where brokerid=?";
    Object[] arrayOfObject = { paramString };
    List localList = getJdbcTemplate().query(str, arrayOfObject, new CommonRowMapper(new Broker()));
    Broker localBroker = null;
    if (localList.size() > 0) {
      localBroker = (Broker)localList.get(0);
    }
    return localBroker;
  }
  
  public List getFirmById(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    String str = "select firmid,name,fullname,contactman,phone, type from m_firm where firmid in (select firmid from m_B_firmandbroker where brokerid=?)  ";
    Object[] arrayOfObject1 = null;
    Object[] arrayOfObject2 = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject1 = paramQueryConditions.getValueArray();
      int i = arrayOfObject1.length + 1;
      arrayOfObject2 = new Object[arrayOfObject1.length + 1];
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
      arrayOfObject2[0] = paramString;
      System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 1, arrayOfObject1.length);
    }
    else
    {
      arrayOfObject2 = new Object[] { paramString };
    }
    List localList = queryBySQL(str, arrayOfObject2, paramPageInfo);
    return localList;
  }
  
  public List getFirms(QueryConditions paramQueryConditions)
  {
    String str = "select * from m_firm where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new Firm()));
  }
  
  public void deleteFirmRelated(String paramString)
  {
    String str = "delete from m_B_firmandbroker where firmid=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void addFirmRelated(String paramString1, String paramString2)
  {
    String str = "insert into m_B_firmandbroker(brokerid,firmid) values(?,?)";
    Object[] arrayOfObject = { paramString1, paramString2 };
    int[] arrayOfInt = { 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void deleteFirmRelatedByBrokerId(String paramString)
  {
    String str = "delete from m_B_firmandbroker where brokerid=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void addLoginLog(String paramString1, String paramString2, String paramString3)
  {
    String str = "insert into T_SYSLOG(ID,userID,action,note,CreateTime) values(SEQ_T_SYSLOG.Nextval,?,?,?,sysdate)";
    Object[] arrayOfObject = { paramString1, paramString2, paramString3 };
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public Dealer getDealerByfirmId(String paramString)
  {
    String str = "select t.firmid,t.name,t.contactman linkman,t.phone tel from m_firm t where firmId=? ";
    Object[] arrayOfObject = { paramString };
    Dealer localDealer = null;
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new Dealer()));
    if (localList.size() > 0) {
      localDealer = (Dealer)localList.get(0);
    }
    return localDealer;
  }
  
  public void deleteBrokerOperatorMenu(String paramString)
  {
    String str = "delete from m_b_brokeroperatorandmenu  where operatorid in (select operatorid from m_b_brokeroperator where brokerid=?)";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void deleteBrokerOperatorGroup(String paramString)
  {
    String str = "delete from m_b_brokeroperatorandgroup  where operatorid in (select operatorid from m_b_brokeroperator where brokerid=?)";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void deleteBrokerGroup(String paramString)
  {
    String str = "delete from m_b_brokergroup where brokerid=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void deleteBrokerOperator(String paramString)
  {
    String str = "delete from m_b_brokeroperator where brokerid=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void deleteBrokerRight(String paramString)
  {
    String str = "delete from m_b_broker_right where brokerid=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void addFirmRelatedGroup(String paramString1, String paramString2)
  {
    String str = "insert into m_b_brokergroupandfirm(groupid,brokerid,firmid) values ((select groupid from m_b_brokergroup g where g.brokerid = ? and g.groupname = ?),?,?)";
    Object[] arrayOfObject = { paramString1, paramString1, paramString1, paramString2 };
    int[] arrayOfInt = { 12, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void deleteFirmRelatedGroup(String paramString1, String paramString2)
  {
    String str = "delete from m_b_brokergroupandfirm where firmid=? and brokerid=?";
    Object[] arrayOfObject = { paramString2, paramString1 };
    int[] arrayOfInt = { 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public int getDealerByBrokerId(String paramString)
  {
    String str = "Select Count(1) From m_b_broker a where a.brokerid='" + paramString + "'";
    int i = queryForInt(str);
    return i;
  }
  
  public void insertBrokerRewardpropsbreed(Broker paramBroker)
  {
    String str = "insert into m_b_brokerrewardprops(brokerid,breedId,moduleId,rewardrate,firstpayrate,secondpayrate) select '" + paramBroker.getBrokerid() + "', a.breedId,2,b.rewardrate,b.firstpayrate,b.secondpayrate from t_a_breed a,m_b_brokerrewardpropsbreed b where a.breedid=b.breedid";
    updateBySQL(str);
  }
  
  public void deleteBrokerRewardProps(String paramString)
  {
    String str = "delete from m_b_brokerrewardprops where brokerid='" + paramString + "'";
    updateBySQL(str);
  }
}
