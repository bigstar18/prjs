<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />

<title>
套餐详情
</title>
<script type="text/javascript">
<!--

//queryCommodity
function queryCommodity(breedID)
{	
    parent.location.href = "<c:url value="/timebargain/baseinfo/commodity.jsp?condition=and a.status <> 1&breedID="/>" + breedID;
}
//cmdtySort_onclick()
function cmdtySort_onclick()
{	
    //window.open("<c:url value="/baseinfo/cmdtySort.jsp"/>",620,600)
    pTop("<c:url value="/timebargain/baseinfo/cmdtySort.jsp"/>",450,400);
}
function cancel_onclick(){
   document.location.href = "<c:url value="/timebargain/baseinfo/tariff.do?funcflg=getTariffList"/>";
}
// -->
</script>
</head>
<body>
		
<fieldset class="pickList" >
							<legend class="common">
								<b>基本信息
								</b>
								</legend>
								<table border="0" align="left" cellpadding="0" cellspacing="0"
								class="common">
<tr><td width="50"/><td width="200">套餐代码：${tariffID}</td><td width="200">套餐名称：${tariffName}</td>
</tr>	
							
							</table>	
							
							
						</fieldset>
								



<table width="100%">
  <tr><td>
	<ec:table items="tariffList" var="tariff" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/tariff.do?funcflg=getCommodityTariffList&tariffid=${tariff.tariffID}"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="tariff.xls" 
			csvFileName="tariff.csv"
			showPrint="true" 
			listWidth="100%"
			title=""			  
			rowsDisplayed="20"
			minHeight="300"
	>
		<ec:row>
							            	          
            <ec:column property="commodityid" title="商品代码" width="100" style="text-align:center;"/>
            <ec:column property="name" title="商品名称" width="110" style="text-align:center;"/>
            <ec:column property="oldFeeRate_B" title="交易所买订立费率" width="130"  style="text-align:center;"/> 
            <ec:column property="newFeeRate_B" title="套餐买订立费率" width="130"  style="text-align:center;"/>  
            <ec:column property="oldFeeRate_S" title="交易所卖订立费率" width="130"  style="text-align:center;"/> 
            <ec:column property="newFeeRate_S" title="套餐卖订立费率" width="130"  style="text-align:center;"/>  
            <ec:column property="oldTodayCloseFeeRate_B" title="交易所买转让今订货费率" width="160"  style="text-align:center;"/> 
            <ec:column property="newTodayCloseFeeRate_B" title="套餐买转让今订货费率" width="160"  style="text-align:center;"/>  
            <ec:column property="oldTodayCloseFeeRate_S" title="交易所卖转让今订货费率" width="160"  style="text-align:center;"/> 
            <ec:column property="newTodayCloseFeeRate_S" title="套餐卖转让今订货费率" width="160"  style="text-align:center;"/>  
            <ec:column property="oldHistoryCloseFeeRate_B" title="交易所买转让历史订货费率" width="170"  style="text-align:center;"/> 
            <ec:column property="newHistoryCloseFeeRate_B" title="套餐买转让历史订货费率" width="160"  style="text-align:center;"/>   
            <ec:column property="oldHistoryCloseFeeRate_S" title="交易所卖转让历史订货费率" width="170"  style="text-align:center;"/> 
            <ec:column property="newHistoryCloseFeeRate_S" title="套餐卖转让历史订货费率" width="160"  style="text-align:center;"/>   
            <ec:column property="oldForceCloseFeeRate_B" title="交易所买强制转让费率" width="160"  style="text-align:center;"/> 
            <ec:column property="newForceCloseFeeRate_B" title="套餐买强制转让费率" width="160"  style="text-align:center;"/>   
            <ec:column property="oldForceCloseFeeRate_S" title="交易所卖强制转让费率" width="160"  style="text-align:center;"/> 
            <ec:column property="newForceCloseFeeRate_S" title="套餐卖强制转让费率" width="160"  style="text-align:center;"/>                        
            <ec:column property="MODIFYTIME" title="修改时间" width="160" cell="date" format="datetime" style="text-align:center;"/>
            <ec:column property="MODIFYUSer" title="修改人" width="100" style="text-align:center;"/>
                        
		</ec:row>
		<c:if test="${ocrr=='true'}">
			<ec:extend>
<table width="50%">			
<td><html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											返回
										</html:button></td>
			</table>
		</ec:extend>
		</c:if>	 		
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
