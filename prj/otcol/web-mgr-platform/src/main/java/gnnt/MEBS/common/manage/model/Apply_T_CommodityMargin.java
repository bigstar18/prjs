package gnnt.MEBS.common.manage.model;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Apply_T_CommodityMargin
  extends Apply
{
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
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
  }
  
  public Short getMarginAlgr()
  {
    return this.marginAlgr;
  }
  
  public void setMarginAlgr(Short paramShort)
  {
    this.marginAlgr = paramShort;
  }
  
  public Double getMarginItem1()
  {
    return this.marginItem1;
  }
  
  public void setMarginItem1(Double paramDouble)
  {
    this.marginItem1 = paramDouble;
  }
  
  public Double getMarginItem1_S()
  {
    return this.marginItem1_S;
  }
  
  public void setMarginItem1_S(Double paramDouble)
  {
    this.marginItem1_S = paramDouble;
  }
  
  public Double getMarginItem2()
  {
    return this.marginItem2;
  }
  
  public void setMarginItem2(Double paramDouble)
  {
    this.marginItem2 = paramDouble;
  }
  
  public Double getMarginItem2_S()
  {
    return this.marginItem2_S;
  }
  
  public void setMarginItem2_S(Double paramDouble)
  {
    this.marginItem2_S = paramDouble;
  }
  
  public Double getMarginItem3()
  {
    return this.marginItem3;
  }
  
  public void setMarginItem3(Double paramDouble)
  {
    this.marginItem3 = paramDouble;
  }
  
  public Double getMarginItem3_S()
  {
    return this.marginItem3_S;
  }
  
  public void setMarginItem3_S(Double paramDouble)
  {
    this.marginItem3_S = paramDouble;
  }
  
  public Double getMarginItem4()
  {
    return this.marginItem4;
  }
  
  public void setMarginItem4(Double paramDouble)
  {
    this.marginItem4 = paramDouble;
  }
  
  public Double getMarginItem4_S()
  {
    return this.marginItem4_S;
  }
  
  public void setMarginItem4_S(Double paramDouble)
  {
    this.marginItem4_S = paramDouble;
  }
  
  public Double getMarginItem5()
  {
    return this.marginItem5;
  }
  
  public void setMarginItem5(Double paramDouble)
  {
    this.marginItem5 = paramDouble;
  }
  
  public Double getMarginItem5_S()
  {
    return this.marginItem5_S;
  }
  
  public void setMarginItem5_S(Double paramDouble)
  {
    this.marginItem5_S = paramDouble;
  }
  
  public Double getMarginItemAssure1()
  {
    return this.marginItemAssure1;
  }
  
  public void setMarginItemAssure1(Double paramDouble)
  {
    this.marginItemAssure1 = paramDouble;
  }
  
  public Double getMarginItemAssure1_S()
  {
    return this.marginItemAssure1_S;
  }
  
  public void setMarginItemAssure1_S(Double paramDouble)
  {
    this.marginItemAssure1_S = paramDouble;
  }
  
  public Double getMarginItemAssure2()
  {
    return this.marginItemAssure2;
  }
  
  public void setMarginItemAssure2(Double paramDouble)
  {
    this.marginItemAssure2 = paramDouble;
  }
  
  public Double getMarginItemAssure2_S()
  {
    return this.marginItemAssure2_S;
  }
  
  public void setMarginItemAssure2_S(Double paramDouble)
  {
    this.marginItemAssure2_S = paramDouble;
  }
  
  public Double getMarginItemAssure3()
  {
    return this.marginItemAssure3;
  }
  
  public void setMarginItemAssure3(Double paramDouble)
  {
    this.marginItemAssure3 = paramDouble;
  }
  
  public Double getMarginItemAssure3_S()
  {
    return this.marginItemAssure3_S;
  }
  
  public void setMarginItemAssure3_S(Double paramDouble)
  {
    this.marginItemAssure3_S = paramDouble;
  }
  
  public Double getMarginItemAssure4()
  {
    return this.marginItemAssure4;
  }
  
  public void setMarginItemAssure4(Double paramDouble)
  {
    this.marginItemAssure4 = paramDouble;
  }
  
  public Double getMarginItemAssure4_S()
  {
    return this.marginItemAssure4_S;
  }
  
  public void setMarginItemAssure4_S(Double paramDouble)
  {
    this.marginItemAssure4_S = paramDouble;
  }
  
  public Double getMarginItemAssure5()
  {
    return this.marginItemAssure5;
  }
  
  public void setMarginItemAssure5(Double paramDouble)
  {
    this.marginItemAssure5 = paramDouble;
  }
  
  public Double getMarginItemAssure5_S()
  {
    return this.marginItemAssure5_S;
  }
  
  public void setMarginItemAssure5_S(Double paramDouble)
  {
    this.marginItemAssure5_S = paramDouble;
  }
  
  public Short getMarginPriceType()
  {
    return this.marginPriceType;
  }
  
  public void setMarginPriceType(Short paramShort)
  {
    this.marginPriceType = paramShort;
  }
  
  public Double getMarginRate_B()
  {
    return this.marginRate_B;
  }
  
  public void setMarginRate_B(Double paramDouble)
  {
    this.marginRate_B = paramDouble;
  }
  
  public Double getMarginRate_S()
  {
    return this.marginRate_S;
  }
  
  public void setMarginRate_S(Double paramDouble)
  {
    this.marginRate_S = paramDouble;
  }
  
  public Short getPayoutAlgr()
  {
    return this.payoutAlgr;
  }
  
  public void setPayoutAlgr(Short paramShort)
  {
    this.payoutAlgr = paramShort;
  }
  
  public Double getPayoutRate()
  {
    return this.payoutRate;
  }
  
  public void setPayoutRate(Double paramDouble)
  {
    this.payoutRate = paramDouble;
  }
  
  public String getSettleDate()
  {
    return this.settleDate;
  }
  
  public void setSettleDate(String paramString)
  {
    this.settleDate = paramString;
  }
  
  public String getSettleDate1()
  {
    return this.settleDate1;
  }
  
  public void setSettleDate1(String paramString)
  {
    this.settleDate1 = paramString;
  }
  
  public String getSettleDate2()
  {
    return this.settleDate2;
  }
  
  public void setSettleDate2(String paramString)
  {
    this.settleDate2 = paramString;
  }
  
  public String getSettleDate3()
  {
    return this.settleDate3;
  }
  
  public void setSettleDate3(String paramString)
  {
    this.settleDate3 = paramString;
  }
  
  public String getSettleDate4()
  {
    return this.settleDate4;
  }
  
  public void setSettleDate4(String paramString)
  {
    this.settleDate4 = paramString;
  }
  
  public String getSettleDate5()
  {
    return this.settleDate5;
  }
  
  public void setSettleDate5(String paramString)
  {
    this.settleDate5 = paramString;
  }
  
  public String getMarketDate()
  {
    return this.marketDate;
  }
  
  public void setMarketDate(String paramString)
  {
    this.marketDate = paramString;
  }
  
  public Short getSettleMarginAlgr_B()
  {
    return this.settleMarginAlgr_B;
  }
  
  public void setSettleMarginAlgr_B(Short paramShort)
  {
    this.settleMarginAlgr_B = paramShort;
  }
  
  public Short getSettleMarginAlgr_S()
  {
    return this.settleMarginAlgr_S;
  }
  
  public void setSettleMarginAlgr_S(Short paramShort)
  {
    this.settleMarginAlgr_S = paramShort;
  }
  
  public Double getSettleMarginRate_B()
  {
    return this.settleMarginRate_B;
  }
  
  public void setSettleMarginRate_B(Double paramDouble)
  {
    this.settleMarginRate_B = paramDouble;
  }
  
  public Double getSettleMarginRate_S()
  {
    return this.settleMarginRate_S;
  }
  
  public void setSettleMarginRate_S(Double paramDouble)
  {
    this.settleMarginRate_S = paramDouble;
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
    Element localElement3 = localDocument.getRootElement().element("marginAlgr");
    setMarginAlgr(Short.valueOf(Short.parseShort(localElement3.getText())));
    Element localElement4 = localDocument.getRootElement().element("marginPriceType");
    if ((localElement4 != null) && (!"".equals(localElement4))) {
      setMarginPriceType(Short.valueOf(Short.parseShort(localElement4.getText())));
    }
    Element localElement5 = localDocument.getRootElement().element("settleDate1");
    setSettleDate1(localElement5.getText() == null ? "" : localElement5.getText());
    Element localElement6 = localDocument.getRootElement().element("settleDate2");
    setSettleDate2(localElement6.getText() == null ? "" : localElement6.getText());
    Element localElement7 = localDocument.getRootElement().element("settleDate3");
    setSettleDate3(localElement7.getText() == null ? "" : localElement7.getText());
    Element localElement8 = localDocument.getRootElement().element("settleDate4");
    setSettleDate4(localElement8.getText() == null ? "" : localElement8.getText());
    Element localElement9 = localDocument.getRootElement().element("settleDate5");
    setSettleDate5(localElement9.getText() == null ? "" : localElement9.getText());
    Element localElement10 = localDocument.getRootElement().element("settleDate");
    setSettleDate(localElement10.getText() == null ? "" : localElement10.getText());
    Element localElement11 = localDocument.getRootElement().element("marginItem1");
    setMarginItem1(Double.valueOf(Double.parseDouble(localElement11.getText())));
    Element localElement12 = localDocument.getRootElement().element("marginItem2");
    if ((localElement12 != null) && (localElement12.getText() != null) && (!"".equals(localElement12.getText()))) {
      setMarginItem2(Double.valueOf(Double.parseDouble(localElement12.getText())));
    }
    Element localElement13 = localDocument.getRootElement().element("marginItem3");
    if ((localElement13.getText() != null) && (!"".equals(localElement13.getText()))) {
      setMarginItem3(Double.valueOf(Double.parseDouble(localElement13.getText())));
    }
    Element localElement14 = localDocument.getRootElement().element("marginItem4");
    if ((localElement14.getText() != null) && (!"".equals(localElement14.getText()))) {
      setMarginItem4(Double.valueOf(Double.parseDouble(localElement14.getText())));
    }
    Element localElement15 = localDocument.getRootElement().element("marginItem5");
    if ((localElement15.getText() != null) && (!"".equals(localElement15.getText()))) {
      setMarginItem5(Double.valueOf(Double.parseDouble(localElement15.getText())));
    }
    Element localElement16 = localDocument.getRootElement().element("marginItem1_S");
    if ((localElement16.getText() != null) && (!"".equals(localElement16.getText()))) {
      setMarginItem1_S(Double.valueOf(Double.parseDouble(localElement16.getText())));
    }
    Element localElement17 = localDocument.getRootElement().element("marginItem2_S");
    if ((localElement17.getText() != null) && (!"".equals(localElement17.getText()))) {
      setMarginItem2_S(Double.valueOf(Double.parseDouble(localElement17.getText())));
    }
    Element localElement18 = localDocument.getRootElement().element("marginItem3_S");
    if ((localElement18.getText() != null) && (!"".equals(localElement18.getText()))) {
      setMarginItem3_S(Double.valueOf(Double.parseDouble(localElement18.getText())));
    }
    Element localElement19 = localDocument.getRootElement().element("marginItem4_S");
    if ((localElement19.getText() != null) && (!"".equals(localElement19.getText()))) {
      setMarginItem4_S(Double.valueOf(Double.parseDouble(localElement19.getText())));
    }
    Element localElement20 = localDocument.getRootElement().element("marginItem5_S");
    if ((localElement20.getText() != null) && (!"".equals(localElement20.getText()))) {
      setMarginItem5_S(Double.valueOf(Double.parseDouble(localElement20.getText())));
    }
    Element localElement21 = localDocument.getRootElement().element("marginItemAssure1");
    if ((localElement21.getText() != null) && (!"".equals(localElement21.getText()))) {
      setMarginItemAssure1(Double.valueOf(Double.parseDouble(localElement21.getText())));
    }
    Element localElement22 = localDocument.getRootElement().element("marginItemAssure2");
    if ((localElement22.getText() != null) && (!"".equals(localElement22.getText()))) {
      setMarginItemAssure2(Double.valueOf(Double.parseDouble(localElement22.getText())));
    }
    Element localElement23 = localDocument.getRootElement().element("marginItemAssure3");
    if ((localElement23.getText() != null) && (!"".equals(localElement23.getText()))) {
      setMarginItemAssure3(Double.valueOf(Double.parseDouble(localElement23.getText())));
    }
    Element localElement24 = localDocument.getRootElement().element("marginItemAssure4");
    if ((localElement24.getText() != null) && (!"".equals(localElement24.getText()))) {
      setMarginItemAssure4(Double.valueOf(Double.parseDouble(localElement24.getText())));
    }
    Element localElement25 = localDocument.getRootElement().element("marginItemAssure5");
    if ((localElement25.getText() != null) && (!"".equals(localElement25.getText()))) {
      setMarginItemAssure5(Double.valueOf(Double.parseDouble(localElement25.getText())));
    }
    Element localElement26 = localDocument.getRootElement().element("marginItemAssure1_S");
    if ((localElement26.getText() != null) && (!"".equals(localElement26.getText()))) {
      setMarginItemAssure1_S(Double.valueOf(Double.parseDouble(localElement26.getText())));
    }
    Element localElement27 = localDocument.getRootElement().element("marginItemAssure2_S");
    if ((localElement27.getText() != null) && (!"".equals(localElement27.getText()))) {
      setMarginItemAssure2_S(Double.valueOf(Double.parseDouble(localElement27.getText())));
    }
    Element localElement28 = localDocument.getRootElement().element("marginItemAssure3_S");
    if ((localElement28.getText() != null) && (!"".equals(localElement28.getText()))) {
      setMarginItemAssure3_S(Double.valueOf(Double.parseDouble(localElement28.getText())));
    }
    Element localElement29 = localDocument.getRootElement().element("marginItemAssure4_S");
    if ((localElement29.getText() != null) && (!"".equals(localElement29.getText()))) {
      setMarginItemAssure4_S(Double.valueOf(Double.parseDouble(localElement29.getText())));
    }
    Element localElement30 = localDocument.getRootElement().element("marginItemAssure5_S");
    if ((localElement30.getText() != null) && (!"".equals(localElement30.getText()))) {
      setMarginItemAssure5_S(Double.valueOf(Double.parseDouble(localElement30.getText())));
    }
    Element localElement31 = localDocument.getRootElement().element("marginRate_B");
    if ((localElement31 != null) && (localElement31.getText() != null) && (!"".equals(localElement31.getText()))) {
      setMarginRate_B(Double.valueOf(Double.parseDouble(localElement31.getText())));
    }
    Element localElement32 = localDocument.getRootElement().element("marginRate_S");
    if ((localElement32.getText() != null) && (!"".equals(localElement32.getText()))) {
      setMarginRate_S(Double.valueOf(Double.parseDouble(localElement32.getText())));
    }
    Element localElement33 = localDocument.getRootElement().element("settleMarginAlgr_B");
    setSettleMarginAlgr_B(Short.valueOf(Short.parseShort(localElement33.getText())));
    Element localElement34 = localDocument.getRootElement().element("settleMarginAlgr_S");
    setSettleMarginAlgr_S(Short.valueOf(Short.parseShort(localElement34.getText())));
    Element localElement35 = localDocument.getRootElement().element("settleMarginRate_B");
    setSettleMarginRate_B(Double.valueOf(Double.parseDouble(localElement35.getText())));
    Element localElement36 = localDocument.getRootElement().element("settleMarginRate_S");
    setSettleMarginRate_S(Double.valueOf(Double.parseDouble(localElement36.getText())));
    Element localElement37 = localDocument.getRootElement().element("payoutAlgr");
    setPayoutAlgr(Short.valueOf(Short.parseShort(localElement37.getText())));
    Element localElement38 = localDocument.getRootElement().element("payoutRate");
    setPayoutRate(Double.valueOf(Double.parseDouble(localElement38.getText())));
  }
  
  public void generationContent()
  {
    Document localDocument = DocumentHelper.createDocument();
    localDocument.setXMLEncoding("gb2312");
    Element localElement1 = localDocument.addElement("root");
    Element localElement2 = localElement1.addElement("commodityID");
    localElement2.setText(getCommodityID());
    Element localElement3 = localElement1.addElement("marginAlgr");
    localElement3.setText(getMarginAlgr() + "");
    Element localElement4 = localElement1.addElement("marginPriceType");
    localElement4.setText(getMarginPriceType() + "");
    Element localElement5 = localElement1.addElement("settleDate1");
    localElement5.setText(getSettleDate1() + "");
    Element localElement6 = localElement1.addElement("settleDate2");
    localElement6.setText(getSettleDate2() + "");
    Element localElement7 = localElement1.addElement("settleDate3");
    localElement7.setText(getSettleDate3() + "");
    Element localElement8 = localElement1.addElement("settleDate4");
    localElement8.setText(getSettleDate4() + "");
    Element localElement9 = localElement1.addElement("settleDate5");
    localElement9.setText(getSettleDate5() + "");
    Element localElement10 = localElement1.addElement("settleDate");
    localElement10.setText(getSettleDate() + "");
    Element localElement11 = localElement1.addElement("marketDate");
    localElement11.setText(getMarketDate() + "");
    Element localElement12 = localElement1.addElement("marginItem1");
    localElement12.setText(getMarginItem1() + "");
    Element localElement13 = localElement1.addElement("marginItem2");
    localElement13.setText(getMarginItem2() + "");
    Element localElement14 = localElement1.addElement("marginItem3");
    localElement14.setText(getMarginItem3() + "");
    Element localElement15 = localElement1.addElement("marginItem4");
    localElement15.setText(getMarginItem4() + "");
    Element localElement16 = localElement1.addElement("marginItem5");
    localElement16.setText(getMarginItem5() + "");
    Element localElement17 = localElement1.addElement("marginItem1_S");
    localElement17.setText(getMarginItem1_S() + "");
    Element localElement18 = localElement1.addElement("marginItem2_S");
    localElement18.setText(getMarginItem2_S() + "");
    Element localElement19 = localElement1.addElement("marginItem3_S");
    localElement19.setText(getMarginItem3_S() + "");
    Element localElement20 = localElement1.addElement("marginItem4_S");
    localElement20.setText(getMarginItem4_S() + "");
    Element localElement21 = localElement1.addElement("marginItem5_S");
    localElement21.setText(getMarginItem5_S() + "");
    Element localElement22 = localElement1.addElement("marginItemAssure1");
    localElement22.setText(getMarginItemAssure1() + "");
    Element localElement23 = localElement1.addElement("marginItemAssure2");
    localElement23.setText(getMarginItemAssure2() + "");
    Element localElement24 = localElement1.addElement("marginItemAssure3");
    localElement24.setText(getMarginItemAssure3() + "");
    Element localElement25 = localElement1.addElement("marginItemAssure4");
    localElement25.setText(getMarginItemAssure4() + "");
    Element localElement26 = localElement1.addElement("marginItemAssure5");
    localElement26.setText(getMarginItemAssure5() + "");
    Element localElement27 = localElement1.addElement("marginItemAssure1_S");
    localElement27.setText(getMarginItemAssure1_S() + "");
    Element localElement28 = localElement1.addElement("marginItemAssure2_S");
    localElement28.setText(getMarginItemAssure2_S() + "");
    Element localElement29 = localElement1.addElement("marginItemAssure3_S");
    localElement29.setText(getMarginItemAssure3_S() + "");
    Element localElement30 = localElement1.addElement("marginItemAssure4_S");
    localElement30.setText(getMarginItemAssure4_S() + "");
    Element localElement31 = localElement1.addElement("marginItemAssure5_S");
    localElement31.setText(getMarginItemAssure5_S() + "");
    Element localElement32 = localElement1.addElement("marginRate_B");
    localElement32.setText(getMarginRate_B() + "");
    Element localElement33 = localElement1.addElement("marginRate_S");
    localElement33.setText(getMarginRate_S() + "");
    Element localElement34 = localElement1.addElement("settleMarginAlgr_B");
    localElement34.setText(getSettleMarginAlgr_B() + "");
    Element localElement35 = localElement1.addElement("settleMarginAlgr_S");
    localElement35.setText(getSettleMarginAlgr_S() + "");
    Element localElement36 = localElement1.addElement("settleMarginRate_B");
    localElement36.setText(getSettleMarginRate_B() + "");
    Element localElement37 = localElement1.addElement("settleMarginRate_S");
    localElement37.setText(getSettleMarginRate_S() + "");
    Element localElement38 = localElement1.addElement("payoutAlgr");
    localElement38.setText(getPayoutAlgr() + "");
    Element localElement39 = localElement1.addElement("payoutRate");
    localElement39.setText(getPayoutRate() + "");
    String str = localDocument.asXML();
    this.content = str;
  }
  
  public String toQury()
  {
    return "select id, applytype, status, proposer, applytime, approver, approvetime,t.content.extract('/root/commodityID/text()').getStringVal() commodityID,t.content.extract('/root/marginAlgr/text()').getStringVal() marginAlgr,t.content.extract('/root/marginPriceType/text()').getStringVal() marginPriceType,t.content.extract('/root/settleDate1/text()').getStringVal() settleDate1,t.content.extract('/root/settleDate2/text()').getStringVal() settleDate2,t.content.extract('/root/settleDate3/text()').getStringVal() settleDate3,t.content.extract('/root/settleDate4/text()').getStringVal() settleDate4,t.content.extract('/root/settleDate5/text()').getStringVal() settleDate5,t.content.extract('/root/settleDate/text()').getStringVal() settleDate,t.content.extract('/root/marginItem1/text()').getStringVal() marginItem1,t.content.extract('/root/marginItem2/text()').getStringVal() marginItem2,t.content.extract('/root/marginItem3/text()').getStringVal() marginItem3,t.content.extract('/root/marginItem4/text()').getStringVal() marginItem4,t.content.extract('/root/marginItem5/text()').getStringVal() marginItem5,t.content.extract('/root/marginItem1_S/text()').getStringVal() marginItem1_S,t.content.extract('/root/marginItem2_S/text()').getStringVal() marginItem2_S,t.content.extract('/root/marginItem3_S/text()').getStringVal() marginItem3_S,t.content.extract('/root/marginItem4_S/text()').getStringVal() marginItem4_S,t.content.extract('/root/marginItem5_S/text()').getStringVal() marginItem5_S,t.content.extract('/root/marginItemAssure1/text()').getStringVal() marginItemAssure1,t.content.extract('/root/marginItemAssure2/text()').getStringVal() marginItemAssure2,t.content.extract('/root/marginItemAssure3/text()').getStringVal() marginItemAssure3,t.content.extract('/root/marginItemAssure4/text()').getStringVal() marginItemAssure4,t.content.extract('/root/marginItemAssure5/text()').getStringVal() marginItemAssure5,t.content.extract('/root/marginItemAssure1_S/text()').getStringVal() marginItemAssure1_S,t.content.extract('/root/marginItemAssure2_S/text()').getStringVal() marginItemAssure2_S,t.content.extract('/root/marginItemAssure3_S/text()').getStringVal() marginItemAssure3_S,t.content.extract('/root/marginItemAssure4_S/text()').getStringVal() marginItemAssure4_S,t.content.extract('/root/marginItemAssure5_S/text()').getStringVal() marginItemAssure5_S,t.content.extract('/root/marginRate_B/text()').getStringVal() marginRate_B,t.content.extract('/root/marginRate_S/text()').getStringVal() marginRate_S,t.content.extract('/root/settleMarginAlgr_B/text()').getStringVal() settleMarginAlgr_B,t.content.extract('/root/settleMarginAlgr_S/text()').getStringVal() settleMarginAlgr_S,t.content.extract('/root/settleMarginRate_B/text()').getStringVal() settleMarginRate_B,t.content.extract('/root/settleMarginRate_S/text()').getStringVal() settleMarginRate_S,t.content.extract('/root/payoutAlgr/text()').getStringVal() payoutAlgr,t.content.extract('/root/payoutRate/text()').getStringVal() payoutRate, note from c_apply t";
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
    if (this.marginAlgr != null) {
      str = str + " and t.content.extract('/root/marginAlgr/text()').getStringVal()=" + this.marginAlgr + "";
    }
    if (this.marginPriceType != null) {
      str = str + " and t.content.extract('/root/marginPriceType/text()').getStringVal()=" + this.marginPriceType + "";
    }
    if (this.settleDate1 != null) {
      str = str + " and to_number(t.content.extract('/root/settleDate1/text()').getStringVal())=" + this.settleDate1 + "";
    }
    if (this.settleDate2 != null) {
      str = str + " and to_number(t.content.extract('/root/settleDate2/text()').getStringVal())=" + this.settleDate2 + "";
    }
    if (this.settleDate3 != null) {
      str = str + " and to_number(t.content.extract('/root/settleDate3/text()').getStringVal())=" + this.settleDate3 + "";
    }
    if (this.settleDate4 != null) {
      str = str + " and to_number(t.content.extract('/root/settleDate4/text()').getStringVal())=" + this.settleDate4 + "";
    }
    if (this.settleDate5 != null) {
      str = str + " and to_number(t.content.extract('/root/settleDate5/text()').getStringVal())=" + this.settleDate5 + "";
    }
    if (this.settleDate != null) {
      str = str + " and to_number(t.content.extract('/root/settleDate/text()').getStringVal())=" + this.settleDate + "";
    }
    if (this.marginItem1 != null) {
      str = str + " and to_number(t.content.extract('/root/marginItem1/text()').getStringVal())=" + this.marginItem1 + "";
    }
    if (this.marginItem2 != null) {
      str = str + " and to_number(t.content.extract('/root/marginItem2/text()').getStringVal())=" + this.marginItem2 + "";
    }
    if (this.marginItem3 != null) {
      str = str + " and to_number(t.content.extract('/root/marginItem3/text()').getStringVal())=" + this.marginItem3 + "";
    }
    if (this.marginItem4 != null) {
      str = str + " and to_number(t.content.extract('/root/marginItem4/text()').getStringVal())=" + this.marginItem4 + "";
    }
    if (this.marginItem5 != null) {
      str = str + " and to_number(t.content.extract('/root/marginItem5/text()').getStringVal())=" + this.marginItem5 + "";
    }
    if (this.marginItem1_S != null) {
      str = str + " and to_number(t.content.extract('/root/marginItem1_S/text()').getStringVal())=" + this.marginItem1_S + "";
    }
    if (this.marginItem2_S != null) {
      str = str + " and to_number(t.content.extract('/root/marginItem2_S/text()').getStringVal())=" + this.marginItem2_S + "";
    }
    if (this.marginItem3_S != null) {
      str = str + " and to_number(t.content.extract('/root/marginItem3_S/text()').getStringVal())=" + this.marginItem3_S + "";
    }
    if (this.marginItem4_S != null) {
      str = str + " and to_number(t.content.extract('/root/marginItem4_S/text()').getStringVal())=" + this.marginItem4_S + "";
    }
    if (this.marginItem5_S != null) {
      str = str + " and to_number(t.content.extract('/root/marginItem5_S/text()').getStringVal())=" + this.marginItem5_S + "";
    }
    if (this.marginItemAssure1 != null) {
      str = str + " and to_number(t.content.extract('/root/marginItemAssure1/text()').getStringVal())=" + this.marginItemAssure1 + "";
    }
    if (this.marginItemAssure2 != null) {
      str = str + " and to_number(t.content.extract('/root/marginItemAssure2/text()').getStringVal())=" + this.marginItemAssure2 + "";
    }
    if (this.marginItemAssure3 != null) {
      str = str + " and to_number(t.content.extract('/root/marginItemAssure3/text()').getStringVal())=" + this.marginItemAssure3 + "";
    }
    if (this.marginItemAssure4 != null) {
      str = str + " and to_number(t.content.extract('/root/marginItemAssure4/text()').getStringVal())=" + this.marginItemAssure4 + "";
    }
    if (this.marginItemAssure5 != null) {
      str = str + " and to_number(t.content.extract('/root/marginItemAssure5/text()').getStringVal())=" + this.marginItemAssure5 + "";
    }
    if (this.marginItemAssure1_S != null) {
      str = str + " and to_number(t.content.extract('/root/marginItemAssure1_S/text()').getStringVal())=" + this.marginItemAssure1_S + "";
    }
    if (this.marginItemAssure2_S != null) {
      str = str + " and to_number(t.content.extract('/root/marginItemAssure2_S/text()').getStringVal())=" + this.marginItemAssure2_S + "";
    }
    if (this.marginItemAssure3_S != null) {
      str = str + " and to_number(t.content.extract('/root/marginItemAssure3_S/text()').getStringVal())=" + this.marginItemAssure3_S + "";
    }
    if (this.marginItemAssure4_S != null) {
      str = str + " and to_number(t.content.extract('/root/marginItemAssure4_S/text()').getStringVal())=" + this.marginItemAssure4_S + "";
    }
    if (this.marginItemAssure5_S != null) {
      str = str + " and to_number(t.content.extract('/root/marginItemAssure5_S/text()').getStringVal())=" + this.marginItemAssure5_S + "";
    }
    if (this.marginRate_B != null) {
      str = str + " and to_number(t.content.extract('/root/marginRate_B/text()').getStringVal())=" + this.marginRate_B + "";
    }
    if (this.marginRate_S != null) {
      str = str + " and to_number(t.content.extract('/root/forceCloseFeeRate_S/text()').getStringVal())=" + this.marginRate_S + "";
    }
    if (this.settleMarginAlgr_B != null) {
      str = str + " and to_number(t.content.extract('/root/settleMarginAlgr_B/text()').getStringVal())=" + this.settleMarginAlgr_B + "";
    }
    if (this.settleMarginAlgr_S != null) {
      str = str + " and to_number(t.content.extract('/root/settleMarginAlgr_S/text()').getStringVal())=" + this.settleMarginAlgr_S + "";
    }
    if (this.settleMarginRate_B != null) {
      str = str + " and to_number(t.content.extract('/root/settleMarginRate_B/text()').getStringVal())=" + this.settleMarginRate_B + "";
    }
    if (this.settleMarginRate_S != null) {
      str = str + " and to_number(t.content.extract('/root/settleMarginRate_S/text()').getStringVal())=" + this.settleMarginRate_S + "";
    }
    if (this.payoutAlgr != null) {
      str = str + " and to_number(t.content.extract('/root/payoutAlgr/text()').getStringVal())=" + this.payoutAlgr + "";
    }
    if (this.payoutRate != null) {
      str = str + " and to_number(t.content.extract('/root/payoutRate/text()').getStringVal())=" + this.payoutRate + "";
    }
    return str;
  }
}
