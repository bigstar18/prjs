<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.math.BigDecimal" %>

<%@ page import="gnnt.trade.bank.dao.*"%>
<%@ page import="gnnt.trade.bank.vo.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>

<%@ page import="java.rmi.*"%>
<%@ page import="gnnt.trade.bank.processorrmi.CapitalProcessorRMI"%>
<%@ page import="gnnt.trade.bank.processorrmi.CCBCapitalProcessorRMI"%>

<%@ page import="gnnt.MEBS.common.security.util.*"%>
<%@ page import="gnnt.MEBS.common.security.*"%>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
%>
<%!
// 分页默认每页大小
public final int BANKPAGESIZE = 15;
//会员或客户编号
public final String FIRMTITLE = "交易账号";
//交易账号
public final String CONTACTTITLE = "交易账号";
//银行账号
public final String ACCOUNTTITLE = "银行账号";
//银行账号名称
public final String ACCOUNTNAMETITLE = "账号名称";
//银行名称
public final String BANKTITLE = "银行";
//序号名称
public final String SEQUNCE = "序号";
// 设置为空的字符串
public String replaceNull(String s){
  if(s==null||"".equals(s.trim())||"null".equals(s.trim())) return "-";
  else return s;
}
//支持市场端签约
public boolean openAccountBank(String bankID){
	if(bankID.startsWith("1") || "005".equals(bankID)){
		return true;
	}
	return false;
}
//支持市场端解约
public boolean delAccountBank(String bankID){
	if(bankID.startsWith("1") || "005".equals(bankID) || "006".equals(bankID)){
		return true;
	}
	return false;
}
//判断发送清算的银行
public int sendQSBank(String bankID){
	if("01".equals(bankID)){
		return 1;
	}
	return 0;
}
//不需要输入银行账号的银行
public boolean checkAccount(String bankID){
	if("005".equals(bankID) || "006".equals(bankID)){//农业银行
		return true;
	}
	return false;
}
%>
<%
	/*String RmiIpAddress = Tool.getConfig("RmiIpAddress");
	String RmiPortNumber = Tool.getConfig("RmiPortNumber");
	String RmiServiceName = Tool.getConfig("RmiServiceName");*/
	String computerIP = request.getRemoteAddr();
%>
<%!
StaticMsg staticMsg = new  StaticMsg();
	public String getProcUrl(String bankID){
		String procUrl = "";
	if(bankID != null && bankID.trim().length()>0&&!bankID.toLowerCase().equals("null")){
		procUrl = staticMsg.getBank(bankID).beleiveProcessor;
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
public CCBCapitalProcessorRMI getCCBBankUrl(String bankID){
	CCBCapitalProcessorRMI result = null;
	String procUrl =getProcUrl(bankID);
	try{
		result = (CCBCapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
%>

<script type="text/javascript">
//可签约银行
function openAccountBank(bankID){
	if(bankID.substr(0,1)==1 || "005"==bankID){
		return true;
	}
	return false;
}
//可解约银行
function delAccountBank(bankID){
	if(bankID.substr(0,1)==1 || "005"==bankID || "006"==bankID){
		return true;
	}
	return false;
}
//变更银行时操作
function gotoBankQS(){
	var bankID = frm.bankID.value;
	if(bankID == ""){
		alert("请选择清算银行");
	}else{
		frm.action="<%=request.getContextPath()%>/bankInterface/compareInfo/"+bankQsPage(bankID);
		frm.submit();
	}
}
//确定每个清算页面的位置
function bankQsPage(bankID){
	if(bankID == "-1"){//返回初始化页面
		return "qs.jsp";
	}if(bankID == "01"){//工商银行
		return "dz/demo/DEMO.jsp";
	}
}
</script>
