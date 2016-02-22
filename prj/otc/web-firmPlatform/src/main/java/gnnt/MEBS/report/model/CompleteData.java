package gnnt.MEBS.report.model;

import java.util.List;

public class CompleteData
{
  private PrimaryTable primaryTable;
  private List<DataTable> dataTableList;
  
  public PrimaryTable getPrimaryTable()
  {
    return this.primaryTable;
  }
  
  public void setPrimaryTable(PrimaryTable primaryTable)
  {
    this.primaryTable = primaryTable;
  }
  
  public List<DataTable> getDataTableList()
  {
    return this.dataTableList;
  }
  
  public void setDataTableList(List<DataTable> dataTableList)
  {
    this.dataTableList = dataTableList;
  }
}
