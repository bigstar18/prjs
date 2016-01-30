package gnnt.mebsv.hqservice.model;

public class co_classVO
{
  public String Market;
  public String cc_classid;
  public String cc_commodity_id;
  public String market_name;
  public String cc_name;
  public String cc_fullname;
  public String cc_remark;
  public int cc_pricetype;
  public String cc_desc;

  public String getCc_classid()
  {
    return this.cc_classid;
  }

  public void setCc_classid(String paramString)
  {
    this.cc_classid = paramString;
  }

  public String getCc_desc()
  {
    return this.cc_desc;
  }

  public void setCc_desc(String paramString)
  {
    this.cc_desc = paramString;
  }

  public String getCc_fullname()
  {
    return this.cc_fullname;
  }

  public void setCc_fullname(String paramString)
  {
    this.cc_fullname = paramString;
  }

  public String getCc_name()
  {
    return this.cc_name;
  }

  public void setCc_name(String paramString)
  {
    this.cc_name = paramString;
  }

  public int getCc_pricetype()
  {
    return this.cc_pricetype;
  }

  public void setCc_pricetype(int paramInt)
  {
    this.cc_pricetype = paramInt;
  }

  public String getCc_remark()
  {
    return this.cc_remark;
  }

  public void setCc_remark(String paramString)
  {
    this.cc_remark = paramString;
  }

  public String getMarket()
  {
    return this.Market;
  }

  public void setMarket(String paramString)
  {
    this.Market = paramString;
  }

  public String getCc_commodity_id()
  {
    return this.cc_commodity_id;
  }

  public void setCc_commodity_id(String paramString)
  {
    this.cc_commodity_id = paramString;
  }

  public String getMarket_name()
  {
    return this.market_name;
  }

  public void setMarket_name(String paramString)
  {
    this.market_name = paramString;
  }
}