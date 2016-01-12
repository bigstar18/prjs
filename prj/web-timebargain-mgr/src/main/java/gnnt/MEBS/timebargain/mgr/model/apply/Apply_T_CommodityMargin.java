package gnnt.MEBS.timebargain.mgr.model.apply;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Apply_T_CommodityMargin extends Apply {
	private String commodityID;
	private Short marginAlgr;
	private Short marginPriceType;
	private String settleDate1;
	private String settleDate2;
	private String settleDate3;
	private String settleDate4;
	private String settleDate5;
	private String marketDate;
	private Double marginItem1;
	private Double marginItem2;
	private Double marginItem3;
	private Double marginItem4;
	private Double marginItem5;
	private Double marginItem1_S;
	private Double marginItem2_S;
	private Double marginItem3_S;
	private Double marginItem4_S;
	private Double marginItem5_S;
	private Double marginItemAssure1;
	private Double marginItemAssure2;
	private Double marginItemAssure3;
	private Double marginItemAssure4;
	private Double marginItemAssure5;
	private Double marginItemAssure1_S;
	private Double marginItemAssure2_S;
	private Double marginItemAssure3_S;
	private Double marginItemAssure4_S;
	private Double marginItemAssure5_S;
	private Double marginRate_B;
	private Double marginRate_S;
	private String settleDate;
	private Short settleMarginAlgr_B;
	private Short settleMarginAlgr_S;
	private Double settleMarginRate_B;
	private Double settleMarginRate_S;
	private Short payoutAlgr;
	private Double payoutRate;

	public String getCommodityID() {
		return this.commodityID;
	}

	public void setCommodityID(String commodityID) {
		this.commodityID = commodityID;
	}

	public Short getMarginAlgr() {
		return this.marginAlgr;
	}

	public void setMarginAlgr(Short marginAlgr) {
		this.marginAlgr = marginAlgr;
	}

	public Double getMarginItem1() {
		return this.marginItem1;
	}

	public void setMarginItem1(Double marginItem1) {
		this.marginItem1 = marginItem1;
	}

	public Double getMarginItem1_S() {
		return this.marginItem1_S;
	}

	public void setMarginItem1_S(Double marginItem1_S) {
		this.marginItem1_S = marginItem1_S;
	}

	public Double getMarginItem2() {
		return this.marginItem2;
	}

	public void setMarginItem2(Double marginItem2) {
		this.marginItem2 = marginItem2;
	}

	public Double getMarginItem2_S() {
		return this.marginItem2_S;
	}

	public void setMarginItem2_S(Double marginItem2_S) {
		this.marginItem2_S = marginItem2_S;
	}

	public Double getMarginItem3() {
		return this.marginItem3;
	}

	public void setMarginItem3(Double marginItem3) {
		this.marginItem3 = marginItem3;
	}

	public Double getMarginItem3_S() {
		return this.marginItem3_S;
	}

	public void setMarginItem3_S(Double marginItem3_S) {
		this.marginItem3_S = marginItem3_S;
	}

	public Double getMarginItem4() {
		return this.marginItem4;
	}

	public void setMarginItem4(Double marginItem4) {
		this.marginItem4 = marginItem4;
	}

	public Double getMarginItem4_S() {
		return this.marginItem4_S;
	}

	public void setMarginItem4_S(Double marginItem4_S) {
		this.marginItem4_S = marginItem4_S;
	}

	public Double getMarginItem5() {
		return this.marginItem5;
	}

	public void setMarginItem5(Double marginItem5) {
		this.marginItem5 = marginItem5;
	}

	public Double getMarginItem5_S() {
		return this.marginItem5_S;
	}

	public void setMarginItem5_S(Double marginItem5_S) {
		this.marginItem5_S = marginItem5_S;
	}

	public Double getMarginItemAssure1() {
		return this.marginItemAssure1;
	}

	public void setMarginItemAssure1(Double marginItemAssure1) {
		this.marginItemAssure1 = marginItemAssure1;
	}

	public Double getMarginItemAssure1_S() {
		return this.marginItemAssure1_S;
	}

	public void setMarginItemAssure1_S(Double marginItemAssure1_S) {
		this.marginItemAssure1_S = marginItemAssure1_S;
	}

	public Double getMarginItemAssure2() {
		return this.marginItemAssure2;
	}

	public void setMarginItemAssure2(Double marginItemAssure2) {
		this.marginItemAssure2 = marginItemAssure2;
	}

	public Double getMarginItemAssure2_S() {
		return this.marginItemAssure2_S;
	}

	public void setMarginItemAssure2_S(Double marginItemAssure2_S) {
		this.marginItemAssure2_S = marginItemAssure2_S;
	}

	public Double getMarginItemAssure3() {
		return this.marginItemAssure3;
	}

	public void setMarginItemAssure3(Double marginItemAssure3) {
		this.marginItemAssure3 = marginItemAssure3;
	}

	public Double getMarginItemAssure3_S() {
		return this.marginItemAssure3_S;
	}

	public void setMarginItemAssure3_S(Double marginItemAssure3_S) {
		this.marginItemAssure3_S = marginItemAssure3_S;
	}

	public Double getMarginItemAssure4() {
		return this.marginItemAssure4;
	}

	public void setMarginItemAssure4(Double marginItemAssure4) {
		this.marginItemAssure4 = marginItemAssure4;
	}

	public Double getMarginItemAssure4_S() {
		return this.marginItemAssure4_S;
	}

	public void setMarginItemAssure4_S(Double marginItemAssure4_S) {
		this.marginItemAssure4_S = marginItemAssure4_S;
	}

	public Double getMarginItemAssure5() {
		return this.marginItemAssure5;
	}

	public void setMarginItemAssure5(Double marginItemAssure5) {
		this.marginItemAssure5 = marginItemAssure5;
	}

	public Double getMarginItemAssure5_S() {
		return this.marginItemAssure5_S;
	}

	public void setMarginItemAssure5_S(Double marginItemAssure5_S) {
		this.marginItemAssure5_S = marginItemAssure5_S;
	}

	public Short getMarginPriceType() {
		return this.marginPriceType;
	}

	public void setMarginPriceType(Short marginPriceType) {
		this.marginPriceType = marginPriceType;
	}

	public Double getMarginRate_B() {
		return this.marginRate_B;
	}

	public void setMarginRate_B(Double marginRate_B) {
		this.marginRate_B = marginRate_B;
	}

	public Double getMarginRate_S() {
		return this.marginRate_S;
	}

	public void setMarginRate_S(Double marginRate_S) {
		this.marginRate_S = marginRate_S;
	}

	public Short getPayoutAlgr() {
		return this.payoutAlgr;
	}

	public void setPayoutAlgr(Short payoutAlgr) {
		this.payoutAlgr = payoutAlgr;
	}

	public Double getPayoutRate() {
		return this.payoutRate;
	}

	public void setPayoutRate(Double payoutRate) {
		this.payoutRate = payoutRate;
	}

	public String getSettleDate() {
		return this.settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getSettleDate1() {
		return this.settleDate1;
	}

	public void setSettleDate1(String settleDate1) {
		this.settleDate1 = settleDate1;
	}

	public String getSettleDate2() {
		return this.settleDate2;
	}

	public void setSettleDate2(String settleDate2) {
		this.settleDate2 = settleDate2;
	}

	public String getSettleDate3() {
		return this.settleDate3;
	}

	public void setSettleDate3(String settleDate3) {
		this.settleDate3 = settleDate3;
	}

	public String getSettleDate4() {
		return this.settleDate4;
	}

	public void setSettleDate4(String settleDate4) {
		this.settleDate4 = settleDate4;
	}

	public String getSettleDate5() {
		return this.settleDate5;
	}

	public void setSettleDate5(String settleDate5) {
		this.settleDate5 = settleDate5;
	}

	public String getMarketDate() {
		return this.marketDate;
	}

	public void setMarketDate(String marketDate) {
		this.marketDate = marketDate;
	}

	public Short getSettleMarginAlgr_B() {
		return this.settleMarginAlgr_B;
	}

	public void setSettleMarginAlgr_B(Short settleMarginAlgr_B) {
		this.settleMarginAlgr_B = settleMarginAlgr_B;
	}

	public Short getSettleMarginAlgr_S() {
		return this.settleMarginAlgr_S;
	}

	public void setSettleMarginAlgr_S(Short settleMarginAlgr_S) {
		this.settleMarginAlgr_S = settleMarginAlgr_S;
	}

	public Double getSettleMarginRate_B() {
		return this.settleMarginRate_B;
	}

	public void setSettleMarginRate_B(Double settleMarginRate_B) {
		this.settleMarginRate_B = settleMarginRate_B;
	}

	public Double getSettleMarginRate_S() {
		return this.settleMarginRate_S;
	}

	public void setSettleMarginRate_S(Double settleMarginRate_S) {
		this.settleMarginRate_S = settleMarginRate_S;
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
		Element marginAlgr = doc.getRootElement().element("marginAlgr");
		setMarginAlgr(Short.valueOf(Short.parseShort(marginAlgr.getText())));
		Element marginPriceType = doc.getRootElement().element("marginPriceType");
		if ((marginPriceType != null) && (!"".equals(marginPriceType))) {
			setMarginPriceType(Short.valueOf(Short.parseShort(marginPriceType.getText())));
		}

		Element settleDate1 = doc.getRootElement().element("settleDate1");

		setSettleDate1(settleDate1.getText() == null ? "" : settleDate1.getText());
		Element settleDate2 = doc.getRootElement().element("settleDate2");
		setSettleDate2(settleDate2.getText() == null ? "" : settleDate2.getText());

		Element settleDate3 = doc.getRootElement().element("settleDate3");
		setSettleDate3(settleDate3.getText() == null ? "" : settleDate3.getText());

		Element settleDate4 = doc.getRootElement().element("settleDate4");
		setSettleDate4(settleDate4.getText() == null ? "" : settleDate4.getText());

		Element settleDate5 = doc.getRootElement().element("settleDate5");
		setSettleDate5(settleDate5.getText() == null ? "" : settleDate5.getText());

		Element settleDate = doc.getRootElement().element("settleDate");
		setSettleDate(settleDate.getText() == null ? "" : settleDate.getText());

		Element marginItem1 = doc.getRootElement().element("marginItem1");
		setMarginItem1(Double.valueOf(Double.parseDouble(marginItem1.getText())));

		Element marginItem2 = doc.getRootElement().element("marginItem2");
		if ((marginItem2 != null) && (marginItem2.getText() != null) && (!"".equals(marginItem2.getText()))) {
			setMarginItem2(Double.valueOf(Double.parseDouble(marginItem2.getText())));
		}

		Element marginItem3 = doc.getRootElement().element("marginItem3");
		if ((marginItem3.getText() != null) && (!"".equals(marginItem3.getText()))) {
			setMarginItem3(Double.valueOf(Double.parseDouble(marginItem3.getText())));
		}
		Element marginItem4 = doc.getRootElement().element("marginItem4");
		if ((marginItem4.getText() != null) && (!"".equals(marginItem4.getText()))) {
			setMarginItem4(Double.valueOf(Double.parseDouble(marginItem4.getText())));
		}
		Element marginItem5 = doc.getRootElement().element("marginItem5");
		if ((marginItem5.getText() != null) && (!"".equals(marginItem5.getText()))) {
			setMarginItem5(Double.valueOf(Double.parseDouble(marginItem5.getText())));
		}

		Element marginItem1_S = doc.getRootElement().element("marginItem1_S");
		if ((marginItem1_S.getText() != null) && (!"".equals(marginItem1_S.getText()))) {
			setMarginItem1_S(Double.valueOf(Double.parseDouble(marginItem1_S.getText())));
		}

		Element marginItem2_S = doc.getRootElement().element("marginItem2_S");
		if ((marginItem2_S.getText() != null) && (!"".equals(marginItem2_S.getText()))) {
			setMarginItem2_S(Double.valueOf(Double.parseDouble(marginItem2_S.getText())));
		}
		Element marginItem3_S = doc.getRootElement().element("marginItem3_S");
		if ((marginItem3_S.getText() != null) && (!"".equals(marginItem3_S.getText()))) {
			setMarginItem3_S(Double.valueOf(Double.parseDouble(marginItem3_S.getText())));
		}
		Element marginItem4_S = doc.getRootElement().element("marginItem4_S");
		if ((marginItem4_S.getText() != null) && (!"".equals(marginItem4_S.getText()))) {
			setMarginItem4_S(Double.valueOf(Double.parseDouble(marginItem4_S.getText())));
		}
		Element marginItem5_S = doc.getRootElement().element("marginItem5_S");
		if ((marginItem5_S.getText() != null) && (!"".equals(marginItem5_S.getText()))) {
			setMarginItem5_S(Double.valueOf(Double.parseDouble(marginItem5_S.getText())));
		}

		Element marginItemAssure1 = doc.getRootElement().element("marginItemAssure1");
		if ((marginItemAssure1.getText() != null) && (!"".equals(marginItemAssure1.getText()))) {
			setMarginItemAssure1(Double.valueOf(Double.parseDouble(marginItemAssure1.getText())));
		}

		Element marginItemAssure2 = doc.getRootElement().element("marginItemAssure2");
		if ((marginItemAssure2.getText() != null) && (!"".equals(marginItemAssure2.getText()))) {
			setMarginItemAssure2(Double.valueOf(Double.parseDouble(marginItemAssure2.getText())));
		}

		Element marginItemAssure3 = doc.getRootElement().element("marginItemAssure3");
		if ((marginItemAssure3.getText() != null) && (!"".equals(marginItemAssure3.getText()))) {
			setMarginItemAssure3(Double.valueOf(Double.parseDouble(marginItemAssure3.getText())));
		}
		Element marginItemAssure4 = doc.getRootElement().element("marginItemAssure4");
		if ((marginItemAssure4.getText() != null) && (!"".equals(marginItemAssure4.getText()))) {
			setMarginItemAssure4(Double.valueOf(Double.parseDouble(marginItemAssure4.getText())));
		}
		Element marginItemAssure5 = doc.getRootElement().element("marginItemAssure5");
		if ((marginItemAssure5.getText() != null) && (!"".equals(marginItemAssure5.getText()))) {
			setMarginItemAssure5(Double.valueOf(Double.parseDouble(marginItemAssure5.getText())));
		}

		Element marginItemAssure1_S = doc.getRootElement().element("marginItemAssure1_S");
		if ((marginItemAssure1_S.getText() != null) && (!"".equals(marginItemAssure1_S.getText()))) {
			setMarginItemAssure1_S(Double.valueOf(Double.parseDouble(marginItemAssure1_S.getText())));
		}

		Element marginItemAssure2_S = doc.getRootElement().element("marginItemAssure2_S");
		if ((marginItemAssure2_S.getText() != null) && (!"".equals(marginItemAssure2_S.getText()))) {
			setMarginItemAssure2_S(Double.valueOf(Double.parseDouble(marginItemAssure2_S.getText())));
		}
		Element marginItemAssure3_S = doc.getRootElement().element("marginItemAssure3_S");
		if ((marginItemAssure3_S.getText() != null) && (!"".equals(marginItemAssure3_S.getText()))) {
			setMarginItemAssure3_S(Double.valueOf(Double.parseDouble(marginItemAssure3_S.getText())));
		}
		Element marginItemAssure4_S = doc.getRootElement().element("marginItemAssure4_S");
		if ((marginItemAssure4_S.getText() != null) && (!"".equals(marginItemAssure4_S.getText()))) {
			setMarginItemAssure4_S(Double.valueOf(Double.parseDouble(marginItemAssure4_S.getText())));
		}
		Element marginItemAssure5_S = doc.getRootElement().element("marginItemAssure5_S");
		if ((marginItemAssure5_S.getText() != null) && (!"".equals(marginItemAssure5_S.getText()))) {
			setMarginItemAssure5_S(Double.valueOf(Double.parseDouble(marginItemAssure5_S.getText())));
		}

		Element marginRate_B = doc.getRootElement().element("marginRate_B");
		if ((marginRate_B != null) && (marginRate_B.getText() != null) && (!"".equals(marginRate_B.getText()))) {
			setMarginRate_B(Double.valueOf(Double.parseDouble(marginRate_B.getText())));
		}

		Element marginRate_S = doc.getRootElement().element("marginRate_S");
		if ((marginRate_S.getText() != null) && (!"".equals(marginRate_S.getText()))) {
			setMarginRate_S(Double.valueOf(Double.parseDouble(marginRate_S.getText())));
		}

		Element settleMarginAlgr_B = doc.getRootElement().element("settleMarginAlgr_B");
		setSettleMarginAlgr_B(Short.valueOf(Short.parseShort(settleMarginAlgr_B.getText())));
		Element settleMarginAlgr_S = doc.getRootElement().element("settleMarginAlgr_S");
		setSettleMarginAlgr_S(Short.valueOf(Short.parseShort(settleMarginAlgr_S.getText())));

		Element settleMarginRate_B = doc.getRootElement().element("settleMarginRate_B");
		setSettleMarginRate_B(Double.valueOf(Double.parseDouble(settleMarginRate_B.getText())));
		Element settleMarginRate_S = doc.getRootElement().element("settleMarginRate_S");
		setSettleMarginRate_S(Double.valueOf(Double.parseDouble(settleMarginRate_S.getText())));

		Element payoutAlgr = doc.getRootElement().element("payoutAlgr");
		setPayoutAlgr(Short.valueOf(Short.parseShort(payoutAlgr.getText())));

		Element payoutRate = doc.getRootElement().element("payoutRate");
		setPayoutRate(Double.valueOf(Double.parseDouble(payoutRate.getText())));
	}

	public void generationContent() {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("gb2312");
		Element booksElement = document.addElement("root");
		Element commodityIDElement = booksElement.addElement("commodityID");
		commodityIDElement.setText(getCommodityID());
		Element marginAlgrElement = booksElement.addElement("marginAlgr");
		marginAlgrElement.setText(getMarginAlgr() != null ? (new StringBuilder()).append(getMarginAlgr()).toString() : "");
		Element marginPriceTypeElement = booksElement.addElement("marginPriceType");
		marginPriceTypeElement.setText(getMarginPriceType() != null ? (new StringBuilder()).append(getMarginPriceType()).toString() : "");
		Element settleDate1Element = booksElement.addElement("settleDate1");
		settleDate1Element.setText(getSettleDate1() != null ? (new StringBuilder(String.valueOf(getSettleDate1()))).toString() : "");
		Element settleDate2Element = booksElement.addElement("settleDate2");
		settleDate2Element.setText(getSettleDate2() != null ? (new StringBuilder(String.valueOf(getSettleDate2()))).toString() : "");
		Element settleDate3Element = booksElement.addElement("settleDate3");
		settleDate3Element.setText(getSettleDate3() != null ? (new StringBuilder(String.valueOf(getSettleDate3()))).toString() : "");
		Element settleDate4Element = booksElement.addElement("settleDate4");
		settleDate4Element.setText(getSettleDate4() != null ? (new StringBuilder(String.valueOf(getSettleDate4()))).toString() : "");
		Element settleDate5Element = booksElement.addElement("settleDate5");
		settleDate5Element.setText(getSettleDate5() != null ? (new StringBuilder(String.valueOf(getSettleDate5()))).toString() : "");
		Element settleDateElement = booksElement.addElement("settleDate");
		settleDateElement.setText(getSettleDate() != null ? (new StringBuilder(String.valueOf(getSettleDate()))).toString() : "");
		Element marketDateElement = booksElement.addElement("marketDate");
		marketDateElement.setText(getMarketDate() != null ? (new StringBuilder(String.valueOf(getMarketDate()))).toString() : "");
		Element marginItem1Element = booksElement.addElement("marginItem1");
		marginItem1Element.setText(getMarginItem1() != null ? (new StringBuilder()).append(getMarginItem1()).toString() : "");
		Element marginItem2Element = booksElement.addElement("marginItem2");
		marginItem2Element.setText(getMarginItem2() != null ? (new StringBuilder()).append(getMarginItem2()).toString() : "");
		Element marginItem3Element = booksElement.addElement("marginItem3");
		marginItem3Element.setText(getMarginItem3() != null ? (new StringBuilder()).append(getMarginItem3()).toString() : "");
		Element marginItem4Element = booksElement.addElement("marginItem4");
		marginItem4Element.setText(getMarginItem4() != null ? (new StringBuilder()).append(getMarginItem4()).toString() : "");
		Element marginItem5Element = booksElement.addElement("marginItem5");
		marginItem5Element.setText(getMarginItem5() != null ? (new StringBuilder()).append(getMarginItem5()).toString() : "");
		Element marginItem1_SElement = booksElement.addElement("marginItem1_S");
		marginItem1_SElement.setText(getMarginItem1_S() != null ? (new StringBuilder()).append(getMarginItem1_S()).toString() : "");
		Element marginItem2_SElement = booksElement.addElement("marginItem2_S");
		marginItem2_SElement.setText(getMarginItem2_S() != null ? (new StringBuilder()).append(getMarginItem2_S()).toString() : "");
		Element marginItem3_SElement = booksElement.addElement("marginItem3_S");
		marginItem3_SElement.setText(getMarginItem3_S() != null ? (new StringBuilder()).append(getMarginItem3_S()).toString() : "");
		Element marginItem4_SElement = booksElement.addElement("marginItem4_S");
		marginItem4_SElement.setText(getMarginItem4_S() != null ? (new StringBuilder()).append(getMarginItem4_S()).toString() : "");
		Element marginItem5_SElement = booksElement.addElement("marginItem5_S");
		marginItem5_SElement.setText(getMarginItem5_S() != null ? (new StringBuilder()).append(getMarginItem5_S()).toString() : "");
		Element marginItemAssure1Element = booksElement.addElement("marginItemAssure1");
		marginItemAssure1Element.setText(getMarginItemAssure1() != null ? (new StringBuilder()).append(getMarginItemAssure1()).toString() : "");
		Element marginItemAssure2Element = booksElement.addElement("marginItemAssure2");
		marginItemAssure2Element.setText(getMarginItemAssure2() != null ? (new StringBuilder()).append(getMarginItemAssure2()).toString() : "");
		Element marginItemAssure3Element = booksElement.addElement("marginItemAssure3");
		marginItemAssure3Element.setText(getMarginItemAssure3() != null ? (new StringBuilder()).append(getMarginItemAssure3()).toString() : "");
		Element marginItemAssure4Element = booksElement.addElement("marginItemAssure4");
		marginItemAssure4Element.setText(getMarginItemAssure4() != null ? (new StringBuilder()).append(getMarginItemAssure4()).toString() : "");
		Element marginItemAssure5Element = booksElement.addElement("marginItemAssure5");
		marginItemAssure5Element.setText(getMarginItemAssure5() != null ? (new StringBuilder()).append(getMarginItemAssure5()).toString() : "");
		Element marginItemAssure1_SElement = booksElement.addElement("marginItemAssure1_S");
		marginItemAssure1_SElement.setText(getMarginItemAssure1_S() != null ? (new StringBuilder()).append(getMarginItemAssure1_S()).toString() : "");
		Element marginItemAssure2_SElement = booksElement.addElement("marginItemAssure2_S");
		marginItemAssure2_SElement.setText(getMarginItemAssure2_S() != null ? (new StringBuilder()).append(getMarginItemAssure2_S()).toString() : "");
		Element marginItemAssure3_SElement = booksElement.addElement("marginItemAssure3_S");
		marginItemAssure3_SElement.setText(getMarginItemAssure3_S() != null ? (new StringBuilder()).append(getMarginItemAssure3_S()).toString() : "");
		Element marginItemAssure4_SElement = booksElement.addElement("marginItemAssure4_S");
		marginItemAssure4_SElement.setText(getMarginItemAssure4_S() != null ? (new StringBuilder()).append(getMarginItemAssure4_S()).toString() : "");
		Element marginItemAssure5_SElement = booksElement.addElement("marginItemAssure5_S");
		marginItemAssure5_SElement.setText(getMarginItemAssure5_S() != null ? (new StringBuilder()).append(getMarginItemAssure5_S()).toString() : "");
		Element marginRate_BElement = booksElement.addElement("marginRate_B");
		marginRate_BElement.setText(getMarginRate_B() != null ? (new StringBuilder()).append(getMarginRate_B()).toString() : "");
		Element marginRate_SElement = booksElement.addElement("marginRate_S");
		marginRate_SElement.setText(getMarginRate_S() != null ? (new StringBuilder()).append(getMarginRate_S()).toString() : "");
		Element settleMarginAlgr_BElement = booksElement.addElement("settleMarginAlgr_B");
		settleMarginAlgr_BElement.setText(getSettleMarginAlgr_B() != null ? (new StringBuilder()).append(getSettleMarginAlgr_B()).toString() : "");
		Element settleMarginAlgr_SElement = booksElement.addElement("settleMarginAlgr_S");
		settleMarginAlgr_SElement.setText(getSettleMarginAlgr_S() != null ? (new StringBuilder()).append(getSettleMarginAlgr_S()).toString() : "");
		Element settleMarginRate_BElement = booksElement.addElement("settleMarginRate_B");
		settleMarginRate_BElement.setText(getSettleMarginRate_B() != null ? (new StringBuilder()).append(getSettleMarginRate_B()).toString() : "");
		Element settleMarginRate_SElement = booksElement.addElement("settleMarginRate_S");
		settleMarginRate_SElement.setText(getSettleMarginRate_S() != null ? (new StringBuilder()).append(getSettleMarginRate_S()).toString() : "");
		Element payoutAlgrElement = booksElement.addElement("payoutAlgr");
		payoutAlgrElement.setText(getPayoutAlgr() != null ? (new StringBuilder()).append(getPayoutAlgr()).toString() : "");
		Element payoutRateElement = booksElement.addElement("payoutRate");
		payoutRateElement.setText(getPayoutRate() != null ? (new StringBuilder()).append(getPayoutRate()).toString() : "");
		String con = document.asXML();
		content = con;
	}

	public String toQury() {
		return "select id, applytype, status, proposer, applytime, approver, approvetime,t.content.extract('/root/commodityID/text()').getStringVal() commodityID,t.content.extract('/root/marginAlgr/text()').getStringVal() marginAlgr,t.content.extract('/root/marginPriceType/text()').getStringVal() marginPriceType,t.content.extract('/root/settleDate1/text()').getStringVal() settleDate1,t.content.extract('/root/settleDate2/text()').getStringVal() settleDate2,t.content.extract('/root/settleDate3/text()').getStringVal() settleDate3,t.content.extract('/root/settleDate4/text()').getStringVal() settleDate4,t.content.extract('/root/settleDate5/text()').getStringVal() settleDate5,t.content.extract('/root/settleDate/text()').getStringVal() settleDate,t.content.extract('/root/marginItem1/text()').getStringVal() marginItem1,t.content.extract('/root/marginItem2/text()').getStringVal() marginItem2,t.content.extract('/root/marginItem3/text()').getStringVal() marginItem3,t.content.extract('/root/marginItem4/text()').getStringVal() marginItem4,t.content.extract('/root/marginItem5/text()').getStringVal() marginItem5,t.content.extract('/root/marginItem1_S/text()').getStringVal() marginItem1_S,t.content.extract('/root/marginItem2_S/text()').getStringVal() marginItem2_S,t.content.extract('/root/marginItem3_S/text()').getStringVal() marginItem3_S,t.content.extract('/root/marginItem4_S/text()').getStringVal() marginItem4_S,t.content.extract('/root/marginItem5_S/text()').getStringVal() marginItem5_S,t.content.extract('/root/marginItemAssure1/text()').getStringVal() marginItemAssure1,t.content.extract('/root/marginItemAssure2/text()').getStringVal() marginItemAssure2,t.content.extract('/root/marginItemAssure3/text()').getStringVal() marginItemAssure3,t.content.extract('/root/marginItemAssure4/text()').getStringVal() marginItemAssure4,t.content.extract('/root/marginItemAssure5/text()').getStringVal() marginItemAssure5,t.content.extract('/root/marginItemAssure1_S/text()').getStringVal() marginItemAssure1_S,t.content.extract('/root/marginItemAssure2_S/text()').getStringVal() marginItemAssure2_S,t.content.extract('/root/marginItemAssure3_S/text()').getStringVal() marginItemAssure3_S,t.content.extract('/root/marginItemAssure4_S/text()').getStringVal() marginItemAssure4_S,t.content.extract('/root/marginItemAssure5_S/text()').getStringVal() marginItemAssure5_S,t.content.extract('/root/marginRate_B/text()').getStringVal() marginRate_B,t.content.extract('/root/marginRate_S/text()').getStringVal() marginRate_S,t.content.extract('/root/settleMarginAlgr_B/text()').getStringVal() settleMarginAlgr_B,t.content.extract('/root/settleMarginAlgr_S/text()').getStringVal() settleMarginAlgr_S,t.content.extract('/root/settleMarginRate_B/text()').getStringVal() settleMarginRate_B,t.content.extract('/root/settleMarginRate_S/text()').getStringVal() settleMarginRate_S,t.content.extract('/root/payoutAlgr/text()').getStringVal() payoutAlgr,t.content.extract('/root/payoutRate/text()').getStringVal() payoutRate, note from t_apply t";
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
		if (this.marginAlgr != null) {
			filter = filter + " and t.content.extract('/root/marginAlgr/text()').getStringVal()=" + this.marginAlgr;
		}
		if (this.marginPriceType != null) {
			filter = filter + " and t.content.extract('/root/marginPriceType/text()').getStringVal()=" + this.marginPriceType;
		}
		if (this.settleDate1 != null) {
			filter = filter + " and to_number(t.content.extract('/root/settleDate1/text()').getStringVal())=" + this.settleDate1;
		}
		if (this.settleDate2 != null) {
			filter = filter + " and to_number(t.content.extract('/root/settleDate2/text()').getStringVal())=" + this.settleDate2;
		}
		if (this.settleDate3 != null) {
			filter = filter + " and to_number(t.content.extract('/root/settleDate3/text()').getStringVal())=" + this.settleDate3;
		}
		if (this.settleDate4 != null) {
			filter = filter + " and to_number(t.content.extract('/root/settleDate4/text()').getStringVal())=" + this.settleDate4;
		}
		if (this.settleDate5 != null) {
			filter = filter + " and to_number(t.content.extract('/root/settleDate5/text()').getStringVal())=" + this.settleDate5;
		}
		if (this.settleDate != null) {
			filter = filter + " and to_number(t.content.extract('/root/settleDate/text()').getStringVal())=" + this.settleDate;
		}
		if (this.marginItem1 != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItem1/text()').getStringVal())=" + this.marginItem1;
		}
		if (this.marginItem2 != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItem2/text()').getStringVal())=" + this.marginItem2;
		}
		if (this.marginItem3 != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItem3/text()').getStringVal())=" + this.marginItem3;
		}
		if (this.marginItem4 != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItem4/text()').getStringVal())=" + this.marginItem4;
		}
		if (this.marginItem5 != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItem5/text()').getStringVal())=" + this.marginItem5;
		}
		if (this.marginItem1_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItem1_S/text()').getStringVal())=" + this.marginItem1_S;
		}
		if (this.marginItem2_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItem2_S/text()').getStringVal())=" + this.marginItem2_S;
		}
		if (this.marginItem3_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItem3_S/text()').getStringVal())=" + this.marginItem3_S;
		}
		if (this.marginItem4_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItem4_S/text()').getStringVal())=" + this.marginItem4_S;
		}
		if (this.marginItem5_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItem5_S/text()').getStringVal())=" + this.marginItem5_S;
		}

		if (this.marginItemAssure1 != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItemAssure1/text()').getStringVal())=" + this.marginItemAssure1;
		}
		if (this.marginItemAssure2 != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItemAssure2/text()').getStringVal())=" + this.marginItemAssure2;
		}
		if (this.marginItemAssure3 != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItemAssure3/text()').getStringVal())=" + this.marginItemAssure3;
		}
		if (this.marginItemAssure4 != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItemAssure4/text()').getStringVal())=" + this.marginItemAssure4;
		}
		if (this.marginItemAssure5 != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItemAssure5/text()').getStringVal())=" + this.marginItemAssure5;
		}
		if (this.marginItemAssure1_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItemAssure1_S/text()').getStringVal())=" + this.marginItemAssure1_S;
		}
		if (this.marginItemAssure2_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItemAssure2_S/text()').getStringVal())=" + this.marginItemAssure2_S;
		}
		if (this.marginItemAssure3_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItemAssure3_S/text()').getStringVal())=" + this.marginItemAssure3_S;
		}
		if (this.marginItemAssure4_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItemAssure4_S/text()').getStringVal())=" + this.marginItemAssure4_S;
		}
		if (this.marginItemAssure5_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginItemAssure5_S/text()').getStringVal())=" + this.marginItemAssure5_S;
		}

		if (this.marginRate_B != null) {
			filter = filter + " and to_number(t.content.extract('/root/marginRate_B/text()').getStringVal())=" + this.marginRate_B;
		}
		if (this.marginRate_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/forceCloseFeeRate_S/text()').getStringVal())=" + this.marginRate_S;
		}

		if (this.settleMarginAlgr_B != null) {
			filter = filter + " and to_number(t.content.extract('/root/settleMarginAlgr_B/text()').getStringVal())=" + this.settleMarginAlgr_B;
		}
		if (this.settleMarginAlgr_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/settleMarginAlgr_S/text()').getStringVal())=" + this.settleMarginAlgr_S;
		}
		if (this.settleMarginRate_B != null) {
			filter = filter + " and to_number(t.content.extract('/root/settleMarginRate_B/text()').getStringVal())=" + this.settleMarginRate_B;
		}
		if (this.settleMarginRate_S != null) {
			filter = filter + " and to_number(t.content.extract('/root/settleMarginRate_S/text()').getStringVal())=" + this.settleMarginRate_S;
		}
		if (this.payoutAlgr != null) {
			filter = filter + " and to_number(t.content.extract('/root/payoutAlgr/text()').getStringVal())=" + this.payoutAlgr;
		}
		if (this.payoutRate != null) {
			filter = filter + " and to_number(t.content.extract('/root/payoutRate/text()').getStringVal())=" + this.payoutRate;
		}
		return filter;
	}
}