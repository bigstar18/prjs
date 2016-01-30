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
	<link rel="stylesheet"
		href="${skinPath }/css/autocomplete/jquery.autocomplete.css"
		type="text/css" />
	<script type='text/javascript'
		src='${publicPath }/js/autocomplete/jquery.autocomplete.js'></script>
	<script
		src="${basePath }/mgr/app/integrated/js/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
	
</script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK"></script>
	<script>
	function checkMoney(){
		var money=document.getElementById("money").value;
		if(money<=0){
			return "*����Ϊ������0";
		}
		if(!flote(money,2)){
			return "*���С������Ϊ2λ";
			}
		if(intByNum(money,10)){
			return "*��������������Ϊ10λ";
		}
	}
	var str="";
	jQuery(document).ready(function() {
		<c:if test="${not empty json}">
		var cities =${json};
		$("#firmId").change().autocomplete(cities);
		str=cities+"";
		</c:if>
		$("#frm").validationEngine('attach');

		$("#update").click(function(check) {
			if ($("#frm").validationEngine('validate')) {
					var flag=flagFirm();
					if(flag){
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							$("#frm").validationEngine('detach');
							//$('#frm').attr('action', 'action');
						    $("#frm").submit();
						}
					}else{
						alert("�����̲�����");
						}
					
		}})
		});
	function flagFirm(){
		var firmId=document.getElementById("firmId").value;
		var flag=false;
		if(firmId!=""&&str!=""){
			var strs=str.split(',');
			for(var i=0;i<strs.length;i++){
				if(firmId==strs[i]){
					flag=true;
					}
			}
	}
		return flag;
	}
	function checkFirmId(){
		var flag=flagFirm();
			if(!flag){
				return "�����̴��벻����";
				}
		}
</script>
	<head>
		<title>ģ�����</title>
	</head>
	<body style="overflow-y: hidden">
		<form id="frm" method="post"
			action="${basePath}/trade/trader/inmoney/inMoneyModel.action">
			<div class="div_cx">
				<table border="0" width="50%" align="left" class="table_left_style">
					<tr>
						<td>
							<table border="0" width="80%" align="left">
								<tr>
									<td>
										<div class="warning">
											<div class="content">
												��ܰ��ʾ :ģ�����
											</div>
										</div>
									</td>
								</tr>
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
										<div  class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" height="70px" align="center" class="table2_style">
												<tr>
													<td align="right" width="40%">
														<span class="required">*</span>�����̴��� ��
													</td>
													<td width="*%">
														<input id="firmId" name="firmId"
															class="validate[required,maxSize[<fmt:message key='firmId' bundle='${PropsFieldLength}'/>]] input_text  datepicker" value=""/>
													</td>
												</tr>
												<tr>
													<td align="right" width="25%">
														<span class="required">*</span>����� ��
													</td>
													<td>
														<input id="money" maxlength="15" name="money"
															class="validate[required,funcCall[checkMoney],custom[number],min[0.01]] input_text" value=""/>
														Ԫ
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
			<table border="0" cellspacing="0" cellpadding="4" width="600px"
				align="left">
					<tr>
						<td align="right">
							<rightButton:rightButton name="���" onclick="" className="btn_sec"
								action="/trade/trader/inmoney/forwardInMoney.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
		</form>

	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>