package gnnt.MEBS.report.assemble;

import gnnt.MEBS.base.query.jdbc.Condition;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.report.model.CompleteData;
import gnnt.MEBS.report.model.DataTable;
import gnnt.MEBS.report.model.PrimaryTable;
import gnnt.MEBS.report.service.ReportService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssembleDataForData
  implements AssembleData
{
  private ReportService primaryReportService;
  private String key;
  private Condition condition;
  private Map<String, ReportService> dataReportServiceMap;
  
  public void setDataReportServiceMap(Map<String, ReportService> dataReportServiceMap)
  {
    this.dataReportServiceMap = dataReportServiceMap;
  }
  
  public void setCondition(Condition condition)
  {
    this.condition = condition;
  }
  
  public void setPrimaryReportService(ReportService primaryReportService)
  {
    this.primaryReportService = primaryReportService;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
  
  public List<CompleteData> getData(QueryConditions qc, List<String> tables)
  {
    List<CompleteData> completeDataList = null;
    List<String> keyList = new ArrayList();
    QueryConditions qcForPrimary = (QueryConditions)qc.clone();
    List conditionPrimaryList = new ArrayList();
    conditionPrimaryList.addAll(qc.getConditionList());
    
    qcForPrimary.setConditionList(conditionPrimaryList);
    DataTable primaryDataTable = this.primaryReportService.getDataObject(qcForPrimary);
    List<Map<String, Object>> primaryDataList = primaryDataTable.getDataList();
    if ((primaryDataList != null) && (primaryDataList.size() > 0))
    {
      completeDataList = new ArrayList();
      for (Map<String, Object> primaryMap : primaryDataList)
      {
        CompleteData completeData = new CompleteData();
        PrimaryTable primaryTable = new PrimaryTable();
        primaryTable.setPrimaryMsg(primaryMap);
        primaryTable.setTemplate(primaryDataTable.getTemplate());
        completeData.setPrimaryTable(primaryTable);
        
        boolean sign = false;
        String keyValue = (String)primaryMap.get(this.key);
        if ((keyValue != null) && (!keyList.contains(keyValue)))
        {
          keyList.add(keyValue);
          sign = true;
          




          List<DataTable> dataList = new ArrayList();
          for (String table : tables)
          {
            DataTable dataShowTable = new DataTable();
            
            ReportService dataReportService = (ReportService)this.dataReportServiceMap.get(table);
            QueryConditions qcForData = (QueryConditions)qc.clone();
            List conditionDataList = new ArrayList();
            conditionDataList.addAll(qc.getConditionList());
            this.condition.setValue(keyValue);
            conditionDataList.add(this.condition);
            qcForData.setConditionList(conditionDataList);
            DataTable dataTable = dataReportService.getDataObject(qcForData);
            dataShowTable.setTemplate(dataTable.getTemplate());
            dataShowTable.setDataList(dataTable.getDataList());
            
            dataList.add(dataShowTable);
          }
          completeData.setDataTableList(dataList);
          completeDataList.add(completeData);
        }
      }
    }
    return completeDataList;
  }
}
