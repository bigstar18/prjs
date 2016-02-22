<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>


<%
String bankID = request.getParameter("MbankID");
String firmID = request.getParameter("MfirmID");
String contact = request.getParameter("Mcontact");
String account = request.getParameter("Maccount");
String bankCardPassword = request.getParameter("MbankCardPassword");
int size = Tool.strToInt(request.getParameter("pageSize"));
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
BankDAO dao = BankDAOFactory.getDAO();
CapitalProcessorRMI cp = null;
try{
	//多处理器路由
	//cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	cp = getBankUrl(bankID);
}catch(Exception e){
	e.printStackTrace();
}
LogValue lv = new LogValue();
lv.setLogopr(AclCtrl.getLogonID(request));
lv.setLogtime(new Date());
lv.setIp(computerIP);
lv.setLogtype("2110");
lv.setLogoprtype("E");
Vector<CorrespondValue> cps = dao.getCorrespondList(" and firmid='"+firmID+"' and bankid='"+bankID+"'");
CorrespondValue corr = null;
if(cps != null && cps.size()>0){
	corr = cps.get(0);
	ErrorCode errorcode = new ErrorCode();
	errorcode.load();
}
String headmsg = "交易资金账号管理";
String strs = "";
long bak = -1;
if("disAccount".equalsIgnoreCase(request.getParameter("submitFlag"))){//禁用
	String str = headmsg+"-禁用账号";
	if(corr.isOpen != 1){
		strs = "非签约状态无需禁用";
	}else {
		try {
			corr.status = 1;
			ReturnValue rv = cp.modCorrespondStatus(corr);
			if(rv.result < 0){
				strs = rv.remark;
				if(strs == null || strs.trim().length()<=0){
					if(ErrorCode.error.get(rv.result) != null){
						strs = ErrorCode.error.get(rv.result);
					}else{
						strs = "返回错误码["+rv.result+"]";
					}
				}
			}else{
				bak = 0;
				strs = "禁用账号["+corr.firmID+"]成功";
			}
		} catch(Exception e) {
			strs = "禁用账号["+corr.firmID+"]系统异常";
			e.printStackTrace();
		}
		lv.setResult(bak==0 ? 0 : 1);
		lv.setLogcontent(str+"-"+strs);
		dao.log(lv);
	}
	%>
		<script>
			alert('<%=strs%>')
		</script>
	<%
}else if("recAccount".equalsIgnoreCase(request.getParameter("submitFlag"))){//启用
	String str = headmsg+"-启用账号";
	if(corr.isOpen != 1){
		strs = "非签约状态不能启用";
	}else {
		try {
			corr.status = 0;
			ReturnValue rv = cp.modCorrespondStatus(corr);
			if(rv.result < 0){
				strs = rv.remark;
				if(strs == null || strs.trim().length()<=0){
					if(ErrorCode.error.get(rv.result) != null){
						strs = ErrorCode.error.get(rv.result);
					}else{
						strs = "返回错误码["+rv.result+"]";
					}
				}
			}else{
				bak = 0;
				strs = "启用账号["+corr.firmID+"]成功";
			}
		} catch(Exception e) {
			strs = "启用账号["+corr.firmID+"]系统异常";
			e.printStackTrace();
		}
		lv.setResult(bak==0 ? 0 : 1);
		lv.setLogcontent(str+"-"+strs);
		dao.log(lv);
	}
	%>
		<script>
			alert('<%=strs%>')
		</script>
	<%
}else if ("recPassword".equalsIgnoreCase(request.getParameter("submitFlag"))){//初始化密码
	String password = "111111";
	String str = headmsg+"-初始化资金密码";
	ReturnValue rv=cp.initializeFrimPwd(firmID,password);
	if(rv.result<0){
		strs = rv.remark;
		if(strs == null || strs.trim().length()<=0){
			if(ErrorCode.error.get(rv.result) != null){
				strs = ErrorCode.error.get(rv.result);
			}else{
				strs = "初始化资金密码，返回码["+rv.result+"]";
			}
		}
	}else{
		bak = 0;
		strs = "初始化["+firmID+"]资金密码["+password+"]成功";
	}
	lv.setResult(bak==0 ? 0 : 1);
	lv.setLogcontent(str+"-"+strs);
	dao.log(lv);
	%>
		<script>
			alert('<%=strs%>')
		</script>
	<%
}
%>

<body>
<form name="frm" action="accountMng.jsp" method="post">
<input type="hidden" name="pageSize" value="<%=size%>">
<input type="hidden" name="pageIndex" value="<%=pageIndex%>">
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
	frm.submit();
</SCRIPT>