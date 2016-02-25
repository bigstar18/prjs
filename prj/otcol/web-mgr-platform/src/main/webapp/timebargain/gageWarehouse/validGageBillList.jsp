<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>

<html xmlns:MEBS>
<head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
    <LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
	<IMPORT namespace="MEBS" implementation="<c:url value="/timebargain/scripts/calendar.htc"/>">
</head>
<body>
   <table width="100%" class="common">
   <tr>
   	<td>
   		<form name="queryForm" action="${pageContext.request.contextPath}/timebargain/menu/gageWarehouse.do?funcflg=validGageBillList" method="post">
   		<fieldset class="" >
							<legend class="common">
								<b>��ѯ����
								</b>
							</legend>
   		<table width="100%" height="100%" align="center" class="">
			<tr>
				<td  width="60" style="white-space:nowrap;">��ƷƷ��:</td><td width="100" >
				<select name="BreedID">
				<option value="" >ȫ��</option>
				<c:forEach var="breed" items="${breedList}">
					<option value = "${breed.BREEDID }">${breed.BREEDNAME }</option>					
				</c:forEach>
				</select>
				<script type="text/javascript">
					queryForm.BreedID.value = "<c:out value='${BreedID}'/>";
				</script>
				</td>
				<td align="left" width="90">�����̴��룺</td><td align="left" width="150"><input type="text" name="FirmID" value="${FirmID }"
onkeypress="onlyNumberAndCharInput()" maxlength="16"></td>
				<td align="left" >
					<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return doQuery();">
											��ѯ
										</html:button>	

					<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return resetData();">
											����
										</html:button>			
				</td>
			</tr>
		</table>
		</fieldset>
		</form>
   	</td>
   </tr>
  <tr><td>
  		<ec:table items="validGageBillList" var="app" 
			action="${pageContext.request.contextPath}/timebargain/menu/gageWarehouse.do?funcflg=validGageBillList"	
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
  			<ec:column property="FIRMID" title="�����̴���" width="25%" style="text-align:center;"/> 
  			<ec:column property="BREEDNAME" title="��ƷƷ��" width="25%" style="text-align:center;"/>	
  			<ec:column property="QUANTITY" title="����" width="25%" style="text-align:center;"/> 
  			<ec:column property="FROZENQTY" title="��������" width="25%" style="text-align:center;"/> 
  		</ec:row>
  		</ec:table>
  </td></tr>
</table>
<script type="text/javascript">
function doQuery()
{
	queryForm.submit();
}

//��������
function resetData()
{
  document.forms[0].reset();
  document.getElementsByName("FirmID")[0].value="";

}
</script>
</body>
</html>