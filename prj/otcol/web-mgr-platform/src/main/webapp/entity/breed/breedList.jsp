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
			<legend>品种查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="90%" height="35">
				<tr height="35">
					<td align="right">品种编号：&nbsp;</td>
					<td align="left">
						<input id="id" name="_breedId[like]" value="<c:out value='${oldParams["breedId[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="32">
					</td>
					<td align="right">品种名称：&nbsp;</td>
					<td align="left">
						<input id="name" name="_breedName[like]" value="<c:out value='${oldParams["breedName[like]"]}'/>" type=text  class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="32">
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	  
	 <%@ include file="breedTable.jsp"%>

   	<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
              <button class="smlbtn" type="button" onclick="fadd()">添加</button>&nbsp;&nbsp;
  			  <button class="smlbtn" type="button" onclick="disposeRec(frm,tableList,'delCheck','删除')">删除</button>
  			  &nbsp;&nbsp;
  			  <button class="lgrbtn" type="button" onclick="synch(frm,tableList,'delCheck','同步')">同步品种名称</button>
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
			alert ( "没有可以"+dispose+"的数据！" );
			return;
		}
		if(isSelNothing(tableList,checkName)){
			alert ( "请选择需要"+dispose+"的数据！" );
			return;
		}
		if(confirm("您确实要"+dispose+"选中数据吗？")){
			frm.action = "<%=basePath%>breedController.entity?funcflg=delete";
			frm_delete.submit();
		}
	}
	
	// 同步品种名称 
	function synch(frm_delete,tableList,checkName,dispose){
	    if(isSelNothing(tableList,checkName) == -1){
			alert ( "没有可以"+dispose+"的数据！" );
			return;
		}
		if(isSelNothing(tableList,checkName)){
			alert ( "请选择需要"+dispose+"的数据！" );
			return;
		}
		else{
			frm.action = "<%=basePath%>breedController.entity?funcflg=synch";
			frm_delete.submit();
		}
	}
	
	//仅输入数字和字母
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
