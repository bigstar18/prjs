<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>�ͻ���Ʒ�ֲ�����</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/commodity/customerHoldQty/list.action"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmin">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															��Ʒ����:&nbsp;
															<label>
																<input type="text" class="input_text" id="name"
																	name="${GNNT_}commodity.name[like]"
																	value="${oldParams['commodity.name[like]'] }">
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick="select1()">
																��ѯ
															</button>&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset()">
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
							<button class="anniu_btn" onclick="addCustomerHoldQty()" id="add">
								���
							</button>
							&nbsp;&nbsp;
							<button class="anniu_btn" onclick="deleteCustomerHoldQty()" id="delete">
								ɾ��
							</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="customerHoldQty"
											action="${basePath}/commodity/customerHoldQty/list.action"
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv"   style="table-layout:fixed">

											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													value="${customerHoldQty.commodityId }:::${customerHoldQty.customerNo}" style="text-align:center; "
													width="4%" viewsAllowed="html" />
												<ec:column width="6%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="commodity.name[like]" title="��Ʒ����"
													style="text-align:center;">
													<a href="#" class="blank_a"
														onclick="update('${customerHoldQty.commodityId }','${customerHoldQty.customerNo}');"><font
														color="#880000">${customerHoldQty.commodityName}</font>
													</a>
												</ec:column>
												<ec:column property="customer.customerNo[like]" title="�����˺�"
													style="text-align:center;"
													value="${customerHoldQty.customerNo}" />
												<ec:column property="oneMaxOrderQty[bigdecimal]"
													title="��������µ���" style="text-align:center;"
													value="${customerHoldQty.oneMaxOrderQty}" />
												<ec:column property="oneMinOrderQty[bigdecimal]"
													title="������С�µ���" style="text-align:center;"
													value="${customerHoldQty.oneMinOrderQty}" />
												<ec:column property="primary.maxCleanQty[bigdecimal]" title="��󾻳ֲ���"
													style="text-align:center;"
													value="${customerHoldQty.maxCleanQty}" />
												<ec:column property="maxHoldQty[bigdecimal]" title="���ֲ���"
													style="text-align:center;"
													value="${customerHoldQty.maxHoldQty}" />
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
					name="" />
		</textarea>
		</div>
		<script type="text/javascript">
function addCustomerHoldQty(id) {
	var url = "${basePath}/commodity/customerHoldQty/forwardAdd.action";
	ecsideDialog(url,"",580,500);
}
function update(id1,id2) {
	var url = "${basePath}/commodity/customerHoldQty/forwardUpdate.action?obj.commodityId="+id1+"&obj.customerNo="+id2;
			ecsideDialog(url,"",580,400);
}
function deleteCustomerHoldQty(){
	  var url="${basePath}/commodity/customerHoldQty/delete.action";
	  deleteEcside(ec.ids,url);
	}
function select1() {
	frm.submit();
}
</script>
	</body>
</html>