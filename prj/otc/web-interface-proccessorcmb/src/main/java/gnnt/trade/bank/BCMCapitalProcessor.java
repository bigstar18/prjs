package gnnt.trade.bank;

import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.ReturnValue;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

public class BCMCapitalProcessor
  extends CapitalProcessor
{
  private static BankDAO DAO;
  
  public BCMCapitalProcessor()
  {
    try
    {
      DAO = BankDAOFactory.getDAO();
    }
    catch (Exception e)
    {
      log("初始化处理器失败：" + Tool.getExceptionTrace(e));
    }
  }
  
  public void log(String content)
  {
    Logger plog = Logger.getLogger("Processorlog");
    plog.debug(content);
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
        log("===========================>fv.firmName=[" + fv.firmName + "];cv.accountName=[" + cv.accountName + "]");
        log(fv.firmName.equals(cv.accountName));
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
}
