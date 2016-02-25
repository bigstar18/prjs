<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=publicPath%>jstools/calendar.htc">
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>tradeInfoController.zcjs?funcflg=list" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
	<fieldset width="95%">
			<legend>查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">
							买卖方向&nbsp;
					</td>
					<td align="left">
						<select id="businessdirection" name="_businessdirection[=]" class="normal" style="width: 80px">
						  <option value="" >全部</option>
						  <option value="B" >买</option>
						  <option value="S" >卖</option>
						 </select>
						 <script>
							 frm.businessdirection.value = "<c:out value='${oldParams["businessdirection[=]"]}'/>";
						 </script>
					</td>
					<td align="right">
							状态&nbsp;
					</td>
					<td align="left">
							<select id="status" name="_status[=]" class="normal" style="width: 80px">
							  <option value="" >全部</option>
							  <option value=1 >未成交</option>
							  <option value=2 >已成交</option>
							  <option value=3 >撤单</option>
							  <option value=4 >废除原单</option>
							  <option value=5 >系统撤单</option>
							 </select>
							 <script>
								 frm.status.value = "<c:out value='${oldParams["status[=]"]}'/>";
							  </script>
					</td>
					<td align="right">
							是否卖仓单&nbsp;
					</td>
					<td align="left">
						<select id="isregstock" name="_isregstock[=]" class="normal" style="width: 80px">
						  <option value="" >全部</option>
						  <option value="Y" >是</option>
						  <option value="N" >否</option>
						 </select>
						  <script>
							 frm.isregstock.value = "<c:out value='${oldParams["isregstock[=]"]}'/>";
						  </script>
					</td>
					<td align="right">挂单时间&nbsp;</td>
					<td align="left"><MEBS:calendar eltID="zSDate" eltName="_goodsorderdate[>=][date]" eltValue="<c:out value='${oldParams["goodsorderdate[>=][date]"]}'/>" eltCSS="date" eltStyle="width:88px" eltImgPath="<%=skinPath%>/images/" />
						&nbsp;至&nbsp;
						<MEBS:calendar eltID="zEDate" eltName="_goodsorderdate[<=][date]" eltValue="<c:out value='${oldParams["goodsorderdate[<=][date]"]}'/>" eltCSS="date" eltStyle="width:88px" eltImgPath="<%=skinPath%>/images/" />
					</td>
					

				 <td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>	
				</td>
				</tr>
			</table>
		</fieldset>
	  <%@ include file="goodsOrderTable.jsp"%>
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
	function initBody(returnValue){
		var msg1='${msg}';
	if(msg1!=""){
		alert(msg1);
		}
	
		changeOrder(sign);
		
	}
	function fmod(vid){
		frm.action = "<%=basePath%>/tradeInfoController.zcjs?funcflg=view&id="+vid;
		frm.submit();
	}
	function resetForm(){
		frm.zSDate.value = "";
		frm.zEDate.value = "";
		frm.businessdirection.value = "";
		frm.isregstock.value = "";
		
		frm.submit();
	}
	function add(){
		frm.action="<%=basePath%>/tradeInfoController.zcjs?funcflg=addForward";
		frm.submit();
	}
	 function del(){
		frm.action="<%=basePath%>/tradeInfoController.zcjs?funcflg=delForward";
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