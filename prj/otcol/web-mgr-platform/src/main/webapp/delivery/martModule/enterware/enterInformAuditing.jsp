<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
	<title><%=TITLE%></title>
</head>
<body>
        <form id="formNew" name="frm" method="POST" targetType="hidden">
		<fieldset width="100%">
		<legend>�������֪ͨ��</legend>
			<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#333333" style="border-collapse:collapse;">
				<tr height="30px">
					<td colspan="5" class="cd_hr">******��Ʒ���֪ͨ��<FONT COLOR="red">��*******��</FONT></td>
				</tr>
			</table>
			<table width="96%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#333333" style="border-collapse:collapse;">
				<tr height="25px">
					<td width="6%" rowspan="2" class="cd_bt">
						<table border="0">
							<tr><td class="cd_bt">��</td></tr>
							<tr><td class="cd_bt">��</td></tr>
							<tr><td class="cd_bt">��</td></tr>
						</table>
					</td>
					<td width="18%" class="cd_bt">${FIRMNAME}</td>
					<td width="29%" class="cd_list1">*****</td>
					<td width="18%" class="cd_bt">${FIRMID}</td>
					<td width="29%" class="cd_list1">*****</td>
				</tr>
				<tr height="25px">
					<td class="cd_bt">��ϵ��</td>
					<td class="cd_list1">********</td>
					<td class="cd_bt">��ϵ�绰</td>
					<td class="cd_list1">********</td>
				</tr>
				<tr height="25px">
					<td rowspan="2" class="cd_bt">
						<table border="0">
							<tr><td class="cd_bt">��</td></tr>
							<tr><td class="cd_bt">��</td></tr>
						</table>
					</td>
					<td class="cd_bt">�ֿ�����</td>
					<td class="cd_list1">*******</td>
					<td class="cd_bt">��ϸ��ַ</td>
					<td class="cd_list1">********</td>
				</tr>
				<tr height="25px">
					<td class="cd_bt">��ϵ��</td>
					<td class="cd_list1">**********</td>
					<td class="cd_bt">��ϵ�绰</td>
					<td class="cd_list1">**********</td>
				</tr>
				<tr height="25px">
					<td rowspan="7" class="cd_bt">
						<table border="0">
							<tr><td class="cd_bt">��</td></tr>
							<tr><td class="cd_bt">Ʒ</td></tr>
							<tr><td class="cd_bt">��</td></tr>
							<tr><td class="cd_bt">Ϣ</td></tr>
						</table>
					</td>
					<td class="cd_bt">�����ʱ��</td>
					<td  class="cd_list1">***********</td>
					<td class="cd_bt">Ʒ������</td>
					<td class="cd_list1">***********</td>
				</tr>
				<tr height="25px">
					<td class="cd_bt">
						�������
						<jsp:include page="/public/countUnit.jsp">
							<jsp:param name="commodityID" value="***********"/>
						</jsp:include>
					</td>
					<td class="cd_list1">***************</td>
					<td class="cd_bt">�ȼ�</td>
					<td class="cd_list1">************</td>
				</tr>
				<tr height="25px">
					<td class="cd_bt">
						ÿ������
						<jsp:include page="/public/countUnit.jsp">
							<jsp:param name="commodityID" value="*********"/>
						</jsp:include>
					</td>
					<td class="cd_list1">**********</td>
					<td class="cd_bt">����</td>
					<td class="cd_list1">*************</td>
				</tr>
				<tr height="25px">
					<td class="cd_bt">����</td>
					<td class="cd_list1">**********</td>
					<td class="cd_bt">***********</td>
					<td class="cd_list1">***********</td>
				</tr>
				<tr height="25px">
					<td class="cd_bt">��������</td>
					<td class="cd_list1">
						**********&nbsp;	
					</td>
					<td class="cd_bt">��������</td>
					<td class="cd_list1">********</td>
				</tr>
				<tr height="25px">
					<td class="cd_bt">��ע</td>
					<td  class="cd_list1"><input name="remark" type="text" readOnly value="**************" class="form_k1"></td>
					<td class="cd_bt">�Ƿ��ʼ�</td>
					<td  class="cd_list1"><input name="remark" type="text" readOnly value="*********" class="form_k1"></td>
				</tr>
				<tr height="25px">
					<td width="18%" class="cd_bt">������뵥���</td>
					<td width="29%" class="cd_list1">***********</td>
					<td width="18%" class="cd_bt">���֪ͨ�����</td>
					<td width="29%" class="cd_list1">**********</td>
				</tr>
				<!--<tr height="25px">
					<td colspan="2" class="cd_bt"><FONT COLOR="red">��֤��*********��</FONT></td>
					<td colspan="3" class="cd_list1"><input name="bail" type="text" readOnly value="*********" class="form_k4"></td>
				</tr>-->
				<tr height="50px">
					<td class="cd_bt">
						<table border="0">
							<tr><td class="cd_bt">��</td></tr>
							<tr><td class="cd_bt">ʾ</td></tr>
						</table>
					</td>
					<td class="cd_list1" colspan=4><font color="blue">
						1������Ӧ������ǰ2���뽻�ղֿ�ȷ���������ڡ�<br>
						<!--&nbsp;2�����г�����ʵ����������걨�����ڡ�10%����̲<br>-->
						&nbsp;2���ֿ��ͼ���ڱ��г�������վ���ء�<br></font>
					</td>
					
				</tr>
			</table>
			<br>
			<br>
			<div align="center">

			  <button class="mdlbtn" type="button" onClick="fReceive();">��ȡ��֤��</button>
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

			 <!-- <button class="mdlbtn" type="button" onClick="fBack();">�˻���֤��</button>
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->

			 <!-- <button class="mdlbtn" type="button" onclick="fBreach();">��ԱΥԼ</button>
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->

			  <button class="mdlbtn" type="button" onClick="fEnterWare();">�鿴��ⵥ</button>
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

			  <button class="smlbtn" type="button" onClick="fprint()">��ӡ</button>
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

			  <button class="smlbtn" type="button" onClick="freturn()">����</button>
            </div>
		</fieldset>
		<input type="hidden" name="tag" value="************">
		<input type="hidden" name="pageIndex" value="***********">
		<input type="hidden" name="queryId" value="************">
		<input type="hidden" name="queryDealerId" value="************">
		<input type="hidden" name="queryWareId" value="*************">
		<input type="hidden" name="queryState" value="***********">
		<INPUT TYPE="hidden" NAME="requestId" value="***********">
		<INPUT TYPE="hidden" NAME="requestState" value="****************">
        </form>
</body>
</html>
<script>
	function fEnterWare(){
		openDialog("***********EnterWare/enterWareInfo.jsp?Id=*********","_bank",750,430);
	}
	function fReceive(){
		if(confirm("ȷ������ȡ�˱�֤����")){
			frm.tag.value="receive";
			frm.submit();
		}
	}
	function fBack(){
		openDialog("**************EnterWare/bailInfo.jsp?Id=********","_bank",600,200);
		if(confirm("ȷ����֤���ѷ�����Ա����")){
			frm.tag.value="back";
			frm.submit();
		}
	}
	function fBreach(){
		if(confirm("ȷ��ΥԼ�Ѵ��������")){
			frm.tag.value="breach";
			frm.submit();
		}
	}
	function freturn(){
		frm.tag.value="query";
		frm.action = "***************EnterWare/enterInformList.jsp";
		frm.submit();
	}
	function fprint(){
		frm.action = "*********enterInformPrint.jsp";
		frm.submit();
	}
</script>
<SCRIPT LANGUAGE="JavaScript">
	var errorInfo = "************";
	if(errorInfo!=""){
		alert(errorInfo);
	}
</SCRIPT>