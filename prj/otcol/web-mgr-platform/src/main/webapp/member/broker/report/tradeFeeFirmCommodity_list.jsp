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
							<ec:column property="moduleId" title="ģ��" width="120" 
								style="text-align:center;" >
								<c:if test="${result.moduleId==2}">
									����
								</c:if>
								<c:if test="${result.moduleId==3}">
									����
								</c:if>
								<c:if test="${result.moduleId==4}">
									����
								</c:if>
							</ec:column>
							<ec:column property="breedname" title="Ʒ������" width="120" 
								style="text-align:center;" />

							<ec:column property="breedid" title="Ʒ�ִ���" width="120" 
								style="text-align:center;" >
								<c:if test="${result.breedid == -1}">��</c:if>
								</ec:column>
							
							<ec:column property="SUMTRADEFEE" title="������" cell="currency" calcTitle="�ϼ�" 
								 width="120" style="text-align:right;"  calc="total" format="#,##0.00"/>
							<ec:column property="SUMSELFGAIN" title="������������"
							     width="150"
								style="text-align:right;" calc="total" format="#,##0.00"/>
							<ec:column property="SUMMARHETGAIN" title="������������"
								cell="currency"  width="150"
								style="text-align:right;" calc="total" format="#,##0.00"/>
							<ec:column property="sumBrokereachdivide" title="�����������ѷֳ�"
								cell="currency"  width="150"
								style="text-align:right;" calc="total" format="#,##0.00"/>
							<ec:column property="SUMFINALMARHETGAIN" title="������ʵ��������" 
								cell="currency"  width="150"
								style="text-align:right;" calc="total" format="#,##0.00"/>
							<ec:column property="sumReward" title="������ʵ��������" calc="total"
								cell="currency"  width="150"
								style="text-align:right;" format="#,##0.00"/>
						</ec:row>
					</ec:table>
				</td>
			</tr>
		</table>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<span style="color: red;"></span>
</body>
</html>
