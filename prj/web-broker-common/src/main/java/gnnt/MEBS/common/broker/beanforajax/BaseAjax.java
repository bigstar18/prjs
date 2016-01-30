package gnnt.MEBS.common.broker.beanforajax;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;

import gnnt.MEBS.common.broker.service.StandardService;
import net.sf.json.JSONArray;

public abstract class BaseAjax {
	protected final transient Log logger = LogFactory.getLog(getClass());
	protected final String SUCCESS = "success";
	@Autowired
	@Qualifier("com_standardService")
	private StandardService standardService;
	protected JSONArray jsonValidateReturn;
	private static final String AJAX = "ajax";
	private InputStream inputStream;

	protected StandardService getService() {
		return this.standardService;
	}

	public JSONArray getJsonValidateReturn() {
		return this.jsonValidateReturn;
	}

	protected HttpServletRequest getRequest() {
		ActionContext ac = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ac.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		return request;
	}

	protected JSONArray createJSONArray(Object... objects) {
		JSONArray result = new JSONArray();
		Object[] arrayOfObject;
		int j = (arrayOfObject = objects).length;
		for (int i = 0; i < j; i++) {
			Object obj = arrayOfObject[i];
			result.add(obj);
		}
		return result;
	}

	protected class AjaxJSONArrayResponse {
		private JSONArray jsonArray = new JSONArray();

		public AjaxJSONArrayResponse(Object... objects) {
			addJSON(objects);
		}

		public void addJSONList(List<Object> list) {
			if ((list != null) && (list.size() > 0)) {
				for (Object obj : list) {
					this.jsonArray.add(obj);
				}
			}
		}

		public void addJSON(Object... objects) {
			Object[] arrayOfObject;
			int j = (arrayOfObject = objects).length;
			for (int i = 0; i < j; i++) {
				Object obj = arrayOfObject[i];
				this.jsonArray.add(obj);
			}
		}

		public JSONArray toJSONArray() {
			return this.jsonArray;
		}

		public int size() {
			return this.jsonArray.size();
		}
	}

	public InputStream getAjaxInputStream() {
		return this.inputStream;
	}

	public void setAjaxInputStream(String data) {
		try {
			byte[] bytes = data.getBytes("utf-8");
			this.inputStream = new ByteArrayInputStream(bytes);
		} catch (UnsupportedEncodingException e) {
			this.logger.error("设置ajax input stream 失败! 错误原因 : {}");
		}
	}

	public String result() {
		String callback = getRequest().getParameter("jsoncallback");
		if ((callback != null) && (callback.length() > 0)) {
			StringBuffer sb = new StringBuffer(callback);
			sb.append("(");
			sb.append(this.jsonValidateReturn);
			sb.append(")");
			setAjaxInputStream(sb.toString());
			return "ajax";
		}
		return "success";
	}
}
