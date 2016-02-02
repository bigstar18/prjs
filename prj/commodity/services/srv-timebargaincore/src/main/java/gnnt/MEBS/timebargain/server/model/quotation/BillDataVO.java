package gnnt.MEBS.timebargain.server.model.quotation;

import java.io.Serializable;

public class BillDataVO
  implements Serializable
{
  private static final long serialVersionUID = -5931032163913849396L;
  public int tradeDate;
  public int time;
  public int date;
  public float curPrice;
  public float balancePrice;
  public float totalMoney;
  public long totalAmount;
  public int reserveCount;
  public float buyPrice;
  public float sellPrice;
}
