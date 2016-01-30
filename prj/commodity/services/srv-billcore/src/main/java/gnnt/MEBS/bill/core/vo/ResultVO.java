package gnnt.MEBS.bill.core.vo;

import gnnt.MEBS.bill.core.util.GnntBeanFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 业务处理返回结果
 * 
 * @author xuejt
 * 
 */
public class ResultVO implements Serializable {
	private static final long serialVersionUID = -2731335665181453151L;

	private Log log = LogFactory.getLog(ResultVO.class);
	/**
	 * -1:失败 0:警告 1:成功
	 */
	private long result = 1;

	/*
	 * 存放错误或者警告信息
	 */
	private Map<java.lang.Long, String> errorInfoMap = new HashMap<Long, String>();

	/**
	 * -1:失败 0:警告 1:成功
	 * 
	 * @return -1:失败 0:警告 1:成功
	 */
	public long getResult() {
		return result;
	}

	/**
	 * -1:失败 0:警告 1:成功
	 * 
	 * @param result
	 *            -1:失败 0:警告 1:成功
	 */
	public void setResult(long result) {
		this.result = result;
	}

	/**
	 * 存放错误或者警告信息
	 * 
	 * @return 存放错误或者警告信息
	 */
	public Map<java.lang.Long, String> getInfoMap() {
		return errorInfoMap;
	}

	/**
	 * 返回错误或者警告信息
	 * 
	 * @return 返回错误或者警告信息
	 */
	public String getErrorInfo() {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<java.lang.Long, String> m : errorInfoMap.entrySet()) {
			sb.append(m.getValue()).append(";");
		}
		return sb.toString();
	}

	/**
	 * 返回错误或者警告详细信息
	 * 
	 * @return 返回错误或者警告详细信息
	 */
	public String getErrorDetailInfo() {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<java.lang.Long, String> m : errorInfoMap.entrySet()) {
			sb.append("错误码【" + m.getKey() + "】错误信息【" + m.getValue()).append(
					"】\n");
		}
		return sb.toString();
	}

	/**
	 * 添加错误或者警告信息
	 * 
	 * @param errorCode
	 *            错误码
	 */
	public void addErrorInfo(long errorCode) {
		errorInfoMap.put(errorCode, GnntBeanFactory
				.getErrorInfo(errorCode + ""));
	}

	/**
	 * 返回错误码
	 * 
	 * @return
	 */
	public String getErrorCode() {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<java.lang.Long, String> m : errorInfoMap.entrySet()) {
			sb.append(m.getKey()).append(" ");
		}
		return sb.toString();
	}

	/**
	 * 添加错误或者警告信息
	 * 
	 * @param errorCode
	 *            错误码
	 * @param args
	 */
	public void addErrorInfo(long errorCode, Object[] args) {
		String format = GnntBeanFactory.getErrorInfo(errorCode + "");
		String errorInfo = format;
		try {
			errorInfo = String.format(format, args);
		} catch (Exception e) {
			log.debug("errorCode:" + errorCode + ";Format Exception!");
			log.debug("formatStr:" + format);
			for (Object o : args) {
				log.debug("Object:" + o.toString());
			}
			log.debug(e.toString());
		}
		errorInfoMap.put(errorCode, errorInfo);
	}
}
