<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
	BankDAO dao = BankDAOFactory.getDAO();
	
	String firmID = request.getParameter("firmID");
	String bankID = request.getParameter("bankID");
	String account = request.getParameter("account");
	String account1 = request.getParameter("account1");
	CorrespondValue corr = dao.getCorrespond(bankID,firmID,account);

	if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
	{
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogtime(new Date());
		lv.setIp(computerIP);
		String bankName = Tool.delNull(request.getParameter("bankName"));
		String bankProvince = Tool.delNull(request.getParameter("bankProvince"));
		String bankCity = Tool.delNull(request.getParameter("bankCity"));
		String mobile = Tool.delNull(request.getParameter("mobile"));
		String email = Tool.delNull(request.getParameter("Email"));
		String accountName = Tool.delNull(request.getParameter("accountName"));
		String account2 = Tool.delNull(request.getParameter("account2"));
		String card2 = Tool.delNull(request.getParameter("card2"));
		String cardType = Tool.delNull(request.getParameter("cardType"));

		corr.bankCity = bankCity;
		corr.bankName = bankName;
		corr.bankProvince = bankProvince;
		corr.email = email;
		corr.mobile = mobile;
		corr.accountName=accountName;
		corr.account=account2;
		corr.card=card2;
		if(checkAccount(bankID)){
			if(corr.isOpen !=1){
				corr.account = Tool.getConfig("DefaultAccount");
			}
		}
		corr.cardType=cardType;
		
		int result = 0;
		try
		{
			result = dao.modCorrespond(corr);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = -1;
		}
		S账ring msg = "";
		if(result == 0)
		{
			msg = "修改交易商银行帐号成功，交易商代码："+firmID+"，新银行账号："+account+"时间："+Tool.fmtTime(new java.util.Date());
			%>账			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("交易商银行帐号修改成功！");
				window.location.href="accountMng.jsp?firmID=<%=firmID%>";
				//window.close();
				//-账>
				</SCRIPT>	
			<%
			return;
		}
		else
		{
			msg = "交易商银行帐号修改失败，交易商代码："+firmID+"，新银行账号："+account+"时间："+Tool.fmtTime(new java.util.Date());
			%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert('<%=msg %>');
				//-->
				</SCRIPT>	
			<%
		}
		lv.setLogcontent(msg);
		dao.log(lv);
	}
%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=账BK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>修改交易商银行帐号</title>
  </head>
  
  <账ody>
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>修改交易商银行帐号</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">交易商代码：&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" readonly type=text  class="text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">银行：&nbsp;</td>
					<td align="left">
						<input name="bankID" value="<%=bankID%>" readonly type=text  class="text" max账ength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">出入金帐号：&nbsp;</td>
					<td align="left">
						<input name="account2" value="<%=replaceNull(account)%>" <%=(corr.isOpen==1) ? "readonly" : ""%> type=text class="text" maxlength="30"><span class=star>*</span>
						<input name="account" value="<%=replaceNull(account)%>"type="hidden" class="text" maxlength="30">
					</td>
				</tr>
				<tr height="35">
					<td align="right">出入金账户名：&nbsp;</td>
					<td align="left"> 
						<input name="accountName" value="<%=replaceNull(corr.accountName)%>" <%=(corr.isOpen==1) ? "readonly" : ""%> type=text  class="text" maxlength="64"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">证件类型：&nbsp;</td>
					<td align="left">
						<select name="cardType">
							<option value="">请选择</option>
							<option value="1" <%=(corr.cardType!=null && corr.cardType.equals("1")) ? "selected" : ""%>>身份证</option>
							<option value="8" <%=(corr.cardType!=null && corr.cardType.equals("8")) ? "selected" : ""%>>组织机构代码</option>
							<option value="9" <%=(corr.cardType!=null && corr.cardType.equals("9")) ? "selected" : ""%>>营业执照</option>
							<option value="a" <%=(corr.cardType!=null && corr.cardType.equals("a")) ? "selected" : ""%>>法人代码证</option>
						</select>
					</td>
					<SCRIPT LANGUAGE="JavaScript">
						//frm.cardType.value='<%=replaceNull(corr.cardType)%>';
					</SCRIPT>
				</tr>
				<tr height="35">
					<td align="right">证件号码：&nbsp;</td>
					<td align="left">
						<input name="card2" value="<%=replaceNull(corr.card)%>" <%=(corr.isOpen==1) ? "readonly" : ""%> type=text  class="text" maxlength="30"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">开户行名称：&nbsp;</td>
					<td align="left">
						<input name="bankName" value="<%=replaceNull(corr.bankName)%>" type=text  class="text" maxlength="30">
					</td>
				</tr>
				<tr height="35">
					<td align="right">开户行省份：&nbsp;</td>
					<td align="left">
						<input name="bankProvince" value="<%=replaceNull(corr.bankProvince)%>" type=text  class="text" maxlength="20">
					</td>
				</tr>
				<tr height="35">
					<td align="right">开户行所在市：&nbsp;</td>
					<td align="left">
						<input name="bankCity" value="<%=replaceNull(corr.bankCity)%>" type=text  class="text" maxlength="20">
					</td>
				</tr>
				<tr height="35">
					<td align="right">电话：&nbsp;</td>
					<td align="left">
						<input name="mobile" value="<%=replaceNull(corr.mobile)%>" type=text  class="text" maxlength="20">
					</td>
				</tr>
				<tr height="35">
					<td align="right">电子邮件：&nbsp;</td>
					<td align="left">
						<input name="Email" value="<%=replaceNull(corr.email)%>" type=text  class="text" maxlength="30">
					</td>
				</tr>
				<tr height="35">					
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doAdd();">修改</button>&nbsp;
						<button type="button" class="smlbtn" onclick="javaScript:window.location.href='accountMng.jsp?firmID=<%=firmID %>';">返回</button>&nbsp;
						<input type=hidden name=submitFlag value="">
					</td>
				</tr>
			</table>
		</fieldset>	  
	</from>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--

function doAdd()
{	
	if(trim(frm.cardType.value) == "")
	{
		alert("请选择证件类型");
		frm.cardType.focus();
	}
	else if(trim(frm.card2.value) == "")
	{
		alert("请输入证件号码");
		frm.card2.focus();
	}
	else if(trim(frm.accountName.value) == "")
	{
		alert("请输入账户名");
		frm.accountName.focus();
	}
	else if(trim(frm.account.value) == "")
	{
		alert("请输入银行账户");
		frm.account.focus();
	}
	else
	{
		
		//if(<%=(corr.isOpen==1)%>){
			//frm.cardType.value='<%=replaceNull(corr.cardType)%>';
		//}
		frm.submitFlag.value = "do";
		frm.submit();
	}
}

//-->
</SCRIPT>