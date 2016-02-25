package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.ToolService;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CheckNoCancel
  implements Filtering
{
  private final transient Log logger = LogFactory.getLog(CheckNoCancel.class);
  private List<String> noCancelPrefixList;
  private static String KEY = "MATCHID";
  @Autowired
  @Qualifier("w_toolService")
  private ToolService toolService;
  
  public void setNoCancelPrefixList(List<String> paramList)
  {
    this.noCancelPrefixList = paramList;
  }
  
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    String str1 = this.toolService.getXmlNode(localSettleMatch.getXml(), KEY);
    if (this.noCancelPrefixList != null)
    {
      Iterator localIterator = this.noCancelPrefixList.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        if (str1.indexOf(str2) >= 0)
        {
          i = -15;
          break;
        }
      }
    }
    return i;
  }
}
