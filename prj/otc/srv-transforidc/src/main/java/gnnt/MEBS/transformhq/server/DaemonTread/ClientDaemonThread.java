package gnnt.MEBS.transformhq.server.DaemonTread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.transformhq.server.model.HQBean;
import gnnt.MEBS.transformhq.server.model.IPConfig;
import gnnt.MEBS.transformhq.server.model.InCommodity;
import gnnt.MEBS.transformhq.server.model.TimeOut;
import gnnt.MEBS.transformhq.server.tools.CompareDateTools;
import gnnt.MEBS.transformhq.server.tools.foctory.HQBeanFactory;

public class ClientDaemonThread extends Thread {
	private Log log = LogFactory.getLog(ClientDaemonThread.class);
	private IPConfig ipconfig;
	private long daemonTime = 0L;
	private long lastReceiveHQTime = 0L;
	private Map<String, InCommodity> inCommodity;
	private Map<String, Long> cmdtyTime = new ConcurrentHashMap();
	private long initTime;
	private ReconnectionInterFace reconnection;
	private Map<String, List<TimeOut>> timeOut;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");

	public ClientDaemonThread(IPConfig ipconfig, Map<String, InCommodity> inCommodity, ReconnectionInterFace reconnection) {
		this.ipconfig = ipconfig;
		this.daemonTime = (ipconfig.getDemoTime() * 1000L);
		this.lastReceiveHQTime = System.currentTimeMillis();
		this.inCommodity = inCommodity;
		this.initTime = Long.valueOf(HQBeanFactory.getConfig("initTime").replace(":", "")).longValue();
		this.reconnection = reconnection;
		this.timeOut = ((Map) HQBeanFactory.getBean("timeOutSet"));
		Iterator<String> iterator = inCommodity.keySet().iterator();
		while (iterator.hasNext()) {
			this.cmdtyTime.put((String) iterator.next(), Long.valueOf(System.currentTimeMillis()));
		}
	}

	public void run() {
		try {
			for (;;) {
				initTime();

				checkReceiveTime();

				checkCommodityTime();
				sleep(1000L);
			}
		} catch (Exception e) {
			this.log.error("check HQ road error:" + e);
		}
	}

	public void initTime() {
		if (Integer.valueOf(this.dateFormat.format(new Date())).intValue() >= this.initTime) {
			if (Integer.valueOf(this.dateFormat.format(new Date(this.lastReceiveHQTime))).intValue() < this.initTime) {
				this.lastReceiveHQTime = System.currentTimeMillis();
				this.log.warn("初始化接收行情时间成功：" + this.initTime);
			}
			Iterator<String> iterator = this.cmdtyTime.keySet().iterator();
			while (iterator.hasNext()) {
				String commodityId = (String) iterator.next();
				long time = ((Long) this.cmdtyTime.get(commodityId)).longValue();
				if (Integer.valueOf(this.dateFormat.format(new Date(time))).intValue() < this.initTime) {
					this.cmdtyTime.put(commodityId, Long.valueOf(System.currentTimeMillis()));
					this.log.warn(commodityId + " 商品初始化接收时间成功：" + this.initTime);
				}
			}
		}
	}

	public void resetHQTime(HQBean hqBean) {
		this.lastReceiveHQTime = System.currentTimeMillis();
		if (this.cmdtyTime.containsKey(hqBean.getCommodityID())) {
			this.cmdtyTime.put(hqBean.getCommodityID(), Long.valueOf(System.currentTimeMillis()));
		}
	}

	private boolean checkReceiveTime() {
		if (this.daemonTime <= 0L) {
			return true;
		}
		if (System.currentTimeMillis() - this.lastReceiveHQTime >= this.daemonTime) {
			this.log.warn("线路超时没有收到行情：" + this.ipconfig.toString() + " 超时时间为:" + (System.currentTimeMillis() - this.lastReceiveHQTime));
			this.lastReceiveHQTime = System.currentTimeMillis();
			if (this.reconnection != null) {
				this.reconnection.reconnection();
			}
			return false;
		}
		return true;
	}

	private boolean checkCommodityTime() {
		Iterator<String> iterator = this.inCommodity.keySet().iterator();
		while (iterator.hasNext()) {
			String commodityId = (String) iterator.next();

			long timeOut = getTimeOut(commodityId);
			if ((timeOut > 0L) && (System.currentTimeMillis() - ((Long) this.cmdtyTime.get(commodityId)).longValue() > timeOut * 1000L)) {
				this.log.warn("商品" + commodityId + "超时没有收到行情:" + this.ipconfig.toString() + " 超时时间为："
						+ (System.currentTimeMillis() - ((Long) this.cmdtyTime.get(commodityId)).longValue()));
				this.ipconfig.changeStatusFalse();
				return false;
			}
		}
		this.ipconfig.changeStatusTrue();
		return true;
	}

	private long getTimeOut(String commodityId) {
		String inCommodityId = ((InCommodity) this.inCommodity.get(commodityId)).getInCommodityId();
		if (this.timeOut.containsKey(inCommodityId)) {
			for (TimeOut to : this.timeOut.get(inCommodityId)) {
				if (CompareDateTools.compareDate(to.getBeginTime(), to.getEndTime())) {
					return to.getTimeOut();
				}
			}
		}
		return ((InCommodity) this.inCommodity.get(commodityId)).getTimeOut();
	}
}
