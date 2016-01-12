<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>强制转让信息</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${basePath}/mgr/app/timebargain/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
		<script src="${basePath}/mgr/app/timebargain/js/tool.js" type="text/javascript" charset="GBK"></script>
		
		<script type="text/javascript">
		  jQuery(document).ready(function() {
			jQuery("#frm").validationEngine('attach');
			//添加按钮注册点击事件
			$("#add").click(function(){
				//验证信息
				if(jQuery("#frm").validationEngine('validate')){

				  if(save_onclick()){
					  var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var addUrl = $(this).attr("action");
							//全 URL 路径
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

		    function priceTypeSelect(obj){
				if(!obj.checked) {
				
					frm.price.value = ${price };
					setReadWrite(frm.price);
				}else {
					
					frm.price.value = "0";
					setReadOnly(frm.price);
				}
			}

		    // 关闭窗口
		    function close_close(){
		    	window.returnValue = true;
				window.close();
			}

			function save_onclick(){
				var quantity = document.getElementById("quantity").value;
				if(parseInt(quantity) <=0 )
				  {
				    alert("平仓数量必须大于0");
				    document.getElementById("quantity").focus();
				    return false;
				}
				
				if(frm.type.value == "0"){
					alert("登录者为代为委托员，不能进行强制转让操作！");
			    	return false;
				} 

				return true;
			}

			
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
									温馨提示 :强制转让信息
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
												对应订货信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														交易商代码：
													</td>
													<td>
														${entity.firmID}&nbsp;
														<input type="hidden" id="firmID" name="entity.firmId" value="${entity.firmID}"/>
														<input type="hidden" id="customerID" name="entity.customerId" value="${entity.customerID}"/>
													</td>
													<td align="right">
														商品代码：
													</td>
													<td>
														${entity.commodityID}&nbsp;
														<input type="hidden" id="commodityID" name="entity.commodityId" value="${entity.commodityID}"/>
													</td>
												</tr>
												<tr>
													<td align="right">
														订货数量：
													</td>
													<td>
														${entity.openQty}&nbsp;
													</td>
													<td align="right">
														可转让数量：
													</td>
													<td>
														${closeQty}&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right" >
														买卖：
													</td>
													<td>
														<c:choose>
	            		                                  <c:when test="${entity.BSFlag eq '1'}">
	            			                                                                                       买进
	            		                                  </c:when>
	            		                                  <c:when test="${entity.BSFlag eq '2'}">
	            			                                                                                       卖出
	            		                                  </c:when>
	            	                                    </c:choose>
	            	                                    <input type="hidden" id="BS_Flag" name="entity.BS_Flag" value="${entity.BSFlag }"/>  
	            	                                      &nbsp;
													</td>
													<td align="right" >
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													
												</tr>
												
											</table>
										</div>
									</td>
								</tr>
								
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												平仓信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
													   <span class="required">*</span>
														强制转让价格方式：
													</td>
													<td>
														按市价:<input type="checkbox" id="priceType" name="priceType" value="1" onselect="" onclick="priceTypeSelect(this);">
														&nbsp;
													</td>
													
												</tr>
												<tr>
												  <td align="right">
												     <span class="required">*</span>
														委托价格：
												  </td>
												  <td>
														<input type="text" id="price" name="entity.price" onkeypress="return onlyDoubleInput()" value="${price }"
															class="validate[required,maxSize[10],custom[onlyDoubleSp]] input_text "/>
														&nbsp;
												  </td>
												  <td>
														<div class="onfocus">不能为空！</div>
												  </td>
												</tr>
												<tr>
													<td align="right">
													   <span class="required">*</span>
														平仓数量：
													</td>
													<td>
														<input type="text" id="quantity" name="entity.quantity" onkeypress="onlyNumberInput()" value="${closeQty }"
								                               class="validate[required,maxSize[10],custom[onlyNumberSp]] input_text"/>
														&nbsp;
													</td>
													<td>
														<div class="onfocus">不能为空！</div>
												   </td>
												</tr>
											
												<input type="hidden" id="rel" name="rel" />
											</table>
											<font color="red">强转参考价格：<span id="">买强转默认价格为跌停版价格；卖强转默认价格为涨停板价格</span></font>
											
										</div>
									</td>
								</tr>
								<input type="hidden" id="type" name="type" value="${type }"/>
								<input type="hidden" id="orderType" name="entity.orderType" value="2"/>
								<input type="hidden" id="closeFlag" name="entity.closeFlag" value="2"/>
								<input type="hidden" id="closeMode" name="entity.closeMode" value="1"/>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
					<tr>
						<td align="center">
							<rightButton:rightButton name="强制转让" onclick="" className="btn_sec" action="/timebargain/authorize/detailForceClose.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="close_close()">关闭</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>