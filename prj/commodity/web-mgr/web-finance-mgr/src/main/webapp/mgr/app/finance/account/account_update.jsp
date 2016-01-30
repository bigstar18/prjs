<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>�޸Ŀ�Ŀ��Ϣ</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//�޸İ�ťע�����¼�
				$("#update").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validate')){
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							//�����ϢURL
							var updateDemoUrl = $(this).attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
					}
				});
			});
		</script>
	</head>
	<body>
		<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<input type="hidden" name="entity.code" value="${entity.code}"/>
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :�޸Ŀ�Ŀ${entity.code}��Ϣ
									<br/>
									<c:if test="${entity.isInit=='Y'}">
									   <span class="required">ϵͳ��Ŀ�����޸�</span>
									</c:if>
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
												��Ŀ�޸�
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														��Ŀ���룺
													</td>
													<td>
														${entity.code}&nbsp;
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														��Ŀ���ƣ�
													</td>
													<td>
														<input type="text" id="name" name="entity.name" style="width: 120px" value="${entity.name }"
															class="validate[required,maxSize[16]] "/>&nbsp;
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														�������
													</td>
													<td>
														<select id="dcFlag" name="entity.dcFlag" class="validate[required]" style="width: 120px">
														<option value="">��ѡ��</option>
														<c:forEach items="${account_dCFlagMap}" var="map" varStatus="status">
															<option value="${map.key}">${map.value}</option>
														</c:forEach>
														</select>
														<script type="text/javascript">
															document.getElementById("dcFlag").value="${entity.dcFlag}";
														</script>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>								
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														��Ŀ����
													</td>
													<td>
														<select id="accountLevel" name="entity.accountLevel" class="validate[required]" style="width: 120px">
														<option value="">��ѡ��</option>
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														</select>
														<script type="text/javascript">
															document.getElementById("accountLevel").value="${entity.accountLevel}";
														</script>
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
							<c:if test="${entity.isInit=='N'}">  
							<rightButton:rightButton name="�޸�" onclick="" className="btn_sec" action="/finance/account/updateAccount.action" id="update"></rightButton:rightButton>
							</c:if>
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