package gnnt.MEBS.bill.core.service;

import gnnt.MEBS.bill.core.vo.DeliveryVO;
import gnnt.MEBS.bill.core.vo.FinancingApplyVO;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.bill.core.vo.TransferGoodsVO;

/**
 * 交易相关接口
 * 
 * @author xuejt
 * 
 */
public interface ITradeService {

	/**
	 * 为交收冻结仓单
	 * 
	 * @param moduleid
	 *            模块号
	 * @param tradeNO
	 *            合同号
	 * @param goodsArr
	 *            仓单数组
	 * @param firmID
	 *            交易商代码用来检查是否仓单所属人
	 * @return
	 */
	public TransferGoodsVO performTransferGoods(int moduleid, String tradeNO,
			String[] stockArr, String firmID);

	/**
	 * 释放交收占用的仓单
	 * 
	 * @param moduleid
	 *            模块号
	 * @param tradeNO
	 *            合同号
	 */
	public ResultVO performRealeseGoods(int moduleid, String tradeNO);

	/**
	 * 释放指定的交收占用的仓单
	 * 
	 * @param moduleid
	 *            模块号
	 * @param tradeNO
	 *            合同号
	 * @param stockArr
	 *            指定仓单释放
	 */
	public ResultVO performRealeseGoods(int moduleid, String tradeNO,
			String[] stockArr);

	/**
	 * 货权变更
	 * 
	 * @param goodsArr
	 *            仓单数组
	 * @param srcFirm
	 *            原货权人
	 * @param tarFirm
	 *            新货权人
	 */
	public ResultVO performStockChg(String[] goodsArr, String srcFirm,
			String tarFirm);

	/**
	 * 交收 ****释放仓单并进行货权转移
	 * 
	 * @param moduleid
	 *            模块号
	 * @param tradeNO
	 *            合同号
	 * @param srcFirm
	 *            原货权人
	 * @param tarFirm
	 *            新货权人
	 */
	public DeliveryVO performDelivery(int moduleid, String tradeNO,
			String srcFirm, String tarFirm);

	/**
	 * 交收 ****释放仓单并进行货权转移
	 * 
	 * @param moduleid
	 *            模块号
	 * @param tradeNO
	 *            合同号
	 * 
	 * @param stockArr
	 *            仓单数组
	 * @param srcFirm
	 *            原货权人
	 * @param tarFirm
	 *            新货权人
	 */
	public DeliveryVO performDelivery(int moduleid, String tradeNO,String[] stockArr, String srcFirm,
			String tarFirm);

	/**
	 * 卖仓单
	 * 
	 * @param moduleid
	 *            模块号
	 * @param orderID
	 *            委托号
	 * @param stockID
	 *            仓单号
	 * @return
	 */
	public ResultVO performSellStock(int moduleid, String orderID,
			String stockID);

	/**
	 * 撤销卖仓单
	 * 
	 * @param moduleid
	 *            模块号
	 * @param orderID
	 *            委托号
	 * @return
	 */
	public ResultVO performWithdrawSellStock(int moduleid, String orderID);

	/**
	 * 检查仓单系统是否已经启动
	 */
	public void checkStart();

	/**
	 * 卖仓单转交收
	 * 
	 * @param moduleid
	 *            模块号
	 * @param orderID
	 *            委托号
	 * @param firmID
	 *            交易商代码用来检查是否仓单所属人
	 * @return
	 */
	public TransferGoodsVO performSellStockToDelivery(int moduleid,
			String orderID, String tradeNO, String firmID);

	/**
	 * 
	 * 获取融资仓单号<br/>
	 * <br/>
	 *
	 * @return long
	 */
	public long getFinancingStockID();

	/**
	 * 仓单融资
	 * 
	 * @param stockID
	 *            仓单号
	 * @param financingStockID
	 * 			    融资仓单号
	 * @return
	 */
	public FinancingApplyVO performStartFinancing(String stockID,long financingStockID);

	/**
	 * 融资完毕
	 * 
	 * @param financingStockID
	 *            融资仓单编号
	 * @return
	 */
	public ResultVO performEndFinancing(long financingStockID);

	/**
	 * 拒绝融资
	 * 
	 * @param financingStockID
	 * @return
	 */
	public ResultVO performRejectFinancing(long financingStockID);

	/**
	 * 
	 * 冻结仓单
	 * <br/><br/>
	 * @param moduleid 模块编号
	 * @param stockArr 冻结仓单编号集合
	 * @return ResultVO
	 */
	public ResultVO frozenStocks(int moduleid,String[] stockArr);

	/**
	 * 
	 * 解冻被冻结的仓单
	 * <br/><br/>
	 * @param moduleid 模块编号
	 * @param stockArr 解冻仓单编号集合
	 * @return ResultVO ResultVO.getResult() 1：成功 0： 警告-1： 失败 -2： 没有找到要解冻的仓单
	 */
	public ResultVO unFrozenStocks(int moduleid,String[] stockArr);
}
