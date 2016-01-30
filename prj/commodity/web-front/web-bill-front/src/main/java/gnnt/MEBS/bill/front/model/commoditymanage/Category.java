package gnnt.MEBS.bill.front.model.commoditymanage;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;

public class Category
  extends StandardModel
{
  private static final long serialVersionUID = 1L;
  @ClassDiscription(name="商品分类号", description="")
  private Long categoryId;
  @ClassDiscription(name="分类名称", description="")
  private String categoryName;
  @ClassDiscription(name="分类说明", description="")
  private String note;
  @ClassDiscription(name="类型", description="")
  private String type;
  @ClassDiscription(name="排序号", description="")
  private Long sortNo;
  @ClassDiscription(name="状态", description="状态1：正常 2：已删除")
  private Integer status;
  @ClassDiscription(name="父分类", description="")
  private Category parentCategory;
  @ClassDiscription(name="子分类", description="")
  private Set<Category> childCategorySet;
  @ClassDiscription(name="分类下的品名", description="")
  private Set<Breed> breedSet;
  @ClassDiscription(name="分类下的属性值", description="")
  private Set<CategoryProperty> propertySet;
  @ClassDiscription(name="是否允许申请溢短货款", description="Y:此品种可能出现损溢 N:此品种不可能出现损溢")
  private String isOffSet;
  @ClassDiscription(name="溢短货款比例", description="")
  private BigDecimal offSetRate;
  @ClassDiscription(name="所属系统模块号", description="")
  private String belongModule;
  
  public String getBelongModule()
  {
    return this.belongModule;
  }
  
  public void setBelongModule(String paramString)
  {
    this.belongModule = paramString;
  }
  
  public String getIsOffSet()
  {
    return this.isOffSet;
  }
  
  public void setIsOffSet(String paramString)
  {
    this.isOffSet = paramString;
  }
  
  public BigDecimal getOffSetRate()
  {
    return this.offSetRate;
  }
  
  public void setOffSetRate(BigDecimal paramBigDecimal)
  {
    this.offSetRate = paramBigDecimal;
  }
  
  public BigDecimal getOffSetRate_v()
  {
    if (this.offSetRate != null)
    {
      BigDecimal localBigDecimal = formatDecimals(this.offSetRate.multiply(new BigDecimal(100)), 2);
      return localBigDecimal;
    }
    return this.offSetRate;
  }
  
  public void setOffSetRate_v(BigDecimal paramBigDecimal)
  {
    this.offSetRate = paramBigDecimal.divide(new BigDecimal(100));
  }
  
  public Set<CategoryProperty> getPropertySet()
  {
    return this.propertySet;
  }
  
  public void setPropertySet(Set<CategoryProperty> paramSet)
  {
    this.propertySet = paramSet;
  }
  
  public Set<Breed> getBreedSet()
  {
    return this.breedSet;
  }
  
  public void setBreedSet(Set<Breed> paramSet)
  {
    this.breedSet = paramSet;
  }
  
  public Long getCategoryId()
  {
    return this.categoryId;
  }
  
  public void setCategoryId(Long paramLong)
  {
    this.categoryId = paramLong;
  }
  
  public String getCategoryName()
  {
    return this.categoryName;
  }
  
  public void setCategoryName(String paramString)
  {
    this.categoryName = paramString;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String paramString)
  {
    this.note = paramString;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public Long getSortNo()
  {
    return this.sortNo;
  }
  
  public void setSortNo(Long paramLong)
  {
    this.sortNo = paramLong;
  }
  
  public Category getParentCategory()
  {
    return this.parentCategory;
  }
  
  public void setParentCategory(Category paramCategory)
  {
    this.parentCategory = paramCategory;
  }
  
  public Set<Category> getChildCategorySet()
  {
    return this.childCategorySet;
  }
  
  public void setChildCategorySet(Set<Category> paramSet)
  {
    this.childCategorySet = paramSet;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer paramInteger)
  {
    this.status = paramInteger;
  }
  
  public String findTreeJsons()
  {
    String str1 = "";
    if (getParentCategory() == null) {
      str1 = "{id:\"" + getCategoryId() + "\"" + ",name:" + "\"" + getCategoryName() + "\"" + ",type:" + "\"" + getType() + "\"" + ",treeType:" + "\"cate\"" + ",pId:" + "\"0\"" + ",children:[";
    } else {
      str1 = "{id:\"" + getCategoryId() + "\"" + ",name:" + "\"" + getCategoryName() + "\"" + ",type:" + "\"" + getType() + "\"" + ",treeType:" + "\"cate\"" + ",pId:" + "\"" + getParentCategory().getCategoryId() + "\"" + ",children:[";
    }
    if ((getChildCategorySet() != null) && (getChildCategorySet().size() > 0))
    {
      int i = 0;
      Iterator localIterator = getChildCategorySet().iterator();
      while (localIterator.hasNext())
      {
        Category localCategory = (Category)localIterator.next();
        if (localCategory.getStatus().intValue() == 1)
        {
          i++;
          String str2 = localCategory.findTreeJsons();
          str1 = str1 + str2;
        }
      }
      if (i > 0) {
        str1 = str1.substring(0, str1.length() - 1);
      }
    }
    str1 = str1 + "]},";
    return str1;
  }
  
  public String getJsons(String paramString)
  {
    if ((paramString != null) && (!"".equals(paramString))) {
      paramString = paramString.substring(0, paramString.length() - 1);
    }
    return paramString;
  }
  
  public String findBreedTreeJsons()
  {
    String str1 = "";
    if (getParentCategory() == null) {
      str1 = "{id:\"" + getCategoryId() + "\"" + ",name:" + "\"" + getCategoryName() + "\"" + ",open:" + "true" + ",type:" + "\"" + getType() + "\"" + ",pId:" + "\"0\"" + ",children:[";
    } else if (getType().equals("leaf")) {
      str1 = "{id:\"" + getCategoryId() + "\"" + ",name:" + "\"" + getCategoryName() + "\"" + ",open:" + "false" + ",type:" + "\"" + getType() + "\"" + ",pId:" + "\"" + getParentCategory().getCategoryId() + "\"" + ",children:[";
    } else {
      str1 = "{id:\"" + getCategoryId() + "\"" + ",name:" + "\"" + getCategoryName() + "\"" + ",open:" + "true" + ",type:" + "\"" + getType() + "\"" + ",pId:" + "\"" + getParentCategory().getCategoryId() + "\"" + ",children:[";
    }
    if ((getChildCategorySet() != null) && (getChildCategorySet().size() > 0))
    {
      int i = 0;
      Iterator localIterator1 = getChildCategorySet().iterator();
      while (localIterator1.hasNext())
      {
        Category localCategory = (Category)localIterator1.next();
        if (localCategory.getStatus().intValue() == 1)
        {
          i++;
          String str2 = localCategory.findBreedTreeJsons();
          str1 = str1 + str2;
          if ((localCategory.getBreedSet() != null) && (localCategory.getBreedSet().size() > 0))
          {
            int j = 0;
            int k = 0;
            Iterator localIterator2 = localCategory.getBreedSet().iterator();
            Breed localBreed;
            while (localIterator2.hasNext())
            {
              localBreed = (Breed)localIterator2.next();
              if (localBreed.getStatus().intValue() == 1) {
                k = 1;
              }
            }
            if (k != 0) {
              str1 = str1.substring(0, str1.length() - 3);
            }
            localIterator2 = localCategory.getBreedSet().iterator();
            while (localIterator2.hasNext())
            {
              localBreed = (Breed)localIterator2.next();
              if (localBreed.getStatus().intValue() == 1)
              {
                j++;
                String str3 = "{id:\"" + localBreed.getBreedId() + "\"" + ",name:" + "\"" + "(" + localBreed.getBreedId() + ")" + localBreed.getBreedName() + "\"" + ",pId:" + "\"" + localCategory.getCategoryId() + "\"" + ",open:" + "false" + ",type:" + "\"" + "pro" + "\"" + ",children:[]" + "},";
                str1 = str1 + str3;
              }
            }
            if (j > 0)
            {
              str1 = str1.substring(0, str1.length() - 1);
              str1 = str1 + "]},";
            }
          }
        }
      }
      if (i > 0) {
        str1 = str1.substring(0, str1.length() - 1);
      }
    }
    str1 = str1 + "]},";
    return str1;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("categoryId", this.categoryId);
  }
  
  private BigDecimal formatDecimals(BigDecimal paramBigDecimal, int paramInt)
  {
    BigDecimal localBigDecimal = null;
    if (paramBigDecimal != null) {
      localBigDecimal = paramBigDecimal.setScale(paramInt, 4);
    }
    return localBigDecimal;
  }
}
