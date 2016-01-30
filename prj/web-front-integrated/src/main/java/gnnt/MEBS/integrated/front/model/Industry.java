package gnnt.MEBS.integrated.front.model;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class Industry
  extends StandardModel
{
  private static final long serialVersionUID = -4092509302978342001L;
  @ClassDiscription(name="行业编码 ", description="")
  private String code;
  @ClassDiscription(name="行业名称", description="")
  private String name;
  
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
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("code", this.code);
  }
}
