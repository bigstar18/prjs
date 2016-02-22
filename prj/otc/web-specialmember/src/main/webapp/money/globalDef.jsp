<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="gnnt.trade.bank.dao.*"%>
<%@ page import="gnnt.trade.bank.dao.ceb.*"%>
<%@ page import="gnnt.trade.bank.dao.citic.*"%>
<%@ page import="gnnt.trade.bank.dao.icbce.*"%>
<%@ page import="gnnt.trade.bank.vo.*" %>
<%@ page import="gnnt.trade.bank.util.*" %>
<%@ page import="gnnt.MEBS.member.ActiveUser.*" %>
<%@ page import="gnnt.trade.bank.processorrmi.*" %>
<%@ page import="java.rmi.Naming" %>
<%@ page import="gnnt.trade.bank.dao.hxb.*"%>
<%@ page import="gnnt.trade.bank.dao.up.*"%>
<%
//加载银行编码
new BankCode().load();

response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
%>

<%
//易极付定制——————开户行名称及其所在省市所用
	//获取开户行城市表中数据
	BankDAO bankDao = BankDAOFactory.getDAO();
	Vector<CityValue> citys = bankDao.getCityNames("");
	CityValue cityForBank = new CityValue();
	Map<String, CityValue> map = new HashMap<String, CityValue>();
	for(int i=0;i<citys.size();i++){
		cityForBank = citys.get(i);
		map.put(cityForBank.getCityCode(), cityForBank);//把对应的开户行城市对象以城市编码为key放到Map中
	}
	
	
%>

<%!
// 分页默认每页大小
public final int BANKPAGESIZE = 15;
//交易账号
public final String CONTACTTITLE = "交易账号";
//银行账号名称
public final String ACCOUNTNAMETITLE = "账号名称";
public final int SESSIONINTERVAL = 60000;
public final String marketpwd = "资金密码";
public String delNull(String s) {
	if(s == null) s = "";
	return s;
}
public void alert(String s,javax.servlet.jsp.JspWriter out){	
	try{
		out.println("<script>alert('"+ s +"');</script>");
	}catch(Exception e){
		System.out.println("function alert() exception : " + e.toString());
	}
}



public void sendRedirect(String url,javax.servlet.jsp.JspWriter out){	
	try{
	    if(url != null && !url.trim().equals("")){
			out.println("<script>window.location='"+ url +"'</script>");
		}
	}catch(Exception e){
		System.out.println(e.toString());
	}
}
public boolean isRegstatus(String bankID){//判断银行是否支持市场端签约
	if("005".equals(bankID)||"050".equals(bankID)){//农行
		return true;
	}else if("17".equals(bankID)){//华夏(前端不支持)
		return false;
	}else if("66".equals(bankID)){//华夏(前端不支持)
		return true;
	}
	return false;
}
public boolean delAccountBank(String bankID){//判断银行是否支持市场端解约
	if("005".equals(bankID)||"050".equals(bankID)){//农行
		return true;
	}else if("17".equals(bankID)){//华夏(前端不支持)
		return false;
	}else if("66".equals(bankID)){//华夏(前端不支持)
		return true;
	}
	return false;
}
/**
 * 银行是否校验密码
 * @param inoutmoney 交易类型 (0 入金;1 出金;2 查询余额)
 * @param bankid 银行编号
 * @param cardtype 证件类型
 * @return boolean (true 银行需要验证密码;false 银行不需要密码)
 */
public boolean showPasswordBank(String inoutmoney,String bankid,String cardtype){
	if(("0".equals(inoutmoney) || "2".equals(inoutmoney) || "1".equals(inoutmoney))){
		return true;
	}
	return false;
}
%>
<%
	String RmiIpAddress = Tool.getConfig("RmiIpAddress");
	String RmiPortNumber = Tool.getConfig("RmiPortNumber");
	String RmiServiceName = Tool.getConfig("RmiServiceName");
	ErrorCode errorcode = new ErrorCode();
	errorcode.load();
%>
<%!
StaticMsg staticMsg = new  StaticMsg();
public String getProcUrl(String bankID){
		String procUrl = "";
	if(bankID != null && bankID.trim().length()>0&&!bankID.toLowerCase().equals("null")){
		if(staticMsg.getBank(bankID) != null){
			System.out.println("==============>2222222222bankID=" + bankID);
			procUrl = staticMsg.getBank(bankID).beleiveProcessor;
		}
	}
	if(procUrl == null || procUrl.trim().length()<=0){
		procUrl = staticMsg.getBankMap().values().iterator().next().beleiveProcessor;
	}
	return procUrl;
	}

public CapitalProcessorRMI getBankUrl(String bankID){
	CapitalProcessorRMI result = null;
	String procUrl=getProcUrl(bankID);
	try{
		result = (CapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
public ABCCapitalProcessorRMI getABCBankUrl(String bankID){
	ABCCapitalProcessorRMI result = null;
	String procUrl =getProcUrl(bankID);
	System.out.println("--农行--procUrl-----"+procUrl);
	try{
	    result = (ABCCapitalProcessorRMI)Naming.lookup(procUrl);
		System.out.println("--农行--procUrl-----"+procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
public YjfCapitalProcessorRMI getYjfBankUrl(String bankID){
	YjfCapitalProcessorRMI result = null;
	String procUrl =getProcUrl(bankID);
	try{
		result = (YjfCapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
public BOCCapitalProcessorRMI getBOCBankUrl(String bankID){
	BOCCapitalProcessorRMI result = null;
	String procUrl =getProcUrl(bankID);
	try{
		result = (BOCCapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
public GFBCapitalProcessorRMI getGFBBankUrl(String bankID){
	GFBCapitalProcessorRMI result = null;
	String procUrl =getProcUrl(bankID);
	try{
		result = (GFBCapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
public CEBCapitalProcessorRMI getCEBBankUrl(String bankID){
	CEBCapitalProcessorRMI result = null;
	String procUrl =getProcUrl(bankID);
	System.out.println("&&&&&&&&&&&"+procUrl+"&&&&&&&&&&&&");
	try{
		result = (CEBCapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
public HXBCapitalProcessorRMI getHXBBankUrl(String bankID){
	HXBCapitalProcessorRMI result = null;
	String procUrl =getProcUrl(bankID);
	System.out.println("&&&&&&&&&&&"+procUrl+"&&&&&&&&&&&&");
	try{
		result = (HXBCapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
public CITICCapitalProcessorRMI getCITICBankUrl(String bankID){
	CITICCapitalProcessorRMI result = null;
	String procUrl =getProcUrl(bankID);
	System.out.println("&&&&&&&&&&&"+procUrl+"&&&&&&&&&&&&");
	try{
		result = (CITICCapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
public ICBCECapitalProcessorRMI getICBCEBankUrl(String bankID){
	ICBCECapitalProcessorRMI result = null;
	String procUrl =getProcUrl(bankID);
	System.out.println("&&&&&&&&&&&"+procUrl+"&&&&&&&&&&&&");
	try{
		result = (ICBCECapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
public UPCapitalProcessorRMI getUPBankUrl(String bankID){
	UPCapitalProcessorRMI result = null;
	String procUrl =getProcUrl(bankID);
	try{
		result = (UPCapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
public TLAPayCapitalProcessorRMI getTLAPayBankUrl(String bankID){
	TLAPayCapitalProcessorRMI result = null;
	String procUrl =getProcUrl(bankID);
	System.out.println("&&&&&&&&&&&"+procUrl+"&&&&&&&&&&&&");
	try{
		result = (TLAPayCapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
public TLGPayCapitalProcessorRMI getTLGPayBankUrl(String bankID){
	TLGPayCapitalProcessorRMI result = null;
	String procUrl =getProcUrl(bankID);
	System.out.println("&&&&&&&&&&&"+procUrl+"&&&&&&&&&&&&");
	try{
		result = (TLGPayCapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
%>
<script>
		/**
	 * 银行是否校验密码
	 * @param inoutmoney 交易类型 (0 入金;1 出金;2 查询余额)
	 * @param bankid 银行编号
	 * @param cardtype 证件类型
	 * @return boolean (true 银行需要验证密码;false 银行不需要密码)
	 */
	function showPasswordBank(inoutmoney,bankid,cardtype){
		if((inoutmoney == 0 || inoutmoney == 2) && bankid == "005"){
			return true;
		}
		return false;
	}
		//判断是否是需要银行密码的银行
	function isPasswordBank(bankid){
	if(bankid == 005 || bankid == 25 || bankid == 15 || bankid == 18 || bankid == 19 || bankid == 12){//农行，民生，招行，平安(深发)，兴业
		return true;
	}else{
		return false;
	}
}
</script>