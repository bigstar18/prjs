package gnnt.trade.bank.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.w3c.dom.Element;

import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmDZValue;

public class RZDZxml {
	private String fileName = "rizhongduizhang";
	private String charset;
	private AddXml xml = null;

	public RZDZxml() {
	}

	public RZDZxml(String url, String charset) {
		this.charset = charset;
		this.fileName = (url + this.fileName);
	}

	public String getRZDZxml(RZDZValue dz) {
		return getRZDZxml(dz, null);
	}

	public String getRZDZxml(RZDZValue dz, Date date) {
		if (dz == null) {
			dz = new RZDZValue();
		}
		mkXml(dz, date);
		addMaketXml(dz);
		addFirmXml(dz);
		return this.fileName;
	}

	private void addMaketXml(RZDZValue dz) {
		Map<String, String> maket = new HashMap();
		maket.put("FILE_NAME", dz.fileName);
		maket.put("BANK_ID", dz.bankID);
		maket.put("MARKET_ID", dz.maketID);
		maket.put("TRADE_DATE", fmdDate(dz.tradeDate));
		maket.put("FIRM_DATA_LIST", "");
		try {
			this.xml.setChildElements("CONTEXT", maket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addFirmXml(RZDZValue dz) {
		Vector<FirmDZValue> vector = dz.getFdv();
		for (FirmDZValue fd : vector) {
			Map<String, String> map = new HashMap();
			Map<String, String> map2 = new HashMap();
			map.put("FIRM_ID", fd.firmID);
			map.put("FIRM_BANKACCOUNT", fd.account);
			map.put("CURRENCY", fd.currency);
			map.put("REMIT_TYPE", fd.type + "");
			map.put("FIRM_ASSET", getDouble(fd.firmRights));
			map.put("CASH", getDouble(fd.cashRights));
			map.put("BILL", getDouble(fd.billRights));
			map.put("AVAILABLE_BALANCE", getDouble(fd.availableBalance));
			map2.put("CASH", getDouble(fd.cash));
			map2.put("CREDIT", getDouble(fd.credit));
			try {
				Element element = this.xml.setChildElements("CONTEXT.FIRM_DATA_LIST", "RECORD", map);
				this.xml.setChildElements(element, "BETAKE_MARGIN", map2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void mkXml(RZDZValue dz, Date date) {
		if (date == null) {
			this.fileName = (this.fileName + "_" + fmdDate(dz.tradeDate) + ".xml");
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

	public static void main(String[] args) {
		RZDZValue dz = new RZDZValue();
		dz.bankID = "01";
		dz.fileName = "rizhongduizhang";
		dz.tradeDate = new Date();
		dz.maketID = " ";
		Vector<FirmDZValue> vv = new Vector();
		for (int i = 0; i < 2; i++) {
			FirmDZValue fd = new FirmDZValue();
			fd.account = "1111111111";
			fd.availableBalance = 100.0D;
			fd.billRights = 20.0D;
			fd.cash = 10.0D;
			fd.cashRights = 30.0D;
			fd.credit = 5.0D;
			fd.currency = "001";
			fd.firmID = ("000" + i);
			fd.firmRights = 20.0D;
			vv.add(fd);
		}
		dz.setFdv(vv);

		RZDZxml rd = new RZDZxml();
		rd.getRZDZxml(dz);
	}
}
