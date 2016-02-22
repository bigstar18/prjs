package gnnt.MEBS.report.service;

import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.report.model.DataTable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public abstract interface ReportService
{
  public abstract DataTable getDataObject(QueryConditions paramQueryConditions);
}
