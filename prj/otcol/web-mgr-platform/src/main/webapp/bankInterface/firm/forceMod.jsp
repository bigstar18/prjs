<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self"> 
<%
	BankDAO dao = BankDAOFactory.getDAO();
	String firmID = request.getParameter("firmID");
	String bankID = request.getParameter("bankID");
	String account = request.getParameter("account");
	CorrespondValue corrVa = dao.getCorrespond(bankID,firmID,account);
	if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do")){
		
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		String account1 = request.getParameter("account1");
		String accountName = request.getParameter("accountName");
		String cardType = request.getParameter("cardType");
		String card = request.getParameter("card");
		String isOpen = request.getParameter("isOpen");
		String status = request.getParameter("status");
		String accountNew = request.getParameter("accountNew");
		corrVa.bankID = bankID;
		corrVa.account = accountNew;
		corrVa.account1 = account1;
		corrVa.accountName = accountName;
		corrVa.cardType = cardType;
		corrVa.card = card;
		corrVa.isOpen = Integer.parseInt(isOpen);
		corrVa.status = Integer.parseInt(status);
		int result = dao.modCorrespond(corrVa);
		String msg = "";
		if(result == 0){
			msg = "ǿ���޸�ǩԼ��ϵ�ɹ���";
		}else{
			msg = "ǿ���޸�ǩԼ��ϵʧ�ܣ�";
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
		lv.setLogcontent(msg+" ���д��룺"+bankID+"ƽ̨�˺ţ�"+firmID);
		dao.log(lv);
	}
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>ǩԼ��ϵǿ���޸�</title>
  </head>
  
  <body>
  	<form id="frm" name="frm" action="" method="post">
		<fieldset width="95%" id="other">
			<legend>ǩԼ��ϵǿ���޸�</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">ƽ̨�˺ţ�&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" readonly type=text  class="text" maxlength="10" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">ǩԼ���У�&nbsp;</td>
					<td align="left">
						<%
						Vector bankList = dao.getBankList(" where validFlag=0 ");
						%>
						<select name="bankID" readonly>
							<OPTION value="-1" disabled="">��ѡ��</OPTION>
							<%
							for(int i=0;i<bankList.size();i++)
							{
								BankValue bank = (BankValue)bankList.get(i);
								%>
								<option value="<%=bank.bankID%>" <%=(corrVa.bankID!=null && corrVa.bankID.equals(bank.bankID)) ? "selected" : ""%> <%=(corrVa.bankID!=null && corrVa.bankID.equals(bank.bankID)) ? "" : "disabled"%>><%=bank.bankName%></option>	
								<%
							}
							%>
						</select><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�������˺ţ�&nbsp;</td>
					<td align="left">
						<input name="account1" value="<%=replaceNull(corrVa.account1)%>" type=text  class="text" maxlength="32" style="width: 140px">		
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����˺ţ�&nbsp;</td>
					<td align="left">
						<input name="accountNew" value="<%=replaceNull(corrVa.account)%>" type=text  class="text" maxlength="32" style="width: 140px"><span class=star>*</span>	
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����˻�����&nbsp;</td>
					<td align="left">
						<input name="accountName" value="<%=replaceNull(corrVa.accountName)%>" type=text  class="text" maxlength="32" style="width: 140px"><span class=star>*</span>	
					</td>
				</tr>
				<tr height="35">
					<td align="right">֤�����ͣ�&nbsp;</td>
					<td align="left">
						<select name="cardType">
							<option value="">��ѡ��</option>
							<option value="1"<%=(corrVa.cardType!=null && corrVa.cardType.equals("1")) ? "selected" : ""%>>���֤</option>
							<!--<option value="2">����֤</option>
							<option value="3">���ڻ���</option>
							<option value="4">���ڱ�</option>
							<option value="5">ѧԱ֤</option>
							<option value="6">����֤</option>
							<option value="7">��ʱ���֤</option>-->
							<option value="8"<%=(corrVa.cardType!=null && corrVa.cardType.equals("8")) ? "selected" : ""%>>��֯��������</option>
							<option value="9"<%=(corrVa.cardType!=null && corrVa.cardType.equals("9")) ? "selected" : ""%>>Ӫҵִ��</option>
							<option value="a"<%=(corrVa.cardType!=null && corrVa.cardType.equals("a")) ? "selected" : ""%>>���˴���֤</option>
						</select><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">֤�����룺&nbsp;</td>
					<td align="left">
						<input name="card" value="<%=replaceNull(corrVa.card)%>" type=text  class="text" maxlength="20" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">ǩԼ״̬��&nbsp;</td>
					<td align="left">
						<select name="isOpen">
							<option value="1"<%=(corrVa.isOpen == 1) ? "selected" : ""%>>��ǩԼ</option>
							<option value="0"<%=(corrVa.isOpen != 1) ? "selected" : ""%>>δǩԼ</option>
						</select><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">����״̬��&nbsp;</td>
					<td align="left">
						<select name="status">
							<option value="0"<%=(corrVa.status == 0) ? "selected" : ""%>>����</option>
							<option value="1"<%=(corrVa.status != 0) ? "selected" : ""%>>������</option>
						</select><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">					
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doAdd();">�޸�</button>&nbsp;
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
	if(trim(frm.accountNew.value) == "")
	{
		alert("�����˺�Ϊ�գ�");
		frm.accountNew.focus();
	}else if(trim(frm.accountName.value) == "")
	{
		alert("�����˻���Ϊ�գ�");
		frm.accountName.focus();
	}
	else if(trim(frm.cardType.value) == "")
	{
		alert("δѡ��֤�����ͣ�");
		frm.cardType.focus();
	}
	else if(trim(frm.card.value) == "")
	{
		alert("֤������Ϊ�գ�");
		frm.card.focus();
	}
	else
	{
		
		//if(frm.bankID.value == "005" || frm.bankID.value == "01"){
		//	frm.account.value="<%=Tool.getConfig("DefaultAccount")%>";
		//}
		alert(frm.accountNew.value);
		frm.submitFlag.value = "do";
		frm.submit();
	}
}


//-->
</SCRIPT>