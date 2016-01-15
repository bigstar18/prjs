package gnnt.MEBS.integrated.broker.model.fundsQuery;

import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.StandardModel.PrimaryInfo;

public class LedgerField extends StandardModel
{
  private static final long serialVersionUID = 5784146401111672026L;
  private String code;
  private String name;
  private Integer fieldSign;
  private String moduleId;
  private Integer orderNum;

  public String getCode()
  {
    return this.code;
  }

  public String getName()
  {
    return this.name;
  }

  public Integer getFieldSign()
  {
    return this.fieldSign;
  }

  public String getModuleId()
  {
    return this.moduleId;
  }

  public Integer getOrderNum()
  {
    return this.orderNum;
  }

  public void setCode(String paramString)
  {
    this.code = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setFieldSign(Integer paramInteger)
  {
    this.fieldSign = paramInteger;
  }

  public void setModuleId(String paramString)
  {
    this.moduleId = paramString;
  }

  public void setOrderNum(Integer paramInteger)
  {
    this.orderNum = paramInteger;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "code", this.code);
  }
}