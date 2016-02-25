package gnnt.MEBS.member.broker.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.member.broker.model.BrokerRewardProps;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class BrokerRewardPropsDao
  extends DaoHelperImpl
{
  private final transient Log logger = LogFactory.getLog(BrokerRewardPropsDao.class);
  
  public List<BrokerRewardProps> getBrokerRewardPropsList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from M_B_BrokerRewardProps t where 1=1 ";
    Object[] arrayOfObject1 = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject1 = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    str = str + "";
    this.logger.debug("sql:" + str);
    for (Object localObject : arrayOfObject1) {
      this.logger.debug(localObject.toString());
    }
    return queryBySQL(str, arrayOfObject1, paramPageInfo, new CommonRowMapper(new BrokerRewardProps()));
  }
  
  public List getBrokerRewardPropsTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from (select t.*,decode(t.moduleid,2,a.breedname,3,z.breedname,4,'竞价') breedName from M_B_BrokerRewardProps t,t_a_breed a,z_breed z where t.breedId=a.breedId(+) and t.breedId=z.breedId(+)) where 1=1";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    str = str + "";
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public void insertBrokerRewardProps(BrokerRewardProps paramBrokerRewardProps)
  {
    String str = "insert into m_b_brokerrewardprops (brokerid,breedId,moduleId,rewardRate,firstPayRate,secondPayRate,autoPay,payPeriod,payPeriodDate) values(?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramBrokerRewardProps.getBrokerId(), paramBrokerRewardProps.getBreedId(), paramBrokerRewardProps.getModuleId(), Double.valueOf(paramBrokerRewardProps.getRewardRate()), Double.valueOf(paramBrokerRewardProps.getFirstPayRate()), Double.valueOf(paramBrokerRewardProps.getSecondPayRate()), paramBrokerRewardProps.getAutoPay(), Integer.valueOf(paramBrokerRewardProps.getPayPeriod()), Integer.valueOf(paramBrokerRewardProps.getPayPeriodDate()) };
    int[] arrayOfInt = { 12, 12, 12, 2, 2, 2, 12, 2, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void deleteBrokerRewardProps(BrokerRewardProps paramBrokerRewardProps)
  {
    String str = "delete from m_b_brokerrewardprops where breedId=? and brokerId=? and moduleId=?";
    Object[] arrayOfObject = { paramBrokerRewardProps.getBreedId(), paramBrokerRewardProps.getBrokerId(), paramBrokerRewardProps.getModuleId() };
    int[] arrayOfInt = { 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void updateBrokerRewardProps(BrokerRewardProps paramBrokerRewardProps)
  {
    String str = "update m_b_brokerrewardprops set rewardRate=?,firstPayRate=?,secondPayRate=?,autoPay=?,payPeriod=?,payPeriodDate=?  where brokerid=? and breedId=? and moduleId=?";
    Object[] arrayOfObject = { Double.valueOf(paramBrokerRewardProps.getRewardRate()), Double.valueOf(paramBrokerRewardProps.getFirstPayRate()), Double.valueOf(paramBrokerRewardProps.getSecondPayRate()), paramBrokerRewardProps.getAutoPay(), Integer.valueOf(paramBrokerRewardProps.getPayPeriod()), Integer.valueOf(paramBrokerRewardProps.getPayPeriodDate()), paramBrokerRewardProps.getBrokerId(), paramBrokerRewardProps.getBreedId(), paramBrokerRewardProps.getModuleId() };
    int[] arrayOfInt = { 2, 2, 2, 12, 2, 2, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public int getBrokerRewardPropsCount(BrokerRewardProps paramBrokerRewardProps)
  {
    String str = "select count(*) from m_b_brokerrewardprops where breedid=? and brokerid=? and moduleId=?";
    Object[] arrayOfObject = { paramBrokerRewardProps.getBreedId(), paramBrokerRewardProps.getModuleId(), paramBrokerRewardProps.getBrokerId() };
    return queryForInt(str, arrayOfObject);
  }
  
  public List getTbBreeds()
  {
    String str = "select breedId, breedName from t_a_breed";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getZcjsBreeds()
  {
    String str = "select breedId, breedName from z_breed";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getBrokers()
  {
    String str = "select brokerId from m_b_broker ";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getBrokerRewardPropsBreedList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select t.breedId,t.rewardrate*100 rewardrate,t.firstpayrate*100 firstpayrate,t.secondpayrate*100 secondpayrate,t1.BreedName from M_B_BrokerRewardPropsBreed t,T_A_Breed t1 where 1=1 and t.breedId=t1.BreedID ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public void insertBrokerRewardPropsBreed(BrokerRewardProps paramBrokerRewardProps)
  {
    String str = "insert into M_B_BrokerRewardPropsBreed  (BREEDID, rewardrate, firstpayrate, secondpayrate) values(?,?,?,?)";
    Object[] arrayOfObject = { Integer.valueOf(Integer.parseInt(paramBrokerRewardProps.getBreedId())), Double.valueOf(paramBrokerRewardProps.getRewardRate()), Double.valueOf(paramBrokerRewardProps.getFirstPayRate()), Double.valueOf(paramBrokerRewardProps.getSecondPayRate()) };
    int[] arrayOfInt = { 4, 8, 8, 8 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void insertBrokerRewardPropsByBreed(BrokerRewardProps paramBrokerRewardProps)
  {
    String str = "insert into m_b_brokerrewardprops(brokerid,breedid,moduleId,rewardrate,firstpayrate,secondpayrate,autopay,payperiod,payperioddate,oprcode_t)";
    str = str + " select a.brokerid,?,?,?,?,?,?,?,?,? from m_b_broker a where a.brokerid not in  (select brokerid from m_b_brokerrewardprops where breedId = ?)";
    Object[] arrayOfObject = { paramBrokerRewardProps.getBreedId(), paramBrokerRewardProps.getModuleId(), Double.valueOf(paramBrokerRewardProps.getRewardRate()), Double.valueOf(paramBrokerRewardProps.getFirstPayRate()), Double.valueOf(paramBrokerRewardProps.getSecondPayRate()), paramBrokerRewardProps.getAutoPay(), Integer.valueOf(paramBrokerRewardProps.getPayPeriod()), Integer.valueOf(paramBrokerRewardProps.getPayPeriodDate()), paramBrokerRewardProps.getOprCode_T(), paramBrokerRewardProps.getBreedId() };
    int[] arrayOfInt = { 12, 12, 8, 8, 8, 12, 2, 2, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public List getBreeds()
  {
    String str = "select BreedID, BreedName from T_A_Breed";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getBrokerRewardPropsBreedByID(int paramInt)
  {
    String str = "select t.breedId,t.rewardrate*100 rewardrate,t.firstpayrate*100 firstpayrate,t.secondpayrate*100 secondpayrate,t1.BreedName from M_B_BrokerRewardPropsBreed t,T_A_Breed t1 where 1=1 and t.breedId=t1.BreedID and t.breedId=" + paramInt + " ";
    return queryBySQL(str);
  }
  
  public void updateBrokerRewardPropsByBreed(BrokerRewardProps paramBrokerRewardProps)
  {
    String str = "update m_b_brokerrewardprops set rewardrate=?, firstpayrate=?, secondpayrate=?,autopay=? where breedId in (?)";
    Object[] arrayOfObject = { Double.valueOf(paramBrokerRewardProps.getRewardRate()), Double.valueOf(paramBrokerRewardProps.getFirstPayRate()), Double.valueOf(paramBrokerRewardProps.getSecondPayRate()), paramBrokerRewardProps.getAutoPay(), paramBrokerRewardProps.getBreedId() };
    int[] arrayOfInt = { 8, 8, 8, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void updateBrokerRewardPropsBreed(BrokerRewardProps paramBrokerRewardProps)
  {
    String str = "update M_B_BrokerRewardPropsBreed set rewardrate=?, firstpayrate=?, secondpayrate=? where BREEDID=? ";
    Object[] arrayOfObject = { Double.valueOf(paramBrokerRewardProps.getRewardRate()), Double.valueOf(paramBrokerRewardProps.getFirstPayRate()), Double.valueOf(paramBrokerRewardProps.getSecondPayRate()), paramBrokerRewardProps.getBreedId() };
    int[] arrayOfInt = { 8, 8, 8, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public List getBrokerIDList()
  {
    String str = "select BrokerID from M_B_Broker t  ";
    return queryBySQL(str);
  }
  
  public void deleteBrokerRewardProps(String paramString1, String paramString2)
  {
    String str = "delete from m_b_brokerrewardprops where breedId=? and brokerid=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    int[] arrayOfInt = { 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void deleteBrokerRewardPropsBreed(int paramInt)
  {
    String str = "delete from M_B_BrokerRewardPropsBreed where BREEDID=?";
    Object[] arrayOfObject = { Integer.valueOf(paramInt) };
    int[] arrayOfInt = { 4 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}
