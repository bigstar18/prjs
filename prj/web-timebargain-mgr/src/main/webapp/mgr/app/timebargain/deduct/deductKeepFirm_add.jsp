<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css"
		type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript">
	
</script>
	<script
		src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
	
</script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK"></script>
	
	<head>
		<SCRIPT type="text/javascript">
		jQuery(document).ready(function() {

				//ajax��֤�����̴����Ƿ����
				jQuery("#frm").validationEngine( {
					ajaxFormValidation : true,
					ajaxFormValidationURL : "../..//ajaxCheck/deduct/formCheckDeductKeepByIds.action",
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
							document.getElementById("add").disabled=true;
						}
					}
				}
	
				//��Ӱ�ťע�����¼�
				$("#add").click(function(){
					//��֤��Ϣ
					if ($("#frm").validationEngine('validateform')) {
					}
				});
	
	
				$("#delete").click(function(check) {
					
					//��ȡ����Ȩ�޵� URL
					var delateUrl = document.getElementById('delete').action;
					//��ȡ������תURL
					var url = "${basePath}"+delateUrl;
					//ִ��ɾ������
					updateRMIEcside(ec.ids,url);
					});
	
	
				$("#back").click(function(check) {
					
					var vaild = affirm("��ȷ��Ҫ������");
					if (vaild == true) {
						 $("#fm").submit();
				}
				});
	
				$("#next").click(function(check) {
					var url="${basePath}"+document.getElementById("next").action;
							var vaild = affirm("��ȷ��Ҫ������");
							if (vaild == true) {
								$('#fm').attr('action', url);
							    $("#fm").submit();
							    document.getElementById("next").disabled=true;
						}
				});
			
			});
		
		</SCRIPT>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
					<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												ǿ����������������
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
						<div class="div_cx">
							<form name="frm" id="frm"  action="${basePath}/timebargain/deduct/addKeepFirm.action"  method="post" targetType="hidden">
							<input id="deductId" name="entity.deductId" value="${deduct.deductId }" type="hidden"/>
							<input id="commodityId" name="commodityId" value="${deduct.commodityId}" type="hidden"/>
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															ǿ������:&nbsp;
															<label>
																<fmt:formatDate value="${deduct.deductDate}" pattern="yyyy-MM-dd"/>
															</label>
														</td>
														<td class="table3_td_1" align="left" style="width: 300px">
															��Ʒ����:&nbsp;
															<label>
																${deduct.commodityId}
															</label>
														</td>
														<td class="table3_td_1" align="left" style="width: 300px">
															���׿ͻ�����:&nbsp;
															<label>
																<select name="entity.customerId" id="customerId" class="input_text_pwdmin">
																	<c:forEach items="${customer}" var="cus">
																		<option value="${cus.customerId}" >
																			${cus.customerId}
																		</option>
																	</c:forEach>
																</select>
															</label>
														</td>
														</tr>
														<tr>
														<td class="table3_td_1" align="left" style="width: 300px">
															������־:&nbsp;
															<label>
																<input type="radio" id="bs_Flag1" name="entity.bs_Flag" value="1" checked="checked" style="border:0px;">��
																<input type="radio" id="bs_Flag2" name="entity.bs_Flag" value="2" style="border:0px;">��
															</label>
														</td>
														<td class="table3_td_1" align="left" style="width: 300px">
															<span style="color: red">*&nbsp;</span>��������:
															<label>
																<input id="keepQty" name="entity.keepQty" value="" style="width: 120px"
																class="validate[required,maxSize[10],custom[number]] "></input>
															</label>
														</td>
														
														<td align="center">
															<rightButton:rightButton name="����" onclick="" className="btn_sec"
																action="/timebargain/deduct/addKeepFirm.action" id="add" ></rightButton:rightButton>
														&nbsp;&nbsp;&nbsp;
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
						<form name="fm" id="fm"  action="${basePath}/timebargain/deduct/updateDeductPositionForward.action"  method="post" targetType="hidden">
							<input id="deductId1" name="entity.deductId" value="${deduct.deductId }" type="hidden"/>&nbsp;&nbsp;
							<rightButton:rightButton name="ɾ��" onclick="" className="anniu_btn" action="/timebargain/deduct/deleteDeductKeep.action" id="delete"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="��һ��" onclick="" className="anniu_btn" action="/timebargain/deduct/operateDeductDetail.action" id="next"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="������һ��" onclick="" className="anniu_btn" action="/timebargain/deduct/updateDeductPositionForward.action" id="back"></rightButton:rightButton>
						</form>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="deductKeep"
											action="${basePath}/timebargain/deduct/deductKeepFirmForward.action?deductId=${deduct.deductId}"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="deductKeep.xls" csvFileName="deductKeep.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${deductKeep.deductId},${deductKeep.customerId},${deductKeep.bs_Flag}" width="5%" viewsAllowed="html" />
												<ec:column property="customerId" width="33%" title="���׿ͻ�����" ellipsis="true" style="text-align:center;"/>
												<ec:column property="_" title="������־" width="33%" style="text-align:center;" >
												<c:if test="${deductKeep.bs_Flag==1}">��</c:if><c:if test="${deductKeep.bs_Flag==2}">��</c:if>
												</ec:column>
												<ec:column property="keepQty" title="��������" width="33%" style="text-align:center;"/>
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
