package gnnt.MEBS.common.mgr.beanforajax;

import gnnt.MEBS.common.mgr.service.StandardService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;

/**
 * AJAX 公用父类
 * @author liuzx
 */
public abstract class BaseAjax {

	/** 日志记录属性 */
	protected transient final Log logger = LogFactory.getLog(this.getClass());

	/** 普通成功结果 */
	protected final String SUCCESS="success";

	/** 注入标准service实例 */
	@Autowired
	@Qualifier("com_standardService")
	private StandardService standardService;

	/**
	 * 用于返回的 json 串
	 */
	protected JSONArray jsonValidateReturn;

	/**
	 * 获取 Service 对象
	 * @return StandardService
	 */
	protected StandardService getService(){
		return standardService;
	}

	/**
	 * 用于返回的 json 串
	 * @return JSONArray
	 */
	public JSONArray getJsonValidateReturn() {
		return jsonValidateReturn;
	}

	/**
	 * 获取Request
	 * @return HttpServletRequest
	 */
	protected HttpServletRequest getRequest(){
		ActionContext ac = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ac
				.get(ServletActionContext.HTTP_REQUEST);
		return request;
	}

	/**
	 * 通过传入参数生成 JSONArray 类
	 * @param objects 参数
	 * @return JSONArray
	 */
	protected JSONArray createJSONArray(Object ... objects){
		JSONArray result = new JSONArray();
		for(Object obj : objects){
			result.add(obj);
		}
		return result;
	}

	/**
	 * JSONArray 生成类
	 * @author liuzx
	 */
	protected class AjaxJSONArrayResponse{

		/** 生成的 JSONArray 类 */
		private JSONArray jsonArray = new JSONArray();

		/**
		 * 构造方法
		 */
		public AjaxJSONArrayResponse(Object ... objects){
			addJSON(objects);
		}

		/**
		 * 通过传入集合，生成多个返回结果
		 * @param list 结果集合
		 */
		public void addJSONList(List<Object> list){
			if(list != null && list.size()>0){
				for(Object obj : list){
					jsonArray.add(obj);
				}
			}
		}

		/**
		 * 通过不定参数个数添加返回结果
		 * @param objects 参数集合
		 */
		public void addJSON(Object ... objects){
			for(Object obj : objects){
				jsonArray.add(obj);
			}
		}

		/**
		 * 获取增加结果后的
		 * @return JSONArray
		 */
		public JSONArray toJSONArray(){
			return jsonArray;
		}

		/**
		 * 获取当前 JSONArray 的长度
		 * @return
		 */
		public int size(){
			return jsonArray.size();
		}
	}

	/** 当跨域访问时返回结果 */
	private final static String AJAX = "ajax";

	/**
	 * ajax回写客户端流
	 */
	private InputStream inputStream;

	/**
	 * 方法由struts2调用,在配置文件里有设置这个方法,名字根据个人爱好
	 * 
	 * @return ajax回写数据流
	 */
	public InputStream getAjaxInputStream() {
		return this.inputStream;
	}

	/**
	 * 设置ajax回写数据流
	 * 
	 * @param data
	 *            返回客户端的字符串
	 */
	public void setAjaxInputStream(String data) {
		byte[] bytes;
		try {
			bytes = data.getBytes("utf-8");
			this.inputStream = new ByteArrayInputStream(bytes);
		} catch (UnsupportedEncodingException e) {
			logger.error("设置ajax input stream 失败! 错误原因 : {}");
		}
	}

	/**
	 * 处理action返回值；如果是异步请求则请求内容中有jsoncallback参数则通过输出流方式返回，否则通过json返回
	 * 
	 * @param request
	 * @return
	 */
	public String result() {
		String callback = getRequest().getParameter("jsoncallback");
		if (callback != null && callback.length() > 0) {
			StringBuffer sb = new StringBuffer(callback);
			sb.append("(");
			sb.append(jsonValidateReturn);
			sb.append(")");
			setAjaxInputStream(sb.toString());
			return AJAX;
		} else {
			return SUCCESS;
		}
	}

}
