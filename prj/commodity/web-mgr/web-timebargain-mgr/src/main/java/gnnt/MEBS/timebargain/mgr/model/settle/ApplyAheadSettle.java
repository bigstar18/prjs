package gnnt.MEBS.timebargain.mgr.model.settle;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class ApplyAheadSettle extends StandardModel
{
  private static final long serialVersionUID = 11L;

  @ClassDiscription(name="提前交收申请id", description="")
  private String applyId;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="卖方交易客户ID", description="卖方")
  private String customerId_S;

  @ClassDiscription(name="买方交易客户ID", description="")
  private String customerId_B;

  @ClassDiscription(name="交收价格", description="")
  private Integer price;

  @ClassDiscription(name="交收数量", description="")
  private Integer quantity;

  @ClassDiscription(name="状态", description="1：待审核，2：审核通过，3：审核不通过")
  private Integer status;

  @ClassDiscription(name="创建人", description="")
  private Date createTime;

  @ClassDiscription(name="创建人", description="")
  private String creator;

  @ClassDiscription(name="创建人备注", description="")
  private String remark1;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  @ClassDiscription(name=" 最后修改人", description="")
  private String modifier;

  @ClassDiscription(name="修改人备注", description="")
  private String remark2;

  @ClassDiscription(name="商品查询类", description="")
  private CommodityS commodityS;

  public String getApplyId()
  {
    return this.applyId;
  }

  public void setApplyId(String applyId)
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

  public String getCustomerId_S()
  {
    return this.customerId_S;
  }

  public void setCustomerId_S(String customerIdS)
  {
    this.customerId_S = customerIdS;
  }

  public String getCustomerId_B()
  {
    return this.customerId_B;
  }

  public void setCustomerId_B(String customerIdB)
  {
    this.customerId_B = customerIdB;
  }

  public Integer getPrice()
  {
    return this.price;
  }

  public void setPrice(Integer price)
  {
    this.price = price;
  }

  public Integer getQuantity()
  {
    return this.quantity;
  }

  public void setQuantity(Integer quantity)
  {
    this.quantity = quantity;
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

  public CommodityS getCommodityS()
  {
    return this.commodityS;
  }

  public void setCommodityS(CommodityS commodityS)
  {
    this.commodityS = commodityS;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "applyId", this.applyId);
  }
}