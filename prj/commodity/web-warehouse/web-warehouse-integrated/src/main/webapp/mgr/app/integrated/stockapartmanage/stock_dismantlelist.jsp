<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>�Ѳ�ֵ��б�</title>
	</head>
	<body onload="getFocus('id');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/stock/dismantlelist/stockDismantleList.action?sortColumns=order+by+stockId&isQueryDB=true"
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
																<input id="id" type="text" class="input_text"   style="width: 90px;"
																maxLength="<fmt:message key='ownerFirm_q' bundle='${PropsFieldLength}'/>"
																	name="${GNNT_}primary.stock.ownerFirm[allLike]"
																	value="${oldParams['primary.stock.ownerFirm[allLike]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="right">
															�𵥱�ţ�
															<label>
																<input type="text" class="input_text" style="width: 100px;"
																maxLength="<fmt:message key='dismantleId_q' bundle='${PropsFieldLength}'/>"
																	name="${GNNT_}primary.dismantleId[=][Long]"
																	value="${oldParams['primary.dismantleId[=][Long]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="right">
															�ֵ��ţ�
															<label>
																<input type="text" class="input_text" style="width: 100px;"
																maxLength="<fmt:message key='stockId_q' bundle='${PropsFieldLength}'/>" 
																	name="${GNNT_}primary.stock.stockId[=][String]"
																	value="${oldParams['primary.stock.stockId[=][String]']}" />
															</label>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1" align="right">
															�ֿ�ԭʼƾ֤�ţ�
															<label>
																<input type="text" class="input_text"  style="width:90px;"
																maxLength="30"
																	name="${GNNT_}primary.realStockCode[allLike]"
																	value="${oldParams['primary.realStockCode[allLike]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="right">
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
						<div class="div_gn">
							
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
									<ec:table items="pageInfo.result" var="dismantle"
											action="${basePath}/stock/dismantlelist/stockDismantleList.action?sortColumns=order+by+dismantleId&isQueryDB=true"
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="dismantle.xls" csvFileName="dismantle.csv" 
											showPrint="true" listWidth="100%" minHeight="345">
											<ec:row>
												<ec:column width="5%" property="_0" title="���" style="text-align:center;" 
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="stock.stockId" width="8%" title="Stock.stockId"
													style="text-align:left; ">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/stock/list/stockDetails.action" id="detail" text="<font color='#880000'>${dismantle.stock.stockId}</font>" onclick="details(${dismantle.stock.stockId})"/>
												</ec:column>
												<ec:column property="dismantleId" width="8%" title="��ֵ����"
													style="text-align:left; ">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/stock/dismantlelist/dismantleDetails.action" id="dismantleDetails" text="<font color='#880000'>${dismantle.dismantleId}</font>" onclick="dismantleDetails(${dismantle.dismantleId})"/>
												</ec:column>
												<ec:column property="newStockId" width="8%" title="�²ֵ���"
													style="text-align:left; ">
												</ec:column>
												<ec:column property="stock.ownerFirm" width="8%" title="����������"
													style="text-align:left; " ellipsis="true">
												</ec:column>
												<ec:column property="realStockCode" width="10%" title="�ֿ�ԭʼƾ֤��"
													style="text-align:left; " ellipsis="true">
												</ec:column>
												<ec:column property="amount" width="10%" title="��Ʒ����" style="text-align:right " ellipsis="true"><fmt:formatNumber value="${dismantle.amount}" pattern="#,##0.00"/>(${dismantle.stock.unit})
												</ec:column>
												<ec:column property="applyTime" width="13%" title="����ʱ��"
													style="text-align:center; ">
													<fmt:formatDate value="${dismantle.applyTime}"
														pattern="yyyy-MM-dd HH:mm:ss"  />
												</ec:column>
												<ec:column property="processTime" width="13%" title="����ʱ��"
													style="text-align:left; ">
													<fmt:formatDate value="${dismantle.processTime}"
														pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="status" width="8%" title="Stock.stockStatus"
													style="text-align:center; ">
													<c:if test="${dismantle.status=='1' }">�𵥳ɹ�</c:if>
													<c:if test="${dismantle.status=='2' }">��ʧ��</c:if>
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


			<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
			<textarea id="ecs_t_input" rows="" cols="" style="display: none">
		<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="" /></textarea>

<SCRIPT type="text/javascript">
	function select1() {
		frm.submit();
	}
	//�ֵ�����
	function details(stockid){
		var a=document.getElementById("detail").action;
		var url="${basePath}"+a+"?isQueryDB=true&entity.stockId="+stockid;
		var result =showDialogRes(url, '', 700, 430);
	}
	function dismantleDetails(dismantleid){
		var a=document.getElementById("dismantleDetails").action;
		var url="${basePath}"+a+"?isQueryDB=true&entity.dismantleId="+dismantleid;
		var result =showDialogRes(url, '', 700, 430);
	}
</SCRIPT>
	</body>
</html>