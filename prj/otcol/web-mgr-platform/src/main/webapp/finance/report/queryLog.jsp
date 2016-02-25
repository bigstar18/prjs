<%@ page contentType="text/html;charset=GBK" %>

<html xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <title>查看日志</title>
    <IMPORT namespace="MEBS" implementation="<%=basePath%>/public/jstools/calendar.htc">
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
	    function init(){
			changeOrder(sign);
		}
		function doQuery(){
			if (frm_query.beginDate.value>frm_query.endDate.value) {
				alert("开始日期不能大于结束日期！");
				return;
			}
			frm_query.submit();
		}
		
		
	</script>
  </head>
  
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/reportController.spr?funcflg=queryLog" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>日志查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">管理员编号：&nbsp;</td>
					<td align="left">
						<input name="_UserID[like]" id="userId" value="<c:out value='${oldParams["UserID[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">类型：&nbsp;</td>
					<td align="left">
						<select id=type name="_type[=]" class="normal" style="width: 100px">
							<OPTION value="">全部</OPTION>
			                <option value="info">信息</option>
			                <option value="alert">警告</option>
			                <option value="error">错误</option>
			                <option value="sysinfo">系统信息</option>
						</select>
					</td>
					<script>
						frm_query.type.value = "<c:out value='${oldParams["type[=]"]}'/>"
					</script>
				</tr>
				<tr>
				    <td align="right">开始日期：&nbsp;</td>
					<td align="left">
						<MEBS:calendar size="10" eltID="beginDate" eltName="_occurTime[>=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["occurTime[>=][date]"]}'/>" />
					</td>
					<td align="right">结束日期：&nbsp;</td>
					<td align="left">
						<MEBS:calendar size="10" eltID="endDate" eltName="_occurTime[<=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["occurTime[<=][date]"]}'/>" />
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<!-- add by yangpei 2011-11-22 增加重置功能 -->
		            	<button type="button" class="smlbtn" onclick="resetForm()">重置</button>&nbsp;
		            	<script>
		            		function resetForm(){
		            			frm_query.userId.value="";
		            			frm_query.type.value="";
		            			frm_query.beginDate.value="";
		            			frm_query.endDate.value="";
		            		}
		            	</script>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" abbr="occurTime">发生日期</td>
				<td class="panel_tHead_MB" abbr="type">类型</td>
				<td class="panel_tHead_MB" abbr="userID">管理员ID</td>
				<td class="panel_tHead_MB" abbr="description">描述</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"><fmt:formatDate value="${result.occurTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	  			<td class="underLine"><c:out value="${result.type}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.userID}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.description}"/>&nbsp;</td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="4">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="4">
					<%@ include file="../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="4"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>