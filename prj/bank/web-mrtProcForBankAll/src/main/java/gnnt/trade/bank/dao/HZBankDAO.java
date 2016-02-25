package gnnt.trade.bank.dao;

import gnnt.trade.bank.vo.TransferInfo;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public abstract class HZBankDAO
  extends BankDAOImpl
{
  public HZBankDAO()
    throws Exception
  {}
  
  public abstract int addBankQS(BankQSVO paramBankQSVO, Connection paramConnection)
    throws SQLException;
  
  public abstract RZQSValue getXYQSValue(String paramString, String[] paramArrayOfString, Date paramDate)
    throws SQLException, ClassNotFoundException;
  
  public abstract RZDZValue getXYDZValue(String paramString, String[] paramArrayOfString, Date paramDate)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addTransfer(TransferInfo paramTransferInfo)
    throws SQLException, ClassNotFoundException;
}
