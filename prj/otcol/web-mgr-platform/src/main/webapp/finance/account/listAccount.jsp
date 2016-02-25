<%@ page contentType="text/html;charset=GBK" %>

<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <c:set var="flag" value='${oldParams["flag"]}'/>
    <title>科目列表</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
	    function init(){
			  changeOrder(sign);   
		}
		function createNew(){
			var returnValue = openDialog("<%=basePath%>/account/createAccount.jsp","_blank",500,300);
			if(returnValue)
			    frm_query.submit();
				//window.location.reload();
		}
		function doQuery(){
			frm_query.submit();
		}
		function resetForm(){
			frm_query.accountCode.value = "";
			frm_query.accountName.value = "";
			frm_query.DCFlag.value = "";
			frm_query.accountLevel.value = "";
		}
		function editInfo(vCode){
			var returnValue = openDialog("<%=basePath%>/accountController.spr?funcflg=accountEditForward&code="+vCode ,"_blank",500,300);
			if(returnValue)
			    frm_query.submit();
				//window.location.reload();
		}
		function generateAccounts(){
			if(confirm("确认要生成交易商科目？"))
				frm_gen.submit();
		}
	</script>
  </head>
  
  <body onload="init();">
  	<form id="frm_gen" name="frm_gen" action="<%=basePath%>/accountController.spr?funcflg=generateFirmAccounts" method="post" targetType="hidden" callback="doQuery();">
  	</form>
  	<form id="frm_query" action="<%=basePath%>/accountController.spr?funcflg=accountList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<input type="hidden" id="_flag" name="_flag" value="<c:out value='${flag}'/>">
		<fieldset width="95%">
			<legend>科目查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right"><%=BITCODE%>：&nbsp;</td>
					<td align="left">
						<input id="accountCode" name="_code[like]" value="<c:out value='${oldParams["code[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberInput()" maxlength="16">
					</td>
					<td align="right"><%=BITCODENAME%>：&nbsp;</td>
					<td align="left">
						<input id="accountName" name="_name[like]" value="<c:out value='${oldParams["name[like]"]}'/>" type=text  class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="32">
					</td>
					<td align="right">借贷方向：&nbsp;</td>
					<td align="left">
						<select id="DCFlag" name="_DCFlag[=]" class="normal" style="width: 80px">
							<OPTION value="">全部</OPTION>
							<option value="D">借方</option>
              				<option value="C">贷方</option>
						</select>
					</td>
					<script>
						frm_query.DCFlag.value = "<c:out value='${oldParams["DCFlag[=]"]}'/>"
					</script>
					<td align="right">科目级别：&nbsp;</td>
					<td align="left">
						<select id="accountLevel" name="_accountLevel[=]" class="normal" style="width: 60px">
							<OPTION value="">全部</OPTION>
							<option value="1">1</option>
                		    <option value="2">2</option>
                		    <option value="3">3</option>
						</select>
					</td>
					<script>
						frm_query.accountLevel.value = "<c:out value='${oldParams["accountLevel[=]"]}'/>"
					</script>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<form id="frm_delete" name="frm_delete" action="<%=basePath%>/accountController.spr?funcflg=accountDelete" method="post" targetType="hidden" callback="doQuery();">
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="code"><%=BITCODE%></td>
				<td class="panel_tHead_MB" abbr="name"><%=BITCODENAME%></td>
				<td class="panel_tHead_MB" abbr="DCFlag">借贷方向</td>
				<td class="panel_tHead_MB" abbr="accountLevel">科目级别</td>
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
	  			<td class="underLine">
	  			<c:if test="${result.DCFlag=='D'}">借方</c:if>
			    <c:if test="${result.DCFlag=='C'}">贷方</c:if>
	  			</td>
	  			<td class="underLine"><c:out value="${result.accountLevel}"/></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="5">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="5">
					<%@ include file="../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="5"></td>
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