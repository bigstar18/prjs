package gnnt.MEBS.common.mgr.common;

import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReturnValue {
	private Log log = LogFactory.getLog(ReturnValue.class);
	/**
	 * -1:失败 0:警告 1:成功 -2 返回不弹出信息
	 */
	private int result = -1;

	/**
	 * 自定义的返回信息解析类型<br/>
	 * 用于用户返回页面后执行不同操作的标识符<br/>
	 * 具体编号表示什么意思根据每个应用自行设计
	 */
	private int resultType = -1;

	/*
	 * 存放错误或者警告信息或者提示信息
	 */
	private Map<java.lang.Long, String> errorInfoMap = new HashMap<Long, String>();

	/**
	 * -1:失败 0:警告 1:成功 -2 返回不弹出信息
	 * 
	 * @return
	 */
	public int getResult() {
		return result;
	}

	/**
	 * -1:失败 0:警告 1:成功 -2 返回不弹出信息
	 * 
	 * @param result
	 */
	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * 
	 * 自定义的返回信息解析类型<br/>
	 * 用于用户返回页面后执行不同操作的标识符<br/>
	 * 具体编号表示什么意思根据每个应用自行设计
	 * <br/><br/>
	 * @return
	 */
	public int getResultType() {
		return resultType;
	}

	/**
	 * 
	 * 自定义的返回信息解析类型<br/>
	 * 用于用户返回页面后执行不同操作的标识符<br/>
	 * 具体编号表示什么意思根据每个应用自行设计
	 * <br/><br/>
	 * @param resultType
	 */
	public void setResultType(int resultType) {
		this.resultType = resultType;
	}

	/**
	 * 存放错误或者警告信息或者提示信息
	 * 
	 * @return
	 */
	public Map<java.lang.Long, String> getInfoMap() {
		return errorInfoMap;
	}

	/**
	 * 返回错误或者警告信息或者提示信息
	 * 
	 * @return
	 */
	public String getInfo() {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<java.lang.Long, String> m : errorInfoMap.entrySet()) {
			if (result == 1)
				sb.append(m.getValue()).append("\\n");
			else
//				sb.append(
//						"错误码【" + m.getKey() + "】错误信息【"
//								+ m.getValue()).append("】\\n");
				sb.append(m.getValue()).append("\\n");
		}
		return sb.toString();
	}

	/**
	 * 添加错误或者警告信息或者提示信息
	 * 
	 * @param errorCode
	 *            错误码
	 */
	public void addInfo(long errorCode) {
		errorInfoMap.put(errorCode, ApplicationContextInit
				.getErrorInfo(errorCode + ""));
	}

	/**
	 * 添加错误或者警告信息或者提示信息
	 * 
	 * @param errorCode
	 *            错误码
	 * @param args
	 *            格式化字符串参数
	 */
	public void addInfo(long errorCode, Object[] args) {
		String format = ApplicationContextInit.getErrorInfo(errorCode + "");
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
		errorInfoMap.put(errorCode, errorInfo.replaceAll("\n", ""));
	}
}
