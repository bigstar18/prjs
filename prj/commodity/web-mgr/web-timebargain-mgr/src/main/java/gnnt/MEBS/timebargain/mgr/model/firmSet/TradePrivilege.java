package gnnt.MEBS.timebargain.mgr.model.firmSet;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class TradePrivilege extends StandardModel
{
  private static final long serialVersionUID = 6080786041812427056L;

  @ClassDiscription(name="交易权限ID", description="")
  private Integer ID;

  @ClassDiscription(name="权限对象", description="")
  private Integer type;

  @ClassDiscription(name="权限对象代码", description="")
  private String typeID;

  @ClassDiscription(name="权限种类", description="")
  private Integer kind;

  @ClassDiscription(name="权限种类代码", description="")
  private String kindID;

  @ClassDiscription(name="买权限代码", description="")
  private Integer privilegeCode_B;

  @ClassDiscription(name="卖权限代码", description="")
  private Integer privilegeCode_S;

  public Integer getID()
  {
    return this.ID;
  }

  public void setID(Integer ID)
  {
    this.ID = ID;
  }

  public Integer getType()
  {
    return this.type;
  }

  public void setType(Integer type)
  {
    this.type = type;
  }

  public String getTypeID()
  {
    return this.typeID;
  }

  public void setTypeID(String typeID)
  {
    this.typeID = typeID;
  }

  public Integer getKind()
  {
    return this.kind;
  }

  public void setKind(Integer kind)
  {
    this.kind = kind;
  }

  public String getKindID()
  {
    return this.kindID;
  }

  public void setKindID(String kindID)
  {
    this.kindID = kindID;
  }

  public Integer getPrivilegeCode_B()
  {
    return this.privilegeCode_B;
  }

  public void setPrivilegeCode_B(Integer privilegeCodeB)
  {
    this.privilegeCode_B = privilegeCodeB;
  }

  public Integer getPrivilegeCode_S()
  {
    return this.privilegeCode_S;
  }

  public void setPrivilegeCode_S(Integer privilegeCodeS)
  {
    this.privilegeCode_S = privilegeCodeS;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "ID", this.ID);
  }
}