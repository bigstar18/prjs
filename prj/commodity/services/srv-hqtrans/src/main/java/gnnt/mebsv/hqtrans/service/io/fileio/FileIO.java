package gnnt.mebsv.hqtrans.service.io.fileio;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.zip.GZIPOutputStream;

import gnnt.mebsv.hqtrans.dao.HQTransDAO;
import gnnt.mebsv.hqtrans.model.DayDataFile;
import gnnt.mebsv.hqtrans.model.FileParameter;
import gnnt.mebsv.hqtrans.tools.GetParameter;

public class FileIO {
	private GetParameter getParameter;
	private FileParameter fileParameter;
	private HQTransDAO dao = null;

	public FileParameter getFileParameter() {
		return this.fileParameter;
	}

	public boolean init(HQTransDAO paramHQTransDAO) {
		this.getParameter = new GetParameter();
		this.fileParameter = new FileParameter();
		if (!this.getParameter.getParameter(this.fileParameter)) {
			return false;
		}
		try {
			this.dao = paramHQTransDAO;
		} catch (Exception localException) {
			return false;
		}
		return true;
	}

	public void createKLineFile(String paramString1, String paramString2) throws IOException {
		HashMap localHashMap = new HashMap();
		try {
			localHashMap = this.dao.getAllProductCode(paramString2);
		} catch (Exception localException) {
			localException.printStackTrace();
			return;
		}
		if (localHashMap.size() == 0) {
			return;
		}
		int i = 0;
		Properties localProperties = new Properties();
		try {
			FileInputStream localFileInputStream = new FileInputStream(
					this.fileParameter.getM_strLocalCache() + "/CreateFile" + paramString2 + ".log");
			localProperties.load(localFileInputStream);
			localFileInputStream.close();
			String localObject1 = localProperties.getProperty(paramString1);
			if (localObject1 != null) {
				i = Integer.parseInt((String) localObject1);
			}
		} catch (FileNotFoundException localFileNotFoundException) {
			mkdir(this.fileParameter.getM_strLocalCache());
			File localObject1 = new File(this.fileParameter.getM_strLocalCache() + "/CreateFile" + paramString2 + ".log");
			((File) localObject1).createNewFile();
		}
		Set localSet = localHashMap.keySet();
		Object localObject1 = localSet.iterator();
		while (((Iterator) localObject1).hasNext()) {
			String localObject2 = (String) ((Iterator) localObject1).next();
			List localObject3 = (List) localHashMap.get(localObject2);
			if (localObject3 != null) {
				String[] arrayOfString = (String[]) ((List) localObject3).toArray(new String[((List) localObject3).size()]);
				for (int j = 0; j < arrayOfString.length; j++) {
					createKLineFile((String) localObject2, arrayOfString[j], i, paramString1);
				}
			}
		}
		Object localObject2 = Calendar.getInstance();
		localProperties.setProperty(paramString1,
				((Calendar) localObject2).get(1) * 10000 + (((Calendar) localObject2).get(2) + 1) * 100 + ((Calendar) localObject2).get(5) + "");
		localProperties.setProperty("CreateKLineFlag", "1");
		Object localObject3 = new FileOutputStream(this.fileParameter.getM_strLocalCache() + "/CreateFile" + paramString2 + ".log");
		localProperties.store((OutputStream) localObject3, "");
		((FileOutputStream) localObject3).close();
	}

	public static boolean mkdir(String paramString) {
		File localFile = new File(paramString);
		if (!localFile.exists()) {
			return localFile.mkdirs();
		}
		return true;
	}

	public void createKLineFile(String paramString1, String paramString2, int paramInt, String paramString3) throws IOException {
		DayDataFile[] arrayOfDayDataFile = new DayDataFile[0];
		if (paramString3.equals("day")) {
			try {
				arrayOfDayDataFile = this.dao.getDayData(paramString1, paramString2, paramInt);
			} catch (Exception localException1) {
				localException1.printStackTrace();
			}
		} else if (paramString3.equals("5min")) {
			try {
				arrayOfDayDataFile = this.dao.getMinData(paramString1, paramString2, paramInt, 5);
			} catch (Exception localException2) {
				localException2.printStackTrace();
			}
		} else if (paramString3.equals("min")) {
			try {
				arrayOfDayDataFile = this.dao.getMinData(paramString1, paramString2, paramInt, 1);
			} catch (Exception localException3) {
				localException3.printStackTrace();
			}
		}
		if (arrayOfDayDataFile.length == 0) {
			return;
		}
		long l = arrayOfDayDataFile[0].date;
		if (paramString3.contains("min")) {
			l = l / 10000L * 10000L;
		}
		mkdir(this.fileParameter.getM_strLocalCache() + "/" + paramString3);
		File localFile = new File(
				this.fileParameter.getM_strLocalCache() + "/" + paramString3 + "/" + paramString1.trim() + paramString2.trim() + "." + paramString3);
		Vector localVector = new Vector();
		if (localFile.exists()) {
			FileInputStream localObject1 = new FileInputStream(localFile);
			DataInputStream localObject2 = new DataInputStream(new BufferedInputStream((InputStream) localObject1));
			int i = ((DataInputStream) localObject2).readInt();
			for (int j = 0; j < i; j++) {
				DayDataFile localObject4 = new DayDataFile();
				((DayDataFile) localObject4).date = ((DataInputStream) localObject2).readInt();
				if (((DayDataFile) localObject4).date >= l) {
					break;
				}
				((DayDataFile) localObject4).openPrice = ((DataInputStream) localObject2).readFloat();
				((DayDataFile) localObject4).highPrice = ((DataInputStream) localObject2).readFloat();
				((DayDataFile) localObject4).lowPrice = ((DataInputStream) localObject2).readFloat();
				((DayDataFile) localObject4).closePrice = ((DataInputStream) localObject2).readFloat();
				((DayDataFile) localObject4).balancePrice = ((DataInputStream) localObject2).readFloat();
				((DayDataFile) localObject4).totalAmount = ((DataInputStream) localObject2).readLong();
				((DayDataFile) localObject4).totalMoney = ((DataInputStream) localObject2).readFloat();
				((DayDataFile) localObject4).reserveCount = ((DataInputStream) localObject2).readInt();
				localVector.add(localObject4);
			}
			((DataInputStream) localObject2).close();
			((FileInputStream) localObject1).close();
		}
		Object localObject1 = new ByteArrayOutputStream();
		Object localObject2 = new DataOutputStream((OutputStream) localObject1);
		((DataOutputStream) localObject2).writeInt(localVector.size() + arrayOfDayDataFile.length);
		for (int i = 0; i < localVector.size(); i++) {
			DayDataFile localObject3 = (DayDataFile) localVector.get(i);
			((DataOutputStream) localObject2).writeInt((int) ((DayDataFile) localObject3).date);
			((DataOutputStream) localObject2).writeFloat(((DayDataFile) localObject3).openPrice);
			((DataOutputStream) localObject2).writeFloat(((DayDataFile) localObject3).highPrice);
			((DataOutputStream) localObject2).writeFloat(((DayDataFile) localObject3).lowPrice);
			((DataOutputStream) localObject2).writeFloat(((DayDataFile) localObject3).closePrice);
			((DataOutputStream) localObject2).writeFloat(((DayDataFile) localObject3).balancePrice);
			((DataOutputStream) localObject2).writeLong(((DayDataFile) localObject3).totalAmount);
			((DataOutputStream) localObject2).writeFloat(((DayDataFile) localObject3).totalMoney);
			((DataOutputStream) localObject2).writeInt(((DayDataFile) localObject3).reserveCount);
		}
		for (int i = 0; i < arrayOfDayDataFile.length; i++) {
			((DataOutputStream) localObject2).writeInt((int) arrayOfDayDataFile[i].date);
			((DataOutputStream) localObject2).writeFloat(arrayOfDayDataFile[i].openPrice);
			((DataOutputStream) localObject2).writeFloat(arrayOfDayDataFile[i].highPrice);
			((DataOutputStream) localObject2).writeFloat(arrayOfDayDataFile[i].lowPrice);
			((DataOutputStream) localObject2).writeFloat(arrayOfDayDataFile[i].closePrice);
			((DataOutputStream) localObject2).writeFloat(arrayOfDayDataFile[i].balancePrice);
			((DataOutputStream) localObject2).writeLong(arrayOfDayDataFile[i].totalAmount);
			((DataOutputStream) localObject2).writeFloat(arrayOfDayDataFile[i].totalMoney);
			((DataOutputStream) localObject2).writeInt(arrayOfDayDataFile[i].reserveCount);
		}
		((DataOutputStream) localObject2).close();
		FileOutputStream localFileOutputStream1 = new FileOutputStream(localFile);
		localFileOutputStream1.write(((ByteArrayOutputStream) localObject1).toByteArray());
		localFileOutputStream1.flush();
		localFileOutputStream1.close();
		Object localObject3 = ((ByteArrayOutputStream) localObject1).toByteArray();
		((ByteArrayOutputStream) localObject1).reset();
		Object localObject4 = new GZIPOutputStream((OutputStream) localObject1);
		DataOutputStream localDataOutputStream = new DataOutputStream((OutputStream) localObject4);
		localDataOutputStream.write((byte[]) localObject3);
		localDataOutputStream.flush();
		localDataOutputStream.close();
		((GZIPOutputStream) localObject4).flush();
		((GZIPOutputStream) localObject4).close();
		mkdir(this.fileParameter.getM_strHistoryDataPath() + "/" + paramString3);
		FileOutputStream localFileOutputStream2 = new FileOutputStream(new File(this.fileParameter.getM_strHistoryDataPath() + "/" + paramString3
				+ "/" + paramString1.trim() + paramString2.trim() + "." + paramString3 + ".zip"));
		localFileOutputStream2.write(((ByteArrayOutputStream) localObject1).toByteArray());
		localFileOutputStream2.flush();
		localFileOutputStream2.close();
		((ByteArrayOutputStream) localObject1).reset();
	}

	public boolean isCreateKLine(String paramString) {
		Properties localProperties = new Properties();
		boolean bool = false;
		try {
			FileInputStream localFileInputStream = new FileInputStream(
					this.fileParameter.getM_strLocalCache() + "/CreateFile" + paramString + ".log");
			localProperties.load(localFileInputStream);
			String localObject = localProperties.getProperty("CreateKLineFlag", "0");
			if ("0".equals(((String) localObject).trim())) {
				bool = false;
			} else {
				bool = true;
			}
			localFileInputStream.close();
		} catch (FileNotFoundException localFileNotFoundException) {
			localFileNotFoundException.printStackTrace();
			mkdir(this.fileParameter.getM_strLocalCache());
			Object localObject = new File(this.fileParameter.getM_strLocalCache() + "/CreateFile" + paramString + ".log");
			try {
				((File) localObject).createNewFile();
			} catch (IOException localIOException2) {
				localIOException2.printStackTrace();
			}
		} catch (IOException localIOException1) {
			localIOException1.printStackTrace();
		}
		return bool;
	}

	public void checkCreateKLineFlag(String paramString) {
		Properties localProperties = new Properties();
		try {
			FileInputStream localFileInputStream = new FileInputStream(
					this.fileParameter.getM_strLocalCache() + "/CreateFile" + paramString + ".log");
			localProperties.load(localFileInputStream);
			localFileInputStream.close();
			String localObject = localProperties.getProperty("CreateKLineFlag");
			if (localObject == null) {
				localProperties.setProperty("CreateKLineFlag", "0");
			} else if (!"0".equals(((String) localObject).trim())) {
				localProperties.setProperty("CreateKLineFlag", "0");
			}
			FileOutputStream localFileOutputStream = new FileOutputStream(
					this.fileParameter.getM_strLocalCache() + "/CreateFile" + paramString + ".log");
			localProperties.store(localFileOutputStream, "");
			localFileOutputStream.close();
		} catch (FileNotFoundException localFileNotFoundException) {
			mkdir(this.fileParameter.getM_strLocalCache());
			Object localObject = new File(this.fileParameter.getM_strLocalCache() + "/CreateFile" + paramString + ".log");
			try {
				((File) localObject).createNewFile();
			} catch (IOException localIOException2) {
				localIOException2.printStackTrace();
			}
		} catch (IOException localIOException1) {
			localIOException1.printStackTrace();
		}
	}
}
