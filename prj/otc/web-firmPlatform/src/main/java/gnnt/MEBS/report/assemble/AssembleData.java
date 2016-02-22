package gnnt.MEBS.report.assemble;

import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.report.model.CompleteData;
import java.util.List;

public abstract interface AssembleData
{
  public abstract List<CompleteData> getData(QueryConditions paramQueryConditions, List<String> paramList);
}
