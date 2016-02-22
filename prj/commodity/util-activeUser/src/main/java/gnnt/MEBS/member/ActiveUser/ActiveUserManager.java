package gnnt.MEBS.member.ActiveUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

public class ActiveUserManager {
	private static int SINGLE_MODE = 1;
	private static int MULTI_MODE = 2;
	private static Hashtable<Long, AUValue> activeStore = new Hashtable(600);
	private static AUThread auThread = null;
	private static Random random = new Random();
	private static int userCount = 0;
	private static Object countLock = new Object();
	private static boolean auThreadFlag = false;
	private static int mode = MULTI_MODE;
	private int space = 30;
	private int expireTime = 60;

	public ActiveUserManager() {
		if (!auThreadFlag) {
			auThreadFlag = true;
			auThread = new AUThread(activeStore, this.space * 1000, this.expireTime * 60000);
			auThread.start();
		}
	}

	public ActiveUserManager(int space, int expireTime, int mode) {
		if (!auThreadFlag) {
			this.space = space;
			this.expireTime = expireTime;
			setMode(mode);

			auThreadFlag = true;
			auThread = new AUThread(activeStore, space * 1000, expireTime * 60000);
			auThread.start();
		}
	}

	public static void setMode(int m) {
		mode = m;
	}

	public long logon(String userID) {
		return logon(userID, null);
	}

	public long logon(String userID, String ip) {
		return logon(userID, ip, null);
	}

	public long logon(String userID, String ip, Long auSessionId) {
		AUValue au = new AUValue();
		au.setUserName(userID);
		au.setIP(ip);
		long sessionID;
		if (auSessionId == null) {
			sessionID = createSessionID();
			while (activeStore.containsKey(new Long(sessionID))) {
				sessionID = createSessionID();
			}
		} else {
			if (!activeStore.containsKey(auSessionId)) {
				sessionID = auSessionId.longValue();
			} else {
				if (userID.equals(activeStore.get(auSessionId))) {
					return auSessionId.longValue();
				}
				return -1L;
			}
		}
		if (mode == SINGLE_MODE) {
			Enumeration sessionKeys = activeStore.keys();

			AUValue av = null;
			while (sessionKeys.hasMoreElements()) {
				Long keyObject = (Long) sessionKeys.nextElement();

				av = (AUValue) activeStore.get(keyObject);
				if (av.getUserName().equals(userID)) {
					activeStore.remove(keyObject);
					decreaseUserCount();
				}
			}
			activeStore.put(new Long(sessionID), au);
		} else if (mode == MULTI_MODE) {
			activeStore.put(new Long(sessionID), au);
		} else {
			return -1L;
		}
		au.setSessionId(sessionID);
		addUserCount();
		return sessionID;
	}

	public void logoff(long sessionID) {
		activeStore.remove(new Long(sessionID));
		decreaseUserCount();
	}

	public int logoffUser(String userID) {
		int n = 0;
		Enumeration sessionKeys = activeStore.keys();

		AUValue av = null;
		while (sessionKeys.hasMoreElements()) {
			Long keyObject = (Long) sessionKeys.nextElement();

			av = (AUValue) activeStore.get(keyObject);
			if (av.getUserName().equals(userID)) {
				activeStore.remove(keyObject);
				decreaseUserCount();
				n++;
			}
		}
		return n;
	}

	public String getUserID(long sessionID) {
		AUValue av = (AUValue) activeStore.get(new Long(sessionID));
		if (av == null) {
			return null;
		}
		av.setLastTime(System.currentTimeMillis());
		return av.getUserName();
	}

	public String getUserIP(long sessionID) {
		AUValue av = (AUValue) activeStore.get(new Long(sessionID));
		if (av == null) {
			return null;
		}
		return av.getIP();
	}

	public String[] getAllUsers() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Enumeration en = activeStore.elements();
		Vector<String> v1 = new Vector();
		AUValue av = null;
		StringBuffer tempBuffer = null;
		while (en.hasMoreElements()) {
			av = (AUValue) en.nextElement();
			tempBuffer = new StringBuffer(av.getUserName());
			tempBuffer.append(",");
			tempBuffer.append(formatter.format(av.getLogonTime()));
			tempBuffer.append(",");
			tempBuffer.append(av.getIP());
			v1.addElement(tempBuffer.toString());
		}
		String[] tmp = new String[v1.size()];
		v1.toArray(tmp);
		return tmp;
	}

	public String[] getAllUsersSys(String name) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Enumeration en = activeStore.elements();
		Vector<String> v1 = new Vector();
		AUValue av = null;
		StringBuffer tempBuffer = null;
		while (en.hasMoreElements()) {
			av = (AUValue) en.nextElement();
			if ((name == null) || ("".equals(name)) || (av.getUserName().equals(name))) {
				tempBuffer = new StringBuffer(av.getUserName());
				tempBuffer.append(",");
				tempBuffer.append(formatter.format(av.getLogonTime()));
				tempBuffer.append(",");
				tempBuffer.append(av.getIP());
				tempBuffer.append(",");
				tempBuffer.append(av.getSessionId());
				v1.addElement(tempBuffer.toString());
			}
		}
		String[] tmp = new String[v1.size()];
		v1.toArray(tmp);
		return tmp;
	}

	public String[] getAllUsersWithIP() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Enumeration en = activeStore.elements();

		Vector<String> v1 = new Vector();
		AUValue av = null;
		StringBuffer tempBuffer = null;
		while (en.hasMoreElements()) {
			av = (AUValue) en.nextElement();
			tempBuffer = new StringBuffer(av.getUserName());
			tempBuffer.append(",");
			tempBuffer.append(formatter.format(av.getLogonTime()));
			tempBuffer.append(",");
			tempBuffer.append(av.getIP());
			v1.addElement(tempBuffer.toString());
		}
		String[] tmp = new String[v1.size()];
		v1.toArray(tmp);
		return tmp;
	}

	public Object getPrivateDate(long sessionID, String key) {
		return ((AUValue) activeStore.get(new Long(sessionID))).getPersonalData(key);
	}

	public void setPrivateDate(long sessionID, String key, Object obj) {
		((AUValue) activeStore.get(new Long(sessionID))).setPersonalData(key, obj);
	}

	private long createSessionID() {
		long t1 = 0x7FFFFFFF & System.currentTimeMillis();

		return t1 << 32 | Math.abs(random.nextInt());
	}

	protected static void addUserCount() {
		synchronized (countLock) {
			userCount += 1;
		}
	}

	protected static void decreaseUserCount() {
		synchronized (countLock) {
			if (userCount > 0) {
				userCount -= 1;
			}
		}
	}

	static class AUThread extends Thread {
		private Hashtable activeStore = null;
		private int space;
		private long expireTime;

		public AUThread(Hashtable activeStore, int space, long expireTime) {
			this.space = space;
			this.expireTime = expireTime;
			this.activeStore = activeStore;
		}

		public void run() {
			while (ActiveUserManager.auThreadFlag) {
				try {
					sleep(this.space);
					checkExpire();
				} catch (InterruptedException e) {
					System.out.println("AU 监视线程失败");
				} catch (NullPointerException e) {
					System.out.println("checkExire catch NullPointer");
				}
			}
		}

		private void checkExpire() {
			Long sessionID = null;
			ActiveUserManager.AUValue av = null;
			Enumeration sessionKeys = this.activeStore.keys();
			long curTime = System.currentTimeMillis();
			while (sessionKeys.hasMoreElements()) {
				sessionID = (Long) sessionKeys.nextElement();
				av = (ActiveUserManager.AUValue) this.activeStore.get(sessionID);
				if (curTime - av.getLastTime() > this.expireTime) {
					if (av.getUserName() != null) {
						ActiveUserManager.decreaseUserCount();
					}
					this.activeStore.remove(sessionID);
				}
			}
		}

		public synchronized void wakeup() {
			notify();
		}
	}

	class AUValue {
		private String userName = null;
		private long lastTime = 0L;
		private Hashtable<String, Object> personalStore = new Hashtable();
		private Date logonTime = null;
		private String IP = null;
		private long sessionId;

		public long getSessionId() {
			return this.sessionId;
		}

		public void setSessionId(long sessionId) {
			this.sessionId = sessionId;
		}

		public AUValue() {
		}

		protected void setUserName(String userName) {
			this.userName = userName;
		}

		protected String getUserName() {
			return this.userName;
		}

		protected void setIP(String ip) {
			this.IP = ip;
		}

		protected String getIP() {
			return this.IP;
		}

		protected void setPersonalData(String key, Object data) {
			this.personalStore.put(key, data);
		}

		protected Object getPersonalData(String key) {
			return this.personalStore.get(key);
		}

		protected void setLastTime(long lastTime) {
			this.lastTime = lastTime;
		}

		protected long getLastTime() {
			return this.lastTime;
		}

		protected void clearData() {
			this.personalStore.clear();
		}

		protected Date getLogonTime() {
			return this.logonTime;
		}
	}
}
