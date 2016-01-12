package gnnt.MEBS.timebargain.mgr.model.settle;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;
import java.util.List;

public class GageBill
{

  @ClassDiscription(name="仓单抵顶表id", description="")
  private Long id;

  @ClassDiscription(name="交易商号", description="")
  private String firmID;

  @ClassDiscription(name="交易商号", description="")
  private String commodityID;

  @ClassDiscription(name="仓单数量", description="")
  private Long quantity;

  @ClassDiscription(name="备注", description="")
  private String remark;

  @ClassDiscription(name="创建时间", description="")
  private Date createtime;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;
  private List<BillFrozen> billForzenList;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirmID() {
    return this.firmID;
  }

  public void setFirmID(String firmID) {
    this.firmID = firmID;
  }

  public String getCommodityID() {
    return this.commodityID;
  }

  public void setCommodityID(String commodityID) {
    this.commodityID = commodityID;
  }

  public Long getQuantity() {
    return this.quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Date getCreatetime() {
    return this.createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

  public Date getModifyTime() {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public List<BillFrozen> getBillForzenList() {
    return this.billForzenList;
  }

  public void setBillForzenList(List<BillFrozen> billForzenList) {
    this.billForzenList = billForzenList;
  }
}