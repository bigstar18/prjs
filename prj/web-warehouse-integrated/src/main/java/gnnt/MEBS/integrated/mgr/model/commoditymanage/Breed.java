package gnnt.MEBS.integrated.mgr.model.commoditymanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Set;

public class Breed
  extends StandardModel
{
  private static final long serialVersionUID = 1L;
  @ClassDiscription(name="品名ID", description="")
  private Long breedId;
  @ClassDiscription(name="品名", description="")
  private String breedName;
  @ClassDiscription(name="交易模式", description="交易模式 1：诚信保障金模式 2：保证金模式")
  private Integer tradeMode;
  @ClassDiscription(name="品名关联商品分类", description="关联对应的商品分类号")
  private Category category;
  @ClassDiscription(name="品名单位", description="")
  private String unit;
  @ClassDiscription(name="状态", description="状态：1：正常 2：已删除")
  private Integer status;
  @ClassDiscription(name="品名下的属性集合", description="")
  private Set<BreedProps> propsSet;
  @ClassDiscription(name="图片", description="")
  private byte[] picture;
  @ClassDiscription(name="所属系统模块", description="")
  private String belongModule;
  @ClassDiscription(name="品名排序号", description="")
  private Long sortNo;
  
  public Long getSortNo()
  {
    return this.sortNo;
  }
  
  public void setSortNo(Long paramLong)
  {
    this.sortNo = paramLong;
  }
  
  public String getBelongModule()
  {
    return this.belongModule;
  }
  
  public void setBelongModule(String paramString)
  {
    this.belongModule = paramString;
  }
  
  public Integer getTradeMode()
  {
    return this.tradeMode;
  }
  
  public void setTradeMode(Integer paramInteger)
  {
    this.tradeMode = paramInteger;
  }
  
  public String getUnit()
  {
    return this.unit;
  }
  
  public void setUnit(String paramString)
  {
    this.unit = paramString;
  }
  
  public Set<BreedProps> getPropsSet()
  {
    return this.propsSet;
  }
  
  public void setPropsSet(Set<BreedProps> paramSet)
  {
    this.propsSet = paramSet;
  }
  
  public Long getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(Long paramLong)
  {
    this.breedId = paramLong;
  }
  
  public String getBreedName()
  {
    return this.breedName;
  }
  
  public void setBreedName(String paramString)
  {
    this.breedName = paramString;
  }
  
  public Category getCategory()
  {
    return this.category;
  }
  
  public void setCategory(Category paramCategory)
  {
    this.category = paramCategory;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer paramInteger)
  {
    this.status = paramInteger;
  }
  
  public byte[] getPicture()
  {
    return this.picture;
  }
  
  public void setPicture(byte[] paramArrayOfByte)
  {
    this.picture = paramArrayOfByte;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("breedId", this.breedId);
  }
}
