<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ page import="gnnt.trade.bank.dao.tlapay.*"%>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
request.setCharacterEncoding("GBK");
	String TLAPayBankID = "027";
	String firmID = request.getParameter("firmID");
	CorrespondValue corr = new CorrespondValue();
	TLAPayBankDAO dao = null;
	try{
		dao =BankDAOFactory.getTLAPayDAO();
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
	<script language="javascript">
	var req;
	function Change_Select(){//����һ���������ѡ����ı�ʱ���øú���
      var OpenBankCodeText = document.getElementById('OpenBankCodeText').value;
      
	  var url = "./bankCodeAjax.jsp?OpenBankCodeText=" + OpenBankCodeText;
	  if(window.XMLHttpRequest){
	  	req = new XMLHttpRequest();
	  }else if(window.ActiveXObject){
	  	req = new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  if(req){
	  	req.open("GET",url,true);         
	  	req.onreadystatechange = callback;//ָ���ص�����Ϊcallback
	  	req.send(null);
	  }
    }
    //�ص�����
    function callback(){
      if(req.readyState ==4){
        if(req.status ==200){
          parseMessage();//����XML�ĵ�
        }else{
          alert("���ܵõ�������Ϣ:" + req.statusText);
        }
      }
    }
    //��������xml�ķ���
    function parseMessage(){	  
		var res = req.responseText;
		if(!(res.indexOf('select')==-1)){//ȡ������ʱ
	     	document.getElementById("OpenBankCode").innerHTML = res;
	    }else{//û��ȡ������ʱ
	     	alert("ȡ�ö�Ӧ������ʧ��");
			return;
	    }
    }
	function init(){
		document.getElementById("OpenBankCode").innerHTML = "<select style='width: 140px'><option value='0' selected>--��ѡ��--</option></select>";
	}
  </script>
	
  </head>
  
  <body onload="init()">
  	<form id="frm" action="openAccount2.jsp" method="post">
	<input type="hidden" name="firmID" value="<%=firmID%>">
		<fieldset width="95%">
			<legend>�󶨽������˻���ͨ���˻�֧��ϵͳ</legend>
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
						<input name="bankID" value="ͨ���˻�֧��" readonly disabled='disabled' type=text  class="text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����ʺţ�&nbsp;</td>
					<td align="left">
						<input name="account"   type=text class="text" maxlength="30"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�˻����ƣ�&nbsp;</td>
					<td align="left"> 
						<input name="accountName" value="<%=Tool.delNull(corr.accountName)%>" type=text  class="text" maxlength="64"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35" id="bankNameFlag">
					<td align="right">�������ƣ�&nbsp;</td>
					<td align="left">
						<input type="text" name="bankName" id="bankName"  class="text"  maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35" id="OpenBankCodeFlag">
					<td align="right">֧���кţ�&nbsp;</td>
					<td align="left">
						<input type="text" name="OpenBankCodeText" id="OpenBankCodeText" onBlur="Change_Select()"  class="text"  maxlength="30" style="width: 140px"><span class=star>*</span>
						<div id="OpenBankCode"></div>
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
						<input name="card" value="<%=Tool.delNull(corr.card)%>"  type=text  class="text" maxlength="30"><span class=star>*</span>
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
	if(trim(frm.account.value) == "")
	{
		alert("�����������˻�");
		frm.account.focus();
	}
	else  if(trim(frm.accountName.value) == "")
	{
		alert("�������˻�����");
		frm.accountName.focus();
	}
	else if(trim(frm.bankName.value)=="")
	{
		alert("�����뿪����������Ϣ��");
		frm.bankName.focus();
	}
	else if(trim(frm.OpenBankCode.value)=="")
	{
		alert("�����뿪�����кţ�");
		frm.OpenBankCode.focus();
	}
	else if(trim(frm.cardType.value) == "")
	{
		alert("��ѡ��֤������");
		frm.cardType.focus();
	}
	else if(trim(frm.card.value) == "")
	{
		alert("������֤������");
		frm.card.focus();
	}
	else
	{
		
		frm.submitFlag.value = "do";
		frm.submit();
	}
}

//-->
</SCRIPT>