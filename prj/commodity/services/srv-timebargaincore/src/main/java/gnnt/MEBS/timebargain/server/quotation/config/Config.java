package gnnt.MEBS.timebargain.server.quotation.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Config {
	private final transient Log logger = LogFactory.getLog(Config.class);
	public int timeSpace;
	public int clearSpace;
	public int commodityStyle;
	public static final short COMMODITYSTYLE_FULL = 0;
	public static final short COMMODITYSTYLE_DQANDYQ = 1;
	public static final short COMMODITYSTYLE_TD = 2;
	public int hqEngineStatus;
	public Map sourceMap = new HashMap();
	public Map targetMap = new HashMap();

	public Config() {
		loadConfig();
	}

	public void loadConfig() {
		SAXReader localSAXReader = new SAXReader();
		try {
			Document localDocument = localSAXReader.read("MEBS_quotation.xml");
			Element localElement1 = localDocument.getRootElement();
			Element localElement2 = localElement1.element("TIMESPACE");
			this.timeSpace = Integer.valueOf(localElement2.getText()).intValue();
			Element localElement3 = localElement1.element("CLEARTIME");
			this.clearSpace = Integer.valueOf(localElement3.getText()).intValue();
			Element localElement4 = localElement1.element("COMMODITYSTYLE");
			this.commodityStyle = Integer.valueOf(localElement4.getText()).intValue();
			Element localElement5 = localElement1.element("HQENGINESTATUS");
			this.hqEngineStatus = Integer.valueOf(localElement5.getText()).intValue();
			Element localElement6 = localElement1.element("SOURCE");
			Object localObject1 = localElement6.elementIterator();
			Object localObject3;
			String str1;
			while (((Iterator) localObject1).hasNext()) {
				Element localObject2 = (Element) ((Iterator) localObject1).next();
				localObject3 = ((Element) localObject2).getName();
				str1 = ((Element) localObject2).getText().trim();
				this.sourceMap.put(localObject3, str1);
			}
			localObject1 = localElement1.element("TARGET");
			Object localObject2 = ((Element) localObject1).elementIterator();
			while (((Iterator) localObject2).hasNext()) {
				localObject3 = (Element) ((Iterator) localObject2).next();
				str1 = ((Element) localObject3).getName();
				String str2 = ((Element) localObject3).getText().trim();
				this.targetMap.put(str1, str2);
			}
		} catch (Exception localException) {
			throw new ConfigException("配置信息有误。", localException);
		}
	}

	public static void main(String[] paramArrayOfString) {
		Config localConfig = new Config();
		SAXReader localSAXReader = new SAXReader();
		HashMap localHashMap1 = new HashMap();
		HashMap localHashMap2 = new HashMap();
		try {
			Document localDocument = localSAXReader.read("MEBS_quotation.xml");
			Element localElement1 = localDocument.getRootElement();
			Element localElement2 = localElement1.element("TIMESPACE");
			String str1 = localElement2.getText();
			Element localElement3 = localElement1.element("SOURCE");
			Object localObject1 = localElement3.elementIterator();
			Object localObject3;
			String str2;
			while (((Iterator) localObject1).hasNext()) {
				Element localObject2 = (Element) ((Iterator) localObject1).next();
				localObject3 = ((Element) localObject2).getName();
				System.out.println((String) localObject3);
				str2 = ((Element) localObject2).getText().trim();
				localHashMap1.put(localObject3, str2);
			}
			localObject1 = localElement1.element("TARGET");
			Object localObject2 = ((Element) localObject1).elementIterator();
			while (((Iterator) localObject2).hasNext()) {
				localObject3 = (Element) ((Iterator) localObject2).next();
				str2 = ((Element) localObject3).getName();
				System.out.println(str2);
				String str3 = ((Element) localObject3).getText().trim();
				localHashMap2.put(str2, str3);
			}
			System.out.println(localHashMap1.get("DBTYPE"));
			System.out.println(localHashMap1.get("DBDriver"));
			System.out.println(localHashMap1.get("DBURL"));
			System.out.print(localHashMap2.get("DBTYPE"));
			System.out.print(localHashMap2.get("DBDriver"));
			System.out.print(localHashMap2.get("DBURL"));
		} catch (DocumentException localDocumentException) {
			localDocumentException.printStackTrace();
		}
	}
}
