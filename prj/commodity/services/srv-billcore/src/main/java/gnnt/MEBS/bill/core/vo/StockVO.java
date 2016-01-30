
package gnnt.MEBS.bill.core.vo;

import gnnt.MEBS.bill.core.po.GoodsPropertyPO;
import gnnt.MEBS.bill.core.po.StockPO;

import java.util.ArrayList;
import java.util.List;

/**
 * <P>类说明：包含有仓单表信息及其仓单属性表信息
 * <br/>
 * <br/>
 * </p>
 * 修改记录:<br/>
 * <ul>
 * <li> 创建仓单对外类                    |2013-4-10上午10:11:56|金网安泰 </li>
 * </ul>
 * @author liuzx
 */
public class StockVO extends ResultVO {
	/** 返回对象序列编号<br/> */
	private static final long serialVersionUID = -5589801656519651429L;
	/** 仓单表对象 */
	private StockPO stock;
	/** 仓单属性表对象集合 */
	private List<GoodsPropertyPO> stockPropertys;

	/**
	 * 
	 * 仓单表对象<br/>
	 * <br/>
	 *
	 * @return StockPO
	 */
	public StockPO getStock() {
		return stock;
	}

	/**
	 * 
	 * 仓单表对象<br/>
	 * <br/>
	 *
	 * @param stock
	 */
	public void setStock(StockPO stock) {
		this.stock = stock;
	}

	/**
	 * 
	 * 仓单属性表对象集合<br/>
	 * <br/>
	 *
	 * @return List<GoodsPropertyPO>
	 */
	public List<GoodsPropertyPO> getStockPropertys() {
		return stockPropertys;
	}

	/**
	 * 
	 * 仓单属性表对象集合<br/>
	 * <br/>
	 *
	 * @param stockPropertys
	 */
	public void setStockPropertys(List<GoodsPropertyPO> stockPropertys) {
		this.stockPropertys = stockPropertys;
	}

	/**
	 * 
	 * 增加仓单属性对象<br/>
	 * <br/>
	 *
	 * @param stockPropertys
	 */
	public void addStockProperty(GoodsPropertyPO stockProperty){
		if(stockPropertys == null){
			synchronized(this){
				if(stockPropertys == null){
					stockPropertys = new ArrayList<GoodsPropertyPO>();
				}
			}
		}
		stockPropertys.add(stockProperty);
	}

}

