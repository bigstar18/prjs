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
		S��ring msg = "";
		if(result == 0)
		{
			msg = "�޸Ľ����������ʺųɹ��������̴��룺"+firmID+"���������˺ţ�"+account+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
			%>��			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("�����������ʺ��޸ĳɹ���");
				window.location.href="accountMng.jsp?firmID=<%=firmID%>";
				//window.close();
				//-��>
				</SCRIPT>	
			<%
			return;
		}
		else
		{
			msg = "�����������ʺ��޸�ʧ�ܣ������̴��룺"+firmID+"���������˺ţ�"+account+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
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
	<META http-equiv="Content-Type" content="text/html; charset=��BK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>�޸Ľ����������ʺ�</title>
  </head>
  
  <��ody>
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>�޸Ľ����������ʺ�</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">�����̴��룺&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" readonly type=text  class="text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">���У�&nbsp;</td>
					<td align="left">
						<input name="bankID" value="<%=bankID%>" readonly type=text  class="text" max��ength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">������ʺţ�&nbsp;</td>
					<td align="left">
						<input name="account2" value="<%=replaceNull(account)%>" <%=(corr.isOpen==1) ? "readonly" : ""%> type=text class="text" maxlength="30"><span class=star>*</span>
						<input name="account" value="<%=replaceNull(account)%>"type="hidden" class="text" maxlength="30">
					</td>
				</tr>
				<tr height="35">
					<td align="right">������˻�����&nbsp;</td>
					<td align="left"> 
						<input name="accountName" value="<%=replaceNull(corr.accountName)%>" <%=(corr.isOpen==1) ? "readonly" : ""%> type=text  class="text" maxlength="64"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">֤�����ͣ�&nbsp;</td>
					<td align="left">
						<select name="cardType">
							<option value="">��ѡ��</option>
							<option value="1" <%=(corr.cardType!=null && corr.cardType.equals("1")) ? "selected" : ""%>>���֤</option>
							<option value="8" <%=(corr.cardType!=null && corr.cardType.equals("8")) ? "selected" : ""%>>��֯��������</option>
							<option value="9" <%=(corr.cardType!=null && corr.cardType.equals("9")) ? "selected" : ""%>>Ӫҵִ��</option>
							<option value="a" <%=(corr.cardType!=null && corr.cardType.equals("a")) ? "selected" : ""%>>���˴���֤</option>
						</select>
					</td>
					<SCRIPT LANGUAGE="JavaScript">
						//frm.cardType.value='<%=replaceNull(corr.cardType)%>';
					</SCRIPT>
				</tr>
				<tr height="35">
					<td align="right">֤�����룺&nbsp;</td>
					<td align="left">
						<input name="card2" value="<%=replaceNull(corr.card)%>" <%=(corr.isOpen==1) ? "readonly" : ""%> type=text  class="text" maxlength="30"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">���������ƣ�&nbsp;</td>
					<td align="left">
						<input name="bankName" value="<%=replaceNull(corr.bankName)%>" type=text  class="text" maxlength="30">
					</td>
				</tr>
				<tr height="35">
					<td align="right">������ʡ�ݣ�&nbsp;</td>
					<td align="left">
						<input name="bankProvince" value="<%=replaceNull(corr.bankProvince)%>" type=text  class="text" maxlength="20">
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����������У�&nbsp;</td>
					<td align="left">
						<input name="bankCity" value="<%=replaceNull(corr.bankCity)%>" type=text  class="text" maxlength="20">
					</td>
				</tr>
				<tr height="35">
					<td align="right">�绰��&nbsp;</td>
					<td align="left">
						<input name="mobile" value="<%=replaceNull(corr.mobile)%>" type=text  class="text" maxlength="20">
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����ʼ���&nbsp;</td>
					<td align="left">
						<input name="Email" value="<%=replaceNull(corr.email)%>" type=text  class="text" maxlength="30">
					</td>
				</tr>
				<tr height="35">					
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doAdd();">�޸�</button>&nbsp;
						<button type="button" class="smlbtn" onclick="javaScript:window.location.href='accountMng.jsp?firmID=<%=firmID %>';">����</button>&nbsp;
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
		alert("��ѡ��֤������");
		frm.cardType.focus();
	}
	else if(trim(frm.card2.value) == "")
	{
		alert("������֤������");
		frm.card2.focus();
	}
	else if(trim(frm.accountName.value) == "")
	{
		alert("�������˻���");
		frm.accountName.focus();
	}
	else if(trim(frm.account.value) == "")
	{
		alert("�����������˻�");
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