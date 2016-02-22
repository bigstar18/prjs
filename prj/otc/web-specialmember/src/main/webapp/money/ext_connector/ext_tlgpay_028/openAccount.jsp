<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ page import="gnnt.trade.bank.dao.tlgpay.*"%>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
request.setCharacterEncoding("GBK");
	String TLGPayBankID = "028";
	String firmID = request.getParameter("firmID");
	CorrespondValue corr = new CorrespondValue();
	TLGPayBankDAO dao = null;
	try{
		dao =BankDAOFactory.getTLGPayDAO();
		Vector<CorrespondValue> vc = dao.getCorrespondList(" and firmID = '" + firmID + "'  ");
		if(vc != null && vc.size() > 0){
			corr = vc.get(0);
		}
	}catch(ClassNotFoundException e){
		e.printStackTrace();
		System.out.println(Tool.getExceptionTrace(e));
	}catch(IllegalAccessException e){
		e.printStackTrace();
		System.out.println(Tool.getExceptionTrace(e));
	}catch(InstantiationException e){
		e.printStackTrace();
		System.out.println(Tool.getExceptionTrace(e));
	}catch(Exception e){
		e.printStackTrace();
		System.out.println(Tool.getExceptionTrace(e));
	}
%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>ǩԼ�����������ʺ�</title>
  </head>
  
  <body>
  	<form id="frm" action="openAccount2.jsp" method="post">
	<input type="hidden" name="firmID" value="<%=firmID%>">
	<input name="account" value="99999999999999999"  type=hidden class="text" maxlength="30">
		<fieldset width="95%">
			<legend>�󶨽������˻�����������</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">�����˺ţ�&nbsp;</td>
					<td align="left">
						<input name="contact" value="<%=corr.contact%>"  readonly type=text  class="text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">ǩԼ���У�&nbsp;</td>
					<td align="left">
						<input name="bankID" value="ͨ������֧��" readonly disabled='disabled' type=text  class="text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
					
				<tr height="35">
					<td align="right">�˻����ƣ�&nbsp;</td>
					<td align="left"> 
						<input name="accountName" value="<%=Tool.delNull(corr.accountName)%>" type=text  class="text" maxlength="64"><span class=star>*</span>
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
				</tr>
				<tr height="35">
					<td align="right">֤�����룺&nbsp;</td>
					<td align="left">
						<input name="card" value="<%=Tool.delNull(corr.card)%>"  type=text  class="text" maxlength="30">
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
	if(trim(frm.accountName.value) == "")
	{
		alert("�������˻�����");
		frm.accountName.focus();
	}
	else
	{
		frm.submitFlag.value = "do";
		frm.submit();
	}
}

//-->
</SCRIPT>