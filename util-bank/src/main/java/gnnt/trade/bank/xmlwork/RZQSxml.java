package gnnt.trade.bank.xmlwork;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmRightValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.MarketRightValue;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.w3c.dom.Element;
/**获取日终清算xml文件*/
public class RZQSxml {
	/**文件名称*/
	private String fileName = "rizhongqingsuan";
	/**编码格式*/
	private String charset;
	/**XML类*/
	private AddXml xml = null;
	public RZQSxml(){
	}
	public RZQSxml(String url,String charset){
		this.charset = charset;
		this.fileName = url + this.fileName;
	}
	public String getRZQSxml(RZQSValue qs){
		return this.getRZQSxml(qs, null);
	}
	public String getRZQSxml(RZQSValue qs,java.util.Date date){
		if(qs==null){
			qs = new RZQSValue();
		}
		this.mkXml(qs,date);
		this.addTitle(qs);
		this.addMaketXml(qs);
		this.addFirmXml(qs);
		return this.fileName;
	}
	/**向xml文件添加交易商信息*/
	private void addFirmXml(RZQSValue qs){
		Vector<FirmRightValue> vector = qs.getFrv();
		for(FirmRightValue fr : vector){
			Map<String,String> map = new HashMap<String,String>();
			Map<String,String> map2 = new HashMap<String,String>();
			map.put("MARKET_SEQUENCE", fr.actionID); //市场流水号
			map.put("FIRM_ID", fr.firmID);		//交易商代码
			map.put("FIRM_BANKACCOUNT", fr.account); //交易商一行结算账号
			map.put("CURRENCY", fr.currency);	//币种
			map.put("REMIT_TYPE", ""+fr.type);	//钞汇标志
			map.put("ASSERT_CHANGE", ""+this.getDouble(fr.firmErrorMoney)); //交易商权限变化
			map.put("CASH_CHANGE", ""+this.getDouble(fr.cashMoney));	//现金变化量
			map.put("BILL_CHANGE", ""+this.getDouble(fr.billMoney));	//票据变化量
			map.put("AVAILABLE_BALANCE_CHANGE", ""+this.getDouble(fr.availableBalance)); //可用资金变化量
			map2.put("CASH", ""+this.getDouble(fr.cash));	//占用现金变化量
			map2.put("CREDIT", ""+this.getDouble(fr.credit)); //占用贷款金额变化量
			try{
				Element element = xml.setChildElements("CONTEXT.FIRM_DATA_LIST","RECORD" ,map);
				xml.setChildElements(element,"BETAKE_MARGIN_CHANGE", map2);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	/**向xml文件添加市场信息*/
	private void addMaketXml(RZQSValue qs){
		Map<String,String> map = new HashMap<String,String>();
		MarketRightValue mr = qs.getMarketRight();
		map.put("POUNDAGE_INCOME", ""+this.getDouble((mr.maketMoney==null ? new BigDecimal(0) : mr.maketMoney)));
		map.put("BANK_BALANCE", ""+this.getDouble(mr.bankErrorMoney));
		map.put("FINANCING_BALANCE", ""+this.getDouble(mr.maketErrorMoney));
		map.put("CURRENCY", mr.currency);
		map.put("REMIT_TYPE", ""+mr.type);
		try{
			xml.setChildElements("CONTEXT","MARKET_DATA",map);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**添加标准信息*/
	private void addTitle(RZQSValue qs){
		Map<String,String> maket = new HashMap<String,String>();
		maket.put("FILE_NAME", qs.fileName);
		maket.put("BANK_ID", qs.bankID);
		maket.put("MARKET_ID", qs.maketID);
		maket.put("TRADE_DATE", ""+this.fmdDate(qs.tradeDate));
		//maket.put("MARKET_DATA", "");
		maket.put("FIRM_DATA_LIST", "");
		try{
			this.xml.setChildElements("CONTEXT", maket);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**生成xml文件*/
	private void mkXml(RZQSValue qs,java.util.Date date){
		if(date==null){
			this.fileName = this.fileName + "_"+this.fmdDate(qs.tradeDate)+".xml";
		}else{
			this.fileName = this.fileName + "_"+this.fmdDate(date)+".xml";
		}
		this.xml = new AddXml(this.fileName,"GNNT",this.charset);
	}
	/**获取日期格式*/
	private String fmdDate(java.util.Date date){
		if(date==null){
			date = new java.util.Date();
		}
		SimpleDateFormat dateFormate=new SimpleDateFormat("yyyyMMdd");
		return dateFormate.format(date);
	}
	/**获取资金额(整数)*/
	private String getDouble(double money){
		String result = Tool.fmtDouble0(money*100);
		return result;
	}
	/**获取资金金额(整数)*/
	private String getDouble(BigDecimal money){
		String result = Tool.fmtDouble0(money.multiply(new BigDecimal(100)));
		return result;
	}
	/**测试方法*/
	public static void main(String args[]){
		RZQSValue qs = new RZQSValue();
		qs.bankID = "01";
		qs.tradeDate = new java.util.Date();
		for (int i = 0 ; i < 3 ; i++){
			FirmRightValue fr = new FirmRightValue();
			fr.account="22222222";
			fr.actionID="sdf";
			fr.availableBalance=10;
			fr.billMoney=12;
			fr.cash=13;
			fr.cashMoney=54;
			fr.credit=3;
			fr.firmErrorMoney=9;
			fr.type=1;
			fr.currency="014";
			qs.putFrv(fr);
		}
		RZQSxml rzqs = new RZQSxml();
		rzqs.getRZQSxml(qs);
	}
}
