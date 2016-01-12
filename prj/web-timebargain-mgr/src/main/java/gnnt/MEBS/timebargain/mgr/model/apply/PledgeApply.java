package gnnt.MEBS.timebargain.mgr.model.apply;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class PledgeApply extends Apply_T
{
  private String firmId;
  private String billID;
  private double billFund;
  private String commodityName;
  private double quantity;
  private int type;

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String firmId) {
    this.firmId = firmId;
  }

  public String getBillID() {
    return this.billID;
  }

  public void setBillID(String billID) {
    this.billID = billID;
  }

  public double getBillFund() {
    return this.billFund;
  }

  public void setBillFund(double billFund) {
    this.billFund = billFund;
  }

  public String getCommodityName() {
    return this.commodityName;
  }

  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  public double getQuantity() {
    return this.quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public void setContent(String content)
  {
    Document doc = null;
    try {
      doc = DocumentHelper.parseText(content);
    }
    catch (DocumentException e) {
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
}