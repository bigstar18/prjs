<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
	<head>
	    <base target="_self"/>
		<title>加盟商添加</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${basePath}/mgr/app/broker/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
   
  <script type="text/javascript">
    jQuery(document).ready(function() {

		//ajax验证交易商代码是否存在
		jQuery("#frm").validationEngine( {
			ajaxFormValidation : true,
			ajaxFormValidationURL : "${basePath }/ajaxcheck/broker/formCheckBrokerBrokerId.action",
			onAjaxFormComplete : ajaxValidationCallback,
			onBeforeAjaxFormValidation : beforeCall
		});

		//提交前事件
		function beforeCall(form, options) {
			return true;
		}

		//提交后事件
		function ajaxValidationCallback(status, form, json, options) {
			//如果返回成功
			if (status === true) {
				var vaild = affirm("您确定要操作吗？");
				if(vaild){
					//添加信息URL
					var addDemoUrl = $("#add").attr("action");
					//全 URL 路径
					var url = "${basePath}"+addDemoUrl;
					$("#frm").attr("action",url);
					frm.submit();
				}
			}
		}

		//添加按钮注册点击事件
		$("#add").click(function(){
			//验证信息
			if ($("#frm").validationEngine('validateform')) {
			}
		});
	  });
	          
			// 验证邮箱是否正确
//			function validateEmail(obj) {
//				var objValue = obj.value;
//				if(objValue.length==0){ 
//					return false;
//				}
//				var regExp = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
//				if(regExp.test(objValue)){
//					return true;
//				}else{
//					alert("邮箱格式不合法！！");
//					obj.value="";
//					obj.focus();
//					return false;
//				}
//			}
			//返回
			function back(){
				
				//获取配置权限的 URL
				document.location.href = "${basePath}/broker/brokerManagement/listBroker.action";
			
				}
		</script>
<head>
	<title>添加加盟商</title>
</head>
<body >
	<form id="frm" name="frm" method="post" 
		action="${basePath }/broker/brokerManagement/addBroker.action">
		<div class="div_tj">
			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div  class="content">
								温馨提示 :添加加盟商
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="div_cxtj">
							<div class="div_cxtjL"></div>
							<div class="div_cxtjC">
								加盟商信息
							</div>
							<div class="div_cxtjR"></div>
						</div>
						<div style="clear: both;"></div>
						<table border="0" cellspacing="6" cellpadding="0" width="100%"
							align="center" class="st_bor">
			
							<tr >
								<td align="right" class="td_size" width="20%">
									<span class="required">*</span> 加盟商编号 ：
								</td>
								<td align="left" width="45%">
									<input id="brokerId" style="width: 160px;" name="entity.brokerId"
										type="text" 
										class="validate[required,maxSize[4],ajax[mouseCheckBrokerBrokerId]] input_text" />
								</td>
								<td align="left">
									<div class="onfocus">
										不能为空！
									</div>
								</td>
							</tr>
							<tr >
								<td align="right" class="td_size" width="20%">
									<span class="required">*</span> 加盟商帐号 ：
								</td>
								<td align="left" width="45%">
									<input id="firmId" style="width: 160px;" name="entity.firmId"
										type="text" class="validate[required,ajax[mouseCheckFirmByFirmId]] input_text datepicker" />
								</td>
								<td align="left">
									<div class="onfocus">
										不能为空！
									</div>
								</td>
							</tr>
							
							<tr >
								<td align="right" class="td_size" width="20%">
									<span class="required">*</span> 密码 ：
								</td>
								<td align="left" width="45%">
									<input id="password" style="width: 160px;"
										name="entity.password" type="password"
										class="validate[required,custom[password],maxSize[64]] input_text" />
								</td>
								<td align="left">
									<div class="onfocus">
										不能为空！
									</div>
								</td>
							</tr>

							<tr >
								<td align="right" class="td_size" width="20%">
									<span class="required">*</span> 确认密码 ：
								</td>
								<td align="left" width="45%">
									<input id="password1" style="width: 160px;" name="password1"
										type="password"
										class="validate[required,equals[password]] input_text" />
								</td>
								<td align="left">
									<div class="onfocus">
										不能为空！
									</div>
								</td>
							</tr>
							<tr >
								<td align="right" class="td_size" width="20%">
									<span class="required">*</span> 加盟商名称 ：
								</td>
								<td align="left" width="45%">
									<input id="name" style="width: 160px;" name="entity.name"
										type="text" class="validate[required,maxSize[32]] input_text" />
								</td>
								<td align="left">
									<div class="onfocus">
										不能为空！
									</div>
								</td>
							</tr>
							  <tr>
			                  <td align="right"><span class="required">*</span>会员类型：</td>
								 <td align="left">											
										<select id="borkerType" name="entity.borkerType"  class="validate[required]" style="width:100px;">
 												<option value="">请选择</option>
												<c:forEach items="${brokerTypeList}" var="result">
														<option value="${result.borkerType}">${result.brokerName}</option>
												</c:forEach>																	
										</select>																 
							  </td>
							  <td align="left">
									<div class="onfocus">
										不能为空！
									</div>
								</td>
							 </tr>
							<tr >
								<td align="right" class="td_size" width="20%">
									 电话 ：
								</td>
								<td align="left" width="45%">
									<input id="telephone" style="width: 160px;" name="entity.telephone"
										type="text" class="validate[maxSize[20],custom[phone]] input_text" />
								</td>
							</tr>
							<tr >
								<td align="right" class="td_size" width="20%">
									 手机 ：
								</td>
								<td align="left" width="45%">
									<input id="mobile" style="width: 160px;" name="entity.mobile"
										type="text" class="validate[maxSize[11],custom[mobile]] input_text" />
								</td>
							</tr>
							<tr >
								<td align="right" class="td_size" width="20%">
									电子邮件 ：
								</td>
								<td align="left" width="45%">
									<input id="email" style="width: 160px;" name="entity.email" 
										type="text" class="validate[maxSize[20],custom[email]] input_text" />
								</td>
							</tr>
							<tr >
								<td align="right" class="td_size" width="20%">
									 地址 ：
								</td>
								<td align="left" width="45%">
									<input id="address" style="width: 160px;" name="entity.address"
										type="text" class="validate[maxSize[32]] input_text" />
								</td>
							</tr>
							<tr >
								<td align="right" class="td_size" width="20%">
									市场开发人员 ：
								</td>
								<td align="left" width="45%">
									<input id="marketManager" style="width: 160px;" name="entity.marketManager"
										type="text" class="validate[maxSize[32]] input_text" />
								</td>
							</tr>
							<tr >
								<td align="right" class="td_size" width="20%">
									会籍期限 ：
								</td>
								<td align="left" width="45%">
								   <input type="text" class="wdate" id="timeLimit"  style="width: 100px" 
									       name="entity.timeLimit" onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />										     											       										
								</td>
							</tr>
							<tr >
            	                  <td align="right" > 区域 ：</td>
                                     <td align="left">
                	                    <select id="areaIds" name="entity.areaIds" class="validate[]" style="width:100px;">
                	                      <option value="">请选择</option>
                	                       <c:forEach items="${brokerAreaList}" var="result">
	                                         <option value="${result.areaId}">${result.name}</option>
		                                   </c:forEach>
                	                    </select>
                                   </td>                              
			                </tr>
			              
							<tr >
								<td align="right" class="td_size">
									备注 ：
								</td>
								<td align="left">
									<textarea id="note" name="entity.note" cols="50"
										rows="4" class="validate"></textarea>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="100%"
				align="center">
				<tr height="35">
					<td align="center">
						<rightButton:rightButton name="添加" onclick=" " className="btn_sec"
							action="/broker/brokerManagement/addBroker.action" id="add"></rightButton:rightButton>
						&nbsp;&nbsp;
						<button class="btn_sec" onClick="window.close();">
							关闭
						</button>
						&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</div>

	</form>
</body>

<%@ include file="/mgr/public/jsp/footinc.jsp"%>