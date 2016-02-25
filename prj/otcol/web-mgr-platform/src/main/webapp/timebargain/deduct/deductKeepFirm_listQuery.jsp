<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>

<title>
客户保留
</title>
<script type="text/javascript">
<!--
//add
function add_onclick()
{
    top.mainFrame.location.href = "<c:url value="/timebargain/deduct/deduct.do?funcflg=editDeductKeepFirm&crud=create"/>";
}
function next_onclick(){
	//var date = ec_table.rows(id).cells(2).innerHTML;
	//var code = ec_table.rows(id).cells(3).innerHTML;
	//alert(date);
	//alert(code);
	//top.MainFrame.location.href = "<c:url value="/deduct/deduct.do?method=nextDeductDetailQuery&date="/>" + date + "&code=" + code;
	//alert('<%=request.getAttribute("date")%>');
	//alert('<%=request.getAttribute("code")%>');
	top.mainFrame.location.href = "<c:url value="/timebargain/deduct/deduct.do?funcflg=nextDeductDetailQuery&date="/>" + '<%=(String)request.getAttribute("date")%>' + "&code=" + '<%=(String)request.getAttribute("code")%>';
	
}

// -->
</script>
</head>
<body>

    <table width="100%" >
  <tr><td>
	<ec:table items="deductKeepFirmListQuery" var="deduct" 
			action="${pageContext.request.contextPath}/timebargain/deduct/deduct.do?funcflg=deductKeepFirmListQuery"	
			autoIncludeParameters="${empty param.autoInc}"
			
	>
    <ec:row>
            <ec:column property="customerID" title="交易客户ID" width="120" style="text-align:center;"/>
			<ec:column property="bS_Flag" title="买卖标志" editTemplate="ecs_t_status" mappingItem="BS_FLAG" width="115" style="text-align:center;"/>
			<ec:column property="keepQty" title="保留数量" format="quantity"  width="120" style="text-align:center;"/>
		</ec:row>
   
	  <ec:extend>
			
			
		</ec:extend>	
		
	</ec:table>
	
	</td>
		
	</tr>
</table>	
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="CUSTOMER_STATUS" />
		</select>
	</textarea>	
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="bS_Flag" >
			<ec:options items="BS_FLAG" />
		</select>
	</textarea>		

	
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>
