package gnnt.MEBS.integrated.broker.model.fundsQuery;

import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class ClientLedger extends StandardModel
{
  private static final long serialVersionUID = 487654776733350247L;
  private Date b_Date;
  private String firmId;
  private String code;
  private Double value;
  private LedgerField ledgerField;

  public Date getB_Date()
  {
    return this.b_Date;
  }

  public void setB_Date(Date paramDate)
  {
    this.b_Date = paramDate;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }

  public String getCode()
  {
    return this.code;
  }

  public void setCode(String paramString)
  {
    this.code = paramString;
  }

  public Double getValue()
  {
    return this.value;
  }

  public void setValue(Double paramDouble)
  {
    this.value = paramDouble;
  }

  public LedgerField getLedgerField()
  {
    return this.ledgerField;
  }

  public void setLedgerField(LedgerField paramLedgerField)
  {
    this.ledgerField = paramLedgerField;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}