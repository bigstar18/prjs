package org.hxx.aut.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hxx.aut.dao.ISchoolDao;
import org.hxx.aut.dao.SqlServerSchoolDao;
import org.hxx.aut.model.SchoolHandler;
import org.hxx.util.IAuthentication;

public class AutService {
	public static Logger LOG = LoggerFactory.getLogger(AutService.class);

	private static ISchoolDao dao = new SqlServerSchoolDao();

	private static Map<String, String> userResult = new ConcurrentHashMap<String, String>();
	private static Map<String, AtomicInteger> schoolCount = new HashMap<String, AtomicInteger>();
	private static Map<String, IAuthentication> schoolHandler = new HashMap<String, IAuthentication>();
	private static BlockingQueue<String[]> users = new ArrayBlockingQueue<String[]>(
			10000, true);
	static {
		initThread();
	}

	private static void initThread() {
		for (int i = 1; i <= 20; i++) {
			final int num = i;
			Thread t = new Thread() {
				public void run() {
					Thread.currentThread().setName("autThread" + num);
					LOG.info(Thread.currentThread() + ": 线程启动");

					while (true) {
						String[] userData = null;
						try {
							userData = users.poll(4, TimeUnit.SECONDS);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						if (userData != null) {// 待处理数据
							AtomicInteger schoolDoing = getSchoolDoing(userData[0]);

							if (!canSchoolDo(schoolDoing)) {// too mutch
								try {
									Thread.sleep(1000l);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								users.add(userData);// re add;
							} else {
								doAut(schoolDoing, userData);// verifying and
																// put result;
							}
						}
					}
				}
			};
			t.setDaemon(true);
			t.start();
		}
	}

	private synchronized static AtomicInteger getSchoolDoing(String schoolCode) {
		AtomicInteger schoolDoing = null;
		if (schoolCount.containsKey(schoolCode)) {
			schoolDoing = schoolCount.get(schoolCode);
		} else {
			schoolDoing = new AtomicInteger(0);
			schoolCount.put(schoolCode, schoolDoing);
		}
		return schoolDoing;
	}

	private static boolean canSchoolDo(AtomicInteger schoolDoing) {
		synchronized (schoolDoing) {
			if (schoolDoing.get() > 2)// 并发最多3个
				return false;
			else {
				if (schoolDoing.incrementAndGet() > 1) {// 计数增加// 间隔500
					try {
						Thread.sleep(500l);
					} catch (InterruptedException e) {
					}
				}
				// 第一个，立刻
				return true;
			}
		}
	}

	private synchronized static IAuthentication getSchoolHandler(
			String schoolCode) {
		if (!schoolHandler.containsKey(schoolCode)) {
			try {// load
				SchoolHandler rs = dao.load(schoolCode);
				schoolHandler.put(schoolCode,
						(IAuthentication) Class.forName(rs.getHandler())
								.newInstance());
			} catch (Exception e) {
				LOG.error("getSchoolHandler:", e);
				schoolHandler.put(schoolCode, null);
				return null;
			}
		}
		return schoolHandler.get(schoolCode);
	}

	private static void doAut(AtomicInteger schoolDoing, String[] userData) {
		IAuthentication handler = getSchoolHandler(userData[0]);

		if (handler == null)// 没有处理器
			userResult.put(userData[2] + userData[3], "408");
		else {
			try {
				if (handler.authenticate(userData[2], userData[1], userData[3])) {
					userResult.put(userData[2] + userData[3], "407");// success
				} else {
					userResult.put(userData[2] + userData[3], "406");// failure
				}
			} catch (Exception e) {
				userResult.put(userData[2] + userData[3], "410");// exception
				LOG.error("aut failure:", e);
			}
		}
		schoolDoing.decrementAndGet();// 计数扣减
	}

	// 可能一开始输错了，code+pwd
	// 或者就是恶意验证访问 TODO
	public String authentication(String schoolCode, String userName,
			String userCode, String password) {
		String[] data = new String[] { schoolCode, userName, userCode, password };
		users.add(data);
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1100l);
			} catch (InterruptedException e) {
			}
			if (userResult.containsKey(userCode + password)) {
				String result = userResult.remove(userCode + password);
				return result;
			}
		}
		for (Iterator iterator = userResult.keySet().iterator(); iterator
				.hasNext();) {
			String userkey = (String) iterator.next();
			if (userkey.contains(userName))
				LOG.info("u=" + userCode + ",r=" + userResult.get(userkey));
		}
		return "409";
	}

}
