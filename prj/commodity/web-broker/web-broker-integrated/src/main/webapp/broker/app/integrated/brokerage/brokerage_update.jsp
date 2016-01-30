<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
	<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
	<script src="${basePath }/broker/app/integrated/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
	<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
	<script>
	

		jQuery(document).ready(function() {
			$("#frm").validationEngine('attach');
			$("#update").click(function(check) {
				if ($("#frm").validationEngine('validate')) {
					var vaild = affirm("��ȷ��Ҫ������");
					if (vaild == true) {
						//$('#frm').attr('action', 'action');
					    $("#frm").submit();
						//document.getElementById("update").disabled=true;
				}
			}})
		});
		function getHidden(type){
			if(type !=3){
				$("#content").css("display","block");
			}else{
				$("#content").css("display","none");
			}
		}
		function changeBrokerage(sel){
			$("#pbrokerAgeId").attr("value",sel.options[sel.selectedIndex].value);
		}
</script>
	<head>
		<title>�Ӽ�����Ϣ</title>
	</head>
		<body onload="">
		<form id="frm" method="post"
			action="${basePath}/broker/brokerage/updateBrokerage.action" targetType="hidden">
			<div class="div_cx"  style="overflow:auto;height:480px;" >
				<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								��ܰ��ʾ :�Ӽ�����Ϣ
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
												������Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%"
												align="right" class="table2_style">
												<tr>
													<td align="right" width="20%">
														�Ӽ��̴��룺
													</td>
													<td  width="30%">
														<input type="text" id="brokerAgeId" name="entity.brokerAgeId"
															class="input_text" style="background-color:#eee"
															readonly="false" value="${entity.brokerAgeId}"/>
													</td>
													<td align="right">
													<span class="required">*</span>
														�Ӽ������ƣ�
													</td>
													<td>
														<input type="text" id="name"
															class="validate[maxSize[20]] input_text"
															name="entity.name" value="${entity.name}"
															data-prompt-position="bottomLeft:0"/>
													</td>													
												</tr>
												<tr>
													<td align="right">
														������Ա��
													</td>
													<td>
														<input type="text" id="brokerId" name="entity.brokerId" 
															class="validate[required,maxSize[12] input_text" style="background-color:#eee"
															readonly="false" value="${entity.brokerId}"/>
														
													</td>
													<td align="right">
														�����Ӽ��̣�
													</td>
													<td>
														<select id="type" class="input_text" id="pBrokerageId2" onchange="changeBrokerage(this)">
															<option value="">��ѡ��</option>
															<c:forEach items="${brokerAgeList}" var="brokerAge">
																<option value="${brokerAge['BROKERAGEID']}"
																<c:if test="${entity.pbrokerAgeId==brokerAge['BROKERAGEID']}">selected="selected"</c:if>>
																	${brokerAge['BROKERAGEID']}</option>
															</c:forEach>
														</select>
														<input type="hidden" id="pbrokerAgeId" name="entity.pbrokerAgeId" value="${entity.pbrokerAgeId }"/>
													</td>													
												</tr>
												<tr>
													<td align="right">
													<span class="required">*</span>
														���֤�ţ�
													</td>
													<td>
														<input type="text" id="idCard"
															class="validate[required,maxSize[18] input_text" name="entity.idCard"
															 value="${entity.idCard}"/>
													</td>
													<td align="right">&nbsp;</td>
													<td>&nbsp;</td>													
												</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												��ϵ��Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%"
												align="center" class="table2_style">
												
												<tr>
												    <td align="right">
													<span class="required">*</span>
														�ֻ����룺
													</td>
													<td >
														<input type="text" id="mobile"
															class="validate[required,maxSize[12],custom[mobile]] input_text"
															name="entity.mobile" value="${entity.mobile}" data-prompt-position="topLeft:0 "/>
													</td>
													<td align="right" width="20%">
														�绰��
													</td>
													<td width="*">
														<input type="text" id="phone"
															class="validate[maxSize[20],custom[phone]] input_text"
															name="entity.telephone" value="${entity.telephone}"
															data-prompt-position="bottomLeft:0"/>
													</td>
												</tr>
												<tr>
													<td align="right">													
													    <span class="required">*</span>
															�������䣺
													</td>
													<td>
														<input type="text" id="email"
															class="validate[required,maxSize[20],custom[email]] input_text"
															name="entity.email" value="${entity.email}" />
													</td>
													<td align="right">
														���룺
													</td>
													<td>
														<input type="text" id="postCode"
															class="validate[maxSize[16]] input_text" name="entity.postCode"
															value="${entity.postCode}" />
													</td>
												</tr>												
												<tr>
													<td align="right">
													<span class="required">*</span>
														��ϵ��ַ��
													</td>
													<td colspan="3" align="left">
														<input type="text" id="address" style="width: 300px;"
															class="validate[required,maxSize[64]] input_text" 
															name="entity.address" value="${entity.address}" />
													</td>
												</tr>
												<tr>
													<td align="right">
														��ע��
													</td>
													<td colspan="3">
														<textarea rows="3" cols="64" name="entity.note" id="note"
															class="validate[maxSize[128]] ">${entity.note }</textarea>
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
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="right">
							<rightButton:rightButton name="�޸�" onclick="" className="btn_sec"
								action="/broker/brokerage/updateBrokerage.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick=window.close();>
								�ر�
							</button>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<script type="text/javascript">
	
</script>
		</form>

	</body>
</html>
<%@ include file="/broker/public/jsp/footinc.jsp"%>