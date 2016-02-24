package gnnt.trade.bank.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.data.boc.BOCExDataImpl;
import gnnt.trade.bank.data.boc.vo.BOCConstant;
import gnnt.trade.bank.data.ccb.CCBExDataImpl;
import gnnt.trade.bank.data.ccb.vo.CCBConstant;
import gnnt.trade.bank.data.ceb.CEBExDataImpl;
import gnnt.trade.bank.data.ceb.vo.CebConstant;
import gnnt.trade.bank.data.cgb.CGBExDataImpl;
import gnnt.trade.bank.data.cgb.vo.CGBConstant;
import gnnt.trade.bank.data.cib.CIBDataImpl;
import gnnt.trade.bank.data.cib.vo.CIBConstant;
import gnnt.trade.bank.data.cmbc.CMBCDataImpl;
import gnnt.trade.bank.data.hxb.ExchangeDataImpl;
import gnnt.trade.bank.data.hxb.HXConstant;
import gnnt.trade.bank.data.hz.HZExDataImpl;
import gnnt.trade.bank.data.hz.vo.HZConstant;
import gnnt.trade.bank.data.icbc.ICBCExDataImpl;
import gnnt.trade.bank.data.icbc.vo.ICBCConstant;
import gnnt.trade.bank.data.jsb.JsConstant;
import gnnt.trade.bank.data.jsb.JsExDataImpl;
import gnnt.trade.bank.data.sfz.PADataImpl;
import gnnt.trade.bank.data.sfz.vo.PAConstant;
import gnnt.trade.bank.data.yjf.YjfConstant;
import gnnt.trade.bank.data.yjf.YjfExDataImpl;
import gnnt.trade.bank.util.Common;
import gnnt.trade.bank.util.StaticMsg;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.ClearingStatusVO;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.SystemStatusVO;

public class FileThread extends Thread {
	private BankDAO DAO = null;
	private static List<String> bankIDs = null;
	private static String FileThreadsyn = "FileThreadsyn";

	public FileThread() {
		try {
			this.DAO = BankDAOFactory.getDAO();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		}
	}

	public void run() {
		log("自动清算线程启动");
		for (;;) {
			try {
				Thread.sleep(getTime());
				Map<String, ClearingStatusVO> clearing = ifsendFile();
				if (clearing != null) {
					ClearingStatusVO ccb = (ClearingStatusVO) clearing.get(CCBConstant.bankID);
					if ((ccb != null) && (ccb.generalStatus == 1)) {
						log("文件生成状态generalStatus：" + ccb.generalStatus + ";[1生成中2生成成功3生成失败]");
						ReturnValue result = sendCCBFile(ccb.tradeDate);
						System.out.println(result.toString());
						modClearingStatusVO(ccb, result.result);
					}
					ClearingStatusVO yjf = (ClearingStatusVO) clearing.get(YjfConstant.bankID);
					if ((yjf != null) && (yjf.generalStatus == 1)) {
						log("文件生成状态generalStatus：" + yjf.generalStatus + ";[1生成中2生成成功3生成失败]");
						ReturnValue result = sendYJFFile(yjf.tradeDate);
						System.out.println(result.toString());
						modClearingStatusVO(yjf, result.result);
					}
					ClearingStatusVO boc = (ClearingStatusVO) clearing.get(BOCConstant.bankID);
					if ((boc != null) && (boc.generalStatus == 1)) {
						log("文件生成状态generalStatus：" + boc.generalStatus + ";[1生成中2生成成功3生成失败]");
						ReturnValue result = sendBOCFile(boc.tradeDate);
						System.out.println(result.toString());
						modClearingStatusVO(boc, result.result);
					}
					ClearingStatusVO jsb = (ClearingStatusVO) clearing.get(JsConstant.bankID);
					if ((jsb != null) && (jsb.generalStatus == 1)) {
						log("文件生成状态generalStatus：" + jsb.generalStatus + ";[1生成中2生成成功3生成失败]");
						ReturnValue result = sendJsFile(jsb.tradeDate);
						System.out.println(result.toString());
						modClearingStatusVO(jsb, result.result);
					}
					ClearingStatusVO hz = (ClearingStatusVO) clearing.get(HZConstant.bankID);
					if ((hz != null) && (hz.generalStatus == 1)) {
						log("文件生成状态generalStatus：" + hz.generalStatus + ";[1生成中2生成成功3生成失败]");
						ReturnValue result = sendHZFile(hz.tradeDate);
						System.out.println(result.toString());
						modClearingStatusVO(hz, result.result);
					}
					ClearingStatusVO icbc = (ClearingStatusVO) clearing.get(ICBCConstant.ICBCbankID);
					if ((icbc != null) && (icbc.generalStatus == 1)) {
						log("文件生成状态generalStatus：" + icbc.generalStatus + ";[1生成中2生成成功3生成失败]");
						ReturnValue result = sendICBCFile(icbc.tradeDate);
						System.out.println(result.toString());
						modClearingStatusVO(icbc, result.result);
					}
					ClearingStatusVO hx = (ClearingStatusVO) clearing.get(HXConstant.bankID);
					if ((hx != null) && (hx.generalStatus == 1)) {
						ReturnValue result = sendHXFile(hx.bankID, hx.tradeDate);
						if (result.result == 0L) {
							modClearingStatusVO(hx, result.result);
						}
					}
					ClearingStatusVO gd = (ClearingStatusVO) clearing.get(CebConstant.bankID);
					if ((gd != null) && (gd.generalStatus == 1)) {
						ReturnValue result = sendGDFile(gd.bankID, gd.tradeDate);

						modClearingStatusVO(gd, result.result);
					}
					ClearingStatusVO cib = (ClearingStatusVO) clearing.get(CIBConstant.bankID);
					if ((cib != null) && (cib.generalStatus == 1)) {
						log("文件生成状态generalStatus：" + cib.generalStatus + ";[1生成中2生成成功3生成失败]");
						ReturnValue result = sendCIBFile(cib.tradeDate);
						System.out.println(result.toString());
						modClearingStatusVO(cib, result.result);
					}
					ClearingStatusVO pa = (ClearingStatusVO) clearing.get(PAConstant.bankID);
					if ((pa != null) && (pa.generalStatus == 1)) {
						log("文件生成状态generalStatus：" + pa.generalStatus + ";[1生成中2生成成功3生成失败]");
						ReturnValue result = sendPAFile(pa.tradeDate);
						System.out.println(result.toString());
						modClearingStatusVO(pa, result.result);
					}
					ClearingStatusVO cgb = (ClearingStatusVO) clearing.get(CGBConstant.bankID);
					if ((cgb != null) && (cgb.generalStatus == 1)) {
						log("文件生成状态generalStatus：" + cgb.generalStatus + ";[1生成中2生成成功3生成失败]");
						ReturnValue result = sendCGBFile(cgb.tradeDate);
						System.out.println(result.toString());
						modClearingStatusVO(cgb, result.result);
					}
				} else {
					System.out.println("当前状态不满足生成清算文件条件");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void modClearingStatusVO(ClearingStatusVO value, long result) {
		value.generalTime = new Date();
		if (result >= 0L) {
			value.generalStatus = 2;
		} else {
			value.generalStatus = 3;
		}
		try {
			this.DAO.modClearing(value);
		} catch (SQLException e) {
			System.out.println(Common.getExceptionTrace(e));
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println(Common.getExceptionTrace(e));
			e.printStackTrace();
		}
		System.out.println("生成清算文件，返回生成结果[" + result + "]记录结果信息：" + value.toString());
	}

	private Map<String, ClearingStatusVO> ifsendFile() {
		Map<String, ClearingStatusVO> result = null;
		List<String> qsBanks = getQSBank();
		if ((qsBanks == null) || (qsBanks.size() <= 0)) {
			System.out.println("没有清算银行");
		} else {
			System.out.print("需要发送清算的银行有:[");
			for (int i = 0; i < qsBanks.size(); i++) {
				System.out.print((String) qsBanks.get(i) + ",");
			}
			System.out.println("]");
		}
		SystemStatusVO ssv = getSSV();
		if (ssv.status != 3) {
			return result;
		}
		try {
			result = new HashMap();
			for (String bankID : getQSBank()) {
				if (bankID == null) {
					System.out.println("空银行编号");
				} else if (HXConstant.bankID.equalsIgnoreCase(bankID)) {
					result.put(HXConstant.bankID, getClearingStatusVO(HXConstant.bankID, ssv.tradeDate));
				} else if (CCBConstant.bankID.equalsIgnoreCase(bankID)) {
					result.put(CCBConstant.bankID, getClearingStatusVO(CCBConstant.bankID, ssv.tradeDate));
				} else if (YjfConstant.bankID.equalsIgnoreCase(bankID)) {
					result.put(YjfConstant.bankID, getClearingStatusVO(YjfConstant.bankID, ssv.tradeDate));
				} else if (BOCConstant.bankID.equalsIgnoreCase(bankID)) {
					result.put(BOCConstant.bankID, getClearingStatusVO(BOCConstant.bankID, ssv.tradeDate));
				} else if (HZConstant.bankID.equalsIgnoreCase(bankID)) {
					result.put(HZConstant.bankID, getClearingStatusVO(HZConstant.bankID, ssv.tradeDate));
				} else if (JsConstant.bankID.equalsIgnoreCase(bankID)) {
					result.put(JsConstant.bankID, getClearingStatusVO(JsConstant.bankID, ssv.tradeDate));
				} else if (CebConstant.bankID.equalsIgnoreCase(bankID)) {
					result.put(CebConstant.bankID, getClearingStatusVO(CebConstant.bankID, ssv.tradeDate));
				} else if (CIBConstant.bankID.equalsIgnoreCase(bankID)) {
					result.put(CIBConstant.bankID, getClearingStatusVO(CIBConstant.bankID, ssv.tradeDate));
				} else if (PAConstant.bankID.equalsIgnoreCase(bankID)) {
					result.put(PAConstant.bankID, getClearingStatusVO(PAConstant.bankID, ssv.tradeDate));
				} else if (ICBCConstant.ICBCbankID.equalsIgnoreCase(bankID)) {
					result.put(ICBCConstant.ICBCbankID, getClearingStatusVO(ICBCConstant.ICBCbankID, ssv.tradeDate));
				} else if (CGBConstant.bankID.equalsIgnoreCase(bankID)) {
					result.put(CGBConstant.bankID, getClearingStatusVO(CGBConstant.bankID, ssv.tradeDate));
				}
			}
			sysDateFile(result, ssv.tradeDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}

	private void sysDateFile(Map<String, ClearingStatusVO> result, Date tradeDate) {
		Iterator<String> it = result.keySet().iterator();
		if (it != null) {
			while (it.hasNext()) {
				String bankid = (String) it.next();
				ClearingStatusVO value = (ClearingStatusVO) result.get(bankid);
				if ((value == null) || (!Tool.fmtDate(value.tradeDate).equalsIgnoreCase(Tool.fmtDate(tradeDate)))) {
					value = new ClearingStatusVO();
					value.bankID = bankid;
					value.tradeDate = tradeDate;
					try {
						int len = this.DAO.addClearing(value);
						if (len > 0) {
							result.put(bankid, value);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private ClearingStatusVO getClearingStatusVO(String bankID, Date tradeDate) {
		ClearingStatusVO result = null;
		try {
			result = this.DAO.getMaxClearing(bankID);
			if (result == null) {
				result = new ClearingStatusVO();
				result.bankID = bankID;
				result.tradeDate = tradeDate;
				int len = this.DAO.addClearing(result);
				if (len > 0) {
					result = this.DAO.getMaxClearing(bankID);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	private SystemStatusVO getSSV() {
		SystemStatusVO result = null;
		try {
			result = this.DAO.getSystemStatus();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private ReturnValue sendHXFile(String bankID, Date date) {
		return new ExchangeDataImpl().send(bankID, date);
	}

	private ReturnValue sendCCBFile(Date date) {
		return new CCBExDataImpl().send(date);
	}

	private ReturnValue sendGDFile(String bankID, Date date) {
		return new CEBExDataImpl().send(date);
	}

	private ReturnValue sendYJFFile(Date date) {
		return new YjfExDataImpl().send(date);
	}

	private ReturnValue sendBOCFile(Date date) {
		return new BOCExDataImpl().send(date);
	}

	private ReturnValue sendHZFile(Date date) {
		return new HZExDataImpl().send(date);
	}

	private ReturnValue sendJsFile(Date date) {
		return new JsExDataImpl().send(date);
	}

	private ReturnValue sendICBCFile(Date date) {
		return new ICBCExDataImpl().send(date);
	}

	private ReturnValue sendCIBFile(Date date) {
		return new CIBDataImpl().send(date);
	}

	private ReturnValue sendPAFile(Date date) {
		return new PADataImpl().send(date);
	}

	private ReturnValue sendCMBCFile(Date date) {
		return new CMBCDataImpl().send(date);
	}

	private ReturnValue sendCGBFile(Date date) {
		return new CGBExDataImpl().send(date);
	}

	private int getTime() {
		int time = 5;
		try {
			String sec = Tool.getConfig("qssleeptime");
			if ((sec != null) && (sec.trim().length() > 0)) {
				time = Integer.parseInt(sec);
				if ((time <= 0) || (time > 100)) {
					time = 2;
				}
			}
		} catch (Exception localException) {
		}
		return time * 1000 * 60;
	}

	private List<String> getQSBank() {
		if (bankIDs != null) {
			return bankIDs;
		}
		synchronized (FileThreadsyn) {
			if (bankIDs != null) {
				return bankIDs;
			}
			bankIDs = new ArrayList();
			StaticMsg msg = new StaticMsg();
			Iterator<String> it = msg.getBankMap().keySet().iterator();

			String procUrl = "";
			String qsBankServiceNames = Tool.getConfig("QSBankRmiServiceNames");
			String[] strA = qsBankServiceNames.split(",", 0);
			while (it.hasNext()) {
				BankValue value = (BankValue) msg.getBankMap().get(it.next());
				if ((strA == null) || (strA.length <= 0)) {
					break;
				}
				for (int i = 0; i < strA.length; i++) {
					procUrl = "//" + Tool.getConfig("RmiIpAddress") + ":" + Tool.getConfig("RmiPortNumber") + "/" + strA[i];
					if (procUrl.equalsIgnoreCase(value.beleiveProcessor)) {
						bankIDs.add(value.bankID);
					}
				}

			}
		}
		return bankIDs;
	}

	private void log(String string) {
		Logger plog = Logger.getLogger("Processorlog");
		plog.debug(string);
	}
}
