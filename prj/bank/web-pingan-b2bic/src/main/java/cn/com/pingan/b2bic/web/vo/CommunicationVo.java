package cn.com.pingan.b2bic.web.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CommunicationVo {
	private String tradeCode;
	private String status;
	private String message;
	private Map<String, String> params;
	private Map<String, List<String>> listParams;
	private static Gson gson = new Gson();

	public CommunicationVo() {
	}

	public CommunicationVo(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getParam(String key) {
		return (String) this.params.get(key);
	}

	public List<String> getListParam(String key) {
		return (List) this.listParams.get(key);
	}

	public void putParam(String key, String value) {
		if (this.params == null) {
			this.params = new HashMap();
		}
		this.params.put(key, value);
	}

	public void putListParam(String key, List<String> value) {
		if (this.listParams == null) {
			this.listParams = new HashMap();
		}
		this.listParams.put(key, value);
	}

	public String toJson() {
		return gson.toJson(this);
	}

	public static CommunicationVo getInstancefromJson(String json) {
		return (CommunicationVo) gson.fromJson(json, new TypeToken<CommunicationVo>() {
		}.getType());
	}

	public String toString() {
		return

		"CommunicationVo [tradeCode=" + this.tradeCode + ", status=" + this.status + ", message=" + this.message + ", params=" + this.params
				+ ", listParams=" + this.listParams + "]";
	}

	public String getTradeCode() {
		return this.tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getParams() {
		return this.params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, List<String>> getListParams() {
		return this.listParams;
	}

	public void setListParams(Map<String, List<String>> listParams) {
		this.listParams = listParams;
	}
}
