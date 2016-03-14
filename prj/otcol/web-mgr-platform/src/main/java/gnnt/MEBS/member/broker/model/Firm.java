package gnnt.MEBS.member.broker.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Firm extends Cloneable {
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
	protected Map<String, String> extendMap = new HashMap();
	protected int type;
	protected String extendData;

	public Map<String, String> getExtendMap() {
		return addToXml(this.extendData);
	}

	public void setExtendData(String paramString) {
		this.extendData = paramString;
	}

	public String getExtendData() {
		if ((this.extendMap != null) && (this.extendMap.size() > 0)) {
			this.extendData = getToXml(this.extendMap);
		}
		return this.extendData;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int paramInt) {
		this.type = paramInt;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp paramTimestamp) {
		this.modifyTime = paramTimestamp;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String paramString) {
		this.note = paramString;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String paramString) {
		this.address = paramString;
	}

	public String getBank() {
		return this.bank;
	}

	public void setBank(String paramString) {
		this.bank = paramString;
	}

	public String getBankAccount() {
		return this.bankAccount;
	}

	public void setBankAccount(String paramString) {
		this.bankAccount = paramString;
	}

	public String getContactMan() {
		return this.contactMan;
	}

	public void setContactMan(String paramString) {
		this.contactMan = paramString;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp paramTimestamp) {
		this.createTime = paramTimestamp;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String paramString) {
		this.email = paramString;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String paramString) {
		this.fax = paramString;
	}

	public String getFirmId() {
		return this.firmId;
	}

	public void setFirmId(String paramString) {
		this.firmId = paramString;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String paramString) {
		this.fullname = paramString;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String paramString) {
		this.phone = paramString;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String paramString) {
		this.postCode = paramString;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String paramString) {
		this.status = paramString;
	}

	public String getIndustryCode() {
		return this.industryCode;
	}

	public void setIndustryCode(String paramString) {
		this.industryCode = paramString;
	}

	public String getZoneCode() {
		return this.zoneCode;
	}

	public void setZoneCode(String paramString) {
		this.zoneCode = paramString;
	}

	public String getToXml(Map<String, String> paramMap) {
		Set localSet = paramMap.entrySet();
		Iterator localIterator = localSet.iterator();
		Document localDocument = DocumentHelper.createDocument();
		localDocument.setXMLEncoding("GBK");
		Element localElement1 = localDocument.addElement("root");
		while (localIterator.hasNext()) {
			Map.Entry localObject = (Map.Entry) localIterator.next();
			Element localElement2 = localElement1.addElement("keyValue");
			Element localElement3 = localElement2.addElement("key");
			localElement3.addCDATA(((Map.Entry) localObject).getKey().toString());
			Element localElement4 = localElement2.addElement("value");
			localElement4.addCDATA(((Map.Entry) localObject).getValue().toString());
		}
		return localDocument.asXML();
	}

	public Map<String, String> addToXml(String paramString) {
		if ((paramString != null) && (!"".equals(paramString))) {
			Document localDocument = null;
			try {
				localDocument = DocumentHelper.parseText(paramString);
			} catch (DocumentException localDocumentException) {
				localDocumentException.printStackTrace();
			}
			Element localElement1 = localDocument.getRootElement();
			Iterator localIterator = localElement1.elementIterator();
			while (localIterator.hasNext()) {
				Element localElement2 = (Element) localIterator.next();
				String str1 = localElement2.element("key").getText();
				String str2 = localElement2.element("value").getText();
				this.extendMap.put(str1, str2);
			}
		}
		return this.extendMap;
	}
}
