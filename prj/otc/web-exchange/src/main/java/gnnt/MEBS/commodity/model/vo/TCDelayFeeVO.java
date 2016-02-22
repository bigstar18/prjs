package gnnt.MEBS.commodity.model.vo;

import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import gnnt.MEBS.commodity.model.SpecialSet;
import gnnt.MEBS.commodity.model.TCDelayFee;
import java.io.Serializable;
import java.util.List;

public class TCDelayFeeVO
  extends SpecialSet
{
  private String commodityId;
  private String commodityName;
  private String firmId;
  private String firmName;
  private String feeAlgr_v;
  private List<TCDelayFee> tcDelayFeeList;
  
  @ClassDiscription(name="延期费各阶梯值:")
  public String getStepDescription()
  {
    String msg = "";
    if ((this.tcDelayFeeList != null) && (this.tcDelayFeeList.size() > 0)) {
      for (TCDelayFee tcDelayFee : this.tcDelayFeeList) {
        if (this.feeAlgr_v.equals("1")) {
          msg = msg + "阶梯" + tcDelayFee.getStepNo() + "的延期费率为" + tcDelayFee.getDelayFee_log() + ",市场延期费率为" + tcDelayFee.getMkt_delayFeeRate_log() + ";";
        } else {
          msg = msg + "阶梯" + tcDelayFee.getStepNo() + "的延期费率为" + tcDelayFee.getDelayFee_v() + ",市场延期费率为" + tcDelayFee.getMkt_delayFeeRate_v() + ";";
        }
      }
    }
    return msg;
  }
  
  public List<TCDelayFee> getTcDelayFeeList()
  {
    return this.tcDelayFeeList;
  }
  
  public void setTcDelayFeeList(List<TCDelayFee> tcDelayFeeList)
  {
    this.tcDelayFeeList = tcDelayFeeList;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  @ClassDiscription(name="商品代码:", key=true, keyWord=true)
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  public void setPrimary(String primary) {}
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  @ClassDiscription(name="商品名称:", key=true, keyWord=true)
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  @ClassDiscription(name="名称:", key=true, keyWord=true, isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Member", value="会员收取"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_S_Member", value="特别会员收取")})
  public String getFirmName()
  {
    if (this.firmName != null) {
      return this.firmName;
    }
    return this.firmId;
  }
  
  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }
  
  @ClassDiscription(name="延期费算法:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="比例"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="固定值")})
  public String getFeeAlgr_v()
  {
    return this.feeAlgr_v;
  }
  
  public void setFeeAlgr_v(String feeAlgr_v)
  {
    this.feeAlgr_v = feeAlgr_v;
  }
}
