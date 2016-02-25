<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<title>
default
</title>
<script type="text/javascript">
<!--
//modifyStatus
function modifyStatus(marketCode,customerID,rowid)
{
    var satushtml = ec_table.rows(rowid).cells(2).innerHTML;
    //alert(satushtml);
    var status;
    if (satushtml == "<fmt:message key="cM_CustomerMapForm.status.option.yx"/>") 
    {
       status = 0; 
    }
    else if (satushtml == "<fmt:message key="cM_CustomerMapForm.status.option.jzjy"/>") 
    {
       status = 1; 
    }
    else if (satushtml == "<fmt:message key="cM_CustomerMapForm.status.option.wkt"/>") 
    {
       status = 2; 
    }
	//alert(marketCode+";" + customerID+";" +status);
    parent.HiddFrame.location.href = "<c:url value="/timebargain/baseinfo/customerMap.do?funcflg=modifyMapStatus&marketCode="/>" + marketCode + "&customerID=" + customerID + "&status=" + status;
}

// -->
</script>
</head>
<body>
<table width="100%">
  <tr><td style="color:red;">
  	修改状态：双击要修改的状态，选择后单击修改图标
  </td></tr>
  <tr><td style="color:red;">
  	市场：<c:out value="${param['marketName'] }"/>&nbsp;&nbsp;摊位：<c:out value="${param['firmID'] }"/>
  </td></tr>
  <tr><td>
	<ec:table items="usedSuffixMapList" var="usedSuffixMap" 
			  action="${pageContext.request.contextPath}/timebargain/baseinfo/customerMap.do?funcflg=listUsedSuffixMap"	
			xlsFileName="usedSuffixMap.xls" 
			csvFileName="usedSuffixMap.csv"
			showPrint="true" 	
			listWidth="100%"		  
			rowsDisplayed="20"
	>
		<ec:row>		
            <ec:column property="customerID" title="customerForm.CustomerID" width="100"/>
			<ec:column property="suffixName" title="suffixForm.name" width="100"/>
			<ec:column property="status" title="cM_CustomerMapForm.status" editable="true" editTemplate="ecs_t_status" mappingItem="CUSTOMERMAP_STATUS" style="color:red;" width="100"/>			
			<ec:column property="_1" title="&#160;" viewsAllowed="html" resizeColWidth="false" tipTitle="修改状态" sortable="false" width="40" style="text-align:center;">
				<a href="#" onclick="modifyStatus('<c:out value="${usedSuffixMap.marketCode}"/>','<c:out value="${usedSuffixMap.customerID}"/>',${GLOBALROWCOUNT}-1)"><img src="<c:url value="/timebargain/images/046.GIF"/>"></a>
			</ec:column>
		</ec:row>		
	</ec:table>
</td></tr>
</table>	
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="CUSTOMERMAP_STATUS" />
		</select>
	</textarea>		
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
  
</html>
