package gnnt.trade.bank.xmlwork;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmDZValue;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.w3c.dom.Element;
/**获取日终对账xml文件*/
public class RZDZxml {
	/**文件名称*/
	private String fileName = "rizhongduizhang";
	/**编码格式*/
	private String charset;
	/**XML类*/
	private AddXml xml = null;
	public RZDZxml(){
	}
	public RZDZxml(String url,String charset){
		this.charset = charset;
		this.fileName = url + fileName;
	}
	public String getRZDZxml(RZDZValue dz){
		return this.getRZDZxml(dz, null);
	}
	public String getRZDZxml(RZDZValue dz,java.util.Date date){
		if(dz==null){
			dz = new RZDZValue();
		}
		this.mkXml(dz,date);
		this.addMaketXml(dz);
		this.addFirmXml(dz);
		return this.fileName;
	}
	/**向xml文件添加市场信息*/
	private void addMaketXml(RZDZValue dz){
		Map<String,String> maket = new HashMap<String,String>();
		maket.put("FILE_NAME", dz.fileName);
		maket.put("BANK_ID", dz.bankID);
		maket.put("MARKET_ID", dz.maketID);
		maket.put("TRADE_DATE", this.fmdDate(dz.tradeDate));
		maket.put("FIRM_DATA_LIST", "");
		try{
			this.xml.setChildElements("CONTEXT", maket);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**向xml文件添加交易商信息*/
	private void addFirmXml(RZDZValue dz){
		Vector<FirmDZValue> vector= dz.getFdv();
		for(FirmDZValue fd : vector){
			Map<String,String> map = new HashMap<String,String>();
			Map<String,String> map2 = new HashMap<String,String>();
			map.put("FIRM_ID", fd.firmID);
			map.put("FIRM_BANKACCOUNT", fd.account);
			map.put("CURRENCY", fd.currency);
			map.put("REMIT_TYPE", ""+fd.type);
			map.put("FIRM_ASSET", ""+this.getDouble(fd.firmRights));
			map.put("CASH", ""+this.getDouble(fd.cashRights));
			map.put("BILL", ""+this.getDouble(fd.billRights));
			map.put("AVAILABLE_BALANCE", ""+this.getDouble(fd.availableBalance));
			map2.put("CASH", ""+this.getDouble(fd.cash));
			map2.put("CREDIT", ""+this.getDouble(fd.credit));
			try{
				Element element = xml.setChildElements("CONTEXT.FIRM_DATA_LIST","RECORD" ,map);
				xml.setChildElements(element,"BETAKE_MARGIN", map2);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	/**生成XML文件*/
	private void mkXml(RZDZValue dz,java.util.Date date){
		if(date==null){
			this.fileName = this.fileName+"_"+this.fmdDate(dz.tradeDate)+".xml";
		}else{
			this.fileName = this.fileName+"_"+this.fmdDate(date)+".xml";
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
	public static void main(String args[]){
		RZDZValue dz = new RZDZValue();
		dz.bankID="01";
		dz.fileName = "rizhongduizhang";
		dz.tradeDate = new java.util.Date();
		dz.maketID = " ";
		Vector<FirmDZValue> vv = new Vector<FirmDZValue>();
		for(int i = 0 ; i < 2 ; i ++){
			FirmDZValue fd = new FirmDZValue();
			fd.account="1111111111";
			fd.availableBalance = 100;
			fd.billRights = 20;
			fd.cash = 10;
			fd.cashRights = 30;
			fd.credit = 5;
			fd.currency="001";
			fd.firmID="000"+i;
			fd.firmRights = 20;
			vv.add(fd);
		}
		dz.setFdv(vv);
		
		RZDZxml rd = new RZDZxml();
		rd.getRZDZxml(dz);
	}
}
