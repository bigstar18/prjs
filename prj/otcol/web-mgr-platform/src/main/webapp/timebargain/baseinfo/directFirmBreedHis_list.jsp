<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>

<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<title>
default
</title>
<script type="text/javascript">
<!--
//add
function add_onclick()
{	
	pTop("<c:url value="/timebargain/baseinfo/directFirmBreed_U.jsp"/>",500,500);
}
// -->
</script>
</head>
<body>
<table width="100%">
  <tr><td>
	<ec:table items="directFirmBreedHisList" var="directFirmBreedHis" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/directFirmBreed.do?funcflg=directFirmBreedGet&type=h"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="directFirmBreedHis.xls" 
			csvFileName="directFirmBreedHis.csv"
			showPrint="true" 
			listWidth="100%"
			title=""			  
			rowsDisplayed="20"
			minHeight="300"
	>
		<ec:row>
            <ec:column property="firmId" title="交易商代码" width="25%" style="text-align:center;">                                                                                       
            </ec:column>
            
            <ec:column property="breedId" title="商品品种" width="25%" style="text-align:center;">
            ${directFirmBreedHis.breedname }
            </ec:column>	
            <ec:column property="deleteDate" title="删除时间" width="25%" style="text-align:center;" cell="date" format="yyyy-MM-dd"/>					
		</ec:row>
		<ec:extend>

		</ec:extend>		
	</ec:table>
		
</td></tr>
</table>		
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>		

<%@ include file="/timebargain/common/messages.jsp" %>

</body>

</html>
