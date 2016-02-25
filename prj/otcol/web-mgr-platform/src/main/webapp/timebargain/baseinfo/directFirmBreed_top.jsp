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
  directFirmBreedForm.submit();
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
					<html:form  action="/timebargain/baseinfo/directFirmBreed.do?funcflg=directFirmBreedGet"
						method="POST" styleClass="form" target="ListFrame" >
						<fieldset class="pickList" >
							<legend class="common">
								<b>查询条件
								</b>
							</legend>
							<table border="0"  cellpadding="1" cellspacing="0"
								class="common">
								<tr>
									<td>&nbsp;</td><td>&nbsp;</td>
									 <td align="right">类型:</td>
					 				<td align="left">
										<select id="type" name="type" style="width:60px" onchange="change(this.value)">
											
											<option value="e" <c:if test="${type=='e'}"><c:out value='selected="selected"'/></c:if>>当前</option>						
											<option value="h" <c:if test="${type=='h'}"> <c:out value='selected="selected"'/></c:if>>历史</option>
											
										</select>
								
									</td>
									<td align="right">
										交易商代码：
									</td>
									<td>
															
										<input type="text" name="_e.firmID[=]" style="width:80" maxlength="16" 
											styleClass="text" />
									</td>
			                        <td align="right">
										商品品种：
									</td>
									<td>
									<select id="_e.breedID[=]" name="_e.breedID[=]" style="width:60px">
									<option value=""></option>
									<c:forEach items="${breedList}" var="breed">

										<option value="${breed.breedid }">${breed.breedname }</option>
									</c:forEach>
									</select>
									</td>	
									 <td align="right">
										品种分类：
									</td>
									<td>
									<select id="_b.sortID[=]" name="_b.sortID[=]" style="width:60px">
									<option value=""></option>
									<c:forEach items="${sorts}" var="sorts">
										<option value="${sorts.sortID }">${sorts.sortName }</option>
									</c:forEach>
									</select>
									</td>
									<td align="right">
										日期：
									</td>
									<td>
										<input type="text" id="beginDate" name="_trunc(e.deletedate)[>=][date]"  ondblclick="if(!this.readOnly){setRq(this);}"  title="双击选择日期" readonly="true" styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:80" onkeypress="return numberPass()" class="ReadOnlyString"/>
									</td>
									<td>至</td>
									<td>
										<input type="text" id="endDate" name="_trunc(e.deletedate)[<=][date]" ondblclick="if(!this.readOnly){setRq(this);}"  title="双击选择日期" readonly="true" styleId="endDate" maxlength="10" style="ime-mode:disabled;width:80" onkeypress="return numberPass()" class="ReadOnlyString"/>
									</td>	
									<td align="right">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>
										
									</td>																	
								</tr>
							</table>
						</fieldset>
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
