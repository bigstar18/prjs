package gnnt.MEBS.bank.front.model;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class Dictionary
  extends StandardModel
{
  private static final long serialVersionUID = -5115729512519432548L;
  @ClassDiscription(name="字典ID", description="")
  private Integer id;
  @ClassDiscription(name="类型 ", description="")
  private Integer type;
  @ClassDiscription(name="银行编号 ", description="")
  private String bankID;
  @ClassDiscription(name="银行", description="")
  private Bank bank;
  @ClassDiscription(name="字典名称", description="")
  private String name;
  @ClassDiscription(name="字典值", description="")
  private String value;
  @ClassDiscription(name="备注信息", description="")
  private String note;
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public Integer getType()
  {
    return this.type;
  }
  
  public void setType(Integer type)
  {
    this.type = type;
  }
  
  public String getBankID()
  {
    return this.bankID;
  }
  
  public void setBankID(String bankID)
  {
    this.bankID = bankID;
  }
  
  public Bank getBank()
  {
    return this.bank;
  }
  
  public void setBank(Bank bank)
  {
    this.bank = bank;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("id", this.id);
  }
}
