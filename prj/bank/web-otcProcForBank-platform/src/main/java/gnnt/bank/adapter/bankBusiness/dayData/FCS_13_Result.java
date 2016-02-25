package gnnt.bank.adapter.bankBusiness.dayData;

import java.io.Serializable;

public class FCS_13_Result
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String busiNum;
  public String tradeTime;
  public String busiSerialNum;
  public String fcsSerialNum;
  public String orderNum;
  public String fromAccount;
  public String otherNum;
  public String amount;
  public String fee;
  public String otherFee;
  public String currency;
  public String busiDate;
  public String checkDate;
  public String adjustStatus;
  public String orderStatus;
  public String errorDescription;
}
