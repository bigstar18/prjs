package gnnt.MEBS.delivery.command.settleBehavior.behavior;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Behaviour;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.MoneyDoService;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ReturnMoneyForDelivery_B
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(Payout.class);
  private MoneyDoService moneyDoService;
  
  public void deal(SettleObject paramSettleObject)
  {
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    System.out.println(localSettleMatch.getXml());
    String str = localSettleMatch.getXml();
    Double localDouble = null;
    try
    {
      Document localDocument = DocumentHelper.parseText(str);
      Element localElement1 = localDocument.getRootElement();
      List localList = localElement1.elements("MONEY");
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Element localElement2 = (Element)localIterator.next();
        localDouble = Double.valueOf(Double.parseDouble(localElement2.element("TRADEPOUNDAGE_B").getText()));
      }
    }
    catch (DocumentException localDocumentException)
    {
      localDocumentException.printStackTrace();
    }
    this.moneyDoService.updateFirmFunds(localSettleMatch.getFirmID_B(), "308", 0.0D - localDouble.doubleValue(), localSettleMatch.getContractId() + "", null);
  }
  
  public void setMoneyDoService(MoneyDoService paramMoneyDoService)
  {
    this.moneyDoService = paramMoneyDoService;
  }
}
