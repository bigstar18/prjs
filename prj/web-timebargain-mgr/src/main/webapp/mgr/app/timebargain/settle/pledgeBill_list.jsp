<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>�ֵ���Ϣ�б�</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//�����Ϣ��ת
			function addAheadSettleForward(){
				//��ȡ����Ȩ�޵� URL
				var addUrl=document.getElementById('add').action;
				//��ȡ������תURL
				var url = "${basePath}"+addUrl;
				//�������ҳ��
				if(showDialog(url, "", 900, 700)){
					//�����ӳɹ�����ˢ���б�
					document.getElementById("view").click();
				}
			}
			//�޸���Ϣ��ת
			function updateCustomerForward(customerId){
				//��ȡ����Ȩ�޵� URL
				var updateUrl = document.getElementById('update'+customerId).action;
				//��ȡ������תURL
				var url = "${basePath}"+updateUrl;
				//�� URL ��Ӳ���
				if(url.indexOf("?")>0){
					url += "&entity.customerId="+customerId;
				}else{
					url += "?entity.customerId="+customerId;
				}
				//�����޸�ҳ��
				if(showDialog(url, "", 800, 550)){
					//����޸ĳɹ�����ˢ���б�
					document.getElementById("view").click();
				};
			}
			//����ɾ����Ϣ
			function delateCustomerList(){
				//��ȡ����Ȩ�޵� URL
				var delateUrl = document.getElementById('delate').action;
				//��ȡ������תURL
				var url = "${basePath}"+delateUrl;
				//ִ��ɾ������
				updateRMIEcside(ec.ids,url);
			}
			//�鿴��Ϣ����
			function customerDetails(customerId){
				var url = "${basePath}/dem/customerplay/customerdetails.action?entity.customerId="+customerId;
				showDialog(url, "", 700, 450);
			}
			//ִ�в�ѯ�б�
			function dolistquery() {
				frm.submit();
			}
		//-->
		</SCRIPT>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								��ܰ��ʾ :�鿴��Ѻ�ʽ�ֵ���<b style="color: red;">${billID}</b>�Ĳֵ���ϸ��Ϣ��
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table  items="pageInfo.result" var="bill"
											action="${basePath}/timebargain/pledge/getBillListByBillID.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="STOCKID"  alias="remarkHdd" width="18%" title="�ֵ���" style="text-align:center;" />
												<ec:column property="WAREHOUSEID" title="�ֿ���" width="18%" style="text-align:center;"/>
												<ec:column property="BREEDNAME" title="Ʒ������" width="18%" style="text-align:center;"/>
												<ec:column property="QTYUNIT" title="��Ʒ����" width="18%" style="text-align:center;">
													${bill.QUANTITY}&nbsp;${bill.UNIT}
												</ec:column>
												<ec:column property="LASTTIME" title="�����ʱ��" width="28%" style="text-align:center;">
													<fmt:formatDate value="${bill.LASTTIME}" pattern="yyyy-MM-dd HH:mm:ss" /> 
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
		<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
					<tr>
						<td align="right">
							<button class="btn_sec" onClick="window.close();">�ر�</button>
						</td>
					</tr>
				</table>
		</div>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>