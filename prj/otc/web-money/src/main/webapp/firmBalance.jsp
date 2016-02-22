<%@ page contentType="text/html;charset=GBK" %>
<%@page import="gnnt.trade.bank.vo.BankValue"%>
<%@page import="gnnt.trade.bank.util.Tool"%>
<jsp:directive.page import="java.text.DecimalFormat"/>
<%@ include file="globalDef.jsp"%>
<%@include file="session.jsp"%>
<%@include file="ajax.jsp"%>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">	
	<link rel="stylesheet" href="skin/default/css/style.css" type="text/css"/>
	
	<link rel="stylesheet" href="css/button.css" type="text/css"/>
	<link rel="stylesheet" href="css/print.css" type="text/css"/>
	<link rel="stylesheet" href="css/report.css" type="text/css"/>
    <IMPORT namespace="MEBS" implementation="public/jstools/calendar.htc">
    <title>查询余额</title>
	<script language="JavaScript">
		function doSubmit() {
			if(frm.bank.value == -1) {
				alert("请选择银行！");
			} else if(frm.password.value==""){
				alert("请输入密码！");
			} else {
				document.getElementById("submitbtn").disabled = 'disabled';
				frm.opt.value="query";
				frm.submit();
			}
		}
	</script>
  </head>
  <%
  	BankDAO dao = BankDAOFactory.getDAO();
	Vector<FirmFundsBankValue> firmFundsBanks = dao.getFirmFundsBank((String)session.getAttribute("FIRMID"));
	String bankid = request.getParameter("bank");
	FirmBalanceValue fv = null;
	try {
		if("query".equals(request.getParameter("opt"))) {
		  	CapitalProcessorRMI cp = null;
			try {
				cp = getBankUrl(bankid);
			} catch(Exception e) {
				e.printStackTrace();
			}
			boolean flag = true;
			if(!showPasswordBank("2",bankid,"1")) {
				long result = cp.isPassword((String)session.getAttribute("FIRMID"),request.getParameter("password"));
				if(result == 1){
					flag = false;
				%>
					<script>
						alert("请先设置密码");
					</script>
				<%
				}else if(result == -1){
					flag = false;
				%>
					<script>
						alert("密码验证失败");
					</script>
				<%
				}else if(result == -2){
					flag = false;
				%>
					<script>
						alert("未查到交易商");
					</script>
				<%
				}
			}
			if(flag){
				fv = cp.getFirmBalance(bankid,(String)session.getAttribute("FIRMID"),request.getParameter("password"));
			}
		}
	} catch(Exception e) {
		e.printStackTrace();
	}
  %>
  <script>
	function showPwdBox() {
		var bankid = frm.bank.value;
		if(bankid != -1){
			if(showPasswordBank("2",bankid,"1")){
				document.getElementById('pawdname').innerText="银行密码  ";
			}else{
				document.getElementById('pawdname').innerText="<%=marketpwd%>  ";
			}
		}else{
			document.getElementById('pawdname').innerText="<%=marketpwd%>  ";
		}
	}
	function changeBank(){
		var bankID = document.getElementById("bank").value;
		frm.submitbtn.disabled='';
		if(bankID=='006'){
			alert("华夏银行暂不支持银行余额查询功能，请使用网银等其他方式查询");
			frm.submitbtn.disabled='disabled';
			return;
		}
		showPwdBox();
		if(frm.bank.value != -1){
			validate(frm.bank.value,'<%=(String)session.getAttribute("FIRMID")%>');
		}else{
			//frm.bankTime.value="";
			document.getElementById("bankTime").innerHTML="";
		}
	}
  </script>
  <body oncontextmenu="return false" onload='changeBank()'>
  	<form id="frm" method="post" action="firmBalance.jsp?fid=<%=fid%>&sid=<%=sid%>">
		<fieldset width="95%">
			<legend>银行余额查询</legend>
			<input type="hidden" name="opt">
			<table border="0" cellspacing="0" cellpadding="0" style="width: 580px;" height="35">				
				<tr height="35">
					<td width="20%" align="right">选择银行&nbsp;</td>
					<td align="left">
						<select  name="bank" class="normal" onchange="changeBank()">
							<OPTION value="-1">请选择</OPTION>
							<% 
							if(firmFundsBanks != null && firmFundsBanks.size()>0){
							  for(int i=0;i<firmFundsBanks.size();i++) {
								  FirmFundsBankValue ffb=(FirmFundsBankValue)firmFundsBanks.get(i);
							%>
								<option value="<%=ffb.bankID%>" <%=(ffb.bankID.trim().equals(bankid)) ? "selected" : ""%>><%=ffb.bankName%></option>
							<%
							}}
							%>
						</select><!-- <input type="text" name="bankTime" readonly value=""> -->
					</td>
					<td width="60%" id="bankTime" name="bankTime"></td>
				</tr>
				<tr height="35" id="tel1">
					<td align="right" id="pawdname"><%=marketpwd%>&nbsp;</td>
					<td align="left">
						<input  name="password" maxlength="6" value="" type="password"  class="text">
					</td>
					<td></td>
				</tr>
				<tr height="35">
				<td></td>
					<td align="center">
						<input type="button" id='submitbtn' class="smlbtn" value="查询余额" onclick="doSubmit();">
					</td>
					<td></td>
				</tr>
			</table>
		</fieldset>
		<fieldset width="95%">
			<legend>银行余额信息</legend>
			<table border="1" cellspacing="0" cellpadding="0" style="width: 580px;" height="35">
				<%
				if(!"query".equals(request.getParameter("opt"))){
				%>
				<tr height="35">
					<td align="center">请选择银行查询您的资金信息.</td>
				</tr>
				<%
				} else if(fv==null) {
				%>
				<tr height="35">
					<td align="center" style="color: red ">验证密码失败.</td>
				</tr>
				<%
				} else if(fv.getCanOutMoney()<0){
					new ErrorCode().load();
					String message=ErrorCode.error.get((long)fv.getCanOutMoney());
					if(message==null) message = fv.getBankBalance()+"";
				%>
				<tr height="35">
					<td align="center" style="color: red "><%=message%></td>
				</tr>
				<%
				}else {
					if(fv.getBankBalance()<0) {
						new ErrorCode().load();
						String message=ErrorCode.error.get((long)fv.getBankBalance());
						if(message==null) message = fv.getBankBalance()+"";
					%>
					<tr height="35">
						<td width="80%" align="center"><font color="red"><%=message %></font></td>
					</tr>
					<%
					}else{
					%>
					<tr height="35">
						<td width="20%" align="right">银行账户:&nbsp;</td>
						<td align="left"><%=Tool.delNull(fv.getBankAccount()) %></td>
					</tr>
					<tr height="35">
						<td width="20%" align="right">银行余额:&nbsp;</td>
						<td align="left"><%=Tool.fmtMoney(fv.getBankBalance()) %></td>
					</tr>
				<%}
				}
				%>
			</table>
		</fieldset>
	</form>	
  </body>
</html>
