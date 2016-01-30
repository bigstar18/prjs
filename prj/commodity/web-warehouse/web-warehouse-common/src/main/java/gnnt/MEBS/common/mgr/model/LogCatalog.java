package gnnt.MEBS.common.mgr.model;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

/**
 * 日志分类
 * 
 * @author xuejt
 * 
 */
public class LogCatalog extends StandardModel {
	private static final long serialVersionUID = -4112756031753238119L;

	/**日志类别号*/
	@ClassDiscription(name="日志类别号",description="")
	private Integer catalogID;

	/**日志类别名称*/
	@ClassDiscription(name="日志类别名称",description="")
	private String catalogName;

	/**日志所属模块类*/
	private TradeModule tradeModule;

	/**
	 * 日志类别号
	 * 
	 * @return
	 */
	public Integer getCatalogID() {
		return catalogID;
	}

	/**
	 * 日志类别号
	 * 
	 * @param catalogID
	 */
	public void setCatalogID(Integer catalogID) {
		this.catalogID = catalogID;
	}


	/**
	 * 日志类别名称
	 * 
	 * @return
	 */
	public String getCatalogName() {
		return catalogName;
	}

	/**
	 * 日志类别名称
	 * 
	 * @param catalogName
	 */
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	/**
	 * 日志所属模块类
	 * 
	 * @return
	 */
	public TradeModule getTradeModule() {
		return tradeModule;
	}

	/**
	 * 日志所属模块类
	 * 
	 * @param tradeModule
	 */
	public void setTradeModule(TradeModule tradeModule) {
		this.tradeModule = tradeModule;
	}

	@Override
	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("catalogID", this.catalogID);
	}

}
