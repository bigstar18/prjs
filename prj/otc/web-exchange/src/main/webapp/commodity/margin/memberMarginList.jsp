<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
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
								action="${basePath}/commodity/memberMargin/list.action?sortName=primary.commodityId"
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
																	name="${GNNT_}commodity.name[like]" id="commodityName"
																	value="${oldParams['commodity.name[like]'] }" />
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick="submitMargin()">
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
							<button class="anniu_btn" onclick="addMemberMargin()"
								id="update">
								���
							</button>
							<button class="anniu_btn" onclick="deleteMemberMargin()"
								id="delete">
								ɾ��
							</button>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="margin"
											action="${basePath}/commodity/memberMargin/list.action?sortName=primary.commodityId"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv" style="table-layout:fixed">
											<ec:row recordKey="${margin.firmId}">
												<ec:column width="4%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="commodity.name[Like]" title="��Ʒ����" 	width="14%" style="text-align:left;" ellipsis="true"> <a href="#" class="blank_a" onclick="return update('${margin.firmId}','${margin.commodityId}');"><font color="#880000">${margin.commodityName }</font> </a> </ec:column>
												<ec:column property="memberinfo.name[like]" title="��Ա����"
													width="14%" style="text-align:left;" ellipsis="true"
													value="${margin.firmName }" />
												<ec:column property="primary.marginAlgr[=][int]" title="��֤���㷨"
													width="14%" style="text-align:left;"
													editTemplate="ecs_t_marginAlgr">
													<c:set var="marginAlgrKey">
														<c:out value="${margin.marginAlgr}"></c:out>
													</c:set>
											  		${commodityMarginAlgrMap[marginAlgrKey]}
									            </ec:column>
												<ec:column property="primary.tradeMargin[=][bigdecimal]" title="����ռ��/����"
													width="14%" style="text-align:right;" filterable="false"
													value="${margin.tradeMargin_log}" />
											 <%-- 	<ec:column property="primary.settleMargin[like]"
													title="����ά��" width="14%" style="text-align:right;"
													value="${margin.settleMargin_log}" filterable="false"/>
												<ec:column property="primary.holidayMargin[like]" title="����ά��"
													width="15%" style="text-align:right;"
													value="${margin.holidayMargin_log}" filterable="false"/> --%> 
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
		<!-- �༭�͹�����ʹ�õı�֤���㷨ģ�� -->
		<textarea id="ecs_t_marginAlgr" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.marginAlgr[=][int]">
			<ec:options items="commodityMarginAlgrMap" />
		</select>
	    </textarea>
		<!-- �༭�͹�����ʹ�õı�֤��ȡ��ģ�� -->
		<textarea id="ecs_t_marginPrice" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.marginPrice[=][int]">
			<ec:options items="commodityMarginPriceMap" />
		</select>
	    </textarea>
		<SCRIPT type="text/javascript">
		function update(firmId,commodityId){
			var url="${basePath}/commodity/memberMargin/forwardUpdate.action?obj.commodityId="+commodityId+"&obj.firmId="+firmId;
			
			ecsideDialog(url,window,580,430);
		}
		function submitMargin(){
			frm.submit();
		}
		function addMemberMargin(){
			var url="${basePath}/commodity/memberMargin/forwardAdd.action";
			ecsideDialog(url,window,580,335);
		}
		function deleteMemberMargin(){
			var url="${basePath}/commodity/memberMargin/delete.action";
	 		deleteEcside(ec.ids,url);
		}
		</SCRIPT>
	</body>
</html>