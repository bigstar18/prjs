package gnnt.MEBS.timebargain.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

public class ActiveUserManager {
	private static int SINGLE_MODE = 1;
	private static int MULTI_MODE = 2;
	private Hashtable activeStore = new Hashtable(600);
	private AUThread auThread = null;
	private Random random = new Random();
	private int userCount = 0;
	private Object countLock = new Object();
	private boolean auThreadFlag = false;
	private int mode = MULTI_MODE;
	private int space = 30;
	private int expireTime = 15;

	public ActiveUserManager() {
		if (!this.auThreadFlag) {
			this.auThreadFlag = true;
			this.auThread = new AUThread(this.activeStore, this.space * 1000, this.expireTime * 60000);
			this.auThread.start();
		}
	}

	public ActiveUserManager(int paramInt1, int paramInt2, int paramInt3) {
		if (!this.auThreadFlag) {
			this.space = paramInt1;
			this.expireTime = paramInt2;
			this.mode = paramInt3;
			this.auThreadFlag = true;
			this.auThread = new AUThread(this.activeStore, paramInt1 * 1000, paramInt2 * 60000);
			this.auThread.start();
		}
	}

	public long logon(String paramString) {
		return logon(paramString, null);
	}

	public long logon(String paramString1, String paramString2) {
		AUValue localAUValue1 = new AUValue();
		localAUValue1.setUserName(paramString1);
		localAUValue1.setLogonIP(paramString2);
		long l;
		for (l = createSessionID(); this.activeStore.containsKey(new Long(l)); l = createSessionID()) {
		}
		if (this.mode == SINGLE_MODE) {
			Enumeration localEnumeration = this.activeStore.keys();
			AUValue localAUValue2 = null;
			while (localEnumeration.hasMoreElements()) {
				Long localLong = (Long) localEnumeration.nextElement();
				localAUValue2 = (AUValue) this.activeStore.get(localLong);
				if (localAUValue2.getUserName().equals(paramString1)) {
					this.activeStore.remove(localLong);
					decreaseUserCount();
				}
			}
			this.activeStore.put(new Long(l), localAUValue1);
		} else if (this.mode == MULTI_MODE) {
			this.activeStore.put(new Long(l), localAUValue1);
		} else {
			return -1L;
		}
		addUserCount();
		return l;
	}

	public void logoff(long paramLong) {
		this.activeStore.remove(new Long(paramLong));
		decreaseUserCount();
	}

	public int logoffUser(String paramString) {
		int i = 0;
		Enumeration localEnumeration = this.activeStore.keys();
		AUValue localAUValue = null;
		while (localEnumeration.hasMoreElements()) {
			Long localLong = (Long) localEnumeration.nextElement();
			localAUValue = (AUValue) this.activeStore.get(localLong);
			if (localAUValue.getUserName().equals(paramString)) {
				this.activeStore.remove(localLong);
				decreaseUserCount();
				i++;
			}
		}
		return i;
	}

	public String getUserID(long paramLong) {
		AUValue localAUValue = (AUValue) this.activeStore.get(new Long(paramLong));
		if (localAUValue == null) {
			return null;
		}
		localAUValue.setLastTime(System.currentTimeMillis());
		return localAUValue.getUserName();
	}

	public String[] getAllUsers() {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Enumeration localEnumeration = this.activeStore.elements();
		Vector localVector = new Vector();
		AUValue localAUValue = null;
		StringBuffer localStringBuffer = null;
		while (localEnumeration.hasMoreElements()) {
			localAUValue = (AUValue) localEnumeration.nextElement();
			localStringBuffer = new StringBuffer(localAUValue.getUserName());
			localStringBuffer.append(",");
			localStringBuffer.append(localSimpleDateFormat.format(localAUValue.getLogonTime()));
			localStringBuffer.append(",");
			localStringBuffer.append(localAUValue.getLogonIP());
			localVector.addElement(localStringBuffer.toString());
		}
		String[] arrayOfString = new String[localVector.size()];
		localVector.toArray(arrayOfString);
		return arrayOfString;
	}

	public Object getPrivateDate(long paramLong, String paramString) {
		return ((AUValue) this.activeStore.get(new Long(paramLong))).getPersonalData(paramString);
	}

	public void setPrivateDate(long paramLong, String paramString, Object paramObject) {
		((AUValue) this.activeStore.get(new Long(paramLong))).setPersonalData(paramString, paramObject);
	}

	private long createSessionID() {
		long l = 0x7FFFFFFF & System.currentTimeMillis();
		return l << 32 | Math.abs(this.random.nextInt());
	}

	protected void addUserCount() {
		synchronized (this.countLock) {
			this.userCount += 1;
		}
	}

	protected void decreaseUserCount() {
		synchronized (this.countLock) {
			if (this.userCount > 0) {
				this.userCount -= 1;
			}
		}
	}

	class AUValue {
		private String userName = null;
		private long lastTime = 0L;
		private Hashtable personalStore = new Hashtable();
		private Date logonTime = null;
		private String logonIP = null;

		public AUValue() {
		}

		protected void setUserName(String paramString) {
			this.userName = paramString;
		}

		protected String getUserName() {
			return this.userName;
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

		protected void setLogonIP(String paramString) {
			this.logonIP = paramString;
		}

		protected String getLogonIP() {
			return this.logonIP;
		}
	}

	class AUThread extends Thread {
		private Hashtable activeStore = null;
		private int space;
		private long expireTime;

		public AUThread(Hashtable paramHashtable, int paramInt, long paramLong) {
			this.space = paramInt;
			this.expireTime = paramLong;
			this.activeStore = paramHashtable;
		}

		public void run() {
			while (ActiveUserManager.this.auThreadFlag) {
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
						ActiveUserManager.this.decreaseUserCount();
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
