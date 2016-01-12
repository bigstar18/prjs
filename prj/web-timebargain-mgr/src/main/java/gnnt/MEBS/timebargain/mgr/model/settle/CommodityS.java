package gnnt.MEBS.timebargain.mgr.model.settle;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class CommodityS extends StandardModel
{
  private static final long serialVersionUID = 234874695698504944L;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="商品名称", description="")
  private String name;

  @ClassDiscription(name="商品分类ID", description="")
  private Long sortId;

  @ClassDiscription(name="状态", description="")
  private Long status;

  @ClassDiscription(name="合约因子", description="")
  private Double contractFactor;

  @ClassDiscription(name="商品品种", description="")
  private Long breedId;

  public String getCommodityId()
  {
    return this.commodityId;
  }

  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Long getSortId()
  {
    return this.sortId;
  }

  public void setSortId(Long sortId)
  {
    this.sortId = sortId;
  }

  public Long getStatus()
  {
    return this.status;
  }

  public void setStatus(Long status)
  {
    this.status = status;
  }

  public Double getContractFactor()
  {
    return this.contractFactor;
  }

  public void setContractFactor(Double contractFactor)
  {
    this.contractFactor = contractFactor;
  }

  public Long getBreedId()
  {
    return this.breedId;
  }

  public void setBreedId(Long breedId)
  {
    this.breedId = breedId;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "commodityId", this.commodityId);
  }
}