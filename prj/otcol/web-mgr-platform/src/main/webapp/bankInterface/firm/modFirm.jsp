<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self">

<%
	String firmID = request.getParameter("firmID");
	
	BankDAO dao = BankDAOFactory.getDAO();
	FirmValue firm = dao.getFirm(firmID);
	if(firm == null)
	{
		out.println("�����̲����ڣ�");
		return;
	}
	if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
	{
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		String name = request.getParameter("name");
		double maxPerSglTransMoney = Tool.strToDouble(request.getParameter("maxPerSglTransMoney"));
		double maxPerTransMoney = Tool.strToDouble(request.getParameter("maxPerTransMoney"));
		int maxPerTransCount = Tool.strToInt(request.getParameter("maxPerTransCount"));
		double maxAuditMoney = Tool.strToDouble(request.getParameter("maxAuditMoney"));
		
		firm.firmID = firmID;
		firm.name = name;
		firm.maxPerSglTransMoney = maxPerSglTransMoney;
		firm.maxPerTransMoney = maxPerTransMoney;
		firm.maxPerTransCount = maxPerTransCount;
		firm.maxAuditMoney = maxAuditMoney;
	
		int result = 0;
		try
		{
			result = dao.modFirm(firm);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = -1;
		}
		String msg = "";
		if(result == 0)
		{
			msg = "�޸Ľ����̳ɹ��������̴��룺"+firmID+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
			%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("�������޸ĳɹ���");
				//window.opener.location.reload();
				window.returnValue="1";
				window.close();
				//-->
				</SCRIPT>	
			<%
			return;
		}
		else
		{
			msg = "�������޸�ʧ�ܣ������̴��룺"+firmID+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
			lv.setLogcontent(msg);
			dao.log(lv);
			%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert('<%=msg %>');
				//-->
				</SCRIPT>	
			<%
		}
	}
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>�޸Ľ�����</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>�޸Ľ�����</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">�����̴��룺&nbsp;</td>
					<td align="left">
						<input name="firmID" readonly value="<%=firm.firmID%>" type=text  class="text" maxlength="10" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�������ת�˽�&nbsp;</td>
					<td align="left">
						<input name="maxPerSglTransMoney" maxlength="11" value="<%=firm.maxPerSglTransMoney<=0?"":Tool.fmtDouble2(firm.maxPerSglTransMoney)%>" type=text  class="text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">ÿ�����ת�˴�����&nbsp;</td>
					<td align="left">
						<input name="maxPerTransCount" maxlength="3" value="<%=firm.maxPerTransCount<=0?"":firm.maxPerTransCount%>" type=text  class="text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">ÿ�����ת�˽�&nbsp;</td>
					<td align="left">
						<input name="maxPerTransMoney" maxlength="11" value="<%=firm.maxPerTransMoney<=0?"":Tool.fmtDouble2(firm.maxPerTransMoney)%>" type=text  class="text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">������˶�ȣ�&nbsp;</td>
					<td align="left">
						<input name="maxAuditMoney" maxlength="11" value="<%=firm.maxAuditMoney<0?"":Tool.fmtDouble2(firm.maxAuditMoney)%>" type=text  class="text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35">					
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doMod();">�޸�</button>&nbsp;
						<button type="button" class="smlbtn" onclick="window.close();">ȡ��</button>&nbsp;
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

function doMod()
{
	frm.submitFlag.value = "do";
	frm.submit();
}

//-->
</SCRIPT>