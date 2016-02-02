package gnnt.MEBS.transformhq.server.PricefiterInterFace.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.transformhq.server.PricefiterInterFace.SecondPriceFiterInterFace;
import gnnt.MEBS.transformhq.server.model.HQBean;
import gnnt.MEBS.transformhq.server.model.TopPrice;
import gnnt.MEBS.transformhq.server.quotation.CheckAndSendHQBean;
import gnnt.MEBS.transformhq.server.tools.foctory.HQBeanFactory;
import gnnt.MEBS.transformhq.server.util.Arith;

public class SecondTopPriceFiterImpl implements SecondPriceFiterInterFace {
	private Log log = LogFactory.getLog(SecondTopPriceFiterImpl.class);
	private CheckAndSendHQBean checkAndSendHQBean;
	public long lastCheckTime = 0L;
	private long cacheTime = 0L;
	public Map<String, List<HQBean>> priceMap = new HashMap();
	public Map<String, Map<String, TopPrice>> topPrice = new HashMap();
	List<String> garbageList = new ArrayList();
	DateFormat format2 = new SimpleDateFormat("yyyyMMddhhmm");

	public void init(CheckAndSendHQBean checkAndSendHQBean) {
		this.checkAndSendHQBean = checkAndSendHQBean;
		this.cacheTime = Integer.valueOf(HQBeanFactory.getConfig("cacheTime")).intValue();
		this.log.info("加载缓存最高价最低价阶段时间：" + this.cacheTime + "分钟");
		new checkPriceTread().start();
	}

	public void setHQBean(HQBean hqBean) {
		synchronized (this.priceMap) {
			priceCache(hqBean);
			if (!this.priceMap.containsKey(hqBean.getCommodityID())) {
				List<HQBean> priceList = new ArrayList();
				priceList.add(hqBean);
				this.priceMap.put(hqBean.getCommodityID(), priceList);
			} else {
				((List) this.priceMap.get(hqBean.getCommodityID())).add(hqBean);
			}
			this.log.info("缓存最新价完成 :" + hqBean.toString());
		}
	}

	public void priceCache(HQBean hqBean) {
		String date = this.format2.format(new Date());
		if (!this.topPrice.containsKey(hqBean.getCommodityID())) {
			Map<String, TopPrice> map = new HashMap();
			map.put(date, new TopPrice(Double.valueOf(hqBean.getPrice())));
			this.topPrice.put(hqBean.getCommodityID(), map);
		} else if (!((Map) this.topPrice.get(hqBean.getCommodityID())).containsKey(date)) {
			((Map) this.topPrice.get(hqBean.getCommodityID())).put(date, new TopPrice(Double.valueOf(hqBean.getPrice())));
		} else {
			TopPrice tp = (TopPrice) ((Map) this.topPrice.get(hqBean.getCommodityID())).get(date);
			if (Arith.compareTo(Double.valueOf(hqBean.getPrice()).doubleValue(), tp.getHighPrice().doubleValue()) > 0) {
				tp.setHighPrice(Double.valueOf(hqBean.getPrice()));
			} else if (Arith.compareTo(Double.valueOf(hqBean.getPrice()).doubleValue(), tp.getLowPrice().doubleValue()) < 0) {
				tp.setLowPrice(Double.valueOf(hqBean.getPrice()));
			}
		}
		for (String key : (this.topPrice.get(hqBean.getCommodityID())).keySet()) {
			if (Long.valueOf(date).longValue() - Long.valueOf(key).longValue() >= this.cacheTime) {
				this.garbageList.add(key);
			}
		}
		if (this.garbageList.size() > 0) {
			for (String key : this.garbageList) {
				((Map) this.topPrice.get(hqBean.getCommodityID())).remove(key);
			}
			this.garbageList.clear();
		}
		this.log.debug(hqBean.getCommodityID() + "商品最高价最低价价格队列：" + ((Map) this.topPrice.get(hqBean.getCommodityID())).size());
	}

	class checkPriceTread extends Thread {
		checkPriceTread() {
		}

		public void run() {
			try {
				for (;;) {
					if (System.currentTimeMillis() - SecondTopPriceFiterImpl.this.lastCheckTime >= 1000L) {
						synchronized (SecondTopPriceFiterImpl.this.priceMap) {
							for (List<HQBean> array : SecondTopPriceFiterImpl.this.priceMap.values()) {
								SecondTopPriceFiterImpl.this.log.debug("before checkPrice array size:" + array.size());
								if (array.size() != 0) {
									if (array.size() == 1) {
										SecondTopPriceFiterImpl.this.sendHQBean((HQBean) array.get(0));
										array.clear();
										SecondTopPriceFiterImpl.this.log.debug("end checkPrice array size ：" + array.size());
									} else if (array.size() > 1) {
										SecondTopPriceFiterImpl.this.chosePrice(array);
									}
									SecondTopPriceFiterImpl.this.log.debug("发送完价格之后，队列长度为：" + array.size());
								}
							}
						}
					}
					sleep(10L);
				}
			} catch (Exception e) {
				e.printStackTrace();
				SecondTopPriceFiterImpl.this.log.error("checkPriceThread error:" + e);
			}
		}
	}

	public void chosePrice(List<HQBean> priceList) {
		int topPriceplace = -1;
		int size = priceList.size();
		HQBean lastPrice = (HQBean) priceList.get(size - 1);
		for (int i = 0; i < size; i++) {
			HQBean hq = (HQBean) priceList.get(i);
			if (isTopPrice(Double.valueOf(Double.parseDouble(hq.getPrice())), hq.getCommodityID())) {
				sendHQBean(hq);
				topPriceplace = i;
				break;
			}
		}
		if (topPriceplace == -1) {
			sendHQBean((HQBean) priceList.get(0));
		}
		priceList.clear();
		if (topPriceplace != size) {
			priceList.add(lastPrice);
			this.log.info("将最后一笔价格放回缓存队列：" + lastPrice.toString());
		}
	}

	public void sendHQBean(HQBean hqBean) {
		this.checkAndSendHQBean.offerHQBean(hqBean);

		this.lastCheckTime = System.currentTimeMillis();
	}

	public boolean isTopPrice(Double hqPrice, String commodityId) {
		boolean isTopPrice = true;
		Date date = new Date();
		if (this.topPrice.get(commodityId) == null) {
			this.log.debug("验证返回为：" + isTopPrice + " 价格为：" + hqPrice);
			return isTopPrice;
		}
		for (String key : (this.topPrice.get(commodityId)).keySet()) {
			if (Long.valueOf(this.format2.format(date)).longValue() - Long.valueOf(key).longValue() >= this.cacheTime) {
				break;
			}
			this.log.debug("高价为：" + ((TopPrice) ((Map) this.topPrice.get(commodityId)).get(key)).getHighPrice() + " 低价为："
					+ ((TopPrice) ((Map) this.topPrice.get(commodityId)).get(key)).getLowPrice());
			if ((Arith.compareTo(hqPrice.doubleValue(),
					((TopPrice) ((Map) this.topPrice.get(commodityId)).get(key)).getHighPrice().doubleValue()) < 0)
					&& (Arith.compareTo(hqPrice.doubleValue(),
							((TopPrice) ((Map) this.topPrice.get(commodityId)).get(key)).getLowPrice().doubleValue()) > 0)) {
				isTopPrice = false;
				break;
			}
		}
		this.log.debug("验证返回为：" + isTopPrice + " 价格为：" + hqPrice);
		return isTopPrice;
	}
}
