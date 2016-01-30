package gnnt.MEBS.timebargain.mgr.util;

public class MonitorSet {
	private int refreshTime;
	private int pageSize;
	private static MonitorSet instance = null;

	public static synchronized MonitorSet getInstance() {
		if (instance == null)
			instance = new MonitorSet();
		return instance;
	}

	private MonitorSet() {
		String pageSize = RWProperty.readValue("monitorConfig.properties", "pageSize");
		this.pageSize = parseInt(pageSize, 10);
		String timerInterval = RWProperty.readValue("monitorConfig.properties", "timerInterval");
		this.refreshTime = parseInt(timerInterval, 5);
	}

	public static int parseInt(String source, int defaultValue) {
		try {
			return Integer.parseInt(source);
		} catch (NumberFormatException e) {
		}
		return defaultValue;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		RWProperty.writeProperties("monitorConfig.properties", "pageSize", pageSize + "");
		this.pageSize = pageSize;
	}

	public int getRefreshTime() {
		return this.refreshTime;
	}

	public void setRefreshTime(int refreshTime) {
		RWProperty.writeProperties("monitorConfig.properties", "timerInterval", refreshTime + "");
		this.refreshTime = refreshTime;
	}
}