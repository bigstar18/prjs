package gnnt.MEBS.timebargain.mgr.model.settle;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class PledgeModel extends StandardModel
{
  private static final long serialVersionUID = 11L;

  @ClassDiscription(name="质押资金id", description="")
  private Long pledgeID;

  @ClassDiscription(name="仓单号", description="")
  private String billID;

  @ClassDiscription(name="仓单金额", description="")
  private double billFund;

  @ClassDiscription(name="交易商ID", description="")
  private String firmID;

  @ClassDiscription(name="品种名称", description="")
  private String breedName;

  @ClassDiscription(name="仓单数量", description="")
  private double quantity;

  @ClassDiscription(name="创建时间", description="")
  private Date createTime;

  @ClassDiscription(name="创建人", description="")
  private String creator;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  @ClassDiscription(name="最后修改人", description="")
  private String modifier;

  @ClassDiscription(name="状态", description="0：已申请，1：审核通过，2：审核不通过，3：已撤销")
  private Integer status;

  @ClassDiscription(name="类型", description="0：质押，1：撤销质押")
  private Integer type;

  @ClassDiscription(name="仓单Model类", description="")
  private Stock stock;

  public Long getPledgeID()
  {
    return this.pledgeID;
  }

  public void setPledgeID(Long pledgeID)
  {
    this.pledgeID = pledgeID;
  }

  public String getBillID()
  {
    return this.billID;
  }

  public void setBillID(String billID)
  {
    this.billID = billID;
  }

  public double getBillFund()
  {
    return this.billFund;
  }

  public void setBillFund(double billFund)
  {
    this.billFund = billFund;
  }

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }

  public String getBreedName()
  {
    return this.breedName;
  }

  public void setBreedName(String breedName)
  {
    this.breedName = breedName;
  }

  public double getQuantity()
  {
    return this.quantity;
  }

  public void setQuantity(double quantity)
  {
    this.quantity = quantity;
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

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public Integer getType()
  {
    return this.type;
  }

  public void setType(Integer type)
  {
    this.type = type;
  }

  public Stock getStock()
  {
    return this.stock;
  }

  public void setStock(Stock stock)
  {
    this.stock = stock;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "pledgeID", this.pledgeID);
  }
}