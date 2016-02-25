package gnnt.MEBS.delivery.command.util;

public class CommandAnnotate
{
  @annotateParse(commandBean="settleCommand", receiveBean="payout")
  public static final String PAYOUT = "PAYOUT";
  @annotateParse(commandBean="settleCommand", receiveBean="income")
  public static final String INCOME = "INCOME";
  @annotateParse(commandBean="settleCommand", receiveBean="marginTurnGoodsPayment")
  public static final String MARGINTURNGOODSPAYMENT = "MARGINTURNGOODSPAYMENT";
  @annotateParse(commandBean="settleCommand", receiveBean="receivePenalty")
  public static final String RECEIVEPENALTY = "RECEIVEPENALTY";
  @annotateParse(commandBean="settleCommand", receiveBean="payPenalty")
  public static final String PAYPENALTY = "PAYPENALTY";
  @annotateParse(commandBean="settleCommand", receiveBean="settleChangePL")
  public static final String SETTLECHANGEPL = "SETTLECHANGEPL";
  @annotateParse(commandBean="settleCommand", receiveBean="settleHL")
  public static final String SETTLEHL = "SETTLEHL";
  @annotateParse(commandBean="settleCommand", receiveBean="settleFinish")
  public static final String SETTLEFINISH = "SETTLEFINISH";
  @annotateParse(commandBean="settleCommand", receiveBean="settleFinishSelf")
  public static final String SETTLEFINISHSELF = "SETTLEFINISHSELF";
  @annotateParse(commandBean="settleCommand", receiveBean="settleRestore")
  public static final String SETTLERESTORE = "SETTLERESTORE";
  @annotateParse(commandBean="settleCommand", receiveBean="changeRegStock")
  public static final String CHANGEREGSTOCK = "CHANGEREGSTOCK";
  @annotateParse(commandBean="settleCommand", receiveBean="changeStatus")
  public static final String CHANGESTATUS = "CHANGESTATUS";
  @annotateParse(commandBean="settleCommand", receiveBean="settleInvoice")
  public static final String SETTLEINVOICE = "SETTLEINVOICE";
  @annotateParse(commandBean="settleCommand", receiveBean="returnMarginForSell")
  public static final String RETURNMARGINFORSELL = "RETURNMARGINFORSELL";
  @annotateParse(commandBean="settleCommand", receiveBean="returnMarginForBuy")
  public static final String RETURNMARGINFORBUY = "RETURNMARGINFORBUY";
  @annotateParse(commandBean="settleCommand", receiveBean="settleDemur")
  public static final String SETTLEDEMUR = "SETTLEDEMUR";
  @annotateParse(commandBean="settleCommand", receiveBean="settleTransfer")
  public static final String SETTLETRANSFER = "SETTLETRANSFER";
  @annotateParse(commandBean="settleCommand", receiveBean="objectionHandleRemove")
  public static final String OBJECTIONHANDLEREMOVE = "OBJECTIONHANDLEREMOVE";
  @annotateParse(commandBean="settleCommand", receiveBean="inHandle")
  public static final String INHANDLE = "INHANDLE";
  @annotateParse(commandBean="settleCommand", receiveBean="toDemurStatus")
  public static final String TODEMURSTATUS = "TODEMURSTATUS";
  @annotateParse(commandBean="settleCommand", receiveBean="noInvoice")
  public static final String NOINVOICE = "NOINVOICE";
  @annotateParse(commandBean="standardCommand", receiveBean="settleAdd")
  public static final String SETTLEADD = "SETTLEADD";
  @annotateParse(commandBean="standardCommand", receiveBean="settleBalance")
  public static final String SETTLEBALANCE = "SETTLEBALANCE";
  @annotateParse(commandBean="settleCommand", receiveBean="settleCancel")
  public static final String SETTLECANCEL = "SETTLECANCEL";
  @annotateParse(commandBean="settleCommand", receiveBean="settleSelf")
  public static final String SETTLESELF = "SETTLESELF";
}
