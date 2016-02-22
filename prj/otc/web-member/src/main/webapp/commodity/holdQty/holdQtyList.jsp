<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/commodity/holdQty/list.action?sortName=primary.commodityId"
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
															<input type="text" class="input_text"
																name="${GNNT_}commodity.name[like]"
																id="commodityName"
																value="${oldParams['commodity.name[like]'] }" />
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="submitMember()">��ѯ</button>&nbsp;&nbsp;
													    <button  class="btn_cz" onclick="myReset()">����</button>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
							</form>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="holdQty"
											action="${basePath}/commodity/holdQty/list.action?sortName=primary.commodityId"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv" style="table-layout:fixed">
										<ec:row recordKey="${holdQty.firmId}">
												<ec:column width="4%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="commodity.name[like]" title="��Ʒ����" width="10%" style="text-align:left;" ellipsis="true"> <a href="#" class="blank_a" onclick="return update('${holdQty.firmId}','${holdQty.commodityId}');"><font color="#880000">${holdQty.commodityName }</font> </a> </ec:column>
												<ec:column property="primary.firmId[like]" title="����" width="10%"
													style="text-align:left;" editTemplate="ecs_t_firmDis">
											  		${firmDisMap[holdQty.firmId]}
											  	</ec:column>
												<ec:column property="primary.oneMaxOrderQty[=][int]" title="��������µ���"
													width="16%" style="text-align:right;"
													value="${holdQty.oneMaxOrderQty}" />
												<ec:column property="primary.oneMinOrderQty[=][int]" title="������С�µ���"
													width="16%" style="text-align:right;"
													value="${holdQty.oneMinOrderQty}" />
												<ec:column property="primary.maxCleanQty[=][int]" title="��󾻳ֲ���"
													width="16%" style="text-align:right;"
													 sortable="false" filterable="false">
													 <c:if test="${holdQty.firmId!='Def_Customer'}">
													 ${holdQty.maxCleanQty}
													 </c:if>
												</ec:column>
												<ec:column property="primary.maxHoldQty[=][int]" title="���ֲ���"
													width="16%" style="text-align:right;"
													value="${holdQty.maxHoldQty}" />
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
		<!-- �༭�͹�����ʹ�õ�Ĭ�Ͻ�����ģ�� -->
		<textarea id="ecs_t_firmDis" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.firmId[like]">
			<ec:options items="firmDisMap" />
		</select>
	    </textarea>

		<SCRIPT type="text/javascript">
		function update(firmId,commodityId){
			var url="${basePath}/commodity/holdQty/forwardUpdate.action?obj.commodityId="+commodityId+"&obj.firmId="+firmId;
			
			ecsideDialog(url,"",580,500);
		}
		function submitMember(){
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>