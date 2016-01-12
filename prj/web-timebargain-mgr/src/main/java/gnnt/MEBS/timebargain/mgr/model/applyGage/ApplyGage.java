package gnnt.MEBS.timebargain.mgr.model.applyGage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class ApplyGage extends StandardModel
{
  private static final long serialVersionUID = -1L;

  @ClassDiscription(name="申请单号", description="")
  private Long applyId;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="交易商代码", description="")
  private String firmId;

  @ClassDiscription(name="交易客户代码", description="")
  private String customerId;

  @ClassDiscription(name="申请数量", description="")
  private Long quantity;

  @ClassDiscription(name="申请种类", description="1：抵顶     2：正常撤销已有抵顶   （资金不够不可撤销）   3：强制撤销已有抵顶   （资金不够也可撤销）")
  private Integer applyType;

  @ClassDiscription(name="当前状态", description="1：待审核   2：审核通过  3：审核不通过")
  private Integer status;

  @ClassDiscription(name="创建时间", description="")
  private Date createTime;

  @ClassDiscription(name="创建人", description="")
  private String creator;

  @ClassDiscription(name="创建人备注", description="")
  private String remark1;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  @ClassDiscription(name="最后修改人", description="")
  private String modifier;

  @ClassDiscription(name="修改人备注", description="")
  private String remark2;

  public Long getApplyId()
  {
    return this.applyId;
  }

  public void setApplyId(Long applyId)
  {
    this.applyId = applyId;
  }

  public String getCommodityId()
  {
    return this.commodityId;
  }

  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }

  public String getCustomerId()
  {
    return this.customerId;
  }

  public void setCustomerId(String customerId)
  {
    this.customerId = customerId;
  }

  public Long getQuantity()
  {
    return this.quantity;
  }

  public void setQuantity(Long quantity)
  {
    this.quantity = quantity;
  }

  public Integer getApplyType()
  {
    return this.applyType;
  }

  public void setApplyType(Integer applyType)
  {
    this.applyType = applyType;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public String getCreator()
  {
    return this.creator;
  }

  public void setCreator(String creator)
  {
    this.creator = creator;
  }

  public String getRemark1()
  {
    return this.remark1;
  }

  public void setRemark1(String remark1)
  {
    this.remark1 = remark1;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }

  public String getModifier()
  {
    return this.modifier;
  }

  public void setModifier(String modifier)
  {
    this.modifier = modifier;
  }

  public String getRemark2()
  {
    return this.remark2;
  }

  public void setRemark2(String remark2)
  {
    this.remark2 = remark2;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "applyId", this.applyId);
  }
}