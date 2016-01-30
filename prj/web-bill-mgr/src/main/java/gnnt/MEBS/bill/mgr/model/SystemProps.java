package gnnt.MEBS.bill.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class SystemProps
  extends StandardModel
{
  private static final long serialVersionUID = 1L;
  @ClassDiscription(name="参数名", description="")
  private String propsKey;
  @ClassDiscription(name="参数值", description="")
  private String propsValue;
  @ClassDiscription(name="运行时的值", description="")
  private String runTimeValue;
  @ClassDiscription(name="说明", description="")
  private String note;
  
  public String getRunTimeValue()
  {
    return this.runTimeValue;
  }
  
  public void setRunTimeValue(String paramString)
  {
    this.runTimeValue = paramString;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String paramString)
  {
    this.note = paramString;
  }
  
  public String getPropsKey()
  {
    return this.propsKey;
  }
  
  public void setPropsKey(String paramString)
  {
    this.propsKey = paramString;
  }
  
  public String getPropsValue()
  {
    return this.propsValue;
  }
  
  public void setPropsValue(String paramString)
  {
    this.propsValue = paramString;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("propsKey", this.propsKey);
  }
}
