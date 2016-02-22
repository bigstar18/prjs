<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>ϵͳ�û����</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						&nbsp;
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="quotation"
											action="${basePath}/tradeManage/offerBrand/list.action" title=""
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv"   style="table-layout:fixed">
											<ec:row recordKey="">
												<ec:column width="3%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="commodity.name[like]" styleClass="font-size:18" title="��Ʒ����" width="12%"
													style="text-align:center;font-weight:bold;font-size:18" value="${quotation.commodityName}" />
												<ec:column property="_1" title="���" width="11%"
													style="text-align:center;font-weight:bold;font-size:18;" >
													<c:if test="${quotation.spread>0}">
													<font  style="color: red;">
													</c:if>
													<c:if test="${quotation.spread<0}">
													<font  style="color: green;">
													</c:if>
													<c:if test="${quotation.spread==0}">
													<font  style="color: black;">
													</c:if>
													<c:if test="${quotation.minPriceMove==1}">
														<fmt:formatNumber value="${quotation.curPrice_B}" pattern="#"/> 
													</c:if>
													<c:if test="${quotation.minPriceMove=='0.01'}">
														<fmt:formatNumber value="${quotation.curPrice_B}" pattern="0.00"/>
													</c:if>
													<c:if test="${quotation.minPriceMove != '0.01' && quotation.minPriceMove != 1}">
														<fmt:formatNumber value="${quotation.curPrice_B}" pattern="0.0"/>
													</c:if>
													</font>
												</ec:column>
												<ec:column property="_2" title="����" width="11%"
													style="text-align:center;font-weight:bold;font-size:18">
													<c:if test="${quotation.spread>0}">
													<font  style="color: red;">
													</c:if>
													<c:if test="${quotation.spread<0}">
													<font  style="color: green;">
													</c:if>
													<c:if test="${quotation.spread==0}">
													<font  style="color: black;">
													</c:if>
													<c:if test="${quotation.minPriceMove==1}">
														<fmt:formatNumber value="${quotation.curPrice_S}" pattern="#"/> 
													</c:if>
													<c:if test="${quotation.minPriceMove=='0.01'}">
														<fmt:formatNumber value="${quotation.curPrice_S}" pattern="0.00"/>
													</c:if>
													<c:if test="${quotation.minPriceMove != '0.01' && quotation.minPriceMove != 1}">
														<fmt:formatNumber value="${quotation.curPrice_S}" pattern="0.0"/>
													</c:if>
													</font>
												</ec:column>
												<ec:column property="curPrice[=][bigdecimal]" title="�м��" width="12%"
													style="text-align:center;font-weight:bold;font-size:18">
													<c:if test="${quotation.spread>0}">
													<font  style="color: red;">
													</c:if>
													<c:if test="${quotation.spread<0}">
													<font  style="color: green;">
													</c:if>
													<c:if test="${quotation.spread==0}">
													<font  style="color: black;">
													</c:if>
													<c:if test="${quotation.minPriceMove==1}">
														<fmt:formatNumber value="${quotation.curPrice}" pattern="#"/> 
													</c:if>
													<c:if test="${quotation.minPriceMove=='0.01'}">
														<fmt:formatNumber value="${quotation.curPrice}" pattern="0.00"/>
													</c:if>
													<c:if test="${quotation.minPriceMove != '0.01' && quotation.minPriceMove != 1}">
														<fmt:formatNumber value="${quotation.curPrice}" pattern="0.0"/>
													</c:if>
													</font>
												</ec:column>
												<ec:column property="highPrice[=][bigdecimal]" title="��߼�" width="13%"
													style="text-align:center;font-weight:bold;font-size:18">
													<c:if test="${quotation.minPriceMove==1}">
														<fmt:formatNumber value="${quotation.highPrice}" pattern="#"/> 
													</c:if>
													<c:if test="${quotation.minPriceMove=='0.01'}">
														<fmt:formatNumber value="${quotation.highPrice}" pattern="0.00"/>
													</c:if>
													<c:if test="${quotation.minPriceMove != '0.01' && quotation.minPriceMove != 1}">
														<fmt:formatNumber value="${quotation.highPrice}" pattern="0.0"/>
													</c:if>
												</ec:column>
												<ec:column property="lowPrice[=][bigdecimal]" title="��ͼ�" width="13%"
													style="text-align:center;font-weight:bold;font-size:18">
													<c:if test="${quotation.minPriceMove==1}">
														<fmt:formatNumber value="${quotation.lowPrice}" pattern="#"/> 
													</c:if>
													<c:if test="${quotation.minPriceMove=='0.01'}">
														<fmt:formatNumber value="${quotation.lowPrice}" pattern="0.00"/>
													</c:if>
													<c:if test="${quotation.minPriceMove != '0.01' && quotation.minPriceMove != 1}">
														<fmt:formatNumber value="${quotation.lowPrice}" pattern="0.0"/>
													</c:if>
												</ec:column>
												<ec:column property="createTime[=][date]" title="����ʱ��" width="20%"
													style="text-align:center;font-weight:bold;font-size:18" value="${datefn:formatdate(quotation.createTime) }" />
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
			</div>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
	</body>
	<script type="text/javascript">
		/**var int = setInterval("ecReloadF()",1000);*/
		function ecReloadF(){
		if (document.readyState == "complete") // ��ҳ�����״̬Ϊ��ȫ����ʱ����
		{
			 ECSideUtil.reload('ec');
		}else
		{
		}
			setTimeout("ecReloadF()",1000);
		}
		ecReloadF();
	</script>
</html>
