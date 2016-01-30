package gnnt.MEBS.integrated.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class CertificateType
  extends StandardModel
{
  private static final long serialVersionUID = -5164424968558888161L;
  @ClassDiscription(name="证件类型编码 ", description="")
  private String code;
  @ClassDiscription(name="证件名称 ", description="")
  private String name;
  @ClassDiscription(name="是否显示 ", description="Y：显示 N：不显示D：已删除")
  private String isvisibal;
  @ClassDiscription(name="排序号 ", description="")
  private Integer sortNo;
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String paramString)
  {
    this.code = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getIsvisibal()
  {
    return this.isvisibal;
  }
  
  public void setIsvisibal(String paramString)
  {
    this.isvisibal = paramString;
  }
  
  public Integer getSortNo()
  {
    return this.sortNo;
  }
  
  public void setSortNo(Integer paramInteger)
  {
    this.sortNo = paramInteger;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("code", this.code);
  }
}
