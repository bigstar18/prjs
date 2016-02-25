package gnnt.MEBS.common.manage.model;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Apply_T_VirtualMoney
  extends Apply
{
  private String firmId;
  private double money;
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public double getMoney()
  {
    return this.money;
  }
  
  public void setMoney(double paramDouble)
  {
    this.money = paramDouble;
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
    Element localElement2 = localDocument.getRootElement().element("firmId");
    setFirmId(localElement2.getText());
    Element localElement3 = localDocument.getRootElement().element("money");
    setMoney(Double.parseDouble(localElement3.getText()));
  }
  
  public void generationContent()
  {
    Document localDocument = DocumentHelper.createDocument();
    localDocument.setXMLEncoding("gb2312");
    Element localElement1 = localDocument.addElement("root");
    Element localElement2 = localElement1.addElement("firmId");
    localElement2.setText(getFirmId());
    Element localElement3 = localElement1.addElement("money");
    localElement3.setText(getMoney() + "");
    String str = localDocument.asXML();
    this.content = str;
  }
  
  public String toQury()
  {
    return "select id, applytype, status, proposer, applytime, approver, approvetime,t.content.extract('/root/firmId/text()').getStringVal() firmId,t.content.extract('/root/money/text()').getStringVal() money, note from c_apply t";
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
    if (this.money != 0.0D) {
      str = str + " and to_number(t.content.extract('/root/money/text()').getStringVal())=" + this.money;
    }
    return str;
  }
}
