/**
 * 
 */
package gnnt.mebsv.hqservice.service;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.mebsv.hqservice.model.ClientSocket;
import gnnt.mebsv.hqservice.model.ProductDataVO;

/**
 * @author hxx
 *
 */
public class HQService2 extends HQService {
	Log log = LogFactory.getLog(getClass());

	static final String[] ignoreProperties = new String[] { "yesterBalancePrice", "closePrice", "openPrice", "highPrice", "lowPrice", "curPrice",
			"totalAmount", "totalMoney", "curAmount", "consignRate", "amountRate", "balancePrice", "reserveCount" };

	@Override
	public byte[] GetQuoteList(ClientSocket paramClientSocket) throws IOException {
		ProductDataVO[] arrayOfProductDataVO = this.quotation.getModifiedProductData(paramClientSocket);
		if (arrayOfProductDataVO.length == 0)
			return new byte[0];

		processIndices(arrayOfProductDataVO);

		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		localDataOutputStream.writeByte(5);
		localDataOutputStream.writeInt(arrayOfProductDataVO.length);

		for (int i = 0; i < arrayOfProductDataVO.length; i++) {
			localDataOutputStream.writeUTF(arrayOfProductDataVO[i].marketID);
			localDataOutputStream.writeUTF(arrayOfProductDataVO[i].code);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].yesterBalancePrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].closePrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].openPrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].highPrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].lowPrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].curPrice);
			localDataOutputStream.writeLong(arrayOfProductDataVO[i].totalAmount);
			localDataOutputStream.writeDouble(arrayOfProductDataVO[i].totalMoney);
			localDataOutputStream.writeInt(arrayOfProductDataVO[i].curAmount);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].consignRate);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].amountRate);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].balancePrice);
			localDataOutputStream.writeInt(arrayOfProductDataVO[i].reserveCount);
			localDataOutputStream.writeInt(arrayOfProductDataVO[i].buyAmount[0]);
			localDataOutputStream.writeInt(arrayOfProductDataVO[i].sellAmount[0]);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].buyPrice[0]);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].sellPrice[0]);
			localDataOutputStream.writeUTF(arrayOfProductDataVO[i].expStr);
			if (arrayOfProductDataVO[i].lUpdateTime > paramClientSocket.quoteListTime)
				paramClientSocket.quoteListTime = arrayOfProductDataVO[i].lUpdateTime;
		}

		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();

		return localByteArrayOutputStream.toByteArray();
	}

	// 100001综合指数

	// 100002钱币指数
	// 50XXXX纸币券
	// 70XXXX流通纪念币
	// 80XXXX金币、银币

	// 100003邮票指数
	// 601XXXX邮票（小型张、小全张等）
	// 602XXXX邮票（版票）
	// 603XXXX邮票（本票票）
	// 604XXXX邮票（待定品类）
	// 605XXX邮资封片

	// 100004电话卡指数
	// 90XXXX电话卡
	private void processIndices(ProductDataVO[] datas) {
		ProductDataVO all = new ProductDataVO();
		ProductDataVO coin = new ProductDataVO();
		ProductDataVO stamp = new ProductDataVO();
		ProductDataVO phonecard = new ProductDataVO();
		int countCoin = 0;
		int countStamp = 0;
		int countPhonecard = 0;
		ProductDataVO srcAll = null, srcCoin = null, srcStamp = null, srcPhonecard = null;
		ProductDataVO cloneCoin = null, cloneStamp = null, clonePhonecard = null;

		for (int i = 0; i < datas.length; i++) {
			ProductDataVO data = datas[i];
			String code = data.code;
			// log.debug(data.toString());

			if ("100001".equals(code)) {
				srcAll = data;
			} else if ("100002".equals(code)) {
				srcCoin = data;
			} else if ("100003".equals(code)) {
				srcStamp = data;
			} else if ("100004".equals(code)) {
				srcPhonecard = data;
			} else if (code.startsWith("50") || code.startsWith("70") || code.startsWith("80")) {
				countCoin += addProps(data, coin);
			} else if (code.startsWith("60")) {
				countStamp += addProps(data, stamp);
			} else if (code.startsWith("90")) {
				countPhonecard += addProps(data, phonecard);
			}
		}

		if (srcCoin != null && countCoin > 0) {
			cloneCoin = (ProductDataVO) coin.clone();
			countAvg(coin, countCoin);
			copyProperties(coin, srcCoin);
		}
		if (srcStamp != null && countStamp > 0) {
			cloneStamp = (ProductDataVO) stamp.clone();
			countAvg(stamp, countStamp);
			copyProperties(stamp, srcStamp);
		}
		if (srcPhonecard != null && countPhonecard > 0) {
			clonePhonecard = (ProductDataVO) phonecard.clone();
			countAvg(phonecard, countPhonecard);
			copyProperties(phonecard, srcPhonecard);
		}

		int countAll = addProps(cloneCoin, cloneStamp, clonePhonecard, all);
		if (countAll > 0)
			copyProperties(all, srcAll);
	}

	private int addProps(ProductDataVO source, ProductDataVO target) {
		// 剔除没有开盘的
		if (source.curPrice < 0.00001f)
			return 0;
		// +=
		target.yesterBalancePrice = (source.yesterBalancePrice + target.yesterBalancePrice);
		target.closePrice = (source.closePrice + target.closePrice);
		target.openPrice = (source.openPrice + target.openPrice);
		target.highPrice = (source.highPrice + target.highPrice);
		target.lowPrice = (source.lowPrice + target.lowPrice);
		target.curPrice = (source.curPrice + target.curPrice);
		target.totalAmount = (source.totalAmount + target.totalAmount);
		target.totalMoney = (source.totalMoney + target.totalMoney);
		target.curAmount = (source.curAmount + target.curAmount);
		target.consignRate = (source.consignRate + target.consignRate);
		target.amountRate = (source.amountRate + target.amountRate);
		target.balancePrice = (source.balancePrice + target.balancePrice);
		target.reserveCount = (source.reserveCount + target.reserveCount);

		return 1;
	}

	private void countAvg(ProductDataVO source, int count) {
		source.yesterBalancePrice = (source.yesterBalancePrice) / count;
		source.closePrice = (source.closePrice) / count;
		source.openPrice = (source.openPrice) / count;
		source.highPrice = (source.highPrice) / count;
		source.lowPrice = (source.lowPrice) / count;
		source.curPrice = (source.curPrice) / count;
		// source.totalAmount = (source.totalAmount) / count;
		// source.totalMoney = (source.totalMoney) / count;
		// source.curAmount = (source.curAmount) / count;
		source.consignRate = (source.consignRate) / count;
		source.amountRate = (source.amountRate) / count;
		source.balancePrice = (source.balancePrice) / count;
		// source.reserveCount = (source.reserveCount) / count;
	}

	private int addProps(ProductDataVO coin, ProductDataVO stamp, ProductDataVO phonecard, ProductDataVO all) {
		int countAll = 0;
		if (coin != null)
			countAll += addProps(coin, all);
		if (stamp != null)
			countAll += addProps(stamp, all);
		if (phonecard != null)
			countAll += addProps(phonecard, all);

		if (countAll > 0)
			countAvg(all, countAll);

		return countAll;
	}

	// 多线程可能有误差，反馈不及时
	private void copyProperties(ProductDataVO source, ProductDataVO target) {
		// BeanUtils.copyProperties(source, target, ignoreProperties);
		target.yesterBalancePrice = (source.yesterBalancePrice);
		target.closePrice = (source.closePrice);
		target.openPrice = (source.openPrice);
		target.highPrice = (source.highPrice);
		target.lowPrice = (source.lowPrice);
		target.curPrice = (source.curPrice);
		target.totalAmount = (source.totalAmount);
		target.totalMoney = (source.totalMoney);
		target.curAmount = (source.curAmount);
		target.consignRate = (source.consignRate);
		target.amountRate = (source.amountRate);
		target.balancePrice = (source.balancePrice);
		target.reserveCount = (source.reserveCount);
		// target.buyAmount = (source.buyAmount);
		// target.sellAmount = (source.sellAmount);
		// target.buyPrice = (source.buyPrice);
		// target.sellPrice = (source.sellPrice);
	}
	// 幅度 是 当前/昨收盘
	// YesterPrice:819.0 yesterBalancePrice 昨平均
	// ClosePrice:819.0 昨闭市
	// OpenPrice:900.0
	// HighPrice:900.0
	// LowPrice:900.0
	// CurPrice:900.0 最新
	// CurAmount:2 现量
	// OpenAmount:1 开仓数
	// CloseAmount:1 平仓数
	// 成交量 就是2
	// ReserveCount:20002 所有日期累加？ 订货量
	// AverageValue:900.0 balancePrice
	// TotalMoney:1800.0 成交金额
	// TotalAmount:2

	// OutAmount:2
	// InAmount:0
	// TradeCue:0
	// NO:12
	// AverAmount5:2
	// AmountRate:0.0 量比？ 量比=现成交总手/（过去5日平均每分钟成交量×当日累计开市时间（分））
	// consignRate:0.0 委比 委比＝〖(委买手数-委卖手数)÷（委买手数＋委卖手数)〗×100%

	// BuyPrice1:0.0
	// SellPrice1:0.0
	// BuyAmount1:0
	// SellAmount1:0
	// BuyPrice2:0.0
	// SellPrice2:0.0
	// BuyAmount2:0
	// SellAmount2:0
	// BuyPrice3:0.0
	// SellPrice3:0.0
	// BuyAmount3:0
	// SellAmount3:0
	// BuyPrice4:0.0
	// SellPrice4:0.0
	// BuyAmount4:0
	// SellAmount4:0
	// BuyPrice5:0.0
	// SellPrice5:0.0
	// BuyAmount5:0
	// SellAmount5:0
}
