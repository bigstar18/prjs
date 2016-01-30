<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<title>品种分类添加</title>
		<script type="text/javascript"> 
		$(document).ready(function() {
			jQuery("#frm").validationEngine('attach');
			$("#update").click(function(){
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
			$("#add").click(function(){
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
				var updateDemoUrl = $("#back").attr("action");
				//全 URL 路径
				var url = "${basePath}"+updateDemoUrl;

				document.location.href = url;
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
									温馨提示 :设置商品分类信息
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
												添加商品分类
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
												<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														品种分类名称：
													</td>
													<td>
													   <input type="text" id="sortName" name="entity.sortName" maxlength="15" value="${entity.sortName }"
															class="validate[required] input_text datepicker"/>
													</td>
													<c:if test="${crud == 'update'}">
													<input type="hidden" id="sortID" name="entity.sortID" value="${entity.sortID }" />
													</c:if>
													<input type="hidden" name="crud" value="${crud }">
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
			<input type="hidden" id="maxHoldQty" value="99999999"/>
			
			<div>
				<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
					<tr>
						<td align="center">
						    <c:if test="${crud == 'create'}">
						    <rightButton:rightButton name="添加" onclick="" className="btn_sec" action="/timebargain/tradeparams/addBCategory.action" id="add"></rightButton:rightButton>
						    </c:if>
							<c:if test="${crud == 'update'}">
						    <rightButton:rightButton name="提交" onclick="" className="btn_sec" action="/timebargain/tradeparams/updateBCategory.action" id="update"></rightButton:rightButton>					    
						    </c:if>
							&nbsp;&nbsp;
							<rightButton:rightButton name="返回" onclick="" className="btn_sec" action="/timebargain/tradeparams/addBCategoryList.action" id="back"></rightButton:rightButton>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
