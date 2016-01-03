package gnnt.mebsv.hqservice.hq.Index;

import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import gnnt.MEBS.util.Configuration;
import gnnt.mebsv.hqservice.dao.HQDAO;
import gnnt.mebsv.hqservice.dao.factory.HQDAOFactory;
import gnnt.mebsv.hqservice.hq.QuotationServer;
import gnnt.mebsv.hqservice.model.ProductDataVO;
import gnnt.mebsv.hqservice.model.ProductInfoVO;
import gnnt.mebsv.hqservice.tools.HZPY;

public class IndexCalculator extends Thread {
	private int iType = 0;
	private int iSamplingInterval;
	private String[] indexName;
	private HQDAO dao;
	public static QuotationServer quotation;
	Date date = null;
	Index index;

	public IndexCalculator(Properties paramProperties, QuotationServer paramQuotationServer) {
		try {
			this.dao = HQDAOFactory.getDAO(paramProperties);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		init();
		quotation = paramQuotationServer;
	}

	public void init() {
		getIndexSetup();
	}

	/**
	 * @deprecated
	 */
	public void run() {
		if (this.index == null)
			return;
		if (this.index.indexCode.length == 0)
			return;
		while (true) {
			try {
				Thread.sleep(this.iSamplingInterval * 1000);
			} catch (InterruptedException localInterruptedException) {
				localInterruptedException.printStackTrace();
			}
			if (quotation.bDataLoaded) {
				Vector localVector = quotation.getAllProductData();
				ProductDataVO localProductDataVO;
				for (int i = localVector.size() - 1; i >= 0; i--) {
					localProductDataVO = (ProductDataVO) localVector.get(i);
					if (quotation.getProductType(localProductDataVO.marketID + localProductDataVO.code) != 0)
						localVector.removeElementAt(i);
				}
				if (localVector.size() != 0) {
					if (this.date == null) {
						System.out.println("Index init ...");
						this.date = this.dao.getHQDate();
						System.out.println("hqDate: " + this.date);
						this.index.indexData = new ProductDataVO[this.index.indexCode.length];
						for (int i = 0; i < this.index.indexCode.length; i++) {
							localProductDataVO = quotation.getProductData(this.index.indexCode[i]);
							if (localProductDataVO == null)
								this.index.initOneIndex(i, this.date);
							else
								this.index.indexData[i] = ((ProductDataVO) localProductDataVO.clone());
						}
					}
					Date localDate = this.dao.getHQDate();
					System.out.println("---->hqDate=" + localDate + "date=" + this.date);
					if (localDate != null) {
						if ((localDate.getYear() != this.date.getYear()) || (localDate.getMonth() != this.date.getMonth())
								|| (localDate.getDate() != this.date.getDate())) {
							System.out.println(new Date().toLocaleString() + " Init Index ...");
							this.date = localDate;
							this.index.init(this.date);
						}
						if (!this.index.input((ProductDataVO[]) localVector.toArray(new ProductDataVO[localVector.size()])))
							;
						for (int j = 0; j < this.index.indexCode.length; j++)
							if (this.iType == 7) {
								int k = this.index.getCalculateResult(j);
								if (k != 0)
									if (k == 1) {
										this.index.indexData[j].time = ((Date) quotation.getHqDate().get("00"));
										System.out.println("Sys Time:" + this.index.indexData[j].time);
										this.dao.updateSeriesData(this.index.indexData[j]);
									} else {
										this.index.indexData[j].time = ((Date) quotation.getHqDate().get("00"));
										System.out.println("Sys Time:" + this.index.indexData[j].time);
										this.dao.insertSeriesData(this.index.indexData[j]);
									}
							} else {
								boolean bool = this.index.calculate(j);
								if (bool) {
									this.index.indexData[j].time = ((Date) quotation.getHqDate().get("00"));
									this.index.indexData[j].name = this.index.indexName[j];
									System.out.println("index.indexData[" + j + "].name=" + this.index.indexData[j].name + "index.indexName[" + j
											+ "]=" + this.index.indexName[j]);
									this.dao.updateRealData(this.index.indexData[j]);
								}
							}
					}
				}
			}
		}
	}

	private void getIndexSetup() {
		Properties localProperties = new Configuration().getSection("MEBS.Index");
		if (localProperties == null)
			return;
		this.iType = Integer.parseInt(localProperties.getProperty("Type", "1"));
		switch (this.iType) {
		case 1:
			this.index = new Index1(this.dao);
			break;
		case 2:
			this.index = new Index2(this.dao);
			break;
		case 3:
			this.index = new Index3(this.dao);
			break;
		case 4:
			break;
		case 5:
			this.index = new Index5(this.dao);
			break;
		case 6:
			this.index = new Index6(this.dao);
			break;
		case 7:
			this.index = new Index7(this.dao);
			break;
		case 8:
			this.index = new Index8(this.dao);
			break;
		default:
			return;
		}
		this.iSamplingInterval = Integer.parseInt(localProperties.getProperty("SamplingInterval", "10"));
		int i = Integer.parseInt(localProperties.getProperty("Num", "0"));
		int j = Integer.parseInt(localProperties.getProperty("BaseDate", "20070104"));
		this.index.baseDate.set(j / 10000, j / 100 % 100 - 1, j % 100, 0, 0, 0);
		this.index.baseDate.set(14, 0);
		this.index.seriesMonth = Integer.parseInt(localProperties.getProperty("SeriesMonth", "0"));
		if (i > 0) {
			localProperties.list(System.out);
			System.out.println("");
		}
		this.index.indexCode = new String[i];
		this.indexName = new String[i];
		this.index.indexName = new String[i];
		this.index.indexMask = new String[i];
		this.index.indexMaskExclude = new String[i];
		for (int k = 0; k < i; k++) {
			this.index.indexCode[k] = localProperties.getProperty("Index_" + (k + 1) + "_Code");
			this.indexName[k] = localProperties.getProperty("Index_" + (k + 1) + "_Name");
			this.index.indexName[k] = this.indexName[k];
			this.index.indexMask[k] = localProperties.getProperty("Index_" + (k + 1) + "_Mask");
			this.index.indexMaskExclude[k] = localProperties.getProperty("Index_" + (k + 1) + "_MaskExclude");
		}
	}

	public ProductInfoVO[] getIndexInfoVO() {
		if ((this.index == null) || (this.index.indexCode == null))
			return new ProductInfoVO[0];
		ProductInfoVO[] arrayOfProductInfoVO = new ProductInfoVO[this.index.indexCode.length];
		for (int i = 0; i < this.index.indexCode.length; i++) {
			arrayOfProductInfoVO[i] = new ProductInfoVO();
			arrayOfProductInfoVO[i].code = this.index.indexCode[i];
			arrayOfProductInfoVO[i].name = this.indexName[i];
			arrayOfProductInfoVO[i].modifyTime = new Date(0L);
			if (i == 0)
				arrayOfProductInfoVO[i].status = 3;
			else
				arrayOfProductInfoVO[i].status = 2;
			try {
				arrayOfProductInfoVO[i].pyName = HZPY.getPYJM(arrayOfProductInfoVO[i].name, "GBK");
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return arrayOfProductInfoVO;
	}

	public static void main(String[] paramArrayOfString) {
	}
}