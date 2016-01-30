<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
	<head>
		<title>添加交易客户</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//添加按钮注册点击事件
				$("#add").click(function(){
					//验证信息
					if(jQuery("#frm").validationEngine('validate')){
						
					  if(onSave()){
						  
						var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var updateUrl = $(this).attr("action");
							//全 URL 路径
							var url = "${basePath}"+updateUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
						
					  }
					}
				});
			});

			function onSave(){
				var code = document.getElementById('code').value;
				var startCode = document.getElementById('startCode').value;
				var endCode = document.getElementById('endCode').value;

				if(code == "" && startCode == "" && endCode == ""){

					alert("二级客户代码不能为空！");
					code = document.getElementById('code').focus();
					return false;
				}else if(startCode == "" && endCode != "" || startCode != "" && endCode == ""){
					alert("二级客户代码起始或结束为空！");
					return false;
				}
				else if(startCode > endCode ){
					alert("起始代码不能大于结束代码");
					return false;
				}

				return true;
			}
		</script>
		
	    <script type="text/javascript">
	      //仅输入数字和逗号
	      function suffixNamePress()
	      {
	        if (event.keyCode == 44 || event.keyCode == 13 || (event.keyCode>=48 && event.keyCode<=57) ) 
	        {
	          event.returnValue=true;
	        }
	        else
	        {
	          event.returnValue=false;
	        }
	      }
	      
	      //仅输入数字
		  function onlyNumberInput() {
		  	if (event.keyCode>=48 && event.keyCode<=57) {
		  		event.returnValue=true;
		  	} else {
		  		event.returnValue=false;
		  	}
		  }
	    
	    </script>
	</head>
	<body >
		<form id="frm" method="post" action="" targetType="hidden">
			<input type="hidden" name="firmID" value="${firmID}"/>
			
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :添加交易客户
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
												二级客户代码维护
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="10" width="100%" align="center" class="table2_style">
												
												<tr>
													<td align="right">
													<span class="required">*</span>
														状态：
													</td>
													<td>
														<select id="status" name="status" class="validate[required] input_text" >
														  <option value="">请选择</option>
														    <c:forEach items="${firm_statusMap}" var="map">
					                                          <option value="${map.key}">${map.value}</option>
				                                            </c:forEach>		
														</select>
														
													</td>
													
												</tr>
												<tr>
													<td align="right">
													<span class="required">*</span>
														二级客户代码：
													</td>
													<td>
													  
													  <textarea id="code" name="code" rows="3" cols="55"  onkeypress="return suffixNamePress()" style="width:150" /></textarea>
													  (格式逗号分隔，如01,02,99)
													</td>
													
												</tr>
												<tr>
												  <td>&nbsp;</td>
												  <td >
													      起始&nbsp;&nbsp;<input type="text" id="startCode" name="startCode"  maxlength="2" onkeypress="return onlyNumberInput()" style="ime-mode:disabled;width:20;" class="input_text"  />&nbsp;&nbsp;
													      结束&nbsp;&nbsp;<input type="text" id="endCode" name="endCode" maxlength="2" onkeypress="return onlyNumberInput()" style="ime-mode:disabled;width:20;" class="input_text" />
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
							<rightButton:rightButton name="添加" onclick="" className="btn_sec" action="/timebargain/firmSet/tradePrivilege/addCustomer.action" id="add"></rightButton:rightButton>
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