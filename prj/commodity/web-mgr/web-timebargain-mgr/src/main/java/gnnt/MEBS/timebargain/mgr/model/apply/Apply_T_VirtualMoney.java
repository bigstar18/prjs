package gnnt.MEBS.timebargain.mgr.model.apply;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Apply_T_VirtualMoney extends Apply {
	private String firmId;
	private double money;

	public String getFirmId() {
		return this.firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

	public double getMoney() {
		return this.money;
	}

	public void setMoney(double money) {
		this.money = money;
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
		Element firmId = doc.getRootElement().element("firmId");
		setFirmId(firmId.getText());
		Element money = doc.getRootElement().element("money");
		setMoney(Double.parseDouble(money.getText()));
	}

	public void generationContent() {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("gb2312");
		Element booksElement = document.addElement("root");
		Element firmIdElement = booksElement.addElement("firmId");
		firmIdElement.setText(getFirmId());
		Element moneyElement = booksElement.addElement("money");
		moneyElement.setText(getMoney() + "");
		String con = document.asXML();
		this.content = con;
	}

	public String toQury() {
		return "select id, applytype, status, proposer, applytime, approver, approvetime,t.content.extract('/root/firmId/text()').getStringVal() firmId,t.content.extract('/root/money/text()').getStringVal() money, note from t_apply t";
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
		if (this.money != 0.0D) {
			filter = filter + " and to_number(t.content.extract('/root/money/text()').getStringVal())=" + this.money;
		}
		return filter;
	}
}