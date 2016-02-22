<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.text.DecimalFormat"/>
<%@ include file="globalDef.jsp"%>
<%@include file="session.jsp"%>
<%@include file="ajax.jsp"%>
<html>
  <head>
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
	Vector<FirmFundsBankValue> firmFundsBanks = dao.getFirmFundsBank((String)session.getAttribute("REGISTERID"));
	FirmFundsValue firmFunds = dao.getFirmFunds((String)session.getAttribute("REGISTERID"));
	String bankid = request.getParameter("bank");
	String pwd = request.getParameter("password");
	String bankPwd = request.getParameter("bankPassword");
	String FIRMID = (String)session.getAttribute("REGISTERID");
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
			if(showPasswordBank("2",bankid,"1")) {
				long result = cp.isPassword(FIRMID,pwd);
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
						alert("资金密码验证失败");
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
				fv = cp.getFirmBalance(bankid,FIRMID,bankPwd);
			}
		}
	} catch(Exception e) {
		e.printStackTrace();
	}
  %>
  <script>
	function showPwdBox()
		{
			var bankid = frm.bank.value;
			if(isPasswordBank(bankid)){
				document.getElementById('bankPwd').style.display="block";
			}else{
				document.getElementById('bankPwd').style.display="none";
			}
			if("25" == bankid){
				document.getElementById('bankPasswordname').innerText="银行查询密码  ";
			}else{
				document.getElementById('bankPasswordname').innerText="银行密码  ";
			}
		}
	function changeBank(){
	    var bankID = document.getElementById("bank").value;
		frm.submitbtn.disabled='';
		
		if(bankID=='050'){
			alert("此银行暂不支持银行余额查询功能，请使用网银等其他方式查询");
			frm.submitbtn.disabled='disabled';
			return;
		}
		if(bankID=='66'){
			alert("国付宝暂不支持余额查询功能");
			frm.submitbtn.disabled='disabled';
			return;
		}
		showPwdBox();
		if(frm.bank.value != -1){
			validate(frm.bank.value,'<%=(String)session.getAttribute("REGISTERID")%>');
		}else{
			document.getElementById("bankTime").innerHTML="";
		}
	}
  </script>
  <body oncontextmenu="return false" onload='changeBank()'>
   <table border="0" cellspacing="0" class="table1_style" cellpadding="0">
  <tr>
  <td>  
  	<form id="frm" method="post">
		<fieldset width="95%">
			<legend fontsize="2">查询余额</legend>
			<input type="hidden" name="opt">
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">				
				<tr height="35">
					<td width="30%" align="right">选择银行&nbsp;</td>
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
						</select><!-- <input type="text" name="bankTime" disabled='disabled' readonly value=""> -->
					</td>
					<td name="bankTime" id="bankTime" width="60%"></td>
				</tr>
					<tr height="35" id="tel1">
						<td width="30%" align="right" id="passwordname">资金密码&nbsp;</td>
						<td align="left">
							<input  name="password" value="" type="password"  class="text">
						</td>
					</tr>
					<tr height="35" id="bankPwd" style="display: none;">
						<td width="30%" align="right" id="bankPasswordname">银行密码&nbsp;</td>
						<td align="left">
							<input  name="bankPassword" value="" type="password"  class="text">
						</td>
					</tr>
				<tr height="35">
					<td></td>
					<td align="center">
						<input type="button" id='submitbtn' class="btn_sec" value="查询余额" onclick="doSubmit();">
					</td>
					<td></td>
				</tr>
			</table>
		</fieldset>
		<fieldset width="95%">
			<legend fontsize="2">余额信息</legend>
			<div >
			<table border="0" cellspacing="0" cellpadding="0" width="40%" height="35">
				<%
				if(!"query".equals(request.getParameter("opt"))){
				%>
				<tr height="35">
					<td align="center" colspan="2">请选择银行查询您的资金信息.</td>
				</tr>
				<%
				} else if(fv==null) {
				%>
				<tr height="35">
					<td align="center" colspan="2" style="color: red ">验证密码失败.</td>
				</tr>
				<%
				} else if(fv.getCanOutMoney()<0){
					new ErrorCode().load();
					String message=ErrorCode.error.get((long)fv.getCanOutMoney());
					if(message==null) message = fv.getBankBalance()+"";
				%>
				<tr height="35">
					<td align="center" colspan="2" style="color: red "><%=message%></td>
				</tr>
				<%
				} else {
					%>
					<tr height="35">
					<td width="30%" align="right">会员编号:&nbsp;</td>
					<td align="left"><%=fv.getFirmId() %></td>
				</tr>
					<%
					if(fv.getBankBalance()<0) {
						new ErrorCode().load();
						String message=ErrorCode.error.get((long)fv.getBankBalance());
						if(message==null) message = fv.getBankBalance()+"";
					%>
					<tr height="35">
						<td width="80%" align="center" colspan="2"><font color="red"><%=message %></font></td>
					</tr>
					<%
					}else{
					%>
					<tr height="35">
						<td width="30%" align="right">银行余额:&nbsp;</td>
						<td width="30%" align="left"><%=Tool.fmtMoney(fv.getBankBalance()) %></td>
					</tr>
				<%}
				}
				%>
			</table>
			</div>
		</fieldset>
	</form>	
	</td>
  </tr>
</table>
  </body>
</html>