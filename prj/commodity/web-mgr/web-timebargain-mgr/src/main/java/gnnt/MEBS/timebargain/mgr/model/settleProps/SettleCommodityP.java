package gnnt.MEBS.timebargain.mgr.model.settleProps;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Set;

public class SettleCommodityP extends StandardModel
{
  private static final long serialVersionUID = -5460334074420040501L;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="商品名称", description="")
  private String name;

  @ClassDiscription(name="商品分类ID", description="")
  private Long sortId;

  @ClassDiscription(name="商品品种", description="")
  private Long breedId;

  @ClassDiscription(name="品名Model", description="")
  private BreedP breed;

  @ClassDiscription(name="商品分类Model", description="")
  private Category category;

  @ClassDiscription(name="交收属性Model", description="")
  private Set<SettlePropsP> settleProps;

  public Set<SettlePropsP> getSettleProps()
  {
    return this.settleProps;
  }

  public void setSettleProps(Set<SettlePropsP> settleProps)
  {
    this.settleProps = settleProps;
  }

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

  public Long getBreedId()
  {
    return this.breedId;
  }

  public void setBreedId(Long breedId)
  {
    this.breedId = breedId;
  }

  public BreedP getBreed()
  {
    return this.breed;
  }

  public void setBreed(BreedP breed)
  {
    this.breed = breed;
  }

  public Category getCategory()
  {
    return this.category;
  }

  public void setCategory(Category category)
  {
    this.category = category;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "commodityId", this.commodityId);
  }
}