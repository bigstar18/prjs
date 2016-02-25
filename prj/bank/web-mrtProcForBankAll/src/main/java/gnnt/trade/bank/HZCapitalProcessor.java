package gnnt.trade.bank;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.HZBankDAO;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfo;
import gnnt.trade.bank.vo.TransferInfoValue;
import gnnt.trade.bank.vo.TransferMoneyVO;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmRightValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.MarketRightValue;

public class HZCapitalProcessor extends CapitalProcessor {
	private static HZBankDAO DAO;

	public HZCapitalProcessor() {
		try {
			DAO = BankDAOFactory.getHZDAO();
		} catch (Exception e) {
			log("初始化江苏银行DAO对象处理器失败：" + Tool.getExceptionTrace(e));
		}
	}

	public RZQSValue getQSInfo(Vector<FirmBalance> list) {
		RZQSValue result = new RZQSValue();
		MarketRightValue mrv = new MarketRightValue();
		mrv.maketMoney = new BigDecimal("0");
		String a = "1";
		for (int i = 0; i < list.size(); i++) {
			FirmRightValue value = new FirmRightValue();
			value.actionID = String.valueOf(getMktActionID());
			value.firmID = ((FirmBalance) list.get(i)).firmID;
			value.account = ((FirmBalance) list.get(i)).account;
			value.availableBalance = ((FirmBalance) list.get(i)).MQYChangeMoney;
			value.billMoney = 0.0D;
			value.cash = 0.0D;
			value.cashMoney = 0.0D;
			value.credit = 0.0D;
			value.firmErrorMoney = ((FirmBalance) list.get(i)).MQYChangeMoney;
			mrv.bankErrorMoney += ((FirmBalance) list.get(i)).QYChangeMoney;
			mrv.maketMoney = mrv.maketMoney.add(new BigDecimal(((FirmBalance) list.get(i)).FeeMoney));
			System.out.println("交易商[" + ((FirmBalance) list.get(i)).firmID + "]的自有资金[" + ((FirmBalance) list.get(i)).FeeMoney + "]总自有资金["
					+ mrv.maketMoney + "]");
			log("交易商[" + ((FirmBalance) list.get(i)).firmID + "]的自有资金[" + ((FirmBalance) list.get(i)).FeeMoney + "]总自有资金[" + mrv.maketMoney + "]");
			result.putFrv(value);
		}
		result.setMarketRight(mrv);
		return result;
	}

	public RZDZValue getDZInfo(Vector<FirmBalance> list) {
		RZDZValue result = new RZDZValue();
		for (int i = 0; i < list.size(); i++) {
			FirmDZValue value = new FirmDZValue();
			value.firmID = ((FirmBalance) list.get(i)).firmID;
			value.account = ((FirmBalance) list.get(i)).account;
			value.firmRights = ((FirmBalance) list.get(i)).QYMoney;
			value.cashRights = 0.0D;
			value.billRights = 0.0D;
			value.availableBalance = ((FirmBalance) list.get(i)).QYMoney;
			value.cash = 0.0D;
			value.credit = 0.0D;
			result.putFdv(value);
		}
		return result;
	}

	public ReturnValue transferPositions(TransferInfo trans) {
		System.out.println(">>>>江苏银行头寸、收益划转>>>>时间：" + Tool.fmtTime(new Date()) + ">>>>\n.money[" + trans.money + "]type[" + trans.type + "]");
		ReturnValue rv = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(trans.bankid);
		if (bankadapter == null) {
			rv.result = -920000L;
		} else {
			TransferInfoValue pay = new TransferInfoValue();
			pay.account = trans.outAccount;
			pay.accountName = trans.outAccountName;
			TransferInfoValue receive = new TransferInfoValue();
			receive.account = trans.inAccount;
			receive.accountName = trans.inAccountName;
			TransferMoneyVO rmo = new TransferMoneyVO(trans.bankid, null, trans.money, pay, receive, 0L);
			rmo.type = trans.type + "";
			try {
				rv = bankadapter.transferMoney(rmo);
				if (0L == rv.result) {
					trans.status = 0;
				} else if (5L == rv.result) {
					trans.status = 2;
				} else {
					trans.status = 1;
				}
				trans.funId = rv.funID;
				trans.actionId = rv.actionId + "";
				DAO.addTransfer(trans);
			} catch (RemoteException e) {
				log(Tool.getExceptionTrace(e));
			} catch (SQLException e) {
				log(Tool.getExceptionTrace(e));
			} catch (ClassNotFoundException e) {
				log(Tool.getExceptionTrace(e));
			}
		}
		return rv;
	}
}
