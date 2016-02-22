<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>


<%
String firmID = request.getParameter("MfirmID");
String contact = request.getParameter("Mcontact");
String bankCardPassword = request.getParameter("MbankCardPassword");
int size = Tool.strToInt(request.getParameter("pageSize"));
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
BankDAO dao = BankDAOFactory.getDAO();
CapitalProcessorRMI cp = null;
try{
	cp = getBankUrl("");
}catch(Exception e){
	e.printStackTrace();
}
LogValue lv = new LogValue();
lv.setLogtime(new Date());
lv.setIp(computerIP);
Vector<CorrespondValue> cps = dao.getCorrespondList(" and firmid='"+firmID+"'");
CorrespondValue corr = null;
if(cps != null && cps.size()>0){
	corr = cps.get(0);
	ErrorCode errorcode = new ErrorCode();
	errorcode.load();
}
String headmsg = CONTACTTITLE+"管理";
String strs = "";
long bak = -1;
if ("recPassword".equalsIgnoreCase(request.getParameter("submitFlag"))){//初始化密码
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
	lv.setLogtype("2210");
	lv.setLogoprtype("M");
	lv.setResult(bak==0 ? 0 : 1);
	lv.setLogcontent(str+"-"+strs);
	lv.setLogopr((String)session.getAttribute("CURRENUSERID"));
	lv.setMark((String)session.getAttribute("REGISTERID"));
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
<input type="hidden" name="firmID" value="<%=firmID%>">
<input type="hidden" name="pageSize" value="<%=size%>">
<input type="hidden" name="pageIndex" value="<%=pageIndex%>">
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
	frm.submit();
</SCRIPT>