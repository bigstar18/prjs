<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <base target="_self"/>
		<title>支付佣金</title>
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

						//验证支付佣金是否大于待付佣金
						if(canReward()){
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
						
					}
				});
			});

			//仅输入数字和.
			function onlyNumberInput()
			{
			  if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)
			  {
			    event.returnValue=false;
			  }
			}

			//验证支付佣金是否大于待付佣金
			function canReward(){
				if(jQuery("#money").val() <= 0){
					alert("支付佣金必须大于0，请重新设置");
					return false;
				}
				
				//var money = document.getElementByID("money");
				if(jQuery("#money").val() > ${entity.amount}){
					alert("支付佣金不能大于待付佣金，请重新设置");
					return false;
				}

				return true;
			}
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
												支付佣金
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
												<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
											       
											   <tr>
													<td align="right">														
														服务商编号：
													</td>
													<td>
													 ${ entity.brokerId}	
																						   
													</td>
													<input type="hidden" name="entity.brokerId" value="${entity.brokerId}" />
													<input type="hidden" name="entity.moduleId" value="${entity.moduleId}" />
													<input type="hidden" name="entity.occurDate" value="<fmt:formatDate value="${entity.occurDate}" pattern="yyyy-MM-dd"/>" />								
											   </tr>
											   <tr>
													<td align="right">							
														待付佣金：
													</td>
													<td>
													 ${entity.amount}												   
													</td>	
													<input id="amount" type="hidden" name="entity.amount" value="${ entity.amount}" />								
											   </tr>
											   <tr>
													<td align="right">														
														已付佣金：
													</td>
													<td>
													 ${entity.paidAmount}												   
													</td>	
													<input type="hidden" name="entity.paidAmount" value="${ entity.paidAmount}" />								
											   </tr>
										     <tr height="35">
								                <td align="right" class="td_size" width="20%">
									                                        支付佣金 ：
								               </td>
								               <td align="left" width="35%">
									             <input id="money" style="width: 160px;"
										            name="money" type="text" onkeypress="return onlyNumberInput()"
										                class="validate[required,maxSize[13],custom[onlyDoubleSp]] input_text" />
										              <span class="required">*</span>
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
							<rightButton:rightButton name="提交" onclick="" className="btn_sec" action="/broker/brokerDataquery/updateBrokerReward.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">关闭窗口</button>
						</td>
					</tr>
				</table>
		</div>
	</form>
</body>