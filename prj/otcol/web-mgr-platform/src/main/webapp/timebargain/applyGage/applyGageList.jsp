<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>

<html xmlns:MEBS>
<head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
    <LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
	<IMPORT namespace="MEBS" implementation="<c:url value="/timebargain/scripts/calendar.htc"/>">
</head>
<body>
   <table width="100%">
   <tr>
   	<td>
   		<form name="queryForm" action="${pageContext.request.contextPath}/timebargain/menu/applyGage.do?funcflg=listApplyGage" method="post">
   		<table width="100%" height="100%" align="center">
			<tr align="center">
				<td align="right">���뵥�ţ�</td><td><input type="text" name="applyId" value="${applyId }"></td>
				<td align="right">��Ʒ���룺</td><td><input type="text" name="commodityID" value="${commodityID }"></td>
				<td align="right">�����̴��룺</td><td><input type="text" name="firmID" value="${firmID }"></td>
				<td align="right">�������ࣺ</td>
				<td>
					<select name="applyType">
						<option value="">ȫ��</option>
						<option value="0">����</option>
						<option value="1">���ֵ�</option>
						<option value="2">�ֶ�ת��</option>
					</select>
					<script type="text/javascript">
						queryForm.applyType.value = "<c:out value='${applyType }'/>";
					</script>
				</td>
			</tr>
			<tr align="center">
				<td align="right">�����ˣ�</td><td><input type="text" name="creator" value="${creator }"></td>
				<td align="right">����ʱ�䣺</td>
				<td>
					<MEBS:calendar eltID="createTime" eltName="createTime" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="${createTime }" />
				</td>
				<td align="right">״̬��</td>
				<td align="right">
					<select name="status">
						<option value="">ȫ��</option>
						<option value="1">�����</option>
						<option value="2">���ͨ��</option>
						<option value="3">���ʧ��</option>
					</select>
					<script type="text/javascript">
						queryForm.status.value = "<c:out value='${status }'/>";
					</script>
				</td>
				<td colspan="2" align="center">
					<input type="button" value="��ѯ" onclick="doQuery()">&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="�������" onclick="addApplyGage()">
				</td>
			</tr>
		</table>
		</form>
   	</td>
   </tr>
  <tr><td>
  		<ec:table items="applyGageList" var="app" 
			action="${pageContext.request.contextPath}/timebargain/menu/applyGage.do?funcflg=listApplyGage"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="commodity.xls" 
			csvFileName="commodity.csv"
			showPrint="true" 
			listWidth="100%"
			title=""	
			rowsDisplayed="20"
			minHeight="300"
  		>
  		<ec:row>
  			<ec:column property="APPLYID" title="���뵥��" width="130" style="text-align:center;">
  				<a href="#" onclick="showApplyMessage('<c:out value="${app.APPLYID }"/>')"> <c:out value="${app.APPLYID }"/> </a>
  			</ec:column>	
  			<ec:column property="COMMODITYID" title="��Ʒ����" width="100" style="text-align:center;"/> 
  			<ec:column property="FIRMID" title="�����̴���" width="100" style="text-align:center;"/> 
  			<ec:column property="CUSTOMERID" title="���׶�������" width="130" style="text-align:center;"/>	
  			<ec:column property="QUANTITY" title="��������" width="100" style="text-align:center;"/> 
  			<ec:column property="APPLYTYPE" title="��������" width="100" mappingItem="NUM2STR_TYPE" style="text-align:center;"/> 
  			<ec:column property="STATUS" title="��ǰ״̬" width="130" mappingItem="NUM2STR_STATUS" style="text-align:center;"/>	
  			<ec:column property="CREATETIME" title="����ʱ��" width="100" cell="date" format="date" style="text-align:center;"/> 
  			<ec:column property="CREATOR" title="������" width="100" style="text-align:center;"/> 
  			<ec:column property="REMARK1" title="�����˱�ע" width="130" style="text-align:center;"/>	
  			<ec:column property="MODIFYTIME" title="�޸�ʱ��" width="100" cell="date" format="date" style="text-align:center;"/> 
  			<ec:column property="MODIFIER" title="����޸���" width="100" style="text-align:center;"/> 
  			<ec:column property="REMARK2" title="�޸��˱�ע" width="100" style="text-align:center;"/> 
  		</ec:row>
  		<ec:extend >
  			
  		</ec:extend>
  		</ec:table>
  </td></tr>
</table>
<script type="text/javascript">
function showApplyMessage(v)
{
	window.location.href = "/timebargain/menu/applyGage.do?funcflg=getApplyGage&applyId="+v+"&d="+Date();
}

function addApplyGage()
{
	queryForm.action="/timebargain/menu/applyGage.do?funcflg=addApplyGage";
	queryForm.submit();
}

function doQuery()
{
	queryForm.submit();
}
</script>
</body>
</html>