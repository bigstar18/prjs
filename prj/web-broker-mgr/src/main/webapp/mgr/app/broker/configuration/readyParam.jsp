<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>��Ӷ���������</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
		<script src="${mgrPath }/app/broker/js/util.js" type="text/javascript" charset="GBK"></script>
		
		<SCRIPT type="text/javascript">
		
		    $(document).ready(function() {
		    	jQuery("#frm").validationEngine('attach');
				//�޸İ�ťע�����¼�
				$("#update").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validateform')){
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							//�����ϢURL
							var updateDemoUrl = $(this).attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							$(this).attr("disabled",true);
						}
					}
				});

				var $list_month = $("<select id='payPeriodDate' name='entity.payPeriodDate' class='validate[required]' style='width:120'></select>");
				var $list_week = $("<select id='payPeriodDate' name='entity.payPeriodDate' class='validate[required]' style='width:120'></select>");
				//$list_month.append("<option value=''>��ѡ��</option>");
				//$list_week.append("<option value=''>��ѡ��</option>");
				
			    for (var i=1; i<31; i++) {
					$list_month.append("<option value='"+i+"'>"+i+"��</option>");
				}
				$list_week.append("<option value='1'>������</option>");
				$list_week.append("<option value='2'>����һ</option>");
				$list_week.append("<option value='3'>���ڶ�</option>");
				$list_week.append("<option value='4'>������</option>");
				$list_week.append("<option value='5'>������</option>");
				$list_week.append("<option value='6'>������</option>");
				$list_week.append("<option value='7'>������</option>");

				$("#sel").data("list_month", $list_month);
				$("#sel").data("list_week", $list_week);
				
				changeManner("${entity.payPeriod}");
				$("#payPeriodDate").val("${entity.payPeriodDate}");
			});

			function changeManner(id){
				var $sel = $("#sel");
				$sel.html("");
				if (id == "1") {
					var $list_month = $sel.data("list_month");
					$list_month.clone().appendTo($sel);
				} else if (id == "2") {
					var $list_week = $sel.data("list_week");
					$list_week.clone().appendTo($sel);
				}
			}

			function onChangePay(){

				if(frm.autoPay.value == "Y")
				 {		 
					 $("#payWeek").show();
					 $("#payDay").show();
					 
				
				 }
				else if(frm.autoPay.value == "N")
				 {		 
					$("#payWeek").hide();
					$("#payDay").hide();
				 }
						
			}

			function window_onload(){
				onChangePay();
			}
	
		</SCRIPT>
	</head>
	<body onload="window_onload()">
		<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<div class="div_cx">
				<table border="0" width="800" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :�޸ĸ�Ӷ�����
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="800" align="center">
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												��Ӷ���������
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														�Ƿ��Զ����
													</td>
													<td>
														<select id="autoPay" name="entity.autoPay" class="validate[required]" style="width:120" onchange="onChangePay()">
															
									                          <option value="Y" <c:if test="${entity.autoPay=='Y'}">selected="selected"</c:if>>��</option>
										                      <option value="N" <c:if test="${entity.autoPay=='N'}">selected="selected"</c:if>>��</option>
														</select>
													</td>
													
													<td >
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
												<tr id="payWeek">
												    <td align="right">
												     <span class="required">*</span>
														�������ڣ�
													</td>
													<td>
													    <select id="payPeriod" name="entity.payPeriod" class="validate[required]" style="width:120" onchange="changeManner(this.value)">
													          <%-- <option value="">��ѡ��</option>--%>
									                          <option value="1" <c:if test="${entity.payPeriod==1}">selected="selected"</c:if>>����</option>
										                      <option value="2" <c:if test="${entity.payPeriod==2}">selected="selected"</c:if>>����</option>
														</select>
													</td>
													<td >
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
												<tr id="payDay">
													<td align="right">
														<span class="required">*</span>
														���������գ�
													</td>
													<td id="sel" >
														
													</td>
													<td >
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
				
												</tr>
												
												<tr>
													<td colspan="4" align="center">                                                  
														<rightButton:rightButton name="�ύ" onclick="" className="btn_sec" action="/config/ready/updateReadyParam.action" id="update"></rightButton:rightButton>
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
		</form>
	</body>
</html>