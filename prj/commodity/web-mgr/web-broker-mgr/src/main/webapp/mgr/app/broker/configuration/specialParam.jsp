<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>特殊佣金设置</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<SCRIPT type="text/javascript">
		<!-- 

		    $(document).ready(function() {
		    
		    	 
			    if ("${crud}" == "update") {
			    	$("#moduleId").attr("readonly", true).css("background-color", "#C0C0C0");
			    	$("#commodityId").attr("readonly", true).css("background-color", "#C0C0C0");
			    	$("#rewardType").attr("readonly", true).css("background-color", "#C0C0C0");
			    	$("#brokerId").attr("readonly", true).css("background-color", "#C0C0C0");
			    	
			    	$("#moduleId").empty();
			    	$("#moduleId").append("<option value='"+'${entity.moduleId}'+"'>"+'${moduleName}'+"</option>");
			    	
			    	$("#commodityId").empty();
			    	$("#commodityId").append("<option value='"+'${entity.commodityId}'+"'>"+'${commodityName}'+"</option>");
				}
		    	jQuery("#frm").validationEngine('attach');
				//修改按钮注册点击事件
				$("#add").click(function(){
					//验证信息
					if(jQuery("#frm").validationEngine('validateform')){
						var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var updateDemoUrl = $(this).attr("action");
							//全 URL 路径
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							$(this).attr("disabled",true);
						}
					}
				});
				$("#update").click(function(){
					//验证信息
					if(jQuery("#frm").validationEngine('validateform')){
						var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var updateDemoUrl = $(this).attr("action");
							//全 URL 路径
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							$(this).attr("disabled",true);
						}
					}
				});
				$("#back").click(function(){
					//添加信息URL
					var updateDemoUrl = $(this).attr("action");
					//全 URL 路径
					var url = "${basePath}"+updateDemoUrl;
					document.location.href = url;
				});
				$("#brokerId").change(function(){
					var brokerId = document.getElementById("brokerId").value;
					var moduleId = document.getElementById("moduleId").value;
					var rewardType = document.getElementById("rewardType").value;
					var commodityId = document.getElementById("commodityId").value;
					if (!(brokerId == "" || moduleId == "" || rewardType == "" || commodityId == "")) {
						$.ajax({
							type: "post",
							url: "../../ajaxcheck/config/checkBroker.action",
							data: {
								brokerId: brokerId,
								moduleId: moduleId,
								rewardType: rewardType,
								commodityId: commodityId
								  },
							success : function(data){
										if(data==false){
										  alert("此加盟商不合法，请修改");
										  $("#brokerId").val("");
										  $("#brokerId").focus();
										}
							          }
						});
					}
				});
				$("#moduleId").change(function(){
					if ("${crud}" != "update") {
						var moduleId = $(this).val();
						$("#commodityId option:gt(0)").remove(); 
						if(moduleId != null && moduleId != ""){
							$.ajax({
								type: "post",
								url: "../../ajaxcheck/broker/getCMDByModuleID.action",
								data: {
									moduleId: moduleId
									  },
								success : function(data){
											var cmd = data[0];
											if(cmd != null && cmd.length>0){
												for(var i=0;i<cmd.length;i++){
													var cmdid = cmd[i].COMMODITYID;
													var name = cmd[i].NAME;
													$("#commodityId").append("<option value='"+cmdid+"'>"+name+"</option>"); 
												}
											}
								          }
							});
						}
					}
				});
			});

		//-->
		function piLiangSZ(){
		if(frm.moduleId.value == ""){
			alert("请先选择模块！");
		}else{
			window.location.href="${basePath}/config/special/skip.action?moduleId="+frm.moduleId.value;
		}
	}
	function vali_FirstPayRate(){
		var firstPayRate = parseFloat($("#firstPayRate").val());
				
				if(firstPayRate > 100){
					alert("两次提成比例不能大于100%");
					$("#secondPayRate").attr("value",0);
					$("#firstPayRate").attr("value",0);
				}
				else if(firstPayRate != 0){
				$("#secondPayRate").attr("value",100-firstPayRate);
				}
		}
	
		function vali_SecondPayRate(){
		var firstPayRate = parseFloat($("#firstPayRate").val());
		var secondPayRate = parseFloat($("#secondPayRate").val());
				if(firstPayRate + secondPayRate > 100){
					alert("两次提成比例不能大于100%");
					$("#secondPayRate").attr("value",0);
					$("#firstPayRate").attr("value",0);		
				}else if(firstPayRate + secondPayRate < 100 && firstPayRate != 0 ){
					alert("两次提成比例不能小于100%");
					$("#secondPayRate").attr("value",0);
					$("#firstPayRate").attr("value",0);	
				}else if(firstPayRate == 0){
					$("#firstPayRate").attr("value",100-secondPayRate);	
				}
		}	
		</SCRIPT>
	</head>
	<body>
		<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<div class="div_cx">
				<table border="0" width="800" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :修改特殊佣金参数
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
												特殊佣金参数设置
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">				
														模块：
													</td>
													<td>
													<%--
														<select id="moduleId" name="entity.moduleId" class="validate[required]" style="width:120">
									                			<option value=""></option>
																<option value="15" <c:if test="${entity.moduleId == 15}">selected</c:if>>${request.shortName }</option>
														</select>
														
													--%>
													 
													
													 
													  <select id="moduleId" name="entity.moduleId"  class="normal" style="width: 120px">
																<%--
																	<option value="">全部</option>
																	<option value="15" <c:if test="${entity.moduleId == 15}">selected</c:if>>${request.shortName }</option>
																--%>		
																<option value=""></option>
																 <c:forEach items="${brTradeModule }" var="d">
																		<option value="${d.moduleId }" <c:if test="${entity.moduleId == d.moduleId}">selected</c:if>>${d.cnName }</option>
																</c:forEach>								
													</select>
													 
														<span class="required">*</span>
													</td>
												</tr>
												
												<input type="hidden" id="rewardType" name="entity.rewardType" value="0" />
												<tr>
													<td align="right">	
														商品：
													</td>
													<td>
												  
														<select id="commodityId" name="entity.commodityId" class="validate[required]" style="width:120">
									                		<option value=""></option>
														</select>
													 
														<span class="required">*</span>
													</td>
												</tr>
												<tr>
													<td align="right">	
														加盟商编号：
													</td>
													<td>
														<input id="brokerId" name="entity.brokerId" maxlength="16" value="${entity.brokerId }"
															class="validate[required] input_text" style="width:120" />
														<span class="required">*</span>
														<c:if test="${crud == 'create'}"><a href="${basePath}/config/special/addforward.action?flag=batch">（批量设置）</a>
														</c:if>
													</td>
												</tr>
												<tr>
													<td align="right">		
														手续费佣金比例：
													</td>
													<td>
														<input id="rewardRate" name="entity.rewardRateTemp" maxlength="11" value="${entity.rewardRateTemp }"
															class="validate[required,custom[double]] input_text" style="width:120" />
														<span class="required">%&nbsp;*</span>
													</td>
												</tr>
												<tr>	
													<td align="right">	
														提成首付比例：
													</td>
													<td>
													    <input id="firstPayRate" name="entity.firstPayRateTemp" maxlength="11" value="${entity.firstPayRateTemp }"
															class="validate[required,custom[double]] input_text" style="width:120" onblur="vali_FirstPayRate();" />
														<span class="required">%&nbsp;*</span>
													</td>
												</tr>
												<tr>
													<td align="right">	
														提成尾款比例：
													</td>
													<td>
														<input id="secondPayRate" name="entity.secondPayRateTemp" maxlength="11" value="${entity.secondPayRateTemp }"
															class="validate[required,custom[double]] input_text" style="width:120" onblur="vali_SecondPayRate();" />
														<span class="required">%&nbsp;*</span>
													</td>
													
												</tr>
												
												<tr>
													<td colspan="2" align="center">    
													<c:if test="${crud == 'create'}">
														<rightButton:rightButton name="添加" onclick="" className="btn_sec" action="/config/special/add.action?flag=common" id="add"></rightButton:rightButton>
													</c:if>                                              
													<c:if test="${crud == 'update'}">
														<rightButton:rightButton name="提交" onclick="" className="btn_sec" action="/config/special/update.action?flag=common" id="update"></rightButton:rightButton>
													</c:if>	
														&nbsp;&nbsp;
														<rightButton:rightButton name="返回" onclick="" className="btn_sec" action="/config/special/specialParamList.action" id="back"></rightButton:rightButton>
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