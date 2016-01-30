<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>�������</title>
		<script src="${publicPath }/js/tool.js" type="text/javascript"></script>
		<script type="text/javascript">
			function select1(){<%//ִ�в�ѯ����%>
				checkQueryDate(frm.beginTime.value,frm.endTime.value);
			}
			function auditOutMoney(btn){<%//ִ�г������%>
				var url = "${basePath}"+btn.action+"?autoInc=false";
				updateRMIEcside(ec.ids,url);
			}
		</script>
	</head>
	<body>
		<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div class="div_cx">
						<form name="frm" id="frm" action="${basePath}/bank/capital/outMoneySecondAuditList.action?sortColumns=order+by+id+desc" method="post">
							<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
								<tr>
									<td class="table5_td_width">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														�����̴���:&nbsp;
														<label>
															<input id="firmID" name="${GNNT_}primary.firm.firmID[allLike]" value="${oldParams['primary.firm.firmID[allLike]'] }" class="input_text"/>
														</label>
													</td>
													<td class="table3_td_1" align="left">
														���д���:&nbsp;
														<label>
															<input id="bankID" name="${GNNT_}primary.bank.bankID[=]" value="${oldParams['primary.bank.bankID[=]'] }" class="input_text"/>
														</label>
													</td>
													<td>
													&nbsp;
													</td>
													<td>
													&nbsp;
													</td>
												</tr>
												<tr>
													<td class="table3_td_1" align="left">
														��ʼ����:&nbsp;
														<label>
															<input id="beginTime" name="${GNNT_}primary.createtime[>=][date]" value="${oldParams['primary.createtime[>=][date]']}" class="wdate validate[custom[date],funcCall[checkBeginDate]] datepicker" onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
														</label>
													</td>
													<td class="table3_td_1" align="left">
														��������:&nbsp;
														<label>
															<input id="endTime" name="${GNNT_}primary.createtime[<=][datetime]" value="${oldParams['primary.createtime[<=][datetime]']}" class="wdate validate[custom[date],funcCall[checkEndDate]] datepicker" onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
														</label>
													</td>
													<td class="table3_td_anniu" align="right">
														<button class="btn_sec" id="view" onclick="select1()">��ѯ</button>&nbsp;&nbsp;
														<button class="btn_cz" onclick=myReset();>����</button>
													</td>
													<td>
													&nbsp;
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
						<rightButton:rightButton name="���ͨ��" onclick="auditOutMoney(this);" className="anniu_btn" action="/bank/capital/outMoneySecondAuditPass.action" id="auditpass"></rightButton:rightButton>&nbsp;&nbsp;
						<rightButton:rightButton name="��˾ܾ�" onclick="auditOutMoney(this);" className="anniu_btn" action="/bank/capital/outMoneySecondAuditRefuse.action" id="auditrefuse"></rightButton:rightButton>
					</div>
					<div class="div_list">
						<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td>
									<ec:table items="pageInfo.result" var="capital"
										action="${basePath}/bank/capital/outMoneySecondAuditList.action"											
										autoIncludeParameters="${empty param.autoInc}"
										xlsFileName="firm.xls" csvFileName="firm.csv"
										showPrint="true" listWidth="100%"
										minHeight="345"  style="table-layout:fixed;">
										<ec:row>
											<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${capital.actionID}" width="5%" viewsAllowed="html" />
											<ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:right;"/>
											<ec:column property="id" width="10%" title="��¼��ˮ��" style="text-align:right;"/>
											<ec:column property="actionID" width="10%" title="�г���ˮ��" style="text-align:right;"/>
											<ec:column property="firm.firmID" title="�����̴���" width="10%" style="text-align:center;"/>
											<ec:column property="bank.bankID" title="���д���" width="10%" style="text-align:center;"/>
											<ec:column property="money" title="���" width="10%" style="text-align:right;"><fmt:formatNumber 
													value="${capital.money}" pattern="#,##0.00" /></ec:column>
											<ec:column property="createtime" title="����ʱ��" width="10%" style="text-align:center;"><fmt:formatDate 
												value="${capital.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
											<ec:column property="property" title="��ע" width="10%" style="text-align:center;" sortable="false"><c:if 
												test="${fn:indexOf(capital.note,'market_in')>=0}">�г����</c:if><c:if 
												test="${fn:indexOf(capital.note,'market_out')>=0}">�г�����</c:if><c:if 
												test="${fn:indexOf(capital.note,'bank_out')>=0}">���г���</c:if><c:if 
												test="${!(fn:indexOf(capital.note,'market_in')>=0 
												|| fn:indexOf(capital.note,'market_out')>=0 
												|| fn:indexOf(capital.note,'bank_out')>=0)}">�������</c:if></ec:column>
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