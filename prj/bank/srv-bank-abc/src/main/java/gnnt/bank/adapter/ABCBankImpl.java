package gnnt.bank.adapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import com.hitrust.trustpay.client.TrxResponse;
import com.hitrust.trustpay.client.market.AccInfoQueryRequest;
import com.hitrust.trustpay.client.market.CreatePayDetailRequest;
import com.hitrust.trustpay.client.market.ModifCustAccNoRequest;
import com.hitrust.trustpay.client.market.PayOrderRequest;
import com.hitrust.trustpay.client.market.PayRequest;
import com.hitrust.trustpay.client.market.SignUpRequest;
import com.hitrust.trustpay.client.market.StatementDownloadFile;

import gnnt.bank.adapter.util.Common;
import gnnt.trade.bank.vo.AbcInfoValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.LogEndmsg;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TradeResultValue;
import gnnt.trade.bank.vo.bankdz.hx.sent.HXSentQSMsgValue;

public class ABCBankImpl extends BankAdapter {
	private static final long serialVersionUID = 1L;
	public static boolean upServer = true;
	public static ArrayList<Hashtable<String, Object>> list = new ArrayList();

	public Vector getBankMoneyInfo(java.util.Date date, Vector v) {
		log("取银行数据...");
		Vector vec = new Vector();
		String fileName = getConfig("dataPath") + "crjmx_" + Common.df7.format(date) + ".txt";
		Map map = getCorrespondValueMap();
		if (map != null) {
			File file = new File(fileName);

			String nameF = downDayDataFile(date);
			log("下载文件结果：" + nameF);

			if (file.exists()) {
				try {
					BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
					String line = "";
					int flag = 0;
					while (true) {
						line = bfr.readLine();
						if (flag == 0) {
							System.out.println("头信息" + line);
							flag++;
						} else {
							if (line == null) {
								break;
							}

							MoneyInfoValue moneyInfo = new MoneyInfoValue();
							String[] col = line.split("\\^");

							String inOutMoneyType = col[1].trim();
							log("inOutMoneyType转换后：" + inOutMoneyType);
							if ("市场转客户".equals(inOutMoneyType)) {
								moneyInfo.type = 1;
								moneyInfo.account = col[4];
								moneyInfo.note = col[5];
							} else if ("客户转市场".equals(inOutMoneyType)) {
								moneyInfo.type = 0;
								moneyInfo.account = col[6];
								moneyInfo.note = col[7];
							} else {
								log("非出入金，不写入集合" + inOutMoneyType);
								continue;
							}
							moneyInfo.id = col[0].trim();
							moneyInfo.money = Double.parseDouble(col[8].trim());
							moneyInfo.firmID = ((String) map.get(moneyInfo.account));
							if (moneyInfo.firmID == null) {
								moneyInfo.firmID = "firmid";
							}
							moneyInfo.compareDate = new java.sql.Date(date.getTime());
							moneyInfo.status = 0;
							moneyInfo.bankID = this.BANKID;

							vec.add(moneyInfo);
						}

					}

					bfr.close();
					return vec;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
			log("找不到文件" + fileName);
		}

		return null;
	}

	private Map<String, String> getCorrespondValueMap() {
		Map map = new HashMap();
		try {
			reUpCapitalProcessorRMI();
			String sql = " where bankid='" + this.BANKID + "' and isopen='1' ";
			Vector<CorrespondValue> ve = this.PROCESSOR.getCorrespondValue(sql);
			for (CorrespondValue corr : ve) {
				String account = corr.account1;

				String firmid = corr.contact;
				map.put(account, firmid);
			}
			return map;
		} catch (RemoteException e) {
			log("连接处理器获取交易商信息失败");
			log(Common.getExceptionTrace(e));
		}
		return null;
	}

	public ReturnValue rgstAccountQuery(CorrespondValue correspondValue) {
		log("市场发起签约, 市场席位号[" + correspondValue.firmID + "]账户性质[" + correspondValue.cardType + "]银行卡号[" + correspondValue.account + "]" + "]开户行["
				+ correspondValue.bankName + "]开户行地址[" + correspondValue.bankCity + "]证件号码[" + correspondValue.card + "]复合标志["
				+ correspondValue.checkFlag + "]");
		log(correspondValue.toString());
		ReturnValue value = new ReturnValue();
		reUpCapitalProcessorRMI();
		TrxResponse tResponse = null;
		SignUpRequest req = new SignUpRequest();

		try {
			req.setRequestID(String.valueOf(correspondValue.actionID));
			req.setCustSignInfo(correspondValue.signInfo);
			req.setCustName(correspondValue.accountName);
			req.setMerchantName(BankAdapter.getConfig("MarketName"));

			tResponse = req.postRequest();

			if (tResponse.isSuccess()) {
				value.result = 0L;
				value.remark = "签约成功";
				if (tResponse.getCustomerNo() == null) {
					value.remark = "签约成功，但银行返回信息为空";
				} else {
					String[] returnMsgs = tResponse.getCustomerNo().split("\\|", -1);
					value.funID = returnMsgs[0];
				}

				log("市场发起签约，交易商" + correspondValue.firmID + "成功");
				log("银行返回" + tResponse.getErrorMessage());
			} else {
				value.result = -1L;
				value.remark = tResponse.getErrorMessage().replace("'", "");

				log("市场发起签约，交易商" + correspondValue.firmID + "失败,市场流水" + correspondValue.actionID + "银行返回码" + tResponse.getReturnCode() + "失败原因"
						+ tResponse.getErrorMessage().replace("'", ""));
			}
		} catch (Exception e) {
			log(Common.getExceptionTrace(e));
		} finally {
			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = correspondValue.account;
			log.bankID = correspondValue.bankID;
			log.contact = correspondValue.firmID;
			log.firmID = correspondValue.firmID;
			endmsg.code = tResponse.getReturnCode();
			log.type = 3;
			endmsg.note = tResponse.getErrorMessage();
			if (!tResponse.isSuccess()) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}
		return value;
	}

	public ReturnValue delAccountQuery(CorrespondValue correspondValue) {
		log("市场端解约");
		ReturnValue value = new ReturnValue();
		SignUpRequest req = new SignUpRequest();
		TrxResponse tResponse = null;
		reUpCapitalProcessorRMI();
		try {
			req.setRequestID(String.valueOf(correspondValue.actionID));
			req.setBusinessID("MARKET");
			req.setFunctionID("0001");
			req.setCustSignInfo(correspondValue.signInfo);
			req.setCustomer(correspondValue.account1);
			req.setCustName(correspondValue.accountName);
			req.setMerchantName(BankAdapter.getConfig("MarketName"));
			tResponse = req.postRequest();
			if (tResponse.isSuccess()) {
				value.result = 0L;
				value.remark = tResponse.getErrorMessage();
				log("市场发出解约，交易商" + correspondValue.firmID + "成功");
				log("银行返回" + tResponse.getErrorMessage());
			} else {
				value.result = -1L;
				value.remark = tResponse.getErrorMessage();
				log("市场发出解约，交易商" + correspondValue.firmID + "失败,市场流水" + correspondValue.actionID + "银行返回码" + tResponse.getReturnCode() + "失败原因"
						+ tResponse.getErrorMessage());
			}
		} catch (Exception localException) {
		} finally {
			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = correspondValue.account;
			log.bankID = correspondValue.bankID;
			log.contact = correspondValue.firmID;
			log.firmID = correspondValue.firmID;
			endmsg.code = tResponse.getReturnCode();
			log.type = 4;
			endmsg.note = tResponse.getErrorMessage();
			if (!tResponse.isSuccess()) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		}

		return value;
	}

	public ReturnValue inMoneyQueryBank(InMoneyVO inMoneyVO) {
		ReturnValue value = new ReturnValue();
		AbcInfoValue result = new AbcInfoValue();

		log("市场端入金<<<<<<<<<<<" + Common.getDate().toLocaleString() + ">>>>>>>>>");

		value.result = -1L;

		PayOrderRequest tPayRequest = new PayOrderRequest();
		tPayRequest.setFunctionID("0025");
		tPayRequest.setRequestID(String.valueOf(inMoneyVO.getActionID()));
		tPayRequest.setBuyCustName(inMoneyVO.getPayInfo().accountName);
		tPayRequest.setBuyCustNo(inMoneyVO.getPayInfo().account1);
		tPayRequest.setOrderNo(String.valueOf(inMoneyVO.getActionID()));
		tPayRequest.setOrderType("A");
		tPayRequest.setPayAmount(new BigDecimal(inMoneyVO.getMoney()));
		tPayRequest.setOrdrMg("");

		TrxResponse tResponse = tPayRequest.postRequest();
		String OrderNo = String.valueOf(inMoneyVO.getActionID());
		if (tResponse.isSuccess()) {
			log("市场端入金申请,交易商[" + inMoneyVO.getFirmID() + "]入金申请[" + inMoneyVO.getMoney() + "]银行处理成功，银行流水[" + tResponse.getFunctionID() + "]市场流水["
					+ inMoneyVO.getActionID() + "]");

			log("准备发起入金支付:");
			reUpCapitalProcessorRMI();
			long actionID = -1L;
			String signInfo = "";
			try {
				result = this.PROCESSOR.getAbcInfo(inMoneyVO.getFirmID(), inMoneyVO.getActionID(), 0);
				actionID = result.actionID;
				signInfo = result.signInfo;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			tPayRequest.setFunctionID("0026");
			tPayRequest.setRequestID(String.valueOf(actionID));
			tPayRequest.setCustSignInfo(signInfo);
			tPayRequest.setOrderNo(OrderNo);
			tPayRequest.setPayAmount(new BigDecimal(inMoneyVO.getMoney()));
			tResponse = tPayRequest.postRequest();
			if (tResponse.isSuccess()) {
				log("市场端入金支付,交易商[" + inMoneyVO.getFirmID() + "]入金支付[" + inMoneyVO.getMoney() + "]银行处理成功，银行流水[" + tResponse.getFunctionID() + "]市场流水["
						+ actionID + "]");
				log("准备回调处理器入金:");
				long n = -1L;
				try {
					n = this.PROCESSOR.inMoney(getBankID(), inMoneyVO.getContact(), inMoneyVO.getPayInfo().account,
							new Timestamp(Common.getDate().getTime()), inMoneyVO.getMoney(), String.valueOf(actionID), inMoneyVO.getActionID(), 0,
							"market_in");
				} catch (RemoteException e) {
					log(Common.getExceptionTrace(e));
				} catch (Exception e) {
					log(Common.getExceptionTrace(e));
				}
				if (n >= 0L) {
					value.result = 0L;
					value.funID = String.valueOf(actionID);
					log("result[" + n + "]");
					log("市场端入金,交易商[" + inMoneyVO.getFirmID() + "]入金[" + inMoneyVO.getMoney() + "]处理成功");
				}
			} else {
				if ((tResponse.getReturnCode().startsWith("1")) || (tResponse.getReturnCode().equals("0999"))) {
					value.result = 5L;
				} else {
					BankAdapter.log("入金失败，银行返回失败，返回码：" + tResponse.getReturnCode());
					value.result = -1L;
				}
				value.funID = String.valueOf(actionID);
				value.remark = tResponse.getErrorMessage();
				BankAdapter.log("市场端入金支付，交易商[" + inMoneyVO.getFirmID() + "]失败,市场流水[" + actionID + "]银行流水号[" + tResponse.getFunctionID() + "]失败原因["
						+ tResponse.getErrorMessage() + "]银行返回码[" + tResponse.getReturnCode() + "]");
			}
		} else {
			value.result = (-1 * Integer.parseInt(tResponse.getReturnCode()));
			value.funID = OrderNo;
			value.remark = tResponse.getErrorMessage();
			BankAdapter.log("市场端入金申请，交易商[" + inMoneyVO.getFirmID() + "]失败,市场流水[" + inMoneyVO.getActionID() + "]银行流水号[" + tResponse.getFunctionID()
					+ "]失败原因[" + tResponse.getErrorMessage() + "]银行返回码[" + tResponse.getReturnCode() + "]");
		}

		if ((value != null) && (value.remark != null) && (value.remark.contains("'"))) {
			if (value.result == 0L)
				value.remark = "银行处理成功";
			else if (value.result == 5L)
				value.remark = "银行返回处理中";
			else {
				value.remark = "银行处理失败";
			}
		}
		if ((value != null) && (value.remark == null)) {
			if (value.result == 0L)
				value.remark = "银行处理成功";
			else if (value.result == 5L)
				value.remark = "银行返回处理中";
			else {
				value.remark = "银行处理失败";
			}
		}
		InterfaceLog log = new InterfaceLog();
		LogEndmsg endmsg = new LogEndmsg();
		log.account = inMoneyVO.getPayInfo().account;
		log.bankID = inMoneyVO.getBankID();
		log.contact = inMoneyVO.getFirmID();
		log.firmID = inMoneyVO.getFirmID();
		endmsg.code = tResponse.getReturnCode();
		log.type = 7;
		endmsg.note = tResponse.getErrorMessage();
		if (!tResponse.isSuccess()) {
			log.result = 1;
		}
		log.endMsg = endmsg.toString();
		interfaceLog(log);

		return value;
	}

	public ReturnValue outMoneyMarketDone(OutMoneyVO outMoneyVO) {
		ReturnValue value = new ReturnValue();
		try {
			AbcInfoValue result = new AbcInfoValue();
			log("市场端出金<<<<<<<<<<<" + Common.getDate().toLocaleString() + ">>>>>>>>>");
			value.result = -1L;

			PayOrderRequest tPayRequest = new PayOrderRequest();
			tPayRequest.setFunctionID("0025");
			tPayRequest.setRequestID(String.valueOf(outMoneyVO.actionID));
			tPayRequest.setBuyCustName(outMoneyVO.receiveInfo.accountName);
			tPayRequest.setBuyCustNo(outMoneyVO.receiveInfo.account1);
			tPayRequest.setOrderNo(String.valueOf(outMoneyVO.actionID));
			tPayRequest.setOrderType("B");
			tPayRequest.setPayAmount(new BigDecimal(outMoneyVO.money));
			tPayRequest.setOrdrMg("");

			TrxResponse tResponse = tPayRequest.postRequest();
			String OrderNo = String.valueOf(outMoneyVO.actionID);
			reUpCapitalProcessorRMI();
			if (tResponse.isSuccess()) {
				log("市场端出金申请,交易商[" + outMoneyVO.firmID + "]出金申请[" + outMoneyVO.money + "]银行处理成功，银行流水[" + tResponse.getFunctionID() + "]市场流水["
						+ outMoneyVO.actionID + "]");

				log("准备发起出金支付:");
				long actionID = -1L;
				String signInfo = "";
				try {
					result = this.PROCESSOR.getAbcInfo(outMoneyVO.firmID, outMoneyVO.actionID, 1);
					actionID = result.actionID;
					signInfo = result.signInfo;
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				tPayRequest.setFunctionID("0026");
				tPayRequest.setRequestID(String.valueOf(actionID));
				tPayRequest.setCustSignInfo(signInfo);
				tPayRequest.setOrderNo(OrderNo);
				tPayRequest.setPayAmount(new BigDecimal(outMoneyVO.money));
				tResponse = tPayRequest.postRequest();
				if (tResponse.isSuccess()) {
					log("市场端出金支付,交易商[" + outMoneyVO.firmID + "]出金支付[" + outMoneyVO.money + "]银行处理成功，银行流水[" + tResponse.getFunctionID() + "]市场流水["
							+ outMoneyVO.actionID + "]");

					value.result = 0L;
					value.funID = String.valueOf(actionID);
				} else {
					if ((tResponse.getReturnCode().startsWith("1")) || (tResponse.getReturnCode().equals("0999")))
						value.result = 5L;
					else {
						try {
							BankAdapter.log("出金银行返回失败，返回码：" + tResponse.getReturnCode());
							value.result = -1L;
						} catch (Exception e) {
							log("处理银行返回码异常：" + Common.getExceptionTrace(e));
							value.result = -1L;
						}
					}

					value.funID = String.valueOf(actionID);
					value.remark = tResponse.getErrorMessage();
					log("市场端出金支付，交易商[" + outMoneyVO.firmID + "]失败,市场流水[" + actionID + "]银行流水号[" + tResponse.getFunctionID() + "]失败原因["
							+ tResponse.getErrorMessage() + "]银行返回码[" + tResponse.getReturnCode() + "]");
				}
			} else {
				if (tResponse.getReturnCode().startsWith("1"))
					value.result = 5L;
				else {
					try {
						value.result = Integer.parseInt(tResponse.getReturnCode());
					} catch (Exception e) {
						log("处理银行返回码异常：" + Common.getExceptionTrace(e));
						value.result = -1L;
					}
				}
				value.funID = OrderNo;
				value.remark = tResponse.getErrorMessage();
				log("市场端出金申请，交易商[" + outMoneyVO.firmID + "]失败,市场流水[" + outMoneyVO.actionID + "]银行流水号[" + tResponse.getFunctionID() + "]失败原因["
						+ tResponse.getErrorMessage() + "]银行返回码[" + tResponse.getReturnCode() + "]");
			}

			if ((value != null) && (value.remark != null) && (value.remark.contains("'"))) {
				if (value.result == 0L)
					value.remark = "银行处理成功";
				else if (value.result == 5L)
					value.remark = "银行返回处理中";
				else {
					value.remark = "银行处理失败";
				}
			}
			if ((value != null) && (value.remark == null)) {
				if (value.result == 0L)
					value.remark = "银行处理成功";
				else if (value.result == 5L)
					value.remark = "银行返回处理中";
				else {
					value.remark = "银行处理失败";
				}
			}
			InterfaceLog log = new InterfaceLog();
			LogEndmsg endmsg = new LogEndmsg();
			log.account = outMoneyVO.receiveInfo.account;
			log.bankID = outMoneyVO.bankID;
			log.contact = outMoneyVO.firmID;
			log.firmID = outMoneyVO.firmID;
			endmsg.code = tResponse.getReturnCode();
			log.type = 6;
			endmsg.note = tResponse.getErrorMessage();
			if (!tResponse.isSuccess()) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			interfaceLog(log);
		} catch (Exception e) {
			log("出金方法异常，返回处理器处理中：" + Common.getExceptionTrace(e));
			value.result = 5L;
			value.remark = "适配器异常，出金处理中";
		}
		return value;
	}

	public int setBankMoneyInfo(java.util.Date date) {
		return 0;
	}

	public double accountQuery(CorrespondValue correspondValue, String password) {
		log("\n查询账号余额<<<<<<<<<<<<<+" + Common.getDate().toLocaleString() + "+>>>>>>>>>>");
		double result = -1.0D;
		AccInfoQueryRequest req = new AccInfoQueryRequest();
		long actionID = -1L;
		try {
			reUpCapitalProcessorRMI();
			actionID = this.PROCESSOR.getMktActionID();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		log("流水号[" + actionID + "]");
		String functionID = String.valueOf(actionID);
		if (functionID.length() > 4)
			functionID = functionID.substring(functionID.length() - 4, functionID.length());
		else if (functionID.length() < 4) {
			for (int i = 0; i < 4 - functionID.length(); i++) {
				functionID = "0" + functionID;
			}
		}
		req.setFunctionID(functionID);

		TrxResponse res = req.postRequest();
		if (res.getReturnCode().trim().equals("0000")) {
			log("交易商[" + correspondValue.firmID + "]查询余额成功");
		} else {
			result = -1.0D;
			log("交易商[" + correspondValue.firmID + "]查询余额失败,银行返回信息[" + res.getErrorMessage() + "]");
		}
		return result;
	}

	public ReturnValue inMoneyQueryBankenregister(InMoneyVO inMoneyVO) {
		return null;
	}

	public ReturnValue modAccount(CorrespondValue corrOld, CorrespondValue corrNew) {
		log("市场发起改约, 市场席位号[" + corrOld.firmID + "];子账号[" + corrOld.account1 + "];旧卡号[" + corrOld.account + "];新卡号[" + corrNew.account + "]");
		log("旧信息【" + corrOld.toString() + "】");
		log("新信息【" + corrNew.toString() + "】");
		ReturnValue value = new ReturnValue();

		reUpCapitalProcessorRMI();
		ModifCustAccNoRequest req = new ModifCustAccNoRequest();
		long actionID = -1L;
		try {
			actionID = this.PROCESSOR.getMktActionID();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		req.setFunctionID("0031");
		req.setRequestID(String.valueOf(actionID));
		req.setNewCustAccNo(corrNew.account);
		req.setOldCustAccNo(corrOld.account);
		req.setCustomer(corrOld.account1);

		if ("1".equals(corrOld.cardType))
			req.setCustType("1");
		else {
			req.setCustType("0");
		}

		TrxResponse res = req.postRequest();
		if (res.getReturnCode().trim().equals("0000")) {
			value.result = 0L;
			value.remark = "改约成功";
			log("市场发起改约，交易商" + corrOld.firmID + "成功");
			log("银行返回席位号信息" + res.getFunctionID() + "成功");
		} else {
			value.result = -1L;
			value.remark = ("改约失败,失败原因：" + res.getErrorMessage());
			log("市场发起改约，交易商" + corrOld.firmID + "失败,市场流水" + actionID + "银行流水号" + res.getMerchantID() + "失败原因" + res.getErrorMessage());
		}
		return value;
	}

	public boolean dayDataReady(java.util.Date date) {
		return false;
	}

	public ReturnValue hxSentDZ(Vector<HXSentQSMsgValue> vector, java.util.Date date) {
		return null;
	}

	public ReturnValue setBankMoneyInfo(Hashtable<String, TradeResultValue> ht) {
		return null;
	}

	public ReturnValue setTotalMoneyInfo(Hashtable<String, Double> ht) {
		return null;
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

	public void pay1() {
		String tRequestID = "32432";
		String tCustSignInfo = "MIIFFAYJKoZIhvcNAQcCoIIFBTCCBQECAQExCzAJBgUrDgMCGgUAMIIBmAYJKoZIhvcNAQcBoIIBiQSCAYUwMDAwMDM3MzAwMDAwMTcwW1tNZXJjaGFudElEXV0wMDAwMTAwMDAyMDAwMDNbW01lcmNoYW50VHJ4Tm9dXVJTVTEzMDEyMzAwMVtbRnVuY3Rpb25JRF1dMDAwMFtbTWVyY2hhbnROYW1lXV3V472tsuLK1MnMu6dbW0N1c3ROYW1lXV3V472tsuLK1L/Nu6dbW190aW1lXV1XZWQgSmFuIDIzIDEzOjIxOjAxIFVUQyswODAwIDIwMTMwMDAwMDE4Nzy/zbunx6nUvMi3yM8+Cgq9u9LXw/uzxqO6vbvS18rQs6HHqdS8Cr270tfB98uuusWjulJTVTEzMDEyMzAwMQq9u9LXytCzocP7s8ajutXjva2y4srUycy7pwq9u9LXytCzobHgusWjujAwMDAxMDAwMDIwMDAwMwq/zbunw/uzxqO61eO9rbLiytS/zbunCsep1LzKsbzko7pXZWQgSmFuIDIzIDEzOjIxOjAwIFVUQyswODAwIDIwMTMwMDAwMDAwMKCCAn8wggJ7MIIB5KADAgECAgp7l8oQJ1oE1h0lMA0GCSqGSIb3DQEBBQUAMBwxDDAKBgNVBAMTA0FCQzEMMAoGA1UEChMDQUJDMB4XDTExMTIwMzA4MTIzNFoXDTEzMTIwMzA4MTIzNFowSjEeMBwGA1UEAxMVMTEwMjg0NjM3MTYuMDAwMC4wMDAwMRowGAYDVQQLExFQZXJzb25hbCBDdXN0b21lcjEMMAoGA1UEChMDQUJDMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEHPbSTmPwHC7e3OQVQRV0gle861Ce9S4xo2Au/DplqzyvrgBNu8r/IzUPwYX3Hi48hZZvJWZWXhrL2UGw2U2ZG+LzuPKSHdLjCaVme/I+U2fvwAD2L3eKsRt2mQQHwiLSN5jemIqHfP1wDgCy3Jel3W7I4vZUaNIdo1aCsbGrsQIDAQABo4GVMIGSMB8GA1UdIwQYMBaAFCWaxgz954dzEQSDbzJoNrHBgQzMMAwGA1UdEwQFMAMBAQAwQgYDVR0fBDswOTA3oDWgM6QxMC8xETAPBgNVBAMTCGNybDE2MjI5MQwwCgYDVQQLEwNjcmwxDDAKBgNVBAoTA0FCQzAdBgNVHQ4EFgQURqBY4ctjNUlvzoiYdriZMSZy+jkwDQYJKoZIhvcNAQEFBQADgYEAI/A+F9w5hhjWP2Mq5z97V4J9vooOEv4wUBwqfi1/kZt+MadwII4EU7exZcMjxe363UmYOwGU1d5tPl3YgfNjbv/eLno4yIQQLBmBAmDh+goQyxm+d22yu6cxIw6Ix0lSSxzCBtEtzOD8Gx7eO5RgUVOFbCwWYculnJdGJ+s/EOMxgc8wgcwCAQEwKjAcMQwwCgYDVQQDEwNBQkMxDDAKBgNVBAoTA0FCQwIKe5fKECdaBNYdJTAJBgUrDgMCGgUAMA0GCSqGSIb3DQEBAQUABIGAE3xFB24SM2t6reaI9s/fo6H1gJbXSNmL2bBHDtzx7Q8T75rR/5lDFS5lp3WUb2UaPTSBaHHM2hvjJXBkaUUVjhz9qV9jsbukyPbz4cr8m1TW05d/hm9T1TNyD1m/AKZRjttxOuGrL9mTMx/l1wAT/hTzC7eDQ4gHlFgIYRk/AAI=";
		String tCustName = "测试";
		String tMerchantName = "ceshi";

		SignUpRequest tRequest = new SignUpRequest();

		tRequest.setCustSignInfo(tCustSignInfo);
		tRequest.setRequestID(tRequestID);
		tRequest.setBusinessID("MARKET");
		tRequest.setFunctionID("0000");
		tRequest.setCustName(tCustName);
		tRequest.setMerchantName(tMerchantName);

		TrxResponse res = tRequest.postRequest();
		String sReturnMsg = res.getErrorMessage();
		String sReturnCode = res.getReturnCode();

		if (res.getReturnCode().trim().equals("0000")) {
			log("成功");
		} else
			log("失败,银行返回信息[" + res.getErrorMessage() + "]");
	}

	public void pay2() {
		String tRequestID = "1234";
		String tCustSignInfo = "MIIEmwYJKoZIhvcNAQcCoIIEjDCCBIgCAQExCzAJBgUrDgMCGgUAMIIBHwYJKoZIhvcNAQcBoIIBEASCAQwwMDAwMDI1MjAwMDAwMTA4W1tNZXJjaGFudFRyeE5vXV1SUVAxMzAxMjMwMDFbW0Z1bmN0aW9uSURdXTAwMDlbW1BheUFtb3VudF1dMTAwLjAwW1tfdGltZV1dV2VkIEphbiAyMyAxMzo0ODoyNyBVVEMrMDgwMCAyMDEzMDAwMDAxMjg8yrXKsdanuLbIt8jPPgoKvbvS18P7s8ajur270tfK0LOhyrXKsdanuLYKvbvS18H3y666xaO6UlFQMTMwMTIzMDAxCtanuLa98Lbuo7oxMDAuMDAKvbvS18qxvOSjuldlZCBKYW4gMjMgMTM6NDg6MjcgVVRDKzA4MDAgMjAxMzAwMDAwMDAwoIICfzCCAnswggHkoAMCAQICCnuXyhAnWgTWHSUwDQYJKoZIhvcNAQEFBQAwHDEMMAoGA1UEAxMDQUJDMQwwCgYDVQQKEwNBQkMwHhcNMTExMjAzMDgxMjM0WhcNMTMxMjAzMDgxMjM0WjBKMR4wHAYDVQQDExUxMTAyODQ2MzcxNi4wMDAwLjAwMDAxGjAYBgNVBAsTEVBlcnNvbmFsIEN1c3RvbWVyMQwwCgYDVQQKEwNBQkMwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMQc9tJOY/AcLt7c5BVBFXSCV7zrUJ71LjGjYC78OmWrPK+uAE27yv8jNQ/BhfceLjyFlm8lZlZeGsvZQbDZTZkb4vO48pId0uMJpWZ78j5TZ+/AAPYvd4qxG3aZBAfCItI3mN6Yiod8/XAOALLcl6Xdbsji9lRo0h2jVoKxsauxAgMBAAGjgZUwgZIwHwYDVR0jBBgwFoAUJZrGDP3nh3MRBINvMmg2scGBDMwwDAYDVR0TBAUwAwEBADBCBgNVHR8EOzA5MDegNaAzpDEwLzERMA8GA1UEAxMIY3JsMTYyMjkxDDAKBgNVBAsTA2NybDEMMAoGA1UEChMDQUJDMB0GA1UdDgQWBBRGoFjhy2M1SW/OiJh2uJkxJnL6OTANBgkqhkiG9w0BAQUFAAOBgQAj8D4X3DmGGNY/YyrnP3tXgn2+ig4S/jBQHCp+LX+Rm34xp3AgjgRTt7FlwyPF7frdSZg7AZTV3m0+XdiB82Nu/94uejjIhBAsGYECYOH6ChDLGb53bbK7pzEjDojHSVJLHMIG0S3M4PwbHt47lGBRU4VsLBZhy6Wcl0Yn6z8Q4zGBzzCBzAIBATAqMBwxDDAKBgNVBAMTA0FCQzEMMAoGA1UEChMDQUJDAgp7l8oQJ1oE1h0lMAkGBSsOAwIaBQAwDQYJKoZIhvcNAQEBBQAEgYB/lw6ocEO2HbOqGmz4oDJoel30c472cHz5PJMGU2FLBDTuONzvEeTjx22gdhyAm4MBFycKxrlE7hWnadZmDIQevptTVNUhZUGyxrbQvogwBP0P95ndsaWMa21gl2GzxI6eOQ/0qE1XB37KG67da5PmycLJgKthh8spqMs0FZFn2Q==";
		String tCustSignInfo2 = "";
		BigDecimal tPayAmount = new BigDecimal("100");
		String tCustomer = "21434";

		String tMessage = "实时支付";

		PayRequest tPayRequest = new PayRequest();
		tPayRequest.setRequestID(tRequestID);
		tPayRequest.setFunctionID("0009");
		tPayRequest.setCustSignInfo(tCustSignInfo);
		tPayRequest.setCustSignInfo2(tCustSignInfo2);
		tPayRequest.setPayAmount(tPayAmount);
		tPayRequest.setCustomerNo(tCustomer);
		tPayRequest.setMessage(tMessage);

		TrxResponse res = tPayRequest.postRequest();
		String sReturnMsg = res.getErrorMessage();
		String sReturnCode = res.getReturnCode();

		if (res.getReturnCode().trim().equals("0000")) {
			log("成功");
		} else {
			log("失败,银行返回信息[" + res.getErrorMessage() + "]");
		}
	}

	public void pay3() {
		String tRequestID = "1234";
		BigDecimal tPayAmount = new BigDecimal("100");
		BigDecimal tFeeAmount = new BigDecimal("10");
		String tCustomer = "测试";
		String tOrgReqSeqTrace = "23414";
		String tTrnxType = "11";
		String tMsg = "交易信息";

		PayRequest tPayRequest = new PayRequest();
		tPayRequest.setRequestID(tRequestID);
		tPayRequest.setFunctionID("0030");
		tPayRequest.setPayAmount(tPayAmount);
		tPayRequest.setCustomerNo(tCustomer);
		tPayRequest.setReqSeqTrace(tOrgReqSeqTrace);
		tPayRequest.setFeeAmount(tFeeAmount);
		tPayRequest.setFlag(tTrnxType);
		tPayRequest.setMessage(tMsg);

		TrxResponse res = tPayRequest.postRequest();
		String sReturnMsg = res.getErrorMessage();
		String sReturnCode = res.getReturnCode();

		if (res.getReturnCode().trim().equals("0000")) {
			log("成功");
		} else {
			log("失败,银行返回信息[" + res.getErrorMessage() + "]");
		}
	}

	public String downDayDataFile(java.util.Date date) {
		long actionID = -1L;
		try {
			reUpCapitalProcessorRMI();
			actionID = this.PROCESSOR.getMktActionID();
		} catch (RemoteException e1) {
			log("获取流水号失败");
			log(Common.getExceptionTrace(e1));
			return null;
		}
		String tRequestID = actionID + "";
		if (date == null) {
			date = Common.getLastDay(new java.util.Date());
		}
		String tStatementDate = Common.df7.format(date);
		String ttxtsettleFilePath = getConfig("dataPath") + "crjmx_" + tStatementDate + ".txt";
		String tFileName = ttxtsettleFilePath;

		CreatePayDetailRequest tRequest = new CreatePayDetailRequest();
		tRequest.setRequestID(tRequestID);
		tRequest.setStartDate(tStatementDate);
		tRequest.setEndDate(tStatementDate);

		TrxResponse tResponse = tRequest.postRequest();

		if (tResponse.isSuccess()) {
			StatementDownloadFile tSettleFile = new StatementDownloadFile(tResponse, tFileName);
			if (tSettleFile.getFlag()) {
				log("SettleDate     = [" + tStatementDate + "]<br>");
				log("FileName       = [" + tSettleFile.getFileName() + "]<br>");
			} else {
				log("文件不存在<br>");
			}
		} else {
			log("ReturnCode   = [" + tResponse.getReturnCode() + "]<br>");
			log("ErrorMessage = [" + tResponse.getErrorMessage() + "]<br>");
		}
		return ttxtsettleFilePath;
	}
}