<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <base target="_self" />
		<title>���׽����</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript"> 
			 $(document).ready(function() {
				 
				    //ajax��֤
					jQuery("#frm").validationEngine( {
						ajaxFormValidation : true,
						ajaxFormValidationURL : "../../ajaxcheck/demo/ajaxAddTradeTimeById.action",
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
							var flag = false;

						    flag = save_onclick();
						    if(flag){
						    	var vaild = affirm("��ȷ��Ҫ������");
								if(vaild){	
									frm.submit();
									$("#add").attr("disabled",true);
								}
							}
						} else {
							$("#sectionID").focus();
						}
					}
					
					//�޸İ�ťע�����¼�
					$("#add").click(function(){
						//��֤��Ϣ
						if(jQuery("#frm").validationEngine('validateform')){	
						}
					});
					change();
			 });

			 function isTime(val) {
					var str=val;
				    
			    	if(str.length == 8) {
			         	var j=str.split(":");
			         	if(j.length == 3) {
			         		var a = j[0].match(/^(\d{2})$/);
					   		if (a == null) {
								return false;
							}
							a = j[1].match(/^(\d{2})$/);
					   		if (a == null) {
								return false;
							}
					   		a = j[2].match(/^(\d{2})$/);
					   		if (a == null) {
								return false;
							}
							
							if (j[0]>24||j[1]>60||j[2]>60) {
					           	return false;
					    	}
				        } else {
							return false;
					    } 	
			      	} else {
			           	return false;
			       	}
			        return true;
			}
			
			function change() {
				var $bid = $("#gatherBid").val();
				if ($bid == "0") {
					$("#bidTime").hide();
				} else if ($bid == "1") {
					$("#bidTime").show();
				}
			}

			// ��ȡ�г�����
			function getMarket(){
				var oldAjaxAsync = $.ajaxSettings.async;
				var url = "${basePath}/ajaxcheck/firmSet/getMarketJson.action";
				$.ajaxSettings.async = false;
				$.getJSON(url,null,function call(result){

					// ���ý���ʱ�����ͣ�0��ͬһ�콻�ף�1�����콻��
					document.getElementById("tradeTimeType").value = result[0];
				});
				$.ajaxSettings.async = oldAjaxAsync;

			}

			//save
			function save_onclick()
			{
				// ��ȡ�г������Ľ���ʱ������	
				getMarket();
			  if(document.forms(0).tradeTimeType.value != ""){

				if ($("#gatherBid").val() == "1") {
					if ($("#bidStartTime").val().indexOf("��") != "-1") {
						alert("ʱ�䲻����������ð�ţ�");
						return false;
					}
					if (!isTime($("#bidStartTime").val())) {
						alert("���Ͼ��۽���ʱ���ʽ����ȷ��");
						$("#bidStartTime").focus();
						return false;
					}
					if ($("#bidEndTime").val().indexOf("��") != "-1") {
						alert("ʱ�䲻����������ð�ţ�");
						return false;
					}
					if (!isTime($("#bidEndTime").val())) {
						alert("���Ͼ��۽���ʱ���ʽ����ȷ��");
						$("#bidEndTime").focus();
						return false;
					}
				}
					if (document.forms(0).startTime.value.indexOf("��") != "-1") {
						alert("ʱ�䲻����������ð�ţ�");
						return false;
					}
					if (!isTime(document.forms(0).startTime.value)) {
						alert("���׿�ʼʱ���ʽ����ȷ��");
						document.forms(0).startTime.focus();
						return false;
					}
					
					if (document.forms(0).endTime.value.indexOf("��") != "-1") {
						alert("ʱ�䲻����������ð�ţ�");
						return false;
					}
					if (!isTime(document.forms(0).endTime.value)) {
						alert("���׽���ʱ���ʽ����ȷ��");
						document.forms(0).endTime.focus();
						return false;
					}
					
					if (document.forms(0).tradeTimeType.value == "0") {//ͬһ�콻��
						if (document.forms(0).gatherBid.value == "1" && document.forms(0).bidStartTime.value != "" && document.forms(0).bidEndTime.value != "") {
						var bidStartTimes = document.forms(0).bidStartTime.value.split(":");
						var startTimes = document.forms(0).startTime.value.split(":");
						var bidEndTimes = document.forms(0).bidEndTime.value.split(":");
						
						var dateBST = new Date(0,0,0,bidStartTimes[0],bidStartTimes[1],bidStartTimes[2]);
						var hourBS = dateBST.getHours();
						var minuteBS = dateBST.getMinutes();
						var secondBS = dateBST.getSeconds();
						var relDateBST = parseInt(hourBS)*3600 + parseInt(minuteBS)*60 + parseInt(secondBS);
						
						var dateST = new Date(0,0,0,startTimes[0],startTimes[1],startTimes[2]);
						var hourST = dateST.getHours();
						var minuteST = dateST.getMinutes();
						var secondST = dateST.getSeconds();
						var relDateST = parseInt(hourST)*3600 + parseInt(minuteST)*60 + parseInt(secondST);
						
						var dateBT = new Date(0,0,0,bidEndTimes[0],bidEndTimes[1],bidEndTimes[2]);
						var hourBT = dateBT.getHours();
						var minuteBT = dateBT.getMinutes();
						var secondBT = dateBT.getSeconds();
						var relDateBT = parseInt(hourBT)*3600 + parseInt(minuteBT)*60 + parseInt(secondBT);
						
						if (relDateBST > relDateST || relDateBST > relDateBT || relDateBST == relDateST || relDateBST == relDateBT) {
							alert("���Ͼ��ۿ�ʼʱ��Ӧ���ڽ��׿�ʼʱ��򼯺Ͼ��۽���ʱ�䣡");
							document.forms(0).bidStartTime.focus();
							return false;
						}
						if (relDateBT > relDateST || relDateBT == relDateST) {
							alert("���Ͼ��۽���ʱ��Ӧ���ڽ��׿�ʼʱ�䣡");
							document.forms(0).bidEndTime.focus();
							return false;
						}
						
						var endTimes = document.forms(0).endTime.value.split(":");
						var dateET = new Date(0,0,0,endTimes[0],endTimes[1],endTimes[2]);
						var hourET = dateET.getHours();
						var minuteET = dateET.getMinutes();
						var secondET = dateET.getSeconds();
						var relDateET = parseInt(hourET)*3600 + parseInt(minuteET)*60 + parseInt(secondET);
						if (relDateST > relDateET || relDateST == relDateET) {
							alert("���׿�ʼʱ��Ӧ���ڽ��׽���ʱ�䣡");
							document.forms(0).startTime.focus();
							return false;
						}
						}else if (document.forms(0).gatherBid.value == "0") {
							document.forms(0).bidStartTime.value = "";
							document.forms(0).bidEndTime.value = "";
						}
					}else {
						if (document.forms(0).gatherBid.value == "1") {
							if (document.forms(0).bidStartTime.value > document.forms(0).bidEndTime.value) {
								alert("���Ͼ��ۿ�ʼʱ��Ӧ���ڼ��Ͼ��۽���ʱ�䣡");
								return false;
							}
						}
					}

					return true;
			  }else{
				  alert("�����ڽ����г������У����ý���ʱ�����ͣ�");
				  return false;
			  }
					
			 }

			 function suffixNamePress()
			{
				
			  if (event.keyCode<=47 || event.keyCode>58)
			  {
			    event.returnValue=false;
			  }
			  else
			  {
			    event.returnValue=true;
			  }
			}

</script>
	</head>

	<body>
		<form id="frm" name="frm" method="post" action="${basePath }/timebargain/tradeparams/addTradeTime.action">
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :���׽����
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
												��ӽ��׽�
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
												<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														���׽ڱ�ţ�
													</td>
													<td>
													   <input type="text" id="sectionID" name="entity.sectionID"
															class="validate[required] input_text datepicker"/>
													</td>
													
													<td align="right">
														<span class="required">*</span>
														���׽����ƣ�
													</td>
													<td>
													    <input type="text" id="name" name="entity.name" 
															class="validate[required] input_text datepicker"/>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														��ǰ���׽�״̬��
													</td>
													<td>
														<select id="status" name="entity.status" class="validate[required]" style="width:120">
															  <option value=""></option>
									                          <option value="0">��Ч</option>
										                      <option value="1" selected="selected">����</option>
														</select>
													</td>
													
													<td align="right">
													    <span class="required">*</span>
														�Ƿ����ü��Ͼ��ۣ�
													</td>
													<td>
														<select id="gatherBid" name="entity.gatherBid" class="validate[required]"
														onchange="change()" style="width:120">
															  <option value=""></option>
									                          <option value="0">������</option>
										                      <option value="1">����</option>
														</select>
													</td>
												</tr>
												<tr id="bidTime">
													<td align="right">
														<span class="required">*</span>
														���Ͼ��ۿ�ʼʱ�䣺
													</td>
													<td>
														<input type="text" id="bidStartTime" name="entity.bidStartTime" 
															class="validate[required] input_text datepicker" onkeypress="return suffixNamePress()"/>
														<span class="required">&nbsp; HH:MM:SS</span>
													</td>
													<td align="right">
														<span class="required">*</span>
														���Ͼ��۽���ʱ�䣺
													</td>
													<td>
														<input type="text" id="bidEndTime" name="entity.bidEndTime" 
															class="validate[required] input_text datepicker" onkeypress="return suffixNamePress()"/>
															<span class="required">&nbsp; HH:MM:SS</span>
													</td>
												
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														��ǰ���׽ڿ�ʼʱ�䣺
													</td>
													<td>
														<input type="text" id="startTime" name="entity.startTime" 
															class="validate[required] input_text datepicker" onkeypress="return suffixNamePress()"/>
															<span class="required">&nbsp; HH:MM:SS</span>
													</td>
													
													<td align="right">
													    <span class="required">*</span>
														��ǰ���׽ڽ���ʱ�䣺
													</td>
													<td>
														<input type="text" id="endTime" name="entity.endTime" 
															class="validate[required] input_text datepicker" onkeypress="return suffixNamePress()"/>
															<span class="required">&nbsp; HH:MM:SS</span>
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
			<input type="hidden" id="tradeTimeType" name="tradeTimeType"  />
			<div class="tab_pad" style="bottom: 250">
				<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
					<tr>
						<td align="center">
							<rightButton:rightButton name="���" onclick="" className="btn_sec" action="/timebargain/tradeparams/addTradeTime.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">�ر�</button>
						</td>
					</tr>
				</table>
			</div>
		</form>

	</body>
</html>
