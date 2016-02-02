package gnnt.MEBS.timebargain.server.model;

import gnnt.MEBS.timebargain.server.engine.PriceOrder;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeMonitor implements Serializable {


	private static final long serialVersionUID = 3690197650654049123L;
	 
	public TradeMonitor(){ 
		waitOrders = new LinkedList<Order>();
		buyQueue = new LinkedList<Order>();
		sellQueue = new LinkedList<Order>();
	}
	 
	/**
	 * 等待撮合队列
	 *   
	 */
	private List<Order> waitOrders;
	/**
	 * 买队列
	 *  
	 */
	public List<Order> buyQueue;
	/**
	 * 卖队列
	 * 
	 */
	public List<Order> sellQueue; 
 
	/**
	 *  扩展字段，可以用来存放一些数据
	 */
	public Map<String,Integer> map;
	

	public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}


	public List<Order> getBuyQueue() {
		return buyQueue;
	}


	public void setBuyQueue(List<Order> buyQueue) {
		this.buyQueue = buyQueue;
	}


	public List<Order> getSellQueue() {
		return sellQueue;
	}


	public void setSellQueue(List<Order> sellQueue) {
		this.sellQueue = sellQueue;
	}


	public List<Order> getWaitOrders() {
		return waitOrders;
	}


	public void setWaitOrders(List<Order> waitOrders) {
		this.waitOrders = waitOrders;
	}
}
