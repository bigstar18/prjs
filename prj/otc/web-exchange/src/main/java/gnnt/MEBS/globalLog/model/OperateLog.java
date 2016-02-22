package gnnt.MEBS.globalLog.model;

import gnnt.MEBS.base.copy.MapToXml;
import gnnt.MEBS.base.copy.ObjectToMap;
import gnnt.MEBS.base.model.Clone;
import java.util.Date;
import java.util.Map;

public class OperateLog
  extends Clone
{
  private Object obj;
  private long id;
  private String operator;
  private String operateIp;
  private String operatorType;
  private String mark;
  private Date operateDate;
  private String operateType;
  private int operateLogType;
  private String operateContent;
  private String currentValue;
  
  public Object getObj()
  {
    return this.obj;
  }
  
  public void setObj(Object obj)
  {
    if (obj != null)
    {
      Map map = ObjectToMap.getMapFromObj(obj);
      String xml = MapToXml.mapToXml(map);
      this.currentValue = xml;
    }
    this.obj = obj;
  }
  
  public Long getId()
  {
    return Long.valueOf(this.id);
  }
  
  public void setId(long id)
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
  
  public Date getOperateDate()
  {
    return this.operateDate;
  }
  
  public void setOperateDate(Date operateDate)
  {
    this.operateDate = operateDate;
  }
  
  public String getOperateType()
  {
    return this.operateType;
  }
  
  public void setOperateType(String operateType)
  {
    this.operateType = operateType;
  }
  
  public String getOperateContent()
  {
    String result = "";
    if (this.operateType != null) {
      result = result + this.operateType;
    }
    if (this.operateContent != null) {
      result = result + this.operateContent;
    }
    return result;
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
  
  public String findXml(Object obj)
  {
    String xml = MapToXml.mapToXml(ObjectToMap.getMapFromObj(obj));
    return xml;
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
  
  public void setPrimary(String primary)
  {
    this.id = Long.parseLong(primary);
  }
  
  public String getOperateIp()
  {
    return this.operateIp;
  }
  
  public void setOperateIp(String operateIp)
  {
    this.operateIp = operateIp;
  }
  
  public int getOperateLogType()
  {
    return this.operateLogType;
  }
  
  public void setOperateLogType(int operateLogType)
  {
    this.operateLogType = operateLogType;
  }
}
