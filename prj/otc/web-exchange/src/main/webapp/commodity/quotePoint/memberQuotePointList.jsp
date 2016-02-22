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
								action="${basePath}/commodity/memberQuotePoint/list.action?sortName=primary.commodityId"
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
															<button class="btn_sec" onclick="submitQuote()">
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
							<button class="anniu_btn" onclick="addMemberQuotePoint()"
								id="update">
								���
							</button>
							<button class="anniu_btn" onclick="deleteMemberQuotePoint()" id="delete">
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
											var="quotePoint"
											action="${basePath}/commodity/memberQuotePoint/list.action?sortName=primary.commodityId"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv" style="table-layout:fixed">
											<ec:row recordKey="${quotePoint.m_firmId}">
												<ec:column width="4%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="commodity.name[Like]" title="��Ʒ����" width="24%" style="text-align:left;" ellipsis="true"><a href="#" class="blank_a" onclick="return update('${quotePoint.m_firmId}','${quotePoint.commodityId}');"><font color="#880000">${quotePoint.commodityName }</font></a></ec:column>
												<ec:column property="memberInfo.name[like]" title="��Ա����"
													width="24%" style="text-align:left;" ellipsis="true"
													value="${quotePoint.firmName}" />
												<ec:column property="primary.quotePointAlgr[=][int]"
													title="���۵���㷨" width="24%" style="text-align:left;"
													editTemplate="ecs_t_quote">
													<c:set var="quotePointAlgr">
														<c:out value="${quotePoint.quotePointAlgr}"></c:out>
													</c:set>
										  		${delayFeeAlgrMap[quotePointAlgr]}
										  		</ec:column>
												<ec:column property="primary.quotePointB[=][bigdecimal]"
													title="�򱨼۵��" width="25%" style="text-align:right;"
													value="${quotePoint.quotePointB_log}" filterable="false"/>
												<%--<ec:column property="primary.quotePointS[like]"
													title="�����۵��" width="25%" style="text-align:right;"
													value="${quotePoint.quotePointS_log}" filterable="false"/>--%>
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
		<textarea id="ecs_t_quotePointBMap" rows="" cols=""
			style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.quotePointB[=][bigdecimal]">
			<ec:options items="quotePointBMap" />
		</select>
	    </textarea>
		<!-- �༭�͹�����ʹ�õ����ڷ��㷨ģ�� -->
		<textarea id="ecs_t_quote" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.quotePointAlgr[=][int]">
			<ec:options items="delayFeeAlgrMap" />
		</select>
	    </textarea>
		<SCRIPT type="text/javascript">
		function update(firmId,commodityId){
			var url="${basePath}/commodity/memberQuotePoint/forwardUpdate.action?obj.commodityId="+commodityId+"&obj.m_firmId="+firmId;
			ecsideDialog(url,"",580,440);
		}
		function submitQuote(){
			frm.submit();
		}
		
		function addMemberQuotePoint(){
			var url="${basePath}/commodity/memberQuotePoint/forwardAdd.action";
			ecsideDialog(url,"",580,373);
		}
		function deleteMemberQuotePoint(){
	  		var url="${basePath}/commodity/memberQuotePoint/delete.action";
	 		deleteEcside(ec.ids,url);
		}
		</SCRIPT>
	</body>
</html>