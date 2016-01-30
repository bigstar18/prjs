package gnnt.MEBS.timebargain.mgr.model.apply;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class Apply_T_CommodityFee extends Apply {
	private int type;

	@ClassDiscription(name = "商品代码", description = "")
	private String commodityID;

	@ClassDiscription(name = "手续费算法", description = "")
	private Short feeAlgr;

	@ClassDiscription(name = "订立买", description = "")
	private Double feeRate_B;

	@ClassDiscription(name = "", description = "")
	private Double feeRate_S;

	@ClassDiscription(name = "买转让历史订货", description = "")
	private Double historyCloseFeeRate_B;

	@ClassDiscription(name = "", description = "")
	private Double historyCloseFeeRate_S;

	@ClassDiscription(name = "买转让今订货", description = "")
	private Double todayCloseFeeRate_B;

	@ClassDiscription(name = "", description = "")
	private Double todayCloseFeeRate_S;

	@ClassDiscription(name = "代为转让(强平)买手续费", description = "")
	private Double forceCloseFeeRate_B;

	@ClassDiscription(name = "代为转让(强平)卖手续费", description = "")
	private Double forceCloseFeeRate_S;

	@ClassDiscription(name = "交收手续费算法", description = "")
	private Short settleFeeAlgr;

	@ClassDiscription(name = "交收买手续费", description = "")
	private Double settleFeeRate_B;

	@ClassDiscription(name = "交收卖手续费", description = "")
	private Double settleFeeRate_S;

	@ClassDiscription(name = "", description = "")
	private Double lowestSettleFee;

	public Double getLowestSettleFee() {
		return this.lowestSettleFee;
	}

	public void setLowestSettleFee(Double lowestSettleFee) {
		this.lowestSettleFee = lowestSettleFee;
	}

	public String getCommodityID() {
		return this.commodityID;
	}

	public void setCommodityID(String commodityID) {
		this.commodityID = commodityID;
	}

	public Short getFeeAlgr() {
		return this.feeAlgr;
	}

	public void setFeeAlgr(Short feeAlgr) {
		this.feeAlgr = feeAlgr;
	}

	public Double getFeeRate_B() {
		return this.feeRate_B;
	}

	public void setFeeRate_B(Double feeRate_B) {
		this.feeRate_B = feeRate_B;
	}

	public Double getFeeRate_S() {
		return this.feeRate_S;
	}

	public void setFeeRate_S(Double feeRate_S) {
		this.feeRate_S = feeRate_S;
	}

	public Double getForceCloseFeeRate_B() {
		return this.forceCloseFeeRate_B;
	}

	public void setForceCloseFeeRate_B(Double forceCloseFeeRate_B) {
		this.forceCloseFeeRate_B = forceCloseFeeRate_B;
	}

	public Double getForceCloseFeeRate_S() {
		return this.forceCloseFeeRate_S;
	}

	public void setForceCloseFeeRate_S(Double forceCloseFeeRate_S) {
		this.forceCloseFeeRate_S = forceCloseFeeRate_S;
	}

	public Double getHistoryCloseFeeRate_B() {
		return this.historyCloseFeeRate_B;
	}

	public void setHistoryCloseFeeRate_B(Double historyCloseFeeRate_B) {
		this.historyCloseFeeRate_B = historyCloseFeeRate_B;
	}

	public Double getHistoryCloseFeeRate_S() {
		return this.historyCloseFeeRate_S;
	}

	public void setHistoryCloseFeeRate_S(Double historyCloseFeeRate_S) {
		this.historyCloseFeeRate_S = historyCloseFeeRate_S;
	}

	public Short getSettleFeeAlgr() {
		return this.settleFeeAlgr;
	}

	public void setSettleFeeAlgr(Short settleFeeAlgr) {
		this.settleFeeAlgr = settleFeeAlgr;
	}

	public Double getSettleFeeRate_B() {
		return this.settleFeeRate_B;
	}

	public void setSettleFeeRate_B(Double settleFeeRate_B) {
		this.settleFeeRate_B = settleFeeRate_B;
	}

	public Double getSettleFeeRate_S() {
		return this.settleFeeRate_S;
	}

	public void setSettleFeeRate_S(Double settleFeeRate_S) {
		this.settleFeeRate_S = settleFeeRate_S;
	}

	public Double getTodayCloseFeeRate_B() {
		return this.todayCloseFeeRate_B;
	}

	public void setTodayCloseFeeRate_B(Double todayCloseFeeRate_B) {
		this.todayCloseFeeRate_B = todayCloseFeeRate_B;
	}

	public Double getTodayCloseFeeRate_S() {
		return this.todayCloseFeeRate_S;
	}

	public void setTodayCloseFeeRate_S(Double todayCloseFeeRate_S) {
		this.todayCloseFeeRate_S = todayCloseFeeRate_S;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setContent(String content) {
		this.content = content;
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(content);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		Element commodityID = doc.getRootElement().element("commodityID");
		setCommodityID(commodityID.getText());
		Element type = doc.getRootElement().element("type");
		setType(Integer.parseInt(type.getText()));
		Element feeAlgr = doc.getRootElement().element("feeAlgr");
		setFeeAlgr(Short.valueOf(Short.parseShort(feeAlgr.getText())));
		Element feeRate_B = doc.getRootElement().element("feeRate_B");
		setFeeRate_B(Double.valueOf(Double.parseDouble(feeRate_B.getText())));
		Element feeRate_S = doc.getRootElement().element("feeRate_S");
		setFeeRate_S(Double.valueOf(Double.parseDouble(feeRate_S.getText())));
		Element historyCloseFeeRate_B = doc.getRootElement().element("historyCloseFeeRate_B");
		setHistoryCloseFeeRate_B(Double.valueOf(Double.parseDouble(historyCloseFeeRate_B.getText())));
		Element historyCloseFeeRate_S = doc.getRootElement().element("historyCloseFeeRate_S");
		setHistoryCloseFeeRate_S(Double.valueOf(Double.parseDouble(historyCloseFeeRate_S.getText())));

		Element todayCloseFeeRate_B = doc.getRootElement().element("todayCloseFeeRate_B");
		setTodayCloseFeeRate_B(Double.valueOf(Double.parseDouble(todayCloseFeeRate_B.getText())));
		Element todayCloseFeeRate_S = doc.getRootElement().element("todayCloseFeeRate_S");
		setTodayCloseFeeRate_S(Double.valueOf(Double.parseDouble(todayCloseFeeRate_S.getText())));

		Element forceCloseFeeRate_B = doc.getRootElement().element("forceCloseFeeRate_B");
		setForceCloseFeeRate_B(Double.valueOf(Double.parseDouble(forceCloseFeeRate_B.getText())));
		Element forceCloseFeeRate_S = doc.getRootElement().element("forceCloseFeeRate_S");
		setForceCloseFeeRate_S(Double.valueOf(Double.parseDouble(forceCloseFeeRate_S.getText())));

		Element settleFeeAlgr = doc.getRootElement().element("settleFeeAlgr");
		setSettleFeeAlgr(Short.valueOf(Short.parseShort(settleFeeAlgr.getText())));
		Element settleFeeRate_B = doc.getRootElement().element("settleFeeRate_B");
		setSettleFeeRate_B(Double.valueOf(Double.parseDouble(settleFeeRate_B.getText())));
		Element settleFeeRate_S = doc.getRootElement().element("settleFeeRate_S");
		setSettleFeeRate_S(Double.valueOf(Double.parseDouble(settleFeeRate_S.getText())));

		Element lowestSettleFee = doc.getRootElement().element("lowestSettleFee");
		setLowestSettleFee(Double.valueOf(Double.parseDouble(lowestSettleFee.getText())));
	}

	public void generationContent() {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("gb2312");
		Element booksElement = document.addElement("root");
		Element commodityIDElement = booksElement.addElement("commodityID");
		commodityIDElement.setText(getCommodityID());
		Element typeElement = booksElement.addElement("type");
		typeElement.setText((new StringBuilder(String.valueOf(getType()))).toString());
		Element feeAlgrElement = booksElement.addElement("feeAlgr");
		feeAlgrElement
				.setText((new StringBuilder()).append(getFeeAlgr()).toString() == null ? "0" : (new StringBuilder()).append(getFeeAlgr()).toString());
		Element feeRate_BElement = booksElement.addElement("feeRate_B");
		feeRate_BElement.setText(
				(new StringBuilder()).append(getFeeRate_B()).toString() == "0" ? "0" : (new StringBuilder()).append(getFeeRate_B()).toString());
		Element feeRate_SElement = booksElement.addElement("feeRate_S");
		feeRate_SElement.setText(
				(new StringBuilder()).append(getFeeRate_S()).toString() == "0" ? "0" : (new StringBuilder()).append(getFeeRate_S()).toString());
		Element historyCloseFeeRate_BElement = booksElement.addElement("historyCloseFeeRate_B");
		historyCloseFeeRate_BElement.setText((new StringBuilder()).append(getHistoryCloseFeeRate_B()).toString() == null ? "0"
				: (new StringBuilder()).append(getHistoryCloseFeeRate_B()).toString());
		Element historyCloseFeeRate_SElement = booksElement.addElement("historyCloseFeeRate_S");
		historyCloseFeeRate_SElement.setText((new StringBuilder()).append(getHistoryCloseFeeRate_S()).toString() == null ? "0"
				: (new StringBuilder()).append(getHistoryCloseFeeRate_S()).toString());
		Element todayCloseFeeRate_BElement = booksElement.addElement("todayCloseFeeRate_B");
		todayCloseFeeRate_BElement.setText((new StringBuilder()).append(getTodayCloseFeeRate_B()).toString() == "0" ? "0"
				: (new StringBuilder()).append(getTodayCloseFeeRate_B()).toString());
		Element todayCloseFeeRate_SElement = booksElement.addElement("todayCloseFeeRate_S");
		todayCloseFeeRate_SElement.setText((new StringBuilder()).append(getTodayCloseFeeRate_S()).toString() == "0" ? "0"
				: (new StringBuilder()).append(getTodayCloseFeeRate_S()).toString());
		Element forceCloseFeeRate_BElement = booksElement.addElement("forceCloseFeeRate_B");
		forceCloseFeeRate_BElement.setText((new StringBuilder()).append(getForceCloseFeeRate_B()).toString() == "0" ? "0"
				: (new StringBuilder()).append(getForceCloseFeeRate_B()).toString());
		Element forceCloseFeeRate_SElement = booksElement.addElement("forceCloseFeeRate_S");
		forceCloseFeeRate_SElement.setText((new StringBuilder()).append(getForceCloseFeeRate_S()).toString() == "0" ? "0"
				: (new StringBuilder()).append(getForceCloseFeeRate_S()).toString());
		Element settleFeeAlgrElement = booksElement.addElement("settleFeeAlgr");
		settleFeeAlgrElement.setText((new StringBuilder()).append(getSettleFeeAlgr()).toString() == null ? "0"
				: (new StringBuilder()).append(getSettleFeeAlgr()).toString());
		Element settleFeeRate_BElement = booksElement.addElement("settleFeeRate_B");
		settleFeeRate_BElement.setText((new StringBuilder()).append(getSettleFeeRate_B()).toString() == "0" ? "0"
				: (new StringBuilder()).append(getSettleFeeRate_B()).toString());
		Element settleFeeRate_SElement = booksElement.addElement("settleFeeRate_S");
		settleFeeRate_SElement.setText((new StringBuilder()).append(getSettleFeeRate_S()).toString() == "0" ? "0"
				: (new StringBuilder()).append(getSettleFeeRate_S()).toString());
		Element lowestSettleFeeElement = booksElement.addElement("lowestSettleFee");
		lowestSettleFeeElement.setText((new StringBuilder()).append(getLowestSettleFee()).toString());
		String con = document.asXML();
		content = con;
	}

	public String toQury() {
		return "select id, applytype, status, proposer, applytime, approver, approvetime,t.content.extract('/root/commodityID/text()').getStringVal() commodityID,t.content.extract('/root/type/text()').getStringVal() type,t.content.extract('/root/feeAlgr/text()').getStringVal() feeAlgr,t.content.extract('/root/feeRate_B/text()').getStringVal() feeRate_B,t.content.extract('/root/feeRate_S/text()').getStringVal() feeRate_S,t.content.extract('/root/historyCloseFeeRate_B/text()').getStringVal() historyCloseFeeRate_B,t.content.extract('/root/historyCloseFeeRate_S/text()').getStringVal() historyCloseFeeRate_S,t.content.extract('/root/todayCloseFeeRate_B/text()').getStringVal() todayCloseFeeRate_B,t.content.extract('/root/todayCloseFeeRate_S/text()').getStringVal() todayCloseFeeRate_S,t.content.extract('/root/forceCloseFeeRate_B/text()').getStringVal() forceCloseFeeRate_B,t.content.extract('/root/forceCloseFeeRate_S/text()').getStringVal() forceCloseFeeRate_S,t.content.extract('/root/settleFeeAlgr/text()').getStringVal() settleFeeAlgr,t.content.extract('/root/settleFeeRate_B/text()').getStringVal() settleFeeRate_B,t.content.extract('/root/settleFeeRate_S/text()').getStringVal() settleFeeRate_S,t.content.extract('/root/lowestSettleFee/text()').getStringVal() lowestSettleFee, note from t_apply t";
	}

	public String toFilter() {
		String filter = "";
		if (this.id != 0L) {
			filter = filter + " and id=" + this.id;
		}
		if (this.applyType != 0) {
			filter = filter + " and applyType=" + this.applyType;
		}
		if (this.status != 0) {
			filter = filter + " and status=" + this.status;
		}
		if ((this.proposer != null) && (!"".equals(this.proposer))) {
			filter = filter + " and proposer='" + this.proposer + "'";
		}
		if ((this.approver != null) && (!"".equals(this.approver))) {
			filter = filter + " and approver='" + this.approver + "'";
		}
		if ((this.note != null) && (!"".equals(this.note))) {
			filter = filter + " and note='" + this.note + "'";
		}
		if ((this.commodityID != null) && (!"".equals(this.commodityID))) {
			filter = filter + " and t.content.extract('/root/commodityID/text()').getStringVal()='" + this.commodityID + "'";
		}
		if (this.feeAlgr != null) {
			filter = filter + " and t.content.extract('/root/feeAlgr/text()').getStringVal()=" + this.feeAlgr;
		}
		if (this.feeRate_B != null) {
			filter = filter + " and t.content.extract('/root/feeRate_B/text()').getStringVal()=" + this.feeRate_B;
		}
		if (this.type != 0) {
			filter = filter + " and to_number(t.content.extract('/root/type/text()').getStringVal())=" + this.type;
		}
		if (this.feeRate_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/feeRate_S/text()').getStringVal())=" + this.feeRate_S;
		}
		if (this.historyCloseFeeRate_B != null) {
			filter = filter + " and to_number(t.content.extract('/root/historyCloseFeeRate_B/text()').getStringVal())=" + this.historyCloseFeeRate_B;
		}
		if (this.historyCloseFeeRate_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/historyCloseFeeRate_S/text()').getStringVal())=" + this.historyCloseFeeRate_S;
		}

		if (this.todayCloseFeeRate_B != null) {
			filter = filter + " and to_number(t.content.extract('/root/todayCloseFeeRate_B/text()').getStringVal())=" + this.todayCloseFeeRate_B;
		}
		if (this.todayCloseFeeRate_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/todayCloseFeeRate_S/text()').getStringVal())=" + this.todayCloseFeeRate_S;
		}

		if (this.forceCloseFeeRate_B != null) {
			filter = filter + " and to_number(t.content.extract('/root/forceCloseFeeRate_B/text()').getStringVal())=" + this.forceCloseFeeRate_B;
		}
		if (this.forceCloseFeeRate_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/forceCloseFeeRate_S/text()').getStringVal())=" + this.forceCloseFeeRate_S;
		}

		if (this.settleFeeAlgr != null) {
			filter = filter + " and to_number(t.content.extract('/root/settleFeeAlgr/text()').getStringVal())=" + this.settleFeeAlgr;
		}
		if (this.settleFeeRate_B != null) {
			filter = filter + " and to_number(t.content.extract('/root/settleFeeRate_B/text()').getStringVal())=" + this.settleFeeRate_B;
		}
		if (this.settleFeeRate_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/settleFeeRate_S/text()').getStringVal())=" + this.settleFeeRate_S;
		}
		return filter;
	}
}