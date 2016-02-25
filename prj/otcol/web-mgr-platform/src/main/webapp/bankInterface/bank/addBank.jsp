<%@ page contentType="text/html;charset=GB2312" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self"> 

<%
BankDAO dao = BankDAOFactory.getDAO();
if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
{
	String bankID = request.getParameter("bankID");
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogdate(new Date());
	lv.setIp(computerIP);
	String bankName = request.getParameter("bankName");
	double maxPerSglTransMoney = Tool.strToDouble(request.getParameter("maxPerSglTransMoney"));
	double maxPerTransMoney = Tool.strToDouble(request.getParameter("maxPerTransMoney"));
	int maxPerTransCount = Tool.strToInt(request.getParameter("maxPerTransCount"));
	double maxAuditMoney = Tool.strToDouble(request.getParameter("maxAuditMoney"));
	String adapterIP = request.getParameter("adapterIP");
	String adapterPort = request.getParameter("adapterPort"); 
	String className = request.getParameter("adapterClassname");
	String beginTime = request.getParameter("beginTime");
	String endTime = request.getParameter("endTime");
	int control = Tool.strToInt(request.getParameter("control"));

	String adapterClassname="//"+adapterIP+":"+adapterPort+"/"+className;

	BankValue bank = new BankValue();
	bank.bankID = bankID;
	bank.bankName = bankName;
	bank.maxAuditMoney = maxAuditMoney;
	bank.maxPerSglTransMoney = maxPerSglTransMoney;
	bank.maxPerTransMoney = maxPerTransMoney;
	bank.maxPerTransCount = maxPerTransCount;
	bank.adapterClassname = adapterClassname;
	bank.validFlag = 0;
	bank.beginTime = beginTime;
	bank.endTime = endTime;
	bank.control = control;

	int result = 0;
	try
	{
		dao.addBank(bank);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		result = -1;
	}
	lv.setLogcontent(result==0?"������гɹ������д��룺"+bankID+"ʱ�䣺"+Tool.fmtTime(new java.util.Date()):"�������ʧ�ܣ����д��룺"+bankID+"ʱ�䣺"+Tool.fmtTime(new java.util.Date()));
	dao.log(lv);
	if(result == 0)
	{
		%>
		<SCRIPT LANGUAGE="JavaScript">
			<!--
			alert("������ӳɹ���");
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
			alert("�������ʧ�ܣ�");
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
    <title>��������</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="45%">
			<legend>��������</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">���д��룺&nbsp;</td>
					<td>
						<input name="bankID" type="text" class="text" style="width: 140px;" reqfv = "required;���д���"  onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�������ƣ�&nbsp;</td>
					<td>
						<input name="bankName" type="text" class="text" style="width: 140px;" reqfv = "required;��������" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�������ת�˽�&nbsp;</td>
					<td>
						<input name="maxPerSglTransMoney" type="text" class="text" maxlength="11" style="width: 140px;" reqfv = "REQ_MLT;1;1;0;0;�������ת�˽��" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">ÿ�����ת�˴�����&nbsp;</td>
					<td>
						<input name="maxPerTransCount" type="text" class="text" maxlength="3" style="width: 140px;" reqfv = "REQ_MLT;1;1;0;0;ÿ�����ת�˴���" maxlength="10" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">ÿ�����ת�˽�&nbsp;</td>
					<td>
						<input name="maxPerTransMoney" type="text" class="text" maxlength="11" style="width: 140px;" reqfv = "REQ_MLT;1;1;0;0;ÿ�����ת�˽��" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">������˶�ȣ�&nbsp;</td>
					<td>
						<input name="maxAuditMoney" type="text" class="text" maxlength="11" style="width: 140px;" reqfv = "REQ_MLT;1;1;0;0;������˶��" maxlength="10" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����������ַ��&nbsp;</td>
					<td>
						<input name="adapterIP" type="text" class="text" style="width: 140px;" reqfv = "required;�����������ַ" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">����������˿ڣ�&nbsp;</td>
					<td>
						<input name="adapterPort" type="text" class="text" style="width: 140px;" reqfv = "required;����������˿�" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">������ʵ�������ƣ�&nbsp;</td>
					<td>
						<input name="adapterClassname" type="text" class="text" style="width: 140px;" reqfv = "required;������ʵ��������" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">ת������&nbsp;</td>
					<td>
						<select name="control">
							<option value="0">����ʱ��˫����</option>
							<option value="2">ֻ����������</option>
							<option value="3">ֻ��ʱ������</option>
							<option value="1">������</option>
						</select><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">ת�˿�ʼʱ��&nbsp;</td>
					<td>
						<input name="bhour" type="text" class="text" maxlength="2" style="width: 30px;">ʱ
						<input name="bminite" type="text" class="text" maxlength="2" style="width: 30px;">��
						<input name="bsecond" type="text" class="text" maxlength="2" style="width: 30px;">��
						<input name="beginTime" type="hidden" value="">
					</td>
				</tr>
				<tr height="35">
					<td align="right">ת�˽���ʱ��&nbsp;</td>
					<td>
						<input name="ehour" type="text" class="text" maxlength="2" style="width: 30px;">ʱ
						<input name="eminite" type="text" class="text" maxlength="2" style="width: 30px;">��
						<input name="esecond" type="text" class="text" maxlength="2" style="width: 30px;">��
						<input name="endTime" type="hidden" value="">
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
			if(checkValue("frm"))
			{
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