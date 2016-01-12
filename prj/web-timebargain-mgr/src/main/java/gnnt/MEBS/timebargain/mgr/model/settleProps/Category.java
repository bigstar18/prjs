package gnnt.MEBS.timebargain.mgr.model.settleProps;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.math.BigDecimal;
import java.util.Set;

public class Category extends StandardModel
{
  private static final long serialVersionUID = 1L;

  @ClassDiscription(name="商品分类号", description="")
  private Long categoryId;

  @ClassDiscription(name="分类名称 ", description="")
  private String categoryName;

  @ClassDiscription(name="分类说明", description="")
  private String note;

  @ClassDiscription(name="类型", description="breed：品种，category：分类，leaf：叶子节点")
  private String type;

  @ClassDiscription(name="排序号", description="")
  private Integer sortNo;

  @ClassDiscription(name="状态", description="1：正常 2：删除")
  private Integer status;

  @ClassDiscription(name="父分类", description="")
  private Category parentCategory;

  @ClassDiscription(name="子分类", description="")
  private Set<Category> childCategorySet;

  @ClassDiscription(name="分类下的品名", description="")
  private Set<BreedP> breedSet;

  @ClassDiscription(name="商品下的属性值", description="")
  private Set<CategoryProperty> propertySet;

  @ClassDiscription(name="是否允许申请溢短货款", description="")
  private String isOffSet;

  @ClassDiscription(name="溢短货款金额比例", description="")
  private BigDecimal offSetRate;

  @ClassDiscription(name="分类所属模块", description="")
  private String belongModule;

  public String getBelongModule()
  {
    return this.belongModule;
  }

  public void setBelongModule(String belongModule)
  {
    this.belongModule = belongModule;
  }

  public String getIsOffSet()
  {
    return this.isOffSet;
  }

  public void setIsOffSet(String isOffSet)
  {
    this.isOffSet = isOffSet;
  }

  public BigDecimal getOffSetRate()
  {
    return this.offSetRate;
  }

  public void setOffSetRate(BigDecimal offSetRate)
  {
    this.offSetRate = offSetRate;
  }

  public BigDecimal getOffSetRate_v()
  {
    if (this.offSetRate != null) {
      BigDecimal deliveryFeeRate_v = formatDecimals(this.offSetRate.multiply(new BigDecimal(100)), 2);
      return deliveryFeeRate_v;
    }
    return this.offSetRate;
  }

  public void setOffSetRate_v(BigDecimal offSetRate_v)
  {
    this.offSetRate = offSetRate_v.divide(new BigDecimal(100));
  }

  public Set<CategoryProperty> getPropertySet()
  {
    return this.propertySet;
  }

  public void setPropertySet(Set<CategoryProperty> propertySet) {
    this.propertySet = propertySet;
  }

  public Set<BreedP> getBreedSet()
  {
    return this.breedSet;
  }

  public void setBreedSet(Set<BreedP> breedSet)
  {
    this.breedSet = breedSet;
  }

  public Long getCategoryId()
  {
    return this.categoryId;
  }

  public void setCategoryId(Long categoryId)
  {
    this.categoryId = categoryId;
  }

  public String getCategoryName()
  {
    return this.categoryName;
  }

  public void setCategoryName(String categoryName)
  {
    this.categoryName = categoryName;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String note)
  {
    this.note = note;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public Integer getSortNo()
  {
    return this.sortNo;
  }

  public void setSortNo(Integer sortNo)
  {
    this.sortNo = sortNo;
  }

  public Category getParentCategory()
  {
    return this.parentCategory;
  }

  public void setParentCategory(Category parentCategory)
  {
    this.parentCategory = parentCategory;
  }

  public Set<Category> getChildCategorySet()
  {
    return this.childCategorySet;
  }

  public void setChildCategorySet(Set<Category> childCategorySet)
  {
    this.childCategorySet = childCategorySet;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public String findTreeJsons()
  {
    String json = "";
    if (getParentCategory() == null)
      json = "{id:\"" + getCategoryId() + "\"" + ",name:" + "\"" + getCategoryName() + "\"" + ",type:" + "\"" + getType() + "\"" + ",treeType:" + "\"cate\"" + ",pId:" + "\"0\"" + ",children:[";
    else {
      json = "{id:\"" + getCategoryId() + "\"" + ",name:" + "\"" + getCategoryName() + "\"" + ",type:" + "\"" + getType() + "\"" + ",treeType:" + "\"cate\"" + ",pId:" + "\"" + getParentCategory().getCategoryId() + "\"" + ",children:[";
    }
    if ((getChildCategorySet() != null) && (getChildCategorySet().size() > 0)) {
      int i = 0;
      for (Category category : getChildCategorySet()) {
        if (category.getStatus().intValue() == 1) {
          i++;
          String childrenJson = category.findTreeJsons();
          json = json + childrenJson;
        }
      }
      if (i > 0) {
        json = json.substring(0, json.length() - 1);
      }
    }
    json = json + "]},";

    return json;
  }

  public String getJsons(String json)
  {
    if ((json != null) && (!"".equals(json))) {
      json = json.substring(0, json.length() - 1);
    }
    return json;
  }

  public String findBreedTreeJsons()
  {
    String json = "";
    if (getParentCategory() == null) {
      json = "{id:\"" + getCategoryId() + "\"" + ",name:" + "\"" + getCategoryName() + "\"" + ",open:" + "true" + ",type:" + "\"" + getType() + "\"" + ",pId:" + "\"0\"" + ",children:[";
    }
    else if (getType().equals("leaf"))
      json = "{id:\"" + getCategoryId() + "\"" + ",name:" + "\"" + getCategoryName() + "\"" + ",open:" + "false" + ",type:" + "\"" + getType() + "\"" + ",pId:" + "\"" + getParentCategory().getCategoryId() + "\"" + ",children:[";
    else {
      json = "{id:\"" + getCategoryId() + "\"" + ",name:" + "\"" + getCategoryName() + "\"" + ",open:" + "true" + ",type:" + "\"" + getType() + "\"" + ",pId:" + "\"" + getParentCategory().getCategoryId() + "\"" + ",children:[";
    }

    if ((getChildCategorySet() != null) && (getChildCategorySet().size() > 0)) {
      int i = 0;
      for (Category category : getChildCategorySet()) {
        if (category.getStatus().intValue() == 1) {
          i++;
          String childrenJson = category.findBreedTreeJsons();
          json = json + childrenJson;
          if ((category.getBreedSet() != null) && (category.getBreedSet().size() > 0)) {
            int j = 0;
            boolean flag = false;
            for (BreedP breed : category.getBreedSet()) {
              if (breed.getStatus().intValue() == 1) {
                flag = true;
              }
            }
            if (flag) {
              json = json.substring(0, json.length() - 3);
            }
            for (BreedP breed : category.getBreedSet()) {
              if (breed.getStatus().intValue() == 1) {
                j++;
                String breedJson = "{id:\"" + breed.getBreedId() + "\"" + ",name:" + "\"" + "(" + breed.getBreedId() + ")" + breed.getBreedName() + "\"" + ",pId:" + "\"" + category.getCategoryId() + "\"" + ",open:" + "false" + ",type:" + "\"" + "pro" + "\"" + ",children:[]" + "},";
                json = json + breedJson;
              }
            }
            if (j > 0) {
              json = json.substring(0, json.length() - 1);
              json = json + "]},";
            }
          }
        }

      }

      if (i > 0) {
        json = json.substring(0, json.length() - 1);
      }
    }
    json = json + "]},";
    return json;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "categoryId", this.categoryId);
  }

  private BigDecimal formatDecimals(BigDecimal value, int scale) {
    BigDecimal valueFormat = null;
    if (value != null)
      valueFormat = value.setScale(scale, 4);
    return valueFormat;
  }
}