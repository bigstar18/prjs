<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>
    <title></title>
  </head>
  
  <body>
  	<form name="frm" action="<%=basePath%>breedController.entity?funcflg=getBreedList" method="post">
  		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>Ʒ�ֲ�ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="90%" height="35">
				<tr height="35">
					<td align="right">Ʒ�ֱ�ţ�&nbsp;</td>
					<td align="left">
						<input id="id" name="_breedId[like]" value="<c:out value='${oldParams["breedId[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="32">
					</td>
					<td align="right">Ʒ�����ƣ�&nbsp;</td>
					<td align="left">
						<input id="name" name="_breedName[like]" value="<c:out value='${oldParams["breedName[like]"]}'/>" type=text  class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="32">
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">��ѯ</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">����</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	  
	 <%@ include file="breedTable.jsp"%>

   	<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
              <button class="smlbtn" type="button" onclick="fadd()">���</button>&nbsp;&nbsp;
  			  <button class="smlbtn" type="button" onclick="disposeRec(frm,tableList,'delCheck','ɾ��')">ɾ��</button>
  			  &nbsp;&nbsp;
  			  <button class="lgrbtn" type="button" onclick="synch(frm,tableList,'delCheck','ͬ��')">ͬ��Ʒ������</button>
            </div></td>
        </tr>
    </table>
 </form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function doQuery(){
		frm.submit();
	}
	function fmod(vid){
		frm.action = "<%=basePath%>breedController.entity?funcflg=breedModForward&breedId="+vid;
		frm.submit();
	}
	function fadd(){
		frm.action = "<%=basePath%>breedController.entity?funcflg=breedAddForward";
		frm.submit();
	}
	function resetForm(){
		frm.id.value = "";
		frm.name.value = "";
		frm.submit();
	}

	function disposeRec(frm_delete,tableList,checkName,dispose){
		if(isSelNothing(tableList,checkName) == -1){
			alert ( "û�п���"+dispose+"�����ݣ�" );
			return;
		}
		if(isSelNothing(tableList,checkName)){
			alert ( "��ѡ����Ҫ"+dispose+"�����ݣ�" );
			return;
		}
		if(confirm("��ȷʵҪ"+dispose+"ѡ��������")){
			frm.action = "<%=basePath%>breedController.entity?funcflg=delete";
			frm_delete.submit();
		}
	}
	
	// ͬ��Ʒ������ 
	function synch(frm_delete,tableList,checkName,dispose){
	    if(isSelNothing(tableList,checkName) == -1){
			alert ( "û�п���"+dispose+"�����ݣ�" );
			return;
		}
		if(isSelNothing(tableList,checkName)){
			alert ( "��ѡ����Ҫ"+dispose+"�����ݣ�" );
			return;
		}
		else{
			frm.action = "<%=basePath%>breedController.entity?funcflg=synch";
			frm_delete.submit();
		}
	}
	
	//���������ֺ���ĸ
function onlyNumberAndCharInput()
{
  if ((event.keyCode>=48 && event.keyCode<=57) || 

(event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && 

event.keyCode<=122))
  {
    event.returnValue=true;
  }
  else
  {
    event.returnValue=false;
  }
}
//-->
</SCRIPT>
