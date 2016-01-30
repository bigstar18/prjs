package gnnt.bank.adapter;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import gnnt.bank.adapter.bankBusiness.cpnt.FileInfo;
import gnnt.bank.adapter.bankBusiness.dayData.DTL01;
import gnnt.bank.adapter.bankBusiness.enumElmt.BusinessCode;
import gnnt.bank.adapter.bankBusiness.enumElmt.CertificationType;
import gnnt.bank.adapter.bankBusiness.enumElmt.CustomerType;
import gnnt.bank.adapter.bankBusiness.enumElmt.ReturnCode;
import gnnt.bank.adapter.bankBusiness.enumElmt.SystemType;
import gnnt.bank.adapter.bankBusiness.enumElmt.TradeSource;
import gnnt.bank.adapter.bankBusiness.enumElmt.YesNoIndicator;
import gnnt.bank.adapter.bankBusiness.info.BankTransfer;
import gnnt.bank.adapter.bankBusiness.info.CheckAccount;
import gnnt.bank.adapter.bankBusiness.info.DayDataReady;
import gnnt.bank.adapter.bankBusiness.info.DayDataReadyResponse;
import gnnt.bank.adapter.bankBusiness.info.DelAccount;
import gnnt.bank.adapter.bankBusiness.info.DelAccountResponse;
import gnnt.bank.adapter.bankBusiness.info.Handshake;
import gnnt.bank.adapter.bankBusiness.info.HandshakeResponse;
import gnnt.bank.adapter.bankBusiness.info.MatketOutMoney;
import gnnt.bank.adapter.bankBusiness.info.ModAccount;
import gnnt.bank.adapter.bankBusiness.info.ModAccountResponse;
import gnnt.bank.adapter.bankBusiness.info.QryAccount;
import gnnt.bank.adapter.bankBusiness.info.QryAccountResponse;
import gnnt.bank.adapter.bankBusiness.info.RgstAccount;
import gnnt.bank.adapter.bankBusiness.info.RgstAccountResponse;
import gnnt.bank.adapter.bankBusiness.info.Transfer;
import gnnt.bank.adapter.bankBusiness.info.TransferResponse;
import gnnt.bank.adapter.bankBusiness.info.UnCertain;
import gnnt.bank.adapter.bankBusiness.info.UnCertainResponse;
import gnnt.bank.adapter.util.Arith;
import gnnt.bank.adapter.util.Common;
import gnnt.bank.adapter.util.MACVerify;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfoValue;

public class ObjTransformer {
	private ICBCBankImpl adapter = null;

	public ObjTransformer() {
		this.adapter = ((ICBCBankImpl) Factory.getInstance().getBankAdapter());
	}

	public Transfer getTransfer(InMoneyVO inMoneyVO) {
		Transfer transfer = new Transfer();

		transfer.GrpHdr.Version = "1.0.0";
		transfer.GrpHdr.SysType = SystemType.BF.getValue();
		transfer.GrpHdr.BusCd = BusinessCode.CODE22001.getValue();
		transfer.GrpHdr.TradSrc = TradeSource.F;
		transfer.GrpHdr.Sender.InstId = this.adapter.MARKETCODE;
		transfer.GrpHdr.Recver.InstId = this.adapter.BANKCODE;
		transfer.GrpHdr.Date = Common.df7.format(Common.getDate());
		transfer.GrpHdr.Time = Common.df8.format(Common.getDate());

		if (inMoneyVO.getPayInfo().cardType.equals("1")) {
			transfer.Cust.CertType = CertificationType.IDCARD.getValue();
			transfer.Cust.Type = CustomerType.PERSONAL.getValue();
		} else if (inMoneyVO.getPayInfo().cardType.equals("8")) {
			transfer.Cust.CertType = CertificationType.LEGALPERSONCODE.getValue();
			transfer.Cust.Type = CustomerType.ORGANIZE.getValue();
		} else if (inMoneyVO.getPayInfo().cardType.equals("9")) {
			transfer.Cust.CertType = CertificationType.BUSINESSLICENCE.getValue();
			transfer.Cust.Type = CustomerType.ORGANIZE.getValue();
		}
		transfer.Cust.CertId = inMoneyVO.getPayInfo().card;
		transfer.Cust.Name = inMoneyVO.getPayInfo().name;

		transfer.FtSeq = String.valueOf(inMoneyVO.getActionID());
		transfer.BkAcct.Id = inMoneyVO.getPayInfo().account;
		transfer.FtAcct.Id = inMoneyVO.getContact();
		transfer.TrfAmt.amt = ((long) Arith.mul(inMoneyVO.getMoney(), 100.0F));

		transfer.Mac = MACVerify.encryption(transfer);
		return transfer;
	}

	public Transfer getTransfer(OutMoneyVO outMoneyVO, boolean asynFlag, boolean isSuccess) {
		Transfer transfer = new Transfer();

		transfer.GrpHdr.Version = "1.0.0";
		transfer.GrpHdr.SysType = SystemType.BF.getValue();
		transfer.GrpHdr.BusCd = BusinessCode.CODE22002.getValue();
		transfer.GrpHdr.TradSrc = TradeSource.F;
		transfer.GrpHdr.Sender.InstId = this.adapter.MARKETCODE;
		transfer.GrpHdr.Recver.InstId = this.adapter.BANKCODE;
		transfer.GrpHdr.Date = Common.df7.format(Common.getDate());
		transfer.GrpHdr.Time = Common.df8.format(Common.getDate());

		if (outMoneyVO.receiveInfo.cardType.equals("1")) {
			transfer.Cust.CertType = CertificationType.IDCARD.getValue();
			transfer.Cust.Type = CustomerType.PERSONAL.getValue();
		} else if (outMoneyVO.receiveInfo.cardType.equals("8")) {
			transfer.Cust.CertType = CertificationType.LEGALPERSONCODE.getValue();
			transfer.Cust.Type = CustomerType.ORGANIZE.getValue();
		} else if (outMoneyVO.receiveInfo.cardType.equals("9")) {
			transfer.Cust.CertType = CertificationType.BUSINESSLICENCE.getValue();
			transfer.Cust.Type = CustomerType.ORGANIZE.getValue();
		} else if (outMoneyVO.receiveInfo.cardType.equals("4")) {
			transfer.Cust.CertType = CertificationType.EGISTEREDCARD.getValue();
			transfer.Cust.Type = CustomerType.PERSONAL.getValue();
		} else if (outMoneyVO.receiveInfo.cardType.equals("2")) {
			transfer.Cust.CertType = CertificationType.OFFICERCARD.getValue();
			transfer.Cust.Type = CustomerType.PERSONAL.getValue();
		} else if (outMoneyVO.receiveInfo.cardType.equals("3")) {
			transfer.Cust.CertType = CertificationType.POSSCARD.getValue();
			transfer.Cust.Type = CustomerType.PERSONAL.getValue();
		} else if (outMoneyVO.receiveInfo.cardType.equals("C")) {
			transfer.Cust.CertType = CertificationType.RETURNCARD.getValue();
			transfer.Cust.Type = CustomerType.PERSONAL.getValue();
		} else if (outMoneyVO.receiveInfo.cardType.equals("J")) {
			transfer.Cust.CertType = CertificationType.SOLDIERCARD.getValue();
			transfer.Cust.Type = CustomerType.PERSONAL.getValue();
		} else if (outMoneyVO.receiveInfo.cardType.equals("X")) {
			transfer.Cust.CertType = CertificationType.OTHER.getValue();
			transfer.Cust.Type = CustomerType.PERSONAL.getValue();
		}

		if (asynFlag) {
			transfer.AsynFlag = 1;
			transfer.AsynSeq = String.valueOf(outMoneyVO.actionID);
			if (isSuccess)
				transfer.AsynStat = YesNoIndicator.Y;
			else {
				transfer.AsynStat = YesNoIndicator.N;
			}
		}
		transfer.Cust.CertId = outMoneyVO.receiveInfo.card;
		transfer.Cust.Name = outMoneyVO.receiveInfo.name;

		transfer.FtSeq = String.valueOf(outMoneyVO.actionID);
		transfer.BkAcct.Id = outMoneyVO.receiveInfo.account;
		transfer.FtAcct.Id = outMoneyVO.contact;
		transfer.TrfAmt.amt = ((long) Arith.mul(outMoneyVO.money, 100.0F));
		transfer.Mac = MACVerify.encryption(transfer);
		return transfer;
	}

	public InMoneyVO getInMoneyVO(Transfer transfer) {
		String bankId = this.adapter.getBankID();
		String firmId = transfer.FtAcct.Id;
		TransferInfoValue payInfo = new TransferInfoValue();
		payInfo.account = transfer.BkAcct.Id;
		String payPwd = null;
		TransferInfoValue receiveInfo = new TransferInfoValue();
		String operator = null;
		long actionID = 0L;

		InMoneyVO inMoneyVO = new InMoneyVO(bankId, null, Arith.div(transfer.TrfAmt.amt, 100.0F), firmId, payInfo, payPwd, receiveInfo, operator,
				actionID);
		return inMoneyVO;
	}

	public TransferResponse getTransferResponse(Transfer transfer, ReturnValue returnValue, boolean asynFlag) {
		TransferResponse transferResponse = new TransferResponse();
		transferResponse.GrpHdr = transfer.GrpHdr;
		transferResponse.BkSeq = transfer.BkSeq;
		transferResponse.BkAcct = transfer.BkAcct;
		transferResponse.FtAcct = transfer.FtAcct;
		transferResponse.TrfAmt = transfer.TrfAmt;

		if (this.adapter.ErrorCodeM2B.get(Long.valueOf(returnValue.result)) != null)
			transferResponse.Rst.Code = String.valueOf(this.adapter.ErrorCodeM2B.get(Long.valueOf(returnValue.result)));
		else {
			transferResponse.Rst.Code = ReturnCode.CODE2041.getValue();
		}
		if (asynFlag) {
			System.out.println("交易待审核");
			transferResponse.AsynFlag = YesNoIndicator.Y;
			transferResponse.Rst.Code = ReturnCode.CODE3014.getValue();
		}
		transferResponse.FtSeq = (returnValue.actionId < 0L ? "" : String.valueOf(returnValue.actionId));
		transferResponse.Mac = MACVerify.encryption(transferResponse);
		return transferResponse;
	}

	public Handshake getHandshake(BusinessCode code, String key) {
		Handshake handshake = new Handshake();
		handshake.GrpHdr.Version = "1.0.0";
		handshake.GrpHdr.SysType = SystemType.BF.getValue();
		handshake.GrpHdr.BusCd = code.getValue();
		handshake.GrpHdr.TradSrc = TradeSource.F;
		handshake.GrpHdr.Sender.InstId = this.adapter.MARKETCODE;
		handshake.GrpHdr.Recver.InstId = this.adapter.BANKCODE;
		handshake.GrpHdr.Date = Common.df7.format(Common.getDate());
		handshake.GrpHdr.Time = Common.df8.format(Common.getDate());
		try {
			handshake.FtSeq = String.valueOf(this.adapter.getPROCESSOR().getMktActionID());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			handshake.FtSeq = "0";
		}

		if (handshake.GrpHdr.BusCd == BusinessCode.CODE20004.getValue()) {
			handshake.MacKey = key;
		}

		handshake.Mac = MACVerify.encryption(handshake);

		return handshake;
	}

	public HandshakeResponse getHandshakeResponse(Handshake handshake, ReturnValue returnValue) {
		HandshakeResponse handshakeResponse = new HandshakeResponse();
		handshakeResponse.GrpHdr = handshake.GrpHdr;

		if (this.adapter.ErrorCodeM2B.get(Long.valueOf(returnValue.result)) != null)
			handshakeResponse.Rst.Code = String.valueOf(this.adapter.ErrorCodeM2B.get(Long.valueOf(returnValue.result)));
		else {
			handshakeResponse.Rst.Code = ReturnCode.CODE2041.getValue();
		}

		handshakeResponse.BkSeq = handshake.BkSeq;
		if (handshakeResponse.Rst.Code != ReturnCode.CODE0000.getValue()) {
			try {
				handshakeResponse.FtSeq = String.valueOf(this.adapter.getPROCESSOR().getMktActionID());
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (Exception e) {
				handshake.FtSeq = "0";
			}
		}
		handshakeResponse.Mac = MACVerify.encryption(handshake);

		return handshakeResponse;
	}

	public CorrespondValue getCorrespondValue(RgstAccount rgstAccount) {
		CorrespondValue correspondValue = new CorrespondValue();

		correspondValue.bankID = this.adapter.getBankID();
		correspondValue.account = rgstAccount.BkAcct.Id;
		correspondValue.firmID = rgstAccount.FtAcct.Id;

		if (rgstAccount.Cust.CertType == CertificationType.IDCARD.getValue())
			correspondValue.cardType = "1";
		else if (rgstAccount.Cust.CertType == CertificationType.LEGALPERSONCODE.getValue())
			correspondValue.cardType = "8";
		else if (rgstAccount.Cust.CertType == CertificationType.BUSINESSLICENCE.getValue())
			correspondValue.cardType = "9";
		else if (rgstAccount.Cust.CertType == CertificationType.OFFICERCARD.getValue())
			correspondValue.cardType = "2";
		else if (rgstAccount.Cust.CertType == CertificationType.EGISTEREDCARD.getValue())
			correspondValue.cardType = "4";
		else if (rgstAccount.Cust.CertType == CertificationType.RETURNCARD.getValue())
			correspondValue.cardType = "C";
		else if (rgstAccount.Cust.CertType == CertificationType.POSSCARD.getValue())
			correspondValue.cardType = "3";
		else if (rgstAccount.Cust.CertType == CertificationType.SOLDIERCARD.getValue())
			correspondValue.cardType = "J";
		else if (rgstAccount.Cust.CertType == CertificationType.OTHER.getValue()) {
			correspondValue.cardType = "X";
		}
		correspondValue.card = rgstAccount.Cust.CertId;
		correspondValue.accountName = rgstAccount.Cust.Name;

		return correspondValue;
	}

	public RgstAccountResponse getRgstAccountResponse(RgstAccount rgstAccount, ReturnValue returnValue) {
		RgstAccountResponse rgstAccountResponse = new RgstAccountResponse();

		rgstAccountResponse.GrpHdr = rgstAccount.GrpHdr;

		if ((returnValue.result + "").length() == 5) {
			rgstAccountResponse.Rst.Code = (0L - returnValue.result) + "";
			rgstAccountResponse.Rst.Info = (returnValue.remark == null ? "验证失败" : returnValue.remark);
		} else if (returnValue.result == 0L) {
			rgstAccountResponse.Rst.Code = ReturnCode.CODE0000.getValue();
			rgstAccountResponse.Rst.Info = "校验通过签约成功";
		} else {
			rgstAccountResponse.Rst.Code = ReturnCode.CODE2041.getValue();
			rgstAccountResponse.Rst.Info = (returnValue.remark == null ? "验证失败" : returnValue.remark);
		}
		rgstAccountResponse.BkSeq = rgstAccount.BkSeq;
		rgstAccountResponse.FtSeq = (returnValue.actionId < 0L ? "" : String.valueOf(returnValue.actionId));
		rgstAccountResponse.BkAcct = rgstAccount.BkAcct;
		rgstAccountResponse.FtAcct = rgstAccount.FtAcct;

		rgstAccountResponse.Mac = MACVerify.encryption(rgstAccountResponse);
		return rgstAccountResponse;
	}

	public CorrespondValue getCorrespondValue(DelAccount delAccount) {
		CorrespondValue correspondValue = new CorrespondValue();
		correspondValue.bankID = this.adapter.getBankID();
		correspondValue.account = delAccount.BkAcct.Id;
		correspondValue.firmID = delAccount.FtAcct.Id;
		return correspondValue;
	}

	public DelAccountResponse getDelAccountResponse(DelAccount delAccount, ReturnValue returnValue) {
		DelAccountResponse delAccountResponse = new DelAccountResponse();

		delAccountResponse.GrpHdr = delAccount.GrpHdr;
		if (this.adapter.ErrorCodeM2B.get(Long.valueOf(returnValue.result)) != null)
			delAccountResponse.Rst.Code = String.valueOf(this.adapter.ErrorCodeM2B.get(Long.valueOf(returnValue.result)));
		else {
			delAccountResponse.Rst.Code = ReturnCode.CODE2041.getValue();
		}
		if (delAccountResponse.Rst.Code != ReturnCode.CODE0000.getValue()) {
			delAccountResponse.Rst.Info = (returnValue.remark == null ? "验证失败" : returnValue.remark);
		}
		delAccountResponse.Rst.Info = "验证通过";
		delAccountResponse.BkSeq = delAccount.BkSeq;
		delAccountResponse.FtSeq = (returnValue.actionId < 0L ? "" : String.valueOf(returnValue.actionId));
		delAccountResponse.BkAcct = delAccount.BkAcct;
		delAccountResponse.FtAcct = delAccount.FtAcct;

		delAccountResponse.Mac = MACVerify.encryption(delAccountResponse);
		return delAccountResponse;
	}

	public CorrespondValue getCorrespondValue(ModAccount modAccount) {
		CorrespondValue correspondValue = new CorrespondValue();
		correspondValue.bankID = this.adapter.getBankID();
		correspondValue.account = modAccount.BkAcct.Id;
		correspondValue.firmID = modAccount.FtAcct.Id;

		if (modAccount.Cust.CertType == CertificationType.IDCARD.getValue())
			correspondValue.cardType = "1";
		else if (modAccount.Cust.CertType == CertificationType.LEGALPERSONCODE.getValue())
			correspondValue.cardType = "8";
		else if (modAccount.Cust.CertType == CertificationType.BUSINESSLICENCE.getValue()) {
			correspondValue.cardType = "9";
		}
		correspondValue.card = modAccount.Cust.CertId;
		correspondValue.accountName = modAccount.Cust.Name;
		return correspondValue;
	}

	public CorrespondValue getCorrespondValueNew(ModAccount modAccount) {
		CorrespondValue correspondValue = new CorrespondValue();
		correspondValue.bankID = this.adapter.getBankID();
		correspondValue.account = modAccount.NewBkAcct.Id;
		correspondValue.firmID = modAccount.FtAcct.Id;

		if (modAccount.Cust.CertType == CertificationType.IDCARD.getValue())
			correspondValue.cardType = "1";
		else if (modAccount.Cust.CertType == CertificationType.LEGALPERSONCODE.getValue())
			correspondValue.cardType = "8";
		else if (modAccount.Cust.CertType == CertificationType.BUSINESSLICENCE.getValue()) {
			correspondValue.cardType = "9";
		}
		correspondValue.card = modAccount.Cust.CertId;
		correspondValue.accountName = modAccount.Cust.Name;

		return correspondValue;
	}

	public ModAccountResponse getModAccountResponse(ModAccount modAccount, ReturnValue returnValue) {
		ModAccountResponse modAccountResponse = new ModAccountResponse();

		modAccountResponse.GrpHdr = modAccount.GrpHdr;
		if (this.adapter.ErrorCodeM2B.get(Long.valueOf(returnValue.result)) != null)
			modAccountResponse.Rst.Code = String.valueOf(this.adapter.ErrorCodeM2B.get(Long.valueOf(returnValue.result)));
		else {
			modAccountResponse.Rst.Code = ReturnCode.CODE2041.getValue();
		}

		modAccountResponse.BkSeq = modAccount.BkSeq;
		modAccountResponse.FtSeq = (returnValue.actionId < 0L ? "" : String.valueOf(returnValue.actionId));
		modAccountResponse.BkAcct = modAccount.BkAcct;
		modAccountResponse.FtAcct = modAccount.FtAcct;

		modAccountResponse.Mac = MACVerify.encryption(modAccountResponse);
		return modAccountResponse;
	}

	public DayDataReady getDayDataReady(List<FileInfo> list) {
		DayDataReady dayDataReady = new DayDataReady();

		dayDataReady.GrpHdr.Version = "1.0.0";
		dayDataReady.GrpHdr.SysType = SystemType.BF.getValue();
		dayDataReady.GrpHdr.BusCd = BusinessCode.CODE23002.getValue();
		dayDataReady.GrpHdr.TradSrc = TradeSource.F;
		dayDataReady.GrpHdr.Sender.InstId = this.adapter.MARKETCODE;
		dayDataReady.GrpHdr.Recver.InstId = this.adapter.BANKCODE;
		dayDataReady.GrpHdr.Date = Common.df7.format(Common.getDate());
		dayDataReady.GrpHdr.Time = Common.df8.format(Common.getDate());
		try {
			dayDataReady.FtSeq = String.valueOf(this.adapter.getPROCESSOR().getMktActionID());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			dayDataReady.FtSeq = "0";
		}

		for (int i = 0; i < list.size(); i++) {
			FileInfo fileInfo = (FileInfo) list.get(i);
			dayDataReady.add(fileInfo);
		}

		dayDataReady.Mac = MACVerify.encryption(dayDataReady);

		return dayDataReady;
	}

	public DayDataReadyResponse getDayDataReadyResponse(DayDataReady dayDataReady, ReturnValue returnValue) {
		DayDataReadyResponse dayDataReadyResponse = new DayDataReadyResponse();
		dayDataReadyResponse.GrpHdr = dayDataReady.GrpHdr;
		if (this.adapter.ErrorCodeM2B.get(Long.valueOf(returnValue.result)) != null)
			dayDataReadyResponse.Rst.Code = String.valueOf(this.adapter.ErrorCodeM2B.get(Long.valueOf(returnValue.result)));
		else {
			dayDataReadyResponse.Rst.Code = ReturnCode.CODE2041.getValue();
		}
		dayDataReadyResponse.BkSeq = dayDataReady.BkSeq;
		dayDataReadyResponse.FtSeq = (returnValue.actionId < 0L ? "" : String.valueOf(returnValue.actionId));
		dayDataReadyResponse.Mac = MACVerify.encryption(dayDataReadyResponse);
		return dayDataReadyResponse;
	}

	public QryAccountResponse getQryAccountResponse(QryAccount qryAccount, FirmBalanceValue firmBalanceValue, ReturnValue returnValue) {
		QryAccountResponse qryAccountResponse = new QryAccountResponse();
		qryAccountResponse.GrpHdr = qryAccount.GrpHdr;
		qryAccountResponse.BkAcct = qryAccount.BkAcct;
		qryAccountResponse.FtAcct = qryAccount.FtAcct;
		if (this.adapter.ErrorCodeM2B.get(Long.valueOf(returnValue.result)) != null)
			qryAccountResponse.Rst.Code = String.valueOf(this.adapter.ErrorCodeM2B.get(Long.valueOf(returnValue.result)));
		else {
			qryAccountResponse.Rst.Code = ReturnCode.CODE2041.getValue();
		}
		qryAccountResponse.BkSeq = qryAccount.BkSeq;
		if (qryAccountResponse.Rst.Code == ReturnCode.CODE0000.getValue()) {
			try {
				qryAccountResponse.FtSeq = String.valueOf(this.adapter.getPROCESSOR().getMktActionID());
			} catch (RemoteException e) {
				qryAccountResponse.FtSeq = "0";
				e.printStackTrace();
			}
		}
		qryAccountResponse.FtBal.CurBal.amt = ((long) Arith.mul(firmBalanceValue.marketBalance, 100.0F));
		qryAccountResponse.FtBal.UseBal.amt = ((long) Arith.mul(firmBalanceValue.avilableBalance, 100.0F));
		qryAccountResponse.FtBal.FtcBal.amt = ((long) Arith.mul(firmBalanceValue.avilableBalance, 100.0F));
		qryAccountResponse.Mac = MACVerify.encryption(qryAccountResponse);
		return qryAccountResponse;
	}

	public UnCertainResponse getUnCertainResponse(UnCertain unCertain, String returnCode) {
		UnCertainResponse unCertainResponse = new UnCertainResponse();
		unCertainResponse.GrpHdr = unCertain.GrpHdr;
		unCertainResponse.Rst.Code = returnCode;
		return unCertainResponse;
	}

	public MoneyInfoValue getMoneyInfoValue(DTL01 dtl01, String bankId, String firmId) {
		MoneyInfoValue info = new MoneyInfoValue();
		info.account = dtl01.bkAcct;
		info.bankID = bankId;
		info.compareDate = new java.sql.Date(new java.util.Date().getTime());
		info.firmID = firmId;
		info.id = dtl01.bkSeq;
		info.m_Id = Long.parseLong(dtl01.ftSeq);
		info.money = dtl01.trfAmt;
		if (dtl01.busCd.equals(Integer.valueOf(BusinessCode.CODE22001.getValue())))
			info.type = 1;
		else if (dtl01.busCd.equals(Integer.valueOf(BusinessCode.CODE22002.getValue()))) {
			info.type = 0;
		}
		info.note = "";
		info.status = 0;
		return info;
	}

	public QryAccount getQryAccount(CorrespondValue correspondValue) {
		QryAccount qryAccount = new QryAccount();
		qryAccount.GrpHdr.Version = "1.0.0";
		qryAccount.GrpHdr.SysType = SystemType.BF.getValue();
		qryAccount.GrpHdr.BusCd = BusinessCode.CODE21005.getValue();
		qryAccount.GrpHdr.TradSrc = TradeSource.F;
		qryAccount.GrpHdr.Sender.InstId = this.adapter.MARKETCODE;
		qryAccount.GrpHdr.Recver.InstId = this.adapter.BANKCODE;
		qryAccount.GrpHdr.Date = Common.df7.format(Common.getDate());
		qryAccount.GrpHdr.Time = Common.df8.format(Common.getDate());
		try {
			qryAccount.FtSeq = String.valueOf(this.adapter.getPROCESSOR().getMktActionID());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			qryAccount.FtSeq = "0";
		}
		qryAccount.BkAcct.Id = correspondValue.account;
		qryAccount.FtAcct.Id = correspondValue.firmID;

		if (correspondValue.cardType.equals("1")) {
			qryAccount.Cust.CertType = CertificationType.IDCARD.getValue();
			qryAccount.Cust.Type = CustomerType.PERSONAL.getValue();
		} else if (correspondValue.cardType.equals("8")) {
			qryAccount.Cust.CertType = CertificationType.LEGALPERSONCODE.getValue();
			qryAccount.Cust.Type = CustomerType.ORGANIZE.getValue();
		} else if (correspondValue.cardType.equals("9")) {
			qryAccount.Cust.CertType = CertificationType.BUSINESSLICENCE.getValue();
			qryAccount.Cust.Type = CustomerType.ORGANIZE.getValue();
		}
		qryAccount.Cust.CertId = correspondValue.card;
		qryAccount.Cust.Name = correspondValue.accountName;

		qryAccount.Mac = MACVerify.encryption(qryAccount);
		return qryAccount;
	}

	public CheckAccount getCheckAccount(CorrespondValue correspondValue) {
		CheckAccount checkAccount = new CheckAccount();
		checkAccount.GrpHdr.Version = "1.0.0";
		checkAccount.GrpHdr.SysType = SystemType.BF.getValue();
		checkAccount.GrpHdr.BusCd = BusinessCode.CODE21013.getValue();
		checkAccount.GrpHdr.TradSrc = TradeSource.F;
		checkAccount.GrpHdr.Sender.InstId = this.adapter.MARKETCODE;
		checkAccount.GrpHdr.Recver.InstId = this.adapter.BANKCODE;

		String nextDayTime = BankAdapter.getConfig("nextDayTime");

		String nowDate = Common.df2.format(Common.getDate());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date nextDay = null;
		try {
			nextDay = sdf.parse(nowDate + " " + nextDayTime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		if (Common.getDate().after(nextDay))
			checkAccount.GrpHdr.Date = Common.df7.format(Common.getNextDay(Common.getDate()));
		else {
			checkAccount.GrpHdr.Date = Common.df7.format(Common.getDate());
		}
		checkAccount.GrpHdr.Time = Common.df8.format(Common.getDate());
		try {
			checkAccount.FtSeq = String.valueOf(this.adapter.getPROCESSOR().getMktActionID());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			checkAccount.FtSeq = "0";
		}

		checkAccount.BkAcct.Id = correspondValue.account;

		if (correspondValue.cardType.equals("1")) {
			checkAccount.Cust.CertType = CertificationType.IDCARD.getValue();
			checkAccount.Cust.Type = CustomerType.PERSONAL.getValue();
		} else if (correspondValue.cardType.equals("8")) {
			checkAccount.Cust.CertType = CertificationType.LEGALPERSONCODE.getValue();
			checkAccount.Cust.Type = CustomerType.ORGANIZE.getValue();
		} else if (correspondValue.cardType.equals("9")) {
			checkAccount.Cust.CertType = CertificationType.BUSINESSLICENCE.getValue();
			checkAccount.Cust.Type = CustomerType.ORGANIZE.getValue();
		}
		checkAccount.Cust.CertId = correspondValue.card;
		checkAccount.Cust.Name = correspondValue.accountName;

		checkAccount.Mac = MACVerify.encryption(checkAccount);

		return checkAccount;
	}

	public BankTransfer getInterTransfer(BankTransferValue bankTransferValue) {
		BankTransfer transfer = new BankTransfer();
		transfer.GrpHdr.Version = "1.0.0";
		transfer.GrpHdr.BusCd = BusinessCode.CODE22006.getValue();
		transfer.GrpHdr.TradSrc = TradeSource.F;
		transfer.GrpHdr.Sender.InstId = this.adapter.MARKETCODE;
		transfer.GrpHdr.Recver.InstId = this.adapter.BANKCODE;
		transfer.GrpHdr.Date = Common.df7.format(Common.getDate());
		transfer.GrpHdr.Time = Common.df8.format(Common.getDate());
		transfer.FtSeq = String.valueOf(bankTransferValue.id);
		transfer.BkAcct.Id = bankTransferValue.account;
		transfer.BkAcct.Name = bankTransferValue.Name;

		transfer.BkAcct.OpFlg = YesNoIndicator.Y;

		transfer.BkAcct.Pwd.Pwd = bankTransferValue.Pwd;
		transfer.BkAcct.St = bankTransferValue.St;
		transfer.BkAcct.Type = bankTransferValue.accountType;
		transfer.BkAcct.RegDt = bankTransferValue.RegDt;
		transfer.BkAcct.VldDt = bankTransferValue.VldDt;

		transfer.BkAcct.RcvCode = bankTransferValue.bankNum;

		transfer.TrfAmt.amt = ((long) Arith.mul(bankTransferValue.money, 100.0F));
		transfer.Mac = MACVerify.encryption(transfer);
		return transfer;
	}

	public MatketOutMoney getMatketOutMoney(BankTransferValue bankTransferValue) {
		MatketOutMoney matketOutMoney = new MatketOutMoney();
		matketOutMoney.GrpHdr.Version = "1.0.0";
		matketOutMoney.GrpHdr.BusCd = BusinessCode.CODE22006.getValue();
		matketOutMoney.GrpHdr.TradSrc = TradeSource.F;
		matketOutMoney.GrpHdr.Sender.InstId = this.adapter.MARKETCODE;
		matketOutMoney.GrpHdr.Recver.InstId = this.adapter.BANKCODE;
		matketOutMoney.GrpHdr.Date = Common.df7.format(Common.getDate());
		matketOutMoney.GrpHdr.Time = Common.df8.format(Common.getDate());
		matketOutMoney.FtSeq = bankTransferValue.actionId + "";
		matketOutMoney.Resend = YesNoIndicator.N;
		matketOutMoney.TransType = bankTransferValue.transType;
		matketOutMoney.TrfAmt.amt = ((long) Arith.mul(bankTransferValue.money, 100.0F));
		matketOutMoney.Dgst = bankTransferValue.info;
		matketOutMoney.Mac = MACVerify.encryption(matketOutMoney);
		return matketOutMoney;
	}
}