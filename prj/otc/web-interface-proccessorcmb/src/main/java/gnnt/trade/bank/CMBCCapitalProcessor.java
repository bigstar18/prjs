package gnnt.trade.bank;

import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.CMBCBankDAO;
import gnnt.trade.bank.util.Common;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class CMBCCapitalProcessor
  extends CapitalProcessor
{
  private static CMBCBankDAO DAO;
  
  public CMBCCapitalProcessor()
  {
    try
    {
      DAO = BankDAOFactory.getCMBCDAO();
    }
    catch (Exception e)
    {
      log("初始化建行DAO对象处理器失败：" + Tool.getExceptionTrace(e));
    }
  }
  
  public Vector<FirmBalance> sendCMBCQS(String bankID, Date tradeDate)
  {
    log("民生清算 适配器调用");
    Date qsDate = tradeDate;
    for (;;)
    {
      qsDate = Common.getLastDay(qsDate);
      try
      {
        boolean flag = DAO.getTradeDate(qsDate);
        if (!flag) {}
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
      }
    }
    Vector<FirmBalance> vfb = DAO.getFirmBalanceValue(tradeDate, qsDate, bankID);
    return vfb;
  }
}
