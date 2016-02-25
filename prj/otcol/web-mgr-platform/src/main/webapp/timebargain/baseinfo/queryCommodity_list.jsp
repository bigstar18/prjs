<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import="java.util.*" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
<title>
default
</title>
<script type="text/javascript">
<!--
//add
	function queryInfo(commodityID,breedID){
		var operNew = "query" + document.getElementById("oper").value;
		parent.location.href = "<c:url value="/timebargain/baseinfo/commodity.do?crud=update&funcflg=edit&commodityID="/>" + commodityID + "&breedID=" + breedID + "&oper=" + operNew;
	}

// -->


</script>
</head>
<body>
		<%
			String oper = (String)request.getAttribute("oper");
			
		%>
	<input type="hidden" id="oper" name="oper" value="<%=oper%>"/>	
<table width="100%" >
  <tr><td>
	<ec:table items="queryCommodityList" var="commodity" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/queryCommodity.do?funcflg=queryCommodity"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="commodity.xls" 
			csvFileName="commodity.csv"
			showPrint="true" 
			listWidth="100%"
			title=""
			rowsDisplayed="20"
			minHeight="300"
			retrieveRowsCallback="limit"   
	>
		<ec:row>
			<ec:column property="name" title="��Ʒ����" width="15%" style="text-align:center;">	
      	<a onclick="queryInfo('<c:out value="${commodity.CommodityID}"/>','<c:out value="${commodity.breedID}"/>')" style="cursor:hand" title="�鿴"><c:out value="${commodity.name}"/></a> 
      </ec:column>	
            <ec:column property="commodityID" title="��Ʒ����" width="15%" style="text-align:center;"/>              								
			<%
				if ("cur".equals(oper)) {
			%>
			
			<ec:column property="status"  title="״̬" editTemplate="ecs_t_status" mappingItem="COMMODITY_STATUS" width="100" style="text-align:center;"/>			
			<%
				}
			%>
			<ec:column property="marginRate_B" title="��֤��ϵ��" width="15%" style="text-align:center;"/> 
			<ec:column property="marginRate_S" title="����֤��ϵ��" width="15%" style="text-align:center;"/> 
			<ec:column property="marketDate" title="��������" cell="date" format="date" width="15%" style="text-align:center;"/>
			<ec:column property="settleDate" title="��������" cell="date" format="date" width="15%" style="text-align:center;"/>				
		</ec:row>
		<ec:extend>
		
		</ec:extend>		
	</ec:table>
</td></tr>

		
</table>	
	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="COMMODITY_STATUS" />
		</select>
	</textarea>		
<%@ include file="/timebargain/common/messages.jsp" %>
</body>

</html>
