<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=publicPath%>jstools/calendar.htc">
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
	<fieldset width="95%">
			<legend>广播消息查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">发送日期&nbsp;</td>
					<td align="left"><MEBS:calendar eltID="zSDate" eltName="_broadcastsendtime[>=][date]" eltValue="<c:out value='${oldParams["broadcastsendtime[>=][date]"]}'/>" eltCSS="date" eltStyle="width:88px" eltImgPath="<%=skinPath%>/images/" />
						&nbsp;至&nbsp;
						<MEBS:calendar eltID="zEDate" eltName="_broadcastsendtime[<=][date]" eltValue="<c:out value='${oldParams["broadcastsendtime[<=][date]"]}'/>" eltCSS="date" eltStyle="width:88px" eltImgPath="<%=skinPath%>/images/" />
					</td>
					

				 <td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>	
				</td>
				</tr>
			</table>
		</fieldset>
	  <%@ include file="broadcastTable.jsp"%>
	  <table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
			  <button class="lgrbtn" type="button" onclick="add();">添加</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    <button class="lgrbtn" type="button" onclick="del();">删除</button>
			</div></td>
        </tr>
    </table>
	
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function fmod(vid){
		frm.action = "<%=basePath%>/broadcastController.zcjs?funcflg=view&id="+vid;
		frm.submit();
	}
	function doQuery(){
		frm.action="<%=basePath%>broadcastController.zcjs?funcflg=list";
		frm.submit();
	}
	function resetForm(){
		frm.zSDate.value = "";
		frm.zEDate.value = "";
		frm.submit();
	}
	function add(){
		frm.action="<%=basePath%>/broadcastController.zcjs?funcflg=addForward";
		frm.submit();
	}
	 function del(){
		frm.action="<%=basePath%>/broadcastController.zcjs?funcflg=delForward";
		deleteRec(frm,tableList,'delCheck');
	}
	
	function deleteRec(frm_delete,tableList,checkName)
	{
		if(isSelNothing(tableList,checkName) == -1)
		{
		alert ( "没有可以操作的数据！" );
		return false;
		}
		if(isSelNothing(tableList,checkName))
		{
		alert ( "请选择需要操作的数据！" );
		return false;
		}
		if(confirm("您确实要操作选中数据吗？"))
		{
		frm.submit();
		//return true;
		}
		else
		{
		return false;
		}
	}
	
	

</SCRIPT>