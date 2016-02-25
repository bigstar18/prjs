package gnnt.MEBS.member.firm.unit;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Firm
  extends Clone
{
  protected String firmId;
  protected String name;
  protected String status;
  protected String fullname;
  protected String bank;
  protected String bankAccount;
  protected String address;
  protected String contactMan;
  protected String phone;
  protected String fax;
  protected String postCode;
  protected String email;
  protected Date createTime;
  protected String note;
  protected Date modifyTime;
  protected String zoneCode;
  protected String industryCode;
  protected String firmCategoryId;
  protected String brokerId;
  protected Map<String, String> extendMap = new HashMap();
  protected int type;
  protected List<FirmModule> firmModule;
  protected String extendData;
  private String tariffID = "none";
  private String tariffName;
  
  public String getFirmCategoryId()
  {
    return this.firmCategoryId;
  }
  
  public void setFirmCategoryId(String firmCategoryId)
  {
    this.firmCategoryId = firmCategoryId;
  }
  
  public Map<String, String> getExtendMap()
  {
    return addToXml(this.extendData);
  }
  
  public void setExtendData(String extendData)
  {
    this.extendData = extendData;
  }
  
  public String getExtendData()
  {
    if ((this.extendMap != null) && (this.extendMap.size() > 0)) {
      this.extendData = getToXml(this.extendMap);
    }
    return this.extendData;
  }
  
  public List<FirmModule> getFirmModule()
  {
    return this.firmModule;
  }
  
  public void addFirmModule(List<FirmModule> firmModule)
  {
    this.firmModule = firmModule;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int type)
  {
    this.type = type;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  public String getBank()
  {
    return this.bank;
  }
  
  public void setBank(String bank)
  {
    this.bank = bank;
  }
  
  public String getBankAccount()
  {
    return this.bankAccount;
  }
  
  public void setBankAccount(String bankAccount)
  {
    this.bankAccount = bankAccount;
  }
  
  public String getContactMan()
  {
    return this.contactMan;
  }
  
  public void setContactMan(String contactMan)
  {
    this.contactMan = contactMan;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public String getFax()
  {
    return this.fax;
  }
  
  public void setFax(String fax)
  {
    this.fax = fax;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public String getFullname()
  {
    return this.fullname;
  }
  
  public void setFullname(String fullname)
  {
    this.fullname = fullname;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  
  public String getPostCode()
  {
    return this.postCode;
  }
  
  public void setPostCode(String postCode)
  {
    this.postCode = postCode;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getIndustryCode()
  {
    return this.industryCode;
  }
  
  public void setIndustryCode(String industryCode)
  {
    this.industryCode = industryCode;
  }
  
  public String getZoneCode()
  {
    return this.zoneCode;
  }
  
  public void setZoneCode(String zoneCode)
  {
    this.zoneCode = zoneCode;
  }
  
  public String getTariffID()
  {
    return this.tariffID;
  }
  
  public void setTariffID(String tariffID)
  {
    this.tariffID = tariffID;
  }
  
  public String getTariffName()
  {
    return this.tariffName;
  }
  
  public void setTariffName(String tariffName)
  {
    this.tariffName = tariffName;
  }
  
  public String getBrokerId()
  {
    return this.brokerId;
  }
  
  public void setBrokerId(String brokerId)
  {
    this.brokerId = brokerId;
  }
  
  public String getToXml(Map<String, String> extendMap)
  {
    Set set = extendMap.entrySet();
    Iterator i = set.iterator();
    Document document = DocumentHelper.createDocument();
    document.setXMLEncoding("GBK");
    Element booksElement = document.addElement("root");
    while (i.hasNext())
    {
      Map.Entry me = (Map.Entry)i.next();
      Element oElement = booksElement.addElement("keyValue");
      Element oElement1 = oElement.addElement("key");
      oElement1.addCDATA(me.getKey().toString());
      Element oElement2 = oElement.addElement("value");
      oElement2.addCDATA(me.getValue().toString());
    }
    String result = document.asXML();
    return result;
  }
  
  public Map<String, String> addToXml(String xml)
  {
    if ((xml != null) && (!"".equals(xml)))
    {
      Document doc = null;
      try
      {
        doc = DocumentHelper.parseText(xml);
      }
      catch (DocumentException e)
      {
        e.printStackTrace();
      }
      Element root = doc.getRootElement();
      Iterator i = root.elementIterator();
      while (i.hasNext())
      {
        Element e = (Element)i.next();
        String key = e.element("key").getText();
        String value = e.element("value").getText();
        this.extendMap.put(key, value);
      }
    }
    return this.extendMap;
  }
}
