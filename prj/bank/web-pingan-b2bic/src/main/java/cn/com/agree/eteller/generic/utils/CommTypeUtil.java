package cn.com.agree.eteller.generic.utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

public class CommTypeUtil
{
  public static CommonType getCommTypeById(List<CommonType> types, String id)
  {
    return getCommTypeById((CommonType[])types.toArray(new CommonType[0]), id);
  }
  
  public static CommonType getCommTypeById(CommonType[] types, String id)
  {
    CommonType[] arrayOfCommonType = types;int j = types.length;
    for (int i = 0; i < j; i++)
    {
      CommonType ct = arrayOfCommonType[i];
      if (ct.getId().equals(id)) {
        return ct;
      }
    }
    return null;
  }
  
  public static List<CommonType> fromBean2CommonTypes(List<?> beanList, String idProperty, String valueProperty)
    throws Exception
  {
    List<CommonType> list = new ArrayList();
    for (Object bean : beanList)
    {
      String id = BeanUtils.getProperty(bean, idProperty);
      String value = BeanUtils.getProperty(bean, valueProperty);
      list.add(new CommonType(id, value));
    }
    return list;
  }
  
  public static List<CommonType> fromBean2CommonTypesWithFmtVal(List<?> beanList, String idProperty, String valueProperty)
    throws Exception
  {
    return fromBean2CommonTypesWithFmtVal(beanList, idProperty, valueProperty, false);
  }
  
  public static List<CommonType> fromBean2CommonTypesWithFmtVal(List<?> beanList, String idProperty, String valueProperty, boolean emptyOption)
    throws Exception
  {
    List<CommonType> list = new ArrayList();
    if (emptyOption) {
      list.add(new CommonType("", ""));
    }
    for (Object bean : beanList)
    {
      String id = BeanUtils.getProperty(bean, idProperty);
      String value = BeanUtils.getProperty(bean, valueProperty);
      value = id + "-" + value;
      list.add(new CommonType(id, value));
    }
    return list;
  }
  
  public static String getValueById(List<CommonType> commTypeList, String id)
  {
    String name = "";
    if ((commTypeList != null) && (StringUtils.isNotBlank(id))) {
      for (CommonType commonType : commTypeList) {
        if (commonType.getId().equals(id)) {
          name = commonType.getValue();
        }
      }
    }
    return name;
  }
}
