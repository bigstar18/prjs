<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <base target="_self"/>
		<title>设置密码</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//修改按钮注册点击事件
				$("#update").click(function(){
					//验证信息
					if(jQuery("#frm").validationEngine('validate')){
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
			});
		</script>
	</head>
<body>
		<form id="frm" method="post" >
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
												设置加盟商密码
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
													 ${ entity.brokerId}	
																						   
													</td>
													<input type="hidden" name="entity.brokerId" value="${entity.brokerId}" />								
											   </tr>
										<tr height="35">
								              <td align="right" class="td_size" width="20%">
									            <span class="required">*</span> 新密码 ：
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

							          <tr height="35">
								            <td align="right" class="td_size" width="20%">
									             <span class="required">*</span> 新密码确认 ：
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
							<rightButton:rightButton name="保存" onclick="" className="btn_sec" action="/broker/brokerManagement/updateBrokerPassword.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">返回</button>
						</td>
					</tr>
				</table>
		</div>
	</form>
</body>