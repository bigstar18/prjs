package gnnt.MEBS.announcement.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

@Repository("noticeProDao")
public class NoticeProDao
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(NoticeProDao.class);
  
  @Resource(name="dataSource")
  public final void setSuperDataSource(DataSource dataSource)
  {
    super.setDataSource(dataSource);
  }
  
  public int marketOKNotice(String roles, String users, Long noticeId)
  {
    MarketOKNotice func = new MarketOKNotice(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_roles", roles);
    inputs.put("p_users", users);
    inputs.put("p_noticeId", noticeId);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("Return")).intValue();
    return result;
  }
  
  public int brokerOKNotice(String organization, String brokerage, String manager, Long noticeId)
  {
    BrokerOKNotice func = new BrokerOKNotice(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_organization", organization);
    inputs.put("p_brokerage", brokerage);
    inputs.put("p_manager", manager);
    inputs.put("p_noticeId", noticeId);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("Return")).intValue();
    return result;
  }
  
  public int memberOKNotice(String all, String member, String users, String relation, Long noticeId)
  {
    MemberOKNotice func = new MemberOKNotice(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_all", all);
    inputs.put("p_members", member);
    inputs.put("p_users", users);
    inputs.put("p_isRelation", relation);
    inputs.put("p_noticeId", noticeId);
    
    this.logger.debug("member:" + member);
    this.logger.debug("users:" + users);
    this.logger.debug("relation:" + relation);
    this.logger.debug("noticeId:" + noticeId);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("Return")).intValue();
    return result;
  }
  
  public int specialMemberOKNotice(String all, String specialMemeber, String users, Long noticeId)
  {
    SpecialMemberOKNotice func = new SpecialMemberOKNotice(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_isAll", all);
    inputs.put("p_specialMemberIds", specialMemeber);
    inputs.put("p_users", users);
    inputs.put("p_noticeId", noticeId);
    
    this.logger.debug("specialMemeber:" + specialMemeber);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("Return")).intValue();
    return result;
  }
  
  public int customerOKNotice(String all, String members, String customerNo, Long noticeId)
  {
    CustomerOKNotice func = new CustomerOKNotice(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_all", all);
    inputs.put("p_members", members);
    inputs.put("p_users", customerNo);
    inputs.put("p_noticeId", noticeId);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("Return")).intValue();
    return result;
  }
  
  public int organizationOKNotice(String isAll, String elementText, String organization, String relation, Long noticeId)
  {
    OrganizationOKNotice func = new OrganizationOKNotice(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_all", isAll);
    inputs.put("p_memberId", elementText);
    inputs.put("p_organization", organization);
    inputs.put("p_isRelation", relation);
    inputs.put("p_noticeId", noticeId);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("Return")).intValue();
    return result;
  }
  
  public int brokerOKNotice(String isAll, String elementText, String brokerage, String relation, Long noticeId)
  {
    BrokerageOKNotice func = new BrokerageOKNotice(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_all", isAll);
    inputs.put("p_memberId", elementText);
    inputs.put("p_brokerage", brokerage);
    inputs.put("p_isRelation", relation);
    inputs.put("p_noticeId", noticeId);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("Return")).intValue();
    return result;
  }
  
  public int customerForMemberOKNotice(String isAll, String object, String trader, Long noticeId)
  {
    CustomerForMemberOKNotice func = new CustomerForMemberOKNotice(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_all", isAll);
    inputs.put("p_members", object);
    inputs.put("p_users", trader);
    inputs.put("p_noticeId", noticeId);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("Return")).intValue();
    return result;
  }
  
  private class MarketOKNotice
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_marketOKNotice";
    
    public MarketOKNotice(DataSource ds)
    {
      super("FN_T_marketOKNotice");
      setFunction(true);
      declareParameter(new SqlOutParameter("Return", 2));
      declareParameter(new SqlParameter("p_roles", 12));
      declareParameter(new SqlParameter("p_users", 12));
      declareParameter(new SqlParameter("p_noticeId", 2));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  private class OrganizationOKNotice
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_organizationOKNotice";
    
    public OrganizationOKNotice(DataSource ds)
    {
      super("FN_T_organizationOKNotice");
      setFunction(true);
      declareParameter(new SqlOutParameter("Return", 2));
      declareParameter(new SqlParameter("p_all", 12));
      declareParameter(new SqlParameter("p_memberId", 12));
      declareParameter(new SqlParameter("p_organization", 12));
      declareParameter(new SqlParameter("p_isRelation", 12));
      declareParameter(new SqlParameter("p_noticeId", 2));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  private class CustomerForMemberOKNotice
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_CustomerOKNotice";
    
    public CustomerForMemberOKNotice(DataSource ds)
    {
      super("FN_T_CustomerOKNotice");
      setFunction(true);
      declareParameter(new SqlOutParameter("Return", 2));
      declareParameter(new SqlParameter("p_all", 12));
      declareParameter(new SqlParameter("p_members", 12));
      declareParameter(new SqlParameter("p_users", 12));
      declareParameter(new SqlParameter("p_noticeId", 2));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  private class BrokerageOKNotice
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_BrokerOKNotice";
    
    public BrokerageOKNotice(DataSource ds)
    {
      super("FN_T_BrokerOKNotice");
      setFunction(true);
      declareParameter(new SqlOutParameter("Return", 2));
      declareParameter(new SqlParameter("p_all", 12));
      declareParameter(new SqlParameter("p_memberId", 12));
      declareParameter(new SqlParameter("p_brokerage", 12));
      declareParameter(new SqlParameter("p_isRelation", 12));
      declareParameter(new SqlParameter("p_noticeId", 2));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  private class BrokerOKNotice
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_brokerOKNotice";
    
    public BrokerOKNotice(DataSource ds)
    {
      super("FN_T_brokerOKNotice");
      setFunction(true);
      declareParameter(new SqlOutParameter("Return", 2));
      declareParameter(new SqlParameter("p_organization", 12));
      declareParameter(new SqlParameter("p_brokerage", 12));
      declareParameter(new SqlParameter("p_manager", 12));
      declareParameter(new SqlParameter("p_noticeId", 2));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  private class MemberOKNotice
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_memberOKNotice";
    
    public MemberOKNotice(DataSource ds)
    {
      super("FN_T_memberOKNotice");
      setFunction(true);
      declareParameter(new SqlOutParameter("Return", 2));
      declareParameter(new SqlParameter("p_all", 12));
      declareParameter(new SqlParameter("p_members", 12));
      declareParameter(new SqlParameter("p_users", 12));
      declareParameter(new SqlParameter("p_isRelation", 12));
      declareParameter(new SqlParameter("p_noticeId", 2));
      
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  private class SpecialMemberOKNotice
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_specialMemberOKNotice";
    
    public SpecialMemberOKNotice(DataSource ds)
    {
      super("FN_T_specialMemberOKNotice");
      setFunction(true);
      declareParameter(new SqlOutParameter("Return", 2));
      declareParameter(new SqlParameter("p_isAll", 12));
      declareParameter(new SqlParameter("p_specialMemberIds", 12));
      declareParameter(new SqlParameter("p_users", 12));
      declareParameter(new SqlParameter("p_noticeId", 2));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  private class CustomerOKNotice
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_customerOKNotice";
    
    public CustomerOKNotice(DataSource ds)
    {
      super("FN_T_customerOKNotice");
      setFunction(true);
      declareParameter(new SqlOutParameter("Return", 2));
      declareParameter(new SqlParameter("p_all", 12));
      declareParameter(new SqlParameter("p_members", 12));
      declareParameter(new SqlParameter("p_users", 12));
      declareParameter(new SqlParameter("p_noticeId", 2));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
}
