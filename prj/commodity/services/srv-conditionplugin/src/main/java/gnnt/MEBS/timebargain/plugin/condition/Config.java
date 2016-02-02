package gnnt.MEBS.timebargain.plugin.condition;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Config
{
  public String DBType;
  public String DBDriver;
  public String DBUrl;
  public String DAO_NAME;
  public String TradeModule;
  public String ConditionModule;
  
  public Config()
  {
    loadConfig();
  }
  
  public void loadConfig()
  {
    SAXReader localSAXReader = new SAXReader();
    try
    {
      Document localDocument = localSAXReader.read("MEBS_plugin.xml");
      Element localElement = localDocument.getRootElement();
      this.DBType = localElement.element("DBTYPE").getText();
      this.DBDriver = localElement.element("DBDRIVER").getText();
      this.DBUrl = localElement.element("DBURL").getText();
      this.DAO_NAME = localElement.element("DAONAME").getText();
      this.ConditionModule = localElement.element("ConditionModule").getText();
      this.TradeModule = localElement.element("TradeModule").getText();
    }
    catch (Exception localException)
    {
      throw new ConfigException("配置信息有误。", localException);
    }
  }
}
