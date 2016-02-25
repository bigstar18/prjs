package gnnt.MEBS.vendue.server.beans.busbeans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PartitionQuotation
  implements Cloneable
{
  private Long partitionId;
  private List quotationDetailList = new ArrayList();
  private StringBuffer partOfPartitionQuotationXML;
  private Timestamp startTime;
  private Timestamp endTime;
  private Long section;
  private Long status;
  private Long curNumOfCommodity;
  private PartitionHighBuffer partitionHighBuffer;
  
  public Long getCurNumOfCommodity()
  {
    return this.curNumOfCommodity;
  }
  
  public void setCurNumOfCommodity(Long paramLong)
  {
    this.curNumOfCommodity = paramLong;
  }
  
  public PartitionHighBuffer getPartitionHighBuffer()
  {
    return this.partitionHighBuffer;
  }
  
  public void setPartitionHighBuffer(PartitionHighBuffer paramPartitionHighBuffer)
  {
    this.partitionHighBuffer = paramPartitionHighBuffer;
  }
  
  public Timestamp getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(Timestamp paramTimestamp)
  {
    this.endTime = paramTimestamp;
  }
  
  public Long getPartitionId()
  {
    return this.partitionId;
  }
  
  public void setPartitionId(Long paramLong)
  {
    this.partitionId = paramLong;
  }
  
  public Timestamp getStartTime()
  {
    return this.startTime;
  }
  
  public void setStartTime(Timestamp paramTimestamp)
  {
    this.startTime = paramTimestamp;
  }
  
  public List getQuotationDetailList()
  {
    return this.quotationDetailList;
  }
  
  public void setQuotationDetailList(List paramList)
  {
    this.quotationDetailList = paramList;
  }
  
  public StringBuffer getPartOfPartitionQuotationXML()
  {
    return this.partOfPartitionQuotationXML;
  }
  
  public void setPartOfPartitionQuotationXML(StringBuffer paramStringBuffer)
  {
    this.partOfPartitionQuotationXML = paramStringBuffer;
  }
  
  public Long getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Long paramLong)
  {
    this.status = paramLong;
  }
  
  public Long getSection()
  {
    return this.section;
  }
  
  public void setSection(Long paramLong)
  {
    this.section = paramLong;
  }
  
  public Object clone()
    throws CloneNotSupportedException
  {
    PartitionQuotation localPartitionQuotation = (PartitionQuotation)super.clone();
    localPartitionQuotation.quotationDetailList = ((ArrayList)((ArrayList)this.quotationDetailList).clone());
    return localPartitionQuotation;
  }
}
