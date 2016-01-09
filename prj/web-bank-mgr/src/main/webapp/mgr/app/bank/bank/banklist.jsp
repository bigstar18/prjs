<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>���й���</title>
		<script type="text/javascript">
			function update(id){<%//���޸�������Ϣҳ��%>
				var url = "${basePath}/bank/bank/updateBankForward.action?entity.bankID="+id;
				if(showDialog(url, "", 650, 530)){
					frm.submit();
				};
			}
			function updatestatus(btn){<%//�޸�����״̬%>
				var url = "${basePath}"+btn.action+"?autoInc=false";
				updateRMIEcside(ec.ids,url);
			}
			function updatefee(id){<%//���޸������ѽ���%>
				var url = "${basePath}/bank/fee/setUpBankFeeForward.action?bankID="+id;
				showDialog(url, "", 900, 530)
			}
		</script>
	</head>
	<body>
		<form id="frm" action="${basePath}/bank/bank/bankList.action?sortColumns=order+by+bankID"></form>
		<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div class="div_gn">
						<rightButton:rightButton name="����" onclick="updatestatus(this);" className="anniu_btn" action="/bank/bank/forbiddenBank.action" id="forbidden"></rightButton:rightButton>&nbsp;&nbsp;
						<rightButton:rightButton name="����" onclick="updatestatus(this);" className="anniu_btn" action="/bank/bank/startUsingBank.action" id="start_using"></rightButton:rightButton>
					</div>
					<div class="div_list">
						<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td>
									<ec:table items="pageInfo.result" var="bank"
										action="${basePath}/bank/bank/bankList.action"											
										autoIncludeParameters="${empty param.autoInc}"
										xlsFileName="bank.xls" csvFileName="bank.csv"
										showPrint="true" listWidth="100%"
										minHeight="345"  style="table-layout:fixed;">
										<ec:row>
											<ec:column width="6%"  cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${bank.bankID}" viewsAllowed="html" />
											<ec:column width="6%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:right;"/>
											<ec:column property="bankID" width="11%" title="���д���" style="text-align:center;"><rightHyperlink:rightHyperlink href="#" className="blank_a" onclick="return update('${bank.bankID}');" text="<font color='#880000'>${bank.bankID}</font>" action="/bank/bank/updateBankForward.action">
											</rightHyperlink:rightHyperlink></ec:column>
											<ec:column property="bankName" width="11%" title="��������" style="text-align:center;" sortable="false"/>
											<ec:column property="maxPersgltransMoney" width="11%" title="������������" style="text-align:right;"><c:if 
											test="${bank.maxPersgltransMoney<=0}">--</c:if><c:if 
											test="${bank.maxPersgltransMoney>0}"><fmt:formatNumber value="${bank.maxPersgltransMoney}" pattern="#,##0.00" /></c:if></ec:column>
											<ec:column property="maxPertransMoney" width="11%" title="ÿ����������" style="text-align:right;"><c:if 
											test="${bank.maxPertransMoney<=0}">--</c:if><c:if 
											test="${bank.maxPertransMoney>0}"><fmt:formatNumber value="${bank.maxPertransMoney}" pattern="#,##0.00" /></c:if></ec:column>
											<ec:column property="maxPertransCount" width="11%" title="ÿ�����������" style="text-align:right;"/>
											<ec:column property="maxAuditMoney" width="11%" title="������˶��" style="text-align:right;"><c:if 
											test="${bank.maxAuditMoney<=0}">--</c:if><c:if 
											test="${bank.maxAuditMoney>0}"><fmt:formatNumber value="${bank.maxAuditMoney}" pattern="#,##0.00" /></c:if></ec:column>
											<ec:column property="validflag" title="״̬" width="11%" style="text-align:center;" value="${bankStatus[bank.validflag]}"/>
											<ec:column property="property" title="����������" width="11%" style="text-align:center;" sortable="false"><rightHyperlink:rightHyperlink display="none" href="#" className="blank_a" onclick="return updatefee('${bank.bankID}');" text="<font color='#880000'>����</font>" action="/bank/fee/setUpBankFeeForward.action">
											</rightHyperlink:rightHyperlink></ec:column>
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