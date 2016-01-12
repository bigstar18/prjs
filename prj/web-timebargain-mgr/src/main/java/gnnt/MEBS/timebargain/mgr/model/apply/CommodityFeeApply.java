package gnnt.MEBS.timebargain.mgr.model.apply;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class CommodityFeeApply extends Apply_T
{

  @ClassDiscription(name="", description="")
  private int type;

  @ClassDiscription(name="", description="")
  private String commodityID;

  @ClassDiscription(name="", description="")
  private Short feeAlgr;

  @ClassDiscription(name="", description="")
  private Double feeRate_B;

  @ClassDiscription(name="", description="")
  private Double feeRate_S;

  @ClassDiscription(name="", description="")
  private Double historyCloseFeeRate_B;

  @ClassDiscription(name="", description="")
  private Double historyCloseFeeRate_S;

  @ClassDiscription(name="", description="")
  private Double todayCloseFeeRate_B;

  @ClassDiscription(name="", description="")
  private Double todayCloseFeeRate_S;

  @ClassDiscription(name="", description="")
  private Double forceCloseFeeRate_B;

  @ClassDiscription(name="", description="")
  private Double forceCloseFeeRate_S;

  @ClassDiscription(name="", description="")
  private Short settleFeeAlgr;

  @ClassDiscription(name="", description="")
  private Double settleFeeRate_B;

  @ClassDiscription(name="", description="")
  private Double settleFeeRate_S;

  @ClassDiscription(name="", description="")
  private Double lowestSettleFee;

  public Double getLowestSettleFee()
  {
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
}