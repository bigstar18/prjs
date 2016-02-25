<%@ page contentType="text/html;charset=GBK" %>
<html>
  <head>
    <%@ include file="headInc.jsp" %>
    <base target="_self">
	<title>创建凭证</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript">
		var returnObj = window.dialogArguments;
		function selectOk()
		{
			 window.returnValue = returnObj;
			 window.close();
		}
		function doQuery(){
			frm_query.submit();
		}
		function init(){ 
			if(frm_query.pageSize.value == ""){
				frm_query.pageSize.value = "10";
				frm_query.submit();
			}
		}
	</script>
	</head>
	<body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/voucherController.spr?funcflg=summaryList" method="post">
  		<input type="hidden" name="targetView" value="public/selectSummary">
		<input type="hidden" name="orderField" value="summaryNo">
		<input type="hidden" name="orderDesc" value="false">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>摘要查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">摘要内容&nbsp;</td>
					<td align="left">
						<input id="summary" name="_summary[like]" value="<c:out value='${oldParams["summary[like]"]}'/>" type=text  class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
					</td>  
				</tr>
			</table>
		</fieldset>
	</form>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">摘要编号</td>
				<td class="panel_tHead_MB">摘要内容</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();" ondblclick="dblClickTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">
	  				<input type="radio" name="selectRadio" onclick="onclickRadio()">
	  				<span mapId="summaryNo"><c:out value="${result.summaryNo}"/></span>
	  			</td>
	  			<td class="underLine"><span mapId="summary"><c:out value="${result.summary}"/></span></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="2">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="2">
					<%@ include file="pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="2"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
   	<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
              <button class="smlbtn" type="button" onclick="selectOk()">确定</button>&nbsp;&nbsp;
  			  <button class="smlbtn" type="button" onclick="window.close()">取消</button>
            </div></td>
        </tr>
    </table>
  </body>
</html>
<%@ include file="footInc.jsp" %>