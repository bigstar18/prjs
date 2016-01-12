package gnnt.MEBS.timebargain.mgr.model.settle;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class TransferModel extends StandardModel
{
  private static final long serialVersionUID = 11L;

  @ClassDiscription(name="非交易过户id(", description="")
  private Long transferID;

  @ClassDiscription(name="过户人", description="")
  private String customerID_s;

  @ClassDiscription(name="接收人", description="")
  private String customerID_b;

  @ClassDiscription(name="商品代码", description="")
  private String commodityID;

  @ClassDiscription(name="商品代码", description="0：买持仓过户；1：卖持仓过户")
  private Integer bs_flag;

  @ClassDiscription(name="过户类型", description="0：整体移仓；1：指定数量")
  private Integer type;

  @ClassDiscription(name="过户数量", description="")
  private Integer quantity;

  @ClassDiscription(name=" 审核状态", description="0：未审核；1：审核通过；2：审核失败")
  private Integer status;

  @ClassDiscription(name="创建时间", description="")
  private Date createTime;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  public Long getTransferID()
  {
    return this.transferID;
  }

  public void setTransferID(Long transferID)
  {
    this.transferID = transferID;
  }

  public String getCustomerID_s()
  {
    return this.customerID_s;
  }

  public void setCustomerID_s(String customerIDS)
  {
    this.customerID_s = customerIDS;
  }

  public String getCustomerID_b()
  {
    return this.customerID_b;
  }

  public void setCustomerID_b(String customerIDB)
  {
    this.customerID_b = customerIDB;
  }

  public String getCommodityID()
  {
    return this.commodityID;
  }

  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }

  public Integer getBs_flag()
  {
    return this.bs_flag;
  }

  public void setBs_flag(Integer bsFlag)
  {
    this.bs_flag = bsFlag;
  }

  public Integer getType()
  {
    return this.type;
  }

  public void setType(Integer type)
  {
    this.type = type;
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

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }

  public static long getSerialversionuid()
  {
    return 11L;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "transferID", this.transferID);
  }
}