<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   
%>
<html>
	<head>
		<title>�������ʽ�ϼ�</title>
		<SCRIPT type="text/javascript">

		
		
			//ִ�в�ѯ�б�
			function dolistquery() {  
		         frm.submit();
			}

			//�鿴��Ϣ����
			function customerFunds_table(brokerID){
				var url = "${basePath}/timebargain/dataquery/customerFunds.action?firmId="+brokerID;
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
							<form name="frm" action="${basePath}/timebargain/dataquery/listBrokerFundsCount.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table4_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															�����̴��룺
															<label>
																<input type="text" class="input_text" id="brokerID"   name="brokerID"" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															���������ƣ�
															<label>
																<input type="text" class="input_text" id="brokerName"   name="brokerName" />
															</label>
														</td>
												<td class="table3_td_anniu" align="center">
													<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>
													&nbsp;&nbsp;
													<button class="btn_cz" onclick="myReset();">����</button>		
												</td>
												
												</tr>
												</table>
												<script type="text/javascript">
											      frm.brokerID.value = "<c:out value='${brokerID}'/>";
											      frm.brokerName.value = "<c:out value='${brokerName}'/>";
											      
											    </script>
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
										<ec:table items="pageInfo.result" var="broker"
											action="${basePath}/timebargain/dataquery/listBrokerFundsCount.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												
												<ec:column property="BROKERID" title="�����̴���" width="170" ellipsis="true" style="text-align:center;"/>	
												<ec:column property="NAME" title="����������" width="80" style="text-align:center;" ellipsis="true"/>
												<ec:column property="NOWLEAVEBALANCE" title="��ǰ�����ʽ�" cell="currency" calcTitle= "�ϼ�" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												
												<ec:column property="RUNTIMEFL" title="��ǰ����" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												<ec:column property="RUNTIMEMARGIN" title="��ǰ��֤��" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												<ec:column property="RUNTIMEASSURE" title="��ǰ������" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
																			
												<ec:column property="LASTBALANCE" title="�����ʽ����" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												<ec:column property="CLEARFL" title="���ո���" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												<ec:column property="CLEARMARGIN" title="���ձ�֤��" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												<ec:column property="CLEARASSURE" title="���յ�����" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												
												<ec:column property="TRADEFEE" title="����������" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>			
												<ec:column property="MAXOVERDRAFT" title="��Ѻ�ʽ�" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>	
												<ec:column property="VIRTUALFUNDS" title="�����ʽ�" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>	
												<ec:column property="CLOSE_PL" title="����ת��ӯ��" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												<%--<ec:column property="settle_PL" title="���ս���ӯ��" cell="currency" calc="total" width="120" style="text-align:right;"/>	--%>
												<%--  <c:set var="out_Amount" value="${BALANCE+CLEARASSURE+CLEARMARGIN+CLEARFL+PL+CLOSE_PL-TRADEFEE}"></c:set>--%>
												<ec:column property="QUANYI" title="��ǰȨ��" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00">
												  <%--  ${broker.BALANCE+broker.CLEARASSURE+broker.CLEARMARGIN+broker.CLEARFL+broker.PL+broker.CLOSE_PL-broker.TRADEFEE}--%>
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