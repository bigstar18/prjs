package gnnt.bank.adapter.util;

import gnnt.bank.adapter.Factory;
import gnnt.bank.adapter.ICBCBankImpl;
import gnnt.bank.adapter.bankBusiness.cpnt.AccoutBalance;
import gnnt.bank.adapter.bankBusiness.cpnt.Amount;
import gnnt.bank.adapter.bankBusiness.cpnt.BankAccount;
import gnnt.bank.adapter.bankBusiness.cpnt.Customer;
import gnnt.bank.adapter.bankBusiness.cpnt.FuturesAccount;
import gnnt.bank.adapter.bankBusiness.cpnt.GroupHeader;
import gnnt.bank.adapter.bankBusiness.cpnt.Institution;
import gnnt.bank.adapter.bankBusiness.cpnt.ReturnResult;
import gnnt.bank.adapter.bankBusiness.enumElmt.BusinessCode;
import gnnt.bank.adapter.bankBusiness.enumElmt.CurrencyCode;
import gnnt.bank.adapter.bankBusiness.enumElmt.TradeSource;
import gnnt.bank.adapter.bankBusiness.enumElmt.YesNoIndicator;
import gnnt.bank.adapter.bankBusiness.info.BankTransfer;
import gnnt.bank.adapter.bankBusiness.info.BankTransferResponse;
import gnnt.bank.adapter.bankBusiness.info.CheckAccount;
import gnnt.bank.adapter.bankBusiness.info.CheckAccountResponse;
import gnnt.bank.adapter.bankBusiness.info.DayDataReady;
import gnnt.bank.adapter.bankBusiness.info.DayDataReadyResponse;
import gnnt.bank.adapter.bankBusiness.info.DelAccount;
import gnnt.bank.adapter.bankBusiness.info.DelAccountResponse;
import gnnt.bank.adapter.bankBusiness.info.Handshake;
import gnnt.bank.adapter.bankBusiness.info.HandshakeResponse;
import gnnt.bank.adapter.bankBusiness.info.MatketOutMoney;
import gnnt.bank.adapter.bankBusiness.info.MatketOutMoneyResponse;
import gnnt.bank.adapter.bankBusiness.info.ModAccount;
import gnnt.bank.adapter.bankBusiness.info.ModAccountResponse;
import gnnt.bank.adapter.bankBusiness.info.QryAccount;
import gnnt.bank.adapter.bankBusiness.info.QryAccountResponse;
import gnnt.bank.adapter.bankBusiness.info.Reversal;
import gnnt.bank.adapter.bankBusiness.info.ReversalResponse;
import gnnt.bank.adapter.bankBusiness.info.RgstAccount;
import gnnt.bank.adapter.bankBusiness.info.RgstAccountResponse;
import gnnt.bank.adapter.bankBusiness.info.Transfer;
import gnnt.bank.adapter.bankBusiness.info.TransferDetail;
import gnnt.bank.adapter.bankBusiness.info.TransferDetailResponse;
import gnnt.bank.adapter.bankBusiness.info.TransferResponse;
import gnnt.bank.adapter.bankBusiness.info.UnCertain;
import gnnt.bank.adapter.bankBusiness.info.UnCertainResponse;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MACVerify
{
  public static void main(String[] args)
  {
    DayDataReadyResponse obj = new DayDataReadyResponse();
    encryption(obj);
  }

  public static String encryption(Object obj)
  {
    ICBCBankImpl adapter = (ICBCBankImpl)Factory.getInstance().getBankAdapter();
    MAC mac = getMAC(obj);
    System.out.println("加密前：>" + mac.getText() + "<");
    String code = mac.getCode();
    System.out.println("加密后：" + code);
    if (adapter.macAble) {
      return code;
    }
    return "";
  }

  public static boolean verify(Object obj, String code)
  {
    ICBCBankImpl adapter = (ICBCBankImpl)Factory.getInstance().getBankAdapter();
    if (adapter.macAble) {
      MAC mac = getMAC(obj);
      System.out.println("MAC校验结果:" + mac.veryfy(code));

      return true;
    }

    return true;
  }

  public static MAC getMAC(Object obj)
  {
    String objName = obj.getClass().getSimpleName();
    Method method = null;
    try {
      method = MACVerify.class.getMethod("get" + objName + "MAC", new Class[] { obj.getClass() });
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    MAC mac = null;
    try {
      mac = (MAC)method.invoke(MACVerify.class, new Object[] { obj });
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return mac;
  }

  public static MAC getDayDataReadyMAC(DayDataReady obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    if ((obj.BkSeq == null) || (obj.BkSeq.equals("")))
      mac.add(obj.FtSeq);
    else {
      mac.add(obj.BkSeq);
    }

    return mac;
  }

  public static MAC getDayDataReadyResponseMAC(DayDataReadyResponse obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.Rst.Code);
    mac.add(obj.BkSeq);
    mac.add(obj.FtSeq);
    return mac;
  }

  public static MAC getDelAccountMAC(DelAccount obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.BkSeq);
    mac.add(obj.Cust.Name);
    mac.add(obj.BkAcct.Id);
    mac.add(obj.FtAcct.Id);
    return mac;
  }

  public static MAC getDelAccountResponseMAC(DelAccountResponse obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.Rst.Code);
    mac.add(obj.BkSeq);
    mac.add(obj.BkAcct.Id);
    mac.add(obj.FtAcct.Id);
    mac.add(obj.FtSeq);
    return mac;
  }

  public static MAC getHandshakeMAC(Handshake obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.FtSeq);
    if (obj.GrpHdr.BusCd == BusinessCode.CODE20004.getValue()) {
      mac.add(obj.MacKey);
    }
    return mac;
  }

  public static MAC getHandshakeResponseMAC(HandshakeResponse obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.Rst.Code);
    mac.add(obj.BkSeq);
    mac.add(obj.FtSeq);
    return mac;
  }

  public static MAC getModAccountMAC(ModAccount obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.BkSeq);
    mac.add(obj.Cust.Name);
    mac.add(obj.Cust.CertType);
    mac.add(obj.Cust.CertId);
    mac.add(obj.BkAcct.Id);
    mac.add(obj.NewBkAcct.Id);
    mac.add(obj.FtAcct.Id);
    mac.add(obj.Ccy.toString());
    return mac;
  }

  public static MAC getModAccountResponseMAC(ModAccountResponse obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.Rst.Code);
    mac.add(obj.BkSeq);
    mac.add(obj.BkAcct.Id);
    mac.add(obj.FtAcct.Id);
    mac.add(obj.FtSeq);
    return mac;
  }

  public static MAC getQryAccountMAC(QryAccount obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    if (obj.GrpHdr.BusCd == BusinessCode.CODE21004.getValue())
      mac.add(obj.BkSeq);
    else if (obj.GrpHdr.BusCd == BusinessCode.CODE21005.getValue()) {
      mac.add(obj.FtSeq);
    }
    mac.add(obj.Cust.Name);
    mac.add(obj.BkAcct.Id);
    mac.add(obj.FtAcct.Id);
    return mac;
  }

  public static MAC getQryAccountResponseMAC(QryAccountResponse obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.Rst.Code);
    mac.add(obj.BkSeq);
    mac.add(obj.FtSeq);
    if (obj.GrpHdr.BusCd == BusinessCode.CODE21004.getValue()) {
      mac.add(obj.FtAcct.Id);
      mac.add(obj.FtBal.CurBal.amt);
      mac.add(obj.FtBal.UseBal.amt);
      mac.add(obj.FtBal.FtcBal.amt);
    } else if (obj.GrpHdr.BusCd == BusinessCode.CODE21005.getValue()) {
      mac.add(obj.BkAcct.Id);
      mac.add(obj.BkBal.CurBal.amt);
    }
    return mac;
  }

  public static MAC getRgstAccountMAC(RgstAccount obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.BkSeq);
    mac.add(obj.Cust.Name);
    mac.add(obj.Cust.CertType);
    mac.add(obj.Cust.CertId);
    mac.add(obj.BkAcct.Id);
    mac.add(obj.FtAcct.Id);
    mac.add(obj.Ccy.toString());
    return mac;
  }

  public static MAC getRgstAccountResponseMAC(RgstAccountResponse obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.Rst.Code);
    mac.add(obj.BkSeq);
    mac.add(obj.BkAcct.Id);
    mac.add(obj.FtAcct.Id);
    mac.add(obj.FtSeq);
    return mac;
  }

  public static MAC getTransferMAC(Transfer obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    if (obj.GrpHdr.TradSrc.equals(TradeSource.F))
      mac.add(obj.FtSeq);
    else {
      mac.add(obj.BkSeq);
    }
    mac.add(obj.Resend.toString());
    mac.add(obj.Cust.Name);
    mac.add(obj.BkAcct.Id);
    mac.add(obj.FtAcct.Id);
    mac.add(obj.TrfAmt.amt);
    mac.add(obj.CustCharge.amt);
    mac.add(obj.FutureCharge.amt);
    return mac;
  }

  public static MAC getTransferDetailMAC(TransferDetail obj)
  {
    MAC mac = new MAC();
    return mac;
  }

  public static MAC getTransferDetailResponseMAC(TransferDetailResponse obj)
  {
    MAC mac = new MAC();
    return mac;
  }

  public static MAC getTransferResponseMAC(TransferResponse obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.Rst.Code);
    mac.add(obj.BkSeq);
    mac.add(obj.FtSeq);
    mac.add(obj.BkAcct.Id);
    mac.add(obj.FtAcct.Id);
    mac.add(obj.TrfAmt.amt);
    mac.add(obj.CustCharge.amt);
    mac.add(obj.FutureCharge.amt);
    return mac;
  }

  public static MAC getUnCertainMAC(UnCertain obj)
  {
    MAC mac = new MAC();
    return mac;
  }

  public static MAC getUnCertainResponseMAC(UnCertainResponse obj)
  {
    MAC mac = new MAC();
    return mac;
  }

  public static MAC getReversalMAC(Reversal obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    if (obj.GrpHdr.TradSrc.equals(TradeSource.F))
      mac.add(obj.FtSeq);
    else {
      mac.add(obj.BkSeq);
    }
    mac.add(obj.OldBankSeqNo);
    mac.add(obj.OldFutureSeqNo);
    mac.add(obj.Resend.toString());
    mac.add(obj.Cust.Name);
    mac.add(obj.BkAcct.Id);
    mac.add(obj.FtAcct.Id);
    mac.add(obj.TrfAmt.amt);
    mac.add(obj.CustCharge.amt);
    mac.add(obj.FutureCharge.amt);
    return mac;
  }

  public static MAC getReversalResponseMAC(ReversalResponse obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.Rst.Code);
    mac.add(obj.BkSeq);
    mac.add(obj.FtSeq);
    mac.add(obj.BkAcct.Id);
    mac.add(obj.FtAcct.Id);
    mac.add(obj.TrfAmt.amt);
    mac.add(obj.CustCharge.amt);
    mac.add(obj.FutureCharge.amt);
    return mac;
  }

  public static MAC getCheckAccountMAC(CheckAccount obj) {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.FtSeq);
    mac.add(obj.Cust.Name);
    mac.add(obj.BkAcct.Id);
    return mac;
  }

  public static MAC getCheckAccountResponseMAC(CheckAccountResponse obj)
  {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.Rst.Code);
    mac.add(obj.BkSeq);
    mac.add(obj.FtSeq);
    return mac;
  }

  public static MAC getBankTransferMAC(BankTransfer obj) {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.FtSeq);
    mac.add(obj.BkAcct.Id);
    mac.add(obj.BkAcct.ICBCFlag);
    mac.add(obj.BkAcct.RcvCode);
    mac.add(obj.TrfAmt.amt);
    return mac;
  }
  public static MAC getBankTransferResponseMAC(BankTransferResponse obj) {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.Rst.Code);
    mac.add(obj.BkSeq);
    mac.add(obj.FtSeq);
    mac.add(obj.FutureCharge.amt);
    return mac;
  }

  public static MAC getMatketOutMoneyMAC(MatketOutMoney obj) {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.FtSeq);
    mac.add(obj.TrfAmt.amt);
    mac.add(obj.Dgst);
    return mac;
  }
  public static MAC getMatketOutMoneyResponseMAC(MatketOutMoneyResponse obj) {
    MAC mac = new MAC();
    mac.add(obj.GrpHdr.BusCd);
    mac.add(obj.GrpHdr.TradSrc.toString());
    mac.add(obj.GrpHdr.Sender.InstId);
    mac.add(obj.GrpHdr.Recver.InstId);
    mac.add(obj.GrpHdr.Date);
    mac.add(obj.Rst.Code);
    mac.add(obj.BkSeq);
    mac.add(obj.FtSeq);
    mac.add(obj.TrfAmt.amt);
    return mac;
  }
}