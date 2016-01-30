package gnnt.MEBS.timebargain.tradeweb.webapp.util;

import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParser
  extends DefaultHandler
{
  Stack tags = new Stack();
  
  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    String str = (String)this.tags.peek();
    if (str.equals("TRADER_ID")) {
      new String(paramArrayOfChar, paramInt1, paramInt2);
    } else if (str.equals("CUSTOMER_ID")) {
      new String(paramArrayOfChar, paramInt1, paramInt2);
    } else if (str.equals("BUY_SELL")) {
      new String(paramArrayOfChar, paramInt1, paramInt2);
    } else if (str.equals("COMMODITY_ID")) {
      new String(paramArrayOfChar, paramInt1, paramInt2);
    } else if (str.equals("PRICE")) {
      new String(paramArrayOfChar, paramInt1, paramInt2);
    }
  }
  
  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
  {
    this.tags.push(paramString3);
  }
}
