package gnnt.MEBS.transformhq.server.util;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.transformhq.server.model.HQBean;

public class TransformForInteractive {
	private Log log = LogFactory.getLog(TransformForInteractive.class);

	public static HQBean transServerHQ(String hqServerString) {
		HQBean hqBean = null;
		Map<String, String> results = ForeignDSUtil.parseResponse(hqServerString);
		String cmdtyid = (String) results.get("5");
		if ((cmdtyid != null) && (!cmdtyid.equals(""))) {
			hqBean = new HQBean();
			hqBean.setCommodityID(cmdtyid);
			hqBean.setHqDate(new Date());
			hqBean.setSellPrice((String) results.get("10"));
			hqBean.setBuyPrice((String) results.get("12"));
			hqBean.setFormerPrice(results.get("8") != null ? (String) results.get("8") : (String) results.get("12"));
			hqBean.setPrice(results.get("8") != null ? (String) results.get("8") : (String) results.get("12"));
			hqBean.setClientContents(hqServerString);
			hqBean.setPriceTime(System.currentTimeMillis());
		}
		return hqBean;
	}

	public static String transClientHQ(HQBean hqBean) {
		hqBean.setDateType("6000");
		StringBuffer sb = new StringBuffer();
		Date curDate = new Date();
		String dateStr = DateUtil.formatDate(curDate, "yyyyMMdd");
		String timeStr = DateUtil.formatDate(curDate, "HHmmssSSS");
		sb.append("|3||");
		sb.append(hqBean.getCommodityID());

		sb.append("|250|23||").append(dateStr).append("|").append(timeStr).append("|");
		sb.append(hqBean.getPrice());

		sb.append("|||||||");

		System.out.println("转换为交易系统行情" + sb.toString());
		return sb.toString();
	}

	public String resolveMarketName() {
		return null;
	}

	public static String changePrice(String price) {
		float iprice = Float.parseFloat(price);
		iprice = (iprice * 10000.0F + (float) Math.round(Math.random() * 10.0D) + (float) Math.round(Math.random() * 100.0D)) / 10000.0F;
		return iprice + "";
	}
}
