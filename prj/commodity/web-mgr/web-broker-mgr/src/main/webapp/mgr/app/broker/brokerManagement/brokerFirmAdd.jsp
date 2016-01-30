<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <base target="_self"/>
		<title>��Ӽ�������Ͻ�û�</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${basePath}/mgr/app/broker/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${basePath}/mgr/app/broker/js/firmjson.js"></script>
		
		<script type="text/javascript">
		jQuery(document).ready(function() {

			//��ȡ�����̴��������б�
			var firmList = getFirmList();
			$("#firmId").focus().autocomplete(firmList);

			//ajax��֤�����̴����Ƿ����
			jQuery("#frm").validationEngine( {
				ajaxFormValidation : true,
				ajaxFormValidationURL : "${basePath }/ajaxcheck/broker/formCheckFirmByFirmId.action",
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
						// �޸���ϢURL
						var updateDemoUrl = $("#update").attr("action");
						//ȫ URL ·��
						var url = "${basePath}"+updateDemoUrl;
						$("#frm").attr("action",url);
						frm.submit();
					}
				}
			}

			//�޸İ�ťע�����¼�
			$("#update").click(function(){
				//��֤��Ϣ
				if ($("#frm").validationEngine('validateform')) {
				}
			});
		  });
			
		</script>
	</head>
<body>
		<form id="frm" method="post" targetType="hidden">
			<div class="div_cx">
				<table border="0" width="100%" align="center">
			
					<tr>
						<td>
							<table border="0" width="100%" align="center">
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												��Ӽ�������Ͻ�û�
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
												<div>
									<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
											       
											   <tr>
													<td align="right">														
														�����̱�ţ�
													</td>
													<td>
													 ${brokerId}																							   
													</td>
													<input type="hidden" name="entity.brokerId" value="${brokerId}" />								
											   </tr>
										      <tr height="35">
								                  <td align="right" class="td_size" width="20%">
									                <span class="required">*</span> �����̴��� ��
								                  </td>
							                      <td align="left" width="45%">
									                 <input id="firmId" style="width: 160px;"
										             name="entity.firmId" type="text" 
										               class="validate[required,ajax[mouseCheckFirmByFirmId]] input_text" />
								                  </td>
								                 <td align="left">
									               <div class="onfocus">
										                                    ����Ϊ�գ�
									               </div>
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
		 <div class="tab_pad" >
				<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
					<tr>
						<td align="center">
							<rightButton:rightButton name="����" onclick="" className="btn_sec" action="/broker/brokerManagement/addBrokerFirm.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">����</button>
						</td>
					</tr>
				</table>
		</div>
	</form>
</body>
