package gnnt.MEBS.vendue.kernel;

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

	public ActiveUserManager(int paramInt1, int paramInt2, int paramInt3) {
		if (!auThreadFlag) {
			this.space = paramInt1;
			this.expireTime = paramInt2;
			setMode(paramInt3);
			auThreadFlag = true;
			auThread = new AUThread(activeStore, paramInt1 * 1000, paramInt2 * 60000);
			auThread.start();
		}
	}

	public static void setMode(int paramInt) {
		mode = paramInt;
	}

	public long logon(String paramString) {
		return logon(paramString, null);
	}

	public long logon(String paramString1, String paramString2) {
		AUValue localAUValue1 = new AUValue();
		localAUValue1.setUserName(paramString1);
		localAUValue1.setIP(paramString2);
		long l = 0l;
		for (l = createSessionID(); activeStore.containsKey(new Long(l)); l = createSessionID()) {
		}
		if (mode == SINGLE_MODE) {
			Enumeration localEnumeration = activeStore.keys();
			AUValue localAUValue2 = null;
			while (localEnumeration.hasMoreElements()) {
				Long localLong = (Long) localEnumeration.nextElement();
				localAUValue2 = (AUValue) activeStore.get(localLong);
				if (localAUValue2.getUserName().equals(paramString1)) {
					activeStore.remove(localLong);
					decreaseUserCount();
				}
			}
			activeStore.put(new Long(l), localAUValue1);
		} else if (mode == MULTI_MODE) {
			activeStore.put(new Long(l), localAUValue1);
		} else {
			return -1L;
		}
		addUserCount();
		return l;
	}

	public void logoff(long paramLong) {
		activeStore.remove(new Long(paramLong));
		decreaseUserCount();
	}

	public int logoffUser(String paramString) {
		int i = 0;
		Enumeration localEnumeration = activeStore.keys();
		AUValue localAUValue = null;
		while (localEnumeration.hasMoreElements()) {
			Long localLong = (Long) localEnumeration.nextElement();
			localAUValue = (AUValue) activeStore.get(localLong);
			if (localAUValue.getUserName().equals(paramString)) {
				activeStore.remove(localLong);
				decreaseUserCount();
				i++;
			}
		}
		return i;
	}

	public String getUserID(long paramLong) {
		AUValue localAUValue = (AUValue) activeStore.get(new Long(paramLong));
		if (localAUValue == null) {
			return null;
		}
		localAUValue.setLastTime(System.currentTimeMillis());
		return localAUValue.getUserName();
	}

	public String getUserIP(long paramLong) {
		AUValue localAUValue = (AUValue) activeStore.get(new Long(paramLong));
		if (localAUValue == null) {
			return null;
		}
		return localAUValue.getIP();
	}

	public String[] getAllUsers() {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Enumeration localEnumeration = activeStore.elements();
		Vector localVector = new Vector();
		AUValue localAUValue = null;
		StringBuffer localStringBuffer = null;
		while (localEnumeration.hasMoreElements()) {
			localAUValue = (AUValue) localEnumeration.nextElement();
			localStringBuffer = new StringBuffer(localAUValue.getUserName());
			localStringBuffer.append(",");
			localStringBuffer.append(localSimpleDateFormat.format(localAUValue.getLogonTime()));
			localVector.addElement(localStringBuffer.toString());
		}
		String[] arrayOfString = new String[localVector.size()];
		localVector.toArray(arrayOfString);
		return arrayOfString;
	}

	public String[] getAllUsersWithIP() {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Enumeration localEnumeration = activeStore.elements();
		Vector localVector = new Vector();
		AUValue localAUValue = null;
		StringBuffer localStringBuffer = null;
		while (localEnumeration.hasMoreElements()) {
			localAUValue = (AUValue) localEnumeration.nextElement();
			localStringBuffer = new StringBuffer(localAUValue.getUserName());
			localStringBuffer.append(",");
			localStringBuffer.append(localSimpleDateFormat.format(localAUValue.getLogonTime()));
			localStringBuffer.append(",");
			localStringBuffer.append(localAUValue.getIP());
			localVector.addElement(localStringBuffer.toString());
		}
		String[] arrayOfString = new String[localVector.size()];
		localVector.toArray(arrayOfString);
		return arrayOfString;
	}

	public Object getPrivateDate(long paramLong, String paramString) {
		return ((AUValue) activeStore.get(new Long(paramLong))).getPersonalData(paramString);
	}

	public void setPrivateDate(long paramLong, String paramString, Object paramObject) {
		((AUValue) activeStore.get(new Long(paramLong))).setPersonalData(paramString, paramObject);
	}

	private long createSessionID() {
		long l = 0x7FFFFFFF & System.currentTimeMillis();
		return l << 32 | Math.abs(random.nextInt());
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

	class AUValue {
		private String userName = null;
		private long lastTime = 0L;
		private Hashtable<String, Object> personalStore = new Hashtable();
		private Date logonTime = null;
		private String IP = null;

		public AUValue() {
		}

		protected void setUserName(String paramString) {
			this.userName = paramString;
		}

		protected String getUserName() {
			return this.userName;
		}

		protected void setIP(String paramString) {
			this.IP = paramString;
		}

		protected String getIP() {
			return this.IP;
		}

		protected void setPersonalData(String paramString, Object paramObject) {
			this.personalStore.put(paramString, paramObject);
		}

		protected Object getPersonalData(String paramString) {
			return this.personalStore.get(paramString);
		}

		protected void setLastTime(long paramLong) {
			this.lastTime = paramLong;
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

	static class AUThread extends Thread {
		private Hashtable activeStore = null;
		private int space;
		private long expireTime;

		public AUThread(Hashtable paramHashtable, int paramInt, long paramLong) {
			this.space = paramInt;
			this.expireTime = paramLong;
			this.activeStore = paramHashtable;
		}

		public void run() {
			while (ActiveUserManager.auThreadFlag) {
				try {
					sleep(this.space);
					checkExpire();
				} catch (InterruptedException localInterruptedException) {
					System.out.println("AU 监视线程失败");
				} catch (NullPointerException localNullPointerException) {
					System.out.println("checkExire catch NullPointer");
				}
			}
		}

		private void checkExpire() {
			Long localLong = null;
			ActiveUserManager.AUValue localAUValue = null;
			Enumeration localEnumeration = this.activeStore.keys();
			long l = System.currentTimeMillis();
			while (localEnumeration.hasMoreElements()) {
				localLong = (Long) localEnumeration.nextElement();
				localAUValue = (ActiveUserManager.AUValue) this.activeStore.get(localLong);
				if (l - localAUValue.getLastTime() > this.expireTime) {
					if (localAUValue.getUserName() != null) {
						ActiveUserManager.decreaseUserCount();
					}
					this.activeStore.remove(localLong);
				}
			}
		}

		public synchronized void wakeup() {
			notify();
		}
	}
}
