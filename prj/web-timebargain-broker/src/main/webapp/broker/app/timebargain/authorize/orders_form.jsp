<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>
		</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<script type="text/javascript"> 
		function setReadOnly(obj)
		{
		  obj.readOnly = true;
		  obj.style.backgroundColor = "#C0C0C0";
		}
		function setReadWrite(obj)
		{
		  obj.readOnly = false;
		  obj.style.backgroundColor = "white";
		}
		function setDisabled(obj)
		{
		  obj.disabled = true;
		  obj.style.backgroundColor = "#C0C0C0";
		}
		function setEnabled(obj)
		{
		  obj.disabled = false;
		  obj.style.backgroundColor = "white";
		}
		
		
		jQuery(document).ready(function() {
			jQuery("#frm").validationEngine('attach');
			$("#order").click(function(){
				//��֤��Ϣ
				if ($("#frm").validationEngine('validate')) {
					loginConsigner();
				}
			});
			
			//�ύ�¼�
			function loginConsigner() {
				var vaild = window.confirm("��ȷ��Ҫ�ύ��");
				if(vaild){	
					//�����ϢURL
					var orderUrl = $("#order").attr("action");
					//ȫ URL ·��
					var url = "${basePath}"+orderUrl;
					$("#frm").attr("action",url);
					frm.submit();
				}
			}
			
			$("#logoff").click(function(){
				var vaild = affirm("��ȷ��Ҫע����");
				if(vaild){
					//�����ϢURL
					var logoffUrl = $("#logoff").attr("action");
					//ȫ URL ·��
					var url = "${basePath}"+logoffUrl;
					$("#frm").attr("action",url);
					frm.submit();
			}});
		});
		
		
		//ҳ��װ��ʱ���� 
		function window_onload()
		{
		    init();
		
		    
		}
		//�����ķ���
		function init()
		{
		    setDisabled(frm.closeMode);
		    setDisabled(frm.timeFlag);
		    setDisabled(frm.closeFlag);
		    setReadOnly(frm.specPrice);
		    spanTime.style.visibility = "hidden";
		    spanPrice.style.visibility = "hidden";
		    inCloseMode.style.visibility = "hidden";
		}
		
		function myReset(){
			
		
		}
		

		//����Ա�Ƿ��¼���ύί��(ͳһ�ص�����)
		function order()
		{	
			frm.action = "${basePath}/timebargain/authorize/order.action";
			frm.submit();
			frm.order.disabled = true;
		}
		
		//ί�����ͱ仯�¼�
		function orderType_onchange()
		{
		  if(frm.orderType.value == "1" || frm.orderType.value == "")
		  {
		    init();
		    setDisabled(frm.closeFlag);
		    $("#closeMode").removeClass("validate[required] text-input");
		  }
		  else if(frm.orderType.value == "2")
		  {
		  	inCloseMode.style.visibility = "visible";
		  	
		 	setEnabled(frm.closeFlag);
		    setEnabled(frm.closeMode);
		    $("#closeMode").addClass("validate[required] text-input");
		  }
		}
		//ת��ģʽ�仯�¼�
		function closeMode_onchange()
		{
		  $("#timeFlag").removeClass("validate[required] text-input");
		  $("#specPrice").removeClass("validate[required] text-input");	
		  if(frm.closeMode.value == "1" || frm.closeMode.value == "")
		  {
		    setDisabled(frm.timeFlag);
		    setReadOnly(frm.specPrice);
		    spanTime.style.visibility = "hidden";
		    spanPrice.style.visibility = "hidden";
		  }
		  else if(frm.closeMode.value == "2")
		  {
		    setEnabled(frm.timeFlag);
		    setReadOnly(frm.specPrice);
		    spanTime.style.visibility = "visible";
		    spanPrice.style.visibility = "hidden";
		    $("#timeFlag").addClass("validate[required] text-input");
		  }
		  else if(frm.closeMode.value == "3")
		  {
		    setDisabled(frm.timeFlag);
		    setReadWrite(frm.specPrice);
		    spanTime.style.visibility = "hidden";
		    spanPrice.style.visibility = "visible";
		    $("#specPrice").addClass("validate[required] text-input");
		  }
		}
		
		function isCheck(){
			if (frm.closeFlag.checked) {
				document.getElementById("td1").className = 'xian';
				document.getElementById("td2").className = 'xian';
				frm.forceCloseType.value = "1";
				$("#forceCloseType").addClass("validate[required] text-input");
			}else {
				document.getElementById("td1").className = 'yin';
				document.getElementById("td2").className = 'yin';
				frm.forceCloseType.value = "";
				$("#forceCloseType").removeClass("validate[required] text-input");
			}
		}
		
		function forceCloseType_onchange(){
			if (frm.forceCloseType.value == "1") {
				frm.price.value = "";
				setReadWrite(frm.price);
			}
			if (frm.forceCloseType.value == "2") {
				frm.price.value = "0";
				setReadOnly(frm.price);
			}
		}
		function BS_Flag_onchange(){
			frm.orderType.value=frm.BS_Flag.value;
			orderType_onchange();
		}
		</script>
		<style type="text/css">
		<!--
		.yin {
			visibility:hidden;
			position:absolute;
			
		}
		.xian{
			visibility:visible;
		}
		-->
		</style>
	</head>
	
	
	<body onload="window_onload();">
	<form id="frm" method="post" enctype="multipart/form-data" action="" target="HiddFrame">
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :��Ϊί��
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
												��Ϊί��
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														�ͻ����룺
														<input type="hidden" id="marketCode"  name="entity.marketCode"  value="">
														<input type="hidden" id="orderType"  name="entity.orderType"  value="">
														<input type="hidden" id="type"  name="entity.type"  value="">
													</td>
													<td>
														<input type="text" id="customerId" name="entity.customerId"  maxlength="64" style="width:120"
															class="validate[required]"/>
													</td>
													
													<td align="right">
														<span class="required">*</span>
														��Ʒ���룺
													</td>
													<td>
														<input type="text" id="commodityId" name="entity.commodityId"  maxlength="64" style="width:105"
															class="validate[required,maxSize[16]]"/>
													</td>
													
													<td align="right">
														<span class="required">*</span>
														������
													</td>
													<td>
														<select id="BS_Flag" name="entity.BS_Flag" class="validate[required]" style="width:105" onchange="BS_Flag_onchange();">
														<option value="">��ѡ��</option>
														<option value="1">���</option>
														<option value="2">����</option>
														</select>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														ί�м۸�
													</td>
													<td>
														<input type="text" id="price" name="entity.price"  maxlength="64" style="ime-mode:disabled;width:105"
															class="validate[required,custom[doubleCus],maxSize[16]]"/>
													</td>
													
													<td align="right">
														<span class="required">*</span>
														ί��������
													</td>
													<td>
														<input type="text" id="quantity" name="entity.quantity"  maxlength="64" style="ime-mode:disabled;width:105"
															class="validate[required,custom[integer],min[1]]"/>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span id="inCloseMode" class="required" style="visibility:hidden">*</span>
														ת�÷�ʽ��
													</td>
													<td>
														<select id="closeMode" name="entity.closeMode" style="width:105" class="" onchange="closeMode_onchange();">
														<option value=""></option>
														<option value="1">��ָ��ת��</option>
														<option value="2">ָ��ʱ��ת��</option>
														<option value="3">ָ���۸�ת��</option>
														</select>
													</td>
													
													<td align="right">
														<span id="spanTime" class="required" style="visibility:hidden">*</span>
														ָ��ʱ���־��
													</td>
													<td>
														<select id="timeFlag" name="entity.timeFlag" class="" style="width:105" onchange="">
														<option value=""></option>
														<option value="1">ת�ý񶩻�</option>
														<option value="2">ת����ʷ����</option>
														</select>
													</td>
													
													<td align="right">
														<span id="spanPrice" class="required" style="visibility:hidden">*</span>
														ָ���۸�
													</td>
													<td>
														<input type="text" id="entity.specPrice" name="specPrice"  maxlength="64" style="width:105"
															class=""/>
													</td>
												</tr>
												 <tr class="<c:if test='${type==1}'>xian</c:if><c:if test='${type!=1}'>yin</c:if>"> 
											      <td align="right">
											      		ǿ��ת�ã�
											      </td> 
											      <td>
											      		<input type="checkbox" id="closeFlag" name="closeFlag" value="2" onclick="isCheck();"/>
											      </td>
											      
											      <td id="td1" class="yin" align="right">
											      <span class="required">*</span>
											      	ǿ��ת�ü۸�ʽ��
											      </td>
										            <td id="td2" class="yin">
										            	<select id="forceCloseType" name="entity.forceCloseType" class="" style="width:105" onchange="forceCloseType_onchange();">
														<option value=""></option>
														<option value="1">ָ���۸�</option>
														<option value="2">���м�</option>
														</select>
										            </td>
											    </tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td>
									<div>
										<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
											<tr>
												<td align="center">
													<rightButton:rightButton name="ȷ��" onclick="" className="btn_sec" action="/timebargain/authorize/chkLogin.action" id="order"></rightButton:rightButton>
													&nbsp;&nbsp;
													<button class="btn_sec" onClick="myReset();" id="reset">����</button>
													&nbsp;&nbsp;
													<rightButton:rightButton name="ע���ǳ�" onclick="" className="btn_sec" action="/timebargain/authorize/logoffConsigner.action?mkName=authorize" id="logoff"></rightButton:rightButton>
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
