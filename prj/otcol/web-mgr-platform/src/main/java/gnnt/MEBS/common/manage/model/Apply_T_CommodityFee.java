package gnnt.MEBS.common.manage.model;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Apply_T_CommodityFee
  extends Apply
{
  private int type;
  private String commodityID;
  private Short feeAlgr;
  private Double feeRate_B;
  private Double feeRate_S;
  private Double historyCloseFeeRate_B;
  private Double historyCloseFeeRate_S;
  private Double todayCloseFeeRate_B;
  private Double todayCloseFeeRate_S;
  private Double forceCloseFeeRate_B;
  private Double forceCloseFeeRate_S;
  private Short settleFeeAlgr;
  private Double settleFeeRate_B;
  private Double settleFeeRate_S;
  private Double lowestSettleFee;
  
  public Double getLowestSettleFee()
  {
    return this.lowestSettleFee;
  }
  
  public void setLowestSettleFee(Double paramDouble)
  {
    this.lowestSettleFee = paramDouble;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
  }
  
  public Short getFeeAlgr()
  {
    return this.feeAlgr;
  }
  
  public void setFeeAlgr(Short paramShort)
  {
    this.feeAlgr = paramShort;
  }
  
  public Double getFeeRate_B()
  {
    return this.feeRate_B;
  }
  
  public void setFeeRate_B(Double paramDouble)
  {
    this.feeRate_B = paramDouble;
  }
  
  public Double getFeeRate_S()
  {
    return this.feeRate_S;
  }
  
  public void setFeeRate_S(Double paramDouble)
  {
    this.feeRate_S = paramDouble;
  }
  
  public Double getForceCloseFeeRate_B()
  {
    return this.forceCloseFeeRate_B;
  }
  
  public void setForceCloseFeeRate_B(Double paramDouble)
  {
    this.forceCloseFeeRate_B = paramDouble;
  }
  
  public Double getForceCloseFeeRate_S()
  {
    return this.forceCloseFeeRate_S;
  }
  
  public void setForceCloseFeeRate_S(Double paramDouble)
  {
    this.forceCloseFeeRate_S = paramDouble;
  }
  
  public Double getHistoryCloseFeeRate_B()
  {
    return this.historyCloseFeeRate_B;
  }
  
  public void setHistoryCloseFeeRate_B(Double paramDouble)
  {
    this.historyCloseFeeRate_B = paramDouble;
  }
  
  public Double getHistoryCloseFeeRate_S()
  {
    return this.historyCloseFeeRate_S;
  }
  
  public void setHistoryCloseFeeRate_S(Double paramDouble)
  {
    this.historyCloseFeeRate_S = paramDouble;
  }
  
  public Short getSettleFeeAlgr()
  {
    return this.settleFeeAlgr;
  }
  
  public void setSettleFeeAlgr(Short paramShort)
  {
    this.settleFeeAlgr = paramShort;
  }
  
  public Double getSettleFeeRate_B()
  {
    return this.settleFeeRate_B;
  }
  
  public void setSettleFeeRate_B(Double paramDouble)
  {
    this.settleFeeRate_B = paramDouble;
  }
  
  public Double getSettleFeeRate_S()
  {
    return this.settleFeeRate_S;
  }
  
  public void setSettleFeeRate_S(Double paramDouble)
  {
    this.settleFeeRate_S = paramDouble;
  }
  
  public Double getTodayCloseFeeRate_B()
  {
    return this.todayCloseFeeRate_B;
  }
  
  public void setTodayCloseFeeRate_B(Double paramDouble)
  {
    this.todayCloseFeeRate_B = paramDouble;
  }
  
  public Double getTodayCloseFeeRate_S()
  {
    return this.todayCloseFeeRate_S;
  }
  
  public void setTodayCloseFeeRate_S(Double paramDouble)
  {
    this.todayCloseFeeRate_S = paramDouble;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public void setContent(String paramString)
  {
    this.content = paramString;
    Document localDocument = null;
    try
    {
      localDocument = DocumentHelper.parseText(paramString);
    }
    catch (DocumentException localDocumentException)
    {
      localDocumentException.printStackTrace();
    }
    Element localElement1 = localDocument.getRootElement();
    Element localElement2 = localDocument.getRootElement().element("commodityID");
    setCommodityID(localElement2.getText());
    Element localElement3 = localDocument.getRootElement().element("type");
    setType(Integer.parseInt(localElement3.getText()));
    Element localElement4 = localDocument.getRootElement().element("feeAlgr");
    setFeeAlgr(Short.valueOf(Short.parseShort(localElement4.getText())));
    Element localElement5 = localDocument.getRootElement().element("feeRate_B");
    setFeeRate_B(Double.valueOf(Double.parseDouble(localElement5.getText())));
    Element localElement6 = localDocument.getRootElement().element("feeRate_S");
    setFeeRate_S(Double.valueOf(Double.parseDouble(localElement6.getText())));
    Element localElement7 = localDocument.getRootElement().element("historyCloseFeeRate_B");
    setHistoryCloseFeeRate_B(Double.valueOf(Double.parseDouble(localElement7.getText())));
    Element localElement8 = localDocument.getRootElement().element("historyCloseFeeRate_S");
    setHistoryCloseFeeRate_S(Double.valueOf(Double.parseDouble(localElement8.getText())));
    Element localElement9 = localDocument.getRootElement().element("todayCloseFeeRate_B");
    setTodayCloseFeeRate_B(Double.valueOf(Double.parseDouble(localElement9.getText())));
    Element localElement10 = localDocument.getRootElement().element("todayCloseFeeRate_S");
    setTodayCloseFeeRate_S(Double.valueOf(Double.parseDouble(localElement10.getText())));
    Element localElement11 = localDocument.getRootElement().element("forceCloseFeeRate_B");
    setForceCloseFeeRate_B(Double.valueOf(Double.parseDouble(localElement11.getText())));
    Element localElement12 = localDocument.getRootElement().element("forceCloseFeeRate_S");
    setForceCloseFeeRate_S(Double.valueOf(Double.parseDouble(localElement12.getText())));
    Element localElement13 = localDocument.getRootElement().element("settleFeeAlgr");
    setSettleFeeAlgr(Short.valueOf(Short.parseShort(localElement13.getText())));
    Element localElement14 = localDocument.getRootElement().element("settleFeeRate_B");
    setSettleFeeRate_B(Double.valueOf(Double.parseDouble(localElement14.getText())));
    Element localElement15 = localDocument.getRootElement().element("settleFeeRate_S");
    setSettleFeeRate_S(Double.valueOf(Double.parseDouble(localElement15.getText())));
    Element localElement16 = localDocument.getRootElement().element("lowestSettleFee");
    setLowestSettleFee(Double.valueOf(Double.parseDouble(localElement16.getText())));
  }
  
  public void generationContent()
  {
    Document localDocument = DocumentHelper.createDocument();
    localDocument.setXMLEncoding("gb2312");
    Element localElement1 = localDocument.addElement("root");
    Element localElement2 = localElement1.addElement("commodityID");
    localElement2.setText(getCommodityID());
    Element localElement3 = localElement1.addElement("type");
    localElement3.setText(getType() + "");
    Element localElement4 = localElement1.addElement("feeAlgr");
    localElement4.setText(getFeeAlgr() + "" != null ? getFeeAlgr() + "" : "0");
    Element localElement5 = localElement1.addElement("feeRate_B");
    localElement5.setText(getFeeRate_B() + "" != "0" ? getFeeRate_B() + "" : "0");
    Element localElement6 = localElement1.addElement("feeRate_S");
    localElement6.setText(getFeeRate_S() + "" != "0" ? getFeeRate_S() + "" : "0");
    Element localElement7 = localElement1.addElement("historyCloseFeeRate_B");
    localElement7.setText(getHistoryCloseFeeRate_B() + "" != null ? getHistoryCloseFeeRate_B() + "" : "0");
    Element localElement8 = localElement1.addElement("historyCloseFeeRate_S");
    localElement8.setText(getHistoryCloseFeeRate_S() + "" != null ? getHistoryCloseFeeRate_S() + "" : "0");
    Element localElement9 = localElement1.addElement("todayCloseFeeRate_B");
    localElement9.setText(getTodayCloseFeeRate_B() + "" != "0" ? getTodayCloseFeeRate_B() + "" : "0");
    Element localElement10 = localElement1.addElement("todayCloseFeeRate_S");
    localElement10.setText(getTodayCloseFeeRate_S() + "" != "0" ? getTodayCloseFeeRate_S() + "" : "0");
    Element localElement11 = localElement1.addElement("forceCloseFeeRate_B");
    localElement11.setText(getForceCloseFeeRate_B() + "" != "0" ? getForceCloseFeeRate_B() + "" : "0");
    Element localElement12 = localElement1.addElement("forceCloseFeeRate_S");
    localElement12.setText(getForceCloseFeeRate_S() + "" != "0" ? getForceCloseFeeRate_S() + "" : "0");
    Element localElement13 = localElement1.addElement("settleFeeAlgr");
    localElement13.setText(getSettleFeeAlgr() + "" != null ? getSettleFeeAlgr() + "" : "0");
    Element localElement14 = localElement1.addElement("settleFeeRate_B");
    localElement14.setText(getSettleFeeRate_B() + "" != "0" ? getSettleFeeRate_B() + "" : "0");
    Element localElement15 = localElement1.addElement("settleFeeRate_S");
    localElement15.setText(getSettleFeeRate_S() + "" != "0" ? getSettleFeeRate_S() + "" : "0");
    Element localElement16 = localElement1.addElement("lowestSettleFee");
    localElement16.setText(getLowestSettleFee() + "");
    String str = localDocument.asXML();
    this.content = str;
  }
  
  public String toQury()
  {
    return "select id, applytype, status, proposer, applytime, approver, approvetime,t.content.extract('/root/commodityID/text()').getStringVal() commodityID,t.content.extract('/root/type/text()').getStringVal() type,t.content.extract('/root/feeAlgr/text()').getStringVal() feeAlgr,t.content.extract('/root/feeRate_B/text()').getStringVal() feeRate_B,t.content.extract('/root/feeRate_S/text()').getStringVal() feeRate_S,t.content.extract('/root/historyCloseFeeRate_B/text()').getStringVal() historyCloseFeeRate_B,t.content.extract('/root/historyCloseFeeRate_S/text()').getStringVal() historyCloseFeeRate_S,t.content.extract('/root/todayCloseFeeRate_B/text()').getStringVal() todayCloseFeeRate_B,t.content.extract('/root/todayCloseFeeRate_S/text()').getStringVal() todayCloseFeeRate_S,t.content.extract('/root/forceCloseFeeRate_B/text()').getStringVal() forceCloseFeeRate_B,t.content.extract('/root/forceCloseFeeRate_S/text()').getStringVal() forceCloseFeeRate_S,t.content.extract('/root/settleFeeAlgr/text()').getStringVal() settleFeeAlgr,t.content.extract('/root/settleFeeRate_B/text()').getStringVal() settleFeeRate_B,t.content.extract('/root/settleFeeRate_S/text()').getStringVal() settleFeeRate_S,t.content.extract('/root/lowestSettleFee/text()').getStringVal() lowestSettleFee, note from c_apply t";
  }
  
  public String toFilter()
  {
    String str = "";
    if (this.id != 0L) {
      str = str + " and id=" + this.id;
    }
    if (this.applyType != 0) {
      str = str + " and applyType=" + this.applyType;
    }
    if (this.status != 0) {
      str = str + " and status=" + this.status;
    }
    if ((this.proposer != null) && (!"".equals(this.proposer))) {
      str = str + " and proposer='" + this.proposer + "'";
    }
    if ((this.approver != null) && (!"".equals(this.approver))) {
      str = str + " and approver='" + this.approver + "'";
    }
    if ((this.note != null) && (!"".equals(this.note))) {
      str = str + " and note='" + this.note + "'";
    }
    if ((this.commodityID != null) && (!"".equals(this.commodityID))) {
      str = str + " and t.content.extract('/root/commodityID/text()').getStringVal()='" + this.commodityID + "'";
    }
    if (this.feeAlgr != null) {
      str = str + " and t.content.extract('/root/feeAlgr/text()').getStringVal()=" + this.feeAlgr + "";
    }
    if (this.feeRate_B != null) {
      str = str + " and t.content.extract('/root/feeRate_B/text()').getStringVal()=" + this.feeRate_B + "";
    }
    if (this.type != 0) {
      str = str + " and to_number(t.content.extract('/root/type/text()').getStringVal())=" + this.type + "";
    }
    if (this.feeRate_S != null) {
      str = str + " and to_number(t.content.extract('/root/feeRate_S/text()').getStringVal())=" + this.feeRate_S + "";
    }
    if (this.historyCloseFeeRate_B != null) {
      str = str + " and to_number(t.content.extract('/root/historyCloseFeeRate_B/text()').getStringVal())=" + this.historyCloseFeeRate_B + "";
    }
    if (this.historyCloseFeeRate_S != null) {
      str = str + " and to_number(t.content.extract('/root/historyCloseFeeRate_S/text()').getStringVal())=" + this.historyCloseFeeRate_S + "";
    }
    if (this.todayCloseFeeRate_B != null) {
      str = str + " and to_number(t.content.extract('/root/todayCloseFeeRate_B/text()').getStringVal())=" + this.todayCloseFeeRate_B + "";
    }
    if (this.todayCloseFeeRate_S != null) {
      str = str + " and to_number(t.content.extract('/root/todayCloseFeeRate_S/text()').getStringVal())=" + this.todayCloseFeeRate_S + "";
    }
    if (this.forceCloseFeeRate_B != null) {
      str = str + " and to_number(t.content.extract('/root/forceCloseFeeRate_B/text()').getStringVal())=" + this.forceCloseFeeRate_B + "";
    }
    if (this.forceCloseFeeRate_S != null) {
      str = str + " and to_number(t.content.extract('/root/forceCloseFeeRate_S/text()').getStringVal())=" + this.forceCloseFeeRate_S + "";
    }
    if (this.settleFeeAlgr != null) {
      str = str + " and to_number(t.content.extract('/root/settleFeeAlgr/text()').getStringVal())=" + this.settleFeeAlgr + "";
    }
    if (this.settleFeeRate_B != null) {
      str = str + " and to_number(t.content.extract('/root/settleFeeRate_B/text()').getStringVal())=" + this.settleFeeRate_B + "";
    }
    if (this.settleFeeRate_S != null) {
      str = str + " and to_number(t.content.extract('/root/settleFeeRate_S/text()').getStringVal())=" + this.settleFeeRate_S + "";
    }
    return str;
  }
}
