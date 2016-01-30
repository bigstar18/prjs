<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <base target="_self">
		<title>ժҪ�б�</title>
		<SCRIPT type="text/javascript">
		
			//�����Ϣ��ת
			function addSummaryForward(){
				//��ȡ����Ȩ�޵� URL
				var addUrl=document.getElementById('add').action;
				//��ȡ������תURL
				var url = "${basePath}"+addUrl;
				//�������ҳ��
				if(showDialog(url, "", 750, 350)){
					//�����ӳɹ�����ˢ���б�
					document.getElementById("view").click();
				}
			}
			//�޸���Ϣ��ת
			function updateSummaryForward(code){
				//��ȡ����Ȩ�޵� URL
				var updateUrl = document.getElementById('update'+customerId).action;
				//��ȡ������תURL
				var url = "${basePath}"+updateUrl;
				//�� URL ��Ӳ���
				if(url.indexOf("?")>0){
					url += "&entity.code="+code;
				}else{
					url += "?entity.code="+code;
				}
				//�����޸�ҳ��
				if(showDialog(url, "", 800, 550)){
					//����޸ĳɹ�����ˢ���б�
					document.getElementById("view").click();
				};
			}
			//����ɾ����Ϣ
			function deleteSummaryList(){
				//��ȡ����Ȩ�޵� URL
				var deleteUrl = document.getElementById('delete').action;
				//��ȡ������תURL
				var url = "${basePath}"+deleteUrl+"?autoInc=false";
				//ִ��ɾ������
				updateRMIEcside(ec.ids,url);
			}
			//�鿴��Ϣ����
			function summaryDetails(code){
				var url = "${basePath}/finance/summary/updateSummaryforward.action?entity.summaryNo="+code;
				if(showDialog(url, "", 750, 350)){
					//����޸ĳɹ�����ˢ���б�
					document.getElementById("view").click();
				};
			}
			//ִ�в�ѯ�б�
			function dolistquery() {
				frm.submit();
			}

			var summaryNo = "";
			
			// ����ѡ��
			function row_onclick(id){

				var objTr=event.srcElement;
				var objRadio = objTr.all.namedItem("selectRadio");
				if(objRadio){
					objRadio.checked = true;
					summaryNo = id;
				}
							
			}

			// ˫��ѡ��
			function row_ondblclick(id){
				
				window.returnValue = id;
				window.close();
			}

	
			// ����ѡ��
			function onclickRadio(id){
				summaryNo = id;
			}
			
			function selectSummary(){
				if(summaryNo != ""){
					window.returnValue = summaryNo;
					window.close();
				}
				else{
					alert("��ѡ��ժҪ��");
				}
			}

			// ȡ��
			function cancel(){
				window.close();
			}

			// ����
			function myResets(){

				document.getElementById('summaryNo').value = "";
				document.getElementById('summary').value = "";
				
			}
		
		</SCRIPT>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx" >
							<form name="frm" action="${basePath}/finance/voucher/viewSummary.action?sortColumns=order+by+summaryNo" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style" >
									<tr>
										<td class="table5_td_width" >
											<div class="div2_top" >
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="width: 600px;">
													<tr>
														<td class="table3_td_1" align="left">
															ժ Ҫ ��:&nbsp;
															<label>
																<input type="text" class="input_text" id="summaryNo"  checked="checked" name="${GNNT_}primary.summaryNo[allLike]" value="${oldParams['primary.summaryNo[allLike]']}" maxLength="5"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															ժҪ����:&nbsp;
															<label>
																<input type="text" class="input_text" id="summary" name="${GNNT_}primary.summary[allLike]" value="${oldParams['primary.summary[allLike]'] }" maxLength="16"/>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick="dolistquery()">��ѯ</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myResets();">����</button>
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
										<ec:table items="pageInfo.result" var="summary"
											action="${basePath}/finance/voucher/viewSummary.action?sortColumns=order+by+summaryNo"											
											autoIncludeParameters="${empty param.autoInc}"
											
											showPrint="false" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row onclick="row_onclick('${summary.summaryNo}')"  ondblclick="javascript:row_ondblclick('${summary.summaryNo}')">
											
												<ec:column property="summaryNo" width="10%" title="ժҪ��" style="text-align:center;">		
												  <input type="radio" name="selectRadio" onclick="onclickRadio('${summary.summaryNo}')" value="${summary.summaryNo}">
												  ${summary.summaryNo}
												</ec:column>
												<ec:column property="summary" title="ժҪ����" width="10%" style="text-align:center;"/>
												
											</ec:row>
											
										</ec:table>
									</td>
								</tr>
							</table>
							
						  <div align="right">
							<button class="anniu_btn" onClick="selectSummary()">ȷ��</button>&nbsp;&nbsp;
							<button class="anniu_btn" onClick="cancel()">�ر�</button>
						  </div>
						</div>
					</td>
				</tr>
			</table>
		
	</body>
</html>