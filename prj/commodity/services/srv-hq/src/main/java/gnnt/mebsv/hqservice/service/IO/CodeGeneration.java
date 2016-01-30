package gnnt.mebsv.hqservice.service.IO;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.util.Configuration;
import gnnt.mebsv.hqservice.hq.QuotationServer;
import gnnt.mebsv.hqservice.model.MarketInfoVO;
import gnnt.mebsv.hqservice.model.ProductInfoVO;
import gnnt.mebsv.hqservice.model.pojo.MarketVO;
import gnnt.mebsv.hqservice.service.ResponderX;
import gnnt.mebsv.hqservice.service.ServiceManagerX;
import gnnt.mebsv.hqservice.tools.HQServiceUtil;

public class CodeGeneration extends ResponderX {
	private Log log = LogFactory.getLog(CodeGeneration.class);
	ServiceManagerX manager = getManager();
	QuotationServer quotation;
	public static Date productlastModifyDate = new Date(0L);
	Properties props;

	public void statusChangeInit(QuotationServer paramQuotationServer) throws IOException {
		this.log.debug("交易节切换，重新生成码表....");
		ProductInfoVO[] arrayOfProductInfoVO = this.quotation.getAllProductInfo();
		productlastModifyDate = new Date(0L);
		init(paramQuotationServer);
	}

	public void init(QuotationServer paramQuotationServer) throws IOException {
		this.quotation = paramQuotationServer;
		ProductInfoVO[] arrayOfProductInfoVO = paramQuotationServer.getAllProductInfo();
		if (arrayOfProductInfoVO == null)
			arrayOfProductInfoVO = new ProductInfoVO[0];
		Date localDate = new Date(0L);
		for (int i = 0; i < arrayOfProductInfoVO.length; i++)
			if (!localDate.after(arrayOfProductInfoVO[i].modifyTime))
				localDate = arrayOfProductInfoVO[i].modifyTime;
		if (productlastModifyDate.before(localDate)) {
			this.log.debug(new Date().toLocaleString() + " make productinfo file " + productlastModifyDate.toLocaleString() + "-->"
					+ localDate.toLocaleString());
			productlastModifyDate = localDate;
			this.props = new Configuration().getSection("MEBS.HQService");
			codeGeneration("productinfo.dat", arrayOfProductInfoVO);
			codeGeneration("marketinfo.dat", paramQuotationServer.getMarketInfo());
			this.log.debug("create co_class file..");
			ArrayList localArrayList = null;
			try {
				localArrayList = QuotationServer.getCurDataDAO().getCo_class();
			} catch (SQLException localSQLException) {
				localSQLException.printStackTrace();
			}
			CreateXMLClass localCreateXMLClass = new CreateXMLClass();
			localCreateXMLClass.CreateXml(this.props.getProperty("CodeFileSavePath"), localArrayList);
			this.log.debug("create co_class file finish...");
		}
	}

	public boolean codeGeneration(String paramString, Object paramObject) throws IOException {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(localByteArrayOutputStream);
		DataOutputStream localDataOutputStream = new DataOutputStream(localGZIPOutputStream);
		if ((paramString != null) && (paramString.toUpperCase().startsWith("PRODUCTINFO")))
			writeProductInfoList(localDataOutputStream, (ProductInfoVO[]) paramObject, productlastModifyDate);
		else if ((paramString != null) && (paramString.toUpperCase().startsWith("MARKETINFO")))
			writeMarketInfoList(localDataOutputStream, (MarketInfoVO) paramObject, productlastModifyDate);
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localGZIPOutputStream.flush();
		localGZIPOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();
		File localFile = new File(this.props.getProperty("CodeFileSavePath") + paramString);
		if (localFile.exists())
			localFile.delete();
		FileOutputStream localFileOutputStream = new FileOutputStream(this.props.getProperty("CodeFileSavePath") + paramString);
		localFileOutputStream.write(localByteArrayOutputStream.toByteArray());
		localFileOutputStream.flush();
		localFileOutputStream.close();
		localByteArrayOutputStream.reset();
		return true;
	}

	public byte[] getProductInfoList(int paramInt1, int paramInt2) throws IOException {
		Date localDate = HQServiceUtil.getDate(paramInt1, paramInt2);
		Vector localVector = new Vector();
		if (localDate.before(productlastModifyDate)) {
			ProductInfoVO[] arrayOfProductInfoVO = this.quotation.getAllProductInfo();
			for (int i = 0; i < arrayOfProductInfoVO.length; i++)
				if (localDate.before(arrayOfProductInfoVO[i].modifyTime))
					localVector.add(arrayOfProductInfoVO[i]);
		}
		ProductInfoVO[] arrayOfProductInfoVO = (ProductInfoVO[]) localVector.toArray(new ProductInfoVO[localVector.size()]);
		if (arrayOfProductInfoVO == null)
			arrayOfProductInfoVO = new ProductInfoVO[0];
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		writeProductInfoList(localDataOutputStream, arrayOfProductInfoVO, productlastModifyDate);
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}

	private void writeProductInfoList(DataOutputStream paramDataOutputStream, ProductInfoVO[] paramArrayOfProductInfoVO, Date paramDate)
			throws IOException {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		paramDataOutputStream.writeByte(3);
		paramDataOutputStream.writeInt(localCalendar.get(1) * 10000 + (localCalendar.get(2) + 1) * 100 + localCalendar.get(5));
		paramDataOutputStream.writeInt(localCalendar.get(11) * 10000 + localCalendar.get(12) * 100 + localCalendar.get(13));
		paramDataOutputStream.writeInt(paramArrayOfProductInfoVO.length);
		for (int i = 0; i < paramArrayOfProductInfoVO.length; i++) {
			paramDataOutputStream.writeUTF(paramArrayOfProductInfoVO[i].code);
			paramDataOutputStream.writeUTF(paramArrayOfProductInfoVO[i].marketID == null ? "00" : paramArrayOfProductInfoVO[i].marketID);
			paramDataOutputStream.writeFloat(paramArrayOfProductInfoVO[i].fUnit);
			paramDataOutputStream.writeUTF(paramArrayOfProductInfoVO[i].name == null ? "" : paramArrayOfProductInfoVO[i].name);
			paramDataOutputStream.writeInt(paramArrayOfProductInfoVO[i].status);
			paramDataOutputStream.writeUTF(paramArrayOfProductInfoVO[i].industryCode);
			paramDataOutputStream.writeUTF(paramArrayOfProductInfoVO[i].addrCode);
			if (paramArrayOfProductInfoVO[i].pyName == null)
				paramArrayOfProductInfoVO[i].pyName = new String[0];
			paramDataOutputStream.writeInt(paramArrayOfProductInfoVO[i].pyName.length);
			for (int j = 0; j < paramArrayOfProductInfoVO[i].pyName.length; j++)
				paramDataOutputStream.writeUTF(paramArrayOfProductInfoVO[i].pyName[j]);
			paramDataOutputStream.writeInt(paramArrayOfProductInfoVO[i].tradeSecNo.length);
			this.log.debug("writeProductInfoList: list[i].code:" + paramArrayOfProductInfoVO[i].code + "list[i].tradeSecNo.length:"
					+ paramArrayOfProductInfoVO[i].tradeSecNo.length);
			for (int j = 0; j < paramArrayOfProductInfoVO[i].tradeSecNo.length; j++)
				paramDataOutputStream.writeInt(paramArrayOfProductInfoVO[i].tradeSecNo[j]);
			paramDataOutputStream.writeFloat(paramArrayOfProductInfoVO[i].spread);
		}
	}

	private void writeMarketInfoList(DataOutputStream paramDataOutputStream, MarketInfoVO paramMarketInfoVO, Date paramDate) throws IOException {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		paramDataOutputStream.writeByte(17);
		paramDataOutputStream.writeInt(paramMarketInfoVO.getMarketInfo().size());
		Set localSet = paramMarketInfoVO.getMarketInfo().entrySet();
		Iterator localIterator = localSet.iterator();
		while (localIterator.hasNext()) {
			Map.Entry localEntry = (Map.Entry) localIterator.next();
			MarketVO localMarketVO = (MarketVO) localEntry.getValue();
			paramDataOutputStream.writeUTF(localMarketVO.getMarketID());
			paramDataOutputStream.writeUTF(localMarketVO.getMarketName());
		}
	}

	public static boolean mkdir(String paramString) {
		File localFile = new File(paramString);
		if (!localFile.exists())
			return localFile.mkdirs();
		return true;
	}

	public void run() {
		try {
			long l = 0L;
			while (true) {
				if (System.currentTimeMillis() - l >= 10000L) {
					init(this.quotation);
					l = System.currentTimeMillis();
				}
				Thread.sleep(2000L);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}
}