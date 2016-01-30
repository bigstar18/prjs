<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
	<head>
		<title>修改交易商权限</title>
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
							//修改信息URL
							var updateUrl = $(this).attr("action");
							//全 URL 路径
							var url = "${basePath}"+updateUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
					}
				});
			});
		</script>
		<style type="text/css">
		   .yin{
		         display: none;
		         position:absolute;
		   }
		   .xian{
		         display: block;
		   }
		 
		</style>
	    <script type="text/javascript">
	    
		  function setDisabled(obj)
		  {
			  obj.disabled = true;
			  obj.style.backgroundColor = "#C0C0C0";
		  }
		  function setEnabled(obj)
		  {
			  obj.disabled = false;
			  obj.style.backgroundColor = "white";
		  }

		  function kind_click(){
				 var kind = document.getElementById("kind").value;
				 
				 if (kind == "") {
					document.getElementById("commodity").className = 'yin';
					document.getElementById("breed").className = 'yin';
			     }
				 else if (kind == "1") {
					document.getElementById("breed").className = 'xian';
					document.getElementById("commodity").className = 'yin';

					document.getElementById("breedID").value = "${entity.kindID}";
					setDisabled(document.getElementById('breedID'));
				
				 }
				 else if (kind == "2") {
				    document.getElementById("commodity").className = 'xian';
					document.getElementById("breed").className = 'yin';

					document.getElementById("commodityID").value = "${entity.kindID}";
					setDisabled(document.getElementById('commodityID'));
				 }	
		  }

		  function window_onLoad(){
			  setDisabled(document.getElementById('kind'));
			  
			  kind_click();
			 
	      }	  
	      
	      
	    </script>
	</head>
	<body onload="window_onLoad()">
		<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<input type="hidden" name="entity.ID" value="${entity.ID}"/>
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :修改交易商权限
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
													<td align="right">
														权限种类：
													</td>
													<td>
														<select id="kind" name="entity.kind" class="validate[required]">
														  <option value="">请选择</option>
														  <c:forEach items="${tradePrivilege_kindMap}" var="map" >
															<option value="${map.key}">${map.value}</option>
														  </c:forEach>
														</select>
														<span class="required">*</span>
														<script type="text/javascript">
															document.getElementById("kind").value="${entity.kind}";
														</script>
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
													
												</tr>
												<tr id="commodity">
													<td align="right">
														上市商品：
													</td>
													<td>
														<select id="commodityID"  class="validate[required]">
														  <option value="">请选择</option>
														  <c:forEach items="${commodityList}" var="result" >
															<option value="${result.COMMODITYID}">${result.NAME}</option>
														  </c:forEach>
														</select>
														<span class="required">*</span>
													</td>
													
												</tr>
												<input type="hidden" id="kindID" name="entity.kindID" value="${entity.kindID }" />
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
														<script type="text/javascript">
															document.getElementById("privilegeCode_B").value="${entity.privilegeCode_B}";
														</script>
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
														<script type="text/javascript">
															document.getElementById("privilegeCode_S").value="${entity.privilegeCode_S}";
														</script>
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
							<rightButton:rightButton name="修改" onclick="" className="btn_sec" action="/timebargain/firmSet/tradePrivilege/updateFirmPrivilege.action" id="update"></rightButton:rightButton>
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