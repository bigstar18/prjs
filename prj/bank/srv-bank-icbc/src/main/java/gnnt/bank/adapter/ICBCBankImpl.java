package gnnt.bank.adapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import gnnt.bank.adapter.bankBusiness.BankBusiness;
import gnnt.bank.adapter.bankBusiness.cpnt.FileInfo;
import gnnt.bank.adapter.bankBusiness.dayData.DTL01;
import gnnt.bank.adapter.bankBusiness.dayData.DTL02;
import gnnt.bank.adapter.bankBusiness.dayData.DTL03;
import gnnt.bank.adapter.bankBusiness.dayData.DTL04;
import gnnt.bank.adapter.bankBusiness.dayData.STL01;
import gnnt.bank.adapter.bankBusiness.dayData.STL02;
import gnnt.bank.adapter.bankBusiness.dayData.STL03;
import gnnt.bank.adapter.bankBusiness.enumElmt.BusinessCode;
import gnnt.bank.adapter.bankBusiness.enumElmt.FileBusinessCode;
import gnnt.bank.adapter.bankBusiness.enumElmt.ReturnCode;
import gnnt.bank.adapter.bankBusiness.enumElmt.TradeSource;
import gnnt.bank.adapter.bankBusiness.exception.BankActionDoubtedException;
import gnnt.bank.adapter.bankBusiness.exception.BankCommException;
import gnnt.bank.adapter.bankBusiness.exception.BankException;
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
import gnnt.bank.adapter.bankBusiness.info.InterTransfer;
import gnnt.bank.adapter.bankBusiness.info.InterTransferResponse;
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
import gnnt.bank.adapter.bankBusiness.info.TransferResponse;
import gnnt.bank.adapter.bankBusiness.info.UnCertain;
import gnnt.bank.adapter.bankBusiness.info.UnCertainResponse;
import gnnt.bank.adapter.rmi.service.RmiClient;
import gnnt.bank.adapter.rmi.service.RmiServer;
import gnnt.bank.adapter.socket.SocketServer;
import gnnt.bank.adapter.util.Arith;
import gnnt.bank.adapter.util.BankNativeImpl;
import gnnt.bank.adapter.util.Common;
import gnnt.bank.adapter.util.ErrorCodeRelation;
import gnnt.bank.adapter.util.FileProcessor;
import gnnt.bank.adapter.util.FileUtil;
import gnnt.bank.adapter.util.FtpUtil;
import gnnt.bank.adapter.util.GzipUtil;
import gnnt.bank.adapter.util.MACVerify;
import gnnt.bank.platform.util.Tool;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.ChargeAgainstValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.LogEndmsg;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TradeResultValue;
import gnnt.trade.bank.vo.TransferMoneyVO;
import gnnt.trade.bank.vo.bankdz.gs.sent.BankFirmRightValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRights;
import gnnt.trade.bank.vo.bankdz.gs.sent.OpenOrDelFirmValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.ProperBalanceValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.TradingDetailsValue;

public class ICBCBankImpl extends BankAdapter {
	private ObjTransformer objTransformer;
	private BankBusiness bankBusiness;
	Map ErrorCodeM2B;
	Map ErrorCodeB2M;
	public boolean macAble;
	public boolean initFlag;

	protected void init() {
		BankAdapter.log("初始化...");
		this.initFlag = true;
		this.objTransformer = Factory.getInstance().getObjTransfomer();
		this.bankBusiness = Factory.getInstance().getBankBusiniss();

		ErrorCodeRelation errorCodeRelation = new ErrorCodeRelation();
		this.ErrorCodeB2M = errorCodeRelation.ErrorCodeB2M;
		this.ErrorCodeM2B = errorCodeRelation.ErrorCodeM2B;

		this.macAble = Boolean.parseBoolean(BankAdapter.getConfig("macAble"));

		setMarketCode(BankAdapter.getConfig("MarketCode"));

		setBankCode(BankAdapter.getConfig("BankCode"));

		setBankID(getConfig("bankId"));

		SocketServer socketServer = null;
		try {
			socketServer = Factory.getInstance().getSocketServer();
		} catch (Exception e) {
			log(Common.getExceptionTrace(e));
			this.initFlag = false;
		}

		socketServer.start();
		try {
			RmiServer.start();
		} catch (RemoteException e) {
			this.initFlag = false;
			log(Common.getExceptionTrace(e));
		} catch (MalformedURLException e) {
			this.initFlag = false;
			log(Common.getExceptionTrace(e));
		} catch (AlreadyBoundException e) {
			this.initFlag = false;
			log(Common.getExceptionTrace(e));
		}
		if (this.initFlag)
			BankAdapter.log("初始化成功!");
		else
			BankAdapter.log("初始化失败!");
	}

	public CapitalProcessorRMI getPROCESSOR() {
		RmiClient rmiClient = Factory.getInstance().getRmiClient();
		if (rmiClient.connProcessor()) {
			setProcessor(rmiClient.capitalProcessorRMI);
		}
		return this.PROCESSOR;
	}

	public ReturnValue inMoneyQueryBank(InMoneyVO inMoneyVO) {
		ReturnValue returnValue = new ReturnValue();

		Transfer transfer = this.objTransformer.getTransfer(inMoneyVO);

		TransferResponse transferResponse = null;
		try {
			transferResponse = (TransferResponse) this.bankBusiness.getResponse(transfer);

			long backCallResult = 0L;
			if (transferResponse.Rst.Code.equals(ReturnCode.CODE0000.getValue())) {
				try {
					backCallResult = getPROCESSOR().inMoney(getBankID(), inMoneyVO.getContact(), inMoneyVO.getPayInfo().account,
							new Timestamp(Common.getDate().getTime()), inMoneyVO.getMoney(), transferResponse.BkSeq, inMoneyVO.getActionID(), 0,
							"BFMem");
				} catch (RemoteException e) {
					returnValue.result = -1L;

					log(Common.getExceptionTrace(e));
				} catch (Exception e) {
					returnValue.result = -1L;
					log(Common.getExceptionTrace(e));
				}
				if (backCallResult >= 0L) {
					returnValue.result = 0L;
					returnValue.funID = transferResponse.BkSeq;
					returnValue.actionId = backCallResult;
					returnValue.bankTime = (transfer.GrpHdr.Date + transfer.GrpHdr.Time);
				} else {
					returnValue.result = backCallResult;
				}
			} else {
				if (this.ErrorCodeB2M.get(transferResponse.Rst.Code) != null)
					returnValue.result = ((Long) this.ErrorCodeB2M.get(transferResponse.Rst.Code)).longValue();
				else {
					returnValue.result = -100L;
				}
				returnValue.remark = transferResponse.Rst.Info;
			}
			BankAdapter.log("\n");
			BankAdapter.log("市场端入金结果-银行：");
			BankAdapter.log("返回码：" + transferResponse.Rst.Code);
			BankAdapter.log("返回码信息：" + transferResponse.Rst.Info);
			BankAdapter.log("银行流水号：" + transferResponse.BkSeq);
			BankAdapter.log("市场流水号：" + transferResponse.FtSeq);
			BankAdapter.log("金额：" + transferResponse.TrfAmt.amt + "分");
			BankAdapter.log("市场端入金结果-处理器：");
			BankAdapter.log(returnValue.toString());
			BankAdapter.log("\n");
		} catch (BankCommException e) {
			returnValue.result = -100L;
			returnValue.remark = "市场端入金连接银行失败";
			log(Common.getExceptionTrace(e));
			BankAdapter.log("市场端入金连接银行失败");
			return returnValue;
		} catch (BankActionDoubtedException e) {
			returnValue.result = -50010L;
			returnValue.remark = "市场端入金银行异常";
			log(Common.getExceptionTrace(e));
			BankAdapter.log("市场端入金银行异常");
			return returnValue;
		} finally {
			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = inMoneyVO.getPayInfo().account;
			log.bankID = inMoneyVO.getBankID();
			log.contact = inMoneyVO.getFirmID();
			log.firmID = inMoneyVO.getFirmID();
			endmsg.code = returnValue.result + "";
			log.type = 7;
			endmsg.note = returnValue.remark;
			if (returnValue.result != 0L) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		InterfaceLog log = new InterfaceLog();
		LogEndmsg endmsg = new LogEndmsg();
		log.account = inMoneyVO.getPayInfo().account;
		log.bankID = inMoneyVO.getBankID();
		log.contact = inMoneyVO.getFirmID();
		log.firmID = inMoneyVO.getFirmID();
		endmsg.code = returnValue.result + "";
		log.type = 7;
		endmsg.note = returnValue.remark;
		if (returnValue.result != 0L) {
			log.result = 1;
		}
		log.endMsg = endmsg.toString();
		interfaceLog(log);

		return returnValue;
	}

	public TransferResponse inMoneyBank(Object reqeust) {
		ReturnValue returnValue = new ReturnValue();
		Transfer transfer = (Transfer) reqeust;
		InMoneyVO inMoneyVO = null;
		TransferResponse transferResponse = null;
		if (!MACVerify.verify(transfer, transfer.Mac)) {
			returnValue.result = -2L;
		} else {
			inMoneyVO = this.objTransformer.getInMoneyVO(transfer);
			long backCallResult = 0L;
			try {
				backCallResult = getPROCESSOR().inMoney(getBankID(), inMoneyVO.getFirmID(), inMoneyVO.getPayInfo().account,
						new Timestamp(Common.getDate().getTime()), inMoneyVO.getMoney(), transfer.BkSeq, inMoneyVO.getActionID(), 0, "Bank_in");
				if (backCallResult >= 0L) {
					returnValue.result = 0L;
					returnValue.actionId = backCallResult;
					returnValue.funID = transfer.BkSeq;
					returnValue.bankTime = (transfer.GrpHdr.Date + transfer.GrpHdr.Time);
				} else {
					returnValue.result = backCallResult;
					returnValue.funID = transfer.BkSeq;
				}

				BankAdapter.log("\n");
				BankAdapter.log("银行端入金结果：");
				BankAdapter.log(returnValue.toString());
				transferResponse = this.objTransformer.getTransferResponse(transfer, returnValue, false);
				BankAdapter.log("银行端入金结果-返回给银行：");
				BankAdapter.log("返回码：" + transferResponse.Rst.Code);
				BankAdapter.log("银行流水号：" + transferResponse.BkSeq);
				BankAdapter.log("市场流水号：" + transferResponse.FtSeq);
				BankAdapter.log("金额：" + transferResponse.TrfAmt.amt + "分");
				BankAdapter.log("\n");
			} catch (RemoteException e) {
				returnValue.result = -1L;
				log(Common.getExceptionTrace(e));
			} catch (Exception e) {
				returnValue.result = -1L;
				log(Common.getExceptionTrace(e));
			} finally {
				InterfaceLog log = new InterfaceLog();
				LogEndmsg endmsg = new LogEndmsg();
				log.account = inMoneyVO.getPayInfo().account;
				log.bankID = inMoneyVO.getBankID();
				log.contact = inMoneyVO.getFirmID();
				log.firmID = inMoneyVO.getFirmID();
				endmsg.code = returnValue.result + "";
				log.type = 7;
				endmsg.note = returnValue.remark;
				if (returnValue.result != 0L) {
					log.result = 1;
				}
				log.endMsg = endmsg.toString();
				interfaceLog(log);
			}
		}
		return transferResponse;
	}

	public TransferResponse outMoneyBank(Object reqeust) {
		ReturnValue returnValue = new ReturnValue();
		boolean asynFlag = false;
		Transfer transfer = (Transfer) reqeust;
		TransferResponse transferResponse = null;
		if (!MACVerify.verify(transfer, transfer.Mac)) {
			returnValue.result = -2L;
		} else {
			try {
				returnValue = getPROCESSOR().outMoney(this.BANKID, Arith.div(transfer.TrfAmt.amt, 100.0F), transfer.FtAcct.Id, transfer.BkAcct.Id,
						transfer.BkSeq, "Bank_out", 0, 2);
				log(returnValue.toString());
				if (returnValue.result == 0L) {
					returnValue.actionId = returnValue.actionId;
					returnValue.funID = transfer.BkSeq;
					returnValue.bankTime = (transfer.GrpHdr.Date + transfer.GrpHdr.Time);
				} else if (returnValue.result == 5L) {
					asynFlag = true;
					returnValue.result = 0L;
					returnValue.actionId = returnValue.actionId;
					returnValue.funID = transfer.BkSeq;
					returnValue.bankTime = (transfer.GrpHdr.Date + transfer.GrpHdr.Time);
				} else {
					returnValue.result = -2L;
					returnValue.funID = transfer.BkSeq;
				}

				BankAdapter.log("\n");
				BankAdapter.log("银行端出金结果：");
				BankAdapter.log(returnValue.toString());
				transferResponse = this.objTransformer.getTransferResponse(transfer, returnValue, asynFlag);
				BankAdapter.log("银行端出金结果-返回给银行：");
				BankAdapter.log("返回码：" + transferResponse.Rst.Code);
				BankAdapter.log("银行流水号：" + transferResponse.BkSeq);
				BankAdapter.log("市场流水号：" + transferResponse.FtSeq);
				BankAdapter.log("金额：" + transferResponse.TrfAmt.amt + "分");
				BankAdapter.log("\n");
			} catch (RemoteException e) {
				returnValue.result = -1L;
				log(Common.getExceptionTrace(e));
			} catch (Exception e) {
				returnValue.result = -1L;
				log(Common.getExceptionTrace(e));
			} finally {
				InterfaceLog log = new InterfaceLog();
				LogEndmsg endmsg = new LogEndmsg();
				log.account = transfer.BkAcct.Id;
				log.bankID = this.BANKID;
				log.contact = transfer.FtAcct.Id;
				log.firmID = transfer.FtAcct.Id;
				endmsg.code = returnValue.result + "";
				log.type = 6;
				endmsg.note = returnValue.remark;
				if (returnValue.result != 0L) {
					log.result = 1;
				}
				log.endMsg = endmsg.toString();
				interfaceLog(log);
			}
		}
		return transferResponse;
	}

	public ReturnValue outMoneyMarketDone(OutMoneyVO outMoneyVO) {
		ReturnValue returnValue = new ReturnValue();

		Transfer transfer = this.objTransformer.getTransfer(outMoneyVO, false, false);

		TransferResponse transferResponse = null;
		try {
			transferResponse = (TransferResponse) this.bankBusiness.getResponse(transfer);

			if (transferResponse.Rst.Code.equals(ReturnCode.CODE0000.getValue())) {
				returnValue.result = 0L;
				returnValue.actionId = outMoneyVO.actionID;
				returnValue.funID = transferResponse.BkSeq;
				returnValue.bankTime = (transfer.GrpHdr.Date + transfer.GrpHdr.Time);
			} else {
				if (this.ErrorCodeB2M.get(transferResponse.Rst.Code) != null)
					returnValue.result = ((Long) this.ErrorCodeB2M.get(transferResponse.Rst.Code)).longValue();
				else {
					returnValue.result = -100L;
				}
				returnValue.remark = transferResponse.Rst.Info;
			}
			BankAdapter.log("\n");
			BankAdapter.log("市场端出金结果-银行：");
			BankAdapter.log("返回码：" + transferResponse.Rst.Code);
			BankAdapter.log("返回码信息：" + transferResponse.Rst.Info);
			BankAdapter.log("银行流水号：" + transferResponse.BkSeq);
			BankAdapter.log("市场流水号：" + transferResponse.FtSeq);
			BankAdapter.log("金额：" + transferResponse.TrfAmt.amt + "分");
			BankAdapter.log("市场端出金结果-处理器：");
			BankAdapter.log(returnValue.toString());
			BankAdapter.log("\n");
		} catch (BankActionDoubtedException e) {
			returnValue.result = -50010L;
			log(Common.getExceptionTrace(e));
			BankAdapter.log("市场端出金异常" + returnValue.toString());
			return returnValue;
		} catch (BankCommException e) {
			returnValue.result = -100L;
			log(Common.getExceptionTrace(e));
			BankAdapter.log("市场端出金失败" + returnValue.toString());
			return returnValue;
		} finally {
			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = outMoneyVO.receiveInfo.account;
			log.bankID = this.BANKID;
			log.contact = outMoneyVO.contact;
			log.firmID = outMoneyVO.contact;
			endmsg.code = returnValue.result + "";
			log.type = 6;
			endmsg.note = returnValue.remark;
			if (returnValue.result != 0L) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		InterfaceLog log = new InterfaceLog();
		LogEndmsg endmsg = new LogEndmsg();
		log.account = outMoneyVO.receiveInfo.account;
		log.bankID = this.BANKID;
		log.contact = outMoneyVO.contact;
		log.firmID = outMoneyVO.contact;
		endmsg.code = returnValue.result + "";
		log.type = 6;
		endmsg.note = returnValue.remark;
		if (returnValue.result != 0L) {
			log.result = 1;
		}
		log.endMsg = endmsg.toString();
		interfaceLog(log);

		return returnValue;
	}

	public ReturnValue outMoneyBackBank(OutMoneyVO outMoneyVO, boolean isSuccess) {
		ReturnValue returnValue = new ReturnValue();

		Transfer transfer = this.objTransformer.getTransfer(outMoneyVO, true, isSuccess);

		TransferResponse transferResponse = null;

		try {
			transferResponse = (TransferResponse) this.bankBusiness.getResponse(transfer);

			if (transferResponse.Rst.Code.equals(ReturnCode.CODE0000.getValue())) {
				returnValue.result = 0L;
				returnValue.actionId = outMoneyVO.actionID;
				returnValue.funID = transferResponse.BkSeq;
				returnValue.bankTime = (transfer.GrpHdr.Date + transfer.GrpHdr.Time);
			} else if (transferResponse.Rst.Code.equals(ReturnCode.CODE3015.getValue())) {
				returnValue.result = 0L;
				returnValue.actionId = outMoneyVO.actionID;
				returnValue.funID = transferResponse.BkSeq;
				returnValue.bankTime = (transfer.GrpHdr.Date + transfer.GrpHdr.Time);
			} else {
				if (this.ErrorCodeB2M.get(transferResponse.Rst.Code) != null)
					returnValue.result = ((Long) this.ErrorCodeB2M.get(transferResponse.Rst.Code)).longValue();
				else {
					returnValue.result = -100L;
				}
				returnValue.remark = transferResponse.Rst.Info;
			}
			BankAdapter.log("\n");
			BankAdapter.log("银行端出金市场端审核结果-银行：");
			BankAdapter.log("返回码：" + transferResponse.Rst.Code);
			BankAdapter.log("返回码信息：" + transferResponse.Rst.Info);
			BankAdapter.log("银行流水号：" + transferResponse.BkSeq);
			BankAdapter.log("市场流水号：" + transferResponse.FtSeq);
			BankAdapter.log("金额：" + transferResponse.TrfAmt.amt + "分");
			BankAdapter.log("银行端出金市场端审核结果-处理器：");
			BankAdapter.log(returnValue.toString());
			BankAdapter.log("\n");
		} catch (BankException e) {
			returnValue.result = -100L;
			log(Common.getExceptionTrace(e));
			BankAdapter.log("银行端出金市场端审核失败");
			ReturnValue localReturnValue1 = returnValue;
			return localReturnValue1;
		} finally {
			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = outMoneyVO.receiveInfo.account;
			log.bankID = this.BANKID;
			log.contact = outMoneyVO.contact;
			endmsg.code = returnValue.result + "";
			log.type = 6;
			endmsg.note = returnValue.remark;
			if (returnValue.result != 0L) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		return returnValue;
	}

	public ReturnValue interTransfer() {
		ReturnValue returnValue = new ReturnValue();

		InterTransfer interTransfer = null;
		InterTransferResponse interTransferResponse = null;
		try {
			interTransferResponse = (InterTransferResponse) this.bankBusiness.getResponse(interTransfer);

			if (interTransferResponse.Rst.Code.equals(ReturnCode.CODE0000.getValue())) {
				returnValue.result = 0L;
				returnValue.actionId = 0L;
				returnValue.funID = interTransferResponse.BkSeq;
				returnValue.bankTime = (interTransferResponse.GrpHdr.Date + interTransferResponse.GrpHdr.Time);
			} else {
				returnValue.result = ((Long) this.ErrorCodeB2M.get(interTransferResponse.Rst.Code)).longValue();
				returnValue.remark = interTransferResponse.Rst.Info;
			}
		} catch (BankException e) {
			returnValue.result = -100L;
			log(Common.getExceptionTrace(e));
			BankAdapter.log("跨行汇拨失败");
			ReturnValue localReturnValue1 = returnValue;
			return localReturnValue1;
		} finally {
			BankAdapter.log("\n");
			BankAdapter.log("跨行汇拨结果-银行：");
			BankAdapter.log("返回码：" + interTransferResponse.Rst.Code);
			BankAdapter.log("返回码信息：" + interTransferResponse.Rst.Info);
			BankAdapter.log("银行流水号：" + interTransferResponse.BkSeq);
			BankAdapter.log("市场流水号：" + interTransferResponse.FtSeq);
			BankAdapter.log("金额：" + interTransfer.TrfAmt.amt + "分");// 有问题 TODO
			BankAdapter.log("跨行汇拨结果-处理器：");
			BankAdapter.log(returnValue.toString());
			BankAdapter.log("\n");
			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = "";
			log.bankID = this.BANKID;
			log.contact = "";
			endmsg.code = returnValue.result + "";
			log.type = 11;
			endmsg.note = returnValue.remark;
			if (returnValue.result != 0L) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		return returnValue;
	}

	public ReturnValue loginBank() throws BankCommException {
		BankAdapter.log("\n");
		BankAdapter.log("签到");
		ReturnValue returnValue = new ReturnValue();
		boolean flag = true;
		HandshakeResponse handshakeResponse = null;
		LogEndmsg endmsg = new LogEndmsg();
		try {
			if ("true".equalsIgnoreCase(BankAdapter.getConfig("tradeState"))) {
				returnValue = getPROCESSOR().isTradeDate(new java.util.Date());
				log("交易系统状态[" + returnValue.result + "]");
				if (returnValue.result != 0L) {
					flag = false;
				}
			}

			if (flag) {
				Handshake handshake = this.objTransformer.getHandshake(BusinessCode.CODE20001, null);
				try {
					handshakeResponse = (HandshakeResponse) this.bankBusiness.getResponse(handshake);
				} catch (BankException e) {
					returnValue.result = -1L;
					returnValue.remark = "签到失败";
					BankAdapter.log("通讯异常,签到失败");
					BankAdapter.log(Common.getExceptionTrace(e));
					endmsg.remark = "系统异常";
					handshakeResponse.Rst.Code = "-1";
					handshakeResponse.Rst.Info = "通讯异常";
					ReturnValue localReturnValue1 = returnValue;

					InterfaceLog log = new InterfaceLog();

					log.account = "";
					log.bankID = this.BANKID;
					log.contact = "";
					endmsg.code = returnValue.result + "";
					log.type = 1;
					endmsg.note = endmsg.remark;
					if (returnValue.result != 0L) {
						log.result = 1;
					}
					log.endMsg = endmsg.toString();
					interfaceLog(log);

					return localReturnValue1;
				} catch (Exception e) {
					returnValue.result = -1L;
					returnValue.remark = "签到失败";
					BankAdapter.log("签到失败");
					log(Common.getExceptionTrace(e));
					endmsg.remark = "系统异常";
					handshakeResponse.Rst.Code = "-1";
					handshakeResponse.Rst.Info = "通讯异常";
					ReturnValue localReturnValue1 = returnValue;

					InterfaceLog log = new InterfaceLog();

					log.account = "";
					log.bankID = this.BANKID;
					log.contact = "";
					endmsg.code = returnValue.result + "";
					log.type = 1;
					endmsg.note = endmsg.remark;
					if (returnValue.result != 0L) {
						log.result = 1;
					}
					log.endMsg = endmsg.toString();
					interfaceLog(log);

					return localReturnValue1;
				}
				if (handshakeResponse.Rst.Code.equals(ReturnCode.CODE0000.getValue())) {
					returnValue.result = 0L;
					returnValue.funID = handshakeResponse.BkSeq;

					FileUtil.write(Common.df7.format(new java.util.Date()), "loginDate.txt");
				} else {
					returnValue.result = ((Long) this.ErrorCodeB2M.get(handshakeResponse.Rst.Code)).longValue();
				}
			} else {
				BankAdapter.log("时间[" + new java.util.Date().toLocaleString() + "]不是交易日");
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
			flag = false;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			InterfaceLog log = new InterfaceLog();

			log.account = "";
			log.bankID = this.BANKID;
			log.contact = "";
			endmsg.code = returnValue.result + "";
			log.type = 1;
			endmsg.note = endmsg.remark;
			if (returnValue.result != 0L) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		return returnValue;
	}

	public ReturnValue loginBank1() {
		ReturnValue rv = null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new java.util.Date());
		while (true) {
			gc.add(5, 1);
			String day = Common.df7.format(gc.getTime());
			rv = loginBank();
			if (rv.result == 0L)
				break;
			try {
				sleep(1000L);
			} catch (InterruptedException e) {
				log(Common.getExceptionTrace(e));
			}
		}
		return null;
	}

	public ReturnValue quitBank() throws BankException {
		BankAdapter.log("\n");
		BankAdapter.log("签退");
		ReturnValue returnValue = new ReturnValue();
		returnValue.result = -1L;
		boolean flag = true;
		HandshakeResponse handshakeResponse = null;
		LogEndmsg endmsg = new LogEndmsg();
		try {
			if ("true".equalsIgnoreCase(BankAdapter.getConfig("tradeState"))) {
				returnValue = getPROCESSOR().isTradeDate(new java.util.Date());
				log("交易系统状态[" + returnValue.result + "]");
				if (returnValue.result != 0L) {
					flag = false;
				}

			}

			if (flag) {
				Handshake handshake = this.objTransformer.getHandshake(BusinessCode.CODE20002, null);
				try {
					handshakeResponse = (HandshakeResponse) this.bankBusiness.getResponse(handshake);
				} catch (BankException e) {
					returnValue.result = -1L;
					returnValue.remark = "签退失败";
					BankAdapter.log("通讯异常,签退失败");
					BankAdapter.log(Common.getExceptionTrace(e));
					endmsg.remark = "系统异常";
					handshakeResponse.Rst.Code = "-1";
					handshakeResponse.Rst.Info = "通讯异常";
					ReturnValue localReturnValue1 = returnValue;

					InterfaceLog log = new InterfaceLog();

					log.account = "";
					log.bankID = this.BANKID;
					log.contact = "";
					log.type = 2;
					endmsg.code = returnValue.result + "";
					log.type = 1;
					endmsg.note = endmsg.remark;

					if (returnValue.result != 0L) {
						log.result = 1;
					}
					log.endMsg = endmsg.toString();
					interfaceLog(log);

					return localReturnValue1;
				} catch (Exception e) {
					returnValue.result = -1L;
					returnValue.remark = "签退失败";
					BankAdapter.log("签退失败");
					log(Common.getExceptionTrace(e));
					endmsg.remark = "系统异常";
					handshakeResponse.Rst.Code = "-1";
					handshakeResponse.Rst.Info = "通讯异常";
					ReturnValue localReturnValue1 = returnValue;

					InterfaceLog log = new InterfaceLog();

					log.account = "";
					log.bankID = this.BANKID;
					log.contact = "";
					log.type = 2;
					endmsg.code = returnValue.result + "";
					log.type = 1;
					endmsg.note = endmsg.remark;

					if (returnValue.result != 0L) {
						log.result = 1;
					}
					log.endMsg = endmsg.toString();
					interfaceLog(log);

					return localReturnValue1;
				}
				if (handshakeResponse.Rst.Code.equals(ReturnCode.CODE0000.getValue())) {
					returnValue.funID = handshakeResponse.BkSeq;
					returnValue.result = 0L;
				} else {
					returnValue.result = ((Long) this.ErrorCodeB2M.get(handshakeResponse.Rst.Code)).longValue();
				}
			} else {
				BankAdapter.log("时间[" + new java.util.Date().toLocaleString() + "]不是交易日");
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
			flag = false;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			InterfaceLog log = new InterfaceLog();

			log.account = "";
			log.bankID = this.BANKID;
			log.contact = "";
			log.type = 2;
			endmsg.code = returnValue.result + "";
			log.type = 1;
			endmsg.note = endmsg.remark;

			if (returnValue.result != 0L) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		return returnValue;
	}

	public ReturnValue synchronizeKey() {
		BankAdapter.log("\n");
		BankAdapter.log("密钥同步");

		BankNativeImpl bni = new BankNativeImpl();
		String key = bni.GenZAK();
		ReturnValue returnValue = new ReturnValue();

		Handshake handshake = this.objTransformer.getHandshake(BusinessCode.CODE20004, key);

		HandshakeResponse handshakeResponse = null;
		try {
			handshakeResponse = (HandshakeResponse) this.bankBusiness.getResponse(handshake);

			if (handshakeResponse.Rst.Code.equals(ReturnCode.CODE0000.getValue())) {
				returnValue.funID = handshakeResponse.BkSeq;
				returnValue.result = 0L;
				returnValue.remark = "交易成功";
				try {
					FileUtil.write(key, "key.txt");
				} catch (IOException e) {
					BankAdapter.log("修改密钥文件失败");
					log(Common.getExceptionTrace(e));
				}
			} else {
				returnValue.result = ((Long) this.ErrorCodeB2M.get(handshakeResponse.Rst.Code)).longValue();
				returnValue.remark = "交易失敗";
			}
		} catch (BankException e) {
			returnValue.result = -1L;
			log(Common.getExceptionTrace(e));

			ReturnValue localReturnValue1 = returnValue;
			return localReturnValue1;
		} finally {
			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = "";
			log.bankID = this.BANKID;
			log.contact = "";
			endmsg.code = returnValue.result + "";
			log.type = 10;
			endmsg.note = returnValue.remark;
			if (returnValue.result != 0L) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		return returnValue;
	}

	public HandshakeResponse checkComm(Object reqeust) {
		Handshake handshake = (Handshake) reqeust;

		ReturnValue returnValue = new ReturnValue();
		if (!MACVerify.verify(handshake, handshake.Mac))
			returnValue.result = -2L;
		else {
			returnValue.result = 0L;
		}
		BankAdapter.log("\n");
		BankAdapter.log("通信检测结果");
		BankAdapter.log(returnValue.toString());
		HandshakeResponse handshakeResponse = this.objTransformer.getHandshakeResponse(handshake, returnValue);
		BankAdapter.log("通信检测结果-返回给银行：");
		BankAdapter.log("返回码：" + handshakeResponse.Rst.Code);
		BankAdapter.log("银行流水号：" + handshakeResponse.BkSeq);
		BankAdapter.log("市场流水号：" + handshakeResponse.FtSeq);
		BankAdapter.log("\n");
		return handshakeResponse;
	}

	public ReturnValue checkFirmInfo(CorrespondValue correspondValue) {
		Vector ve = null;
		ReturnValue returnValue = new ReturnValue();
		returnValue.result = -1L;
		try {
			String filter = " where bankid='" + correspondValue.bankID + "'" + " and firmid='" + correspondValue.firmID + "' ";
			ve = getPROCESSOR().getCorrespondValue(filter);

			if ((ve != null) && (ve.size() > 0)) {
				CorrespondValue correspondValueMarket = (CorrespondValue) ve.get(0);
				String marketCard = correspondValueMarket.card;
				String bankCard = correspondValue.card;

				if (correspondValue.cardType.equals("1")) {
					int indexbank;
					if (bankCard.length() == 15) {
						indexbank = 6;
					} else {
						indexbank = 8;
					}
					int indexmarket;
					if (marketCard.length() == 15) {
						indexmarket = 6;
					} else {
						indexmarket = 8;
					}

					try {
						bankCard = bankCard.substring(0, 6) + bankCard.substring(indexbank, indexbank + 9);
						marketCard = marketCard.substring(0, 6) + marketCard.substring(indexmarket, indexmarket + 9);
					} catch (Exception e) {
						returnValue.result = -1012L;
						returnValue.remark = "证件类型有误";
						log(Common.getExceptionTrace(e));
						return returnValue;
					}
				}
				if (correspondValueMarket.isOpen == 1) {
					returnValue.result = -2014L;
					returnValue.remark = "该资金账户已开户";
				} else if ((correspondValueMarket.account == null) || (!correspondValueMarket.account.equals(correspondValue.account))) {
					returnValue.result = -3001L;
					returnValue.remark = "银行帐号校验错";
				} else if (!bankCard.equals(marketCard)) {
					returnValue.result = -1012L;
					returnValue.remark = "证件不符";
				} else if ((correspondValueMarket.accountName == null) || (!correspondValueMarket.accountName.equals(correspondValue.accountName))) {
					returnValue.result = -2047L;
					returnValue.remark = "账户姓名不符";
				} else {
					returnValue.result = 0L;
					returnValue.remark = "校验通过";
				}
			} else {
				returnValue.result = -2011L;
				returnValue.remark = ("资金账户" + correspondValue.firmID + "不存在");
			}
		} catch (RemoteException e) {
			returnValue.result = -1L;
			log(Common.getExceptionTrace(e));
		} catch (Exception e) {
			returnValue.result = -1L;
			log(Common.getExceptionTrace(e));
		}
		return returnValue;
	}

	public RgstAccountResponse rgstAccount(Object request) {
		RgstAccount rgstAccount = (RgstAccount) request;
		RgstAccountResponse rgstAccountResponse = null;
		ReturnValue returnValue = new ReturnValue();
		CorrespondValue correspondValue = null;
		try {
			if (!MACVerify.verify(rgstAccount, rgstAccount.Mac)) {
				returnValue.result = -2L;
			} else {
				correspondValue = this.objTransformer.getCorrespondValue(rgstAccount);
				String contact = correspondValue.firmID;
				String firmid = "";

				firmid = getPROCESSOR().getFirmID(contact, correspondValue.bankID);

				BankAdapter.log("bankID=" + correspondValue.bankID);
				BankAdapter.log("account=" + correspondValue.account);
				BankAdapter.log("firmID=" + firmid);
				BankAdapter.log("contact=" + contact);

				Vector ve = null;
				try {
					String filter = " where bankid='" + correspondValue.bankID + "'" + " and firmid='" + firmid + "' ";
					ve = getPROCESSOR().getCorrespondValue(filter);
				} catch (RemoteException e1) {
					returnValue.result = -1L;
					e1.printStackTrace();
				}

				returnValue = checkFirmInfo(correspondValue);

				CorrespondValue correspondValueNew = null;

				if (returnValue.result >= 0L) {
					log("验证通过,调用处理器开户方法");

					correspondValueNew = (CorrespondValue) ve.get(0);
					correspondValueNew.status = 0;
					correspondValueNew.card = correspondValue.card;
					correspondValueNew.cardType = correspondValue.cardType;

					correspondValueNew.opentime = Common.getDate();
					try {
						returnValue = getPROCESSOR().openAccount(correspondValueNew);
					} catch (RemoteException e) {
						returnValue = new ReturnValue();
						returnValue.result = -1L;
						log(Common.getExceptionTrace(e));
					} catch (Exception e) {
						returnValue = new ReturnValue();
						returnValue.result = -1L;
						log(Common.getExceptionTrace(e));
					}
				} else {
					log("验证失败");
				}

			}

			BankAdapter.log("\n");
			BankAdapter.log("开户结果:");
			BankAdapter.log(returnValue.toString());
			rgstAccountResponse = this.objTransformer.getRgstAccountResponse(rgstAccount, returnValue);
			BankAdapter.log("开户结果-返回给银行：");
			BankAdapter.log("返回码：" + rgstAccountResponse.Rst.Code);
			BankAdapter.log("银行流水号：" + rgstAccountResponse.BkSeq);
			BankAdapter.log("市场流水号：" + rgstAccountResponse.FtSeq);
			BankAdapter.log("\n");
		} catch (RemoteException e2) {
			BankAdapter.log("获取交易商代码异常：" + Tool.getExceptionTrace(e2));
		} finally {
			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = correspondValue.account;
			log.bankID = this.BANKID;
			log.contact = correspondValue.firmID;
			log.firmID = correspondValue.firmID;
			endmsg.code = returnValue.result + "";
			log.type = 3;
			endmsg.note = returnValue.remark;
			if (returnValue.result != 0L) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		return rgstAccountResponse;
	}

	public DelAccountResponse delAccount(Object request) {
		DelAccount delAccount = (DelAccount) request;
		CorrespondValue correspondValue = null;
		ReturnValue returnValue = new ReturnValue();
		DelAccountResponse delAccountResponse = null;
		try {
			if (!MACVerify.verify(delAccount, delAccount.Mac)) {
				returnValue.result = -2L;
			} else {
				correspondValue = this.objTransformer.getCorrespondValue(delAccount);
				String contact = correspondValue.firmID;
				String firmid = "";

				firmid = getPROCESSOR().getFirmID(contact, correspondValue.bankID);
				returnValue = getPROCESSOR().ifQuitFirm(firmid, correspondValue.bankID);
				correspondValue.firmID = firmid;
				correspondValue.contact = contact;
				if (returnValue.result == 0L) {
					returnValue.result = getPROCESSOR().delAccount(correspondValue);
				}

			}

			BankAdapter.log("\n");
			BankAdapter.log("销户结果:");
			BankAdapter.log(returnValue.toString());
			delAccountResponse = this.objTransformer.getDelAccountResponse(delAccount, returnValue);
			BankAdapter.log("销户结果-返回给银行：");
			BankAdapter.log("返回码：" + delAccountResponse.Rst.Code);
			BankAdapter.log("银行流水号：" + delAccountResponse.BkSeq);
			BankAdapter.log("市场流水号：" + delAccountResponse.FtSeq);
			BankAdapter.log("\n");
		} catch (RemoteException e) {
			returnValue = new ReturnValue();
			returnValue.result = -1L;
			log(Common.getExceptionTrace(e));
		} catch (Exception e) {
			returnValue = new ReturnValue();
			returnValue.result = -1L;
			log(Common.getExceptionTrace(e));
		} finally {
			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = correspondValue.account;
			log.bankID = this.BANKID;
			log.contact = correspondValue.firmID;
			log.firmID = correspondValue.firmID;
			endmsg.code = returnValue.result + "";

			endmsg.note = returnValue.remark;
			log.type = 4;

			if (returnValue.result != 0L) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		return delAccountResponse;
	}

	public ModAccountResponse modAccount(Object request) {
		ModAccount modAccount = (ModAccount) request;
		CorrespondValue correspondValue = null;
		ReturnValue returnValue = new ReturnValue();
		ModAccountResponse modAccountResponse = null;
		try {
			if (!MACVerify.verify(modAccount, modAccount.Mac)) {
				returnValue.result = -2L;
			} else {
				correspondValue = this.objTransformer.getCorrespondValue(modAccount);
				CorrespondValue correspondValueNew = new CorrespondValue();
				Vector ve = null;

				String contact = correspondValue.firmID;
				correspondValue.firmID = getPROCESSOR().getFirmID(contact, correspondValue.bankID);
				correspondValue.contact = contact;
				log(" where bankid='" + correspondValue.bankID + "'" + " and firmid='" + correspondValue.firmID + "'" + " and account='"
						+ correspondValue.account + "'" + " and cardtype = " + correspondValue.cardType + " and card = '" + correspondValue.card
						+ "'");

				ve = getPROCESSOR().getCorrespondValue(" where bankid='" + correspondValue.bankID + "'" + " and firmid='" + correspondValue.firmID
						+ "'" + " and account='" + correspondValue.account + "'" + " and cardtype = " + correspondValue.cardType + " and card = '"
						+ correspondValue.card + "'");

				System.out.println("PROCESSOR=" + getPROCESSOR());
				System.out.println("ve=" + ve);

				if (ve.size() > 0) {
					log("验证通过,调用账户修改接口");
					correspondValue = (CorrespondValue) ve.get(0);
					correspondValueNew.account = modAccount.NewBkAcct.Id;
					correspondValueNew.account1 = correspondValue.account1;
					System.out.println("Name=" + modAccount.Cust.Name);
					correspondValueNew.accountName = modAccount.Cust.Name;
					correspondValueNew.bankCity = correspondValue.bankCity;
					correspondValueNew.bankID = correspondValue.bankID;
					correspondValueNew.bankName = correspondValue.bankName;
					correspondValueNew.bankProvince = correspondValue.bankProvince;
					correspondValueNew.card = correspondValue.card;
					correspondValueNew.cardType = correspondValue.cardType;
					correspondValueNew.email = correspondValue.email;
					correspondValueNew.firmID = correspondValue.firmID;
					correspondValueNew.isOpen = correspondValue.isOpen;
					correspondValueNew.mobile = correspondValue.mobile;
					correspondValueNew.status = correspondValue.status;
					try {
						log("correspondValue==>");
						log(correspondValue.toString());
						log("correspondValueNew==>");
						log(correspondValueNew.toString());
						returnValue = getPROCESSOR().modAccount(correspondValue, correspondValueNew);
					} catch (RemoteException e) {
						returnValue.result = -1L;
						log(Common.getExceptionTrace(e));
					} catch (Exception e) {
						returnValue.result = -1L;
						log(Common.getExceptionTrace(e));
					}
				} else {
					returnValue.result = -10019L;
					log("交易系统中无此交易商");
				}
			}

			BankAdapter.log("\n");
			BankAdapter.log("账户变更结果:");
			BankAdapter.log(returnValue.toString());
			modAccountResponse = this.objTransformer.getModAccountResponse(modAccount, returnValue);
			BankAdapter.log("账户变更结果-返回给银行：");
			BankAdapter.log("返回码：" + modAccountResponse.Rst.Code);
			BankAdapter.log("银行流水号：" + modAccountResponse.BkSeq);
			BankAdapter.log("市场流水号：" + modAccountResponse.FtSeq);
			BankAdapter.log("\n");
		} catch (RemoteException e) {
			log(Common.getExceptionTrace(e));
		} catch (Exception e) {
			log(Common.getExceptionTrace(e));
		} finally {
			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = correspondValue.account;
			log.bankID = this.BANKID;
			log.contact = correspondValue.firmID;
			endmsg.code = returnValue.result + "";

			endmsg.note = returnValue.remark;
			log.type = 9;

			if (returnValue.result != 0L) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		return modAccountResponse;
	}

	public UnCertainResponse unCertain(Object request, String returnCode) {
		UnCertain unCertain = (UnCertain) request;

		UnCertainResponse unCertainResponse = this.objTransformer.getUnCertainResponse(unCertain, returnCode);
		BankAdapter.log("\n");
		BankAdapter.log("未知业务结果-返回给银行：");
		BankAdapter.log("返回码：" + unCertainResponse.Rst.Code);
		BankAdapter.log("\n");
		return unCertainResponse;
	}

	public ReturnValue chongZhengByHand(ChargeAgainstValue cv) {
		ReturnValue result = new ReturnValue();
		cv.bankID = this.BANKID;
		cv.bankTime = new Timestamp(Common.getDate().getTime());
		System.out.println("原银行流水号[" + cv.funID + "]新银行流水号[" + cv.funIDCA + "]");
		try {
			result = getPROCESSOR().chargeAgainst(cv);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("冲正结果：" + result.result);
		return result;
	}

	public void getTradeDataRst(java.util.Date date) {
		FileInfo fileInfo = new FileInfo();
		fileInfo.FileName = ("B_DTL03_" + Common.df7.format(date));
		getBankMoneyInfo(fileInfo);
		fileInfo = new FileInfo();
		fileInfo.FileName = ("B_DTL04_" + Common.df7.format(date));
		getBankMoneyInfo(fileInfo);
	}

	public Vector<BankFirmRightValue> getBankFirmRightValue(String bankID2, java.util.Date tradeDate) {
		String basePath = "dayData/";
		FtpUtil ftp = new FtpUtil();
		String filename = "B_DTL03_" + Common.df7.format(tradeDate) + ".gz";
		System.out.println("===========================");
		System.out.println("filename:" + filename);
		System.out.println("===========================");
		String host = BankAdapter.getConfig("ftpIp");
		String ftpName = BankAdapter.getConfig("ftpName");
		String ftpPassword = BankAdapter.getConfig("ftpPassword");
		String ftpPath = BankAdapter.getConfig("ftpPath");
		try {
			ftp.connectServer(host, ftpName, ftpPassword, ftpPath);
			ftp.download(filename, basePath + filename);
		} catch (IOException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("下载日终对账文件失败");
		} catch (Exception e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("下载日终对账文件失败");
		}

		String filenameNew = "";
		filenameNew = filename.split("\\.")[0];
		try {
			GzipUtil.upZip(basePath + filename, basePath + filenameNew);
		} catch (FileNotFoundException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("解压日终对账文件失败");
		} catch (IOException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("解压日终对账文件失败");
		}

		String path = basePath + filenameNew;
		FileProcessor fp = new FileProcessor(path);
		Vector bankFirmRightValues = new Vector();

		Vector ve = null;
		try {
			System.out.println("=================================");
			ve = fp.getDTL03();
			System.out.println(ve);
			System.out.println("=================================");
		} catch (NumberFormatException nfe) {
			BankAdapter.log("对账文件内容有误,对账失败");
			BankAdapter.log(nfe.getMessage());
		}
		for (int i = 0; i < ve.size(); i++) {
			DTL03 dtl03 = (DTL03) ve.get(i);
			BankFirmRightValue info = new BankFirmRightValue();
			String year = dtl03.date.substring(0, 4);
			String month = dtl03.date.substring(4, 6);
			String date = dtl03.date.substring(6, 8);
			java.util.Date tempDate = new java.util.Date(Integer.parseInt(year) - 1900, Integer.parseInt(month) - 1, Integer.parseInt(date));
			info.bdate = new Timestamp(tempDate.getTime());
			info.firmId = dtl03.futuresId;
			info.bankRight = Arith.div((float) dtl03.BkBal, 100.0F);
			info.maketRight = Arith.div((float) dtl03.FtBal, 100.0F);
			info.reason = dtl03.reason;
			info.bankId = this.BANKID;

			BankAdapter.log(dtl03.toString());
			BankAdapter.log(info.toString());
			bankFirmRightValues.add(info);
		}
		return bankFirmRightValues;
	}

	public ProperBalanceValue getProperBalanceValue(String bankID, java.util.Date tradeDate) {
		String basePath = "dayData/";
		FtpUtil ftp = new FtpUtil();
		String filename = "B_DTL04_" + Common.df7.format(tradeDate) + ".gz";
		System.out.println("===========================");
		System.out.println("filename:" + filename);
		System.out.println("===========================");
		String host = BankAdapter.getConfig("ftpIp");
		String ftpName = BankAdapter.getConfig("ftpName");
		String ftpPassword = BankAdapter.getConfig("ftpPassword");
		String ftpPath = BankAdapter.getConfig("ftpPath");
		try {
			ftp.connectServer(host, ftpName, ftpPassword, ftpPath);
			ftp.download(filename, basePath + filename);
		} catch (IOException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("下载日终对账文件失败");
		} catch (Exception e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("下载日终对账文件失败");
		}

		String filenameNew = "";
		filenameNew = filename.split("\\.")[0];
		try {
			GzipUtil.upZip(basePath + filename, basePath + filenameNew);
		} catch (FileNotFoundException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("解压日终对账文件失败");
		} catch (IOException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("解压日终对账文件失败");
		}

		String path = basePath + filenameNew;
		FileProcessor fp = new FileProcessor(path);
		DTL04 dtl04 = fp.getDTL04();
		ProperBalanceValue info = new ProperBalanceValue();
		String year = dtl04.date.substring(0, 4);
		String month = dtl04.date.substring(4, 6);
		String date = dtl04.date.substring(6, 8);
		java.util.Date tempDate = new java.util.Date(Integer.parseInt(year) - 1900, Integer.parseInt(month) - 1, Integer.parseInt(date));
		info.bdate = new Timestamp(tempDate.getTime());
		info.allMoney = Arith.div((float) dtl04.bkManageSumBlance, 100.0F);
		info.gongMoney = Arith.div((float) dtl04.bkSumBlanceLocal, 100.0F);
		info.otherMoney = Arith.div((float) dtl04.bkSumBlanceOther, 100.0F);
		info.bankId = this.BANKID;

		BankAdapter.log(dtl04.toString());
		BankAdapter.log(info.toString());
		return info;
	}

	public ReturnValue sendTradeData(List<FirmRights> frs, List<TradingDetailsValue> tds, List<OpenOrDelFirmValue> opdf, java.util.Date tradeDate) {
		ReturnValue returnValue = new ReturnValue();

		Map tzMap = new HashMap();
		String fileDate = FileUtil.readline("loginDate.txt");
		String filePath = BankAdapter.getConfig("tzFilePath");
		String tzFileName = filePath + "TZ_" + fileDate + ".txt";
		if (FileUtil.fileIsExist(tzFileName))
			tzMap = FileUtil.getTZMessage(tzFileName);
		else {
			BankAdapter.log(fileDate + "当天没有调账文件，不需要调账");
		}

		Set tzFirms = tzMap.keySet();
		if (tzFirms == null) {
			tzFirms = new HashSet();
		}

		Vector veSTL01 = new Vector();
		Vector veSTL02 = new Vector();
		Vector veSTL03 = new Vector();
		for (int i = 0; i < tds.size(); i++) {
			TradingDetailsValue tradingDetailsValue = (TradingDetailsValue) tds.get(i);
			STL01 stl01 = new STL01();
			stl01.bankId = BankAdapter.getConfig("BankCode1");
			stl01.futuresId = getMarketCode();

			stl01.date = FileUtil.readline("loginDate.txt");
			stl01.ftSeq = String.valueOf(tradingDetailsValue.maketNum);
			stl01.bkAcct = tradingDetailsValue.account;
			stl01.ftAcct = tradingDetailsValue.firmId;
			stl01.custName = tradingDetailsValue.firmName;
			stl01.busType = 0;
			stl01.ccy = "001";
			stl01.cashExCd = 0;
			if (tzFirms.contains(tradingDetailsValue.firmId)) {
				if ("1".equals(tradingDetailsValue.updown))
					tradingDetailsValue.money = Arith.sub(Double.parseDouble((String) tzMap.get(tradingDetailsValue.firmId)),
							tradingDetailsValue.money);
				else if ("2".equals(tradingDetailsValue.updown)) {
					tradingDetailsValue.money = Arith.add(Double.parseDouble((String) tzMap.get(tradingDetailsValue.firmId)),
							tradingDetailsValue.money);
				}
				if (tradingDetailsValue.money > 0.0D) {
					tradingDetailsValue.updown = "2";
				} else {
					tradingDetailsValue.updown = "1";
					tradingDetailsValue.money = Arith.mul(tradingDetailsValue.money, -1.0F);
				}
			}
			stl01.trfFlag = tradingDetailsValue.updown;
			stl01.trfAmt = ((long) Arith.mul(tradingDetailsValue.money, 100.0F));
			stl01.info1 = "";
			stl01.info2 = "";
			stl01.info3 = "";
			veSTL01.add(stl01);
			System.out.println(stl01);
		}
		for (int i = 0; i < frs.size(); i++) {
			FirmRights firmRights = (FirmRights) frs.get(i);
			STL02 stl02 = new STL02();
			stl02.bankId = BankAdapter.getConfig("BankCode1");
			stl02.futuresId = getMarketCode();

			stl02.date = FileUtil.readline("loginDate.txt");
			stl02.bkAcct = firmRights.account;
			stl02.ftAcct = firmRights.firmId;
			stl02.custName = firmRights.firmName;
			stl02.ccy = "001";
			stl02.cashExCd = 0;
			stl02.FtBal = ((long) Arith.mul(firmRights.money, 100.0F));
			stl02.info1 = "";
			stl02.info2 = "";
			stl02.info3 = "";
			veSTL02.add(stl02);
			System.out.println(stl02);
		}

		for (int i = 0; i < opdf.size(); i++) {
			OpenOrDelFirmValue openOrDelFirmValue = (OpenOrDelFirmValue) opdf.get(i);
			STL03 stl03 = new STL03();
			stl03.bankId = BankAdapter.getConfig("BankCode1");
			stl03.futuresId = getMarketCode();

			stl03.date = FileUtil.readline("loginDate.txt");
			stl03.ftSeq = String.valueOf(openOrDelFirmValue.maketNum);
			stl03.ftAcct = openOrDelFirmValue.firmId;
			stl03.custName = openOrDelFirmValue.firmName;
			stl03.ccy = "001";
			stl03.cashExCd = 0;
			stl03.busType = openOrDelFirmValue.openordel;
			stl03.info1 = "";
			stl03.info2 = "";
			stl03.info3 = "";
			veSTL03.add(stl03);
			System.out.println(stl03);
		}
		DayDataReadyResponse dayDataReadyResponse = null;

		try {
			String basePath = "dayData/";
			FileProcessor fp = new FileProcessor(basePath);
			String filenameSTL01 = "";
			String filenameSTL02 = "";
			String filenameSTL03 = "";
			ReturnValue localReturnValue1;
			try {
				filenameSTL01 = fp.setSTL01(veSTL01);
			} catch (IOException e) {
				log(Common.getExceptionTrace(e));
				BankAdapter.log("生成交收明细文件失败");
				returnValue.remark = "操作失败";
				returnValue.result = -1L;
				localReturnValue1 = returnValue;

				BankAdapter.log("发送日终数据就绪结果-银行：");
				BankAdapter.log("返回码：" + dayDataReadyResponse.Rst.Code);// 有问题 TODO
				BankAdapter.log("返回码信息：" + dayDataReadyResponse.Rst.Info);
				BankAdapter.log("银行流水号：" + dayDataReadyResponse.BkSeq);
				BankAdapter.log("市场流水号：" + dayDataReadyResponse.FtSeq);

				InterfaceLog log = new InterfaceLog();
				LogEndmsg endmsg = new LogEndmsg();
				log.account = "";
				log.bankID = this.BANKID;
				log.contact = "";
				endmsg.code = returnValue.result + "";
				endmsg.note = returnValue.toString();
				log.type = 12;
				if (returnValue.result != 0L) {
					log.result = 1;
				}
				log.endMsg = endmsg.toString();
				interfaceLog(log);

				return localReturnValue1;
			}
			try {
				filenameSTL02 = fp.setSTL02(veSTL02);
			} catch (IOException e) {
				log(Common.getExceptionTrace(e));
				BankAdapter.log("生成客户资金台账资金余额明细表失败");
				returnValue.remark = "操作失败";
				returnValue.result = -1L;
				localReturnValue1 = returnValue;

				BankAdapter.log("发送日终数据就绪结果-银行：");
				BankAdapter.log("返回码：" + dayDataReadyResponse.Rst.Code);
				BankAdapter.log("返回码信息：" + dayDataReadyResponse.Rst.Info);
				BankAdapter.log("银行流水号：" + dayDataReadyResponse.BkSeq);
				BankAdapter.log("市场流水号：" + dayDataReadyResponse.FtSeq);

				InterfaceLog log = new InterfaceLog();
				LogEndmsg endmsg = new LogEndmsg();
				log.account = "";
				log.bankID = this.BANKID;
				log.contact = "";
				endmsg.code = returnValue.result + "";
				endmsg.note = returnValue.toString();
				log.type = 12;
				if (returnValue.result != 0L) {
					log.result = 1;
				}
				log.endMsg = endmsg.toString();
				interfaceLog(log);

				return localReturnValue1;
			}
			try {
				filenameSTL03 = fp.setSTL03(veSTL03);
			} catch (IOException e) {
				log(Common.getExceptionTrace(e));
				BankAdapter.log("生成客户资金台账开销户文档失败");
				returnValue.remark = "操作失败";
				returnValue.result = -1L;
				localReturnValue1 = returnValue;

				BankAdapter.log("发送日终数据就绪结果-银行：");
				BankAdapter.log("返回码：" + dayDataReadyResponse.Rst.Code);
				BankAdapter.log("返回码信息：" + dayDataReadyResponse.Rst.Info);
				BankAdapter.log("银行流水号：" + dayDataReadyResponse.BkSeq);
				BankAdapter.log("市场流水号：" + dayDataReadyResponse.FtSeq);

				InterfaceLog log = new InterfaceLog();
				LogEndmsg endmsg = new LogEndmsg();
				log.account = "";
				log.bankID = this.BANKID;
				log.contact = "";
				endmsg.code = returnValue.result + "";
				endmsg.note = returnValue.toString();
				log.type = 12;
				if (returnValue.result != 0L) {
					log.result = 1;
				}
				log.endMsg = endmsg.toString();
				interfaceLog(log);

				return localReturnValue1;
			}

			String filenameSTL01New = filenameSTL01 + ".gz";
			String filenameSTL02New = filenameSTL02 + ".gz";
			String filenameSTL03New = filenameSTL03 + ".gz";
			GzipUtil.zipFile(basePath + filenameSTL01, basePath + filenameSTL01New);
			GzipUtil.zipFile(basePath + filenameSTL02, basePath + filenameSTL02New);
			GzipUtil.zipFile(basePath + filenameSTL03, basePath + filenameSTL03New);
			System.out.println("filenameSTL01New" + filenameSTL01New);
			System.out.println("filenameSTL02New" + filenameSTL02New);
			System.out.println("filenameSTL03New" + filenameSTL03New);

			List fileList = new ArrayList();
			FileInfo fileInfo1 = new FileInfo();
			fileInfo1.FileName = filenameSTL01New;
			fileList.add(fileInfo1);
			FileInfo fileInfo2 = new FileInfo();
			fileInfo2.FileName = filenameSTL02New;
			fileList.add(fileInfo2);
			FileInfo fileInfo3 = new FileInfo();
			fileInfo3.FileName = filenameSTL03New;
			fileList.add(fileInfo3);

			FtpUtil ftp = new FtpUtil();
			String host = BankAdapter.getConfig("ftpIp_bk");
			String ftpName = BankAdapter.getConfig("ftpName_bk");
			String ftpPassword = BankAdapter.getConfig("ftpPassword_bk");
			String ftpPath = BankAdapter.getConfig("ftpPath_bk");
			try {
				ftp.connectServer(host, ftpName, ftpPassword, ftpPath);
				System.out.println("filenameSTL01New=" + filenameSTL01New);
				System.out.println("filenameSTL02New=" + filenameSTL02New);
				System.out.println("filenameSTL03New=" + filenameSTL03New);
				long rst1 = ftp.upload(basePath + filenameSTL01New);
				long rst2 = ftp.upload(basePath + filenameSTL02New);
				long rst3 = ftp.upload(basePath + filenameSTL03New);
				System.out.println("rst1=" + rst1);
				System.out.println("rst2=" + rst2);
				System.out.println("rst3=" + rst3);
			} catch (IOException e) {
				log(Common.getExceptionTrace(e));
				BankAdapter.log("上传日终对账文件失败");
				returnValue.remark = "操作失败";
				returnValue.result = -1L;
				localReturnValue1 = returnValue;

				BankAdapter.log("发送日终数据就绪结果-银行：");
				BankAdapter.log("返回码：" + dayDataReadyResponse.Rst.Code);
				BankAdapter.log("返回码信息：" + dayDataReadyResponse.Rst.Info);
				BankAdapter.log("银行流水号：" + dayDataReadyResponse.BkSeq);
				BankAdapter.log("市场流水号：" + dayDataReadyResponse.FtSeq);

				InterfaceLog log = new InterfaceLog();
				LogEndmsg endmsg = new LogEndmsg();
				log.account = "";
				log.bankID = this.BANKID;
				log.contact = "";
				endmsg.code = returnValue.result + "";
				endmsg.note = returnValue.toString();
				log.type = 12;
				if (returnValue.result != 0L) {
					log.result = 1;
				}
				log.endMsg = endmsg.toString();
				interfaceLog(log);

				return localReturnValue1;
			} catch (Exception e) {
				log(Common.getExceptionTrace(e));
				BankAdapter.log("上传日终对账文件失败");
				returnValue.remark = "操作失败";
				returnValue.result = -1L;
				localReturnValue1 = returnValue;

				BankAdapter.log("发送日终数据就绪结果-银行：");
				BankAdapter.log("返回码：" + dayDataReadyResponse.Rst.Code);
				BankAdapter.log("返回码信息：" + dayDataReadyResponse.Rst.Info);
				BankAdapter.log("银行流水号：" + dayDataReadyResponse.BkSeq);
				BankAdapter.log("市场流水号：" + dayDataReadyResponse.FtSeq);

				InterfaceLog log = new InterfaceLog();
				LogEndmsg endmsg = new LogEndmsg();
				log.account = "";
				log.bankID = this.BANKID;
				log.contact = "";
				endmsg.code = returnValue.result + "";
				endmsg.note = returnValue.toString();
				log.type = 12;
				if (returnValue.result != 0L) {
					log.result = 1;
				}
				log.endMsg = endmsg.toString();
				interfaceLog(log);

				return localReturnValue1;
			}

			DayDataReady dayDataReady = this.objTransformer.getDayDataReady(fileList);
			dayDataReadyResponse = (DayDataReadyResponse) this.bankBusiness.getResponse(dayDataReady);
			if ("0000".equals(dayDataReadyResponse.Rst.Code)) {
				returnValue.result = 0L;
				returnValue.remark = "操作成功";
			} else {
				returnValue.result = -1L;
				returnValue.remark = dayDataReadyResponse.Rst.Info;
			}
		} catch (Exception e) {
			e.printStackTrace();
			dayDataReadyResponse.Rst.Code = "-1";
			dayDataReadyResponse.Rst.Info = "處理异常";
		} finally {

			BankAdapter.log("发送日终数据就绪结果-银行：");
			BankAdapter.log("返回码：" + dayDataReadyResponse.Rst.Code);
			BankAdapter.log("返回码信息：" + dayDataReadyResponse.Rst.Info);
			BankAdapter.log("银行流水号：" + dayDataReadyResponse.BkSeq);
			BankAdapter.log("市场流水号：" + dayDataReadyResponse.FtSeq);

			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = "";
			log.bankID = this.BANKID;
			log.contact = "";
			endmsg.code = returnValue.result + "";
			endmsg.note = returnValue.toString();
			log.type = 12;
			if (returnValue.result != 0L) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		return returnValue;
	}

	public void sendTradeData() {
		Vector veSTL01 = new Vector();
		Vector veSTL02 = new Vector();
		Vector veSTL03 = new Vector();

		List TradingDetailsValues = null;
		List FirmRightsValues = null;
		List OpenOrDelFirmValues = null;
		try {
			TradingDetailsValues = getPROCESSOR().getChangeBalance(this.BANKID, null, Common.getDate());
			FirmRightsValues = getPROCESSOR().getRightsList(this.BANKID, null, Common.getDate());
			OpenOrDelFirmValues = getPROCESSOR().getOpenOrDropMaket(this.BANKID, Common.getDate());

			System.out.println("TradingDetailsValues=" + TradingDetailsValues.size());
			System.out.println("FirmRightsValues=" + FirmRightsValues.size());
			System.out.println("OpenOrDelFirmValues=" + OpenOrDelFirmValues.size());
		} catch (RemoteException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("获取交收数据、交易商权益、开销户数据失败");
			return;
		}
		for (int i = 0; i < TradingDetailsValues.size(); i++) {
			TradingDetailsValue tradingDetailsValue = (TradingDetailsValue) TradingDetailsValues.get(i);
			STL01 stl01 = new STL01();
			stl01.bankId = BankAdapter.getConfig("BankCode1");
			stl01.futuresId = getMarketCode();
			stl01.date = Common.df7.format(Common.getDate());
			stl01.ftSeq = String.valueOf(tradingDetailsValue.maketNum);
			stl01.bkAcct = tradingDetailsValue.account;
			stl01.ftAcct = tradingDetailsValue.firmId;
			stl01.custName = tradingDetailsValue.firmName;
			stl01.busType = 0;
			stl01.trfFlag = tradingDetailsValue.updown;
			stl01.ccy = "001";
			stl01.cashExCd = 0;
			stl01.trfAmt = ((long) Arith.mul(tradingDetailsValue.money, 100.0F));
			stl01.info1 = "";
			stl01.info2 = "";
			stl01.info3 = "";
			veSTL01.add(stl01);
		}

		for (int i = 0; i < FirmRightsValues.size(); i++) {
			FirmRights firmRights = (FirmRights) FirmRightsValues.get(i);
			STL02 stl02 = new STL02();
			stl02.bankId = BankAdapter.getConfig("BankCode1");
			stl02.futuresId = getMarketCode();
			stl02.date = Common.df7.format(Common.getDate());
			stl02.bkAcct = firmRights.account;
			stl02.ftAcct = firmRights.firmId;
			stl02.custName = firmRights.firmName;
			stl02.ccy = "001";
			stl02.cashExCd = 0;
			stl02.FtBal = ((long) Arith.mul(firmRights.money, 100.0F));
			stl02.info1 = "";
			stl02.info2 = "";
			stl02.info3 = "";
			veSTL02.add(stl02);
		}

		for (int i = 0; i < OpenOrDelFirmValues.size(); i++) {
			OpenOrDelFirmValue openOrDelFirmValue = (OpenOrDelFirmValue) OpenOrDelFirmValues.get(i);
			STL03 stl03 = new STL03();
			stl03.bankId = BankAdapter.getConfig("BankCode1");
			stl03.futuresId = getMarketCode();
			stl03.date = Common.df7.format(Common.getDate());
			stl03.ftSeq = String.valueOf(openOrDelFirmValue.maketNum);
			stl03.ftAcct = openOrDelFirmValue.firmId;
			stl03.custName = openOrDelFirmValue.firmName;
			stl03.ccy = "001";
			stl03.cashExCd = 0;
			stl03.busType = openOrDelFirmValue.openordel;
			stl03.info1 = "";
			stl03.info2 = "";
			stl03.info3 = "";
			veSTL03.add(stl03);
		}

		String basePath = "dayData/";
		FileProcessor fp = new FileProcessor(basePath);
		String filenameSTL01 = "";
		String filenameSTL02 = "";
		String filenameSTL03 = "";
		try {
			filenameSTL01 = fp.setSTL01(veSTL01);
		} catch (IOException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("生成交收明细文件失败");
			return;
		}
		try {
			filenameSTL02 = fp.setSTL02(veSTL02);
		} catch (IOException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("生成客户资金台账资金余额明细表失败");
			return;
		}
		try {
			filenameSTL03 = fp.setSTL03(veSTL03);
		} catch (IOException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("生成客户资金台账开销户文档失败");
			return;
		}

		String filenameSTL01New = filenameSTL01 + ".gz";
		String filenameSTL02New = filenameSTL02 + ".gz";
		String filenameSTL03New = filenameSTL03 + ".gz";
		GzipUtil.zipFile(basePath + filenameSTL01, basePath + filenameSTL01New);
		GzipUtil.zipFile(basePath + filenameSTL02, basePath + filenameSTL02New);
		GzipUtil.zipFile(basePath + filenameSTL03, basePath + filenameSTL03New);

		List fileList = new ArrayList();
		FileInfo fileInfo1 = new FileInfo();
		fileInfo1.FileName = filenameSTL01New;
		fileList.add(fileInfo1);
		FileInfo fileInfo2 = new FileInfo();
		fileInfo2.FileName = filenameSTL02New;
		fileList.add(fileInfo2);
		FileInfo fileInfo3 = new FileInfo();
		fileInfo3.FileName = filenameSTL03New;
		fileList.add(fileInfo3);

		FtpUtil ftp = new FtpUtil();
		String host = BankAdapter.getConfig("ftpIp_bk");
		String ftpName = BankAdapter.getConfig("ftpName_bk");
		String ftpPassword = BankAdapter.getConfig("ftpPassword_bk");
		String ftpPath = BankAdapter.getConfig("ftpPath_bk");
		try {
			ftp.connectServer(host, ftpName, ftpPassword, ftpPath);
			System.out.println("filenameSTL01New=" + filenameSTL01New);
			System.out.println("filenameSTL02New=" + filenameSTL02New);
			System.out.println("filenameSTL03New=" + filenameSTL03New);
			long rst1 = ftp.upload(basePath + filenameSTL01New);
			long rst2 = ftp.upload(basePath + filenameSTL02New);
			long rst3 = ftp.upload(basePath + filenameSTL03New);
			System.out.println("rst1=" + rst1);
			System.out.println("rst2=" + rst2);
			System.out.println("rst3=" + rst3);
		} catch (IOException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("上传日终对账文件失败");
			return;
		} catch (Exception e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("上传日终对账文件失败");
			return;
		}

		DayDataReady dayDataReady = this.objTransformer.getDayDataReady(fileList);
		DayDataReadyResponse dayDataReadyResponse = (DayDataReadyResponse) this.bankBusiness.getResponse(dayDataReady);

		BankAdapter.log("发送日终数据就绪结果-银行：");
		BankAdapter.log("返回码：" + dayDataReadyResponse.Rst.Code);
		BankAdapter.log("返回码信息：" + dayDataReadyResponse.Rst.Info);
		BankAdapter.log("银行流水号：" + dayDataReadyResponse.BkSeq);
		BankAdapter.log("市场流水号：" + dayDataReadyResponse.FtSeq);
	}

	public DayDataReadyResponse dayDataReady(Object request) {
		DayDataReady dayDataReady = (DayDataReady) request;

		ReturnValue returnValue = new ReturnValue();
		if (!MACVerify.verify(dayDataReady, dayDataReady.Mac)) {
			returnValue.result = -2L;
		} else {
			List fileInfos = dayDataReady.FileInfo;

			for (int i = 0; i < fileInfos.size(); i++) {
				getBankMoneyInfo((FileInfo) fileInfos.get(i));
			}
			try {
				returnValue.actionId = getPROCESSOR().getMktActionID();
				returnValue.result = 0L;
			} catch (RemoteException e) {
				returnValue.result = -1L;
				log(Common.getExceptionTrace(e));
			}
		}
		BankAdapter.log("\n");
		BankAdapter.log("日终数据结果:");
		BankAdapter.log(returnValue.toString());
		DayDataReadyResponse dayDataReadyResponse = this.objTransformer.getDayDataReadyResponse(dayDataReady, returnValue);
		BankAdapter.log("日终数据结果-返回给银行：");
		BankAdapter.log("返回码：" + dayDataReadyResponse.Rst.Code);
		BankAdapter.log("银行流水号：" + dayDataReadyResponse.BkSeq);
		BankAdapter.log("市场流水号：" + dayDataReadyResponse.FtSeq);
		BankAdapter.log("\n");
		return dayDataReadyResponse;
	}

	public void getBankMoneyInfo(FileInfo fileInfo) {
		String basePath = "dayData/";

		FtpUtil ftp = new FtpUtil();
		String filename = fileInfo.FileName;
		String host = BankAdapter.getConfig("ftpIp");
		String ftpName = BankAdapter.getConfig("ftpName");
		String ftpPassword = BankAdapter.getConfig("ftpPassword");
		String ftpPath = BankAdapter.getConfig("ftpPath");
		try {
			ftp.connectServer(host, ftpName, ftpPassword, ftpPath);
			ftp.download(filename, basePath + filename);
		} catch (IOException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("下载日终对账文件失败");
			return;
		} catch (Exception e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("下载日终对账文件失败");
			return;
		}

		String filenameNew = "";
		filenameNew = filename.split("\\.")[0];
		try {
			GzipUtil.upZip(basePath + filename, basePath + filenameNew);
		} catch (FileNotFoundException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("解压日终对账文件失败");
			return;
		} catch (IOException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("解压日终对账文件失败");
			return;
		}

		String path = basePath + filenameNew;
		FileProcessor fp = new FileProcessor(path);
		Vector result = new Vector();
		Vector bankFirmRightValues = new Vector();

		if (getFileType(filenameNew).equals("DTL01")) {
			Vector ve = null;
			try {
				ve = fp.getDTL01();
			} catch (NumberFormatException nfe) {
				BankAdapter.log("对账文件内容有误,对账失败");
				BankAdapter.log(nfe.getMessage());
				return;
			}
			for (int i = 0; i < ve.size(); i++) {
				DTL01 dtl01 = (DTL01) ve.get(i);
				BankAdapter.log(dtl01.toString());
				MoneyInfoValue info = new MoneyInfoValue();
				info.account = dtl01.bkAcct;
				info.bankID = getBankID();
				String year = dtl01.bankTransferDate.substring(0, 4);
				String month = dtl01.bankTransferDate.substring(4, 6);
				String date = dtl01.bankTransferDate.substring(6, 8);
				java.util.Date tempDate = new java.util.Date(Integer.parseInt(year) - 1900, Integer.parseInt(month) - 1, Integer.parseInt(date));
				info.compareDate = new java.sql.Date(tempDate.getTime());
				info.firmID = dtl01.ftAcct;
				info.id = dtl01.bkSeq;
				info.m_Id = Long.parseLong((dtl01.ftSeq == null) || (dtl01.ftSeq.trim().length() <= 0) ? "-2" : dtl01.ftSeq);
				info.money = Arith.div(dtl01.trfAmt, 100.0F);
				if (Integer.parseInt(dtl01.busCd) == BusinessCode.CODE22001.getValue())
					info.type = 0;
				else if (Integer.parseInt(dtl01.busCd) == BusinessCode.CODE22002.getValue()) {
					info.type = 1;
				}
				info.note = "";
				info.status = 0;
				BankAdapter.log(info.toString());
				result.add(info);
			}

			try {
				getPROCESSOR().insertBankMoneyInfo(result, 1);
			} catch (RemoteException e) {
				log(Common.getExceptionTrace(e));
			}
		} else if (getFileType(filenameNew).equals("DTL02")) {
			DTL02 dtl02 = fp.getDTL02();
			if (dtl02 != null) {
				BankAdapter.log(dtl02.toString());
				MoneyInfoValue info = new MoneyInfoValue();
				info.bankID = getBankID();
				String year = dtl02.sumDate.substring(0, 4);
				String month = dtl02.sumDate.substring(4, 6);
				String date = dtl02.sumDate.substring(6, 8);
				java.util.Date tempDate = new java.util.Date(Integer.parseInt(year) - 1900, Integer.parseInt(month) - 1, Integer.parseInt(date));
				info.compareDate = new java.sql.Date(tempDate.getTime());
				info.money = Arith.div(dtl02.trfAmt, 100.0F);
				if (dtl02.trfFlag.equals("0"))
					info.type = 0;
				else if (dtl02.trfFlag.equals("1")) {
					info.type = 1;
				}
				info.note = "";
				info.status = 0;

				BankAdapter.log(info.toString());
				result.add(info);
				try {
					getPROCESSOR().insertBankMoneyInfo(result, 2);
				} catch (RemoteException e) {
					log(Common.getExceptionTrace(e));
				}
			}

		} else if (getFileType(filenameNew).equals("DTL03")) {
			Vector ve = null;
			try {
				ve = fp.getDTL03();
			} catch (NumberFormatException nfe) {
				BankAdapter.log("对账文件内容有误,对账失败");
				BankAdapter.log(nfe.getMessage());
				return;
			}
			for (int i = 0; i < ve.size(); i++) {
				DTL03 dtl03 = (DTL03) ve.get(i);

				BankFirmRightValue info = new BankFirmRightValue();
				String year = dtl03.date.substring(0, 4);
				String month = dtl03.date.substring(4, 6);
				String date = dtl03.date.substring(6, 8);
				java.util.Date tempDate = new java.util.Date(Integer.parseInt(year) - 1900, Integer.parseInt(month) - 1, Integer.parseInt(date));
				info.bdate = new Timestamp(tempDate.getTime());
				try {
					info.firmId = getPROCESSOR().getFirmID(dtl03.futuresId, this.BANKID);
				} catch (RemoteException e) {
					BankAdapter.log("获取交易商代码异常：" + Tool.getExceptionTrace(e));
				}
				info.bankRight = Arith.div((float) dtl03.BkBal, 100.0F);
				info.maketRight = Arith.div((float) dtl03.FtBal, 100.0F);
				info.reason = dtl03.reason;
				info.bankId = this.BANKID;

				BankAdapter.log(dtl03.toString());
				BankAdapter.log(info.toString());
				bankFirmRightValues.add(info);
			}

			try {
				getPROCESSOR().setBankFirmRightValue(bankFirmRightValues);
			} catch (RemoteException e) {
				log(Common.getExceptionTrace(e));
			}

		} else if (getFileType(filenameNew).equals("DTL04")) {
			DTL04 dtl04 = fp.getDTL04();
			ProperBalanceValue info = new ProperBalanceValue();
			String year = dtl04.date.substring(0, 4);
			String month = dtl04.date.substring(4, 6);
			String date = dtl04.date.substring(6, 8);
			java.util.Date tempDate = new java.util.Date(Integer.parseInt(year) - 1900, Integer.parseInt(month) - 1, Integer.parseInt(date));
			info.bdate = new Timestamp(tempDate.getTime());
			info.allMoney = Arith.div((float) dtl04.bkManageSumBlance, 100.0F);
			info.gongMoney = Arith.div((float) dtl04.bkSumBlanceLocal, 100.0F);
			info.otherMoney = Arith.div((float) dtl04.bkSumBlanceOther, 100.0F);
			info.bankId = this.BANKID;

			BankAdapter.log(dtl04.toString());
			BankAdapter.log(info.toString());
			try {
				getPROCESSOR().setProperBalanceValue(info);
			} catch (RemoteException e) {
				log(Common.getExceptionTrace(e));
			}
		}
	}

	private String getFileType(String name) {
		String fileType = name.split("_")[1];
		return fileType;
	}

	public double accountQuery(CorrespondValue correspondValue, String password) {
		double result = 0.0D;
		QryAccount qryAccount = this.objTransformer.getQryAccount(correspondValue);
		QryAccountResponse qryAccountResponse = null;
		try {
			qryAccountResponse = (QryAccountResponse) this.bankBusiness.getResponse(qryAccount);
		} catch (BankException e) {
			log(Common.getExceptionTrace(e));
			result = Long.valueOf(-100L).longValue();
		}

		if (qryAccountResponse.Rst.Code.equals(ReturnCode.CODE0000.getValue()))
			result = Arith.div(qryAccountResponse.BkBal.CurBal.amt, 100.0F);
		else {
			result = ((Long) this.ErrorCodeB2M.get(qryAccountResponse.Rst.Code)).longValue();
		}

		BankAdapter.log("查询交易商银行余额结果：");
		BankAdapter.log("返回码：" + qryAccountResponse.Rst.Code);
		BankAdapter.log("返回码信息：" + qryAccountResponse.Rst.Info);
		BankAdapter.log("银行流水号：" + qryAccountResponse.BkSeq);
		BankAdapter.log("市场流水号：" + qryAccountResponse.FtSeq);
		BankAdapter.log("当前余额：" + qryAccountResponse.BkBal.CurBal.amt);
		BankAdapter.log("可用余额：" + qryAccountResponse.BkBal.UseBal.amt);
		BankAdapter.log("可取余额：" + qryAccountResponse.BkBal.FtcBal.amt);

		return result;
	}

	public QryAccountResponse accountQuery(Object request) {
		QryAccount qryAccount = (QryAccount) request;

		ReturnValue returnValue = new ReturnValue();
		FirmBalanceValue firmBalanceValue = null;
		if (!MACVerify.verify(qryAccount, qryAccount.Mac))
			returnValue.result = -2L;
		else {
			try {
				String firmid = getPROCESSOR().getFirmID(qryAccount.FtAcct.Id, this.BANKID);
				firmBalanceValue = getPROCESSOR().getMarketBalance(firmid, this.BANKID);
				returnValue.result = 0L;
			} catch (Exception e) {
				returnValue.result = -1L;
			}
		}

		BankAdapter.log("查询交易商市场余额结果:");
		BankAdapter.log(returnValue.toString());
		QryAccountResponse qryAccountResponse = this.objTransformer.getQryAccountResponse(qryAccount, firmBalanceValue, returnValue);
		BankAdapter.log("查询交易商市场余额结果-返回给银行：");
		BankAdapter.log("返回码：" + qryAccountResponse.Rst.Code);
		BankAdapter.log("银行流水号：" + qryAccountResponse.BkSeq);
		BankAdapter.log("市场流水号：" + qryAccountResponse.FtSeq);
		BankAdapter.log("当前余额：" + qryAccountResponse.FtBal.CurBal.amt);
		BankAdapter.log("可用余额：" + qryAccountResponse.FtBal.UseBal.amt);
		BankAdapter.log("可取余额：" + qryAccountResponse.FtBal.FtcBal.amt);

		return qryAccountResponse;
	}

	public Object reversal(Object request) {
		Reversal reversal = (Reversal) request;
		ReturnValue returnValue = new ReturnValue();
		returnValue.result = -1L;
		ChargeAgainstValue chargeAgainstValue = new ChargeAgainstValue();
		chargeAgainstValue.bankID = this.BANKID;
		chargeAgainstValue.funID = reversal.OldBankSeqNo;
		chargeAgainstValue.bankTime = new Timestamp(Common.getDate().getTime());
		chargeAgainstValue.funIDCA = reversal.BkSeq;
		ReversalResponse reversalResponse = null;
		try {
			returnValue = getPROCESSOR().chargeAgainst(chargeAgainstValue);

			reversalResponse = new ReversalResponse();
			reversalResponse.GrpHdr = reversal.GrpHdr;
			reversalResponse.BkSeq = reversal.BkSeq;
			if (this.ErrorCodeM2B.get(Long.valueOf(returnValue.result)) != null)
				reversalResponse.Rst.Code = String.valueOf(this.ErrorCodeM2B.get(Long.valueOf(returnValue.result)));
			else {
				reversalResponse.Rst.Code = ReturnCode.CODE2041.getValue();
			}
			if (reversalResponse.Rst.Code != ReturnCode.CODE0000.getValue()) {
				ReversalResponse localReversalResponse1 = reversalResponse;
				return localReversalResponse1;
			}
			reversalResponse.FtSeq = (returnValue.actionId < 0L ? "" : String.valueOf(returnValue.actionId));
			reversalResponse.BkAcct = reversal.BkAcct;
			reversalResponse.FtAcct = reversal.FtAcct;
			reversalResponse.TrfAmt = reversal.TrfAmt;

			reversalResponse.Mac = MACVerify.encryption(reversalResponse);
		} catch (RemoteException e) {
			log(Common.getExceptionTrace(e));
			returnValue.result = -1L;
		} catch (Exception e) {
			log(Common.getExceptionTrace(e));
			returnValue.result = -1L;
		} finally {
			BankAdapter.log("银行端冲正结果-返回给银行：");
			BankAdapter.log("返回码：" + reversalResponse.Rst.Code);
			BankAdapter.log("银行流水号：" + reversalResponse.BkSeq);
			BankAdapter.log("市场流水号：" + reversalResponse.FtSeq);
			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = reversalResponse.BkAcct.Id;
			log.bankID = this.BANKID;
			log.contact = reversalResponse.FtAcct.Id;
			log.type = 8;
			endmsg.code = returnValue.result + "";
			endmsg.note = returnValue.toString();
			if (returnValue.result != 0L) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		return reversalResponse;
	}

	public ReturnValue transferMoney(TransferMoneyVO arg0) {
		return null;
	}

	public Vector<MoneyInfoValue> getBankMoneyInfo(java.util.Date date, Vector v) {
		return getBankMoneyInfo(date);
	}

	public ReturnValue bankTransfer(BankTransferValue bankTransferValue) {
		ReturnValue returnValue = new ReturnValue();

		BankTransfer transfer = this.objTransformer.getInterTransfer(bankTransferValue);

		BankAdapter.log("跨行转账");

		BankTransferResponse transferResponse = null;
		try {
			transferResponse = (BankTransferResponse) this.bankBusiness.getResponse(transfer);

			if (transferResponse.Rst.Code.equals(ReturnCode.CODE0000.getValue())) {
				returnValue.result = 0L;
				returnValue.actionId = 0L;
				returnValue.funID = transferResponse.BkSeq;
				returnValue.bankTime = (transferResponse.GrpHdr.Date + transferResponse.GrpHdr.Time);
			} else {
				returnValue.result = ((Long) this.ErrorCodeB2M.get(transferResponse.Rst.Code)).longValue();
				returnValue.remark = transferResponse.Rst.Info;
			}
		} catch (BankException e) {
			returnValue.result = -100L;
			log(Common.getExceptionTrace(e));
			BankAdapter.log("跨行转账失败");
			ReturnValue localReturnValue1 = returnValue;
			return localReturnValue1;
		} finally {
			BankAdapter.log("\n");
			BankAdapter.log("跨行转账结果-银行：");
			BankAdapter.log("返回码：" + transferResponse.Rst.Code);
			BankAdapter.log("返回码信息：" + transferResponse.Rst.Info);
			BankAdapter.log("银行流水号：" + transferResponse.BkSeq);
			BankAdapter.log("市场流水号：" + transferResponse.FtSeq);
			BankAdapter.log("金额：" + transfer.TrfAmt.amt + "分");
			BankAdapter.log("跨行汇拨结果-处理器：");
			BankAdapter.log(returnValue.toString());
			BankAdapter.log("\n");
			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = "";
			log.bankID = this.BANKID;
			log.contact = "";
			endmsg.code = returnValue.result + "";
			endmsg.note = returnValue.toString();
			log.type = 13;
			if (returnValue.result != 0L) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		return returnValue;
	}

	public int setBankMoneyInfo(java.util.Date arg0) {
		return 0;
	}

	public List getData(int arg0, java.util.Date arg1) {
		return null;
	}

	public List transferMoneyAfterTrade() {
		return null;
	}

	public ReturnValue rgstAccountQuery(CorrespondValue correspondValue) {
		BankAdapter.log("");
		BankAdapter.log("市场端签约");
		BankAdapter.log("");
		ReturnValue returnValue = new ReturnValue();
		if ("true".equals(getConfig("openAccMarketSuccess"))) {
			log("开启直接签约成功功能");
			returnValue.result = 0L;
			returnValue.remark = "签约成功";
			return returnValue;
		}
		if ("true".equals(getConfig("openAccMarket"))) {
			log("配置文件开启市场端签约模式，关闭信息校验功能，直接返回处理器该银行不支持市场端签约");
			returnValue.result = 5L;
			returnValue.remark = "该银行不支持市场端签约";
			return returnValue;
		}
		CheckAccount checkAccount = this.objTransformer.getCheckAccount(correspondValue);
		CheckAccountResponse checkAccountResponse = null;

		try {
			checkAccountResponse = (CheckAccountResponse) this.bankBusiness.getResponse(checkAccount);

			if (checkAccountResponse.Rst.Code.equals(ReturnCode.CODE0000.getValue())) {
				returnValue.result = -500L;
				returnValue.remark = "信息检验通过";
			} else {
				Object code = this.ErrorCodeB2M.get(checkAccountResponse.Rst.Code);
				returnValue.result = ((Long) (code == null ? Long.valueOf(-100L) : code)).longValue();
				returnValue.remark = checkAccountResponse.Rst.Info;
			}
		} catch (BankException e) {
			log(Common.getExceptionTrace(e));
			returnValue.result = Long.valueOf(-100L).longValue();
		} finally {
			BankAdapter.log("客户信息验证结果——银行：");
			BankAdapter.log("返回码：" + checkAccountResponse.Rst.Code);
			BankAdapter.log("返回码信息：" + checkAccountResponse.Rst.Info);
			BankAdapter.log("银行流水号：" + checkAccountResponse.BkSeq);
			BankAdapter.log("返回给处理器：");
			BankAdapter.log(returnValue.toString());
			BankAdapter.log("");
			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = correspondValue.account;
			log.bankID = this.BANKID;
			log.contact = correspondValue.contact;
			endmsg.code = returnValue.result + "";
			log.type = 3;
			endmsg.note = returnValue.remark;
			if (returnValue.result != 0L) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		return returnValue;
	}

	public ReturnValue checkAccount(CorrespondValue correspondValue) {
		BankAdapter.log("");
		BankAdapter.log("客户信息验证");
		BankAdapter.log("");
		ReturnValue returnValue = new ReturnValue();
		CheckAccount checkAccount = this.objTransformer.getCheckAccount(correspondValue);
		CheckAccountResponse checkAccountResponse = null;

		try {
			checkAccountResponse = (CheckAccountResponse) this.bankBusiness.getResponse(checkAccount);

			if (checkAccountResponse.Rst.Code.equals(ReturnCode.CODE0000.getValue())) {
				returnValue.result = 0L;
				returnValue.remark = "信息检验通过";
			} else {
				Object code = this.ErrorCodeB2M.get(checkAccountResponse.Rst.Code);
				returnValue.result = ((Long) (code == null ? Long.valueOf(-100L) : code)).longValue();
				returnValue.remark = checkAccountResponse.Rst.Info;
			}
		} catch (BankException e) {
			log(Common.getExceptionTrace(e));
			returnValue.result = Long.valueOf(-100L).longValue();
		} finally {
			BankAdapter.log("客户信息验证结果——银行：");
			BankAdapter.log("返回码：" + checkAccountResponse.Rst.Code);
			BankAdapter.log("返回码信息：" + checkAccountResponse.Rst.Info);
			BankAdapter.log("银行流水号：" + checkAccountResponse.BkSeq);
			BankAdapter.log("返回给处理器：");
			BankAdapter.log(returnValue.toString());
			BankAdapter.log("");
			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = correspondValue.account;
			log.bankID = this.BANKID;
			log.contact = correspondValue.contact;
			endmsg.code = returnValue.result + "";
			log.type = 13;
			endmsg.note = returnValue.remark;
			if (returnValue.result != 0L) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		return returnValue;
	}

	public ReturnValue delAccountQuery(CorrespondValue correspondValue) {
		ReturnValue result = new ReturnValue();
		if ("true".equals(getConfig("openAccMarketSuccess"))) {
			log("开启直接解约成功功能");
			result.result = 0L;
			result.remark = "解约成功";
			return result;
		}
		if ("true".equals(BankAdapter.getConfig("openAccMarket"))) {
			result.result = 5L;
			result.remark = "该银行不支持市场端解约";
		} else {
			result.result = -1L;
			result.remark = "适配器未开启判断是否支持市场端解约功能";
		}
		return result;
	}

	public List transferMoneyAfterTrade(java.util.Date date) {
		return null;
	}

	public Vector<MoneyInfoValue> getBankMoneyInfo(java.util.Date date) {
		FileInfo fileInfo = new FileInfo();
		fileInfo.FileName = ("B_DTL01_" + Common.df7.format(date) + ".gz");
		getBankMoneyInfo(fileInfo);
		fileInfo = new FileInfo();
		fileInfo.FileName = ("B_DTL02_" + Common.df7.format(date) + ".gz");
		getBankMoneyInfo(fileInfo);
		return new Vector();
	}

	public ReturnValue setBankMoneyInfo(Hashtable<String, TradeResultValue> arg0) {
		return null;
	}

	public ReturnValue setTotalMoneyInfo(Hashtable<String, Double> arg0) {
		return null;
	}

	public ReturnValue loginBank(String bankId) {
		return loginBank();
	}

	public ReturnValue quitBank(String bankId) {
		return quitBank();
	}

	public void yemx() {
		try {
			List FirmRightsValuesmarket = new ArrayList();
			String fileName = "icbc_tz/yemx.txt";
			File file = new File(fileName);
			if (file.exists()) {
				System.out.println("tz.............7");
				BufferedReader bfr = new BufferedReader(new FileReader(file));
				String line = "";
				while (true) {
					line = bfr.readLine();
					if (line == null)
						break;
					String[] col = line.split("\\|");
					FirmRights tr = new FirmRights();
					tr.account = col[1].trim();
					tr.firmId = col[0].trim();
					tr.firmName = col[2].trim();
					tr.money = 0.0D;
					FirmRightsValuesmarket.add(tr);
				}

				bfr.close();
			}
			System.out.println("aaa" + FirmRightsValuesmarket.size());

			Vector veSTL02 = new Vector();
			for (int i = 0; i < FirmRightsValuesmarket.size(); i++) {
				FirmRights firmRights = (FirmRights) FirmRightsValuesmarket.get(i);
				STL02 stl02 = new STL02();
				stl02.bankId = BankAdapter.getConfig("BankCode1");
				stl02.futuresId = getMarketCode();
				stl02.date = Common.df7.format(Common.getDate());
				stl02.bkAcct = firmRights.account;
				stl02.ftAcct = firmRights.firmId;
				stl02.custName = firmRights.firmName;
				stl02.ccy = "001";
				stl02.cashExCd = 0;
				stl02.FtBal = ((long) Arith.mul(firmRights.money, 100.0F));
				stl02.info1 = "";
				stl02.info2 = "";
				stl02.info3 = "";
				veSTL02.add(stl02);
			}

			System.out.println("ccc" + veSTL02.size());
			String basePath = "icbc_tz/";
			FileProcessor fp = new FileProcessor(basePath);
			String filenameSTL02 = "";
			try {
				filenameSTL02 = fp.setSTL02(veSTL02);
			} catch (IOException e) {
				log(Common.getExceptionTrace(e));
				BankAdapter.log("生成客户资金台账资金余额明细表失败");
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ICBCBankImpl adapter = (ICBCBankImpl) Factory.getInstance().getBankAdapter();
		adapter.init();

		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		String str = "";

		while (!str.equals("exit")) {
			try {
				str = buf.readLine();
				if (str.equals("check")) {
					RmiClient rmiClient = Factory.getInstance().getRmiClient();
					if (rmiClient.connProcessor()) {
						System.out.println("连接处理器成功");
					}
				} else if (str.trim().equals("bankid")) {
					System.out.println(adapter.BANKID);
				} else if (str.trim().equals("bankcode")) {
					System.out.println(adapter.BANKCODE);
				} else if (str.trim().equals("marketcode")) {
					System.out.println(adapter.MARKETCODE);
				} else if (str.trim().equals("yemx")) {
					adapter.yemx();
					System.out.println("查询完成");
				} else if (str.trim().equals("daydata")) {
					DayDataReady dayDataReady = new DayDataReady();
					dayDataReady.GrpHdr.Version = "1.0";
					dayDataReady.GrpHdr.BusCd = BusinessCode.CODE23001.getValue();
					dayDataReady.GrpHdr.TradSrc = TradeSource.B;
					dayDataReady.GrpHdr.Sender.InstId = "10200000";
					dayDataReady.GrpHdr.Recver.InstId = "00000148";
					dayDataReady.GrpHdr.Date = Common.df7.format(Common.getDate());
					dayDataReady.GrpHdr.Time = Common.df8.format(Common.getDate());
					dayDataReady.BkSeq = "0001";
					FileInfo fileInfo = new FileInfo();
					fileInfo.BusCode = FileBusinessCode.DETAIL.getValue();
					fileInfo.BusDate = "20091105";
					fileInfo.Host = "183.9.100.235";
					fileInfo.FileName = "B_DTL01_20091031.gz";
					dayDataReady.add(fileInfo);
					FileInfo fileInfo1 = new FileInfo();
					fileInfo1.BusCode = FileBusinessCode.SUM.getValue();
					fileInfo1.BusDate = "20091105";
					fileInfo1.Host = "83.9.100.235";
					fileInfo1.FileName = "B_DTL02_20091031.gz";
					dayDataReady.add(fileInfo1);

					adapter.dayDataReady(dayDataReady);
				} else if (str.trim().equals("syn")) {
					ReturnValue returnValue = adapter.synchronizeKey();
					if (returnValue.result == 0L)
						System.out.println("密钥同步成功");
					else
						System.out.println("密钥同步失败");
				} else if (str.trim().equals("login")) {
					ReturnValue returnValue = adapter.loginBank();
					if (returnValue.result == 0L)
						System.out.println("签到成功");
					else {
						System.out.println("签到失败");
					}
				} else if (str.trim().equals("quit")) {
					ReturnValue returnValue = adapter.quitBank();
					if (returnValue.result == 0L)
						System.out.println("签退成功");
					else
						System.out.println("签退失败");
				} else if (str.trim().equals("qry")) {
					CorrespondValue correspondValue = new CorrespondValue();
					correspondValue.account = "1202083109900332221";
					correspondValue.firmID = "0041";
					correspondValue.cardType = "9";
					correspondValue.card = "33012500000012365";
					correspondValue.accountName = "杭州";
					adapter.accountQuery(correspondValue, null);
				} else if (str.trim().equals("send")) {
					adapter.sendTradeData();
				} else if (str.startsWith("cz_")) {
					System.out.println("手工冲正");
					String[] strs = str.split("_", -1);
					ChargeAgainstValue chargeAgainstValue = new ChargeAgainstValue();
					chargeAgainstValue.funID = strs[1];
					chargeAgainstValue.funIDCA = strs[2];
					adapter.chongZhengByHand(chargeAgainstValue);
				} else if (str.trim().equals("traderst")) {
					adapter.getTradeDataRst(new java.util.Date());
				} else if (str.trim().equals("transrst")) {
					adapter.getBankMoneyInfo(new java.util.Date());
				} else if (str.trim().indexOf("TZ") == 0) {
					System.out.println("tz输入:" + str);
					str = str.split("-")[1];
					adapter.sendTradeDataTZ(str);
				} else if (str.trim().equals("notice")) {
					adapter.sendTradeDataNotice();
				} else if (str.trim().equals("help")) {
					System.out.println("--------------------------------------------------");
					System.out.println("     exit:                       退出                      ");
					System.out.println("     check:                      测试处理器连接             ");
					System.out.println("     bankid:                     银行代码                   ");
					System.out.println("     bankcode:                   银行机构代码               ");
					System.out.println("     marketcode:                 市场机构代码               ");
					System.out.println("     syn:                        密钥同步                   ");
					System.out.println("     login:                      签到                       ");
					System.out.println("     quit:                       签退                       ");
					System.out.println("     daydata:                    取日终数据                 ");
					System.out.println("     send:                       发送交收数据                ");
					System.out.println("     traderst:                   获取今日总分、分分结果       ");
					System.out.println("     transrst:                   获取今日转账交易明细        ");
					System.out.println("     notice:                     文件通知（只通知）        ");
					System.out.println("     yemx:                     生成余额明细文件        ");
					System.out.println("     TZ-yyyyMMdd|yyyyMMdd:       调账（详见 工行调账步骤.txt）");
					System.out.println("     cz_oldFunID_newFunID:       手工冲正");
					System.out.println("--------------------------------------------------");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.exit(0);
	}

	public void sendTradeDataTZ(String str) {
		Vector veSTL01 = new Vector();
		Vector veSTL03 = new Vector();
		List TradingDetailsValues = null;
		List OpenOrDelFirmValues = null;
		TradingDetailsValues = TZ(str);
		OpenOrDelFirmValues = KXH(str);
		System.out.println("TradingDetailsValues.size()=" + TradingDetailsValues.size());
		System.out.println("OpenOrDelFirmValues.size()=" + OpenOrDelFirmValues.size());
		for (int i = 0; i < TradingDetailsValues.size(); i++) {
			TradingDetailsValue tradingDetailsValue = (TradingDetailsValue) TradingDetailsValues.get(i);
			STL01 stl01 = new STL01();
			stl01.bankId = "0001";
			stl01.futuresId = getMarketCode();
			stl01.date = Common.df7.format(Common.getDate());
			stl01.ftSeq = String.valueOf(tradingDetailsValue.maketNum);
			stl01.bkAcct = tradingDetailsValue.account;
			stl01.ftAcct = tradingDetailsValue.firmId;
			stl01.custName = tradingDetailsValue.firmName;
			stl01.busType = 0;
			stl01.trfFlag = tradingDetailsValue.updown;
			stl01.ccy = "001";
			stl01.cashExCd = 0;
			stl01.trfAmt = Math.abs((long) Arith.mul(tradingDetailsValue.money, 1.0F));
			stl01.info1 = "";
			stl01.info2 = "";
			stl01.info3 = "";
			veSTL01.add(stl01);
		}
		for (int i = 0; i < OpenOrDelFirmValues.size(); i++) {
			OpenOrDelFirmValue openOrDelFirmValue = (OpenOrDelFirmValue) OpenOrDelFirmValues.get(i);
			STL03 stl03 = new STL03();
			stl03.bankId = "0001";
			stl03.futuresId = getMarketCode();
			stl03.date = Common.df7.format(Common.getDate());
			stl03.ftSeq = String.valueOf(openOrDelFirmValue.maketNum);
			stl03.ftAcct = openOrDelFirmValue.firmId;
			stl03.custName = openOrDelFirmValue.firmName;
			stl03.ccy = "001";
			stl03.cashExCd = 0;
			stl03.busType = openOrDelFirmValue.openordel;
			stl03.info1 = "";
			stl03.info2 = "";
			stl03.info3 = "";
			veSTL03.add(stl03);
		}
		String basePath = "icbc_tz/new/";
		FileProcessor fp = new FileProcessor(basePath);
		String filenameSTL01 = "";
		try {
			filenameSTL01 = fp.setSTL01(veSTL01);
		} catch (IOException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("生成交收明细文件失败");
			return;
		}
		String filenameSTL03 = "";
		try {
			filenameSTL03 = fp.setSTL03(veSTL03);
		} catch (IOException e) {
			log(Common.getExceptionTrace(e));
			BankAdapter.log("生成客户资金台账开销户文档失败");
			return;
		}

		GzipUtil.zipFile(basePath + filenameSTL01, basePath + filenameSTL01 + ".gz");
		GzipUtil.zipFile(basePath + filenameSTL03, basePath + filenameSTL03 + ".gz");
		System.out.println("生成文件完成");
	}

	public List<OpenOrDelFirmValue> KXH(String str) {
		String[] dates = str.split("\\|");
		List OpenOrDelFirmValues = new ArrayList();
		try {
			String fileName2 = "icbc_tz/B_DTL03_" + dates[1];
			File file2 = new File(fileName2);
			int i = 30000;
			if (file2.exists()) {
				BufferedReader bfr = new BufferedReader(new FileReader(file2));
				String line = "";
				while (true) {
					line = bfr.readLine();
					if (line == null)
						break;
					String[] col = line.split("\\|");
					if (col[7].trim().equals("2")) {
						OpenOrDelFirmValue openordel = new OpenOrDelFirmValue();
						openordel.firmId = col[5].trim();
						openordel.firmName = col[6].trim();
						openordel.openordel = "1";
						openordel.maketNum = i;
						OpenOrDelFirmValues.add(openordel);
						i++;
					}

				}

				bfr.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return OpenOrDelFirmValues;
	}

	public List<TradingDetailsValue> TZ(String str) {
		String[] dates = str.split("\\|");
		int i = 1;
		List TradingDetailsValues = new ArrayList();
		try {
			List<FirmRights> FirmRightsValuesmarket = new ArrayList();
			String fileName = "icbc_tz/F_STL02_" + dates[0];
			File file = new File(fileName);
			if (file.exists()) {
				System.out.println("tz.............7");
				BufferedReader bfr = new BufferedReader(new FileReader(file));
				String line = "";
				while (true) {
					line = bfr.readLine();
					if (line == null)
						break;
					String[] col = line.split("\\|");
					FirmRights tr = new FirmRights();
					tr.account = col[3].trim();
					tr.firmId = col[4].trim();
					tr.firmName = col[5].trim();
					tr.money = Double.parseDouble(quling(col[8].trim()));
					FirmRightsValuesmarket.add(tr);
				}

				bfr.close();
			}

			List<TradingDetailsValue> todayBCs = new ArrayList();
			Map todayBCsM = new HashMap();
			String fileName1 = "icbc_tz/F_STL01_" + dates[0];
			File file1 = new File(fileName1);
			if (file1.exists()) {
				System.out.println("tz.............7");
				BufferedReader bfr = new BufferedReader(new FileReader(file1));
				String line = "";
				while (true) {
					line = bfr.readLine();
					if (line == null)
						break;
					String[] col = line.split("\\|");

					TradingDetailsValue tr = new TradingDetailsValue();
					tr.maketNum = i;
					tr.account = col[4].trim();
					tr.firmId = col[5].trim();
					tr.firmName = col[6].trim();
					tr.money = Double.parseDouble(col[11].trim());
					tr.updown = col[8].trim();
					todayBCsM.put(tr.firmId, tr);
					todayBCs.add(tr);
					i++;
				}

				bfr.close();
			}

			Map FirmRightsValuesbank = new HashMap();
			String fileName2 = "icbc_tz/B_DTL03_" + dates[1];
			File file2 = new File(fileName2);
			if (file2.exists()) {
				BufferedReader bfr = new BufferedReader(new FileReader(file2));
				String line = "";
				while (true) {
					line = bfr.readLine();
					if (line == null)
						break;
					String[] col = line.split("\\|");
					if (!col[7].trim().equals("0")) {
						if (col[7].trim().equals("2")) {
							TradingDetailsValue td = new TradingDetailsValue();
							td.firmId = col[5].trim();
							td.firmName = col[6].trim();
							td.money = Double.parseDouble(quling(col[8].trim()));
							if (td.money != 0.0D) {
								Vector firm = getPROCESSOR().getCorrespondValue(" where firmid='D_" + td.firmId + "' ");
								if ((firm != null) && (firm.size() > 0)) {
									td.account = ((CorrespondValue) firm.get(0)).account;

									td.updown = "1";
									if (td.money < 0.0D) {
										td.updown = "2";
									}
									td.maketNum = i;
									i++;
									TradingDetailsValues.add(td);
								}
							}
						}
					} else {
						String firmId = col[5].trim();

						Double money = Double.valueOf(Double.parseDouble(quling(col[8].trim())));
						FirmRightsValuesbank.put(firmId, money);
					}

				}

				bfr.close();
			}
			System.out.println("FirmRightsValuesmarket.size" + FirmRightsValuesmarket.size());

			Map mxs = new HashMap();
			String fileName3 = "icbc_tz/B_DTL01_" + dates[0];
			File file3 = new File(fileName3);
			String firmId;
			if (file3.exists()) {
				BufferedReader bfr = new BufferedReader(new FileReader(file3));
				String line = "";
				while (true) {
					line = bfr.readLine();
					if (line == null)
						break;
					String[] col = line.split("\\|");
					firmId = col[8].trim();

					String flag = col[11].trim();
					int j = 1;
					if (flag.equals("22001")) {
						j = -1;
					}
					Double money = Double.valueOf(j * Double.parseDouble(quling(col[14].trim())));
					if (mxs.get(firmId) != null) {
						money = Double.valueOf(money.doubleValue() + ((Double) mxs.get(firmId)).doubleValue());
					}

					mxs.put(firmId, money);
				}

				bfr.close();
			}

			Map tzBCs = new HashMap();
			List<TradingDetailsValue> tzBCsL = new ArrayList();

			for (FirmRights fr : FirmRightsValuesmarket)
				if (FirmRightsValuesbank.get(fr.firmId) != null) {
					TradingDetailsValue tr = new TradingDetailsValue();
					double marketMoney = fr.money;
					double bankMoney = 0.0D;
					double bankmx = 0.0D;
					if (mxs.get(fr.firmId) != null) {
						bankmx = ((Double) mxs.get(fr.firmId)).doubleValue();
					}
					bankMoney = ((Double) FirmRightsValuesbank.get(fr.firmId)).doubleValue();
					marketMoney += bankmx;

					double money = marketMoney - bankMoney;
					tr.maketNum = i;
					tr.account = fr.account;
					tr.firmId = fr.firmId;
					tr.firmName = fr.firmName;
					tr.money = money;
					tr.updown = (money > 0.0D ? "2" : "1");

					tzBCs.put(tr.firmId, tr);
					if (todayBCsM.get(tr.firmId) == null) {
						tzBCsL.add(tr);
						i++;
					}

					i++;
				}
			for (TradingDetailsValue td : todayBCs) {
				if (tzBCs.get(td.firmId) != null) {
					if (((TradingDetailsValue) tzBCs.get(td.firmId)).money != 0.0D) {
						TradingDetailsValues.add((TradingDetailsValue) tzBCs.get(td.firmId));
					}
				} else
					TradingDetailsValues.add(td);
			}
			for (TradingDetailsValue td : tzBCsL) {
				if (todayBCsM.get(td.firmId) == null) {
					TradingDetailsValues.add(td);
				}
			}
			System.out.println("TradingDetailsValues.size" + TradingDetailsValues.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return TradingDetailsValues;
	}

	public String quling(String s) {
		int len = s.length();
		for (int i = 0; i < len - 1; i++) {
			if (s.indexOf("0") == 0)
				s = s.substring(1, s.length());
			else {
				return s;
			}
		}
		return s;
	}

	public void sendTradeDataNotice() {
		String filenameSTL01New = "F_STL01_" + Common.df7.format(new java.util.Date()) + ".gz";
		String filenameSTL02New = "F_STL02_" + Common.df7.format(new java.util.Date()) + ".gz";
		String filenameSTL03New = "F_STL03_" + Common.df7.format(new java.util.Date()) + ".gz";

		List fileList = new ArrayList();
		FileInfo fileInfo1 = new FileInfo();
		fileInfo1.FileName = filenameSTL01New;
		fileList.add(fileInfo1);
		FileInfo fileInfo2 = new FileInfo();
		fileInfo2.FileName = filenameSTL02New;
		fileList.add(fileInfo2);
		FileInfo fileInfo3 = new FileInfo();
		fileInfo3.FileName = filenameSTL03New;
		fileList.add(fileInfo3);

		DayDataReady dayDataReady = this.objTransformer.getDayDataReady(fileList);
		DayDataReadyResponse dayDataReadyResponse = (DayDataReadyResponse) this.bankBusiness.getResponse(dayDataReady);

		BankAdapter.log("发送日终数据就绪结果-银行：");
		BankAdapter.log("返回码：" + dayDataReadyResponse.Rst.Code);
		BankAdapter.log("返回码信息：" + dayDataReadyResponse.Rst.Info);
		BankAdapter.log("银行流水号：" + dayDataReadyResponse.BkSeq);
		BankAdapter.log("市场流水号：" + dayDataReadyResponse.FtSeq);
	}

	public ReturnValue matketOutMoney(BankTransferValue bankTransferValue) {
		ReturnValue returnValue = new ReturnValue();

		MatketOutMoney matketOutMoney = this.objTransformer.getMatketOutMoney(bankTransferValue);
		BankAdapter.log("市场出金");

		MatketOutMoneyResponse matketOutMoneyResponse = null;
		try {
			matketOutMoneyResponse = (MatketOutMoneyResponse) this.bankBusiness.getResponse(matketOutMoney);
		} catch (BankException e) {
			returnValue.result = -100L;
			log(Common.getExceptionTrace(e));
			BankAdapter.log("市场出金失败");
			return returnValue;
		}
		if (matketOutMoneyResponse.Rst.Code.equals(ReturnCode.CODE0000.getValue())) {
			returnValue.result = 0L;
			returnValue.actionId = 0L;
			returnValue.funID = matketOutMoneyResponse.BkSeq;
			returnValue.bankTime = (matketOutMoneyResponse.GrpHdr.Date + matketOutMoneyResponse.GrpHdr.Time);
		} else {
			returnValue.result = ((Long) this.ErrorCodeB2M.get(matketOutMoneyResponse.Rst.Code)).longValue();
			returnValue.remark = matketOutMoneyResponse.Rst.Info;
		}
		BankAdapter.log("\n");
		BankAdapter.log("市场出金结果-银行：");
		BankAdapter.log("返回码：" + matketOutMoneyResponse.Rst.Code);
		BankAdapter.log("返回码信息：" + matketOutMoneyResponse.Rst.Info);
		BankAdapter.log("银行流水号：" + matketOutMoneyResponse.BkSeq);
		BankAdapter.log("市场流水号：" + matketOutMoneyResponse.FtSeq);
		BankAdapter.log("金额：" + matketOutMoneyResponse.TrfAmt.amt + "分");
		BankAdapter.log("跨行汇拨结果-处理器：");
		BankAdapter.log(returnValue.toString());
		BankAdapter.log("\n");
		return returnValue;
	}

	private void interfaceLog(InterfaceLog log) {
		try {
			log(log.toString());
			if ("1".equals(BankAdapter.getConfig("InterfaceLog")))
				this.PROCESSOR.interfaceLog(log);
		} catch (Exception e) {
			log("写日志" + log.toString() + "时异常" + Common.getExceptionTrace(e));
		}
	}
}