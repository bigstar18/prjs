<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="globalDef.jsp"%>
<%@include file="session.jsp"%>
<base target="_self">
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">	
	<link rel="stylesheet" href="skin/default/css/style.css" type="text/css"/>
	<link rel="stylesheet" href="css/button.css" type="text/css"/>
	<link rel="stylesheet" href="css/print.css" type="text/css"/>
	<link rel="stylesheet" href="css/report.css" type="text/css"/>
    <title>�޸�<%=marketpwd%></title>
	<%
	
	CapitalProcessorRMI cp = null;
	try{
		cp = getBankUrl("");
	}catch(Exception e){
		e.printStackTrace();
	}
	String FIRMID = (String)session.getAttribute("REGISTERID");
	String oldpassword = ""+request.getParameter("oldpwd");
	String newpassword = ""+request.getParameter("newpwd");
	long flag = cp.isPassword(FIRMID,oldpassword);
	
	
	if("do".equals(request.getParameter("doSubmit"))){
		String msg="";
		boolean flg=false;
		if(FIRMID!=null){
			try{
				flag = cp.isPassword(FIRMID,oldpassword);
				if(flag>=0){
					long flag2 = cp.modPwd(FIRMID,oldpassword,newpassword);
					if(flag2>=0){
						msg = "�޸�����ɹ�";
						flg=true;
					}else{
						msg="�޸�����ʧ��";
					}
				}else{
					msg="������ľ��������";
				}
			}catch(Exception e){
				msg="�޸�����ʱϵͳ�����쳣";
			}
		}else{
			msg="���ĵ�¼�Ѿ�ʧЧ�������µ�¼";
		}
		%>
			<script language="JavaScript">
				alert('<%=msg%>');
				if('true'=='<%=flg%>'){
					window.returnValue="1";
					location.replace(location) ;
				}
			</script>
		<%
	}
	%>
	<script language="JavaScript">
		function changepwd()
		{
			var old=frm.oldpwd.value;
			var tnew=frm.newpwd.value;
			var rtnew=frm.rnewpwd.value;
			if(tnew=="")
			{
				alert("������������");
			}
			else if(!isnum(tnew))
			{
				alert("������������6λ����");
			}
			else if(rtnew=="")
			{
				alert("���ظ�������");
			}
			else if(tnew != rtnew)
			{
				alert("�������������벻һ��");
				frm.newpwd.value="";
				frm.rnewpwd.value="";
			}
			else
			{
				document.getElementById("sub_btn").disabled = 'disabled';
				frm.doSubmit.value="do";
				frm.submit();
			}
		}
		//�ж�1-6λ����
		function isnum(num)
		{
			var result=false;
			var pattern = /^[0-9]{6}$/;
			if(pattern.exec(num))
			{
				result=true;
			}
			return result;
		}
	</script>
  </head>
  <body>
  <table border="0" cellspacing="0" class="table1_style" cellpadding="0">
  <tr>
  <td>  
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend fontsize="2">�޸�<%=marketpwd%></legend>
			<table align="center">
			<%if(flag==1){ %>
				<tr>
				<td colspan="2" align="center">���ǳ��������ʽ�����</td>
				<input type="hidden" id="oldpwd" name="oldpwd" value="" />
				</tr>
			<% }else{%>
				<tr>
					<td>������ԭ���룺&nbsp;</td>
					<td><input type="password" class="input_text"style="width: 100px" maxlength=6 id="oldpwd" name="oldpwd" value=""></td>
				</tr>
			
			<%} %>
				<tr>
					<td>�����������룺&nbsp;</td>
					<td><input type="password" class="input_text"style="width: 100px" maxlength=6 id="newpwd" name="newpwd" value=""></td>
				</tr>
				<tr>
					<td>���ظ������룺&nbsp;</td>
					<td><input type="password" class="input_text"style="width: 100px" maxlength=6 id="rnewpwd" name="rnewpwd" value=""></td>
				</tr>
				<tr>
					<td colspan='2' align='center'>
						<input id="sub_btn" type="button" class="btn_sec" value="�޸�" onclick="changepwd();">
						<input type="hidden" id="doSubmit" name="doSubmit" value="">
					</td>
				</td>
			</table>
		</fieldset>
	</form>	
		</td>
  </tr>
</table>
  </body>
</html>
