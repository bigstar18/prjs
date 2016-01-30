package gnnt.MEBS.timebargain.mgr.model.settle;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class BillFrozen extends StandardModel
{
  private static final long serialVersionUID = -3154079536599510562L;

  @ClassDiscription(name="仓单抵顶表id", description="")
  private Long id;

  @ClassDiscription(name="业务代码", description="")
  private String operation;

  @ClassDiscription(name="仓单号", description="")
  private String billID;

  @ClassDiscription(name="业务类型", description="0:仓单申请 1:提前交收 2:交收配对")
  private Integer operationType;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  public BillFrozen()
  {
  }

  public BillFrozen(String operation, String billID, Integer operationType, Date modifyTime)
  {
    this.operation = operation;
    this.billID = billID;
    this.operationType = operationType;
    this.modifyTime = modifyTime;
  }

  public Integer getOperationType() {
    return this.operationType;
  }

  public void setOperationType(Integer operationType) {
    this.operationType = operationType;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOperation() {
    return this.operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public String getBillID() {
    return this.billID;
  }

  public void setBillID(String billID) {
    this.billID = billID;
  }

  public Date getModifyTime() {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}