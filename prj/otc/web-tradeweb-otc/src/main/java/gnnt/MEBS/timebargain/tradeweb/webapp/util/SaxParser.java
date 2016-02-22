package gnnt.MEBS.timebargain.tradeweb.webapp.util;

import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParser
  extends DefaultHandler
{
  Stack tags = new Stack();
  
  public void characters(char[] ch, int start, int length)
    throws SAXException
  {
    String tag = (String)this.tags.peek();
    if (tag.equals("TRADER_ID")) {
      new String(ch, start, length);
    } else if (tag.equals("CUSTOMER_ID")) {
      new String(ch, start, length);
    } else if (tag.equals("BUY_SELL")) {
      new String(ch, start, length);
    } else if (tag.equals("COMMODITY_ID")) {
      new String(ch, start, length);
    } else if (tag.equals("PRICE")) {
      new String(ch, start, length);
    }
  }
  
  public void startElement(String uri, String localName, String qName, Attributes attrs)
  {
    this.tags.push(qName);
  }
}
