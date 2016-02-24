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
  public String contact;
  public TransferInfoValue payInfo;
  public TransferInfoValue receiveInfo;
  public long actionID;
  public int express;
  public String funID;

  public OutMoneyVO(String bankID, String bankname, double money, String firmID, String contact, TransferInfoValue payInfo, TransferInfoValue receiveInfo, long actionID, int express, String funID)
  {
    this.contact = contact;
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