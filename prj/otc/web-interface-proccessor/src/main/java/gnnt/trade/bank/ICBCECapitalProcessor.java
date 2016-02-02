package gnnt.trade.bank;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.icbce.ICBCEBankDAO;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfoValue;
import java.sql.Connection;
import java.sql.SQLException;

public class ICBCECapitalProcessor
  extends CapitalProcessor
{
  private static ICBCEBankDAO DAO;
  
  public ICBCECapitalProcessor()
  {
    try
    {
      DAO = BankDAOFactory.getICBCEDAO();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
    }
    catch (InstantiationException e)
    {
      e.printStackTrace();
    }
  }
  
  public ReturnValue inMoneyMarket(InMoneyMarket imm)
  {
    log("市场入金 inMoneyMarket imm:" + (imm == null ? "为 null" : new StringBuilder("\n").append(imm.toString()).append("\n").toString()));
    ReturnValue result = new ReturnValue();
    
    Connection conn = null;
    try
    {
      ReturnValue localReturnValue1;
      if (imm.money <= 0.0D)
      {
        result.result = -920019L;
        result.remark = "入金金额必须为正数";
        localReturnValue1 = result;return localReturnValue1;
      }
      conn = DAO.getConnection();
      CorrespondValue cv = getCorrespond(imm.bankID, imm.firmID, imm.contact, imm.account);
      if (cv == null)
      {
        result.result = -920013L;
        result.remark = "未查询到交易账号信息";
        localReturnValue1 = result;return localReturnValue1;
      }
      imm.contact = cv.contact;
      imm.account = cv.account;
      imm.firmID = cv.firmID;
      result = ifbankTrade(imm.bankID, imm.firmID, imm.contact, 0, 0);
      if (result.result != 0L)
      {
        localReturnValue1 = result;return localReturnValue1;
      }
      if (cv.isOpen != 1)
      {
        result.result = -10019L;
        result.remark = "交易账号未签约";
        localReturnValue1 = result;return localReturnValue1;
      }
      if (cv.status != 0)
      {
        result.result = -10020L;
        result.remark = "交易账号不可用";
        localReturnValue1 = result;return localReturnValue1;
      }
      log(imm.toString());
      result.actionId = DAO.getActionID(conn);
      result.result = result.actionId;
      if (result.result <= 0L)
      {
        result.remark = "获取流水号失败";
        localReturnValue1 = result;return localReturnValue1;
      }
      TransferInfoValue payInfo = getPayInfo(imm.bankID, imm.firmID, 0, conn);
      TransferInfoValue receiveInfo = getReceiveInfo(imm.bankID, imm.firmID, 0, conn);
      InMoneyVO inMoneyInfo = new InMoneyVO(imm.bankID, null, imm.money, imm.contact, payInfo, imm.accountPwd, receiveInfo, imm.remark, result.actionId);
      inMoneyInfo.setBankFlag(imm.bankFlag);
      BankAdapterRMI bankadapter = getAdapter(imm.bankID);
      if (bankadapter == null)
      {
        result.result = -920000L;
        result.remark = "网络异常，处理器无法连接适配器";
        log(result.remark);
      }
      else
      {
        result = bankadapter.inMoneyQueryBank(inMoneyInfo);
      }
      if (result.result >= 0L) {
        log("入金订单组建适配器处理成功");
      }
    }
    catch (SQLException e)
    {
      result.result = -10014L;
      result.remark = "数据库连接异常";
      log("市场端发起入金SQLException:" + result + Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      result.result = -10015L;
      result.remark = "系统异常";
      log("市场端发起入金Exception:" + result + Tool.getExceptionTrace(e));
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return result;
  }
}
