<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<c:import url="/timebargain/common/date.jsp"/>
		<script language="JavaScript" src="/mgr/timebargain/scripts/open.js"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function query_onclick()
{
  breedForm.submit();
}
function change(value){
	if(value=="h"){
	directFirmBreedForm.beginDate.readOnly=false;
	 directFirmBreedForm.beginDate.style.backgroundColor = "white";
	directFirmBreedForm.endDate.readOnly=false;
	directFirmBreedForm.endDate.style.backgroundColor = "white";
	}
	else{
	directFirmBreedForm.beginDate.readOnly=true;
	directFirmBreedForm.beginDate.value="";
	 directFirmBreedForm.beginDate.style.backgroundColor = "#C0C0C0";
	directFirmBreedForm.endDate.readOnly=true;
	directFirmBreedForm.endDate.value="";
	directFirmBreedForm.endDate.style.backgroundColor = "#C0C0C0";
	}
}
</script>
	</head>

	<body leftmargin="14" topmargin="0" >
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="left">
			<tr>
				<td>
					<html:form  action="/timebargain/baseinfo/breed.do?funcflg=breedGet" method="POST" styleClass="form" target="ListFrame" >
						<fieldset class="pickList" >
							<legend class="common">
								<b>查询条件
								</b>
							</legend>
							<table border="0"  cellpadding="1" cellspacing="0"
								class="common">							
			                        <td align="right">
										品种分类：
									</td>
									<td>
									<select id="sortid" name="sortid" style="width:100px">
									<option value="">全部品种</option>
									<c:forEach items="${sorts}" var="sort">
										<option value="${sort.sortid}">${sort.sortname}</option>
									</c:forEach>
									</select>
									</td>	
									<td align="right">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>
										
									</td>																	
							</table>
						</fieldset>
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
