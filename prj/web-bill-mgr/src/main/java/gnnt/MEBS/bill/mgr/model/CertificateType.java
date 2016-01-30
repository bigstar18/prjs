package gnnt.MEBS.bill.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class CertificateType
  extends StandardModel
{
  private static final long serialVersionUID = -5164424968558888161L;
  @ClassDiscription(name="证件类型编号", description="")
  private Integer code;
  @ClassDiscription(name="证件名称", description="")
  private String name;
  
  public Integer getCode()
  {
    return this.code;
  }
  
  public void setCode(Integer paramInteger)
  {
    this.code = paramInteger;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("code", this.code);
  }
}
