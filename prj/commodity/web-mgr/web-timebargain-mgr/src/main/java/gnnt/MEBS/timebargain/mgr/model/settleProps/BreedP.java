package gnnt.MEBS.timebargain.mgr.model.settleProps;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Set;

public class BreedP extends StandardModel
{
  private static final long serialVersionUID = 1L;

  @ClassDiscription(name="品名ID", description="")
  private Long breedId;

  @ClassDiscription(name="品名", description="")
  private String breedName;

  @ClassDiscription(name="交易模式", description="1：诚信保障金模式 2：保证金模式")
  private Integer tradeMode;

  @ClassDiscription(name="与商品分类 关联 对应分类号", description="")
  private Category category;

  @ClassDiscription(name="品名单位", description="")
  private String unit;

  @ClassDiscription(name="状态", description="1：正常 2：删除")
  private Integer status;

  @ClassDiscription(name="品名下面的属性集合", description="")
  private Set<BreedProps> propsSet;

  @ClassDiscription(name="图片", description="")
  private byte[] picture;

  @ClassDiscription(name="分类所属模块", description="")
  private String belongModule;

  @ClassDiscription(name="排序号", description="")
  private Integer sortNo;

  public Integer getSortNo()
  {
    return this.sortNo;
  }

  public void setSortNo(Integer sortNo)
  {
    this.sortNo = sortNo;
  }

  public String getBelongModule()
  {
    return this.belongModule;
  }

  public void setBelongModule(String belongModule)
  {
    this.belongModule = belongModule;
  }

  public Integer getTradeMode()
  {
    return this.tradeMode;
  }

  public void setTradeMode(Integer tradeMode)
  {
    this.tradeMode = tradeMode;
  }

  public String getUnit()
  {
    return this.unit;
  }

  public void setUnit(String unit)
  {
    this.unit = unit;
  }

  public Set<BreedProps> getPropsSet()
  {
    return this.propsSet;
  }

  public void setPropsSet(Set<BreedProps> propsSet) {
    this.propsSet = propsSet;
  }

  public Long getBreedId()
  {
    return this.breedId;
  }

  public void setBreedId(Long breedId) {
    this.breedId = breedId;
  }

  public String getBreedName()
  {
    return this.breedName;
  }

  public void setBreedName(String breedName) {
    this.breedName = breedName;
  }

  public Category getCategory()
  {
    return this.category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public byte[] getPicture()
  {
    return this.picture;
  }

  public void setPicture(byte[] picture)
  {
    this.picture = picture;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "breedId", this.breedId);
  }
}