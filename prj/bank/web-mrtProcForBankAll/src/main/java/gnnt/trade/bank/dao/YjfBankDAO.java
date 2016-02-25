package gnnt.trade.bank.dao;

import gnnt.trade.bank.vo.bankdz.BankQSVO;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public abstract class YjfBankDAO
  extends BankDAOImpl
{
  public int[] pageinfo = new int[4];
  
  public YjfBankDAO()
    throws Exception
  {}
  
  public abstract Vector<BankQSVO> getBankQSDate(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addBankQS(BankQSVO paramBankQSVO, Connection paramConnection)
    throws SQLException;
  
  public abstract int modBankQS(BankQSVO paramBankQSVO, Connection paramConnection)
    throws SQLException;
  
  public abstract RZQSValue getXYQSValue(String paramString, String[] paramArrayOfString, Date paramDate)
    throws SQLException, ClassNotFoundException;
  
  public abstract Date getMaxBankQSSuccessDate(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException;
  
  public abstract Date getlastDate(Date paramDate, Connection paramConnection)
    throws SQLException;
  
  public abstract RZDZValue getXYDZValue(String paramString, String[] paramArrayOfString, Date paramDate)
    throws SQLException, ClassNotFoundException;
}
