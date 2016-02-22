package gnnt.trade.bank.data.cmbc;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.CapitalProcessor;
import gnnt.trade.bank.dao.CMBCBankDAO;
import gnnt.trade.bank.data.cib.vo.CIBConstant;
import gnnt.trade.bank.data.cmbc.vo.CMBCConstant;
import gnnt.trade.bank.data.cmbc.vo.Sbala;
import gnnt.trade.bank.data.cmbc.vo.Sbusi;
import gnnt.trade.bank.data.cmbc.vo.Spay;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;

public class CMBCDataImpl
{
  private BankAdapterRMI bankadapter;
  private CMBCBankDAO DAO;
  private Connection conn = null;
  
  private void closeStatement(ResultSet rs, Statement state, Connection conn)
  {
    try
    {
      if (rs != null) {
        rs.close();
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    try
    {
      if (state != null) {
        state.close();
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    try
    {
      if (conn != null) {
        conn.close();
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
  }
  
  private void log(String content)
  {
    Logger plog = Logger.getLogger("Processorlog");
    plog.debug(content);
  }
  
  public ReturnValue send(Date tradeDate)
  {
    ReturnValue result = new ReturnValue();
    try
    {
      for (;;)
      {
        try
        {
          boolean flag = this.DAO.getTradeDate(tradeDate);
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
      Vector<FirmBalance> vfb = this.DAO.getFirmBalanceValue(tradeDate, tradeDate, CMBCConstant.bankID);
      BankAdapterRMI adapter = getAdapter();
      if (adapter == null)
      {
        result.result = -30011L;
        log("发送民生银行清算数据，连接适配器异常，错误码：" + result.result);
        return result;
      }
      List<Sbusi> sbusis = new ArrayList();
      List<Sbala> sbalas = new ArrayList();
      List<Spay> spays = new ArrayList();
      double QYHZ = 0.0D;
      double FeeHZ = 0.0D;
      for (int i = 0; i < vfb.size(); i++)
      {
        Sbusi sbusi = new Sbusi();
        sbusi.setMoney(((FirmBalance)vfb.get(i)).QYChangeMoney - ((FirmBalance)vfb.get(i)).FeeMoney);
        sbusi.setsCustAcct(((FirmBalance)vfb.get(i)).firmID);
        sbusi.setbCustAcct(((FirmBalance)vfb.get(i)).account);
        sbusi.setMoneyType("0");
        if (sbusi.getMoney() != 0.0D) {
          sbusis.add(sbusi);
        }
        Sbala sbala = new Sbala();
        sbala.setbCustAcct(((FirmBalance)vfb.get(i)).account);
        sbala.setsCustAcct(((FirmBalance)vfb.get(i)).firmID);
        sbala.setMoney(((FirmBalance)vfb.get(i)).QYMoney);
        sbala.setMoneyType("0");
        sbalas.add(sbala);
        
        QYHZ += ((FirmBalance)vfb.get(i)).QYChangeMoney;
        FeeHZ += ((FirmBalance)vfb.get(i)).FeeMoney;
      }
      if (QYHZ != 0.0D)
      {
        Spay spay = new Spay();
        spay.setMoney(QYHZ);
        spay.setBusinCode("O");
        spay.setMoneyType("0");
        spays.add(spay);
      }
      if (FeeHZ != 0.0D)
      {
        Spay spay = new Spay();
        spay.setMoney(FeeHZ * -1.0D);
        spay.setBusinCode("C");
        spay.setMoneyType("0");
        spays.add(spay);
      }
      result = adapter.sendCMBCQSValue(sbusis, sbalas, 
        spays, null, tradeDate);
    }
    catch (RemoteException e)
    {
      log("发送清算出现异常！");
      result.result = -1L;
      result.remark = "发送清算出现异常";
      return result;
    }
    return result;
  }
  
  private BankAdapterRMI getAdapter()
  {
    return new CapitalProcessor().getAdapter(CIBConstant.bankID);
  }
}
