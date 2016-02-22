package gnnt.MEBS.report.assemble;

import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.report.model.CompleteData;
import gnnt.MEBS.report.model.DataTable;
import gnnt.MEBS.report.model.PrimaryTable;
import gnnt.MEBS.report.service.ReportService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AssembleDataForCache
  implements AssembleData
{
  private ReportService primaryReportService;
  private Map<String, ReportService> dataReportServiceMap;
  private String key;
  
  public void setKey(String key)
  {
    this.key = key;
  }
  
  public void setPrimaryReportService(ReportService primaryReportService)
  {
    this.primaryReportService = primaryReportService;
  }
  
  public void setDataReportServiceMap(Map<String, ReportService> dataReportServiceMap)
  {
    this.dataReportServiceMap = dataReportServiceMap;
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
      List<DataTable> dataTableList = new ArrayList();
      for (String table : tables)
      {
        ReportService dataReportService = (ReportService)this.dataReportServiceMap.get(table);
        QueryConditions qcForData = (QueryConditions)qc.clone();
        List conditionDataList = new ArrayList();
        conditionDataList.addAll(qc.getConditionList());
        
        qcForData.setConditionList(conditionDataList);
        DataTable dataTable = dataReportService.getDataObject(qcForData);
        dataTableList.add(dataTable);
      }
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
          for (DataTable dataTable : dataTableList)
          {
            DataTable dataShowTable = new DataTable();
            dataShowTable.setTemplate(dataTable.getTemplate());
            List<Map<String, Object>> dataShowList = new ArrayList();
            Iterator<Map<String, Object>> itr = dataTable.getDataList().iterator();
            while (itr.hasNext())
            {
              Map<String, Object> map = (Map)itr.next();
              String dataKeyValue = (String)map.get(this.key);
              if (keyValue.equals(dataKeyValue))
              {
                dataShowList.add(map);
                itr.remove();
              }
            }
            dataShowTable.setDataList(dataShowList);
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
