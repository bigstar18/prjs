<%@ page contentType="text/html;charset=GBK" %>
<html>
  <head>
   <%@ include file="../../public/headInc.jsp"%>
    <title>交易商列表</title>

	<script language="JavaScript">
	
	function addBrokerUser(brokerid)
	{
	
	//PopWindow("brokerUserAdd.jsp?brokerid="+brokerid,400,250);
	var result = window.showModalDialog("<%=brokerControllerPath%>brokerUserAddForward&brokerid="+brokerid,this, "dialogWidth=420px; dialogHeight=280px; status=yes;scroll=yes;help=no;");
	}
	    function init(){
			  changeOrder(sign);  
		}

		function doQuery(){
			frm_query.submit();
		}
		function resetForm(){
			frm_query.firmId.value = "";
			frm_query.name.value = "";
			frm_query.type.value = "";
			frm_query.contactMan.value = "";
			frm_query.phone.value = "";
		}
	
		function setStatus(frm_delete,tableList,checkName)
		{
		   if(isSelNothing(tableList,checkName) == -1)
			{
				alert ( "没有可以操作的数据！" );
				return;
			}
			if(isSelNothing(tableList,checkName))
			{
				alert ( "请选择需要操作的数据！" );
				return;
			}
			if(frm_delete.status.value=="")
			{
			    alert("未设置状态");
			    return;
			}
			if(confirm("您确实要处理选中数据吗？"))
			{
				frm_delete.submit();
			}
		}
		function deleteFirm(frm_delete,tableList,checkName)
		{
		   if(isSelNothing(tableList,checkName) == -1)
			{
				alert ( "没有可以操作的数据！" );
				return;
			}
			if(isSelNothing(tableList,checkName))
			{
				alert ( "请选择需要操作的数据！" );
				return;
			}

		}
		
		function deletefrmChk(frm_delete,tableList)
		{
			
			if(isSelNothing(tableList,"ck") == -1)
			{
				alert ( "没有可以操作的数据！" );
				return false;
			}
			if(isSelNothing(tableList,"ck"))
			{
				alert ( "请选择需要操作的数据！" );
				return false;
			}
			if(confirm("您确实要操作选中数据吗？"))
			{	
				frm_query.action="<%=brokerControllerPath%>brokerFirmDelete";
				//alert(frm_query.action);
				frm_query.submit();
				//return true;
			}
			else
			{
				return false;
			}
		}
	
	</script>
  </head>
  <body>
  	<form name="frm_query" id="frm_query" action="<%=brokerControllerPath%>brokerlistFirm" method="post">
  		<input type="hidden" id="brokerid" name="brokerid" value="${brokerid}">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>交易商查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="90%" height="35">
				<tr height="35">
					<td align="right"><%=FIRMID%>&nbsp;</td>
					<td align="left">
						<input id="firmId" name="_firmId[like]" value="<c:out value='${oldParams["firmId[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right"><%=FIRMNAME%>&nbsp;</td>
					<td align="left">
						<input id="name" name="_name[like]" value="<c:out value='${oldParams["name[like]"]}'/>" type=text  class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">类型&nbsp;</td>
					<td align="left">
						<select id="type" name="_type[=]" class="normal" style="width: 60px">
							<OPTION value="">全部</OPTION>
							<option value="1">法人</option>
        		            <option value="2">代理</option>
           		            <option value="3">个人</option>
						</select>
					</td>
					<script>
						frm_query.type.value = "<c:out value='${oldParams["type[=]"]}'/>"
					</script>
					<td align="left">
						<button type="button" class="smlbtn" onclick="javascript:document.frm_query.submit();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
				<tr height="35">
					<td align="right">联系人&nbsp;</td>
					<td align="left">
						<input id="contactMan" name="_contactMan[like]" value="<c:out value='${oldParams["contactMan[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">联系电话&nbsp;</td>
					<td align="left">
						<input id="phone" name="_phone[like]" value="<c:out value='${oldParams["phone[like]"]}'/>" type=text  class="text" style="width: 100px" onkeypress="numberPass()" maxlength="16">
					</td>
					<td align="right">&nbsp;</td>
					<td align="left">&nbsp;</td>
					<td align="left">&nbsp;</td>
				</tr>
			</table>
		</fieldset>
	
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="ck" name="ck" onclick="selectAll(tableList,'ck')"></td>
				<td class="panel_tHead_MB" abbr="firmId"><%=FIRMID%></td>
				<td class="panel_tHead_MB" abbr="name"><%=FIRMNAME%></td>
				<td class="panel_tHead_MB" abbr="fullname"><%=FULLNAME%></td>
				<td class="panel_tHead_MB" abbr="contactMan">联系人</td>
				<td class="panel_tHead_MB" abbr="phone">联系电话</td>
				<td class="panel_tHead_MB" abbr="type">类型</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"><input name="ck" type="checkbox" value="<c:out value="${result.firmId}"/>"></td>
	  			<td class="underLine">				
	  				<c:out value="${result.firmId}"/></td>
	  			<td class="underLine"><c:out value="${result.name}"/></td>
	  			<td class="underLine"><c:out value="${result.fullname}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.contactMan}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.phone}"/>&nbsp;</td>
	  		    <td class="underLine">
	  			  <c:if test="${result.type==1}">法人</c:if>
			      <c:if test="${result.type==2}">代理</c:if>
			      <c:if test="${result.type==3}">个人</c:if>
	  			</td>
	  			
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="7">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="7">
					<%@ include file="../../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="7"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
			    <input type="button" onclick="javascript:addBrokerUser('${brokerid}');" class="btn" value="添加">&nbsp;&nbsp;
  			    <input type="button" onclick="return deletefrmChk(frm_query,tableList);" class="btn" value="删除">&nbsp;&nbsp;
  			    <input name="back" type="button" onclick="gotoUrl('<%=brokerControllerPath%>brokerList');" class="btn" value="返回">
          </div>
          <input type="hidden" name="opt">
          </td>
          </tr>
     </table>	 
	</from>

</html>

<%@ include file="../../public/footInc.jsp" %>