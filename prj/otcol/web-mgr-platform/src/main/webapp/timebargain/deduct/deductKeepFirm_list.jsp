<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html:html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
<title>
default
</title>
<script type="text/javascript">
<!--
//add
function add_onclick() {
    parent.location.href = "<c:url value="/timebargain/deduct/deduct.do?funcflg=editDeductKeepFirm&crud=create"/>";
}
function next_onclick() {
	parent.location.href = "<c:url value="/timebargain/deduct/deduct.do?funcflg=nextDeductDetailQuery&date="/>" + '<%=(String)request.getAttribute("date")%>' + "&code=" + '<%=(String)request.getAttribute("code")%>' + "&id=" + '<%=(String)request.getAttribute("id")%>';
}
function goback_onclick() {
	parent.location.href = "<c:url value="/timebargain/deduct/deduct.do?funcflg=editDeductGuide&deductID="/>" + '<%=(String)request.getAttribute("id")%>' +"&d="+Date();
}
// -->
</script>
</head>
<body>
	<table width="100%" >
  		<tr>
  			<td>
				<ec:table items="deductKeepFirmList" var="deduct" 
						action="${pageContext.request.contextPath}/timebargain/deduct/deduct.do?funcflg=deductKeepFirmList"	
						autoIncludeParameters="${empty param.autoInc}"
						minHeight="200">
    				<ec:row>
						<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${deduct.deductID},${deduct.customerID},${deduct.bS_Flag},${deduct.deductDate},${deduct.commodityID}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:80;border:0px;"/>						
            			<ec:column property="customerID" title="交易客户代码" width="120" style="text-align:center;"></ec:column>
						<ec:column property="bS_Flag" title="买卖标志" editTemplate="ecs_t_status" mappingItem="BS_FLAG" width="115" style="text-align:center;"/>
						<ec:column property="keepQty" title="保留数量" format="quantity"  width="120" style="text-align:center;"/>
					</ec:row>
				</ec:table>
			</td>
		</tr>
		<tr>
			<td align="right">
				<html:button property="next" styleClass="button" onclick="next_onclick()">下一步</html:button>
				<html:button property="next" styleClass="button" onclick="goback_onclick()">返回上一步</html:button>
			</td>
		</tr>
	</table>	
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="bS_Flag" >
			<ec:options items="BS_FLAG" />
		</select>
	</textarea>	
	<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html:html>
