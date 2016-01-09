package gnnt.MEBS.bank.mgr.coremodel;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import java.sql.Date;

public class CompareResultVO extends StandardModel
{
  private static final long serialVersionUID = -8340333443223991576L;
  private int errorType;
  private String firmID;
  private String bankID;
  private String account;
  private String id;
  private long m_Id;
  private int type;
  private int m_type;
  private double money;
  private double m_money;
  private Date compareDate;

  public int getErrorType()
  {
    return this.errorType;
  }

  public void setErrorType(int errorType)
  {
    this.errorType = errorType;
  }

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }

  public String getBankID()
  {
    return this.bankID;
  }

  public void setBankID(String bankID)
  {
    this.bankID = bankID;
  }

  public String getAccount()
  {
    return this.account;
  }

  public void setAccount(String account)
  {
    this.account = account;
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public long getM_Id()
  {
    return this.m_Id;
  }

  public void setM_Id(long mId)
  {
    this.m_Id = mId;
  }

  public int getType()
  {
    return this.type;
  }

  public void setType(int type)
  {
    this.type = type;
  }

  public int getM_type()
  {
    return this.m_type;
  }

  public void setM_type(int mType)
  {
    this.m_type = mType;
  }

  public double getMoney()
  {
    return this.money;
  }

  public void setMoney(double money)
  {
    this.money = money;
  }

  public double getM_money()
  {
    return this.m_money;
  }

  public void setM_money(double mMoney)
  {
    this.m_money = mMoney;
  }

  public Date getCompareDate()
  {
    return this.compareDate;
  }

  public void setCompareDate(Date compareDate)
  {
    this.compareDate = compareDate;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}