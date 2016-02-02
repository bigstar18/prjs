package gnnt.trade.bank;

import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.CCBBankDAO;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmInfo;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.ReturnValue;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class CCBCapitalProcessor
  extends CapitalProcessor
{
  private static CCBBankDAO DAO;
  
  public CCBCapitalProcessor()
  {
    try
    {
      DAO = BankDAOFactory.getCCBDAO();
    }
    catch (Exception e)
    {
      log("初始化建行DAO对象处理器失败：" + Tool.getExceptionTrace(e));
    }
  }
  
  public String getfirmid(String contact)
  {
    log("通过contact[" + contact + "]获取firmid");
    String firmids = null;
    Vector<FirmValue> firmivalues = null;
    try
    {
      firmivalues = DAO.getFirmList("and contact ='" + contact + "'");
    }
    catch (SQLException e)
    {
      log(Tool.getExceptionTrace(e));
    }
    catch (ClassNotFoundException e)
    {
      log(Tool.getExceptionTrace(e));
    }
    if ((firmivalues != null) && (firmivalues.size() > 0)) {
      firmids = ((FirmValue)firmivalues.get(0)).firmID;
    }
    log("获得的Firmid[" + firmids + "]");
    return firmids;
  }
  
  public ReturnValue ifQuitFirm(String firmID, String bankID)
  {
    ReturnValue rv = new ReturnValue();
    String truefirmis = getfirmid(firmID);
    if (truefirmis == null)
    {
      rv.result = -920013L;
      return rv;
    }
    rv = ifbankTrade(bankID, truefirmis, null, 3, 0);
    if (rv.result != 0L)
    {
      log("验证失败，失败原因：" + rv.result + "失败说明：" + rv.remark);
      return rv;
    }
    Connection conn = null;
    try
    {
      conn = DAO.getConnection();
      Vector<CapitalValue> capitals = DAO.getCapitalInfoList(" and bankID='" + bankID + "' and contact='" + firmID + "' and createtime>=trunc(sysdate) and createtime<trunc(sysdate)+1  ", conn);
      if ((capitals != null) && (capitals.size() > 0))
      {
        rv.result = -941002L;
        log("验证失败，失败原因：当日有转账交易不能解约");
        return rv;
      }
    }
    catch (SQLException e1)
    {
      e1.printStackTrace();
      log("验证失败，失败原因：SQLException异常");
      rv.result = -1L;
      return rv;
    }
    catch (ClassNotFoundException e1)
    {
      e1.printStackTrace();
      log("验证失败，失败原因：ClassNotFoundException异常");
      rv.result = -1L;
      return rv;
    }
    try
    {
      conn.setAutoCommit(false);
      rv.result = dataProcess.ifFirmDelAccount(truefirmis, bankID, conn);
      if (rv.result < 0L)
      {
        rv.remark = "账户未终止";
        return rv;
      }
    }
    catch (Exception e1)
    {
      log("判断账号解约异常=" + Tool.getExceptionTrace(e1));
    }
    finally
    {
      try
      {
        conn.rollback();
      }
      catch (SQLException e)
      {
        log("判断账号解约,数据回滚异常=" + Tool.getExceptionTrace(e));
      }
      DAO.closeStatement(null, null, conn);
    }
    try
    {
      conn.rollback();
    }
    catch (SQLException e)
    {
      log("判断账号解约,数据回滚异常=" + Tool.getExceptionTrace(e));
    }
    DAO.closeStatement(null, null, conn);
    
    return rv;
  }
  
  public ReturnValue modCapitalInfoStatus(long id, String funID, int status, Timestamp bankTime)
  {
    ReturnValue rv = new ReturnValue();
    Connection conn = null;
    try
    {
      conn = DAO.getConnection();
      if (bankTime == null) {
        bankTime = new Timestamp(System.currentTimeMillis());
      }
      DAO.modCapitalInfoStatus(id, funID, status, bankTime, conn);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      rv.result = -1L;
      rv.remark = ("修改 " + id + " 银行流水号 " + funID + " 数据库异常");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      rv.result = -1L;
      rv.remark = ("修改 " + id + " 银行流水号 " + funID + " 系统异常");
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return rv;
  }
  
  public Map<String, CapitalValue> getCapitalValue(Vector<String> funid, String bankID)
  {
    Map<String, CapitalValue> result = new HashMap();
    Connection conn = null;
    try
    {
      conn = DAO.getConnection();
      if ((funid != null) && (funid.size() > 0)) {
        for (String contact : funid)
        {
          Vector<CapitalValue> capitalList = DAO.getCapitalInfoList(" and funid='" + contact + "' and bankid='" + bankID + "' ", conn);
          if ((capitalList != null) && (capitalList.size() > 0))
          {
            CapitalValue cv = (CapitalValue)capitalList.get(0);
            result.put(contact, cv);
          }
        }
      }
    }
    catch (Exception e)
    {
      log(Tool.getExceptionTrace(e));
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return result;
  }
  
  public long chenckCorrespondValue(CorrespondValue cv)
  {
    log("银行开户销户变更账户检验参数，" + (cv == null ? "传入参数为空，" : new StringBuilder("\n").append(cv.toString()).append("\n").toString()) + "时间：" + Tool.getNowStr());
    long checkResult = -920012L;
    String bankid = cv.bankID;
    String contact = cv.firmID;
    String account = cv.account;
    if ((cv.contact != null) && (cv.contact.trim().length() > 0)) {
      contact = cv.contact;
    }
    if ((bankid == null) || (bankid.trim().length() <= 0))
    {
      checkResult = -920011L;
      return checkResult;
    }
    if ((contact == null) || (contact.trim().length() <= 0))
    {
      checkResult = -920010L;
      return checkResult;
    }
    if ((account == null) || (account.trim().length() <= 0))
    {
      checkResult = -920012L;
      return checkResult;
    }
    try
    {
      List<CorrespondValue> cvresult = DAO.getCorrespondList(" and bankId='" + bankid + "' and CONTACT='" + contact + "' and (account like '%" + account.trim() + "%' or account='" + Tool.getConfig("DefaultAccount") + "')");
      if ((cvresult == null) || (cvresult.size() <= 0)) {
        return -920013L;
      }
      if (cvresult.size() > 1) {
        return -920014L;
      }
      checkResult = 0L;
    }
    catch (SQLException e)
    {
      checkResult = -920015L;
      log("交易账号代码与银行账号对应SQLException," + Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      checkResult = -920016L;
      log("交易账号代码与银行账号对应Exception," + Tool.getExceptionTrace(e));
    }
    return checkResult;
  }
  
  public ReturnValue checkSigning(FirmValue value)
  {
    ReturnValue returnValue = new ReturnValue();
    returnValue = ifbankTrade(value.firminfo.bankid, value.firmID, value.contact, 3, 1);
    log("预签约查询" + returnValue.result + "____" + returnValue.remark);
    if (returnValue.result < 0L) {
      return returnValue;
    }
    Connection conn = null;
    if ((value.firminfo.bankid == null) || (value.firminfo.bankid.length() == 0) || 
      (value.firmID == null) || (value.firmID.length() == 0))
    {
      returnValue.result = -40001L;
      return returnValue;
    }
    try
    {
      conn = DAO.getConnection();
      FirmValue fv = DAO.getFirm(value.firmID, conn);
      if (fv == null)
      {
        returnValue.result = -40002L;
        log("银行账号注册，交易账号不存在，错误码=" + returnValue.result);
      }
      else if (DAO.getBank(value.firminfo.bankid, conn) == null)
      {
        returnValue.result = -40003L;
        log("银行账号注册，银行不存在，错误码=" + returnValue.result);
      }
      else if (DAO.getCorrespondList(" and bankID='" + value.firminfo.bankid + "' and firmID='" + value.firmID + "' and isopen=1", conn).size() > 0)
      {
        returnValue.result = -40004L;
        log("银行账号注册，账号已注册，错误码=" + returnValue.result);
      }
      else
      {
        conn.setAutoCommit(false);
        returnValue = dataProcess.changeFirmIsOpen(value.firmID, 1, value.firminfo.bankid, conn);
        ReturnValue localReturnValue1;
        if (returnValue.result < 0L) {
          return returnValue;
        }
        returnValue.result = 0L;
        
        return returnValue;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      returnValue.result = -40006L;
      log("银行账号注册SQLException，错误码=" + returnValue.result + "  " + Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      e.printStackTrace();
      returnValue.result = -40007L;
      log("银行账号注册Exception，错误码=" + returnValue.result + "  " + Tool.getExceptionTrace(e));
    }
    finally
    {
      try
      {
        conn.rollback();
        conn.setAutoCommit(true);
      }
      catch (Exception e)
      {
        log(Tool.getExceptionTrace(e));
      }
      DAO.closeStatement(null, null, conn);
    }
    try
    {
      conn.rollback();
      conn.setAutoCommit(true);
    }
    catch (Exception e)
    {
      log(Tool.getExceptionTrace(e));
    }
    DAO.closeStatement(null, null, conn);
    
    return returnValue;
  }
  
  public int[] getPageinfo()
  {
    int[] pageinfo = new int[4];
    try
    {
      pageinfo = DAO.pageinfo;
    }
    catch (Exception e)
    {
      log(Tool.getExceptionTrace(e));
    }
    return pageinfo;
  }
  
  public Vector<CorrespondValue> getCorrespondList(String filter)
  {
    Vector<CorrespondValue> result = new Vector();
    try
    {
      result = DAO.getCorrespondList(filter);
    }
    catch (Exception e)
    {
      log(Tool.getExceptionTrace(e));
    }
    return result;
  }
  
  public Vector<FirmValue> getFirmUserList(String filter, int[] pageinfo, String type, String key, String bankid)
  {
    log("查询客户信息表以及客户和银行对应表的数据\n" + filter);
    Vector<FirmValue> result = null;
    try
    {
      DAO.pageinfo = pageinfo;
      result = DAO.getFirmList3(filter, type, key, bankid);
    }
    catch (Exception e)
    {
      log(Tool.getExceptionTrace(e));
      result = new Vector();
    }
    return result;
  }
  
  public int modfirmuser(FirmValue value, String bankid)
  {
    log("修改客户信息表以及客户和银行对应表的数据库\n" + value.toString());
    int result = -1;
    try
    {
      Vector<FirmInfo> finfos = DAO.getFirmInfo(value.firmID, value.firminfo.bankid, "CCBAc");
      if (finfos.size() <= 0)
      {
        log("字段扩展表内不存在该交易商或银行的数据：新增一条数据\n");
        result = DAO.insertFirmInfo(value, "CCBAc");
      }
      else
      {
        log("字段扩展表内存在该交易商或银行的数据：修改本条数据\n");
        result = DAO.modfirmuser(value, "CCBAc");
      }
    }
    catch (Exception e)
    {
      log(Tool.getExceptionTrace(e));
    }
    return result;
  }
  
  public FirmValue getFirmValue(FirmValue value)
  {
    FirmValue firmers = new FirmValue();
    try
    {
      firmers = DAO.getFirmValue(value);
    }
    catch (Exception e)
    {
      log(Tool.getExceptionTrace(e));
    }
    return firmers;
  }
  
  public int updateFirmInfo(String firmid, String bankid, String key, String value)
  {
    log("修改扩展表firmid[" + firmid + "]bankid[" + bankid + "]key[" + key + "]value[" + value + "]");
    int reulst = 0;
    try
    {
      reulst = DAO.updateFirmInfo(firmid, bankid, key, value);
    }
    catch (Exception e)
    {
      log(Tool.getExceptionTrace(e));
      reulst = -1;
    }
    return reulst;
  }
}
