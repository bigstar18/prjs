package gnnt.mebsv.hqservice.service.IO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;

import gnnt.mebsv.hqservice.model.co_classVO;

public class CreateXMLClass {
	public void CreateXml(String paramString, ArrayList paramArrayList) {
		co_classVO localco_classVO = new co_classVO();
		Date localDate = new Date();
		String str1 = paramString + "CreateXMLClass";
		String str2 = "<?xml version='1.0' encoding='GBK' ?>";
		String str3 = "<zxonliveimagemessage>";
		String str4 = "";
		Object localObject1;
		Object localObject2;
		for (int i = 0; i < paramArrayList.size(); i++) {
			localco_classVO = (co_classVO) paramArrayList.get(i);
			String str6 = "<zxmessage cc_classid='" + localco_classVO.getCc_classid() + "'>";
			localObject1 = "<cc_commodity_id>" + localco_classVO.getCc_commodity_id() + "</cc_commodity_id>" + "<cc_desc>"
					+ localco_classVO.getCc_desc() + "</cc_desc>" + "<cc_fullname>" + localco_classVO.getCc_fullname() + "</cc_fullname>"
					+ "<cc_name>" + localco_classVO.getCc_name() + "</cc_name>" + "<cc_pricetype>" + localco_classVO.getCc_pricetype()
					+ "</cc_pricetype>" + "<cc_remark>" + localco_classVO.getCc_remark() + "</cc_remark>" + "<market_name>"
					+ localco_classVO.getMarket_name() + "</market_name>" + "<market>" + localco_classVO.getMarket() + "</market>";
			localObject2 = " </zxmessage>";
			String str7 = str6 + (String) localObject1 + (String) localObject2;
			str4 = str4 + str7;
		}
		String str5 = "</zxonliveimagemessage>";
		String str6 = str2 + str3 + str4 + str5;
		try {
			localObject1 = new FileOutputStream(str1 + ".xml");
			localObject2 = new OutputStreamWriter((OutputStream) localObject1, "GBK");
			((Writer) localObject2).write(str6);
			((Writer) localObject2).close();
			((FileOutputStream) localObject1).close();
		} catch (FileNotFoundException localFileNotFoundException) {
			localFileNotFoundException.printStackTrace();
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
			localUnsupportedEncodingException.printStackTrace();
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
	}
}