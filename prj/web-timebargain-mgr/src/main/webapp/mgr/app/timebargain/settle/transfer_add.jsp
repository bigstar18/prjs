<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>添加非交易过户</title>
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
				var customerid_s = document.getElementById("customerid_s").value; //获取过户人
				var bs_flag = $(":radio:checked").val(); //获取持仓方向 （买：1；卖：2）
				
				//先清空商品下拉框
				document.getElementById("commodityId").length = 1;
				//将数量清0
				document.getElementById("quantity").value = 0;
				
				if(customerid_s!="") {
					var url = "${basePath}/ajaxcheck/transfer/searchCommodity.action?customerid_s="+customerid_s+"&bs_flag="+bs_flag;
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
				var customerid_s = document.getElementById("customerid_s").value; 		//获取过户人
				var bs_flag = $(":radio:checked").val(); 						//获取持仓方向 （买：1；卖：2）
				var commodityid = document.getElementById("commodityID").value; //获取商品
				var type = document.getElementById("type").value; 				//获取过户类型(0:整体移仓；1：指定数量)
				
				//先将数量清0
				document.getElementById("quantity").value = 0;
				
				if(commodityid!=""&&type!="") {
					var time = new Date().getTime();
					var url = "${basePath}/ajaxcheck/transfer/searchQuantity.action?commodityid="+commodityid+"&type="+type+"&customerid_s="+customerid_s+"&bs_flag="+bs_flag+"&date="+time;
					$.ajaxSettings.async = false;
					$.getJSON(url,null,function(result){
						if(result!=null&&result!=""){
							document.getElementById("quantity").value = result;
							document.getElementById("quantity").readOnly = true;
						}
					});
					$.ajaxSettings.async = oldAjaxAsync;
				}
				if(type!=""&&type==0){
					document.getElementById("quantity").readOnly = true;
				}else{
					document.getElementById("quantity").readOnly = false;
				}
			}
			
			function holdCheck(){
				var oldAjaxAsync = $.ajaxSettings.async;
				var value = 0; //返回值(1：验证通过；-1：持仓数量不足；0：)
				var holdQty = 0;
				
				var customerid_s = document.getElementById("customerid_s").value; 		//获取过户人
				var bs_flag = $(":radio:checked").val(); 						//获取持仓方向 （买：1；卖：2）
				var commodityid = document.getElementById("commodityID").value; //获取商品
				var quantity = document.getElementById("quantity").value;		//过户数量
				if(quantity>0) {
					var url = "${basePath}/ajaxcheck/transfer/holdCheck.action?commodityid="+commodityid+"&customerid_s="+customerid_s+"&bs_flag="+bs_flag;
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
						var vaild = window.confirm("您确定要操作吗？");
						if(vaild){
							var a = holdCheck();
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
									温馨提示 :非交易过户信息添加
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
													<td align="right" width="20%">
														<span class="required">*</span>
														过户人二级代码：
													</td>
													<td>
														<input type="text" id="customerID_s" name="entity.customerID_s"  onchange="getCommodity()" class="validate[required] input_text datepicker" />
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
													<td align="right">
														<span class="required">*</span>
														持仓方向：
													</td>
										            <td>
														<input id="bs_flag1" type="radio" name="entity.bs_flag" value="1" onchange="getCommodity();" checked="checked"  style="border:0px;">买
										            	<input id="bs_flag2" type="radio" name="entity.bs_flag" value="2" onchange="getCommodity();" style="border:0px;">卖
										            </td>
												</tr>
												
												<tr>
													<td align="right">
														<span class="required">*</span>
														商品代码：
													</td>
													<td>
														<select id="commodityID" name="entity.commodityID" onchange="getQuantity()" style="width: 120" class="validate[required]">
														<option value="" selected="selected">请选择</option>
														</select>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
										            <td align="right">
														<span class="required">*</span>
														过户类型：
													</td>
													<td>
														<select id="type" name="entity.type" onchange="getQuantity()" style="width: 120" class="validate[required]">
														<option value="" selected="selected">请选择</option>
														<c:forEach items="${Transfer_typeMap}" var="map" varStatus="status">
															<option value="${map.key}">${map.value}</option>
														</c:forEach>
														</select>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												
												<tr>
													<td align="right">
													         数量：
												    </td>
												 	<td width="115">
														<input type="text" size="10" id="quantity" name="entity.quantity" value="${entity.quantity}"
															style="width:120" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="10" class="input_text datepicker,validate[required]" />
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
													
													<td align="right">
														<span class="required">*</span>
														接受人二级代码：
													</td>
													<td>
														<input type="text" id="customerID_b" name="entity.customerID_b" class="validate[required] input_text datepicker"/>
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
							<rightButton:rightButton name="添加" onclick="" className="btn_sec" action="/timebargain/transfer/addTransfer.action" id="add"></rightButton:rightButton>
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