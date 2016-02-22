<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>系统用户浏览</title>
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
											csvFileName="导出列表.csv"   style="table-layout:fixed">
											<ec:row recordKey="">
												<ec:column width="3%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="commodity.name[like]" styleClass="font-size:18" title="商品名称" width="12%"
													style="text-align:center;font-weight:bold;font-size:18" value="${quotation.commodityName}" />
												<ec:column property="_1" title="买价" width="11%"
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
												<ec:column property="_2" title="卖价" width="11%"
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
												<ec:column property="curPrice[=][bigdecimal]" title="中间价" width="12%"
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
												<ec:column property="highPrice[=][bigdecimal]" title="最高价" width="13%"
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
												<ec:column property="lowPrice[=][bigdecimal]" title="最低价" width="13%"
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
												<ec:column property="createTime[=][date]" title="行情时间" width="20%"
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
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
	</body>
	<script type="text/javascript">
		/**var int = setInterval("ecReloadF()",1000);*/
		function ecReloadF(){
		if (document.readyState == "complete") // 当页面加载状态为完全结束时进入
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
