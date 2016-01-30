<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css"
		type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript"></script>
	<script
		src="${basePath }/mgr/app/bill/js/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
		</script>
	<script type="text/javascript">
	jQuery(document).ready(function(){
		//  展示仓单对应信息 
	//	var value = ${outStock.deliveryStatus};
		
		var value ;
			if( $("#ValueId").val()!="" || $("#ValueId").val()!=null ){
				value=$("#ValueId").val();
			}
		
		
		
		
		 /*  if(value==0){
		 		document.getElementById('idnumbertr').style.display='block';
		 		document.getElementById('addressphone').style.display='none';		 		
		 		
				document.getElementById("span").innerText = "用户自提";
			 		 
			  }
		  if(value==1){
			  	document.getElementById('idnumbertr').style.display='none';
			  	document.getElementById('addressphone').style.display='';
			  	
			    document.getElementById("span").innerText = "配送";
			  		
			  }		 */
				if(value==0){
				 		$("#addressphone").attr("style","display:none");
				 		 $("#deliveryStatus").find("option[value='0']").attr("selected",true);
				 		
						 		$("#span").text("用户自提");
					 		
					  }
				  if(value==1){
				 		$("#idnumbertr").attr("style","display:none");
					  	$("#deliveryStatus").find("option[value='1']").attr("selected",true);
					
						 		$("#span").text("配送");
					  		
					  }		
});
	
	</script>
	<head>
		<title>仓单详情</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
	<body style="overflow-y: hidden">
		<form id="frm" method="post">
		<input type="hidden" id="ValueId" value="${outStock.deliveryStatus }" />
			<div
				style="overflow-x: hidden; position: relative;  overflow: auto; height: 400px; width: 100%;">
				<div class="div_cx">
					<table border="0" width="95%" align="center">
						<tr>
							<td>
								<div class="warning">
									<div class="content">
										温馨提示 :仓单详情
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
													仓单信息
												</div>
												<div class="div_cxtjR"></div>
											</div>
											<div style="clear: both;"></div>
											<div>
												<table border="0" cellspacing="0" cellpadding="4"
													width="100%" align="center" class="table2_style">
													<tr>
														<td align="right" width="20%">
															仓单号：
														</td>
														<td width="20%">
															${entity.stockId}
														</td>
														<!-- 
														<td align="right" width="20%">
															仓库编号：
														</td>
														<td width="30%">
															${entity.warehouseId}
														</td>
														 -->
														<td align="right" width="20%">
															&nbsp;
														</td>
														<td width="30%">
															&nbsp;
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															仓库原始凭证号：
														</td>
														<td width="30%">
															<div style="width:185px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;wzy:expression(void(this.title=this.innerText))">${entity.realStockCode}</div>
														</td>
														<td align="right" width="20%">
															所属交易商：
														</td>
														<td width="20%">
															${entity.ownerFirm}
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															品名：
														</td>
														<td width="20%">
															${entity.breed.breedName}
														</td>
														<td align="right" width="20%">
															商品数量：
														</td>
														<td width="30%"><div style="width:185px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;wzy:expression(void(this.title=this.innerText))">
															<fmt:formatNumber value="${entity.quantity}"
																pattern="#,##0.00" />（${entity.unit }）</div>
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															创建时间：
														</td>
														<td width="20%">
															<fmt:formatDate value="${entity.createTime}"
																pattern="yyyy-MM-dd HH:mm:ss" />
														</td>
													<c:if test="${entity.lastTime !=null}">
														<td align="right" width="20%">
															最后变更时间：
														</td>
														<td width="30%">
															<fmt:formatDate value="${entity.lastTime}"
																pattern="yyyy-MM-dd HH:mm:ss" />
														</td>
													</c:if>
													</tr>
													<c:if test="${entity.stockStatus==2 || entity.stockStatus==5}">
													<tr>
														<td align="right">
															提货方式：
														</td>
														<td>
															<span id="span"></span>
														</td>
														<%-- <td align="right">
															提货密钥：
														</td>
														<td>
															${outStock.key}
														</td> --%>
														<td align="right">
															提货人：
														</td>
														<td>
															${outStock.deliveryPerson}
														</td>
													</tr>
													
													<tr id="idnumbertr" >
														<td align="right" width="20%">
															提货人身份证：
														</td>
														<td width="20%">
															${outStock.idnumber}
														</td>
													</tr>
													<tr id="addressphone" >
														<td align="right" width="20%">
															电话：
														</td>
														<td width="20%">
															${outStock.address}
														</td>
														<td align="right" width="20%">
															地址：
														</td>
														<td width="20%">
															${outStock.phone}
														</td>
													</tr>
													</c:if>
												</table>
											</div>
										</td>
									</tr>
								</table>

							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" align="center">
									<c:forEach var="map" items="${tpmap}">
									<tr>
										<td>
											<div class="div_cxtj">
												<div class="div_cxtjL"></div>
												<div class="div_cxtjC">
													${map.key.name}
												</div>
												<div class="div_cxtjR"></div>
											</div>
											<div style="clear: both;"></div>
											<div>
												<table border="0" cellspacing="0" cellpadding="4"
													width="100%" align="center" class="table2_style">
													<c:if test="${not empty map.value }">
														<c:set var="propertysize"
															value="${fn:length(map.value)}"></c:set>
														<tr>
															<c:forEach var="property"
																items="${map.value }" varStatus="status">
																<c:if
																	test="${(status.count-1)%2==0 and status.count!=1}">
														</tr>
														<tr>
													</c:if>
													<td align="right" width="20%" scope="row">
														${property.propertyName}：
													</td>
													<td width="30%">
														${property.propertyValue}
													</td>
													</c:forEach>
													<c:if test="${propertysize%2!=0}">
														<c:forEach begin="1" end="${2-(propertysize%2)}">
															<th align="center" valign="middle" scope="row">
																&nbsp;
															</th>
															<td align="center" valign="middle">
																&nbsp;
															</td>
														</c:forEach>
													</c:if>
													</tr>
													</c:if>
												</table>
											</div>
										</td>
									</tr>
									</c:forEach>
								</table>
							</td>
						</tr>

						<tr>
							<td align="right">
								<button class="btn_sec" onClick=window.close();>
									关闭
								</button>
								&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>

	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>