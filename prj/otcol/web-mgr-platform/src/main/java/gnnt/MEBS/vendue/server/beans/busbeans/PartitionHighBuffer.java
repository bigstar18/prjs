package gnnt.MEBS.vendue.server.beans.busbeans;

import java.util.List;
import java.util.Map;

public class PartitionHighBuffer
{
  private List highBufferQuotationIdList = null;
  private Map highBufferQuotationMap = null;
  
  public List getHighBufferQuotationIdList()
  {
    return this.highBufferQuotationIdList;
  }
  
  public void setHighBufferQuotationIdList(List paramList)
  {
    this.highBufferQuotationIdList = paramList;
  }
  
  public Map getHighBufferQuotationMap()
  {
    return this.highBufferQuotationMap;
  }
  
  public void setHighBufferQuotationMap(Map paramMap)
  {
    this.highBufferQuotationMap = paramMap;
  }
}
