package gnnt.MEBS.timebargain.mgr.model.apply;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Apply_T_PledgeMoney extends Apply {
	private String firmId;
	private String billID;
	private double billFund;
	private String commodityName;
	private double quantity;
	private int type;

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getBillFund() {
		return this.billFund;
	}

	public void setBillFund(double billFund) {
		this.billFund = billFund;
	}

	public String getBillID() {
		return this.billID;
	}

	public void setBillID(String billID) {
		this.billID = billID;
	}

	public String getCommodityName() {
		return this.commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getFirmId() {
		return this.firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

	public double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
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
		Element billID = doc.getRootElement().element("billID");
		setBillID(billID.getText());
		Element type = doc.getRootElement().element("type");
		setType(Integer.parseInt(type.getText()));
		Element commodityName = doc.getRootElement().element("commodityName");

		setCommodityName(commodityName.getText());
		Element quantity = doc.getRootElement().element("quantity");
		setQuantity(Double.parseDouble(quantity.getText()));
		Element firmId = doc.getRootElement().element("firmId");
		setFirmId(firmId.getText());
		Element billFund = doc.getRootElement().element("billFund");
		setBillFund(Double.parseDouble(billFund.getText()));
	}

	public void generationContent() {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("gb2312");
		Element booksElement = document.addElement("root");
		Element billIDElement = booksElement.addElement("billID");
		billIDElement.setText(getBillID());
		Element typeElement = booksElement.addElement("type");
		typeElement.setText((new StringBuilder(String.valueOf(getType()))).toString());
		Element commodityNameElement = booksElement.addElement("commodityName");
		commodityNameElement.setText(getCommodityName() == null ? "" : getCommodityName());
		Element quantityElement = booksElement.addElement("quantity");
		quantityElement.setText(getQuantity() == 0.0D ? "0" : (new StringBuilder(String.valueOf(getQuantity()))).toString());
		Element firmIdElement = booksElement.addElement("firmId");
		firmIdElement.setText(getFirmId() == null ? "" : getFirmId());
		Element billFundElement = booksElement.addElement("billFund");
		billFundElement.setText(getBillFund() == 0.0D ? "0" : (new StringBuilder(String.valueOf(getBillFund()))).toString());
		String con = document.asXML();
		content = con;
	}

	public String toQury() {
		return "select id, applytype, status, proposer, applytime, approver, approvetime,t.content.extract('/root/billID/text()').getStringVal() billID,t.content.extract('/root/type/text()').getStringVal() type,t.content.extract('/root/commodityName/text()').getStringVal() commodityName,(case when to_number(t.content.extract('/root/quantity/text()').getStringVal())=0 then '' else t.content.extract('/root/quantity/text()').getStringVal() end) quantity,t.content.extract('/root/firmId/text()').getStringVal() firmId,(case when to_number(t.content.extract('/root/billFund/text()').getStringVal())=0 then '' else t.content.extract('/root/billFund/text()').getStringVal() end)  billFund, note from t_apply t";
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
		if ((this.firmId != null) && (!"".equals(this.firmId))) {
			filter = filter + " and t.content.extract('/root/firmId/text()').getStringVal()='" + this.firmId + "'";
		}
		if ((this.billID != null) && (!"".equals(this.billID))) {
			filter = filter + " and t.content.extract('/root/billID/text()').getStringVal()='" + this.billID + "'";
		}
		if ((this.commodityName != null) && (!"".equals(this.commodityName))) {
			filter = filter + " and t.content.extract('/root/commodityName/text()').getStringVal()='" + this.commodityName + "'";
		}
		if (this.type != 0) {
			filter = filter + " and to_number(t.content.extract('/root/type/text()').getStringVal())=" + this.type;
		}
		if (this.quantity != 0.0D) {
			filter = filter + " and to_number(t.content.extract('/root/quantity/text()').getStringVal())=" + this.quantity;
		}
		if (this.billFund != 0.0D) {
			filter = filter + " and to_number(t.content.extract('/root/billFund/text()').getStringVal())=" + this.billFund;
		}
		return filter;
	}
}