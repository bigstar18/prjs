<%@ page contentType="text/html;charset=GBK" %>
<html>
  <head>
    <%@ include file="../public/headInc.jsp" %> 
    <title>录入模板列表</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
	    function init(){
			  changeOrder(sign);  
		}
		function createNew(){
			var returnValue = openDialog("<%=basePath%>/voucherController.spr?funcflg=channelAddForward","_blank",600,350);
			if(returnValue)
			    frm_query.submit();
				//window.location.reload();
		}
		function doQuery(){
			frm_query.submit();
		}
		function resetForm(){
			frm_query.code.value = "";
			frm_query.name.value = "";
		}
		function editInfo(vCode){
			var returnValue = openDialog("<%=basePath%>/voucherController.spr?funcflg=channelModForward&code="+vCode ,"_blank",600,350);
			if(returnValue)
			    frm_query.submit();
		}
	</script>
  </head>
  
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/voucherController.spr?funcflg=channelList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>摘要查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right"><%=CODEID%>：&nbsp;</td>
					<td align="left">
						<input id="code" name="_code[like]" value="<c:out value='${oldParams["code[like]"]}'/>" type=text class="text" style="width: 80px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right"><%=CODENAME%>：&nbsp;</td>
					<td align="left">
						<input id="name" name="_name[like]" value="<c:out value='${oldParams["name[like]"]}'/>" type=text  class="text" style="width: 80px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<form id="frm_delete" action="<%=basePath%>/voucherController.spr?funcflg=channelDelete" method="post" targetType="hidden" callback="doQuery();">
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="code"><%=CODEID%></td>
				<td class="panel_tHead_MB" abbr="name"><%=CODENAME%></td>
				<td class="panel_tHead_MB" abbr="summaryNo"><%=SUMMARYNO%></td>
				<td class="panel_tHead_MB" abbr="debitCode">借方<%=BITCODE%></td>
				<td class="panel_tHead_MB" abbr="creditCode">贷方<%=BITCODE%></td>
				<td class="panel_tHead_MB" abbr="needContractNo">需要合同号</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">
	  				<input name="delCheck" type="checkbox" value="<c:out value="${result.code}"/>">
	  			</td>
	  			<td class="underLine">
	  				<span onclick="editInfo('<c:out value="${result.code}"/>')" style="cursor:hand;color:blue">
	  				<c:out value="${result.code}"/></span></td>
	  			<td class="underLine"><c:out value="${result.name}"/></td>
	  			<td class="underLine"><c:out value="${result.summaryNo}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.debitCode}"/></td>
	  			<td class="underLine"><c:out value="${result.creditCode}"/>&nbsp;</td>
	  			<td class="underLine">
	  			<c:if test="${result.needContractNo=='Y'}">是</c:if>
			    <c:if test="${result.needContractNo=='N'}">否</c:if>
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
					<%@ include file="../public/pagerInc.jsp" %>
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
	</from>
   	<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
              <button class="smlbtn" type="button" onclick="createNew()">添加</button>&nbsp;&nbsp;
  			  <button class="smlbtn" type="button" onclick="deleteRec(frm_delete,tableList,'delCheck')">删除</button>
            </div></td>
        </tr>
    </table>
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>