<%@ page contentType="text/html;charset=GBK" %>

<html xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <title>�鿴��־</title>
    <IMPORT namespace="MEBS" implementation="<%=basePath%>/public/jstools/calendar.htc">
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
	    function init(){
			changeOrder(sign);
		}
		function doQuery(){
			if (frm_query.beginDate.value>frm_query.endDate.value) {
				alert("��ʼ���ڲ��ܴ��ڽ������ڣ�");
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
			<legend>��־��ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">����Ա��ţ�&nbsp;</td>
					<td align="left">
						<input name="_UserID[like]" id="userId" value="<c:out value='${oldParams["UserID[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">���ͣ�&nbsp;</td>
					<td align="left">
						<select id=type name="_type[=]" class="normal" style="width: 100px">
							<OPTION value="">ȫ��</OPTION>
			                <option value="info">��Ϣ</option>
			                <option value="alert">����</option>
			                <option value="error">����</option>
			                <option value="sysinfo">ϵͳ��Ϣ</option>
						</select>
					</td>
					<script>
						frm_query.type.value = "<c:out value='${oldParams["type[=]"]}'/>"
					</script>
				</tr>
				<tr>
				    <td align="right">��ʼ���ڣ�&nbsp;</td>
					<td align="left">
						<MEBS:calendar size="10" eltID="beginDate" eltName="_occurTime[>=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["occurTime[>=][date]"]}'/>" />
					</td>
					<td align="right">�������ڣ�&nbsp;</td>
					<td align="left">
						<MEBS:calendar size="10" eltID="endDate" eltName="_occurTime[<=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["occurTime[<=][date]"]}'/>" />
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">��ѯ</button>&nbsp;
						<!-- add by yangpei 2011-11-22 �������ù��� -->
		            	<button type="button" class="smlbtn" onclick="resetForm()">����</button>&nbsp;
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
				<td class="panel_tHead_MB" abbr="occurTime">��������</td>
				<td class="panel_tHead_MB" abbr="type">����</td>
				<td class="panel_tHead_MB" abbr="userID">����ԱID</td>
				<td class="panel_tHead_MB" abbr="description">����</td>
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