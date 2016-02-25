<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../../public/headInc.jsp" %>

<style>
checkbox_1 {
	border-width: 0px;
	border-left-style: none;
	border-right-style: none;
	border-top-style: none;
	border-bottom-style: none;
	font-family: SimSun;
	color: #333333;
	background-color: transparent;
}
</style>

<html xmlns:MEBS>
	<head>
	<import namespace="MEBS" implementation="<%=basePath%>/public/calendar.htc">
		<title>手续费汇总查询</title>
		<script type="text/javascript">
			//重置按钮重新填写
			function resetForm(){
				var firmidhidden=document.getElementById("firmidhidden").value;
				var brokeridhidden=document.getElementById("brokeridhidden").value;
				if(firmidhidden != null){
					document.getElementById("firmid").value=firmidhidden;
				}else{
					document.getElementById("firmid").value="";
				}
				if(brokeridhidden != null){
					document.getElementById("brokerid").value=brokeridhidden;
				}else{
					document.getElementById("brokerid").value="";
				}
			}
			//提交表单进行查询
			function submitForm(){
				var param=/^[0-9]{4}-([0][1-9]|[1][0-2])-([0][1-9]|[1|2][0-9]|30|31)$/;
				if(frm.scleardate.value != null && frm.scleardate.value!= ""){
					if(!param.exec(frm.scleardate.value)){
						alert("填写的起始日期格式有误");
						return;
					}
				}
				if(frm.ecleardate.value != null && frm.ecleardate.value!= ""){
					if(!param.exec(frm.ecleardate.value)){
						alert("填写的终止日期格式有误");
						return;
					}
				}
				frm.submit();
			}
		</script>
	</head>
	<body>
		<form name="frm" action="<%=feeDetailControllerPath %>brokerUserFeeList" method="post">
			<fieldset width="100%">
				<legend>
					手续费统计表
				</legend>
				<span>
					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr height="35" width="100%">
							<td align="right">
								<%FIRMID%>：
							</td>
							<td align="left">
								<input name="firmid" id="firmid" size=10 type=text value="<c:out value='${oldParams.firmid}'/>">
								<input name="firmidhidden" id="firmidhidden" type="hidden" value="<c:out value='${oldParams.firmid}'/>">
							</td>
							<td align="right">
								<%=BROKERID%> ：
							</td>
							<td align="left">
								<input name="brokerid" id="brokerid" size=10 type=text value="<c:out value='${oldParams.brokerid}'/>">
								<input name="brokeridhidden" id="brokeridhidden" type="hidden" value="<c:out value='${oldParams.brokerid}'/>">
							</td>
							<td align="right">
								交收日期：
							</td>
							<td align="left" colspan="2">
							<MEBS:calendar eltID="scleardate" eltCSS="date" eltName="scleardate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" eltValue="<c:out value='${oldParams.scleardate}'/>"/>
							&nbsp;至&nbsp;
							<MEBS:calendar eltID="ecleardate" eltCSS="date" eltName="ecleardate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" eltValue="<c:out value='${oldParams.ecleardate}'/>"/>
							</td>
							<td align="left">
								<input type="button" class="btn" onclick="submitForm();" value="查询">&nbsp;
								<input type="button" class="btn" onclick="resetForm();"value="重置">&nbsp;
							</td>
						</tr>
					</table> </span>
			</fieldset>
			<br>
			<table id="tb" border="0" cellspacing="0" cellpadding="0"
				width="100%" height="300">
				<thead>
					<tr height="25" align=center>
						<td class="panel_tHead_LB">
							&nbsp;
						</td>
						<td class="panel_tHead_MB" align=left>
							<%=FIRMID%>
						</td>
						<td class="panel_tHead_MB" align=left>
							<%=FIRMNAME%>
						</td>
						<td class="panel_tHead_MB" align=right>
							订立量
						</td>
						<td class="panel_tHead_MB" align=right>
							转让量
						</td>
						<td class="panel_tHead_MB" align=right>
							交收量
						</td>
						<td class="panel_tHead_MB" align=right>
							交易手续费(元)
						</td>
						<td class="panel_tHead_MB" align=right>
							交收手续费(元)
						</td>
						<td class="panel_tHead_MB" align=right>
							手续费合计(元)&nbsp;
						</td>
						<td class="panel_tHead_MB" align=left>
							&nbsp;<%=BROKERID%>
						</td>
						<td class="panel_tHead_RB">
							&nbsp;
						</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${resultList}" var="rs">
						<tr align=center height="25" onclick="selectTr();">
							<td class="panel_tBody_LB">&nbsp;</td>
					  		<td class="underLine" align=left>
					  			<c:out value="${rs.firmId}"/>
					  		</td>
							<td class="underLine" align=left>
								<c:out value="${rs.firmName}"/>
							</td>
							<td class="underLine" align=right>
								<c:out value="${rs.openQty}"/>
							</td>
							<td class="underLine" align="right">
								<c:out value="${rs.closeQty}"/>
							</td>
							<td class="underLine" align="right">
								<c:out value="${rs.settleQty}"/>
							</td>
							<td class="underLine" align=right>
								<c:out value="${rs.tradeFee}"/>
							</td>
							<td class="underLine" align=right>
								<c:out value="${rs.settleFee}"/>
							</td>
							<td class="underLine" align=right>
								<c:out value="${rs.tradeFee+rs.settleFee}"/>
							</td>
							<td class="underLine" align=right>
								<c:out value="${rs.brokerId}"/>
							</td>
							<td class="panel_tBody_RB">&nbsp;</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr height="100%">
						<td class="panel_tBody_LB">
							&nbsp;
						</td>
						<td colspan="9">
							&nbsp;
						</td>
						<td class="panel_tBody_RB">
							&nbsp;
						</td>
					</tr>
					<tr height="22">
						<td class="panel_tBody_LB">
							&nbsp;
						</td>
						<td class="pager" colspan="9">
							<%@ include file="../../public/pagerInc.jsp"%>
						</td>
						<td class="panel_tBody_RB">
							&nbsp;
						</td>
					</tr>
					<tr height="22">
						<td class="panel_tFoot_LB">
							&nbsp;
						</td>
						<td class="panel_tFoot_MB" colspan="9"></td>
						<td class="panel_tFoot_RB">
							&nbsp;
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</body>
</html>