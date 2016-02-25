<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self"> 

<%
String bankID = request.getParameter("bankID");

BankDAO dao = BankDAOFactory.getDAO();
BankValue bank = dao.getBank(bankID);
if(bank == null)
{
	out.println("���в����ڣ�");
	return;
}
int con = bank.control;
String begint=bank.beginTime;
String endt = bank.endTime;
begint=begint.replaceAll(":|-|/|\\\\| ", "");
endt=endt.replaceAll(":|-|/|\\\\| ", "");
String bhour = begint.substring(0,2);
String bminite = begint.substring(2,4);
String bsecond = begint.substring(4,6);
String ehour = endt.substring(0,2);
String eminite = endt.substring(2,4);
String esecond = endt.substring(4,6);
if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
{
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogdate(new Date());
	lv.setIp(computerIP);
	String bankName = request.getParameter("bankName");
	double maxPerSglTransMoney = Tool.strToDouble(request.getParameter("maxPerSglTransMoney"));
	double maxPerTransMoney = Tool.strToDouble(request.getParameter("maxPerTransMoney"));
	int maxPerTransCount = Tool.strToInt(request.getParameter("maxPerTransCount"));
	double maxAuditMoney = Tool.strToDouble(request.getParameter("maxAuditMoney")); 
	String adapterClassname = request.getParameter("adapterClassname");
	String beginTime = request.getParameter("beginTime");
	String endTime = request.getParameter("endTime");
	int control=Tool.strToInt(request.getParameter("control"));
	
	bank.bankID = bankID;
	bank.bankName = bankName;
	bank.maxAuditMoney = maxAuditMoney;
	bank.maxPerSglTransMoney = maxPerSglTransMoney;
	bank.maxPerTransMoney = maxPerTransMoney;
	bank.maxPerTransCount = maxPerTransCount;
	bank.beginTime = beginTime;
	bank.endTime = endTime;
	bank.adapterClassname = adapterClassname;
	bank.control = control;

	int result = 0;
	try
	{
		dao.modBank(bank);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		result = -1;
	}
	lv.setLogcontent(result==0?"�޸����гɹ������д��룺"+bankID+"ʱ�䣺"+Tool.fmtTime(new java.util.Date()):"�޸�����ʧ�ܣ����д��룺"+bankID+"ʱ�䣺"+Tool.fmtTime(new java.util.Date()));
	dao.log(lv);
	if(result == 0)
	{
		%>
		<SCRIPT LANGUAGE="JavaScript">
			<!--
			alert("�����޸ĳɹ���");
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
		%>
		<SCRIPT LANGUAGE="JavaScript">
			<!--
			alert("�����޸�ʧ�ܣ�");
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
    <title>�޸�����</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
  	<input name="adapterClassname" value="<%=bank.adapterClassname%>" type="hidden">
		<fieldset width="95%">
			<legend>�޸�����</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">���д��룺&nbsp;</td>
					<td align="left">
						<input name="bankID" readonly value="<%=bank.bankID%>" type=text  class="text" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�������ƣ�&nbsp;</td>
					<td align="left">
						<input name="bankName" value="<%=bank.bankName%>" type=text  class="text" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�������ת�˽�&nbsp;</td>
					<td align="left">
						<input name="maxPerSglTransMoney" maxlength="11" value="<%=Tool.fmtDouble2(bank.maxPerSglTransMoney)%>" type=text  class="text" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">ÿ�����ת�˴�����&nbsp;</td>
					<td align="left">
						<input name="maxPerTransCount" maxlength="3" value="<%=bank.maxPerTransCount%>" type=text  class="text" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">ÿ�����ת�˽�&nbsp;</td>
					<td align="left">
						<input name="maxPerTransMoney" maxlength="11" value="<%=Tool.fmtDouble2(bank.maxPerTransMoney)%>" type=text  class="text" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">������˶�ȣ�&nbsp;</td>
					<td>
						<input name="maxAuditMoney" maxlength="11" value="<%=Tool.fmtDouble2(bank.maxAuditMoney)%>" type="text" class="text" style="width: 140px;"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">ת������&nbsp;</td>
					<td>
						<select name="control">
							<option <%=con==0 ? "selected" : ""%> value="0">����ʱ��˫����</option>
							<option <%=con==2 ? "selected" : ""%> value="2">ֻ����������</option>
							<option <%=con==3 ? "selected" : ""%> value="3">ֻ��ʱ������</option>
							<option <%=con==1 ? "selected" : ""%> value="1">������</option>
						</select><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">ת�˿�ʼʱ��&nbsp;</td>
					<td>
						<input name="bhour" value="<%=bhour%>" type="text" class="text" maxlength="2" style="width: 30px;">ʱ
						<input name="bminite" value="<%=bminite%>" type="text" class="text" maxlength="2" style="width: 30px;">��
						<input name="bsecond" value="<%=bsecond%>" type="text" class="text" maxlength="2" style="width: 30px;">��
						<input name="beginTime" type="hidden" value="">
					</td>
				</tr>
				<tr height="35">
					<td align="right">ת�˽���ʱ��&nbsp;</td>
					<td>
						<input name="ehour" value="<%=ehour%>" type="text" class="text" maxlength="2" style="width: 30px;">ʱ
						<input name="eminite" value="<%=eminite%>" type="text" class="text" maxlength="2" style="width: 30px;">��
						<input name="esecond" value="<%=esecond%>" type="text" class="text" maxlength="2" style="width: 30px;">��
						<input name="endTime" type="hidden" value="">
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
	if(trim(frm.bankID.value) == "")
	{
		alert("���������д��룡");
		frm.bankID.focus();
	}
	else if(trim(frm.bankName.value) == "")
	{
		alert("�������������ƣ�");
		frm.bankName.focus();
	}
	else if(trim(frm.maxPerSglTransMoney.value) == "" || isNaN(frm.maxPerSglTransMoney.value) || frm.maxPerSglTransMoney.value <= 0)
	{
		alert("����ȷ���뵥�����ת�˽�");
		frm.maxPerSglTransMoney.focus();
	}
	else if(trim(frm.maxPerTransCount.value) == "" || isNaN(frm.maxPerTransCount.value) || frm.maxPerTransCount.value <= 0)
	{
		alert("����ȷ����ÿ�����ת�˴�����");
		frm.maxPerTransCount.focus();
	}
	else if(trim(frm.maxPerTransMoney.value) == "" || isNaN(frm.maxPerTransMoney.value) || frm.maxPerTransMoney.value <= 0)
	{
		alert("����ȷ����ÿ�����ת�˽�");
		frm.maxPerTransMoney.focus();
	}
	else
	{
		var bhour = frm.bhour.value;
		var bminite = frm.bminite.value;
		var bsecond = frm.bsecond.value;
		var ehour = frm.ehour.value;
		var eminite = frm.eminite.value;
		var esecond = frm.esecond.value;
		bhour = getNum(bhour,"00");
		bminite = getNum(bminite,"00");
		bsecond = getNum(bsecond,"00");
		ehour = getNum(ehour,"23");
		eminite = getNum(eminite,"59");
		esecond = getNum(esecond,"59");
		frm.beginTime.value=bhour+":"+bminite+":"+bsecond;
		frm.endTime.value=ehour+":"+eminite+":"+esecond;
		frm.submitFlag.value = "do";
		frm.submit();
	}
}

//�ж��Ƿ�Ϊ����
function getNum(text,defaul){
	var param=/^[0-9]{1,2}$/;
	var param2=/^[0-9]{1}$/;
	if(!param.exec(text)){
		text = defaul;
	}else if(param2.exec(text)){
		text = "0" + text;
	}
	return text;
}
//-->
</SCRIPT>