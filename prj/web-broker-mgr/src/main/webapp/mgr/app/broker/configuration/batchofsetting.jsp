<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>特殊佣金批量设置</title>
		<link rel="stylesheet"
			href="${skinPath }/css/validationengine/validationEngine.jquery.css"
			type="text/css" />
		<link rel="stylesheet"
			href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script type="text/javascript"
			src="<c:url value="/mgr/public/js/global.js"/>">
</script>
		<script src="${publicPath }/js/jquery-1.6.min.js"
			type="text/javascript">
</script>
		<script
			src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js"
			type="text/javascript" charset="GBK">
</script>
		<script
			src="${publicPath }/js/validationengine/jquery.validationEngine.js"
			type="text/javascript" charset="GBK">
</script>

		<SCRIPT type="text/javascript">
		<!-- 
		    $(document).ready(function() {
			   
		    	jQuery("#frm").validationEngine('attach');
				//修改按钮注册点击事件
				$("#add").click(function(){
					//验证信息
					var moduleId = document.getElementById("moduleId").value;
					var rewardType = document.getElementById("rewardType").value;
					var commodityId = document.getElementById("commodityId").value;
					if (frm.lbRight.length == 0) {
					alert("添加佣金设置的加盟商不能为空！");
					return false;
					}else{
					var brokerids = "";
					for(var t=0;t<frm.lbRight.length;t++){						
						brokerids+=frm.lbRight.options[t].value+",";
					}
					if(brokerids != "")
				    {
				      brokerids =  brokerids.substr(0, brokerids.length - 1) ;
				    }
					frm.brokerids.value=brokerids;
					
					}
	
					if(jQuery("#frm").validationEngine('validateform')){
				
						var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var updateDemoUrl = $(this).attr("action");
							//全 URL 路径
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							
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
		<form id="frm" method="post" enctype="multipart/form-data" action=""
			targetType="hidden">
			<div class="div_cx">
				<table border="0" width="800" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :批量修改特殊佣金参数(若已设置，则会覆盖掉已设置参数)
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
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<tr align="center" valign="top">
													<td align="center" class="common" width="30%">
														<table border="0" class="common" width="200">
															<tr>
																<td align="center">
																	未用
																</td>
															</tr>
															<tr>
																<td>
																	<select name="lbLeft" size="16"
																		onDblClick="moveSelected(lbLeft,lbRight);"
																		style="width: 100%" multiple>
																		<%
																			List list = (List) request.getAttribute("brokerList");
																			if (list != null && list.size() > 0) {
																				for (int i = 0; i < list.size(); i++) {
																					Map map = (Map) list.get(i);
																					String BROKERID = map.get("BROKERID") + "";
																		%>
																		<option value="<%=BROKERID%>"><%=BROKERID%></option>
																		<%
																			}
																			}
																		%>
																	</select>
																</td>
															</tr>
														</table>
													</td>
													<td align="center" valign="center" width="15%">

														<table border="0" class="common" height="140" width="100">
															<tr>
																<td align="center" valign="bottom">
																	<input type="button" name="lbAdd" value="  >   "
																		onclick="moveSelected(lbLeft,lbRight);" class="button">
																</td>
															</tr>
															<tr>
																<td align="center" valign="top">
																	<input type="button" name="lbAddAll" value="  >>  "
																		onclick="return moveSelectedAll(lbLeft,lbRight);"
																		class="button">
																</td>
															</tr>
															<tr>
																<td align="center" valign="bottom">
																	<input type="button" name="lbDel" value="  <   "
																		onclick="moveSelected(lbRight,lbLeft);" class="button">
																</td>
															</tr>
															<tr>
																<td align="center" valign="top">
																	<input type="button" name="lbDelAll" value="  <<  "
																		onclick="moveSelectedAll(lbRight,lbLeft);"
																		class="button">
																</td>
															</tr>
														</table>
													</td>
													<td align="center" class="common" width="30%">
														<table border="0" class="common" width="200">
															<tr>
																<td class="common" align="center">
																	已用
																</td>
															</tr>
															<tr>
																<td>
																	<select name="lbRight" size="16"
																		onDblClick="moveSelected(lbRight,lbLeft);"
																		style="width: 100%" multiple
																		onchange="checkBrokerid();">

																	</select>
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td align="right">
														模块：
													</td>
													<td>
														<select id="moduleId" name="moduleId"
															class="validate[required]" style="width: 120">
															<%--
															<option value=""></option>
															<option value="15"
																<c:if test="${entity.moduleId == 15}">selected</c:if>>
																${request.shortName }
															</option>
															 --%>
															 
															  <option value=""></option>
																 <c:forEach items="${brTradeModule }" var="d">
																 
																		<option value="${d.moduleId }" >${d.cnName }</option>
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
														<select id="commodityId" name="commodityId" class="validate[required]" style="width:120">
									                		<option value=""></option>
									                		<c:forEach items="${list}" var="list">
									                			<option value="${list.COMMODITYID }" <c:if test="${entity.commodityId == list.COMMODITYID}">selected</c:if>>${list.NAME }</option>
									                		</c:forEach>
														</select>
														<span class="required">*</span>
													</td>
												</tr>
												<tr>
													<td align="right">
														手续费佣金比例：
													</td>
													<td>
														<input id="rewardRate" name="rewardRate" maxlength="11"
															value="${entity.rewardRate}"
															class="validate[required,custom[double]] input_text"
															style="width: 120" />
														<span class="required">%&nbsp;*</span>
													</td>
												</tr>
												<tr>
													<td align="right">
														提成首付比例：
													</td>
													<td>
														<input id="firstPayRate" name="firstPayRate"
															maxlength="11" value="${entity.firstPayRate}"
															class="validate[required,custom[double]] input_text"
															style="width: 120" onblur="vali_FirstPayRate();" />
														<span class="required">%&nbsp;*</span>
													</td>
												</tr>
												<tr>
													<td align="right">
														提成尾款比例：
													</td>
													<td>
														<input id="secondPayRate" name="secondPayRate"
															maxlength="11" value="${entity.secondPayRate}"
															class="validate[required,custom[double]] input_text"
															style="width: 120" onblur="vali_SecondPayRate();" />
														<span class="required">%&nbsp;*</span>
													</td>

												</tr>
												<input type="hidden" id="commit_flag" value="" />
												<tr>
													<td colspan="3" align="center">
														<c:if test="${crud == 'create'}">
															<rightButton:rightButton name="添加" onclick=""
																className="btn_sec"
																action="/config/special/add.action?flag=batch" id="add"></rightButton:rightButton>
														</c:if>
														<c:if test="${crud == 'update'}">
															<rightButton:rightButton name="提交" onclick=""
																className="btn_sec"
																action="/config/special/update.action?flag=batch"
																id="update"></rightButton:rightButton>
														</c:if>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<rightButton:rightButton name="返回" onclick=""
															className="btn_sec"
															action="/config/special/specialParamList.action"
															id="back"></rightButton:rightButton>
														<input type="hidden" name="brokerids" value="" />
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