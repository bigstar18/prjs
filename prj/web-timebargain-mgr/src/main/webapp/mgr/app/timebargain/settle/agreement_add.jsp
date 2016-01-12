<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>客户信息添加</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<script type="text/javascript">
			function getCommodity(){
				var oldAjaxAsync = $.ajaxSettings.async;
				var firmid = document.getElementById("firmId_s").value; //获取过户人
				var bs_flag = $(":radio:checked").val(); //获取持仓方向 （买：1；卖：2）
				
				//先清空商品下拉框
				document.getElementById("commodityId").length = 1;
				//将数量清0
				document.getElementById("quantity").value = 0;
				
				if(firmid!="") {
					var url = "${basePath}/ajaxcheck/transfer/searchCommodity.action?firmid="+firmid+"&bs_flag="+bs_flag;
					$.ajaxSettings.async = false;
					$.getJSON(url,null,function(result){
						for(var i=0;i<result.length;i++){
							//加入商品下拉框
							if(result[i]!="" && result[i]!=null){
								document.getElementById("commodityId").add(new Option(result[i],result[i]));
							}
						}
					});
					$.ajaxSettings.async = oldAjaxAsync;
				}
			}
			
			function getQuantity(){
				var oldAjaxAsync = $.ajaxSettings.async;
				var firmid = document.getElementById("firmId_s").value; 		//获取过户人
				var bs_flag = $(":radio:checked").val(); 						//获取持仓方向 （买：1；卖：2）
				var commodityid = document.getElementById("commodityID").value; //获取商品
				var type = document.getElementById("type").value; 				//获取过户类型(0:整体移仓；1：指定数量)
				
				//先将数量清0
				document.getElementById("quantity").value = 0;
				
				if(commodityid!=""&&type!="") {
					var url = "${basePath}/ajaxcheck/transfer/searchQuantity.action?commodityid="+commodityid+"&type="+type+"&firmid="+firmid+"&bs_flag="+bs_flag;
					$.ajaxSettings.async = false;
					$.getJSON(url,null,function(result){
						if(result!=null&&result!=""){
							document.getElementById("quantity").value = result
						}
					});
					$.ajaxSettings.async = oldAjaxAsync;
				}
				if(type!=""&&type==0){
					document.getElementById("quantity").disabled = true;
				}else{
					document.getElementById("quantity").disabled = false;
				}
			}
			
			function holdCheck(){
				var oldAjaxAsync = $.ajaxSettings.async;
				var value = 0; //返回值(1：验证通过；-1：持仓数量不足；0：)
				var holdQty = 0;
				
				var firmid = document.getElementById("firmId_s").value; 		//获取过户人
				var bs_flag = $(":radio:checked").val(); 						//获取持仓方向 （买：1；卖：2）
				var commodityid = document.getElementById("commodityID").value; //获取商品
				var quantity = document.getElementById("quantity").value;		//过户数量
				if(quantity>0) {
					var url = "${basePath}/ajaxcheck/transfer/holdCheck.action?commodityid="+commodityid+"&firmid="+firmid+"&bs_flag="+bs_flag;
					$.ajaxSettings.async = false;
					$.getJSON(url,null,function(result){
						if(result!=null&&result!=""){
							var holdQty = result;
							if(Number(holdQty)>=Number(quantity)){
								value = 1;
							} else {
							    value = -1;//持仓数量不足
							}
						}
					});
					$.ajaxSettings.async = oldAjaxAsync;
				}
				return value;
			}
			
			
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//修改按钮注册点击事件
				$("#add").click(function(){
					//验证信息
					if(jQuery("#frm").validationEngine('validate')){
						var vaild = affirm("您确定要操作吗？");
						if(vaild){
							var a = 1;
							if(a==1){
								//添加信息URL
								var addDemoUrl = $("#add").attr("action");
								//全 URL 路径
								var url = "${basePath}"+addDemoUrl;
								$("#frm").attr("action",url);
								frm.submit();
							}else if(a==-1){
								document.getElementById("quantity").value = 0;
							    alert("持仓数量不足，添加失败！");
							}else if(a==0){
								alert("过户数量必须大于0！")
							}else{
								alert("添加失败，请与管理员联系！")
							}
						}
					}
				});
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
									温馨提示 :协议交收信息添加
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
												添加过户信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														商品代码：
													</td>
													<td>
														<input type="text" id="commodityID" name="entity.commodityID" class="validate[required,maxSize[10]] input_text datepicker" />
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														买二级代码：
													</td>
													<td>
														<input type="text" id="customerID_B" name="entity.customerID_B" class="validate[required] input_text datepicker" />
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
										            <td align="right">
														<span class="required">*</span>
														卖二级代码：
													</td>
													<td>
														<input type="text" id="customerID_S" name="entity.customerID_S" class="validate[required] input_text datepicker" />
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												
												<tr>
													<td align="right" width="100">
													         转让价：
												    </td>
												 	<td width="115">
														<input type="text" size="10" id="price" name="entity.price" value="${entity.quantity}"
															style="width:120" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="10" class="input_text datepicker,validate[required]" />
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
													
													<td align="right">
														<span class="required">*</span>
														转让数量：
													</td>
													<td>
														<input type="text" size="10" id="quantity" name="entity.quantity" value="${entity.quantity}"
															style="width:120" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="10" class="input_text datepicker,validate[required]" />
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
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
							<rightButton:rightButton name="提交" onclick="" className="btn_sec" action="/timebargain/agreementSettle/addAgreementSettle.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">关闭</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>