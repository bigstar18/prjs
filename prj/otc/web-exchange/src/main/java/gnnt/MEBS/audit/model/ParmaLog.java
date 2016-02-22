package gnnt.MEBS.audit.model;

import gnnt.MEBS.base.copy.MapToXml;
import gnnt.MEBS.base.copy.ObjectToMap;
import gnnt.MEBS.base.model.Clone;
import java.util.Date;
import java.util.Map;

public class ParmaLog
  extends Clone
{
  private Long id;
  private String operator;
  private String oldOperator;
  private Date operateTime;
  private String operatorType;
  private String mark;
  private String operateContent;
  private String currentValue;
  private String oldValue;
  private String operateGflag;
  private Object obj;
  private Object oldObj;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public String getOperator()
  {
    return this.operator;
  }
  
  public void setOperator(String operator)
  {
    this.operator = operator;
  }
  
  public Date getOperateTime()
  {
    return this.operateTime;
  }
  
  public void setOperateTime(Date operateTime)
  {
    this.operateTime = operateTime;
  }
  
  public String getOperatorType()
  {
    return this.operatorType;
  }
  
  public void setOperatorType(String operatorType)
  {
    this.operatorType = operatorType;
  }
  
  public String getMark()
  {
    return this.mark;
  }
  
  public void setMark(String mark)
  {
    this.mark = mark;
  }
  
  public String getOperateContent()
  {
    return this.operateContent;
  }
  
  public void setOperateContent(String operateContent)
  {
    this.operateContent = operateContent;
  }
  
  public String getCurrentValue()
  {
    return this.currentValue;
  }
  
  public void setCurrentValue(String currentValue)
  {
    this.currentValue = currentValue;
  }
  
  public String getOperateGflag()
  {
    return this.operateGflag;
  }
  
  public void setOperateGflag(String operateGflag)
  {
    this.operateGflag = operateGflag;
  }
  
  public void setPrimary(String primary)
  {
    this.id = Long.valueOf(Long.parseLong(primary));
  }
  
  public void setObj(Object obj)
  {
    if (obj != null)
    {
      Map map = ObjectToMap.getMapFromObjForParam(obj);
      String xml = MapToXml.mapToXml(map);
      this.currentValue = xml;
    }
    this.obj = obj;
  }
  
  public String getOldOperator()
  {
    return this.oldOperator;
  }
  
  public void setOldOperator(String oldOperator)
  {
    this.oldOperator = oldOperator;
  }
  
  public String getOldValue()
  {
    return this.oldValue;
  }
  
  public void setOldValue(String oldValue)
  {
    this.oldValue = oldValue;
  }
  
  public void setOldObj(Object oldObj)
  {
    if (oldObj != null)
    {
      Map map = ObjectToMap.getMapFromObjForParam(oldObj);
      String xml = MapToXml.mapToXml(map);
      this.oldValue = xml;
    }
    this.oldObj = oldObj;
  }
}
