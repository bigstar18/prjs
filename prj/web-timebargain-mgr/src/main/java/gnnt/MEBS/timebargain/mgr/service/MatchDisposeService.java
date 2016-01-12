package gnnt.MEBS.timebargain.mgr.service;

import gnnt.MEBS.timebargain.mgr.model.settle.SettleMatch;
import java.util.Date;

public abstract interface MatchDisposeService
{
  public abstract double getFirmFunds(String paramString)
    throws Exception;

  public abstract String getOperator(String paramString);

  public abstract int getPriceType(String paramString, Date paramDate, int paramInt);

  public abstract int updateHL_Amount(SettleMatch paramSettleMatch)
    throws Exception;

  public abstract int marginToPayout(SettleMatch paramSettleMatch, double paramDouble)
    throws Exception;

  public abstract int incomePayout(SettleMatch paramSettleMatch, double paramDouble)
    throws Exception;

  public abstract int payPayout(SettleMatch paramSettleMatch, double paramDouble)
    throws Exception;

  public abstract int settleTransfer(SettleMatch paramSettleMatch)
    throws Exception;

  public abstract int settleFinish_agreement(SettleMatch paramSettleMatch)
    throws Exception;

  public abstract int backSMargin(SettleMatch paramSettleMatch, double paramDouble)
    throws Exception;

  public abstract int payPenaltyToS(SettleMatch paramSettleMatch, double paramDouble)
    throws Exception;

  public abstract int payPenaltyToB(SettleMatch paramSettleMatch, double paramDouble)
    throws Exception;

  public abstract int takePenaltyFromB(SettleMatch paramSettleMatch, double paramDouble)
    throws Exception;

  public abstract int takePenaltyFromS(SettleMatch paramSettleMatch, double paramDouble)
    throws Exception;

  public abstract int settlePL_B(SettleMatch paramSettleMatch, double paramDouble)
    throws Exception;

  public abstract int settlePL_S(SettleMatch paramSettleMatch, double paramDouble)
    throws Exception;

  public abstract int settleFinish_default(SettleMatch paramSettleMatch)
    throws Exception;

  public abstract int settleCancel(SettleMatch paramSettleMatch, String paramString)
    throws Exception;
}