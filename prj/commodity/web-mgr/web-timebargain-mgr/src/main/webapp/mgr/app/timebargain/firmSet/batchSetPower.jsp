<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
	<head>
		<title>批量添加交易商权限</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
		<script src="${basePath }/mgr/app/timebargain/js/tool.js" type="text/javascript" charset="GBK"></script>
		
		<script type="text/javascript">
	
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				// 添加按钮点击事件
				$("#add").click(function(){

			      var flag = onSave();
			      if(flag){
				      
					// 验证信息
					if(jQuery("#frm").validationEngine('validate')){
						
						var vaild = affirm("您确定要操作吗？");
						if(vaild){
							// 添加信息URL
							var addUrl = $(this).attr("action");
							// 全 URL 路径
							var url = "${basePath}"+addUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
					}
			      }
				});

				// 删除按钮点击事件
				$("#delete").click(function(){

			      var flag = onSave();
			      if(flag){
				      
					// 验证信息
					if(jQuery("#frm").validationEngine('validate')){
						
						var vaild = affirm("您确定要操作吗？");
						if(vaild){
							// 添加信息URL
							var addUrl = $(this).attr("action");
							// 全 URL 路径
							var url = "${basePath}"+addUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
					}
			      }
				});
			});
			
		</script>
		
	    <script type="text/javascript">

	      function onSave(){
	    	  if(frm.typeID.value == "" && frm.startFirm.value == "" && frm.endFirm.value == ""){
					frm.typeID.focus();
					alert("请输入交易商代码！");
					return false;
				}

              if(frm.typeID.value != ""){
              	$("#typeID").addClass("validate[required]");
              }else{
              	$("#typeID").removeClass("validate[required]");	
              }
				
			    if(frm.startFirm.value != "" || frm.endFirm.value != ""){
			    	$("#startFirm").removeClass("validate[maxSize[10],custom[onlyNumberSp]] input_text");	
          	    $("#startFirm").addClass("validate[required,maxSize[10],custom[onlyNumberSp]] input_text");
          	    $("#endFirm").removeClass("validate[maxSize[10],custom[onlyNumberSp]] input_text");	
          	    $("#endFirm").addClass("validate[required,maxSize[10],custom[onlyNumberSp]] input_text");
              }else{
          	    $("#startFirm").removeClass("validate[required,maxSize[10],custom[onlyNumberSp]] input_text");	
			        $("#startFirm").addClass("validate[maxSize[10],custom[onlyNumberSp]] input_text");
			        $("#endFirm").removeClass("validate[required,maxSize[10],custom[onlyNumberSp]] input_text");	
			        $("#endFirm").addClass("validate[maxSize[10],custom[onlyNumberSp]] input_text");
              }

			    // 结束值大于开始值
              if(frm.startFirm.value != "" && frm.endFirm.value != "" && frm.startFirm.value >= frm.endFirm.value){
              	frm.endFirm.focus();
                  alert("结束值必须大于开始值！");
                  return false;
              }

              var kind = document.getElementById("kind").value;
				var breedID = document.getElementById("breedID").value;
				var commodityID = document.getElementById("commodityID").value;
				if(kind == "1" && breedID != ""){                   
              	document.getElementById("kindID").value = breedID;
				}
              else if(kind == "2" && commodityID != "" ){
              	document.getElementById("kindID").value = commodityID;
              }

	         return true;
	      }
	    
		  function kind_click(){
			 var kind = document.getElementById("kind").value;
			 
			 if (kind == "") {				
				 $("#commodity").hide();
				 $("#breed").hide();			
		     }
			 else if (kind == "1") {
				 $("#commodity").hide();
				 $("#breed").show();
			
			 }
			 else if (kind == "2") {			  
				 $("#commodity").show();
				 $("#breed").hide();
			 }	
		  }
		  
		  function window_onload(){
			  kind_click();
		  }

		  function myReset(){
			  frm.typeID.value="";
			  frm.startFirm.value="";
			  frm.endFirm.value="";
			  frm.kind.value="";
			  frm.breedID.value="";
			  frm.commodityID.value="";
			  frm.privilegeCode_B.value="";
			  frm.privilegeCode_S.value="";

			  }
		    
	    </script>
	</head>
	<body onload="window_onload()">
		<form id="frm" name="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<input type="hidden" name="entity.type" value="1"/>
			
			
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :批量添加交易商权限
									<br/>
									<span class="required">注：代码值的输入格式支持两种情况，例如，情况一：5,7,9(表示编号5、编号7和编号9) 情况二：1001-1005(表示编号1001到1005区间内编号)</span>
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
												交易商权限维护
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="10" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right" >
														交易商代码：
													</td>
													<td>
													  <textarea id="typeID" name="typeID" style="width: 300px;" onkeypress="notSpace()"  rows="5" cols="10"></textarea>
													</td>
													
												</tr>
												<tr>
													<td align="right">
														开始交易商：
														 
													</td>
													
													<td>
													    <input type="text" id="startFirm" name="startFirm" style="width: 100px;" class="validate[maxSize[10],custom[onlyNumberSp]] input_text" onkeypress="onlyNumberInput()"/>
														&nbsp;&nbsp;结束交易商： 
														<input type="text" id="endFirm" name="endFirm" style="width: 100px;" class="validate[maxSize[10],custom[onlyNumberSp]] input_text" onkeypress="onlyNumberInput()"/>
													</td>
													
													
												</tr>
												<tr>
													<td align="right">
														权限种类：
													</td>
													<td>
														<select id="kind" name="entity.kind" class="validate[required]" onchange="kind_click()">
														  <option value="">请选择</option>
														  <c:forEach items="${tradePrivilege_kindMap}" var="map" >
															<option value="${map.key}">${map.value}</option>
														  </c:forEach>
														</select>
														<span class="required">*</span>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												<tr id="breed">
													<td align="right">
														上市品种：
													</td>
													<td>
														<select id="breedID"  class="validate[required]">
														  <option value="">请选择</option>
														  <c:forEach items="${breedList}" var="result" >
															<option value="${result.BREEDID}">${result.BREEDNAME}</option>
														  </c:forEach>
														</select>
														<span class="required">*</span>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												
												<tr id="commodity">
													<td align="right">
														上市商品：
													</td>
													<td>
														<select id="commodityID"  class="validate[required]">
														  <option value="">请选择</option>
														  <c:forEach items="${commodityList}" var="map" >
															<option value="${map['COMMODITYID']}">${map['NAME']}</option>
														  </c:forEach>
														</select>
														<span class="required">*</span>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												<input type="hidden" id="kindID" name="entity.kindID" />
												
												<tr>
													<td align="right">
														买方权限：
													</td>
													<td>
														<select id="privilegeCode_B" name="entity.privilegeCode_B" class="validate[required]">
														  <option value="">请选择</option>
														  <option value="101">全权</option>
														  <option value="102">只可订立</option>
														  <option value="103">只可转让</option>
														  <option value="104">无权</option>
														</select>
														<span class="required">*</span>
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														卖方权限：
													</td>
													<td>
														<select id="privilegeCode_S" name="entity.privilegeCode_S" class="validate[required]">
														  <option value="">请选择</option>
														  <option value="201">全权</option>
														  <option value="202">只可订立</option>
														  <option value="203">只可转让</option>
														  <option value="204">无权</option>
														</select>
														<span class="required">*</span>
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
				<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
					<tr>
						<td align="center">
							<rightButton:rightButton name="批量添加" onclick="" className="btn_sec" action="/timebargain/firmSet/tradePrivilege/batchSetSaveFirmPrivilege.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="批量清空" onclick="" className="btn_sec" action="/timebargain/firmSet/tradePrivilege/batchSetClearFirmPrivilege.action" id="delete"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_cz" onclick="myReset();">重置</button>
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