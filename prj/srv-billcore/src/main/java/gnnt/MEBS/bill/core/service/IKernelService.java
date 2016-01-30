package gnnt.MEBS.bill.core.service;

import java.util.List;
import java.util.Map;

import gnnt.MEBS.bill.core.bo.StockOutApplyBO;
import gnnt.MEBS.bill.core.bo.StockOutAuditBO;
import gnnt.MEBS.bill.core.po.GoodsPropertyPO;
import gnnt.MEBS.bill.core.po.StockPO;
import gnnt.MEBS.bill.core.vo.AddStockResultVO;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.bill.core.vo.StockAddVO;
import gnnt.MEBS.bill.core.vo.StockQiantityResultVO;

public abstract interface IKernelService {
	public abstract AddStockResultVO addStock(StockPO paramStockPO, List<GoodsPropertyPO> paramList);

	public abstract ResultVO registerStock(String paramString);

	public abstract ResultVO dismantleStock(String paramString, Double[] paramArrayOfDouble);

	public abstract ResultVO dismantleStock(String paramString, boolean paramBoolean, Map<String, String> paramMap);

	public abstract ResultVO logoutStock(String paramString);

	public abstract ResultVO stockOutApply(StockOutApplyBO paramStockOutApplyBO);

	public abstract ResultVO stockOutUpdate(StockOutApplyBO paramStockOutApplyBO);

	public abstract ResultVO stockOutAudit(StockOutAuditBO paramStockOutAuditBO);

	public abstract ResultVO withdrowStockOutApply(String paramString);

	public abstract AddStockResultVO addStockForWarehouseServer(StockAddVO paramStockAddVO);

	public abstract ResultVO stockOut(String paramString1, String paramString2, String paramString3, String paramString4);

	public abstract String getConfigInfo(String paramString);

	public abstract List<String> getUnusedStocks(long paramLong, Map<String, String> paramMap, String paramString, double paramDouble);

	public abstract List<String> getUnusedStocks(long paramLong, Map<String, String> paramMap, String paramString);

	public abstract List<String> getUnusedStocks(int paramInt, String paramString);

	public abstract StockQiantityResultVO getQuantityByStockIDs(List<String> paramList);

	// /**
	// * 录入仓单 准备条件：交易商将商品存入仓库，仓库系统扣留交易商纸质仓单，此仓单出库只能使用密钥
	// *
	// * @param stockPO
	// * 仓单
	// * @param propertyList
	// * 仓单属性列表
	// *
	// * @return
	// */
	// public AddStockResultVO addStock(StockPO stockPO,
	// List<GoodsPropertyPO> propertyList);
	//
	// /**
	// * 注册仓单<br>
	// * 修改仓单状态并且通知仓库系统该仓单已注册将不能出库
	// *
	// * @param stockID
	// * 仓单号
	// * @return
	// */
	// public ResultVO registerStock(String stockID);
	//
	// /**
	// * 拆仓单
	// *
	// * @param stockID
	// * 仓单号
	// * @param amountarr
	// * 拆单后数量数组
	// * @return
	// */
	// public ResultVO dismantleStock(String stockID, Double[] amountarr);
	//
	// /**
	// * 拆仓单审核
	// *
	// * @param stockID
	// * 仓单号
	// * @param result
	// * false 失败 true 成功
	// * @param dismantleMap
	// * 拆单成功后使用 key:拆单编码 value:拆单后仓库仓单号
	// * @return
	// */
	// public ResultVO dismantleStock(String stockID, boolean result,
	// Map<String, String> dismantleMap);
	//
	// /**
	// * 注销仓单
	// *
	// * @param stockID
	// * 仓单号
	// * @return
	// */
	// public ResultVO logoutStock(String stockID);
	//
	// /**
	// * 仓单出库<br>
	// * 生成出库密钥 通知仓库出库密钥
	// *
	// * @param stockID
	// * 仓单号
	// * @return result结果大于1为仓单密钥
	// */
	// public ResultVO stockOut(String stockID);
	//
	// /**
	// * 开放给仓库系统注册仓单使用的方法
	// *
	// * @param request
	// * 注册仓单仓库传来对象
	// * @return AddStockResultVO
	// */
	// public AddStockResultVO addStockForWarehouseServer(StockAddVO stockAddVO);
	//
	// /**
	// * 仓单出库 需要向仓库系统发送验证时调用
	// *
	// * @param stockID
	// * 仓单号
	// * @param userID
	// * 仓库系统账户编号
	// * @param userName
	// * 仓库系统账户名
	// * @param password
	// * 仓库系统验证密码
	// * @return ResultVO 结果大于1为仓单密钥
	// */
	// public ResultVO stockOut(String stockID, String userID, String userName,
	// String password);
	//
	//
	// /**
	// * 获取交易核心配置信息
	// *
	// * @param key
	// * 配置信息键值
	// * @return 对应的值 如果传入的键值不存在则返回null
	// */
	// public String getConfigInfo(String key);
	//
	// /**
	// *
	// * 方法说明：通过品名编号、属性信息、交易商代码、仓单最大数量获取未使用的已注册仓单
	// * <br/>
	// * <br/>
	// *
	// * @param breedID 品名编号
	// * @param propertys 属性信息
	// * @param firmID 交易商代码
	// * @param quantity 仓单最大数量
	// * @return List<String> 仓单编号集合
	// */
	// public List<String> getUnusedStocks(long breedID,
	// Map<String,String> propertys,String firmID,double quantity);
	//
	// /**
	// *
	// * 方法说明：通过品名编号、属性信息、交易商代码获取未使用的已注册仓单
	// * <br/>
	// * <br/>
	// *
	// * @param breedID 品名编号
	// * @param propertys 属性信息
	// * @param firmID 交易商代码
	// * @return List<String> 仓单编号集合
	// */
	// public List<String> getUnusedStocks(long breedID,
	// Map<String,String> propertys,String firmID);
	//
	// /**
	// *
	// * 通过模块编号，交易商代码获取本交易商在当个模块的所有未使用仓单
	// * <br/><br/>
	// * @param moduleid 模块编号
	// * @param firmID 交易商代码
	// * @return List<String> 仓单编号集合
	// */
	// public List<String> getUnusedStocks(int moduleid,String firmID);
	//
	// /**
	// *
	// * 方法说明：通过仓单编号集合，获取仓单数量的总和
	// * <br/><br/>
	// *
	// * @param stockIDs 仓单编号集合
	// * @return StockQiantityResultVO
	// */
	// public StockQiantityResultVO getQuantityByStockIDs(List<String> stockIDs);

}
