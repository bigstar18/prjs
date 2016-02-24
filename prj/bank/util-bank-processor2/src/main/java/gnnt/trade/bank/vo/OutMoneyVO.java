package gnnt.trade.bank.vo;

import java.io.Serializable;

public class OutMoneyVO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankname;
  public String bankID;
  public double money;
  public String firmID;
  public TransferInfoValue payInfo;
  public TransferInfoValue receiveInfo;
  public long actionID;
  public int express;
  public String funID;
  public String isCrossline;
  
  public OutMoneyVO(String bankID, String bankname, double money, String firmID, TransferInfoValue payInfo, TransferInfoValue receiveInfo, long actionID, String funID)
  {
    this.bankID = bankID;
    this.bankname = bankname;
    this.firmID = firmID;
    this.money = money;
    this.payInfo = payInfo;
    this.receiveInfo = receiveInfo;
    this.actionID = actionID;
    this.funID = funID;
  }
  
  public OutMoneyVO(String bankID, String bankname, double money, String firmID, TransferInfoValue payInfo, TransferInfoValue receiveInfo, long actionID, int express, String funID)
  {
    this.bankID = bankID;
    this.bankname = bankname;
    this.firmID = firmID;
    this.money = money;
    this.payInfo = payInfo;
    this.receiveInfo = receiveInfo;
    this.actionID = actionID;
    this.express = express;
    this.funID = funID;
  }
}
