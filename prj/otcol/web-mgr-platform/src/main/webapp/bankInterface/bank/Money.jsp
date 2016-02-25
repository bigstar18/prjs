<%@ page contentType="text/html;charset=GBK" %>
<%@page import="gnnt.trade.bank.vo.BankValue"%>
<%@page import="gnnt.trade.bank.util.Tool"%>
<%@ include file="../globalDef.jsp"%>
<%@include file="../session.jsp"%>
<%@ page import="gnnt.trade.bank.*" %>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">	
	<link rel="stylesheet" href="skin/default/css/style.css" type="text/css"/>
	
	<link rel="stylesheet" href="css/button.css" type="text/css"/>
	<link rel="stylesheet" href="css/print.css" type="text/css"/>
	<link rel="stylesheet" href="css/report.css" type="text/css"/>
    <IMPORT namespace="MEBS" implementation="public/jstools/calendar.htc">
    <title>�ʽ𻮲�</title>
	<%
  	CapitalProcessorRMI cp = null;
	try
	{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	Vector bankList=cp.getBankList(" where bankid='66' ");
  	
	String results2="û������";
	
	String bankQuery = request.getParameter("bankQuery");
	String FIRMID = "MarketGSOut";
	long result = -1;
	String results = result+"";
	if(cp==null)
	{
		results="����Rmi����";
	}
	int express=0;
	
	
	String optValue = request.getParameter("opt");
	boolean showBabkBalance = true;
	FirmBalanceValue fv = null;
	if(cp!=null)
	{
		System.out.println("111"+optValue+"bbb");
		if(optValue!=null && "query".equals(optValue))
		{System.out.println("222:"+bankQuery);
			fv = cp.getBankBalance(bankQuery,FIRMID,"");
		}
	}
	
	
  %>
	<script language="JavaScript">
	<%response.setCharacterEncoding("GBK");%>
	function doSubmitOut() 
	{
		frm.opt.value="out";
		if(frm.bank.value == -1)
		{
			alert("��ѡ�����У�");
		}
		else if(frm.money.value == "" || isNaN(frm.money.value) || frm.money.value<=0)
		{
			alert("��������ȷ�Ľ�");
		}
		else{
			if(confirm("��ȷ���������滮��������"))
			{
		 		frm.submit();
			}
		}
	}
	
	function doSubmitQuery() 
	{
		frm.opt.value="query";
		if(frm.bankQuery.value == -1)
		{
			alert("��ѡ�����У�");
		}
		else
		{
			frm.submit();
		}
	}
	
	function resetForm()
	{
		if(frm.inoutMoney)
		{
			frm.inoutMoney.value="1";
		}
		if(frm.bankID)
		{
			frm.bankID.value="-1";
		}
		if(frm.money)
		{
			frm.money.value="";
		}
		if(frm.password)
		{
			frm.password.value="";
		}
		if(frm.IsExpress)
		{
			frm.IsExpress.value="";
		}
	}
		
	function checkNum()   
	{
	 	if(frm.money.value!='')
	 	{
		  var txt = frm.money.value;//��У���ֵ
			var pattern= /^([1-9]{1}*[0-9][0-9]*|0)((\.\d)|(\.\d\d))?$/;
			if(!pattern.exec(txt))
			{
			    alert("������Ϸ����������ֵ!");
			    frm.money.value="0";
			}
		}
	}
	
	function NoSubmit(ev)
	{
	
	    if( ev.keyCode == 13 )
	    {
	        return false;
	    }
	    return true;
	}
	</script>
  </head>
  <!-- /^([1-9]\d{0,2}(,\d{3})*|0)((\.\d)|(\.\d\d))?$/; -->
  <body >

  	<form id="frm" action="MoneyAction.jsp" method="post"  onkeypress="javascript:return NoSubmit(event);">
	<input type="hidden" name="opt">
		<fieldset width="95%">
			<legend>�ʽ𻮲�������˻� -> ������˻���</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				
				<tr height="35">
					<td name="bankID" width="30%" align="right">ѡ������&nbsp;</td>
					<td align="left">
						<select  name="bank" class="normal" >
							<OPTION value="-1">��ѡ��</OPTION>
							<% 
							for(int i=0;i<bankList.size();i++)
							{
							  	BankValue bankValue=(BankValue)bankList.get(i);
								if(isOutMoneyBank(bankValue.bankID)){
									if(i==0){
									%>
										<option value="<%=bankValue.bankID%>" selected><%=bankValue.bankName%></option>
									<%
									}else{
									%>
										<option value="<%=bankValue.bankID%>" ><%=bankValue.bankName%></option>
									<%
									}
								}
							}
							%>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td width="30%" align="right">���뻮ת���&nbsp;</td>
					<td align="left">
						<input  name="money" value="" type=text  class="text"style="width: 100px" onblur="checkNum()">����λ��Ԫ   ���ȣ��֣�
					</td>
				</tr>
				
				<tr height="35">
					<td align="center" colspan=2>
						<input type="button" class="smlbtn" value="ȷ��" onclick="doSubmitOut();">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="smlbtn" value="����" onclick="resetForm();">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
				</tr>
				
			</table>
		</fieldset>
		<fieldset width="95%">
			<legend>��ѯ����˻����</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">				
				<tr height="35">
					<td width="30%" align="right">ѡ������&nbsp;</td>
					<td align="left">
						<select  name="bankQuery" class="normal" >
							<OPTION value="-1">��ѡ��</OPTION>
							<%
							for(int i=0;i<bankList.size();i++){
							  	BankValue bankValue=(BankValue)bankList.get(i);
								if(isOutMoneyBank(bankValue.bankID)){
									if(i==0){
									%>
										<option value="<%=bankValue.bankID%>" selected><%=bankValue.bankName%></option>
									<%
									}else{
									%>
										<option value="<%=bankValue.bankID%>" ><%=bankValue.bankName%></option>
									<%
									}
								}
							}
							%>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="center" colspan=2>
						<input type="button" class="smlbtn" value="��ѯ���" onclick="doSubmitQuery();">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset width="95%">
			<legend>�˻������Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<%
				if(fv==null)
				{
				%>
				<tr height="35">
					<td align="center" colspan="2" style="color: red "><%=results2%></td>
				</tr>
				<%
				}else{
				%>
					<tr height="35">
						<td width="30%" align="right">����ʺ�:&nbsp;</td>
						<td width="30%" align="left"><%=Tool.getConfig("marketGSAcount")%></td>
					</tr>
					<tr height="35">
						<td width="30%" align="right">�˻����:&nbsp;</td>
						<td width="30%" align="left"><%=Tool.fmtDouble(fv.getBankBalance()) %></td>
					</tr>
				<%
				}
				%>
			</table>
		</fieldset>
		
	</form>	
  </body>
</html>
