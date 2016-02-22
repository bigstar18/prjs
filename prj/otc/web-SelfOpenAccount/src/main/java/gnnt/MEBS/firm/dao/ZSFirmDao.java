package gnnt.MEBS.firm.dao;

import gnnt.MEBS.account.model.CompMember;
import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.model.CustomerStatus;
import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.broke.model.CustomerMappingBroker;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.firm.model.CustomerInfoAudit;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import oracle.sql.CLOB;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.StoredProcedure;

public class ZSFirmDao
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(ZSFirmDao.class);
  
  public List checkMember(String memberNo)
  {
    String sql = "select * from m_memberinfo t where t.memberno='" + memberNo + "'";
    System.out.println("sql------>" + sql);
    return getJdbcTemplate().queryForList(sql);
  }
  
  public long insertCount(String bankid, String firmId, String bankAccout, String name, String cardNumber, String carType)
  {
    String sql = "select SEQ_F_B_FIRMIDANDACCOUNT.Nextval from dual";
    long id = getJdbcTemplate().queryForLong(sql);
    sql = "insert into f_b_firmidandaccount(id,BANKID,FIRMID,ACCOUNT,ACCOUNTNAME,ISOPEN,CARDTYPE,CARD,contact) values (?,?,?,?,?,0,?,?,?)";
    Object[] params = { Long.valueOf(id), bankid, firmId, bankAccout, name, carType, cardNumber, firmId };
    int[] dataTypes = { -5, 12, 12, 12, 12, 
      12, 12, 12 };
    getJdbcTemplate().update(sql, params, dataTypes);
    return id;
  }
  
  public int deleteCount(long id)
  {
    String sql = "delete from f_b_firmidandaccount where id=?";
    Object[] params = { Long.valueOf(id) };
    int[] dataTypes = { -5 };
    return getJdbcTemplate().update(sql, params, dataTypes);
  }
  
  public Map getMemberForBrokerage(String brokerage)
  {
    String sql = "SELECT * from m_memberinfo m where m.memberno in (SELECT b.memberno  from m_b_brokerage b where b.brokerageno=?)";
    Object[] params = { brokerage };
    int[] dataTypes = { 12 };
    List<Map> list = getJdbcTemplate().queryForList(sql, params, dataTypes);
    Map map = null;
    if ((list != null) && (list.size() > 0)) {
      map = (Map)list.get(0);
    }
    return map;
  }
  
  public void insertFirmUser(String firmId, String name, String cardNumber, String carType)
  {
    String sql = "insert into f_b_firmuser(firmid,FIRMname,status,CARDTYPE,CARD,contact) values (?,?,0,?,?,?)";
    Object[] params = { firmId, name, carType, cardNumber, firmId };
    int[] dataTypes = { 12, 12, 12, 12, 12 };
    getJdbcTemplate().update(sql, params, dataTypes);
  }
  
  public void deleteFirmUser(String firmid)
  {
    String sql = "delete from f_b_firmuser where firmid=?";
    Object[] params = { firmid };
    int[] dataTypes = { 12 };
    getJdbcTemplate().update(sql, params, dataTypes);
  }
  
  public void insertFirm(String firmId, String name)
  {
    String sql = "insert into m_firm(FIRMID,NAME,FIRMTYPE) values (?,?,'C')";
    Object[] params = { firmId, name };
    int[] dataTypes = { 12, 12 };
    getJdbcTemplate().update(sql, params, dataTypes);
  }
  
  public void deleteFirm(String firmid)
  {
    String sql = "delete from m_firm where firmid=?";
    Object[] params = { firmid };
    int[] dataTypes = { 12 };
    getJdbcTemplate().update(sql, params, dataTypes);
  }
  
  public int addFrimBindingInfo(String customerNo, String wxno, String wxName)
  {
    String sql = "update m_customerinfo set wxno=?,wxnickname=? where CUSTOMERNO=?";
    Object[] params = { wxno, wxName, customerNo };
    int[] types = { 12, 12, 12 };
    return getJdbcTemplate().update(sql, params, types);
  }
  
  public List<Organization> getOrganization(String memberNo)
  {
    String sql = "select * from m_b_organization t where t.memberno='" + memberNo + "'";
    List list = getJdbcTemplate().queryForList(sql);
    return list;
  }
  
  public List<Brokerage> getOrgBroker(String organizationno)
  {
    String sql = "select * from m_b_brokerage t where t.brokerageno in (select m.brokerageno from m_b_brokerageandorganization m where m.organizationno='" + organizationno + "')";
    System.out.println("sql--->" + sql);
    return getJdbcTemplate().queryForList(sql);
  }
  
  public List getBroker(String memberNo)
  {
    List listOrganization = getOrganization(memberNo);
    String sql = "select * from m_b_brokerage b where b.brokerageno not in( select t.brokerageno from m_b_brokerageandorganization t,m_b_organization  a where t.organizationno=a.organizationno and a.memberno='" + memberNo + "') and b.memberno='" + memberNo + "'";
    return getJdbcTemplate().queryForList(sql);
  }
  
  public Map getCustomInfo(String customerno)
  {
    String sql = "select * from m_customerinfo t where t.customerno='" + customerno + "'";
    Map map = new HashMap();
    List<Map> list = getJdbcTemplate().queryForList(sql);
    if ((list != null) && (list.size() > 0)) {
      map = (Map)list.get(0);
    }
    return map;
  }
  
  public Map getCustomerinfor(String customerno)
  {
    String sql = "select * from m_trader t where t.traderid='" + customerno + "'";
    Map map = new HashMap();
    List<Map> list = getJdbcTemplate().queryForList(sql);
    if ((list != null) && (list.size() > 0)) {
      map = (Map)list.get(0);
    }
    return map;
  }
  
  public Map getMemberInfors(String memberNo)
  {
    String sql = "SELECT * from m_memberinfo t where t.memberno='" + memberNo + "' ";
    List<Map> list = getJdbcTemplate().queryForList(sql);
    Map map = new HashMap();
    if ((list != null) && (list.size() > 0)) {
      map = (Map)list.get(0);
    }
    return map;
  }
  
  public Map getMemberInfor(String memberNo)
  {
    String sql = "SELECT t.memberno,t.notes,t.hrefaddress from t_memberinfor t where t.memberno='" + memberNo + "' ";
    List<Map> list = getJdbcTemplate().queryForList(sql);
    Map map = new HashMap();
    if ((list != null) && (list.size() > 0)) {
      map = (Map)list.get(0);
    }
    return map;
  }
  
  public List getMemberIdAndName()
  {
    String sql = "select m.memberno , m.name from m_memberinfo m ";
    return getJdbcTemplate().queryForList(sql);
  }
  
  public MemberInfo getById(String memNo)
  {
    MemberInfo memberInfo = new MemberInfo();
    




    String sql = "select t.*,s.* from m_memberinfo t, t_compmember s where t.memberno = '" + 
      memNo + "' and s.m_firmid = '" + memNo + "' ";
    System.out.println(sql);
    List<Map> list = getJdbcTemplate().queryForList(sql);
    System.out.println(list);
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      memberInfo.setId((String)map.get("id"));
      memberInfo.setSignNo((String)map.get("signno"));
      memberInfo.setName((String)map.get("name"));
      memberInfo.setMemberType((String)map.get("membertype"));
      memberInfo.setPapersType(Integer.valueOf(Integer.parseInt(map.get("paperstype")
        .toString())));
      memberInfo.setPapersName((String)map.get("papersname"));
      memberInfo.setAddress((String)map.get("address"));
      memberInfo.setPhone((String)map.get("phone"));
      memberInfo.setFax((String)map.get("fax"));
      memberInfo.setPostCode((String)map.get("postcode"));
      memberInfo.setEmail((String)map.get("email"));
      memberInfo.setSpecialMemberNos((String)map.get("specialmembernos"));
      memberInfo.setExtendData((String)map.get("extenddata"));
      CompMember compMember = new CompMember();
      compMember.setFirmName((String)map.get("firmName"));
      compMember.setId((String)map.get("m_firmId"));
      compMember.setSm_firmId((String)map.get("sm_firmId"));
      compMember.setStatus((String)map.get("status"));
      compMember.setStatusChangeDate((Date)map.get("statuschangedate"));
      memberInfo.setCompMember(compMember);
      memberInfo.setCreateTime((Date)map.get("createtime"));
      memberInfo.setPassword((String)map.get("password"));
      return memberInfo;
    }
    return memberInfo;
  }
  
  public String getMemberClobById(String id)
  {
    CLOB clob = null;
    String sql = "select revelation from m_memberreveal where memberno=? ";
    List list = getJdbcTemplate().queryForList(sql, 
      new Object[] { id }, new int[] { 12 });
    String str = "此会员暂时未填写风险揭示书。";
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      str = (String)map.get("revelation");
    }
    return str;
  }
  
  public boolean checkAccountNum(String bankaccount)
  {
    String sql = "select * from f_b_bankcompareinfo t where t.account = ? ";
    
    List list = getJdbcTemplate().queryForList(sql, 
      new Object[] { bankaccount }, new int[] { 12 });
    if (list == null) {
      return false;
    }
    if (list.size() == 0) {
      return true;
    }
    return false;
  }
  
  public boolean checkedCardNumber(String cardNumber, String memNo)
  {
    cardNumber = cardNumber.replace("x", "X");
    String sql = "select paperscode from m_customerinfo  where paperscode=?  and memberno = ? ";
    Object[] params = { cardNumber, memNo };
    int[] types = { 12, 12 };
    List list = getJdbcTemplate().queryForList(sql, params, types);
    if (list == null) {
      return false;
    }
    if (list.size() > 0) {
      return true;
    }
    return false;
  }
  
  public void addAudit(CustomerInfoAudit audit)
  {
    String sql = "insert into m_customerinfo_audit(  customerno,name,fullname,memberno,paperstype,paperscode,auditstatus,bank,bankaccount, contactman,contactsphone,phonepwd,address,phone,fax,postcode,email,note,  createtime,modifytime   ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?)";
    



    Object[] params = { audit.getCustomerNo(), 
      audit.getName(), audit.getFullName(), audit.getMemberNo(), 
      audit.getPapersType(), audit.getPapersName(), 
      audit.getStatus(), audit.getBank(), audit.getBankAccount(), 
      audit.getContactman(), audit.getContactsPhone(), 
      audit.getPhonePwd(), audit.getAddress(), audit.getPhone(), 
      audit.getFax(), audit.getPostCode(), audit.getEmail(), 
      audit.getNote(), audit.getModifyTime() };
    
    int[] dataTypes = { 12, 12, 
      12, 12, 12, 12, 
      12, 12, 12, 12, 
      12, 12, 12, 12, 
      12, 12, 12, 12, 
      91 };
    
    getJdbcTemplate().update(sql, params, dataTypes);
  }
  
  public boolean checkTradetime()
  {
    String sql = "select * from t_systemstatus t where tradedate=trunc(sysdate)";
    List list = getJdbcTemplate().queryForList(sql);
    if (list == null) {
      return false;
    }
    if (list.size() == 0) {
      return false;
    }
    return true;
  }
  
  public boolean checkedFirm(String bankAccount, String businessLicenseNo, int cardType, String cardNumber)
  {
    String sql = "select * from M_firm where bankaccount = '" + bankAccount + 
      "'";
    
    List list = getJdbcTemplate().queryForList(sql);
    if (list == null) {
      return false;
    }
    if (list.size() == 0) {
      return true;
    }
    return false;
  }
  
  public boolean PersonCode(String memberNo, String personNo)
  {
    String sql = "select * from m_customerinfo t where t.paperstype='2' and t.paperscode='" + personNo + "' and t.memberno='" + memberNo + "'";
    List list = getJdbcTemplate().queryForList(sql);
    if (list == null) {
      return true;
    }
    if (list.size() == 0) {
      return true;
    }
    return false;
  }
  
  public boolean ContacterPhoneNo(String ContacterPhoneNo)
  {
    String sql = "select * from m_customerinfo t where t.phone ='" + ContacterPhoneNo + "'";
    System.out.println("sql" + sql);
    List list = getJdbcTemplate().queryForList(sql);
    if (list == null) {
      return true;
    }
    if (list.size() > 0) {
      return true;
    }
    return false;
  }
  
  public List getAllBank()
  {
    return getJdbcTemplate().queryForList("select * from f_b_banks t where t.bankid='010'");
  }
  
  public int checkAccount(String firmId)
  {
    CheckAccount func = new CheckAccount(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_firmid", firmId);
    func.execute(inputs);
    
    return 0;
  }
  
  private class CheckAccount
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_F_B_FirmAdd";
    
    public CheckAccount(DataSource ds)
    {
      super("SP_F_B_FirmAdd");
      
      declareParameter(new SqlParameter("p_firmid", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  private class DoneFirmSign
    extends StoredProcedure
  {
    private DoneFirmSign() {}
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int deleteAccount(String firmId)
  {
    DeleteAccount func = new DeleteAccount(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_firmid", firmId);
    func.execute(inputs);
    
    String sql = "delete from m_firm where firmid=?";
    Object[] params = { firmId };
    int[] dataTypes = { 12 };
    this.logger.debug("sql: " + sql);
    return getJdbcTemplate().update(sql, params, dataTypes);
  }
  
  private class DeleteAccount
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_F_B_FirmDel";
    
    public DeleteAccount(DataSource ds)
    {
      super("SP_F_B_FirmDel");
      
      declareParameter(new SqlParameter("p_firmid", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public void insertTraderModule(String firmId, String moudleId)
  {
    String sql = " insert into M_TraderModule (ModuleID,TraderID,Enabled) values(?,?,'Y')";
    Object[] params = { moudleId, firmId };
    int[] dataTypes = { 12, 12 };
    this.logger.debug("sql: " + sql);
    getJdbcTemplate().update(sql, params, dataTypes);
  }
  
  public String queryErrorCount(String bankaccount)
  {
    long errorcount = 1L;
    
    String sql = "select errorcount from f_b_webfirmaddcheck  where bankaccount=" + 
      bankaccount + " and TRUNC(CLEARDATE)=TRUNC(sysdate)";
    List list = getJdbcTemplate().queryForList(sql);
    if ((list != null) && (list.size() > 0)) {
      errorcount = 
        ((BigDecimal)((Map)list.get(0)).get("errorcount")).longValue();
    }
    if (errorcount >= 10L) {
      return "-1";
    }
    return "0";
  }
  
  public void insertBankError(String bankname, String bankaccount)
  {
    String bankid = "301";
    int errorcount = 1;
    String sql = "select bankid from f_b_banks  where bankname='" + 
      bankname + "'";
    List list = getJdbcTemplate().queryForList(sql);
    if ((list != null) && (list.size() > 0)) {
      bankid = (String)((Map)list.get(0)).get("bankid");
    }
    sql = 
      "select * from f_b_webfirmaddcheck where bankaccount=" + bankaccount + " and TRUNC(CLEARDATE)=TRUNC(sysdate)";
    list = getJdbcTemplate().queryForList(sql);
    if ((list != null) && (list.size() > 0))
    {
      sql = " update f_b_webfirmaddcheck set CLEARDATE=sysdate,ERRORCOUNT=ERRORCOUNT+1 where bankaccount=? and TRUNC(CLEARDATE)=TRUNC(sysdate)";
      Object[] params = { bankaccount };
      int[] dataTypes = { 12 };
      this.logger.debug("sql: " + sql);
      getJdbcTemplate().update(sql, params, dataTypes);
    }
    else
    {
      sql = " insert into f_b_webfirmaddcheck values(sysdate,?,?,?)";
      Object[] params = { bankid, bankaccount, Integer.valueOf(errorcount) };
      int[] dataTypes = { 12, 12, 
        2 };
      this.logger.debug("sql: " + sql);
      getJdbcTemplate().update(sql, params, dataTypes);
    }
  }
  
  public void updateFirmStatus(String firmId)
  {
    String sql = " update m_firm t set status='N' where firmid=?";
    Object[] params = { firmId };
    int[] dataTypes = { 12 };
    this.logger.debug("sql: " + sql);
    getJdbcTemplate().update(sql, params, dataTypes);
    
    sql = " update m_trader t set status='N' where firmid=?";
    Object[] params1 = { firmId };
    int[] dataTypes1 = { 12 };
    this.logger.debug("sql: " + sql);
    getJdbcTemplate().update(sql, params1, dataTypes1);
  }
  
  public String getLastFirmIdByBroker()
  {
    String sql = "select customerno from m_customerinfo_audit order by customerno desc";
    String lastFirmId = "0";
    List list = getJdbcTemplate().queryForList(sql);
    if ((list != null) && (list.size() > 0)) {
      lastFirmId = (String)((Map)list.get(0)).get("customerno");
    }
    return lastFirmId;
  }
  
  public boolean getFirm(String id)
  {
    String sql = "select customerno from m_customerinfo where customerno = '" + 
      id + "'";
    List list = getJdbcTemplate().queryForList(sql);
    if (list == null) {
      return false;
    }
    if (list.size() == 0) {
      return false;
    }
    return true;
  }
  
  public String getMaxNoByMemNo(String memNo)
  {
    String max = "1";
    String sql = "select nvl(maxnum,0)+1 nextnum from (select max(to_number(substr(t.customerno,4))) maxnum from m_customerinfo t where substr(t.customerno,0,3)='" + 
      memNo + "')";
    List list = getJdbcTemplate().queryForList(sql);
    if ((list != null) && (list.size() > 0)) {
      max = ((BigDecimal)((Map)list.get(0)).get("nextnum")).longValue();
    }
    return max;
  }
  
  public String getFirmId(String memNo, String organization, String broker)
  {
    String max = "1";
    String str = "";
    String accout = "";
    if (("-1".equals(organization)) && ("-1".equals(broker)))
    {
      String sql = "select t.mem from (select substr(t.customerno,4,6) mem ,rownum rn from m_customerinfo t where substr(t.customerno,0,3)='" + memNo + "')t where t.rn=(select max(b.rn) from (select rownum rn from m_customerinfo t where substr(t.customerno,0,3)='" + memNo + "')b)";
      List list = getJdbcTemplate().queryForList(sql);
      if ((list != null) && (list.size() > 0))
      {
        max = (String)((Map)list.get(0)).get("mem");
        str = memNo + "0000008";
      }
      else
      {
        str = memNo + "0000008";
      }
      System.out.println("str----------------------------->" + str);
      String sql1 = "select nvl(maxnum,0)+1 nextnum from (select max(to_number(substr(t.customerno,11))) maxnum from m_customerinfo t where substr(t.customerno,0 ,10)='" + str + "')";
      System.out.println("sql----------------------------->" + sql);
      List list1 = getJdbcTemplate().queryForList(sql1);
      if ((list1 != null) && (list1.size() > 0))
      {
        Long longs = Long.valueOf(((BigDecimal)((Map)list1.get(0)).get("nextnum")).longValue());
        if (longs.longValue() > 99999L)
        {
          max = Long.parseLong(max) + 1L;
          str = memNo + "0000008";
          longs = Long.valueOf(1L);
          accout = "00001";
          max = "-1";
        }
        else
        {
          String temp = longs;
          System.out.println("longs---------------------------------->" + longs);
          while (temp.length() < 5) {
            temp = "0" + temp;
          }
          max = str + temp;
          System.out.println("max---------------------------------->" + max);
        }
      }
    }
    if (("-1".equals(organization)) && (!"-1".equals(broker)))
    {
      String sql = "select nvl(maxnum,0)+1 nextnum from (select max(to_number(substr(t.customerno,10))) maxnum from m_customerinfo t where substr(t.customerno,0,9)='" + broker + "')";
      List list = getJdbcTemplate().queryForList(sql);
      Long longs = Long.valueOf(((BigDecimal)((Map)list.get(0)).get("nextnum")).longValue());
      String temp = longs;
      while (temp.length() < 6) {
        temp = "0" + temp;
      }
      max = broker + temp;
    }
    if ((!"-1".equals(organization)) && ("-1".equals(broker)))
    {
      String sql = "select nvl(maxnum,0)+1 nextnum from (select max(to_number(substr(t.customerno,10))) maxnum from m_customerinfo t where substr(t.customerno,0,9)='" + organization + "')";
      List list = getJdbcTemplate().queryForList(sql);
      Long longs = Long.valueOf(((BigDecimal)((Map)list.get(0)).get("nextnum")).longValue());
      String temp = longs;
      while (temp.length() < 6) {
        temp = "0" + temp;
      }
      max = organization + temp;
    }
    if ((!"-1".equals(organization)) && (!"-1".equals(broker)))
    {
      String sql = "select nvl(maxnum,0)+1 nextnum from (select max(to_number(substr(t.customerno,10))) maxnum from m_customerinfo t where substr(t.customerno,0,9)='" + broker + "')";
      List list = getJdbcTemplate().queryForList(sql);
      Long longs = Long.valueOf(((BigDecimal)((Map)list.get(0)).get("nextnum")).longValue());
      String temp = longs;
      while (temp.length() < 6) {
        temp = "0" + temp;
      }
      max = broker + temp;
    }
    return max;
  }
  
  public void addCustomer(Customer obj)
  {
    String sql = "insert into m_customerinfo(  customerno,name,memberno,paperstype,paperscode, phonepwd,address,phone,fax,postcode,email,extenddata,  createtime ) values(?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
    


    Object[] params = { obj.getCustomerNo(), obj.getName(), 
      obj.getMemberNo(), obj.getPapersType(), 
      obj.getPapersName(), 
      
      obj.getPhonePWD(), obj.getAddress(), obj.getPhone(), 
      obj.getFax(), obj.getPostCode(), obj.getEmail(), 
      obj.getExtendData() };
    
    int[] dataTypes = { 12, 12, 
      12, 12, 12, 12, 
      12, 12, 12, 12, 
      12, 12 };
    




    getJdbcTemplate().update(sql, params, dataTypes);
  }
  
  private void addCustomerStatus(Customer obj)
  {
    String sql = "insert into t_customer(  firmid,status ) values(?,?)";
    
    Object[] params = {
      obj.getCustomerStatus().getCustomerNo(), 
      obj.getCustomerStatus().getStatus() };
    
    int[] dataTypes = { 12, 12 };
    
    getJdbcTemplate().update(sql, params, dataTypes);
  }
  
  public void addCustomerInfoPro(String customerNo)
  {
    AddCustomerInfoPro func = new AddCustomerInfoPro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_customerNo", customerNo);
    Map results = func.execute(inputs);
  }
  
  private class AddCustomerInfoPro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_M_Customer";
    
    public AddCustomerInfoPro(DataSource ds)
    {
      super("SP_M_Customer");
      setFunction(false);
      declareParameter(new SqlParameter("p_customerNo", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public void customerAddToPwd(Customer customer)
  {
    CustomerAddToPwd func = new CustomerAddToPwd(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_M_customerID", customer.getId());
    inputs.put("p_phonePwd", customer.getPhonePWD());
    inputs.put("p_password", customer.getPassword());
    Map results = func.execute(inputs);
  }
  
  private class CustomerAddToPwd
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_M_CustomerAddToPwd";
    
    public CustomerAddToPwd(DataSource ds)
    {
      super("SP_M_CustomerAddToPwd");
      setFunction(false);
      


      declareParameter(new SqlParameter("p_M_customerID", 12));
      declareParameter(new SqlParameter("p_phonePwd", 12));
      declareParameter(new SqlParameter("p_password", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public void updateCustomerMappingBroker(CustomerMappingBroker customerMappingBroker)
  {
    String sql = " update M_B_CustomerMappingBroker t set organizationNo = ? , brokerageno = ?,managerno=? where customerno=?";
    Object[] params = { customerMappingBroker.getOrganizationNo(), customerMappingBroker.getBrokerageNo(), customerMappingBroker.getManagerNo(), customerMappingBroker.getCustomerNo() };
    int[] dataTypes = { 12, 12, 12, 12 };
    this.logger.debug("sql: " + sql);
    getJdbcTemplate().update(sql, params, dataTypes);
  }
  
  public CustomerMappingBroker getCustomerMappingBrokerById(String customerNo)
  {
    CustomerMappingBroker customerMappingBroker = null;
    String sql = "select * from M_B_CustomerMappingBroker where customerno = '" + 
      customerNo + "' ";
    System.out.println(sql);
    List list = getJdbcTemplate().queryForList(sql);
    if (list.size() != 0)
    {
      Map map = (Map)list.get(0);
      customerMappingBroker = new CustomerMappingBroker();
      customerMappingBroker.setBrokerageNo((String)map.get("brokerageno"));
      customerMappingBroker.setCustomerNo(customerNo);
      customerMappingBroker.setManagerNo((String)map.get("managerNo"));
      customerMappingBroker.setOrganizationNo((String)map.get("organizationNo"));
      customerMappingBroker.setMemberNo(getMemberNoByCustomer(customerNo));
    }
    return customerMappingBroker;
  }
  
  private String getMemberNoByCustomer(String customerNo)
  {
    String sql = "select memberno from m_customerinfo where customerno = '" + 
      customerNo + "'";
    List list = getJdbcTemplate().queryForList(sql);
    String memberNo = (String)((Map)list.get(0)).get("memberno");
    return memberNo;
  }
  
  public void addCustomerMappingBroker(CustomerMappingBroker customerMappingBroker)
  {
    String sql = " insert into M_B_CustomerMappingBroker ( organizationNo  , brokerageno ,managerno, customerno)values(?,?,?,?) ";
    Object[] params = { customerMappingBroker.getOrganizationNo(), customerMappingBroker.getBrokerageNo(), customerMappingBroker.getManagerNo(), customerMappingBroker.getCustomerNo() };
    int[] dataTypes = { 12, 12, 12, 12 };
    this.logger.debug("sql: " + sql);
    getJdbcTemplate().update(sql, params, dataTypes);
  }
  
  public void lockUserRole()
  {
    String sql = "select * from c_user_role where userid='admin' for update";
    this.logger.error(sql);
    getJdbcTemplate().queryForList(sql);
  }
}
