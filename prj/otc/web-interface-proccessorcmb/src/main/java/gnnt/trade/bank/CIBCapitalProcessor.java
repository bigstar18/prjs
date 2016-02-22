package gnnt.trade.bank;

import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.CIBBankDAO;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmInfo;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.FFHDValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.ZFPHValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.child.FirmDateValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class CIBCapitalProcessor
  extends CapitalProcessor
{
  private static final int INMONEY = 0;
  private static final int OUTMONEY = 1;
  private static final int RATE = 2;
  private static CIBBankDAO DAO;
  
  public CIBCapitalProcessor()
  {
    try
    {
      DAO = BankDAOFactory.getZXDAO();
    }
    catch (Exception e)
    {
      log("初始化DAO对象处理器失败：" + Tool.getExceptionTrace(e));
    }
  }
  
  public String getConfig(String key)
  {
    return Tool.getConfig(key);
  }
  
  public ReturnValue saveZFPH(ZFPHValue zfph)
  {
    ReturnValue result = new ReturnValue();
    Connection conn = null;
    try
    {
      conn = DAO.getConnection();
      try
      {
        conn.setAutoCommit(false);
        if ((zfph.bankID != null) && (zfph.bankID.trim().length() > 0) && (zfph.tradeDate != null))
        {
          DAO.delZFPH(zfph.bankID, zfph.tradeDate, zfph.result, conn);
          DAO.addZFPH(zfph, conn);
          result.remark = ("添加银行[" + zfph.bankID + "][" + Tool.fmtDate(zfph.tradeDate) + "]总分平衡信息成功");
        }
        else
        {
          result.result = -1L;
          result.remark = "写入总分平衡监管信息，传来数据不完整";
          log(zfph.toString());
        }
        conn.commit();
      }
      catch (SQLException e)
      {
        conn.rollback();
        result.result = -1L;
        result.remark = "添加总分平衡时数据库异常";
        e.printStackTrace();
      }
      finally
      {
        conn.setAutoCommit(true);
      }
    }
    catch (SQLException e)
    {
      result.result = -1L;
      result.remark = "添加总分平衡取得数据库连接时数据库异常";
      e.printStackTrace();
    }
    catch (Exception e)
    {
      result.result = -2L;
      result.remark = "添加总分平衡时系统异常";
      e.printStackTrace();
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return result;
  }
  
  public ReturnValue saveFFHD(FFHDValue ffhd)
  {
    ReturnValue result = new ReturnValue();
    Connection conn = null;
    try
    {
      conn = DAO.getConnection();
      try
      {
        conn.setAutoCommit(false);
        if ((ffhd != null) && (ffhd.getFdv() != null) && (ffhd.getFdv().size() > 0))
        {
          FirmDateValue fdv = (FirmDateValue)ffhd.getFdv().get(0);
          if (fdv != null)
          {
            DAO.delFFHD(fdv.bankID, null, fdv.tradeDate, conn);
            DAO.addFFHD(ffhd, conn);
          }
        }
        result.remark = "写入分分核对信息成功";
        conn.commit();
      }
      catch (SQLException e)
      {
        conn.rollback();
        result.result = -1L;
        result.remark = "写入分分核对信息，数据库异常";
        e.printStackTrace();
      }
      finally
      {
        conn.setAutoCommit(true);
      }
    }
    catch (SQLException e)
    {
      result.result = -1L;
      result.remark = "传入分分核对信息，数据库异常";
      e.printStackTrace();
    }
    catch (Exception e)
    {
      result.result = -1L;
      result.remark = "传入分分核对信息，系统异常";
      e.printStackTrace();
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return result;
  }
  
  public RZQSValue getXYQSValue(String bankID, String[] firmIDs, Date tradeDate)
  {
    RZQSValue result = null;
    Connection conn = null;
    try
    {
      conn = DAO.getConnection();
      result = DAO.getXYQSValue(bankID, firmIDs, tradeDate);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    if (result == null) {
      result = new RZQSValue();
    }
    return result;
  }
  
  public RZDZValue getXYDZValue(String bankID, String[] firmIDs, Date tradeDate)
  {
    RZDZValue result = null;
    Connection conn = null;
    try
    {
      result = DAO.getXYDZValue(bankID, firmIDs, tradeDate);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    if (result == null) {
      result = new RZDZValue();
    }
    return result;
  }
  
  public ReturnValue checkSigning(CorrespondValue cv)
  {
    ReturnValue returnValue = new ReturnValue();
    returnValue = ifbankTrade(cv.bankID, cv.firmID, cv.contact, 3, 1);
    if (returnValue.result < 0L) {
      return returnValue;
    }
    Connection conn = null;
    if ((cv.bankID == null) || (cv.bankID.length() == 0) || 
      (cv.contact == null) || (cv.contact.length() == 0))
    {
      returnValue.result = -40001L;
      return returnValue;
    }
    if ((cv.firmID == null) || (cv.firmID.trim().length() <= 0)) {
      cv.firmID = getfirmid(cv.contact);
    }
    try
    {
      conn = DAO.getConnection();
      FirmValue fv = DAO.getFirm(cv.firmID, conn);
      if (fv == null)
      {
        returnValue.result = -40002L;
        log("银行账号注册，交易账号不存在，错误码=" + returnValue.result);
      }
      else if (DAO.getBank(cv.bankID, conn) == null)
      {
        returnValue.result = -40003L;
        log("银行账号注册，银行不存在，错误码=" + returnValue.result);
      }
      else if (DAO.getCorrespondList(" and bankID='" + cv.bankID + "' and firmID='" + cv.firmID + "' and isopen=1", conn).size() > 0)
      {
        returnValue.result = -40004L;
        log("银行账号注册，账号已注册，错误码=" + returnValue.result);
      }
      else
      {
        conn.setAutoCommit(false);
        returnValue = dataProcess.changeFirmIsOpen(cv.firmID, 1, cv.bankID, conn);
        ReturnValue localReturnValue1;
        if (returnValue.result < 0L) {
          return returnValue;
        }
        if (!fv.contact.equals(cv.contact))
        {
          returnValue.result = -40002L;
        }
        else if (!fv.firmName.equals(cv.accountName))
        {
          log(fv.firmName + cv.accountName);
          returnValue.result = -40001L;
          returnValue.remark = "开户名称不一致";
        }
        else if (!fv.cardType.equals(cv.cardType))
        {
          log(fv.cardType + cv.cardType);
          returnValue.result = -40001L;
          returnValue.remark = "证件类型不一致";
        }
        else if (!fv.card.equals(cv.card))
        {
          log(fv.card + cv.card);
          returnValue.result = -40001L;
          returnValue.remark = "证件号码不一致";
        }
        else
        {
          returnValue.result = 0L;
        }
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
  
  public int modfirmuser(FirmValue value, String bankid)
  {
    log("修改客户信息表以及客户和银行对应表的数据库\n" + value.toString());
    int result = -1;
    try
    {
      Vector<FirmInfo> finfos = DAO.getFirmInfo(value.firmID, value.firminfo.bankid, "CIBAc");
      if (finfos.size() <= 0)
      {
        log("字段扩展表内不存在该交易商或银行的数据：新增一条数据\n");
        result = DAO.insertFirmInfo(value, "CIBAc");
      }
      else
      {
        log("字段扩展表内存在该交易商或银行的数据：修改本条数据\n");
        result = DAO.modfirmuser(value, "CIBAc");
      }
    }
    catch (Exception e)
    {
      log(Tool.getExceptionTrace(e));
    }
    return result;
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
}
