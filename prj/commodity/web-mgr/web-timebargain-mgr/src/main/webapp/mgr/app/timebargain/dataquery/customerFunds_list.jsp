<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   
%>
<html>
	<head>
		<title>�ʽ����</title>
		<SCRIPT type="text/javascript">

		
		
			//ִ�в�ѯ�б�
			function dolistquery() {

		     
		         frm.submit();
			}

			//�鿴��Ϣ����
			function customerFunds_table(firmId){
				var url = "${basePath}/timebargain/dataquery/customerFunds.action?firmId="+firmId;
				showDialog(url, "", 900, 650);
			}
		</SCRIPT>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/timebargain/dataquery/listCustomerFunds.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table4_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															�����̴��룺
															<label>
																<input type="text" class="input_text" id="firmId"   name="${GNNT_}primary.firmId[=]" value="${oldParams['primary.firmId[=]']}" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															���������ƣ�
															<label>
																<input type="text" class="input_text" id="firmName"   name="${GNNT_}primary.firmName[=]" title="������ģʽƥ�����ѯ" value="${oldParams['primary.firmName[=]']}" />
															</label>
														</td>
														
														
												<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>
															
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
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="hold"
											action="${basePath}/timebargain/dataquery/listCustomerFunds.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												
												<ec:column property="firmId" width="170" title="�����̴���" ellipsis="true" style="text-align:center;">
												     <rightHyperlink:rightHyperlink text="<font color='#880000'>${hold.firmId}</font>" className="blank_a" onclick="return customerFunds_table('${hold.firmId}');" action="/timebargain/dataquery/customerFunds.action" />
												</ec:column>
											    <ec:column property="firmName" title="����������" width="120" style="text-align:right;"  ellipsis="true"/>
												<ec:column property="nowLeaveBalance" title="��ǰ�����ʽ�" calc="total" calcTitle= "�ϼ�" width="160" style="text-align:right;" format="#,##0.00">
												       <fmt:formatNumber value="${hold.nowLeaveBalance}" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="runTimeFl" width="120" title="��ǰ����" calc="total" style="text-align:right;" format="#,##0.00">
												        <fmt:formatNumber value="${hold.runTimeFl }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="runTimeMargin" width="120" title="��ǰ��֤��" calc="total" style="text-align:right;" format="#,##0.00">
												        <fmt:formatNumber value="${hold.runTimeMargin }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="runTimeAssure" width="120" title="��ǰ������" calc="total" style="text-align:right;" format="#,##0.00">
												        <fmt:formatNumber value="${hold.runTimeAssure }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="lastBalance" width="160" title="�����ʽ����" calc="total" style="text-align:right;" format="#,##0.00">
												        <fmt:formatNumber value="${hold.lastBalance }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="clearFl" width="120" title="���ո���" calc="total" style="text-align:right;" format="#,##0.00">
												        <fmt:formatNumber value="${hold.clearFl }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="clearMargin" width="120" title="���ձ�֤��" calc="total" style="text-align:right;" format="#,##0.00">
												        <fmt:formatNumber value="${hold.clearMargin }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="clearAssure" width="120" title="���յ�����" calc="total" style="text-align:right;" format="#,##0.00">
												        <fmt:formatNumber value="${hold.clearAssure }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="tradeFee" width="120" title="����������" calc="total" style="text-align:right;" format="#,##0.00">
												        <fmt:formatNumber value="${hold.tradeFee }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="maxOverdraft" width="120" title="��Ѻ�ʽ�" calc="total" style="text-align:right;" format="#,##0.00">
												        <fmt:formatNumber value="${hold.maxOverdraft }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="close" title="����ת��ӯ��" calc="total"  width="120" style="text-align:right;" format="#,##0.00">
												       <fmt:formatNumber value="${hold.close }" pattern="#,##0.00" />
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
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>