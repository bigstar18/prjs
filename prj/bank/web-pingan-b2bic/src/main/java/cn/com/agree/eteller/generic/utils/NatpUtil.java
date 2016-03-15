// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name: NatpUtil.java

package cn.com.agree.eteller.generic.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import cn.com.agree.eteller.generic.action.NatpGenericAction;
import cn.com.agree.eteller.generic.exception.ServiceException;
import cn.com.agree.eteller.generic.nnatp.INatp;
import cn.com.agree.eteller.generic.nnatp.impl.CommunicationNatp;

// Referenced classes of package cn.com.agree.eteller.generic.utils:
// ConfigUtil, Pagination, SendDataThread

public class NatpUtil {
	public static class AsynSendDataToAFA {

		private Object obj;
		private String methodNames[];
		private Map dataMap;

		public Map getData() {
			String as[];
			int j = (as = methodNames).length;
			for (int i = 0; i < j; i++) {
				String methodName = as[i];
				final String m = methodName;
				(new Thread() {

					public void run() {
						Class clazz = obj.getClass();
						try {
							Method method = clazz.getDeclaredMethod(m, new Class[0]);
							method.setAccessible(true);
							Object data = method.invoke(obj, new Object[0]);
							dataMap.put(m, data);
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}

				}).start();
			}

			while (dataMap.size() < methodNames.length)
				;
			return dataMap;
		}

		public AsynSendDataToAFA(Object obj, String methodNames[]) {
			this.obj = obj;
			this.methodNames = methodNames;
			dataMap = new Hashtable();
		}
	}

	private class NatpTimer extends Thread {

		private int time;

		public void run() {
			int newTime = time;
			while (timing) {
				if ((newTime -= 1000) <= 0) {
					timeout = true;
					break;
				}
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			timing = false;
		}

		public NatpTimer(int time) {
			this.time = time;
		}
	}

	public static abstract class ParseNatpMsgCallback {

		public abstract String doInParse(String s, String s1, int i) throws Exception;

		public ParseNatpMsgCallback() {
		}
	}

	public static final String CONNECT = "connect";
	public static final String TEMPLATE_CODE = "templateCode";
	public static final String TRANS_CODE = "transCode";
	private static int time;
	private NatpTimer timer;
	private boolean timing;
	private boolean timeout;

	public NatpUtil() {
	}

	public static INatp getInstance(String templateCode, String transCode) {
		int natpVersion = 16;
		String reservedCode = "";
		CommunicationNatp natp = new CommunicationNatp();
		natp.init(natpVersion, transCode, templateCode, reservedCode);
		return natp;
	}

	public static INatp autoPack(INatp natp, Map data) throws Exception {
		java.util.Map.Entry entry;
		for (Iterator iterator = data.entrySet().iterator(); iterator.hasNext(); natp.pack((String) entry.getKey(), (String) entry.getValue()))
			entry = (java.util.Map.Entry) iterator.next();

		return natp;
	}

	public static INatp autoPackInStruts2(INatp natp) throws Exception {
		for (Iterator iterator = ServletActionContext.getContext().getParameters().entrySet().iterator(); iterator.hasNext();) {
			java.util.Map.Entry params = (java.util.Map.Entry) iterator.next();
			if (params.getValue() instanceof String[]) {
				String vals[] = (String[]) params.getValue();
				String as[];
				int j = (as = vals).length;
				for (int i = 0; i < j; i++) {
					String val = as[i];
					natp.pack((String) params.getKey(), val);
				}

			}
		}

		return natp;
	}

	public static Map autoUnpack(INatp natp, String fields[]) throws Exception {
		Map map = new HashMap();
		String as[];
		int j = (as = fields).length;
		for (int i = 0; i < j; i++) {
			String f = as[i];
			String val = natp.unpack(f, 1);
			map.put(f, val);
		}

		return map;
	}

	public static INatp exchange(Map sendData) throws Exception {
		String connect = (String) sendData.get("connect");
		int natpVersion = 16;
		String transCode = (String) sendData.get("transCode");
		String templateCode = (String) sendData.get("templateCode");
		String reservedCode = "";
		if (connect == null || transCode == null || templateCode == null)
			throw new RuntimeException("必要参数不全！");
		CommunicationNatp natp = new CommunicationNatp();
		natp.init(natpVersion, transCode, templateCode, reservedCode);
		for (Iterator iterator = sendData.entrySet().iterator(); iterator.hasNext();) {
			java.util.Map.Entry sendDataEntry = (java.util.Map.Entry) iterator.next();
			if (!"connect".equals(sendDataEntry.getKey()) && !"transCode".equals(sendDataEntry.getKey())
					&& !"templateCode".equals(sendDataEntry.getKey()))
				natp.pack((String) sendDataEntry.getKey(), (String) sendDataEntry.getValue());
		}

		natp.exchange(connect);
		String retcode = natp.unpack("errorCode", 1);
		String retmsg = natp.unpack("errorMsg", 1);
		if (!"0000".equals(retcode))
			throw new ServiceException(retcode, retmsg);
		else
			return natp;
	}

	public static List parseNatpMsg(INatp natp, List fieldNames, String lengthName, ParseNatpMsgCallback callback) throws Exception {
		List msgList = new ArrayList();
		String retcode = natp.unpack("errorCode", 1);
		String retmsg = natp.unpack("errorMsg", 1);
		if ("0000".equals(retcode)) {
			String num = natp.unpack(lengthName, 1);
			for (int i = 1; i < Integer.parseInt(num) + 1; i++) {
				Map dataMap = new HashMap();
				String k;
				String data;
				for (Iterator iterator = fieldNames.iterator(); iterator.hasNext(); dataMap.put(k, data)) {
					k = (String) iterator.next();
					data = natp.unpack(k, i);
					if (data != null)
						data = data.trim();
					if (callback != null)
						data = callback.doInParse(data, k, i);
				}

				msgList.add(dataMap);
			}

		} else {
			throw new ServiceException(retcode, retmsg);
		}
		return msgList;
	}

	public static List parseNatpMsg(INatp natp, List fieldNames, String lengthName) throws Exception {
		return parseNatpMsg(natp, fieldNames, lengthName, null);
	}

	public static List parseNatpMsg(INatp natp, String fieldNames[], String lengthName, ParseNatpMsgCallback callback) throws Exception {
		return parseNatpMsg(natp, Arrays.asList(fieldNames), lengthName, callback);
	}

	public static List parseNatpMsg(INatp natp, String fieldNames[], String lengthName) throws Exception {
		return parseNatpMsg(natp, Arrays.asList(fieldNames), lengthName, null);
	}

	public static List splitPage(List list, Pagination page) {
		List newList = new ArrayList();
		page.setAllRecords(Integer.valueOf(list.size()));
		for (int i = 0; i < list.size(); i++)
			if (i >= page.getFirstRecord().intValue() && i < page.getFirstRecord().intValue() + page.getPerPageRecords().intValue())
				newList.add((Map) list.get(i));

		return newList;
	}

	public Map sendDataToAFA(String connect, String transCode, String templateCode, String fieldName[], List fieldValueList) {
		Map map = new Hashtable();
		int dataNum = fieldValueList.size();
		for (int i = 0; i < dataNum; i++) {
			SendDataThread thread = new SendDataThread(connect, transCode, templateCode, fieldName, (String[]) fieldValueList.get(i), map);
			(new Thread(thread)).start();
		}

		startupNatpTimer();
		while (isTiming() && !isTimeout())
			if (map.size() >= dataNum) {
				stopTimer();
				break;
			}
		if (isTimeout())
			map.put("timeout", "此次通讯超时,可能通讯异常或显示结果不完整");
		return map;
	}

	public static String parseMapToHtml(Map map, String title) {
		StringBuffer text = new StringBuffer();
		int i = 1;
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if (!"timeout".equals(key)) {
				String msg = (String) map.get(key);
				text.append(i++).append(".").append(title).append("[").append(key.substring(1)).append("] 处理结果[").append(msg).append("]<br/>");
			} else {
				text.append((String) map.get("timeout")).append("<br/>");
			}
		}

		return text.toString();
	}

	public void startupNatpTimer() {
		timeout = false;
		timing = true;
		timer = new NatpTimer(time);
		timer.start();
	}

	public boolean isTimeout() {
		return timeout;
	}

	public boolean isTiming() {
		return timing;
	}

	public void stopTimer() {
		timing = false;
	}

	public static void fillMapDefaultValueWithEmptyStr(Map map, String fieldNames[]) {
		String as[];
		int j = (as = fieldNames).length;
		for (int i = 0; i < j; i++) {
			String field = as[i];
			if (map.get(field) == null)
				map.put(field, "");
		}

	}

	public static void initQueryKey(NatpGenericAction action, Map map, String keys[]) {
		String as[];
		int j = (as = keys).length;
		for (int i = 0; i < j; i++) {
			String k = as[i];
			if (!action.getFirstQueryKey().containsKey(k))
				action.getFirstQueryKey().put(k, "");
			if (!action.getLastQueryKey().containsKey(k))
				action.getLastQueryKey().put(k, "");
		}

	}

	public static void main(String args1[]) throws Exception {
	}

	static {
		ConfigUtil conf = new ConfigUtil("config/nnatp.properties");
		time = conf.getInt("timeout");
	}

}
