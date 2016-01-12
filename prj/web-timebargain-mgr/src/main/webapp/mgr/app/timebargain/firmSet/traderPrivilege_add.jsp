<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
	<title>��ӽ���ԱȨ��</title>
	  <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
	  <link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
	  <script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
	  <script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
	  <script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
	  <script type="text/javascript">
		jQuery(document).ready(function() {
			
			// ajax��֤����Ȩ���Ƿ����
			jQuery("#frm").validationEngine( {
				ajaxFormValidation : true,
				ajaxFormValidationURL : "../../../ajaxcheck/firmSet/formCheckTradeprivilege.action",
				onAjaxFormComplete : ajaxValidationCallback,
				onBeforeAjaxFormValidation : beforeCall
			});

			// �ύǰ�¼�
			function beforeCall(form, options) {
				return true;
			}

			// �ύ���¼�
			function ajaxValidationCallback(status, form, json, options) {
				// ������سɹ�
				if (status === true) {
					var vaild = affirm("��ȷ��Ҫ������");
					if(vaild){
						// �����ϢURL
						var addUrl = $("#add").attr("action");
						// ȫ URL ·��
						var url = "${basePath}"+addUrl;
						$("#frm").attr("action",url);
						frm.submit();
					}
				}
			}

			// ��Ӱ�ťע�����¼�
			$("#add").click(function(){

				//��֤��Ϣ
				if ($("#frm").validationEngine('validateform')) {
				}
			});
		});
	</script>
		
  </head>
  <body>
    <form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<input type="hidden" name="entity.type" value="3"/>
			<input type="hidden" name="entity.typeID" value="${typeID}"/>
			<input type="hidden" name="entity.kind" value="1"/>
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :��ӽ���ԱȨ��
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
												����ԱȨ��ά��
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="10" width="100%" align="center" class="table2_style">	
												<tr>
													<td align="right">
														����Ʒ�֣�
													</td>
													<td>
														<select id="breedID" name="entity.kindID" class="validate[required]">
														  <option value="">��ѡ��</option>
														  <c:forEach items="${breedList}" var="result" >
															<option value="${result.BREEDID}">${result.BREEDNAME}</option>
														  </c:forEach>
														</select>
														<span class="required">*</span>
													</td>
													
												</tr>	
												<tr>
													<td align="right">
														��Ȩ�ޣ�
													</td>
													<td>
														<select id="privilegeCode_B" name="entity.privilegeCode_B" class="validate[required]">
														  <option value="">��ѡ��</option>
														  <option value="101">ȫȨ</option>
														  <option value="102">ֻ�ɶ���</option>
														  <option value="103">ֻ��ת��</option>
														  <option value="104">��Ȩ</option>
														</select>
														<span class="required">*</span>
													</td>
												</tr>
												<tr>
													<td align="right">
														����Ȩ�ޣ�
													</td>
													<td>
														<select id="privilegeCode_S" name="entity.privilegeCode_S" class="validate[required]">
														  <option value="">��ѡ��</option>
														  <option value="201">ȫȨ</option>
														  <option value="202">ֻ�ɶ���</option>
														  <option value="203">ֻ��ת��</option>
														  <option value="204">��Ȩ</option>
														</select>
														<span class="required">*</span>
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
				<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
					<tr>
						<td align="center">
							<rightButton:rightButton name="���" onclick="" className="btn_sec" action="/timebargain/firmSet/tradePrivilege/addTraderPrivilege.action" id="add"></rightButton:rightButton>
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