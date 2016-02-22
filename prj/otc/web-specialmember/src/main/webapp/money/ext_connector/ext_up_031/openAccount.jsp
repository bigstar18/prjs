<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
	UPBankDAO dao = BankDAOFactory.getUPDAO();
		String UPBankID = "031";

UPCapitalProcessorRMI cp=getUPBankUrl(UPBankID);
	try {		
		cp = getUPBankUrl(UPBankID);
	} catch(Exception e) {
		e.printStackTrace();
	}
	String firmID = (String)session.getAttribute("REGISTERID");
String filter=" and firmid='"+firmID+"' " ;
Vector<CorrespondValue> corVector = dao.getCorrespondList(filter);
CorrespondValue corr1 = new CorrespondValue();
if(corVector!=null&&corVector.size()>0){
	corr1=corVector.get(0);
}
	if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
	{
		String bankID = request.getParameter("bankID");
		
		String account = request.getParameter("account");
		
		//String accountName = Tool.delNull(request.getParameter("accountName"));
		
		//String bankName = Tool.delNull(request.getParameter("bankName"));
		//String bankProvince = Tool.delNull(request.getParameter("bankProvince"));
		//String bankCity = Tool.delNull(request.getParameter("bankCity"));
		String OpenBankCode = Tool.delNull(request.getParameter("OpenBankCode"));
		String accountName =  Tool.delNull(new String(request.getParameter("accountName").getBytes("ISO-8859-1"),"GBK"));
		
		String bankName =  Tool.delNull(new String(request.getParameter("bankName").getBytes("ISO-8859-1"),"GBK"));
		String bankProvince = Tool.delNull(new String(request.getParameter("bankProvince").getBytes("ISO-8859-1"),"GBK"));
		String bankCity =  Tool.delNull(new String(request.getParameter("bankCity").getBytes("ISO-8859-1"),"GBK"));
		
		CorrespondValue corr = corr1;
		corr.account = account;
		
		corr.accountName = accountName;
		
		corr.OpenBankCode = OpenBankCode;
		corr.bankCity = bankCity;
		corr.bankID = "031";
		corr.bankName = bankName;
		corr.bankProvince = bankProvince;		
		corr.firmID = firmID;
		
		corr.status = 1;
		corr.isOpen = 0;
		long result = 0;
		
		try
		{
			if(dao.getCorrespond(bankID,firmID,account)!=null)
			{
				result = -2;
			}
			else
			{
				result = cp.rgstAccount(corr);
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = -1;
		}
		String msg = "";
		if(result == 0)
		{
			msg = "�����������ʺ�ע��ɹ�����ϵ�г�ǩԼ���˺�";
		}
		else if(result == -2)
		{
			msg = "�����������ʺŶ�Ӧ��ϵ�Ѵ��ڣ�";
		}
		else
		{
			msg = "�����������ʺ�ע��ʧ�ܣ�";
		}	

		%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert('<%=msg %>');
				//window.opener.location.reload();
				window.returnValue="1";
				window.close();
				//-->
				</SCRIPT>	
			<%
		
	}
%>


<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>ע�ύ���������ʺ�</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>ע�ύ���������ʺ�</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">�����̴��룺&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" readonly type=text  class="text" maxlength="10" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				
				
				<tr height="35">
					<td align="right">�����ʺţ�&nbsp;</td>
					<td align="left">
						<input name="account" value="" type=text  class="text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�ظ������ʺţ�&nbsp;</td>
					<td align="left">
						<input name="reaccount" value="" type=text  class="text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				
				<tr height="35">
					<td align="right">�˻�����&nbsp;</td>
					<td align="left">
						<input name="accountName" value="<%=corr1.accountName%>" type=text  class="text" maxlength="20" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				
				
				<tr height="35">
				
					<td align="right">�������кţ�&nbsp;</td>
					<td align="left">
					<%
						Vector bankCode = dao.getBankCodeList("   ");
						%>
						<select name="OpenBankCode">
						<option value="">��ѡ��</option>
						<%
							for(int i=0;i<bankCode.size();i++)
							{
								BankCodes bank = (BankCodes)bankCode.get(i);
								%>
								<option value="<%=bank.bankCode%>"><%=bank.bankName%></option>	
								<%
							}
							%>
							
						</select><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">���������ƣ�&nbsp;</td>
					<td align="left">
						<input name="bankName" value="" type=text  class="text" maxlength="50" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">������ʡ�ݣ�&nbsp;</td>
					<td align="left">
						<input name="bankProvince" value="" type=text  class="text" maxlength="20" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����������У�&nbsp;</td>
					<td align="left">
						<input name="bankCity" value="" type=text  class="text" maxlength="20" style="width: 140px">
					</td>
				</tr>
				
				<tr height="35">					
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doAdd();">���</button>&nbsp;
						<button type="button" class="smlbtn" onclick="window.close();">ȡ��</button>&nbsp;
						<input type=hidden name=submitFlag value="">
					</td>
				</tr>
			</table>
		</fieldset>	  
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--

function doAdd()
{
	if(trim(frm.account.value) == "")
	{
		alert("�����������ʺţ�");
		frm.account.focus();
	}
	else if(trim(frm.reaccount.value) == "")
	{
		alert("���ظ������ʺţ�");
		frm.account.focus();
	}
	else if(trim(frm.reaccount.value) != trim(frm.account.value))
	{
		alert("�����˺����ظ������ʺŲ�һ�£�");
		frm.reaccount.value="";
		frm.account.focus();
	}
	else if(trim(frm.accountName.value) == "")
	{
		alert("�������˻���");
		frm.accountName.focus();
	}
	else if(trim(frm.OpenBankCode.value) == "")
	{
		alert("��ѡ�񿪻���");
		frm.OpenBankCode.focus();
	}
	else
	{
		
		frm.submitFlag.value = "do";
		frm.submit();
	}
}

//-->
</SCRIPT>