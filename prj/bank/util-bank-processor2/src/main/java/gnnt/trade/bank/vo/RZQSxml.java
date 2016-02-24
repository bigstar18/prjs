package gnnt.trade.bank.vo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.w3c.dom.Element;

import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmRightValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.MarketRightValue;

public class RZQSxml {
	private String fileName = "rizhongqingsuan";
	private String charset;
	private AddXml xml = null;

	public RZQSxml() {
	}

	public RZQSxml(String url, String charset) {
		this.charset = charset;
		this.fileName = (url + this.fileName);
	}

	public String getRZQSxml(RZQSValue qs) {
		return getRZQSxml(qs, null);
	}

	public String getRZQSxml(RZQSValue qs, Date date) {
		if (qs == null) {
			qs = new RZQSValue();
		}
		mkXml(qs, date);
		addTitle(qs);
		addMaketXml(qs);
		addFirmXml(qs);
		return this.fileName;
	}

	private void addFirmXml(RZQSValue qs) {
		Vector<FirmRightValue> vector = qs.getFrv();
		for (FirmRightValue fr : vector) {
			Map<String, String> map = new HashMap();
			Map<String, String> map2 = new HashMap();
			map.put("MARKET_SEQUENCE", fr.actionID);
			map.put("FIRM_ID", fr.firmID);
			map.put("FIRM_BANKACCOUNT", fr.account);
			map.put("CURRENCY", fr.currency);
			map.put("REMIT_TYPE", fr.type + "");
			map.put("ASSERT_CHANGE", getDouble(fr.firmErrorMoney));
			map.put("CASH_CHANGE", getDouble(fr.cashMoney));
			map.put("BILL_CHANGE", getDouble(fr.billMoney));
			map.put("AVAILABLE_BALANCE_CHANGE", getDouble(fr.availableBalance));
			map2.put("CASH", getDouble(fr.cash));
			map2.put("CREDIT", getDouble(fr.credit));
			try {
				Element element = this.xml.setChildElements("CONTEXT.FIRM_DATA_LIST", "RECORD", map);
				this.xml.setChildElements(element, "BETAKE_MARGIN_CHANGE", map2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void addMaketXml(RZQSValue qs) {
		Map<String, String> map = new HashMap();
		MarketRightValue mr = qs.getMarketRight();
		map.put("POUNDAGE_INCOME", getDouble(mr.maketMoney == null ? new BigDecimal(0) : mr.maketMoney));
		map.put("BANK_BALANCE", getDouble(mr.bankErrorMoney));
		map.put("FINANCING_BALANCE", getDouble(mr.maketErrorMoney));
		map.put("CURRENCY", mr.currency);
		map.put("REMIT_TYPE", mr.type + "");
		try {
			this.xml.setChildElements("CONTEXT", "MARKET_DATA", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addTitle(RZQSValue qs) {
		Map<String, String> maket = new HashMap();
		maket.put("FILE_NAME", qs.fileName);
		maket.put("BANK_ID", qs.bankID);
		maket.put("MARKET_ID", qs.maketID);
		maket.put("TRADE_DATE", fmdDate(qs.tradeDate));

		maket.put("FIRM_DATA_LIST", "");
		try {
			this.xml.setChildElements("CONTEXT", maket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void mkXml(RZQSValue qs, Date date) {
		if (date == null) {
			this.fileName = (this.fileName + "_" + fmdDate(qs.tradeDate) + ".xml");
		} else {
			this.fileName = (this.fileName + "_" + fmdDate(date) + ".xml");
		}
		this.xml = new AddXml(this.fileName, "GNNT", this.charset);
	}

	private String fmdDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyyMMdd");
		return dateFormate.format(date);
	}

	private String getDouble(double money) {
		String result = Tool.fmtDouble0(money * 100.0D);
		return result;
	}

	private String getDouble(BigDecimal money) {
		String result = Tool.fmtDouble0(money.multiply(new BigDecimal(100)));
		return result;
	}

	public static void main(String[] args) {
		RZQSValue qs = new RZQSValue();
		qs.bankID = "01";
		qs.tradeDate = new Date();
		for (int i = 0; i < 3; i++) {
			FirmRightValue fr = new FirmRightValue();
			fr.account = "22222222";
			fr.actionID = "sdf";
			fr.availableBalance = 10.0D;
			fr.billMoney = 12.0D;
			fr.cash = 13.0D;
			fr.cashMoney = 54.0D;
			fr.credit = 3.0D;
			fr.firmErrorMoney = 9.0D;
			fr.type = 1;
			fr.currency = "014";
			qs.putFrv(fr);
		}
		RZQSxml rzqs = new RZQSxml();
		rzqs.getRZQSxml(qs);
	}
}
