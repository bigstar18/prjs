package gnnt.MEBS.transformhq.server.PricefiterInterFace.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.transformhq.server.PricefiterInterFace.BurrPriceFiterInterFace;
import gnnt.MEBS.transformhq.server.model.Glitch;
import gnnt.MEBS.transformhq.server.model.HQBean;
import gnnt.MEBS.transformhq.server.model.InCommodity;
import gnnt.MEBS.transformhq.server.model.PriceFilter;
import gnnt.MEBS.transformhq.server.tools.CompareDateTools;

public class BurrPriceFilterImpl implements BurrPriceFiterInterFace {
	private Log log = LogFactory.getLog(BurrPriceFilterImpl.class);
	private HashMap<String, List<Glitch>> glitchSet;
	private Glitch defaultGlitch;
	private Glitch glitch;
	private Map<String, PriceFilter> priceMap;
	private PriceFilter priceFilter;
	private double priceDiff;
	private Map<String, String> cmdtyMap = new HashMap();
	private int result = 0;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");

	public void setDefaultGlitch(Glitch defaultGlitch) {
		this.defaultGlitch = defaultGlitch;
	}

	public void setGlitchSet(HashMap<String, List<Glitch>> glitchSet) {
		this.glitchSet = glitchSet;
	}

	public LinkedList<HQBean> getGlitchPLst(String commodityID) {
		return ((PriceFilter) this.priceMap.get(commodityID)).getGlitchPLst();
	}

	public BurrPriceFilterImpl() {
		this.priceMap = new HashMap();
	}

	public int checkPrice(HQBean hqBean) {
		double lastPrice;
		String commodityID;
		lastPrice = Double.parseDouble(hqBean.getPrice());
		commodityID = hqBean.getCommodityID();
		if (Double.parseDouble(hqBean.getFormerPrice()) == 0.0D) {
			log.info((new StringBuilder("检测到毛刺数据，毛刺数据将被剔除...")).append(hqBean.toString()).toString());
			return 1;
		}
		try {
			if (!priceMap.containsKey(commodityID)) {
				PriceFilter priceFilters = new PriceFilter();
				priceFilters.setCommodityID(hqBean.getCommodityID());
				priceFilters.setNormalPrice(lastPrice);
				priceMap.put(hqBean.getCommodityID(), priceFilters);
			}
			priceFilter = (PriceFilter) priceMap.get(commodityID);
			if (glitchSet.containsKey(cmdtyMap.get(commodityID))) {
				for (Iterator iterator = ((List) glitchSet.get(cmdtyMap.get(commodityID))).iterator(); iterator.hasNext();) {
					Glitch g = (Glitch) iterator.next();
					if (CompareDateTools.compareDate(g.getBeginTime(), g.getEndTime()))
						glitch = g;
				}

			}
			if (glitch == null)
				glitch = defaultGlitch;
			restoreSite(Double.valueOf(lastPrice));
			if (priceFilter.isExist()) {
				if (verifyPrice(lastPrice, priceFilter.getComparePrice(), glitch.getRange())) {
					HQBean hqBeans;
					for (Iterator iterator1 = priceFilter.getGlitchPLst().iterator(); iterator1.hasNext(); log
							.info((new StringBuilder("检测到毛刺数据,毛刺数据将被剔除....")).append(hqBeans.toString()).toString()))
						hqBeans = (HQBean) iterator1.next();

					priceFilter.getGlitchPLst().clear();
					if (verifyPrice(lastPrice, priceFilter.getNormalPrice(), glitch.getRange())) {
						priceFilter.getGlitchPLst().add(hqBean);
						priceFilter.setComparePrice(lastPrice);
						result = 1;
						log.info((new StringBuilder("检查到可疑数据....")).append(hqBean.toString()).toString());
					} else {
						priceFilter.setNormalPrice(lastPrice);
						priceFilter.setExist(false);
						result = 0;
					}
				} else {
					priceFilter.getGlitchPLst().add(hqBean);
					if (priceFilter.getGlitchPLst().size() >= glitch.getFrequency()) {
						HQBean hqBeans;
						for (Iterator iterator2 = priceFilter.getGlitchPLst().iterator(); iterator2.hasNext(); log
								.debug((new StringBuilder("此可疑数据为正常数据，将被推送到客户端....")).append(hqBeans.toString()).toString()))
							hqBeans = (HQBean) iterator2.next();

						priceFilter.setNormalPrice(lastPrice);
						priceFilter.setExist(false);
						result = 2;
					} else {
						result = 1;
					}
				}
			} else if (verifyPrice(lastPrice, priceFilter.getNormalPrice(), glitch.getRange())) {
				priceFilter.setComparePrice(lastPrice);
				priceFilter.getGlitchPLst().add(hqBean);
				priceFilter.setExist(true);
				result = 1;
				log.info((new StringBuilder("检查到可疑数据.....")).append(hqBean.toString()).toString());
			} else {
				priceFilter.setNormalPrice(lastPrice);
				result = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error((new StringBuilder("过滤数据发生异常.....异常原因.....")).append(e.toString()).toString());
			result = 0;
		}
		return result;
	}

	private void restoreSite(Double price) {
		if (this.priceFilter.getGlitchPLst().size() > this.glitch.getFrequency()) {
			this.priceFilter.getGlitchPLst().clear();
			this.priceFilter.setNormalPrice(price.doubleValue());
			this.priceFilter.setExist(false);
		}
	}

	public boolean verifyPrice(double lastPrice, double comparesPrice, int rules) {
		this.priceDiff = (lastPrice - comparesPrice);
		boolean isGiltch;
		if (Math.abs(this.priceDiff / comparesPrice) > rules) {
			isGiltch = true;
		} else {
			isGiltch = false;
		}
		return isGiltch;
	}

	public boolean verifyPrice(double lastPrice, double comparesPrice, double rules) {
		this.priceDiff = (lastPrice - comparesPrice);
		boolean isGiltch;
		if (Math.abs(this.priceDiff) > rules) {
			isGiltch = true;
		} else {
			isGiltch = false;
		}
		return isGiltch;
	}

	public void setInCommodity(Map<String, InCommodity> inCommodity) {
		Iterator<String> iterator = inCommodity.keySet().iterator();
		while (iterator.hasNext()) {
			String commodityId = (String) iterator.next();
			this.cmdtyMap.put(commodityId, ((InCommodity) inCommodity.get(commodityId)).getInCommodityId());
		}
	}
}
