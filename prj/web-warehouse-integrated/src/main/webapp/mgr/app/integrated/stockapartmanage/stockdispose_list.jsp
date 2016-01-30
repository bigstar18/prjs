<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>�ֵ������б�</title>
	</head>
	<body onload="getFocus('id');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/stock/apart/list.action?sortColumns=order+by+id"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style" >
									<tr>
										<td class="table2_td_width" style="width: 100%">
											<div class="div2_top" style="margin-left: 0px">
												<table class="table3_style" border="0" cellspacing="0" 
													cellpadding="0" width="100%">
													<tr >
														<td class="table3_td_1" align="right">
															���������̣�
															<label>
																<input id="id" type="text" class="input_text" style="width: 100px;"
																maxLength="<fmt:message key='ownerFirm_q' bundle='${PropsFieldLength}'/>" 
																	name="${GNNT_}primary.stock.ownerFirm[allLike]"
																	value="${oldParams['primary.stock.ownerFirm[allLike]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="right">
															�ֵ��ţ�
															<label>
																<input type="text" class="input_text"  style="width: 100px;"
																maxLength="<fmt:message key='stockId_q' bundle='${PropsFieldLength}'/>"
																	name="${GNNT_}primary.stock.stockId[=][String]"
																	value="${oldParams['primary.stock.stockId[=][String]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="right">
															�ֿ�ԭʼƾ֤�ţ�
															<label>
																<input type="text" class="input_text"  style="width:90px;"
																maxLength="30"
																	name="${GNNT_}primary.stock.realStockCode[allLike]"
																	value="${oldParams['primary.stock.realStockCode[allLike]'] }" />
															</label>
														</td>
														<td class="table3_td_anniu" align="right">
															<button class="btn_sec" onclick=select1();>
																��ѯ
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=myReset();>
																	����
															</button>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
						<div>&nbsp;</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
									<ec:table items="pageInfo.result" var="stockOperation"
											action="${basePath}/stock/apart/list.action?sortColumns=order+by+id"
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="stockdispose_list.xls" csvFileName="stockdispose_list.csv" 
											showPrint="true"
											listWidth="100%" minHeight="345" style="table-layout:fixed;">
											<ec:row>
												<ec:column width="4%" property="_0" title="���" style="text-align:center;"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="stock.stockId" width="8%" title="�ֵ���"
													style="text-align:left; ">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/stock/apart/stockDetails.action" id="detail" text="<font color='#880000'>${stockOperation.stock.stockId}</font>" onclick="details(${stockOperation.stock.stockId})"/>
												</ec:column>
												<ec:column property="stock.realStockCode" width="8%" title="�ֿ�ԭʼƾ֤��"
													style="text-align:left; " ellipsis="true">
												</ec:column>
												<ec:column property="stock.breed.breedName" width="8%" title="Ʒ��"
													style="text-align:left; " ellipsis="true">
												</ec:column>
												<ec:column property="stock.quantity" width="12%" title="��Ʒ����"
													style="text-align:right; " ellipsis="true"><fmt:formatNumber value="${stockOperation.stock.quantity}"
														pattern="#,##0.00"/>(${stockOperation.stock.unit})</ec:column>
												<%--<ec:column property="stock.unit" width="8%" title="Stock.unit"
													style="text-align:center; " ellipsis="true">
												</ec:column>--%>
												<ec:column property="stock.ownerFirm" width="10%" title="����������"
													style="text-align:left; "  ellipsis="true">
												</ec:column>
												<%--<ec:column property="stock.stockStatus" width="8%" title="�ֵ�״̬"
													style="text-align:center; ">
													${stockStatusMap[stockOperation.stock.stockStatus] }
												</ec:column>--%>
												<ec:column property="stock.lastTime" width="14%" title="�����ʱ��"
													style="text-align:center; ">
													<fmt:formatDate value="${stockOperation.stock.lastTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="stock.createTime" width="14%" title="����ʱ��"
													style="text-align:center; ">
													<fmt:formatDate value="${stockOperation.stock.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="operate" title="���ݴ���" width="8%" 
													style="text-align:center; " sortable="false"
													filterable="false">
													<c:if test="${stockOperation.stock.stockStatus==4}">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/stock/apart/updateStockDispose.action" id="updateStockDispose" text="<font color='#880000'>��ֵ�����</font>" onclick="return updateStockDispose('${stockOperation.stock.stockId}');"/>
													</a>
													</c:if>
												</ec:column>
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
					name="" /></textarea>

<SCRIPT type="text/javascript">
	//��ֵ���Ϣ����
	function updateStockDispose(stockid){
		var a=document.getElementById("updateStockDispose").action;
		var url="<%=basePath%>"+a+"?stockId="+stockid;
		ecsideDialog(url,"",900,300);
	}
	function select1() {
		frm.submit();
	}
	
	function details(stockid){
		var a=document.getElementById("detail").action;
		var url="${basePath}"+a+"?stockId="+stockid;
		var result =  showDialogRes(url, '', 700, 400);
	}
</SCRIPT>
	</body>
</html>