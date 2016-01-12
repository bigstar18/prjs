<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>�ͻ���Ϣ���</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<script type="text/javascript">
			
			
			function applyType(){
				var type = document.getElementById("type").value;
				document.getElementById("billID").length = 1;
				if(type==""){
					$("#firm").hide();//����������ʾ
					$("#f1").hide();
					$("#f2").hide();
					$("#f3").hide();
					$("#f4").hide();
					$("#f5").hide();
					$("#f6").hide();
				}else if(type==0){//��Ѻ
					$("#firm").show();//����������ʾ
					$("#f1").hide();
					$("#f2").show();
					$("#f3").show();
					$("#f4").show();
					$("#f5").show();
					$("#f6").hide();
				}else if(type==1){//������Ѻ
					$("#firm").hide();//����������ʾ
					$("#f1").show();
					$("#f2").show();
					$("#f3").show();
					$("#f4").show();
					$("#f5").hide();
					$("#f6").show();
					getBill(1);
				}
				
				document.getElementById("firmID").value = "";
				document.getElementById("firmMsg").innerHTML = "";
				document.getElementById("warehouseID").innerHTML = "";
				document.getElementById("breed").innerHTML = "";
				document.getElementById("billQty").innerHTML = "";
				document.getElementById("quantity").value = "";
				document.getElementById("breedName").value = "";
				
			}
		
			//��ȡ�ֵ�������ѡ��
			function getBill(type){
				var oldAjaxAsync = $.ajaxSettings.async;
				
				//����ղֵ�������
				document.getElementById("billID").length = 1;
				//����Ѻ�����0
				document.getElementById("billFund").value = 0;
				
				if(type==0){//��Ѻ�ʽ�����
					var firmid = document.getElementById("firmID").value; 			//��ȡ�����̴�����
					if(firmid==null||firmid==""){
						alert("�����뽻���̴��룡");
						return;
					}
					if(firmid!="") {
						var url = "${basePath}/ajaxcheck/pledge/searchBill.action?firmid="+firmid;
						$.ajaxSettings.async = false;
						$.getJSON(url,null,function(result){
							for(var i=0;i<result.length;i++){
								//����ֵ�������
								if(result[i]!="" && result[i]!=null){
									document.getElementById("billID").add(new Option(result[i],result[i]));
								}
							}
						});
					}
				}else if(type==1){//������Ѻ����
					var url1 = "${basePath}/ajaxcheck/pledge/searchReBill.action";
					$.ajaxSettings.async = false;
					$.getJSON(url1,null,function(result){
						for(var j=0;j<result.length;j++){
							//����ֵ�������
							if(result[j]!="" && result[j]!=null){
								document.getElementById("billID").add(new Option(result[j],result[j]));
							}
						}
					});
				}
				$.ajaxSettings.async = oldAjaxAsync;
			}
			
			
			function getBillMessage(){
				var oldAjaxAsync = $.ajaxSettings.async;
				var type = document.getElementById("type").value;			//��ȡ��������
				var BillID = document.getElementById("billID").value; 		//��ȡ�ֵ���
				//����Ѻ�����0
				document.getElementById("billFund").value = 0;
				if(BillID!="") {
					if(type==0){//��Ѻ�ʽ�����
						var url = "${basePath}/ajaxcheck/pledge/searchBillMessage.action?billid="+BillID;
						$.ajaxSettings.async = false;
						$.getJSON(url,null,function(result){
							var warehouseID = result[0];
							var breedname = result[1];
							var quantity = result[2];
							var unit = result[3];
							document.getElementById("warehouseID").innerHTML = warehouseID;
							document.getElementById("breedName").value = breedname;
							document.getElementById("breed").innerHTML = breedname;
							document.getElementById("quantity").value = quantity;
							var qu=quantity+""+unit;
							document.getElementById("billQty").innerHTML = qu;
							
						});
					}else if(type==1){//������Ѻ����
						var url = "${basePath}/ajaxcheck/pledge/searchReBillMessage.action?billid="+BillID;
						$.ajaxSettings.async = false;
						$.getJSON(url,null,function(result){
							document.getElementById("firmID").value = result[0];
							document.getElementById("firmMsg").innerHTML = result[0];
							document.getElementById("warehouseID").innerHTML = result[1];
							document.getElementById("breedName").value = result[2];
							document.getElementById("breed").innerHTML = result[2];
							document.getElementById("quantity").value = result[3];
						 	var qu = result[3] + "" + result[5];
							document.getElementById("billQty").innerHTML = qu;
							document.getElementById("billFund").value = result[4];
							document.getElementById("fund").innerHTML = result[4];
						});
					}
				}else{
					document.getElementById("firmMsg").innerHTML = "";
					document.getElementById("warehouseID").innerHTML = "";
					document.getElementById("breed").innerHTML = "";
					document.getElementById("billQty").innerHTML = "";
					document.getElementById("quantity").value = "";
					document.getElementById("breedName").value = "";
				}
				$.ajaxSettings.async = oldAjaxAsync;
			}
			
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//�޸İ�ťע�����¼�
				$("#add").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validate')){
						var type = document.getElementById("type").value;
						var BillFund = document.getElementById("billFund").value;
						if(type==0){
							if(BillFund<=0){
								alert("��Ѻ���������0��");
								return;
							}
						}
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
				});
				
				//�״ν������ҳ�����ض�̬��Ϣ
				$("#firm").hide();
				$("#f1").hide();
				$("#f2").hide();
				$("#f3").hide();
				$("#f4").hide();
				$("#f5").hide();
				$("#f6").hide();
				
			});
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
									��ܰ��ʾ :��Ѻ�ʽ���Ϣ���
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
												�����Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<input type="hidden" id=breedName name="entity.breedName" />
											<input type="hidden" id=quantity name="entity.quantity" />
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
										            <td align="right">
														<span class="required">*</span>
														�������ͣ�
													</td>
													<td>
														<select id="type" name="entity.type" onchange="applyType()" style="width: 120" class="validate[required]">
														<option value="" selected="selected">��ѡ��</option>
														<c:forEach items="${Pledge_typeMap}" var="map" varStatus="status">
															<option value="${map.key}">${map.value}</option>
														</c:forEach>
														</select>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
												<tr id="firm">
														<td align="right">
															<span class="required">*</span>
															�����̴��룺
														</td>
														<td>
															<input type="text" id="firmID" name="entity.firmID"  onchange="" class="validate[required,maxSize[<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />]] input_text datepicker" />
															<rightButton:rightButton name="��ѯ�ֵ�" onclick="getBill(0)" className="btn_sec" action="" id="selectBill"></rightButton:rightButton>
														</td>
														<td>
															<div class="onfocus">����Ϊ�գ�</div>
														</td>
												</tr>
												 
												<tr>
													<td align="right">
														<span class="required">*</span>
														�ֵ��ţ�
													</td>
													<td>
														<select id="billID" name="entity.billID" onchange="getBillMessage()" style="width: 120" class="validate[required]">
														<option value="" selected="selected">��ѡ��</option>
														</select>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
												<tr id="f1">
													<td align="right">
															�����̴��룺
													</td>
													<td id="firmMsg">
													</td>
												</tr>
												<tr id="f2">
													<td align="right">
															�ֿ��ţ�
													</td>
													<td id="warehouseID">
													</td>
												</tr>
												<tr id="f3">
													<td align="right">
															Ʒ�����ƣ�
													</td>
													<td id="breed">
														
													</td>
													
												</tr>
												<tr id="f4">
													<td align="right">
															��Ʒ������
													</td>
													<td id="billQty">
														
													</td>
												</tr>
												<tr id="f5">
													<td align="right" width="100">
														         ��Ѻ��
													</td>
													<td>
														<input type="text" size="10" id="billFund" name="entity.billFund" "
																 onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="10" class="input_text datepicker,validate[required]" />
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
												<tr id="f6">
													<td align="right" width="100">
														         ��Ѻ��
													</td>
													<td id="fund">
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
							<rightButton:rightButton name="���" onclick="" className="btn_sec" action="/timebargain/pledge/addPledge.action" id="add"></rightButton:rightButton>
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