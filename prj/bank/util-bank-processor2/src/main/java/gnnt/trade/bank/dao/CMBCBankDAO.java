package gnnt.trade.bank.dao;

import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import java.util.Date;
import java.util.Vector;

public abstract class CMBCBankDAO
  extends BankDAOImpl
{
  public CMBCBankDAO()
    throws Exception
  {}
  
  public abstract Vector<FirmBalance> getFirmBalanceValue(Date paramDate1, Date paramDate2, String paramString);
}
