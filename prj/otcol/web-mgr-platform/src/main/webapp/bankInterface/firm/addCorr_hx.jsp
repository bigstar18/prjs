<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self"> 
<%
	BankDAO dao = BankDAOFactory.getDAO();
	CapitalProcessorRMI cp = null;
	try
	{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	String firmID = request.getParameter("firmID");
	String bankID = request.getParameter("bankID");
	String account = request.getParameter("account");
	CorrespondValue corrVa = dao.getCorrespond(bankID,firmID,account);
	if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
	{
		
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		String accountName1 = Tool.delNull(request.getParameter("accountName1"));
		String AccountProp = Tool.delNull(request.getParameter("AccountProp"));
		String RelatingAcct = Tool.delNull(request.getParameter("RelatingAcct"));
		String RelatingAcctName = Tool.delNull(request.getParameter("RelatingAcctName"));
		String RelatingAcctBank = Tool.delNull(request.getParameter("RelatingAcctBank"));
		String RelatingAcctBankAddr = Tool.delNull(request.getParameter("RelatingAcctBankAddr"));
		String RelatingAcctBankCode = Tool.delNull(request.getParameter("RelatingAcctBankCode"));
		String PersonName = Tool.delNull(request.getParameter("PersonName"));
		String OfficeTel = Tool.delNull(request.getParameter("OfficeTel"));
		String MobileTel = Tool.delNull(request.getParameter("MobileTel"));
		String Addr = Tool.delNull(request.getParameter("Addr"));
		String LawName = Tool.delNull(request.getParameter("LawName"));
		String cardType = Tool.delNull(request.getParameter("cardType"));
		String card = Tool.delNull(request.getParameter("card"));
		String NoteFlag = Tool.delNull(request.getParameter("NoteFlag"));
		String NotePhone = Tool.delNull(request.getParameter("NotePhone"));
		String eMail = Tool.delNull(request.getParameter("eMail"));
		String zipCode = Tool.delNull(request.getParameter("ZipCode"));
		String checkFlag = Tool.delNull(request.getParameter("CheckFlag"));
		
		CorrespondValue corr = new CorrespondValue();
		corr.bankID = bankID;
		corr.firmID = firmID;
		corr.account = RelatingAcct;
		corr.accountName = RelatingAcctName;
		corr.accountName1 = accountName1;
		corr.bankName = RelatingAcctBank;
		corr.bankCity = RelatingAcctBankAddr;
		corr.mobile = OfficeTel;
		corr.status = 0;
		corr.isOpen = 1;
		corr.cardType = cardType;
		corr.card = card;
		corr.OpenBankCode = RelatingAcctBankCode;
		corr.Phone = MobileTel;
		corr.Linkman = PersonName;
		corr.addr = Addr;
		corr.LawName = LawName;
		corr.NoteFlag = NoteFlag;
		corr.NotePhone = NotePhone;
		corr.email = eMail;
		corr.zipCode = zipCode;
		corr.checkFlag = checkFlag;
		
		ReturnValue retuenValue=new ReturnValue();
		try
		{
			
				retuenValue.result = cp.rgstAccount(corr);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			retuenValue.result = -1;
		}
		String msg = "";
		if(retuenValue.result == 0)
		{
			msg = "�����������ʺ�ǩԼ�ɹ���";
		}
		else
		{
			msg = "�����������ʺ�ǩԼʧ�ܣ�";
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
		lv.setLogcontent(msg+" ���д��룺"+bankID+"�����̴��룺"+firmID);
		dao.log(lv);
	}
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>ע�Ừ�����н����������ʺ�</title>
  </head>
  
  <body>
  	<form id="frm" name="frm" action="" method="post">
		<fieldset width="95%" id="other">
			<legend>ע�Ừ�����н����������ʺ�</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">�����̴��룺&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" readonly type=text  class="text" maxlength="10" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">���˻����ƣ�&nbsp;</td>
					<td align="left">
						<input name="accountName1" value="<%=replaceNull(corrVa.accountName1)%>" type=text  class="text" maxlength="10" style="width: 140px"><span class=star>*</span>				
					</td>
				</tr>
				<tr height="35">
					<td align="right">�˻����ͣ�&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "1" name="AccountProp" CHECKED/>����
						<input type = "radio" value = "0" name="AccountProp" />��ҵ
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����˺�(����)��&nbsp;</td>
					<td align="left">
						<input name="RelatingAcct" value="<%=replaceNull(corrVa.account)%>" type=text  class="text" maxlength="32" style="width: 140px"><span class=star>*</span>	
					</td>
				</tr>
				
				<tr height="35">
					<td align="right">�����˻�����&nbsp;</td>
					<td align="left">
						<input name="RelatingAcctName" value="<%=replaceNull(corrVa.accountName)%>" type=text  class="text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">���������ƣ�&nbsp;</td>
					<td align="left">
						<input name="RelatingAcctBank" value="<%=replaceNull(corrVa.bankName)%>" type=text  class="text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����е�ַ��&nbsp;</td>
					<td align="left">
						<input name="RelatingAcctBankAddr" value="<%=replaceNull(corrVa.bankCity)%>" type=text  class="text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�������кţ�&nbsp;</td>
					<td align="left">
						<input name="RelatingAcctBankCode" value="<%=replaceNull(corrVa.OpenBankCode)%>" type=text  class="text" maxlength="12" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">��ϵ�ˣ�&nbsp;</td>
					<td align="left">
						<input name="PersonName" value="<%=replaceNull(corrVa.Linkman)%>" type=text  class="text" maxlength="50" style="width: 140px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�칫�绰��&nbsp;</td>
					<td align="left">
						<input name="OfficeTel" value="<%=replaceNull(corrVa.mobile)%>" type=text  class="text" maxlength="20" style="width: 140px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�ƶ��绰��&nbsp;</td>
					<td align="left">
						<input name="MobileTel" value="<%=replaceNull(corrVa.Phone)%>" type=text  class="text" maxlength="20" style="width: 140px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
					</td>
				</tr>
				<tr height="35">
					<td align="right">��ϵ��ַ��&nbsp;</td>
					<td align="left">
						<input name="Addr" value="<%=replaceNull(corrVa.addr)%>" type=text  class="text" maxlength="20" style="width: 140px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�������룺&nbsp;</td>
					<td align="left">
						<input name="ZipCode" value="<%=replaceNull(corrVa.zipCode)%>" type=text  class="text" maxlength="20" style="width: 140px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
					</td>
				</tr>
				<tr height="35">
					<td align="right">����������&nbsp;</td>
					<td align="left">
						<input name="LawName" value="<%=replaceNull(corrVa.LawName)%>" type=text  class="text" maxlength="20" style="width: 140px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
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
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">֤�����룺&nbsp;</td>
					<td align="left">
						<input name="card" value="<%=replaceNull(corrVa.card)%>" type=text  class="text" maxlength="20" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�Ƿ���Ҫ����֪ͨ��&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "1" name="NoteFlag" CHECKED/>��Ҫ
						<input type = "radio" value = "0" name="NoteFlag" />����Ҫ
					</td>
				</tr>
				<tr height="35">
					<td align="right">����֪ͨ�ֻ��ţ�&nbsp;</td>
					<td align="left">
						<input name="NotePhone" value="<%=replaceNull(corrVa.NotePhone)%>" type=text  class="text" maxlength="20" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����ʼ���&nbsp;</td>
					<td align="left">
						<input name="eMail" value="<%=replaceNull(corrVa.email)%>" type=text  class="text" maxlength="20" style="width: 140px"><font color="red">&nbsp;</font>
					</td>
				</tr>
				<tr height="35">
					<td align="right">���˱�ʶ��&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "1" name="CheckFlag" CHECKED/>��Ҫ
						<input type = "radio" value = "0" name="CheckFlag" />����Ҫ
					</td>
				</tr>
				<tr height="35">					
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doAdd();">ǩԼ</button>&nbsp;
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
	if(trim(frm.accountName1.value) == "")
	{
		alert("���������˺����ƣ�");
		frm.accountName1.focus();
	}else if(trim(frm.RelatingAcct.value) == "")
	{
		alert("��������������ʺţ�");
		frm.RelatingAcct.focus();
	}
	else if(trim(frm.RelatingAcctName.value) == "")
	{
		alert("����������˻�����");
		frm.RelatingAcctName.focus();
	}
	else if(trim(frm.RelatingAcctBank.value) == "")
	{
		alert("��ѡ���������ƣ�");
		frm.RelatingAcctBank.focus();
	}
	else if(trim(frm.RelatingAcctBankAddr.value) == "")
	{
		alert("�����뿪���е�ַ��");
		frm.RelatingAcctBankAddr.focus();
	}
	else if(trim(frm.RelatingAcctBankCode.value) == "")
	{
		alert("�����뿪����ϵͳ�кţ�");
		frm.RelatingAcctBankCode.focus();
	}	
	else if(document.all("AccountProp")[1].checked && trim(frm.PersonName.value) == "")
	{
		alert("��ҵ�˻�����������ϵ�ˣ�");
		frm.PersonName.focus();
	}
	else if(document.all("AccountProp")[1].checked && trim(frm.PersonName.value) == "")
	{
		alert("��ҵ�˻�����������ϵ�ˣ�");
		frm.PersonName.focus();
	}
	else if(document.all("AccountProp")[1].checked && trim(frm.OfficeTel.value) == "")
	{
		alert("��ҵ�˻���������칫�绰��");
		frm.OfficeTel.focus();
	}
	else if(document.all("AccountProp")[1].checked && trim(frm.MobileTel.value) == "")
	{
		alert("��ҵ�˻����������ƶ��绰��");
		frm.MobileTel.focus();
	}
	else if(document.all("AccountProp")[1].checked && trim(frm.Addr.value) == "")
	{
		alert("��ҵ�˻�����������ϵ��ַ��");
		frm.Addr.focus();
	}
	else if(document.all("AccountProp")[1].checked && trim(frm.LawName.value) == "")
	{
		alert("��ҵ�˻��������뷨��������");
		frm.LawName.focus();
	}	
	else if(trim(frm.cardType.value) == "")
	{
		alert("��ѡ��֤�����ͣ�");
		frm.cardType.focus();
	}	
	else if(trim(frm.card.value) == "")
	{
		alert("������֤�����룡");
		frm.card.focus();
	}	
	else if(document.all("NoteFlag")[0].checked && trim(frm.NotePhone.value) == "")
	{
		alert("���������֪ͨ�ֻ��ţ�");
		frm.NotePhone.focus();
	}	
	else
	{
		
		//if(frm.bankID.value == "005" || frm.bankID.value == "01"){
		//	frm.account.value="<%=Tool.getConfig("DefaultAccount")%>";
		//}
		frm.submitFlag.value = "do";
		frm.submit();
	}
}


//-->
</SCRIPT>