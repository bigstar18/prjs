package gnnt.MEBS.timebargain.mgr.model.settle;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import gnnt.MEBS.timebargain.mgr.model.dataquery.CommodityF;
import java.util.Date;

public class ApplyTreatySettle extends StandardModel
{
  private static final long serialVersionUID = 11L;

  @ClassDiscription(name="提前交收申请id", description="")
  private Long applyID;

  @ClassDiscription(name="商品代码", description="")
  private String commodityID;

  @ClassDiscription(name="卖方交易客户ID", description="卖方")
  private String customerID_S;

  @ClassDiscription(name="买方交易客户ID", description="")
  private String customerID_B;

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
  private CommodityF commodityF;

  public Long getApplyID()
  {
    return this.applyID;
  }

  public void setApplyID(Long applyID)
  {
    this.applyID = applyID;
  }

  public String getCommodityID()
  {
    return this.commodityID;
  }

  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }

  public String getCustomerID_S()
  {
    return this.customerID_S;
  }

  public void setCustomerID_S(String customerIDS)
  {
    this.customerID_S = customerIDS;
  }

  public String getCustomerID_B()
  {
    return this.customerID_B;
  }

  public void setCustomerID_B(String customerIDB)
  {
    this.customerID_B = customerIDB;
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

  public CommodityF getCommodityF()
  {
    return this.commodityF;
  }

  public void setCommodityF(CommodityF commodityF)
  {
    this.commodityF = commodityF;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "applyID", this.applyID);
  }
}