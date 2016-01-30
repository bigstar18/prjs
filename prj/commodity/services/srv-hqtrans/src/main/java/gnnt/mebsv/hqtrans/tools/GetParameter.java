package gnnt.mebsv.hqtrans.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import gnnt.MEBS.util.Configuration;
import gnnt.mebsv.hqtrans.model.FileParameter;

public class GetParameter {
	public boolean getParameter(FileParameter paramFileParameter) {
		if (paramFileParameter == null) {
			paramFileParameter = new FileParameter();
		}
		Properties localProperties1 = new Properties();
		FileInputStream localFileInputStream = null;
		try {
			File localFile = new File("HQTrans.properties");
			String str = localFile.getAbsolutePath();
			localFileInputStream = new FileInputStream(str);
			localProperties1.load(localFileInputStream);
			paramFileParameter.setTimeIntervalCheck(Integer.parseInt(localProperties1.getProperty("TimeIntervalCheck")));
			paramFileParameter.setM_strLocalCache(localProperties1.getProperty("CachePath"));
			if ((paramFileParameter.getM_strLocalCache() == null) || (paramFileParameter.getM_strLocalCache().length() == 0)) {
				System.out.println("Please setup :Cache Path !");
				boolean bool2 = false;
				return bool2;
			}
			Properties localProperties2 = new Configuration().getSection("MEBS.HQService");
			paramFileParameter.setM_strHistoryDataPath(localProperties2.getProperty("CodeFileSavePath"));
			return true;
		} catch (IOException localIOException2) {
			localIOException2.printStackTrace();
			System.err.println(localIOException2);
			return false;
		} finally {
			if (localFileInputStream != null) {
				try {
					localFileInputStream.close();
				} catch (IOException localIOException5) {
					localIOException5.printStackTrace();
				}
			}
		}
	}
}
