<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <base target="_self"/>
		<title>添加加盟商所辖用户</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${basePath}/mgr/app/broker/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${basePath}/mgr/app/broker/js/firmjson.js"></script>
		
		<script type="text/javascript">
		jQuery(document).ready(function() {

			//获取交易商代码下拉列表
			var firmList = getFirmList();
			$("#firmId").focus().autocomplete(firmList);

			//ajax验证交易商代码是否存在
			jQuery("#frm").validationEngine( {
				ajaxFormValidation : true,
				ajaxFormValidationURL : "${basePath }/ajaxcheck/broker/formCheckFirmByFirmId.action",
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
						// 修改信息URL
						var updateDemoUrl = $("#update").attr("action");
						//全 URL 路径
						var url = "${basePath}"+updateDemoUrl;
						$("#frm").attr("action",url);
						frm.submit();
					}
				}
			}

			//修改按钮注册点击事件
			$("#update").click(function(){
				//验证信息
				if ($("#frm").validationEngine('validateform')) {
				}
			});
		  });
			
		</script>
	</head>
<body>
		<form id="frm" method="post" targetType="hidden">
			<div class="div_cx">
				<table border="0" width="100%" align="center">
			
					<tr>
						<td>
							<table border="0" width="100%" align="center">
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												添加加盟商所辖用户
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
												<div>
									<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
											       
											   <tr>
													<td align="right">														
														加盟商编号：
													</td>
													<td>
													 ${brokerId}																							   
													</td>
													<input type="hidden" name="entity.brokerId" value="${brokerId}" />								
											   </tr>
										      <tr height="35">
								                  <td align="right" class="td_size" width="20%">
									                <span class="required">*</span> 交易商代码 ：
								                  </td>
							                      <td align="left" width="45%">
									                 <input id="firmId" style="width: 160px;"
										             name="entity.firmId" type="text" 
										               class="validate[required,ajax[mouseCheckFirmByFirmId]] input_text" />
								                  </td>
								                 <td align="left">
									               <div class="onfocus">
										                                    不能为空！
									               </div>
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
		 <div class="tab_pad" >
				<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
					<tr>
						<td align="center">
							<rightButton:rightButton name="保存" onclick="" className="btn_sec" action="/broker/brokerManagement/addBrokerFirm.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">返回</button>
						</td>
					</tr>
				</table>
		</div>
	</form>
</body>
