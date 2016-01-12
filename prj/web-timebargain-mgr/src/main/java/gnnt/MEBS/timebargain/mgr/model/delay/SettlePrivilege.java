package gnnt.MEBS.timebargain.mgr.model.delay;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class SettlePrivilege extends StandardModel
{

  @ClassDiscription(name="表中主键", description="")
  private Long id;

  @ClassDiscription(name="权限对象", description="")
  private Integer type;

  @ClassDiscription(name="权限对象代码", description="")
  private String typeId;

  @ClassDiscription(name="权限对象代码", description="")
  private Integer kind;

  @ClassDiscription(name="权限种类代码", description="")
  private String kindId;

  @ClassDiscription(name="买权限代码", description="")
  private Integer privilegecodeb;

  @ClassDiscription(name="卖权限代码", description="1:买 buy,2:卖 sell")
  private Integer privilegecodes;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Integer getType() {
    return this.type;
  }
  public void setType(Integer type) {
    this.type = type;
  }
  public String getTypeId() {
    return this.typeId;
  }
  public void setTypeId(String typeId) {
    this.typeId = typeId;
  }
  public Integer getKind() {
    return this.kind;
  }
  public void setKind(Integer kind) {
    this.kind = kind;
  }
  public String getKindId() {
    return this.kindId;
  }
  public void setKindId(String kindId) {
    this.kindId = kindId;
  }
  public Integer getPrivilegecodeb() {
    return this.privilegecodeb;
  }
  public void setPrivilegecodeb(Integer privilegecodeb) {
    this.privilegecodeb = privilegecodeb;
  }
  public Integer getPrivilegecodes() {
    return this.privilegecodes;
  }
  public void setPrivilegecodes(Integer privilegecodes) {
    this.privilegecodes = privilegecodes;
  }

  public StandardModel.PrimaryInfo fetchPKey() {
    return new StandardModel.PrimaryInfo( "id", this.id);
  }
}