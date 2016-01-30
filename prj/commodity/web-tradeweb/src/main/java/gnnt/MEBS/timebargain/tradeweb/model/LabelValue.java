package gnnt.MEBS.timebargain.tradeweb.model;

import java.io.Serializable;
import java.util.Comparator;

public class LabelValue
  implements Comparable, Serializable
{
  private static final long serialVersionUID = 3690197650654049813L;
  public static final Comparator CASE_INSENSITIVE_ORDER = new Comparator()
  {
    public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
    {
      String str1 = ((LabelValue)paramAnonymousObject1).getLabel();
      String str2 = ((LabelValue)paramAnonymousObject2).getLabel();
      return str1.compareToIgnoreCase(str2);
    }
  };
  private String label = null;
  private String value = null;
  
  public LabelValue() {}
  
  public LabelValue(String paramString1, String paramString2)
  {
    this.label = paramString1;
    this.value = paramString2;
  }
  
  public String getLabel()
  {
    return this.label;
  }
  
  public void setLabel(String paramString)
  {
    this.label = paramString;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String paramString)
  {
    this.value = paramString;
  }
  
  public int compareTo(Object paramObject)
  {
    String str = ((LabelValue)paramObject).getLabel();
    return getLabel().compareTo(str);
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer("LabelValue[");
    localStringBuffer.append(this.label);
    localStringBuffer.append(", ");
    localStringBuffer.append(this.value);
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof LabelValue)) {
      return false;
    }
    LabelValue localLabelValue = (LabelValue)paramObject;
    int i = getValue() == null ? 1 : 0;
    i += (localLabelValue.getValue() == null ? 1 : 0);
    if (i == 2) {
      return true;
    }
    if (i == 1) {
      return false;
    }
    return getValue().equals(localLabelValue.getValue());
  }
  
  public int hashCode()
  {
    return getValue() == null ? 17 : getValue().hashCode();
  }
}
