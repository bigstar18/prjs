package gnnt.MEBS.finance.dao;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.finance.base.dao.oracle.DaoHelperImpl;
import gnnt.MEBS.finance.base.util.SysData;
import gnnt.MEBS.finance.unit.Channel;
import gnnt.MEBS.finance.unit.Summary;
import gnnt.MEBS.finance.unit.Voucher;
import gnnt.MEBS.finance.unit.VoucherEntry;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;

public class VoucherDao
  extends DaoHelperImpl
{
  private final transient Log logger = LogFactory.getLog(VoucherDao.class);
  
  public void createSummary(Summary paramSummary)
  {
    String str1 = paramSummary.getAccountCodeOpp();
    String str2 = "insert into f_Summary(summaryNo,summary,ledgerItem,fundDCFlag,appendAccount,accountCodeOpp) values(?,?,?,?,?,";
    if (str1 != null) {
      str2 = str2 + "'" + str1 + "')";
    } else {
      str2 = str2 + str1 + ")";
    }
    Object[] arrayOfObject = { paramSummary.getSummaryNo(), paramSummary.getSummary(), paramSummary.getLedgerItem(), paramSummary.getFundDCFlag(), paramSummary.getAppendAccount() };
    this.logger.debug("sql: " + str2);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    updateBySQL(str2, arrayOfObject);
  }
  
  public void updateSummary(Summary paramSummary)
  {
    StringBuffer localStringBuffer = new StringBuffer("update f_Summary set summary='").append(paramSummary.getSummary()).append("'").append(",ledgerItem=?,fundDCFlag='" + paramSummary.getFundDCFlag() + "',AppendAccount='" + paramSummary.getAppendAccount() + "'");
    localStringBuffer.append(" where summaryNo='").append(paramSummary.getSummaryNo()).append("'");
    Object[] arrayOfObject = { paramSummary.getLedgerItem() };
    this.logger.debug("sql: " + localStringBuffer);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    updateBySQL(localStringBuffer.toString(), arrayOfObject);
  }
  
  public int deleteSummary(String paramString1, String paramString2)
  {
    String str1 = "select * from F_Voucher where summaryNo='" + paramString1 + "'";
    List localList = queryBySQL(str1, null);
    int i = -1;
    if ((localList != null) && (localList.size() > 0)) {
      i = 0;
    } else {
      try
      {
        str1 = "delete from f_Summary where summaryNo=?";
        Object[] arrayOfObject = { paramString1 };
        this.logger.debug("sql: " + str1);
        this.logger.debug("summaryNo: " + arrayOfObject[0]);
        updateBySQL(str1, arrayOfObject);
        String str2 = "删除摘要 " + paramString1;
        str1 = "insert into f_log values(sysdate,'info','" + paramString2 + "','" + str2 + "')";
        updateBySQL(str1);
        i = 1;
      }
      catch (DataAccessException localDataAccessException)
      {
        throw localDataAccessException;
      }
    }
    return i;
  }
  
  public Summary getSummaryByNo(String paramString)
  {
    String str = "select * from f_Summary where summaryNo=?";
    Object[] arrayOfObject = { paramString };
    this.logger.debug("sql: " + str);
    this.logger.debug("summaryNo: " + arrayOfObject[0]);
    Object localObject = queryForObject(str, arrayOfObject, new SummaryRowMapper());
    return (Summary)localObject;
  }
  
  public int getSummary(String paramString)
  {
    String str = "select count(*) from f_Summary where summaryNo=?";
    Object[] arrayOfObject = { paramString };
    this.logger.debug("sql: " + str);
    this.logger.debug("summaryNo: " + arrayOfObject[0]);
    return queryForInt(str, arrayOfObject);
  }
  
  public List getSummarys(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select SummaryNo,Summary,(select name from F_LedgerField where code=LedgerItem) LedgerItem,FundDCFlag,nvl(AccountCodeOpp,'') AccountCodeOpp,AppendAccount from f_Summary where SUBSTRB(SummaryNo,1,1) in (select moduleid from M_trademodule where Enabled='Y')";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    List localList = queryBySQL(str, arrayOfObject, paramPageInfo, new SummaryRowMapper());
    return localList;
  }
  
  public void createVoucher(Voucher paramVoucher)
  {
    try
    {
      String str1 = SysData.getConfig("systemType");
      long l;
      if ("awmts".equals(str1))
      {
        l = queryForLong("select nvl(max(serial_no),0)+1 from last_serial");
        updateBySQL("insert into last_serial(serial_no) values(" + l + ")");
      }
      else
      {
        l = queryForLong("select seq_f_voucher.Nextval from dual");
      }
      paramVoucher.setVoucherNo(new Long(l));
      String str2 = "insert into f_Voucher( summaryNo, summary, voucherno, note, inputuser, inputtime, auditor, audittime, auditnote, status,contractNo) values(?,?,?,?,?,?,?,?,?,?,?)";
      Object[] arrayOfObject = { paramVoucher.getSummaryNo(), paramVoucher.getSummary(), paramVoucher.getVoucherNo(), paramVoucher.getNote(), paramVoucher.getInputUser(), paramVoucher.getInputTime(), paramVoucher.getAuditor(), paramVoucher.getAuditTime(), paramVoucher.getAuditNote(), paramVoucher.getStatus(), paramVoucher.getContractNo() };
      int[] arrayOfInt = { 1, 12, -5, 12, 12, 93, 12, 93, 12, 12, 12 };
      this.logger.debug("sql: " + str2);
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
      updateBySQL(str2, arrayOfObject, arrayOfInt);
      List localList = paramVoucher.getVoucherEntrys();
      if ((localList != null) && (localList.size() > 0)) {
        for (int j = 0; j < localList.size(); j++)
        {
          VoucherEntry localVoucherEntry = (VoucherEntry)localList.get(j);
          localVoucherEntry.setVoucherNo(new Long(l));
          insertVoucherEntry(localVoucherEntry);
        }
      }
    }
    catch (DataAccessException localDataAccessException)
    {
      throw localDataAccessException;
    }
  }
  
  public void deleteVoucher(Long paramLong, String paramString)
  {
    try
    {
      String str1 = "delete from f_Voucher where voucherNo=? and status='editing'";
      Object[] arrayOfObject = { paramLong };
      this.logger.debug("sql: " + str1);
      this.logger.debug("voucherNo: " + arrayOfObject[0]);
      int i = updateBySQL(str1, arrayOfObject);
      if (i > 0)
      {
        str2 = "delete from f_VoucherEntry where voucherNo=?";
        this.logger.debug("entrySql: " + str1);
        this.logger.debug("voucherNo: " + arrayOfObject[0]);
        updateBySQL(str2, arrayOfObject);
      }
      String str2 = "删除凭证 " + paramLong;
      str1 = "insert into f_log values(sysdate,'info','" + paramString + "','" + str2 + "')";
      updateBySQL(str1);
    }
    catch (DataAccessException localDataAccessException)
    {
      throw localDataAccessException;
    }
  }
  
  public Voucher getVoucherByNo(Long paramLong)
  {
    String str = "select * from f_Voucher where voucherNo=?";
    Object[] arrayOfObject = { paramLong };
    this.logger.debug("sql: " + str);
    this.logger.debug("voucherNo: " + arrayOfObject[0]);
    Object localObject = null;
    Voucher localVoucher = null;
    try
    {
      localObject = queryForObject(str, arrayOfObject, new VoucherRowMapper());
      localVoucher = (Voucher)localObject;
      localVoucher.setVoucherEntrys(getVoucherEntrys(paramLong));
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      this.logger.error("voucherNo " + paramLong + " Not exist !");
    }
    return localVoucher;
  }
  
  private List getVoucherEntrys(Long paramLong)
  {
    Object[] arrayOfObject = { paramLong };
    String str = "select a.*,b.name from f_VoucherEntry a,F_Account b where voucherNo=? and a.accountcode=b.code order by entryId";
    this.logger.debug("sql: " + str);
    this.logger.debug("voucherNo: " + arrayOfObject[0]);
    List localList = queryBySQL(str, arrayOfObject, null, new EntryRowMapper());
    return localList;
  }
  
  public List getVouchers(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    String str = "select * from f_Voucher where Status != 'accounted'";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    if (paramString != null) {
      str = str + paramString;
    }
    this.logger.debug("sql: " + str);
    List localList = queryBySQL(str, arrayOfObject, paramPageInfo, new VoucherRowMapper());
    for (int i = 0; i < localList.size(); i++)
    {
      Voucher localVoucher = (Voucher)localList.get(i);
      localVoucher.setVoucherEntrys(getVoucherEntrys(localVoucher.getVoucherNo()));
    }
    return localList;
  }
  
  public void updateVoucherNotEntrys(Voucher paramVoucher)
  {
    String str = "update f_Voucher set summaryNo=?, summary=?, note=?, inputuser=?, inputtime=?, auditor=?, audittime=?, auditnote=?, status=?, contractNo=?  where voucherNo=?";
    Object[] arrayOfObject = { paramVoucher.getSummaryNo(), paramVoucher.getSummary(), paramVoucher.getNote(), paramVoucher.getInputUser(), paramVoucher.getInputTime(), paramVoucher.getAuditor(), paramVoucher.getAuditTime(), paramVoucher.getAuditNote(), paramVoucher.getStatus(), paramVoucher.getContractNo(), paramVoucher.getVoucherNo() };
    int[] arrayOfInt = { 1, 12, 12, 12, 93, 12, 93, 12, 12, 12, 12 };
    this.logger.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void updateVoucher(Voucher paramVoucher)
  {
    try
    {
      updateVoucherNotEntrys(paramVoucher);
      List localList = paramVoucher.getVoucherEntrys();
      String str1 = "";
      for (int i = 0; i < localList.size(); i++)
      {
        VoucherEntry localVoucherEntry1 = (VoucherEntry)localList.get(i);
        if (localVoucherEntry1.getEntryId() != null) {
          str1 = str1 + localVoucherEntry1.getEntryId() + ",";
        }
      }
      String str2 = "delete from f_VoucherEntry where voucherNo=" + paramVoucher.getVoucherNo();
      if (str1.endsWith(","))
      {
        str1 = str1.substring(0, str1.length() - 1);
        str2 = str2 + " and entryId not in (" + str1 + ")";
        this.logger.debug("sql: " + str2);
        updateBySQL(str2);
      }
      else
      {
        updateBySQL(str2);
      }
      for (int j = 0; j < localList.size(); j++)
      {
        VoucherEntry localVoucherEntry2 = (VoucherEntry)localList.get(j);
        if (localVoucherEntry2.getEntryId() != null)
        {
          updateVoucherEntry(localVoucherEntry2);
        }
        else
        {
          localVoucherEntry2.setVoucherNo(paramVoucher.getVoucherNo());
          insertVoucherEntry(localVoucherEntry2);
        }
      }
    }
    catch (DataAccessException localDataAccessException)
    {
      throw localDataAccessException;
    }
  }
  
  private void updateVoucherEntry(VoucherEntry paramVoucherEntry)
  {
    String str = "update f_VoucherEntry set entrysummary=?, accountcode=?, debitamount=?, creditamount=? where entryId=?";
    Object[] arrayOfObject = { paramVoucherEntry.getEntrySummary(), paramVoucherEntry.getAccountCode(), paramVoucherEntry.getDebitAmount(), paramVoucherEntry.getCreditAmount(), paramVoucherEntry.getEntryId() };
    this.logger.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    updateBySQL(str, arrayOfObject);
  }
  
  private void insertVoucherEntry(VoucherEntry paramVoucherEntry)
  {
    long l = queryForLong("select seq_F_voucherEntry.Nextval from dual");
    paramVoucherEntry.setEntryId(new Long(l));
    String str = "insert into f_VoucherEntry(entryid, entrysummary, voucherno, accountcode, debitamount, creditamount) values(?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramVoucherEntry.getEntryId(), paramVoucherEntry.getEntrySummary(), paramVoucherEntry.getVoucherNo(), paramVoucherEntry.getAccountCode(), paramVoucherEntry.getDebitAmount(), paramVoucherEntry.getCreditAmount() };
    this.logger.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    updateBySQL(str, arrayOfObject);
  }
  
  public int setVoucherBDate(String paramString1, String paramString2, String paramString3)
  {
    int i = -1;
    try
    {
      String str1 = "FN_F_SetVoucherBDate(to_date('" + paramString1 + " 23:59:59','yyyy-MM-dd hh24:mi:ss'),to_date('" + paramString2 + "','yyyy-MM-dd'))";
      try
      {
        i = callStoredProcedure(str1);
      }
      catch (Exception localException)
      {
        this.logger.error("Call Stored Procedure Failed!" + localException.getMessage());
        i = -1;
        throw new RuntimeException(localException);
      }
      String str2 = "";
      if (!paramString1.equals(paramString2)) {
        str2 = "设置结算日期将从" + paramString1 + "到" + paramString2 + "凭证设置结算日期为" + paramString2;
      } else {
        str2 = "设置结算日期将" + paramString1 + "的凭证设置结算日期为" + paramString2;
      }
      str1 = "insert into f_log values(sysdate,'info','" + paramString3 + "','" + str2 + "')";
      updateBySQL(str1);
    }
    catch (DataAccessException localDataAccessException)
    {
      i = -1;
      throw localDataAccessException;
    }
    return i;
  }
  
  public int balanceVoucher(String paramString1, String paramString2)
  {
    int i = 0;
    try
    {
      String str1;
      if (paramString1 != null) {
        str1 = "FN_F_Balance(to_date('" + paramString1 + "','yyyy-MM-dd'))";
      } else {
        str1 = "FN_F_Balance()";
      }
      i = callStoredProcedure(str1);
      String str2 = "{call " + str1 + " }";
      this.logger.debug(str2);
      if (i == 1)
      {
        String str3 = "结算完成 ";
        str2 = "insert into f_log values(sysdate,'info','" + paramString2 + "','" + str3 + "')";
        updateBySQL(str2);
      }
    }
    catch (Exception localException)
    {
      this.logger.error(localException);
      i = 0;
    }
    return i;
  }
  
  public Channel getChannelByCode(String paramString)
  {
    String str = "select * from f_vouchermodel where code=?";
    Object[] arrayOfObject = { paramString };
    Object localObject = queryForObject(str, arrayOfObject, new channelRowMapper());
    return (Channel)localObject;
  }
  
  class channelRowMapper
    implements RowMapper
  {
    channelRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToUser(paramResultSet);
    }
    
    private Channel rsToUser(ResultSet paramResultSet)
      throws SQLException
    {
      Channel localChannel = new Channel();
      localChannel.setCode(paramResultSet.getString("code"));
      localChannel.setName(paramResultSet.getString("name"));
      localChannel.setSummaryNo(paramResultSet.getString("summaryNo"));
      localChannel.setDebitCode(paramResultSet.getString("debitCode"));
      localChannel.setCreditCode(paramResultSet.getString("creditCode"));
      localChannel.setNeedContractNo(paramResultSet.getString("needContractNo"));
      localChannel.setNote(paramResultSet.getString("note"));
      return localChannel;
    }
  }
  
  class EntryRowMapper
    implements RowMapper
  {
    EntryRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToVoucherEntry(paramResultSet);
    }
    
    private VoucherEntry rsToVoucherEntry(ResultSet paramResultSet)
      throws SQLException
    {
      VoucherEntry localVoucherEntry = new VoucherEntry();
      localVoucherEntry.setEntryId(new Long(paramResultSet.getLong("entryId")));
      localVoucherEntry.setEntrySummary(paramResultSet.getString("entrySummary"));
      localVoucherEntry.setVoucherNo(new Long(paramResultSet.getLong("voucherNo")));
      localVoucherEntry.setAccountCode(paramResultSet.getString("accountCode"));
      localVoucherEntry.setAccountName(paramResultSet.getString("name"));
      localVoucherEntry.setDebitAmount(paramResultSet.getBigDecimal("debitAmount"));
      localVoucherEntry.setCreditAmount(paramResultSet.getBigDecimal("creditAmount"));
      return localVoucherEntry;
    }
  }
  
  class VoucherRowMapper
    implements RowMapper
  {
    VoucherRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToVoucher(paramResultSet);
    }
    
    private Voucher rsToVoucher(ResultSet paramResultSet)
      throws SQLException
    {
      Voucher localVoucher = new Voucher();
      localVoucher.setVoucherDate(paramResultSet.getDate("b_date"));
      localVoucher.setSummaryNo(paramResultSet.getString("summaryNo"));
      localVoucher.setSummary(paramResultSet.getString("summary"));
      localVoucher.setVoucherNo(new Long(paramResultSet.getLong("voucherNo")));
      localVoucher.setNote(paramResultSet.getString("Note"));
      localVoucher.setInputUser(paramResultSet.getString("inputUser"));
      localVoucher.setInputTime(paramResultSet.getTimestamp("inputTime"));
      localVoucher.setAuditor(paramResultSet.getString("auditor"));
      localVoucher.setAuditTime(paramResultSet.getTimestamp("auditTime"));
      localVoucher.setAuditNote(paramResultSet.getString("auditNote"));
      localVoucher.setStatus(paramResultSet.getString("Status"));
      localVoucher.setContractNo(paramResultSet.getString("contractNo"));
      localVoucher.setFundFlowID(paramResultSet.getDouble("fundFlowID"));
      return localVoucher;
    }
  }
  
  class SummaryRowMapper
    implements RowMapper
  {
    SummaryRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToSummary(paramResultSet);
    }
    
    private Summary rsToSummary(ResultSet paramResultSet)
      throws SQLException
    {
      Summary localSummary = new Summary();
      localSummary.setSummaryNo(paramResultSet.getString("summaryNo"));
      localSummary.setSummary(paramResultSet.getString("summary"));
      localSummary.setLedgerItem(paramResultSet.getString("ledgerItem"));
      localSummary.setFundDCFlag(paramResultSet.getString("fundDCFlag"));
      localSummary.setAccountCodeOpp(paramResultSet.getString("accountCodeOpp"));
      localSummary.setAppendAccount(paramResultSet.getString("appendAccount"));
      return localSummary;
    }
  }
}
