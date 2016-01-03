package gnnt.mebsv.hqservice.model.HQVO;

public class MarketStatusVO
{
  public String marketID;
  public String code;
  public float cur;
  public byte status;
  public float value;

  public String toString()
  {
    return "\r\ncode:" + this.code + "\r\ncur:" + this.cur + "\r\nstatus:" + this.status + "\r\nvalue:" + this.value;
  }
}