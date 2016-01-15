<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>ժҪ���</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				//��ȡ�����̴��������б�
				var firmList = getfirmList();
				$("#firm_firmId").focus().autocomplete(firmList);

				//ajax��֤�����̴����Ƿ����
				jQuery("#frm").validationEngine( {
					ajaxFormValidation : true,
					ajaxFormValidationURL : "../../ajaxcheckVoucherModel/voucherModel/formCheckSummaryByNo.action",
					onAjaxFormComplete : ajaxValidationCallback,
					onBeforeAjaxFormValidation : beforeCall
				});

				//�ύǰ�¼�
				function beforeCall(form, options) {
					return true;
				}

				//�ύ���¼�
				function ajaxValidationCallback(status, form, json, options) {
					//������سɹ�
					if (status === true) {
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							//�����ϢURL
							var addDemoUrl = $("#add").attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+addDemoUrl;
							$("#frm").attr("action",url);
							frm.submit();
						}
					}
				}

				//��Ӱ�ťע�����¼�
				$("#add").click(function(){
					//��֤��Ϣ
					if ($("#frm").validationEngine('validateform')) {
					}
				});
			});
			
			
			function checkSummaryNo(){
					var summaryNo = document.getElementById("summaryNo").value;
					if(summaryNo.length!=5){
						return "* ����Ϊ5λ�ַ�";
					}			
			}
		</script>
	</head>
	<body>
		<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :ժҪ���
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="100%" align="center">
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												���ժҪ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														ժҪ�ţ�
													</td>
													<td colspan="4">
														<input type="text" id="summaryNo" name="entity.summaryNo"
															class="validate[required,funcCall[checkSummaryNo]] "/>&nbsp;
															
														<input type="hidden" id="isInit" name="entity.isInit" value="N"/>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														ժҪ���ƣ�
													</td>
													<td colspan="4">
														<input type="text" id="summary" name="entity.summary" size="70"
															class="validate[required,maxSize[16]] "/>&nbsp;
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required"></span>
														�������ˣ�
													</td>
													<td>
														<select id="ledgerItem" name="entity.ledgerItem">
														<option value="">��ѡ��</option>
														<c:forEach items="${ledgerFieldPage.result}" var="map" varStatus="status">
															<option value="${map.code}">${map.name}</option>
														</c:forEach>
														</select>
													</td>
													<td>
													</td>
													
													<td align="right">
														<span class="required">*</span>
														�������ʽ�������
													</td>
													<td>
														<select id="fundDCFlag" name="entity.fundDCFlag" class="validate[required]">
														<option value="">��ѡ��</option>
														<c:forEach items="${summary_fundDCFlagMap}" var="map" varStatus="status">
															<option value="${map.key}">${map.value}</option>
														</c:forEach>
														</select>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>												
												</tr>
												<tr>
													<td align="right">
														<span class="required"></span>
														�Է���Ŀ���룺
													</td>
													<td>
														<input type="text" id="accountCodeOpp" name="entity.accountCodeOpp"
															class="validate[maxSize[16]] "/>
													</td>
													<td>
													</td>
													
													
													<td align="right">
														<span class="required">*</span>
														�����ˣ�
													</td>
													<td>
														<select id="appendAccount" name="entity.appendAccount" class="validate[required]">
														<option value="">��ѡ��</option>
														<c:forEach items="${summary_appendAccountMap}" var="map" varStatus="status">
															<option value="${map.key}">${map.value}</option>
														</c:forEach>
														</select>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
					<tr>
						<td align="right">
							<rightButton:rightButton name="���" onclick="" className="btn_sec" action="/finance/summary/addSummary.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">�ر�</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>