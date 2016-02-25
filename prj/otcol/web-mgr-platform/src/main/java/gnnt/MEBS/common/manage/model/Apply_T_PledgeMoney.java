package gnnt.MEBS.common.manage.model;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Apply_T_PledgeMoney
  extends Apply
{
  private String firmId;
  private String billID;
  private double billFund;
  private String commodityName;
  private double quantity;
  private int type;
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public double getBillFund()
  {
    return this.billFund;
  }
  
  public void setBillFund(double paramDouble)
  {
    this.billFund = paramDouble;
  }
  
  public String getBillID()
  {
    return this.billID;
  }
  
  public void setBillID(String paramString)
  {
    this.billID = paramString;
  }
  
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String paramString)
  {
    this.commodityName = paramString;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public double getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(double paramDouble)
  {
    this.quantity = paramDouble;
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
    Element localElement2 = localDocument.getRootElement().element("billID");
    setBillID(localElement2.getText());
    Element localElement3 = localDocument.getRootElement().element("type");
    setType(Integer.parseInt(localElement3.getText()));
    Element localElement4 = localDocument.getRootElement().element("commodityName");
    setCommodityName(localElement4.getText());
    Element localElement5 = localDocument.getRootElement().element("quantity");
    setQuantity(Double.parseDouble(localElement5.getText()));
    Element localElement6 = localDocument.getRootElement().element("firmId");
    setFirmId(localElement6.getText());
    Element localElement7 = localDocument.getRootElement().element("billFund");
    setBillFund(Double.parseDouble(localElement7.getText()));
  }
  
  public void generationContent()
  {
    Document localDocument = DocumentHelper.createDocument();
    localDocument.setXMLEncoding("gb2312");
    Element localElement1 = localDocument.addElement("root");
    Element localElement2 = localElement1.addElement("billID");
    localElement2.setText(getBillID());
    Element localElement3 = localElement1.addElement("type");
    localElement3.setText(getType() + "");
    Element localElement4 = localElement1.addElement("commodityName");
    localElement4.setText(getCommodityName() != null ? getCommodityName() : "");
    Element localElement5 = localElement1.addElement("quantity");
    localElement5.setText(getQuantity() != 0.0D ? getQuantity() + "" : "0");
    Element localElement6 = localElement1.addElement("firmId");
    localElement6.setText(getFirmId() != null ? getFirmId() : "");
    Element localElement7 = localElement1.addElement("billFund");
    localElement7.setText(getBillFund() != 0.0D ? getBillFund() + "" : "0");
    String str = localDocument.asXML();
    this.content = str;
  }
  
  public String toQury()
  {
    return "select id, applytype, status, proposer, applytime, approver, approvetime,t.content.extract('/root/billID/text()').getStringVal() billID,t.content.extract('/root/type/text()').getStringVal() type,t.content.extract('/root/commodityName/text()').getStringVal() commodityName,(case when to_number(t.content.extract('/root/quantity/text()').getStringVal())=0 then '' else t.content.extract('/root/quantity/text()').getStringVal() end) quantity,t.content.extract('/root/firmId/text()').getStringVal() firmId,(case when to_number(t.content.extract('/root/billFund/text()').getStringVal())=0 then '' else t.content.extract('/root/billFund/text()').getStringVal() end)  billFund, note from c_apply t";
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
    if ((this.firmId != null) && (!"".equals(this.firmId))) {
      str = str + " and t.content.extract('/root/firmId/text()').getStringVal()='" + this.firmId + "'";
    }
    if ((this.billID != null) && (!"".equals(this.billID))) {
      str = str + " and t.content.extract('/root/billID/text()').getStringVal()='" + this.billID + "'";
    }
    if ((this.commodityName != null) && (!"".equals(this.commodityName))) {
      str = str + " and t.content.extract('/root/commodityName/text()').getStringVal()='" + this.commodityName + "'";
    }
    if (this.type != 0) {
      str = str + " and to_number(t.content.extract('/root/type/text()').getStringVal())=" + this.type + "";
    }
    if (this.quantity != 0.0D) {
      str = str + " and to_number(t.content.extract('/root/quantity/text()').getStringVal())=" + this.quantity + "";
    }
    if (this.billFund != 0.0D) {
      str = str + " and to_number(t.content.extract('/root/billFund/text()').getStringVal())=" + this.billFund + "";
    }
    return str;
  }
}
