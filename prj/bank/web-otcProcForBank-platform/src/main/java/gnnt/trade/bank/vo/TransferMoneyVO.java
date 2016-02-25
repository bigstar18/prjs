package gnnt.trade.bank.vo;

import java.io.Serializable;

public class TransferMoneyVO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public double money;
  public String firmID;
  public TransferInfoValue payInfo;
  public TransferInfoValue receiveInfo;
  public long actionID;
  
  public TransferMoneyVO(String bankID, String firmID, double money, TransferInfoValue payInfo, TransferInfoValue receiveInfo, long actionID)
  {
    this.bankID = bankID;
    this.firmID = firmID;
    this.money = money;
    this.payInfo = payInfo;
    this.receiveInfo = receiveInfo;
    this.actionID = actionID;
  }
}
