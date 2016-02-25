<%@ include file="/timebargain/common/taglibs.jsp"%>

<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<%@ include file="/timebargain/common/ecside_head.jsp"%>
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<title>default</title>

	</head>
	<body leftmargin="2" topmargin="0">
		<table width="100%">
			<tr>
				<td>
					<ec:table items="resultList" var="result"
						action="${pageContext.request.contextPath}/member/brokerReportController.mem?funcflg=tradeFeeFirmCommodityList"
						xlsFileName="tradeFeeFirmList.xls"
						csvFileName="tradeFeeFirmList.csv" showPrint="true"
						rowsDisplayed="20" listWidth="100%" 
						minHeight="300"
						filterable="false">
						<ec:row>
							<ec:column property="moduleId" title="模块" width="120" 
								style="text-align:center;" >
								<c:if test="${result.moduleId==2}">
									订单
								</c:if>
								<c:if test="${result.moduleId==3}">
									挂牌
								</c:if>
								<c:if test="${result.moduleId==4}">
									竞价
								</c:if>
							</ec:column>
							<ec:column property="breedname" title="品种名称" width="120" 
								style="text-align:center;" />

							<ec:column property="breedid" title="品种代码" width="120" 
								style="text-align:center;" >
								<c:if test="${result.breedid == -1}">无</c:if>
								</ec:column>
							
							<ec:column property="SUMTRADEFEE" title="手续费" cell="currency" calcTitle="合计" 
								 width="120" style="text-align:right;"  calc="total" format="#,##0.00"/>
							<ec:column property="SUMSELFGAIN" title="加盟商手续费"
							     width="150"
								style="text-align:right;" calc="total" format="#,##0.00"/>
							<ec:column property="SUMMARHETGAIN" title="交易所手续费"
								cell="currency"  width="150"
								style="text-align:right;" calc="total" format="#,##0.00"/>
							<ec:column property="sumBrokereachdivide" title="加盟商手续费分成"
								cell="currency"  width="150"
								style="text-align:right;" calc="total" format="#,##0.00"/>
							<ec:column property="SUMFINALMARHETGAIN" title="交易所实得手续费" 
								cell="currency"  width="150"
								style="text-align:right;" calc="total" format="#,##0.00"/>
							<ec:column property="sumReward" title="加盟商实得手续费" calc="total"
								cell="currency"  width="150"
								style="text-align:right;" format="#,##0.00"/>
						</ec:row>
					</ec:table>
				</td>
			</tr>
		</table>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<span style="color: red;"></span>
</body>
</html>
